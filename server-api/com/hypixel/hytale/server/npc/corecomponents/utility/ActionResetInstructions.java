/*    */ package com.hypixel.hytale.server.npc.corecomponents.utility;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.utility.builders.BuilderActionResetInstructions;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionResetInstructions extends ActionBase {
/*    */   protected final int[] instructions;
/*    */   
/*    */   public ActionResetInstructions(@Nonnull BuilderActionResetInstructions builder, @Nonnull BuilderSupport support) {
/* 18 */     super((BuilderActionBase)builder);
/* 19 */     this.instructions = builder.getInstructions(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 24 */     super.execute(ref, role, sensorInfo, dt, store);
/* 25 */     role.addDeferredAction((_ref, _role, _dt, _store) -> resetInstructions(_role, _dt));
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   protected boolean resetInstructions(@Nonnull Role role, double dt) {
/* 30 */     if (this.instructions.length == 0) {
/* 31 */       role.resetAllInstructions();
/* 32 */       return true;
/*    */     } 
/*    */     
/* 35 */     for (int instruction : this.instructions) {
/* 36 */       role.resetInstruction(instruction);
/*    */     }
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponent\\utility\ActionResetInstructions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */