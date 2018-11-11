package cn.Jzsst.Maze;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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

	public Pane MazeGeneration(String string, int Spe,int num,int BG,int END) throws Exception {
		pane.getChildren().clear();
		this.Spe = Spe;
		mapSpe = (800 - Spe * 5) / Spe;
		return bulid(string , num,BG,END);

	}

	private Pane bulid(String string,int num,int BG,int END) {

		double color_num = 1.5;
		Line line = new Line();
		BorderPane bp = new BorderPane();

		int d = 0;
		while (d < 4) {
			if (string.charAt(d) == '1') {

				switch (d) {
				case 0:
					line = new Line(10, 10, mapSpe, 10);
					line.setStroke(Color.YELLOW);
					line.setStroke(Color.BLACK);
					line.setStrokeWidth(color_num);
					bp.setTop(line);
					break;
				case 1:
					line = new Line(mapSpe, 10, mapSpe, mapSpe);
					line.setStroke(Color.YELLOW);
					line.setStroke(Color.BLACK);
					line.setStrokeWidth(color_num);
					bp.setRight(line);
					break;
				case 2:
					line = new Line(10, mapSpe, mapSpe, mapSpe);
					line.setStroke(Color.YELLOW);
					line.setStroke(Color.BLACK);
					line.setStrokeWidth(color_num);
					bp.setBottom(line);
					break;
				case 3:
					line = new Line(10, 10, 10, mapSpe);
					line.setStroke(Color.YELLOW);
					line.setStroke(Color.BLACK);
					line.setStrokeWidth(color_num);
					bp.setLeft(line);
					break;
				default:
					break;
				}
			}
			d++;
		}
		//³öÃ»µã
		if (num == END || num == BG) {
			Circle circle = new Circle((600 - Spe * 5) / (Spe * 2 * 2));
			if(num == BG) {
				circle.setFill(Color.RED);
				circle.setStroke(Color.RED);
			}
			if(num == END) {
				circle.setFill(Color.GREEN);
				circle.setFill(Color.WHITE);
				circle.setStroke(Color.GREEN);
			}
			bp.setCenter(circle);
		}
		
		return bp;
		// }
	}

}
