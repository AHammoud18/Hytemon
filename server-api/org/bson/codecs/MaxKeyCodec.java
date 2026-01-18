/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.types.MaxKey;
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
/*    */ public class MaxKeyCodec
/*    */   implements Codec<MaxKey>
/*    */ {
/*    */   public void encode(BsonWriter writer, MaxKey value, EncoderContext encoderContext) {
/* 31 */     writer.writeMaxKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public MaxKey decode(BsonReader reader, DecoderContext decoderContext) {
/* 36 */     reader.readMaxKey();
/* 37 */     return new MaxKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<MaxKey> getEncoderClass() {
/* 42 */     return MaxKey.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\MaxKeyCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */