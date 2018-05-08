import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TaskView extends JFrame implements ViewInterface {
	TaskModel data;
	private JTextArea description;
	private JTextField title;
	private JTextField date;
	private JButton editB;
	private JButton okB;
	private JButton closeB;
	private boolean isEditing;
	public TaskView(TaskModel _data, boolean editMode) {
		super("Task View");
		data = _data;
		data.attach(this);
		isEditing = editMode;
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//setLayout(new FlowLayout());
		setLayout(new BorderLayout());
		title = new JTextField();
		description = new JTextArea();
		//description.setPreferredSize(new Dimension(150, 300));
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		date = new JTextField();
		editB = new JButton("edit");
		okB = new JButton("Ok");
		closeB = new JButton("Close");
		JPanel options = new JPanel();
		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
		options.add(editB);
		options.add(okB);
		options.add(closeB);
		infoPane.add(title);
		infoPane.add(description);
		infoPane.add(date);
		add(infoPane, BorderLayout.CENTER);
		add(options, BorderLayout.SOUTH);
		update();
		pack();
		
	}
	@Override
	public void update() {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if (isEditing)
				{
					
				}
				//System.out.println(data.getName());
				title.setText(data.getName());
				description.setText(data.getText());
				SimpleDateFormat f = new SimpleDateFormat("MM-dd-yyyy");
				title.setEditable(isEditing);
				description.setEditable(isEditing);
				date.setEditable(isEditing);
				repaint();
				date.setText(f.format(data.getEnd().getTime()));
				
			}
		});
		
	}

}
