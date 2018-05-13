import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TaskBoardView extends JPanel implements ViewInterface {
	// private ProjectView projView;
	private ProjectController projController;
	private ProjectModel projData;
	private TaskBoardModel data;

	private MainScreen parentScr;

	// All must be updated for edit button
	private JPopupMenu edit;
	private JTextField editProjName;
	private JTextField editColumnName;
	private JTextField addColumnName;
	private ProjectSection chooseColumn;

	// Used for choosing a project
	private JComboBox projectChange;

	private JButton save;
	private JButton delete;

	private JPopupMenu createNew;
	private JTextField newProject;
	private JTextField newColumn;

	private JButton logout;
	private MainScreen frame;

	public TaskBoardView(ProjectModel projData, ProjectController projController, MainScreen parentScr) {
		this.parentScr = parentScr;
		this.projData = projData;
		this.projController = projController;

		// Attaching view class to project
		projData.attach(this);
		// Setting SpringLayout for this JPanel
		setLayout(new SpringLayout());
		newProject = new JTextField();
		createProjectOptions();
		createEditButton();
		createSave();
		//createDelete();
		// createLoad()
		createNew();
		// logout();
		
		add(projectChange);
		add(edit);
		add(save);
		add(createNew);
	}

	private void createEditButton() {
		// Edit button for Projects (delete, clear all tasks), Columns(add/delete), and
		// Tasks (ordering)
		// use this in case it doesn't work
		// https://stackoverflow.com/questions/12543206/java-menu-item-enabling-within-event-listener
		editProject();
		editColumn();
		this.add(edit);
	}

	private void editProject() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		editProjName.setText(projController.getModel().getName());

		// Adding a menu itself for the edit button : Editing a Project
		edit.add(new JMenuItem(new AbstractAction("Edit Project") {
			public void actionPerformed(ActionEvent e) {

				// Creating Text field for editing project's title
				JLabel label = new JLabel("Edit Project's Name: ");
				panel.add(label, BorderLayout.NORTH);
				label.setHorizontalAlignment(JLabel.CENTER);
				panel.add(editProjName, BorderLayout.CENTER);

				// Creating a delete project button
				JButton deleteProj = new JButton("Delete Project");
				deleteProj.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						int dialogResult = JOptionPane.showConfirmDialog(null,
								"In deleting this Project all of your columns and tasks will be erased from the system. Are you sure you want to continue",
								"Project Deletion", JOptionPane.YES_NO_OPTION);
						if (dialogResult == JOptionPane.YES_OPTION) {
							// TODO: Delete Project method
						}
					}
				});
				panel.add(deleteProj, BorderLayout.SOUTH);
				JOptionPane.showMessageDialog(null, panel, "Editing a Project", JOptionPane.INFORMATION_MESSAGE);
			}
		}));
	}

	private void editColumn() {
		// Column Selector drop down box for edit column button
		String[] sections = new String[projController.getCount()];
		int count = 1;
		sections[1] = "Choose...";
		for (ProjectSection sect : projController.getSections()) {
			sections[count] = sect.getTitle();
			count++;
		}
		JComboBox selectCol = new JComboBox(sections);
		// the selected index is the Choose button
		selectCol.setSelectedIndex(0);

		// Adding a menu itself for the edit button : Editing Columns
		edit.add(new JMenuItem(new AbstractAction("Edit Columns") {
			public void actionPerformed(ActionEvent e) {
				JPanel panel1 = new JPanel();
				panel1.setLayout(new BorderLayout());

				// Panel adds drop down box of columns as only thing first and then displays
				// more as the column is selected
				panel1.add(selectCol, BorderLayout.NORTH);
				// Add Column option is added
				JLabel addCol = new JLabel("Add a New Column: ");
				panel1.add(addCol, BorderLayout.CENTER);
				panel1.add(addColumnName, BorderLayout.CENTER);
				addCol.setHorizontalAlignment(JLabel.CENTER);

				JOptionPane.showMessageDialog(null, panel1, "Editing a Column", JOptionPane.INFORMATION_MESSAGE);

				// Find the column selected and displays a pop up based on that
				selectCol.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox cb = (JComboBox) e.getSource();

						// Sets current column as selected index
						String name = (String) cb.getSelectedItem();
						int count = 0;
						// Finds the ProjectSection object pressed
						for (ProjectSection section : projController.getSections()) {
							if (section.getTitle().equals(name)) {
								chooseColumn = section;
								break;
							}
							count++;
						}

						JLabel label = new JLabel("Edit Column's Name: ");
						panel1.add(label, BorderLayout.CENTER);
						label.setHorizontalAlignment(JLabel.LEFT);
						editColumnName = new JTextField(chooseColumn.getTitle());
						panel1.add(editColumnName, BorderLayout.CENTER);
						editColumnName.setHorizontalAlignment(JLabel.RIGHT);

						// Creating a delete project button
						JButton deleteColumn = new JButton("Delete Column");
						deleteColumn.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {

								int dialogResult = JOptionPane.showConfirmDialog(null,
										"In deleting this Column all of your tasks within this column will be erased from the system. Are you sure you want to continue",
										"Column Deletion", JOptionPane.YES_NO_OPTION);
								if (dialogResult == JOptionPane.YES_OPTION) {
									// TODO: Delete Column method
									// delete choose column from project
								}
							}
						});
					}
				});
			}
		}));
	}

	private void createProjectOptions() {
		projectChange = new JComboBox<>();
		projectChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						data.setSelected(projectChange.getSelectedIndex());
						if (projectChange.getSelectedIndex() >= 0) {
							// parentScr.getProjectView().updateSelected(data.getSelectedModel());
							parentScr.updateSelectedView();
						}
					}
				});
			}
		});
		for (ProjectModel c : data) {
			projectChange.addItem(c.getName());
		}
		projectChange.setSelectedIndex(0);
		this.add(new JLabel("Select Project "));
		this.add(projectChange);
	}

	private void createSave() {
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(parentScr);
				// File selected = fc.getSelectedFile();
				// System.out.println(selected.getAbsolutePath());
			}
		});
	}

	private void createNew() {
		// Menu item for creating new project
		createNew.add(new JMenuItem(new AbstractAction("New Project") {
			public void actionPerformed(ActionEvent e) {
				ProjectEditView d = new ProjectEditView(data.getController());
				d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				d.setVisible(true);
			}
		}));
		
		// Menu item for creating new column
		createNew.add(new JMenuItem(new AbstractAction("New Column") {
			public void actionPerformed(ActionEvent e) {
				JPanel addCol = new JPanel();
				addCol.setLayout(new FlowLayout());
				JTextField columnName = new JTextField(40);
				JButton addColumnB = new JButton("Add Column");

				addColumnB.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						projController.addColumn(columnName.getText());
					}
				});
				addCol.add(addColumnB);
				addCol.add(columnName);
				JOptionPane.showOptionDialog(null, addCol, "Add Column", JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, new Object[] {}, null);

			}
		}));
		
		// Menu item for creating a new task
		// Menu item for creating new column
		createNew.add(new JMenuItem(new AbstractAction("New Task") {
			public void actionPerformed(ActionEvent e) {
				String name = "Task " + projController.taskCount();
				String text = "Description";
				String section = "TODO";
				TaskModel newTask = new TaskModel(name, new GregorianCalendar(), text, section);
				TaskView tView = new TaskView(newTask, true, projController);
			}
		}));
	}


	public void update() {

	}

}