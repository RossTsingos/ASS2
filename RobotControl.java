package control;

import java.util.Scanner;
import robot.Robot;

// Robot Assignment for Programming 1 s2 2016
// Adapted by Caspar from original Robot code in RobotImpl.jar written by Dr Charles Thevathayan
public class RobotControl implements Control {
	private Robot robot;

	private static final int MAX_HEIGHT = 12;

	private int height = 2;
	private int width = 1;
	private int depth = 0;
	private int extendAmt = 10;
	int sourceHt = 12;
	int blockHt = 3;

	// called by RobotImpl
	// the unused arrays are based on cmd line params to RobotImpl not used in
	// this assignment
	@Override
	public void control(Robot robot, int barHeightsUnused[], int blockHeightsUnused[]) {
		// save robot so we can access it from other methods
		this.robot = robot;

		// ASSIGNMENT PART A
		// replace this code with a console based menu to populate the arrays
		
		Scanner input = new Scanner(System.in);
		int numberofBars = 0;//set variable to 0
		while (numberofBars < 1 || numberofBars > 6) {//starts a loop which will ensure number of bars are is in a correct range
			System.out.println("Enter number of bars(min 1 / max 6): ");
			if (input.hasNextInt()) {
				numberofBars = input.nextInt(); // if input isn't an integer it will make 'number of bars = 0' 
				                               //which will cause the command to be recalled.								
			} else {
				input.next();
				numberofBars = 0;//set variable to 0 if condition met
			}

		}

		int barHeights[] = new int[numberofBars];//set variables 
		int countofBars = 1; //set variable to 1
		for (int a = 0; a <= numberofBars - 1; a = a + 1) {

			while (barHeights[a] <= 0 || barHeights[a] >= 8) {//starts a loop which will ensure bar heights are is in a correct range
				System.out.println("Enter height of bar " + countofBars + " of " + numberofBars + "(min 1 / max 7): ");
				if (input.hasNextInt()) {
					barHeights[a] = input.nextInt(); // if input isn't an integer it will make 'barheights = 0' 
                                                    //which will recall the last command.							
				} else {
					input.next();
					barHeights[a] = 0;//set variable to 0 if condition met
				}
			}
			countofBars += 1;//ensures the countofbars increases with each loop
		}

		int numberofBlocks = 0;//set variable to 0
		while (numberofBlocks < 1 || numberofBlocks > 6) {//starts a loop which will ensure number of blocks are is in a correct range
			System.out.println("Enter number of blocks(min 1 / max 6): ");
			if (input.hasNextInt()) {
				numberofBlocks = input.nextInt(); // if input isn't an integer it will make 'numberofblocks = 0' 
                                                  //which will recall the last command.
			} else {
				input.next();
				numberofBlocks = 0;//set variable to 0 if condition met
			}
		}

		int blockHeights[] = new int[numberofBlocks];
		int countofBlocks = 1;//set variable to 1 
		for (int b = 0; b <= numberofBlocks - 1; b = b + 1) {
			while (blockHeights[b] <= 0 || blockHeights[b] >= 4) {//starts a loop which will ensure block heights are is in a correct range
				System.out.println(
						"Enter height of block " + countofBlocks + " of " + numberofBlocks + "(min 1 / max 3): ");
				if (input.hasNextInt()) {
					blockHeights[b] = input.nextInt(); // if input isn't an integer it will make 'blockheight = 0' 
                                                       //which will recall the last command.
				} else {
					input.next();
					blockHeights[b] = 0;//set variable to 0 if condition met
				}
			}
			countofBlocks += 1;//ensures the countofblocks increases with each loop
		}

		System.out.print("Input Sucessfully Compleated");//prints new line

		// initialise the robot
		robot.init(barHeights, blockHeights, height, width, depth);
	
	

		// a simple private method to demonstrate how to control (assignment
		// PART B)
		

		// assignment part B implemented here
	
		
			
			// Internally the Robot object maintains the value for Robot height(h),
			// arm-width (w) and picker-depth (d).

			// These values are displayed for your convenience
			// These values are initialised as h=2 w=1 and d=0

			// When you call the methods up() or down() h will be changed
			// When you call the methods extend() or contract() w will be changed
			// When you call the methods lower() or raise() d will be changed

			// sample code to get you started
			// Try running this program with obstacle 555555 and blocks of height
			// 2222 (default)
			// It will work for fisrt block only
			// You are free to introduce any other variables

			int h = 2; // Initial height of arm 1
			int w = 1; // Initial width of arm 2
			int d = 0; // Initial depth of arm 3

			int sourceHt = 12;

			// For Parts (a) and (b) assume all four blocks are of the same height
			// For Part (c) you need to compute this from the values stored in the
			// array blockHeights
			// i.e. sourceHt = blockHeights[0] + blockHeights[1] + ... use a loop!

			int targetCol1Ht = 0; // Applicable only for part (c) - Initially empty
			int targetCol2Ht = 0; // Applicable only for part (c) - Initially empty

			// height of block just picked will be 3 for parts A and B
			// For part (c) this value must be extracing the topmost unused value
			// from the array blockHeights

			int blockHt = 3;

			// clearance should be based on the bars, the blocks placed on them,
			// the height of source blocks and the height of current block

			// Initially clearance will be determined by the blocks at source
			// (3+3+3+3=12)
			// as they are higher than any bar and block-height combined

			int clearence = 12;

			// Raise it high enough - assumed max obstacle = 4 < sourceHt

			// this makes sure robot goes high enough to clear any obstacles
			while (h < clearence + 1) {
				// Raising 1
				robot.up();

				// Current height of arm1 being incremented by 1
				h++;
			}

			System.out.println("Debug 1: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			// this will need to be updated each time a block is dropped off
			int extendAmt = 10;

			// Bring arm 2 to column 10
			while (w < extendAmt) {
				// moving 1 step horizontally
				robot.extend();

				// Current width of arm2 being incremented by 1
				w++;
			}

			System.out.println("Debug 2: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			// lowering third arm - the amount to lower is based on current height
			// and the top of source blocks

			// the position of the picker (bottom of third arm) is determined by h
			// and d
			while (h - d > sourceHt + 1) {
				// lowering third arm
				robot.lower();

				// current depth of arm 3 being incremented
				d++;
			}

			// picking the topmost block
			robot.pick();

			// topmost block is assumed to be 3 for parts (a) and (b)
			blockHt = 3;

			// When you pick the top block height of source decreases
			sourceHt -= blockHt;

			// raising third arm all the way until d becomes 0
			while (d > 0) {
				robot.raise();
				d--;
			}

			System.out.println("Debug 3: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			// decide to put the block on column (extendAmt - contrctAmt)
			int contractAmt = 7;

			// Must be a variable. Initially contract by 3 units to get to column 3
			// where the first bar is placed (from column 10)

			while (contractAmt > 0) {
				robot.contract();
				contractAmt--;
			}

			System.out.println("Debug 4: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			// You need to lower the third arm so that the block sits just above the
			// bar
			// For part (a) all bars are initially set to 7
			// For Parts (b) and (c) you must extract this value from the array
			// barHeights

			int currentBar = 0;

			
			// lowering third arm
			while ((h - 1) - d - blockHt > barHeights[currentBar]) {
				robot.lower();
				d++;
			}

			System.out.println("Debug 5: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			// dropping the block
			robot.drop();

			// The height of currentBar increases by block just placed
			barHeights[currentBar] += blockHt;

			// raising the third arm all the way
			while (d > 0) {
				robot.raise();
				d--;
			}
			System.out.println("Debug 6: height(arm1)= " + h + " width (arm2) = " + w + " depth (arm3) =" + d);

			// This just shows the message at the end of the sample robot run -
			// you don't need to duplicate (or even use) this code in your program.

			/*
			 * JOptionPane.showMessageDialog(null,
			 * "You have moved one block from source " +
			 * "to the first bar position.\n" + "Now you may modify this code or " +
			 * "redesign the program and come up with " +
			 * "your own method of controlling the robot.", "Helper Code Execution",
			 * JOptionPane.INFORMATION_MESSAGE);
			 **/

			// You have moved one block from source to the first bar position.
			// You should be able to get started now.
		}
	}
