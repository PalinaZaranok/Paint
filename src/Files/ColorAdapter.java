package Files;

import com.google.gson.*;

import java.awt.Color;
import java.lang.reflect.Type;

public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("r", src.getRed());
        obj.addProperty("g", src.getGreen());
        obj.addProperty("b", src.getBlue());
        obj.addProperty("a", src.getAlpha());
        return obj;
    }

    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        int r = obj.get("r").getAsInt();
        int g = obj.get("g").getAsInt();
        int b = obj.get("b").getAsInt();
        int a = obj.get("a").getAsInt();
        return new Color(r, g, b, a);
    }
}
