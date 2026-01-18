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
/*    */ public class ShortCodec
/*    */   implements Codec<Short>
/*    */ {
/*    */   public void encode(BsonWriter writer, Short value, EncoderContext encoderContext) {
/* 35 */     writer.writeInt32(value.shortValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Short decode(BsonReader reader, DecoderContext decoderContext) {
/* 40 */     int value = NumberCodecHelper.decodeInt(reader);
/* 41 */     if (value < -32768 || value > 32767) {
/* 42 */       throw new BsonInvalidOperationException(String.format("%s can not be converted into a Short.", new Object[] { Integer.valueOf(value) }));
/*    */     }
/* 44 */     return Short.valueOf((short)value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Short> getEncoderClass() {
/* 49 */     return Short.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\ShortCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */