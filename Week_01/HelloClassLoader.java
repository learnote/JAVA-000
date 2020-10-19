package com.lauhom.code;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>
 *     自定义类加载器 ，加载d://Hello.xlass文件
 * </p>
 *
 * @author liuhong
 * @date 2020/10/19
 **/
public class HelloClassLoader extends ClassLoader{

    private String classPath;

    HelloClassLoader(String classPath) {
        this.classPath = classPath;
    }
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        final Class<?> aClass = new HelloClassLoader("D://").findClass("Hello");
        final Object o = aClass.newInstance();
        final Method classMethod = aClass.getMethod("hello");
        classMethod.invoke(o);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(classPath,name + ".xlass");
        try (FileInputStream inputStream = new FileInputStream(file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            int len = 0;
            byte b = 0;
            while ((len = inputStream.read()) != -1) {
                b = (byte) (255 - len);
                outputStream.write(b);
            }

            final byte[] bytes = outputStream.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);

    }
}
