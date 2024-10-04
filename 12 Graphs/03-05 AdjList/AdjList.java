// Name: Cherish Seelam
// Date: 5/17/24
 
import java.util.*;
import java.io.*;

/* Resource classes and interfaces
 * for use with Graphs3: EdgeList,
 *              Graphs4: DFS-BFS
 *          and Graphs5: EdgeListCities
 */

/**************** Graphs 3: EdgeList *****/
interface VertexInterface
{
   public String getName();
   public HashSet<Vertex> getAdjacencies();
   
   /*
     postcondition: if the set already contains a vertex with the same name, the vertex v is not added
                    because adjacencies is a HashSet, this method should operate in O(1)
   */
   public void addAdjacent(Vertex v);
   /*
     postcondition:  returns as a string one vertex with its adjacencies, without commas.
                     for example, D [C A]
     */
   public String toString(); 
 
} 
 
/*************************************************************/
class Vertex implements VertexInterface, Comparable<Vertex> //2 vertexes are equal if and only if they have the same name
{
   private final String name;
   private HashSet<Vertex> adjacencies;
   /* enter your code here  */
   public Vertex(String s)
   {
      name = s;
      adjacencies = new LinkedHashSet<Vertex>();
   }
     
   public String getName()
   {
      return name;  
   }
     
   public HashSet<Vertex> getAdjacencies()
   {
      return adjacencies;
   }
     
   /*
     postcondition: if the set already contains a vertex with the same name, the vertex v is not added
                       because adjacencies is a HashSet, this method should operate in O(1)
   */
   public void addAdjacent(Vertex v)
   {
      adjacencies.add(v);  
   }
   /*
     postcondition:  returns as a string one vertex with its adjacencies, without commas.
                     for example, D [C A]
   */
   public String toString()
   {
      List<String> adjList = new ArrayList<>();
      for (Vertex v : adjacencies) 
      {
         adjList.add(v.getName());
      }
      Collections.sort(adjList);
      String s = name + " [";
      for (String adj : adjList)
      {
         s = s + adj + " ";
      }
      return s.trim() + "]";
   }
      
   public int compareTo(Vertex other) 
   {
      return name.compareTo(other.getName());
   }
}  
 

/*************************************************************/
interface AdjListInterface
{
   public Set<Vertex> getVertices();
   public Vertex getVertex(String vName);
   public Map<String, Vertex> getVertexMap();  //this is just for codepost testing
   
   /*      
      postcondition: if a Vertex with the name v exists, then the map is unchanged.
                     addVertex should work in O(log n)
   */
   public void addVertex(String vName);
   
   /*
      precondition:  both Vertexes, source and target, are already stored in the graph.
      postcondition:  addEdge should work in O(log n)
   */
   public void addEdge(String source, String target); 
   
   /*
       returns the whole graph as one string, e.g.:
       A [C]
       B [A]
       C [C D]
       D [C A]
     */
   public String toString(); 
}

  
/********************** Graphs 4: DFS and BFS *****/
interface DFS_BFS
{
   public String depthFirstSearch(String name);
   public String breadthFirstSearch(String name);
   /*   extra credit  */
   public String depthFirstRecur(String name);
   public List<Vertex> depthFirstRecurHelper(Vertex v, List<Vertex> reachable);
}

/****************** Graphs 5: Edgelist with Cities *****/
interface EdgeListWithCities
{
   public void readData(String cities, String edges) throws FileNotFoundException;
   public int edgeCount();
   public int vertexCount();
   public boolean isReachable(String source, String target);
   public boolean isStronglyConnected(); //return true if every vertex is reachable from every 
                                          //other vertex, otherwise false 
}


/*************  start the Adjacency-List graph  *********/
public class AdjList implements AdjListInterface, DFS_BFS, EdgeListWithCities
{
   // we want our map to be ordered alphabetically by vertex name
   private Map<String, Vertex> vertexMap = new TreeMap<String, Vertex>();
      
   /* constructor is not needed because of the instantiation above */
  
   /* enter your code here  */
   public Set<Vertex> getVertices()
   {
      return new HashSet<Vertex>(vertexMap.values());
   }
   
   public Vertex getVertex(String vName)
   {
      return vertexMap.get(vName);
   }
   
   public Map<String, Vertex> getVertexMap()  // this is just for codepost testing
   {
      return vertexMap;
   }
   
   /*      
      postcondition: if a Vertex with the name v exists, then the map is unchanged.
                     addVertex should work in O(log n)
   */
   public void addVertex(String vName)
   {
      if (!vertexMap.keySet().contains(vName))
      {
         vertexMap.put(vName, new Vertex(vName));
      }
   }
   
   /*
      precondition:  both Vertexes, source and target, are already stored in the graph.
      postcondition:  addEdge should work in O(log n)
   */
   public void addEdge(String source, String target)
   {
       Vertex sourceVertex = vertexMap.get(source);
       Vertex targetVertex = vertexMap.get(target);
       if (sourceVertex != null && targetVertex != null) 
       {
        sourceVertex.addAdjacent(targetVertex);
       }   
   }
   
   /*
       returns the whole graph as one string, e.g.:
       A [C]
       B [A]
       C [C D]
       D [C A]
     */
   public String toString()
   {
      String str = "";
      for (String s : vertexMap.keySet()) 
      {
         str = str + vertexMap.get(s).toString() + "\n";
      }
      return str;
   }
   
   public String depthFirstSearch(String name)
   {
      String result = "";
      Set<Vertex> visited = new HashSet<>();
      Stack<Vertex> stack = new Stack<>();
      stack.push(vertexMap.get(name));
      while (!stack.isEmpty())
      {
         Vertex current = stack.pop();
         if (!visited.contains(current))
         {
            visited.add(current);
            result += current.getName() + " ";
            List<Vertex> adjList = new ArrayList<>(current.getAdjacencies());
            Collections.sort(adjList, Collections.reverseOrder());
            for (Vertex v : adjList)
            {
               if (!visited.contains(v))
               {
                  stack.push(v);
               }
            }
         }
      }
      return result.trim();     
   }
   
   public String breadthFirstSearch(String name) 
   {
       String result = "";
       Queue<Vertex> queue = new LinkedList<>();
       Set<Vertex> visited = new HashSet<>();
       queue.add(vertexMap.get(name));
       visited.add(vertexMap.get(name)); // Mark the starting vertex as visited
       while (!queue.isEmpty()) {
           Vertex current = queue.poll();
           result += current.getName() + " ";
           for (Vertex v : current.getAdjacencies()) {
               if (!visited.contains(v)) {
                   visited.add(v);
                   queue.add(v); // Add unvisited adjacent vertices to the queue
               }
           }
       }
       return result.trim();
   }
   
   public String depthFirstRecur(String name)
   {
      List<Vertex> reachable = new ArrayList<>();
      depthFirstRecurHelper(vertexMap.get(name), reachable);
      String result = "";
      for (Vertex v : reachable) 
      {
          result = result + v.getName() + " ";
      }
      return result.trim();   
   }
   
   public List<Vertex> depthFirstRecurHelper(Vertex v, List<Vertex> reachable)
   {
      if (!reachable.contains(v))
      {
         reachable.add(v);
         List<Vertex> adjList = new ArrayList<>(v.getAdjacencies());
         Collections.sort(adjList);
         for (Vertex neighbor : adjList)
         {
            depthFirstRecurHelper(neighbor, reachable);
         }
      }
      return reachable;
   }
   
  public void readData(String cities, String edges) throws FileNotFoundException 
  {
        Scanner cityScanner = new Scanner(new File(cities));
        Scanner edgeScanner = new Scanner(new File(edges));
        while (cityScanner.hasNextLine()) 
        {
            String city = cityScanner.nextLine().trim();
            addVertex(city);
        }
        while (edgeScanner.hasNextLine()) 
        {
            String[] edge = edgeScanner.nextLine().split("\\s+");
            addEdge(edge[0], edge[1]);
        }
        cityScanner.close();
        edgeScanner.close();
    }

    public int edgeCount() 
    {
        int count = 0;
        for (Vertex v : vertexMap.values()) 
        {
            count += v.getAdjacencies().size();
        }
        return count;
    }

    public int vertexCount() 
    {
        return vertexMap.size();
    }

    public boolean isReachable(String source, String target) 
    {
        if (!vertexMap.containsKey(source) || !vertexMap.containsKey(target)) 
        {
            return false;
        }
        Set<Vertex> visited = new HashSet<>();
        Stack<Vertex> stack = new Stack<>();
        stack.push(vertexMap.get(source));
        while (!stack.isEmpty()) 
        {
            Vertex current = stack.pop();
            if (current.getName().equals(target)) 
            {
                return true;
            }
            if (!visited.contains(current)) 
            {
                visited.add(current);
                for (Vertex v : current.getAdjacencies()) 
                {
                    if (!visited.contains(v)) 
                    {
                        stack.push(v);
                    }
                }
            }
        }
        return false;
    }

    public boolean isStronglyConnected() 
    {
        for (Vertex v : vertexMap.values()) 
        {
            Set<Vertex> visited = new HashSet<>();
            depthFirstRecurHelper(v, new ArrayList<>(visited));
            if (visited.size() != vertexMap.size()) 
            {
                return false;
            }
        }
        AdjList reversedGraph = new AdjList();
        for (Vertex v : vertexMap.values()) 
        {
            reversedGraph.addVertex(v.getName());
        }
        for (Vertex v : vertexMap.values()) 
        {
            for (Vertex adj : v.getAdjacencies()) 
            {
                reversedGraph.addEdge(adj.getName(), v.getName());
            }
        }
        for (Vertex v : reversedGraph.getVertices()) 
        {
            Set<Vertex> visited = new HashSet<>();
            reversedGraph.depthFirstRecurHelper(v, new ArrayList<>(visited));
            if (visited.size() != reversedGraph.vertexMap.size()) 
            {
                return false;
            }
        }
        return true;    
   }
}
