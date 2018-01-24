import java.util.*;
import java.util.LinkedList;
import java.util.List;

public class graph_parser{

  static class Node{

    private Object[] node;

    public Node(String connector, String value, double weight){
      this.node = new Object[3];

      node[0] = connector;
      node[1] = value;
      node[2] = weight;
    }

    public String getDestination(){
      return (String)this.node[0];
    }

    public String getValue(){
      return (String)this.node[1];
    }

    public double getWeight(){
      return (double)this.node[2];
    }

    public void setIndex(int newIndex){
      this.node[0] = newIndex;
    }

    public void setValue(String newValue){
      this.node[1] = newValue;
    }

    public void setWeight(double newWeight){
      this.node[2] = newWeight;
    }

  }

  static class Graph{
    int V;
    LinkedList<Node> adjListArray[];

    Graph(int V)
    {
      this.V = V;
      adjListArray = new LinkedList[V];

      for(int i = 0; i < V ; i++){
        adjListArray[i] = new LinkedList<>();
      }
    }
  }

  static void addEdge(Graph graph, int src, Node values)
	{
		graph.adjListArray[src].addFirst(values);
	}

  static void printGraph(Graph graph) {
		for(int v = 0; v < graph.V; v++)
		{
      for(int i = 0; i < graph.adjListArray[v].size(); i++){
        System.out.println(graph.adjListArray[v].get(i).getValue());
        System.out.println("___TO:____");
        System.out.println(graph.adjListArray[v].get(i).getDestination());
        System.out.println("____WEIGHT:____");
        System.out.println(graph.adjListArray[v].get(i).getWeight());
        System.out.println("****");

      }
		}
	}

  public static void main(String [] args){

    String input = args[0];

    int indexOfStart = input.indexOf("__START__");

    String userName = input.substring(2, indexOfStart-1);

    String stemmed_input = input.substring(indexOfStart+11);

    String[] arr = stemmed_input.split("]");

    List<List<String>> values_for_graph = new ArrayList<List<String>>();

    for(int i = 0; i < arr.length; i++){
      int count = 1;
      for(int j = 0; j < arr[i].length(); j++){
        if(arr[i].charAt(j) == '&'){
          count++;
        }
      }

      List<String> values = new ArrayList<String>();
      String[] temp = arr[i].split("& ");
      for (int z = 0; z < count; z++) {
          values.add(temp[z]);
       }
      values_for_graph.add(values);
    }

    List<String> vertex_container = new ArrayList<String>();
    List<Double> weight_container = new ArrayList<Double>();

    for(int i = 0; i < values_for_graph.size(); i++){
      for(int j = 0; j < values_for_graph.get(i).size(); j++){

        boolean hit = false;

        String temp = values_for_graph.get(i).get(j);
        int index = temp.indexOf('?');

        if(index != -1){
          String clicked = temp.substring(0, 32);

          //BUG: doesn't check if value is contained in other arrays.
          if(!(vertex_container.contains(clicked))) {
            hit = true;
          }

          String pearson_og = temp.substring(index+2);
          int lengthOfPearson = pearson_og.length();
          String pearson_final = pearson_og.substring(0, lengthOfPearson-3);
          double pearson_double = Double.parseDouble(pearson_final);
          if(hit){
            vertex_container.add(clicked);
            weight_container.add(pearson_double);
          }

          int indexOfHit = vertex_container.indexOf(clicked);

          Double pearson_in_container = weight_container.get(indexOfHit);
          double diff = pearson_double - pearson_in_container;
          /*
          System.out.println("INDEX: " + indexOfHit + "______:");
          System.out.println("new: " + pearson_double + "________");
          System.out.println("old: " + pearson_in_container + "________");
          System.out.println("DIFF: " + diff);
          System.out.println("________");
          */
          double newLog = 0.0;

          if(diff != 0.0){
            newLog = Math.log(Math.abs(pearson_double))/Math.log(Math.abs(diff));
          }

          double newWeight = 0.0;

          if(diff < 0){
            newWeight = (1 - newLog) * pearson_in_container;
          }
          if(diff > 0){
            newWeight = (1 + newLog) * pearson_in_container;
          }
          if(diff == 0.0){
            newWeight = pearson_in_container;
          }
          /*
          System.out.println("ADJ. WEIGHT: " + newWeight);
          System.out.println("________"+"\n");
          */
          weight_container.set( indexOfHit, newWeight);
        }
      }
    }

    List<String> connector_container = new ArrayList<String>();

    for(int i = 0; i < vertex_container.size()-1; i++){
      int next = i;
      ++next;
      String connector = vertex_container.get(next);
      connector_container.add(connector);
    }

    Graph graph = new Graph(vertex_container.size());

    for(int i = 0; i < connector_container.size(); i++){
      Node temp =new Node(connector_container.get(i), vertex_container.get(i), weight_container.get(i));
      addEdge(graph, i, temp);
    }
    printGraph(graph);

  }
}
