/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonBinary;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
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
/*    */ public class ByteArrayCodec
/*    */   implements Codec<byte[]>
/*    */ {
/*    */   public void encode(BsonWriter writer, byte[] value, EncoderContext encoderContext) {
/* 31 */     writer.writeBinaryData(new BsonBinary(value));
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] decode(BsonReader reader, DecoderContext decoderContext) {
/* 36 */     return reader.readBinaryData().getData();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<byte[]> getEncoderClass() {
/* 41 */     return byte[].class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\ByteArrayCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */