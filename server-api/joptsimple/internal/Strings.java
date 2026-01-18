/*     */ package joptsimple.internal;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
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
/*     */ public final class Strings
/*     */ {
/*     */   public static final String EMPTY = "";
/*  38 */   public static final String LINE_SEPARATOR = System.getProperty("line.separator");
/*     */   
/*     */   private Strings() {
/*  41 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String repeat(char ch, int count) {
/*  52 */     StringBuilder buffer = new StringBuilder();
/*     */     
/*  54 */     for (int i = 0; i < count; i++) {
/*  55 */       buffer.append(ch);
/*     */     }
/*  57 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNullOrEmpty(String target) {
/*  67 */     return (target == null || target.isEmpty());
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
/*     */   public static String surround(String target, char begin, char end) {
/*  80 */     return begin + target + end;
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
/*     */   public static String join(String[] pieces, String separator) {
/*  92 */     return join(Arrays.asList(pieces), separator);
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
/*     */   public static String join(Iterable<String> pieces, String separator) {
/* 104 */     StringBuilder buffer = new StringBuilder();
/*     */     
/* 106 */     for (Iterator<String> iter = pieces.iterator(); iter.hasNext(); ) {
/* 107 */       buffer.append(iter.next());
/*     */       
/* 109 */       if (iter.hasNext()) {
/* 110 */         buffer.append(separator);
/*     */       }
/*     */     } 
/* 113 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\internal\Strings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */