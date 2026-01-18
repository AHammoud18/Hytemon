/*    */ package org.bson;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Arrays;
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
/*    */ public class LazyBSONDecoder
/*    */   implements BSONDecoder
/*    */ {
/*    */   private static final int BYTES_IN_INTEGER = 4;
/*    */   
/*    */   public BSONObject readObject(byte[] bytes) {
/* 32 */     BSONCallback bsonCallback = new LazyBSONCallback();
/* 33 */     decode(bytes, bsonCallback);
/* 34 */     return (BSONObject)bsonCallback.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public BSONObject readObject(InputStream in) throws IOException {
/* 39 */     BSONCallback bsonCallback = new LazyBSONCallback();
/* 40 */     decode(in, bsonCallback);
/* 41 */     return (BSONObject)bsonCallback.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public int decode(byte[] bytes, BSONCallback callback) {
/*    */     try {
/* 47 */       return decode(new ByteArrayInputStream(bytes), callback);
/* 48 */     } catch (IOException e) {
/* 49 */       throw new BSONException("Invalid bytes received", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int decode(InputStream in, BSONCallback callback) throws IOException {
/* 55 */     byte[] documentSizeBuffer = new byte[4];
/* 56 */     int documentSize = Bits.readInt(in, documentSizeBuffer);
/* 57 */     byte[] documentBytes = Arrays.copyOf(documentSizeBuffer, documentSize);
/* 58 */     Bits.readFully(in, documentBytes, 4, documentSize - 4);
/*    */ 
/*    */     
/* 61 */     callback.gotBinary(null, (byte)0, documentBytes);
/* 62 */     return documentSize;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\LazyBSONDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */