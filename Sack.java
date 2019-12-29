/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author Adam
 * Sack specification
 * NOT threads
 * Sacks are a fixed size array, acting as a buffer for depositing Presents
 * Provide methods for turntables to determine if there is space in the sack
 * i.e. Turntable can run methods from this class
 */
public class Sack {
    private final String[] splittedData;
    private Present[] presentsInSack;
    private final int sackId;
    private final int capacity;
    private final String ageRange;
    private int presentCount = 0;
    private int available;
    private String[] buffer;

    // TO-DO: sort this data out into appropiate variables
    public Sack(String dataForSack) {
        splittedData = dataForSack.split("\\s+");
        sackId = Integer.parseInt(splittedData[0]);
        capacity = Integer.parseInt(splittedData[2]);
        ageRange = splittedData[4];
    }     

    public void Insert(Present present) {
        presentsInSack[presentCount] = present;
        System.out.println("Present: " + presentsInSack[presentCount].GetDestinationSack() + " has entered sack " + ageRange);
        presentCount++;
    }
    
    public int GetSackId() {
        return sackId;
    }

    public int GetCapacity() {
        return capacity;
    }

    public boolean IsFull() {
        return capacity == presentCount;
    }
    
    public String GetAgeRange() {
        return ageRange;
    }
    
    public int GetPresentCount() {
        return presentCount;
    }
   
}
