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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SystemTaskData<ECS_TYPE>
/*     */   implements IntConsumer
/*     */ {
/*     */   @Nullable
/*     */   private EntityTickingSystem<ECS_TYPE> system;
/*     */   private float dt;
/*     */   @Nullable
/*     */   private ArchetypeChunk<ECS_TYPE> archetypeChunk;
/*     */   @Nullable
/*     */   private Store<ECS_TYPE> store;
/*     */   @Nullable
/*     */   private CommandBuffer<ECS_TYPE> commandBuffer;
/*     */   
/*     */   public void init(EntityTickingSystem<ECS_TYPE> system, float dt, ArchetypeChunk<ECS_TYPE> archetypeChunk, Store<ECS_TYPE> store, CommandBuffer<ECS_TYPE> commandBuffer) {
/* 108 */     this.system = system;
/* 109 */     this.dt = dt;
/* 110 */     this.archetypeChunk = archetypeChunk;
/* 111 */     this.store = store;
/* 112 */     this.commandBuffer = commandBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(int index) {
/* 118 */     assert this.commandBuffer.setThread();
/* 119 */     this.system.tick(this.dt, index, this.archetypeChunk, this.store, this.commandBuffer);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 123 */     this.system = null;
/* 124 */     this.archetypeChunk = null;
/* 125 */     this.store = null;
/* 126 */     this.commandBuffer = null;
/*     */   }
/*     */   
/*     */   public static <ECS_TYPE> void invokeParallelTask(@Nonnull ParallelTask<SystemTaskData<ECS_TYPE>> parallelTask, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer) {
/* 130 */     int parallelTaskSize = parallelTask.size();
/* 131 */     if (parallelTaskSize <= 0)
/* 132 */       return;  parallelTask.doInvoke();
/*     */ 
/*     */     
/* 135 */     for (int x = 0; x < parallelTaskSize; x++) {
/* 136 */       ParallelRangeTask<SystemTaskData<ECS_TYPE>> systemTask = parallelTask.get(x);
/* 137 */       for (int i = 0, systemTaskSize = systemTask.size(); i < systemTaskSize; i++) {
/* 138 */         SystemTaskData<ECS_TYPE> taskData = (SystemTaskData<ECS_TYPE>)systemTask.get(i);
/* 139 */         taskData.commandBuffer.mergeParallel(commandBuffer);
/* 140 */         taskData.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\tick\EntityTickingSystem$SystemTaskData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */