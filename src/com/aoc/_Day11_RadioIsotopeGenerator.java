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
		System.out.println(getMinimumSteps(Arrays.asList(part1.split("\n")), 14));
		System.out.println(getMinimumSteps(Arrays.asList(part2.split("\n")), 16));
	}

	private static int getMinimumSteps(List<String> instructions, int size) {
		int minSteps = 0;
		String[][] matrix = new String[4][size];
		int index = 2;
		for (String ins : instructions) {
			ins = ins.replaceAll("\\.", "");
			if (ins.contains("The first floor contains ")) {
				String[] split = ins.substring("The first floor contains ".length() + 1).split(",");

				matrix[0][0] = "FF";
				matrix[0][1] = "E";

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
				matrix[1][0] = "SF";
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
				matrix[2][0] = "TF";
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
				matrix[3][0] = "FOF";
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
		int elevatorPos = 0;
		int noOfChips = 0;
		while (true) {
			if (elevatorPos == 3) {
				return minSteps;
			}
			for (int i = 2; i < matrix[elevatorPos].length; i++) {
				if(noOfChips >=2) break;
				if (matrix[elevatorPos][i] == null)
					continue;
				String chip = matrix[elevatorPos][i];
				if (chip == null)
					continue;
				String matching = chip.endsWith("G") ? chip.substring(0, chip.length() - 1) + "M"
						: chip.substring(0, chip.length() - 1) + "G";
				
				// There exists a match in the same floor
				for (int j = 2; j < matrix[elevatorPos].length; j++) {
					if (noOfChips >= 2) {
						break;
					}
					String name = matrix[elevatorPos][j];
					if (name == null)
						continue;
					if (name.equals(matching)) {
						matrix[elevatorPos + 1][i] = matrix[elevatorPos][i];
						matrix[elevatorPos][i] = null;
						noOfChips++;
						matrix[elevatorPos + 1][j] = matrix[elevatorPos][j];
						matrix[elevatorPos][j] = null;
						noOfChips++;
					}
				}
				
				// There exists a match in the next floor
				for (int j = 2; j < matrix[elevatorPos + 1].length; j++) {
					if (noOfChips >= 2) {
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

			}

			noOfChips = 0;
			List<String> list = Arrays.asList(matrix[elevatorPos + 1]);
			for (int i = 2; i < matrix[elevatorPos].length; i++) {
				if (matrix[elevatorPos][i] != null) {
					if (matrix[elevatorPos][i].endsWith("M")) {
						String orElse = list.stream().filter((s) -> s != null && s.endsWith("M")).findFirst().orElse("");
						int indexOf = list.indexOf(orElse);
						if(indexOf == -1) continue;
						matrix[elevatorPos][indexOf] = matrix[elevatorPos + 1][indexOf];
						matrix[elevatorPos + 1][indexOf] = null;
						minSteps += 1;
					} else if (matrix[elevatorPos][i].endsWith("G") ) {
						String orElse = list.stream().filter((s) -> s != null && s.endsWith("G")).findFirst().orElse("");
						int indexOf = list.indexOf(orElse);
						if(indexOf == -1) continue;
						matrix[elevatorPos][indexOf] = matrix[elevatorPos + 1][indexOf];
						matrix[elevatorPos + 1][indexOf] = null;
						minSteps += 1;
					}
					break;
				}
			}

			boolean found = false;
			for (int j = 2; j < matrix[elevatorPos].length; j++) {
				if (matrix[elevatorPos][j] != null) {
					found = true;
					break;
				}
			}

			if(!found) elevatorPos += 1;
		}

	}

}
