package cn.Jzsst.Maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class test extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<String> mazeList = new ArrayList<>();
		Stage mainStage = null;
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File("."));
		File select = chooser.showOpenDialog(mainStage);
		int num = 0;
		Scanner in;
		try {
			in = new Scanner(select);
			while (in.hasNext()) {
				mazeList.add(in.next());
				num++;
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		spanMaze spanRoad = new spanMaze();
		spanRoad.spanFindRoad(mazeList,(int)Math.sqrt(num));//???????????????????
		Stack<String> stack = spanRoad.getMazeRoad();
		for (int i = 0; i < stack.size(); i++) {
			System.out.println(stack.get(i));
		}
//		BorderPane borderPane = new BorderPane();
//
//		Arrow ArrowUp = new Arrow(0, 10, 0, 0, 10, new Color(1, 0, 0, 1));
//		Arrow ArrowUp1 = new Arrow(0, 10, 0, 0, 10, new Color(1, 0, 0, 1));
//		Arrow ArrowRight = new Arrow(0, 0, 20, 0, 20, new Color(0, 1, 0, 1));
//		Arrow ArrowDown = new Arrow(0, 0, 0, 30, 30, new Color(0, 0, 1, 1));
//		Arrow ArrowLeft = new Arrow(40, 0, 0, 0, 40, new Color(1, 1, 1, 1));
//		borderPane.setCenter(new Text("123"));
//		borderPane.setCenter(ArrowLeft.getPolyline());
////		borderPane.setLeft(ArrowUp.getPane());
////		borderPane.setRight(ArrowRight.getPane());
////		borderPane.setBottom(ArrowDown.getPane());
//		Scene scene = new Scene(borderPane);
//		primaryStage.setScene(scene);
//		primaryStage.show();

	}
}

