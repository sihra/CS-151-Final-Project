import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class ProjectView extends JPanel implements ViewInterface{
/*
 * So here's the deal, this thing is gunna need a scroll panel FOR SURE, it will contain a sub panel with the various columns
 * of tasks that the user has, as well as buttons to manipulate these columns. So what i'm thinking is, there is a private class
 * in here that is used to handle just the sections themselves.
 */
	class ColumnView extends JPanel {
		private static final int V_SPACING = 5;
		private ProjectSection model;
		private ProjectController parent;
		private JScrollPane scroller;
		private JPanel taskView;
		private DragListener dListener;
		private int count;
		public ColumnView(ProjectSection _model, ProjectController _parent, DragListener d)
		{
			model = _model;
			parent = _parent;
			taskView = new JPanel();
			dListener = d;
			taskView.setLayout(new BoxLayout(taskView, BoxLayout.Y_AXIS));
			
			for(TaskModel c : model)
			{
				taskView.add(new TaskLabel(c, controller, dListener));
				taskView.add(Box.createRigidArea(new Dimension(V_SPACING,V_SPACING)));
				count++;
			}
			taskView.revalidate();
			scroller = new JScrollPane(taskView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			//setLayout(new BorderLayout());
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setPreferredSize(new Dimension(175, 500));
			JButton addTaskB = new JButton("+");
			addTaskB.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					parent.addTask(model.getTitle(), new TaskModel("New Task", new GregorianCalendar(), "",model.getTitle()));
				}
			});
			add(new JLabel(model.getTitle()));
			add(scroller);
			add(addTaskB);
			addMouseListener(d);
			
		}
		public void update()
		{
			/*
			 * See explanation below
			 */
			EventQueue.invokeLater(new Runnable() { public void run() {
			if (count!=model.count())
			{
				count = 0;
				taskView.removeAll();
				for(TaskModel c : model)
				{
					taskView.add(new TaskLabel(c, controller,dListener));
					taskView.add(Box.createRigidArea(new Dimension(V_SPACING,V_SPACING)));
					count++;
				}
				taskView.revalidate();
			}
			for (int i = 0; i < taskView.getComponentCount(); i++)
			{
				if (taskView.getComponent(i) instanceof TaskLabel)//this is jank, should probably come up with a better solution
				{
					TaskLabel d = (TaskLabel)(taskView.getComponent(i));
					d.update();
				}
			}
			
			}});
		}
		public ProjectSection getModel()
		{
			return model;
		}
	}
	private ProjectController controller;
	private ProjectModel data;
	private JPanel taskColumns;
	private JScrollPane taskScroller;
	private DragListener dListener;
	private int count;
	public ProjectView()
	{
		//do nothing if there isn't any content to add
	}
	public ProjectView(ProjectModel _data) {
		dListener = new DragListener(this);
		data = _data;
		data.attach(this);
		setLayout(new BorderLayout());
		controller = new ProjectController(data);//the controller will designate methods for how to handle user input
		
		taskColumns = new JPanel();
		taskColumns.setLayout(new BoxLayout(taskColumns, BoxLayout.X_AXIS));
		for (ProjectSection c: data)
		{
			taskColumns.add(new ColumnView(c, controller,dListener));
			count++;
		}
		taskScroller = new JScrollPane(taskColumns, JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(taskScroller, BorderLayout.CENTER);
		
		JPanel buttons = new JPanel();
		JTextField columnName = new JTextField(40);
		JButton addColumnB = new JButton("Add Column");
		addColumnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addColumn(columnName.getText());
			}
		});
		buttons.add(columnName);
		buttons.add(addColumnB);
		add(buttons, BorderLayout.SOUTH);
	}
	public ArrayList<ColumnView> getSections()
	{
		ArrayList<ColumnView> _return = new ArrayList<>();
		for (int i = 0; i < this.getComponentCount(); i++)
		{
			if (this.getComponent(i) instanceof ColumnView)
			{
				_return.add((ColumnView)this.getComponent(i));
			}
		}
		return _return;
	}
	public void update()
	{
		/*
		 * Thanks to https://stackoverflow.com/questions/5060645/strange-swing-error-when-from-calling-removeall-on-subclass-of-jpanel
		 * to fix errors encured by changing the UI in the main thread, we create a runnable with the necessary changes and add it to
		 * the eventqueue later. The error occurs because the changes to the compenets are not registered in the
		 * AWT eventqueue thread swing uses.
		 * 
		 * Thus, by performing these operations in that thread we correct this issue.
		 * 
		 */
		ProjectView exterior = this;
		EventQueue.invokeLater(new Runnable() { public void run() {
			if (data!=null)
			{
			if (data.count()!=count)
			{
				count = 0;
				taskColumns.removeAll();
				for (ProjectSection c: data)
				{
					taskColumns.add(new ColumnView(c, controller, dListener));
				}
				taskColumns.revalidate();
			}
			for (int i = 0; i < taskColumns.getComponentCount(); i++)
			{
				ColumnView d = ((ColumnView)(taskColumns.getComponent(i)));
				d.update();
			}
			revalidate();
			repaint();
			}
	    }});

	}
	public ProjectController getController()
	{
		return controller;
	}
}
class DragListener implements MouseListener {
	ProjectView context;
	Component source;
	Component over;
	String destination;
	ArrayList<ProjectView.ColumnView> targetColumns;
	public DragListener(ProjectView _context) {
			context = _context;
			targetColumns = context.getSections();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		over = e.getComponent();
		if (over.getClass().getName().equals("ProjectView$ColumnView"))
		{
			destination = ((ProjectView.ColumnView)over).getModel().getTitle();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		source = e.getComponent();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				if (over instanceof ProjectView.ColumnView)
				{
					ProjectView.ColumnView c = (ProjectView.ColumnView) over;
					if (c.getModel().getTitle()==destination)
					{
						if (source instanceof TaskLabel)
						{
							TaskModel t = ((TaskLabel)(source)).getModel();
							context.getController().transferTask(t, destination);
							
						}
					}
					else
					{
						System.out.println("ERROR: TRANSFER LOCATION IS DIFFERENT");
					}
				}
				
			}
		});

		
	}
	
}
