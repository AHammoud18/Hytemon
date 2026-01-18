/*    */ package com.hypixel.hytale.sneakythrow.supplier;
/*    */ 
/*    */ import com.hypixel.hytale.sneakythrow.SneakyThrow;
/*    */ import java.util.function.IntSupplier;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface ThrowableIntSupplier<E extends Throwable>
/*    */   extends IntSupplier
/*    */ {
/*    */   default int getAsInt() {
/*    */     try {
/* 13 */       return getAsIntNow();
/* 14 */     } catch (Throwable e) {
/* 15 */       throw SneakyThrow.sneakyThrow(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   int getAsIntNow() throws E;
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\sneakythrow\supplier\ThrowableIntSupplier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */