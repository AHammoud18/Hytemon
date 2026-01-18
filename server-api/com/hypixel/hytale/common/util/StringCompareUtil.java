/*     */ package com.hypixel.hytale.common.util;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringCompareUtil
/*     */ {
/*     */   public static int indexOfDifference(@Nullable CharSequence cs1, @Nullable CharSequence cs2) {
/*  54 */     if (cs1 == cs2) {
/*  55 */       return -1;
/*     */     }
/*  57 */     if (cs1 == null || cs2 == null) {
/*  58 */       return 0;
/*     */     }
/*     */     int i;
/*  61 */     for (i = 0; i < cs1.length() && i < cs2.length() && 
/*  62 */       cs1.charAt(i) == cs2.charAt(i); i++);
/*     */ 
/*     */ 
/*     */     
/*  66 */     if (i < cs2.length() || i < cs1.length()) {
/*  67 */       return i;
/*     */     }
/*  69 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFuzzyDistance(@Nonnull CharSequence term, @Nonnull CharSequence query, @Nonnull Locale locale) {
/*  99 */     if (term == null || query == null)
/* 100 */       throw new IllegalArgumentException("Strings must not be null"); 
/* 101 */     if (locale == null) {
/* 102 */       throw new IllegalArgumentException("Locale must not be null");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     String termLowerCase = term.toString().toLowerCase(locale);
/* 110 */     String queryLowerCase = query.toString().toLowerCase(locale);
/*     */ 
/*     */     
/* 113 */     int score = 0;
/*     */ 
/*     */ 
/*     */     
/* 117 */     int termIndex = 0;
/*     */ 
/*     */     
/* 120 */     int previousMatchingCharacterIndex = Integer.MIN_VALUE;
/*     */     
/* 122 */     for (int queryIndex = 0; queryIndex < queryLowerCase.length(); queryIndex++) {
/* 123 */       char queryChar = queryLowerCase.charAt(queryIndex);
/*     */       
/* 125 */       boolean termCharacterMatchFound = false;
/* 126 */       for (; termIndex < termLowerCase.length() && !termCharacterMatchFound; termIndex++) {
/* 127 */         char termChar = termLowerCase.charAt(termIndex);
/*     */         
/* 129 */         if (queryChar == termChar) {
/*     */           
/* 131 */           score++;
/*     */ 
/*     */ 
/*     */           
/* 135 */           if (previousMatchingCharacterIndex + 1 == termIndex) {
/* 136 */             score += 2;
/*     */           }
/*     */           
/* 139 */           previousMatchingCharacterIndex = termIndex;
/*     */ 
/*     */ 
/*     */           
/* 143 */           termCharacterMatchFound = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     return score;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLevenshteinDistance(@Nonnull CharSequence s, @Nonnull CharSequence t) {
/* 184 */     if (s == null || t == null) {
/* 185 */       throw new IllegalArgumentException("Strings must not be null");
/*     */     }
/*     */     
/* 188 */     int n = s.length();
/* 189 */     int m = t.length();
/*     */     
/* 191 */     if (n == 0)
/* 192 */       return m; 
/* 193 */     if (m == 0) {
/* 194 */       return n;
/*     */     }
/*     */     
/* 197 */     if (n > m) {
/*     */       
/* 199 */       CharSequence tmp = s;
/* 200 */       s = t;
/* 201 */       t = tmp;
/* 202 */       n = m;
/* 203 */       m = t.length();
/*     */     } 
/*     */     
/* 206 */     int[] p = new int[n + 1];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     for (i = 0; i <= n; i++) {
/* 217 */       p[i] = i;
/*     */     }
/*     */     
/* 220 */     for (int j = 1; j <= m; j++) {
/* 221 */       int upper_left = p[0];
/* 222 */       char t_j = t.charAt(j - 1);
/* 223 */       p[0] = j;
/*     */       
/* 225 */       for (i = 1; i <= n; i++) {
/* 226 */         int upper = p[i];
/* 227 */         int cost = (s.charAt(i - 1) == t_j) ? 0 : 1;
/*     */         
/* 229 */         p[i] = Math.min(Math.min(p[i - 1] + 1, p[i] + 1), upper_left + cost);
/* 230 */         upper_left = upper;
/*     */       } 
/*     */     } 
/*     */     
/* 234 */     return p[n];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\StringCompareUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */