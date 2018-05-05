import java.awt.Graphics;
import java.util.Date;

import javax.swing.JPanel;

public class TaskModel extends JPanel{
	private String name = "Task1";
	private String description;
	private Date dueDate;
	private String status;
	private int importance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public void paintComponents(Graphics g) {

	}

}
