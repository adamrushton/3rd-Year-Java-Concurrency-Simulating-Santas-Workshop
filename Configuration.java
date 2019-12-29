/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Adam
 */
public class Configuration {

    private final String fileName;
    private int delay;

    private List<String> belts = new ArrayList<>();
    private List<String> hoppers = new ArrayList<>();
    private List<String> sacks = new ArrayList<>();
    private List<String> turntables = new ArrayList<>();
    private List<String> firstPresents = new ArrayList<>();
    private List<String> timer = new ArrayList<>();
    public static boolean isRunning = false;
    // Read from Config file
    // Create Hoppers, Belts, Turntables and Sacks based on the file
    // Fill hoppers with Presents according to the configuration file
    // Call run methods on the threaded objects
    // Instigate shutdown of the machine
    // Display to console the final report
    public void Run() {
        LoadConfiguration();
    }

    // Loading only the important information from the file 
    private List<String> LoadImportantDetailsFromFile(String filter) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            if (!"-".equals(filter)) {
                return stream.filter(line -> line.contains(filter)).collect(Collectors.toList());
            } else { // check for less than 7, supporting age range up to 3 digits <xxx-xxx>
                return stream.filter(line -> line.length() < 7 && line.contains(filter) || line.contains("PRESENTS")).collect(Collectors.toList());
            }
        } catch (Exception e) {
            System.out.println("Error loading from file" + e.getMessage());
        }
        return null;
    }

    private void LoadConfiguration() {
        belts = LoadImportantDetailsFromFile("length");
        hoppers = LoadImportantDetailsFromFile("speed");
        sacks = LoadImportantDetailsFromFile("age");
        turntables = LoadImportantDetailsFromFile("ib");
        firstPresents = LoadImportantDetailsFromFile("-");
        timer = LoadImportantDetailsFromFile("TIMER");
        
        System.out.println("Presents: " + firstPresents);
        
        if (belts == null || hoppers == null || sacks == null || turntables == null || firstPresents == null || timer == null) {
            System.out.println("An error occured reading in the file.");
            System.out.println("Belts: " + belts);
            System.out.println("Hoppers: " + hoppers);
            System.out.println("Sacks: " + sacks);
            System.out.println("Turntables: " + turntables);
            System.out.println("Presents: " + firstPresents);
            System.out.println("Timer: " + timer);
            System.out.println("Terminated program.");
            return;
        }

        delay = Integer.parseInt(timer.get(0).replaceAll("[\\D]", ""));

        // Creation of the objects
        ConveyorBelt[] belt = new ConveyorBelt[belts.size()];
        Hopper[] hopper = new Hopper[hoppers.size()];
        Sack[] sack = new Sack[sacks.size()];
        Turntable[] turntable = new Turntable[turntables.size()];
        Present[] present = new Present[firstPresents.size()];
        
        int hopperId = 0;
        int presentId = 0;
        for (int i = 0; i < firstPresents.size(); i++) {
            if (firstPresents.get(i).contains("PRESENT")) {
                hopperId = Integer.parseInt(firstPresents.get(i).substring(firstPresents.get(i).indexOf(" ")+1));
            } else {           
                present[presentId] = new Present(firstPresents.get(i), hopperId);                
                presentId++;
            }
        }

        for (int i = 0; i < sacks.size(); i++) {
            sack[i] = new Sack(sacks.get(i));
        }

        for (int i = 0; i < belts.size(); i++) {
            belt[i] = new ConveyorBelt(belts.get(i));
        }
        
        isRunning = true;
        
        // Turntables are threads
        for (int i = 0; i < turntables.size(); i++) {
            turntable[i] = new Turntable(turntables.get(i), belt);
            turntable[i].start();
        }
        
        // Hoppers are threads
        // Create an array of present for each hopper.
        
        for (int i = 0; i < hoppers.size(); i++) {
            hopper[i] = new Hopper(hoppers.get(i), present, belt);
            hopper[i].start();
        }

        System.out.println("Started " + hoppers.size() + " Hopper threads");
        System.out.println("Presents in Hopper 1: " + hopper[1].GetPresentCount());
        System.out.println("Started " + turntables.size() + " Turntable threads");
        System.out.println("Turntable and Hopper threads started.");
        //System.out.println("time: " + timeElapsed);
    }
    
    public Configuration(String fileName) {
        this.fileName = fileName;
    }

}
