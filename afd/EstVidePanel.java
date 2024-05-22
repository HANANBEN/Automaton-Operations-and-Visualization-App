package afd;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class EstVidePanel extends JPanel {
    public EstVidePanel() {
        setLayout(new BorderLayout());

        // Create a panel for the buttons and messages
        JPanel panel = new JPanel(new BorderLayout());

        // Create a button to open the file chooser
        JButton openButton = new JButton("Open File");
        panel.add(openButton, BorderLayout.NORTH);

        // Create a text area to display messages
        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the panel to this EstVidePanel
        add(panel, BorderLayout.CENTER);

        // Add action listener to the open button
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Input File");

                // Show the file chooser dialog and check if a file is selected
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String inputFile = fileChooser.getSelectedFile().getPath();
                    Set<Vertex<String>> initialStates = new HashSet<>();
                    Set<Vertex<String>> endStates = new HashSet<>();
                    List<String> alphabetList = new ArrayList<>(); // Renamed to avoid conflict

                    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                        String line;
                        Graph<String> graph = new Graph<>(true); // Assuming a directed graph

                        // Read the alphabet
                        if ((line = reader.readLine()) != null) {
                            alphabetList.addAll(Arrays.asList(line.trim().split("\\s+")));
                        }

                        // Read the states
                        List<Vertex<String>> states = new ArrayList<>();
                        if ((line = reader.readLine()) != null) {
                            for (String state : line.trim().split("\\s+")) {
                                states.add(new Vertex<>(state));
                            }
                        }

                        // Read the initial state
                        if ((line = reader.readLine()) != null) {
                            String[] initialStateArray = line.trim().split("\\s+");
                            for (String state : initialStateArray) {
                                initialStates.add(new Vertex<>(state));
                            }
                        }

                        // Read the final states
                        if ((line = reader.readLine()) != null) {
                            String[] finalStateArray = line.trim().split("\\s+");
                            for (String state : finalStateArray) {
                                endStates.add(new Vertex<>(state));
                            }
                        }

                        // Read the transitions
                        while ((line = reader.readLine()) != null) {
                            String[] parts = line.trim().split("\\s+");
                            if (parts.length >= 3) {
                                graph.addEdge(parts[0], parts[2]);
                            }
                        }

                        LangageReconnu cdg = new LangageReconnu();
                        messageArea.append("Alphabet: " + alphabetList + "\n");
                        messageArea.append("etats: " + states + "\n");
                        messageArea.append("l'etat initiale: " + initialStates + "\n");
                        messageArea.append("les etats finaux: " + endStates + "\n");
                        messageArea.append("existe des cyles dans l'automate: " + cdg.hasCycle(graph) + "\n");
                        if(cdg.hasCycle(graph)) {
                        	 messageArea.append("l'automate accepte le langage infini: "+ "\n");
                        }
                        else {
                        }
                        messageArea.append("Automate accepte le language vide?: " + cdg.acceptsEmptyLanguage(graph, initialStates, endStates) + "\n");
                    } catch (IOException ex) {
                        messageArea.append("Error reading file: " + ex.getMessage() + "\n");
                    }
                } else {
                    messageArea.append("No file selected.\n");
                }
            }
        });
    }
}
