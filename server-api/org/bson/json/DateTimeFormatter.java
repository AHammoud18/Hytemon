/*    */ package org.bson.json;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import java.time.LocalDate;
/*    */ import java.time.ZoneId;
/*    */ import java.time.ZoneOffset;
/*    */ import java.time.ZonedDateTime;
/*    */ import java.time.temporal.TemporalAccessor;
/*    */ import java.time.temporal.TemporalQuery;
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
/*    */ final class DateTimeFormatter
/*    */ {
/* 31 */   private static final int DATE_STRING_LENGTH = "1970-01-01".length();
/*    */ 
/*    */   
/*    */   static long parse(String dateTimeString) {
/* 35 */     if (dateTimeString.length() == DATE_STRING_LENGTH) {
/* 36 */       return LocalDate.parse(dateTimeString, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
/*    */     }
/* 38 */     return ((Instant)java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME.<Instant>parse(dateTimeString, new TemporalQuery<Instant>()
/*    */         {
/*    */           public Instant queryFrom(TemporalAccessor temporal) {
/* 41 */             return Instant.from(temporal);
/*    */           }
/* 43 */         })).toEpochMilli();
/*    */   }
/*    */ 
/*    */   
/*    */   static String format(long dateTime) {
/* 48 */     return ZonedDateTime.ofInstant(Instant.ofEpochMilli(dateTime), ZoneId.of("Z")).format(java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\json\DateTimeFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */