package org.cgspine.rxjavademo;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by cgine on 16/2/22.
 */
public class Util {
    private Util(){}
    public static void close(Closeable obj){
        try {
            obj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
