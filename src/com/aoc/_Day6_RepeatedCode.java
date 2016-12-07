package com.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class _Day6_RepeatedCode {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		List<String> codes = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if ("quit".equalsIgnoreCase(nextLine)) {
				break;
			}
			codes.add(nextLine);
		}
		scanner.close();
		System.out.println(getLeastCommonErrorCode(codes));
	}

	private static String getMostCommonErrorCode(List<String> codes) {
		Map<Integer, Map<String, Integer>> codeMap = new HashMap<>();
		for(String code : codes) {
			for(int i = 0;i<code.length(); i++) {
				String c = String.valueOf(code.charAt(i));
				Map<String, Integer> computeIfAbsent = codeMap.computeIfAbsent(i, (key) -> new HashMap<>());
				computeIfAbsent.compute(c, (k, v) -> v== null ? 1 : ++v);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(Entry<Integer, Map<String, Integer>> entry : codeMap.entrySet()) {
			int maxCount = 0;
			String s = "";
			for(String key : entry.getValue().keySet()) {
				Integer value = entry.getValue().get(key);
				if(value > maxCount) {
					maxCount = value;
					s = key;
				}
			}
			sb.append(s);
		}
		return sb.toString(); 
	}
	
	private static String getLeastCommonErrorCode(List<String> codes) {
		Map<Integer, Map<String, Integer>> codeMap = new HashMap<>();
		for(String code : codes) {
			for(int i = 0;i<code.length(); i++) {
				String c = String.valueOf(code.charAt(i));
				Map<String, Integer> computeIfAbsent = codeMap.computeIfAbsent(i, (key) -> new HashMap<>());
				computeIfAbsent.compute(c, (k, v) -> v== null ? 1 : ++v);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(Entry<Integer, Map<String, Integer>> entry : codeMap.entrySet()) {
			int maxCount = Integer.MAX_VALUE;
			String s = "";
			for(String key : entry.getValue().keySet()) {
				Integer value = entry.getValue().get(key);
				if(value < maxCount) {
					maxCount = value;
					s = key;
				}
			}
			sb.append(s);
		}
		return sb.toString(); 
	}

}
