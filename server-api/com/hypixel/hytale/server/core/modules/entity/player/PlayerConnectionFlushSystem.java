/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.Order;
/*    */ import com.hypixel.hytale.component.dependency.OrderPriority;
/*    */ import com.hypixel.hytale.component.dependency.RootDependency;
/*    */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*    */ import com.hypixel.hytale.component.dependency.SystemGroupDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.ArchetypeTickingSystem;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*    */ import com.hypixel.hytale.server.core.Constants;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PlayerConnectionFlushSystem extends EntityTickingSystem<EntityStore> implements RunWhenPausedSystem<EntityStore> {
/* 23 */   public static final Set<Dependency<EntityStore>> DEPENDENCIES = (Set)Set.of(new SystemGroupDependency(Order.AFTER, EntityStore.SEND_PACKET_GROUP), new SystemDependency(Order.AFTER, PlayerPingSystem.class, OrderPriority.CLOSEST), 
/*    */ 
/*    */       
/* 26 */       RootDependency.last());
/*    */   
/*    */   private final ComponentType<EntityStore, PlayerRef> componentType;
/*    */ 
/*    */   
/*    */   public PlayerConnectionFlushSystem(ComponentType<EntityStore, PlayerRef> componentType) {
/* 32 */     this.componentType = componentType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 38 */     return DEPENDENCIES;
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 43 */     return (Query)this.componentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 48 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 56 */     if (Constants.FORCE_NETWORK_FLUSH) {
/* 57 */       store.tick((ArchetypeTickingSystem)this, dt, systemIndex);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 63 */     ((PlayerRef)archetypeChunk.getComponent(index, this.componentType)).getPacketHandler().tryFlush();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerConnectionFlushSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */