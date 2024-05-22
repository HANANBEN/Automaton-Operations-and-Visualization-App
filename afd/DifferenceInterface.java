package afd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DifferenceInterface extends JPanel {
    private JTextArea transitionsArea1, transitionsArea2;
    private JTextField alphabetField1, statesField1, initialStateField1, finalStatesField1;
    private JTextField alphabetField2, statesField2, initialStateField2, finalStatesField2;
    private JTextArea resultArea;

    public DifferenceInterface() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245)); // Light grey background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel inputPanel1 = createInputPanel("Automate 1");
        JPanel inputPanel2 = createInputPanel("Automate 2");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        add(inputPanel1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        add(inputPanel2, gbc);

        JButton calculateButton = new JButton("Calculate Difference");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        calculateButton.setPreferredSize(new Dimension(200, 40));
        calculateButton.setBackground(new Color(255, 105, 180)); // Rose background
        calculateButton.setForeground(Color.WHITE); // White text
        calculateButton.setFocusPainted(false);
        calculateButton.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Black border
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AFD automate1 = createAutomateFromInput(transitionsArea1, initialStateField1, finalStatesField1);
                AFD automate2 = createAutomateFromInput(transitionsArea2, initialStateField2, finalStatesField2);
                DifferenceAFD automateDifference = new DifferenceAFD();
                AFD resultAutomate = automateDifference.DFADifferenceMeth(automate1, automate2);
                printAutomate(resultAutomate);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(20, 10, 20, 10);
        add(calculateButton, gbc);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Result"));
        resultArea.setBackground(Color.WHITE);
        resultArea.setForeground(Color.BLACK);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(resultArea), gbc);
    }

    private JPanel createInputPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(new Color(245, 245, 245)); // Light grey background

        JLabel alphabetLabel = new JLabel("Alphabet:");
        JLabel statesLabel = new JLabel("States:");
        JLabel initialStateLabel = new JLabel("Initial State:");
        JLabel finalStatesLabel = new JLabel("Final States:");
        JLabel transitionsLabel = new JLabel("Transitions:");

        // Set label colors
        alphabetLabel.setForeground(Color.BLACK);
        statesLabel.setForeground(Color.BLACK);
        initialStateLabel.setForeground(Color.BLACK);
        finalStatesLabel.setForeground(Color.BLACK);
        transitionsLabel.setForeground(Color.BLACK);

        JTextField alphabetField = new JTextField();
        JTextField statesField = new JTextField();
        JTextField initialStateField = new JTextField();
        JTextField finalStatesField = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(alphabetLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(alphabetField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(statesLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(statesField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(initialStateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(initialStateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(finalStatesLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(finalStatesField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(transitionsLabel, gbc);

        JTextArea transitionsArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(transitionsArea);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(scrollPane, gbc);

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
                
                alphabet.add(String.valueOf(symbol));

                transitions.computeIfAbsent(currentState, k -> new HashMap<>());
                transitions.get(currentState).put(symbol, nextState);
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
