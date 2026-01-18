/*    */ package org.bson.codecs.jsr310;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import java.time.LocalDate;
/*    */ import java.time.ZoneOffset;
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonWriter;
/*    */ import org.bson.codecs.DecoderContext;
/*    */ import org.bson.codecs.EncoderContext;
/*    */ import org.bson.codecs.configuration.CodecConfigurationException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LocalDateCodec
/*    */   extends DateTimeBasedCodec<LocalDate>
/*    */ {
/*    */   public LocalDate decode(BsonReader reader, DecoderContext decoderContext) {
/* 46 */     return Instant.ofEpochMilli(validateAndReadDateTime(reader)).atZone(ZoneOffset.UTC).toLocalDate();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, LocalDate value, EncoderContext encoderContext) {
/*    */     try {
/* 57 */       writer.writeDateTime(value.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
/* 58 */     } catch (ArithmeticException e) {
/* 59 */       throw new CodecConfigurationException(String.format("Unsupported LocalDate '%s' could not be converted to milliseconds: %s", new Object[] { value, e
/* 60 */               .getMessage() }), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<LocalDate> getEncoderClass() {
/* 66 */     return LocalDate.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\jsr310\LocalDateCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */