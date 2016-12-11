package com.aoc;

import java.util.Arrays;
import java.util.List;

public class _Day11_RadioIsotopeGenerator {

	private static final String part2 = "The first floor contains a polonium generator, a thulium generator, a elerium generator, a elerium-compatible microchip, a dilithium generator, a dilithium-compatible microchip, a thulium-compatible microchip, a promethium generator, a ruthenium generator, a ruthenium-compatible microchip, a cobalt generator, and a cobalt-compatible microchip."
+ "\nThe second floor contains a polonium-compatible microchip and a promethium-compatible microchip."
+ "\nThe third floor contains nothing relevant."
+ "\nThe fourth floor contains nothing relevant.";
	
	private static final String part1 = "The first floor contains a polonium generator, a thulium generator, a thulium-compatible microchip, a promethium generator, a ruthenium generator, a ruthenium-compatible microchip, a cobalt generator, and a cobalt-compatible microchip."
+ "\nThe second floor contains a polonium-compatible microchip and a promethium-compatible microchip."
+ "\nThe third floor contains nothing relevant."
+ "\nThe fourth floor contains nothing relevant.";
	
	private static final String part3A = "The first floor contains a promethium generator and a promethium-compatible microchip."
+ "\nThe second floor contains a cobalt generator, a curium generator, a ruthenium generator, and a plutonium generator."
+ "\nThe third floor contains a cobalt-compatible microchip, a curium-compatible microchip, a ruthenium-compatible microchip, and a plutonium-compatible microchip."
+ "\nThe fourth floor contains nothing relevant.";
	
	private static final String part3B = "The first floor contains a promethium generator, a elerium generator, a elerium-compatible microchip, a dilithium generator, a dilithium-compatible microchip and a promethium-compatible microchip."
			+ "\nThe second floor contains a cobalt generator, a curium generator, a ruthenium generator, and a plutonium generator."
			+ "\nThe third floor contains a cobalt-compatible microchip, a curium-compatible microchip, a ruthenium-compatible microchip, and a plutonium-compatible microchip."
			+ "\nThe fourth floor contains nothing relevant.";
				
	
	
	
	public enum CHIP_GEN {
		HM("hydrogen-compatible microchip"), 
		LM("lithium-compatible microchip"), 
		HG("hydrogen generator"), 
		LG("lithium generator"), 
		PG("polonium generator"), 
		TG("thulium generator"), 
		TM("thulium-compatible microchip"), 
		PRG("promethium generator"), 
		RG("ruthenium generator"), 
		RM("ruthenium-compatible microchip"),
		CG("cobalt generator"), 
		CM("cobalt-compatible microchip"), 
		PM("polonium-compatible microchip"), 
		PRM("promethium-compatible microchip"),	
		EG("elerium generator"), 
		EM("elerium-compatible microchip"),
		DG("dilithium generator"), 
		DM("dilithium-compatible microchip");
		
		public String name;

		private CHIP_GEN(String name) {
			this.name = name;
		}

		public static String getChipOrGen(String name) {
			for (CHIP_GEN c : CHIP_GEN.values()) {
				if (name.equals(c.name)) {
					return c.toString();
				}
			}
			return null;
		}
	}

	public static void main(String args[]) {
		System.out.println(getMinimumSteps(Arrays.asList(part1.split("\n")), 12));
		System.out.println(getMinimumSteps(Arrays.asList(part2.split("\n")), 16));
		System.out.println(getMinimumSteps(Arrays.asList(part3A.split("\n")), 12));
		System.out.println(getMinimumSteps(Arrays.asList(part3B.split("\n")), 16));
	}

