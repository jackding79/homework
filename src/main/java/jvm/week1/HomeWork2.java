package jvm.week1;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;


public class HomeWork2 {

    public static void main(String[] args) {
        try {
            Class clazz = new HelloClassLoader().findClass("Hello");
            clazz.getMethod("hello").invoke(clazz.newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static class HelloClassLoader extends ClassLoader {

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try (InputStream is = this.getClass().getResourceAsStream("/Hello.xlass")
            ) {
                int length = is.available();
                byte [] buffer = new byte[length];
                is.read(buffer);
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = (byte) (255 - buffer[i]);
                }
                return defineClass(name, buffer, 0, length);
            } catch (IOException e) {
                throw new ClassNotFoundException("class not found");
            }
        }
    }

}
