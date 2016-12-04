package com.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class _Day1_TaxiCab {
	
	private enum Direction {NORTH, SOUTH, WEST, EAST};

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		List<String> blocks = new ArrayList<>();
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if("quit".equalsIgnoreCase(nextLine)) {
				break;
			}
			String[] split = nextLine.split(", ");
			blocks = Arrays.asList(split);
		}
		scanner.close();
		System.out.println(getTaxiCabDistance(blocks));
		System.out.println(getFirstLocationVisitedTwice(blocks));
	}

	public static int getTaxiCabDistance(List<String> blocks) {
		int x = 0;
		int y = 0;
		
		Direction direction = Direction.NORTH;

		for(int i = 0; i<blocks.size(); i++) {
			String currentBlock = blocks.get(i);
			char currentDirection = currentBlock.charAt(0);
			int blocksToGo = Integer.parseInt(currentBlock.substring(1));
			
			switch(currentDirection) {
				case 'L' :	 
						if(direction == Direction.NORTH) {
							x -= blocksToGo;
							direction = Direction.WEST;
						} else if(direction == Direction.WEST) {
							y -= blocksToGo;
							direction = Direction.SOUTH;
						} else if(direction == Direction.SOUTH) {
							direction= Direction.EAST;
							x += blocksToGo;
						} else if(direction == Direction.EAST) {
							direction= Direction.NORTH;
							y += blocksToGo;
						}
						break;
				case 'R' :
					if(direction == Direction.NORTH) {
						x += blocksToGo;
						direction = Direction.EAST;
					} else if(direction == Direction.WEST) {
						y += blocksToGo;
						direction = Direction.NORTH;
					} else if(direction == Direction.SOUTH) {
						direction= Direction.WEST;
						x -= blocksToGo;
					} else if(direction == Direction.EAST) {
						direction= Direction.SOUTH;
						y -= blocksToGo;
					}
					break;
				default : break;
			}
			
		}
		return Math.abs(x) + Math.abs(y);
	}
	
	private static class Coordinate{
		private int x;
		private int y;
		
		public Coordinate(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinate other = (Coordinate) obj;
			if (x == other.x && y == other.y)
				return true;
			return false;
		}

	}

	private static List<Coordinate> coordinates = new ArrayList<>();
	private static Coordinate found;
	
	public static int getFirstLocationVisitedTwice(List<String> blocks) {
		Direction direction = Direction.NORTH;

		int x=0;
		int y=0;
		
		for(int i = 0; i<blocks.size(); i++) {
			String currentBlock = blocks.get(i);
			char currentDirection = currentBlock.charAt(0);
			int blocksToGo = Integer.parseInt(currentBlock.substring(1));
			
			switch(currentDirection) {
				case 'L' :	 
						if(direction == Direction.NORTH) {
							if(visitedX(blocksToGo, x, y, false)) {
								return Math.abs(found.x) + Math.abs(found.y);
							}
							x -=  blocksToGo;
							direction = Direction.WEST;
						} else if(direction == Direction.WEST) {
							if(visitedY(blocksToGo, x, y, false)) {
								return Math.abs(found.x) + Math.abs(found.y);
							}
							y -= blocksToGo;
							direction = Direction.SOUTH;
						} else if(direction == Direction.SOUTH) {
							if(visitedX(blocksToGo, x, y, true)) {
								return Math.abs(found.x) + Math.abs(found.y);
							}
							direction= Direction.EAST;
							x += blocksToGo;
						} else if(direction == Direction.EAST) {
							if(visitedY(blocksToGo, x, y, true)) {
								return Math.abs(found.x) + Math.abs(found.y);
							}
							direction= Direction.NORTH;
							y += blocksToGo;
						}
						break;
				case 'R' :
					if(direction == Direction.NORTH) {
						if(visitedX(blocksToGo, x, y, true)) {
							return Math.abs(found.x) + Math.abs(found.y);
						}
						x += blocksToGo;
						direction = Direction.EAST;
					} else if(direction == Direction.WEST) {
						if(visitedY(blocksToGo, x, y, true)) {
							return Math.abs(found.x) + Math.abs(found.y);
						}
						y += blocksToGo;
						direction = Direction.NORTH;
					} else if(direction == Direction.SOUTH) {
						if(visitedX(blocksToGo, x, y, false)) {
							return Math.abs(found.x) + Math.abs(found.y);
						}
						direction= Direction.WEST;
						x -= blocksToGo;
					} else if(direction == Direction.EAST) {
						if(visitedY(blocksToGo, x, y, false)) {
							return Math.abs(found.x) + Math.abs(found.y);
						}
						direction= Direction.SOUTH;
						y -= blocksToGo;
					}
					break;
				default : break;
			}
			
		}
		return Math.abs(x) + Math.abs(y);
	}
	
	private static boolean visitedX(int blocksToGo, int x, int y, boolean isIncrement) {
		for(int i = 0; i<blocksToGo ; i++) {
			Coordinate coordinate;
			if(isIncrement) {
				coordinate = new Coordinate(++x, y);
			} else {
				coordinate = new Coordinate(--x, y);
			}
			if(coordinates.contains(coordinate)) {
				found = coordinate;
				return true;
			}
			coordinates.add(coordinate);
		}
		return false;
	}
	
	private static boolean visitedY(int blocksToGo, int x, int y, boolean isIncrement) {
		for(int i = 0; i<blocksToGo ; i++) {
			Coordinate coordinate;
			if(isIncrement) {
				coordinate = new Coordinate(x, ++y);
			} else {
				coordinate = new Coordinate(x, --y);
			}
			if(coordinates.contains(coordinate)) {
				found = coordinate;
				return true;
			}
			coordinates.add(coordinate);
		}
		return false;
	}
}
