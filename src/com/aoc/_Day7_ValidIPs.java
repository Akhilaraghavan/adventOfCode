package com.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

public class _Day7_ValidIPs {
	
	
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		List<String> ips = new ArrayList<>();
	
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if("quit".equalsIgnoreCase(nextLine)) {
				break;
			}
			ips.add(nextLine);
		}
		scanner.close();
		System.out.println(getValidIPs(ips));
		System.out.println(getSSLIPs(ips));
	}

	public static int getValidIPs(List<String> ips) {
		int count =0;
		for(String ip : ips) {
			count += getABBA(ip, false, (s) -> {
				
				for(int i = 0; i<s.length(); i++) {
					if(i >= s.length() - 3) {
						return false;
					}
					char pos1 = s.charAt(i);
					char pos2 = s.charAt(i + 1);
					char pos3 = s.charAt(i + 2);
					char pos4 = s.charAt(i + 3);
					if(pos1 != pos2 && pos1 == pos4 && pos2 == pos3) {
						return true;
					}
				}
			return false;
		 });
		}
		return count;
	}
	
	public static int getSSLIPs(List<String> ips) {
		int count =0;
		for(String ip : ips) {
			final List<String> knownbabs = new ArrayList<>();
			
			count += getBAB(ip, (s, list) -> {
				
				if(!knownbabs.isEmpty()) {
					if(list.stream().filter(b1-> {
						return knownbabs.stream()
								.filter(b2-> b1.contains(b2))
								.findFirst()
								.orElse(null) == null ? false : true;
						}).findFirst().orElse(null) != null) return true;
				}
				
				for(int i = 0; i<s.length(); i++) {
					if(i >= s.length() - 2) {
						return false;
					}
					char pos1 = s.charAt(i);
					char pos2 = s.charAt(i + 1);
					char pos3 = s.charAt(i + 2);
					
					if(pos1 != pos2 && pos1 == pos3) {
						String bab = new String(new char[]{pos2, pos1, pos2});
						if(list.stream().filter(b1-> b1.contains(bab)).findFirst().orElseGet(()-> { knownbabs.add(bab); return null;}) != null) return true;
					}
				}
			return false;
		 }, new ArrayList<>());
		}
		return count;
	}
	

	private static int getABBA(String ip, boolean found, Function<String, Boolean> supportTLS) {
		int start = ip.indexOf("[");
		int end = ip.indexOf("]");
		
		if(start == -1) {
			if(found || supportTLS.apply(ip)) {
				return 1;
			}
			return 0;
		}
				
		if(supportTLS.apply(ip.substring(start + 1, end))) {
			return 0;	
		} else {
			if(!found) {
				found = supportTLS.apply(ip.substring(0, start));
			}
			return getABBA(ip.substring( end +1 , ip.length()) , found, supportTLS);
		}
	}
	
	

	private static int getBAB(String ip, BiFunction<String, List<String>, Boolean> babFunc , List<String> hypernets) {
		int start = ip.indexOf("[");
		int end = ip.indexOf("]");
		
		if(start == -1) {
			return babFunc.apply(ip, hypernets) == true ? 1: 0;
		}
		
		String part = ip.substring(0, start);
		hypernets.add(ip.substring(start + 1, end));
		if(babFunc.apply(part, hypernets)) {
			return 1;
		} else  {
			return getBAB(ip.substring(end +1 , ip.length()), babFunc, hypernets);
		}
	}
}

