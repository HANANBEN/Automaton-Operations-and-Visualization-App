package afd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BMCInterface extends JFrame {
    private JTextArea transitionsArea1;
    private JTextField alphabetField1, statesField1, initialStateField1, finalStatesField1;
    private JTextArea resultArea;

    public BMCInterface() {
        setTitle("BMC ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Setting the background color for the main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light grey background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel inputPanel1 = createInputPanel("Automate 1");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        mainPanel.add(inputPanel1, gbc);

        JButton calculateButton = new JButton("Calculate");
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
                BMC BMC = new BMC();
                Map<String, Map<String, String>> T = automate1.getTransitions();
                Set<String> finalStates = new HashSet<>();
                String etatInitiale = automate1.getInitialState();
                for (String et : finalStates) {
                    T.get(et).put("$", "F");
                }
                Map<String, String> aux = new HashMap<>();
                aux.put("$", etatInitiale);
                T.put("I", aux);
                T = BMC.minimiserEtat(automate1.getTransitions(), automate1.getFinalStates(), automate1.getAlphabet());
                printAutomate(T);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.insets = new Insets(20, 10, 20, 10);
        mainPanel.add(calculateButton, gbc);

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
        mainPanel.add(new JScrollPane(resultArea), gbc);

        add(mainPanel);
        setVisible(true);
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
            }
        }

        return new AFD(states, alphabet, transitions, initialState, finalStates);
    }

    private void printAutomate(Map<String, Map<String, String>> T) {
        StringBuilder result = new StringBuilder();

        result.append("δ = {\n");
        for (String etat : T.keySet()) {
            for (String symb : T.get(etat).keySet()) {
                result.append("\t(").append(etat).append(", ").append(symb).append(") = ").append(T.get(etat).get(symb)).append("\n");
            }
        }
        result.append("}");

        resultArea.setText(result.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BMCInterface();
            }
        });
    }
}
