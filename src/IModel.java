package application;

public class IModel {
	public String mode;
	public String action;
	public void setMode(String mode){
		this.mode = mode;
		Main.graphView.draw();
	}
	public String getMode() {
		return this.mode;
	}
	public void setAction(String action){
		this.action = action;
	}
	public String getAction() {
		return this.action;
	}
	public String toString() {
		return "InteractionModel:{Mode(" + this.mode + ") Action(" + this.action + ")}";
	}
}
