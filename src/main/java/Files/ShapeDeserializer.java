package Files;

import Shapes.Shape;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ShapeDeserializer {
    public static List<Shape> load(File filename) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Shape.class, new ShapeAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .create();

        Type shapeListType = new TypeToken<List<Shape>>(){}.getType();
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, shapeListType);
        }
    }
}
