import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TaskBoardView extends JPanel implements ViewInterface{
	//private ProjectView projView;
	private ProjectController projController;
	private ProjectModel projData;
	private TaskBoardModel data;
	
	private JComboBox projectChange;
	private JComboBox edit;
	private JButton save;
	private JButton delete;
	private JComboBox createNew;
	private JTextField newProject;
	private JTextField newColumn;
	private JButton logout;
	private MainScreen frame;

	public TaskBoardView(ProjectModel projData,  ProjectController projController) {
		this.projData = projData;
		this.projController = projController;
		
		// Attaching view class to project
		projData.attach(this);
		// Setting SpringLayout for this JPanel
		setLayout(new SpringLayout());
		newProject = new JTextField();
		
		createButtons();
	}
	
	
	
	public void createButtons() {
		// Edit button for Projects (delete, clear all tasks), Coumns(add/delete), and Tasks (ordering)
		String[] editOptions = {"Edit...", "Project" , "Columns", "Tasks"};
		edit = new JComboBox(editOptions);
		edit.setSelectedIndex(0);
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Gets the box the user clicked
				JComboBox cb = (JComboBox)e.getSource();
		        String name = (String)cb.getSelectedItem();
				MainScreen frame = null;
				
//				if(name.equals("Project")) {
//					
//				}
//			        
//	            String n = JOptionPane.showInputDialog(frame, "What is your name?", null);
//				
//				Object[] projectOptions = { "Delete Project", "Clear all Tasks" };
//				 JPanel panel1 = new JPanel();
//				 panel1.setLayout(new FlowLayout());
//				 JLabel deleteProj = new JLabel("Delete Project");
//				 deleteProj.addActionListener(new ActionListener() {
//				 @Override
//				 public void actionPerformed(ActionEvent e) {
//				
//				 String n = JOptionPane.showConfirmDialog(null,
//				 "In deleting this Project all of your columns and tasks will be erased from
//				 the system. Are you sure you want to continue?",
//				 null);
//				 }
//				
//				 });
//				 panel1.add(deleteProj);
//				 int n = JOptionPane.showConfirmDialog(null, panel1, "Test",
//				 JOptionPane.YES_NO_OPTION);

				int dialogResult = JOptionPane.showConfirmDialog(null,
						"In deleting this Project all of your columns and tasks will be erased from the system. Are you sure you want to continue",
						"Project Deletion", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					// Saving code here
				}
	            
			}
			
		});
	}
	
	public void update() {
		// TODO idk how
	}

	
	
	
}