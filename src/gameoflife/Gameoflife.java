package gameoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author oumaima
 */
public class Gameoflife  extends JComponent implements WindowListener{

    private static final int CELL_SIZE = 15;
    private static final String CELL_COLOR = "333333";
    
    private int[][] zone;
    private int nbCol = 0;
    private int nbLigne = 0;
    
    
    /**
     * @return the nbCol
     */
    public int getNbCol() {
        return nbCol;
    }

    /**
     * @param nbCol the nbCol to set
     */
    public void setNbCol(int nbCol) {
        this.nbCol = nbCol;
    }

    /**
     * @return the nbLigne
     */
    public int getNbLigne() {
        return nbLigne;
    }

    /**
     * @param nbLigne the nbLigne to set
     */
    public void setNbLigne(int nbLigne) {
        this.nbLigne = nbLigne;
    }
    
    public Gameoflife(){
    
    }
    
    public Gameoflife(int nbLigne,int nbCol){
        setNbCol(nbCol);
        setNbLigne(nbLigne);
        init();
    }
    
    public void init(){
        zone = new int[nbLigne][nbCol];
    }
     
    public void iterate(){
        //Pour chaque cellule, on calcule le nb de voisin vivant et on en déduit sont état...
        int[][] newZone = new int[nbLigne][nbCol];
        for(int i = 0; i < getNbLigne(); i++){
            for(int j = 0; j < getNbCol(); j++){
                newZone[i][j] = calculerNouvelEtat(zone[i][i],getNbVoisinVivant(i,j));
            }
        }
        zone = newZone;

    }
    
    public int getNbVoisinVivant(int ligne, int col){
        
        int nbVoisin = 0;
        nbVoisin += isCellVivant(ligne-1, col-1);
        nbVoisin += isCellVivant(ligne-1, col);
        nbVoisin += isCellVivant(ligne-1, col+1);
        
        //nbVoisin += isCellVivant(ligne, col);
        //La cellule : nbVoisin += isCellVivant(ligne, col);
        nbVoisin += isCellVivant(ligne, col+1);
         nbVoisin += isCellVivant(ligne, col-1);
        
        nbVoisin += isCellVivant(ligne+1, col-1);
        nbVoisin += isCellVivant(ligne+1, col);
        nbVoisin += isCellVivant(ligne+1, col+1);

        return nbVoisin;
    }
    
    public int calculerNouvelEtat(int etatCourant,int nbVoisin){
        int next = 0;
        if(nbVoisin < 2 && nbVoisin > 3){
            next = 0;
        }else if(nbVoisin == 2){
            next = etatCourant;
        }else if(nbVoisin == 3){
            next = 1;
        }
        return next;
    }

    void setVivant(int ligne, int col) {
        zone[ligne][col] = 1;
    }
    
    int isCellVivant(int ligne, int col){
        
        //ligne precedente
        if(ligne >= 0 && ligne < nbLigne && col >= 0 && col < nbCol){
            return zone[ligne][col] ;
        }
        return 0;
    }
    
    public void paint(Graphics g) {  
        
        //La zone
        g.setColor(Color.decode(CELL_COLOR));
        g.fillRect(0, 0, getNbCol()*CELL_SIZE, getNbLigne()*CELL_SIZE);

        //Les cellules
        for(int i = 0; i < getNbLigne(); i++){
            for(int j = 0; j < getNbCol(); j++){                
                if(isCellVivant(i, j)==1){
                    g.setColor(Color.WHITE);
                    g.fillRect(j*CELL_SIZE, i*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }

            }
        }
    }
    
    public void redraw() {
        paint(getGraphics());
    }
    
    public static Gameoflife fromFile(File f) throws FileNotFoundException, IOException{
        Gameoflife gof = new Gameoflife();
             BufferedReader buff = new BufferedReader(new FileReader(f));
             
             //Lecture premiere ligne
             String line0 = buff.readLine();
             String[] lignes; 
                gof.setNbCol(Integer.parseInt(line0.split("[*]")[0]));
                gof.setNbLigne(Integer.parseInt(line0.split("[*]")[1]));
                lignes = new String[gof.getNbLigne()];
                String tmp;
                int i = 0;
                while((tmp = buff.readLine())!=null){
                    lignes[i] = tmp;
                    i++;
                }
             
             gof.init();
             
             //init lines
             for(int ligne = 0; ligne < gof.getNbLigne();ligne++){        
                 for(int col = 0; col < gof.getNbCol(); col++){
                    if('o' == lignes[ligne].charAt(col)){
                        gof.setVivant(ligne,col);
                    }
                 }
             }
             
         
        
        return gof;
    }
    
    public static Gameoflife toFile(Gameoflife gof, File f) throws FileNotFoundException, IOException{
        
        
        
        StringBuilder sb = new StringBuilder();
        
        //Ecriture premiere ligne
        sb.append(gof.getNbCol()+"*"+gof.getNbLigne()+"\n");
        
             //init lines
        for(int ligne = 0; ligne < gof.getNbLigne();ligne++){        
            for(int col = 0; col < gof.getNbCol(); col++){
                if(gof.isCellVivant(ligne,col)==1){
                    sb.append("o");
                }else{
                    sb.append("x");
                }
            }
            sb.append("\n");
        }
        
        //Write file
        FileWriter fw = new FileWriter(f); 
        fw.write(sb.toString());
        fw.close();
        
        return gof;
    }
    
    
   

    @Override
    public void windowOpened(WindowEvent e) {
        //Nothing to do
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            toFile(this, new File("test/out.txt"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //Nothing to do
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //Nothing to do
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //Nothing to do
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //Nothing to do
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //Nothing to do
    }
    
}
