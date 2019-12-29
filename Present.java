/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import java.util.Arrays;

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
    static int count = 0; // Counting all the presents in total, there will be multiple instances of this class for different hoppers
    private final String destinationSack;
    private final String[] splittedData;
    private final int hopperNumber;
    private String name;
    
    // To-do: sort this string itno appropiate variables
    public Present(String dataForPresent, int hopperNumber) {
        splittedData = dataForPresent.split("\\s+");
        destinationSack = splittedData[0];
        this.hopperNumber = hopperNumber;
        System.out.println("Present created. Destination Sack: " + Arrays.toString(splittedData) + " Hopper Number: " + hopperNumber);
        count++;
        name = "Present " + count;
    }
    
    public String GetDestinationSack() {
        return destinationSack;
    }
    
    public String GetName() {
        return name;
    }
    
    public void SetName(String name) {
        this.name = name;
    }
    
    public int GetHopperNumber() {
        return hopperNumber;
    }
    
    public int GetCount() {
        return count;
    }
}
