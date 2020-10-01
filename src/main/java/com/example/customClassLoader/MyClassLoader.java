package com.example.customClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/31 上午7:03
 */
public class MyClassLoader extends ClassLoader {

    private final static Path DEFAULT_CLASS_DIR = Paths.get("classloader1");

    private final Path classDir;

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = this.readClassBytes(name);

        // 没读到数据抛出异常
        if (null == classBytes && classBytes.length == 0) {
            throw new ClassNotFoundException("Can not load the class " + name);
        }

        // 调用defineClass方法定义class
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    //////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return "My ClassLoader";
    }

    public MyClassLoader() {
        super();
        classDir = DEFAULT_CLASS_DIR;
    }

    public MyClassLoader(String classDir) {
        super();
        this.classDir = Paths.get(classDir);
    }

    public MyClassLoader(String classDir, ClassLoader parent) {
        super(parent);
        this.classDir = Paths.get(classDir);
    }

    private byte[] readClassBytes(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        Path classFullPath = classDir.resolve(Paths.get(classPath + ".class"));
        if (!classFullPath.toFile().exists()) {
            throw new ClassNotFoundException("The class " + name + " not found.");
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Files.copy(classFullPath, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new ClassNotFoundException("load the class " + name + " occur error", e);
        }
    }

}
