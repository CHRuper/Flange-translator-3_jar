import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static JFrame frame;
    public static JFrame myFrame;

    public static void main(String[] args) {

//====================LABEL=======================
        JLabel labelExample = new JLabel("<html>Example: <br/>03012100BD003543<br/>'BCMP|pid=|id=061620DUEA|process=PRESS|station=FPCMPRESS01_NEW|model=28334467|status=PASS'</html>");
//===================TEXTAREA=====================
        JTextArea leftTextArea = new JTextArea();
        JTextArea rightTextArea = new JTextArea();

        JTextArea newTextArea = new JTextArea();
        newTextArea.setEditable(false);
//===================SCROLLPANE===================
        JScrollPane leftScrollPane = new JScrollPane(leftTextArea);
        JScrollPane rightScrollPane = new JScrollPane(rightTextArea);
        JScrollPane scrollPane = new JScrollPane(newTextArea);

        leftScrollPane.setSize(300, 450);
        rightScrollPane.setSize(300, 450);

//===================COMBOBOX====================
        String[] portStrings = {"*select port*", "FPCMPRESS01_NEW [24698]", "test [24099]", "FPCMPRESS01_NEW_Roman [24679]","FA5_ASSY1 [24445]"};

        JComboBox comboBox = new JComboBox(portStrings);
        comboBox.setSelectedIndex(0);
        comboBox.setEditable(true);
//====================BUTTON=====================
        JButton button = new JButton("CONVERT");
        button.setSize(50, 100);

        JButton button2 = new JButton("COPY");
        button.setSize(50, 100);

        JButton button3 = new JButton("CLEAR");
        button.setSize(30, 70);

        JButton button4 = new JButton("SEND TELNET");
        button.setSize(20, 40);

        JRadioButton radioButton = new JRadioButton("SN-Path");
        JRadioButton radioButton2 = new JRadioButton("Path-SN");
        JRadioButton radioButton3 = new JRadioButton("not confirm");

        radioButton.setSelected(true);
        radioButton3.setSelected(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton);
        buttonGroup.add(radioButton2);

        radioButton.setBackground(Color.lightGray);
        radioButton2.setBackground(Color.lightGray);

        radioButton.addActionListener(e -> labelExample.setText("<html>Example: <br/>03012100BD003543<br/>'BCMP|pid=|id=061620DUEA|process=PRESS|station=FPCMPRESS01_NEW|model=28334467|status=PASS'</html>"));
        radioButton2.addActionListener(e -> labelExample.setText("<html>Example: <br/>'BCMP|pid=|id=061620C9D2|process=PRESS|station=FPCMPRESS01_NEW|model=28334467|status=PASS'<br/>03012100BD002KZD</html>"));
        radioButton3.addActionListener(e -> radioButton3.setText(radioButton3.isSelected() ? "confirm" : "not confirm"));

        button.addActionListener(e -> {
            rightTextArea.setText("");
            List<String> list = new ArrayList(Arrays.asList(leftTextArea.getText().split("\\s+")));
            if (radioButton.isSelected()) {
                try {
                    LOGIC.TextReBuilder(list);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                if (radioButton2.isSelected()) {
                    try {
                        LOGIC.TextReBuilder2(list);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            try {
                LOGIC.ListToTextArea(list, rightTextArea);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        button2.addActionListener(e -> LOGIC.CopyToClipboard(rightTextArea.getText()));

        button3.addActionListener(e -> {
            leftTextArea.setText("");
            rightTextArea.setText("");
        });


        button4.addActionListener(e -> {
            if (radioButton3.isSelected() && comboBox.getSelectedIndex() != 0) {
                newTextArea.setText("");
                myFrame.setVisible(true);

                try {
                    TelnetSender.telnet(LOGIC.portFromName((String) Objects.requireNonNull(comboBox.getSelectedItem())), rightTextArea.getText(), newTextArea);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

//====================PANELS=====================
        JPanel leftPanel = new JPanel();
        JPanel centralPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel centralSubPanel1 = new JPanel();
        JPanel centralSubPanel2 = new JPanel();
        JPanel subPanel = new JPanel();
        JPanel subPanel2 = new JPanel();

        JPanel newPanel = new JPanel();
        newPanel.setLayout(new GridLayout());
        newPanel.add(scrollPane);

        leftPanel.setBackground(Color.lightGray);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.setLayout(new GridLayout());

        centralPanel.setBackground(Color.lightGray);
        centralPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        centralPanel.setMaximumSize(new Dimension(750, 100));
        centralPanel.setLayout(new GridLayout(2, 1));

        rightPanel.setBackground(Color.lightGray);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setLayout(new GridLayout());
        rightPanel.setBackground(Color.lightGray);

        subPanel.setLayout(new GridLayout(2, 1));
        subPanel2.setLayout(new GridLayout(2, 1));

        leftPanel.add(leftScrollPane);
        rightPanel.add(rightScrollPane);

        centralPanel.add(centralSubPanel1);
        centralPanel.add(centralSubPanel2);
        centralSubPanel1.add(subPanel);
        centralSubPanel1.add(button);
        centralSubPanel1.add(button2);
        centralSubPanel1.add(button3);
        centralSubPanel1.add(subPanel2);
        centralSubPanel1.add(button4);
        centralSubPanel2.add(labelExample);

        subPanel.add(radioButton);
        subPanel.add(radioButton2);

        //subPanel2.add(labelPort);
        subPanel2.add(comboBox);
        subPanel2.add(radioButton3);
//=========================FRAME========================
        frame = new JFrame("Converter");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(3, 1));
        frame.add(leftPanel);
        frame.add(centralPanel);
        frame.add(rightPanel);
        frame.validate();
        frame.repaint();

        myFrame = new JFrame("Telnet response");
        myFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        myFrame.setSize(700, 250);
        myFrame.setVisible(false);
        myFrame.setLayout(new GridLayout());
        myFrame.add(newPanel);
        myFrame.validate();
        myFrame.repaint();
    }
}
