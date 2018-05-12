import java.io.File;

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
	public ProjectModel loadProjectFromFile(String filePath)
	{
		//TODO: use file loader here
		//return TaskFileManager.readFromFile(new File(filePath));
		return null;
	}
	public void saveProjectsToFile(String filePath)
	{
		//TODO: use file loader here
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
