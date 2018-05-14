import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.sound.midi.ControllerEventListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.tools.JavaFileManager;

public class MainScreen extends JFrame implements ViewInterface, WindowListener {
	private ProjectView projView;
	private TaskBoardEditPanel editP;
	private SpringLayout layout;
	private TaskBoardController controller;

	public ProjectView getProjectView() {
		return projView;
	}

	public void setProjectView(ProjectModel p) {
		projView = new ProjectView(p);
	}

	public TaskBoardController getTBController() {
		return controller;
	}

	// private TaskBoardView tBView;
	private TaskBoardModel data;

	public void update() {
		projView.update();
		revalidate();
		repaint();
	}

	public MainScreen(TaskBoardModel _data) {
		data = _data;
		controller = new TaskBoardController(data);
		data.attach(this);
		setLayout(new BorderLayout());
		projView = new ProjectView(data.getSelectedModel());
		editP = new TaskBoardEditPanel(_data, this);
		add(editP, BorderLayout.NORTH);
		add(projView, BorderLayout.SOUTH);
		// pack();
	}

	public void updateSelectedView() {
		MainScreen ext = this;
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				remove(projView);
				layout.removeLayoutComponent(projView);
				projView = new ProjectView(data.getSelectedModel());
				layout.putConstraint(SpringLayout.NORTH, projView, 0, SpringLayout.SOUTH, editP);
				layout.putConstraint(SpringLayout.SOUTH, projView, 0, SpringLayout.SOUTH, ext.getContentPane());
				// layout.putConstraint(SpringLayout.WEST, ext.getContentPane(), 0,
				// SpringLayout.WEST, projView);
				// layout.putConstraint(SpringLayout.EAST, ext.getContentPane(), 0,
				// SpringLayout.EAST, projView);
				layout.putConstraint(SpringLayout.WEST, projView, 0, SpringLayout.WEST, ext.getContentPane());
				layout.putConstraint(SpringLayout.EAST, projView, 0, SpringLayout.EAST, ext.getContentPane());
				// layout.putConstraint(SpringLayout.WEST, ext.getContentPane(), 0,
				// SpringLayout.WEST, projView);
				// layout.putConstraint(SpringLayout.EAST, ext.getContentPane(), 0,
				// SpringLayout.EAST, projView);
				add(projView);
				revalidate();
				// pack();
			}
		});
	}

	public void setLoggedIn(boolean b) {
		MainScreen ext = this;
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {

				ext.getContentPane().removeAll();
				if (b) {
					if (data == null) {
						data = new TaskBoardModel();
					}
					setLayout(new BorderLayout());
					projView = new ProjectView();
					editP = new TaskBoardEditPanel(data, ext);
					data.attach(editP);
					layout = new SpringLayout();
					setLayout(layout);
					layout.putConstraint(SpringLayout.NORTH, editP, 0, SpringLayout.NORTH, ext.getContentPane());
					layout.putConstraint(SpringLayout.WEST, editP, 0, SpringLayout.WEST, ext.getContentPane());
					layout.putConstraint(SpringLayout.EAST, editP, 0, SpringLayout.EAST, ext.getContentPane());
					// layout.putConstraint(SpringLayout.WEST, ext.getContentPane(), 0,
					// SpringLayout.WEST, editP);
					// layout.putConstraint(SpringLayout.EAST, ext.getContentPane(), 0,
					// SpringLayout.EAST, editP);
					layout.preferredLayoutSize(ext);
					// layout.putConstraint(SpringLayout.SOUTH, editP, 50, SpringLayout.NORTH,
					// ext.getContentPane());

					layout.putConstraint(SpringLayout.NORTH, projView, 50, SpringLayout.NORTH, ext.getContentPane());
					layout.putConstraint(SpringLayout.SOUTH, projView, 0, SpringLayout.SOUTH, ext.getContentPane());
					// layout.putConstraint(SpringLayout.WEST, projView, 0, SpringLayout.WEST,
					// ext.getContentPane());
					// layout.putConstraint(SpringLayout.EAST, projView, 0, SpringLayout.EAST,
					// ext.getContentPane());
					// layout.putConstraint(SpringLayout.WEST, ext.getContentPane(), 0,
					// SpringLayout.WEST, projView);
					// layout.putConstraint(SpringLayout.EAST, ext.getContentPane(), 0,
					// SpringLayout.EAST, projView);

					getContentPane().add(editP);
					getContentPane().add(projView);
					pack();
					setSize(500, 500);
					setMinimumSize(new Dimension(600, 700));
					setExtendedState(JFrame.MAXIMIZED_BOTH);
				} else {
					LoginView lc = new LoginView();
					lc.setParent(ext);
					setLayout(new BorderLayout());
					getContentPane().add(lc, BorderLayout.CENTER);
					revalidate();
					repaint();
					// pack();
				}
			}
		});
	}

	public void homepage(boolean b) {
		MainScreen ext = this;
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if(!b) {
					HomePageView hp = new HomePageView();
					hp.setVariable(ext);
					setLayout(new BorderLayout());
					getContentPane().add(hp, BorderLayout.CENTER);
					revalidate();
					repaint();
				}
			}

		});
	}

	// public void homepage() {
	// addWindowListener(this);
	// setSize(500,500);
	//
	// }
	public MainScreen() {
		addWindowListener(this);
		setSize(500, 500);
		setLoggedIn(false);
		homepage(false);
	}

	public void setData(TaskBoardModel _data) {
		data = _data;
		// System.out.println(data==null);
		data.attach(this);
		editP.setData(data);
		data.notifyViews();
		updateSelectedView();
	}

	public static void main(String[] args) {
		// ProjectModel c = new ProjectModel("Test");
		// c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In
		// this case, if parent was red, then we didn’t need to recur for prent, we can
		// simply make it black (red + double black = single black)", "Done"), "Done");
		// c.addTask(new TaskModel("Do Entire Project2", new GregorianCalendar(), "In
		// this case, if parent was red, then we didn’t need to recur for prent, we can
		// simply make it black (red + double black = single black)", "Done"), "Done");
		// TaskBoardModel d = new TaskBoardModel();
		// d.addProject(c);
		MainScreen view = new MainScreen();
		view.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		view.setVisible(true);
		// c.clearView();
		// c.addSection(new ProjectSection("Test"));

		// view.update();
		// c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In
		// this case, if parent was red, then we didn’t need to recur for prent, we can
		// simply make it black (red + double black = single black)", "Done"), "Done");

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		if (data != null && data.getController().needSave()) {
			int result = JOptionPane.showConfirmDialog(this,
					"You are about to close the application, any unsaved work will be lost.\n Are you sure you would like to continue?",
					null, JOptionPane.OK_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.CANCEL_OPTION:
				break;
			default:
				dispose();
				System.exit(0);
				break;
			}
		} else {
			dispose();
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}

/**
 * This is a temp, i'm just using it to test the edit features
 * 
 * @author Zackary Finer
 *
 */
class TaskBoardEditPanel extends JPanel implements ViewInterface {
	private TaskBoardModel data;
	private TaskBoardController controller;

	public void setData(TaskBoardModel _data) {
		data = _data;
		data.attach(this);
	}

	private MainScreen parentScr;
	private JButton createNewB;
	private JButton editB;
	private JButton saveB;
	private JButton loadB;
	private JButton delete;
	private JComboBox<ProjectModel> sectionCB;

	public TaskBoardEditPanel(TaskBoardModel _data, MainScreen _parent) {
		data = _data;
		controller = new TaskBoardController(data);
		parentScr = _parent;
		data.attach(this);
		for (ProjectModel c : data) {
			c.attach(this);
		}
		editB = new JButton("edit");
		editB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (data.getSelectedModel() != null) {
					ProjectEditView d = new ProjectEditView(data.getSelectedModel());
					d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					d.setVisible(true);
				}
			}
		});
		createNewB = new JButton("Create New Project");
		createNewB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProjectEditView d = new ProjectEditView(data.getController());
				d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				d.setVisible(true);

			}
		});
		delete = new JButton("Delete Project");
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"In deleting this Project all of your columns and tasks will be erased from the system. Are you sure you want to continue",
						"Project Deletion", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					controller.deleteProject(parentScr.getProjectView().getProjectModel());
					parentScr.homepage(false);
				}

			}

		});
		saveB = new JButton("save");
		saveB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int val = fc.showSaveDialog(parentScr);

				if (val == JFileChooser.APPROVE_OPTION) {
					File selected = fc.getSelectedFile();
					data.getController().saveProjectsToFile(selected, data);
				}
			}
		});
		loadB = new JButton("load");
		loadB.addActionListener(new ActionListener() {

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
		sectionCB = new JComboBox<>();
		sectionCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						data.setSelectedIndex(sectionCB.getSelectedIndex());
						if (sectionCB.getSelectedIndex() >= 0) {
							// parentScr.getProjectView().updateSelected(data.getSelectedModel());
							parentScr.updateSelectedView();
						}
					}
				});

			}
		});
		for (ProjectModel c : data) {
			sectionCB.addItem(c);
		}
		if (sectionCB.getItemCount() > 0) {
			sectionCB.setSelectedIndex(0);
		}
		setLayout(new FlowLayout());
		add(sectionCB);
		add(saveB);
		add(loadB);
		add(editB);
		add(delete);
		add(createNewB);

	}

	@Override
	public void update() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// System.out.println(data.getProjectList().size());
				// System.out.println("RUN!!!");
				sectionCB.removeAllItems();
				System.out.println();
				for (ProjectModel c : data) {
					sectionCB.addItem(c);
				}
				revalidate();

			}
		});

	}
}