package utills;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Random;


public class Utils {
    private static Random r = new Random();
    private static int min = 50;
    private static int max = 200;

    private static JsonParser parser = new JsonParser();

    public static int getNextRandom() {
        return r.nextInt((max - min) + 1) + min;
    }

    public static long getBricks(String response) {
        JsonElement je = parser.parse(response);
        return je.getAsJsonObject().get("bricks").getAsLong();
    }

    public static int getOrderId(String response) {
        JsonElement je = parser.parse(response);
        return je.getAsJsonObject().get("id").getAsInt();
    }
}
