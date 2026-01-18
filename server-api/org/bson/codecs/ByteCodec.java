/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonInvalidOperationException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ByteCodec
/*    */   implements Codec<Byte>
/*    */ {
/*    */   public void encode(BsonWriter writer, Byte value, EncoderContext encoderContext) {
/* 35 */     writer.writeInt32(value.byteValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Byte decode(BsonReader reader, DecoderContext decoderContext) {
/* 40 */     int value = NumberCodecHelper.decodeInt(reader);
/* 41 */     if (value < -128 || value > 127) {
/* 42 */       throw new BsonInvalidOperationException(String.format("%s can not be converted into a Byte.", new Object[] { Integer.valueOf(value) }));
/*    */     }
/* 44 */     return Byte.valueOf((byte)value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Byte> getEncoderClass() {
/* 49 */     return Byte.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\ByteCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */