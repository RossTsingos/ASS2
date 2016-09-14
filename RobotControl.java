package control;

import java.util.Scanner;
import robot.Robot;

// Robot Assignment for Programming 1 s2 2016
// Adapted by Caspar from original Robot code in RobotImpl.jar written by Dr Charles Thevathayan
public class RobotControl implements Control {
	private Robot robot;
	private int height = 2;
	private int width = 1;
	private int depth = 0;

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
		int numberofBars = 0;// set variable to 0
		while (numberofBars < 1 || numberofBars > 6) {// starts a loop which
														// will ensure number of
														// bars are is in a	
														// correct range
			System.out.println("Enter number of bars(min 1 / max 6): ");
			if (input.hasNextInt()) {
				numberofBars = input.nextInt(); // if input isn't an integer it
												// will make 'number of bars =
												// 0'
												// which will cause the command
												// to be recalled.
			} else {
				input.next();
				numberofBars = 0;// set variable to 0 if condition met
			}

		}

		int barHeights[] = new int[numberofBars];// set variables
		int countofBars = 1; // set variable to 1
		for (int a = 0; a <= numberofBars - 1; a = a + 1) {

			while (barHeights[a] <= 0 || barHeights[a] >= 8) {// starts a loop
																// which will
																// ensure bar
																// heights are
																// is in a
																// correct range
				System.out.println("Enter height of bar " + countofBars + " of " + numberofBars + "(min 1 / max 7): ");
				if (input.hasNextInt()) {
					barHeights[a] = input.nextInt(); // if input isn't an
														// integer it will make
														// 'barheights = 0'
														// which will recall the
														// last command.
				} else {
					input.next();
					barHeights[a] = 0;// set variable to 0 if condition met
				}
			}
			countofBars += 1;// ensures the countofbars increases with each loop
		}

		int numberofBlocks = 0;// set variable to 0
		while (numberofBlocks < 1 || numberofBlocks > 6) {// starts a loop which
															// will ensure
															// number of blocks
															// are is in a
															// correct range
			System.out.println("Enter number of blocks(min 1 / max 6): ");
			if (input.hasNextInt()) {
				numberofBlocks = input.nextInt(); // if input isn't an integer
													// it will make
													// 'numberofblocks = 0'
													// which will recall the
													// last command.
			} else {
				input.next();
				numberofBlocks = 0;// set variable to 0 if condition met
			}
		}

		int blockHeights[] = new int[numberofBlocks];
		int countofBlocks = 1;// set variable to 1
		for (int b = 0; b <= numberofBlocks - 1; b = b + 1) {
			while (blockHeights[b] <= 0 || blockHeights[b] >= 4) {// starts a
																	// loop
																	// which
																	// will
																	// ensure
																	// block
																	// heights
																	// are is in
																	// a correct
																	// range
				System.out.println(
						"Enter height of block " + countofBlocks + " of " + numberofBlocks + "(min 1 / max 3): ");
				if (input.hasNextInt()) {
					blockHeights[b] = input.nextInt(); // if input isn't an
														// integer it will make
														// 'blockheight = 0'
														// which will recall the
														// last command.
				} else {
					input.next();
					blockHeights[b] = 0;// set variable to 0 if condition met
				}
			}
			countofBlocks += 1;// ensures the countofblocks increases with each
								// loop
		}

		System.out.print("Input Sucessfully Compleated");// prints new line

		// initialise the robot
		robot.init(barHeights, blockHeights, height, width, depth);

		// assignment part B implemented here
		int targetHeight1 = 0; 
		int targetHeight2 = 0; 
		int initialBlock = 0;
		int blockHeight = blockHeights[initialBlock];
		int sourceHeight = 0;

		while (initialBlock < blockHeights.length) {
			sourceHeight += blockHeights[initialBlock];
			initialBlock++;
		}

		initialBlock--;

		while (height < sourceHeight + 1) {
			// Raising 1
			robot.up();

			// Current height of arm1 being incremented by 1
			height++;
		}

		final int EXTEND_DISTANCE = 10;
		final int SECOND_ARM_DEPTH = 1;

		// currentBar number 3 - 8
		int currentBar = 0;

		while (initialBlock >= 0) {

			blockHeight = blockHeights[initialBlock];
			// decide to put the block on column (extendAmt - contrctAmt)
			int contractAmt;
			switch (blockHeight) {
			case 1:
				contractAmt = 9; // 9 means 10 - 1 (Column 1)
				break;
			case 2:
				contractAmt = 8; // 8 means 10 - 2 (Column 2)
				break;
			case 3:
				contractAmt = EXTEND_DISTANCE - currentBar - 3; // for blockHeight =
																// 3, the Column
																// number is
																// dynamic which
																// is related to
																// currentBar
				break;
			default:
				contractAmt = 9;
				break;
			}

			// Bring arm 2 to column 10
			while (width < EXTEND_DISTANCE) {
				// moving 1 step horizontally
				robot.extend();

				// Current width of arm2 being incremented by 1
				width++;
			}

			while (height - depth > sourceHeight + 1) {
				// lowering third arm
				robot.lower();

				// current depth of arm 3 being incremented
				depth++;
			}

			// picking the topmost block
			robot.pick();

			// When you pick the top block height of source decreases
			sourceHeight -= blockHeight;

			int maxHtRaiseBackward = sourceHeight;

			if (blockHeight == 1) {
				for (int pointerBar = 0; pointerBar < 6; pointerBar++) {
					if (barHeights[pointerBar] > maxHtRaiseBackward) {
						maxHtRaiseBackward = barHeights[pointerBar];
					}
				}
				if (maxHtRaiseBackward < targetHeight2) {
					maxHtRaiseBackward = targetHeight2;
				}
				if (maxHtRaiseBackward < targetHeight1) {
					maxHtRaiseBackward = targetHeight1;
				}
			} else if (blockHeight == 2) {
				for (int pointerBar = 0; pointerBar < 6; pointerBar++) {
					if (barHeights[pointerBar] > maxHtRaiseBackward) {
						maxHtRaiseBackward = barHeights[pointerBar];
					}
				}
				if (maxHtRaiseBackward < targetHeight2) {
					maxHtRaiseBackward = targetHeight2;
				}
			} else if (blockHeight == 3) {
				for (int pointerBar = currentBar; pointerBar < 6; pointerBar++) {
					if (barHeights[pointerBar] > maxHtRaiseBackward) {
						maxHtRaiseBackward = barHeights[pointerBar];
					}
				}
			}

			// raising third arm to the optimistic height according to
			// maxHtRaiseBackward
			while (height - SECOND_ARM_DEPTH - maxHtRaiseBackward < depth + blockHeight) {
				robot.raise();
				depth--;
			}

			while (contractAmt > 0) {
				robot.contract();
				contractAmt--;
				width--;
			}

			// lowering third arm
			if (blockHeight == 1) {
				while ((height - 1) - depth - blockHeight > targetHeight1) {
					robot.lower();
					depth++;
				}

				// dropping the block
				robot.drop();

				// The height of currentBar increases by block just placed
				targetHeight1 += blockHeight;

			} else if (blockHeight == 2) {
				while ((height - 1) - depth - blockHeight > targetHeight2) {
					robot.lower();
					depth++;
				}

				// dropping the block
				robot.drop();

				// The height of currentBar increases by block just placed
				targetHeight2 += blockHeight;

			} else if (blockHeight == 3) {
				while ((height - 1) - depth - blockHeight > barHeights[currentBar]) {
					robot.lower();
					depth++;
				}

				// dropping the block
				robot.drop();

				// The height of currentBar increases by block just placed
				barHeights[currentBar] += blockHeight;
			}

			int maxHtRaiseForward = sourceHeight;

			if (blockHeight == 1) {
				for (int pointerBar = 0; pointerBar < 6; pointerBar++) {
					if (barHeights[pointerBar] > maxHtRaiseForward) {
						maxHtRaiseForward = barHeights[pointerBar];
					}
				}
				if (maxHtRaiseForward < targetHeight2) {
					maxHtRaiseForward = targetHeight2;
				}

				// raising the third arm to the optimistic height according to
				// maxHtRaiseForward
				if (targetHeight1 < maxHtRaiseForward) {
					if (sourceHeight > 0) {
						while (depth > height - SECOND_ARM_DEPTH - maxHtRaiseForward) {
							robot.raise();
							depth--;
						}
					}
				}
			} else if (blockHeight == 2) {
				for (int pointerBar = 0; pointerBar < 6; pointerBar++) {
					if (barHeights[pointerBar] > maxHtRaiseForward) {
						maxHtRaiseForward = barHeights[pointerBar];
					}
				}

				// raising the third arm to the optimistic height according to
				// maxHtRaiseForward
				if (targetHeight2 < maxHtRaiseForward) {
					if (sourceHeight > 0) {
						while (depth > height - SECOND_ARM_DEPTH - maxHtRaiseForward) {
							robot.raise();
							depth--;
						}
					}
				}
			} else if (blockHeight == 3) {
				for (int pointerBar = currentBar; pointerBar < 6; pointerBar++) {
					if (barHeights[pointerBar] > maxHtRaiseForward) {
						maxHtRaiseForward = barHeights[pointerBar];
					}
				}

				// raising the third arm to the optimistic height according to
				// maxHtRaiseForward
				if (barHeights[currentBar] < maxHtRaiseForward) {
					if (sourceHeight > 0) {
						while (depth > height - SECOND_ARM_DEPTH - maxHtRaiseForward) {
							robot.raise();
							depth--;
						}
					}
				}
			}

			// point to the next bar if block height = 3
			if (blockHeight == 3) {
				currentBar++;
			}

			// point to the next block
			// topmost block is assumed to be 3 for parts (a) and (b)
			blockHeight = blockHeights[initialBlock--];

		}
	}
}