	private static int getMinimumSteps(List<String> instructions, int size) {
		int minSteps = 0;
		String[][] matrix = new String[4][size];
		int index = 2;
		for (String ins : instructions) {
			ins = ins.replaceAll("\\.", "");
			if (ins.contains("The first floor contains ")) {
				String[] split = ins.substring("The first floor contains ".length() + 1).split(",");
	
	
				for (String s : split) {
					String[] split2 = s.split(" a ");
					if (s.contains(" and a ")) {
						split2 = s.split(" and a ");
					}
					for (String s1 : split2) {
						if (s1.contains("generator") || s1.contains("microchip")) {
							matrix[0][index++] = CHIP_GEN.getChipOrGen(s1.trim().replace(" a ", ""));
						}
					}
				}
			} else if (ins.contains("The second floor contains ")) {
				String[] split = ins.substring("The first floor contains ".length() + 1).split(",");
				for (String s : split) {
					String[] split2 = s.split("a ");
					for (String s1 : split2) {
						if (s1.contains("generator") || s1.contains("microchip")) {
							matrix[1][index++] = CHIP_GEN.getChipOrGen(s1.trim().replace(" and", ""));
						}
					}
				}
	
			} else if (ins.contains("The third floor contains ")) {
				String[] split = ins.substring("The third floor contains ".length() + 1).split(",");
				for (String s : split) {
					String[] split2 = s.split("a ");
					for (String s1 : split2) {
						if (s1.contains("generator") || s1.contains("microchip")) {
							matrix[2][index++] = CHIP_GEN.getChipOrGen(s1.trim().replace(" and", ""));
						}
					}
				}
	
			} else if (ins.contains("The fourth floor contains ")) {
				String[] split = ins.substring("The fourth floor contains ".length() + 1).split(",");
				for (String s : split) {
					String[] split2 = s.split("a ");
					for (String s1 : split2) {
						if (s1.contains("generator") || s1.contains("microchip")) {
							matrix[3][index++] = CHIP_GEN.getChipOrGen(s1.trim().replace(" and", ""));
						}
					}
				}
	
			}
		}
		
		//Actual code starts here!!!
		int elevatorPos = 0;
		int noOfChips = 0;
		while (true) {
			if (elevatorPos == 3) {
				return minSteps;
			}
			for (int i = 2; i < matrix[elevatorPos].length; i++) {
				if(noOfChips >=1) break;
				if (matrix[elevatorPos][i] == null)
					continue;
				String chip = matrix[elevatorPos][i];
				if (chip == null)
					continue;
				String matching = chip.endsWith("G") ? chip.substring(0, chip.length() - 1) + "M"
						: chip.substring(0, chip.length() - 1) + "G";
				
				// There exists a match in the next floor
				for (int j = 2; j < matrix[elevatorPos + 1].length; j++) {
					if (noOfChips >= 1) {
						break;
					}
					String name = matrix[elevatorPos + 1][j];
					if (name == null)
						continue;
					if (name.equals(matching)) {
						matrix[elevatorPos + 1][i] = matrix[elevatorPos][i];
						matrix[elevatorPos][i] = null;
						noOfChips++;
	
					}
				}
				
				// There exists a match in the same floor
				for (int j = 2; j < matrix[elevatorPos].length; j++) {
					if (noOfChips >= 1) {
						break;
					}
					String name = matrix[elevatorPos][j];
					if (name == null)
						continue;
					if (name.equals(matching)) {
						if(noOfChips == 0) {
							matrix[elevatorPos + 1][i] = matrix[elevatorPos][i];
							matrix[elevatorPos][i] = null;
							noOfChips++;
							matrix[elevatorPos + 1][j] = matrix[elevatorPos][j];
							matrix[elevatorPos][j] = null;
							noOfChips++;
						}
					}
				}
	
			}
	
			// There exists no match in the same floor
			for (int j = 2; j < matrix[elevatorPos].length; j++) {
				if (noOfChips >= 2) {
					break;
				}
				String name = matrix[elevatorPos][j];
				if (name == null)
					continue;
				matrix[elevatorPos + 1][j] = matrix[elevatorPos][j];
				matrix[elevatorPos][j] = null;
				noOfChips++;
			}

			minSteps += (noOfChips -1);
			
			noOfChips = 0;
			List<String> nextFloor = Arrays.asList(matrix[elevatorPos + 1]);
			List<String> currentFloor = Arrays.asList(matrix[elevatorPos]);
			System.out.println("Before");
			
			displayMatrix(matrix);
			boolean found  = currentFloor.stream().filter(s -> currentFloor.indexOf(s) > 1 && s!= null).findAny().orElse(null) == null ? true : false;
			
			if(!found) {
				// There exists a match in the same floor
				for (int i = 2; i < matrix[elevatorPos+1].length; i++) {
					if (matrix[elevatorPos +1 ][i] != null) {
						if (matrix[elevatorPos + 1][i].endsWith("M")) {
							final String gen = matrix[elevatorPos + 1][i].substring(0, matrix[elevatorPos + 1][i].length() -1) + "G";
							String orElse = nextFloor.stream().filter((s) -> s != null && gen.equals(s)).findFirst().orElse(null);
							if(orElse == null) {
								matrix[elevatorPos][i] = matrix[elevatorPos + 1][i];
								matrix[elevatorPos + 1][i] = null;
								minSteps += 1;
								found = true;
								break;
							}
						} else if (matrix[elevatorPos + 1][i].endsWith("G") ) {
							final String gen = matrix[elevatorPos + 1][i].substring(0, matrix[elevatorPos + 1][i].length() -1) + "M";
							String orElse = nextFloor.stream().filter((s) -> s != null && gen.equals(s)).findFirst().orElse(null);
							if(orElse == null) {
								matrix[elevatorPos][i] = matrix[elevatorPos + 1][i];
								matrix[elevatorPos + 1][i] = null;
								minSteps += 1;
								found = true;
								break;
							}
						}
					}
				}
				
			}
			
			if(!found) {
				for (int j = 2; j < matrix[elevatorPos].length; j++) {
					String name = matrix[elevatorPos][j];
					if (name == null)
						continue;
					if (matrix[elevatorPos][j].endsWith("M")) {
						final String gen = matrix[elevatorPos][j].substring(0, matrix[elevatorPos][j].length() -1) + "G";
						String orElse = currentFloor.stream().filter((s) -> s != null && s.equals(gen)).findFirst().orElse(null);
						
						if(orElse == null) {
							String chip = nextFloor.stream().filter((s) -> s != null && s.endsWith("M")).findFirst().orElse(null);
							int indexOf = nextFloor.indexOf(chip);
							if(indexOf != -1) {
								matrix[elevatorPos][indexOf] = matrix[elevatorPos + 1][indexOf];
								matrix[elevatorPos + 1][indexOf] = null;
								minSteps += 1;
								found = true;
								break;
							}
						}
					} else if (matrix[elevatorPos][j].endsWith("G") ) {
						final String gen = matrix[elevatorPos][j].substring(0, matrix[elevatorPos][j].length() -1) + "M";
						String orElse = currentFloor.stream().filter((s) -> s != null && gen.equals(s)).findFirst().orElse(null);
						if(orElse == null) {
							String chip = nextFloor.stream().filter((s) -> s != null && s.endsWith("G")).findFirst().orElse(null);
							int indexOf = nextFloor.indexOf(chip);
							if(indexOf != -1) {
								matrix[elevatorPos][indexOf] = matrix[elevatorPos + 1][indexOf];
								matrix[elevatorPos + 1][indexOf] = null;
								minSteps += 1;
								found = true;
								break;
							}
						}
					}
				}
			}
			
			boolean nextElevator = false;
			for (int j = 2; j < matrix[elevatorPos].length; j++) {
				if (matrix[elevatorPos][j] != null) {
					if(!found) {
						for (int i = 2; i < matrix[elevatorPos+1].length; i++) {
							if (matrix[elevatorPos +1 ][i] != null) {
								matrix[elevatorPos][i] = matrix[elevatorPos + 1][i];
								matrix[elevatorPos + 1][i] = null;
								minSteps += 1;
								break;
							}
						}
					}
					nextElevator = true;
					break;
				}
			}
			System.out.println("After");
			displayMatrix(matrix);
			if(!nextElevator) elevatorPos += 1;
		}
	
	}
	
	private static void displayMatrix(String[][] matrix) {
		for(int i = 0; i<matrix.length; i++) {
			for(int j = 0; j<matrix[i].length ; j++) {
				System.out.print(matrix[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println("**********************************************************");
	}


}
