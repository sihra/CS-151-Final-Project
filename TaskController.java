import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TaskController {
	TaskModel c;
	ProjectController d;
	public TaskController(TaskModel c, ProjectController d)
	{
		this.c = c;
		this.d = d;
	}
	public void setDescription(String desc)
	{
		c.setText(desc);
	}
	public void setTitle(String ti)
	{
		c.setName(ti);
	}
	public void setDueDate(String date)
	{
		//TaskModel newT = (TaskModel)c.clone();
		SimpleDateFormat dateParse = new SimpleDateFormat("MM-dd-yyyy");
		try {
			c.getEnd().setTime(dateParse.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updatePlacement();
		
	}
	public void updatePlacement()
	{
		d.updateTask(c.getCategory());
	}

}
