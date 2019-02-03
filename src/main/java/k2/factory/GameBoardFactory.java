package k2.factory;

import k2.valueobject.Space;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class GameBoardFactory {

    public static Graph<Space, DefaultWeightedEdge> generate(/* @TODO: season */)
    {
        Graph<Space, DefaultWeightedEdge> board = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);//@TODO: Change to undirected?

        board.addVertex(Space.BASE_CAMP);
        board.addVertex(Space.S1);
        board.addVertex(Space.S2);
        board.addVertex(Space.S3);
        board.addVertex(Space.S4);
        board.addVertex(Space.S5);
        board.addVertex(Space.S6);
        board.addVertex(Space.S7);
        board.addVertex(Space.S8);
        board.addVertex(Space.SUMMIT);

        board.addEdge(Space.BASE_CAMP, Space.S1);
        board.addEdge(Space.S1, Space.S2);
        board.addEdge(Space.S2, Space.S3);
        board.addEdge(Space.S2, Space.S4);
        board.addEdge(Space.S3, Space.S5);
        board.addEdge(Space.S4, Space.S6);
        board.addEdge(Space.S4, Space.S5);
        board.addEdge(Space.S5, Space.S7);
        board.addEdge(Space.S6, Space.S7);
        board.addEdge(Space.S7, Space.S8);
        //...
        board.addEdge(Space.S7, Space.SUMMIT);
        board.setEdgeWeight(Space.S7, Space.SUMMIT, Space.SUMMIT.getCostOfEntry());//@TODO: Use only when cost is gt 0

        return board;
    }
}
