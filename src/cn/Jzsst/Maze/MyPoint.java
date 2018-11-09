package cn.Jzsst.Maze;

public class MyPoint {

	private int left = 1;
	private int right = 1;
	private int up = 1;
	private int down = 1;
	private String string = "";
	private int X = 0;
	private int Y = 0;
	private MyPoint pre;
	public boolean visted;

	public MyPoint(int x, int y) {
		X = x;
		Y = y;
		string = "( " + x + " , " + y + " )";
	}

	public MyPoint(int x, int y, MyPoint pre) {
		X = x;
		Y = y;
		this.pre = pre;
		string = "( " + x + " , " + y + " )";
	}

	// 广度遍历 前驱节点
	public MyPoint getPre() {
		return pre;
	}

	public void setPre(MyPoint pre) {
		this.pre = pre;
	}

	public String getMyPoint() {
		return string;
	}

	public void setMyPoint(int x, int y) {
		X = x;
		Y = y;
		this.string = "( " + x + " , " + y + " )";
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft() {
		this.left = 0;
	}

	public int getRight() {
		return right;
	}

	public void setRight() {
		this.right = 0;
	}

	public int getUp() {
		return up;
	}

	public void setUp() {
		this.up = 0;
	}

	public int getDown() {
		return down;
	}

	public void setDown() {
		this.down = 0;
	}

	@Override
	public String toString() {
		return up + ""+ right +""+ down + "" + left + ""+", x=" + X + ", y="
				+ Y ;
	}
	public String toState() {
		return up + ""+ right +""+ down + "" + left;
	}
}
