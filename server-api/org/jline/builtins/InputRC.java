/*    */ package org.jline.builtins;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.Reader;
/*    */ import java.net.URL;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import org.jline.reader.LineReader;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InputRC
/*    */ {
/*    */   public static void configure(LineReader reader, URL url) throws IOException {
/* 39 */     org.jline.reader.impl.InputRC.configure(reader, url);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void configure(LineReader reader, InputStream is) throws IOException {
/* 50 */     org.jline.reader.impl.InputRC.configure(reader, is);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void configure(LineReader reader, Reader r) throws IOException {
/* 61 */     org.jline.reader.impl.InputRC.configure(reader, r);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void configure(LineReader lineReader, Path path) throws IOException {
/* 72 */     if (Files.exists(path, new java.nio.file.LinkOption[0]) && Files.isRegularFile(path, new java.nio.file.LinkOption[0]) && Files.isReadable(path)) {
/* 73 */       Reader reader = Files.newBufferedReader(path); try {
/* 74 */         configure(lineReader, reader);
/* 75 */         if (reader != null) reader.close(); 
/*    */       } catch (Throwable throwable) {
/*    */         if (reader != null)
/*    */           try {
/*    */             reader.close();
/*    */           } catch (Throwable throwable1) {
/*    */             throwable.addSuppressed(throwable1);
/*    */           }  
/*    */         throw throwable;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void configure(LineReader lineReader) throws IOException {
/* 89 */     String userHome = System.getProperty("user.home");
/* 90 */     if (userHome != null) {
/* 91 */       configure(lineReader, Paths.get(userHome, new String[] { ".inputrc" }));
/*    */     }
/* 93 */     configure(lineReader, Paths.get("/etc/inputrc", new String[0]));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\InputRC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */