import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class CarbonFootprintTracker extends JFrame implements ActionListener {
    JTextField transportField, electricityField, garbageField;
    JButton calculateButton, clearButton;
    JTextArea resultArea;
    JLabel titleLabel, transportLabel, electricityLabel, garbageLabel;
    double totalEmission = 0;
    
    CarbonFootprintTracker() {
        setTitle("Carbon Footprint Tracker");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        titleLabel = new JLabel("CARBON FOOTPRINT CALCULATOR");
        titleLabel.setBounds(50, 20, 400, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);
        
        transportLabel = new JLabel("Transportation (km/day):");
        transportLabel.setBounds(50, 70, 200, 25);
        add(transportLabel);
        
        transportField = new JTextField();
        transportField.setBounds(250, 70, 150, 25);
        add(transportField);
        
        electricityLabel = new JLabel("Electricity (kWh/day):");
        electricityLabel.setBounds(50, 110, 200, 25);
        add(electricityLabel);
        
        electricityField = new JTextField();
        electricityField.setBounds(250, 110, 150, 25);
        add(electricityField);
        
        garbageLabel = new JLabel("Garbage (kg/day):");
        garbageLabel.setBounds(50, 150, 200, 25);
        add(garbageLabel);
        
        garbageField = new JTextField();
        garbageField.setBounds(250, 150, 150, 25);
        add(garbageField);
        
        calculateButton = new JButton("Calculate Footprint");
        calculateButton.setBounds(50, 200, 180, 35);
        calculateButton.addActionListener(this);
        add(calculateButton);
        
        clearButton = new JButton("Clear");
        clearButton.setBounds(250, 200, 150, 35);
        clearButton.addActionListener(this);
        add(clearButton);
        
        
        resultArea = new JTextArea();
        resultArea.setBounds(50, 250, 370, 200);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(resultArea);
        
        setSize(480, 520);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        
        if (clickedButton == clearButton) {
            transportField.setText("");
            electricityField.setText("");
            garbageField.setText("");
            resultArea.setText("");
            totalEmission = 0;
        } else if (clickedButton == calculateButton) {
            calculateFootprint();
        }
    }
    
    void calculateFootprint() {
        try {
            double transport = 0, electricity = 0, garbage = 0;
            
            
            if (!transportField.getText().isEmpty()) {
                transport = Double.parseDouble(transportField.getText());
            }
            if (!electricityField.getText().isEmpty()) {
                electricity = Double.parseDouble(electricityField.getText());
            }
            if (!garbageField.getText().isEmpty()) {
                garbage = Double.parseDouble(garbageField.getText());
            }
            
            double transportEmission = transport * 0.21;  
            double electricityEmission = electricity * 0.5;  
            double garbageEmission = garbage * 0.3;  
            
            totalEmission = transportEmission + electricityEmission + garbageEmission;
            
            String result = "==== CARBON FOOTPRINT REPORT ====\n\n";
            result += "Transportation: " + formatNumber(transportEmission) + " kg CO2\n";
            result += "Electricity: " + formatNumber(electricityEmission) + " kg CO2\n";
            result += "Garbage: " + formatNumber(garbageEmission) + " kg CO2\n";
            result += "-------------------------\n";
            result += "TOTAL DAILY: " + formatNumber(totalEmission) + " kg CO2\n";
            result += "MONTHLY: " + formatNumber(totalEmission * 30) + " kg CO2\n";
            result += "YEARLY: " + formatNumber(totalEmission * 365) + " kg CO2\n\n";
            
            result += "==== SUGGESTIONS ====\n";
            if (totalEmission > 10) {
                result += "* Use public transport\n";
                result += "* Reduce electricity usage\n";
                result += "* Recycle waste properly\n";
            } else if (totalEmission > 5) {
                result += "* Good! Try to reduce more\n";
                result += "* Consider carpooling\n";
            } else {
                result += "* Excellent! Keep it up!\n";
            }
            
            resultArea.setText(result);
            
        } catch (NumberFormatException ex) {
            resultArea.setText("Error: Please enter valid numbers!");
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
        }
    }
    
    String formatNumber(double number) {
        if (number == (int) number) {
            return String.valueOf((int) number);
        } else {
            return String.format("%.2f", number);
        }
    }
    
    public static void main(String[] args) {
        new CarbonFootprintTracker();
    }
}
