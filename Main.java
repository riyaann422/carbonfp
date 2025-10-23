import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class CarbonFootprintTracker extends JFrame implements ActionListener {
    JTextField TransportField, ElectricityField, GarbageField;
    JButton CalculateButton, ClearButton;
    JTextArea ResultArea;
    JLabel TitleLabel, TransportLabel, ElectricityLabel, GarbageLabel;
    double TotalEmission = 0;
    
    CarbonFootprintTracker() {
        setTitle("Carbon Footprint Tracker");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        TitleLabel = new JLabel("CARBON FOOTPRINT CALCULATOR");
        TitleLabel.setBounds(50, 20, 400, 30);
        TitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(TitleLabel);
        
        TransportLabel = new JLabel("Transportation (km/day):");
        TransportLabel.setBounds(50, 70, 200, 25);
        add(TransportLabel);
        
        TransportField = new JTextField();
        TransportField.setBounds(250, 70, 150, 25);
        add(TransportField);
        
        ElectricityLabel = new JLabel("Electricity (kWh/day):");
        ElectricityLabel.setBounds(50, 110, 200, 25);
        add(ElectricityLabel);
        
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
        
        ClearButton = new JButton("Clear");
        ClearButton.setBounds(250, 200, 150, 35);
        ClearButton.addActionListener(this);
        add(ClearButton);
        
        
        ResultArea = new JTextArea();
        ResultArea.setBounds(50, 250, 370, 200);
        ResultArea.setEditable(false);
        ResultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        ResultArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(ResultArea);
        
        setSize(480, 520);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        
        if (clickedButton == ClearButton) {
            TransportField.setText("");
            ElectricityField.setText("");
            GarbageField.setText("");
            ResultArea.setText("");
            TotalEmission = 0;
        } else if (clickedButton == CalculateButton) {
            calculateFootprint();
        }
    }
    
    void calculateFootprint() {
        try {
            double transport = 0, electricity = 0, garbage = 0;
            
            
            if (!TransportField.getText().isEmpty()) {
                transport = Double.parseDouble(TransportField.getText());
            }
            if (!ElectricityField.getText().isEmpty()) {
                electricity = Double.parseDouble(ElectricityField.getText());
            }
            if (!GarbageField.getText().isEmpty()) {
                garbage = Double.parseDouble(GarbageField.getText());
            }
            
            double transportEmission = transport * 0.21;  
            double electricityEmission = electricity * 0.5;  
            double garbageEmission = garbage * 0.3;  
            
            TotalEmission = transportEmission + electricityEmission + garbageEmission;
            
            String result = "CARBON FOOTPRINT REPORT \n";
            result += "Transportation: " + formatNumber(transportEmission) + " kg CO2\n";
            result += "Electricity: " + formatNumber(electricityEmission) + " kg CO2\n";
            result += "Garbage: " + formatNumber(garbageEmission) + " kg CO2\n";
            result += "\n";
            result += "TOTAL DAILY: " + formatNumber(TotalEmission) + " kg CO2\n";
            result += "MONTHLY: " + formatNumber(TotalEmission * 30) + " kg CO2\n";
            result += "YEARLY: " + formatNumber(TotalEmission * 365) + " kg CO2\n\n";
            
            result += "SUGGESTIONS\n";
            if (TotalEmission > 10) {
                result += "BAD! Use public transport,reduce electricity usage and try recycling.\n";
            } else if (TotalEmission > 5) {
                result += "AVERAGE! Try to reduce more\n";
            } else {
                result += "GOOD!\n";
            }
            ResultArea.setText(result);
            
        } catch (NumberFormatException ex) {
            ResultArea.setText("Error: Enter valid number!");
        } catch (Exception ex) {
            ResultArea.setText("Error: " + ex.getMessage());
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

