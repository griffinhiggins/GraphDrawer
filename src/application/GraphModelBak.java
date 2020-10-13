package application;

import java.util.ArrayList;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GraphModelBak {
	private SimpleListProperty<Node> vertexListProperty;
	private SimpleListProperty<Edge> edgeListProperty;
	private int vertexRadius;
	public GraphModelBak(int vertexRadius) {
		this.vertexRadius = vertexRadius;
		
		ArrayList<Node> vertexList = new ArrayList<Node>();
		ObservableList<Node> observableVertexList = (ObservableList<Node>) FXCollections.observableArrayList(vertexList);
		vertexListProperty = new SimpleListProperty<Node>(observableVertexList);
		
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		ObservableList<Edge> observableEdgeList = (ObservableList<Edge>) FXCollections.observableArrayList(edgeList);
		edgeListProperty = new SimpleListProperty<Edge>(observableEdgeList);
	}

	public SimpleListProperty<Node> vertexListProperty(){
		return vertexListProperty;
	}

	public SimpleListProperty<Edge> edgeListProperty(){
		return edgeListProperty;
	}
	
	public int getVertexRadius() {
		return vertexRadius;
	}

	public void addVertex(int x, int y, String color){
		Node vertex = new Node(x, y, vertexRadius, color);
		vertexListProperty.add(vertex);
	}
	
	public Edge addEdge(Node v, Node v_prime){
		Edge edge = null;
		for(int i = 0; i < edgeListProperty.size(); i++) {
			if (edgeListProperty.get(i).contains(v) && edgeListProperty.get(i).contains(v_prime)){
				return edge;
			}
		}
		edge = new Edge();
		edge.setEdge(v,v_prime);
		edgeListProperty.add(edge);
		return edge;
	}
	
	public void deleteEdges(Node v){
		for(int i = 0; i < edgeListProperty.size(); i++) {
			if (edgeListProperty.get(i).contains(v)){
				if(Main.graphController.printModelAction) {
					System.out.println(edgeListProperty.get(i) + " DELETED");
				}
				edgeListProperty.remove(edgeListProperty.get(i));
				i--;
			}
		}
	}

	public Node deleteVertexAt(int x, int y){
		Node vertex = getVertexAt(x, y);
		vertexListProperty.remove(vertex);
		return vertex;
	}

	public Node getVertexAt(int x, int y){
		Node vertex = null;
		for (Node v : vertexListProperty){
			if (v.containsPoint(x, y)){
				return v;
			}
		}
		return vertex;
	}
	
	public boolean isSelected() {
		for (Node v : vertexListProperty){
			if (v.isSelected()){
				return true;
			}
		}
		return false;
	}
	
	public Node getSelected() {
		for (Node v : vertexListProperty){
			if (v.isSelected()){
				return v;
			}
		}
		return null;
	}
	
	public void printGraph() {
		System.out.println("EDGE LIST: {\n");
		edgeList();
		System.out.println("}\n\nADJACENCY MATRIX: {\n");
		adjMatrix();
		System.out.println("}");
	}
	
	public int scanEdges(int i, int j) {
		for(Edge e : edgeListProperty) {
			if(e.areAdj(i,j)) {
				return 1;
			}
		}
		return 0;
	}
	
	public void adjMatrix() {
		int len = this.vertexListProperty.getSize();
		int arr[][] = new int[len][len];
		for(int i = 0; i < len; i++) {
			int flag;
			for(int j = 0; j < len; j++) {
				flag = scanEdges(i,j);
				arr[i][j] = flag;
				arr[j][i] = flag;
			}
		}
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void edgeList() {
		System.out.print("NODES: {");
		for(Node v : vertexListProperty) {
			System.out.print(" " + v.ID);
		}
		System.out.print(" }\n\n");
		System.out.print("EDGES: {");
		for(Edge e : edgeListProperty) {
			System.out.print(" [(" + e.getStartV().ID + ")-(" + e.getEndV().ID + ")] ");
		}
		System.out.print(" }\n\n");
	}
}
