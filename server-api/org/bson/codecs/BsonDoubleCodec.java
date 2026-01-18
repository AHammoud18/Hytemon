/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonDouble;
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
/*    */ public class BsonDoubleCodec
/*    */   implements Codec<BsonDouble>
/*    */ {
/*    */   public BsonDouble decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new BsonDouble(reader.readDouble());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonDouble value, EncoderContext encoderContext) {
/* 36 */     writer.writeDouble(value.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonDouble> getEncoderClass() {
/* 41 */     return BsonDouble.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonDoubleCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */