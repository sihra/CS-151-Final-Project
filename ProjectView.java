import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProjectView extends JPanel {
/*
 * So here's the deal, this thing is gunna need a scroll panel FOR SURE, it will contain a sub panel with the various columns
 * of tasks that the user has, as well as buttons to manipulate these columns. So what i'm thinking is, there is a private class
 * in here that is used to handle just the sections themselves.
 */
	class ColumnView extends JPanel {
		private static final int V_SPACING = 5;
		private ProjectSection model;
		private JScrollPane scroller;
		private JPanel taskView;
		private int count;
		public ColumnView(ProjectSection _model)
		{
			model = _model;
			taskView = new JPanel();
			taskView.setLayout(new BoxLayout(taskView, BoxLayout.Y_AXIS));
			
			for(TaskModel c : model)
			{
				taskView.add(new TaskLabel(c));
				taskView.add(Box.createRigidArea(new Dimension(V_SPACING,V_SPACING)));
				count++;
			}
			taskView.revalidate();
			scroller = new JScrollPane(taskView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			//setLayout(new BorderLayout());
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setPreferredSize(new Dimension(175, 400));
			add(new JLabel(model.getTitle()));
			add(scroller);
			
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
					taskView.add(new TaskLabel(c));
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
	private ProjectModel data;
	private JPanel taskColumns;
	private JScrollPane taskScroller;
	private int count;
	public ProjectView(ProjectModel _data) {
		data = _data;
		taskColumns = new JPanel();
		taskColumns.setLayout(new BoxLayout(taskColumns, BoxLayout.X_AXIS));
		for (ProjectSection c: data)
		{
			taskColumns.add(new ColumnView(c));
			count++;
		}
		taskScroller = new JScrollPane(taskColumns, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(taskScroller);
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
		EventQueue.invokeLater(new Runnable() { public void run() {
			if (data.count()!=count)
			{
				count = 0;
				taskColumns.removeAll();
				for (ProjectSection c: data)
				{
					taskColumns.add(new ColumnView(c));
				}
				taskColumns.revalidate();
			}
			for (int i = 0; i < taskColumns.getComponentCount(); i++)
			{
				ColumnView d = ((ColumnView)(taskColumns.getComponent(i)));
				d.update();
			}
			revalidate();
	    }});

	}
}
