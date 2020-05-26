package simple_Perceptron;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Perceptron extends Thread {
  
   private static ArrayList <ArrayList <Double> > patterns = new ArrayList <>();
   private static final ArrayList <Integer> targets = new ArrayList <Integer>();
   private static ArrayList <Double> weights = new ArrayList <>();
   private int counter = 0;
   
   Perceptron(){   
   }

    /**
     * @return the patterns
     */
    public static ArrayList <ArrayList <Double> > getPatterns() {
        return patterns;
    }

    /**
     * @param aPatterns the patterns to set
     */
    public static void setPatterns(ArrayList <ArrayList <Double> > aPatterns) {
        patterns = aPatterns;
    }

    /**
     * @return the targets
     */
    public static ArrayList <Integer> getTargets() {
        return targets;
    }

    /**
     * @param aWeights the targets to set
     */
    public static void setTargets(Integer x) {
        targets.add(x);
    }
    
    public void initialize_weights(){
        ExecutorService executor = Executors.newFixedThreadPool(Application.getThreadsCounter());
        executor.submit(() -> {});
        
        Future<Integer> futureTask = (Future<Integer>) executor.submit(() -> {
            for (short i = 0; i < 3; i++)
                getWeights().add(1.0);
        });
        //System.out.println("Weights: "+getWeights());
    }
    
    public void calculate_output(){    
        MenuFrame.setTextLabel("Training");
        ExecutorService executor = Executors.newFixedThreadPool(Application.getThreadsCounter());
        executor.submit(() -> {});
        
        Future<Boolean> futureTask = (Future<Boolean>) executor.submit(() -> {
            for (int i=0; i < 100; i++){
                for (int j = 0; j < targets.size(); j++){
                    double sum = 0.0d;
                    for (int k = 0; k < getWeights().size(); k++){
                        sum += patterns.get(j).get(k) * getWeights().get(k);
                    }
                    //System.out.println("Sum = "+sum);
                
                    int function;
                    if (sum > 0)
                        function = 1;
                    else
                        function = -1;
                
                    //weight training
                    if (function != targets.get(j)){
                        counter = 0;
                        for (int k = 0; k < Perceptron.weights.size(); k++){
                            double education = Perceptron.weights.get(k) + 0.1 * ( targets.get(j) - function) * patterns.get(j).get(k);
                            Perceptron.weights.set(k,education);
                            //System.out.println(" Next Weights["+k+"] = "+education);
                        }
                    }
                    else{
                        counter++;
                        if (counter == targets.size()){
                            System.out.println("Weights = "+Perceptron.weights);
        
                            MenuFrame.setTextLabel("Weights: "+Perceptron.weights);                        
                            return true;
                        }                      
                    }    
                }
            }
            return false;
        });   
    }
    
    @Override
    public void start()
    {
        if (!(patterns.size() == targets.size())){
            System.out.println("Wrong data!");
            System.exit(0);
        }
        else
        {
            System.out.println("Let's Start!");
            initialize_weights();
            calculate_output();
        }
    }

    /**
     * @return the weights
     */
    public static ArrayList <Double> getWeights() {
        return weights;
    }

    /**
     * @param aWeights the weights to set
     */
    public static void setWeights(ArrayList <Double> aWeights) {
        weights = aWeights;
    }
}
