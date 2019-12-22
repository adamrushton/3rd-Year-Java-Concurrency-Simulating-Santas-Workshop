/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import static java.lang.Thread.sleep;
import java.util.Arrays;

/**
 *
 * @author Adam Stores gift objects as they pass presents onto it
 */
public class ConveyorBelt {
    final int INDEX_OF_FIRST_DESTINATION = 4;
    Buffer produceBuffer, consumeBuffer;
    Present present;
    int beltId, consumeId, maxOnBelt, presentCount;
    int[] destinations = null;
    String[] splittedData;

    public ConveyorBelt(String dataForBelt) {
        splittedData = dataForBelt.split("\\s+");
        beltId = Integer.parseInt(splittedData[0]);
        maxOnBelt = Integer.parseInt(splittedData[2]);
        
        // index 4 til the end of the splitted data size is the amount of destinations
        destinations = new int[splittedData.length - INDEX_OF_FIRST_DESTINATION];
        
        
        try {
            for (int i = INDEX_OF_FIRST_DESTINATION; i < splittedData.length; i++) {
                destinations[i - INDEX_OF_FIRST_DESTINATION] = Integer.parseInt(splittedData[i]);
            }
        } catch (Exception e) {
            System.out.println("An error occured parsing a destination for  conveyorbelt. - " + e.getMessage());
        }
        
        System.out.println("Destinations: " + Arrays.toString(destinations));
    }

    public void AddPresentToBelt(Present present, Turntable turntable) {
        presentCount++;
        this.present = present;
    }

    public void DropInSack() {
        presentCount--;
    }

    public boolean BeltFull() {
        return maxOnBelt == presentCount;
    }

    public int GetBeltId() {
        return beltId;
    }

    public int[] GetDestinations() {
        return destinations;
    }

    public int GetMaxOnBelt() {
        return maxOnBelt;
    }

    public void Produce(int i) {
        System.out.println("Producer: " + beltId + " producing item: " + i);
    }

    public boolean Consume() {
        String nextPresent = consumeBuffer.extract();
        System.out.println("Consumer " + consumeId + " consuming: " + nextPresent);
        return !nextPresent.equals("****");
    }

    public void RunProduce() {
        for (int i = 0; i < maxOnBelt; i++) {
            try {
                sleep(75);
            } catch (InterruptedException ex) {
                System.out.println("Failed to sleep." + ex);
            }
            Produce(i);
        }
    }

    public void RunConsume() {
        while (Consume()) {
            try {
                sleep(75);
            } catch (InterruptedException ex) {
                System.out.println("Failed to sleep." + ex);
            }
        }
        consumeBuffer.insert("****");
    }
}
