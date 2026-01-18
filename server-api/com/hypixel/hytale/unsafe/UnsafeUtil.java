/*    */ package com.hypixel.hytale.unsafe;
/*    */ 
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Field;
/*    */ import javax.annotation.Nullable;
/*    */ import sun.misc.Unsafe;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnsafeUtil
/*    */ {
/*    */   @Nullable
/*    */   public static final Unsafe UNSAFE;
/*    */   
/*    */   static {
/*    */     Unsafe instance;
/*    */     try {
/* 21 */       Field field = Unsafe.class.getDeclaredField("theUnsafe");
/* 22 */       field.setAccessible(true);
/* 23 */       instance = (Unsafe)field.get(null);
/* 24 */     } catch (Exception e1) {
/*    */       try {
/* 26 */         Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor(new Class[0]);
/* 27 */         c.setAccessible(true);
/* 28 */         instance = c.newInstance(new Object[0]);
/* 29 */       } catch (Exception e) {
/* 30 */         throw SneakyThrow.sneakyThrow(e);
/*    */       } 
/*    */     } 
/* 33 */     UNSAFE = instance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytal\\unsafe\UnsafeUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */