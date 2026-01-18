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
/*    */ class RelaxedExtendedJsonDoubleConverter
/*    */   implements Converter<Double>
/*    */ {
/* 20 */   private static final Converter<Double> FALLBACK_CONVERTER = new ExtendedJsonDoubleConverter();
/*    */ 
/*    */   
/*    */   public void convert(Double value, StrictJsonWriter writer) {
/* 24 */     if (value.isNaN() || value.isInfinite()) {
/* 25 */       FALLBACK_CONVERTER.convert(value, writer);
/*    */     } else {
/* 27 */       writer.writeNumber(Double.toString(value.doubleValue()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\RelaxedExtendedJsonDoubleConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */