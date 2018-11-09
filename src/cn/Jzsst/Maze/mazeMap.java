package cn.Jzsst.Maze;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

public class mazeMap extends Pane {

	private int Spe = 0;
	private double mapSpe = 0;
	private Pane pane = new Pane();

	public double getMapSpe() {
		return mapSpe;
	}

	public mazeMap(int Spe) {
		super();
		this.Spe = Spe;
	}

	public int getSpe() {
		return Spe;
	}

	public mazeMap() {
	}

	public Pane MazeGeneration(String string, int Spe,int num) throws Exception {
		pane.getChildren().clear();
		this.Spe = Spe;
		mapSpe = (600 - Spe * 5) / Spe;
		// 迷宫数据 ( 0 1 )编码 ( 上 右 下 左 )
		// 通过mazemodel类返回生成建设路径
		
		// 装pane
		// gridPane.setLayoutY(60);
		// gridPane.setLayoutX(60);
		return bulid(string , num);

	}

	private Pane bulid(String string,int num) {

		Line polyline = null;
		BorderPane bp = new BorderPane();

		int d = 0;
		while (d < 4) {
			if (string.charAt(d) == '1') {

				switch (d) {
				case 0:
					polyline = new Line(0, 0, mapSpe, 0);
					bp.setTop(polyline);
					break;
				case 1:
					polyline = new Line(mapSpe, 0, mapSpe, mapSpe);
					bp.setRight(polyline);
					break;
				case 2:
					polyline = new Line(0, mapSpe, mapSpe, mapSpe);
					bp.setBottom(polyline);
					break;
				case 3:
					polyline = new Line(0, 0, 0, mapSpe);
					bp.setLeft(polyline);
					break;
				default:
					break;
				}
			}
			d++;
		}
		//出没点
		if (num == Spe*Spe-1 || num == 0) {
			Circle circle = new Circle((600 - Spe * 5) / (Spe * 2 * 2));
			if(num == 0) {
				circle.setFill(Color.RED);
				circle.setStroke(Color.RED);
			}
			if(num == Spe*Spe-1) {
			circle.setFill(Color.GREEN);
			circle.setStroke(Color.GREEN);
			}
			bp.setCenter(circle);
		}
		
		return bp;
		// }
	}

}
