/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author Adam
 */
public class Turntable extends Thread {

    String[] splittedData;
    final String INPUT_BELT = "ib";
    final String OUTPUT_BELT = "ob";
    final String OUTPUT_SACK = "os";

    char turntableId;
    int inputBelt;

    int firstMoveOutputSack = -1;
    int firstMoveOutputBelt = -1;

    int secondMoveOutputSack = -1;
    int secondMoveOutputBelt = -1;

    int thirdMoveOutputSack = -1;
    int thirdMoveOutputBelt = -1;
    int thirdMoveInputBelt = -1;

    /*
    N E S W = North East South West
    ib = input belt (start belt)
    ob = output belt (end belt)
    os = output sack (end destination)
    null = no connection
    Below, shows turntable A has its north port connected to belt 2, from which it receives input, etc.
    
    A N ib 2 E ob 3 S null W ib 1
    B N ib 4 E os 1 S ob 5 W ib 3
    C N ib 5 E os 3 S ob 6 W os 2
    D N ib 6 E os 5 S null W os 4
    
    I am assuming we are always going N E S W movements.
     */
    // To-do. sort this data into appropiate variables
    public Turntable(String dataForTurntable) {
        splittedData = dataForTurntable.split("\\s+");
        turntableId = splittedData[0].charAt(0);
        
        // Assuming that input belt is the first point of call
        inputBelt = Integer.parseInt(splittedData[3]);

        // First move
        if (splittedData[5] == OUTPUT_BELT) {
            firstMoveOutputBelt = Integer.parseInt(splittedData[6]);
        } else if (splittedData[5] == OUTPUT_SACK) {
            firstMoveOutputSack = Integer.parseInt(splittedData[6]);
        }
        
        // Second move
        if (splittedData[8] != "null") {
            if (splittedData[8] == OUTPUT_BELT) {
                secondMoveOutputBelt = Integer.parseInt(splittedData[9]);
            } else if (splittedData[8] == OUTPUT_SACK) {
                secondMoveOutputSack = Integer.parseInt(splittedData[9]);
            }
        }
        
        //3rd move
        if (splittedData[10] == OUTPUT_BELT) {
            thirdMoveOutputBelt = Integer.parseInt(splittedData[11]);
        } else if (splittedData[10] == OUTPUT_SACK) {
            thirdMoveOutputSack = Integer.parseInt(splittedData[11]);
        } else if (splittedData[11] == INPUT_BELT) {
            thirdMoveInputBelt = Integer.parseInt(splittedData[11]);
        }
    }
    
    public void DetectWaitingGift() {
        // Table turn to receive the gift        
        // obtain destination sack for present
        // Table turn to line up with appropriate output port
        // Should only move 90 degrees at once
        // North-South and East-West are the only scenarios in which the turntable needs to move
        // If movement was west to east then turntable wouldnt need to rotate
        // Thread needs to sleep for x time to simulate the time taken in turning. it should also sleep to represent moving to present on and off the turntable
        // 0.5 seconds to rotate 90 degrees
        // 0.75 seconds to move a present either on or off of a turntable
        // Things are kept simple as the configuration for turntables have a set of destination sacks associated with each of the output ports
        // and we also know the identities of any attached convenyot belts and sacks. 
        // If turntable B from the config receives a gift destined for sack 4, it wil know to eject the gift to the south
        // Eject gift at final destination if reached
    }
    /*
    private structure AssignMovements(int index) {
          if (splittedData[index] != null){
            if (splittedData[index] == OUTPUT_BELT) {
                return Integer.parseInt(splittedData[index+1]);
            } else if (splittedData[index] == OUTPUT_SACK) {
                return Integer.parseInt(splittedData[index+1]);
            }
        }
          
    }
     */
}
