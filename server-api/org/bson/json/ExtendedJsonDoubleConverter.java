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
/*    */ class ExtendedJsonDoubleConverter
/*    */   implements Converter<Double>
/*    */ {
/*    */   public void convert(Double value, StrictJsonWriter writer) {
/* 22 */     writer.writeStartObject();
/* 23 */     writer.writeName("$numberDouble");
/* 24 */     writer.writeString(Double.toString(value.doubleValue()));
/* 25 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonDoubleConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */