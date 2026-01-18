/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import com.google.crypto.tink.proto.EncryptedKeyset;
/*    */ import com.google.crypto.tink.proto.Keyset;
/*    */ import com.google.errorprone.annotations.InlineMe;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public final class BinaryKeysetWriter
/*    */   implements KeysetWriter
/*    */ {
/*    */   private final OutputStream outputStream;
/*    */   
/*    */   private BinaryKeysetWriter(OutputStream stream) {
/* 37 */     this.outputStream = stream;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static KeysetWriter withOutputStream(OutputStream stream) {
/* 46 */     return new BinaryKeysetWriter(stream);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   @InlineMe(replacement = "BinaryKeysetWriter.withOutputStream(new FileOutputStream(file))", imports = {"com.google.crypto.tink.BinaryKeysetWriter", "java.io.FileOutputStream"})
/*    */   public static KeysetWriter withFile(File file) throws IOException {
/* 59 */     return withOutputStream(new FileOutputStream(file));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(Keyset keyset) throws IOException {
/*    */     try {
/* 65 */       keyset.writeTo(this.outputStream);
/*    */     } finally {
/* 67 */       this.outputStream.close();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(EncryptedKeyset keyset) throws IOException {
/*    */     try {
/* 74 */       keyset.toBuilder().clearKeysetInfo().build().writeTo(this.outputStream);
/*    */     } finally {
/* 76 */       this.outputStream.close();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\BinaryKeysetWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */