/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.types.MinKey;
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
/*    */ public class MinKeyCodec
/*    */   implements Codec<MinKey>
/*    */ {
/*    */   public void encode(BsonWriter writer, MinKey value, EncoderContext encoderContext) {
/* 31 */     writer.writeMinKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public MinKey decode(BsonReader reader, DecoderContext decoderContext) {
/* 36 */     reader.readMinKey();
/* 37 */     return new MinKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<MinKey> getEncoderClass() {
/* 42 */     return MinKey.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\MinKeyCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */