/*    */ package com.hypixel.hytale.codec.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Documentation
/*    */ {
/*    */   public static String stripMarkdown(@Nullable String markdown) {
/* 16 */     if (markdown == null) return null;
/*    */     
/* 18 */     StringBuilder output = new StringBuilder();
/* 19 */     IntArrayList counts = new IntArrayList();
/* 20 */     for (int i = 0; i < markdown.length(); i++) {
/* 21 */       int start; boolean isEnding; int targetCount, matchingCount; char c = markdown.charAt(i);
/* 22 */       switch (c) { case '*':
/*    */         case '_':
/* 24 */           start = i;
/* 25 */           isEnding = (start >= 1 && !Character.isWhitespace(markdown.charAt(start - 1)));
/* 26 */           targetCount = (!counts.isEmpty() && isEnding) ? counts.getInt(counts.size() - 1) : -1;
/* 27 */           for (; i < markdown.length() && 
/* 28 */             markdown.charAt(i) == c && i - start != targetCount; i++);
/*    */           
/* 30 */           matchingCount = i - start;
/* 31 */           if (!counts.isEmpty() && counts.getInt(counts.size() - 1) == matchingCount) {
/* 32 */             if (!isEnding) {
/* 33 */               output.append(String.valueOf(c).repeat(matchingCount));
/*    */               break;
/*    */             } 
/* 36 */             counts.removeInt(counts.size() - 1);
/*    */           } else {
/* 38 */             if (i < markdown.length() && Character.isWhitespace(markdown.charAt(i))) {
/* 39 */               output.append(String.valueOf(c).repeat(matchingCount));
/* 40 */               output.append(markdown.charAt(i));
/*    */               break;
/*    */             } 
/* 43 */             counts.add(matchingCount);
/*    */           } 
/* 45 */           i--; break;
/*    */         default:
/* 47 */           output.append(c); break; }
/*    */     
/*    */     } 
/* 50 */     if (!counts.isEmpty()) {
/* 51 */       throw new IllegalArgumentException("Unbalanced markdown formatting");
/*    */     }
/* 53 */     return output.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\code\\util\Documentation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */