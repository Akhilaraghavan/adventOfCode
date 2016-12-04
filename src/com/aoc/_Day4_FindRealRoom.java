package com.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class _Day4_FindRealRoom {
	
	public static final String CHECKSUM = "([[a-z]+])";
	public static final String ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
	
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		List<String> names = new ArrayList<>();
	
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if("quit".equalsIgnoreCase(nextLine)) {
				break;
			}
			names.add(nextLine);
		}
		scanner.close();
		System.out.println(getTotalSectorIdForRealRoom(names));
		System.out.println(getNorthPoleObjectRool(names));
	}
	
	private static int getNorthPoleObjectRool(List<String> names) {
		int northPoleSectorId = 0;
		for(String name : names) {
			
			String[] split = name.split("-");
			String last = split[split.length -1];
			
			int sectorId = Integer.valueOf(last.substring(0, last.indexOf("[")));
			
			String codeStr = name.substring(0, name.indexOf(String.valueOf(sectorId)));
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i< codeStr.length(); i++) {
				sb.append(String.valueOf(rotate(codeStr.charAt(i) , sectorId)));
			}
			if("northpole object storage ".equals(sb.toString())) {
				return sectorId;
			}
		}
		
		return northPoleSectorId;
	}

	private static char rotate(char charAt, int sectorId) {
		char ret = charAt;
		if(ret == '-') {
			return ' ';
		}
		int index = ALPHABETS.indexOf(charAt);
		for(int i = 0; i<sectorId ; i++) {
			index++;
			if(index >= 26) {
				index = 0;
			}
			ret = ALPHABETS.charAt(index);
		}
		return ret;
	}

	private static int getTotalSectorIdForRealRoom(List<String> names) {
		int total = 0;
		for(String name : names) {
			
			String[] split = name.split("-");
			String last = split[split.length -1];
			
			String checksum = last.substring(last.indexOf("[")+1, last.length()-1);
			int sectorId = Integer.valueOf(last.substring(0, last.indexOf("[")));
			
			Map<String, Integer> counter = new LinkedHashMap<>();
				
			for(int j = 0; j<split.length -1; j++) {
				String code = split[j];
				for(int i = 0; i<code.length(); i++) {
					String key = String.valueOf(code.charAt(i));
					counter.compute(key, (k, v) -> (v == null) ? 1 : ++v);
				}
			}
			
			Comparator<String> c = (a, b) -> {
				Integer countA = counter.get(a);
				Integer countB = counter.get(b);
			
				if(countA == countB) {
					return a.compareTo(b);
				} else if(countA > countB){
					return -1;
				} else {
					return 1;
				}
				};
				
			ArrayList<String> list = new ArrayList<String>(counter.keySet());
			Collections.sort(list, c);
			String collect = list.stream().collect(Collectors.joining());
			if(collect.contains(checksum)) {
				total+= sectorId;
			}
			
		}
		return total;
	}

	
}
