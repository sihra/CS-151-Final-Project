import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainScreen extends JFrame implements ViewInterface{
	private ProjectView projView;
	private TaskBoardEditPanel editP;
	public void setLoggedIn(boolean b)
	{
		MainScreen ext = this;
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				ext.getContentPane().removeAll();
				if (b)
				{
					data = new TaskBoardModel();
					setLayout(new BorderLayout());
					projView = new ProjectView();
					editP = new TaskBoardEditPanel(data, ext);
					data.attach(editP);
					add(editP, BorderLayout.NORTH);
					add(projView, BorderLayout.SOUTH);
					pack();
				}
				else
				{
					LoginView lc = new LoginView();
					lc.setParent(ext);
					setLayout(new BorderLayout());
					getContentPane().add(lc, BorderLayout.CENTER);
					revalidate();
					repaint();
					pack();
				}
			}
		});

	}
	public ProjectView getProjectView()
	{
		return projView;
	}
	public void  updateSelectedView()
	{
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				remove(projView);
				projView = new ProjectView(data.getSelectedModel());
				add(projView, BorderLayout.SOUTH);
				revalidate();
				pack();
			}
		});
		
		
	}
	private TaskBoardView tBView;
	private TaskBoardModel data;
	public void update()
	{
		projView.update();
		revalidate();
		repaint();
	}
	public MainScreen(TaskBoardModel _data) {
		data = _data;
		data.attach(this);
		setLayout(new BorderLayout());
		projView = new ProjectView(data.getSelectedModel());
		editP = new TaskBoardEditPanel(_data, this);
		add(editP, BorderLayout.NORTH);
		add(projView, BorderLayout.SOUTH);
		pack();
	}
	public MainScreen()
	{
		setLoggedIn(false);
	}
	public void setData(TaskBoardModel _data)
	{
		data = _data;
		//System.out.println(data==null);
		data.attach(this);
		editP.setData(data);
		data.notifyViews();
		updateSelectedView();
	}
	public static void main(String[] args)
	{
		//ProjectModel c = new ProjectModel("Test");
		//c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In this case, if parent was red, then we didn’t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
		//c.addTask(new TaskModel("Do Entire Project2", new GregorianCalendar(), "In this case, if parent was red, then we didn’t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
		//TaskBoardModel d = new TaskBoardModel();
		//d.addProject(c);
		MainScreen view = new MainScreen();
		view.setDefaultCloseOperation(EXIT_ON_CLOSE);
		view.setVisible(true);
		//c.clearView();
		//c.addSection(new ProjectSection("Test"));

		//view.update();
		//c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In this case, if parent was red, then we didn’t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
		
	}
}
/**
 * This is a temp, i'm just using it to test the edit features
 * @author Zackary Finer
 *
 */
class TaskBoardEditPanel extends JPanel implements ViewInterface
{
	private TaskBoardModel data;
	public void setData(TaskBoardModel _data)
	{
		data = _data;
		data.attach(this);
	}
	private MainScreen parentScr;
	private JButton createNewB;
	private JButton editB;
	private JButton saveB;
	private JButton loadB;
	private JComboBox<String> sectionCB;
	public TaskBoardEditPanel(TaskBoardModel _data, MainScreen _parent) {
		data = _data;
		parentScr = _parent;
		data.attach(this);
		for (ProjectModel c: data)
		{
			c.attach(this);
		}
		editB = new JButton("edit");
		editB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.getSelectedModel()!=null){
				ProjectEditView d = new ProjectEditView(data.getSelectedModel());
				d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				d.setVisible(true);}
			}
		});
		createNewB = new JButton("Create New Project");
		createNewB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ProjectEditView d = new ProjectEditView(data.getController());
				d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				d.setVisible(true);
				
			}
		});
		saveB = new JButton("save");
		saveB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int val = fc.showSaveDialog(parentScr);
				
				if (val == JFileChooser.APPROVE_OPTION)
				{
					File selected = fc.getSelectedFile();
					try {
						TaskFileManager.saveToFile(data, selected);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		loadB = new JButton("load");
		loadB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int val = fc.showOpenDialog(parentScr);
				
				if (val== JFileChooser.APPROVE_OPTION)
				{
					File selected = fc.getSelectedFile();
					try{
					TaskBoardModel data = TaskFileManager.readFromFile(selected);
					parentScr.setData(data);
					}
					catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		sectionCB = new JComboBox<>();
		sectionCB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						data.setSelectedIndex(sectionCB.getSelectedIndex());
						if (sectionCB.getSelectedIndex()>=0)
						{
							//parentScr.getProjectView().updateSelected(data.getSelectedModel());
							parentScr.updateSelectedView();
						}
					}
				});

			}
		});
		for (ProjectModel c : data)
		{
			sectionCB.addItem(c.getName());
		}
		if (sectionCB.getItemCount() > 0)
		{
			sectionCB.setSelectedIndex(0);
		}
		setLayout(new FlowLayout());
		add(sectionCB);
		add(saveB);
		add(loadB);
		add(editB);
		add(createNewB);
		
	}

	@Override
	public void update() {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				//System.out.println(data.getProjectList().size());
				//System.out.println("RUN!!!");
				sectionCB.removeAllItems();
				System.out.println();
				for (ProjectModel c : data)
				{
					sectionCB.addItem(c.getName());
				}
				revalidate();
				
			}
		});
		
	}
}