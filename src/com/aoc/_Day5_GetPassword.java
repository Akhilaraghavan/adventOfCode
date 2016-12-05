package com.aoc;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class _Day5_GetPassword {

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		String nextLine = "";
		while(scanner.hasNextLine()) {
			nextLine = scanner.nextLine();
			if(!nextLine.isEmpty()) {
				break;
			}
		}
		System.out.println(getPassword(nextLine));
		System.out.println(getSubstitutionPassword(nextLine));
		scanner.close();
		
}

	private static String getPassword(String code) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");
			for(int i = 0; i<Integer.MAX_VALUE; i++) {
				byte[] byteArray= (code + i).getBytes(StandardCharsets.UTF_8);
				byte[] digest = instance.digest(byteArray);
				
				BigInteger bi = new BigInteger(1, digest);
				String hex = String.format("%0" + (digest.length << 1) + "X", bi);	
				System.out.println( code +i + "\t\t" + bi.longValue());
				if(hex.startsWith("00000")) {
					++count;
					sb.append(hex.charAt(5));
					if(count == 8) {
						return sb.toString();
					}
				}
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	private static String getSubstitutionPassword(String code) {
		List<String> substituedPassword = new ArrayList<>(Arrays.asList("","","","","","","",""));
		
		int count = 0;
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");
			for(int i = 0; i<Integer.MAX_VALUE; i++) {
				byte[] byteArray= (code + i).getBytes(StandardCharsets.UTF_8);
				byte[] digest = instance.digest(byteArray);
				
				BigInteger bi = new BigInteger(1, digest);
				String hex = String.format("%0" + (digest.length << 1) + "X", bi);
				if(hex.startsWith("00000")) {
					char position = hex.charAt(5);
					char password = hex.charAt(6);
					
					if(position >= '0' && position<='7') {
						int pos = Integer.valueOf(String.valueOf(position));
						if(substituedPassword.get(pos).isEmpty()) {
							++count;
							substituedPassword.set(pos, String.valueOf(password));
						}
					}
					
					if(count == 8) {
						return substituedPassword.stream().collect(Collectors.joining());
					}
				}
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return substituedPassword.stream().collect(Collectors.joining());
	}
}
