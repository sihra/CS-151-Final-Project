import java.util.Date;

import javax.swing.JPanel;

public class TaskController extends JPanel {
	private TaskModel model;
	private TaskView view;
	
	public TaskController(TaskModel model, TaskView view) {
		this.model = model;
		this.view = view;
	}

	public String getTaskName() {
		return model.getName();
	}

	public void setTaskName(String taskName) {
		model.setName(taskName);
	}

	public String getTaskDescription() {
		return model.getDescription();
	}

	public void setTaskDescription(String taskDescription) {
		model.setDescription(taskDescription);
	}

	public String getTaskStatus() {
		return model.getStatus();
	}

	public void setTaskStatus(String taskStatus) {
		model.setStatus(taskStatus);
	}

	public Date getTaskDueDate() {
		return model.getDueDate();
	}

	public void setTaskDueDate(Date taskDueDate) {
		model.setDueDate(taskDueDate);
	}

	public int getTaskImportance() {
		return model.getImportance();
	}

	public void setTaskImportance(int taskImportance) {
		model.setImportance(taskImportance);
	}

	public void update() {
		view.update();
	}
}
