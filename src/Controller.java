package application;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;

public class Controller {
	//SET THESE TO "TRUE" TO DEBUG THE MODEL EVENTS OR MOUSE SELECTION EVENTS IN CONSOLE
		public boolean printModelAction = false;
		public boolean printMouseAction = false;
	private int x_MOUSE;
	private int y_MOUSE;
	
	public Controller() {
		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
	            	try {
		            	int x = (int) e.getX();
						int y = (int) e.getY();
						x_MOUSE = x;
						y_MOUSE = y;
						
						Main.graphView.setCursor(Cursor.DEFAULT);
						Node n = Main.graphModel.getNodeAt(x, y);
						boolean userClicksNode = false;
						if(n != null) {
							userClicksNode = true;
						}
						boolean thereAreNodesAreCurrentlySelected = true;
						if(!Main.graphModel.isSelectedNode()) {
							thereAreNodesAreCurrentlySelected = false;
						}
						String action = Main.interactionModel.getAction();
						
						if(action.equals("create")) {
							//--------------------------------------------------------------------------
			            	if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			            		if(!thereAreNodesAreCurrentlySelected) {
									if(userClicksNode && e.isShiftDown()) {
										n.setShiftSelected(true); 
										n.setSelected(true);
										if(printMouseAction) {
											System.out.println(n + " SHIFT SELECTED");
										}
									}
									else if(userClicksNode) {
										n.setSelected(true);
										Main.dataBar.getNodeAttr(n);
										if(printMouseAction) {
											System.out.println(n + " SELECTED");
										}
									}
								}
			            	}
			            	//--------------------------------------------------------------------------
			            	else if(e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			            		if(thereAreNodesAreCurrentlySelected) {
			    					n = Main.graphModel.getSelectedNode();
			    					if(n.isShiftSelected()) {
			    						Node v_prime = Main.graphModel.getNodeAt(x,y);
			    						if(userClicksNode && n != v_prime) {
			    							n.setShiftSelected(true);
			    							v_prime.setShiftSelected(true);
			    							if("Physical".equals(Main.interactionModel.getMode())){
				    							Edge edge = Main.graphModel.addEdge("Physical", n, v_prime);
				    							if(printModelAction) {
				    								System.out.println(edge + " PHYSICAL - ADDED [E]");
				    							}
			    							}
			    							else if("Cyber".equals(Main.interactionModel.getMode())){
			    								Edge edge = Main.graphModel.addEdge("Cyber",n, v_prime);
				    							if(printModelAction) {
				    								System.out.println(edge + " CYBER - ADDED [E]");
				    							}
			    							}
			    							n.setSelected(false);
			    							n.setShiftSelected(false);
			    							v_prime.setSelected(false);
			    							v_prime.setShiftSelected(false);
			    						}
		//	    						else if(!userClicksNode){
		//	    							v = Main.graphModel.getVertexAt((int)v.getCenterX(), (int)v.getCenterY());
		//	    							Main.graphModel.deleteEdges(v);
		//	    							v = Main.graphModel.deleteVertexAt((int)v.getCenterX(), (int)v.getCenterY());
		//	    							if(printModelAction) {
		//	    								System.out.println( v + " DELETED [V]");
		//	    							}
		//	    						}			
			    					}
									n.setSelected(false);
									n.setShiftSelected(false);
									if(printMouseAction) {
										System.out.println(n + " DESELECTED");  
									}
			    				}
			    			}
			            	//--------------------------------------------------------------------------
			            	else if(e.getEventType() == MouseEvent.MOUSE_CLICKED && !Main.interactionModel.mode.equals("Hybrid")) {
			            		if(!thereAreNodesAreCurrentlySelected && !e.isShiftDown()) {
									if(!userClicksNode && Main.interactionModel.getMode() != null) {
										Main.graphModel.addNode(x, y, null);
										n = Main.graphModel.getNodeAt(x, y);
										if(printModelAction) {
											System.out.println( n + " ADDED");
										}
									}
								}
			            	}
			            	//--------------------------------------------------------------------------
			            	else if(e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			            		if(thereAreNodesAreCurrentlySelected) {
			    					n = Main.graphModel.getSelectedNode();
			    					if(n.isShiftSelected()) {
			    						//(IT WORKS SO IM NOT GONNA MESS WITH THIS)
			    					}
			    					else if(e.isPrimaryButtonDown()) {
			    						n.setCenterX(e.getX());
			    						n.setCenterY(e.getY());
			    					}
			    				}
			            	}
						}
						else if(action.equals("delete")) {
							if(Main.graphModel.getNodeAt((int)n.getCenterX(),(int)n.getCenterY()) != null) {
								n = Main.graphModel.getNodeAt((int)n.getCenterX(),(int)n.getCenterY());
							}
							else {
								n = null;
							}
							if(e.getEventType() == MouseEvent.MOUSE_MOVED && n != null) {
								Main.graphView.setCursor(Cursor.CROSSHAIR);
							}
							else if(e.getEventType() == MouseEvent.MOUSE_CLICKED && n != null) {
								Main.graphModel.deleteEdge(n);
								n = Main.graphModel.deleteNodeAt((int)n.getCenterX(), (int)n.getCenterY());
								if(printModelAction) {
									System.out.println( n + " DELETED");
								}
							}
						}
						//MOD001 START
						else if(action.equals("insert")) {
							if(e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			            		if(!thereAreNodesAreCurrentlySelected) {
									if(userClicksNode) {
										n.setSelected(true);
										if(printMouseAction) {
											System.out.println(n + " SELECTED");
										}
									}
								}
			            	}
						}
						//MOD001 END
						
						
		            	Main.graphView.draw();
		            }
	            catch(NullPointerException exception) {
	            	
	            }
            }
        };
		Main.graphView.setOnMouseClicked(mouseHandler);
		Main.graphView.setOnMouseDragged(mouseHandler);
        Main.graphView.setOnMousePressed(mouseHandler);
        Main.graphView.setOnMouseReleased(mouseHandler);
        Main.graphView.setOnMouseMoved(mouseHandler);
	}
	public int getX() {
		return x_MOUSE;
	}
	public int getY() {
		return y_MOUSE;
	}
}