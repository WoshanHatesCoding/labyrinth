/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package labirithm;

import javax.swing.*;

public class Labirithm {
    private static JFrame frame;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        frame = new JFrame("Labirithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        toMainMenu();

    }

    public static void toMainMenu(){
        frame.getContentPane().removeAll();
        frame.add(new MainMenu());
        frame.repaint();
        frame.setVisible(true);
    }

    public static void toGame(){
        frame.getContentPane().removeAll();
        frame.add(new GameGUI());
        frame.repaint();
        frame.setVisible(true);
    }
    
    public static void toLeader(){
        frame.getContentPane().removeAll();
        frame.add(new Leader());
        frame.repaint();
        frame.setVisible(true);
    }

}
