import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class ProjectEditView extends JFrame implements ViewInterface{
	private ProjectModel data;
	private JTextField titleField;
	private JList<ProjectSection> sectionList;
	private DefaultListModel<ProjectSection> sectionListModel;
	private JScrollPane sectionListScroller;
	public ProjectEditView(ProjectModel data) {
		this.data = data;
		titleField = new JTextField(20);
		sectionListModel = new DefaultListModel<>();
		sectionList = new JList<>(sectionListModel);
		sectionListScroller = new JScrollPane(sectionList);
		JPanel contentPane = new JPanel();
		contentPane.add(titleField);
		contentPane.add(sectionListScroller);
	}
	public ProjectEditView(TaskBoardController toAdd)
	{
		data = new ProjectModel();
		toAdd.addBoardProject(data);
		titleField = new JTextField(20);
		sectionListModel = new DefaultListModel<>();
		sectionList = new JList<>(sectionListModel);
		sectionListScroller = new JScrollPane(sectionList);
		JPanel contentPane = new JPanel();
		contentPane.add(titleField);
		contentPane.add(sectionListScroller);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//update code goes here
			}
		});
		
	}
}
