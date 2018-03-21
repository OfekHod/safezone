import java.awt.*;
import java.awt.geom.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Funnel {
    private Point2D position;
    private Point2D upper;
    private Point2D rightLower;
    private Point2D rightUpper;
    private Point2D leftLower;
    private Point2D leftUpper;

    private Line2D lineLeftLower;
    private Line2D lineLeftUpper;
    private Line2D lineWidth;
    private Line2D lineRightLower;
    private Line2D lineRightUpper;
    private Line2D lineHeight;

    private Area area;
    private Path2D path;

    public Funnel(Point2D position, double lowerLineLength, double upperLineLength, double wideLineLength, double heightLineLength, double line1To4Degrees) {
        Line2D baseLine = new Line2D.Double(position.getX(), position.getY(), position.getX(), position.getY() + heightLineLength);

        this.position = position;
        this.upper = new Point2D.Double(position.getX(), position.getY() + heightLineLength);

        Line2D line1Right = rotateLine(new Line2D.Double(position.getX(), position.getY(), position.getX(), position.getY() + lowerLineLength), 90 - (line1To4Degrees));
        Line2D line1Left = rotateLine(new Line2D.Double(position.getX(), position.getY(), position.getX(), position.getY() + lowerLineLength), 90 + (line1To4Degrees));
        this.leftLower = line1Right.getP2();
        this.rightLower = line1Left.getP2();

        Line2D line2Right = getLine(leftLower, position.getX() + wideLineLength / 2, upperLineLength);
        Line2D line2Left = getLine(rightLower, position.getX() - wideLineLength / 2, upperLineLength);

        this.leftUpper = line2Right.getP2();
        this.rightUpper = line2Left.getP2();

        this.lineRightLower = new Line2D.Double(position.getX(), position.getY(), rightLower.getX(), rightLower.getY());
        this.lineRightUpper = new Line2D.Double(rightLower.getX(), rightLower.getY(), rightUpper.getX(), rightUpper.getY());
        this.lineWidth = new Line2D.Double(rightUpper.getX(), rightUpper.getY(), leftUpper.getX(), leftUpper.getY());
        this.lineLeftLower = new Line2D.Double(position.getX(), position.getY(), leftLower.getX(), leftLower.getY());
        this.lineLeftUpper = new Line2D.Double(leftLower.getX(), leftLower.getY(), leftUpper.getX(), leftUpper.getY());
        this.lineHeight = new Line2D.Double(position.getX(), position.getY(), upper.getX(), upper.getY());

        this.path = new Path2D.Double();
        path.moveTo(position.getX(), position.getY());
        path.lineTo(rightLower.getX(), rightLower.getY());
        path.lineTo(rightUpper.getX(), rightUpper.getY());
        path.lineTo(upper.getX(), upper.getY());
        path.lineTo(leftUpper.getX(), leftUpper.getY());
        path.lineTo(leftLower.getX(), leftLower.getY());
        path.lineTo(position.getX(), position.getY());

        this.area = new Area(path);
    }

    public Funnel(Funnel funnel) {
        
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getLeftLower() {
        return leftLower;
    }

    public Point2D getLeftUpper() {
        return leftUpper;
    }

    public Point2D getRightLower() {
        return rightLower;
    }

    public Point2D getRightUpper() {
        return rightUpper;
    }

    public Point2D getUpper() {
        return upper;
    }

    public Area getArea() {
        return this.area;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.cyan);
        g.fill(area);
        g.setColor(Color.black);
        g.draw(area);
    }

    public void expand(double leftDegrees, double rightDegrees) {
        //Funnel clone = (Funnel)this.clone();
    }

    public void rotate(double degrees) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(degrees), position.getX(), position.getY());
        this.path.transform(tx);
        //path = new Path2D.Double(tx.createTransformedShape(area));
        PathIterator it = path.getPathIterator(null);
        double []values = new double[2];
        it.next();
        it.currentSegment(values);
        rightLower = new Point2D.Double(values[0], values[1]);
        System.out.println("rightLower:["+values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);
        rightUpper = new Point2D.Double(values[0], values[1]);
        System.out.println("rightUpper:["+values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);
        upper = new Point2D.Double(values[0], values[1]);
        System.out.println("upper:["+values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);
        leftUpper = new Point2D.Double(values[0], values[1]);
        System.out.println("leftUpper:["+values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);
        leftLower = new Point2D.Double(values[0], values[1]);
        System.out.println("leftLower:["+values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);

        area = new Area(path);
    }

    private double lineLength(Line2D line) {
        return Math.pow(Math.pow(line.getX2() - line.getX1(), 2) + Math.pow(line.getY2() - line.getY1(), 2), 0.5);
    }

    private Line2D getLine(Point2D point1, double pointX2, double length) {
        double pointY2 = Math.pow(Math.pow(length, 2) - Math.pow(pointX2 - point1.getX(), 2), 0.5) + point1.getY();
        //double pointY2Fixed = 2 * point1.getY() - pointY2;
        return new Line2D.Double(point1.getX(), point1.getY(), pointX2, pointY2);
    }

    private Line2D rotateLine(Line2D line, double degrees) {
        double length = lineLength(line);
        double xChange = length * cos(Math.toRadians(degrees));
        double yChange = length * sin(Math.toRadians(degrees));

        return new Line2D.Double(line.getX1(), line.getY1(), line.getX1() + xChange, line.getY1() + yChange);
    }
}
