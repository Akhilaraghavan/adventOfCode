package com.aoc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class _Day13_Maze {
	private static int [][] maze = new int[50][50];
	private static int input = 1364;
	
	public static void main(String args[]) {
		recursivelyMarkShortestPath(1, 1, 39, 31, 0);
		Node findShortestPath = findShortestPath(39, 31, false);
		System.out.println(findShortestPath.count);
		
		visited.clear();
		queue.clear();
		findShortestPath = findShortestPath(39, 31, true);
		System.out.println(visited.size());
		displayMaze();
	}

	static class Node {
		int x;
		int y;
		int count;
		
		public Node(int x, int y, int count) {
			super();
			this.x = x;
			this.y = y;
			this.count = count;
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
			Node other = (Node) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		
		
		
	}
	
	
	static List<Node> visited = new ArrayList<>();
	static Queue<Node> queue = new LinkedList<>();
	
	private static Node findShortestPath(int endY, int endX, boolean isPart2) {
		queue.add(new Node(1, 1, 0));
		Node poll = null;
		
		while(!queue.isEmpty()) {
			poll = queue.poll();
			int count = poll.count;
			int y = poll.y;
			int x = poll.x;
			
			if(maze[poll.y][poll.x] == 9) {
				visited.add(new Node(y, x, count));
				continue;
			}
			if(visited.contains(poll)) {
				Node node = visited.get(visited.indexOf(poll));
				if(node.count > poll.count) {
					node.count = poll.count;
				} 
				continue;
			}
			
			
			if(x == endX && y == endY) {
				return poll;
			}
			visited.add(poll);
			if(isPart2 && count >=50) {
				return poll;
			}
			
			if(y + 1 < maze.length && maze[y + 1][x] != 9 ) {
				 Node e = new Node(x, y+1, count +1);
				 if(!queue.contains(e)) {
					queue.add(e);
				 }
			}
			
			if(x +1 < maze[0].length && maze[y][x +1] != 9 ) {
				Node e = new Node(x+1, y, count +1);
				if(!queue.contains(e)) {
					queue.add(e);
				}
			}
			
			if(y - 1 >= 0 && maze[y-1][x] != 9) {
				Node e = new Node(x, y-1, count +1);
				if(!queue.contains(e)) {
					queue.add(e);
				}
			}
			
			if(x -1 >= 0 && maze[y][x -1] != 9) {
				Node e = new Node(x-1, y, count +1);
				if(!queue.contains(e)) {
					queue.add(e);
				}
			}
		
			
		}
		
		return poll;
	}


	public static void recursivelyMarkShortestPath(int y, int x, int endY , int endX, int count) {
		//visited 
		if(maze[y][x] == 9) return;
		
		if(maze[y][x] == 1) {
			return;
		}
				
		if(x == endX && y == endY) {
			maze[y][x] = 1000;
			return;
		}	
		
		boolean openSpace = isOpenSpace(y, x);

		if(!openSpace)  {
			maze[y][x] = 9;
			return;
		}
		maze[y][x] = 1;
		
		if(y + 1 < maze.length) {
			recursivelyMarkShortestPath(y +1, x, endY, endX, count + 1);	
		}
		
		if(x +1 < maze[0].length) {
			recursivelyMarkShortestPath(y, x +1, endY, endX, count + 1);	
		}
		
		if(y - 1 >= 0) {
			recursivelyMarkShortestPath(y -1, x, endY, endX, count + 1);	
		}
		
		if(x -1 >= 0) {
			recursivelyMarkShortestPath(y, x -1, endY, endX, count + 1);	
		}
		
	}
	
	
	
	
	public static void displayMaze() {
		for(int j= 0; j<maze.length; j++) {
			for(int i= 0; i<maze[0].length; i++) {
				System.out.print(maze[j][i]);
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static boolean isOpenSpace(int y, int x) {
		int val = ((x*x) + (3*x) + (2*x*y) + y +(y*y)) + input;
		return Integer.toBinaryString(val).chars().filter(c -> {
			return Character.getNumericValue(c) == 1 ? true : false; 
					}).count() % 2 == 0;
	}
}
