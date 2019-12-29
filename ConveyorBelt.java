/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Adam Stores gift objects as they pass presents onto it
 */
public class ConveyorBelt {

    Semaphore mutex = new Semaphore(1);
    Semaphore numPresentsOnBelt = new Semaphore(0);
    Semaphore numFreeSpaces;
    
    Present[] presentsOnBelt;
    int nextPresentIn = 0;
    int nextPresentOut = 0;
    String[] destinations;
    int noPresentsOnBelt = 0;
    int maxPresentsOnBelt;
    
    int beltId, capacity;
    String[] splittedData;

    public ConveyorBelt(String dataForBelt) {
        splittedData = dataForBelt.split("\\s+");
        beltId = Integer.parseInt(splittedData[0]); // roadName
        capacity = Integer.parseInt(splittedData[2]); // maxCarsInRoad
        // Creates a buffer of presents for the size of the conveyor belt
        presentsOnBelt = new Present[capacity];
        numFreeSpaces = new Semaphore(capacity);
        // index 4 til the end of the splitted data size is the amount of destinations
        destinations = new String[splittedData.length - 4];

        try {
            for (int i = 4; i < splittedData.length; i++) {
                destinations[i - 4] = splittedData[i];
            }
        } catch (NumberFormatException e) {
            System.out.println("An error occured parsing a destination for  conveyorbelt. - " + e.getMessage());
        }
    }

    public int GetBeltId() { //same as road name
        return beltId;
    }

    public Integer GetPresentsWaiting() {
        return noPresentsOnBelt;
    }
    public boolean FreeSpaceOnBelt() {
        return noPresentsOnBelt < capacity;
    }
    public boolean IsDestination(String destination) {
        for (String testDestination : destinations) {
            if (destination.equalsIgnoreCase(testDestination)){
                return true;
            }
        }
        return false;
    }
    
    public void EnterBelt(Present present) throws InterruptedException {
        numFreeSpaces.acquire(); // Ensure free spaces once acquired theres free space
        mutex.acquire();         // Ensure nobody else in critical region
        
        presentsOnBelt[nextPresentIn] = present;
        noPresentsOnBelt++;
        nextPresentIn++;
        if (nextPresentIn == presentsOnBelt.length){
            nextPresentIn = 0;
        }
        
        numPresentsOnBelt.release(); // Present added to belt. Allow exit of present
        mutex.release();             // Leave critical region      
    }
    
    public String GetFirstPresentDestination() {
        return presentsOnBelt[nextPresentOut].GetDestinationSack();
    }
    
    public Present ExitBelt() throws InterruptedException {
        numPresentsOnBelt.acquire(); // Ensure a present on belt to take
        mutex.acquire();             // Ensure nobody else in critical region
        
        Present present = presentsOnBelt[nextPresentOut];
        presentsOnBelt[nextPresentOut] = null; // Remove Present from Belt
        noPresentsOnBelt--;
        
        nextPresentOut++;
        if (nextPresentOut == presentsOnBelt.length) {
            nextPresentOut = 0;
        }
        mutex.release(); // Leave critical region
        numFreeSpaces.release(); // Present moved from belt, allow new present
        return present;
    }
    
}
