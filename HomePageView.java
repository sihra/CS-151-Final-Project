import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePageView extends JPanel{
	private TaskBoardController controller;
	private JButton createNewB;
	private TaskBoardModel data;
	private MainScreen screen;
	
	public void setVariable(MainScreen frame) {
		screen = frame;
		controller = frame.getTBController();
		
	}
	
	
	public HomePageView() {
		this.setLayout(new FlowLayout());
		this.screen = screen;
		this.controller = controller;
		data = new TaskBoardModel();
		ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
		
		if(list.size() == 0 ){
			createNewB = new JButton("Create New Project");
			createNewB.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ProjectEditView d = new ProjectEditView(data.getController());
					d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					d.setVisible(true);
					
				}
			});
			this.add(createNewB, FlowLayout.CENTER);
			createNewB.setHorizontalAlignment(JButton.CENTER);
		}else {
			setLayout(new BorderLayout());
			JLabel title = new JLabel("Choose a Project");
			Font f = title.getFont();
			// Bolds the title of this Panel
			title.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
			add(title, BorderLayout.PAGE_START);
			title.setHorizontalAlignment(JLabel.CENTER);
			for(ProjectModel p : list) {
				JButton project = new JButton(p.getName());
				project.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						screen.setProjectView(p);
						screen.homepage(true);
					}
					
				});
				this.add(project, FlowLayout.LEFT);
			}
			
		}
		
		
	}
	
	
	public static void main(String[] args)
	{
		JFrame d = new JFrame();
		d.getContentPane().add(new HomePageView());
		d.pack();
		d.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		d.setVisible(true);
	}
}
