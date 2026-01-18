/*    */ package com.hypixel.hytale.builtin.adventure.npcshop.npc;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.npcshop.npc.builders.BuilderActionOpenShop;
/*    */ import com.hypixel.hytale.builtin.adventure.shop.ShopPage;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.CustomUIPage;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionOpenShop
/*    */   extends ActionBase {
/*    */   public ActionOpenShop(@Nonnull BuilderActionOpenShop builder, @Nonnull BuilderSupport support) {
/* 21 */     super((BuilderActionBase)builder);
/* 22 */     this.shopId = builder.getShopId(support);
/*    */   }
/*    */   protected final String shopId;
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 27 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 32 */     super.execute(ref, role, sensorInfo, dt, store);
/* 33 */     Ref<EntityStore> playerReference = role.getStateSupport().getInteractionIterationTarget();
/* 34 */     if (playerReference == null) return false;
/*    */     
/* 36 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(playerReference, PlayerRef.getComponentType());
/* 37 */     assert playerRefComponent != null;
/*    */     
/* 39 */     Player playerComponent = (Player)store.getComponent(playerReference, Player.getComponentType());
/* 40 */     assert playerComponent != null;
/*    */     
/* 42 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new ShopPage(playerRefComponent, this.shopId));
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcshop\npc\ActionOpenShop.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */