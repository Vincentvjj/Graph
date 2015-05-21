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
	private Collection<Edge> edges;
	private Collection<Vertex> vertexes;

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
		
		edges = new HashSet<Edge>();
		vertexes = new HashSet<Vertex>();
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
		System.out.println(eMap);
		System.out.println(vMap);
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
//	public Path shortestPath(Vertex a, Vertex b) {
//
//		// YOUR CODE HERE (you might comment this out this method while doing
//		// Part 1)
//
//	}

}