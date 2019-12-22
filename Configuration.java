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
    private List<String> presents = new ArrayList<>();
    private List<String> timer = new ArrayList<>();

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
        presents = LoadImportantDetailsFromFile("-");
        timer = LoadImportantDetailsFromFile("TIMER");
        delay = Integer.parseInt(timer.get(0).replaceAll("[\\D]", ""));

        // Record sack number and delete first line 
        // Store the different types of sack numbers
        // Know which presents are going to what sack
        // Assume hopperNumber = sackNumber
        long numOfPresentHeadings = presents.stream().filter(p -> p.contains("PRESENTS")).count();
        // Currently supporting one PRESENT instance from the config file
        int startingHopper = Integer.parseInt(presents.get(0).replaceAll("[\\D]", ""));
        presents.remove(0);

        // Creation of the objects
        ConveyorBelt[] belt = new ConveyorBelt[belts.size()];
        Hopper[] hopper = new Hopper[hoppers.size()];
        Sack[] sack = new Sack[sacks.size()];
        Turntable[] turntable = new Turntable[turntables.size()];
        Present[] present = new Present[presents.size()];

        for (int i = 0; i < presents.size(); i++) {
            present[i] = new Present(presents.get(i), startingHopper);
            switch (present[i].GetDestinationShoot()) {
                case "0-3":
                    // sack 1
                    break;
                case "4-6":
                    // sack 2 or 4
                    break;
                case "7-10":
                    // sack 3
                    break;
                case "11-16":
                    // sack 5
                    break;
            }

        }
        
        // The presents go into the hopper given from the file
        // Make this randomise which hopper its goes in next time
        for (int i = 0; i < sacks.size(); i++) {
            sack[i] = new Sack(sacks.get(i));
        }

        for (int i = 0; i < turntables.size(); i++) {
            turntable[i] = new Turntable(turntables.get(i));
        }
        for (int i = 0; i < belts.size(); i++) {
            belt[i] = new ConveyorBelt(belts.get(i));
        }
        
        for (int i = 0; i < hoppers.size(); i++) {
            hopper[i] = new Hopper(hoppers.get(i), belt, turntable);
            if (startingHopper == i) {
                hopper[i].LoadPresents(present);
            }
        }

        System.out.println("Present classes: " + present.length);
        System.out.println("Belt classes: " + belt.length);
        System.out.println("Hopper classes: " + hopper.length);
        System.out.println("Sack classes: " + sack.length);
        System.out.println("Turntable classes: " + turntable.length);

        System.out.println("Starting " + hoppers.size() + " Hopper threads");
        hopper[0].start();
        System.out.println("Presents in Hopper: " + hopper[0].GetPresentCount());
        
        System.out.println("Starting " + turntables.size() + " Turntable threads");
        turntable[0].start();
        System.out.println("Turntable and Hopper threads started.");

        // Everything is now set up. Need to start concurrency
        // Concurrency
    }

    public Configuration(String fileName) {
        this.fileName = fileName;
    }

}
