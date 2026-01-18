/*    */ package org.bson.codecs.jsr310;
/*    */ 
/*    */ import java.time.Instant;
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
/*    */ public class InstantCodec
/*    */   extends DateTimeBasedCodec<Instant>
/*    */ {
/*    */   public Instant decode(BsonReader reader, DecoderContext decoderContext) {
/* 44 */     return Instant.ofEpochMilli(validateAndReadDateTime(reader));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void encode(BsonWriter writer, Instant value, EncoderContext encoderContext) {
/*    */     try {
/* 54 */       writer.writeDateTime(value.toEpochMilli());
/* 55 */     } catch (ArithmeticException e) {
/* 56 */       throw new CodecConfigurationException(String.format("Unsupported Instant value '%s' could not be converted to milliseconds: %s", new Object[] { value, e
/* 57 */               .getMessage() }), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Instant> getEncoderClass() {
/* 63 */     return Instant.class;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\jsr310\InstantCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */