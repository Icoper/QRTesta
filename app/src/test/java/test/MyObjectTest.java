package test;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertEquals;

public class MyObjectTest {
    private static final String FILE_PATH = "c:\\Users\\user\\myObjects.txt";
    private MyObject objToSave = new MyObject(Integer.MAX_VALUE, "text", new MyObject2(Integer.MIN_VALUE, "_text"));

    private void saveObj() {
        // Write objects to file
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(FILE_PATH)))) {
            objToSave.writeExternal(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private MyObject readObjFromFile() {
        MyObject myObject = new MyObject();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(FILE_PATH)))) {

            myObject.readExternal(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return myObject;
    }

    @Test
    public void checkObjects() {
        saveObj();
        MyObject objFromStore = readObjFromFile();
        System.out.println(objFromStore.equals(objToSave));
        assertEquals(objToSave, objFromStore);
    }

}