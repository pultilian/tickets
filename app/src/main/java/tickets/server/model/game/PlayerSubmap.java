package tickets.server.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PlayerSubmap {

    private Map<String, List<String>> adjacencyList;

    PlayerSubmap() {
        this.adjacencyList = new HashMap<>();
    }

    void addRoute(String src, String dest) {
        if (!adjacencyList.containsKey(src)) {
            adjacencyList.put(src, new ArrayList<String>());
        }
        List<String> routesFromSrc = adjacencyList.get(src);
        routesFromSrc.add(dest);

        if (!adjacencyList.containsKey(dest)) {
            adjacencyList.put(dest, new ArrayList<String>());
        }
        List<String> routesFromDest = adjacencyList.get(dest);
        routesFromDest.add(src);
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
}
