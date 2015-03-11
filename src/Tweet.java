//Caroline Holmes | caroline.b.holmes@gmail.com
public class Tweet implements Comparable<Tweet> {
	int hour;
	int minute;
	int second;
	String id;
	String text;
	String userLocation;
	String geoTag;

	public Tweet() {

	}

	public Tweet(String time, String id, String text, String userLocation,
			String geoTag) {
		parseTime(time);
		this.id = id;
		this.text = text;
		this.userLocation = userLocation;
		this.geoTag = geoTag;
	}

	public void parseTime(String stringTime) {
		//stringTime = stringTime.substring(11, 19); // time in hh:mm:ss format
		hour = Integer.parseInt(stringTime.substring(0, 2));
		minute = Integer.parseInt(stringTime.substring(3,5));
		second = Integer.parseInt(stringTime.substring(6,8));
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public String getGeoTag() {
		return geoTag;
	}

	public void setGeoTag(String geoTag) {
		this.geoTag = geoTag;
	}

	@Override
	public int compareTo(Tweet o) { //default compare by time. Fix to watch for midnight. 
		return (hour - o.getHour()) + (minute - o.getMinute()) + (second - o.getSecond());
		// returns a negative integer, zero, or a positive integer as this
		// object is less than, equal to,
		// or greater than the specified object.

	}

	@Override
	public String toString() {
		return hour + ":" + minute + ":" + second + " " + text + " "
				+ userLocation + " " + geoTag;
	}

}
