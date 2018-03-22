import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class FunnelClassifier {

    public static Graphics2D g = null;

    public static Boolean isFunnelAllowed(Point2D globalPos, String weaponName, double rightAngle, double leftAngle) {
        Weapon weapon = Weapon.getWeapon(weaponName);
        Funnel funnel = new Funnel(globalPos, weapon)
                .rotate(angleToRotate(rightAngle, leftAngle));

        double angleDiff = Math.abs(rightAngle - leftAngle);
        if (angleDiff > weapon.lowerLineToHeightLineDegree * 2) {
            System.out.println("expanding degrees each side: " + angleDiff / 2);
            funnel = funnel.expand(angleDiff / 2, angleDiff / 2);
        }


        Line2D[] firePoly = getOuterFirePoly(globalPos);

        if (g != null) {
            g.draw(new Area(linesToPath(firePoly, 1, 0)));
            funnel.g = g;
            funnel.draw();
        } else {
            System.out.println("Cant draw! initialize Graphics2D insize Funnel in order to draw");
        }

        for (Line2D line : firePoly) {
            if (line.intersectsLine(funnel.getLineHeight()) ||
                    line.intersectsLine(funnel.getLineWidth()) ||
                    line.intersectsLine(funnel.getLineRightLower()) ||
                    line.intersectsLine(funnel.getLineRightUpper()) ||
                    line.intersectsLine(funnel.getLineLeftLower()) ||
                    line.intersectsLine(funnel.getLineLeftUpper()) ||
                    (funnel.getIsExpanded() && (line.intersectsLine(funnel.getLineRightUpper2()) ||
                            line.intersectsLine(funnel.getLineLeftUpper2())))) {
                return false;
            }
        }
        return true;
    }

    private static double angleToRotate(double rightAngle, double leftAngle) {
        double avg = (rightAngle + leftAngle) / 2;
        double diff = Math.abs(rightAngle - leftAngle);
        if (diff > 180) {
            diff = 360 - diff;
        }
        if (avg - Math.abs(avg - Math.max(rightAngle, leftAngle)) == Math.min(rightAngle, leftAngle)) {
            double angle = Math.min(rightAngle, leftAngle) + diff / 2;
            System.out.println("angle: " + angle);
            return angle;
        } else {
            double angle = Math.max(rightAngle, leftAngle) + diff / 2;
            System.out.println("angle: " + angle);
            return angle;
        }
    }

    private static Line2D[] getOuterFirePoly(Point2D globalPos) {
        Line2D[] lines = {
                new Line2D.Double(31.266038, 34.824224, 31.255561, 34.809837),
                new Line2D.Double(31.255561, 34.809837, 31.269140, 34.796031),
                new Line2D.Double(31.269140, 34.796031, 31.273871, 34.815633),
                new Line2D.Double(31.273871, 34.815633, 31.266038, 34.824224)};
        return lines;
    }

    private static double fixX(double x) {
        //return x * 30000 - 937500;
        return x;
    }

    private static double fixY(double y) {
        //return y * 30000     - 1044000;
        return y;
    }

    private static Path2D linesToPath(Line2D[] lines, double factorSize, double diffSize) {
        Path2D path = new Path2D.Double();
        path.moveTo(lines[0].getX1() * factorSize + diffSize, lines[0].getY1() * factorSize + diffSize);
        for (Line2D line : lines) {
            path.lineTo(line.getX2() * factorSize + diffSize, line.getY2() * factorSize + diffSize);
        }
        return path;
    }

    public static double angleBetweenTwoPointsWithFixedPoint(double point1X, double point1Y,
                                                             double point2X, double point2Y,
                                                             double fixedX, double fixedY) {
        double angle1 = Math.atan2(point1Y - fixedY, point1X - fixedX);
        double angle2 = Math.atan2(point2Y - fixedY, point2X - fixedX);

        return angle1 - angle2;
    }
}
