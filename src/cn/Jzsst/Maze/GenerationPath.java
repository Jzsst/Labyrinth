package cn.Jzsst.Maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GenerationPath {
	private int Spe = 0;
	private Random random = new Random();
	
	public GenerationPath(int Spe) {
		super();
		this.Spe = Spe;
	}

	public ArrayList<MyPoint> getMaze() {
		ArrayList<MyPoint> maze = new ArrayList<MyPoint>();
		for (int h = 0; h < Spe; h++) {
			for (int w = 0; w < Spe; w++) {
				MyPoint point = new MyPoint(w, h);
				maze.add(point);
			}
		}
		return CreateMaze(maze);
	}

	private ArrayList<MyPoint> CreateMaze(ArrayList<MyPoint> maze) {
		//起点
		int x = 0;
		int y = 0;
		Stack<MyPoint> mazeteam = new Stack<MyPoint>();
		mazeteam.add(maze.get(x + y * Spe));
		while (!mazeteam.isEmpty()) {
			int[] val = new int[] { -1, -1, -1, -1 };
			int times = 0;
			boolean flag = false;
			MyPoint pt = (MyPoint) mazeteam.peek();
			x = pt.getX();
			y = pt.getY();
			pt.visted = true;

			ro1: while (times < 4) {
				int Direction = random.nextInt(4);
				if (val[Direction] == Direction)
					continue;
				else
					val[Direction] = Direction;

				switch (Direction) {

				case 0:// 上边
					if ((y - 1) >= 0 && maze.get(x + (y - 1) * Spe).visted == false) {
						maze.get(x + y * Spe).setUp();
						maze.get(x + (y - 1) * Spe).setDown();
						mazeteam.add(maze.get(x + (y - 1) * Spe));
						flag = true;
						break ro1;
					}
					break;
				case 1: // 右边
					if ((x + 1) < Spe && maze.get(x + 1 + y * Spe).visted == false) {

						maze.get(x + y * Spe).setRight();
						maze.get(x + 1 + y * Spe).setLeft();
						mazeteam.add(maze.get(x + 1 + y * Spe));
						flag = true;
						break ro1;
					}
					break;
				case 2: //下边
					if ((y + 1) < Spe && maze.get(x + (y + 1) * Spe).visted == false) {
						maze.get(x + y * Spe).setDown();
						maze.get(x + (y + 1) * Spe).setUp();
						mazeteam.add(maze.get(x + (y + 1) * Spe));
						flag = true;
						break ro1;
					}
					break;
				case 3:  // 左边
					if ((x - 1) >= 0 && maze.get(x - 1 + y * Spe).visted == false) {
						maze.get(x + y * Spe).setLeft();
						maze.get(x - 1 + y * Spe).setRight();
						mazeteam.add(maze.get(x - 1 + y * Spe));
						flag = true;
						break ro1;
					}
					break;
				}
				times += 1;
			}
			if (!flag) {
				mazeteam.pop();
			}

		}
		for (int i = 0; i < Spe*3; i++) {
			int fx = (int) (Math.random() * (Spe-2)+1);
			int fy = (int) (Math.random() * (Spe-2)+1);
			switch ((int) (Math.random() * 4)) {
			case 0:
				maze.get(fx+fy*Spe).setUp();
				maze.get(fx+(fy-1)*Spe).setDown();
				break;
			case 1:
				maze.get(fx+fy*Spe).setRight();
				maze.get(fx+fy*Spe+1).setLeft();
				break;
			case 2:
				maze.get(fx+fy*Spe).setDown();
				maze.get(fx+(fy+1)*Spe).setUp();
				break;
			case 3:
				maze.get(fx+fy*Spe).setLeft();
				maze.get(fx+fy*Spe-1).setRight();
				break;
			default:
				break;
			}
		}

		return maze;
	}
}
