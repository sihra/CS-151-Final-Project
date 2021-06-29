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
	private Integer selectedIndex = new Integer(0);
	
	public void setSelectedIndex(Integer selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	public Integer getSelectedIndex()
	{
		return selectedIndex;
	}
	public ArrayList<ProjectModel> getProjectList() {
		return projectList;
	}
	public void setProjectList(ArrayList<ProjectModel> projectList) {
		this.projectList = projectList;
	}
	private boolean isDirty = false;
	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
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
		if (projectList.size()>0)
		{
			return projectList.get(selectedIndex);
		}
		else
		{
			return null;
		}
	}
	public void notifyViews()
	{
		isDirty = true;
		for (ViewInterface c : views)
		{
			c.update();
		}
	}
	public void setSelectedIndex(int _index)
	{
		selectedIndex = _index;
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
