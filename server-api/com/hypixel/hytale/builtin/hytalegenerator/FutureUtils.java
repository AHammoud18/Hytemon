/*    */ package com.hypixel.hytale.builtin.hytalegenerator;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FutureUtils
/*    */ {
/*    */   public static <T> CompletableFuture<Void> allOf(@Nonnull List<CompletableFuture<T>> tasks) {
/* 10 */     return CompletableFuture.allOf((CompletableFuture<?>[])tasks.<CompletableFuture>toArray(new CompletableFuture[tasks.size()]));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\FutureUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */