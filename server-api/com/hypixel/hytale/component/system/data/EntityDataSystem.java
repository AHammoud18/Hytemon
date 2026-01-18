/*     */ package com.hypixel.hytale.component.system.data;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.task.ParallelRangeTask;
/*     */ import com.hypixel.hytale.component.task.ParallelTask;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
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
/*     */ public abstract class EntityDataSystem<ECS_TYPE, Q, R>
/*     */   extends ArchetypeDataSystem<ECS_TYPE, Q, R>
/*     */ {
/*     */   public boolean isParallel() {
/*  24 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fetch(@Nonnull ArchetypeChunk<ECS_TYPE> archetypeChunk, @Nonnull Store<ECS_TYPE> store, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer, Q query, List<R> results) {
/*  29 */     doFetch(this, archetypeChunk, store, commandBuffer, query, results);
/*     */   }
/*     */   
/*     */   public abstract void fetch(int paramInt, ArchetypeChunk<ECS_TYPE> paramArchetypeChunk, Store<ECS_TYPE> paramStore, CommandBuffer<ECS_TYPE> paramCommandBuffer, Q paramQ, List<R> paramList);
/*     */   
/*     */   public static <ECS_TYPE, Q, R> void doFetch(@Nonnull EntityDataSystem<ECS_TYPE, Q, R> system, @Nonnull ArchetypeChunk<ECS_TYPE> archetypeChunk, @Nonnull Store<ECS_TYPE> store, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer, Q query, List<R> results) {
/*  35 */     if (system.isParallel()) {
/*  36 */       int size = archetypeChunk.size();
/*  37 */       if (size == 0)
/*     */         return; 
/*  39 */       ParallelTask<SystemTaskData<ECS_TYPE, ?, ?>> task = store.getFetchTask();
/*  40 */       ParallelRangeTask<SystemTaskData<ECS_TYPE, ?, ?>> systemTask = task.appendTask();
/*     */       
/*  42 */       systemTask.init(0, size);
/*     */       
/*  44 */       for (int i = 0, systemTaskSize = systemTask.size(); i < systemTaskSize; i++)
/*     */       {
/*  46 */         ((SystemTaskData<ECS_TYPE, Q, R>)systemTask.get(i)).init(system, archetypeChunk, store, commandBuffer.fork(), query);
/*     */       }
/*     */     } else {
/*  49 */       for (int index = 0, archetypeChunkSize = archetypeChunk.size(); index < archetypeChunkSize; index++)
/*  50 */         system.fetch(index, archetypeChunk, store, commandBuffer, query, results); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class SystemTaskData<ECS_TYPE, Q, R>
/*     */     implements IntConsumer {
/*  56 */     private final List<R> results = (List<R>)new ObjectArrayList();
/*     */     
/*     */     @Nullable
/*     */     private EntityDataSystem<ECS_TYPE, Q, R> system;
/*     */     @Nullable
/*     */     private ArchetypeChunk<ECS_TYPE> archetypeChunk;
/*     */     @Nullable
/*     */     private Store<ECS_TYPE> store;
/*     */     @Nullable
/*     */     private CommandBuffer<ECS_TYPE> commandBuffer;
/*     */     @Nullable
/*     */     private Q query;
/*     */     
/*     */     public void init(EntityDataSystem<ECS_TYPE, Q, R> system, ArchetypeChunk<ECS_TYPE> archetypeChunk, Store<ECS_TYPE> store, CommandBuffer<ECS_TYPE> commandBuffer, Q query) {
/*  70 */       this.system = system;
/*  71 */       this.archetypeChunk = archetypeChunk;
/*  72 */       this.store = store;
/*  73 */       this.commandBuffer = commandBuffer;
/*  74 */       this.query = query;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void accept(int index) {
/*  80 */       assert this.commandBuffer.setThread();
/*  81 */       this.system.fetch(index, this.archetypeChunk, this.store, this.commandBuffer, this.query, this.results);
/*     */     }
/*     */     
/*     */     public void clear() {
/*  85 */       this.system = null;
/*  86 */       this.archetypeChunk = null;
/*  87 */       this.store = null;
/*  88 */       this.commandBuffer = null;
/*  89 */       this.query = null;
/*  90 */       this.results.clear();
/*     */     }
/*     */     
/*     */     public static <ECS_TYPE, Q, R> void invokeParallelTask(@Nonnull ParallelTask<SystemTaskData<ECS_TYPE, Q, R>> parallelTask, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer, @Nonnull List<R> results) {
/*  94 */       int parallelTaskSize = parallelTask.size();
/*     */       
/*  96 */       if (parallelTaskSize > 0) {
/*  97 */         parallelTask.doInvoke();
/*     */ 
/*     */         
/* 100 */         for (int x = 0; x < parallelTaskSize; x++) {
/* 101 */           ParallelRangeTask<SystemTaskData<ECS_TYPE, Q, R>> systemTask = parallelTask.get(x);
/* 102 */           for (int i = 0, systemTaskSize = systemTask.size(); i < systemTaskSize; i++) {
/* 103 */             SystemTaskData<ECS_TYPE, Q, R> taskData = (SystemTaskData<ECS_TYPE, Q, R>)systemTask.get(i);
/* 104 */             results.addAll(taskData.results);
/* 105 */             taskData.commandBuffer.mergeParallel(commandBuffer);
/* 106 */             taskData.clear();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\system\data\EntityDataSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */