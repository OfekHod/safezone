import java.awt.*;
import java.awt.geom.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Funnel {
    private Point2D upper;
    private Point2D position;
    private Point2D rightLower;
    private Point2D rightUpper;
    private Point2D leftLower;
    private Point2D leftUpper;

    private Point2D rightUpper2 = null;
    private Point2D leftUpper2 = null;

    private Line2D lineLeftLower;
    private Line2D lineLeftUpper;
    private Line2D lineWidth;
    private Line2D lineRightLower;
    private Line2D lineRightUpper;
    private Line2D lineHeight;

    private Area area;
    private Path2D path;

    public Graphics2D g;

    public Funnel(Point2D position, Weapon weapon) {
            this.position = position;
        this.upper = new Point2D.Double(position.getX(), position.getY() + weapon.height);

        Line2D line1Right = rotateLine(new Line2D.Double(position.getX(), position.getY(), position.getX(), position.getY() + weapon.lowerLineLength), 90 - (weapon.lowerLineToHeightLineDegree));
        Line2D line1Left = rotateLine(new Line2D.Double(position.getX(), position.getY(), position.getX(), position.getY() + weapon.lowerLineLength), 90 + (weapon.lowerLineToHeightLineDegree));
        this.leftLower = line1Right.getP2();
        this.rightLower = line1Left.getP2();

        Line2D line2Right = getLine(leftLower, position.getX() + weapon.width / 2, weapon.upperLineLength);
        Line2D line2Left = getLine(rightLower, position.getX() - weapon.width / 2, weapon.upperLineLength);

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
        this.upper = funnel.upper;
        this.position = funnel.position;
        this.rightLower = funnel.rightLower;
        this.rightUpper = funnel.rightUpper;
        this.leftLower = funnel.leftLower;
        this.leftUpper = funnel.leftUpper;
        this.rightUpper2 = funnel.rightUpper2;
        this.leftUpper2 = funnel.leftUpper2;

        this.lineLeftLower = funnel.lineLeftLower;
        this.lineLeftUpper = funnel.lineLeftUpper;
        this.lineWidth = funnel.lineWidth;
        this.lineRightLower = funnel.lineRightLower;
        this.lineRightUpper = funnel.lineRightUpper;
        this.lineHeight = funnel.lineHeight;

        this.path = new Path2D.Double(funnel.path);
        this.area = new Area(path);
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

    public Funnel expand(double leftDegrees, double rightDegrees) {
        Funnel rightFunell = this.rotate(rightDegrees);
        Funnel leftFunnel = this.rotate(-leftDegrees);

        //rightFunell.draw(g);
        //leftFunnel.draw(g);

        Funnel expandedFunnel = new Funnel(this);
        expandedFunnel.rightLower = rightFunell.rightLower;
        expandedFunnel.rightUpper = rightFunell.rightUpper;
        expandedFunnel.leftLower = leftFunnel.leftLower;
        expandedFunnel.leftUpper = leftFunnel.leftUpper;
        expandedFunnel.rightUpper2 = rightFunell.upper;
        expandedFunnel.leftUpper2 = leftFunnel.upper;

        expandedFunnel.path = new Path2D.Double();
        expandedFunnel.path.moveTo(position.getX(), position.getY());
        expandedFunnel.path.lineTo(expandedFunnel.rightLower.getX(), expandedFunnel.rightLower.getY());
        expandedFunnel.path.lineTo(expandedFunnel.rightUpper.getX(), expandedFunnel.rightUpper.getY());
        expandedFunnel.path.lineTo(expandedFunnel.rightUpper2.getX(), expandedFunnel.rightUpper2.getY());
        expandedFunnel.path.lineTo(expandedFunnel.upper.getX(), expandedFunnel.upper.getY());
        expandedFunnel.path.lineTo(expandedFunnel.leftUpper2.getX(), expandedFunnel.leftUpper2.getY());
        expandedFunnel.path.lineTo(expandedFunnel.leftUpper.getX(), expandedFunnel.leftUpper.getY());
        expandedFunnel.path.lineTo(expandedFunnel.leftLower.getX(), expandedFunnel.leftLower.getY());
        expandedFunnel.path.lineTo(position.getX(), position.getY());

        expandedFunnel.area = new Area(expandedFunnel.path);

        return expandedFunnel;
    }

    public Funnel rotate(double degrees) {
        Funnel rotatedFunnel = new Funnel(this);

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(degrees), position.getX(), position.getY());
        rotatedFunnel.path.transform(tx);
        //path = new Path2D.Double(tx.createTransformedShape(area));
        PathIterator it = rotatedFunnel.path.getPathIterator(null);
        double[] values = new double[2];
        it.next();
        it.currentSegment(values);
        rotatedFunnel.rightLower = new Point2D.Double(values[0], values[1]);
        System.out.println("rightLower:[" + values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);
        rotatedFunnel.rightUpper = new Point2D.Double(values[0], values[1]);
        System.out.println("rightUpper:[" + values[0] + ", " + values[1] + "]");
        if(rightUpper2 != null) {
            it.next();
            it.currentSegment(values);
            rotatedFunnel.rightUpper2 = new Point2D.Double(values[0], values[1]);
            System.out.println("rightUpper2:[" + values[0] + ", " + values[1] + "]");
        }
        it.next();
        it.currentSegment(values);
        rotatedFunnel.upper = new Point2D.Double(values[0], values[1]);
        System.out.println("upper:[" + values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);
        if(leftUpper2 != null) {
            it.next();
            it.currentSegment(values);
            rotatedFunnel.leftUpper2 = new Point2D.Double(values[0], values[1]);
            System.out.println("leftUpper2:[" + values[0] + ", " + values[1] + "]");
        }
        rotatedFunnel.leftUpper = new Point2D.Double(values[0], values[1]);
        System.out.println("leftUpper:[" + values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);
        rotatedFunnel.leftLower = new Point2D.Double(values[0], values[1]);
        System.out.println("leftLower:[" + values[0] + ", " + values[1] + "]");
        it.next();
        it.currentSegment(values);

        rotatedFunnel.area = new Area(rotatedFunnel.path);

        return rotatedFunnel;
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
