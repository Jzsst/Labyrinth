package cn.Jzsst.Maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class spanMaze {

	// 尝试 8*8 地图 从(0 , 0) 到 ( 7 , 7);
	private String Up = "Up";
	private String Down = "Down";
	private String Left = "Left";
	private String Right = "Right";
	private int StartX = 0;
	private int StartY = 0;
	private int EndX = 0;
	private int EndY = 0;
	private int Spe = 0;
	private ArrayList<String> mazeList = new ArrayList<>();
	private int[][] mazeRecordUpperNode;
	private ArrayList<Integer> mazeDirection = new ArrayList<>();
	private ArrayList<Boolean> mazeBoolean = new ArrayList<>();
	private Stack<MyPoint> mazePoint = new Stack<>();// 记录路径
	private Stack<String> mazeRoad = new Stack<>();// 记录路径走法
	private StringBuffer path = new StringBuffer();

	public StringBuffer getPath() {
		return path;
	}

	public Stack<String> getMazeRoad() {
		return mazeRoad;
	}

	public Stack<MyPoint> getMazePoint() {
		return mazePoint;
	}

	public spanMaze(int Spe, int StartX, int StartY, int EndX, int EndY) {
		super();
		this.StartX = StartX;
		this.StartY = StartY;
		this.EndX = EndX;
		this.Spe = Spe;
		this.EndY = EndY;
	}

	public spanMaze() {
	}

	public Stack<String> spanFindRoad(ArrayList<String> mazeList, int Spe,int BG,int END) throws Exception {
		mazeBoolean.clear();
		mazePoint.clear();
		mazeRoad.clear();
		mazeDirection.clear();
		mazeRecordUpperNode = new int[Spe][Spe];
		this.mazeList = mazeList;
		for (int i = 0; i < mazeList.size(); i++) {
			mazeBoolean.add(false);
			mazeDirection.add(0);
		}
		this.Spe = Spe;
		
		StartX =BG%Spe; 
		StartY = BG/Spe;
		
		EndX =END%Spe;
		EndY = END/Spe;
		
		spanFind();

		return mazeRoad;
	}

	private void spanFind() throws Exception {

		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(StartX * Spe + StartY); // 矩阵数组按0,1,2...n*m编号
		mazeRecordUpperNode[StartX][StartY] = StartX * Spe + StartY;
		mazeBoolean.set(StartX * Spe + StartY,true);
		while (!queue.isEmpty()) {
			int index = queue.poll();
			int x = index / Spe;
			int y = index % Spe;
			String mazeString = mazeList.get(x + y * Spe);
			for (int i = 0; i < 4; i++) {
				int nx = x;
				int ny = y;
				switch (i) {
				case 0:
					ny--;
					break;
				case 1:
					nx++;
					break;
				case 2:
					ny++;
					break;
				case 3:
					nx--;
					break;
				default:
					break;
				}

				if (nx >= 0 && nx < Spe && ny >= 0 && ny < Spe &&
						mazeString.charAt(i) == '0' && mazeBoolean.get(nx * Spe + ny) == false) {
					queue.offer(nx * Spe + ny);  
					mazeBoolean.set(nx * Spe + ny,true);
					mazeRecordUpperNode[nx][ny] = index;
					mazeDirection.set(nx+ny*Spe, i);
				}
			}
		}

		int fx = EndX;
		int fy = EndY;
		int index = EndX * Spe + EndY;
		while (mazeRecordUpperNode[fx][fy] != index) {
			
			switch (mazeDirection.get(fx+fy*Spe)) {
			case 0:
				mazeRoad.push(Up);
				break;
			case 1:
				mazeRoad.push(Right);
				break;
			case 2:
				mazeRoad.push(Down);
				break;
			case 3:
				mazeRoad.push(Left);
				break;
			}
			int x = mazeRecordUpperNode[fx][fy] / Spe;
			int y = mazeRecordUpperNode[fx][fy] % Spe;
			mazePoint.add(new MyPoint(x,y));
			fx = x;
			fy = y;
			index = fx * Spe + fy;
		}

	}
}

