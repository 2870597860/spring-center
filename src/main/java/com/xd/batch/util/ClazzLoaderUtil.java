package com.xd.batch.util;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.util.Objects.isNull;

public class ClazzLoaderUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ClazzLoaderUtil.class);

    private final static String resourcePattern =  "**/*.class";
    private final static String startFlag = "target/classes/";
    private final static String endStart = ".class";

    @Nullable
    private static ResourcePatternResolver resourcePatternResolver;

    @Nullable
    private static Environment environment;

    public static List<Class> getAllClassByPackageName(String[] packageNameArr){
        if(isNull(packageNameArr) || packageNameArr.length == 0){
            LOG.info("packageNameArr is empty!");
            return Collections.EMPTY_LIST;
        }
        List<Class> classList = new ArrayList<>();
        for (String packageName : packageNameArr) {
            List<Class> allClassByPackageName = getAllClassByPackageName(packageName);
            if(CollectionUtil.isNotEmpty(allClassByPackageName)){
                classList.addAll(allClassByPackageName);
            }
        }
        return classList;
    }

    /**
     * 获取指定包以及子包路径下的所有class
     * @param packageName
     * @return
     */
    public static List<Class> getAllClassByPackageName(String packageName){
        List<Class> classList = new ArrayList<>();
        LOG.info("packageName to search："+ packageName);
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(packageName) + '/' + resourcePattern;

            Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
            for (Resource resource : resources) {
                String path = resource.getURL().getPath();
                Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(subClassPackagePath(path));
                classList.add(aClass);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classList;
    }

    /**
     * 获取实现指定class接口的所有class
     * @param clazz
     * @return
     */
    public static ArrayList<Class> getAllClassByInterface(Class<com.xd.batch.cache.ICacheDefinition> clazz) {
        ArrayList<Class> list = new ArrayList<>();
        // 判断是否是一个接口
        if (clazz.isInterface()) {
            try {
                ArrayList<Class> allClass = getAllClass(clazz.getPackage().getName());
                /**
                 * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
                 */
                for (int i = 0; i < allClass.size(); i++) {
                    /**
                     * 判断是不是同一个接口
                     */
                    // isAssignableFrom:判定此 Class 对象所表示的类或接口与指定的 Class
                    // 参数所表示的类或接口是否相同，或是否是其超类或超接口
                    if (clazz.isAssignableFrom(allClass.get(i))) {
                        if (!clazz.equals(allClass.get(i))) {
                            // 自身并不加进去
                            list.add(allClass.get(i));
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error("出现异常{}",e.getMessage());
                throw new RuntimeException("出现异常"+e.getMessage());
            }
        }
        LOG.info("class list size :"+list.size());
        return list;
    }


    /**
     * 获取某包所在根路径的所有clazz
     * @param packageNamePath 包名
     * @return 类的完整名称
     */
    public static ArrayList<Class> getAllClass(String packageNamePath) throws IOException {
        List<String> classNameList =  getClassName(packageNamePath);
        ArrayList<Class> list = new ArrayList<>();
        for(String className : classNameList){
            try {
                list.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                LOG.error("load class from name failed:"+className+e.getMessage());
                throw new RuntimeException("load class from name failed:"+className+e.getMessage());
            }
        }
        LOG.info("find list size :"+list.size());
        return list;
    }

    /**
     * 获取某包所在根路径的所有类路径
     * @param packageName 包名
     * @return 类的完整名称
     */
    public static List<String> getClassName(String packageName) {

        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            LOG.debug("file type : " + type);
            if (type.equals("file")) {
                String fileSearchPath = url.getPath();
                LOG.debug("fileSearchPath: "+fileSearchPath);
                fileSearchPath = fileSearchPath.substring(0,fileSearchPath.indexOf("/classes")+"/classes".length());
                LOG.debug("fileSearchPath: "+fileSearchPath);
                fileNames = getClassNameByFile(fileSearchPath);
            } else if (type.equals("jar")) {
                try{
                    JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    fileNames = getClassNameByJar(jarFile,packagePath);
                }catch (java.io.IOException e){
                    throw new RuntimeException("open Package URL failed："+e.getMessage());
                }

            }else{
                throw new RuntimeException("file system not support! cannot load MsgProcessor！");
            }
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     * @param filePath 文件路径
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassNameByFile(childFile.getPath()));
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(JarFile jarFile , String packagePath) {
        List<String> myClassName = new ArrayList<String>();
        try {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                //LOG.info("entrys jarfile:"+entryName);
                if (entryName.endsWith(".class")) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                    //LOG.debug("Find Class :"+entryName);
                }
            }
        } catch (Exception e) {
            LOG.error("发生异常:"+e.getMessage());
            throw new RuntimeException("发生异常:"+e.getMessage());
        }
        return myClassName;
    }

    public static ResourcePatternResolver getResourcePatternResolver() {
        if (resourcePatternResolver == null) {
            resourcePatternResolver = new PathMatchingResourcePatternResolver();
        }
        return resourcePatternResolver;
    }

    public static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(getEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    private static String subClassPackagePath(String path) throws IOException {
        int start = path.indexOf(startFlag);
        int end = path.indexOf(endStart);
        return  StringUtils.substring(path, start + startFlag.length(), end).replaceAll("/",".");

    }

    public static Environment getEnvironment() {
        if (environment == null) {
            environment = new StandardEnvironment();
        }
        return environment;
    }
}
