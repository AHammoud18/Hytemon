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
/*    */ class RelaxedExtendedJsonDateTimeConverter
/*    */   implements Converter<Long>
/*    */ {
/* 20 */   private static final Converter<Long> FALLBACK_CONVERTER = new ExtendedJsonDateTimeConverter();
/*    */   
/*    */   private static final long LAST_MS_OF_YEAR_9999 = 253402300799999L;
/*    */   
/*    */   public void convert(Long value, StrictJsonWriter writer) {
/* 25 */     if (value.longValue() < 0L || value.longValue() > 253402300799999L) {
/* 26 */       FALLBACK_CONVERTER.convert(value, writer);
/*    */     } else {
/* 28 */       writer.writeStartObject();
/* 29 */       writer.writeString("$date", DateTimeFormatter.format(value.longValue()));
/* 30 */       writer.writeEndObject();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\RelaxedExtendedJsonDateTimeConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */