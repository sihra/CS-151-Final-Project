import java.util.ArrayList;
/**
 * Class dedicated to modeling a list of projects in a task board application.
 * contains a list of projects.
 * @author Zackary Finer
 *
 */
public class TaskBoardModel {
	private ArrayList<ProjectModel> projectList;
	private ArrayList<ViewInterface> views= new ArrayList<>();
	/**
	 * Adds a project to the list of projects in this task board model
	 * @param p - ProjectModel of the project
	 */
	public TaskBoardModel()
	{
		projectList = new ArrayList<>();
	}
	public void attach(ViewInterface view)
	{
		views.add(view);
	}
	public void addProject(ProjectModel p)
	{
		projectList.add(p);
		notifyViews();
	}
	public ProjectModel getSelectedModel()
	{
		return projectList.get(0);
	}
	public void notifyViews()
	{
		for (ViewInterface c : views)
		{
			c.update();
		}
	}
}
