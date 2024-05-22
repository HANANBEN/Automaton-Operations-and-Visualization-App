package afd;

import javax.swing.*;
import java.awt.*;

public class Navigation extends JFrame {

    private static final long serialVersionUID = 1L;

    // Define the buttons
    private JButton lire_Afd = new JButton("Lecture de AFD");
    private JButton mot_Accepter = new JButton("MOT SAISIE");
    private JButton estVideButton = new JButton("Est Vide");
    private JButton intersectionButton = new JButton("Intersection");
    private JButton differenceButton = new JButton("Difference");
    private JButton unionButton = new JButton("Union"); // New Union button
    private JButton complementButton = new JButton("Complémentaire"); // New Complement button
    private JButton bmcButton = new JButton("BMC"); // New BMC button

    private JPanel afdPanel;

    public Navigation() {
        // Set the title of the JFrame
        setTitle("Navigation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the navigation panel
        JPanel sideMenu = createSideMenu();

        // Create the AFDReader panel
        afdPanel = new JPanel();
        afdPanel.setLayout(new BorderLayout());

        // Create a split pane to separate menu and content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sideMenu, afdPanel);
        splitPane.setDividerLocation(200); // Set initial divider location
        splitPane.setResizeWeight(0.2); // Set the resizing behavior

        // Add the split pane to the frame
        add(splitPane, BorderLayout.CENTER);

        // Set the size of the JFrame
        setSize(800, 600);

        // Make the frame visible
        setVisible(true);

        // Add action listeners to the buttons
        lire_Afd.addActionListener(e -> showAFDReaderPanel());
        mot_Accepter.addActionListener(e -> showDFAAcceptMotPanel());
        estVideButton.addActionListener(e -> showEstVidePanel());
        intersectionButton.addActionListener(e -> showIntersectionPanel());
        differenceButton.addActionListener(e -> showDifferencePanel());
        unionButton.addActionListener(e -> showUnionPanel()); // Add action listener for Union button
        complementButton.addActionListener(e -> showComplementPanel()); // Add action listener for Complement button
        bmcButton.addActionListener(e -> showBMCPanel()); // Add action listener for BMC button
    }

    private JPanel createSideMenu() {
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        // Set the size and color of the buttons
        Dimension buttonSize = new Dimension(200, 40); // Uniform size for buttons
        lire_Afd.setPreferredSize(buttonSize);
        lire_Afd.setMaximumSize(buttonSize);
        lire_Afd.setBackground(new Color(173, 216, 230)); // Light Blue

        mot_Accepter.setPreferredSize(buttonSize);
        mot_Accepter.setMaximumSize(buttonSize);
        mot_Accepter.setBackground(new Color(144, 238, 144)); // Light Green

        estVideButton.setPreferredSize(buttonSize);
        estVideButton.setMaximumSize(buttonSize);
        estVideButton.setBackground(new Color(255, 255, 153)); // Light Yellow

        intersectionButton.setPreferredSize(buttonSize);
        intersectionButton.setMaximumSize(buttonSize);
        intersectionButton.setBackground(new Color(255, 192, 203)); // Light Pink

        differenceButton.setPreferredSize(buttonSize);
        differenceButton.setMaximumSize(buttonSize);
        differenceButton.setBackground(new Color(255, 160, 122)); // Light Coral

        unionButton.setPreferredSize(buttonSize); // Set size and color for Union button
        unionButton.setMaximumSize(buttonSize);
        unionButton.setBackground(new Color(135, 206, 250)); // Light Sky Blue

        complementButton.setPreferredSize(buttonSize); // Set size and color for Complement button
        complementButton.setMaximumSize(buttonSize);
        complementButton.setBackground(new Color(221, 160, 221)); // Plum

        bmcButton.setPreferredSize(buttonSize); // Set size and color for BMC button
        bmcButton.setMaximumSize(buttonSize);
        bmcButton.setBackground(new Color(255, 182, 193)); // Light Pink

        // Add the buttons to the panel
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(lire_Afd);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(mot_Accepter);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(estVideButton);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(intersectionButton);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(differenceButton);
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(unionButton); // Add Union button to the side menu
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(complementButton); // Add Complement button to the side menu
        sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        sideMenu.add(bmcButton); // Add BMC button to the side menu

        return sideMenu;
    }

    private void showAFDReaderPanel() {
        // Remove previous components from the AFD panel
        afdPanel.removeAll();
        afdPanel.revalidate();
        afdPanel.repaint();

        // Create a new AFDReader panel
        AFDReader afdReader = new AFDReader();
        afdPanel.add(afdReader, BorderLayout.CENTER);
        afdPanel.revalidate();
        afdPanel.repaint();
    }

    private void showDFAAcceptMotPanel() {
        // Remove previous components from the AFD panel
        afdPanel.removeAll();
        afdPanel.revalidate();
        afdPanel.repaint();

        // Create a new DFAacceptMot panel
        JPanel dfaAcceptPanel = DFAacceptMot.createDFAAcceptMotPanel();
        afdPanel.add(dfaAcceptPanel, BorderLayout.CENTER);
        afdPanel.revalidate();
        afdPanel.repaint();
    }

    private void showEstVidePanel() {
        // Remove previous components from the AFD panel
        afdPanel.removeAll();
        afdPanel.revalidate();
        afdPanel.repaint();

        // Create a new EstVidePanel
        EstVidePanel estVidePanel = new EstVidePanel();
        afdPanel.add(estVidePanel, BorderLayout.CENTER);
        afdPanel.revalidate();
        afdPanel.repaint();
    }

    private void showIntersectionPanel() {
        // Remove previous components from the AFD panel
        afdPanel.removeAll();
        afdPanel.revalidate();
        afdPanel.repaint();

        // Create a new IntersectionInterface panel
        IntersectionInterface intersectionInterface = new IntersectionInterface();
        afdPanel.add(intersectionInterface, BorderLayout.CENTER);
        afdPanel.revalidate();
        afdPanel.repaint();
    }

    private void showDifferencePanel() {
        // Remove previous components from the AFD panel
        afdPanel.removeAll();
        afdPanel.revalidate();
        afdPanel.repaint();

        // Create a new DifferenceInterface panel
        DifferenceInterface differenceInterface = new DifferenceInterface();
        afdPanel.add(differenceInterface, BorderLayout.CENTER);
        afdPanel.revalidate();
        afdPanel.repaint();
    }

    private void showUnionPanel() {
        // Remove previous components from the AFD panel
        afdPanel.removeAll();
        afdPanel.revalidate();
        afdPanel.repaint();

        // Create a new UnionInterface panel
        UnionInterface unionInterface = new UnionInterface();
        afdPanel.add(unionInterface, BorderLayout.CENTER);
        afdPanel.revalidate();
        afdPanel.repaint();
    }

    private void showComplementPanel() {
        // Remove previous components from the AFD panel
        afdPanel.removeAll();
        afdPanel.revalidate();
        afdPanel.repaint();

        // Create a new ComplementPanel
        JPanel complementPanel = afdcomple.createComplementPanel();
        afdPanel.add(complementPanel, BorderLayout.CENTER);
        afdPanel.revalidate();
        afdPanel.repaint();
    }

    private void showBMCPanel() {
        // Remove previous components from the AFD panel
        afdPanel.removeAll();
        afdPanel.revalidate();
        afdPanel.repaint();

        // Create a new BMCInterface panel
        BMCInterface bmcInterface = new BMCInterface();
        afdPanel.add(bmcInterface.getContentPane(), BorderLayout.CENTER);
        afdPanel.revalidate();
        afdPanel.repaint();
    }

    public static void main(String[] args) {
        // Create an instance of the Navigation class to display the frame
        SwingUtilities.invokeLater(() -> new Navigation());
    }
}
