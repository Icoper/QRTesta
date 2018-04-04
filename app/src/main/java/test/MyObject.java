package test;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class MyObject implements Externalizable {
    private int number;
    private String text;
    private MyObject2 myObject2;

    public MyObject() {
    }

    MyObject(int number, String text, MyObject2 myObject2) {
        this.number = number;
        this.text = text;
        this.myObject2 = myObject2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyObject myObject = (MyObject) o;

        if (number != myObject.number) return false;
        if (text != null ? !text.equals(myObject.text) : myObject.text != null) return false;
        return myObject2 != null ? myObject2.equals(myObject.myObject2) : myObject.myObject2 == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (myObject2 != null ? myObject2.hashCode() : 0);
        return result;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(number);
        out.writeUTF(text);
        out.writeObject(myObject2);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.number = in.readInt();
        this.text = in.readUTF();
        this.myObject2 = (MyObject2) in.readObject();
    }
}
