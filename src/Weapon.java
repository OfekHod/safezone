import java.util.HashMap;

public class Weapon {
    String name;
    double lowerLineLength;
    double upperLineLength;
    double width;
    double height;
    double lowerLineToHeightLineDegree;

    static HashMap<String, Weapon> supportedWeapons = new HashMap<String, Weapon>() {{
        put("halulan", new Weapon("Halulan", 3.7, 10, 10.5, 14, 40, 0.01));
    }};

    public Weapon(String name, double lowerLineLength, double upperLineLength, double width, double height, double loweLinetToHeightLineDegree, double sizeFactor) {
        this.lowerLineLength = lowerLineLength * sizeFactor;
        this.upperLineLength = upperLineLength * sizeFactor;
        this.width = width * sizeFactor;
        this.height = height * sizeFactor;
        this.lowerLineToHeightLineDegree = loweLinetToHeightLineDegree;

    }
}
