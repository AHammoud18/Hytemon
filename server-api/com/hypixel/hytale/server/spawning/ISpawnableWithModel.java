/*    */ package com.hypixel.hytale.server.spawning;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.util.expression.ExecutionContext;
/*    */ import com.hypixel.hytale.server.npc.util.expression.Scope;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISpawnableWithModel
/*    */   extends ISpawnable
/*    */ {
/*    */   @Nullable
/*    */   String getSpawnModelName(ExecutionContext paramExecutionContext, Scope paramScope);
/*    */   
/*    */   @Nullable
/*    */   default Scope createModifierScope(ExecutionContext executionContext) {
/* 18 */     throw new IllegalStateException("Call to createModifierScope not valid for ISpawnableWithModel");
/*    */   }
/*    */   
/*    */   Scope createExecutionScope();
/*    */   
/*    */   void markNeedsReload();
/*    */   
/*    */   boolean isMemory(ExecutionContext paramExecutionContext, @Nullable Scope paramScope);
/*    */   
/*    */   String getMemoriesCategory(ExecutionContext paramExecutionContext, @Nullable Scope paramScope);
/*    */   
/*    */   String getMemoriesNameOverride(ExecutionContext paramExecutionContext, @Nullable Scope paramScope);
/*    */   
/*    */   @Nonnull
/*    */   String getNameTranslationKey(ExecutionContext paramExecutionContext, @Nullable Scope paramScope);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\ISpawnableWithModel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */