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
		c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In this case, if parent was red, then we didn’t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
		c.addTask(new TaskModel("Do Entire Project2", new GregorianCalendar(), "In this case, if parent was red, then we didn’t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
		TaskBoardModel d = new TaskBoardModel();
		d.addProject(c);
		MainScreen view = new MainScreen(d);
		view.setDefaultCloseOperation(EXIT_ON_CLOSE);
		view.setVisible(true);
		//c.clearView();
		c.addSection(new ProjectSection("Test"));
		//view.update();
		c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In this case, if parent was red, then we didn’t need to recur for prent, we can simply make it black (red + double black = single black)", "Done"), "Done");
	}
}
