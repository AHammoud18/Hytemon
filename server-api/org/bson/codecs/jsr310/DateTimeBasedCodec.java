/*    */ package org.bson.codecs.jsr310;
/*    */ 
/*    */ import org.bson.BsonReader;
/*    */ import org.bson.BsonType;
/*    */ import org.bson.codecs.Codec;
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
/*    */ abstract class DateTimeBasedCodec<T>
/*    */   implements Codec<T>
/*    */ {
/*    */   long validateAndReadDateTime(BsonReader reader) {
/* 29 */     BsonType currentType = reader.getCurrentBsonType();
/* 30 */     if (!currentType.equals(BsonType.DATE_TIME))
/* 31 */       throw new CodecConfigurationException(String.format("Could not decode into %s, expected '%s' BsonType but got '%s'.", new Object[] {
/* 32 */               getEncoderClass().getSimpleName(), BsonType.DATE_TIME, currentType
/*    */             })); 
/* 34 */     return reader.readDateTime();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\jsr310\DateTimeBasedCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */