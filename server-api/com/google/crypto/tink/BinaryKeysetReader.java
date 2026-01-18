/*    */ package com.google.crypto.tink;
/*    */ 
/*    */ import com.google.crypto.tink.proto.EncryptedKeyset;
/*    */ import com.google.crypto.tink.proto.Keyset;
/*    */ import com.google.errorprone.annotations.InlineMe;
/*    */ import com.google.protobuf.ExtensionRegistryLite;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BinaryKeysetReader
/*    */   implements KeysetReader
/*    */ {
/*    */   private final InputStream inputStream;
/*    */   
/*    */   public static KeysetReader withInputStream(InputStream stream) {
/* 46 */     return new BinaryKeysetReader(stream);
/*    */   }
/*    */ 
/*    */   
/*    */   public static KeysetReader withBytes(byte[] bytes) {
/* 51 */     return new BinaryKeysetReader(new ByteArrayInputStream(bytes));
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
/*    */   
/*    */   @Deprecated
/*    */   @InlineMe(replacement = "BinaryKeysetReader.withInputStream(new FileInputStream(file))", imports = {"com.google.crypto.tink.BinaryKeysetReader", "java.io.FileInputStream"})
/*    */   public static KeysetReader withFile(File file) throws IOException {
/* 67 */     return withInputStream(new FileInputStream(file));
/*    */   }
/*    */   
/*    */   private BinaryKeysetReader(InputStream stream) {
/* 71 */     this.inputStream = stream;
/*    */   }
/*    */ 
/*    */   
/*    */   public Keyset read() throws IOException {
/*    */     try {
/* 77 */       return Keyset.parseFrom(this.inputStream, ExtensionRegistryLite.getEmptyRegistry());
/*    */     } finally {
/* 79 */       this.inputStream.close();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public EncryptedKeyset readEncrypted() throws IOException {
/*    */     try {
/* 86 */       return EncryptedKeyset.parseFrom(this.inputStream, ExtensionRegistryLite.getEmptyRegistry());
/*    */     } finally {
/* 88 */       this.inputStream.close();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\crypto\tink\BinaryKeysetReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */