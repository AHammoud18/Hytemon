/*    */ package com.hypixel.hytale.server.spawning.jobs;
/*    */ 
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.server.spawning.ISpawnableWithModel;
/*    */ import com.hypixel.hytale.server.spawning.SpawningContext;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SpawnJob
/*    */ {
/* 17 */   protected static final HytaleLogger LOGGER = SpawningPlugin.get().getLogger();
/*    */   
/*    */   private static int jobIdCounter;
/*    */   
/*    */   protected final int jobId;
/* 22 */   protected final SpawningContext spawningContext = new SpawningContext();
/*    */   
/*    */   protected int columnBudget;
/*    */   
/*    */   protected int budgetUsed;
/*    */   protected boolean terminated;
/*    */   
/*    */   public SpawnJob() {
/* 30 */     this.jobId = jobIdCounter++;
/*    */   }
/*    */   
/*    */   public int getJobId() {
/* 34 */     return this.jobId;
/*    */   }
/*    */   
/*    */   public int getBudgetUsed() {
/* 38 */     return this.budgetUsed;
/*    */   }
/*    */   
/*    */   public void setBudgetUsed(int budgetUsed) {
/* 42 */     this.budgetUsed = budgetUsed;
/*    */   }
/*    */   
/*    */   public int getColumnBudget() {
/* 46 */     return this.columnBudget;
/*    */   }
/*    */   
/*    */   public void setColumnBudget(int columnBudget) {
/* 50 */     this.columnBudget = columnBudget;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public SpawningContext getSpawningContext() {
/* 55 */     return this.spawningContext;
/*    */   }
/*    */   
/*    */   protected void beginProbing() {
/* 59 */     reset();
/* 60 */     this.terminated = false;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 64 */     this.spawningContext.releaseFull();
/*    */   }
/*    */   
/*    */   public boolean budgetAvailable() {
/* 68 */     return (this.budgetUsed < this.columnBudget);
/*    */   }
/*    */   
/*    */   public boolean isTerminated() {
/* 72 */     return this.terminated;
/*    */   }
/*    */   
/*    */   public void setTerminated(boolean terminated) {
/* 76 */     this.terminated = terminated;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public abstract ISpawnableWithModel getSpawnable();
/*    */   
/*    */   public abstract boolean shouldTerminate();
/*    */   
/*    */   @Nullable
/*    */   public abstract String getSpawnableName();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\jobs\SpawnJob.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */