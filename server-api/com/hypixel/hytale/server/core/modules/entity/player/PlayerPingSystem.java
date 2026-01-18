/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.RootDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class PlayerPingSystem
/*    */   extends EntityTickingSystem<EntityStore>
/*    */   implements RunWhenPausedSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/* 25 */   private static final ComponentType<EntityStore, PlayerRef> PLAYER_REF_COMPONENT_TYPE = PlayerRef.getComponentType();
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 29 */     return (Query)PLAYER_REF_COMPONENT_TYPE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 36 */     return RootDependency.lastSet();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SystemGroup<EntityStore> getGroup() {
/* 42 */     return EntityStore.SEND_PACKET_GROUP;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 47 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 52 */     PlayerRef playerRefComponent = (PlayerRef)archetypeChunk.getComponent(index, PLAYER_REF_COMPONENT_TYPE);
/* 53 */     assert playerRefComponent != null;
/*    */     
/* 55 */     playerRefComponent.getPacketHandler().tickPing(dt);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerPingSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */