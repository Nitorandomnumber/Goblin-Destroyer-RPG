//package goblindestroyerrpg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.*;

//import org.fusesource.jansi.AnsiConsole;

public class GoblinDestroyerRPG {
    
    // Game variables
    private static String[] locations = {"Forest1", "Forest2", "Forest3", "Kingdom Gates", "King's Castle", "Blacksmith", "Inn", "Dungeon"};
    private static String[] startlocations = {"Forest1", "Forest2", "Forest3"};
    private static String playername;
    private static int[][] distances; // Stores distances between locations
    private static int playerLocation; // Current location of the player
    private static int time = 6; // Game time starts at 6 AM
    private static int goblinCrystals = 0; // Currency for upgrades
    private static int playerHealth = 100;
    private static int playerStamina = 0;
    private static int swordShards = 0; // Number of sword shards collected
    private static boolean hasSword = false; // Whether the player has the Goblin Destroyer Sword
    private static boolean dialogue = true;
    private static boolean music = true;
    private static boolean onmusic = false;
    private static boolean inbattle = false;
    private static boolean backgroundmusic = true;
    private static boolean hasVisitedGates = false; // Track if player has visited Kingdom Gates
    private static boolean hasVisitedCastle = false;
    private static boolean finalboss = false;
    
    private static boolean battlemusic = true;
    private static Clip mus;
    // Player stats
    private static int playerAttack = 10;
    private static int playerDefense = 5;
    private static int weaponUpgrades = 0; // Track weapon upgrades
    private static int armorUpgrades = 0; // Track armor upgrades
    private static int[][] mapCoordinates = new int[locations.length][2];


    // Scanner for user input
    private static Scanner scanner = new Scanner(System.in);
    private static Scanner enter = new Scanner(System.in);

     
  public static void main(String[] args) throws InterruptedException {
    //AnsiConsole.systemInstall();

    // Display the animation
    displayAnimation();
    startScreen();

    // Proceed with game initialization and start
    initializeGame();  // Set up the game
    displayGameIntro();
    gamestart();       // Begin the game

    //AnsiConsole.systemUninstall();
}

public static void displayAnimation() throws InterruptedException {
    String[][] frames = {
        {
            "                                 ██████    ██████  ████████   ██         ██   ████    ██ ",
            "                                ██        ██    ██ ██      ██ ██         ██   ██ ██   ██ ",
            "                                ██ ████   ██ ██ ██ ██   ████  ██         ██   ██   ██ ██ ",
            "                                ██    ██  ██    ██ ██      ██ ██         ██   ██    ████ ",
            "                                 ██████     ████   ████████   ████████   ██   ██     ███ "
        },
        {
            "                        ██████  ███████  ████████  ████████ ██████   ██████  ██    ██ ███████ ██████  ",
            "                        ██   ██ ██       ██           ██    ██   ██ ██    ██ ████████ ██      ██   ██ ",
            "                        ██   ██ █████    ████████     ██    ██████  ██    ██    ██    █████   ██████  ",
            "                        ██   ██ ██             ██     ██    ██   ██ ██    ██    ██    ██      ██   ██ ",
            "                        ██████  ███████  ████████     ██    ██   ██  ██████    ████   ███████ ██   ██ "
        },
        {
            "                                        ██████  ██████   ██████  ",
            "                                        ██   ██ ██   ██ ██       ",
            "                                        ██████  ██████  ██   ███ ",
            "                                        ██   ██ ██      ██    ██ ",
            "                                        ██   ██ ██       ██████  "
        }
    };

    for (String[] frame : frames) {
        clearConsole();
        dissolveFrame(frame, "\u001B[32m");
    }

    clearConsole();
    System.out.println("\u001B[32m");
    System.out.println("                         ██████      ██████   ██████    ██        ██████  ████    ██");
    System.out.println("                        ██          ██    ██  ██    ██  ██          ██    ██ ██   ██");
    System.out.println("                        ██     ███ ██      ██ ██ ████   ██          ██    ██  ██  ██");
    System.out.println("                        ██       ██ ██    ██  ██    ██  ██          ██    ██   ██ ██");
    System.out.println("                         ████████    ██████   ██████    ████████  ██████  ██    ████ ");
    System.out.println();
    System.out.println("           ██████  ███████ ████████ ██████████  ████████      █████     ██    ██ ████████   ██████   ");
    System.out.println("           ██   ██ ██      ██           ██      ██      ██   ██    ██    ██  ██  ██         ██    ██ ");
    System.out.println("           ██   ██ █████   ████████     ██      ████████    ██      ██     ██    ██████     ██████   ");
    System.out.println("           ██   ██ ██            ██     ██      ██      ██   ██    ██      ██    ██         ██   ██  ");
    System.out.println("           ██████  ███████ ████████    ████     ██      ██    ██████       ██    ████████   ██    ██ ");
    System.out.println();
    System.out.println("                                              ██████  ██████   ██████  ");
    System.out.println("                                              ██   ██ ██   ██ ██       ");
    System.out.println("                                              ██████  ██████  ██   ███ ");
    System.out.println("                                              ██   ██ ██      ██    ██ ");
    System.out.println("                                              ██   ██ ██       ██████  ");
    System.out.println("\u001B[0m");
}
    public static void dissolveFrame(String[] text, String color) throws InterruptedException {
        int maxLength = 0;
        for (String line : text) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }

