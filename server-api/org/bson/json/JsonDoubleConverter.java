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
/*    */ class JsonDoubleConverter
/*    */   implements Converter<Double>
/*    */ {
/*    */   public void convert(Double value, StrictJsonWriter writer) {
/* 22 */     writer.writeNumber(Double.toString(value.doubleValue()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\JsonDoubleConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */