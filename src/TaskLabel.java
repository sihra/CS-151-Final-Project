import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.awt.event.MouseAdapter;
import java.util.GregorianCalendar;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.event.MouseInputListener;

/**
 * The purpose of this class is to provide instructions for the graphical representation of a task
 * in the project view. 
 * @author Zackary Finer
 *
 */
public class TaskLabel extends JTextArea implements ViewInterface{
	private static final String propertyName = "Task";
	public static final int DEFAULT_WIDTH = 100;
	public static final int DEFAULT_HEIGHT = 100;
	private TaskModel data; //reference to the TaskModel of the current task this label is displaying
	private Color taskColor = Color.YELLOW;
	private String text; // Any text to be shown on screen
	private ProjectController controller; // ProjectController object to
	private boolean isHighlighted = false;
	public Color getTaskColor()
	{
		return taskColor;
	}
	public TaskLabel(TaskModel data, ProjectController caller, DragListener d)
	{
		controller = caller;
		setEditable(false); // Setter method for the JTextArea declaring it non editable
		setHighlighter(null);
		setBackground(taskColor); // Background color of the TextArea for the column itself
		setLineWrap(true); // Keeps the task in a closed rectangle space
		setWrapStyleWord(true); // Keeps words in the text area together

		// Creates and attaches a Mouse Listener to the label for this task, "listening" to if the task itself is clicked 
		
		addMouseListener(new MouseAdapter() {
			@Override
			// Translates to if the mouse is clicked on the task
			public void mousePressed(MouseEvent evt){
				// A TaskView is created for the task itself
				if (SwingUtilities.isRightMouseButton(evt))
				{
				TaskView c = new TaskView(data, false, controller);
				// Closes the mouse when the window is closed
				c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				c.setVisible(true);
				}
			}
		});
		//addMouseListener(new LabelDragListener(this, controller));
		this.data = data;
		this.data.attach(this);
		//setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		// Updates everything viewable
		update();
		addMouseListener(d);
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
		SimpleDateFormat p = new SimpleDateFormat("MM-dd-yyyy");
		
		text+="\n"+p.format(data.getEnd().getTime())+"\n";
	}
	public void update()
	{
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				updateText();
				setText(text);
				if (isHighlighted)
				{
					setBackground(Color.GREEN);
				}
				repaint();
				
			}
		});
	}
	public TaskModel getModel()
	{
		return data;
	}
}
