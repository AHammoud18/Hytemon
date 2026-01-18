/*    */ package com.hypixel.hytale.math.util;
/*    */ 
/*    */ import java.util.Random;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ 
/*    */ public class FastRandom
/*    */   extends Random {
/*    */   private static final long multiplier = 25214903917L;
/*    */   private static final long addend = 11L;
/*    */   private static final long mask = 281474976710655L;
/*    */   private long seed;
/*    */   
/*    */   public FastRandom() {
/* 14 */     this.seed = initialScramble(ThreadLocalRandom.current().nextLong());
/*    */   }
/*    */   
/*    */   public FastRandom(long seed) {
/* 18 */     this.seed = initialScramble(seed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSeed(long seed) {
/* 23 */     this.seed = initialScramble(seed);
/*    */   }
/*    */   
/*    */   private static long initialScramble(long seed) {
/* 27 */     return (seed ^ 0x5DEECE66DL) & 0xFFFFFFFFFFFFL;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int next(int bits) {
/* 32 */     long seed = this.seed;
/* 33 */     seed = seed * 25214903917L + 11L & 0xFFFFFFFFFFFFL;
/* 34 */     this.seed = seed;
/* 35 */     return (int)(seed >>> 48 - bits);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized double nextGaussian() {
/* 41 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\FastRandom.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */