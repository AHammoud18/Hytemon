/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.BsonTimestamp;
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
/*    */ class ExtendedJsonTimestampConverter
/*    */   implements Converter<BsonTimestamp>
/*    */ {
/*    */   public void convert(BsonTimestamp value, StrictJsonWriter writer) {
/* 27 */     writer.writeStartObject();
/* 28 */     writer.writeStartObject("$timestamp");
/* 29 */     writer.writeNumber("t", Long.toUnsignedString(Integer.toUnsignedLong(value.getTime())));
/* 30 */     writer.writeNumber("i", Long.toUnsignedString(Integer.toUnsignedLong(value.getInc())));
/* 31 */     writer.writeEndObject();
/* 32 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonTimestampConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */