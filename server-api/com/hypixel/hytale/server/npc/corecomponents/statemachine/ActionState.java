/*    */ package com.hypixel.hytale.server.npc.corecomponents.statemachine;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderActionState;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.ComponentInfo;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionState extends ActionBase {
/*    */   protected final int state;
/*    */   protected final int subState;
/*    */   protected final boolean clearOnce;
/*    */   protected final boolean componentLocal;
/*    */   protected final int componentIndex;
/*    */   
/*    */   public ActionState(@Nonnull BuilderActionState builderActionState, @Nonnull BuilderSupport support) {
/* 24 */     super((BuilderActionBase)builderActionState);
/* 25 */     this.state = builderActionState.getStateIndex();
/* 26 */     this.subState = builderActionState.getSubStateIndex();
/* 27 */     this.clearOnce = builderActionState.isClearState();
/* 28 */     this.componentLocal = builderActionState.isComponentLocal();
/* 29 */     this.componentIndex = support.getComponentIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 34 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 36 */     if (this.componentLocal) {
/* 37 */       role.getStateSupport().setComponentState(this.componentIndex, this.state);
/* 38 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 42 */     StateEvaluator stateEvaluatorComponent = (StateEvaluator)store.getComponent(ref, StateEvaluator.getComponentType());
/* 43 */     if (stateEvaluatorComponent == null || !stateEvaluatorComponent.isActive()) {
/* 44 */       role.getStateSupport().setState(this.state, this.subState, this.clearOnce, false);
/*    */     }
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getInfo(@Nonnull Role role, @Nonnull ComponentInfo holder) {
/* 51 */     if (this.componentLocal) {
/* 52 */       holder.addField("Component local state: " + this.state);
/*    */     } else {
/* 54 */       holder.addField("State: " + role.getStateSupport().getStateName(this.state, this.subState));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\statemachine\ActionState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */