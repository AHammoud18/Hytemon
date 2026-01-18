/*    */ package com.hypixel.hytale.server.core.modules.entity.dynamiclight;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.DynamicLight;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.PersistentDynamicLight;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Setup
/*    */   extends HolderSystem<EntityStore> {
/* 18 */   private final ComponentType<EntityStore, DynamicLight> dynamicLightComponentType = DynamicLight.getComponentType();
/* 19 */   private final ComponentType<EntityStore, PersistentDynamicLight> persistentDynamicLightComponentType = PersistentDynamicLight.getComponentType();
/* 20 */   private final Query<EntityStore> query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.persistentDynamicLightComponentType, (Query)Query.not((Query)this.dynamicLightComponentType) });
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 24 */     PersistentDynamicLight persistentLight = (PersistentDynamicLight)holder.getComponent(this.persistentDynamicLightComponentType);
/* 25 */     holder.putComponent(this.dynamicLightComponentType, (Component)new DynamicLight(persistentLight.getColorLight()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 35 */     return this.query;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\dynamiclight\DynamicLightSystems$Setup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */