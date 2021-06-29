import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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

	private JButton edit;
	private JComboBox projectChange;
	private JButton save;
	private JButton delete;
	private JButton createNew;
	private JButton logout;
	private JButton load;

	public TaskBoardView(MainScreen parentScr, TaskBoardModel data) {
		this.data = data;
		data.attach(this);
		this.parentScr = parentScr;
		this.projData = projData;
		this.projController = projController;
		for (ProjectModel c : data) {
			c.attach(this);
		}

		// Setting SpringLayout for this JPanel
		setLayout(new SpringLayout());
		createProjectOptions();
		createEditButton();
		createSave();
		// createDelete();
		createLoad();
		createNewProject();
		// logout();

		add(projectChange);
		add(edit);
		add(save);
		add(createNew);
	}

	private void createEditButton() {
		edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.getSelectedModel() != null) {
					ProjectEditView d = new ProjectEditView(data.getSelectedModel());
					d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					d.setVisible(true);
				}
			}
		});
		this.add(edit);
	}

	// private void editProject() {
	// JPanel panel = new JPanel();
	// panel.setLayout(new BorderLayout());
	// editProjName.setText(projController.getModel().getName());
	//
	// // Adding a menu itself for the edit button : Editing a Project
	// edit.add(new JMenuItem(new AbstractAction("Edit Project") {
	// public void actionPerformed(ActionEvent e) {
	//
	// // Creating Text field for editing project's title
	// JLabel label = new JLabel("Edit Project's Name: ");
	// panel.add(label, BorderLayout.NORTH);
	// label.setHorizontalAlignment(JLabel.CENTER);
	// panel.add(editProjName, BorderLayout.CENTER);
	//
	// // Creating a delete project button
	//
	// panel.add(deleteProj, BorderLayout.SOUTH);
	// JOptionPane.showMessageDialog(null, panel, "Editing a Project",
	// JOptionPane.INFORMATION_MESSAGE);
	// }
	// }));
	// }
	//
	// private void editColumn() {
	// // Column Selector drop down box for edit column button
	// String[] sections = new String[projController.getCount()];
	// int count = 1;
	// sections[1] = "Choose...";
	// for (ProjectSection sect : projController.getSections()) {
	// sections[count] = sect.getTitle();
	// count++;
	// }
	// JComboBox selectCol = new JComboBox(sections);
	// // the selected index is the Choose button
	// selectCol.setSelectedIndex(0);
	//
	// // Adding a menu itself for the edit button : Editing Columns
	// edit.add(new JMenuItem(new AbstractAction("Edit Columns") {
	// public void actionPerformed(ActionEvent e) {
	// JPanel panel1 = new JPanel();
	// panel1.setLayout(new BorderLayout());
	//
	// // Panel adds drop down box of columns as only thing first and then displays
	// // more as the column is selected
	// panel1.add(selectCol, BorderLayout.NORTH);
	// // Add Column option is added
	// JLabel addCol = new JLabel("Add a New Column: ");
	// panel1.add(addCol, BorderLayout.CENTER);
	// panel1.add(addColumnName, BorderLayout.CENTER);
	// addCol.setHorizontalAlignment(JLabel.CENTER);
	//
	// JOptionPane.showMessageDialog(null, panel1, "Editing a Column",
	// JOptionPane.INFORMATION_MESSAGE);
	//
	// // Find the column selected and displays a pop up based on that
	// selectCol.addActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// JComboBox cb = (JComboBox) e.getSource();
	//
	// // Sets current column as selected index
	// String name = (String) cb.getSelectedItem();
	// int count = 0;
	// // Finds the ProjectSection object pressed
	// for (ProjectSection section : projController.getSections()) {
	// if (section.getTitle().equals(name)) {
	// chooseColumn = section;
	// break;
	// }
	// count++;
	// }
	//
	// JLabel label = new JLabel("Edit Column's Name: ");
	// panel1.add(label, BorderLayout.CENTER);
	// label.setHorizontalAlignment(JLabel.LEFT);
	// editColumnName = new JTextField(chooseColumn.getTitle());
	// panel1.add(editColumnName, BorderLayout.CENTER);
	// editColumnName.setHorizontalAlignment(JLabel.RIGHT);
	//
	// // Creating a delete project button
	// JButton deleteColumn = new JButton("Delete Column");
	// deleteColumn.addActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	//
	// int dialogResult = JOptionPane.showConfirmDialog(null,
	// "In deleting this Column all of your tasks within this column will be erased
	// from the system. Are you sure you want to continue",
	// "Column Deletion", JOptionPane.YES_NO_OPTION);
	// if (dialogResult == JOptionPane.YES_OPTION) {
	// // TODO: Delete Column method
	// // delete choose column from project
	// }
	// }
	// });
	// }
	// });
	// }
	// }));
	// }

	private void createProjectOptions() {
		projectChange = new JComboBox<>();
		projectChange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						data.setSelectedIndex(projectChange.getSelectedIndex());
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
		if (projectChange.getItemCount() > 0) {
			projectChange.setSelectedIndex(0);
		}
		this.add(new JLabel("Select Project "));
		this.add(projectChange);
	}

	private void createSave() {
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int val = fc.showSaveDialog(parentScr);

				if (val == JFileChooser.APPROVE_OPTION) {
					File selected = fc.getSelectedFile();
					try {
						TaskFileManager.saveToFile(data, selected);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	private void createNewProject() {
		createNew = new JButton("Create New Project");
		createNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProjectEditView d = new ProjectEditView(data.getController());
				d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				d.setVisible(true);

			}
		});
		this.add(createNew);
	}

	//
	// private void createNew() {
	// // Menu item for creating new project
	// createNew.add(new JMenuItem(new AbstractAction("New Project") {
	// public void actionPerformed(ActionEvent e) {
	// ProjectEditView d = new ProjectEditView(data.getController());
	// d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	// d.setVisible(true);
	// }
	// }));
	//
	// // Menu item for creating new column
	// createNew.add(new JMenuItem(new AbstractAction("New Column") {
	// public void actionPerformed(ActionEvent e) {
	// JPanel addCol = new JPanel();
	// addCol.setLayout(new FlowLayout());
	// JTextField columnName = new JTextField(40);
	// JButton addColumnB = new JButton("Add Column");
	//
	// addColumnB.addActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// projController.addColumn(columnName.getText());
	// }
	// });
	// addCol.add(addColumnB);
	// addCol.add(columnName);
	// JOptionPane.showOptionDialog(null, addCol, "Add Column",
	// JOptionPane.DEFAULT_OPTION,
	// JOptionPane.INFORMATION_MESSAGE, null, new Object[] {}, null);
	//
	// }
	// }));
	//
	// // Menu item for creating a new task
	// // Menu item for creating new column
	// createNew.add(new JMenuItem(new AbstractAction("New Task") {
	// public void actionPerformed(ActionEvent e) {
	// String name = "Task " + projController.taskCount();
	// String text = "Description";
	// String section = "TODO";
	// TaskModel newTask = new TaskModel(name, new GregorianCalendar(), text,
	// section);
	// TaskView tView = new TaskView(newTask, true, projController);
	// }
	// }));
	// }
	//
	public void createDelete() {
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
	}

	public void createLoad() {
		load = new JButton("load");
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int val = fc.showOpenDialog(parentScr);

				if (val == JFileChooser.APPROVE_OPTION) {
					File selected = fc.getSelectedFile();
					try {
						TaskBoardModel data = TaskFileManager.readFromFile(selected);
						parentScr.setData(data);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		this.add(load);
	}

	public void createLogout() {
		logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parentScr.setLoggedIn(false);

			}

		});
		this.add(logout);

	}

	public void setData(TaskBoardModel _data) {
		data = _data;
		data.attach(this);
	}

	@Override
	public void update() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// System.out.println(data.getProjectList().size());
				// System.out.println("RUN!!!");
				projectChange.removeAllItems();
				System.out.println();
				for (ProjectModel c : data) {
					projectChange.addItem(c.getName());
				}
				revalidate();

			}
		});

	}

}