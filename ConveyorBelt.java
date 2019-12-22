/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author Adam Stores gift objects as they pass presents onto it
 */
public class ConveyorBelt {

    final int INDEX_OF_FIRST_DESTINATION = 4;
    Buffer produceBuffer, consumeBuffer;
    
    private Present[] presents;
    private int nextIn = 0;
    private int nextOut = 0;
    private int available;
    
    int beltId, maxOnBelt, presentCount;
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

    }

    // Needed methods
    // Method for hopper to check if there is space
    public boolean BeltFull() {
        return maxOnBelt == available;
    }
    
    public synchronized void Insert(Present item, int totalPresents){
        if (presents == null) {
            presents = new Present[totalPresents];
        }
        while (available == totalPresents){
            System.out.println("insert waiting");
            
            try {
                wait();
            } catch (InterruptedException ex) {
            }
            
        }
        presents[nextIn] = item;
        available += 1;
        try {
            Thread.sleep((int) (Math.random() * 10));
        } catch (InterruptedException ex) {
        }
        nextIn++;
        if(nextIn == presents.length){
            nextIn = 0;
        }
        if (available == presents.length)
            System.out.println("Buffer full. All presents added to conveyor");
        notifyAll();
    }
    
    // Method for turntable to take a present
    public synchronized Present Extract(){
        while (available == 0){
            System.out.println("extract waiting");
            
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        Present res = presents[nextOut];
        try {
            Thread.sleep((int) (Math.random() * 10));
        } catch (InterruptedException ex) {
        }
        available--;

        if (res==null)
            System.out.println("Invalid present");

        nextOut++;
        if (nextOut==presents.length)
            nextOut=0;

        if (available == 0)
            System.out.println("buffer empty");

        notifyAll();
        return res;
    }
    
}
