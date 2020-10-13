package application;

import com.sun.javafx.geom.Point2D;
import javafx.scene.shape.Circle;

public class Node extends Circle{
	public static int IDs = 0;
	public int ID;
	private boolean selected;
	private boolean shiftSelected;
	private String hybridNodeType;
	public String fName,lName,affiliation;
	public int hybridFrq,cyberFrq,physicalFrq;
	public Node(int x, int y, int radius, String nodeType) {
		this.ID = IDs;
		this.setCenterX(x);
		this.setCenterY(y);
		this.setRadius(radius);
		this.hybridNodeType = nodeType;
		selected = false;
		shiftSelected = false;
		fName = "";
		lName = "";
		hybridFrq = 0;
		cyberFrq = 0;
		physicalFrq = 0;
		IDs++;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isShiftSelected() {
		return shiftSelected;
	}
	public void setShiftSelected(boolean shiftSelected) {
		this.shiftSelected = shiftSelected;
	}
	public Point2D getPosition(int x, int y){
		return new Point2D(x,y);
	}
	public boolean containsPoint(int x, int y){
		return this.contains(x, y);
	}
	public String getHybridNodeType() {
		return hybridNodeType;
	}
	public void setHybridNodeType(String newHybridNodeType) {
		hybridNodeType = newHybridNodeType;
	}
	public String toString(){
		return "Node(" + this.ID + "):{"
				+"x: "+(int)this.getCenterX()
				+", y: "+(int)this.getCenterY()
				+", r: "+(int)this.getRadius()
				+", sel: "+this.isSelected()
				+", selShift: "+this.isShiftSelected()
				+", mode: "+this.getHybridNodeType()
				+"}";
	}
}