import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TaskFileManager {
	public TaskFileManager() {
	}
	public static void saveToFile(TaskBoardModel object, File output) throws FileNotFoundException
	{
		FileOutputStream fo = new FileOutputStream(output);
		BufferedOutputStream bo = new BufferedOutputStream(fo);
		XMLEncoder encoder = new XMLEncoder(bo);
		encoder.writeObject(object);
		encoder.close();
		try {
			bo.close();
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static TaskBoardModel readFromFile(File input) throws FileNotFoundException
	{
		FileInputStream fi = new FileInputStream(input);
		BufferedInputStream bi = new BufferedInputStream(fi);
		XMLDecoder decoder = new XMLDecoder(bi);
		TaskBoardModel _return = (TaskBoardModel)decoder.readObject();
		decoder.close();
		try {
			bi.close();
			fi.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return _return;
	}
}
