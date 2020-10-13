package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ToolBarController {
	@FXML public Button writeBtn,readBtn;
	@FXML public ToggleButton hybridBtn, physicalBtn, cyberBtn, createBtn, deleteBtn;
	@FXML public ToggleGroup modeGroup, actionGroup;
	private IModel model = Main.interactionModel;
	private boolean initMode = true;
	private boolean initAction = true;
	@FXML
    private void setMode() {
		try {
			if(initMode) {
				model.setMode("");
				initMode = false;
			}
			ToggleButton modeBtn = (ToggleButton) modeGroup.getSelectedToggle();
			model.setMode(modeBtn.getText());
			if(Main.dataBar.n != null) {
				Main.dataBar.getNodeAttr(Main.dataBar.n);
			}
		}
		catch(NullPointerException exception) {
        	
        }
    }
	
	@FXML
    private void setAction() {
		try {
			if(initAction) {
				model.setAction("");
				initAction = false;
			}
			ToggleButton actionBtn = (ToggleButton) actionGroup.getSelectedToggle();
			model.setAction(actionBtn.getText());
		}
		catch(NullPointerException exception) {
        	
        }
	}
	
	@FXML
	private void writeIO() {
		Main.graphModel.writeIO();
	}
	
	@FXML
	private void readIO() {
		Main.graphModel.readIO();
	}
}
