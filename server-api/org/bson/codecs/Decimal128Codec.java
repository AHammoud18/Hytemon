/*    */ package org.bson.codecs;
/*    */ 
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
/*    */ public final class Decimal128Codec
/*    */   implements Codec<Decimal128>
/*    */ {
/*    */   public Decimal128 decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return reader.readDecimal128();
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, Decimal128 value, EncoderContext encoderContext) {
/* 36 */     writer.writeDecimal128(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Decimal128> getEncoderClass() {
/* 41 */     return Decimal128.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\Decimal128Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */