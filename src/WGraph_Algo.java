package ex1.src;

import java.io.*;
import java.util.*;

/**
 * The WGraph_Algo class is designed to perform operations using the WGraph_DS class, which contains:
 * - Deep copy of the graph.
 * - Calculation of the shortest path between 2 vertices using the Dijkstra algorithm.
 * - Save and load graph from file.
 *
 *  @author Lior Atiya
 *  @see weighted_graph_algorithms
 */

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {

    /**
     * Graph for the use of methods
     */
    private weighted_graph g;

    /**
     * Constructor - Shallow copy
     * @param g graph
     */
    public WGraph_Algo(weighted_graph g) {
        this.g = g;
    }

    /**
     * Default constructor
     */
    public WGraph_Algo() {
        this.g = new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g graph
     */
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
    @Override
    public weighted_graph copy() {
        if (this.g == null) return null;
        weighted_graph copy = new WGraph_DS();

        //Go over the collection of all nodes and copy the attributes of the node
        for (node_info i : this.g.getV()) {
            copy.addNode(i.getKey());
            copy.getNode(i.getKey()).setTag(i.getTag());
            copy.getNode(i.getKey()).setInfo(i.getInfo());

            //Copy of the neighbors
            for (node_info v : this.g.getV(i.getKey())) {
                copy.connect(i.getKey(), v.getKey(), this.g.getEdge(i.getKey(), v.getKey()));
            }
        }

        return copy;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVERY node to each
     * other node (unidirectional graph).
     * @return true = Is connected ,false = Is not connected
     */
    @Override
    public boolean isConnected() {
        if (this.g.getV().isEmpty()) return true;
        node_info first = this.g.getV().iterator().next();

        Dijkstra(g, g.getNode(first.getKey()));
        for (node_info x : g.getV()) {
            if (x.getTag() == Double.MAX_VALUE) return false;
        }
        return true;
    }

    /**
     * returns the length of the shortest path between src to dest by using the Dijkstra algorithm.
     * @param src - start node
     * @param dest - end (target) node
     * @return Sum of all weights between vertices.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        //If it does not exist src or dest.
        if (g.getNode(dest) == null || g.getNode(src) == null) return -1;
        Dijkstra(this.g, g.getNode(src));
        //If there is no path to the dest.
        if (g.getNode(dest).getTag() == Double.MAX_VALUE) return -1;

        ////*In the attribute of the node - "Tag", save the distance between the src and the dest.
        return g.getNode(dest).getTag();
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest. By using the "shortestPathDist" function.
     * @param src - start node
     * @param dest - end (target) node
     * @return List of vertices.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        //Scan the entire graph and return the number of edges of the shortest path
        double sizePath = shortestPathDist(src, dest);
        if (sizePath == -1) return null;

        //Contain all nodes of the path
        List<node_info> path = new ArrayList<>();

        if (src == dest) path.add(g.getNode(dest));
        else {
            path.add(g.getNode(dest));
            node_info d = this.g.getNode(dest);
            //*In the attribute of the node - "Info", save the key of the parent node in the shortest path
            node_info parent = this.g.getNode(Integer.parseInt(d.getInfo()));

            //Loop from dest to src
            while (parent.getInfo() != null) {
                path.add(0, parent);
                parent = this.g.getNode(Integer.parseInt(parent.getInfo()));
            }
            path.add(0, g.getNode(src));
        }
        return path;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        boolean ans = false;
        ObjectOutputStream oos;
        try {
            FileOutputStream fileOut = new FileOutputStream(file, true);
            oos = new ObjectOutputStream(fileOut);
            oos.writeObject(this.g);
            fileOut.close();
            oos.close();
            ans = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ans;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream streamIn = new FileInputStream(file);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            weighted_graph readCase = (weighted_graph) objectinputstream.readObject();
            this.g = readCase;
            streamIn.close();
            objectinputstream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Dijkstra algorithms - Given a graph and a source vertex in the graph,
     * find the shortest paths from source to all vertices in the given graph by weight of each side.
     * This algorithm uses Priority Queue to check if there is a edge
     * with a lower weight than the top of the Queue to get to the shortest path
     * And uses HashMap for checking for each vertex whether we scanned all of its neighbors
     * by a Boolean variable "visited".
     * @param g graph on which the scan is performed
     * @param src Source vertex from which to start the scan
     */
    private static void Dijkstra(weighted_graph g, node_info src) {
        HashMap<Integer, Boolean> visited = new HashMap<>();

        Comparator<node_info> nameSorter = Comparator.comparing(node_info::getTag);
        PriorityQueue<node_info> pQueue = new PriorityQueue<>(nameSorter);
        for (node_info x : g.getV()) {
            if (x != src) {
                x.setTag(Double.MAX_VALUE);
            } else {
                x.setTag(0);
            }
            x.setInfo(null);
            pQueue.add(x);
            visited.put(x.getKey(), false);
        }

        while (!pQueue.isEmpty()) {
            node_info curr = pQueue.remove();

            for (node_info v : g.getV(curr.getKey())) {
                if (!visited.get(curr.getKey())) {
                    double t = curr.getTag() + g.getEdge(curr.getKey(), v.getKey());
                    if (v.getTag() > t) {
                        v.setTag(t);
                        v.setInfo("" + curr.getKey());
                        pQueue.add(v);
                    }
                }
            }
            visited.put(curr.getKey(), true);
        }

    }
}
