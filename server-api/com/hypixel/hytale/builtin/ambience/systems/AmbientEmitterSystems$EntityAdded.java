/*    */ package com.hypixel.hytale.builtin.ambience.systems;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.ambience.components.AmbientEmitterComponent;
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Intangible;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabCopyableComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAdded
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/* 28 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)AmbientEmitterComponent.getComponentType(), (Query)TransformComponent.getComponentType() });
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 32 */     if (!holder.getArchetype().contains(NetworkId.getComponentType())) {
/* 33 */       holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*    */     }
/*    */     
/* 36 */     holder.ensureComponent(Intangible.getComponentType());
/* 37 */     holder.ensureComponent(PrefabCopyableComponent.getComponentType());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */   
/*    */   public Query<EntityStore> getQuery() {
/* 46 */     return this.query;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\ambience\systems\AmbientEmitterSystems$EntityAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */