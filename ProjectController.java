
public class ProjectController {
	ProjectModel model;
	public ProjectController(ProjectModel _model) {
		this.model = _model;
	}
	public void addColumn(String s)
	{
		model.addSection(new ProjectSection(s));
	}
}
