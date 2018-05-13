import java.io.File;
import java.io.FileNotFoundException;

public class TaskBoardController {
	/*
	 * Change list:
	 * 1. this is a controller class, it should not contain a list of project models,
	 * instead it should use the taskboard model associated with it.
	 */
	public TaskBoardController(TaskBoardModel _model)
	{
		model = _model;
	}
	private TaskBoardModel model;
	private MainScreen view;
	private String taskBoardName;
	private String filePath;
	public void setBoardName(String name)
	{
		this.taskBoardName = name;
	}
	public String getBoardName()
	{
		return taskBoardName;
	}
	public void addBoardProject(ProjectModel p)
	{
		model.addProject(p);
	}
	public void setBoardFileName(String filePath)
	{
		this.filePath = filePath;
	}
	public String getBoardFileName()
	{
		return filePath;
	}
	public TaskBoardModel loadProjectFromFile(File location)
	{
		//TODO: use file loader here
		try {
			return TaskFileManager.readFromFile(location);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		//return null;
	}
	public void saveProjectsToFile(File location, TaskBoardModel c)
	{
		//TODO: use file loader here
		try {
			TaskFileManager.saveToFile(c, location);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setSelectedProject(int index)
	{
		model.setSelected(index);
	}
	public void update()
	{
		view.update();
	}
}
