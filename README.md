<center> <h1>Weighted Graph Algorithms</h1> </center>
<p align="center">
  <img width="460" height="300" src="http://up419.siz.co.il/up3/kdm2mxdmgcmz.png">
</p>

> ## Description:
>
>> This library is designed to implement a data structure of an weighted and unintentional graph, and additional calculations in the graph.

> ### Classes:
>
>> #### WGraph_DS (implements weighted_graph) - 
>> Uses the NodeInfo (internal class) to create a graph with a collection of vertices (NodeInfo) stored in a HashMap data structure, collection of neighbors of each vertex >> >> (NodeInfo) using HashMap within HashMap, in addition to variables for counting the number of actions performed on the graph, and the number of edges in the graph.

>> #### WGraph_Algo (implements weighted_graph_algorithms) - 
>> Uses the Graph_DS class for calculations on the graph, check if they are connected, find the shortest path in the graph between 2 vertices by weight of each edge using the  >> Dijkstra algorithm, and in addition initialize the graph and make a deep copy.<br>

>> *Method in addition to the interface - Dijkstra algorithm*<br>
Designed to scan the entire graph and enter values in the "Tag" & "Info" variables of the vertex (NodeInfo) according to a particular vertex in the graph, helps to find information about the paths in the graph and the connection of the graph.
<br> Read more: <https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm>


<h2>How to download the project</h2>
   First if you do not have git installed you will need to download from here: https://git-scm.com/
  <ol>
    <li>Select a destination to which you want to download the project</li>
    <li>Enter cmd Navigate to the destination to which you want to download the project</li>
    <li>Copy and paste the following address in cmd:</br></li>
  </ol>
  
  ```bash
git clone https://github.com/LiorAtiya/weighted_graph_algorithms.git
```

<h2>How to use the project</h2>
  <ul>
    <li>Create a graph</li>
  </ul>
  
      weighted_graph g = new WGraph_DS();
      Methods: getNode, hasEdge, getEdge, addNode, connect, getV, removeNode, nodeSize, edgeSize, getMC
      
  <ul>
    <li>Create a Graph Algorithm</li>
  </ul>
  
      weighted_graph_algorithms wga = new WGraph_Algo();
      Methods: init, getGraph, copy, isConnected, shortestPathDist, shortestPath, save, load
      
      
    
