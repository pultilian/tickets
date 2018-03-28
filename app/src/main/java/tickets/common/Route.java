
package tickets.common;

import java.util.List;

public class Route {
	private int length;
	private String src;
	private String dest;

	private RouteColors color;
	private PlayerColor firstOwner;
    private PlayerColor secondOwner;
	private boolean doubleRoute;

	public Route(String src, String dest, RouteColors color, boolean doubleRoute, int length) {
		this.src = src;
		this.dest = dest;

		this.color = color;
		this.firstOwner = null;
        this.secondOwner = null;

		this.length = length;

		this.doubleRoute = doubleRoute;
	}

    public RouteColors getColor() {
        return color;
    }

	public int getLength() {
		return length;
	}

	public String getSrc() {
		return src;
	}

	public String getDest() {
		return dest;
	}

	public boolean isDouble() {
		return doubleRoute;
	}

	public boolean equals(String src, String dest) {
		if (this.src.equals(src)  && this.dest.equals(dest)) return true;
		if (this.src.equals(dest) && this.dest.equals(src))  return true;
		else return false;
	}

	@Override
	// Since edges on the map graph are undirected,
	//	 (src, dest) == (dest, src) is defined as true
	//
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o.getClass() != Route.class) return false;

		Route other = (Route) o;
		if (other.src.equals(this.src)) {
			if (other.dest.equals(this.dest)) return true;
			else return false;
		}
		else if (other.dest.equals(this.src)) {
			if (other.src.equals(this.src)) return true;
			else return false;
		}
		return false;
	}

	public boolean isOwned() {
        if (firstOwner == null)
            return false;
        return true;
    }

    public boolean isAvailable() {
        if (firstOwner == null)
            return false;
		else if (doubleRoute && secondOwner == null)
		    return false;
		return true;
	}


	// Return true if the route is claimed successfully.
	// Return false if the route is already claimed (or someone tries to claim both routes) or
	//   if neither of the routes between these two cities have the specified color (or gray).
	public boolean claim(List<TrainCard> cards, PlayerColor claimant) {
	    // Test if claim is valid
        if (cards.size() != length)
            return false;
        for (TrainCard card : cards) {
            if (card.getColor() != this.color) {
                if (!(card.getColor() == RouteColors.Gray || this.color == RouteColors.Gray))
                    return false;
            }
        }

        // If not already owned, set owner
        if (firstOwner == null) {
            firstOwner = claimant;
            return true;
        }
        else {
            // If it's a double route, try setting second owner
            if (doubleRoute && secondOwner == null) {
                secondOwner = claimant;
                return true;
            }
        }

		return false;
	}

	// Get the number of points gained by the player
	//   associated to this route's length
	public int getPointValue() {
		if (length == 1) return 1;
		if (length == 2) return 2;
		if (length == 3) return 4;
		if (length == 4) return 7;
		if (length == 5) return 10;
		if (length == 6) return 15;
		else return 0;
	}

}