import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * The purpose of this class is to hold the information of tasks within the TaskBoard
 * @author sihra
 *
 */

public class TaskModel implements Comparable<TaskModel>, Cloneable{

	private boolean isDirty = false;
	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	private String name;
	private GregorianCalendar end;
	private String text;
	private ArrayList<ViewInterface> views = new ArrayList<ViewInterface>(); // ArrayList of View classes that are used to represent parts of TaskModel
	private String category;
	public TaskModel()
	{
		this.name = "";
		this.end = null;
		this.text = "";
		this.category = "";
	}
	/**
	 * TaskModel constructor that initializes the Task variables and view class attached to TaskModel
	 * @param name - name of the task
	 * @param end - Due Date of the task
	 * @param text - Description of task
	 * @param category - Category of task
	 */
	public TaskModel(String name, GregorianCalendar end, String text, String category)
	{
		this.name = name;
		this.end = end;
		this.text = text;
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns the start date for this task
	 * @return Date representing the start date
	 */
	public GregorianCalendar getEnd()
	{
		return end;
	}
	/*
	 * Below was auto-generated
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		notifyViews();
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		notifyViews();
	}
	public void setEnd(GregorianCalendar end) {
		this.end = end;
		notifyViews();
	}
	/**
	 * Compares two tasks, first by date, then by title
	 * @return Integer representing the decision, -1 if less than, 1 if greater than, 0 if equal
	 */
	@Override
	public int compareTo(TaskModel other)
	{
		int decision;
		if ((decision=this.end.compareTo(other.end))==0)
		{
			if ((decision=this.name.compareTo(other.name))==0)
			{
				decision = this.text.compareTo(other.text);
			}
			
		}
		return decision;
	}
	public String toString()
	{
		SimpleDateFormat y = new SimpleDateFormat("MM-dd-yyyy");
		return "["+name+", "+y.format(end.getTime())+"]";
	}
	
	/**
	 * When is the equals method used?????************
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o.getClass().equals(this.getClass()))
		{
			TaskModel other = (TaskModel)o;
			return this.compareTo(other)==0;
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		ArrayList<Object> hasher = new ArrayList<>();
		hasher.add(name);
		hasher.add(text);
		hasher.add(end);
		return hasher.hashCode();
	}
	public void attach(ViewInterface view)
	{
		views.add(view);
	}
	public void notifyViews()
	{
		isDirty = true;
		for (ViewInterface c : views)
		{
			c.update();
		}
	}
	
	@Override
	public Object clone()
	{
		return new TaskModel(name, end, text, category);
	}
}
