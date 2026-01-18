/*    */ package com.hypixel.hytale.sneakythrow.consumer;
/*    */ 
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ThrowableConsumer<T, E extends Throwable>
/*    */   extends Consumer<T>
/*    */ {
/*    */   default void accept(T t) {
/*    */     try {
/* 13 */       acceptNow(t);
/* 14 */     } catch (Throwable e) {
/* 15 */       throw SneakyThrow.sneakyThrow(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   void acceptNow(T paramT) throws E;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\sneakythrow\consumer\ThrowableConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */