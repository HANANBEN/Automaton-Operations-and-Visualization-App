package afd;

import java.util.*;


public class LangageReconnu {

    public boolean hasCycle(Graph<String> graph) {
        Set<Vertex<String>> whiteSet = new HashSet<>();
        Set<Vertex<String>> graySet = new HashSet<>();
        Set<Vertex<String>> blackSet = new HashSet<>();

        for (Vertex<String> vertex : graph.getAllVertex()) {
            whiteSet.add(vertex);
        }

        while (whiteSet.size() > 0) {
            Vertex<String> current = whiteSet.iterator().next();
            if (dfs(current, whiteSet, graySet, blackSet)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfs(Vertex<String> current, Set<Vertex<String>> whiteSet,
                        Set<Vertex<String>> graySet, Set<Vertex<String>> blackSet) {
        //move current to gray set from white set and then explore it.
        moveVertex(current, whiteSet, graySet);
        for (Vertex<String> neighbor : current.getAdjacentVertexes()) {
            if (blackSet.contains(neighbor)) {
                continue;
            }
            //if in gray set then cycle found.
            if (graySet.contains(neighbor)) {
                return true;
            }
            if (dfs(neighbor, whiteSet, graySet, blackSet)) {
                return true;
            }
        }
        //move vertex from gray set to black set when done exploring.
        moveVertex(current, graySet, blackSet);
        return false;
    }

    private void moveVertex(Vertex<String> vertex, Set<Vertex<String>> sourceSet,
                            Set<Vertex<String>> destinationSet) {
        sourceSet.remove(vertex);
        destinationSet.add(vertex);
    }

    public boolean acceptsEmptyLanguage(Graph<String> graph, Set<Vertex<String>> initialStates, Set<Vertex<String>> finalStates) {
        Set<Vertex<String>> accessibleStates = new HashSet<>();
        Stack<Vertex<String>> stack = new Stack<>();

        // Find the actual vertices in the graph that correspond to the initial states
        for (Vertex<String> initialState : initialStates) {
            for (Vertex<String> vertex : graph.getAllVertex()) {
                if (vertex.equals(initialState)) {
                    stack.push(vertex);
                    break;
                }
            }
        }

        while (!stack.isEmpty()) {
            Vertex<String> currentState = stack.pop();

            if (!accessibleStates.contains(currentState)) {
                accessibleStates.add(currentState);

                for (Vertex<String> neighbor : currentState.getAdjacentVertexes()) {
                    if (!accessibleStates.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }

        // Check if at least one final state is accessible from the initial states
        for (Vertex<String> finalState : finalStates) {
            if (accessibleStates.contains(finalState)) {
                return false; // The language is not empty
            }
        }

        return true;
    }

}

