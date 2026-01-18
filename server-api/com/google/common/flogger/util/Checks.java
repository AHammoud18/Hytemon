/*    */ package com.google.common.flogger.util;
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
/*    */ 
/*    */ public class Checks
/*    */ {
/*    */   public static <T> T checkNotNull(T value, String name) {
/* 29 */     if (value == null) {
/* 30 */       throw new NullPointerException(name + " must not be null");
/*    */     }
/* 32 */     return value;
/*    */   }
/*    */   
/*    */   public static void checkArgument(boolean condition, String message) {
/* 36 */     if (!condition) {
/* 37 */       throw new IllegalArgumentException(message);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void checkState(boolean condition, String message) {
/* 42 */     if (!condition) {
/* 43 */       throw new IllegalStateException(message);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String checkMetadataIdentifier(String s) {
/* 51 */     if (s.isEmpty()) {
/* 52 */       throw new IllegalArgumentException("identifier must not be empty");
/*    */     }
/* 54 */     if (!isLetter(s.charAt(0))) {
/* 55 */       throw new IllegalArgumentException("identifier must start with an ASCII letter: " + s);
/*    */     }
/* 57 */     for (int n = 1; n < s.length(); n++) {
/* 58 */       char c = s.charAt(n);
/* 59 */       if (!isLetter(c) && (c < '0' || c > '9') && c != '_') {
/* 60 */         throw new IllegalArgumentException("identifier must contain only ASCII letters, digits or underscore: " + s);
/*    */       }
/*    */     } 
/*    */     
/* 64 */     return s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isLetter(char c) {
/* 73 */     return (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogge\\util\Checks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */