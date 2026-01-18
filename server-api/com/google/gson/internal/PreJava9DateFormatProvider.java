/*    */ package com.google.gson.internal;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Locale;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PreJava9DateFormatProvider
/*    */ {
/*    */   public static DateFormat getUsDateTimeFormat(int dateStyle, int timeStyle) {
/* 32 */     String pattern = getDatePartOfDateTimePattern(dateStyle) + " " + getTimePartOfDateTimePattern(timeStyle);
/* 33 */     return new SimpleDateFormat(pattern, Locale.US);
/*    */   }
/*    */   
/*    */   private static String getDatePartOfDateTimePattern(int dateStyle) {
/* 37 */     switch (dateStyle) {
/*    */       case 3:
/* 39 */         return "M/d/yy";
/*    */       case 2:
/* 41 */         return "MMM d, yyyy";
/*    */       case 1:
/* 43 */         return "MMMM d, yyyy";
/*    */       case 0:
/* 45 */         return "EEEE, MMMM d, yyyy";
/*    */     } 
/* 47 */     throw new IllegalArgumentException("Unknown DateFormat style: " + dateStyle);
/*    */   }
/*    */ 
/*    */   
/*    */   private static String getTimePartOfDateTimePattern(int timeStyle) {
/* 52 */     switch (timeStyle) {
/*    */       case 3:
/* 54 */         return "h:mm a";
/*    */       case 2:
/* 56 */         return "h:mm:ss a";
/*    */       case 0:
/*    */       case 1:
/* 59 */         return "h:mm:ss a z";
/*    */     } 
/* 61 */     throw new IllegalArgumentException("Unknown DateFormat style: " + timeStyle);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\gson\internal\PreJava9DateFormatProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */