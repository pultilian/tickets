
package tickets.common;

public class DestinationCard {
	private String firstCity;
	private String secondCity;
	private int value;
	
	DestinationCard(String firstCity, String secondCity, int value) {
		this.firstCity = firstCity;
		this.secondCity = secondCity;
		this.value = value;
	}

	public String getFirstCity() {
		return firstCity;
	}

	public String getSecondCity() {
		return secondCity;
	}

	public int getValue() {
		return value;
	}
}