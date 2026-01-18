/*     */ package com.hypixel.hytale.server.core.modules.entity.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.OrderPriority;
/*     */ import com.hypixel.hytale.component.dependency.RootDependency;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.SnapshotBuffer;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nonnull;
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
/*     */ public class SnapshotSystems
/*     */ {
/*  46 */   public static long HISTORY_LENGTH_NS = TimeUnit.MILLISECONDS.toNanos(500L);
/*     */   
/*  48 */   private static final HytaleLogger LOGGER = HytaleLogger.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Resize
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/*  58 */       return RootDependency.firstSet();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/*  63 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*  64 */       int tickLength = world.getTickStepNanos();
/*  65 */       SnapshotSystems.SnapshotWorldInfo info = (SnapshotSystems.SnapshotWorldInfo)store.getResource(SnapshotSystems.SnapshotWorldInfo.getResourceType());
/*     */ 
/*     */       
/*  68 */       if (tickLength == info.tickLengthNanos && SnapshotSystems.HISTORY_LENGTH_NS == info.historyLength)
/*     */         return; 
/*  70 */       info.historyLength = SnapshotSystems.HISTORY_LENGTH_NS;
/*  71 */       info.tickLengthNanos = tickLength;
/*  72 */       int previousHistorySize = info.historySize;
/*     */       
/*  74 */       info.historySize = Math.max(1, (int)((info.historyLength + tickLength - 1L) / tickLength));
/*     */ 
/*     */ 
/*     */       
/*  78 */       super.tick(dt, systemIndex, store);
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/*  83 */       return (Query<EntityStore>)SnapshotBuffer.getComponentType();
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  88 */       SnapshotSystems.SnapshotWorldInfo info = (SnapshotSystems.SnapshotWorldInfo)store.getResource(SnapshotSystems.SnapshotWorldInfo.getResourceType());
/*     */       
/*  90 */       ((SnapshotBuffer)archetypeChunk.getComponent(index, SnapshotBuffer.getComponentType())).resize(info.historySize);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  95 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Add
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 105 */       SnapshotBuffer buffer = (SnapshotBuffer)holder.ensureAndGetComponent(SnapshotBuffer.getComponentType());
/* 106 */       buffer.resize(((SnapshotSystems.SnapshotWorldInfo)store.getResource(SnapshotSystems.SnapshotWorldInfo.getResourceType())).historySize);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/* 116 */       return (Query<EntityStore>)TransformComponent.getComponentType();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Capture
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/* 126 */     private static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemDependency(Order.AFTER, SnapshotSystems.Resize.class), new RootDependency(OrderPriority.CLOSEST));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 135 */     private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] {
/* 136 */           (Query)TransformComponent.getComponentType(), 
/* 137 */           (Query)SnapshotBuffer.getComponentType()
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*     */     public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 143 */       SnapshotSystems.SnapshotWorldInfo info = (SnapshotSystems.SnapshotWorldInfo)store.getResource(SnapshotSystems.SnapshotWorldInfo.getResourceType());
/* 144 */       info.currentTick++;
/* 145 */       super.tick(dt, systemIndex, store);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 150 */       SnapshotBuffer buffer = (SnapshotBuffer)archetypeChunk.getComponent(index, SnapshotBuffer.getComponentType());
/* 151 */       TransformComponent transform = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 152 */       SnapshotSystems.SnapshotWorldInfo info = (SnapshotSystems.SnapshotWorldInfo)store.getResource(SnapshotSystems.SnapshotWorldInfo.getResourceType());
/*     */       
/* 154 */       buffer.storeSnapshot(info.currentTick, transform.getPosition(), transform.getRotation());
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 160 */       return this.query;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Set<Dependency<EntityStore>> getDependencies() {
/* 166 */       return DEPENDENCIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 171 */       return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SnapshotWorldInfo implements Resource<EntityStore> {
/*     */     public static ResourceType<EntityStore, SnapshotWorldInfo> getResourceType() {
/* 177 */       return EntityModule.get().getSnapshotWorldInfoResourceType();
/*     */     }
/*     */ 
/*     */     
/* 181 */     private int tickLengthNanos = -1;
/* 182 */     private long historyLength = -1L;
/* 183 */     private int historySize = 1;
/* 184 */     private int currentTick = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SnapshotWorldInfo(int tickLengthNanos, long historyLength, int historySize, int currentTick) {
/* 190 */       this.tickLengthNanos = tickLengthNanos;
/* 191 */       this.historyLength = historyLength;
/* 192 */       this.historySize = historySize;
/* 193 */       this.currentTick = currentTick;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Resource<EntityStore> clone() {
/* 199 */       return new SnapshotWorldInfo(this.tickLengthNanos, this.historyLength, this.historySize, this.currentTick);
/*     */     }
/*     */     
/*     */     public SnapshotWorldInfo() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\SnapshotSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */