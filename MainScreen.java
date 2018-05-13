import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.Context;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class MainScreen extends JFrame implements ViewInterface {
	private ProjectView projView;

	public ProjectView getProjectView() {
		return projView;
	}

	public void updateSelectedView() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				remove(projView);
				projView = new ProjectView(data.getSelectedModel());
				add(projView, BorderLayout.SOUTH);
				revalidate();
				pack();
			}
		});

	}

	private TaskBoardView tBView;
	private TaskBoardModel data;
	private ProjectController pc;

	public void update() {
		projView.update();
		revalidate();
		repaint();
	}

	public MainScreen(TaskBoardModel _data, ProjectController pc) {
		data = _data;
		this.pc = pc;
		data.attach(this);
		setLayout(new BorderLayout());
		projView = new ProjectView(data.getSelectedModel());
		TaskBoardEditPanel edit = new TaskBoardEditPanel(_data, this, pc);
		add(edit, BorderLayout.NORTH);
		add(projView, BorderLayout.SOUTH);
		pack();
	}

	public static void main(String[] args) {
		ProjectModel c = new ProjectModel("Test");
		// c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In
		// this case, if parent was red, then we didn’t need to recur for prent, we can
		// simply make it black (red + double black = single black)", "Done"), "Done");
		// c.addTask(new TaskModel("Do Entire Project2", new GregorianCalendar(), "In
		// this case, if parent was red, then we didn’t need to recur for prent, we can
		// simply make it black (red + double black = single black)", "Done"), "Done");
		TaskBoardModel d = new TaskBoardModel();
		ProjectController pc = new ProjectController(c);
		d.addProject(c);
		MainScreen view = new MainScreen(d, pc);
		view.setDefaultCloseOperation(EXIT_ON_CLOSE);
		view.setVisible(true);
		// c.clearView();
		// c.addSection(new ProjectSection("Test"));

		// view.update();
		// c.addTask(new TaskModel("Do Entire Project", new GregorianCalendar(), "In
		// this case, if parent was red, then we didn’t need to recur for prent, we can
		// simply make it black (red + double black = single black)", "Done"), "Done");

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
	private MainScreen parentScr;
	private JButton createNewB;
	private JButton editB;
	private JButton saveB;
	private JButton loadB;
	private JComboBox<String> sectionCB;
	private ProjectController pc;

	public TaskBoardEditPanel(TaskBoardModel _data, MainScreen _parent, ProjectController pc) {
		data = _data;
		this.pc = pc;
		parentScr = _parent;
		data.attach(this);
		for (ProjectModel c : data) {
			c.attach(this);
		}
		editB = new JButton("edit");
		editB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProjectEditView d = new ProjectEditView(data.getSelectedModel());
				d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				d.setVisible(true);
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

		saveB = new JButton("save");
		saveB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(parentScr);
				// File selected = fc.getSelectedFile();
				// System.out.println(selected.getAbsolutePath());
			}
		});
		loadB = new JButton("load");
		sectionCB = new JComboBox<>();
		sectionCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						data.setSelected(sectionCB.getSelectedIndex());
						if (sectionCB.getSelectedIndex() >= 0) {
							// parentScr.getProjectView().updateSelected(data.getSelectedModel());
							parentScr.updateSelectedView();
						}
					}
				});

			}
		});
		for (ProjectModel c : data) {
			sectionCB.addItem(c.getName());
		}
		sectionCB.setSelectedIndex(0);
		setLayout(new FlowLayout());
		add(sectionCB);
		add(saveB);
		add(loadB);
		add(editB);
		add(createNewB);

	}

	@Override
	public void update() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// System.out.println(data.getProjectList().size());
				System.out.println("RUN!!!");
				sectionCB.removeAllItems();
				System.out.println();
				for (ProjectModel c : data) {
					sectionCB.addItem(c.getName());
				}
				revalidate();

			}
		});

	}
}