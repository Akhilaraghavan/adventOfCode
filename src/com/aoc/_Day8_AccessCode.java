package com.aoc;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class _Day8_AccessCode {
	
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		List<String> codes = new ArrayList<>();
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if("quit".equalsIgnoreCase(nextLine)) {
				break;
			}
			codes.add(nextLine);
		}
		scanner.close();
		System.out.println(getLitPixels(codes));
	}
	
	private static int getLitPixels(List<String> codes) {
		int count = 0;
		int[][] arr = new int[6][50];
		for(String code : codes) {
			if(code.contains("rect ")) {
				String[] split2 = code.split("rect\\s")[1].split("x");
				for(int i = 0; i<Integer.valueOf(split2[1]); i++){
			         for(int j = 0; j<Integer.valueOf(split2[0]); j++){
			             arr[i][j] = 1;
			         }
			     }	
			} else if(code.contains("rotate row ")) {
				String[] split2 = code.split("rotate row\\s")[1].split(" by ");
				int rotateOrder = Integer.valueOf(split2[1]);
				int pos = Integer.valueOf(split2[0].substring(split2[0].indexOf("=") +1));
				 for(int i = 0; i<rotateOrder; i++){
			         for(int j = arr[0].length - 1; j>0; j--){
			             int temp = arr[pos][j];
			             arr[pos][j] =  arr[pos][j-1];
			             arr[pos][j-1] = temp;
			         }
			      }
			} else if(code.contains("rotate column ")) {
				String[] split2 = code.split("rotate column ")[1].split(" by ");
				int rotateOrder = Integer.valueOf(split2[1]);
				int pos = Integer.valueOf(split2[0].substring(split2[0].indexOf("=") +1));
				for(int i = 0; i<rotateOrder; i++){
			         for(int j = arr.length -1; j>0; j--){
			             int temp = arr[j][pos];
			             arr[j][pos] =  arr[j-1][pos];
			             arr[j-1][pos] = temp;
			         }
			      }
			}
		}
		for(int i = 0; i<arr.length; i++){
	         for(int j = 0; j<arr[i].length; j++){
	            if(arr[i][j] == 1) ++count;
	         }
	     }	
		int rowWidth = 5;
		int begin = 0;
		while(true) {
			for(int i = 0; i<arr.length; i++){
		       for(int j = begin; j<rowWidth; j++){
		    	   if(arr[i][j] == 1) {
		    		   System.out.print("* ");
		    	   } else {
		    		   System.out.print("  ");   
		    	   }
		       }
		       System.out.println();
			}
			begin+=5;
			rowWidth+=5;
			
			if(begin >= arr[0].length) {
				break;
			}
			System.out.println();
		}
		 
		return count;
	}
    
}
