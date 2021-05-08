import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Created by worm2fed on 05.02.17.
 */
class MonteCarlo {
    private ArrayList<Point2D> polygon;
    private Pair<Point2D, Point2D> rectangle;
    private ArrayList<Pair> segments;
    private double inside = 0;
    private double all = 0;
    private double rectangle_squre;

    MonteCarlo(ArrayList<Point2D> coordinates) {
        polygon = coordinates;
    }

    // Find area
    String calculate() {
        rectangle = find_rectangle();
        segments = find_segments();
        rectangle_squre = (rectangle.getValue().getX() - rectangle.getKey().getX()) * (rectangle.getValue().getY() - rectangle.getKey().getY());
        Random r = new Random();
        double cur_ans = 0;
        double last_ans = 100;


        while (all < 100 || abs(cur_ans - last_ans) > 1e-6) {
            last_ans = cur_ans;
            double x = r.nextDouble() * (rectangle.getValue().getX() - rectangle.getKey().getX()) + rectangle.getKey().getX();
            double y = r.nextDouble() * (rectangle.getValue().getY() - rectangle.getKey().getY()) + rectangle.getKey().getY();

            if (inside(new Point2D.Double(x, y)))
                inside++;

            all++;
            cur_ans = rectangle_squre * inside / all;
        }

        return String.format("%.2f", cur_ans);
    }

    // Find polygon segments
    private ArrayList<Pair> find_segments() {
        ArrayList<Pair> segm = new ArrayList<>();

        for (int i = 0; i < polygon.size() - 1; i++)
            segm.add(new Pair(polygon.get(i), polygon.get(i + 1)));

        segm.add(new Pair(polygon.get(0), polygon.get(polygon.size() - 1)));

        return segm;
    }

    // Find rectangle around polygon
    private Pair<Point2D, Point2D> find_rectangle() {
        Pair rectangle;
        Point2D min = new Point2D.Double(99999, 99999);
        Point2D max = new Point2D.Double(-99999, -99999);

        for (int i = 0; i < polygon.size(); i++) {
            Point2D a = (Point2D) polygon.get(i);

            // Check min X
            if (min.getX() > a.getX())
                min.setLocation(a.getX(), min.getY());

            // Check max X
            if (max.getX() < a.getX())
                max.setLocation(a.getX(), max.getY());

            // Check min Y
            if (min.getY() > a.getY())
                min.setLocation(min.getX(), a.getY());

            // Check max Y
            if (max.getY() < a.getY())
                max.setLocation(max.getX(), a.getY());
        }

        return new Pair(min, max);
    }

    // Check is point inside polygon
    private boolean inside(Point2D point) {
        int ctr = 0;
        for (int i = 0; i < segments.size(); i++) {
            Pair<Point2D, Point2D> a = (Pair) segments.get(i);
            if (intersects(a.getKey(), a.getValue(),
                    point, new Point2D.Double(rectangle.getValue().getX() + 2, rectangle.getValue().getY() + 3)))
                ctr++;
        }

        return (ctr % 2 == 0) ? false : true;
    }

    // Check is segments intersects
    private static boolean intersects(Point2D a, Point2D b, Point2D c, Point2D d) {
        // We describe the section AB as A+(B-A)*u and CD as C+(D-C)*v
        // then we solve A + (B-A)*u = C + (D-C)*v
        // let's use Kramer's rule to solve the task (Ax = B) were x = (u, v)^T
        // build a matrix for the equation
        double[][] A = new double[2][2];
        A[0][0] = b.getX() - a.getX();
        A[1][0] = b.getY() - a.getY();
        A[0][1] = c.getX() - d.getX();
        A[1][1] = c.getY() - d.getY();

        // calculate determinant
        double det0 = A[0][0] * A[1][1] - A[1][0] * A[0][1];

        // substitute columns and calculate determinants
        double detU = (c.getX() - a.getX()) * A[1][1] - (c.getY() - a.getY()) * A[0][1];
        double detV = A[0][0] * (c.getY() - a.getY()) - A[1][0] * (c.getX() - a.getX());

        // calculate the solution
        // even if det0 == 0 (they are parallel) this will return NaN and comparison will fail -> false

        double u = detU / det0;
        double v = detV / det0;

        return u > 0 && u < 1 && v > 0 && v < 1;
    }
}
