import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProjectView extends JPanel {
/*
 * So here's the deal, this thing is gunna need a scroll panel FOR SURE, it will contain a sub panel with the various columns
 * of tasks that the user has, as well as buttons to manipulate these columns. So what i'm thinking is, there is a private class
 * in here that is used to handle just the sections themselves.
 */
	class ColumnView extends JPanel {
		private ProjectSection model;
		private JScrollPane scroller;
		private JPanel taskView;
		public ColumnView(ProjectSection _model)
		{
			model = _model;
			taskView = new JPanel();
			//taskView.setLayout(new BoxLayout(taskView, BoxLayout.Y_AXIS));
			
			for(TaskModel c : model)
			{
				taskView.add(new TaskLabel(c));
			}
			taskView.revalidate();
			scroller = new JScrollPane(taskView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			setLayout(new BorderLayout());
			add(scroller,BorderLayout.WEST);
			
		}
		public void update()
		{
			for (int i = 0; i < taskView.getComponentCount(); i++)
			{
				TaskLabel d = ((TaskLabel)(taskView.getComponent(i)));
				d.update();
			}
		}
	}
	private ProjectModel data;
	private JPanel taskColumns;
	private JScrollPane taskScroller;
	public ProjectView(ProjectModel _data) {
		data = _data;
		taskColumns = new JPanel();
		taskColumns.setLayout(new BoxLayout(taskColumns, BoxLayout.X_AXIS));
		for (ProjectSection c: data)
		{
			taskColumns.add(new ColumnView(c));
		}
		taskScroller = new JScrollPane(taskColumns, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(taskScroller);
	}
	
	public void update()
	{
		for (int i = 0; i < taskColumns.getComponentCount(); i++)
		{
			ColumnView d = ((ColumnView)(taskColumns.getComponent(i)));
			d.update();
		}
	}
}
