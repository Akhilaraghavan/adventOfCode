package com.aoc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Day16_FindChecksum {
	
	private static final String GROUP = "(.)\\1";
	
	public static void main(String args[]) {
		Pattern compile = Pattern.compile(GROUP);
		String a = "10011111011011001";
		int length = 35651584;
		long start = System.currentTimeMillis();
		while(true) {
			String b = a;
			StringBuilder sb = new StringBuilder();
			sb.append(a);
			sb.append("0");
			for(int i = b.length() -1; i>=0 ; i--) {
				if(Character.getNumericValue(b.charAt(i)) == 1) {
					sb.append("0"); 
				} else {
					sb.append("1");
				}
			}
			a = sb.toString();
			if(a.length() >= length) {
				break; 
			} 
		}
		
		a = a.length() > length ? a.substring(0, length) : a;
		while(true) {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i< a.length()-1 ; i = i+2) {
				String substring = a.substring(i, i+2);
				Matcher matcher = compile.matcher(substring);
				if(matcher.find()) {
					sb.append("1");
				} else {
					sb.append("0");
				}
			}
			a = sb.toString();
			if(a.length() % 2 != 0) {
				System.out.println(a);
				System.out.println("Time : " + (System.currentTimeMillis() - start));
				break;
			}
		}
		
	}
}
