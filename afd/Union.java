package afd;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Union {
    public AFD unionAutomate(AFD automate1, AFD automate2) {
        Set<String> states = new HashSet<>();
        Set<String> alphabet = new HashSet<>(automate1.getAlphabet());
        alphabet.retainAll(automate2.getAlphabet());
        Map<String, Map<String, String>> transitions = new HashMap<>();
        Set<String> finalStates = new HashSet<>();

        // Création des états de l'automate produit
        for (String state1 : automate1.getStates()) {
            for (String state2 : automate2.getStates()) {
                String newState = "(" + state1 + "," + state2 + ")";
                states.add(newState);

                // Création des transitions de l'automate produit
                Map<String, String> newTransitions = new HashMap<>();
                for (String symbol : alphabet) {
                    String nextState1 = automate1.getTransitions().getOrDefault(state1, new HashMap<>()).get(symbol);
                    String nextState2 = automate2.getTransitions().getOrDefault(state2, new HashMap<>()).get(symbol);
                    if (nextState1 != null && nextState2 != null) {
                        String nextState = "(" + nextState1 + "," + nextState2 + ")";
                        newTransitions.put(symbol, nextState);
                    }
                }
                transitions.put(newState, newTransitions);

                // Vérification des états finaux de l'automate produit
                if (automate1.getFinalStates().contains(state1) && automate2.getFinalStates().contains(state2)) {
                    finalStates.add(newState);
                }
            }
        }

        // Création de l'automate produit
        String initialState = "(" + automate1.getInitialState() + "," + automate2.getInitialState() + ")";
        return new AFD(states, alphabet, transitions, initialState, finalStates);
    }

}
