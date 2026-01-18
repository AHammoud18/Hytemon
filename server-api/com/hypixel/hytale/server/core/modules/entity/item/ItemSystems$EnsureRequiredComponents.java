/*    */ package com.hypixel.hytale.server.core.modules.entity.item;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.math.shape.Box;
/*    */ import com.hypixel.hytale.protocol.ColorLight;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.BoundingBox;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.DynamicLight;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
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
/*    */ public class EnsureRequiredComponents
/*    */   extends HolderSystem<EntityStore>
/*    */ {
/* 30 */   private static final ComponentType<EntityStore, ItemComponent> ITEM_COMPONENT_TYPE = ItemComponent.getComponentType();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 35 */     return (Query)ITEM_COMPONENT_TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 40 */     if (!holder.getArchetype().contains(NetworkId.getComponentType())) {
/* 41 */       holder.addComponent(NetworkId.getComponentType(), (Component)new NetworkId(((EntityStore)store.getExternalData()).takeNextNetworkId()));
/*    */     }
/* 43 */     holder.ensureComponent(ItemPhysicsComponent.getComponentType());
/*    */ 
/*    */ 
/*    */     
/* 47 */     holder.putComponent(BoundingBox.getComponentType(), (Component)new BoundingBox(Box.horizontallyCentered(0.5D, 0.5D, 0.5D)));
/*    */     
/* 49 */     ItemComponent itemComponent = (ItemComponent)holder.getComponent(ItemComponent.getComponentType());
/* 50 */     assert itemComponent != null;
/*    */     
/* 52 */     ColorLight itemDynamicLight = itemComponent.computeDynamicLight();
/* 53 */     if (itemDynamicLight != null)
/* 54 */       holder.putComponent(DynamicLight.getComponentType(), (Component)new DynamicLight(itemDynamicLight)); 
/*    */   }
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\item\ItemSystems$EnsureRequiredComponents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */