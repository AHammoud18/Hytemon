/*    */ package org.bson.json;
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
/*    */ class ExtendedJsonDateTimeConverter
/*    */   implements Converter<Long>
/*    */ {
/*    */   public void convert(Long value, StrictJsonWriter writer) {
/* 22 */     writer.writeStartObject();
/* 23 */     writer.writeStartObject("$date");
/* 24 */     writer.writeString("$numberLong", Long.toString(value.longValue()));
/* 25 */     writer.writeEndObject();
/* 26 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonDateTimeConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */