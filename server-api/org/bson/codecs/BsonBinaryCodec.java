/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonBinary;
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
/*    */ public class BsonBinaryCodec
/*    */   implements Codec<BsonBinary>
/*    */ {
/*    */   public void encode(BsonWriter writer, BsonBinary value, EncoderContext encoderContext) {
/* 31 */     writer.writeBinaryData(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public BsonBinary decode(BsonReader reader, DecoderContext decoderContext) {
/* 36 */     return reader.readBinaryData();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonBinary> getEncoderClass() {
/* 41 */     return BsonBinary.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonBinaryCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */