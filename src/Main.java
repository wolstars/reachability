import java.util.*;
public class Main {
	ArrayList<ArrayList<Node>> components = new ArrayList<>();
	Integer[][] componentEdges;
	ArrayList<Node> nodes = new ArrayList<>();
	ArrayList<Node> originalNodes;
	Integer time;
	Integer[][] edges;
	Integer componentIndex;
	
	public static void main(String[] args) {
		Main m = new Main();
		m.graph();
	}
	
	public void graph() {
		for (int i = 0; i < 6; i++) {
			nodes.add(new Node(i));
		}
		
		originalNodes =  new ArrayList<Node>(nodes);
		makeEdges();
		printEdge();
		DFS();
		printGraph();
	}
	
	public void printComponent() {
		componentIndex = 0;
		for (ArrayList<Node> arr : components) {
			System.out.println("component " + componentIndex + " is :");
			componentIndex++;
			for (Node n : arr) {
				System.out.println("Node " + n.getValue());
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	public void printEdge() {
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (edges[j][i] != null) {
					System.out.print(edges[j][i] + " ");
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	public void makeEdges() {
		edges = new Integer[nodes.size()][nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			edges[i][i] = 0;
		}
		edges[0][1] = 3;
		edges[1][2] = 3;
		edges[2][3] = 3;
		edges[2][1] = 3;
		edges[3][1] = 3;
		edges[4][5] = 3;
		edges[5][4] = 3;
	}
	
	
	public void transpose() {
		Integer temp;
		Integer size = nodes.size() - 1;
		for (int i = size; i >= 0; i--) {
			for (int j = 0; j <= size - i; j++) {
				if (edges[j][size - i] != null || edges[size - i][j] != null) {
					temp = edges[j][size - i];
					edges[j][size - i] = edges[size - i][j];
					edges[size - i][j] = temp;
				}
			}
		}
	}
	
	public void printGraph() {
		for (Node n : nodes) {
			System.out.println(n);
		}
	}
	
	
	
	public void printPath(Node n) {
		if (n != null) {
			printPath(n.getPath());
			System.out.println(n.getValue());
		}
		
	}
	
	public void DFS() {
		componentIndex = 0;
		time = 0;
		for (Node n : nodes) {
			n.setDone(false);
			n.setPath(null);
		}
		components = new ArrayList<>();
		for (Node n : nodes) {
			if (!n.isDone()) {
				components.add(new ArrayList<>());
				DFS_VISIT(n);
				componentIndex++;
			}
		}
	}
	
	public void DFS_VISIT(Node n) {
		Node temp;
		time++;
		n.discover(time);
		n.done();
		components.get(componentIndex).add(n);
		for (int i = 0; i < nodes.size(); i++) {
			if (i == n.getValue()) continue;
			if (edges[n.getValue()][i] != null) {
				temp = originalNodes.get(edges[n.getValue()][i]);
				assert n != temp : "temp is the same as n";
				if (temp.getMinValue() < n.getMinValue()) {
					n.setMinValue(temp.getMinValue());
				}
				if (!temp.isDone()) {
					temp.setPath(n);
					DFS_VISIT(temp);
				}
			}
		}
		time++;
		n.finish(time);
	}
}

class Node implements Comparable<Node>{
	private Integer value;
	private Integer minValue;
	private Integer discover;
	private Integer finish;
	private boolean done = false;
	private Node path = null;
	
	public Node(Integer value) {
		this.minValue = this.value = value;
	}
	
	public void finish(Integer finish) {
		this.finish = finish;
	}
	
	public void discover(Integer discover) {
		this.discover = discover;
	}
	
	public Node getPath() {
		return path;
	}
	
	public boolean isDone(){
		return done;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public void setPath(Node n) {
		this.path = n;
	}
	
	public void done() {
		done = true;
	}
	
	public String toString() {
		return "Node " + value + " is: " + finish + " minValue is: " + minValue;
	}
	
	public int compareTo(Node n) {
		return n.finish - finish;
	}
	
	public Integer getValue() {
		return value;
	}
	public Integer getMinValue() {
		return minValue;
	}
	public void setMinValue(Integer value) {
		this.minValue = value;
	}

}
