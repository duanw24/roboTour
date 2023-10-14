import framework.map.Map;
import framework.map.mapUtils;

public class Main {
    public static void main(String[] args) {
        /*
        0 = empty
        1 = gridline
        2 = wood
        3 = boundary
         */
        String t="1000:0000:0000:0000.0000:0000:0000:0000.0000:0000:0000:0000.0000:0000:0000:0000";
        String[] m = t.split("\\.");
        mapUtils.readMap(t);
        System.out.println(Map.getMap().toString());
    }
}