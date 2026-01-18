/*    */ package org.bson.json;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.TimeZone;
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
/*    */ 
/*    */ class ShellDateTimeConverter
/*    */   implements Converter<Long>
/*    */ {
/*    */   public void convert(Long value, StrictJsonWriter writer) {
/* 29 */     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
/* 30 */     dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
/* 31 */     if (value.longValue() >= -59014396800000L && value.longValue() <= 253399536000000L) {
/* 32 */       writer.writeRaw(String.format("ISODate(\"%s\")", new Object[] { dateFormat.format(new Date(value.longValue())) }));
/*    */     } else {
/* 34 */       writer.writeRaw(String.format("new Date(%d)", new Object[] { value }));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\ShellDateTimeConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */