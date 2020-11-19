package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    /**
     * Creates vertices and connects edges in order
     * @param v_size number of vertices
     * @param e_size number of edges
     * @return new graph by input values
     */
    public static weighted_graph graph_creator(int v_size, int e_size) {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < v_size; i++) {
            g.addNode(i);
        }

        for (int i = 0; i < v_size; i++) {
            for (int j = 1; j < v_size; j++) {
                if (g.edgeSize() < e_size) {
                    g.connect(i, j, 4);
                } else break;
            }
        }
        return g;
    }

    @Test
    void init_getGraph_Test() {
        weighted_graph wg = graph_creator(10, 20);
        weighted_graph_algorithms wga = new WGraph_Algo();
        //Init test
        wga.init(wg);
        assertEquals(wg, wga.getGraph());

        //Constructor test
        wga = new WGraph_Algo(wg);
        assertEquals(wg, wga.getGraph());

        //Null test
        wga.init(null);
        assertEquals(wga.getGraph(), null);
    }

    @Test
    void copy() {
        weighted_graph wg = graph_creator(10, 30);
        weighted_graph_algorithms wga = new WGraph_Algo(wg);
        weighted_graph copy = wga.copy();

        assertEquals(copy, wg);

        //Remove 4 edges
        copy.removeEdge(0, 1);
        copy.removeEdge(0, 2);
        copy.removeEdge(0, 3);
        copy.removeEdge(0, 4);
        copy.removeEdge(0, 1);
        copy.removeEdge(0, 31);

        assertEquals(copy.edgeSize(), 26);
        assertEquals(wg.edgeSize(), 30);
        assertEquals(copy.getMC(), wg.getMC() + 4);
        assertNotEquals(copy, wg);

        wga = new WGraph_Algo(null);
        copy = wga.copy();

        assertEquals(copy, null);
    }

    @Test
    void isConnectedTest() {
        //Empty graph
        weighted_graph_algorithms wga = new WGraph_Algo();
        weighted_graph ga = graph_creator(0, 0);
        wga.init(ga);
        assertTrue(wga.isConnected());

        //Single node in the graph
        ga = graph_creator(1, 0);
        wga.init(ga);
        assertTrue(wga.isConnected());

        //Two nodes without edges
        ga = graph_creator(2, 0);
        wga.init(ga);
        assertFalse(wga.isConnected());

        //Graph with 5 nodes and 4 edges before and after remove edge
        ga = graph_creator(5, 4);
        wga.init(ga);
        assertTrue(wga.isConnected());
        ga.removeEdge(0, 3);
        wga.init(ga);
        assertFalse(wga.isConnected());

    }

    @Test
    void shortestPathDist() {
        weighted_graph wg = graph_creator(6, 0);
        wg.connect(0, 1, 1);
        wg.connect(1, 2, 1);
        wg.connect(3, 2, 1);
        wg.connect(3, 5, 1);
        wg.connect(5, 0, 5);

        weighted_graph_algorithms wga = new WGraph_Algo(wg);
        double actual = wga.shortestPathDist(0, 5);
        assertEquals(4, actual);
        actual = wga.shortestPathDist(5, 0);
        assertEquals(4, actual);

        //There is no such path
        actual = wga.shortestPathDist(4, 6);
        assertEquals(-1, actual);

        //The source and destination are equal
        actual = wga.shortestPathDist(4, 4);
        assertEquals(0, actual);

        actual = wga.shortestPathDist(1, 2);
        assertEquals(1, actual);

    }

    @Test
    void shortestPath() {
        weighted_graph wg = graph_creator(6, 0);
        wg.connect(0, 1, 1);
        wg.connect(1, 2, 1);
        wg.connect(2, 3, 1);
        wg.connect(3, 4, 1);
        wg.connect(4, 5, 1);
        wg.connect(0, 3, 1);
        wg.connect(0, 5, 5);
        wg.connect(0, 2, 3);

        weighted_graph_algorithms wga = new WGraph_Algo(wg);

        List actual = wga.shortestPath(0, 5);
        List expected = new ArrayList();
        expected.add(wga.getGraph().getNode((0)));
        expected.add(wga.getGraph().getNode((3)));
        expected.add(wga.getGraph().getNode((4)));
        expected.add(wga.getGraph().getNode((5)));
        assertEquals(expected, actual);

        actual = wga.shortestPath(5, 0);
        expected = new ArrayList();
        expected.add(wga.getGraph().getNode((5)));
        expected.add(wga.getGraph().getNode((4)));
        expected.add(wga.getGraph().getNode((3)));
        expected.add(wga.getGraph().getNode((0)));
        assertEquals(expected, actual);

        actual = wga.shortestPath(2, 0);
        expected = new ArrayList();
        expected.add(wga.getGraph().getNode((2)));
        expected.add(wga.getGraph().getNode((1)));
        expected.add(wga.getGraph().getNode((0)));
        assertEquals(expected, actual);

        ////The source and destination are equal
        actual = wga.shortestPath(5, 5);
        expected = new ArrayList();
        expected.add(wga.getGraph().getNode((5)));
        assertEquals(expected, actual);

        actual = wga.shortestPath(5, 4);
        expected = new ArrayList();
        expected.add(wga.getGraph().getNode((5)));
        expected.add(wga.getGraph().getNode((4)));
        assertEquals(expected, actual);

        //There is no such path
        actual = wga.shortestPath(3, 9);
        assertEquals(null, actual);

        //Empty graph
        wga = new WGraph_Algo();
        actual = wga.shortestPath(3, 6);
        assertEquals(null, actual);

        //Single node
        wg = graph_creator(1, 0);
        wga.init(wg);
        actual = wga.shortestPath(0, 0);
        expected = new ArrayList();
        expected.add(wg.getNode(0));
        assertEquals(expected, actual);
    }

    @Test
    void save_load_Test() {
        weighted_graph wg = graph_creator(6, 12);
        weighted_graph_algorithms wga1 = new WGraph_Algo(wg);
        assertTrue(wga1.save("newGraph"));

        weighted_graph_algorithms wga2 = new WGraph_Algo();

        assertTrue(wga2.load("newGraph"));
        assertEquals(wga1.getGraph(), wga2.getGraph());
    }
}