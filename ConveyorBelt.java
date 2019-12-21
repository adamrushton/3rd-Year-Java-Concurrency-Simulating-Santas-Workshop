/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Adam
 * Stores gift objects as they pass presents onto it
 */
public class ConveyorBelt {
    Buffer produceBuffer, consumeBuffer;
    Present present;
    int beltId, consumeId, maxOnBelt, presentCount;
    ArrayList<Integer> destinations = new ArrayList<Integer>();
    String[] splittedData;    
    
    public ConveyorBelt(String dataForBelt) {
        splittedData = dataForBelt.split("\\s+");
        beltId = Integer.parseInt(splittedData[0]);
        maxOnBelt = Integer.parseInt(splittedData[2]);
        
        for (int i = 4; i < splittedData.length; i++) {
            destinations.add(Integer.parseInt(splittedData[i]));
        }
        System.out.println("Destinations: " + destinations);
    }
    
    public void AddPresentToBelt(Present present) {
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

    public ArrayList<Integer> GetDestinations() {
        return destinations;
    }

    public int GetMaxOnBelt() {
        return maxOnBelt;
    }
    
    public void Produce(int i){
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
            } catch (InterruptedException ex){
                System.out.println("Failed to sleep." + ex);
            }
            Produce(i);
        }
    }
    
    public void RunConsume() {
        while (Consume()) {
            try {
                sleep(75);
            } catch (InterruptedException ex){
                 System.out.println("Failed to sleep." + ex);
            }
        }
        consumeBuffer.insert("****");
    }
}
