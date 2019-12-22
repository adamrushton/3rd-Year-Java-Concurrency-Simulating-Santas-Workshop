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
    Present[] presents;
    int count = 0;
    String[] splittedData;
    final String INPUT_BELT = "ib";
    final String OUTPUT_BELT = "ob";
    final String OUTPUT_SACK = "os";
    String turntableId;

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
    private int CreateDirectionId(String[] direction, int position, int compassIndex) {
        if (!"null".equals(direction[position])) {
            return Integer.parseInt(splittedData[compassIndex + 2]);
        }
        return -1;
    }

    @Override
    public void run() {
        DetectWaitingGift();
        // to-do run this 
    }
    public Turntable(String dataForTurntable) {
        int northIndex = -1, eastIndex = -1, southIndex = -1, westIndex = -1;
        String[] direction = new String[4];
        int[] directionId = new int[4];
        // Search for index locations of N, E, S, W
        splittedData = dataForTurntable.split("\\s+");
        //System.out.println("Splitted data: " + Arrays.toString(splittedData));

        for (int i = 0; i < splittedData.length; i++) {
            if (splittedData[i].equals("N")) {
                northIndex = i;
            }
            if (splittedData[i].equals("E")) {
                eastIndex = i;
            }
            if (splittedData[i].equals("S")) {
                southIndex = i;
            }
            if (splittedData[i].equals("W")) {
                westIndex = i;
            }
        }

        turntableId = splittedData[0];

        //System.out.println("ttid: " + turntableId);
        //System.out.println("N: " + northIndex + " E: " + eastIndex + " S: " + southIndex + " W: " + westIndex);
        //System.out.println("Splitted data for Turntable: " + splittedData.length);
        direction[0] = splittedData[northIndex + 1];
        direction[1] = splittedData[eastIndex + 1];
        direction[2] = splittedData[southIndex + 1];
        direction[3] = splittedData[westIndex + 1];
        // Set string array to null, os, ib, ob 
        // If there is an input belt, output belt or output sack, a directionId will be found
        directionId[0] = CreateDirectionId(direction, 0, northIndex);
        directionId[1] = CreateDirectionId(direction, 1, eastIndex);
        directionId[2] = CreateDirectionId(direction, 2, southIndex);
        directionId[3] = CreateDirectionId(direction, 3, westIndex);

        //System.out.println("Direction 0: " + direction[0] + " DirectionId 0: " + directionId[0]);
        //System.out.println("Direction 1: " + direction[1] + " DirectionId 1: " + directionId[1]);
        //System.out.println("Direction 2: " + direction[2] + " DirectionId 2: " + directionId[2]);
        //System.out.println("Direction 3: " + direction[3] + " DirectionId 3: " + directionId[3]);
        
        // check index of N, E, S and W
        // if value is not null, get the value after it
        // null means empty, if a section is empty, don't check the value after it
        // 2, 5, 8, 10 direction indexes
        // 3, 6, 9, 11 values of direction
        // Assuming that input belt is the first point of call
    }

    public void DetectWaitingGift(ConveyorBelt belt) {
        presents[count] = belt.Extract();
        count++;
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
}
