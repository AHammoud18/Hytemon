/*    */ package com.hypixel.hytale.server.spawning.suppression.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionEntry;
/*    */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionQueue;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
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
/*    */ public class Ticking
/*    */   extends TickingSystem<ChunkStore>
/*    */ {
/*    */   private final ComponentType<ChunkStore, ChunkSuppressionEntry> chunkSuppressionEntryComponentType;
/*    */   private final ResourceType<ChunkStore, ChunkSuppressionQueue> chunkSuppressionQueueResourceType;
/*    */   
/*    */   public Ticking(ComponentType<ChunkStore, ChunkSuppressionEntry> chunkSuppressionEntryComponentType, ResourceType<ChunkStore, ChunkSuppressionQueue> chunkSuppressionQueueResourceType) {
/* 65 */     this.chunkSuppressionEntryComponentType = chunkSuppressionEntryComponentType;
/* 66 */     this.chunkSuppressionQueueResourceType = chunkSuppressionQueueResourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<ChunkStore> store) {
/* 71 */     ChunkSuppressionQueue queue = (ChunkSuppressionQueue)store.getResource(this.chunkSuppressionQueueResourceType);
/* 72 */     List<Map.Entry<Ref<ChunkStore>, ChunkSuppressionEntry>> addQueue = queue.getToAdd();
/* 73 */     if (!addQueue.isEmpty()) {
/* 74 */       for (int i = 0; i < addQueue.size(); i++) {
/* 75 */         Map.Entry<Ref<ChunkStore>, ChunkSuppressionEntry> entry = addQueue.get(i);
/* 76 */         Ref<ChunkStore> ref = entry.getKey();
/* 77 */         store.putComponent(ref, this.chunkSuppressionEntryComponentType, (Component)entry.getValue());
/* 78 */         SpawningPlugin.get().getLogger().at(Level.FINEST).log("Annotated chunk %s from queue", ref);
/*    */       } 
/* 80 */       addQueue.clear();
/*    */     } 
/*    */     
/* 83 */     List<Ref<ChunkStore>> removeQueue = queue.getToRemove();
/* 84 */     if (!removeQueue.isEmpty()) {
/* 85 */       for (int i = 0; i < removeQueue.size(); i++) {
/* 86 */         Ref<ChunkStore> ref = removeQueue.get(i);
/* 87 */         store.tryRemoveComponent(ref, this.chunkSuppressionEntryComponentType);
/* 88 */         SpawningPlugin.get().getLogger().at(Level.FINEST).log("Removed annotation from chunk %s from queue", ref);
/*    */       } 
/* 90 */       removeQueue.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\system\ChunkSuppressionSystems$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */