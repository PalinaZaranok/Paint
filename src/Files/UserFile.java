package Files;

import Shapes.Shape;
import utils.Canvas;

import java.io.*;
import java.util.List;

public class UserFile implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public void saveToFile(String fileName, Object shapes) {
        try {
            FileOutputStream fileStream = new FileOutputStream(fileName);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(shapes);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    public List<Shape> loadFromFile(String fileName) {
        List<Shape> shapes = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            shapes =(List<Shape>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return shapes;
    }
}
