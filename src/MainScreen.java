import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;


public class MainScreen extends JFrame implements ViewInterface{
	private ProjectView projView;
	private SpringLayout layout;
	public ProjectView getProjectView()
	{
		return projView;
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
		tBView = new TaskBoardView(this, data);
		add(tBView, BorderLayout.NORTH);
		add(projView, BorderLayout.SOUTH);
		//pack();
	}
	public void  updateSelectedView()
	{
		MainScreen ext = this;
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				remove(projView);
				layout.removeLayoutComponent(projView);
				projView = new ProjectView(data.getSelectedModel());
				layout.putConstraint(SpringLayout.NORTH, projView, 0, SpringLayout.SOUTH, tBView);
				layout.putConstraint(SpringLayout.SOUTH, projView, 0, SpringLayout.SOUTH, ext.getContentPane());
				//layout.putConstraint(SpringLayout.WEST, ext.getContentPane(), 0, SpringLayout.WEST, projView);
				//layout.putConstraint(SpringLayout.EAST, ext.getContentPane(), 0, SpringLayout.EAST, projView);
				layout.putConstraint(SpringLayout.WEST, projView, 0, SpringLayout.WEST, ext.getContentPane());
				layout.putConstraint(SpringLayout.EAST, projView, 0, SpringLayout.EAST, ext.getContentPane());
				//layout.putConstraint(SpringLayout.WEST, ext.getContentPane(), 0, SpringLayout.WEST, projView);
				//layout.putConstraint(SpringLayout.EAST, ext.getContentPane(), 0, SpringLayout.EAST, projView);
				add(projView);
				revalidate();
				//pack();
			}
		});
	}
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
					tBView = new TaskBoardView(ext, data);
					data.attach(tBView);
					layout = new SpringLayout();
					setLayout(layout);
					layout.putConstraint(SpringLayout.NORTH, tBView, 0, SpringLayout.NORTH, ext.getContentPane());
					layout.putConstraint(SpringLayout.WEST, tBView, 0, SpringLayout.WEST, ext.getContentPane());
					layout.putConstraint(SpringLayout.EAST, tBView, 0, SpringLayout.EAST, ext.getContentPane());
					//layout.putConstraint(SpringLayout.WEST, ext.getContentPane(), 0, SpringLayout.WEST, editP);
					//layout.putConstraint(SpringLayout.EAST, ext.getContentPane(), 0, SpringLayout.EAST, editP);
					layout.preferredLayoutSize(ext);
					//layout.putConstraint(SpringLayout.SOUTH, editP, 50, SpringLayout.NORTH, ext.getContentPane());
					
					layout.putConstraint(SpringLayout.NORTH, projView, 50, SpringLayout.NORTH, ext.getContentPane());
					layout.putConstraint(SpringLayout.SOUTH, projView, 0, SpringLayout.SOUTH, ext.getContentPane());
					//layout.putConstraint(SpringLayout.WEST, projView, 0, SpringLayout.WEST, ext.getContentPane());
					//layout.putConstraint(SpringLayout.EAST, projView, 0, SpringLayout.EAST, ext.getContentPane());
					//layout.putConstraint(SpringLayout.WEST, ext.getContentPane(), 0, SpringLayout.WEST, projView);
					//layout.putConstraint(SpringLayout.EAST, ext.getContentPane(), 0, SpringLayout.EAST, projView);
					
					getContentPane().add(tBView);
					getContentPane().add(projView);
					pack();
					setSize(500,500);
					setMinimumSize(new Dimension(600,700));
					setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
				else
				{
					LoginView lc = new LoginView();
					lc.setParent(ext);
					setLayout(new BorderLayout());
					getContentPane().add(lc, BorderLayout.CENTER);
					revalidate();
					repaint();
					//pack();
				}
			}
		});
	}
	public MainScreen()
	{
		setSize(500,500);
		setLoggedIn(false);
	}
	public void setData(TaskBoardModel _data)
	{
		data = _data;
		//System.out.println(data==null);
		data.attach(this);
		tBView.setData(data);
		data.notifyViews();
		updateSelectedView();
	}
	public static void main(String[] args)
	{
		ProjectModel c = new ProjectModel("Test");
		c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In this case, if parent was red, then we didn�t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
		c.addTask(new TaskModel("Do Entire Project2", new GregorianCalendar(), "In this case, if parent was red, then we didn�t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
		TaskBoardModel d = new TaskBoardModel();
		d.addProject(c);
		MainScreen view = new MainScreen();
		view.setDefaultCloseOperation(EXIT_ON_CLOSE);
		view.setVisible(true);
		c.clearView();
		c.addSection(new ProjectSection("Test"));

		//view.update();
		//c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In this case, if parent was red, then we didn�t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
		
	}
}
///**
// * This is a temp, i'm just using it to test the edit features
// * @author Zackary Finer
// *
// */
//class TaskBoardEditPanel extends JPanel implements ViewInterface
//{
//	private TaskBoardModel data;
//	public void setData(TaskBoardModel _data)
//	{
//		data = _data;
//		data.attach(this);
//	}
//	private MainScreen parentScr;
//	private JButton createNewB;
//	private JButton editB;
//	private JButton saveB;
//	private JButton loadB;
//	private JComboBox<String> sectionCB;
//	public TaskBoardEditPanel(TaskBoardModel _data, MainScreen _parent) {
//		data = _data;
//		parentScr = _parent;
//		data.attach(this);
//		for (ProjectModel c: data)
//		{
//			c.attach(this);
//		}
//		editB = new JButton("edit");
//		editB.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (data.getSelectedModel()!=null){
//				ProjectEditView d = new ProjectEditView(data.getSelectedModel());
//				d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				d.setVisible(true);}
//			}
//		});
//		createNewB = new JButton("Create New Project");
//		createNewB.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				ProjectEditView d = new ProjectEditView(data.getController());
//				d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//				d.setVisible(true);
//				
//			}
//		});
//		saveB = new JButton("save");
//		saveB.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser fc = new JFileChooser();
//				int val = fc.showSaveDialog(parentScr);
//				
//				if (val == JFileChooser.APPROVE_OPTION)
//				{
//					File selected = fc.getSelectedFile();
//					try {
//						TaskFileManager.saveToFile(data, selected);
//					} catch (FileNotFoundException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			}
//		});
//		loadB = new JButton("load");
//		loadB.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser fc = new JFileChooser();
//				int val = fc.showOpenDialog(parentScr);
//				
//				if (val== JFileChooser.APPROVE_OPTION)
//				{
//					File selected = fc.getSelectedFile();
//					try{
//					TaskBoardModel data = TaskFileManager.readFromFile(selected);
//					parentScr.setData(data);
//					}
//					catch (FileNotFoundException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			}
//		});
//		sectionCB = new JComboBox<>();
//		sectionCB.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				EventQueue.invokeLater(new Runnable() {
//					
//					@Override
//					public void run() {
//						data.setSelectedIndex(sectionCB.getSelectedIndex());
//						if (sectionCB.getSelectedIndex()>=0)
//						{
//							//parentScr.getProjectView().updateSelected(data.getSelectedModel());
//							parentScr.updateSelectedView();
//						}
//					}
//				});
//
//			}
//		});
//		for (ProjectModel c : data)
//		{
//			sectionCB.addItem(c.getName());
//		}
//		if (sectionCB.getItemCount() > 0)
//		{
//			sectionCB.setSelectedIndex(0);
//		}
//		setLayout(new FlowLayout());
//		add(sectionCB);
//		add(saveB);
//		add(loadB);
//		add(editB);
//		add(createNewB);
//		
//	}
//
//	@Override
//	public void update() {
//		EventQueue.invokeLater(new Runnable() {
//			
//			@Override
//			public void run() {
//				//System.out.println(data.getProjectList().size());
//				//System.out.println("RUN!!!");
//				sectionCB.removeAllItems();
//				System.out.println();
//				for (ProjectModel c : data)
//				{
//					sectionCB.addItem(c.getName());
//				}
//				revalidate();
//				
//			}
//		});
//		
//	}
//}