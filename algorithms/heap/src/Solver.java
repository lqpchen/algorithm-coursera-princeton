/*
A* search algorithm for solving slider puzzle
see more info at: https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver {

    private final int MAX_PRIORITY = 99999;
    private boolean solvable;
    private Node goal;

    private class Node implements Comparable<Node> {
        public Board board;
        public Node par; // refer to the parent node
        public Integer priority;
        public int numMoves;

        public Node(Board board, int priority, int numMoves, Node par) {
            this.board = board;
            this.priority = priority;
            this.numMoves = numMoves;
            this.par = par;
        }

        public int compareTo(Node that) {
            return this.priority.compareTo(that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        final PriorityQueue<Node> mpq;
        final PriorityQueue<Node> alterMPQ; // used to detect unsolvable puzzle

        // initialize the minimum priority queue
        mpq = new PriorityQueue<>();
        Node node = new Node(initial, MAX_PRIORITY - initial.manhattan(),
                0, null);
        mpq.insert(node);

        // initialize alterMPQ for detecting unsolvable puzzle
        alterMPQ = new PriorityQueue<>();
        Board alterBoard = initial.twin();
        Node alterNode = new Node(alterBoard, MAX_PRIORITY - alterBoard.manhattan(),
                0,null);
        alterMPQ.insert(alterNode);

        solve(mpq, alterMPQ);
    }

    private void solve(PriorityQueue<Node> mpq, PriorityQueue<Node> alterMPQ) {
        while (!mpq.isEmpty() && !alterMPQ.isEmpty()) {
            Node node = mpq.delMax();
            // System.out.println(node.board);
            if (node.board.isGoal()) {
                obtainSolution(1, node);
                return;
            }
            for (Board neighb : node.board.neighbors()) {
                if (node.par != null && node.par.board.equals(neighb)) {
                    continue;
                }
                Node childNode = new Node(
                        neighb,
                        MAX_PRIORITY - (neighb.manhattan() + node.numMoves + 1),
                        node.numMoves + 1,
                        node);
                mpq.insert(childNode);
            }

            Node alterNode = alterMPQ.delMax();
            if (alterNode.board.isGoal()) {
                obtainSolution(0, null);
                return;
            }
            for (Board neighb : alterNode.board.neighbors()) {
                if (alterNode.par != null && alterNode.par.board.equals(neighb)) {
                    continue;
                }
                Node childNode = new Node(
                        neighb,
                        MAX_PRIORITY - (neighb.manhattan() + alterNode.numMoves + 1),
                        alterNode.numMoves + 1,
                        alterNode);
                alterMPQ.insert(childNode);
            }
        }
    }

    private void obtainSolution(int state, Node goal) {
        if (state == 1) { // has solution
            this.solvable = true;
            this.goal = goal;
        } else if (state == 0) { // no solution
            this.solvable = false;
            this.goal = null;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!solvable) {
            return -1;
        } else {
            return goal.numMoves;
        }
    }

    private class BoardIterable implements Iterable<Board> {

        private class BoardIter implements Iterator<Board> {
            private final Board [] solution;
            private int iter;

            public BoardIter() {
                solution = new Board[goal.numMoves + 1];
                Node seq = goal;
                for (int step = goal.numMoves; step >= 0; --step) {
                    solution[step] = seq.board;
                    seq = seq.par;
                }
                iter = 0;
            }

            public boolean hasNext() {
                return iter < solution.length;
            }

            public Board next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return solution[iter++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public Iterator<Board> iterator() {
            return new BoardIter();
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) {
            return null;
        }

        return new BoardIterable();
    }

    // test client (see below)
    public static void main(String[] args) {
        // int [][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        int [][] tiles = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
    }
}
