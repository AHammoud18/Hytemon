/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonObjectId;
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
/*    */ public class BsonObjectIdCodec
/*    */   implements Codec<BsonObjectId>
/*    */ {
/*    */   public void encode(BsonWriter writer, BsonObjectId value, EncoderContext encoderContext) {
/* 31 */     writer.writeObjectId(value.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonObjectId decode(BsonReader reader, DecoderContext decoderContext) {
/* 36 */     return new BsonObjectId(reader.readObjectId());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonObjectId> getEncoderClass() {
/* 41 */     return BsonObjectId.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonObjectIdCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */