package com.aoc;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class _Day12_Monorail {

	public static void main(String args[]) throws Exception{
		List<String> readAllLines = Files.readAllLines(new File("Day12.txt").toPath());
		int index = 0;
		int a = 0, b= 0, c= 0, d= 0;
		while(index < readAllLines.size()) {
			String instruction = readAllLines.get(index);
			if(instruction.startsWith("cpy ")) {
				String substring = instruction.substring("cpy ".length());
				String[] split = substring.split(" ");
				int val = 0;
				try {
					val = Integer.valueOf(split[0]);
				} catch (NumberFormatException e) {
					val = "a".equals(split[0]) ? a : "b".equals(split[0]) ? b : "c".equals(split[0]) ? c : "d".equals(split[0]) ? d : 0;
				}
				if("a".equals(split[1])) {
					a = val;
				} else if("b".equals(split[1])) {
					b = val;
				} else  if("c".equals(split[1])) {
					c = val;
				}else if("d".equals(split[1])) {
					d = val;
				}
				++index;
			} else if(instruction.startsWith("inc ")) {
				String substring = instruction.substring("inc ".length());
				if("a".equals(substring)) {
					++a;
				} else if("b".equals(substring)) {
					++b;
				} else  if("c".equals(substring)) {
					++c;
				}else if("d".equals(substring)) {
					++d;
				}
				++index;
			} else if(instruction.startsWith("dec ")) {
				String substring = instruction.substring("dec ".length());
				if("a".equals(substring)) {
					--a;
				} else if("b".equals(substring)) {
					--b;
				} else  if("c".equals(substring)) {
					--c;
				}else if("d".equals(substring)) {
					--d;
				}
				++index;
			} else if(instruction.startsWith("jnz ")) {
				String substring = instruction.substring("jnz ".length());
				String[] split = substring.split(" ");
				int val = Integer.valueOf(split[1]);
				
				int registerVal = 0;
				try {
					registerVal = Integer.valueOf(split[0]);
				} catch (NumberFormatException e) {
					registerVal = "a".equals(split[0]) ? a : "b".equals(split[0]) ? b : "c".equals(split[0]) ? c : "d".equals(split[0]) ? d : 0;
				}
				
				if(registerVal != 0) {
					index += val;
					continue;
				}
				
				++index;
			}
		}
		
		System.out.println(a);
	}
}
