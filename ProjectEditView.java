import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


public class ProjectEditView extends JFrame implements ViewInterface{
	//TODO: make this not ugly, able to edit columns, and able to update.
	private ProjectModel data;
	private JTextField titleField;
	private JList<ProjectSection> sectionList;
	private JButton okB;
	private JButton addColumnB;
	private JButton deleteColumnB;
	private JTextField columnName;
	private DefaultListModel<ProjectSection> sectionListModel;
	private JScrollPane sectionListScroller;
	public void initSectionFields()
	{
		sectionListModel = new DefaultListModel<>();
		sectionList = new JList<>(sectionListModel);
		sectionList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		sectionListScroller = new JScrollPane(sectionList);
	}
	public void initButtons()
	{
		deleteColumnB = new JButton("Delete Column");
		deleteColumnB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sectionList.getSelectedValue()!=null)
				{
					data.removeSection(sectionList.getSelectedValue());
				}
			}
		});
		columnName = new JTextField(40);
		addColumnB = new JButton("Add Column");
		addColumnB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int r = JOptionPane.showConfirmDialog(null, columnName, "Add Column", JOptionPane.OK_CANCEL_OPTION);
				if (r==JOptionPane.OK_OPTION)
				{
					String rText = columnName.getText();
					if (rText != null && rText.length() > 0)
					{
						data.addSection(new ProjectSection(rText));
					}
				}
			}
		});

	}
	public void initLayout()
	{
		JPanel contentPane = new JPanel();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		JLabel titleL = new JLabel("Project Title: ");
		
		layout.putConstraint(SpringLayout.NORTH, titleField, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, titleField, 90, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, titleL, -5, SpringLayout.WEST, titleField);
		layout.putConstraint(SpringLayout.NORTH, titleL, 0, SpringLayout.NORTH, titleField);
		
		layout.putConstraint(SpringLayout.NORTH, sectionListScroller, 30, SpringLayout.SOUTH, titleField);
		layout.putConstraint(SpringLayout.WEST, sectionListScroller, 0, SpringLayout.WEST, titleField);
		
		layout.putConstraint(SpringLayout.WEST, addColumnB, 5, SpringLayout.EAST, sectionListScroller);
		layout.putConstraint(SpringLayout.NORTH, addColumnB, 0, SpringLayout.NORTH, sectionListScroller);
		
		layout.putConstraint(SpringLayout.NORTH, deleteColumnB, 5, SpringLayout.SOUTH, addColumnB);
		layout.putConstraint(SpringLayout.WEST, deleteColumnB, 0, SpringLayout.WEST, addColumnB);
		
		layout.putConstraint(SpringLayout.SOUTH, okB, -5, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, okB, -5, SpringLayout.EAST, contentPane);
		contentPane.add(titleL);
		contentPane.add(titleField);
		contentPane.add(sectionListScroller);
		contentPane.add(okB);
		contentPane.add(deleteColumnB);
		contentPane.add(addColumnB);
		add(contentPane);
		setMinimumSize(new Dimension(400, 300));
		setResizable(false);
	}
	public ProjectEditView(ProjectModel data) {
		this.data = data;
		this.data.attach(this);
		titleField = new JTextField(this.data.getName(), 20);
		initSectionFields();
		for (ProjectSection c: this.data)
		{
			sectionListModel.addElement(c);
		}
		okB = new JButton("Ok");
		okB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				data.setName(titleField.getText());
				dispose();
			}
		});
		initButtons();
		initLayout();
		pack();
	}
	public ProjectEditView(TaskBoardController toAdd)
	{
		titleField = new JTextField(20);
		data = new ProjectModel("NewProject");
		data.attach(this);
		initSectionFields();
		for (ProjectSection c: data)
		{
			sectionListModel.addElement(c);
		}
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
		initButtons();
		initLayout();
		pack();
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				sectionListModel.removeAllElements();
				for (ProjectSection c: data)
				{
					sectionListModel.addElement(c);
				}
			}
		});
		
	}
}
