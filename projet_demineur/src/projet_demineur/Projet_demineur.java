/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projet_demineur;

import javax.swing.SwingUtilities;

/**
 *
 * @author yohan
 */
public class Projet_demineur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         SwingUtilities.invokeLater(() -> {
            DemineurGUI game = new DemineurGUI(11, 11, 10); // Crée une instance de DemineurGUI avec des paramètres
            game.setVisible(true); // Rendre la fenêtre visible
        });
    }
}  
    