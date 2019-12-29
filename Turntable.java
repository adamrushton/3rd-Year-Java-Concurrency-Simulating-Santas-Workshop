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
 * @author Adam
 * 
 * TURNTABLES
    4
    * ID, North, inputBelt
    A N ib 2 E ob 3 S null W ib 1
    B N ib 4 E os 1 S ob 5 W ib 3
    C N ib 5 E os 3 S ob 6 W os 2
    D N ib 6 E os 5 S null W os 4 
 */

public class Turntable extends Thread {

    long rotationTime = 500; // 1/2 a second sleep time
    long movePresentTime = 750; // 3/4 of a second sleep time
    
    Present present;            // Stores a present from the buffer 
    int inputBeltNumber;
    ConveyorBelt[] belt;        // Access to all the conveyor belts
    String[] splittedData;      // Configuration from the file for each turntable
    String turntableId;         // Storing A,B,C or D from the default configuration      
    ConveyorBelt[] inputBelts;
    ConveyorBelt[] outputBelts;
    String[] ports;

    public Turntable(String dataForTurntable, ConveyorBelt[] belt) {
        splittedData = dataForTurntable.split("\\s+");
        turntableId = splittedData[0];
        inputBeltNumber = 0; // Start at first input belt
        if (turntableId.equals("A")) {
            inputBelts = new ConveyorBelt[2];
            outputBelts = new ConveyorBelt[1];
            inputBelts[0] = belt[Integer.parseInt(splittedData[3])];
            inputBelts[1] = belt[Integer.parseInt(splittedData[11])];
            outputBelts[0] = belt[Integer.parseInt(splittedData[6])];
        }
        System.out.println("Splitted data for TURNTABLE " + turntableId + " DATA: " + dataForTurntable);
        this.belt = belt; // stores all the belts that are created

    }

    @Override
    public void run() {
        while (Configuration.isRunning) {
            try {
                while (inputBelts[inputBeltNumber].GetPresentsWaiting() <= 0) {
                    // No presents waiting
                   System.out.println("There are no presents ready yet!");
                }
                String destination = inputBelts[inputBeltNumber].GetFirstPresentDestination();
                ConveyorBelt correctOutputBelt = null;
                
                // Find correct exit belt
                
                for (ConveyorBelt outputBelt : outputBelts) {
                    if (outputBelt.IsDestination(destination)) {
                        correctOutputBelt = outputBelt;
                        break; // Found next belt to move to
                    }
                }
                
                // Ensure space on output Belt
                if (correctOutputBelt != null) {
                    while (!correctOutputBelt.FreeSpaceOnBelt()){
                        // No space on belt, wait for space
                        Thread.sleep(1000); // Sleep for a second
                        System.out.println("Sleeping for a second, theres no space on the corresponding output belt");
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occured while running turntable: " + turntableId);
            }
        }
    }

    // Moves present to belt or sack
    public void produce() {

    }

    // Takes present from belt, simulating a present being on the "turntable"
    public void GetAvailablePresent() {
        if (inputBelts[0] != null) {
            try {
                present = inputBelts[0].ExitBelt(); // Takes the present from the buffer
            } catch (InterruptedException ex) {
                Logger.getLogger(Turntable.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Turntable " + turntableId + " has present " + present.GetName() + " which needs to go to sack " + present.GetDestinationSack());
        } else {
            System.out.println("input belt doesnt exist");
        }
    }

}
