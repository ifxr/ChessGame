import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Leaderboard extends JPanel{
	static String team = null;
	static String time = null;
	static String eaten = null;
	
	static Object[][] data;
	JPanel panel;
	JScrollPane scrollPane;
	static int count = 0;
	
	int size = 5;
	String fileName = "leaderboard.json";
	
	Leaderboard(){
		panel = new JPanel();
		
		//panel.setBounds(0, 0, this.getWidth(), this.getHeight());
		panel.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		readLeaderboard();
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	Leaderboard(String team, String time, String eaten){
		this.team = team;
		this.time = time;
		this.eaten = eaten;
			
		writeLeaderboard();
	}
		
		
	public void readLeaderboard() {
		JSONParser jParse = new JSONParser();
		
		try{
			File file = new File(fileName);
			
			 System.out.println("File length: "+ file.length());
			
			file.createNewFile();
			
			if (file.length() == 0) {
				file.delete();
				return;
			}
				
			FileReader reader = new FileReader(fileName);
			Object obj = jParse.parse(reader);
			JSONArray jsonArr = (JSONArray) obj;
			
			String[] columnNames = {"Winner ", "Time Spent", "Pieces Eaten"};
			data = new Object[jsonArr.size()][columnNames.length];
			
			jsonArr.forEach(element -> readHelper((JSONObject)element));
			
			JTable table = new JTable(data, columnNames);
			scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(getSize());
			panel.add(scrollPane, BorderLayout.CENTER);
			count = 0;
			
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (ParseException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	public static void readHelper(JSONObject element) {
		JSONObject elementObj = (JSONObject) element.get("player");
		
		team = (String) elementObj.get("Winner");
		time = (String) elementObj.get("Time Spent");
		eaten = (String) elementObj.get("Pieces Eaten");
		
		data[count][0] = team;
		data[count][1] = time;
		data[count][2] = eaten;
		
		count++;
		
		System.out.println("Team: "+ team+"Time: "+ time+"Eaten: "+ eaten);
	}
	
	public void writeLeaderboard() {
		JSONParser jParse = new JSONParser();
		
		try{
			//Used to check if file exists
			File file = new File(fileName);
			
			// Array used to save contents of leaderboard
			JSONArray jsonArr;
			
			// If file exists, contents is parsed and saved to be appended on.
			// If file does not exist, file is created
			if (file.createNewFile()) {
		        System.out.println("File created: " + file.getName());
		        jsonArr = new JSONArray();
		    } else {
		        System.out.println("File already exists.");
		        
		        FileReader reader = new FileReader(fileName);
				Object obj = jParse.parse(reader);
				jsonArr = (JSONArray) obj;
				System.out.println(jsonArr);
		    }
			
			// Limits the size of file to 'n' entries
			while(jsonArr.size() >= size) {
				jsonArr.remove(0);
			}
			
			// Stats will save all the components that will be displayed on screen
			JSONObject stats = new JSONObject();
			stats.put("Winner", team);
			stats.put("Time Spent", time);
			stats.put("Pieces Eaten", eaten);
			
			// Used to save multiple records into the JSONArray by separating each entry by the 'player' tag
			JSONObject player = new JSONObject();
			player.put("player", stats);
			
			// Add it to the jsonArray
			jsonArr.add(player);
			
			System.out.println(jsonArr);
			
			// Write the JSONArray into the file
			FileWriter writer = new FileWriter(fileName);
			writer.write(jsonArr.toJSONString());
			writer.flush();
			writer.close();
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (ParseException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	 }
}
