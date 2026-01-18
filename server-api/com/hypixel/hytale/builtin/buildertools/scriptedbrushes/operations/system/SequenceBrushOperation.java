/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigEditStore;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ 
/*    */ public abstract class SequenceBrushOperation extends BrushOperation {
/*    */   private final boolean doesOperateOnBlocks;
/*    */   
/*    */   public SequenceBrushOperation(String name, String description, boolean doesOperateOnBlocks) {
/* 14 */     super(name, description);
/* 15 */     this.doesOperateOnBlocks = doesOperateOnBlocks;
/*    */   }
/*    */   
/*    */   public boolean modifyBlocks(Ref<EntityStore> ref, BrushConfig brushConfig, BrushConfigCommandExecutor brushConfigCommandExecutor, BrushConfigEditStore edit, int x, int y, int z, ComponentAccessor<EntityStore> componentAccessor) {
/* 19 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void beginIterationIndex(int iterationIndex) {}
/*    */ 
/*    */   
/*    */   public int getNumModifyBlockIterations() {
/* 27 */     return 1;
/*    */   }
/*    */   
/*    */   public boolean doesOperateOnBlocks() {
/* 31 */     return this.doesOperateOnBlocks;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\operations\system\SequenceBrushOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */