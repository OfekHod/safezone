
import java.util.HashMap;

public class Weapon {
    String name;
    double lowerLineLength;
    double upperLineLength;
    double width;
    double height;
    public double lowerLineToHeightLineDegree;

    private static HashMap<String, Weapon> supportedWeapons = new HashMap<String, Weapon>() {{
        put("halulan", new Weapon("Halulan", 3.7, 10, 10.5, 14, 40, 1));
    }};

    public static Weapon getWeapon(String name) {
        return supportedWeapons.get(name);
    }

    public Weapon(String name, double lowerLineLength, double upperLineLength, double width, double height, double lowerLinetToHeightLineDegree, double sizeFactor) {
        this.name = name;
        this.lowerLineLength = lowerLineLength * sizeFactor;
        this.upperLineLength = upperLineLength * sizeFactor;
        this.width = width * sizeFactor;
        this.height = height * sizeFactor;
        this.lowerLineToHeightLineDegree = lowerLinetToHeightLineDegree;

    }

    public Weapon(Weapon weapon) {
        this.name = weapon.name;
        this.lowerLineLength = weapon.lowerLineLength;
        this.upperLineLength = weapon.upperLineLength;
        this.width = weapon.width;
        this.height = weapon.height;
        this.lowerLineToHeightLineDegree = weapon.lowerLineToHeightLineDegree;
    }

    public Weapon withFactor(double sizeFactor) {
        Weapon newWeapon = new Weapon(this);
        newWeapon.lowerLineLength = lowerLineLength * sizeFactor;
        newWeapon.upperLineLength = upperLineLength * sizeFactor;
        newWeapon.width = width * sizeFactor;
        newWeapon.height = height * sizeFactor;

        return newWeapon;
    }
}
