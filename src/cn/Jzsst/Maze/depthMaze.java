package cn.Jzsst.Maze;

import java.util.ArrayList;
import java.util.Stack;

public class depthMaze {
	private String Up = "Up";
	private String Down = "Down";
	private String Left = "Left";
	private String Right = "Right";
	private int StartX = 0;
	private int StartY = 0;
	private int EndX = 0;
	private int EndY = 0;
	private int mazeS = 0;
	private int Judge ;//  0  找路  1 遍历  
	private ArrayList<Boolean> mazeBoolean = new ArrayList<>();
	private ArrayList<String> mazeList = new ArrayList<>();
	private Stack<MyPoint> mazePoint = new Stack<>();// 记录路径
	private Stack<MyPoint> mazePoint1 = new Stack<>();// 记录路径
	private Stack<String> mazeRoad = new Stack<>();// 记录路径走法

	public Stack<MyPoint> getMazePoint() {
		return mazePoint;
	}
	public Stack<MyPoint> getMazePoint1() {
		return mazePoint1;
	}
	// 给出起点和终点
	public int getMazeS() {
		return mazeS;
	}
	public depthMaze() {
		// TODO Auto-generated constructor stub
	}

	public Stack<String> depthFindRoad(ArrayList<String> mazeList,int Spe,int Judge) throws Exception {
		// 给定路径
		this.Judge =Judge;
		mazeBoolean.clear();
		mazePoint.clear();
		mazePoint1.clear();
		mazeRoad.clear();
		this.mazeList =mazeList;
		for (int i = 0; i < mazeList.size(); i++) {
			mazeBoolean.add(false);
		}
		mazeS = Spe;
		EndX = mazeS - 1;
		EndY = mazeS - 1;
		// 对路径进行分析,寻路
		depthFind();
		return mazeRoad;
	}

	private void depthFind() throws Exception {

		MyPoint myPoint = new MyPoint(0, 0);
		// X 0 7
		// Y 0 7
		mazePoint.add(new MyPoint(StartX, StartY));
		mazePoint1.add(new MyPoint(StartX, StartY));
		mazeRoad.add(Left);
		while (!mazePoint.empty()) {

			myPoint = mazePoint.peek();
			int d = 0;

			while (d < 4) {

				// 计算位置
				int x = myPoint.getX();
				int y = myPoint.getY();
				String mazeString = mazeList.get(x + y * mazeS);
				switch (d) {
				case 0:
					y--;
					break;
				case 1:
					x++;
					break;
				case 2:
					y++;
					break;
				case 3:
					x--;
					break;
				default:
					break;
				}
				if (mazeString.charAt(d) == '0' && mazeBoolean.get(x + y * mazeS) == false) {
					mazeBoolean.set(x + y * mazeS, true);
					myPoint = new MyPoint(x, y);
					mazePoint.push(myPoint);
					mazePoint1.push(myPoint);
					// 记录动向
					switch (d) {
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
					if (mazePoint.peek().getX() == EndX && mazePoint.peek().getY() == EndY) {
					
					if(Judge == 0)
						return ;
					
					} else {
						d = 0;
					}
				} else
					d++;
			}
			if(Judge==1) {
				int sum =0;
				for (int i = 0; i < mazeBoolean.size(); i++) {
					if(mazeBoolean.get(i)==false)
						sum++;
				}
				
				if(sum==0) {
					return;
				}
			}
			mazePoint.pop();
			if(Judge == 0)
				mazeRoad.pop();
		}

	}

}
