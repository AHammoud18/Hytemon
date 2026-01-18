/*    */ package com.hypixel.hytale.sneakythrow.consumer;
/*    */ 
/*    */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ThrowableTriConsumer<T, U, V, E extends Throwable>
/*    */   extends TriConsumer<T, U, V>
/*    */ {
/*    */   default void accept(T t, U u, V v) {
/*    */     try {
/* 12 */       acceptNow(t, u, v);
/* 13 */     } catch (Throwable e) {
/* 14 */       throw SneakyThrow.sneakyThrow(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   void acceptNow(T paramT, U paramU, V paramV) throws E;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\sneakythrow\consumer\ThrowableTriConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */