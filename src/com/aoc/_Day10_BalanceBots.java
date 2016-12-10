package com.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class _Day10_BalanceBots {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		List<String> instructions = new ArrayList<>();
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if("quit".equalsIgnoreCase(nextLine)) {
				break;
			}
			instructions.add(nextLine);
		}
		scanner.close();
		System.out.println(getBotNumber(instructions, true));
		System.out.println(getBotNumber(instructions, false));
	}
	
	private static class Instruction {
		private int low = -1;
		private int high = -1;
		private int lowOut = -1;
		private int highOut = -1;
		private boolean isLowBot;
		private boolean isHighBot;
		private int key = -1;
		
		public void setValue(int val) {
			if(low == -1 && high == -1) {
				low = val;
			} else if(val > low) {
				high = val;
			} else if(val < low) {
				high = low;
				low = val;
			}
		}
	}
	
	private static int getBotNumber(List<String> instructions , boolean isPart1) {
		int botNum = 0;
		Map<Integer, Instruction> inputMap = new HashMap<>();
		AtomicInteger start = new AtomicInteger(-1);
		
		instructions.stream()
			.forEach(a -> { 
				if(a.startsWith("value ")) {
					int len = "value ".length();
					final String[] split = a.substring(len).split(" goes to bot ");
					inputMap.compute(Integer.valueOf(split[1]), (k, v) -> {
						if(v == null) {
							v = new Instruction();
							v.key = k;
						} 
						v.setValue(Integer.valueOf(split[0]));
						if(v.high != -1 && v.low != -1 && start.get() == -1) {
							start.set(k);
						}
						return v;
					});
					
				} else if(a.startsWith("bot ")) {
					int len = "bot ".length();
					final String[] split = a.substring(len).split(" gives low to ");
					final String[] split2 = split[1].split(" and high to ");
					
					inputMap.compute(Integer.valueOf(split[0]), (k, v) -> {
						if(v == null) {
							v = new Instruction();
							v.key = k;
						} 
						String[] out1 = split2[0].split("\\s");
						v.lowOut = Integer.valueOf(out1[1]);
						v.isLowBot = "output".equals(out1[0]) ? false : true;
						
						String[] out2 = split2[1].split("\\s");
						v.highOut = Integer.valueOf(out2[1]);
						v.isHighBot = "output".equals(out2[0]) ? false : true;
						return v;
					});
					
				}
			});
		
		Queue<Instruction> queue = new LinkedList<>();
		queue.add(inputMap.get(start.get()));
		int out0 = 0, out1= 0, out2 =0;
		while(true) {
			Instruction currentIns = queue.poll();
			if(!isPart1 && out0 != 0 && out1 !=0 && out2 != 0) {
				return out0 * out1 * out2;
			}
			botNum = currentIns.key;
			if(isPart1 && currentIns.low == 17 && currentIns.high == 61) {
				return botNum;
			}
			if(currentIns.isLowBot) {
				Instruction ins = inputMap.get(currentIns.lowOut);
				ins.setValue(currentIns.low);	
				queue.add(ins);
			} 
			
			if(!currentIns.isLowBot) {
				if(currentIns.lowOut == 0) {
					out0 = currentIns.low;
				} else if(currentIns.lowOut == 1) {
					out1 = currentIns.low;
				} else if(currentIns.lowOut == 2) {
					out2 = currentIns.low;
				}
			}
			if(currentIns.isHighBot) {
				Instruction ins = inputMap.get(currentIns.highOut);
				ins.setValue(currentIns.high);
				queue.add(ins);
			} 
			
			if(!currentIns.isHighBot) {
				if(currentIns.highOut == 0) {
					out0 = currentIns.high;
				} else if(currentIns.highOut == 1) {
					out1 = currentIns.high;
				} else if(currentIns.highOut == 2) {
					out2 = currentIns.high;
				}
			}
		}
	}
	
}
