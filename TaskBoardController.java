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
			model.getController().markClean();
			TaskFileManager.saveToFile(c, location);
			model.getController().markClean();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void setSelectedProject(int index)
	{
		model.setSelectedIndex(index);
	}
	public boolean needSave()
	{
		boolean decision = model.isDirty();
		for (ProjectModel a : model)
		{
			decision = decision || a.isDirty();
			for (ProjectSection b: a)
			{
				for (TaskModel c : b)
				{
					decision = decision || c.isDirty();
				}
			}
		}
		return decision;
	}
	private void markClean()
	{
		model.setDirty(false);
		for (ProjectModel a : model)
		{
			a.setDirty(false);
			for (ProjectSection b: a)
			{
				for (TaskModel c : b)
				{
					c.setDirty(false);
				}
			}
			
		}
	}
	public void update()
	{
		view.update();
	}
}
