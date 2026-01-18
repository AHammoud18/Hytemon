/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonMinKey;
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
/*    */ public class BsonMinKeyCodec
/*    */   implements Codec<BsonMinKey>
/*    */ {
/*    */   public void encode(BsonWriter writer, BsonMinKey value, EncoderContext encoderContext) {
/* 31 */     writer.writeMinKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonMinKey decode(BsonReader reader, DecoderContext decoderContext) {
/* 36 */     reader.readMinKey();
/* 37 */     return new BsonMinKey();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonMinKey> getEncoderClass() {
/* 42 */     return BsonMinKey.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonMinKeyCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */