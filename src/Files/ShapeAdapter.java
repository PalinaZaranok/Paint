package Files;

import Shapes.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ShapeAdapter implements JsonSerializer<Shape>, JsonDeserializer<Shape> {
    @Override
    public JsonElement serialize(Shape src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", src.getClass().getSimpleName());
        obj.add("data", context.serialize(src));
        return obj;
    }


    @Override
    public Shape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String type = obj.get("type").getAsString();
        JsonElement data = obj.get("data");


        switch (type) {
            case "Line": return context.deserialize(data, Line.class);
            case "BrokenLine": return context.deserialize(data, BrokenLine.class);
            case "Circle": return  context.deserialize(data, Circle.class);
            case "Polygon": return context.deserialize(data, Polygon.class);
            case "Rectangle": return context.deserialize(data, Rectangle.class);
            default: throw new JsonParseException("Unknown shape type: " + type);
        }
    }
}
