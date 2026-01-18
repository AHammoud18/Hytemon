/*    */ package org.bson.codecs;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.types.Decimal128;
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
/*    */ public final class BigDecimalCodec
/*    */   implements Codec<BigDecimal>
/*    */ {
/*    */   public void encode(BsonWriter writer, BigDecimal value, EncoderContext encoderContext) {
/* 34 */     writer.writeDecimal128(new Decimal128(value));
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal decode(BsonReader reader, DecoderContext decoderContext) {
/* 39 */     return reader.readDecimal128().bigDecimalValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BigDecimal> getEncoderClass() {
/* 44 */     return BigDecimal.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BigDecimalCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */