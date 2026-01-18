/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonBoolean;
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
/*    */ public class BsonBooleanCodec
/*    */   implements Codec<BsonBoolean>
/*    */ {
/*    */   public BsonBoolean decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     boolean value = reader.readBoolean();
/* 32 */     return BsonBoolean.valueOf(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonBoolean value, EncoderContext encoderContext) {
/* 37 */     writer.writeBoolean(value.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonBoolean> getEncoderClass() {
/* 42 */     return BsonBoolean.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonBooleanCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */