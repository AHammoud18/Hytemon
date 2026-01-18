/*     */ package org.bson;
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
/*     */ class BSON
/*     */ {
/*     */   static final byte B_GENERAL = 0;
/*     */   static final byte B_BINARY = 2;
/*     */   private static final int FLAG_GLOBAL = 256;
/*  37 */   private static final int[] FLAG_LOOKUP = new int[65535];
/*     */   
/*     */   static {
/*  40 */     FLAG_LOOKUP[103] = 256;
/*  41 */     FLAG_LOOKUP[105] = 2;
/*  42 */     FLAG_LOOKUP[109] = 8;
/*  43 */     FLAG_LOOKUP[115] = 32;
/*  44 */     FLAG_LOOKUP[99] = 128;
/*  45 */     FLAG_LOOKUP[120] = 4;
/*  46 */     FLAG_LOOKUP[100] = 1;
/*  47 */     FLAG_LOOKUP[116] = 16;
/*  48 */     FLAG_LOOKUP[117] = 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int regexFlags(String s) {
/*  59 */     int flags = 0;
/*     */     
/*  61 */     if (s == null) {
/*  62 */       return flags;
/*     */     }
/*     */     
/*  65 */     for (char f : s.toLowerCase().toCharArray()) {
/*  66 */       flags |= regexFlag(f);
/*     */     }
/*     */     
/*  69 */     return flags;
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
/*     */   private static int regexFlag(char c) {
/*  81 */     int flag = FLAG_LOOKUP[c];
/*     */     
/*  83 */     if (flag == 0) {
/*  84 */       throw new IllegalArgumentException(String.format("Unrecognized flag [%c]", new Object[] { Character.valueOf(c) }));
/*     */     }
/*     */     
/*  87 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String regexFlags(int flags) {
/*  98 */     int processedFlags = flags;
/*  99 */     StringBuilder buf = new StringBuilder();
/*     */     
/* 101 */     for (int i = 0; i < FLAG_LOOKUP.length; i++) {
/* 102 */       if ((processedFlags & FLAG_LOOKUP[i]) > 0) {
/* 103 */         buf.append((char)i);
/* 104 */         processedFlags -= FLAG_LOOKUP[i];
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     if (processedFlags > 0) {
/* 109 */       throw new IllegalArgumentException("Some flags could not be recognized.");
/*     */     }
/*     */     
/* 112 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\BSON.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */