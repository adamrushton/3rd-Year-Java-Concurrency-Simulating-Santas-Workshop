/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author Adam
 * Specification of Present:
 * NOT Threads
 * Must be created at the start of the simulation
 * After created, loaded into hopper (acting as a simulation for toys being distributed)
 * This class stores the type of toy, the age group of the toy (which will be its destination chute)
 * Must also be able to read the destinationshoot in the sorting machine
 */
public class Present {
    static int count = 0; // Counting all the presents in total, there will be multiple instances of this class for different sack numbers
    private final String destinationShoot;
    private final String[] splittedData;
    private final int startingHopper;
    private final String name;
    
    // To-do: sort this string itno appropiate variables
    public Present(String dataForPresent, int startingHopper) {
        splittedData = dataForPresent.split("\\s+");
        //System.out.println("Splitted data for present:" + dataForPresent);
        destinationShoot = splittedData[0];
        this.startingHopper = startingHopper;
        count++;
        name = "Bob " + count;
    }

    public String GetDestinationShoot() {
        return destinationShoot;
    }
    
    public String GetName() {
        return name;
    }
    
    public int GetStartingHopper() {
        return startingHopper;
    }
    
    public int GetCount() {
        return count;
    }
}
