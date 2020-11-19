package ex1.src;

import java.io.Serializable;
import java.util.*;

/**
 * The WGraph_DS class is used to create a weighted unintentional graph and operations on the graph,
 * the class contains:
 * - An internal class of nodeInfo which represents a vertex in the graph.
 * - Collection of all vertices (nodeInfo) using HashMap data structure.
 * - Collection of all vertex neighbors, using a HashMap data structure within HashMap,
 *   by key of nodeInfo has access to its list of neighbors (the internal Hashmap).
 * - Counts the number of actions within the graph.
 * - Number of sides in the graph.
 *
 * @author Lior Atiya
 * @see weighted_graph
 */

public class WGraph_DS implements weighted_graph, Serializable {

    /**
     * Internal class for creating a vertex in a graph
     */
    private static class NodeInfo implements node_info, Serializable {

        /**
         * ID of node
         */
        private int key;
        /**
         * temporal data
         */
        private String info;
        private double tag;

        public NodeInfo(int key) {
            this.key = key;
            this.info = null;
            this.tag = Double.MAX_VALUE;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

    }

    /**
     * Collection of all vertices (node_info).
     */
    private final HashMap<Integer, node_info> nodes;
    /**
     * Collection of neighbors of each vertex and and weight of edges.
     */
    private final HashMap<Integer, HashMap<Integer,Double>> neighbors;

    /**
     * Counts to the number of actions in the graph and the number of edges.
     */
    private int countMC;
    private int edgeSize;


    public WGraph_DS() {
        this.nodes = new HashMap<>();
        this.neighbors = new HashMap<>();
        this.countMC = 0;
        this.edgeSize = 0;
    }

    /**
     * add a new node to the graph with the given key.
     * @param key - new node
     */
    @Override
    public void addNode(int key) {
        //If it already exists or n is null
        if (this.nodes.containsKey(key)) return;

        node_info n = new NodeInfo(key);
        this.nodes.put(key, n);
        this.neighbors.put(key, new HashMap<>());
        this.countMC++;
    }

    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        if (this.nodes.containsKey(key)) return this.nodes.get(key);
        return null;
    }

    /**
     * Connect an edge between node1 and node2.
     * edge - node1 becomes node2's neighbor and node2 becomes node1's neighbor.
     * @param node1 - node_id1
     * @param node2 - node_id2
     * @param w - weight between 2 vertices
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (w >= 0) {
            //Same node.
            if (node1 == node2) return;

            node_info one = getNode(node1);
            node_info two = getNode(node2);
            //Not exists
            if (one == null || two == null) return;
            //Edge exists with same weight.
            if (hasEdge(node1, node2)) {
                if (getEdge(node1, node2) == w) {
                } else { ////Edge exists with different weight.
                    this.neighbors.get(node1).put(node2, w);
                    this.neighbors.get(node2).put(node1, w);
                    this.countMC++;
                }
                return;
            }

            this.neighbors.get(node1).put(node2, w);
            this.neighbors.get(node2).put(node1, w);
            this.countMC++;
            this.edgeSize++;
        }
    }

    /**
     * return true if (if and only if) there is an edge between node1 and node2.
     * @param node1 - key of node1
     * @param node2 - key of node2
     * @return true - There is a edge between 2 vertices.
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(this.neighbors.get(node1) == null) return false;
        return this.neighbors.get(node1).containsKey(node2);
    }

    /**
     * return the weight if the edge (node1, node1).
     * @param node1 key of node1
     * @param node2 key of node2
     * @return If there is a edge = returns the weight of the edge, If no edge exists = returns -1.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2)) return this.neighbors.get(node1).get(node2);
        return -1;
    }

    /**
     * This method return a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * @return Collection<node_info>
     */
    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id.
     * Note: this method run in O(k) time, k - being the degree of node_id.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> getNei = new ArrayList<>();
        for(Integer x : this.neighbors.get(node_id).keySet()) {
            getNei.add(getNode(x));
        }
        return getNei;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
        node_info x;
        //If it does not exist
        if (getNode(key) == null) return null;
        else {
            for(node_info i : getV(key)){
                removeEdge(key, i.getKey());
            }
            this.neighbors.remove(key);
            x = this.nodes.remove(key);
            this.countMC++;
        }
        return x;
    }

    /**
     * Delete the edge from the graph,
     * Removing node2 from node1's neighbors and deleting node1 from node2's neighbors.
     * @param node1 key of node1
     * @param node2 key of node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (node1 == node2 || !hasEdge(node1, node2)) return;

        this.neighbors.get(node1).remove(node2);
        this.neighbors.get(node2).remove(node1);

        this.countMC++;
        this.edgeSize--;
    }

    /** return the number of vertices (nodes) in the graph.
     * @return int variable
     */
    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    /**
     * return the number of edges (unidirectional graph).
     * @return int variable
     */
    @Override
    public int edgeSize() {
        return this.edgeSize;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return int variable
     */
    @Override
    public int getMC() {
        return this.countMC;
    }

    /**
     * Compares the graph values (replaces the default equals function)
     * @param obj another graph
     * @return true if they equals
     */
    public boolean equals(Object obj) {
        boolean ans = true;
        weighted_graph other = (weighted_graph)obj;

        if (this == obj) return true;
        else if (obj == null || this.edgeSize() != other.edgeSize() || this.getMC() != other.getMC()) return false;
        else {
            Iterator<node_info> thisGraph = getV().iterator();
            Iterator<node_info> otherGraph = other.getV().iterator();

            //Checks all nodes in the graph
            while (thisGraph.hasNext() && otherGraph.hasNext()) {
                node_info nodeA = thisGraph.next();
                node_info nodeB = otherGraph.next();

                if (nodeA.getKey() != nodeB.getKey() || nodeA.getInfo() != nodeB.getInfo()
                        || nodeA.getTag() != nodeB.getTag()) {
                    ans = false;
                    break;
                }

                Iterator<node_info> thisNei = getV(nodeA.getKey()).iterator();
                Iterator<node_info> otherNei = ((weighted_graph) obj).getV(nodeB.getKey()).iterator();

                //Checks neighbors of each node
                while (thisNei.hasNext() && otherNei.hasNext()) {
                    node_info nodeA1 = thisNei.next();
                    node_info nodeB1 = otherNei.next();

                    if (nodeA1.getKey() != nodeB1.getKey() || nodeA1.getInfo() != nodeB1.getInfo()
                            || nodeA1.getTag() != nodeB1.getTag()) {
                        ans = false;
                        break;
                    }
                }
            }
        }

        return ans;
    }

    /**
     * Printing the graph values;
     * @return null;
     */
    public String toString() {
        for (node_info x : getV()) {
            System.out.print("Key: " + x.getKey() + " | Tag: " + x.getTag() + " | Info: " + x.getInfo() + " | Neighbor: ");
            System.out.println(this.neighbors.get(x.getKey()).keySet());
        }
        return null;
    }
}

