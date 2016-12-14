package com.aoc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

public class _Day14_SaltMD5 {
	
	public static void main(String args[]) throws Exception{
		String salt = "zpqevtbw";
		MessageDigest instance = MessageDigest.getInstance("MD5");
		int index = 0;
		
		for(int i = 0; i<Integer.MAX_VALUE; i++) {
			byte[] byteArray= (salt + i).getBytes(StandardCharsets.UTF_8);
			byte[] digest = instance.digest(byteArray);
			String hex = DatatypeConverter.printHexBinary(digest).toLowerCase();
			
			hex = findHashFor(hex, instance, 0);
			for(int j = 0; j<hex.length(); j++) {
				if(j == hex.length() -2) break;
				if(hex.charAt(j) == hex.charAt(j+1) && hex.charAt(j+1) == hex.charAt(j+2)) {
					//System.out.println("found for " + i);
					if(findFiveConsequtives(i +1, hex.charAt(j), salt, instance)) {
						System.out.println("for " + i);
						++index;
					}
					break;
				}
			}
			if(index == 64) {
				System.out.println(i);
				break;
			}
		}
	}

	private static String findHashFor(String hex, MessageDigest instance, int count) {
		if(count == 2016) {
			return hex;
		}
		byte[] byteArray= hex.getBytes(StandardCharsets.UTF_8);
		byte[] digest = instance.digest(byteArray);
		return findHashFor(DatatypeConverter.printHexBinary(digest).toLowerCase(), instance, ++count);
	}

	static Map<Integer, String> cache = new HashMap<>(); 
	private static boolean findFiveConsequtives(int i, char charAt, String salt, MessageDigest instance) {
		int end = i + 1000;
		while (i <= end) {
			byte[] byteArray = (salt + i).getBytes(StandardCharsets.UTF_8);
			byte[] digest = instance.digest(byteArray);
			String hex = DatatypeConverter.printHexBinary(digest).toLowerCase();
			if(!cache.containsKey(i)) {
				cache.put(i, findHashFor(hex, instance, 0));
			}
			hex = cache.get(i); 
			for (int j = 0; j < hex.length(); j++) {
				if (j == hex.length() - 4)
					break;
				if(hex.charAt(j) == charAt) {
					if (hex.charAt(j) == hex.charAt(j + 1) && hex.charAt(j + 1) == hex.charAt(j + 2)
							&& hex.charAt(j + 2) == hex.charAt(j + 3) && hex.charAt(j + 3) == hex.charAt(j + 4)) {
						return true;
					}
				}
			}
			++i;
		}
		return false;
	}

}
