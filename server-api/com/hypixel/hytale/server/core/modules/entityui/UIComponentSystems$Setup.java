/*    */ package com.hypixel.hytale.server.core.modules.entityui;
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
/*    */ public class Setup
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, UIComponentList> uiComponentListComponentType;
/*    */   
/*    */   public Setup(ComponentType<EntityStore, UIComponentList> uiComponentListType) {
/* 23 */     this.uiComponentListComponentType = uiComponentListType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 28 */     UIComponentList components = (UIComponentList)holder.getComponent(this.uiComponentListComponentType);
/* 29 */     if (components == null) {
/* 30 */       components = (UIComponentList)holder.ensureAndGetComponent(this.uiComponentListComponentType);
/* 31 */       components.update();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 42 */     return (Query<EntityStore>)AllLegacyLivingEntityTypesQuery.INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entityui\UIComponentSystems$Setup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */