/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonUndefined;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BsonUndefinedCodec
/*    */   implements Codec<BsonUndefined>
/*    */ {
/*    */   public BsonUndefined decode(BsonReader reader, DecoderContext decoderContext) {
/* 37 */     reader.readUndefined();
/* 38 */     return new BsonUndefined();
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonUndefined value, EncoderContext encoderContext) {
/* 43 */     writer.writeUndefined();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonUndefined> getEncoderClass() {
/* 48 */     return BsonUndefined.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonUndefinedCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */