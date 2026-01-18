/*    */ package org.bson.codecs;
/*    */ 
/*    */ import org.bson.BsonDateTime;
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
/*    */ public class BsonDateTimeCodec
/*    */   implements Codec<BsonDateTime>
/*    */ {
/*    */   public BsonDateTime decode(BsonReader reader, DecoderContext decoderContext) {
/* 31 */     return new BsonDateTime(reader.readDateTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, BsonDateTime value, EncoderContext encoderContext) {
/* 36 */     writer.writeDateTime(value.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<BsonDateTime> getEncoderClass() {
/* 41 */     return BsonDateTime.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonDateTimeCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */