/*    */ package com.hypixel.hytale;
/*    */ 
/*    */ import com.hypixel.hytale.plugin.early.EarlyPluginLoader;
/*    */ import com.hypixel.hytale.plugin.early.TransformingClassLoader;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Locale;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Main
/*    */ {
/*    */   public static void main(String[] args) {
/* 26 */     Locale.setDefault(Locale.ENGLISH);
/*    */ 
/*    */     
/* 29 */     System.setProperty("java.awt.headless", "true");
/*    */ 
/*    */     
/* 32 */     System.setProperty("file.encoding", "UTF-8");
/*    */ 
/*    */ 
/*    */     
/* 36 */     EarlyPluginLoader.loadEarlyPlugins(args);
/*    */     
/* 38 */     if (EarlyPluginLoader.hasTransformers()) {
/*    */       
/* 40 */       launchWithTransformingClassLoader(args);
/*    */     } else {
/*    */       
/* 43 */       LateMain.lateMain(args);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void launchWithTransformingClassLoader(@Nonnull String[] args) {
/*    */     try {
/* 49 */       URL[] urls = getClasspathUrls();
/* 50 */       ClassLoader appClassLoader = Main.class.getClassLoader();
/*    */ 
/*    */ 
/*    */       
/* 54 */       TransformingClassLoader transformingClassLoader = new TransformingClassLoader(urls, EarlyPluginLoader.getTransformers(), appClassLoader.getParent(), appClassLoader);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 59 */       Thread.currentThread().setContextClassLoader((ClassLoader)transformingClassLoader);
/*    */ 
/*    */       
/* 62 */       Class<?> lateMainClass = transformingClassLoader.loadClass("com.hypixel.hytale.LateMain");
/* 63 */       Method mainMethod = lateMainClass.getMethod("lateMain", new Class[] { String[].class });
/* 64 */       mainMethod.invoke(null, new Object[] { args });
/* 65 */     } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException e) {
/* 66 */       throw new RuntimeException("Failed to launch with transforming classloader", e);
/* 67 */     } catch (InvocationTargetException e) {
/* 68 */       Throwable cause = e.getCause();
/* 69 */       if (cause instanceof RuntimeException) { RuntimeException re = (RuntimeException)cause; throw re; }
/* 70 */        if (cause instanceof Error) { Error err = (Error)cause; throw err; }
/* 71 */        throw new RuntimeException("LateMain.lateMain() threw an exception", cause);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static URL[] getClasspathUrls() {
/* 76 */     ClassLoader classLoader = Main.class.getClassLoader();
/* 77 */     if (classLoader instanceof URLClassLoader) { URLClassLoader urlClassLoader = (URLClassLoader)classLoader;
/* 78 */       return urlClassLoader.getURLs(); }
/*    */ 
/*    */ 
/*    */     
/* 82 */     ObjectArrayList<URL> urls = new ObjectArrayList();
/*    */     
/* 84 */     String classpath = System.getProperty("java.class.path");
/* 85 */     if (classpath != null && !classpath.isEmpty()) {
/* 86 */       for (String pathStr : classpath.split(System.getProperty("path.separator"))) {
/*    */         try {
/* 88 */           Path path = Path.of(pathStr, new String[0]);
/* 89 */           if (Files.exists(path, new java.nio.file.LinkOption[0])) {
/* 90 */             urls.add(path.toUri().toURL());
/*    */           }
/* 92 */         } catch (Exception e) {
/* 93 */           System.err.println("[EarlyPlugin] Failed to parse classpath entry: " + pathStr);
/*    */         } 
/*    */       } 
/*    */     }
/* 97 */     return (URL[])urls.toArray(x$0 -> new URL[x$0]);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\Main.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */