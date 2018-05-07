import java.awt.Dimension;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * The purpose of this class is to provide instructions for the graphical representation of a task
 * in the project view.
 * @author Zackary Finer
 *
 */
public class TaskLabel extends JTextArea{
	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGHT = 100;
	private TaskModel data;//reference to the task this label is displying
	private String text;
	public TaskLabel(TaskModel data)
	{
		setEditable(false);
		setLineWrap(true);
		setWrapStyleWord(true);
		this.data = data;
		//setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		update();
	}
	private String parseDate()
	{
		String day = ""+data.getEnd().get(GregorianCalendar.DAY_OF_MONTH);
		String month = ""+data.getEnd().get(GregorianCalendar.MONTH);
		String year = ""+data.getEnd().get(GregorianCalendar.YEAR);
		return day+"/"+month+"/"+year;
	}
	public void updateText()
	{
		text = "Name: "+data.getName()+"\n";
		text+= "\nDescription: "+data.getText()+"\n";
		text+="\n"+parseDate()+"\n";
	}
	public void update()
	{
		updateText();
		setText(text);
		repaint();
	}

}
