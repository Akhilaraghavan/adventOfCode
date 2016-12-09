package com.aoc;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class _Day9_CompressedData {
	
	public static void main(String args[]) throws Exception {
		List<String> readAllLines = Files.readAllLines(new File("C:\\github\\adventOfCode\\Day9.txt").toPath());
		String s = readAllLines.get(0);
		long counter = 0;
		counter = getDecompressedData1(s, counter);
		System.out.println(counter);
		counter = getDecompressedData2(s, counter);
		System.out.println(counter);
	}
	
	private static long getDecompressedData1(String s, long counter) throws Exception{
		int start = 0;
		s = s.replaceAll("\\s", "");
		while(true) {
				if(s.indexOf("(") == -1) {
					counter += s.length();
					return counter;
				}
				counter += s.substring(start, s.indexOf("(")).length();
		
				String substring = s.substring(s.indexOf("(") + 1 , s.indexOf(")"));
				String[] split = substring.split("x");
				String repeat = s.substring(s.indexOf(")") + 1, s.indexOf(")") + Integer.valueOf(split[0]) + 1);
				for(int i = 0; i<Integer.valueOf(split[1]); i++) {
					counter += repeat.length(); 
				}
				start = s.indexOf(repeat) + repeat.length();
				s = s.substring(start);
				start = 0;
			}
	}


	private static long getDecompressedData2(String s, long counter) throws Exception{
		int start = 0;
		s = s.replaceAll("\\s", "");
		while(true) {
				if(s.indexOf("(") == -1) {
					counter += s.length();
					return counter;
				}
				counter += s.substring(start, s.indexOf("(")).length();
		
				String substring = s.substring(s.indexOf("(") + 1 , s.indexOf(")"));
				String[] split = substring.split("x");
				String repeat = s.substring(s.indexOf(")") + 1, s.indexOf(")") + Integer.valueOf(split[0]) + 1);
				
				String repeatedString = "";
				for(int i = 0; i<Integer.valueOf(split[1]); i++) {
					repeatedString += repeat;
				}
				counter = getDecompressedData2(repeatedString, counter);
				repeatedString = null;
				start = s.indexOf(repeat) + repeat.length();
				s = s.substring(start);
				start = 0;
			}
	}

}
