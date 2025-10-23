package carbonfp;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Main extends JFrame implements ActionListener {
    JTextField TransportField, ElectricityField, GarbageField, NameField;
    JButton CalculateButton, ClearButton, SaveButton, HistoryButton;
    JTextArea ResultArea;
    JComboBox<String> TransportModeBox;
    double totalEmission;
    String fileName = "CarbonFP_Records.txt";

    public Main() {
        setTitle("Carbon Footprint Tracker");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Carbon Footprint Calculator");
        titleLabel.setBounds(100, 20, 250, 25);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);

        JLabel nameLabel = new JLabel("Your Name:");
        nameLabel.setBounds(40, 60, 150, 25);
        add(nameLabel);

        NameField = new JTextField();
        NameField.setBounds(220, 60, 150, 25);
        add(NameField);

        JLabel modeLabel = new JLabel("Transport Mode:");
        modeLabel.setBounds(40, 100, 150, 25);
        add(modeLabel);

        String[] modes = {"Car", "Bus", "Bike", "Walk"};
        TransportModeBox = new JComboBox<>(modes);
        TransportModeBox.setBounds(220, 100, 150, 25);
        add(TransportModeBox);

        JLabel transportLabel = new JLabel("Transportation (km/day):");
        transportLabel.setBounds(40, 140, 180, 25);
        add(transportLabel);

        TransportField = new JTextField();
        TransportField.setBounds(220, 140, 150, 25);
        add(TransportField);

        JLabel electricityLabel = new JLabel("Electricity (kWh/day):");
        electricityLabel.setBounds(40, 180, 180, 25);
        add(electricityLabel);

        ElectricityField = new JTextField();
        ElectricityField.setBounds(220, 180, 150, 25);
        add(ElectricityField);

        JLabel garbageLabel = new JLabel("Garbage (kg/day):");
        garbageLabel.setBounds(40, 220, 180, 25);
        add(garbageLabel);

        GarbageField = new JTextField();
        GarbageField.setBounds(220, 220, 150, 25);
        add(GarbageField);

        CalculateButton = new JButton("Calculate");
        CalculateButton.setBounds(40, 270, 90, 30);
        CalculateButton.addActionListener(this);
        add(CalculateButton);

        ClearButton = new JButton("Clear");
        ClearButton.setBounds(140, 270, 90, 30);
        ClearButton.addActionListener(this);
        add(ClearButton);

        SaveButton = new JButton("Save");
        SaveButton.setBounds(240, 270, 90, 30);
        SaveButton.addActionListener(this);
        add(SaveButton);

        HistoryButton = new JButton("History");
        HistoryButton.setBounds(340, 270, 90, 30);
        HistoryButton.addActionListener(this);
        add(HistoryButton);

        ResultArea = new JTextArea();
        ResultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ResultArea);
        scrollPane.setBounds(40, 320, 360, 220);
        add(scrollPane);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ClearButton) {
            TransportField.setText("");
            ElectricityField.setText("");
            GarbageField.setText("");
            NameField.setText("");
            ResultArea.setText("");
        } else if (e.getSource() == CalculateButton) {
            calculateFootprint();
        } else if (e.getSource() == SaveButton) {
            saveRecord();
        } else if (e.getSource() == HistoryButton) {
            showHistory();
        }
    }
    void calculateFootprint() {
        try {
            double transport = 0, electricity = 0, garbage = 0;

            if (!TransportField.getText().isEmpty())
                transport = Double.parseDouble(TransportField.getText());

            if (!ElectricityField.getText().isEmpty())
                electricity = Double.parseDouble(ElectricityField.getText());

            if (!GarbageField.getText().isEmpty())
                garbage = Double.parseDouble(GarbageField.getText());

            String mode = (String) TransportModeBox.getSelectedItem();
            double transportFactor = 0.21;
            
            if (mode.equals("Bus")) {
                transportFactor = 0.089;
            } else if (mode.equals("Bike") || mode.equals("Walk")) {
                transportFactor = 0.0;
            }

            double transportEmission = transport * transportFactor;
            double electricityEmission = electricity * 0.5;
            double garbageEmission = garbage * 0.3;

            totalEmission = transportEmission + electricityEmission + garbageEmission;

            String result = "Daily Carbon Footprint:\n";
            result += "Transport Mode: " + mode + "\n";
            result += "Transportation: " + transportEmission + " kg CO2\n";
            result += "Electricity: " + electricityEmission + " kg CO2\n";
            result += "Garbage: " + garbageEmission + " kg CO2\n";
            result += "Total: " + totalEmission + " kg CO2/day\n\n";

            result += "Tips:";
            if (totalEmission < 5) {
                result += "Good.\n";
            } else if (totalEmission < 10) {
                result += "Average, but can improve.\n";
            } else {
                result += "Bad, improve.\n";
            }

            if (transportEmission > 2) {
                result += "Use public transport\n";
            }
            if (electricityEmission > 5) {
                result += "Reduce electricity usage\n";
            }

            ResultArea.setText(result);

        } catch (NumberFormatException ex) {
            ResultArea.setText("Please enter valid numbers!");
        }
    }
//ith to save the records
    void saveRecord() {
        try {
            String name = NameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter your name!");
                return;
            }

            FileWriter fw = new FileWriter(fileName, true); // open file in append mode
            String mode = (String) TransportModeBox.getSelectedItem();

            fw.write(name + ", " + mode + ", " +
                     TransportField.getText() + " km, " +
                     ElectricityField.getText() + " kWh, " +
                     GarbageField.getText() + " kg, " +
                     totalEmission + " kg CO2\n");

            fw.close();
            JOptionPane.showMessageDialog(this, "Record saved!");
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while saving!");
        }
    }
//to view history, directly saved to txt file
    void showHistory() {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "No history found!");
                return;
            }
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line, records = "";
            while ((line = br.readLine()) != null) {
                records += line + "\n";
            }

            br.close();
            fr.close();
            JTextArea area = new JTextArea(records);
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);

            JOptionPane.showMessageDialog(this, scroll, "History", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error while reading history!");
        }
    }


    public static void main(String[] args) {
        new Main();
    }
}
