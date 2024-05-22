package afd;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class AFDReader extends JPanel {

    private Map<String, Map<String, String>> transitions;
    private Map<String, Integer> statePositions;
    private Set<String> finalStates;
    private String initialState;

    public AFDReader() {
        finalStates = new HashSet<>();
        transitions = readAutomatonFromFile("C:\\Users\\HP\\eclipse-workspace\\AfdProject\\src\\main\\java\\afd\\auto2.txt");
        calculateStatePositions();

        // Set layout and preferred size for proper display
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Panel pour dessiner le graphe
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Dessiner les états et les transitions
                drawAutomaton(g2d);
            }
        };
        add(panel, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return this;
    }

    // Méthode pour lire l'automate à partir du fichier
    private Map<String, Map<String, String>> readAutomatonFromFile(String filename) {
        Map<String, Map<String, String>> transitions = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Ignorer les deux premières lignes (alphabet et états)
            reader.readLine();
            reader.readLine();
            String finalAndInitialLine = reader.readLine();
            String[] finalAndInitial = finalAndInitialLine.split(" ");
            initialState = reader.readLine();
            System.out.println("etat" + initialState);
            for (int i = 1; i < finalAndInitial.length; i++) {
                finalStates.add(finalAndInitial[i]);
            }

            // Lire les transitions
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String fromState = parts[0];
                    String symbol = parts[1];
                    String toState = parts[2];
                    Map<String, String> transitionMap = transitions.get(fromState);
                    if (transitionMap == null) {
                        transitionMap = new HashMap<>();
                        transitions.put(fromState, transitionMap);
                    }
                    transitionMap.put(symbol, toState);
                } else {
                    System.err.println("Ligne invalide : " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transitions;
    }

    // Méthode pour calculer les positions y des états
    private void calculateStatePositions() {
        statePositions = new HashMap<>();
        int availableHeight = 400 - 50; // Hauteur disponible (taille de la fenêtre - marge)
        int stateCount = transitions.size(); // Nombre total d'états
        int verticalSpacing = availableHeight / stateCount; // Espacement vertical entre chaque état
        int yPos = verticalSpacing; // Commencer à partir de la première position verticale
        for (String state : transitions.keySet()) {
            statePositions.put(state, yPos);
            yPos += verticalSpacing; // Augmenter la position y pour chaque état
        }
    }

    // Méthode pour dessiner l'automate
    private void drawAutomaton(Graphics2D g) {
        if (transitions.isEmpty()) {
            System.err.println("Erreur lors de la lecture de l'automate.");
            return;
        }

        // Dessiner les transitions
        for (Map.Entry<String, Map<String, String>> entry : transitions.entrySet()) {
            String fromState = entry.getKey();
            int x1 = 100 + Integer.parseInt(fromState.substring(1)) * 100;
            int y1 = statePositions.get(fromState);
            for (Map.Entry<String, String> transition : entry.getValue().entrySet()) {
                String symbol = transition.getKey();
                String toState = transition.getValue();
                int x3 = 100 + Integer.parseInt(toState.substring(1)) * 100;
                int y3 = statePositions.get(toState);
                drawTransition(g, x1, y1, x3, y3, symbol);
            }
        }

        // Dessiner les états
        for (Map.Entry<String, Integer> entry : statePositions.entrySet()) {
            String state = entry.getKey();
            int x = 100 + Integer.parseInt(state.substring(1)) * 100;
            int y = entry.getValue();
            boolean isFinal = finalStates.contains(state);
            drawState(g, x, y, state, isFinal);
        }

        // Dessiner la flèche de l'état initial
        int x1 = 50; // Coordonnée x de départ de la flèche
        int y1 = 200; // Coordonnée y de départ de la flèche
        int x2 = 100 - 25; // Coordonnée x de fin de la flèche (centre de l'état initial)
        int y2 = statePositions.get(initialState); // Coordonnée y de fin de la flèche (centre de l'état initial)
        drawArrow(g, x1, y1, x2, y2);
    }

    private void drawArrow(Graphics2D g, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        int size = 10;
        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(new int[]{x2, x2 - size, x2 - size, x2},
                new int[]{y2, y2 - size, y2 + size, y2},
                4);
    }

    // Méthode pour dessiner un état
    private void drawState(Graphics2D g, int x, int y, String label, boolean isFinal) {
        // Dessiner le cercle extérieur
        g.drawOval(x - 25, y - 25, 50, 50);
        // Si l'état est final, dessiner un cercle intérieur
        if (isFinal) {
            g.drawOval(x - 20, y - 20, 40, 40);
        }
        g.drawString(label, x - 5, y + 5);
    }

    // Méthode pour dessiner une transition avec une flèche
    private void drawTransition(Graphics2D g, int x1, int y1, int x2, int y2, String label) {
        // Dessiner la ligne
        g.drawLine(x1, y1, x2, y2);

        // Calculer l'angle de la flèche
        double angle = Math.atan2(y2 - y1, x2 - x1);
        // Définir la taille de la flèche
        int arrowSize = 1;
        // Réduire la longueur de la flèche
        int reducedLength = 10;
        // Dessiner la première pointe de la flèche
        g.fillPolygon(new int[]{x2, (int) (x2 - reducedLength * Math.cos(angle - Math.PI / 6)), (int) (x2 - reducedLength * Math.cos(angle + Math.PI / 6))},
                new int[]{y2, (int) (y2 + reducedLength * Math.sin(angle - Math.PI / 6)), (int) (y2 + reducedLength * Math.sin(angle + Math.PI / 6))},
                3);
        // Dessiner l'étiquette de la transition
        g.drawString(label, (x1 + x2) / 2, (y1 + y2) / 2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            //  @Override
            public void run() {
                AFDReader frame = new AFDReader();
                frame.setVisible(true);
            }
        });
    }
}
