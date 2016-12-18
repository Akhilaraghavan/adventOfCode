package com.aoc;

public class _Day18_FindTraps {

	
	public static void main(String args[]) {
		String input = "^..^^.^^^..^^.^...^^^^^....^.^..^^^.^.^.^^...^.^.^.^.^^.....^.^^.^.^.^.^.^.^^..^^^^^...^.....^....^.";
		int[] arr = input.chars().toArray();
		int count = input.toString().chars().filter(i -> {
			return i == 46;}).toArray().length;
		for(int j = 1; j<400000; j++) {
			int[] result = new int[arr.length];
			for(int i=0 ; i<arr.length; i++) {
				boolean left = (i-1<0) ? true : arr[i-1] == 46 ? true : false;
				boolean center = arr[i] == 46 ? true : false;
				boolean right = (i+1>=arr.length) ? true : arr[i+1] == '.' ? true : false;
				
				if(!left && !center && right) {
					result[i] = 94;
				} else if(left && !center && !right) {
					result[i] = 94;
				} else if(!left && center && right) {
					result[i] = 94;
				} else if(!right && left && center) {
					result[i] = 94;
				} else {
					++count;
					result[i] = 46;
				}
			}
			arr = result;
		}
		System.out.println(count);
	}

}
