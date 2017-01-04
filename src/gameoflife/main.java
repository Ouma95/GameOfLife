/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoflife;


import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author oumaima
 */
public class main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int nbIter =-1;//-1 = infini
        
        
        try {
            Gameoflife g = Gameoflife.fromFile(new File("test/ship.txt"));
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setBounds(30, 30, 800, 800);
            window.getContentPane().add(g);
            window.setVisible(true);
            window.addWindowListener(g);
            g.redraw();
            //g.drawZone();            
            while(nbIter !=0) {
                g.iterate();                
                g.redraw();
                Thread.sleep(100);
                nbIter--;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
     
}
