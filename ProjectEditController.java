
public class ProjectEditController {
	private ProjectModel c;
	private ProjectEditView v;
	public ProjectEditController(ProjectModel c, ProjectEditView v) {
		this.c = c;
		this.v = v;
	}
	public void removeSection(ProjectSection toRemove)
	{
		//make sure user wants to delete
		//and delete the task
	}
	public void addSection(String name)
	{
		//add new section
	}

}
