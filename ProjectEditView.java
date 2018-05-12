import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class ProjectEditView extends JFrame implements ViewInterface{
	private ProjectModel data;
	private JTextField titleField;
	private JList<ProjectSection> sectionList;
	private JButton okB;
	private DefaultListModel<ProjectSection> sectionListModel;
	private JScrollPane sectionListScroller;
	public ProjectEditView(ProjectModel data) {
		this.data = data;
		titleField = new JTextField(data.getName());
		sectionListModel = new DefaultListModel<>();
		sectionList = new JList<>(sectionListModel);
		sectionListScroller = new JScrollPane(sectionList);
		okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				data.setName(titleField.getText());
				//TODO: also update any sections
				dispose();
			}
		});
		JPanel contentPane = new JPanel();
		contentPane.add(titleField);
		contentPane.add(sectionListScroller);
		contentPane.add(okB);
		add(contentPane);
		pack();
	}
	public ProjectEditView(TaskBoardController toAdd)
	{
		titleField = new JTextField(20);
		sectionListModel = new DefaultListModel<>();
		sectionList = new JList<>(sectionListModel);
		sectionListScroller = new JScrollPane(sectionList);
		data = new ProjectModel("NewProject");
		
		okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				data.setName(titleField.getText());
				//TODO: also update any sections
				toAdd.addBoardProject(data);
				dispose();
			}
		});
		JPanel contentPane = new JPanel();
		contentPane.add(titleField);
		contentPane.add(sectionListScroller);
		contentPane.add(okB);
		add(contentPane);
		pack();
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
