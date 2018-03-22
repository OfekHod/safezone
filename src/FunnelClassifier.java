import com.sun.org.omg.CORBA.ExceptionDescription;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

import java.awt.geom.Point2D;
import java.util.*;

public class FunnelClassifier {

    public static Boolean isFunnelAllowed(Point2D globalPos, String weaponName, double rightAngle, double leftAngle) {
        Funnel funnel = new Funnel(globalPos, Weapon.supportedWeapons.get(weaponName));


        return false;
    }
}
