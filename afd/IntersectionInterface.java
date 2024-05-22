package afd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntersectionInterface extends JPanel {
    private JTextArea transitionsArea1, transitionsArea2;
    private JTextField alphabetField1, statesField1, initialStateField1, finalStatesField1;
    private JTextField alphabetField2, statesField2, initialStateField2, finalStatesField2;
    private JTextArea resultArea;

    public IntersectionInterface() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel inputPanel1 = createInputPanel("Automate 1");
        JPanel inputPanel2 = createInputPanel("Automate 2");

        JButton calculateButton = new JButton("Calculate Intersection");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AFD automate1 = createAutomateFromInput(transitionsArea1, initialStateField1, finalStatesField1);
                AFD automate2 = createAutomateFromInput(transitionsArea2, initialStateField2, finalStatesField2);

                IntersectionAFD automateIntersection = new IntersectionAFD();
                AFD resultAutomate = automateIntersection.intersectionAutomate(automate1, automate2);

                printAutomate(resultAutomate);
            }
        });

        calculateButton.setPreferredSize(new Dimension(150, 30));

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        mainPanel.add(inputPanel1);
        mainPanel.add(inputPanel2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(calculateButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(new JScrollPane(resultArea));

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel(String title) {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JLabel alphabetLabel = new JLabel("Alphabet:");
        JLabel statesLabel = new JLabel("States:");
        JLabel initialStateLabel = new JLabel("Initial State:");
        JLabel finalStatesLabel = new JLabel("Final States:");
        JLabel transitionsLabel = new JLabel("Transitions:");

        JTextField alphabetField = new JTextField();
        JTextField statesField = new JTextField();
        JTextField initialStateField = new JTextField();
        JTextField finalStatesField = new JTextField();

        panel.add(alphabetLabel);
        panel.add(alphabetField);
        panel.add(statesLabel);
        panel.add(statesField);
        panel.add(initialStateLabel);
        panel.add(initialStateField);
        panel.add(finalStatesLabel);
        panel.add(finalStatesField);
        panel.add(transitionsLabel);

        JTextArea transitionsArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(transitionsArea);
        panel.add(scrollPane);

        if (title.equals("Automate 1")) {
            alphabetField1 = alphabetField;
            statesField1 = statesField;
            initialStateField1 = initialStateField;
            finalStatesField1 = finalStatesField;
            transitionsArea1 = transitionsArea;
        } else {
            alphabetField2 = alphabetField;
            statesField2 = statesField;
            initialStateField2 = initialStateField;
            finalStatesField2 = finalStatesField;
            transitionsArea2 = transitionsArea;
        }

        return panel;
    }

    private AFD createAutomateFromInput(JTextArea transitionsArea, JTextField initialStateField, JTextField finalStatesField) {
        Set<String> states = new HashSet<>();
        Set<String> alphabet = new HashSet<>();
        String initialState = initialStateField.getText();
        Set<String> finalStates = new HashSet<>(Arrays.asList(finalStatesField.getText().split(",")));

        Map<String, Map<String, String>> transitions = new HashMap<>();

        for (String line : transitionsArea.getText().split("\n")) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 3) {
                String currentState = parts[0];
                String symbol = String.valueOf(parts[1].charAt(0));
                String nextState = parts[2];

                states.add(currentState);
                states.add(nextState);
                alphabet.add(symbol);

                transitions.computeIfAbsent(currentState, k -> new HashMap<>());
                transitions.get(currentState).put(symbol, nextState);
            } else {
                // Handle incorrect lines if necessary
            }
        }

        return new AFD(states, alphabet, transitions, initialState, finalStates);
    }

    private void printAutomate(AFD automate) {
        StringBuilder result = new StringBuilder();

        result.append("Σ = ").append(automate.getAlphabet()).append("\n");
        result.append("Q = ").append(automate.getStates()).append("\n");
        result.append("q₀ = ").append(automate.getInitialState()).append("\n");
        result.append("F = ").append(automate.getFinalStates()).append("\n");

        result.append("δ = {\n");
        for (String currentState : automate.getTransitions().keySet()) {
            Map<String, String> currentTransitions = automate.getTransitions().get(currentState);
            for (String symbol : currentTransitions.keySet()) {
                String nextState = currentTransitions.get(symbol);
                result.append("\t(").append(currentState).append(", ").append(symbol).append(") = ").append(nextState).append("\n");
            }
        }
        result.append("}");

        resultArea.setText(result.toString());
    }
}
