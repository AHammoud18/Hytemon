/*     */ package com.hypixel.hytale.component.system.tick;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.task.ParallelRangeTask;
/*     */ import com.hypixel.hytale.component.task.ParallelTask;
/*     */ import java.util.function.IntConsumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityTickingSystem<ECS_TYPE>
/*     */   extends ArchetypeTickingSystem<ECS_TYPE>
/*     */ {
/*     */   protected static boolean maybeUseParallel(int archetypeChunkSize, int taskCount) {
/*  19 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean useParallel(int archetypeChunkSize, int taskCount) {
/*  23 */     return (taskCount > 0 || archetypeChunkSize > ParallelRangeTask.PARALLELISM);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  31 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, @Nonnull ArchetypeChunk<ECS_TYPE> archetypeChunk, @Nonnull Store<ECS_TYPE> store, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer) {
/*  36 */     doTick(this, dt, archetypeChunk, store, commandBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void tick(float paramFloat, int paramInt, @Nonnull ArchetypeChunk<ECS_TYPE> paramArchetypeChunk, @Nonnull Store<ECS_TYPE> paramStore, @Nonnull CommandBuffer<ECS_TYPE> paramCommandBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <ECS_TYPE> void doTick(@Nonnull EntityTickingSystem<ECS_TYPE> system, float dt, @Nonnull ArchetypeChunk<ECS_TYPE> archetypeChunk, @Nonnull Store<ECS_TYPE> store, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer) {
/*  77 */     int archetypeChunkSize = archetypeChunk.size();
/*  78 */     if (archetypeChunkSize == 0)
/*     */       return; 
/*  80 */     ParallelTask<SystemTaskData<ECS_TYPE>> task = store.getParallelTask();
/*  81 */     if (system.isParallel(archetypeChunkSize, task.size())) {
/*  82 */       ParallelRangeTask<SystemTaskData<ECS_TYPE>> systemTask = task.appendTask();
/*  83 */       systemTask.init(0, archetypeChunkSize);
/*  84 */       for (int i = 0, systemTaskSize = systemTask.size(); i < systemTaskSize; i++) {
/*  85 */         ((SystemTaskData<ECS_TYPE>)systemTask.get(i)).init(system, dt, archetypeChunk, store, commandBuffer.fork());
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  91 */     for (int index = 0; index < archetypeChunkSize; index++)
/*  92 */       system.tick(dt, index, archetypeChunk, store, commandBuffer); 
/*     */   }
/*     */   
/*     */   public static class SystemTaskData<ECS_TYPE>
/*     */     implements IntConsumer {
/*     */     @Nullable
/*     */     private EntityTickingSystem<ECS_TYPE> system;
/*     */     private float dt;
/*     */     @Nullable
/*     */     private ArchetypeChunk<ECS_TYPE> archetypeChunk;
/*     */     @Nullable
/*     */     private Store<ECS_TYPE> store;
/*     */     @Nullable
/*     */     private CommandBuffer<ECS_TYPE> commandBuffer;
/*     */     
/*     */     public void init(EntityTickingSystem<ECS_TYPE> system, float dt, ArchetypeChunk<ECS_TYPE> archetypeChunk, Store<ECS_TYPE> store, CommandBuffer<ECS_TYPE> commandBuffer) {
/* 108 */       this.system = system;
/* 109 */       this.dt = dt;
/* 110 */       this.archetypeChunk = archetypeChunk;
/* 111 */       this.store = store;
/* 112 */       this.commandBuffer = commandBuffer;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(int index) {
/* 118 */       assert this.commandBuffer.setThread();
/* 119 */       this.system.tick(this.dt, index, this.archetypeChunk, this.store, this.commandBuffer);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 123 */       this.system = null;
/* 124 */       this.archetypeChunk = null;
/* 125 */       this.store = null;
/* 126 */       this.commandBuffer = null;
/*     */     }
/*     */     
/*     */     public static <ECS_TYPE> void invokeParallelTask(@Nonnull ParallelTask<SystemTaskData<ECS_TYPE>> parallelTask, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer) {
/* 130 */       int parallelTaskSize = parallelTask.size();
/* 131 */       if (parallelTaskSize <= 0)
/* 132 */         return;  parallelTask.doInvoke();
/*     */ 
/*     */       
/* 135 */       for (int x = 0; x < parallelTaskSize; x++) {
/* 136 */         ParallelRangeTask<SystemTaskData<ECS_TYPE>> systemTask = parallelTask.get(x);
/* 137 */         for (int i = 0, systemTaskSize = systemTask.size(); i < systemTaskSize; i++) {
/* 138 */           SystemTaskData<ECS_TYPE> taskData = (SystemTaskData<ECS_TYPE>)systemTask.get(i);
/* 139 */           taskData.commandBuffer.mergeParallel(commandBuffer);
/* 140 */           taskData.clear();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\tick\EntityTickingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */