package application;

import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class View extends Pane {
	private Canvas canvas;
	private GraphicsContext gc;
	public View() {
		canvas = new Canvas();
		this.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		
		Main.graphModel.nodeListProperty().addListener(new ListChangeListener<Node>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Node> c) {
				draw();
			}
		}); 
	}
	@Override
	public void layoutChildren(){
		canvas.setWidth(this.getWidth());
		canvas.setHeight(this.getHeight());
		draw();
	}
	public void draw() {
		gc.setFill(Color.GREY);
		gc.fillRect(0,0,this.getWidth(),this.getHeight());
		String mode = Main.interactionModel.getMode();
		if("Physical".equals(mode)) {
			for(Edge e : Main.graphModel.edgeListPhysicalProperty()) {
				Node[] arr = e.getEdges();
				int vX = (int)arr[0].getCenterX();
				int vY = (int)arr[0].getCenterY();
				int v_primeX = (int)arr[1].getCenterX();
				int v_primeY = (int)arr[1].getCenterY();
				gc.strokeLine(vX, vY, v_primeX, v_primeY);
			}
		}
		else if("Cyber".equals(mode)){
			for(Edge e : Main.graphModel.edgeListCyberProperty()) {
				Node[] arr = e.getEdges();
				int vX = (int)arr[0].getCenterX();
				int vY = (int)arr[0].getCenterY();
				int v_primeX = (int)arr[1].getCenterX();
				int v_primeY = (int)arr[1].getCenterY();
				gc.strokeLine(vX, vY, v_primeX, v_primeY);
			}
		}
		else{
			for(Edge e : Main.graphModel.edgeListHybridProperty()) {
				Node[] arr = e.getEdges();
				int vX = (int)arr[0].getCenterX();
				int vY = (int)arr[0].getCenterY();
				int v_primeX = (int)arr[1].getCenterX();
				int v_primeY = (int)arr[1].getCenterY();
				gc.strokeLine(vX, vY, v_primeX, v_primeY);
			}
		}
		for (Node n : Main.graphModel.nodeListProperty()){
			int x = (int)n.getCenterX();
			int y = (int)n.getCenterY();
			double r;
			if("Physical".equals(mode)) {
				r = n.physicalFrq;
			}
			else if("Cyber".equals(mode)){
				r = n.cyberFrq;
			}
			else{
				r = n.hybridFrq;
			}
			r = r * 2 + 20;
			n.setRadius(r);
			if(n.isSelected() && !n.isShiftSelected()) {
				gc.setFill(Color.PURPLE);
			}
			else {
				if("Ego".equals(n.getHybridNodeType())) {
				 gc.setFill(Color.ORANGE);
				}
				else if("Physical".equals(mode)) {
				 	gc.setFill(Color.SALMON);
				}
				else if("Cyber".equals(mode)){
					gc.setFill(Color.LIGHTBLUE);
				}
				else{
					String hybridNodeType = n.getHybridNodeType();
					if("Physical".equals(hybridNodeType)) {
						gc.setFill(Color.SALMON);
					}
					else if("Cyber".equals(hybridNodeType)) {
						gc.setFill(Color.LIGHTBLUE);
					}
					else if("Hybrid".equals(hybridNodeType)){
						gc.setFill(Color.LIGHTGREEN);
					}
					else {
						gc.setFill(Color.MAGENTA);;
					}
				}
				if(n.isShiftSelected()) {
					r += 2;
					gc.strokeOval(x - r, y - r, r * 2, r * 2);
					gc.strokeLine(x, y, Main.graphController.getX(), Main.graphController.getY());
				}
			}
			gc.fillOval(x - r, y - r, r * 2, r * 2);
			gc.strokeOval(x - r, y - r, r * 2, r * 2);
			gc.setLineWidth(1.0);
	        gc.setFill(Color.BLACK);
	        
	        if(n.ID > 9) {
	        	gc.fillText(Integer.toString(n.ID), x-7, y+5);
	        }
	        else {
	        	gc.fillText(Integer.toString(n.ID), x-4, y+5);
	        }
		}
	}
}
