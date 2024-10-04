// Name: Cherish Seelam
// Date: 5/29/24

import java.util.*;
import java.io.*;

/* Resource classes and interfaces for 
 *              Graphs6: Dijkstra
 *              Graphs7: Dijkstra with Cities
 */

class Neighbor implements Comparable<Neighbor> 
{
   private final wVertex target;
   private final double edgeDistance;
   
   public Neighbor(wVertex t, double d) 
   {
      target = t;
      edgeDistance = d;
   }

   public wVertex getTarget() 
   {
      return target;
   }

   public double getEdgeDistance() 
   {
      return edgeDistance;
   }

   @Override
   public int hashCode() 
   {
      return Objects.hash(target);
   }

   @Override
   public boolean equals(Object obj) 
   {
      if (this == obj) 
      {
         return true;
      }
      if (obj == null || getClass() != obj.getClass()) 
      {
         return false;
      }
      Neighbor other = (Neighbor) obj;
      return target.equals(other.target);
   }

   @Override
   public int compareTo(Neighbor other) 
   {
      return this.target.getName().compareTo(other.target.getName());
   }

   public String toString() 
   {
      return target.getName() + " " + edgeDistance;
   }
}

/**************************************************************/
class PQelement implements Comparable<PQelement> 
{ 
   private wVertex vertex;
   private Double distanceToVertex; 
   private wVertex previous; //for Dijkstra 7
      
   public PQelement(wVertex v, double d) 
   {
      vertex = v;
      distanceToVertex = d;
   }
   
   //getter and setter methods provided
   public wVertex getVertex() 
   {
      return this.vertex;
   }
   
   public Double getDistanceToVertex() 
   {
      return this.distanceToVertex;
   }
   
   public void setVertex(wVertex v) 
   {
      this.vertex = v;
   }
   
   public void setDistanceToVertex(Double d) 
   {
      this.distanceToVertex = d;
   }   
   
   public int compareTo(PQelement other) 
   {
      return Double.compare(this.distanceToVertex, other.distanceToVertex);
   }
   
   public wVertex getPrevious() 
   {
      return this.previous;
   }

   public void setPrevious(wVertex v)
    {
      this.previous = v;
   } 
   
   public String toString() 
   {
      return vertex.getName() + " " + distanceToVertex;
   }
}

/********************* wVertexInterface ************************/
interface wVertexInterface 
{
   public String getName();
   
   public Set<Neighbor> getNeighbors();
   
   public void addAdjacent(wVertex v, double d); 
     
   public ArrayList<PQelement> getAlDistanceToVertex();
   
   public PQelement getPQelement(wVertex v);
   
   public Double getDistanceToVertex(wVertex v);
   
   public void setDistanceToVertex(wVertex v, double d); 

   public String toString();  
}

class wVertex implements Comparable<wVertex>, wVertexInterface 
{ 
   public static final double NODISTANCE = Double.POSITIVE_INFINITY;
   private final String name;
   private Set<Neighbor> neighbors;  
   private ArrayList<PQelement> alDistanceToVertex;

   public wVertex(String name) 
   {
      this.name = name;
      this.neighbors = new HashSet<>();
      this.alDistanceToVertex = new ArrayList<>();
   }

   public String getName() 
   {
      return name;
   }

   public Set<Neighbor> getNeighbors() 
   {
      return neighbors;
   }

   public void addAdjacent(wVertex v, double d) 
   {
      neighbors.add(new Neighbor(v, d));
   }

   public ArrayList<PQelement> getAlDistanceToVertex() 
   {
      return alDistanceToVertex;
   }

   public PQelement getPQelement(wVertex v) 
   {
      for (PQelement pq : alDistanceToVertex) 
      {
         if (pq.getVertex().equals(v)) 
         {
            return pq;
         }
      }
      return null;
   }

   public Double getDistanceToVertex(wVertex v) 
   {
      PQelement pq = getPQelement(v);
        if (pq != null) 
        {
            return pq.getDistanceToVertex();
        } 
        else 
        {
            return null;
        }
   }

   public void setDistanceToVertex(wVertex v, double d) 
   {
      PQelement pq = getPQelement(v);
      if (pq != null) 
      {
         pq.setDistanceToVertex(d);
      } 
      else 
      {
         alDistanceToVertex.add(new PQelement(v, d));
      }
   }

   @Override
   public int hashCode() 
   {
      return Objects.hash(name);
   }

   @Override
   public boolean equals(Object obj) 
   {
      if (this == obj) 
      {
         return true;
      }
      if (obj == null || getClass() != obj.getClass()) 
      {
         return false;
      }
      wVertex other = (wVertex) obj;
      return Objects.equals(name, other.name);
   }

   @Override
   public int compareTo(wVertex other) 
   {
      return this.name.compareTo(other.name);
   }

   public String toString() 
   {
      String neighborsStr = neighbors.toString();
      String alDistanceToVertexStr = alDistanceToVertex.toString();
      return name + " " + neighborsStr + " List: " + alDistanceToVertexStr;
   }
}

/*********************   Interface for Graphs 6:  Dijkstra ****************/
interface AdjListWeightedInterface {
   public Set<wVertex> getVertices();  
   public Map<String, wVertex> getVertexMap();
   public wVertex getVertex(String vName);
   public void addVertex(String vName);
   public void addEdge(String source, String target, double d);
   public void minimumWeightPath(String vertexName);
   public String toString();  
}  

/***********************  Interface for Graphs 7:  Dijkstra with Cities   */
interface AdjListWeightedInterfaceWithCities {       
   public List<String> getShortestPathTo(wVertex vSource, wVertex target);
   public void readData(String vertexNames, String edgeListData);
}
 
/****************************************************************/ 
/**************** this is the graph  ****************************/
public class AdjListWeighted implements AdjListWeightedInterface {
   private Map<String, wVertex> vertexMap = new TreeMap<>();

   public Set<wVertex> getVertices() 
   {
      return new HashSet<>(vertexMap.values());
   }

   public Map<String, wVertex> getVertexMap() 
   {
      return vertexMap;
   }

   public wVertex getVertex(String vName) 
   {
      return vertexMap.get(vName);
   }

   public void addVertex(String vName) 
   {
      if (!vertexMap.containsKey(vName)) 
      {
         vertexMap.put(vName, new wVertex(vName));
      }
   }

   public void addEdge(String source, String target, double d) 
   {
      wVertex vSource = getVertex(source);
      wVertex vTarget = getVertex(target);
      if (vSource != null && vTarget != null) 
      {
         vSource.addAdjacent(vTarget, d);
      }
   }

   public void minimumWeightPath(String vertexName) 
   {
      // Implementation of Dijkstra's algorithm to be added here
      wVertex source = getVertex(vertexName);  
      if (source == null) 
      {
         return;
      }

      PriorityQueue<PQelement> pq = new PriorityQueue<>();
      Map<wVertex, Double> distances = new HashMap<>(); 
      
      Map<wVertex, wVertex> previous = new HashMap<>();

      for (wVertex v : getVertices()) 
      {
         PQelement pqElement;
         if (v.equals(source)) 
         {
            distances.put(v, 0.0);
            pqElement = new PQelement(v, 0.0);
         } 
         else 
         {
            distances.put(v, Double.POSITIVE_INFINITY);
            pqElement = new PQelement(v, Double.POSITIVE_INFINITY);
         }
         pq.add(pqElement);
         source.getAlDistanceToVertex().add(pqElement);
         previous.put(v, null);
      }

      while (!pq.isEmpty()) 
      {
         PQelement currentPQ = pq.poll();
         wVertex currentVertex = currentPQ.getVertex();
         double currentDist = currentPQ.getDistanceToVertex();

         for (Neighbor neighbor : currentVertex.getNeighbors()) 
         {
            wVertex neighborVertex = neighbor.getTarget();
            double newDist = currentDist + neighbor.getEdgeDistance();

            if (newDist < distances.get(neighborVertex)) 
            {
               distances.put(neighborVertex, newDist);
               PQelement pqElement = new PQelement(neighborVertex, newDist);
               pq.add(pqElement);
               previous.put(neighborVertex, currentVertex);
            }
         }
      }

      for (Map.Entry<wVertex, Double> entry : distances.entrySet()) 
      {
         source.setDistanceToVertex(entry.getKey(), entry.getValue());
         wVertex v = entry.getKey();
         PQelement pqElement = source.getPQelement(v);
         if (pqElement != null) 
         {
            pqElement.setPrevious(previous.get(v));
         }
      }   
   }
   
   public String toString() 
   {
      String strResult = "";
      for (String vName : vertexMap.keySet()) 
      {
         strResult += vertexMap.get(vName).toString() + "\n";
      }
      return strResult.trim();
   }
   
   public List<String> getShortestPathTo(wVertex vSource, wVertex target) 
   {
      List<String> path = new LinkedList<>();
      wVertex step = target;

      if (vSource.getDistanceToVertex(target) == null || vSource.getDistanceToVertex(target) == Double.POSITIVE_INFINITY) 
      {
         path.add(target.getName());
         return path; 
      }

      while (step != null && vSource.getPQelement(step) != null) 
      {
         path.add(step.getName());
         step = vSource.getPQelement(step).getPrevious();
      }
      Collections.reverse(path);
      return path;   
   }   
   public void readData(String vertexNames, String edgeListData) 
   {
      // Implementation for Graphs 7 with try-catch for reading data
      try 
      {
         Scanner vertexScanner = new Scanner(new File(vertexNames));
         while (vertexScanner.hasNextLine()) 
         {
            String vertexName = vertexScanner.nextLine().trim();
            addVertex(vertexName);
         }
         vertexScanner.close();

         Scanner edgeScanner = new Scanner(new File(edgeListData));
         while (edgeScanner.hasNextLine()) 
         {
            String[] edge = edgeScanner.nextLine().trim().split("\\s+");
            String source = edge[0];
            String target = edge[1];
            double weight = Double.parseDouble(edge[2]);
            addEdge(source, target, weight);
         }
         edgeScanner.close();
      } 
      catch (FileNotFoundException e) 
      {
         System.err.println("File not found: " + e.getMessage());
      }
   }
}
