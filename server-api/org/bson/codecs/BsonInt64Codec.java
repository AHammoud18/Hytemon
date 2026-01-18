/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonInt64;
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
/*    */ public class BsonInt64Codec
/*    */   implements Codec<BsonInt64>
/*    */ {
/*    */   public BsonInt64 decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new BsonInt64(reader.readInt64());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonInt64 value, EncoderContext encoderContext) {
/* 36 */     writer.writeInt64(value.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonInt64> getEncoderClass() {
/* 41 */     return BsonInt64.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonInt64Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */