/*    */ package com.hypixel.hytale.server.core.universe.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.RootDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
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
/*    */ public class PlayerRefAddedSystem
/*    */   extends RefSystem<EntityStore>
/*    */ {
/*    */   @Nonnull
/*    */   private final ComponentType<EntityStore, PlayerRef> playerRefComponentType;
/*    */   
/*    */   public PlayerRefAddedSystem(@Nonnull ComponentType<EntityStore, PlayerRef> playerRefComponentType) {
/* 31 */     this.playerRefComponentType = playerRefComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<EntityStore>> getDependencies() {
/* 37 */     return RootDependency.firstSet();
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 42 */     return (Query)this.playerRefComponentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 47 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, this.playerRefComponentType);
/* 48 */     assert playerRefComponent != null;
/*    */     
/* 50 */     playerRefComponent.addedToStore(ref);
/* 51 */     ((EntityStore)store.getExternalData()).getWorld().trackPlayerRef(playerRefComponent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 56 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, this.playerRefComponentType);
/* 57 */     assert playerRefComponent != null;
/*    */     
/* 59 */     ((EntityStore)store.getExternalData()).getWorld().untrackPlayerRef(playerRefComponent);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\system\PlayerRefAddedSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */