/*    */ package org.bson.codecs.jsr310;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import java.time.LocalDateTime;
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
/*    */ public class LocalDateTimeCodec
/*    */   extends DateTimeBasedCodec<LocalDateTime>
/*    */ {
/*    */   public LocalDateTime decode(BsonReader reader, DecoderContext decoderContext) {
/* 45 */     return Instant.ofEpochMilli(validateAndReadDateTime(reader)).atZone(ZoneOffset.UTC).toLocalDateTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, LocalDateTime value, EncoderContext encoderContext) {
/*    */     try {
/* 56 */       writer.writeDateTime(value.toInstant(ZoneOffset.UTC).toEpochMilli());
/* 57 */     } catch (ArithmeticException e) {
/* 58 */       throw new CodecConfigurationException(String.format("Unsupported LocalDateTime value '%s' could not be converted to milliseconds: %s", new Object[] { value, e
/* 59 */               .getMessage() }), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<LocalDateTime> getEncoderClass() {
/* 65 */     return LocalDateTime.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\jsr310\LocalDateTimeCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */