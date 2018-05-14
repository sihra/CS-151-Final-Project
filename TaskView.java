import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TaskView extends JFrame implements ViewInterface {
	TaskModel data;
	TaskController controller;
	ProjectController parent;
	ProjectView parentView;
	private JTextArea description;
	private JTextField title;
	private JTextField date;
	private Object[] transferStatus;
	private JButton editB;
	private JButton okB;
	private JButton closeB;
	private JComboBox statusChange;
	private String[] taskSections;
	private boolean isEditing;

	public TaskView(TaskModel _data, boolean editMode, ProjectController _parent) {
		super("Task View");
		data = _data;
		parent = _parent;
		// Count of all sections
		taskSections = new String[parent.getCount()];
		// Adds a TaskView object to the TaskModel class of the task itself
		data.attach(this);
		// Creates and attaches a new controller to the task
		controller = new TaskController(data, parent);
		isEditing = editMode;
		// Creates border of the task itself in the column
		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		// Have seperate text fields to make it easier to change in view class and
		// translate to model/controller classes
		title = new JTextField();
		description = new JTextArea();
		// description.setPreferredSize(new Dimension(150, 300));
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		date = new JTextField();

		editB = new JButton("edit");
		okB = new JButton("Ok");
		TaskView ext = this;
		okB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				updateModel();
				ext.dispose();
			}
		});
		

		closeB = new JButton("Close");
		closeB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ext.dispose();
				
			}
		});
		// Creating JPanel for buttons
		JPanel options = new JPanel();

		// Creating JPanel for text fields
		JPanel infoPane = new JPanel();

		// Adds an edit button to a JPanel
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
		//options.add(editB);
		// If there's any action, then do this
		editB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {



				isEditing = !isEditing;
				update();
				if (!isEditing) {
					updateModel();
				}
			}
		});

		// Closing the JPanel application
		closeB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				options.setVisible(false);
				infoPane.setVisible(false);
				
			}
		});

		int count = 0;
		for (ProjectSection section : parent.getSections()) {
			taskSections[count] = section.getTitle();
			count++;
		}
		statusChange = new JComboBox(taskSections);
		statusChange.setSelectedIndex(parent.getCount() - 1);
		statusChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Gets the box the user clicked
				JComboBox cb = (JComboBox) e.getSource();
				String name = (String) cb.getSelectedItem();
				transferStatus = new Object[3];
				transferStatus[0] = data;
				transferStatus[1] = parent.getStatus(data); // Old project section
				transferStatus[2] = name; // new project section
				parent.transferTask(data, name); // Automatically updates views for Project through ProjectModel's
													// notifyViews() method

			}
		});
		JButton deleteB = new JButton("Delete");
		deleteB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.getModel().removeTask(data, data.getCategory());
				dispose();
				
			}
		});
		Container mp = getContentPane();
		JLabel titleL = new JLabel("Title: ");
		JLabel textL = new JLabel("Description: ");
		JLabel dateL = new JLabel("Date: ");
		JLabel sectionL = new JLabel("Section: ");
		
		layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, mp);
		layout.putConstraint(SpringLayout.WEST, title, 75, SpringLayout.WEST, mp);
		
		layout.putConstraint(SpringLayout.NORTH, description, 20, SpringLayout.SOUTH, title);
		layout.putConstraint(SpringLayout.WEST, description, 0, SpringLayout.WEST, title);
		layout.putConstraint(SpringLayout.EAST, description, -20, SpringLayout.EAST, mp);
		layout.putConstraint(SpringLayout.SOUTH, description, -100, SpringLayout.SOUTH, mp);
		
		layout.putConstraint(SpringLayout.NORTH, date, 15, SpringLayout.SOUTH, description);
		layout.putConstraint(SpringLayout.WEST, date, 0, SpringLayout.WEST, description);
		
		layout.putConstraint(SpringLayout.NORTH, closeB, 5, SpringLayout.SOUTH, date);
		layout.putConstraint(SpringLayout.EAST, closeB, 0, SpringLayout.EAST, description);
		
		layout.putConstraint(SpringLayout.NORTH, okB, 0, SpringLayout.NORTH, closeB);
		layout.putConstraint(SpringLayout.EAST, okB, -15, SpringLayout.WEST, closeB);
		
		layout.putConstraint(SpringLayout.NORTH, editB, 0, SpringLayout.NORTH, okB);
		layout.putConstraint(SpringLayout.EAST, editB, -15, SpringLayout.WEST, okB);
		
		layout.putConstraint(SpringLayout.NORTH, deleteB, 0, SpringLayout.NORTH, editB);
		layout.putConstraint(SpringLayout.EAST, deleteB, -15, SpringLayout.WEST, editB);
		
		layout.putConstraint(SpringLayout.NORTH, statusChange, 0, SpringLayout.NORTH, deleteB);
		layout.putConstraint(SpringLayout.EAST, statusChange, -15, SpringLayout.WEST, deleteB);
		
		
		mp.add(okB);
		mp.add(closeB);
		mp.add(statusChange);
		mp.add(title);
		mp.add(description);
		mp.add(date);
		mp.add(editB);
		mp.add(deleteB);
		//add(infoPane, BorderLayout.NORTH);
		//add(options, BorderLayout.SOUTH);
		update();
		pack();
		setMinimumSize(new Dimension(500, 300));

	}

	public void updateStatus() {
		ProjectSection section = parent.getStatus(data);
		int count = 0;
		for (String sect : taskSections) {
			if (section.getTitle().equals(sect)) {
				statusChange.setSelectedIndex(count);
				break;
			}
			count++;
		}
	}

	public void updateModel() {
		controller.setTitle(title.getText());
		controller.setDescription(description.getText());
		controller.setDueDate(date.getText());

	}

	@Override
	public void update() {
		EventQueue.invokeLater(new Runnable() {

			// This inner method will change the viewable text on screen
			@Override
			public void run() {
				// System.out.println(data.getName());
				title.setText(data.getName());
				description.setText(data.getText());
				SimpleDateFormat f = new SimpleDateFormat("MM-dd-yyyy");
				title.setEditable(isEditing);
				description.setEditable(isEditing);
				date.setEditable(isEditing);
				statusChange.setEnabled(isEditing);
				// method just created
				updateStatus();
				repaint();
				date.setText(f.format(data.getEnd().getTime()));
				parent.getModel().updateTask(data.getCategory());
				revalidate();
			}
		});
	}

	public class DregerButton extends JButton implements MouseListener {
		Font defaultFont = new Font("Gill Sans MT", Font.BOLD, 14);
		Color textColor = Color.decode("#ffffff");
		Color backgroundColor = Color.decode("#000000");
		Color hoverColor = Color.decode("#00aced");

		public DregerButton(String s) {
			s = s.toUpperCase();
			this.setFocusPainted(false);
			this.setText(s);
			this.setBorder(null);
			this.setForeground(textColor);
			this.setBackground(backgroundColor);
			this.setFont(defaultFont);
			this.setOpaque(true);
			addMouseListener(this);
		}

		public DregerButton(String s, Color backgroundColor, Color hoverColor) {
			s = s.toUpperCase();
			this.setFocusPainted(false);
			this.setText(s);
			this.setBorder(null);
			this.setHoverColor(hoverColor);
			this.setBackground(backgroundColor);
			this.setFont(defaultFont);
			this.setOpaque(true);
			addMouseListener(this);
		}

		public void setBackgroundColor(Color color) {
			backgroundColor = color;
		}

		public void setHoverColor(Color color) {
			hoverColor = color;
		}

		@Override
		public void mouseClicked(MouseEvent me) {
		}

		@Override
		public void mouseReleased(MouseEvent me) {
		}

		@Override
		public void mousePressed(MouseEvent me) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == this) {
				this.setBackground(this.hoverColor);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == this) {
				this.setBackground(this.backgroundColor);
			}
		}
	}

}
