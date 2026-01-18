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
/*    */ class ExtendedJsonInt64Converter
/*    */   implements Converter<Long>
/*    */ {
/*    */   public void convert(Long value, StrictJsonWriter writer) {
/* 22 */     writer.writeStartObject();
/* 23 */     writer.writeName("$numberLong");
/* 24 */     writer.writeString(Long.toString(value.longValue()));
/* 25 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonInt64Converter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */