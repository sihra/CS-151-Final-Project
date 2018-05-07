import java.util.ArrayList;
import java.util.Date;

public class TaskModel implements Comparable<TaskModel>{
	private String name;
	private Date end;
	private String text;

	/**
	 * Returns the start date for this task
	 * @return Date representing the start date
	 */
	public Date getEnd()
	{
		return end;
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
