/*    */ package io.netty.handler.codec.http.multipart;
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
/*    */ final class FileUploadUtil
/*    */ {
/*    */   static int hashCode(FileUpload upload) {
/* 23 */     return upload.getName().hashCode();
/*    */   }
/*    */   
/*    */   static boolean equals(FileUpload upload1, FileUpload upload2) {
/* 27 */     return upload1.getName().equalsIgnoreCase(upload2.getName());
/*    */   }
/*    */   
/*    */   static int compareTo(FileUpload upload1, FileUpload upload2) {
/* 31 */     return upload1.getName().compareToIgnoreCase(upload2.getName());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\http\multipart\FileUploadUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */