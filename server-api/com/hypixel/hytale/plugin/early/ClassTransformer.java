/*    */ package com.hypixel.hytale.plugin.early;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ClassTransformer
/*    */ {
/*    */   default int priority() {
/* 24 */     return 0;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   byte[] transform(@Nonnull String paramString1, @Nonnull String paramString2, @Nonnull byte[] paramArrayOfbyte);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\plugin\early\ClassTransformer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */