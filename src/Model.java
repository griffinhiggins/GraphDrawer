package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
	private SimpleListProperty<Node> nodeListProperty;
	private SimpleListProperty<Edge> edgeListPhysicalProperty;
	private SimpleListProperty<Edge> edgeListCyberProperty;
	private SimpleListProperty<Edge> edgeListHybridProperty;
	
	private int nodeRadius;
	public Model(int vertexRadius) {
		this.nodeRadius = vertexRadius;
		
		ArrayList<Node> vertexList = new ArrayList<Node>();
		ObservableList<Node> observableVertexList = (ObservableList<Node>) FXCollections.observableArrayList(vertexList);
		nodeListProperty = new SimpleListProperty<Node>(observableVertexList);
		
		ArrayList<Edge> edgeListPhysical = new ArrayList<Edge>();
		ObservableList<Edge> observableEdgeListPhysical = (ObservableList<Edge>) FXCollections.observableArrayList(edgeListPhysical);
		edgeListPhysicalProperty = new SimpleListProperty<Edge>(observableEdgeListPhysical);
		
		ArrayList<Edge> edgeListCyber = new ArrayList<Edge>();
		ObservableList<Edge> observableEdgeListCyber = (ObservableList<Edge>) FXCollections.observableArrayList(edgeListCyber);
		edgeListCyberProperty = new SimpleListProperty<Edge>(observableEdgeListCyber);
		
		ArrayList<Edge> edgeListHybrid = new ArrayList<Edge>();
		ObservableList<Edge> observableEdgeListHybrid = (ObservableList<Edge>) FXCollections.observableArrayList(edgeListHybrid);
		edgeListHybridProperty = new SimpleListProperty<Edge>(observableEdgeListHybrid);
	}

	public SimpleListProperty<Node> nodeListProperty(){
		return nodeListProperty;
	}
	public SimpleListProperty<Edge> edgeListPhysicalProperty(){
		return edgeListPhysicalProperty;
	}
	public SimpleListProperty<Edge> edgeListCyberProperty(){
		return edgeListCyberProperty;
	}
	public SimpleListProperty<Edge> edgeListHybridProperty(){
		return edgeListHybridProperty;
	}
	
	public int getNodeRadius() {
		return nodeRadius;
	}
	public Node addNode(int x, int y, String hybridNodeType){
		Node node = new Node(x, y, nodeRadius, hybridNodeType);
		if(node.ID == 0) {
			node.setHybridNodeType("Ego");
		}
		nodeListProperty.add(node);
		return node;
	}
	public Node addNodeFromIO(int ID, int x, int y, String hybridNodeType, int cyberFrq, int physicalFrq, int hybridFrq, String fName, String lName){
		Node node = new Node(x, y, nodeRadius, hybridNodeType);
		node.ID = ID;
		node.cyberFrq = cyberFrq;
		node.physicalFrq = physicalFrq;
		node.hybridFrq = hybridFrq;
		if(fName != null) {
			node.fName = fName;
		}
		if(lName != null) {
			node.lName = lName;
		}
		if(node.ID == 0) {
			node.setHybridNodeType("Ego");
		}
		nodeListProperty.add(node);
		return node;
	}
	public Node deleteNodeAt(int x, int y){
		Node node = getNodeAt(x, y);
		nodeListProperty.remove(node);
		return node;
	}
	public Node getNodeAt(int x, int y){
		Node node = null;
		for (Node n : nodeListProperty){
			if (n.containsPoint(x, y)){
				return n;
			}
		}
		return node;
	}
	public boolean isSelectedNode() {
		for (Node n : nodeListProperty){
			if (n.isSelected()){
				return true;
			}
		}
		return false;
	}
	public Node getSelectedNode() {
		for (Node n : nodeListProperty){
			if (n.isSelected()){
				return n;
			}
		}
		return null;
	}
	public Node getNodeInList(int ID) {
		for(int i = 0; i < nodeListProperty.size(); i++) {
			if (nodeListProperty.get(i).ID == ID){
				return nodeListProperty.get(i);
			}
		}
		return null;
	}
	
	public Edge addEdge(String mode, Node n, Node n_prime) {
		Edge edge = null;
		SimpleListProperty<Edge> list = modeSelector(mode);
		for(int i = 0; i < list.size(); i++) {
			if (list.get(i).contains(n) && list.get(i).contains(n_prime)){
				return edge;
			}
		}
		edge = new Edge();
		edge.setEdge(n,n_prime);
		if(n.getHybridNodeType() == "Ego") {
			n_prime.setHybridNodeType(mode);
		}
		else if(n_prime.getHybridNodeType() == "Ego") {
			n.setHybridNodeType(mode);
		}
		list.add(edge);
		addHybridEdge(n, n_prime);
		return edge;
	}
	public Edge addHybridEdge(Node n, Node n_prime){
		Edge edge = null;
		if(n.getHybridNodeType() == "Ego") {
			String alter = n_prime.getHybridNodeType();
			if(alter.equals("Cyber")) {
				if(scanEdges("Physical",n.ID,n_prime.ID,true) == 1) {
					n_prime.setHybridNodeType("Hybrid");
				}
			}
			else if(alter.equals("Physical")) {
				if(scanEdges("Cyber",n.ID,n_prime.ID,true) == 1) {
					n_prime.setHybridNodeType("Hybrid");
				}
			}
		}
		else if(n_prime.getHybridNodeType() == "Ego") {
			String alter = n.getHybridNodeType();
			if(alter.equals("Cyber")) {
				if(scanEdges("Physical",n.ID,n_prime.ID,true) == 1) {
					n.setHybridNodeType("Hybrid");
				}
			}
			else if(alter.equals("Physical")) {
				if(scanEdges("Cyber",n.ID,n_prime.ID,true) == 1) {
					n.setHybridNodeType("Hybrid");
				}
			}
		}
		for(int i = 0; i < edgeListHybridProperty.size(); i++) {
			if (edgeListHybridProperty.get(i).contains(n) && edgeListHybridProperty.get(i).contains(n_prime)){
				return edge;
			}
		}
		edge = new Edge();
		edge.setEdge(n,n_prime);
		edgeListHybridProperty.add(edge);
		if(Main.graphController.printModelAction) {
			System.out.println(edge + " HYBRID - ADDED [E]");
		}
		return edge;
	}
	public void deleteEdge(Node n) {
		for(int i = 0; i < edgeListPhysicalProperty.size(); i++) {
			if (edgeListPhysicalProperty.get(i).contains(n)){
				if(Main.graphController.printModelAction) {
					System.out.println(edgeListPhysicalProperty.get(i) + "PHYSICAL - DELETED");
				}
				edgeListPhysicalProperty.remove(edgeListPhysicalProperty.get(i));
				i--;
			}
		}
		for(int i = 0; i < edgeListCyberProperty.size(); i++) {
			if (edgeListCyberProperty.get(i).contains(n)){
				if(Main.graphController.printModelAction) {
					System.out.println(edgeListCyberProperty.get(i) + " CYBER - DELETED");
				}
				edgeListCyberProperty.remove(edgeListCyberProperty.get(i));
				i--;
			}
		}
		for(int i = 0; i < edgeListHybridProperty.size(); i++) {
			if (edgeListHybridProperty.get(i).contains(n)){
				if(Main.graphController.printModelAction) {
					System.out.println(edgeListHybridProperty.get(i) + " CYBER - DELETED");
				}
				edgeListHybridProperty.remove(edgeListHybridProperty.get(i));
				i--;
			}
		}
	}
	public int scanEdges(String mode, int i, int j, boolean hybridScan) {
		SimpleListProperty<Edge> list = modeSelector(mode);
		for(Edge e : list) {
			if(e.areAdj(i,j)) {
				if(i > 0 && j > 0 && hybridScan) {
					return 2;
				}
				return 1;
			}
		}
		return 0;
	}
	
	public void writeIO() {
		writeGraph("Physical");
		writeGraph("Cyber");
		writeGraph("Hybrid");
		writeNodeAttr();
		writeHybridStats();
	}
	public void writeGraph(String mode) {
		writeEdgeList(mode);
		writeMatrix(mode);
	}
	public void writeEdgeList(String mode) {
		SimpleListProperty<Edge> list = modeSelector(mode);
		try{
			PrintStream fileOut = new PrintStream("./data/edgeLists/" + mode.toLowerCase() + ".txt");
			System.setOut(fileOut);
			for(Node n : nodeListProperty) {
				System.out.print(n.ID + " ");
			}
			System.out.println();
			for(Edge e : list) {
				System.out.println(e.getStartV().ID + " " + e.getEndV().ID);
			}
			fileOut.close();
		}
		catch(FileNotFoundException ex){
	        ex.printStackTrace();
	    }
	}
	public void writeMatrix(String mode) {
		int len = this.nodeListProperty.getSize();
		int arr[][] = new int[len][len];
		for(int i = 0; i < len; i++) {
			int flag;
			for(int j = 0; j < len; j++) {
				flag = scanEdges(mode,i,j,false);
				arr[i][j] = flag;
				arr[j][i] = flag;
			}
		}
		try{
			PrintStream fileOut = new PrintStream("./data/matricies/" + mode + ".txt");
			System.setOut(fileOut);
			for(int i = 0; i < len; i++) {
				for(int j = 0; j < len; j++) {
					System.out.print(arr[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
			fileOut.close();
		}
		catch(FileNotFoundException ex){
	        ex.printStackTrace();
	    }
	}
	public void writeNodeAttr() {
		try{
			PrintStream fileOut = new PrintStream("./data/meta/nodes.txt");
			System.setOut(fileOut);
			for(Node n : nodeListProperty) {
				System.out.println("NODE(" + n.ID + "){ firstName: " + n.fName + " lastName: " + n.lName + " }");
			}
			fileOut.close();
		}
		catch(FileNotFoundException ex){
	        ex.printStackTrace();
	    }
		try{
			PrintStream fileOut = new PrintStream("./data/meta/db/nodes.txt");
			System.setOut(fileOut);
			for(Node n : nodeListProperty) {
				System.out.println( n.ID +  " " + 
									n.getCenterX() + " " + 
									n.getCenterY() + " " + 
									n.getHybridNodeType() + " " + 
									n.cyberFrq + " " + 
									n.physicalFrq + " " + 
									n.hybridFrq + " " + 
									n.fName + " " + 
									n.lName);
				}
				fileOut.close();
		}
		catch(FileNotFoundException ex){
	        ex.printStackTrace();
	    }
	}
	public void writeHybridStats() {
		double totalNodes = 0;
		double nonNodes = 0;
		double physicalNodes = 0;
		double cyberNodes = 0;
		double hybridNodes = 0;
		for (Node n : nodeListProperty){
			totalNodes++;
			if(n.getHybridNodeType() == "Physical") {
				physicalNodes++;
			}
			else if(n.getHybridNodeType() == "Cyber") {
				cyberNodes++;
			}
			else if(n.getHybridNodeType() == "Hybrid") {
				hybridNodes++;
			}
			else if(n.ID > 0) {
				nonNodes++;
			}
		}
		try{
			PrintStream fileOut = new PrintStream("./data/meta/stats.txt");
			System.setOut(fileOut);
			System.out.print("TOTAL NODES:    { #" + totalNodes + ", %"); System.out.printf("%.2f }\n",(1/totalNodes)*100);
			totalNodes--;
			System.out.print("PHYSICAL NODES: { #" + physicalNodes + ", %" ); System.out.printf("%.2f }\n",(physicalNodes/totalNodes)*100);
			System.out.print("CYBER NODES:    { #" + cyberNodes + ", %"); System.out.printf("%.2f }\n",(cyberNodes/totalNodes)*100);
			System.out.print("HYBRID NODES:   { #" + hybridNodes + ", %"); System.out.printf("%.2f }\n",(hybridNodes/totalNodes)*100);
			System.out.print("NULL NODES:     { #" + nonNodes + ", %"); System.out.printf("%.2f }\n",(nonNodes/totalNodes)*100);
			fileOut.close();
		}
		catch(FileNotFoundException ex){
	        ex.printStackTrace();
	    }
	}
	
	public void readIO() {
		clearCurrentData();
		readNodeAttr();
		readEdges("Physical");
		readEdges("Cyber");
		readEdges("Hybrid");
		
	}
	public void clearCurrentData() {
		edgeListPhysicalProperty.clear();
		edgeListCyberProperty.clear();
		edgeListHybridProperty.clear();
		nodeListProperty.clear();
	}
	public void readNodeAttr() {
		try {
			BufferedReader in = new BufferedReader(new FileReader("./data/meta/db/nodes.txt"));
			String line = in.readLine();
			Scanner sc;
			while(line != null){
				sc = new Scanner(line);
				int ID = (int)sc.nextInt();
				int x = (int)sc.nextDouble();
				int y = (int)sc.nextDouble();
				String hybridNodeType = sc.next();
				int cyberFrq = (int)sc.nextDouble();
				int physicalFrq = (int)sc.nextDouble();
				int hybridFrq = (int)sc.nextDouble();
				String fName = (sc.hasNext()) ? sc.next() : "";
				String lName = (sc.hasNext()) ? sc.next() : "";
				addNodeFromIO(ID, x, y, hybridNodeType, cyberFrq, physicalFrq, hybridFrq, fName, lName);
				line = in.readLine();
			}
			in.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readEdges(String mode) {
		try {
			BufferedReader in = new BufferedReader(new FileReader("./data/edgeLists/" + mode.toLowerCase() + ".txt"));
			String line = in.readLine();
			line = in.readLine();
			Scanner sc;
			while(line != null){
				sc = new Scanner(line);
				int n_ID = sc.nextInt();
				int n_prime_ID = sc.nextInt();
				Node n = getNodeInList(n_ID);
				Node n_prime = getNodeInList(n_prime_ID);
				if("Physical".equals(mode)) {
					addEdge("Physical", n,n_prime);
				}
				else if("Cyber".equals(mode)){
					addEdge("Cyber",n,n_prime);
				}
				else if("Hybrid".equals(mode)){
					addHybridEdge(n,n_prime);
				}
				line = in.readLine();
			}
			in.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SimpleListProperty<Edge> modeSelector(String mode){
		if("Physical".equals(mode)) {
			return edgeListPhysicalProperty;
		}
		else if("Cyber".equals(mode)){
			return edgeListCyberProperty;
		}
		else if("Hybrid".equals(mode)){
			return edgeListHybridProperty;
		}
		return null;
	}
}