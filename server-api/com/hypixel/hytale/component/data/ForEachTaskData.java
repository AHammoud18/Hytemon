/*    */ package com.hypixel.hytale.component.data;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.task.ParallelRangeTask;
/*    */ import com.hypixel.hytale.component.task.ParallelTask;
/*    */ import com.hypixel.hytale.function.consumer.IntBiObjectConsumer;
/*    */ import java.util.function.IntConsumer;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ForEachTaskData<ECS_TYPE>
/*    */   implements IntConsumer
/*    */ {
/*    */   @Nullable
/*    */   private IntBiObjectConsumer<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> consumer;
/*    */   @Nullable
/*    */   private ArchetypeChunk<ECS_TYPE> archetypeChunk;
/*    */   @Nullable
/*    */   private CommandBuffer<ECS_TYPE> commandBuffer;
/*    */   
/*    */   public void init(IntBiObjectConsumer<ArchetypeChunk<ECS_TYPE>, CommandBuffer<ECS_TYPE>> consumer, ArchetypeChunk<ECS_TYPE> archetypeChunk, CommandBuffer<ECS_TYPE> commandBuffer) {
/* 25 */     this.consumer = consumer;
/* 26 */     this.archetypeChunk = archetypeChunk;
/* 27 */     this.commandBuffer = commandBuffer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void accept(int index) {
/* 33 */     assert this.commandBuffer.setThread();
/* 34 */     this.consumer.accept(index, this.archetypeChunk, this.commandBuffer);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 38 */     this.consumer = null;
/* 39 */     this.archetypeChunk = null;
/* 40 */     this.commandBuffer = null;
/*    */   }
/*    */   
/*    */   public static <ECS_TYPE> void invokeParallelTask(@Nonnull ParallelTask<ForEachTaskData<ECS_TYPE>> parallelTask, @Nonnull CommandBuffer<ECS_TYPE> commandBuffer) {
/* 44 */     int parallelTaskSize = parallelTask.size();
/* 45 */     if (parallelTaskSize <= 0)
/* 46 */       return;  parallelTask.doInvoke();
/*    */ 
/*    */     
/* 49 */     for (int x = 0; x < parallelTaskSize; x++) {
/* 50 */       ParallelRangeTask<ForEachTaskData<ECS_TYPE>> systemTask = parallelTask.get(x);
/* 51 */       for (int i = 0, systemTaskSize = systemTask.size(); i < systemTaskSize; i++) {
/* 52 */         ForEachTaskData<ECS_TYPE> data = (ForEachTaskData<ECS_TYPE>)systemTask.get(i);
/* 53 */         data.commandBuffer.mergeParallel(commandBuffer);
/* 54 */         data.clear();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\data\ForEachTaskData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */