/*    */ package com.hypixel.hytale.builtin.adventure.npcshop.npc;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.npcshop.npc.builders.BuilderActionOpenBarterShop;
/*    */ import com.hypixel.hytale.builtin.adventure.shop.barter.BarterPage;
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
/*    */ public class ActionOpenBarterShop
/*    */   extends ActionBase
/*    */ {
/*    */   protected final String shopId;
/*    */   
/*    */   public ActionOpenBarterShop(@Nonnull BuilderActionOpenBarterShop builder, @Nonnull BuilderSupport support) {
/* 24 */     super((BuilderActionBase)builder);
/* 25 */     this.shopId = builder.getShopId(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 35 */     super.execute(ref, role, sensorInfo, dt, store);
/* 36 */     Ref<EntityStore> playerReference = role.getStateSupport().getInteractionIterationTarget();
/* 37 */     if (playerReference == null) return false;
/*    */     
/* 39 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(playerReference, PlayerRef.getComponentType());
/* 40 */     if (playerRefComponent == null) return false;
/*    */     
/* 42 */     Player playerComponent = (Player)store.getComponent(playerReference, Player.getComponentType());
/* 43 */     if (playerComponent == null) return false;
/*    */     
/* 45 */     playerComponent.getPageManager().openCustomPage(ref, store, (CustomUIPage)new BarterPage(playerRefComponent, this.shopId));
/* 46 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcshop\npc\ActionOpenBarterShop.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */