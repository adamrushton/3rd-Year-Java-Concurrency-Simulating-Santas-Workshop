/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam Hopper specification ARE Threads Store collection of Presents
 * Associated with ConveyorBelt. Have a speed of working Using the speed, at
 * interval times until empty, a hopper will try to place presents onto the
 * conveyor belt, as long as there is space This class will place presents onto
 * the conveyor belt, aslong as there is space
 */
public final class Hopper extends Thread {

    // Load presents and feed to belt
    String[] splittedData; // Data from the file
    Present[] presents;    // Array of presents 
    ConveyorBelt enterBelt;// Storing only the belt that the hopper has access to
    int hopperId, connectedBeltId, capacity, speed, presentId = 0;
    String range;

    Hopper(String dataForHopper, Present[] present, ConveyorBelt[] belt) {
        splittedData = dataForHopper.split("\\s+");
        //this.present = new Present[present.length];
        hopperId = Integer.parseInt(splittedData[0]);
        connectedBeltId = Integer.parseInt(splittedData[2]);
        capacity = Integer.parseInt(splittedData[4]);
        speed = Integer.parseInt(splittedData[6]);
        speed = 5000;
        int count = 0; // how many presents are for a particular hopper

        // Only store a present if it is already existing here
        for (Present p : present) {
            if (p != null && p.GetHopperNumber() == hopperId) {
                count++;
            }
        }
        this.presents = new Present[count]; // create that many presents

        // add the presents to our presents collection
        int presentCount = 0;
        for (Present p : present) {
            if (p != null && p.GetHopperNumber() == hopperId) {
                this.presents[presentCount] = p;
                presentCount++;
            }
        }
        this.enterBelt = belt[connectedBeltId];
    }

    /**
     *
     * ye
     */
    @Override
    public void run() {
        while (presents.length > 0) {
            AddPresentToConveyorBelt();
        }
    }

    private void AddPresentToConveyorBelt() {
        // Passes each present onto the first conveyor belt        
        for (Present p : presents) {
            if (p != null) {
                try {
                    while (!enterBelt.FreeSpaceOnBelt()) {
                        // Belt is full, unable to put any more presents on the belt. Sleep and try again
                        Thread.sleep(speed);
                        System.out.println("Belt is full so sleeping and gonna try again");
                    }
                    enterBelt.EnterBelt(p);
                    System.out.println("Hopper " + hopperId + " added P [" + p.GetDestinationSack() + "] to the buffer");

                    p = null;     // Present no longer in the hopper. 
                    sleep(speed); // Sleep to simulate present being added to the belt            
                } catch (InterruptedException ex) {
                    System.out.println("Failed adding or sleeping");
                }
            }
        }
    }

    public int GetPresentCount() {
        if (presents != null) {
            return presents.length - 3;
        }
        return -1; // This will happen when the id on presents doesnt match a hopper id
    }

    public int GetHopperId() {
        return hopperId;
    }

    public int GetConnectedBeltId() {
        return connectedBeltId;
    }

    public int GetCapacity() {
        return capacity;
    }
}
