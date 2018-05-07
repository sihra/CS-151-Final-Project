import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A class which contains data associated with a project, such as tasks
 * deadlines, and TODOs
 * @author Zackary Finer
 *
 */
public class ProjectModel {
	/*
	 * So this is the challenge, tasks need to be stored in such a way that their
	 * order is preserved, and they can be divided up into different sections.
	 * One idea could be to use a double array
	 */
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
			if (sections.get(i).getTitle().equals(section))//if we find it
			{
				sections.get(i).addTask(t);//add the task
				return;//terminate
			}
		}
		throw new NoSuchElementException("The associated section " + section + " could not be found.");
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
	
}