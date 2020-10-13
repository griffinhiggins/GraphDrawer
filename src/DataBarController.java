package application;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DataBarController {
	public Node n = null;
	@FXML public Label lbl = new Label();
	@FXML public TextField idTxtField = new TextField();
	@FXML public TextField fnameTxtField = new TextField();
	@FXML public TextField lnameTxtField = new TextField();
	@FXML public ChoiceBox<String> affChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList("Student", "Faculty", "Third"));
	@FXML public Button submitBtn;
//	@FXML
//	protected void initialize(){
//		if(this.n != null) {
//			fnameTxtField.setText("griff");
//	    	lnameTxtField.setText("higg");
//		}
//	}
	public void setSelectedNode(Node n){
		this.n = n;
	}
	@FXML
	public void populateData(){
		fnameTxtField.setText("griff");
    	lnameTxtField.setText("higg");
	}
}
