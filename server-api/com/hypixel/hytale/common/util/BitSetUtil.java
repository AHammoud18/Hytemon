/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import com.hypixel.hytale.unsafe.UnsafeUtil;
/*    */ import java.util.BitSet;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BitSetUtil
/*    */ {
/*    */   public static final long WORDS_OFFSET;
/*    */   public static final long WORDS_IN_USE_OFFSET;
/*    */   
/*    */   static {
/*    */     try {
/* 19 */       if (UnsafeUtil.UNSAFE != null) {
/* 20 */         WORDS_OFFSET = UnsafeUtil.UNSAFE.objectFieldOffset(BitSet.class.getDeclaredField("words"));
/* 21 */         WORDS_IN_USE_OFFSET = UnsafeUtil.UNSAFE.objectFieldOffset(BitSet.class.getDeclaredField("wordsInUse"));
/*    */       } else {
/* 23 */         WORDS_OFFSET = 0L;
/* 24 */         WORDS_IN_USE_OFFSET = 0L;
/*    */       } 
/* 26 */     } catch (NoSuchFieldException e) {
/* 27 */       throw SneakyThrow.sneakyThrow(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void copyValues(@Nonnull BitSet from, @Nonnull BitSet to) {
/* 32 */     if (UnsafeUtil.UNSAFE == null) {
/* 33 */       copyValuesSlow(from, to);
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     int wordsInUse = UnsafeUtil.UNSAFE.getInt(from, WORDS_IN_USE_OFFSET);
/* 38 */     UnsafeUtil.UNSAFE.putInt(to, WORDS_IN_USE_OFFSET, wordsInUse);
/*    */     
/* 40 */     long[] fromWords = (long[])UnsafeUtil.UNSAFE.getObject(from, WORDS_OFFSET);
/* 41 */     long[] toWords = (long[])UnsafeUtil.UNSAFE.getObject(to, WORDS_OFFSET);
/*    */     
/* 43 */     if (wordsInUse > toWords.length) {
/* 44 */       toWords = new long[wordsInUse];
/* 45 */       UnsafeUtil.UNSAFE.putObject(to, WORDS_OFFSET, toWords);
/*    */     } 
/*    */     
/* 48 */     System.arraycopy(fromWords, 0, toWords, 0, wordsInUse);
/*    */   }
/*    */   
/*    */   public static void copyValuesSlow(@Nonnull BitSet from, @Nonnull BitSet to) {
/* 52 */     to.clear();
/* 53 */     to.or(from);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\BitSetUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */