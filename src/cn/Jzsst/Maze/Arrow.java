package cn.Jzsst.Maze;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

public class Arrow extends Pane {
	private double Sx = 0;
	private double Sy = 0;
	private double Ex = 0;
	private double Ey = 0;
	private double mazeSpe = 0;
	private Color color ;
	private Polyline polyline;

	public Arrow(double x1, double y1, double x2, double y2, double mazeSpe,Color color) {
		Sx = x1;
		Sy = y1;
		Ex = x2;
		Ey = y2;
		this.mazeSpe = mazeSpe;
		this.color = color;
	}

	public Polyline getPolyline() {
		drawArrowLine();
		return polyline;
	}

	public void drawArrowLine() {
		double slope = ((((double) Sy) - (double) Ey)) / (((double) Sx) - (((double) Ex)));
		double arctan = Math.atan(slope);

		double set45 = 1.57 / 2;

		if (Sx < Ex) {
			set45 = -1.57 * 1.5;
		}
		double arrlen = mazeSpe / 3;

		polyline = new Polyline(
				Sx, Sy,
				(Ex + (Math.cos(arctan + set45) * arrlen)),
				((Ey)) + (Math.sin(arctan + set45) * arrlen),
				Ex, Ey,
				(Ex + (Math.cos(arctan - set45) * arrlen)),
				((Ey)) + (Math.sin(arctan - set45) * arrlen),
				Sx, Sy
				);
		polyline.setFill(color);
		polyline.setStroke(color);
	}
}
