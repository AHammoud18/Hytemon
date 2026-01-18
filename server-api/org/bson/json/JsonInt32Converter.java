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
/*    */ class JsonInt32Converter
/*    */   implements Converter<Integer>
/*    */ {
/*    */   public void convert(Integer value, StrictJsonWriter writer) {
/* 22 */     writer.writeNumber(Integer.toString(value.intValue()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonInt32Converter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */