package Files;

import Shapes.Circle;
import Shapes.Shape;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ShapeSerializer {
    public static void save(List<Shape> shapes, File filename) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Shape.class, new ShapeAdapter())
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .setPrettyPrinting()
                .create();

        Type shapeListType = new TypeToken<List<Shape>>(){}.getType();

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(shapes, shapeListType, writer);
        }
    }
}
