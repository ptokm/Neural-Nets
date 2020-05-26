package simple_Perceptron;

import java.awt.Color;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuFrame extends JFrame{
    private final MenuBar bar;
    private final Menu file,education;
    private final MenuItem itemFile[],perceptron[];
    private static JLabel label;
    private boolean patternsFlag, targetsFlag;
    private final Perceptron p = new Perceptron();
    
    MenuFrame(String title){
        super(title);
        
        this.targetsFlag = false;
        this.patternsFlag = false;
        
        setSize(400,400);
        setResizable(false);
        
        setLayout(new FlowLayout());
        
        itemFile = new MenuItem[2];
        itemFile[0] = new MenuItem("Load Patterns");
        itemFile[1] = new MenuItem("Load Targets");
                
        file = new Menu("File");
        for (short i = 0; i < itemFile.length; i++){
            file.add(itemFile[i]);
        }
        
        perceptron = new MenuItem[1];       
        perceptron[0] = new MenuItem("Train");
        
        education = new Menu("Perceptron");
        education.add(perceptron[0]);
        
        bar = new MenuBar();
        bar.add(file);
        bar.add(education);
        setMenuBar(bar);
        
        this.getContentPane().setBackground(Color.CYAN);
    
        label = new JLabel("<html><h3 align = 'center'>Perceptron Algorithm!</h3><ol><li> File -- Load Patterns</li><li>File -- Loat Targets</li><li>Perceptron -- <i>Train</i></li></ol></html>");
        this.add(label);
    }
    
    //Graphic Interface to choose a file from user's system to load patterns data
    public void loadPatternFile(){
        if (patternsFlag){
            MenuFrame.setTextLabel("You already load Patterns!");          
        }
        else{
            JFileChooser chooser=new JFileChooser();
            int returnVal = chooser.showOpenDialog(MenuFrame.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
                String filename=chooser.getSelectedFile().getAbsolutePath();
                
                ExecutorService executor = Executors.newFixedThreadPool(Application.getThreadsCounter());
                executor.submit(() -> {});
                
                Future <Integer> futureTask;
                futureTask = (Future <Integer>) executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList <ArrayList <Double> > list = new ArrayList <>();
                        try {
                            FileReader file = new FileReader(filename);
                            try (Scanner in = new Scanner(file)) {
                                patternsFlag = true;
                                while(in.hasNextLine())
                                {
                                    String line=in.nextLine();
                                    String index[] = line.split("\\s");
                                    if (index.length != 2){
                                        System.out.println("Wrong file(Μορφοποίηση)");
                                    }
                                    else{
                                        ArrayList <Double> small_list1 = new ArrayList<>();
                                        
                                        small_list1.add(Double.parseDouble(index[0]));
                                        small_list1.add(Double.parseDouble(index[1]));
                                        small_list1.add(1.0);
                                        list.add(small_list1);
                                    }
                                }
                            }
                            Perceptron.setPatterns(list);
                            System.out.println("Patterns: " +Perceptron.getPatterns());
                        } catch (FileNotFoundException | NumberFormatException ex) {
                            Logger.getLogger(MenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                            patternsFlag = false;
                        }
                    } 
                });
                if (targetsFlag == true)    MenuFrame.setTextLabel("Now you can play from Perceptron -> Play!");
            }
        }
    }
    
    //Graphic Interface to choose a file from user's system to load targets data 
    public void loadTargetFile(){
        if (targetsFlag){
            MenuFrame.setTextLabel("You already load Targets!");
        }
        else{
            JFileChooser chooser=new JFileChooser();
            int returnVal = chooser.showOpenDialog(MenuFrame.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) 
            {
                String filename=chooser.getSelectedFile().getAbsolutePath();
                
                ExecutorService executor = Executors.newFixedThreadPool(Application.getThreadsCounter());
                executor.submit(() -> {});
               
                Future <Integer> futureTask = (Future <Integer>) executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FileReader file = new FileReader(filename);
                            try (Scanner in = new Scanner(file)) {
                                while(in.hasNextLine())
                                {
                                    String line=in.nextLine();
                                    Perceptron.setTargets(Integer.parseInt(line));
                                }
                            }
                            System.out.println("Targets: " +Perceptron.getTargets());
                        } catch (FileNotFoundException | NumberFormatException ex) {
                            Logger.getLogger(MenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }    
                });               
                }
                targetsFlag = true;
                if (patternsFlag == true)   MenuFrame.setTextLabel("Now you can play from Perceptron -> Play!");
        }
    }
    
    @Override
    public boolean action(Event event, Object obj){
        if (event.target instanceof MenuItem){
            String text = (String)obj;
            
            switch (text) {
                case "Load Patterns":
                    loadPatternFile();
                    break;
                case "Load Targets":
                    loadTargetFile();
                    break;
                case "Train":
                    if ((patternsFlag == true) && targetsFlag == true){
                        p.start();
                    }
                    else
                        MenuFrame.setTextLabel("<html> <h3>Can't play!</h3> <br/> Need to load patterns & targets data</html>");
                    break;
                default:
                    break;
            }
        }
        return true;
    }
    
    //put text in JLabel
    public static void setTextLabel(String text){
        label.setText(text);
    }
}
