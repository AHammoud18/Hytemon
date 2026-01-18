/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Levenshtein
/*     */ {
/*     */   public static int distance(CharSequence lhs, CharSequence rhs) {
/*  61 */     return distance(lhs, rhs, 1, 1, 1, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int distance(CharSequence source, CharSequence target, int deleteCost, int insertCost, int replaceCost, int swapCost) {
/*  70 */     if (2 * swapCost < insertCost + deleteCost) {
/*  71 */       throw new IllegalArgumentException("Unsupported cost assignment");
/*     */     }
/*  73 */     if (source.length() == 0) {
/*  74 */       return target.length() * insertCost;
/*     */     }
/*  76 */     if (target.length() == 0) {
/*  77 */       return source.length() * deleteCost;
/*     */     }
/*  79 */     int[][] table = new int[source.length()][target.length()];
/*  80 */     Map<Character, Integer> sourceIndexByCharacter = new HashMap<>();
/*  81 */     if (source.charAt(0) != target.charAt(0)) {
/*  82 */       table[0][0] = Math.min(replaceCost, deleteCost + insertCost);
/*     */     }
/*  84 */     sourceIndexByCharacter.put(Character.valueOf(source.charAt(0)), Integer.valueOf(0));
/*  85 */     for (int k = 1; k < source.length(); k++) {
/*  86 */       int deleteDistance = table[k - 1][0] + deleteCost;
/*  87 */       int insertDistance = (k + 1) * deleteCost + insertCost;
/*  88 */       int matchDistance = k * deleteCost + ((source.charAt(k) == target.charAt(0)) ? 0 : replaceCost);
/*  89 */       table[k][0] = Math.min(Math.min(deleteDistance, insertDistance), matchDistance);
/*     */     } 
/*  91 */     for (int j = 1; j < target.length(); j++) {
/*  92 */       int deleteDistance = (j + 1) * insertCost + deleteCost;
/*  93 */       int insertDistance = table[0][j - 1] + insertCost;
/*  94 */       int matchDistance = j * insertCost + ((source.charAt(0) == target.charAt(j)) ? 0 : replaceCost);
/*  95 */       table[0][j] = Math.min(Math.min(deleteDistance, insertDistance), matchDistance);
/*     */     } 
/*  97 */     for (int i = 1; i < source.length(); i++) {
/*  98 */       int maxSourceLetterMatchIndex = (source.charAt(i) == target.charAt(0)) ? 0 : -1;
/*  99 */       for (int m = 1; m < target.length(); m++) {
/* 100 */         int swapDistance; Integer candidateSwapIndex = sourceIndexByCharacter.get(Character.valueOf(target.charAt(m)));
/* 101 */         int jSwap = maxSourceLetterMatchIndex;
/* 102 */         int deleteDistance = table[i - 1][m] + deleteCost;
/* 103 */         int insertDistance = table[i][m - 1] + insertCost;
/* 104 */         int matchDistance = table[i - 1][m - 1];
/* 105 */         if (source.charAt(i) != target.charAt(m)) {
/* 106 */           matchDistance += replaceCost;
/*     */         } else {
/* 108 */           maxSourceLetterMatchIndex = m;
/*     */         } 
/*     */         
/* 111 */         if (candidateSwapIndex != null && jSwap != -1) {
/* 112 */           int preSwapCost, iSwap = candidateSwapIndex.intValue();
/*     */           
/* 114 */           if (iSwap == 0 && jSwap == 0) {
/* 115 */             preSwapCost = 0;
/*     */           } else {
/* 117 */             preSwapCost = table[Math.max(0, iSwap - 1)][Math.max(0, jSwap - 1)];
/*     */           } 
/* 119 */           swapDistance = preSwapCost + (i - iSwap - 1) * deleteCost + (m - jSwap - 1) * insertCost + swapCost;
/*     */         } else {
/* 121 */           swapDistance = Integer.MAX_VALUE;
/*     */         } 
/* 123 */         table[i][m] = Math.min(Math.min(Math.min(deleteDistance, insertDistance), matchDistance), swapDistance);
/*     */       } 
/* 125 */       sourceIndexByCharacter.put(Character.valueOf(source.charAt(i)), Integer.valueOf(i));
/*     */     } 
/* 127 */     return table[source.length() - 1][target.length() - 1];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\Levenshtein.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */