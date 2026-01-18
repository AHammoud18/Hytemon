/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonMaxKey;
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
/*    */ public class BsonMaxKeyCodec
/*    */   implements Codec<BsonMaxKey>
/*    */ {
/*    */   public void encode(BsonWriter writer, BsonMaxKey value, EncoderContext encoderContext) {
/* 31 */     writer.writeMaxKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonMaxKey decode(BsonReader reader, DecoderContext decoderContext) {
/* 36 */     reader.readMaxKey();
/* 37 */     return new BsonMaxKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonMaxKey> getEncoderClass() {
/* 42 */     return BsonMaxKey.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonMaxKeyCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */