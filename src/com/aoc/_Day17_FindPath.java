package com.aoc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.bind.DatatypeConverter;

public class _Day17_FindPath {
	private static String passcode = "vkjiggvb";
	private static MessageDigest instance;
	
	public static void main(String args[]) throws Exception{
		instance = MessageDigest.getInstance("MD5");
		Queue<Node> queue = new LinkedList<>();
		queue.add(new Node(0, 0, passcode));
		Node node = findShortestPath(new Node(3, 3, ""), queue);
		System.out.println(node.path);
		
		int findShortestPath = findLongestPath(new Node(0, 0, passcode), new Node(3, 3, ""));
		System.out.println(findShortestPath);
		
		
	}


	static class Node {
		int x;
		int y;
		String path;
		int count;
		
		public Node(int x, int y, String path) {
			this(x, y, path, 0);
		}
		
		public Node(int x, int y, String path, int count) {
			super();
			this.x = x;
			this.y = y;
			this.path = path;
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
	
	
	private static Node findShortestPath(Node end, Queue<Node> queue) {
		while(!queue.isEmpty()) {
			Node start = queue.poll();
			String path = start.path;
			int y = start.y;
			int x = start.x;

			if(x == end.x && y == end.y) {
				return start;
			}
			
			String openSpace = getCode(path);
			
			if(y +1 < 4) {
				if("bcdef".contains(String.valueOf(openSpace.charAt(1)))){
					Node e = new Node(x, y +1, path + "D");
					queue.add(e);
				}
			}
			
			if(y - 1 >= 0) { 
				if("bcdef".contains(String.valueOf(openSpace.charAt(0)))) {
					Node e = new Node(x, y-1, path + "U");
					queue.add(e);
				} 
			}
			
			if( x + 1 < 4) {
				if("bcdef".contains(String.valueOf(openSpace.charAt(3)))) {
					Node e = new Node(x +1, y, path + "R");
					queue.add(e);
				} 
			}
			
			if( x - 1 >= 0) {
				if("bcdef".contains(String.valueOf(openSpace.charAt(2)))) {
					Node e = new Node(x-1, y, path + "L");
					queue.add(e);
				} 
			}
		}
		return null;
	}
	
	private static int findLongestPath(Node start , Node end) {
			String path = start.path;
			int y = start.y;
			int x = start.x;

			if(x == end.x && y == end.y) {
				return path.length() - 8;
			}
			
			String openSpace = getCode(path);
			int paths = 0; 
			
			
			if(y +1 < 4) {
				if("bcdef".contains(String.valueOf(openSpace.charAt(1)))){
					Node e = new Node(x, y +1, path + "D");
					paths = Math.max(paths, findLongestPath(e, end));
				}
			}
			
			if(y - 1 >= 0) { 
				if("bcdef".contains(String.valueOf(openSpace.charAt(0)))) {
					Node e = new Node(x, y-1, path + "U");
					paths = Math.max(paths, findLongestPath(e, end));
				} 
			}
			
			if( x + 1 < 4) {
				if("bcdef".contains(String.valueOf(openSpace.charAt(3)))) {
					Node e = new Node(x +1, y, path + "R");
					paths = Math.max(paths, findLongestPath(e, end));
				} 
			}
			
			if( x - 1 >= 0) {
				if("bcdef".contains(String.valueOf(openSpace.charAt(2)))) {
					Node e = new Node(x-1, y, path + "L");
					paths = Math.max(paths, findLongestPath(e, end));
				} 
			}
			
		return paths;
	}
	
	
	

	public static String getCode(String passcode) {
		byte[] digest = instance.digest(passcode.getBytes(StandardCharsets.UTF_8));
		return DatatypeConverter.printHexBinary(digest).substring(0, 4).toLowerCase();
	}


}
