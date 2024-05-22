package afd;

import java.util.Map;
import java.util.HashSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class AFD {
	 private Set<String> states;
	    private Set<String> alphabet;
	    private Map<String, Map<String, String>> transitions;
	    private String initialState;
	    private Set<String> finalStates;

	    public AFD(Set<String> states, Set<String> alphabet, Map<String, Map<String, String>> transitions,
	                    String initialState, Set<String> finalStates) {
	        this.states = states;
	        this.alphabet = alphabet;
	        this.transitions = transitions;
	        this.initialState = initialState;
	        this.finalStates = finalStates;
	    }
	    public static AFD parseAutomate(List<String> lines) {
	        Set<String> states = new HashSet<>();
	        Set<String> alphabet = new HashSet<>();
	        Map<String, Map<String, String>> transitions = new HashMap<>();
	        String initialState = null;
	        Set<String> finalStates = new HashSet<>();

	        boolean readAlphabet = false;
	        for (String line : lines) {
	            String[] parts = line.split(" ");
	            if (!readAlphabet) {
	                for (String symbol : parts) {
	                    alphabet.add(String.valueOf(symbol.charAt(0)));
	                }
	                readAlphabet = true;
	            } else if (states.isEmpty()) {
	                states.addAll(Arrays.asList(parts));
	            } else if (initialState == null) {
	                initialState = parts[0];
	            } else if (finalStates.isEmpty()) {
	                finalStates.addAll(Arrays.asList(parts));
	            } else {
	                String fromState = parts[0];
	                Map<String, String> stateTransitions = transitions.getOrDefault(fromState, new HashMap<>());
	                for (int i = 1; i < parts.length; i += 2) {
	                	
	                    String symbol = String.valueOf(parts[i].charAt(0));
	                    String toState = parts[i + 1];
	                    stateTransitions.put(symbol, toState);
	                }
	                transitions.put(fromState, stateTransitions);
	            }
	        }
	        return new AFD(states, alphabet, transitions, initialState, finalStates);
	    }


	    public Set<String> getStates() {
	        return states;
	    }

	    public Set<String> getAlphabet() {
	        return alphabet;
	    }

	    public Map<String, Map<String, String>> getTransitions() {
	        return transitions;
	    }

	    public String getInitialState() {
	        return initialState;
	    }

	    public Set<String> getFinalStates() {
	        return finalStates;
	    }
	    @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("States: ").append(states).append("\n");
	        sb.append("Alphabet: ").append(alphabet).append("\n");
	        sb.append("Initial State: ").append(initialState).append("\n");
	        sb.append("Final States: ").append(finalStates).append("\n");
	        sb.append("Transitions:\n");
	        for (Map.Entry<String, Map<String, String>> entry : transitions.entrySet()) {
	            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
	        }
	        return sb.toString();
	    }


}
