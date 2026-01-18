/*    */ package com.hypixel.hytale.server.core.modules.entity.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.RootDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.SnapshotBuffer;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
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
/*    */ public class Resize
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 58 */     return RootDependency.firstSet();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 63 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 64 */     int tickLength = world.getTickStepNanos();
/* 65 */     SnapshotSystems.SnapshotWorldInfo info = (SnapshotSystems.SnapshotWorldInfo)store.getResource(SnapshotSystems.SnapshotWorldInfo.getResourceType());
/*    */ 
/*    */     
/* 68 */     if (tickLength == info.tickLengthNanos && SnapshotSystems.HISTORY_LENGTH_NS == info.historyLength)
/*    */       return; 
/* 70 */     info.historyLength = SnapshotSystems.HISTORY_LENGTH_NS;
/* 71 */     info.tickLengthNanos = tickLength;
/* 72 */     int previousHistorySize = info.historySize;
/*    */     
/* 74 */     info.historySize = Math.max(1, (int)((info.historyLength + tickLength - 1L) / tickLength));
/*    */ 
/*    */ 
/*    */     
/* 78 */     super.tick(dt, systemIndex, store);
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 83 */     return (Query<EntityStore>)SnapshotBuffer.getComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 88 */     SnapshotSystems.SnapshotWorldInfo info = (SnapshotSystems.SnapshotWorldInfo)store.getResource(SnapshotSystems.SnapshotWorldInfo.getResourceType());
/*    */     
/* 90 */     ((SnapshotBuffer)archetypeChunk.getComponent(index, SnapshotBuffer.getComponentType())).resize(info.historySize);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 95 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\SnapshotSystems$Resize.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */