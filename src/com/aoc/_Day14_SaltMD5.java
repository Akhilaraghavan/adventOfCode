package com.aoc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import javax.xml.bind.DatatypeConverter;

public class _Day14_SaltMD5 {
	private static final Pattern CONS_THREE  = Pattern.compile("(.)\\1\\1");
	
	public static void main(String args[]) throws Exception{
		String salt = "zpqevtbw";
		MessageDigest instance = MessageDigest.getInstance("MD5");
		AtomicInteger index = new AtomicInteger(0);
		
		IntStream.range(0, Integer.MAX_VALUE).allMatch(i -> {
			String hex = findHashFor(salt + i, instance, 0, 2017);
			Matcher matcher = CONS_THREE.matcher(hex);
			
			if(matcher.find()) {
				//System.out.println("found for " + i);
				String charToMatch = matcher.group() + matcher.group().substring(0, 2);
				if(findFiveConsequtives(i +1, charToMatch, salt, instance)) {
					System.out.println("for " + i);
					index.incrementAndGet();
				}
			}
			if(index.get() == 64) {
				System.out.println(i);
				return false;
			}
			return true;
		});
	}

	private static String findHashFor(String hex, MessageDigest instance, int count, int end) {
		if(count == end) {
			return hex;
		}
		byte[] byteArray= hex.getBytes(StandardCharsets.UTF_8);
		byte[] digest = instance.digest(byteArray);
		return findHashFor(DatatypeConverter.printHexBinary(digest).toLowerCase(), instance, ++count, end);
	}

	static Map<Integer, String> cache = new HashMap<>(); 
	private static boolean findFiveConsequtives(int index, String charToMatch, String salt, MessageDigest instance) {
		return IntStream.range(index, index + 1000)
		.anyMatch(i -> cache.computeIfAbsent(i, (key) -> findHashFor(salt + i, instance, 0, 2017)).contains(charToMatch));
	}

}
