package XmlToJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	static JSONArray jsonObj = new JSONArray();
	static JSONObject assessmentsObj = new JSONObject();
	static JSONArray questionsJson = new JSONArray();

	public static void main(String[] args) {
		// Directory where the files are located
		File dir = new File("AssessmentItemFiles");

		// Create a FileFilter that matches ".xml" files
		//FileFilter filter = (File file) -> file.isFile() && file.getName().endsWith(".xml");

		// Get pathnames of matching files.
		File[] paths = dir.listFiles();
		for (int i = 0; i < paths.length; i++) {
		String fileName = paths[i].getName();
			createJSON(fileName, paths, i);
		}
		
		FileWriter file;
		try {
			file = new FileWriter(System.getProperty("user.dir") + "\\src\\ResultOfJson\\assessments.json");
			file.write(assessmentsObj.toJSONString());
			file.close();
		} catch (IOException e) {
		}
	}

	public static void createJSON(String fileName, File[] paths, int count) {
		JSONObject mainJson = new JSONObject();
		Assessments Assessment = new Assessments();
		Assessments.Questions questions = Assessment.new Questions();
		addJsonObject("(.*).xml", fileName, Assessment, "OID", mainJson);
		addJsonObject("([0-9](.[0-9]*)).xml", fileName, Assessment, "Version", mainJson);
		addJsonObject("_([a-zA-Z]*)_", fileName, Assessment, "Language", mainJson);
		addJsonObject("ravenna_([0-9])*_", fileName, Assessment, "CourseNumber", mainJson);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc = null;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		try {
			doc = dBuilder.parse(paths[0]);
		} catch (Exception e) {
		}

		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementsByTagName("item");

		for (int j = 0; j < nList.getLength(); j++) {
			JSONArray questionsList = new JSONArray();
			JSONArray answersList = new JSONArray();
			JSONArray incorrAnswersList = new JSONArray();
			questions.IncorrectAnswers = new ArrayList<String>();
			questions.CorrectAnswers = new ArrayList<String>();
			questions.Question = new ArrayList<String>();

			Element element = (Element) nList.item(j);
			if (element.getElementsByTagName("mattext").item(0) != null) {
				questions.Question.add(element.getElementsByTagName("mattext").item(0).getTextContent());
				if (!questionsList.contains(questions.Question)) {
					questionsList.add(element.getElementsByTagName("mattext").item(0).getTextContent());
				}
			}
			NodeList qList = element.getElementsByTagName("response_label");

			for (int k = 0; k < qList.getLength(); k++) {
				Element qElement = (Element) qList.item(k);
				if (qElement.getAttribute("rlcorrect").equals("No")) {
					if (qElement.getElementsByTagName("mattext").item(0) != null) {
						questions.IncorrectAnswers
								.add(qElement.getElementsByTagName("mattext").item(0).getTextContent());
						if (!incorrAnswersList.contains(questions.IncorrectAnswers)) {
							incorrAnswersList.add(questions.IncorrectAnswers);
						}
					}
				} else if (qElement.getAttribute("rlcorrect").equals("Yes")) {
					if (qElement.getElementsByTagName("mattext").item(0) != null) {
						questions.CorrectAnswers.add(qElement.getElementsByTagName("mattext").item(0).getTextContent());
						if (!answersList.contains(questions.CorrectAnswers)) {
							answersList.add(qElement.getElementsByTagName("mattext").item(0).getTextContent());
						}
					}
				}
			}
			JSONObject obj = new JSONObject();
			obj.put("Question", questionsList);
			questionsJson.add(obj);
			obj = new JSONObject();
			obj.put("CorrectAnswers", answersList);
			questionsJson.add(obj);
			obj = new JSONObject();
			obj.put("IncorrectAnswers", incorrAnswersList);
			questionsJson.add(obj);
		}
		mainJson.put("questions", questionsJson);
		jsonObj.add(mainJson);
		assessmentsObj.put("Assessments", jsonObj.toString());
	}

	public static void addJsonObject(String regex, String fileName, Assessments Assessment, 
			String object, JSONObject mainJson) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fileName);

		if (matcher.find()) {
			mainJson.put(object, matcher.group(1));
		}
	}
}