        for (int step = 0; step <= maxLength; step++) {
            clearConsole();
            for (String line : text) {
                int length = Math.min(step, line.length());
                System.out.println(color + line.substring(0, length) + "\u001B[0m");
            }
            Thread.sleep(20);
        }
        Thread.sleep(250);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    private static void startScreen() {
        System.out.println("--- Goblin Destroyer RPG ---");
        talk("Welcome to Goblin Destroyer RPG!\n");
        System.out.println("1. Start Game");
        System.out.println("2. Toggle Dialogue (Currently: " + (dialogue ? "On" : "Off") + ")");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        if (choice == 2) {
            dialogue = !dialogue;
            System.out.println("Dialogues are now " + (dialogue ? "On" : "Off") + ".");
            System.out.println("Press Enter to continue.");
            enter.nextLine(); // Wait for Enter
            startScreen(); // Show start screen again
        } else if (choice != 1) {
            System.out.println("Invalid choice. Please try again.");
            System.out.println("Press Enter to continue");
            enter.nextLine();
            startScreen(); // Show start screen again
        }
    }

   public static void gamestart() {
    while (true) {
        try {
            if (!backgroundmusic) {  // If music is playing, stop it
                mus.stop();
                mus.close();
                onmusic = false;
            } else if (!onmusic) {  // If music is NOT playing, start it
                // Updated file path
                File audioFile = new File("src\\resources\\battlemusic.wav");
    
                // Check if file exists
                if (!audioFile.exists()) {
                    throw new FileNotFoundException("Audio file not found: " + audioFile.getAbsolutePath());
                }
    
                // Get the audio input stream
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                mus = AudioSystem.getClip();
                mus.open(audioStream);
                mus.loop(Clip.LOOP_CONTINUOUSLY);
                onmusic = true;
            }
            // Toggle state
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }

        if (!inbattle) {
            displayMenu();
            int choice = scanner.nextInt();
            handleMenuChoice(choice);
        } else {
            if(!finalboss){
            battleGoblin();
            continue;}
            else if(finalboss){
            battleGoblinKing();
            continue;   
            }
        }
    }
}
    // Initialize the game
    private static void initializeGame() {
        Random random = new Random();
        distances = new int[locations.length][locations.length];

        // Generate random distances between locations (10-100 meters)
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations.length; j++) {
                if (i == j) {
                    distances[i][j] = 0; // Distance to self is 0
                } else {
                    distances[i][j] = random.nextInt(91) + 10; // Random distance between 10 and 100
                }
            }
        }

        // Generate random coordinates for each location based on distances
        for (int i = 0; i < locations.length; i++) {
            mapCoordinates[i][0] = random.nextInt(48); // Random x coordinate (0-47)
            mapCoordinates[i][1] = random.nextInt(12); // Random y coordinate (0-11)
        }

        // Randomize player's starting location
        playerLocation = random.nextInt(startlocations.length);
    }

    // Display the game introduction
    private static void displayGameIntro() {
        talk("You have been transported to a world overrun by goblins.\n");
        talk("Your mission is to find the lost parts of the Goblin Destroyer Sword and defeat the Goblin King!\n");
        talk("Your adventure begins in: " + startlocations[playerLocation] + "\n");
        System.out.println("Press Enter to continue");
        enter.nextLine();
    }

    // Display the main menu
    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. View Map");
        System.out.println("2. Travel to a new location");
        System.out.println("3. Check Stats");
        System.out.println("4. Visit Blacksmith");
        System.out.println("5. Rest at Inn");
        System.out.println("6. Enter Dungeon (if sword is reassembled)");
        System.out.println("7. Toggle Dialogues");
        System.out.println("8. Toggle Music");
        System.out.println("9. Quit Game");
        talk("Choose an option: ");
        
    }

    // Handle the player's menu choice
    private static void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                displayMap();
                break;
            case 2:
                travelToLocation();
                break;
            case 3:
                displayStats();
                break;
            case 4:
                visitBlacksmith();
                break;
            case 5:
                restAtInn();
                break;
            case 6:
                enterDungeon();
                break;
            case 7:
                dialogue = !dialogue;
                if (dialogue){talk("Dialogues: On"); }
                else{ talk("Dialogues: Off");}  
                           
                break;  
            case 8:
                backgroundmusic = !backgroundmusic;
                battlemusic = !battlemusic;
                if (music){talk("Music: On"); }
                else{ talk("Music: Off");}  
                break;  
            case 9:
                talk("Thanks for playing Goblin Destroyer RPG!");
                System.out.println("Press Enter to continue");
                enter.nextLine();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                System.out.println("Press Enter to continue");
                enter.nextLine();
        }
    }

    // Display the map with the player's live location
    public static void displayMap(){
    String[][] mapArt = new String[25][50];
        String B = "\u001B[38;2;89;43;43m\u2588\u2588\u001B[0m";
        String G = "\u001B[32m\u2588\u2588\u001B[0m";
        String D = "\u001B[38;2;20;69;17m\u2588\u2588\u001B[0m";
        String Gy = "\u001B[38;2;84;84;84m\u2588\u2588\u001B[0m";
        String R = "\u001B[38;2;130;55;55m\u2588\u2588\u001B[0m";        String Bl = "\u001B[38;2;0;0;0m\u2588\u2588\u001B[0m";
        String Y = "\u001B[38;2;196;128;55m\u2588\u2588\u001B[0m";
        String Bu = "\u001B[38;2;9;5;247m\u2588\u2588\u001B[0m"; 
        String P = "\033[38;2;141;85;36m\u2588\u2588\u001B[0m";
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 50; j++) {
            	mapArt[i][j] = G;
            }
        }
        String[][] pathways = {
            {G, G, G, G, G, G, G, P, P, P, P, P, P, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, },
            {G, G, G, G, G, G, P, P, G, G, P, G, P, P, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, },
            {G, G, G, G, P, P, P, G, G, G, P, G, G, P, P, G, G, G, G, G, G, G, G, G, G, P, G, P, G, G, G, G, G, G, G, G, },
            {G, G, G, G, P, G, G, G, G, G, P, G, G, G, P, G, G, G, G, G, G, G, G, G, G, P, G, P, P, G, G, G, G, G, G, G, },
            {G, G, P, P, P, G, G, G, G, G, P, G, G, G, P, G, G, G, G, G, G, G, G, P, P, P, G, G, P, G, G, G, G, G, G, G, },
            {G, G, P, G, G, G, G, G, G, G, P, G, G, G, P, P, G, G, G, G, G, G, G, P, G, G, G, G, P, G, G, G, G, G, G, G, },
            {G, P, P, G, G, G, G, G, G, G, P, G, G, G, G, P, G, G, G, G, G, P, P, P, G, G, G, G, P, P, G, G, G, G, G, G, },
            {G, P, G, G, G, G, G, G, G, G, P, G, G, G, G, P, G, G, G, G, P, P, G, G, G, G, G, G, G, P, G, G, G, G, G, G, },
            {G, P, G, G, G, P, P, P, P, P, P, P, P, P, P, P, P, P, P, P, P, G, G, G, G, G, G, G, G, P, G, G, G, G, G, G, },
            {P, P, P, P, P, P, G, G, G, G, P, G, G, G, G, P, G, G, G, G, P, P, P, P, P, P, P, G, G, P, P, G, G, G, G, G, },
            {P, G, G, G, G, G, G, G, G, P, P, G, G, G, G, P, G, G, G, G, G, P, G, G, G, G, P, G, G, G, G, G, G, G, G, G, },
            {P, P, G, G, G, G, G, G, G, P, G, G, G, G, P, P, G, G, G, G, G, P, G, G, G, G, P, P, P, P, P, P, P, G, G, P, },
            {G, P, G, G, G, G, G, G, G, P, G, G, G, G, P, G, G, G, G, G, G, P, G, G, G, G, G, G, G, G, G, G, G, G, G, P, },
            {G, P, P, G, G, G, G, G, G, P, G, G, G, G, P, G, G, G, G, G, G, P, G, G, G, G, G, G, G, G, G, G, G, G, G, P, },
            {G, G, P, P, P, G, G, G, G, P, G, G, G, P, P, G, G, G, G, G, G, P, G, G, G, G, G, G, G, G, G, G, G, P, P, P, },
            {G, G, G, G, P, G, G, G, G, P, G, G, P, P, G, G, G, G, G, G, G, P, G, G, G, G, G, G, G, G, G, G, G, P, G, G, },
            {G, G, G, G, P, P, G, G, G, P, G, G, P, G, G, G, G, G, G, G, G, P, G, G, G, G, G, G, G, G, G, G, G, P, G, G, },
            {G, G, G, G, G, P, P, P, P, P, P, P, P, G, G, G, G, G, G, G, G, P, G, G, G, G, G, G, G, G, G, G, P, P, G, G, },
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, P, G, G, G, G, G, G, G, G, G, G, P, G, G, G, },
            {G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, G, P, P, P, G, G, G, G, P, P, P, P, P, G, G, G, },
        };
        String[][] forest = {
            {G,D,D,G},
            {D,D,D,D},
            {D,D,D,D},
            {G,B,B,G},
            {P,B,B,P}
        };
        String[][] forest2 = {
            {G,D,D,G},
            {D,D,D,D},
            {D,D,D,D},
            {G,B,B,G},
            {G,B,B,G},
            {P,B,B,P}
        };
        String[][] walls = {
            {Gy,Gy},
            {Gy,Gy},
            {Gy,Gy},
            {Gy,Gy},
            {Gy,Gy},
            {Gy,Gy},
            {Gy,Gy},
            {Gy,Gy},
            {Gy,Gy}
        };
        String[][] gates = {
            {Gy, Gy, Gy, Gy},
            {Gy, Gy, Gy, Gy},
            {Gy, Gy, Gy, Gy},
            {G, R, R, G},
            {P, R, R, P},
            {G, R, R, G},
            {Gy, Gy, Gy, Gy},
            {Gy, Gy, Gy, Gy},
            {Gy, Gy, Gy, Gy}
        };
        String[][] dungeon = {
            {G, G, Gy, Gy, Gy, G, G},
            {G, Gy, R, R, R, Gy, G},
            {Gy, R, R, R, R, R, Gy},
            {Gy, R, R, R, R, R, Gy},
            {Gy, R, R, R, R, R, Gy},
            {Gy, R, R, R, R, Bl, Gy},
            {Gy, R, R, R, R, R, Gy},
            {Gy, Gy, Gy, Gy, Gy, Gy, Gy},
        };
        String[][] castle = {
            {Gy, G, Gy, G, Gy, G, G, Gy, G, Gy, G, Gy},
            {Gy, Gy, Gy, Gy, Gy, G, G, Gy, Gy, Gy, Gy, Gy},
            {G, Gy, Gy, Gy, G, G, G, G, Gy, Gy, Gy, G},
            {G, Gy, Gy, Gy, G, G, G, G, Gy, Gy, Gy, G},
            {G, Gy, Gy, Gy, Gy, Gy, Gy, Gy, Gy, Gy, Gy, G},
            {G, Gy, Gy, Gy, Gy, R, R, Gy, Gy, Gy, Gy, G},
            {P, Gy, Gy, Gy, Gy, R, R, Gy, Gy, Gy, Gy, G}
        };
        String[][] inn = {
            {G, G, R, G, G, G, G, G},
            {G, R, R, R, G, G, G, G},
            {G, R, Y, R, G, G, G, G},
            {R, R, Y, R, R, R, R, R},
            {R, Y, Y, Y, R, R, R, R},
            {G, Y, R, Y, Y, Y, Y, G},
            {G, Y, R, Y, Y, Y, Y, G},
        };
        String[][] blacksmith = {
            {Gy, Gy, Gy, Gy, Gy, Gy, Gy},
            {G, Gy, Gy, Gy, Gy, Y, Y},
            {G, B, B, B, Gy, Y, Y},
            {G, Gy, B, Gy, Gy, Y, Y},
            {Gy, Gy, Gy, Gy, Gy, Gy, Gy}
        };
        String[][] player = {
            {Bu},
            {Bu}
        };
        placeGrid(mapArt, pathways, 4, 8);
        placeGrid(mapArt, forest, 8, 17);
        placeGrid(mapArt, forest, 0, 18);
        placeGrid(mapArt, forest2, 16, 15);
        placeGrid(mapArt, walls, 0, 25);
        placeGrid(mapArt, walls, 17, 25);
        placeGrid(mapArt, gates, 9, 24);
        placeGrid(mapArt, dungeon, 8, 1);
        placeGrid(mapArt, castle, 9, 37);
        placeGrid(mapArt, inn, 16, 30);
        placeGrid(mapArt, blacksmith, 1, 32);

        switch(playerLocation){
        case 0: placeGrid(mapArt, player, 4, 32);
        break;
        case 1: placeGrid(mapArt, player, 12, 19);
        break;
        case 2: placeGrid(mapArt, player, 20, 17);
        break;
        case 3: placeGrid(mapArt, player, 13, 27);
        break;
        case 4: placeGrid(mapArt, player, 15, 41);
        break;
        case 5: placeGrid(mapArt, player, 4, 36);
        break;
        case 6: placeGrid(mapArt, player, 22, 35);
        break;
        case 7: placeGrid(mapArt, player, 14, 9);
        //{"Forest1", "Forest2", "Forest3", "Kingdom Gates", "King's Castle", "Blacksmith", "Inn", "Dungeon"};    
        }
        
        // Print the pixel art
        for (String[] row : mapArt) {
            for (String color : row) {
                System.out.print(color);  // Print colored block
            }
            System.out.println();  // New line after each row
        }
    }
    public static void placeGrid(String[][] mapArt, String[][] locationArt, int startX, int startY) {
        for (int i = 0; i < locationArt.length; i++) {
            for (int j = 0; j < locationArt[0].length; j++) {
                // Ensure we don't go out of bounds
                if (startX + i < mapArt.length && startY + j < mapArt[0].length) {
                    mapArt[startX + i][startY + j] = locationArt[i][j];
                }
            }
        }
    }

    // Travel to a new location
    private static void travelToLocation() {
        displayMap2();
        System.out.print("Choose a location to travel to (1-" + locations.length + "): ");
        int destination = scanner.nextInt() - 1;

        if (destination < 0 || destination >= locations.length) {
            System.out.println("Invalid location.");
            System.out.println("Press Enter to continue");
            enter.nextLine();
            return;
        }

        // Check if the player is trying to travel to restricted areas
        if (!hasVisitedGates && !locations[destination].equals("Kingdom Gates")) {
            System.out.println("You must first visit the Kingdom Gates to gain access to other areas.");
            System.out.println("Press Enter to continue");
            enter.nextLine();
            return;
        }

        if (hasVisitedGates && !hasVisitedCastle && !locations[destination].equals("King's Castle")) {
            System.out.println("You must first visit the King's Castle to speak with the king.");
            System.out.println("Press Enter to continue");
            enter.nextLine();
            return;
        }

        int distance = distances[playerLocation][destination];
        int travelTime = (int) (distance / 0.1); // Travel time in minutes (0.1 meters per minute)

        System.out.println("\n---------------------------------------------------\n");
        System.out.println("Traveling to " + locations[destination] + "...");
        System.out.println("Distance: " + distance + " meters");
        System.out.println("Time taken: " + travelTime + " minutes");
        System.out.println("\n---------------------------------------------------\n");

        // Update game time
        time += travelTime / 60; // Convert minutes to hours
        if (time >= 24) time -= 24; // Reset time after 24 hours

        playerLocation = destination;
        System.out.println("You have arrived at " + locations[playerLocation] + " at " + time + ":00.");

        // Update flags if the player visits the Kingdom Gates or King's Castle
        if (locations[playerLocation].equals("Kingdom Gates")) {
            hasVisitedGates = true;
            talk("You have arrived at the Kingdom Gates. You were stopped by a guard.\n");
            talk("Guard: State your name, stranger.\n");
            scanner.nextLine(); // Consume the leftover newline character
            System.out.print("Enter your name: ");
            playername = scanner.nextLine(); // Read the player's name
            talk("Guard: Well, " + playername + ". Welcome to our kingdom, I'd suggest visiting the King first ");
            talk("before you continue on with your adventure.\n");
            System.out.println("Press Enter to continue.");
            enter.nextLine();
        } else if (locations[playerLocation].equals("King's Castle")) {
            hasVisitedCastle = true;
            talk("You meet the king. He tells you about the Goblin King and the lost sword shards.\n");
            talk("The King then gave you armor and a sword to ease your journey on defeating the goblins.");
            System.out.println("You have acquired armor and a sword.");
            System.out.println("Press Enter to continue.");
            enter.nextLine(); // Wait for the player to press Enter
        }

        // Random encounter check
        if (locations[playerLocation].startsWith("Forest")) {
            randomEncounter();
        }
    }

    // Display the map with distances
    private static void displayMap2() {
        displayMap();
        System.out.println("\n----------------- Map -----------------\n");
        for (int i = 0; i < locations.length; i++) {
            System.out.println((i + 1) + ". " + locations[i] + " - " + distances[playerLocation][i] + " meters away");
        }
    }

    // Random encounter with a goblin or riddler
    private static void randomEncounter() {
        Random random = new Random();
        int encounter = random.nextInt(2); // 0 = Goblin, 1 = Riddler

        if (encounter == 0) {
            talk("\nA wild goblin appears!");
            battleGoblin();
        } else {
            talk("\nA mysterious riddler approaches you...");
            solveRiddle();
        }
    }

    // Battle a goblin
    private static void battleGoblin() {
        int goblinHealth = 40;
        int goblinAttack = 6;
        int goblinHeals = 1;
        
        
        if(inbattle){
            if (battlemusic){
                try {
                    File audioFile = new File("C:\\Users\\drexl\\OneDrive\\Desktop\\Personal projects\\MyProject\\gobs\\battlemusic.wav");
    
                // Check if file exists
                if (!audioFile.exists()) {
                    throw new FileNotFoundException("Audio file not found: " + audioFile.getAbsolutePath());
                }
    
                // Get the audio input stream
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY); // Start looping sound while printing
                    System.out.println("Battle Start!");
            while (goblinHealth > 0 && playerHealth > 0) {
                System.out.println("\nYour Health: " + playerHealth);
                System.out.println("Goblin Health: " + goblinHealth);
                System.out.println("1. Attack");
                System.out.println("2. Defend");
                System.out.println("3. Heal");
                talk("Choose an action: ");
                int action = scanner.nextInt();

                switch (action) {
                    case 1:
                        int damage = calculateDamage(playerAttack, goblinAttack / 2, true);
                        goblinHealth -= damage;
                        talk("You attack the goblin for " + damage + " damage!");
                        break;
                    case 2:
                        playerStamina += 20;
                        talk("You defend and gain 20 stamina.");
                        break;
                    case 3:
                        if (playerStamina >= 20) {
                            playerHealth += 20;
                            playerStamina -= 20;
                            talk("You heal for 20 health!");
                        } else {
                            talk("Not enough stamina to heal!");
                        }
                        break;
                    default:
                        System.out.println("Invalid action.");
                }

                // Goblin's turn
                if (goblinHealth > 0) {
                    int goblinDamage = goblinAttack - (playerDefense / 2);
                    playerHealth -= goblinDamage;
                    talk("The goblin attacks you for " + goblinDamage + " damage!");

                    // Goblin heals if health is low
                    if (goblinHealth <= 10 && goblinHeals > 0) {
                        goblinHealth += 20;
                        goblinHeals--;
                        talk("The goblin heals itself for 20 health!");
                    }
                }
            
            }

            if (playerHealth > 0) {
                talk("You defeated the goblin!");
                goblinCrystals += 15;
                talk("You earned 15 goblin crystals!");
                clip.stop(); // Stop sound after printing
                clip.close();
                // Random chance to drop a sword shard
                Random random = new Random();
                if (random.nextInt(100) < 20) { // 20% chance to drop a shard
                    swordShards++;
                    talk("The goblin dropped a shard of the Goblin Destroyer Sword!");
                    talk("You now have " + swordShards + "/8 shards.");
                }
                inbattle=false;
                backgroundmusic = true;
            } else {
                
                inbattle=false;
                
                clip.stop(); // Stop sound after printing
                clip.close();
                inbattle=false;
                backgroundmusic = true;
                death("   YOU HAVE BEEN DEFEATED BY THE GOBLINS!!!   ");
                talk("You were defeated by the goblin...");
                talk("The goblin took 5 crystals while you we're knocked out.");
                goblinCrystals -= 5;
                System.out.println("Press Enter to continue.");
                enter.nextLine();
                respawnAtInn(); // Respawn at the Inn
            }
                    
                } catch (Exception e) {
                    System.err.println("Error playing sound: " + e.getMessage());
                }
            }
        }
        else if (!inbattle){
            backgroundmusic = false;
            inbattle = true;
        }
    }
        public static void death(String text) {     
                Clip clip = null;
                try {
                    
                    // Load the audio file from the resources folder
                   
                    File audioFile = new File("C:\\Users\\drexl\\OneDrive\\Desktop\\Personal projects\\MyProject\\gobs\\stop.wav");
    
                // Check if file exists
                if (!audioFile.exists()) {
                    throw new FileNotFoundException("Audio file not found: " + audioFile.getAbsolutePath());
                }
    
                // Get the audio input stream
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
                    // Obtain a clip to play the audio
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY); // Start looping sound while printing
            
                    // Print the text character by character
                    for (char letter : text.toCharArray()) {
                        System.out.print(letter);
                        Thread.sleep(170);
                    }
                }  catch (Exception e) {
                    System.err.println("Error playing sound: " + e.getMessage());
                } finally {
                    // Ensure the clip is stopped and closed
                    if (clip != null) {
                        clip.stop();
                        clip.close();
                    }
                }
            
        }       
    
    private static void respawnAtInn() {
        talk("You wake up at the Inn, feeling weak but alive.");
        playerLocation = findLocationIndex("Inn"); // Find the index of the Inn
        playerHealth = 100; // Reset health
        playerStamina = 0; // Reset stamina
        time = 6; // Reset time to 6 AM
        System.out.println("You are now at the Inn. Your health and stamina have been restored.");
        System.out.println("Press Enter to continue.");
        enter.nextLine();
    }

    // Helper method to find the index of a location by name
    private static int findLocationIndex(String locationName) {
        for (int i = 0; i < locations.length; i++) {
            if (locations[i].equals(locationName)) {
                return i;
            }
        }
        return -1; // Location not found
    }
    private static int calculateDamage(int attack, int defense, boolean isPlayer) {
        Random random = new Random();
        int damage = attack - defense;
        if (damage < 0) damage = 0; // Ensure damage is not negative

        // 20% chance for critical hit
        if (random.nextInt(100) < 20) {
            damage *= 2; // Double damage for critical hit
            if (isPlayer) {
                talk("Critical hit!");
            } else {
                talk("The enemy lands a critical hit!");
            }
        }

        return damage;
    }



    // Solve a riddle
    private static void solveRiddle() {
        Random random = new Random();
        int ran = random.nextInt(16);
        String answer = null;
        String[] correctanswer = {"piano", "Egg", "Promises", "Tomorrow", "Piano", "River", "Married", "2", "3", "Boys", "40 cents", "Friday", "10",
                                  "M", "5,050", "240"};
        switch(ran){
            case 0: talk("The riddler asks: What has keys but can't open locks?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 1: talk("The riddler asks: What has to be broken before you can use it?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 2: talk("The riddler asks: What gets broken without being held?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 3: talk("The riddler asks: What’s always coming but never arrives?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 4: talk("The riddler asks: I am full of keys but cannot open any door. What am I? ");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 5: talk("The riddler asks: What can run but never walks, has a mouth but never eats, and has a bed but never sleeps?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 6: talk("The riddler asks: There was a plane crash and every single person died. Which people survived?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 7: talk("The riddler asks: Two people are walking into town. Another two people are going the other way. How many people are going into town altogether?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 8: talk("The riddler asks: There are two ducks in front of a duck, two ducks behind a duck, and a duck in the middle. How many ducks are there?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 9: talk("The riddler asks: Bill has as many sisters as he has brothers. Are there more boys or girls in the family?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 10: talk("The riddler asks: An apple is 40 cents, a banana is 60 cents, and a grapefruit is 80 cents. How much is a pear?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 11: talk("The riddler asks: The day before two days after the day before tomorrow is Saturday. What day is it today?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 12: talk("The riddler asks: 25, 20, 16, 13, 11, … What’s next?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 13: talk("The riddler asks: In this series, what letter is next: Y, Z, V, W, S, T, P, Q?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 14: talk("The riddler asks: What is the sum when you total the numbers from one to 100?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;
            case 15: talk("The riddler asks: Divide 50 by half and add 20, then multiply by 2. What is the answer?");
                    System.out.print("Your answer: ");
                    answer = scanner.next();
                    break;          
        }
        

        if (answer.equalsIgnoreCase(correctanswer[ran])) {
            talk("Correct! You receive 10 crystals.");
            goblinCrystals += 10;
        } else {
            talk("Incorrect. The answer was "+correctanswer[ran]);
            talk("The riddler sneaks behind you and took 5 crystals");
            goblinCrystals -= 5;
        }
        
    }

    // Display player stats
    private static void displayStats() {
        System.out.println("\n--- Player Stats ---");
        System.out.println("Health: " + playerHealth);
        System.out.println("Stamina: " + playerStamina);
        System.out.println("Goblin Crystals: " + goblinCrystals);
        System.out.println("Sword Shards: " + swordShards + "/8");
        System.out.println("Current Location: " + locations[playerLocation]);
        System.out.println("Time: " + time + ":00");
    }

    // Rest at the inn
    private static void restAtInn() {
        if (locations[playerLocation].equals("Inn")) {
            talk("You rest at the inn and skip nighttime.");
            time = 6; // Reset time to 6 AM
            playerHealth = 100; // Fully heal the player
        } else {
            System.out.println("You can only rest at the inn.");
        }
    }

    // Visit the blacksmith
    private static void visitBlacksmith() {
        if (locations[playerLocation].equals("Blacksmith")) {
            System.out.println("\n--- Blacksmith ---");
            System.out.println("1. Upgrade Weapon (50 crystals)");
            System.out.println("2. Upgrade Armor (50 crystals)");
            System.out.println("3. Reassemble Goblin Destroyer Sword (requires 8 shards and 500 crystals)");
            talk("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (weaponUpgrades >= 10) {
                        System.out.println("You have reached the maximum number of weapon upgrades.");
                    } else if (goblinCrystals >= 50) {
                        playerAttack += 5;
                        goblinCrystals -= 50;
                        weaponUpgrades++;
                        talk("Your weapon has been upgraded! Attack +5.");
                    } else {
                        System.out.println("Not enough crystals.");
                    }
                    break;
                case 2:
                    if (armorUpgrades >= 10) {
                        System.out.println("You have reached the maximum number of armor upgrades.");
                    } else if (goblinCrystals >= 50) {
                        playerDefense += 5;
                        goblinCrystals -= 50;
                        armorUpgrades++;
                        talk("Your armor has been upgraded! Defense +5.");
                    } else {
                        System.out.println("Not enough crystals.");
                    }
                    break;
                case 3:
                    if (swordShards >= 8 && goblinCrystals >= 500) {
                        hasSword = true;
                        swordShards -= 8; // Deduct sword shards
                        goblinCrystals -= 500; // Deduct crystals
                        craftSword("YOU HAVE CRAFTED THE GOBLIN DESTROYER SW0RD!!!");
                    } else {
                        if (swordShards < 8) {
                            System.out.println("You need 8 sword shards to reassemble the sword.");

                        }
                        if (goblinCrystals < 500) {
                            System.out.println("You need 500 crystals to reassemble the sword.");
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("You can only visit the blacksmith at the Blacksmith location.");
        }
    }

    public static void craftSword(String text) {
        
        
        Clip clip = null;
        try {
            // Load the audio file from the resources folder
            File audioFile = new File("C:\\Users\\drexl\\OneDrive\\Desktop\\Personal projects\\MyProject\\gobs\\anvil.wav");
    
                // Check if file exists
                if (!audioFile.exists()) {
                    throw new FileNotFoundException("Audio file not found: " + audioFile.getAbsolutePath());
                }
    
                // Get the audio input stream
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Obtain a clip to play the audio
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Start looping sound while printing

            // Print the text character by character
            for (char letter : text.toCharArray()) {
                System.out.print(letter);
                Thread.sleep(80);
            }
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        } finally {
            // Ensure the clip is stopped and closed
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        }
    
    }

    // Enter the dungeon
    private static void enterDungeon() {
        if (locations[playerLocation].equals("Dungeon")) {
            if (hasSword) {
                talk("\nYou enter the dungeon and face the Goblin King!");
                battleGoblinKing();
            } else {
                System.out.println("You need the Goblin Destroyer Sword to enter the dungeon.");
            }
        } else {
            System.out.println("You can only enter the dungeon at the Dungeon location.");
        }
    }

    // Battle the Goblin King
    private static void battleGoblinKing() {
        int goblinKingHealth = 200;
        int goblinKingAttack = 10;
        int goblinKingHeals = 2;
        
        if(inbattle){
            if (battlemusic){
                try {
                    
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("/battlemusic.wav"));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY); // Start looping sound while printing
                    System.out.println("Battle Start!");
            System.out.println("Battle Start!");
            while (goblinKingHealth > 0 && playerHealth > 0) {
                System.out.println("\nYour Health: " + playerHealth);
                System.out.println("Goblin King Health: " + goblinKingHealth);
                System.out.println("1. Attack");
                System.out.println("2. Defend");
                System.out.println("3. Heal");
                talk("Choose an action: ");
                int action = scanner.nextInt();

                switch (action) {
                    case 1:
                        int damage = playerAttack - (goblinKingAttack / 2);
                        goblinKingHealth -= damage;
                        talk("You attack the Goblin King for " + damage + " damage!");
                        break;
                    case 2:
                        playerStamina += 10;
                        talk("You defend and gain 10 stamina.");
                        break;
                    case 3:
                        if (playerStamina >= 20) {
                            playerHealth += 20;
                            playerStamina -= 20;
                            talk("You heal for 20 health!");
                        } else {
                            System.out.println("Not enough stamina to heal!");
                        }
                        break;
                    default:
                        System.out.println("Invalid action.");
                }

                // Goblin King's turn
                if (goblinKingHealth > 0) {
                    int goblinKingDamage = goblinKingAttack - (playerDefense / 2);
                    playerHealth -= goblinKingDamage;
                    talk("The Goblin King attacks you for " + goblinKingDamage + " damage!");

                    // Goblin King heals if health is low
                    if (goblinKingHealth <= 50 && goblinKingHeals > 0) {
                        goblinKingHealth += 40;
                        goblinKingHeals--;
                        talk("The Goblin King heals itself for 40 health!");
                    }
                }
            }

            if (playerHealth > 0) {
                talk("You defeated the goblin!");
                goblinCrystals += 10;
                talk("You earned 10 goblin crystals!");
                clip.stop(); // Stop sound after printing
                clip.close();
                // Random chance to drop a sword shard
                Random random = new Random();
                if (random.nextInt(100) < 20) { // 20% chance to drop a shard
                    swordShards++;
                    talk("The goblin dropped a shard of the Goblin Destroyer Sword!");
                    talk("You now have " + swordShards + "/8 shards.");
                }
                inbattle=false;
                backgroundmusic = true;
                finalboss=false;
            } else {
                clip.stop(); // Stop sound after printing
                clip.close();
                inbattle=false;
                backgroundmusic = true;
                finalboss=false;
                death("   YOU HAVE BEEN DEFEATED BY THE GOBLINS!!!   ");
                talk("You were defeated by the goblin...");
                System.out.println("Press Enter to continue.");
                enter.nextLine();
                respawnAtInn(); // Respawn at the Inn
            }
                } catch (Exception e) {
                    System.err.println("Error playing sound: " + e.getMessage());
                }        
            }
        }
        else if (!inbattle){
            backgroundmusic = false;
            inbattle = true;
            finalboss=true;
        }
    }
    public static void talk(String text) {
        PrintWriter out = new PrintWriter(System.out, true);
        
        if (dialogue) {
            Clip clip = null;
            try {
                // Use absolute path to load the audio file
                File audioFile = new File("src\\resources\\talk.wav");
    
                // Check if file exists
                if (!audioFile.exists()) {
                    throw new FileNotFoundException("Audio file not found: " + audioFile.getAbsolutePath());
                }
    
                // Get the audio input stream
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
    
                // Obtain a clip to play the audio
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Start looping sound while printing
    
                // Print the text character by character
                for (char letter : text.toCharArray()) {
                    System.out.print(letter);
                    Thread.sleep(75);
                }
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            } finally {
                // Ensure the clip is stopped and closed
                if (clip != null) {
                    clip.stop();
                    clip.close();
                }
            }
        } else {
            out.println(text);
        }
    }
}