import java.util.ArrayList;

public class ProjectController {
	ProjectModel model;
	public ProjectModel getModel()
	{
		return model;
	}
	public ProjectController(ProjectModel _model) {
		this.model = _model;
	}

	public void addColumn(String s) {
		model.addSection(new ProjectSection(s));
	}

	public void addTask(String s, TaskModel n) {
		model.addTask(n, s);
	}

	public ArrayList<ProjectSection> getSections() {
		return model.getSections();
	}

	public int getCount() {
		return model.count();
	}

	public int taskCount() {
		return model.taskCount();
	}
	
	public void updateTask(String s) {
		model.updateTask(s);
	}

	public ProjectSection getStatus(TaskModel t) {
		return model.getStatus(t);
	}
	
	public void transferTask(Object t, Object sec2) {
		TaskModel task = (TaskModel)t;
		String name = (String)sec2;
		model.transferTask(task, name);
	}
}
