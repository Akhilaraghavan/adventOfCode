package com.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _Day3_ListTriangle {
	private static final String WHITSPACE = "[^\\d]+";
	
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		List<String> triangleSides = new ArrayList<>();
	
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if("quit".equalsIgnoreCase(nextLine)) {
				break;
			}
			triangleSides.add(nextLine);
		}
		scanner.close();
		int[][] sides = new int[triangleSides.size()][3];
		System.out.println(listTriangles(triangleSides, sides));
		System.out.println(listTrianglesByColumn(sides));
	}

	private static int listTriangles(List<String> triangleSides, int[][] sides) {
		int count = 0;
		int index = 0;
		for(String str : triangleSides) {
			String[] split = str.split(WHITSPACE);
			int a = Integer.valueOf(split[1]);
			int b = Integer.valueOf(split[2]);
			int c = Integer.valueOf(split[3]);
			
			sides[index][0] = a;
			sides[index][1] = b;
			sides[index][2] = c;
			
			if((a+b > c) && (a+c > b) && (b+c > a)) {
				++count;
			}
			index++;
		}
		return count;
	}
	
	
	private static int listTrianglesByColumn(int[][] sides) {
		int count = 0;
		for(int i = 0; i<sides.length; i+= 3) {
			for(int j = 0; j<3; j++) {
				int a = sides[i][j];
				int b = sides[i+1][j];
				int c = sides[i+2][j];
				
				if((a+b > c) && (a+c > b) && (b+c > a)) {
					++count;
				}	
			}
		}
		return count;
	}
}
