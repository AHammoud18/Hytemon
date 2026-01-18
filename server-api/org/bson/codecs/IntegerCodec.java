/*    */ package org.bson.codecs;
/*    */ 
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
/*    */ public class IntegerCodec
/*    */   implements Codec<Integer>
/*    */ {
/*    */   public void encode(BsonWriter writer, Integer value, EncoderContext encoderContext) {
/* 33 */     writer.writeInt32(value.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer decode(BsonReader reader, DecoderContext decoderContext) {
/* 38 */     return Integer.valueOf(NumberCodecHelper.decodeInt(reader));
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Integer> getEncoderClass() {
/* 43 */     return Integer.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\IntegerCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */