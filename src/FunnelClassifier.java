import models.Funnel;
import models.Weapon;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class FunnelClassifier {

    public static Graphics2D g = null;

    public static Boolean isFunnelAllowed(Point2D globalPos, String weaponName, double rightAngle, double leftAngle) {
        Funnel funnel = new Funnel(globalPos, Weapon.getWeapon(weaponName))
                .expand(rightAngle, leftAngle)
                .rotate(0);

        Line2D[] firePoly = getOuterFirePoly(globalPos);

        if (g != null) {
            g.draw(new Area(linesToPath(firePoly)));
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

    private static Line2D[] getOuterFirePoly(Point2D globalPos) {
        Line2D[] lines = {
                new Line2D.Double(10, 10, 50, 25),
                new Line2D.Double(50, 25, 500, 65),
                new Line2D.Double(500, 65, 550, 380),
                new Line2D.Double(550, 380, 105, 600),
                new Line2D.Double(105, 600, 10, 10)};
        return lines;
    }

    private static Path2D linesToPath(Line2D[] lines) {
        Path2D path = new Path2D.Double();
        path.moveTo(lines[0].getX1(), lines[0].getY1());
        for (Line2D line : lines) {
            path.lineTo(line.getX2(), line.getY2());
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
