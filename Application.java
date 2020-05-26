package simple_Perceptron;

import javax.swing.WindowConstants;

public class Application {

    private static int threadsCounter = 0;
    
    public static void main(String[] args) {

        //Γραφική διεπαφή χρήστη
        MenuFrame myFrame = new MenuFrame("Perceptron with Java Concurrency");
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        if (args.length == 1){
            setThreadsCounter(Integer.parseInt(args[0]));
            System.out.println("I run with " +getThreadsCounter()+" threads!");
        }
        else{
            MenuFrame.setTextLabel("Need arguments for count of threads want to use!");
        }
        
        myFrame.setVisible(true);     
    }

    /**
     * @return the threadsCounter
     */
    public static int getThreadsCounter() {
        return threadsCounter;
    }

    /**
     * @param aCounter_threads the threadsCounter to set
     */
    public static void setThreadsCounter(int aCounter_threads) {
        threadsCounter = aCounter_threads;
    }
    
}
