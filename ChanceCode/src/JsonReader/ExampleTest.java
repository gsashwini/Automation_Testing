package JsonReader;

import org.junit.Test;

import org.codehaus.jackson.map.ObjectMapper;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.util.ArrayList;

public class ExampleTest {
	
	ArrayList<AssessmentObj> list = new ArrayList<AssessmentObj>();
	@Test
	public void test() {
		parseAssessments();
	}

	public void parseAssessments() {
		JSONParser parser = new JSONParser();
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			Object obj = parser.parse(new FileReader("src//ResultOfJson//assessments.json"));
		//	System.out.println("objjjj "+obj);
			JSONObject jsonObject = (JSONObject) obj;

			ArrayList arr1 = new ArrayList();
			String ass = jsonObject.get("Assessments").toString();
			//System.out.println("assassassassass "+ass);

			Object obj2 = parser.parse(ass);
			arr1 = (ArrayList) obj2;
			for (int j = 0; j < arr1.size(); j++) {
				AssessmentObj assessment = new AssessmentObj();
				AssessmentObj.Questions questions = new AssessmentObj.Questions();
				ArrayList queslist = new ArrayList();
				ArrayList incorrlist = new ArrayList();
				ArrayList corrlist = new ArrayList();
				JSONObject mainJson = (JSONObject) arr1.get(j);
				//System.out.println("mainJsonmainJsonmainJson "+mainJson);

				assessment.OID = mainJson.get("OID").toString();
			//	System.out.println("assessment.OIDassessment.OID "+assessment.OID);
				assessment.Language = mainJson.get("Language").toString();
				//System.out.println("assessment.OIDassessment.Language "+assessment.Language);
				assessment.CourseNumber = mainJson.get("CourseNumber").toString();
			//	System.out.println("assessment.OIDassessment.CourseNumber "+assessment.CourseNumber);
				assessment.Version = mainJson.get("Version").toString();
			//	System.out.println("assessment.OIDassessment.Version "+assessment.Version);
				
				
				String questionsObj = mainJson.get("questions").toString();
				Object obj1 = parser.parse(questionsObj);
				//System.out.println("ojb11111111 "+obj1);

				ArrayList arr = new ArrayList();
				arr = (ArrayList) obj1;
				for (int i = 0; i < arr.size(); i += 3) {
					JSONObject ques = (JSONObject) arr.get(i);
					JSONObject corr = (JSONObject) arr.get(i + 1);
					JSONObject incorr = (JSONObject) arr.get(i + 2);

					String object = ques.get("Question").toString();
					//System.out.println("QuestionQuestion    "+object);
					questions.Question.add(object);

					object = corr.get("CorrectAnswers").toString();
				//	System.out.println("CorrectAnswers    "+object);
					questions.CorrectAnswers.add(object);

					object = incorr.get("IncorrectAnswers").toString();
					//System.out.println("IncorrectAnswers   "+object);
					questions.IncorrectAnswers.add(object);
				}
				list.add(assessment);
			}
		} catch (Exception e) {
		}
		
		
//		AssessmentObj assessment0 = list.get(0);
//		ArrayList<String> ques = assessment0.getQuestion();
//		System.out.println(ques.get(0));
//		ArrayList<String> ca = assessment0.getCorrectAnswers();
//		 System.out.println(ca.get(0));
	}
}