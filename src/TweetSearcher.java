//Caroline Holmes | caroline.b.holmes@gmail.com
import java.awt.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TweetSearcher {
	private ArrayList<Tweet> tweetList;
	private final String[] TITLES = {"Birdman", "Whiplash", "American Sniper", "Grand Budapest Hotel",
			"Imitation Game", "Selma", "Theory of Everything", "Boyhood"};

	public TweetSearcher() {
		tweetList = new ArrayList<Tweet>(); // initialize array
	}
	
	public TweetSearcher(String fileName){
		this();
		parseTweets(fileName);
	}

	// http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file
	private String readFile(String filename) {
		String content = null;
		File file = new File(filename); // for ex foo.txt
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	public void parseTweets(String file) {
		//Time, ID, Text, Retweets, GeoTag, PlaceTag, Favorites, User Name, User Location, User ID, Time Zone, 
		//User Followers, User Statuses, User Friends, User Handle, HashTags in Tweet, UserMentions in Tweet
		
		String[] splitList = readFile(file).split("Mon Feb 23 "); //creates an array containing each tweet separately
		for(String s : splitList){
			if(s.substring(0, 4).equals("Time")){
				continue;
				//skip the first row describing order of information
			}
			String[] splitTweet = s.split(",");
			tweetList.add(new Tweet(splitTweet[0], splitTweet[1], splitTweet[2], splitTweet[8], splitTweet[4]));
			//creates and adds a new tweet with Time, ID, Text, User Location, and Geotag
			
		}
	}
	public String toString(){
		//creates a string buffer to create a string representation of the list of tweets
		//much faster than appending strings
		StringBuffer listOfTweets = new StringBuffer();
		for (Tweet tweet : tweetList){
			listOfTweets.append(tweet.toString());
			listOfTweets.append("\n");
		}
		return listOfTweets.toString();	
	}
	
	//Returns a string indicating the most popular movie with the number of mentions
	//when determining the mentions the method ignores case and non important leading words to account for user
	//truncation during tweets
	public String popularity(){
		if(tweetList.size() == 0){
			return "Please read a file first";
		}
		TreeMap<String, Integer> counts = new TreeMap<String, Integer>();
		counts.put("Birdman", 0);
		counts.put("Whiplash", 0);
		counts.put("American Sniper", 0);
		counts.put("Grand Budapest Hotel", 0);
		counts.put("Imitation Game", 0);
		counts.put("Selma", 0);
		counts.put("Theory of Everything", 0);
		counts.put("Boyhood", 0);
		//birdman, whiplash, american sniper, grand budapest hotel, imitation game, selma, theory of everything, boyhood
		for(Tweet t : tweetList){
			for(String s : counts.keySet()){
				if (t.getText().toLowerCase().contains(s.toLowerCase())){
					counts.put(s, counts.get(s) + 1);
				}
			}
		}
		StringBuffer popularList= new StringBuffer("Movie Popularity ranked in descending order is as follows:\n");
		int counter = 1;
		for(String v : sortByValues(counts).keySet()){
			popularList.append(counter + ":\t(" + v + ", " + counts.get(v) + ") \n");
			counter++;
		}
		return popularList.toString();
	}
	
	public String announcementPrediction(int titleNum){
		int hour = 5;
		int minute = 30;
		int[] maxCount = {5, 30, 0};
		int counter = 0;
		for(Tweet t : tweetList) {
			if (minute != t.getMinute()) {
				if (counter > maxCount[2]){
					maxCount[0] = hour;
					maxCount[1] = minute;
					maxCount[2] = counter;
				}
				counter = 0;
				if(minute == 0){
						minute = 59;
						hour--;
					}
					else{
						minute--;
					}
				} //it is a new minute, check to see if past minute had a higher count of birdman mentions and update the counter
			
			if (hour == t.getHour()) {
				if (minute == t.getMinute()){
					if(t.getText().toLowerCase().contains(TITLES[titleNum].toLowerCase())){
						counter++;
					}
				} //if it's in the same counting period check for the title
			
			}
		 }
		if (maxCount[1] < 10){
		return "The time with the most " + TITLES[titleNum] + " mentions was 0" + maxCount[0] + ":0" + maxCount[1] + " UTC with " + maxCount[2] + " mentions.";  
		}
		else{
			return "The time with the most " + TITLES[titleNum] + " mentions was 0" + maxCount[0] + ":" + maxCount[1] + " UTC with " + maxCount[2] + " mentions.";  
		}
	}
	
	//returns the name of the state that was most often tweeted from using UserLocation
	//it is not case sensitive, but uses only full state names to determine location, not cities or other data
	//and thus can be used for a general idea of popular tweet locations, but not as a definite answer
	public String location(){
		TreeMap<String, Integer> states = new TreeMap<String, Integer>();
		states.put("Alabama", 0);
		states.put("Alaska", 0);
		states.put("Arizona", 0);
		states.put("Arkansas",0);
		states.put("California", 0);
		states.put("Colorado", 0);
		states.put("Connecticut", 0); 
		states.put("Delaware", 0);
		states.put("Florida", 0);
		states.put("Georgia", 0);
		states.put("Hawaii", 0);
		states.put("Idaho", 0);
		states.put("Illinois", 0);
		states.put("Indiana", 0); 
		states.put("Iowa", 0);
		states.put("Kansas", 0);
		states.put("Kentucky", 0);
		states.put("Louisiana", 0); 
		states.put("Maine", 0);
		states.put("Maryland", 0);
		states.put("Massachusetts", 0);
		states.put("Michigan", 0);
		states.put("Minnesota", 0);
		states.put("Mississippi", 0); 
		states.put("Missouri", 0);
		states.put("Montana", 0);
		states.put("Nebraska", 0); 
		states.put("Nevada", 0);
		states.put("New Hampshire", 0); 
		states.put("New Jersey", 0);
		states.put("New Mexico", 0); 
		states.put("New York", 0);
		states.put("North Carolina", 0); 
		states.put("North Dakota", 0); 
		states.put("Ohio", 0);
		states.put("Oklahoma", 0); 
		states.put("Oregon", 0);
		states.put("Pennsylvania", 0);
		states.put("Rhode Island", 0);
		states.put("South Carolina", 0);
		states.put("South Dakota", 0);
		states.put("Tennessee", 0);
		states.put("Texas", 0);
		states.put("Utah", 0);
		states.put("Vermont", 0);
		states.put("Virginia", 0);
		states.put("Washington", 0);
		states.put("West Virginia", 0);
		states.put("Wisconsin", 0);
		states.put("Wyoming", 0);	
		//Counters to determine max value
		for(String s : states.keySet()){
			for(Tweet t : tweetList){
				if(t.getUserLocation().toLowerCase().contains(s.toLowerCase())){
					//count each occurrence of each state
					states.put(s, states.get(s) + 1);
				}
			}
		}
		StringBuffer stateParticipation = new StringBuffer("The state participation ranked in descending order is as follows: \n");
		int counter = 1;
		for(String v : sortByValues(states).keySet()){
			stateParticipation.append(counter + ":\t(" + v + ", " + states.get(v) + ") \n");
			counter++;
		}

		return stateParticipation.toString();
	}

	public ArrayList<Tweet> getTweetList() {
		return tweetList;
	}

	public void setTweetList(ArrayList<Tweet> tweetList) {
		this.tweetList = tweetList;
	}
	

	public static void main(String[] args){
		TweetSearcher t = new TweetSearcher("oscar_tweets.csv");
		System.out.println(t.popularity());
		System.out.println(t.announcementPrediction(0) + "\n");
		System.out.println(t.location());
		
		
	}
	
	//From http://javarevisited.blogspot.com/2012/12/how-to-sort-hashmap-java-by-key-and-value.html#ixzz3U78tPDdc

	public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
	    LinkedList<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
	  
	    Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

	        @Override
	        public int compare(Entry<K, V> o1, Entry<K, V> o2) {
	            return o2.getValue().compareTo(o1.getValue());
	        }
	    });
	  
	    //LinkedHashMap will keep the keys in the order they are inserted
	    //which is currently sorted on natural ordering
	    Map<K,V> sortedMap = new LinkedHashMap<K,V>();
	  
	    for(Map.Entry<K,V> entry: entries){
	        sortedMap.put(entry.getKey(), entry.getValue());
	    }
	  
	    return sortedMap;
	}


	
}
