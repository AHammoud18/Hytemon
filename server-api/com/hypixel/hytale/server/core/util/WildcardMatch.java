/*    */ package com.hypixel.hytale.server.core.util;
/*    */ 
/*    */ 
/*    */ public final class WildcardMatch
/*    */ {
/*    */   public static boolean test(String text, String pattern) {
/*  7 */     return test(text, pattern, false);
/*    */   }
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
/*    */   public static boolean test(String text, String pattern, boolean ignoreCase) {
/* 24 */     if (ignoreCase) {
/* 25 */       text = text.toLowerCase();
/* 26 */       pattern = pattern.toLowerCase();
/*    */     } 
/*    */     
/* 29 */     if (text.equals(pattern)) {
/* 30 */       return true;
/*    */     }
/*    */     
/* 33 */     int t = 0, p = 0, starIdx = -1, match = 0;
/*    */     
/* 35 */     while (t < text.length()) {
/* 36 */       if (p < pattern.length() && (pattern.charAt(p) == '?' || pattern.charAt(p) == text.charAt(t))) {
/* 37 */         t++;
/* 38 */         p++; continue;
/* 39 */       }  if (p < pattern.length() && pattern.charAt(p) == '*') {
/* 40 */         starIdx = p++;
/* 41 */         match = t; continue;
/* 42 */       }  if (starIdx != -1) {
/* 43 */         p = starIdx + 1;
/* 44 */         t = ++match; continue;
/*    */       } 
/* 46 */       return false;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 51 */     while (p < pattern.length() && pattern.charAt(p) == '*') {
/* 52 */       p++;
/*    */     }
/*    */     
/* 55 */     return (p == pattern.length());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\WildcardMatch.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */