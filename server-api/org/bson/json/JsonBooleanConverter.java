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
/*    */ class JsonBooleanConverter
/*    */   implements Converter<Boolean>
/*    */ {
/*    */   public void convert(Boolean value, StrictJsonWriter writer) {
/* 22 */     writer.writeBoolean(value.booleanValue());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonBooleanConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */