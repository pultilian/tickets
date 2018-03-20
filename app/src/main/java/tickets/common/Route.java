
package tickets.common;

import tickets.common.RouteColors;
import tickets.common.PlayerColor;

public class Route {
	int length;
	String src;
	String dest;

	RouteColors firstColor;
	PlayerColor firstOwner;

	RouteColors secondColor;
	PlayerColor secondOwner;

	public Route(String src, String dest, RouteColors firstColor, int length) {
		this.src = src;
		this.dest = dest;

		this.firstColor = firstColor;
		this.firstOwner = null;

		this.length = length;

		this.secondColor = null;
		this.secondOwner = null;
	}

	public Route(String src, String dest, RouteColors firstColor, RouteColors secondColor, int length) {
		this.src = src;
		this.dest = dest;

		this.firstColor = firstColor;
		this.firstOwner = null;

		this.secondColor = secondColor;
		this.secondOwner = null;

		this.length = length;
	}



	// Check if a route is a double route or not
	// 
	public boolean isDouble() {
		if (this.secondColor == null) {
			return false;
		}
		return true;
	}

	public boolean equals(String src, String dest) {
		if (this.src == src  && this.dest == dest) return true;
		if (this.src == dest && this.dest == src)  return true;
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

	public boolean isOwned(RouteColors color) {
		if (this.firstColor == color) {
			if (this.firstOwner == null) return false;
			else return true;
		}
		else if (this.secondColor == color) {
			if (this.secondOwner == null) return false;
			else return true;
		}
		return false;
	}

	// Return true if the route is claimed successfully.
	// Return false if the route is already claimed or
	//   if neither of the routes between these two
	// 	 cities have the specified color.
	public boolean claim(RouteColors color, PlayerColor claimant) {
		if (this.firstColor == color) {
			if (this.firstOwner == null) {
				this.firstOwner = claimant;
				return true;
			}
			else return false;
		}
		else if (this.secondColor == color) {
			if (this.secondOwner == null) {
				this.secondOwner = claimant;
				return true;
			}
			else return false;
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