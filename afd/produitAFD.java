package afd;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class produitAFD {
    public AFD product(AFD automate1, AFD automate2) {
        Set<String> states = new HashSet<>();
        Set<String> alphabet = new HashSet<>(automate1.getAlphabet());
        alphabet.retainAll(automate2.getAlphabet());
        Map<String, Map<String, String>> transitions = new HashMap<>();
        Set<String> finalStates = new HashSet<>();

        for (String state1 : automate1.getStates()) {
            for (String state2 : automate2.getStates()) {
                String newState = state1 + "," + state2;
                states.add(newState);

                Map<String, String> newTransitions = new HashMap<>();
                for (String symbol : alphabet) {
                    String nextState1 = automate1.getTransitions().get(state1).get(symbol);
                    String nextState2 = automate2.getTransitions().get(state2).get(symbol);
                    if (nextState1 != null && nextState2 != null) {
                        newTransitions.put(symbol, nextState1 + "," + nextState2);
                    }
                }
                transitions.put(newState, newTransitions);

                if (automate1.getFinalStates().contains(state1) && automate2.getFinalStates().contains(state2)) {
                    finalStates.add(newState);
                }
            }
        }

        return new AFD(states, alphabet, transitions, automate1.getInitialState() + "," + automate2.getInitialState(), finalStates);
    }
}

    /*public static void main(String[] args) {
        // Exemple d'utilisation
        Set<String> states1 = new HashSet<>();
        states1.add("q0");
        states1.add("q1");
        Set<Character> alphabet1 = new HashSet<>();
        alphabet1.add('a');
        alphabet1.add('b');
        Map<String, Map<Character, String>> transitions1 = new HashMap<>();
        Map<Character, String> q0Transitions1 = new HashMap<>();
        q0Transitions1.put('a', "q1");
        q0Transitions1.put('b', "q0");
        Map<Character, String> q1Transitions1 = new HashMap<>();
        q1Transitions1.put('a', "q0");
        q1Transitions1.put('b', "q1");
        transitions1.put("q0", q0Transitions1);
        transitions1.put("q1", q1Transitions1);
        String initialState1 = "q0";
        Set<String> finalStates1 = new HashSet<>();
        finalStates1.add("q1");
        Automate automate1 = new Automate(states1, alphabet1, transitions1, initialState1, finalStates1);

        // Definir le deuxieme automate
        Set<String> states2 = new HashSet<>();
        states2.add("p0");
        states2.add("p1");
        Set<Character> alphabet2 = new HashSet<>();
        alphabet2.add('a');
        alphabet2.add('b');
        Map<String, Map<Character, String>> transitions2 = new HashMap<>();
        Map<Character, String> p0Transitions2 = new HashMap<>();
        p0Transitions2.put('a', "p1");
        p0Transitions2.put('b', "p0");
        Map<Character, String> p1Transitions2 = new HashMap<>();
        p1Transitions2.put('a', "p0");
        p1Transitions2.put('b', "p1");
        transitions2.put("p0", p0Transitions2);
        transitions2.put("p1", p1Transitions2);
        String initialState2 = "p0";
        Set<String> finalStates2 = new HashSet<>();
        finalStates2.add("p1");
        Automate automate2 = new Automate(states2, alphabet2, transitions2, initialState2, finalStates2);

        // Produit des deux automates
        DFAProduct dfaProduct = new DFAProduct();
        Automate productAutomate = dfaProduct.product(automate1, automate2);

        // Affichage des résultats
        System.out.println("States: " + productAutomate.getStates());
        System.out.println("Alphabet: " + productAutomate.getAlphabet());
        System.out.println("Transitions: " + productAutomate.getTransitions());
        System.out.println("Initial State: " + productAutomate.getInitialState());
        System.out.println("Final States: " + productAutomate.getFinalStates());
    }*/

