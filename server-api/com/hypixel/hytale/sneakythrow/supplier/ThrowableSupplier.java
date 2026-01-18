/*    */ package com.hypixel.hytale.sneakythrow.supplier;
/*    */ 
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ThrowableSupplier<T, E extends Throwable>
/*    */   extends Supplier<T>
/*    */ {
/*    */   default T get() {
/*    */     try {
/* 13 */       return getNow();
/* 14 */     } catch (Throwable e) {
/* 15 */       throw SneakyThrow.sneakyThrow(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   T getNow() throws E;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\sneakythrow\supplier\ThrowableSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */