import java.util.Iterator;
import java.util.TreeSet;

/**
 * A class used to represent a section of tasks in a project, graphically
 * this would be a column on the project view.
 * @author Zackary Finer
 *
 */
public class ProjectSection implements Iterable<TaskModel> {
	
	/** The list of tasks in this section, sorted in order of start date */
	private TreeSet<TaskModel> list;
	public TreeSet<TaskModel> getList() {
		return list;
	}
	public void setList(TreeSet<TaskModel> list) {
		this.list = list;
	}
	/** A string title associated with this section */
	private String title;
	/**
	 * Default constructor for project section object
	 * @param title - String title to be associated with this section
	 */
	public ProjectSection(String title)
	{
		this.title = title;
		list = new TreeSet<>();
	}
	/**
	 * Adds a task to this section
	 * @param t - TaskModel to be added to the section
	 */
	public void addTask(TaskModel t)
	{
		list.add(t);
	}
	public void updateTask()
	{
		TreeSet<TaskModel> temp = new TreeSet<>();
		
		for (TaskModel c : list)
			temp.add(c);
		
		list.clear();
		list.addAll(temp);
		//System.out.println(list.toString());
		//notifyAll();
	}
	/**
	 * Removes a task from the list of all tasks
	 * @param t - TaskModel to be removed
	 */
	public void remove(TaskModel t)
	{
		list.remove(t);
	}
	/**
	 * Returns the number of tasks in this section.
	 * @return Integer of the number of tasks in this section
	 */
	public int count()
	{
		return list.size();
	}
	/**
	 * Returns the title of this section
	 * @return String of the title of this section
	 */
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String newTitle)
	{
		title =  newTitle;
	}
	@Override
	public Iterator<TaskModel> iterator() {
		// TODO Auto-generated method stub
		return list.iterator();
	}
}
