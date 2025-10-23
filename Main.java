import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class CarbonFootprintTracker extends JFrame implements ActionListener {
    JTextField TransportField, ElectricityField, GarbageField;
    JButton calculateButton, clearButton;
    JTextArea resultArea;
    JLabel titleLabel, TransportLabel, ElectricityLabel, GarbageLabel;
    double totalEmission = 0;
    
    CarbonFootprintTracker() {
        setTitle("Carbon Footprint Tracker");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        titleLabel = new JLabel("CARBON FOOTPRINT CALCULATOR");
        titleLabel.setBounds(50, 20, 400, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);
        
        TransportLabel = new JLabel("Transportation (km/day):");
        TransportLabel.setBounds(50, 70, 200, 25);
        add(TransportLabel);
        
        TransportField = new JTextField();
        TransportField.setBounds(250, 70, 150, 25);
        add(transportField);
        
        ElectricityLabel = new JLabel("Electricity (kWh/day):");
        ElectricityLabel.setBounds(50, 110, 200, 25);
        add(electricityLabel);
        
        ElectricityField = new JTextField();
        ElectricityField.setBounds(250, 110, 150, 25);
        add(ElectricityField);
        
        GarbageLabel = new JLabel("Garbage (kg/day):");
        GarbageLabel.setBounds(50, 150, 200, 25);
        add(GarbageLabel);
        
        GarbageField = new JTextField();
        GarbageField.setBounds(250, 150, 150, 25);
        add(GarbageField);
        
        CalculateButton = new JButton("Calculate Footprint");
        CalculateButton.setBounds(50, 200, 180, 35);
        CalculateButton.addActionListener(this);
        add(CalculateButton);
        
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
            TransportField.setText("");
            ElectricityField.setText("");
            GarbageField.setText("");
            resultArea.setText("");
            totalEmission = 0;
        } else if (clickedButton == calculateButton) {
            calculateFootprint();
        }
    }
    
    void calculateFootprint() {
        try {
            double Transport = 0, Electricity = 0, Garbage = 0;
            
            
            if (!TransportField.getText().isEmpty()) {
                Transport = Double.parseDouble(TransportField.getText());
            }
            if (!ElectricityField.getText().isEmpty()) {
                Electricity = Double.parseDouble(ElectricityField.getText());
            }
            if (!GarbageField.getText().isEmpty()) {
                Garbage = Double.parseDouble(GarbageField.getText());
            }
            
            double TransportEmission = Transport * 0.21;  
            double ElectricityEmission = Electricity * 0.5;  
            double GarbageEmission = Garbage * 0.3;  
            
            TotalEmission = TransportEmission + ElectricityEmission + GarbageEmission;
            
            String result = "==== CARBON FOOTPRINT REPORT ====\n\n";
            result += "Transportation: " + formatNumber(TransportEmission) + " kg CO2\n";
            result += "Electricity: " + formatNumber(ElectricityEmission) + " kg CO2\n";
            result += "Garbage: " + formatNumber(GarbageEmission) + " kg CO2\n";
            result += "-------------------------\n";
            result += "TOTAL DAILY: " + formatNumber(TotalEmission) + " kg CO2\n";
            result += "MONTHLY: " + formatNumber(TotalEmission * 30) + " kg CO2\n";
            result += "YEARLY: " + formatNumber(TotalEmission * 365) + " kg CO2\n\n";
            
            result += "==== SUGGESTIONS ====\n";
            if (TotalEmission > 10) {
                result += "* Use public transport\n";
                result += "* Reduce electricity usage\n";
                result += "* Recycle waste properly\n";
            } else if (TotalEmission > 5) {
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
