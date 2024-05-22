package afd;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DFAacceptMot {
    static class DFA {
        Map<String, Map<String, String>> transitions;
        Set<String> acceptingStates;
        String initialState;

        DFA() {
            transitions = new HashMap<>();
        }
    }

    public static JPanel createDFAAcceptMotPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JButton loadButton = new JButton("Charger DFA");
        loadButton.setBackground(Color.decode("#4CAF50")); // Green color for button
        loadButton.setForeground(Color.WHITE);
        panel.add(loadButton);

        JTextArea wordTextArea = new JTextArea(1, 20);
        wordTextArea.setText("Entrez un mot ici :");
        wordTextArea.setBackground(Color.decode("#F5F5F5")); // Light grey background
        panel.add(wordTextArea);

        JButton checkButton = new JButton("Vérifier");
        checkButton.setBackground(Color.decode("#008CBA")); // Blue color for button
        checkButton.setForeground(Color.WHITE);
        panel.add(checkButton);

        JLabel resultLabel = new JLabel("Résultat : ");
        resultLabel.setForeground(Color.decode("#333333")); // Dark text color
        panel.add(resultLabel);

        DFA dfa = new DFA();

        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    Scanner scanner = new Scanner(selectedFile);
                    // Clear previous DFA data
                    dfa.transitions.clear();
                    dfa.acceptingStates = null;
                    dfa.initialState = null;

                    // Read alphabet
                    String alphabetLine = scanner.nextLine();
                    String[] alphabet = alphabetLine.trim().split("\\s+");

                    // Read states
                    String statesLine = scanner.nextLine();
                    String[] states = statesLine.trim().split("\\s+");

                    // Read accepting states
                    String acceptingStatesLine = scanner.nextLine();
                    String[] acceptingStatesArray = acceptingStatesLine.trim().split("\\s+");
                    dfa.acceptingStates = new HashSet<>(Arrays.asList(acceptingStatesArray));

                    // Read initial state
                    String initialState = scanner.nextLine().trim();
                    dfa.initialState = initialState;

                    // Read transitions
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.trim().split("\\s+");
                        String fromState = parts[0];
                        String symbol = parts[1];
                        String toState = parts[2];

                        dfa.transitions.computeIfAbsent(fromState, k -> new HashMap<>()).put(symbol, toState);
                    }

                    scanner.close();
                    JOptionPane.showMessageDialog(null, "DFA chargé avec succès !");
                    wordTextArea.setText("");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors du chargement du fichier DFA !");
                }
            }
        });

        checkButton.addActionListener(e -> {
            String word = wordTextArea.getText();
            if (acceptWord(dfa, word)) {
                resultLabel.setText("Résultat : Le mot est accepté par le DFA.");
            } else {
                resultLabel.setText("Résultat : Le mot est rejeté par le DFA.");
            }
        });

        return panel;
    }

    static boolean acceptWord(DFA dfa, String word) {
        String currentState = dfa.initialState;
        for (char symbol : word.toCharArray()) {
            String symbolString = Character.toString(symbol);
            if (!dfa.transitions.containsKey(currentState) || !dfa.transitions.get(currentState).containsKey(symbolString)) {
                return false;
            }
            currentState = dfa.transitions.get(currentState).get(symbolString);
        }
        return dfa.acceptingStates.contains(currentState);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DFA Accept Mot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); // Center the window
        frame.add(createDFAAcceptMotPanel());
        frame.setVisible(true);
    }
}
