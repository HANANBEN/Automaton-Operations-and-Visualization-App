package afd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnionInterface extends JPanel {
    private static List<String> automate1Lines;
    private static List<String> automate2Lines;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textAreaUnion;

    public UnionInterface() {
        setLayout(new BorderLayout());

        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        JScrollPane scrollPane1 = new JScrollPane(textArea1);

        textArea2 = new JTextArea();
        textArea2.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);

        textAreaUnion = new JTextArea();
        textAreaUnion.setEditable(false);
        JScrollPane scrollPaneUnion = new JScrollPane(textAreaUnion);

        JButton openButton1 = new JButton("Open Automate 1 File");
        JButton openButton2 = new JButton("Open Automate 2 File");
        JButton unionButton = new JButton("Calculate Union");

        ActionListener fileChooserListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        List<String> lines = readFile(selectedFile);
                        if (e.getSource() == openButton1) {
                            automate1Lines = lines;
                            textArea1.setText("Automate 1 loaded:\n" + String.join("\n", lines));
                        } else {
                            automate2Lines = lines;
                            textArea2.setText("Automate 2 loaded:\n" + String.join("\n", lines));
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        if (e.getSource() == openButton1) {
                            textArea1.setText("Failed to load Automate 1");
                        } else {
                            textArea2.setText("Failed to load Automate 2");
                        }
                    }
                }
            }
        };

        openButton1.addActionListener(fileChooserListener);
        openButton2.addActionListener(fileChooserListener);

        unionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (automate1Lines != null && automate2Lines != null) {
                    AFD automate1 = AFD.parseAutomate(automate1Lines);
                    AFD automate2 = AFD.parseAutomate(automate2Lines);
                    Union unionCalculator = new Union();
                    AFD unionAutomate = unionCalculator.unionAutomate(automate1, automate2);
                    textAreaUnion.setText("Union Automate:\n" + unionAutomate.toString());
                } else {
                    textAreaUnion.setText("Please load both automates before calculating the union.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton1);
        buttonPanel.add(openButton2);
        buttonPanel.add(unionButton);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(scrollPane1);
        splitPane.setRightComponent(scrollPane2);

        add(buttonPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(scrollPaneUnion, BorderLayout.SOUTH);
    }

    private static List<String> readFile(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }
}
