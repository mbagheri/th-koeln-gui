/*
  @author Mahdi Bagheri
 */

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Klasse MainWindow, in der das Hauptfenster beim Programmstart erstellt wird
 */
public class MainWindow extends JFrame{

    /*
    Der Klassenkonstruktor, in dem das Menü erstellt wird mit dem Fenstertitel und die Fenstergröße
     */
    public MainWindow(){
        setTitle("Luhn Formel");
        setSize(500,400);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu dateiMenu = new JMenu("Datei");
        JMenu luhnMenu = new JMenu("Luhn-Formel-Test");

        JMenuItem beenden = new JMenuItem("Beenden");

        menuBar.add(dateiMenu);
        menuBar.add(luhnMenu);
        dateiMenu.add(beenden);

        beenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        luhnMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                LuhnWindow luhnWindow = new LuhnWindow();
                luhnWindow.setVisible(true);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        setJMenuBar(menuBar);
    }
}
