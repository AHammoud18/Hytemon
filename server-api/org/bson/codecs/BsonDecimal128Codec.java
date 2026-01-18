/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonDecimal128;
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
/*    */ public class BsonDecimal128Codec
/*    */   implements Codec<BsonDecimal128>
/*    */ {
/*    */   public BsonDecimal128 decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new BsonDecimal128(reader.readDecimal128());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonDecimal128 value, EncoderContext encoderContext) {
/* 36 */     writer.writeDecimal128(value.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonDecimal128> getEncoderClass() {
/* 41 */     return BsonDecimal128.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonDecimal128Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */