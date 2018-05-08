import java.awt.Color;
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
	private TaskModel data;//reference to the task this label is displying
	private String text;
	private ProjectController controller;
	private boolean isHighlighted = false;
	public TaskLabel(TaskModel data, ProjectController caller)
	{
		controller = caller;
		setEditable(false);
		//setBackground(Color.BLUE);
		setLineWrap(true);
		setWrapStyleWord(true);
		/*
		setTransferHandler(new TransferHandler(propertyName));
		addMouseMotionListener(new MouseAdapter() {
		      public void mousePressed(MouseEvent evt) {
		          JComponent comp = (JComponent) evt.getSource();
		          TransferHandler th = comp.getTransferHandler();

		          th.exportAsDrag(comp, evt, TransferHandler.COPY);
		        }
		});
		*/
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt){
				TaskView c = new TaskView(data, false, controller);
				c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				c.setVisible(true);
			}
		});
		this.data = data;
		this.data.attach(this);
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
}
