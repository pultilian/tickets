package tickets.common;

public class Route {
	private int length;
	private String src;
	private String dest;

    private RouteColors firstColor;
    private PlayerColor firstOwner;

    private RouteColors secondColor;
    private PlayerColor secondOwner;

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
        if (this.secondColor == null) {
            return false;
        }
        return true;
    }

    public RouteColors getFirstColor() {
        return firstColor;
    }

    public PlayerColor getFirstOwner() {
        return firstOwner;
    }

    public RouteColors getSecondColor() {
        return secondColor;
    }

    public PlayerColor getSecondOwner() {
        return secondOwner;
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
		else if (isDouble() && secondOwner == null)
		    return false;
		return true;
	}


	// Return true if the route is claimed successfully.
	// Return false if the route is already claimed (or someone tries to claim both routes) or
	//   if neither of the routes between these two cities have the specified color (or gray).
    public boolean claim(RouteColors color, PlayerColor claimant) {
        if (this.firstColor == RouteColors.Gray || this.firstColor == color) {
            if (this.firstOwner == null && this.secondOwner != claimant) {
                this.firstOwner = claimant;
                return true;
            }
            else return false;
        }
        else if (this.secondColor == RouteColors.Gray || this.secondColor == color) {
            if (this.secondOwner == null && this.firstOwner != claimant) {
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