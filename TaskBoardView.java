import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class TaskBoardView extends JPanel implements ViewInterface{
	//private ProjectView projView;
	private ProjectController projController;
	private ProjectModel projData;
	private TaskBoardModel data;
	
	private JComboBox projectChange;
	private JButton edit;
	private JButton save;
	private JButton delete;
	private JComboBox createNew;
	private JTextField newProject;
	private JTextField newColumn;
	private JButton logout;

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
		// Edit button for add or delete columns
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
			
		});
	}
	
	public void update() {
		// TODO idk how
	}

	
	
	
}