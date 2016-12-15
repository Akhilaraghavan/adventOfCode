package com.aoc;

public class _Day15_RotatingDiscs {

	
	/*Disc #1 has 17 positions; at time=0, it is at position 5.
			Disc #2 has 19 positions; at time=0, it is at position 8.
			Disc #3 has 7 positions; at time=0, it is at position 1.
			Disc #4 has 13 positions; at time=0, it is at position 7.
			Disc #5 has 5 positions; at time=0, it is at position 1.
			Disc #6 has 3 positions; at time=0, it is at position 0.*/

	
	/*Example input . discPositions[0] = 4;
	discPositions[1] = 1;
	discSize[0] = 5;
	discSize[1] = 2;*/
	
	public static void main(String args[]) throws Exception {
		
		int[] discSize = new int[7];
		int[] discPositions = new int[7];
		
		int time = 0;
		int capsulePos = 0;
		
		discSize[0] = 17;
		discSize[1] = 19;
		discSize[2] = 7;
		discSize[3] = 13;
		discSize[4] = 5;
		discSize[5] = 3;
		discSize[6] = 11;
		
		discPositions[0] = 5;
		discPositions[1] = 8;
		discPositions[2] = 1;
		discPositions[3] = 7;
		discPositions[4] = 1;
		discPositions[5] = 0;
		discPositions[6] = 0;
		
		
		while (true) {
			int position = -1;
			boolean found = true;
			while(capsulePos < discSize.length) {
				int pos = (((time + discPositions[capsulePos]) % discSize[capsulePos]) +  capsulePos + 1) % discSize[capsulePos];
				
				if(position == -1) {
					position = pos;  
				} else if(position != pos) {
					found= false;
					break;
				}
				++capsulePos;
			}
			if(!found) {
				System.out.println("Not found at " + (time));
				++time;
				capsulePos = 0;
			} else {
				System.out.println("Found at " + (time));
				break;
			}
			
		}

	}
}
