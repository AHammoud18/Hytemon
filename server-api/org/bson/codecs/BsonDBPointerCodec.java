/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonDbPointer;
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
/*    */ public class BsonDBPointerCodec
/*    */   implements Codec<BsonDbPointer>
/*    */ {
/*    */   public BsonDbPointer decode(BsonReader reader, DecoderContext decoderContext) {
/* 32 */     return reader.readDBPointer();
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonDbPointer value, EncoderContext encoderContext) {
/* 37 */     writer.writeDBPointer(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonDbPointer> getEncoderClass() {
/* 42 */     return BsonDbPointer.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonDBPointerCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */