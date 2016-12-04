package com.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _Day2_BathRoomCode {
	
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
		System.out.println(getBathRoomCode(codes));
		System.out.println(getBathRoomCode2(codes));
	}

	private static String getBathRoomCode(List<String> codes) {
		String bathRoomCode = "";
		int start = 5;
		
		for(String code : codes) {
			for(int i = 0; i<code.length(); i++) {
				char key = code.charAt(i);
				switch (start) {
					case 1:
						if(key == 'R') {
							start = 2;
						} else if(key == 'D') {
							start = 4;
						}
						break;
					case 2:
						if(key == 'R') {
							start = 3;
						} else if(key == 'D') {
							start = 5;
						} else if(key == 'L') {
							start = 1;
						} 
						break;
					case 3:
						if(key == 'L') {
							start = 2;
						} else if(key == 'D') {
							start = 6;
						}
						break;
					case 4:
						if(key == 'R') {
							start = 5;
						} else if(key == 'D') {
							start = 7;
						} else if(key == 'U') {
							start = 1;
						}
						break;
					case 5:
						if(key == 'R') {
							start = 6;
						} else if(key == 'D') {
							start = 8;
						} else if(key == 'L') {
							start = 4;
						} else if(key == 'U') {
							start = 2;
						}
						break;
					case 6:
						if(key == 'L') {
							start = 5;
						} else if(key == 'D') {
							start = 9;
						} else if(key == 'U') {
							start = 3;
						}
						break;
					case 7:
						if(key == 'R') {
							start = 8;
						} else if(key == 'U') {
							start = 4;
						}
						break;
					case 8:
						if(key == 'R') {
							start = 9;
						} else if(key == 'L') {
							start = 7;
						} else if(key == 'U') {
							start = 5;
						}
						break;
					case 9:
						if(key == 'L') {
							start = 8;
						} else if(key == 'U') {
							start = 6;
						}
						break;
					default:
						break;
					}
			}
			bathRoomCode += start;
		}
		return bathRoomCode;
	}


	private static String getBathRoomCode2(List<String> codes) {
		String bathRoomCode = "";
		int start = 5;
		
		for(String code : codes) {
			for(int i = 0; i<code.length(); i++) {
				char key = code.charAt(i);
				switch (start) {
					case 1:
						if(key == 'D') {
							start = 3;
						}
						break;
					case 2:
						if(key == 'R') {
							start = 3;
						} else if(key == 'D') {
							start = 6;
						} 
						break;
					case 3:
						if(key == 'R') {
							start = 4;
						} else if(key == 'L') {
							start = 2;
						} else if(key == 'U') {
							start = 1;
						} else if(key == 'D') {
							start = 7;
						}
						break;
					case 4:
						if(key == 'L') {
							start = 3;
						} else if(key == 'D') {
							start = 8;
						}
						break;
					case 5:
						if(key == 'R') {
							start = 6;
						} 
						break;
					case 6:
						if(key == 'L') {
							start = 5;
						} else if(key == 'D') {
							start = 'A';
						} else if(key == 'U') {
							start = 2;
						} else if(key == 'R') {
							start = 7;
						}
						break;
					case 7:
						if(key == 'R') {
							start = 8;
						} else if(key == 'L') {
							start = 6;
						} else if(key == 'U') {
							start = 3;
						} else if(key == 'D') {
							start = 'B';
						}
						break;
					case 8:
						if(key == 'R') {
							start = 9;
						} else if(key == 'L') {
							start = 7;
						} else if(key == 'U') {
							start = 4;
						} else if(key == 'D') {
							start = 'C';
						}
						break;
					case 9:
						if(key == 'L') {
							start = 8;
						} 
						break;
					case 'A':
						if(key == 'R') {
							start = 'B';
						} else if(key == 'U') {
							start = 6;
						} 
						break;
					case 'B':
						if(key == 'R') {
							start = 'C';
						} else if(key == 'L') {
							start = 'A';
						} else if(key == 'U') {
							start = 7;
						} else if(key == 'D') {
							start = 'D';
						}
						break;
					case 'C':
						if(key == 'L') {
							start = 'B';
						}  else if(key == 'U') {
							start = 8;
						}
						break;
					case 'D':
						if(key == 'U') {
							start = 'B';
						}
						break;
					default:
						break;
					}
			}
			if(start > 9) {
				bathRoomCode += (char) start;
			} else {
				bathRoomCode += start;
			}
			
		}
		return bathRoomCode;
	}
	
	
}
