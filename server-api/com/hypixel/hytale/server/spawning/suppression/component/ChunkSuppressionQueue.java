/*    */ package com.hypixel.hytale.server.spawning.suppression.component;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkSuppressionQueue
/*    */   implements Resource<ChunkStore>
/*    */ {
/*    */   public static ResourceType<ChunkStore, ChunkSuppressionQueue> getResourceType() {
/* 25 */     return SpawningPlugin.get().getChunkSuppressionQueueResourceType();
/*    */   }
/*    */   
/* 28 */   private final List<Map.Entry<Ref<ChunkStore>, ChunkSuppressionEntry>> toAdd = (List<Map.Entry<Ref<ChunkStore>, ChunkSuppressionEntry>>)new ObjectArrayList();
/* 29 */   private final List<Ref<ChunkStore>> toRemove = (List<Ref<ChunkStore>>)new ObjectArrayList();
/*    */   
/*    */   @Nonnull
/*    */   public List<Map.Entry<Ref<ChunkStore>, ChunkSuppressionEntry>> getToAdd() {
/* 33 */     return this.toAdd;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public List<Ref<ChunkStore>> getToRemove() {
/* 38 */     return this.toRemove;
/*    */   }
/*    */   
/*    */   public void queueForAdd(@Nonnull Ref<ChunkStore> reference, @Nonnull ChunkSuppressionEntry entry) {
/* 42 */     this.toAdd.add(Map.entry(reference, entry));
/*    */   }
/*    */   
/*    */   public void queueForRemove(Ref<ChunkStore> reference) {
/* 46 */     this.toRemove.add(reference);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<ChunkStore> clone() {
/* 53 */     ChunkSuppressionQueue queue = new ChunkSuppressionQueue();
/* 54 */     queue.toAdd.addAll(this.toAdd);
/* 55 */     queue.toRemove.addAll(this.toRemove);
/* 56 */     return queue;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\component\ChunkSuppressionQueue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */