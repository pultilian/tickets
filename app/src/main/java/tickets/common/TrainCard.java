
package tickets.common;

import tickets.common.RouteColors;

public class TrainCard {
	private RouteColors color;

	public TrainCard(RouteColors color) {
		this.color = color;
	}

	public RouteColors getColor() {
		return color;
	}
}