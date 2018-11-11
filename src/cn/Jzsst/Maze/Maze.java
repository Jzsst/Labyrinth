package cn.Jzsst.Maze;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 未完成 3.界面固定 4.出末点 5.UI改变 6.其它功能(点击显示)
 * 
 */
public class Maze extends Application {
	//初末点
	private int BG =0;
	private int END =0;
	
	// 背景
	private StackPane stackPane = new StackPane();
	private HBox AllPane = new HBox();
	private depthMaze depthRoad = new depthMaze();
	private spanMaze spanRoad = new spanMaze();
	// 动画添加
	private Timeline timeline = null;
	private mazeMap map = new mazeMap();
	private GridPane Maze_map_pane = new GridPane();
	private int Num_Animation_Frame = 0;
	private EventHandler<ActionEvent> generate_EventHandler = null;
	// 滑动条
	private Slider slider = new Slider();
	// ***********
	private ArrayList<String> mazeList = new ArrayList<>();
	private GenerationPath gp;
	private int Spe = 0;
	private File select;
	private GridPane gridPane1 = new GridPane();
	private int flag = 0;
	private GridPane gridPane2 = new GridPane();
	private Stack<MyPoint> mazePointB;
	private Stack<String> mazeRoadB;
	private Pane pane = new Pane();

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// gridPane1||gridPane2||Maze_map_pane初始化

		gridPane1.setPadding(new Insets(220, 90, 250, 100));
		gridPane2.setPadding(new Insets(220, 90, 250, 100));
		Maze_map_pane.setPadding(new Insets(220, 90, 250, 100));

		Label label = new Label("Maze");
		label.setFont(new Font(200));
		label.setTextFill(Color.YELLOW);
		label.setPadding(new Insets(320, 100, 250, 100));

		pane.getChildren().add(label);

		Text text = new Text("迷宫生成模式");
		Button Bautomatic = new Button("自动生成");
		Button Bmanually = new Button("手动生成");
		Button Breset = new Button("重置");
		TextField textFieldSpe = new TextField();
		//输入起点终点
		TextField textFieldBG_X = new TextField();
		TextField textFieldBG_Y = new TextField();
		TextField textFieldEnd_X = new TextField();
		TextField textFieldEnd_Y = new TextField();
		textFieldBG_X.setMaxWidth(40);
		textFieldBG_Y.setMaxWidth(40);
		textFieldEnd_X.setMaxWidth(40);
		textFieldEnd_Y.setMaxWidth(40);
		//*********
		TextField fieldError = new TextField("错误信息显示...");
		Button button = new Button("寻找路径");
		Button button1 = new Button("遍历迷宫");
		Button button2 = new Button("最短路径");
		Button button3 = new Button("单步寻路");
		Button button4 = new Button("添加迷宫文件(*.txt)");
		Button sure = new Button("确定");
		sure.setDisable(true);
		button.setDisable(true);
		button1.setDisable(true);
		button2.setDisable(true);
		button3.setDisable(true);
		button4.setDisable(true);
		textFieldSpe.setDisable(true);
		textFieldBG_X.setDisable(true);
		textFieldBG_Y.setDisable(true);
		textFieldEnd_X.setDisable(true);
		textFieldEnd_Y.setDisable(true);

		// button
		VBox paneRight = new VBox();
		paneRight.setPadding(new Insets(0, 40, 0, 100));
		paneRight.setAlignment(Pos.CENTER);
		paneRight.setSpacing(10);
		// 生成功能
		HBox mode = new HBox();
		mode.getChildren().addAll(Bautomatic, Bmanually);
		mode.setAlignment(Pos.CENTER);
		mode.setSpacing(10);
		mode.setPadding(new Insets(10, 0, 40, 0));

		text.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		paneRight.getChildren().add(text);
		paneRight.getChildren().add(mode);

		//初终点
		
		VBox box = new VBox();
		Text text2 = new Text("                      (   X  ,   Y   )  ");
		Text textBG = new Text("初始位置:");
		Text textEND = new Text("终点位置:");
		Circle circleBG = new Circle(15);
		Circle circleEND = new Circle(15);
		circleBG.setFill(Color.RED);
		circleBG.setStroke(Color.RED);
		
		circleEND.setFill(Color.GREEN);
		circleEND.setFill(Color.WHITE);
		circleEND.setStroke(Color.GREEN);
		Text textkong1 = new Text("      ");
		Text textkong2 = new Text("      ");
		box.setSpacing(10);
		HBox box_BG = new HBox();
		box_BG.getChildren().addAll(textBG,textFieldBG_X, textFieldBG_Y,textkong1,circleBG);
		box_BG.setAlignment(Pos.CENTER);
		box_BG.setSpacing(10);
		
		HBox box_END = new HBox();
		box_END.getChildren().addAll(textEND,textFieldEnd_X, textFieldEnd_Y,textkong2,circleEND);
		box_END.setAlignment(Pos.CENTER);
		box_END.setSpacing(10);
		
		box.getChildren().add(text2);
		box.getChildren().add(box_BG);
		box.getChildren().add(box_END);
		
		
		// 手动功能

		Text text3 = new Text("迷宫规模");
		text3.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		paneRight.getChildren().add(text3);

		VBox b1 = new VBox();
		b1.setAlignment(Pos.CENTER);
		b1.setSpacing(20);
		b1.getChildren().addAll(button4, textFieldSpe,box, sure);
		b1.setPadding(new Insets(15, 0, 30, 0));
		paneRight.getChildren().add(b1);

		VBox b2 = new VBox();
		b2.setAlignment(Pos.CENTER);
		b2.setSpacing(10);
		b2.getChildren().addAll(button, button1, button2, button3);
		b2.setPadding(new Insets(15, 0, 30, 0));
		paneRight.getChildren().add(b2);
		paneRight.getChildren().add(Breset);
		paneRight.getChildren().add(fieldError);
		paneRight.getChildren().add(new Label("动画速度"));
		paneRight.getChildren().add(new Label("慢              ---->            快"));
		paneRight.getChildren().add(slider);
		// PaneRight
		// ***************
		// 事件处理器
		// 重置
		Breset.setOnAction(e -> {
			mazeList.clear();
			// 动画重新定义
			Num_Animation_Frame = 0;
			Maze_map_pane = new GridPane();
			// slider = new Slider(20,1000,20);
			// *************
			button.setDisable(true);
			button1.setDisable(true);
			button2.setDisable(true);
			button3.setDisable(true);
			button4.setDisable(true);
			sure.setDisable(true);
			textFieldSpe.setDisable(true);
			textFieldSpe.setDisable(true);
			textFieldBG_X.setDisable(true);
			textFieldBG_Y.setDisable(true);
			textFieldEnd_X.setDisable(true);
			textFieldEnd_Y.setDisable(true);
			Bautomatic.setDisable(false);
			Bmanually.setDisable(false);
			pane.getChildren().clear();
			pane.getChildren().add(label);
			fieldError.setText("错误信息显示...");
			flag = 0;
			Spe = 0;
		});
		// 生成模式
		// 自动按钮
		Bautomatic.setOnAction(e -> {
			Bautomatic.setDisable(true);
			Bmanually.setDisable(true);
			sure.setDisable(false);
			textFieldBG_X.setDisable(false);
			textFieldBG_Y.setDisable(false);
			textFieldEnd_X.setDisable(false);
			textFieldEnd_Y.setDisable(false);
			textFieldSpe.setDisable(false);
		});

		sure.setOnAction(e -> {

			pane.getChildren().clear();
			try {
				Spe = Integer.valueOf(textFieldSpe.getText().trim());
				BG = (Integer.valueOf(textFieldBG_Y.getText().trim())-1)*Spe+(Integer.valueOf(textFieldBG_X.getText().trim())-1);
				 END = (Integer.valueOf(textFieldEnd_Y.getText().trim())-1)*Spe+(Integer.valueOf(textFieldEnd_X.getText().trim())-1);
				gp = new GenerationPath(Spe ,BG,END);
				ArrayList<MyPoint> GPathRoad = gp.getMaze();
				for (int i = 0; i < GPathRoad.size(); i++) {
					mazeList.add(GPathRoad.get(i).toState());
				}
				// 添加动画
				Maze_Generate_Animation(BG,END);
				sure.setDisable(true);
				button.setDisable(false);
				button1.setDisable(false);
			} catch (Exception e1) {
				pane.getChildren().add(label);
				fieldError.setText("请在输入框输入值!!");
			}

		});
		// 手动按钮
		Bmanually.setOnAction(e -> {
			Bmanually.setDisable(true);
			Bautomatic.setDisable(true);
			sure.setDisable(true);
			textFieldSpe.setDisable(true);
			textFieldBG_X.setDisable(false);
			textFieldBG_Y.setDisable(false);
			textFieldEnd_X.setDisable(false);
			textFieldEnd_Y.setDisable(false);
			button4.setDisable(false);
		});
		button4.setOnAction(e -> {

			int num = 0;
			try {
				Stage mainStage = null;
				FileChooser chooser = new FileChooser();
				chooser.setInitialDirectory(new File("."));
				select = chooser.showOpenDialog(mainStage);
				Scanner in = new Scanner(select);
				while (in.hasNext()) {
					mazeList.add(in.next());
					num++;
				}
				in.close();
				Spe = (int) Math.sqrt(num);
				pane.getChildren().clear();
				// ********************

				 BG = (Integer.valueOf(textFieldBG_Y.getText().trim())-1)*Spe+(Integer.valueOf(textFieldBG_X.getText().trim())-1);
				 END = (Integer.valueOf(textFieldEnd_Y.getText().trim())-1)*Spe+(Integer.valueOf(textFieldEnd_X.getText().trim())-1);
				// 动画

				Maze_Generate_Animation(BG, END);

				// *********************
				button4.setDisable(true);
				button.setDisable(false);
				button1.setDisable(false);
			} catch (Exception e1) {
				fieldError.setText("请在选择txt文件!!");
			}

		});
		// 单步
		button3.setOnAction(e -> {
			gridPane2.getChildren().clear();
			gridPane1.getChildren().clear();

			pane.getChildren().clear();
			try {
				// **
				Stack<MyPoint> mazePointT = new Stack<>();
				Stack<String> mazeRoadT = new Stack<>();
				for (int i = 0; i < flag; i++) {
					mazePointT.add(mazePointB.get(i));
					mazeRoadT.add(mazeRoadB.get(i));
				}
				flag++;
				// Color.CORNFLOWERBLUE
				Ui(mazePointT, mazeRoadT, Color.CORNFLOWERBLUE, 1);
				// **
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (flag == mazePointB.size()) {
				try {
					Ui(spanRoad.getMazePoint(), spanRoad.spanFindRoad(mazeList, Spe,BG,END), new Color(0, 1, 0, 1), 0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				button3.setDisable(true);
				gridPane2.getChildren().clear();
			}
			pane.getChildren().clear();
			pane.getChildren().add(gridPane1);
			pane.getChildren().add(gridPane2);

		});

		// 最短
		button2.setOnAction(e -> {
			gridPane1.getChildren().clear();
			// 控制
			try {
				Num_Animation_Frame = 0;
				Stack<String> spanRoad_Back = spanRoad.spanFindRoad(mazeList, Spe,BG,END);
				// 将两个数组进行倒置
//				Stack<String> spanRoad_Back_T = (Stack) spanRoad_Back.clone();
//				@SuppressWarnings("rawtypes")
//				Stack spanRoad_T =(Stack) spanRoad.getMazePoint().clone();	
//				spanRoad_Back_T =reverseStack(spanRoad_Back_T);
//				spanRoad_T =reverseStack(spanRoad_T);
//				Maze_Search_Animation(spanRoad_T, spanRoad_Back_T, new Color(0, 1, 0, 1), 0);
				Maze_Search_Animation(spanRoad.getMazePoint(), spanRoad_Back, new Color(0, 1, 0, 1), 0);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			pane.getChildren().clear();
			pane.getChildren().add(gridPane1);

		});

		// 遍历
		button1.setOnAction(e -> {
			// 控制
			button3.setDisable(false);
			button2.setDisable(false);
			gridPane1.getChildren().clear();

			try {
				mazePointB = depthRoad.getMazePoint1();
				mazeRoadB = depthRoad.depthFindRoad(mazeList, Spe, 1,BG,END);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				Num_Animation_Frame = 0;
				Maze_Search_Animation(mazePointB, mazeRoadB, new Color(0, 0, 1, 1), 0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			pane.getChildren().clear();
			pane.setLayoutY(60);
			pane.setLayoutX(60);
			pane.getChildren().add(gridPane1);

		});
		// 寻找路径
		button.setOnAction(e -> {
			// 控制
			gridPane1.getChildren().clear();
			try {
				Num_Animation_Frame = 0;
				Maze_Search_Animation(depthRoad.getMazePoint(), depthRoad.depthFindRoad(mazeList, Spe, 0,BG,END),
						new Color(1, 0, 0, 1), 0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			pane.getChildren().clear();
			pane.getChildren().add(gridPane1);
		});
		// 背景
		ImageView imageView = new ImageView();
//		ImageView imageView = new ImageView("file:file/backgrand.png");
		AllPane.getChildren().add(pane);
		AllPane.getChildren().add(paneRight);
		AllPane.setAlignment(Pos.CENTER);
		stackPane.getChildren().add(imageView);
		stackPane.getChildren().add(AllPane);
		Scene scene = new Scene(stackPane);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("file:file/logo.png"));
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Maze2.1v ByJzsst");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	// *************
//	private Stack reverseStack(Stack s) {
//		Queue r = new LinkedList();
//
//		// r.offer() 是将指定队列插到r中
//
//		// s.pop是 移除堆栈顶部的对象，并作为此函数的值返回该对象。
//		while (s.size() > 0)
//			r.offer(s.pop());
//
//		// s.push() 把项压入堆栈顶部。
//
//		// r.poll() 获取并移除此队列的头，如果此队列为空，则返回 null。
//		while (r.size() > 0)
//			s.push(r.poll());
//		return s;
//	}

	private void Ui(Stack<MyPoint> mazePoint, Stack<String> mazeRoad, Color color, int num) {

		double mapSpe = (800 - Spe * 5) / Spe;
		double color_num = 1.5;
		try {

			Line line = new Line();
			for (int i = 0; i < mazeList.size(); i++) {

				BorderPane bp = new BorderPane();

				int d = 0;
				while (d < 4) {
					if (mazeList.get(i).charAt(d) == '1') {

						switch (d) {
						case 0:
							line = new Line(10, 10,mapSpe, 10);
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
							line = new Line(10, mapSpe,mapSpe, mapSpe);
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
				int x = i % Spe;
				int y = i / Spe;

				for (int j = 0; j < mazePoint.size(); j++) {
					if (mazePoint.get(j).getX() + mazePoint.get(j).getY() * Spe == i
							&& mazePoint.get(j).getX() + mazePoint.get(j).getY() * Spe != (mazeList.size() - 1)) {
						switch (mazeRoad.get(j)) {
						case "Up":
							bp.setCenter(
									new Arrow(0, map.getMapSpe() * 0.16, 0, 0, map.getMapSpe(), color).getPolyline());
							break;
						case "Right":
							bp.setCenter(
									new Arrow(0, 0, map.getMapSpe() * 0.16, 0, map.getMapSpe(), color).getPolyline());
							break;
						case "Down":
							bp.setCenter(
									new Arrow(0, 0, 0, map.getMapSpe() * 0.16, map.getMapSpe(), color).getPolyline());
							break;
						case "Left":
							bp.setCenter(
									new Arrow(map.getMapSpe() * 0.16, 0, 0, 0, map.getMapSpe(), color).getPolyline());
							break;
						default:
							break;
						}
					}
				}
				if (i == END || i == BG) {
					Circle circle = new Circle((600 - Spe * 5) / (Spe * 2 * 2));
					if (i == BG) {
						circle.setFill(Color.RED);
						circle.setStroke(Color.RED);
					}
					if (i == END) {
						circle.setFill(Color.GREEN);
						circle.setFill(Color.WHITE);
						circle.setStroke(Color.GREEN);
					}
					bp.setCenter(circle);
				}
				
				if (num == 0) {
					gridPane1.add(bp, x, y);
				} else
					gridPane2.add(bp, x, y);

				// 转化
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/** 迷宫寻路动画 */
	public void Maze_Search_Animation(Stack<MyPoint> mazePoint, Stack<String> mazeRoad, Color color, int num) {
		generate_EventHandler = e -> {
			Stack<String> mazeRoad_temp = new Stack<>();
			Stack<MyPoint> mazePoint_temp = new Stack<>();
			// 每一帧的动画,将每一个方格添加入map_Maze
			for (int i = 0; i < Num_Animation_Frame; i++) {
				mazeRoad_temp.push(mazeRoad.get(i));
				mazePoint_temp.push(mazePoint.get(i));
			}
			try {
				Ui(mazePoint_temp, mazeRoad, color, 0);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Num_Animation_Frame++;
			// 动画停止
			if (Num_Animation_Frame == mazeRoad.size())
				timeline.stop();
		};
		Num_Animation_Frame = 0;
		timeline = new Timeline(new KeyFrame(Duration.millis(100), generate_EventHandler));
		slider.setMax(10 * Math.sqrt(Spe));
		slider.setMin(1 * Math.sqrt(Spe));
		timeline.rateProperty().bind(slider.valueProperty());
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.play();
	}

	/** 生成迷宫动画 */
	public void Maze_Generate_Animation(int BG ,int END) {
		generate_EventHandler = e -> {
			// 每一帧的动画,将每一个方格添加入map_Maze
			try {
				int x = Num_Animation_Frame % Spe;
				int y = Num_Animation_Frame / Spe;

				Maze_map_pane.add(map.MazeGeneration(mazeList.get(Num_Animation_Frame), Spe, Num_Animation_Frame,BG,END), x,
						y);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			Num_Animation_Frame++;
			// 动画停止
			if (Num_Animation_Frame == mazeList.size())
				timeline.stop();
		};

		Maze_map_pane.setPadding(new Insets(220, 90, 250, 100));
		pane.getChildren().add(Maze_map_pane);
		Num_Animation_Frame = 0;
		timeline = new Timeline(new KeyFrame(Duration.millis(100), generate_EventHandler));
		slider.setMax(10 * Math.sqrt(Spe));
		slider.setMin(1 * Math.sqrt(Spe));
		timeline.rateProperty().bind(slider.valueProperty());
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.play();
	}

}