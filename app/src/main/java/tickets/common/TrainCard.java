
package tickets.common;

public class TrainCard {

	private RouteColors color;

	public TrainCard(RouteColors cardColor) {
		this.color = cardColor;
	}

    public RouteColors getColor() {
      return color;
  }

    public void setColor(RouteColors color) {
        this.color = color;
        return;
    }
}