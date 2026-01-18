/*    */ package com.hypixel.fastutil.util;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ public class TLRUtil
/*    */ {
/*    */   private static final Unsafe UNSAFE;
/*    */   private static final long PROBE;
/*    */   
/*    */   static {
/*    */     Unsafe instance;
/*    */     try {
/* 16 */       Field field = Unsafe.class.getDeclaredField("theUnsafe");
/* 17 */       field.setAccessible(true);
/* 18 */       instance = (Unsafe)field.get(null);
/* 19 */     } catch (Exception e1) {
/*    */       try {
/* 21 */         Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor(new Class[0]);
/* 22 */         c.setAccessible(true);
/* 23 */         instance = c.newInstance(new Object[0]);
/* 24 */       } catch (Exception e) {
/* 25 */         throw SneakyThrow.sneakyThrow(e);
/*    */       } 
/*    */     } 
/*    */     
/* 29 */     UNSAFE = instance;
/*    */     
/*    */     try {
/* 32 */       PROBE = UNSAFE.objectFieldOffset(Thread.class.getDeclaredField("threadLocalRandomProbe"));
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 37 */     catch (NoSuchFieldException t) {
/* 38 */       throw SneakyThrow.sneakyThrow(t);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void localInit() {
/* 43 */     ThreadLocalRandom.current();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getProbe() {
/* 52 */     return UNSAFE.getInt(Thread.currentThread(), PROBE);
/*    */   }
/*    */   
/*    */   public static int advanceProbe(int probe) {
/* 56 */     probe ^= probe << 13;
/* 57 */     probe ^= probe >>> 17;
/* 58 */     probe ^= probe << 5;
/* 59 */     UNSAFE.putInt(Thread.currentThread(), PROBE, probe);
/* 60 */     return probe;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\fastuti\\util\TLRUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */