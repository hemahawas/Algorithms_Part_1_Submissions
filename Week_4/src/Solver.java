import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private final LinkedList<SearchNode> list = new LinkedList<>();
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial == null) throw  new IllegalArgumentException();
        MinPQ<SearchNode> minPQ = new MinPQ<>((o1, o2) -> {
            if(o1.moves+o1.board.manhattan() > o2.moves+o2.board.manhattan()) return 1;
            else if(o1.moves+o1.board.manhattan() < o2.moves+o2.board.manhattan()) return -1;
            return 0;
        });
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>((o1, o2) -> {
            if(o1.moves+o1.board.manhattan() > o2.moves+o2.board.manhattan()) return 1;
            else if(o1.moves+o1.board.manhattan() < o2.moves+o2.board.manhattan()) return -1;
            return 0;
        });

        Board twin = initial.twin();
        SearchNode goal;
        LinkedList<SearchNode> twinList = new LinkedList<>();

        minPQ.insert(new SearchNode(initial, null));
        twinMinPQ.insert(new SearchNode(twin, null));

//        For Testing
//        int step = 0;
//        System.out.println("########### Step " + (step++) + "###########");
//        System.out.println(initial);
//        System.out.println("############################################");

        do{
            goal = minPQ.delMin();
//            twinList.addLast(twinMinPQ.delMin());

             for (Board b : goal.board.neighbors()) {
                if(goal.previousSearchNode == null) {
                    minPQ.insert(new SearchNode(b, goal));
                }else if (!b.equals(goal.previousSearchNode.board)) {
                    minPQ.insert(new SearchNode(b, goal));
                }
             }

//            for (Board b : twinList.getLast().board.neighbors()) {
//                if(twinList.getLast().previousSearchNode == null) {
//                    twinMinPQ.insert(new SearchNode(b, twinList.getLast()));
//                }else if (!b.equals(twinList.getLast().previousSearchNode.board)) {
//                    twinMinPQ.insert(new SearchNode(b, twinList.getLast()));
//                }
//            }

            //For Testing
//            System.out.println("########### Step " + (step++) + "###########");
//            for (SearchNode s : minPQ) {
//                System.out.println("Priority = " + (s.moves+s.board.manhattan()));
//                System.out.println(s.board);
//            }
//            System.out.println("############################################");

        }while(!goal.board.isGoal() /*&& !twinList.getLast().board.isGoal()*/);

        SearchNode next = goal;
        while (next.previousSearchNode != null){
            list.addFirst(next);
            next = next.previousSearchNode;
        }
        list.addFirst(new SearchNode(initial, null));
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return list.getLast().board.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if(!isSolvable()) return -1;
        return this.list.size()-1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if(!isSolvable()) return null;
        LinkedList<Board> boards = new LinkedList<>();

        for (SearchNode searchNode : this.list) {
            boards.addLast(searchNode.board);
        }
        return boards;
    }

    private static class SearchNode{
        private final Board board;
        private final int moves;
        private final SearchNode previousSearchNode;

        SearchNode(Board board, SearchNode previousSearchNode){
            this.board = board;
            if(previousSearchNode == null){
                this.moves = 0;
            }
            else {
                this.moves = previousSearchNode.moves + 1;
            }
            this.previousSearchNode = previousSearchNode;
        }
    }

    // test client (see below)
    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        /* Testing
        int[][] tiles = new int[][]{{0,1,3},{4,2,5},{7,8,6}};
        Board board = new Board(tiles);

        Solver solver = new Solver(board);
        */
    }
}