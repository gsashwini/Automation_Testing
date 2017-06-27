package XmlToJson;
import java.util.ArrayList;

public class Assessments {

	public String OID;
	public String CourseNumber;
	public String Version;
	public String Language;
	
	public class Questions {
		public ArrayList<String> Question;
		public ArrayList<String> CorrectAnswers;
		public ArrayList<String> IncorrectAnswers;
	}
	
	
}
