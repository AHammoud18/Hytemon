/*    */ package com.hypixel.hytale.sneakythrow.consumer;
/*    */ 
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.util.function.IntConsumer;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ThrowableIntConsumer<E extends Throwable>
/*    */   extends IntConsumer
/*    */ {
/*    */   default void accept(int t) {
/*    */     try {
/* 13 */       acceptNow(t);
/* 14 */     } catch (Throwable e) {
/* 15 */       throw SneakyThrow.sneakyThrow(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   void acceptNow(int paramInt) throws E;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\sneakythrow\consumer\ThrowableIntConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */