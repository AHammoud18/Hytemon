/*    */ package org.bson.codecs.jsr310;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import java.time.LocalDate;
/*    */ import java.time.LocalTime;
/*    */ import java.time.ZoneOffset;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.codecs.DecoderContext;
/*    */ import org.bson.codecs.EncoderContext;
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
/*    */ 
/*    */ public class LocalTimeCodec
/*    */   extends DateTimeBasedCodec<LocalTime>
/*    */ {
/*    */   public LocalTime decode(BsonReader reader, DecoderContext decoderContext) {
/* 43 */     return Instant.ofEpochMilli(validateAndReadDateTime(reader)).atOffset(ZoneOffset.UTC).toLocalTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, LocalTime value, EncoderContext encoderContext) {
/* 53 */     writer.writeDateTime(value.atDate(LocalDate.ofEpochDay(0L)).toInstant(ZoneOffset.UTC).toEpochMilli());
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<LocalTime> getEncoderClass() {
/* 58 */     return LocalTime.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\jsr310\LocalTimeCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */