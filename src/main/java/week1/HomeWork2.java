package week1;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HomeWork2 {

    public static void main(String[] args) {
        try {
            byte [] buffer = Files.readAllBytes(Paths.get(new URI("file:///F://工作&学习//code//homework//src//main//java//week1//Hello.xlass")));
            for (int i=0;i<buffer.length;i++){
                buffer [i] = (byte) (255-buffer[i]);
            }
            Class clazz = new HelloClassLoader().findClass("Hello",buffer);
            clazz.getMethod("hello").invoke(clazz.newInstance());
        }catch (URISyntaxException | IOException  | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

   private static class HelloClassLoader extends ClassLoader{

        private Class<?> findClass(String name,byte [] buffer){
            HelloClassLoader helloClassLoader = new HelloClassLoader();
            return helloClassLoader.defineClass(name,buffer,0,buffer.length);
        }

    }

}
