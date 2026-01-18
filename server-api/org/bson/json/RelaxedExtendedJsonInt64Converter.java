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
/*    */ class RelaxedExtendedJsonInt64Converter
/*    */   implements Converter<Long>
/*    */ {
/*    */   public void convert(Long value, StrictJsonWriter writer) {
/* 22 */     writer.writeNumber(Long.toString(value.longValue()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\RelaxedExtendedJsonInt64Converter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */