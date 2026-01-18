/*    */ package joptsimple.util;
/*    */ 
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum PathProperties
/*    */ {
/* 12 */   FILE_EXISTING("file.existing")
/*    */   {
/*    */     boolean accept(Path path) {
/* 15 */       return Files.isRegularFile(path, new java.nio.file.LinkOption[0]);
/*    */     }
/*    */   },
/* 18 */   DIRECTORY_EXISTING("directory.existing")
/*    */   {
/*    */     boolean accept(Path path) {
/* 21 */       return Files.isDirectory(path, new java.nio.file.LinkOption[0]);
/*    */     }
/*    */   },
/* 24 */   NOT_EXISTING("file.not.existing")
/*    */   {
/*    */     boolean accept(Path path) {
/* 27 */       return Files.notExists(path, new java.nio.file.LinkOption[0]);
/*    */     }
/*    */   },
/* 30 */   FILE_OVERWRITABLE("file.overwritable")
/*    */   {
/*    */     boolean accept(Path path) {
/* 33 */       return (FILE_EXISTING.accept(path) && WRITABLE.accept(path));
/*    */     }
/*    */   },
/* 36 */   READABLE("file.readable")
/*    */   {
/*    */     boolean accept(Path path) {
/* 39 */       return Files.isReadable(path);
/*    */     }
/*    */   },
/* 42 */   WRITABLE("file.writable")
/*    */   {
/*    */     boolean accept(Path path) {
/* 45 */       return Files.isWritable(path);
/*    */     }
/*    */   };
/*    */   
/*    */   private final String messageKey;
/*    */   
/*    */   PathProperties(String messageKey) {
/* 52 */     this.messageKey = messageKey;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   String getMessageKey() {
/* 58 */     return this.messageKey;
/*    */   }
/*    */   
/*    */   abstract boolean accept(Path paramPath);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimpl\\util\PathProperties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */