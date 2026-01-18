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
/*    */ class JsonJavaScriptConverter
/*    */   implements Converter<String>
/*    */ {
/*    */   public void convert(String value, StrictJsonWriter writer) {
/* 22 */     writer.writeStartObject();
/* 23 */     writer.writeString("$code", value);
/* 24 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonJavaScriptConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */