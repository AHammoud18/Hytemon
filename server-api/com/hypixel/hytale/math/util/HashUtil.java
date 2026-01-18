/*    */ package com.hypixel.hytale.math.util;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HashUtil
/*    */ {
/*    */   public static long hash(long v) {
/* 14 */     v = (v >>> 30L ^ v) * -4658895280553007687L;
/* 15 */     v = (v >>> 27L ^ v) * -7723592293110705685L;
/* 16 */     v = v >>> 31L ^ v;
/* 17 */     return v;
/*    */   }
/*    */   
/*    */   public static long hash(long l1, long l2) {
/* 21 */     l1 = (hash(l1) >>> 30L ^ l1) * -4658895280553007687L;
/* 22 */     l1 = hash(l2) >>> 31L ^ l1;
/* 23 */     return l1;
/*    */   }
/*    */   
/*    */   public static long hash(long l1, long l2, long l3) {
/* 27 */     l1 = (hash(l1) >>> 30L ^ l1) * -4658895280553007687L;
/* 28 */     l1 = (hash(l2) >>> 27L ^ l1) * -7723592293110705685L;
/* 29 */     l1 = hash(l3) >>> 31L ^ l1;
/* 30 */     return l1;
/*    */   }
/*    */   
/*    */   public static long hash(long l1, long l2, long l3, long l4) {
/* 34 */     l1 = (hash(l1) >>> 30L ^ l1) * -4658895280553007687L;
/* 35 */     l1 = (hash(l2) >>> 27L ^ l1) * -7723592293110705685L;
/* 36 */     l1 = (hash(l3) >>> 30L ^ l1) * -6389720478792763523L;
/* 37 */     l1 = hash(l4) >>> 31L ^ l1;
/* 38 */     return l1;
/*    */   }
/*    */   
/*    */   public static long rehash(long l1) {
/* 42 */     return hash(hash(l1));
/*    */   }
/*    */   
/*    */   public static long rehash(long l1, long l2) {
/* 46 */     return hash(hash(l1, l2));
/*    */   }
/*    */   
/*    */   public static long rehash(long l1, long l2, long l3) {
/* 50 */     return hash(hash(l1, l2, l3));
/*    */   }
/*    */   
/*    */   public static long rehash(long l1, long l2, long l3, long l4) {
/* 54 */     return hash(hash(l1, l2, l3, l4));
/*    */   }
/*    */   
/*    */   public static double random(long l1) {
/* 58 */     return hashToRandomDouble(rehash(l1));
/*    */   }
/*    */   
/*    */   public static double random(long l1, long l2) {
/* 62 */     return hashToRandomDouble(rehash(l1, l2));
/*    */   }
/*    */   
/*    */   public static double random(long l1, long l2, long l3) {
/* 66 */     return hashToRandomDouble(rehash(l1, l2, l3));
/*    */   }
/*    */   
/*    */   public static double random(long l1, long l2, long l3, long l4) {
/* 70 */     return hashToRandomDouble(rehash(l1, l2, l3, l4));
/*    */   }
/*    */   
/*    */   public static int randomInt(long l1, long l2, long l3, int bound) {
/* 74 */     long hash = rehash(l1, l2, l3);
/* 75 */     hash &= Long.MAX_VALUE;
/* 76 */     return (int)(hash % bound);
/*    */   }
/*    */   
/*    */   private static double hashToRandomDouble(long hash) {
/* 80 */     hash &= 0xFFFFFFFFL;
/* 81 */     return hash / 4.294967295E9D;
/*    */   }
/*    */   
/*    */   public static long hashUuid(@Nonnull UUID uuid) {
/* 85 */     return hash(uuid.getLeastSignificantBits(), uuid.getMostSignificantBits());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\mat\\util\HashUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */