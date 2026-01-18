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
/*    */ public class FloatCodec
/*    */   implements Codec<Float>
/*    */ {
/*    */   public void encode(BsonWriter writer, Float value, EncoderContext encoderContext) {
/* 35 */     writer.writeDouble(value.floatValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Float decode(BsonReader reader, DecoderContext decoderContext) {
/* 40 */     double value = NumberCodecHelper.decodeDouble(reader);
/* 41 */     if (value < -3.4028234663852886E38D || value > 3.4028234663852886E38D) {
/* 42 */       throw new BsonInvalidOperationException(String.format("%s can not be converted into a Float.", new Object[] { Double.valueOf(value) }));
/*    */     }
/* 44 */     return Float.valueOf((float)value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Float> getEncoderClass() {
/* 49 */     return Float.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\FloatCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */