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
/*    */ 
/*    */ 
/*    */ class ShellInt64Converter
/*    */   implements Converter<Long>
/*    */ {
/*    */   public void convert(Long value, StrictJsonWriter writer) {
/* 24 */     if (value.longValue() >= -2147483648L && value.longValue() <= 2147483647L) {
/* 25 */       writer.writeRaw(String.format("NumberLong(%d)", new Object[] { value }));
/*    */     } else {
/* 27 */       writer.writeRaw(String.format("NumberLong(\"%d\")", new Object[] { value }));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ShellInt64Converter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */