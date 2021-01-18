/*
board game
see more info at: https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private int [][] slider;
    private final int n; // #row (#col)

    private int distMan;
    private int distHam;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        slider = new int [n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                slider[i][j] = tiles[i][j];
            }
        }

        calcManhattan();
        calcHamming();
    }

    private void exch(int i1, int j1, int i2, int j2) {
        int temp = slider[i1][j1];
        slider[i1][j1] = slider[i2][j2];
        slider[i2][j2] = temp;
    }

    // string representation of this board
    public String toString() {
        String str = String.valueOf(n);
        str += "\n";
        // inefficient string concat, better use the StringBuilder class
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                str += String.valueOf(slider[i][j]) + " ";
            }
            str += "\n";
        }
        return str;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    private void calcHamming() {
        distHam = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (slider[i][j] == 0) {
                    continue;
                }
                distHam += slider[i][j] != (i*n + j + 1) ? 1 : 0;
            }
        }
    }

    // sum of Manhattan distances between tiles and goal
    private void calcManhattan() {
        distMan = 0;
        int row, col; // the supposed cord for each number
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (slider[i][j] == 0) {
                    continue;
                }
                row = (slider[i][j] - 1)/n;
                col = (slider[i][j] - 1) % n;
                distMan += Math.abs(row - i) + Math.abs(col - j);
            }
        }
    }

    public int hamming() {
        return distHam;
    }

    public int manhattan() {
        return distMan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i*j == (n - 1)*(n - 1)) {
                    if (slider[i][j] != 0) {
                        return false;
                    } else {
                        return true;
                    }
                }
                if (slider[i][j] != i*n + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y
    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (n != other.dimension()) {
            return false;
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (slider[i][j] != other.slider[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initArr(int [][] arr) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                arr[i][j] = slider[i][j];
            }
        }
    }

    private class NeighboringBoards implements Iterable<Board> {

        private class BoardIter implements Iterator<Board> {
            Board [] boards = new Board[4];
            int numNeighb;
            int iter;

            // construct all possible neighbouring boards
            public BoardIter() {
                // find the blank block
                int i = 0, j = 0;
                boolean found = false;
                for (i = 0; i < n; ++i) {
                    for (j = 0; j < n; ++j) {
                        if (slider[i][j] == 0) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }

                numNeighb = 0;
                iter = 0;
                // left neighbour
                if (j > 0) {
                    int [][] neighb = new int [n][n];
                    initArr(neighb);
                    Board newBoard = new Board(neighb);
                    newBoard.exch(i, j, i, j - 1);
                    boards[numNeighb++] = newBoard;
                }
                // right neighbour
                if (j < n - 1) {
                    int [][] neighb = new int [n][n];
                    initArr(neighb);
                    Board newBoard = new Board(neighb);
                    newBoard.exch(i, j, i, j + 1);
                    boards[numNeighb++] = newBoard;
                }
                // up neighbour
                if (i > 0) {
                    int [][] neighb = new int [n][n];
                    initArr(neighb);
                    Board newBoard = new Board(neighb);
                    newBoard.exch(i, j, i - 1, j);
                    boards[numNeighb++] = newBoard;
                }
                // down neighbour
                if (i < n - 1) {
                    int [][] neighb = new int [n][n];
                    initArr(neighb);
                    Board newBoard = new Board(neighb);
                    newBoard.exch(i, j, i + 1, j);
                    boards[numNeighb++] = newBoard;
                }
            }

            public boolean hasNext() {
                return iter < numNeighb;
            }

            public Board next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return boards[iter++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public Iterator<Board> iterator() {
            return new BoardIter();
        }
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new NeighboringBoards();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newBoard = new Board(slider);

        int u = 0, v = 0;
        for (u = 0; u < n*n; ++u) {
            for (v = 0; v < n*n; ++v) {
                if (u != v && slider[u/n][u % n]*slider[v/n][v % n] != 0) {
                    newBoard.exch(u/n, u % n, v/n, v % n);
                    return newBoard;
                }
            }
        }

        return newBoard;
    }

    public static void main(String[] args) {
        // int [][] slider = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        int [][] slider = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        // int [][] slider = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        // int [][] slider = {{5, 8, 7}, {1, 4, 0}, {3, 2, 6}};
        // int [][] slider = {{0, 1}, {2, 3}};
        Board b = new Board(slider);
        System.out.println(b);
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        System.out.println(b.isGoal());
        System.out.println();
        Iterable<Board> neighb = b.neighbors();
        for (Board n : neighb) {
            System.out.println(n);
        }
        System.out.println(b.twin());

    }
}
