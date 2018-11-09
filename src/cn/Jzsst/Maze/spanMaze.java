package cn.Jzsst.Maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class spanMaze {

	// ���� 8*8 ��ͼ ��(0 , 0) �� ( 7 , 7);
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
	private Stack<MyPoint> mazePoint = new Stack<>();// ��¼·��
	private Stack<String> mazeRoad = new Stack<>();// ��¼·���߷�
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

	public Stack<String> spanFindRoad(ArrayList<String> mazeList, int Spe) throws Exception {
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
		EndX = Spe - 1;
		EndY = Spe - 1;
		spanFind();

		return mazeRoad;
	}

	private void spanFind() throws Exception {

		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(StartX * Spe + StartY); // �������鰴0,1,2...n*m���
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
/*
 * Queue<MyPoint> queue = new LinkedList<MyPoint>(); MyPoint myPoint = new
 * MyPoint(0, 0); // X 0 7 // Y 0 7 mazePoint.add(new MyPoint(StartX, StartY));
 * queue.offer(myPoint); // �������鰴0,1,2...n*m��� RecordUpperNode[StartX][StartY] =
 * StartX + StartY* Spe; mazeBoolean.set(EndX + EndY* Spe, true);
 * 
 * 
 * while (!queue.isEmpty()) {
 * 
 * myPoint = queue.poll();
 * 
 * for (int i = 0; i < 4; i++) { int x = myPoint.getX(); int y = myPoint.getY();
 * String mazeString = mazeList.get(x + y * Spe); switch (i) { case 0: y--;
 * break; case 1: x++; break; case 2: y++; break; case 3: x--; break; default:
 * break; } if (mazeString.charAt(i) == '0' && mazeBoolean.get(x + y * Spe) ==
 * false) { mazeBoolean.set(x * Spe + y, true); myPoint = new MyPoint(x, y);
 * System.out.println(myPoint); queue.offer(myPoint); RecordUpperNode[x][y] = x
 * + y * Spe; last_dir[x][y] = i;
 * 
 * } } }
 * 
 * 
 * int fx = EndX; int fy = EndY; int index = EndX + EndY* Spe; while
 * (RecordUpperNode[fx][fy] != index) {
 * 
 * switch (last_dir[fx][fy]) { case 0: mazeRoad.push(Up); break; case 1:
 * mazeRoad.push(Right); break; case 2: mazeRoad.push(Down); break; case 3:
 * mazeRoad.push(Left); break; } int x = RecordUpperNode[fx][fy] % Spe; int y =
 * RecordUpperNode[fx][fy] / Spe; fx = x; fy = y; index = fx * Spe + fy; }
 * 
 * 
 * 
 * 
 * 
 */
