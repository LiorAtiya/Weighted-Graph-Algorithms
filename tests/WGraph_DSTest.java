package ex1.tests;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void addNodeTest() {
        weighted_graph wg = graph_creator(4,0);

        wg.addNode(0);
        wg.addNode(3);
        wg.addNode(3);

        int expected = 4;
        assertEquals(wg.nodeSize(), expected);
    }

    @Test
    void getNode_Info_Tag_Test() {
        weighted_graph wg = new WGraph_DS();
        wg.addNode(5);
        node_info n1 = wg.getNode(5);

        n1.setInfo("Lior");
        assertEquals(n1.getInfo(), "Lior");
        n1.setTag(15);
        assertEquals(n1.getTag(), 15);

        node_info n2 = wg.getNode(20);
        assertNull(n2);
    }

    @Test
    void hasEdge_getEdge_Connect_Test() {
        weighted_graph wg = graph_creator(3, 0);

        wg.connect(0, 1, 4);
        wg.connect(0, 2, 0);
        wg.connect(2, 3, -5);

        assertTrue(wg.hasEdge(0, 2));
        assertFalse(wg.hasEdge(2, 3));

        //Already exist
        assertEquals(wg.getEdge(0, 1), 4);
        //Not valid
        assertEquals(wg.getEdge(2, 3), -1);

        //Change weight of edge
        wg.connect(1, 0, 20);
        assertEquals(wg.getEdge(0,1), 20);
    }


    @Test
    void getVTest() {
        weighted_graph wg = graph_creator(5, 0);
        int expected = wg.getV().size();

        assertEquals(expected, 5);

        wg = new WGraph_DS();
        assertEquals(0, wg.getV().size());
    }

    @Test
    void removeNode_getMC_nodeSize_Test() {
        weighted_graph wg = graph_creator(5, 0);
        wg.connect(1, 2, 5);
        wg.connect(1, 3, 5);
        wg.connect(1, 4, 5);
        wg.connect(1, 0, 5);

        wg.removeNode(1);
        wg.removeNode(1);
        wg.removeNode(7);
        assertEquals(wg.nodeSize(), 4);

        assertEquals(wg.getMC(), 14);
    }

    @Test
    void removeEdgeTest() {
        weighted_graph wg = graph_creator(5, 4);
        wg.removeEdge(0, 1);
        wg.removeEdge(0, 2);
        wg.removeEdge(0, 4);
        wg.removeEdge(0, 4);
        wg.removeEdge(0, 5);
        int actual = wg.edgeSize();
        assertEquals(1, actual);
    }

    public static weighted_graph graph_creator(int v_size, int e_size) {
        weighted_graph g = new WGraph_DS();
        for (int i = 0; i < v_size; i++) {
            g.addNode(i);
        }

        for (int i = 0; i < v_size; i++) {
            for (int j = 1; j < v_size; j++) {
                if (g.edgeSize() < e_size) {
                    g.connect(i, j, 4);
                }
            }
            if (g.edgeSize() >= e_size) {
                break;
            }
        }

        return g;
    }

    //General tests

    /**
     Create a graph of a million vertices and 10 edges.
     */
    @Test
    void generalTest1() {
        weighted_graph wg = graph_creator(1000000,10000000);
//        weighted_graph wg = graph_creator(1000000,10000000);
        assertEquals(wg.nodeSize(), 1000000);
        assertEquals(wg.edgeSize(), 10000000);
    }

    @Test
    void generalTest2(){
        weighted_graph wg = new WGraph_DS();
        wg.addNode(1);
        wg.addNode(3);
        wg.addNode(4);
        wg.addNode(10);
        wg.addNode(20);
        wg.addNode(30);
        wg.addNode(31);

        node_info n1 = wg.getNode(1);
        assertEquals(1,n1.getKey());
        n1 = wg.getNode(5);
        assertEquals(null,n1);

        wg.connect(3,4, 7);
        wg.connect(4,3, 7);
        wg.connect(3,5, 1.5);
        wg.connect(1,3, 1.5);
        wg.connect(10,3, 11);
        wg.connect(20,3, 22);
        wg.connect(30,3, 33);
        wg.connect(30,31, 100);

        assertTrue(wg.hasEdge(3,4));
        assertFalse(wg.hasEdge(1,5));

        assertEquals(7, wg.getEdge(3,4));
        assertEquals(-1, wg.getEdge(1,5));

        assertEquals(7, wg.getV().size());
        assertEquals(5, wg.getV(3).size());

        assertEquals(6,wg.edgeSize());
        wg.removeEdge(3,1);
        wg.removeEdge(3,1);
        wg.removeEdge(3,1);
        wg.removeEdge(2,7);
        wg.removeEdge(7,2);
        assertEquals(5,wg.edgeSize());

        assertEquals(7, wg.nodeSize());
        wg.removeNode(3);
        wg.removeNode(3);
        wg.removeNode(15);
        assertEquals(6, wg.nodeSize());

        assertEquals(19,wg.getMC());
    }

}