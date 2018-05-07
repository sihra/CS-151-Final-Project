import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

public class MainScreen extends JFrame{
	private ProjectView projView;
	private TaskBoardModel data;
	public void update()
	{
		projView.update();
	}
	public MainScreen(TaskBoardModel _data) {
		data = _data;
		projView = new ProjectView(data.getSelectedModel());
		add(projView);
		pack();
	}
	public static void main(String[] args)
	{
		ProjectModel c = new ProjectModel();
		c.addTask(new TaskModel("Do the entire project", new GregorianCalendar(), "dsaffjhafdkljahfjdsa fadsjklfhdsajlf dhsajkfld sahjkfdash fkasd;fasadfafdas"), "TODO");
		TaskBoardModel d = new TaskBoardModel();
		d.addProject(c);
		MainScreen view = new MainScreen(d);
		view.setDefaultCloseOperation(EXIT_ON_CLOSE);
		view.setVisible(true);
	}
}
