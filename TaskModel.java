import java.util.ArrayList;
import java.util.GregorianCalendar;

public class TaskModel implements Comparable<TaskModel>{

	private String name;
	private GregorianCalendar end;
	private String text;
	public TaskModel(String name, GregorianCalendar end, String text)
	{
		this.name = name;
		this.end = end;
		this.text = text;
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
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setEnd(GregorianCalendar end) {
		this.end = end;
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
			return name.compareTo(other.name);
		}
		return decision;
	}
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
}
