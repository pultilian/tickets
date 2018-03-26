package tickets.server.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tickets.common.Route;

class PlayerSubmap {

    private Map<String, List<String>> adjacencyList;
    private List<Route> routes;

    PlayerSubmap() {
        this.adjacencyList = new HashMap<>();
        this.routes = new ArrayList<>();
    }

    void addRoute(Route route) {
        if (!adjacencyList.containsKey(route.getSrc())) {
            adjacencyList.put(route.getSrc(), new ArrayList<String>());
        }
        List<String> routesFromSrc = adjacencyList.get(route.getSrc());
        routesFromSrc.add(route.getDest());

        if (!adjacencyList.containsKey(route.getDest())) {
            adjacencyList.put(route.getDest(), new ArrayList<String>());
        }
        List<String> routesFromDest = adjacencyList.get(route.getDest());
        routesFromDest.add(route.getSrc());
        routes.add(route);
    }

    boolean pathExists(String src, String dest) {
        if (!adjacencyList.containsKey(src) || !adjacencyList.containsKey(dest)) return false;

        // Base case: map for src contains dest
        if (adjacencyList.get(src).contains(dest)) return true;

        // Recursive case: depth-first search through adjacency list
        else {
            for (String intermediate : adjacencyList.get(src)) {
                if (pathExists(intermediate, dest)) return true;
            }
            // All intermediates searched, return false
            return false;
        }
    }

    int findLongestRoute() {
        // In the extremely unlikely event that this player did nothing the whole game...
        if (routes.isEmpty()) return 0;

        int longestRoute = 0;
        List<Route> visitedRoutes = new ArrayList<>();
        for (Route route : routes) {
            String start = route.getSrc();
            int lengthOfPath = longestRouteRecursive(visitedRoutes, start);
            if (lengthOfPath > longestRoute) longestRoute = lengthOfPath;
        }
        return longestRoute;
    }

    // Depth-first search through routes, ignoring those already visited.
    private int longestRouteRecursive(List<Route> visitedRoutes, String src) {
        List<Route> routes = findConnectingRoutes(src);
        routes.removeAll(visitedRoutes);

        // Base case: no more connecting routes to visit
        if (routes.isEmpty()) return 0;

        // Recursive case: Depth-first search through routes
        int longestSoFar = 0;
        for (Route route : routes) {
            visitedRoutes.add(route);
            String dest = (route.getSrc().equals(src) ? route.getDest() : route.getSrc());
            int pathLength = longestRouteRecursive(visitedRoutes, dest);
            if ((pathLength + route.getLength()) > longestSoFar) longestSoFar = (pathLength + route.getLength());
            visitedRoutes.remove(route);
        }
        return longestSoFar;
    }

    private List<Route> findConnectingRoutes(String src) {
        List<Route> connectingRoutes = new ArrayList<>();
        for (Route route : routes) {
            if (route.getSrc().equals(src) || route.getDest().equals(src)) connectingRoutes.add(route);
        }
        return connectingRoutes;
    }
}
