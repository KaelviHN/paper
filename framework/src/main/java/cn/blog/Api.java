package cn.blog;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: anran.ma
 * @created: 2024/4/27
 * @description:
 **/
public class Api {
    public void createER() {
//        Configuration configuration = new Configuration();
//        configuration.configure();
//        MetadataSources metadata = new MetadataSources(
//                new StandardServiceRegistryBuilder()
//                        .applySettings(configuration.getProperties())
//                        .build());
//        metadata.addAnnotatedClassName()
//        String packageName = "cn.blog.model.pojo";
//
//        // 获取包路径
//        String packagePath = packageName.replace(".", "/");
        String packagePath = "C:\\Users\\Aprox\\Desktop\\MyBlog\\framework\\src\\main\\java\\cn\\blog\\model\\pojo";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            // 获取包的URL路径
            File packageDirectory = new File(classLoader.getResource(packagePath).getFile());
            // 扫描包下的所有类
            List<Class<?>> classes = scanPackage(packageDirectory);
            // 打印扫描结果
            for (Class<?> clazz : classes) {
                System.out.println(clazz.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Class<?>> scanPackage(File packageDirectory) {
        List<Class<?>> classes = new ArrayList<>();
        if (packageDirectory.exists() && packageDirectory.isDirectory()) {
            for (File file : packageDirectory.listFiles()) {
                if (file.getName().endsWith(".class")) {
                    try {
                        // 加载类
                        String className = file.getName().substring(0, file.getName().length() - 6);
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }

    public static void main(String[] args) {
        new Api().createER();
    }
}
