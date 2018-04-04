package test;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class MyObject2 implements Externalizable {
    private int number;
    private String text;

    public MyObject2() {
    }

    MyObject2(int number, String text) {
        this.number = number;
        this.text = text;
    }

    int getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyObject2 myObject2 = (MyObject2) o;

        if (number != myObject2.number) return false;
        return text != null ? text.equals(myObject2.text) : myObject2.text == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(number);
        out.writeUTF(text);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.number = in.readInt();
        this.text = in.readUTF();
    }
}
