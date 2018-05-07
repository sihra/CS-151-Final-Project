import java.util.ArrayList;
/**
 * Class dedicated to modeling a list of projects in a task board application.
 * contains a list of projects.
 * @author Zackary Finer
 *
 */
public class TaskBoardModel {
	private ArrayList<ProjectModel> projectList;
	/**
	 * Adds a project to the list of projects in this task board model
	 * @param p - ProjectModel of the project
	 */
	public void addProject(ProjectModel p)
	{
		projectList.add(p);
	}
}
