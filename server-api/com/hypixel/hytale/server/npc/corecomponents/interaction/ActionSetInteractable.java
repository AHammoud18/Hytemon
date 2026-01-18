/*    */ package com.hypixel.hytale.server.npc.corecomponents.interaction;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.interaction.builders.BuilderActionSetInteractable;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.StateSupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ActionSetInteractable
/*    */   extends ActionBase
/*    */ {
/*    */   protected final boolean setTo;
/*    */   
/*    */   public ActionSetInteractable(@Nonnull BuilderActionSetInteractable builder, @Nonnull BuilderSupport support) {
/* 22 */     super((BuilderActionBase)builder);
/* 23 */     this.setTo = builder.getSetTo(support);
/* 24 */     this.hint = builder.getHint();
/* 25 */     this.showPrompt = builder.getShowPrompt();
/*    */   }
/*    */   @Nullable
/*    */   protected final String hint; protected final boolean showPrompt;
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 35 */     super.execute(ref, role, sensorInfo, dt, store);
/* 36 */     StateSupport stateSupport = role.getStateSupport();
/* 37 */     stateSupport.setInteractable(ref, stateSupport.getInteractionIterationTarget(), this.setTo, this.hint, this.showPrompt, store);
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\interaction\ActionSetInteractable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */