/*    */ package org.jline.builtins;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.net.URL;
/*    */ import java.nio.file.FileSystem;
/*    */ import java.nio.file.FileSystems;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClasspathResourceUtil
/*    */ {
/*    */   public static Path getResourcePath(String name) throws IOException, URISyntaxException {
/* 36 */     return getResourcePath(name, ClasspathResourceUtil.class.getClassLoader());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Path getResourcePath(String name, Class<?> clazz) throws IOException, URISyntaxException {
/* 49 */     URL resource = clazz.getResource(name);
/* 50 */     if (resource == null) {
/* 51 */       throw new IOException("Resource not found: " + name);
/*    */     }
/* 53 */     return getResourcePath(resource);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Path getResourcePath(String name, ClassLoader classLoader) throws IOException, URISyntaxException {
/* 66 */     URL resource = classLoader.getResource(name);
/* 67 */     if (resource == null) {
/* 68 */       throw new IOException("Resource not found: " + name);
/*    */     }
/* 70 */     return getResourcePath(resource);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Path getResourcePath(URL resource) throws IOException, URISyntaxException {
/* 82 */     URI uri = resource.toURI();
/* 83 */     String scheme = uri.getScheme();
/*    */     
/* 85 */     if (scheme.equals("file")) {
/* 86 */       return Paths.get(uri);
/*    */     }
/*    */     
/* 89 */     if (!scheme.equals("jar")) {
/* 90 */       throw new IllegalArgumentException("Cannot convert to Path: " + uri);
/*    */     }
/*    */     
/* 93 */     String s = uri.toString();
/* 94 */     int separator = s.indexOf("!/");
/* 95 */     String entryName = s.substring(separator + 2);
/* 96 */     URI fileURI = URI.create(s.substring(0, separator));
/*    */     
/* 98 */     FileSystem fs = FileSystems.newFileSystem(fileURI, new HashMap<>());
/* 99 */     return fs.getPath(entryName, new String[0]);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\ClasspathResourceUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */