import javax.swing.JFrame;

public class ProjectController {
	ProjectModel model;
	public ProjectController(ProjectModel _model) {
		this.model = _model;
	}
	public void addColumn(String s)
	{
		model.addSection(new ProjectSection(s));
	}
	public void addTask(String s, TaskModel n)
	{
		model.addTask(n, s);
		TaskView editTask = new TaskView(n, true, this);
		editTask.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editTask.setVisible(true);
	}
	public void updateTask(String s)
	{
		model.updateTask(s);
	}
}
