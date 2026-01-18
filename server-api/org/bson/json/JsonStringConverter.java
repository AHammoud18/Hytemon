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
/*    */ class JsonStringConverter
/*    */   implements Converter<String>
/*    */ {
/*    */   public void convert(String value, StrictJsonWriter writer) {
/* 22 */     writer.writeString(value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonStringConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */