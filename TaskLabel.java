import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * The purpose of this class is to provide instructions for the graphical representation of a task
 * in the project view.
 * @author Zackary Finer
 *
 */
public class TaskLabel extends JTextArea{
	public static final int DEFAULT_WIDTH = 40;
	public static final int DEFAULT_HEIGHT = 40;
	private TaskModel data;//reference to the task this label is displying
	private String text;
	public TaskLabel(TaskModel data)
	{
		this.data = data;
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		text = "Name: "+data.getName()+"\n";
		text+= "\nDescription: "+data.getText()+"\n";
		text+="\n"+data.getEnd().toString()+"\n";
		setText(text);
	}
	public void update()
	{
		revalidate();
		repaint();
	}

}
