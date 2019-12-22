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

    private final int sackId;
    private final int capacity;
    private final String ageRange;
    private int presentCount;
    private int available;
    private int nextIn = 0;
    private int nextOut = 0;
    private String[] buffer;

    // TO-DO: sort this data out into appropiate variables
    public Sack(String dataForSack) {
        splittedData = dataForSack.split("\\s+");
        sackId = Integer.parseInt(splittedData[0]);
        capacity = Integer.parseInt(splittedData[2]);
        ageRange = splittedData[4];
    }     

    public int GetSackId() {
        return sackId;
    }

    public int GetCapacity() {
        return capacity;
    }
    // Check if sack is full
    public boolean IsFull() {
        return capacity == nextIn;
    }
    
    public String GetAgeRange() {
        return ageRange;
    }
    
    public int GetPresentCount() {
        return presentCount;
    }

    public void Insert(String item) {
        while (available == buffer.length) {
            System.out.println("Insert waiting");
            try {
                wait();
            } catch (InterruptedException ex) {

            }
            buffer[nextIn] = item;
            available += 1;
            try {
                Thread.sleep(75);
            } catch (InterruptedException ex) {
                System.out.println("Failed to sleep." + ex);
            }
            nextIn++;
            if (nextIn == buffer.length) {
                nextIn = 0;
            }
            if (available == buffer.length) {
                System.out.println("The sack is full of presents!");
            }
            notifyAll();
        }
    }

    public synchronized String Extract() {
        String res = "";
        while (available == 0) {
            System.out.println("Waiting to extract");

            try {
                wait();
            } catch (InterruptedException ex) {
                System.out.println("Failed to wait." + ex);
            }
        }
        res = buffer[nextOut];
        try {
            Thread.sleep(75);
        } catch (InterruptedException ex) {
            System.out.println("Failed to sleep." + ex);
        }
        available--;
        if (res == null) {
            res = "invalid present";
        }
        nextOut++;
        if (nextOut == buffer.length) {
            nextOut = 0;
        }
        if (available == 0) {
           System.out.println("No presents in the sack");
        }
        notifyAll();
        return res;
    }
}
