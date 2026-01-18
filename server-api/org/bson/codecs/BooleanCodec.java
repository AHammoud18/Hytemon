/*    */ package org.bson.codecs;
/*    */ 
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
/*    */ public class BooleanCodec
/*    */   implements Codec<Boolean>
/*    */ {
/*    */   public void encode(BsonWriter writer, Boolean value, EncoderContext encoderContext) {
/* 30 */     writer.writeBoolean(value.booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean decode(BsonReader reader, DecoderContext decoderContext) {
/* 35 */     return Boolean.valueOf(reader.readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Boolean> getEncoderClass() {
/* 40 */     return Boolean.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BooleanCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */