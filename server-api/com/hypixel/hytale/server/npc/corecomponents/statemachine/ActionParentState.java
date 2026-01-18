/*    */ package com.hypixel.hytale.server.npc.corecomponents.statemachine;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.StatePair;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderActionParentState;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionParentState extends ActionBase {
/*    */   protected final int state;
/*    */   
/*    */   public ActionParentState(@Nonnull BuilderActionParentState builderActionState, @Nonnull BuilderSupport support) {
/* 20 */     super((BuilderActionBase)builderActionState);
/* 21 */     StatePair statePair = builderActionState.getStatePair(support);
/* 22 */     this.state = statePair.getState();
/* 23 */     this.subState = statePair.getSubState();
/*    */   }
/*    */   protected final int subState;
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 28 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */ 
/*    */     
/* 31 */     StateEvaluator stateEvaluatorComponent = (StateEvaluator)store.getComponent(ref, StateEvaluator.getComponentType());
/* 32 */     if (stateEvaluatorComponent == null || !stateEvaluatorComponent.isActive()) {
/* 33 */       role.getStateSupport().setState(this.state, this.subState, true, false);
/*    */     }
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\statemachine\ActionParentState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */