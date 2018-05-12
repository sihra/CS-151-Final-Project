import java.util.ArrayList;
import java.util.Iterator;
/**
 * Class dedicated to modeling a list of projects in a task board application.
 * contains a list of projects.
 * @author Zackary Finer
 *
 */
public class TaskBoardModel implements Iterable<ProjectModel>{
	private ArrayList<ProjectModel> projectList;
	private int selectedIndex;
	public ArrayList<ProjectModel> getProjectList() {
		return projectList;
	}
	public void setProjectList(ArrayList<ProjectModel> projectList) {
		this.projectList = projectList;
	}
	private ArrayList<ViewInterface> views= new ArrayList<>();
	private TaskBoardController controller;
	/**
	 * Adds a project to the list of projects in this task board model
	 * @param p - ProjectModel of the project
	 */
	public TaskBoardModel()
	{
		projectList = new ArrayList<>();
		controller = new TaskBoardController(this);
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
		return projectList.get(selectedIndex);
	}
	public void notifyViews()
	{
		for (ViewInterface c : views)
		{
			c.update();
		}
	}
	public void setSelected(int _index) throws IndexOutOfBoundsException
	{
		if (_index < projectList.size())
		{
			selectedIndex = _index;
			
		}
		else
		{
			throw new IndexOutOfBoundsException();
		}
		
	}
	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return projectList.iterator();
	}
	public TaskBoardController getController() {
		// TODO Auto-generated method stub
		return controller;
	}
}
