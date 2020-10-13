package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Main extends Application {
	public static String color, action;
	public static final Model graphModel = new Model(20);
	public static final View graphView = new View();
	public static final Controller graphController = new Controller();
	public static final ToolBarController toolBarController = new ToolBarController();
	public static final IModel interactionModel = new IModel();
	public final static DataBar dataBar = new DataBar();
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane bp = new BorderPane();
			HBox toolBar = FXMLLoader.<HBox>load(getClass().getResource("Toolbar.fxml"));
			Scene scene = new Scene(bp,1000,750);
			bp.setTop(toolBar);
			bp.setCenter(graphView);
			bp.setBottom(dataBar.hbox);
			primaryStage.setTitle("SNA - SOCI4573");		
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

