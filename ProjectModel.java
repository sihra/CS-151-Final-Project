import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class which contains data associated with a project, such as tasks
 * deadlines, and TODOs
 * @author Zackary Finer
 *
 */
public class ProjectModel implements Iterable<ProjectSection> {
	/*
	 * So this is the challenge, tasks need to be stored in such a way that their
	 * order is preserved, and they can be divided up into different sections.
	 * One idea could be to use a double array
	 */
	private ArrayList<ViewInterface> views = new ArrayList<>();
	/** the list of sections contained in this project, default will contain 4 sections */
	private ArrayList<ProjectSection> sections;
	/**
	 * Default constructor for project model objects, takes no parameters and
	 * initializes the section to contain todo, inprogress, review, and done
	 */
	public ProjectModel()
	{
		sections = new ArrayList<>();
		sections.add(new ProjectSection("TODO"));
		sections.add(new ProjectSection("In-Progress"));
		sections.add(new ProjectSection("Review"));
		sections.add(new ProjectSection("Done"));
	}
	public void attach(ViewInterface view)
	{
		views.add(view);
	}
	/**
	 * Adds a task to an associated section in the project model.
	 * @param t - TaskModel to be added to this project model
	 * @param section - String of the title of the sections which this model will be added to
	 * @throws NoSuchElementException if the associated section could not be located in this project
	 */
	public void addTask(TaskModel t, String section) throws NoSuchElementException
	{
		for (int i = 0; i < sections.size(); i++)//search for the right section
		{
			if (sections.get(i).getTitle().toLowerCase().equals(section.toLowerCase()))//if we find it
			{
				sections.get(i).addTask(t);//add the task
				notifyViews();
				return;//terminate
			}
		}
		throw new NoSuchElementException("The associated section " + section + " could not be found.");
	}
	public void updateTask(String section)
	{
		for (int i = 0; i < sections.size(); i++)//search for the right section
		{
			if (sections.get(i).getTitle().toLowerCase().equals(section.toLowerCase()))//if we find it
			{
				sections.get(i).updateTask();//update the specified task
				notifyViews();
				return;//terminate
			}
		}
		throw new NoSuchElementException("The associated section " + section + " could not be found.");
	}
	public void addSection(ProjectSection s)
	{
		sections.add(s);
		notifyViews();
	}
	public void clearView()
	{
		sections.clear();
		notifyViews();
	}
	/**
	 * Transfers a task from one section into another
	 * @param sec1 - String of the title of the section the task will be pulled from
	 * @param sec2 - String of the title of the section the task will be transfered to
	 * @param TaskModel - The task object to be transfered
	 */
	public void transferTask(String sec1, String sec2, TaskModel t)
	{
		//TODO: implement me, gotta find an associated section, and another, then find the task
	}
	@Override
	public Iterator<ProjectSection> iterator() {
		// TODO Auto-generated method stub
		return sections.iterator();
	}
	public boolean containsSection(String sec)
	{
		for (int i = 0; i < sections.size(); i++)
		{
			if (sections.get(i).getTitle().toLowerCase().equals(sec.toLowerCase()))
				return true;
		}
		return false;
	}
	public int count()
	{
		return sections.size();
	}
	public void notifyViews()
	{
		for (ViewInterface c : views)
		{
			c.update();
		}
	}
}
