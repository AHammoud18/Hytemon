/*    */ package org.bson.codecs;
/*    */ 
/*    */ import java.util.Date;
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
/*    */ public class DateCodec
/*    */   implements Codec<Date>
/*    */ {
/*    */   public void encode(BsonWriter writer, Date value, EncoderContext encoderContext) {
/* 32 */     writer.writeDateTime(value.getTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public Date decode(BsonReader reader, DecoderContext decoderContext) {
/* 37 */     return new Date(reader.readDateTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Date> getEncoderClass() {
/* 42 */     return Date.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\DateCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */