package k2.factory;

import k2.valueobject.Space;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.EnumSet;

public class GameBoardFactory {

    public static Graph<Space, DefaultWeightedEdge> generate(/* @TODO: season */)
    {
        Graph<Space, DefaultWeightedEdge> board = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);//@TODO: Change to undirected?

        addSpaces(board);
        connectSpaces(board);
        calculateEntryCosts(board);

        return board;
    }

    private static void calculateEntryCosts(Graph<Space, DefaultWeightedEdge> board) {
        EnumSet.allOf(Space.class).forEach(
                space -> board.incomingEdgesOf(space).iterator().forEachRemaining(
                        edge -> board.setEdgeWeight(edge, space.getCostOfEntry())
                )
        );
    }

    private static void addSpaces(Graph<Space, DefaultWeightedEdge> board) {
        EnumSet.allOf(Space.class).forEach(space -> board.addVertex(space));
    }

    private static void connectSpaces(Graph<Space, DefaultWeightedEdge> board) {

        //5000
        board.addEdge(Space.BASE_CAMP, Space.S1); board.addEdge(Space.S1, Space.BASE_CAMP);
        board.addEdge(Space.S1, Space.S2); board.addEdge(Space.S2, Space.S1);
        board.addEdge(Space.S2, Space.S3); board.addEdge(Space.S3, Space.S2);
        board.addEdge(Space.S2, Space.S4); board.addEdge(Space.S4, Space.S2);
        board.addEdge(Space.S3, Space.S5); board.addEdge(Space.S5, Space.S3);
        board.addEdge(Space.S4, Space.S6); board.addEdge(Space.S6, Space.S4);
        board.addEdge(Space.S4, Space.S5); board.addEdge(Space.S5, Space.S4);
        board.addEdge(Space.S5, Space.S7); board.addEdge(Space.S7, Space.S5);
        board.addEdge(Space.S6, Space.S7); board.addEdge(Space.S7, Space.S6);
        board.addEdge(Space.S7, Space.S8); board.addEdge(Space.S8, Space.S7);
        board.addEdge(Space.S8, Space.S9); board.addEdge(Space.S9, Space.S8);
        board.addEdge(Space.S9, Space.S10); board.addEdge(Space.S10, Space.S9);
        board.addEdge(Space.S6, Space.S11); board.addEdge(Space.S11, Space.S6);
        board.addEdge(Space.S11, Space.S12); board.addEdge(Space.S12, Space.S11);
        board.addEdge(Space.S10, Space.S15); board.addEdge(Space.S15, Space.S10);
        board.addEdge(Space.S10, Space.S14); board.addEdge(Space.S14, Space.S10);
        board.addEdge(Space.S11, Space.S14); board.addEdge(Space.S14, Space.S11);
        board.addEdge(Space.S12, Space.S13); board.addEdge(Space.S13, Space.S12);

        //6000
        board.addEdge(Space.S15, Space.S16); board.addEdge(Space.S16, Space.S15);
        board.addEdge(Space.S14, Space.S16); board.addEdge(Space.S16, Space.S14);
        board.addEdge(Space.S14, Space.S13); board.addEdge(Space.S13, Space.S14);
        board.addEdge(Space.S13, Space.S18); board.addEdge(Space.S18, Space.S13);
        board.addEdge(Space.S16, Space.S17); board.addEdge(Space.S17, Space.S16);
        board.addEdge(Space.S18, Space.S19); board.addEdge(Space.S19, Space.S18);
        board.addEdge(Space.S18, Space.S20); board.addEdge(Space.S20, Space.S18);
        board.addEdge(Space.S20, Space.S22); board.addEdge(Space.S22, Space.S20);
        board.addEdge(Space.S19, Space.S21); board.addEdge(Space.S21, Space.S19);
        board.addEdge(Space.S17, Space.S21); board.addEdge(Space.S21, Space.S17);

        //7000
        board.addEdge(Space.S21, Space.S24); board.addEdge(Space.S24, Space.S21);
        board.addEdge(Space.S22, Space.S23); board.addEdge(Space.S23, Space.S22);
        board.addEdge(Space.S24, Space.S25); board.addEdge(Space.S25, Space.S24);
        board.addEdge(Space.S25, Space.S26); board.addEdge(Space.S26, Space.S25);
        board.addEdge(Space.S23, Space.S26); board.addEdge(Space.S26, Space.S23);

        //8000
        board.addEdge(Space.S26, Space.SUMMIT); board.addEdge(Space.SUMMIT, Space.S26);
    }
}
