package afd;

import java.util.*;
import java.util.List;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class afdcomple {

    // Class to represent a transition
    static class Transition {
        int nextState;
        char symbol;

        Transition(int nextState, char symbol) {
            this.nextState = nextState;
            this.symbol = symbol;
        }
    }

    // Class to represent a state of the automaton
    static class State {
        int stateID;
        boolean isFinal;
        List<Transition> transitions;

        State(int stateID, boolean isFinal) {
            this.stateID = stateID;
            this.isFinal = isFinal;
            this.transitions = new ArrayList<>();
        }
    }

    // Method to get the complement of the AFD
    static List<State> getComplement(List<State> originalAFD) {
        List<State> complementAFD = new ArrayList<>();

        // Copy the original states
        for (State state : originalAFD) {
            complementAFD.add(new State(state.stateID, !state.isFinal));
        }

        // Copy the transitions
        for (int i = 0; i < originalAFD.size(); i++) {
            State originalState = originalAFD.get(i);
            State complementState = complementAFD.get(i);

            for (Transition transition : originalState.transitions) {
                complementState.transitions.add(new Transition(transition.nextState, transition.symbol));
            }
        }

        return complementAFD;
    }

    // Method to read an AFD from a text file
    static List<State> readAFDFromFile(File file) throws IOException {
        List<State> afd = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("state")) {
                String[] parts = line.split(" ");
                int stateID = Integer.parseInt(parts[1]);
                boolean isFinal = Boolean.parseBoolean(parts[2]);
                afd.add(new State(stateID, isFinal));
            } else if (line.startsWith("transition")) {
                String[] parts = line.split(" ");
                int fromState = Integer.parseInt(parts[1]);
                char symbol = parts[2].charAt(0);
                int toState = Integer.parseInt(parts[3]);
                afd.get(fromState).transitions.add(new Transition(toState, symbol));
            }
        }
        reader.close();
        return afd;
    }

    // Method to display the AFD
    static String displayAFD(List<State> afd) {
        StringBuilder result = new StringBuilder();
        for (State state : afd) {
            result.append("State ").append(state.stateID).append(" isFinal: ").append(state.isFinal).append(", Transitions: ");
            for (Transition transition : state.transitions) {
                result.append("(").append(transition.symbol).append("->").append(transition.nextState).append(") ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    // Method to create the complement panel for the interface
    public static JPanel createComplementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton openButton = new JButton("Open AFD File");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        List<State> originalAFD = readAFDFromFile(selectedFile);
                        List<State> complementAFD = getComplement(originalAFD);
                        textArea.setText("Original AFD:\n" + displayAFD(originalAFD) + "\nComplement AFD:\n" + displayAFD(complementAFD));
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(panel, "Error reading file: " + ex.getMessage());
                    }
                }
            }
        });

        panel.add(openButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AFD Complement");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel complementPanel = createComplementPanel();
        frame.add(complementPanel);
        frame.setVisible(true);
    }
}
