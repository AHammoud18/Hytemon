/*    */ package com.hypixel.hytale.sneakythrow.consumer;
/*    */ 
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.util.function.BiConsumer;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ThrowableBiConsumer<T, U, E extends Throwable>
/*    */   extends BiConsumer<T, U>
/*    */ {
/*    */   default void accept(T t, U u) {
/*    */     try {
/* 13 */       acceptNow(t, u);
/* 14 */     } catch (Throwable e) {
/* 15 */       throw SneakyThrow.sneakyThrow(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   void acceptNow(T paramT, U paramU) throws E;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\sneakythrow\consumer\ThrowableBiConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */