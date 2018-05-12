import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

public class MainScreen extends JFrame implements ViewInterface{
	private ProjectView projView;
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
		projView = new ProjectView(data.getSelectedModel());
		add(projView);
		pack();
	}
	public static void main(String[] args)
	{
		ProjectModel c = new ProjectModel();
		c.addTask(new TaskModel("Task1", new GregorianCalendar(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ", "Done"), "Done");
		c.addTask(new TaskModel("Task2", new GregorianCalendar(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ", "Done"), "Done");
		TaskBoardModel d = new TaskBoardModel();
		d.addProject(c);
		/*
		try {
			TaskFileManager.saveToFile(d, new File("Test.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		MainScreen view = new MainScreen(d);
		view.setDefaultCloseOperation(EXIT_ON_CLOSE);
		view.setVisible(true);
		//c.clearView();
		//c.addSection(new ProjectSection("Test"));
		//view.update();
		c.addTask(new TaskModel("Task3", new GregorianCalendar(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ", "Done"), "Done");
	}
}
