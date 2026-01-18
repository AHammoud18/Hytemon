/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonInt32;
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
/*    */ public class BsonInt32Codec
/*    */   implements Codec<BsonInt32>
/*    */ {
/*    */   public BsonInt32 decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new BsonInt32(reader.readInt32());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonInt32 value, EncoderContext encoderContext) {
/* 36 */     writer.writeInt32(value.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonInt32> getEncoderClass() {
/* 41 */     return BsonInt32.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonInt32Codec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */