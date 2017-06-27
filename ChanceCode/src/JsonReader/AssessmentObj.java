package JsonReader;
import java.util.ArrayList;

public class AssessmentObj {
public String OID;
public String CourseNumber;
public String Version;
public String Language;

public static class Questions {
public static ArrayList<String> Question = new ArrayList<String>();
public static ArrayList<String> CorrectAnswers = new ArrayList<String>();
public static ArrayList<String> IncorrectAnswers = new ArrayList<String>();
}

public ArrayList<String> getQuestion() {
System.out.println("objjjj "+Questions.Question);
return Questions.Question;

}

public ArrayList<String> getCorrectAnswers() {
return Questions.CorrectAnswers;
}

public ArrayList<String> getIncorrectAnswers() {
return Questions.IncorrectAnswers;
}
}