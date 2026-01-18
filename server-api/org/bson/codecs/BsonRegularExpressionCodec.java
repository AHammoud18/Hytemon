/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonRegularExpression;
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
/*    */ public class BsonRegularExpressionCodec
/*    */   implements Codec<BsonRegularExpression>
/*    */ {
/*    */   public BsonRegularExpression decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return reader.readRegularExpression();
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonRegularExpression value, EncoderContext encoderContext) {
/* 36 */     writer.writeRegularExpression(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonRegularExpression> getEncoderClass() {
/* 41 */     return BsonRegularExpression.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonRegularExpressionCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */