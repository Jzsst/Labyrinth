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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * δ��� 1.���� 2.����� 3.����̶� 4.��ĩ�� 5.UI�ı� 6.��������(�����ʾ)
 * 
 */
// ���� 8*8 ��ͼ ��(0 , 0) �� ( 7 , 7);
public class Maze extends Application {
	private HBox AllPane = new HBox();
	private depthMaze depthRoad = new depthMaze();
	private spanMaze spanRoad = new spanMaze();
	// �������
	private Timeline timeline = null;
	private mazeMap map = new mazeMap();
	private GridPane Maze_map_pane = new GridPane();
	private int Num_Animation_Frame = 0;
	private EventHandler<ActionEvent> generate_EventHandler = null;
	// ������
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

		Label label = new Label("Maze");
		label.setFont(new Font(200));
		label.setPadding(new Insets(200, 0, 250, 100));

		pane.getChildren().add(label);

		Text text = new Text("�Թ�����ģʽ");
		Button Bautomatic = new Button("�Զ�����");
		Button Bmanually = new Button("�ֶ�����");
		Button Breset = new Button("����");
		TextField textFieldSpe = new TextField();
		TextField fieldError = new TextField("������Ϣ��ʾ...");
		Button button = new Button("Ѱ��·��");
		Button button1 = new Button("�����Թ�");
		Button button2 = new Button("���·��");
		Button button3 = new Button("����Ѱ·");
		Button button4 = new Button("����Թ��ļ�(*.txt)");
		Button sure = new Button("ȷ��");
		sure.setDisable(true);
		button.setDisable(true);
		button1.setDisable(true);
		button2.setDisable(true);
		button3.setDisable(true);
		button4.setDisable(true);
		textFieldSpe.setDisable(true);
		
		// button
		VBox paneRight = new VBox();
		paneRight.setPadding(new Insets(0, 40, 0, 100));
		paneRight.setAlignment(Pos.CENTER);
		paneRight.setSpacing(10);
		// ���ɹ���
		HBox mode = new HBox();
		mode.getChildren().addAll(Bautomatic, Bmanually);
		mode.setAlignment(Pos.CENTER);
		mode.setSpacing(10);
		mode.setPadding(new Insets(10, 0, 40, 0));

		text.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		paneRight.getChildren().add(text);
		paneRight.getChildren().add(mode);

		// �ֶ�����

		Text text3 = new Text("�Թ���ģ");
		text3.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		paneRight.getChildren().add(text3);

		VBox b1 = new VBox();
		b1.setAlignment(Pos.CENTER);
		b1.setSpacing(20);
		b1.getChildren().addAll(button4, textFieldSpe, sure);
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
		paneRight.getChildren().add(new Label("�����ٶ�"));
		paneRight.getChildren().add(new Label("��              ---->            ��"));
		paneRight.getChildren().add(slider);
		// PaneRight
		// ***************
		// �¼�������
		// ����
		Breset.setOnAction(e -> {
			mazeList.clear();
			// �������¶���
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
			Bautomatic.setDisable(false);
			Bmanually.setDisable(false);
			pane.getChildren().clear();
			pane.getChildren().add(label);
			fieldError.setText("������Ϣ��ʾ...");
			flag = 0;
			Spe = 0;
		});
		// ����ģʽ
		// �Զ���ť
		Bautomatic.setOnAction(e -> {
			Bautomatic.setDisable(true);
			Bmanually.setDisable(true);
			sure.setDisable(false);
			textFieldSpe.setDisable(false);
		});

		sure.setOnAction(e -> {

			pane.getChildren().clear();
			try {
				Spe = Integer.valueOf(textFieldSpe.getText().trim());
				gp = new GenerationPath(Spe);
				ArrayList<MyPoint> GPathRoad = gp.getMaze();
				for (int i = 0; i < GPathRoad.size(); i++) {
					mazeList.add(GPathRoad.get(i).toState());
				}
				// *******************************
				// 2018-9-9
				// ��Ӷ���
				Maze_Generate_Animation();

				// *******************************

				sure.setDisable(true);
				button.setDisable(false);
				button1.setDisable(false);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				pane.getChildren().add(label);
				fieldError.setText("�������������ֵ!!");
			}

		});
		// �ֶ���ť
		Bmanually.setOnAction(e -> {
			Bmanually.setDisable(true);
			Bautomatic.setDisable(true);
			sure.setDisable(true);
			textFieldSpe.setDisable(true);
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

				// ����

				Maze_Generate_Animation();

				// *********************
				button4.setDisable(true);
				button.setDisable(false);
				button1.setDisable(false);
			} catch (Exception e1) {
				fieldError.setText("����ѡ��txt�ļ�!!");
			}

		});
		// Button button = new Button("Ѱ��·��");
		// Button button1 = new Button("�����Թ�");
		// Button button2 = new Button("���·��");
		// Button button3 = new Button("����Ѱ·");

		// ����
		// gridPand2
		// mazePointB = depthRoad.getMazePoint1();
		// mazeRoadB =depthRoad.depthFindRoad(mazeList, Spe, 1);
		button3.setOnAction(e -> {
			gridPane2.getChildren().clear();
			gridPane1.getChildren().clear();

			pane.getChildren().clear();
			pane.setLayoutY(60);
			pane.setLayoutX(60);
			try {
				// **
				Stack<MyPoint> mazePointT = new Stack<>();
				Stack<String> mazeRoadT = new Stack<>();
				for (int i = 0; i < flag; i++) {
					mazePointT.add(mazePointB.get(i));
					mazeRoadT.add(mazeRoadB.get(i));
				}
				flag++;
				Ui(mazePointT, mazeRoadT, Color.CORNFLOWERBLUE, 1);
				// **
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (flag == mazePointB.size()) {
				try {
					Ui(spanRoad.getMazePoint(), spanRoad.spanFindRoad(mazeList, Spe), new Color(0, 1, 0, 1), 0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				button3.setDisable(true);
				gridPane2.getChildren().clear();
			}
			pane.getChildren().clear();
			pane.getChildren().add(gridPane1);
			pane.getChildren().add(gridPane2);

		});

		// ���
		button2.setOnAction(e -> {
			gridPane1.getChildren().clear();
			// ����
			try {
				Num_Animation_Frame=0;
				Stack<String> spanRoad_Back=spanRoad.spanFindRoad(mazeList, Spe);
//				Stack<String> spanRoad_Just = new Stack<>();
//				
//				
//				for (int i = 0; i<spanRoad_Back.size()-1; i++) {
//					spanRoad_Just.set(spanRoad_Back.get(i));
//				}
//				
				
				Maze_Search_Animation(spanRoad.getMazePoint(), spanRoad_Back, new Color(0, 1, 0, 1), 0);
			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pane.getChildren().clear();
			pane.setLayoutY(60);
			pane.setLayoutX(60);
			pane.getChildren().add(gridPane1);

		});

		// ����
		button1.setOnAction(e -> {
			// ����
			button3.setDisable(false);
			button2.setDisable(false);
			gridPane1.getChildren().clear();

			try {
				mazePointB = depthRoad.getMazePoint1();
				mazeRoadB = depthRoad.depthFindRoad(mazeList, Spe, 1);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				Num_Animation_Frame=0;
				Maze_Search_Animation(mazePointB, mazeRoadB, new Color(0, 0, 1, 1), 0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pane.getChildren().clear();
			pane.setLayoutY(60);
			pane.setLayoutX(60);
			pane.getChildren().add(gridPane1);

		});

		// Ѱ��·��
		button.setOnAction(e -> {

			// ����
			gridPane1.getChildren().clear();
			try {
				Num_Animation_Frame=0;
				Maze_Search_Animation(depthRoad.getMazePoint(),  depthRoad.depthFindRoad(mazeList, Spe, 0),  new Color(1, 0, 0, 1), 0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pane.getChildren().clear();
			pane.setLayoutY(60);
			pane.setLayoutX(60);
			pane.getChildren().add(gridPane1);

		});
		// ***ע��
		AllPane.getChildren().add(pane);
		AllPane.setPadding(new Insets(0, 0, 20, 0));
		AllPane.getChildren().add(paneRight);
		AllPane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(AllPane);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("file:maze.jpg"));
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Maze1.0v ByJzsst");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void Ui(Stack<MyPoint> mazePoint, Stack<String> mazeRoad, Color color, int num) {

		try {

			Polyline polyline = null;
			for (int i = 0; i < mazeList.size(); i++) {

				BorderPane bp = new BorderPane();

				int d = 0;
				while (d < 4) {
					if (mazeList.get(i).charAt(d) == '1') {

						switch (d) {
						case 0:
							polyline = new Polyline(0, 0, (600 - Spe * 5) / Spe, 0);
							bp.setTop(polyline);
							break;
						case 1:
							polyline = new Polyline((600 - Spe * 5) / Spe, 0, (600 - Spe * 5) / Spe,
									(600 - Spe * 5) / Spe);
							bp.setRight(polyline);
							break;
						case 2:
							polyline = new Polyline(0, (600 - Spe * 5) / Spe, (600 - Spe * 5) / Spe,
									(600 - Spe * 5) / Spe);
							bp.setBottom(polyline);
							break;
						case 3:
							polyline = new Polyline(0, 0, 0, (600 - Spe * 5) / Spe);
							bp.setLeft(polyline);
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
				if (i == mazeList.size() - 1 || i == 0) {
					Circle circle = new Circle((600 - Spe * 5) / (Spe * 2 * 2));
					circle.setFill(Color.GREEN);
					circle.setStroke(Color.GREEN);
					bp.setCenter(circle);
				}

				if (num == 0) {
					gridPane1.add(bp, x, y);
				} else
					gridPane2.add(bp, x, y);
				// ת��
			}
			gridPane1.setLayoutX(60);
			gridPane1.setLayoutY(60);
			gridPane2.setLayoutX(60);
			gridPane2.setLayoutY(60);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/** �Թ�Ѱ·���� */
	public void Maze_Search_Animation(Stack<MyPoint> mazePoint, Stack<String> mazeRoad, Color color, int num) {
		generate_EventHandler = e -> {

			Stack<String> mazeRoad_temp = new Stack<>();	
			Stack<MyPoint> mazePoint_temp = new Stack<>();
			// ÿһ֡�Ķ���,��ÿһ�����������map_Maze
			for (int i = 0; i < Num_Animation_Frame; i++) {
				mazeRoad_temp.push(mazeRoad.get(i));
				mazePoint_temp.push(mazePoint.get(i));
			}
			try {
				Ui(mazePoint_temp,mazeRoad , new Color(0, 1, 0, 1), 0);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Num_Animation_Frame++;
			// ����ֹͣ
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

	/** �����Թ����� */
	public void Maze_Generate_Animation() {
		generate_EventHandler = e -> {

			// ÿһ֡�Ķ���,��ÿһ�����������map_Maze
			try {
				int x = Num_Animation_Frame % Spe;
				int y = Num_Animation_Frame / Spe;

				Maze_map_pane.add(map.MazeGeneration(mazeList.get(Num_Animation_Frame), Spe, Num_Animation_Frame), x,
						y);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Num_Animation_Frame++;
			// ����ֹͣ
			if (Num_Animation_Frame == mazeList.size())
				timeline.stop();
		};

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