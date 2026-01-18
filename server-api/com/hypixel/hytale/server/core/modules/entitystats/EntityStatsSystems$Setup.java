/*    */ package com.hypixel.hytale.server.core.modules.entitystats;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.AllLegacyLivingEntityTypesQuery;
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
/*    */ public class Setup
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, EntityStatMap> componentType;
/*    */   
/*    */   public Setup(ComponentType<EntityStore, EntityStatMap> componentType) {
/* 48 */     this.componentType = componentType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 56 */     return (Query<EntityStore>)AllLegacyLivingEntityTypesQuery.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 61 */     EntityStatMap stats = (EntityStatMap)holder.getComponent(this.componentType);
/* 62 */     if (stats == null) {
/* 63 */       stats = (EntityStatMap)holder.ensureAndGetComponent(this.componentType);
/* 64 */       stats.update();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\EntityStatsSystems$Setup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */