package application;
public class Edge {
	public static int IDs = 0;
	public int ID;
	private Node v;
	private Node v_prime;
	public Edge() {
		this.ID = IDs;
		this.v = null;
		this.v_prime = null;
		IDs++;
	}
	public boolean contains(Node v) {
		return  v == this.v || v == this.v_prime;
	}
	public Node[] getEdges() {
		Node[] arr = {this.v, this.v_prime};
		return arr;
	}
	public void setEdge(Node v,Node v_prime) {
		this.v = v;
		this.v_prime = v_prime;
	}
	public Node getStartV(){
		return v;
	}
	public Node getEndV(){
		return v_prime;
	}
	boolean areAdj(int v, int v_prime) {
//		System.out.println(v + " " + v_prime);
//		System.out.println(this.v.ID + " " + this.v_prime.ID);
		if((this.v.ID == v && this.v_prime.ID == v_prime) || (this.v.ID == v_prime && this.v_prime.ID == v)) {
			return true;
		}
		return false;
	}
	public String toString(){
		return "Edge(" + this.ID + "):{" +"Vertex(" + v.ID + ") <-> Vertex(" + v_prime.ID + ")}";
	};
}
