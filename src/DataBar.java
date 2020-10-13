package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class DataBar{
	public Node n;
	public HBox hbox;
	public Label idTxtFieldLbl, fnameTxtFieldLbl, lnameTxtFieldLbl, frqTxtFieldLbl;
	public TextField idTxtField, fnameTxtField, lnameTxtField, frqTxtField;
	public Button updateBtn;
	public DataBar() {
		n = null;
		hbox = new HBox(10);
		idTxtFieldLbl = new Label("ID:");
		idTxtFieldLbl.setPadding(new Insets(3, 0, 0, 10));
		idTxtField = new TextField("");
		idTxtField.setPrefWidth(75);
		fnameTxtFieldLbl = new Label("first name:");
		fnameTxtFieldLbl.setPadding(new Insets(3, 0, 0, 0));
		fnameTxtField = new TextField("");
		lnameTxtFieldLbl = new Label("last name:");
		lnameTxtFieldLbl.setPadding(new Insets(3, 0, 0, 0));
		lnameTxtField = new TextField("");
		frqTxtFieldLbl = new Label("Frequency:");
		frqTxtFieldLbl.setPadding(new Insets(3, 0, 0, 0));
		frqTxtField = new TextField("");
		frqTxtField.setPrefWidth(75);
		updateBtn = new Button("update");
		updateBtn.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	setNodeAttr();
	        }
	    });
		hbox.getChildren().addAll(
				idTxtFieldLbl,idTxtField,
				fnameTxtFieldLbl,fnameTxtField,
				lnameTxtFieldLbl,lnameTxtField,
				frqTxtFieldLbl,frqTxtField,
				updateBtn);
		hbox.setPadding(new Insets(3));
	}
	public void getNodeAttr(Node n) {
		String mode = Main.interactionModel.getMode();
		if(n != null) {
			this.n = n;
			idTxtField.setText(Integer.toString(n.ID));
			fnameTxtField.setText(n.fName);
			lnameTxtField.setText(n.lName);
			if("Physical".equals(mode)) {
				frqTxtField.setText(Integer.toString(n.physicalFrq));
			}
			else if("Cyber".equals(mode)){

				frqTxtField.setText(Integer.toString(n.cyberFrq));
			}
			else{
				n.hybridFrq = n.physicalFrq + n.cyberFrq;
				frqTxtField.setText(Integer.toString(n.hybridFrq));
			}
		}
	}
	public void setNodeAttr() {
		String mode = Main.interactionModel.getMode();
		if(this.n != null) {
			this.n.ID = Integer.parseInt(idTxtField.getText());
			this.n.fName = fnameTxtField.getText();
			this.n.lName = lnameTxtField.getText();
			int frq = Integer.parseInt(frqTxtField.getText());
			if("Physical".equals(mode)) {
				n.physicalFrq = frq;
				n.hybridFrq += n.physicalFrq;
			}
			else if("Cyber".equals(mode)){
				n.cyberFrq = frq;
				n.hybridFrq += n.cyberFrq;
			}
//			else{
//				n.hybridFrq = n.cyberFrq + n.physicalFrq;
//			}
			
		}
	}
}
