package afd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public class DifferenceAFD {
    public AFD DFADifferenceMeth(AFD automate1, AFD automate2) {
        Set<String> states = new HashSet<>();
        Set<String> alphabet = new HashSet<>(automate1.getAlphabet()); // Même alphabet pour les deux automates
        Map<String, Map<String, String>> transitions = new HashMap<>();
        Set<String> finalStates = new HashSet<>();

        // Création des états de l'automate résultant
        for (String state1 : automate1.getStates()) {
            for (String state2 : automate2.getStates()) {
                String newState = "(" + state1 + "-" + state2 + ")";
                states.add(newState);

                Map<String, String> newTransitions = new HashMap<>();
                for (String symbol : alphabet) {
                    String nextState1 = automate1.getTransitions().get(state1).get(symbol);
                    String nextState2 = automate2.getTransitions().get(state2).get(symbol);

                    if (nextState1 != null && nextState2 != null) {
                        String combinedState = nextState1 + "-" + nextState2;
                        if (automate1.getStates().contains(nextState1) && automate2.getStates().contains(nextState2)) {
                            newTransitions.put(symbol, combinedState);
                        }
                    }
                }
                transitions.put(newState, newTransitions);

                if (automate1.getFinalStates().contains(state1) && !automate2.getFinalStates().contains(state2)) {
                    finalStates.add(newState);
                }
            }
        }

        return new AFD(states, alphabet, transitions, automate1.getInitialState() + "-" + automate2.getInitialState(), finalStates);
    }
}