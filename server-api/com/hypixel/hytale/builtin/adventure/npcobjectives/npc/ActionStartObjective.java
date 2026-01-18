/*    */ package com.hypixel.hytale.builtin.adventure.npcobjectives.npc;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.NPCObjectivesPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.npcobjectives.npc.builders.BuilderActionStartObjective;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionStartObjective extends ActionBase {
/*    */   protected final String objectiveId;
/*    */   
/*    */   public ActionStartObjective(@Nonnull BuilderActionStartObjective builder, @Nonnull BuilderSupport support) {
/* 19 */     super((BuilderActionBase)builder);
/* 20 */     this.objectiveId = builder.getObjectiveId(support);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canExecute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 25 */     return (super.canExecute(ref, role, sensorInfo, dt, store) && role.getStateSupport().getInteractionIterationTarget() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 30 */     super.execute(ref, role, sensorInfo, dt, store);
/* 31 */     NPCObjectivesPlugin.startObjective(role.getStateSupport().getInteractionIterationTarget(), this.objectiveId, store);
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\npcobjectives\npc\ActionStartObjective.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */