import java.util.*;

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {
	// you will need some private fields to represent the graph
	// you are also likely to want some private helper methods
	
	private Map<Vertex, List<Vertex>> vMap;
	private Map<Vertex, List<Edge>> eMap;
	private List<Edge> edges;
	private List<Vertex> vertexes;

	// YOUR CODE HERE

	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v
	 *            a collection of the vertices in this graph
	 * @param e
	 *            a collection of the edges in this graph
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
		
		edges = new ArrayList<Edge>();
		vertexes = new ArrayList<Vertex>();
		vMap = new HashMap<Vertex, List<Vertex>>();
		eMap = new HashMap<Vertex, List<Edge>>();
		
		for(Vertex vs : v) {
			List<Vertex> l = new LinkedList<Vertex>();
			List<Edge> ll = new LinkedList<Edge>();
			
			if(!vMap.containsKey(vs)) {
				vMap.put(vs, l);
				eMap.put(vs, ll);
				vertexes.add(vs);
			}
		}
		
		for(Edge edges : e) {
			Vertex from = edges.getSource();
			Vertex to = edges.getDestination();
			
			if(!vMap.containsKey(from) || !vMap.containsKey(to)) {
				throw new IllegalArgumentException("No such source");
			}
			if(edges.getWeight() < 0) {
				throw new IllegalArgumentException("Negative weight");

			}
			
			if(!vMap.get(from).contains(to)) {
				vMap.get(from).add(to);
				eMap.get(from).add(edges);
				this.edges.add(edges);
			}
		}
	}

	/**
	 * Return the collection of vertices of this graph
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Vertex> vertices() {		
		return vertexes; 
	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {
		return edges; 
	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	@Override
	public Collection<Vertex> adjacentVertices(Vertex v) {

		return vMap.get(v);

	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	@Override
	public int edgeCost(Vertex a, Vertex b) {
		if(vMap.get(a).contains(b)) {	
			int weight = -1;
			for(Edge e : eMap.get(a)) {
				
				if(e.getDestination().equals(b)) {
					weight = e.getWeight();
					break;
				} 
			}
			return weight;
		} else {
			return -1;
		}
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	
	public Path shortestPath(Vertex a, Vertex b) {
		if(a.equals(b)) {
			Path temp = new Path(new LinkedList<Vertex>(), 0);
			temp.vertices.add(a);
			return temp;
		}
		List<Vertex> unknown = new LinkedList<Vertex>();
		Map<Vertex, Integer> costMap = new HashMap<Vertex, Integer>();
		Map<Vertex, Path> pathMap = new HashMap<Vertex, Path>();

		for(Vertex v : vertexes) {
			unknown.add(v);
			costMap.put(v, Integer.MAX_VALUE);
			pathMap.put(v, new Path(new ArrayList<Vertex>(), -1));
			
		}
		
		Vertex source = unknown.get(vertexes.indexOf(a));
		Vertex chosenVertex = source;
		costMap.put(source, 0);
		int min;
		boolean noPath = true; 
		
		while(!unknown.isEmpty()) {
			min = Integer.MAX_VALUE;
			for(Vertex vs : unknown) {
				if(costMap.get(vs) <= min) {
					min = costMap.get(vs);
					chosenVertex = vs;
				}
			}
	
			for(Edge e : eMap.get(chosenVertex)) {
				if(unknown.contains(e.getDestination())) {
					noPath = false;
					Vertex curr = unknown.get(unknown.indexOf(e.getDestination()));
					int prev = costMap.get(curr);
					prev = Math.abs(prev + 1) - 1;
					int newWeight = costMap.get(chosenVertex) + (e.getWeight());
					newWeight = Math.abs(newWeight + 1) - 1;
					if(newWeight < prev) {
						costMap.put(curr, newWeight);
						pathMap.get(curr).vertices.clear();
						pathMap.get(curr).vertices.add(chosenVertex);
					}
				}
				
			}
			if(chosenVertex.equals(source) && noPath) {
				return null;
			}
			unknown.remove(chosenVertex);
		}		
		
		Path shortPath = new Path(new LinkedList<Vertex>(), costMap.get(b));
		boolean done = false; 
		
		if(pathMap.get(b).vertices.isEmpty()) {
			return null; 
		}
		Vertex temp = pathMap.get(b).vertices.get(0);
		shortPath.vertices.add(b);
		
		while(!done) {
			shortPath.vertices.add(temp);
			if(temp.equals(a)) {
				done = true;
			} else {	
				temp = pathMap.get(temp).vertices.get(0);
			}
		}
		return shortPath;
	}
}