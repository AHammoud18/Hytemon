/*    */ package org.bson;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import org.bson.io.BsonInput;
/*    */ import org.bson.io.ByteBufferBsonInput;
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
/*    */ public class BasicBSONDecoder
/*    */   implements BSONDecoder
/*    */ {
/*    */   public BSONObject readObject(byte[] bytes) {
/* 32 */     BSONCallback bsonCallback = new BasicBSONCallback();
/* 33 */     decode(bytes, bsonCallback);
/* 34 */     return (BSONObject)bsonCallback.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public BSONObject readObject(InputStream in) throws IOException {
/* 39 */     return readObject(readFully(in));
/*    */   }
/*    */ 
/*    */   
/*    */   public int decode(byte[] bytes, BSONCallback callback) {
/* 44 */     BsonBinaryReader reader = new BsonBinaryReader((BsonInput)new ByteBufferBsonInput(new ByteBufNIO(ByteBuffer.wrap(bytes))));
/*    */     try {
/* 46 */       BsonWriter writer = new BSONCallbackAdapter(new BsonWriterSettings(), callback);
/* 47 */       writer.pipe(reader);
/* 48 */       return reader.getBsonInput().getPosition();
/*    */     } finally {
/* 50 */       reader.close();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int decode(InputStream in, BSONCallback callback) throws IOException {
/* 56 */     return decode(readFully(in), callback);
/*    */   }
/*    */   
/*    */   private byte[] readFully(InputStream input) throws IOException {
/* 60 */     byte[] sizeBytes = new byte[4];
/* 61 */     Bits.readFully(input, sizeBytes);
/* 62 */     int size = Bits.readInt(sizeBytes);
/*    */     
/* 64 */     byte[] buffer = new byte[size];
/* 65 */     System.arraycopy(sizeBytes, 0, buffer, 0, 4);
/* 66 */     Bits.readFully(input, buffer, 4, size - 4);
/* 67 */     return buffer;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BasicBSONDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */