
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture pict;
    private double [][] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException();
        pict = new Picture(picture);

        // calculate and store energy for each pixel
        refreshEnergy();
    }

    private void refreshEnergy() {
        energy = new double[pict.width()][pict.height()];

        // cache the RGB value for each pixel
        int [][] colorR = new int[pict.width()][pict.height()];
        int [][] colorG = new int[pict.width()][pict.height()];
        int [][] colorB = new int[pict.width()][pict.height()];
        for (int x = 0; x < pict.width(); ++x) {
            for (int y = 0; y < pict.height(); ++y) {
                colorR[x][y] = pict.get(x, y).getRed();
                colorG[x][y] = pict.get(x, y).getGreen();
                colorB[x][y] = pict.get(x, y).getBlue();
            }
        }

        // calculate energy for each pixel
        for (int x = 0; x < energy.length; ++x) {
            for (int y = 0; y < energy[0].length; ++y) {
                if (x == 0 || x == energy.length - 1 || y == 0 || y == energy[0].length - 1) {
                    energy[x][y] = 1000;
                    continue;
                }
                double rx = colorR[x + 1][y] - colorR[x - 1][y];
                double gx = colorG[x + 1][y] - colorG[x - 1][y];
                double bx = colorB[x + 1][y] - colorB[x - 1][y];
                double ry = colorR[x][y + 1] - colorR[x][y - 1];
                double gy = colorG[x][y + 1] - colorG[x][y - 1];
                double by = colorB[x][y + 1] - colorB[x][y - 1];
                double dx2 = Math.pow(rx, 2) + Math.pow(gx, 2) + Math.pow(bx, 2);
                double dy2 = Math.pow(ry, 2) + Math.pow(gy, 2) + Math.pow(by, 2);
                energy[x][y] = Math.sqrt(dx2 + dy2);
            }
        }
    }

    // current picture
    public Picture picture() {
        return new Picture(pict);
    }

    // width of current picture
    public int width() {
        return pict.width();
    }

    // height of current picture
    public int height() {
        return pict.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new IllegalArgumentException();
        return energy[x][y];
    }

    private int vidToX(int vid) {
        return (vid - 1) % width();
    }

    private int vidToY(int vid) {
        return (vid - 1) / width();
    }

    private int xyToVid(int x, int y) {
        return y*width() + x + 1;
    }

    private void relax(int from, int to, double [] distTo, int [] edgeTo) {
        /*
        int from = e.from(); // e is the directed edge
        int to = e.to();
        if (distTo[to] > distTo[from] + e.weight()) {
            distTo[to] = distTo[from] + e.weight();
            edgeTo[to] = e;
        }
        */
        double weight = 0.0;
        if (to < height()*width() + 1)
            weight = energy(vidToX(to), vidToY(to));
        if (distTo[to] > distTo[from] + weight) {
            distTo[to] = distTo[from] + weight;
            edgeTo[to] = from;
        }
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int [] seam = new int[height()];

        /*
        each pixel(x, y) represent a vertex with id x*width() + y + 1
        vertex with id 0 or width()*height() + 1 represents
        top (source) and bottom (dest) virtual vertex
         */
        int [] edgeTo = new int[width()*height() + 2];
        double [] distTo = new double[width()*height() + 2];

        // initialize edgeTo and distTo
        for (int v = 0; v < distTo.length; ++v) {
            distTo[v] = Double.POSITIVE_INFINITY;
            if (v <= width()) {
                edgeTo[v] = 0;
                distTo[v] = 0.0;
            }
        }

        // go through all vertices based on topological order
        for (int v = 1; v <= width()*(height() - 1); ++v) {
            // for each vertex, go through each of its three directed edge
            for (int d = -1; d <= 1; ++d) {
                if (v % width() == 1 && d == -1) continue;
                if (v % width() == 0 && d == 1) continue;
                relax(v, v + width() + d, distTo, edgeTo);
            }
        }
        for (int v = width()*(height() - 1) + 1; v <= width()*height(); ++v) {
            relax(v, width()*height() + 1, distTo, edgeTo);
        }

        // get seam path from edgeTo
        int v = edgeTo[width()*height() + 1];
        for (int i = 0; i < height(); ++i) {
            seam[height() - 1 - i] = vidToX(v);
            v = edgeTo[v];
        }

        return seam;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int [] seam = new int[width()];

        /*
        each pixel(x, y) represent a vertex with id x*width() + y + 1
        vertex with id 0 or width()*height() + 1 represents
        left (source) and right (dest) virtual vertex
         */
        int [] edgeTo = new int[width()*height() + 2];
        double [] distTo = new double[width()*height() + 2];

        // initialize edgeTo and distTo
        for (int v = 1; v < distTo.length; ++v) {
            distTo[v] = Double.POSITIVE_INFINITY;
            if (vidToX(v) == 0 && v < distTo.length - 1) {
                edgeTo[v] = 0;
                distTo[v] = 0.0;
            }
        }
        edgeTo[0] = 0;
        distTo[0] = 0.0;

        // go through all vertices based on topological order
        for (int x = 0; x < width() - 1; ++x) {
            for (int y = 0; y < height(); ++y) {
                for (int d = -1; d <= 1; ++d) {
                    if (y == 0 && d == -1) continue;
                    if (y == height() - 1 && d == 1) continue;
                    relax(xyToVid(x, y), xyToVid(x + 1, y + d), distTo, edgeTo);
                }
            }
        }
        for (int y = 0; y < height(); ++y) {
            relax(xyToVid(width() - 1, y), width()*height() + 1, distTo, edgeTo);
        }

        // get seam path from edgeTo
        int v = edgeTo[width()*height() + 1];
        for (int i = 0; i < width(); ++i) {
            seam[width() - 1 - i] = vidToY(v);
            v = edgeTo[v];
        }

        return seam;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height() || width() <= 1)
            throw new IllegalArgumentException();
        for (int i = 0; i < seam.length; ++i) {
            if (seam[i] < 0 || seam[i] >= width())
                throw new IllegalArgumentException();
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException();
        }

        Picture newPict = new Picture(width() - 1, height());
        for (int y = 0; y < height(); ++y) {
            for (int x = 0; x < width() - 1; ++x) {
                if (x < seam[y]) newPict.set(x, y, pict.get(x, y));
                else newPict.set(x, y, pict.get(x + 1, y));
            }
        }
        pict = newPict;
        //
        refreshEnergy();
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width() || height() <= 1)
            throw new IllegalArgumentException();
        for (int i = 0; i < seam.length; ++i) {
            if (seam[i] < 0 || seam[i] >= height())
                throw new IllegalArgumentException();
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException();
        }
        Picture newPict = new Picture(width(), height() - 1);
        for (int x = 0; x < width(); ++x) {
            for (int y = 0; y < height() - 1; ++y) {
                if (y < seam[x]) newPict.set(x, y, pict.get(x, y));
                else newPict.set(x, y, pict.get(x, y + 1));
            }
        }
        pict = newPict;
        //
        refreshEnergy();
    }


    // unit testing (optional)
    public static void main(String[] args) {
        Picture p = new Picture("test.png");
        SeamCarver sc = new SeamCarver(p);
        int [] seam = sc.findVerticalSeam();
        // sc.removeVerticalSeam(seam);
        // sc.picture().show();
        seam = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(seam);
    }

}

