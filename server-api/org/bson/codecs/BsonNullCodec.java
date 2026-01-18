/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonNull;
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
/*    */ 
/*    */ public class BsonNullCodec
/*    */   implements Codec<BsonNull>
/*    */ {
/*    */   public BsonNull decode(BsonReader reader, DecoderContext decoderContext) {
/* 32 */     reader.readNull();
/* 33 */     return BsonNull.VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonNull value, EncoderContext encoderContext) {
/* 38 */     writer.writeNull();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonNull> getEncoderClass() {
/* 43 */     return BsonNull.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonNullCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */