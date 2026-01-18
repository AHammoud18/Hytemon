/*    */ package org.bson.json;
/*    */ 
/*    */ import org.bson.types.Decimal128;
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
/*    */ class ExtendedJsonDecimal128Converter
/*    */   implements Converter<Decimal128>
/*    */ {
/*    */   public void convert(Decimal128 value, StrictJsonWriter writer) {
/* 24 */     writer.writeStartObject();
/* 25 */     writer.writeName("$numberDecimal");
/* 26 */     writer.writeString(value.toString());
/* 27 */     writer.writeEndObject();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ExtendedJsonDecimal128Converter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */