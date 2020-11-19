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


<h3>How to Use</h3>
  <ol>
    <li>Open a new folder for a project on your computer</li>
    <li>Go to cmd and navigate to the new folder you opened</li>
    <li>Copy and paste the following address in cmd:</br> git clone https://github.com/LiorAtiya/weighted_graph_algorithms.git</li>
  </ol>
