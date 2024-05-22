package afd;

import java.util.*;

public class Graph<T>{

    private List<Edge<T>> allEdges;
    private Map<String,Vertex<T>> allVertex;
    boolean isDirected = false;

    public Graph(boolean isDirected){
        allEdges = new ArrayList<>();
        allVertex = new HashMap<>();
        this.isDirected = isDirected;
    }

    public void addEdge(String id1, String id2){
        addEdge(id1,id2,0);
    }


    public void addEdge(String id1,String id2, int weight){
        Vertex<T> vertex1 = null;
        if(allVertex.containsKey(id1)){
            vertex1 = allVertex.get(id1);
        }else{
            vertex1 = new Vertex<T>(id1);
            allVertex.put(id1, vertex1);
        }
        Vertex<T> vertex2 = null;
        if(allVertex.containsKey(id2)){
            vertex2 = allVertex.get(id2);
        }else{
            vertex2 = new Vertex<T>(id2);
            allVertex.put(id2, vertex2);
        }

        Edge<T> edge = new Edge<T>(vertex1,vertex2,isDirected,weight);
        allEdges.add(edge);
        vertex1.addAdjacentVertex(edge, vertex2);
        if(!isDirected){
            vertex2.addAdjacentVertex(edge, vertex1);
        }

    }


    public Collection<Vertex<T>> getAllVertex(){
        return allVertex.values();
    }


}

 class Vertex<T> {
    String id;
    private List<Edge<T>> edges = new ArrayList<>();
    private List<Vertex<T>> adjacentVertex = new ArrayList<>();

    Vertex(String id) {
        this.id = id;
    }

    public void addAdjacentVertex(Edge<T> e, Vertex<T> v) {
        edges.add(e);
        adjacentVertex.add(v);
    }

    @Override
    public String toString() {
        return id;
    }

    public List<Vertex<T>> getAdjacentVertexes() {
        return adjacentVertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}




class Edge<T>{
    private boolean isDirected = false;
    private Vertex<T> vertex1;
    private Vertex<T> vertex2;
    private int weight;


    Edge(Vertex<T> vertex1, Vertex<T> vertex2,boolean isDirected,int weight){
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
        this.isDirected = isDirected;
    }


}

