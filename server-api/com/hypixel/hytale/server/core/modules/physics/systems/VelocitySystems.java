/*    */ package com.hypixel.hytale.server.core.modules.physics.systems;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.AllLegacyEntityTypesQuery;
/*    */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class VelocitySystems
/*    */ {
/*    */   public static class AddSystem
/*    */     extends HolderSystem<EntityStore> {
/*    */     private final ComponentType<EntityStore, Velocity> velocityComponentType;
/*    */     @Nonnull
/*    */     private final Query<EntityStore> query;
/*    */     
/*    */     public AddSystem(ComponentType<EntityStore, Velocity> velocityComponentType) {
/* 24 */       this.velocityComponentType = velocityComponentType;
/* 25 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)AllLegacyEntityTypesQuery.INSTANCE, (Query)Query.not((Query)velocityComponentType) });
/*    */     }
/*    */ 
/*    */     
/*    */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 30 */       holder.ensureComponent(this.velocityComponentType);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public Query<EntityStore> getQuery() {
/* 41 */       return this.query;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\physics\systems\VelocitySystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */