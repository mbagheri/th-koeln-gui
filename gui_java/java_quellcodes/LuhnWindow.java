/*
  @author Mahdi Bagheri
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

/*
Das Fenster, in dem die Luhn Berechnung stattfindet
 */
public class LuhnWindow extends JDialog {

    LuhnAlgorithm luhnAlgorithm;
    JPanel gridPanel;
    JPanel topPanel;
    JPanel middlePanel;
    JPanel bottomPanel;
    JPanel titlePanel;
    JTextField entry;
    JRadioButton calcCheckDigit;
    JRadioButton checkNumber;
    JButton luhnCalculateButton;
    JButton exitButton;
    JLabel solution;
    JLabel titleLabel;

    /*
    Konstruktor, in dem die einzelnen Elemente des Fenster erstellt werden zur Anzeige beim Aufruf
     */
    public LuhnWindow(){
        setTitle("Luhn-Formel-Test");
        setSize(500, 400);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        gridPanel = new JPanel(new GridLayout(4,1));
        titlePanel = new JPanel();
        topPanel = new JPanel();
        middlePanel = new JPanel();
        bottomPanel = new JPanel();

        titleLabel = new JLabel("Willkommen zum Luhn Rechner, geben Sie eine Zahl ein zum Starten");
        titlePanel.add(titleLabel);

        entry = new JTextField(15);
        topPanel.add(entry);

        calcCheckDigit = new JRadioButton("Prüfziffer berechnen");
        topPanel.add(calcCheckDigit);

        checkNumber = new JRadioButton("Zahl überprüfen");
        topPanel.add(checkNumber);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(calcCheckDigit);
        buttonGroup.add(checkNumber);

        solution = new JLabel();

        luhnCalculateButton = new JButton("Luhn-Formel");
        luhnCalculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformLuhn();
            }
        });
        middlePanel.add(luhnCalculateButton);

        exitButton = new JButton("Verlassen");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        middlePanel.add(exitButton);

        bottomPanel.add(solution);
        
        getRootPane().setDefaultButton(luhnCalculateButton);

        gridPanel.add(titlePanel);
        gridPanel.add(topPanel);
        gridPanel.add(middlePanel);
        gridPanel.add(bottomPanel);
        add(gridPanel);
    }

    /*
    Methode, welche die Luhn Berechnung ausführt.
    Die Methode erhält eine Zahl über ein Textfeld entgegen und
    überprüft welche Radio Button bzw. welche Berechnungsmethode ausgewählt wurde
    und wendet dann entsprechende Rechnung an.
     */
    public void PerformLuhn(){
        luhnAlgorithm = new LuhnAlgorithm();
        String labelValue = "";
        BigInteger typedNumber;

        //Überprüfung, dass sich eine Zahl im Textfeld befindet
        try{
            typedNumber = new BigInteger(entry.getText());
        }catch (Exception ex){
            //System.out.println(ex);
            if(entry.getText().isEmpty()){
                solution.setText("Bitte eine Zahl eingeben");
            }else{
                solution.setText("Bitte geben Sie nur Zahlen ein");
            }
            return;
        }

        //Überprüfung, dass ein Radio Button ausgewählt wurde
        if(!calcCheckDigit.isSelected() && !checkNumber.isSelected()){
            solution.setText("Bitte wählen Sie eine der Rechenoptionen aus");
        }

        //Ausführung der Prüfzifferberechnung, falls ausgewählt
        if(calcCheckDigit.isSelected()){
            labelValue = String.valueOf(luhnAlgorithm.calculateCheckDigit(typedNumber));
            solution.setText("Prüfziffer: " + labelValue);
        }

        //Ausführung, der Validierung der Zahl, falls ausgewählt
        if(checkNumber.isSelected()){
            labelValue = String.valueOf(luhnAlgorithm.checkNumber(typedNumber));
            int solutionValue = luhnAlgorithm.checkNumber(typedNumber);
            if (solutionValue == 0) {
                solution.setText("Luhn Prüfsumme: " + labelValue + ". Der Wert ist gültig");
            }else{
                solution.setText("Luhn Prüfsumme: " + labelValue + ". Der Wert ist ungültig");
            }

        }
    }

}
