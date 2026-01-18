/*    */ package com.hypixel.hytale.server.npc.corecomponents.statemachine;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderActionToggleStateEvaluator;
/*    */ import com.hypixel.hytale.server.npc.decisionmaker.stateevaluator.StateEvaluator;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ActionToggleStateEvaluator extends ActionBase {
/*    */   protected final boolean enable;
/*    */   
/*    */   public ActionToggleStateEvaluator(@Nonnull BuilderActionToggleStateEvaluator builder) {
/* 18 */     super((BuilderActionBase)builder);
/* 19 */     this.enable = builder.isEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 24 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 26 */     StateEvaluator stateEvaluatorComponent = (StateEvaluator)store.getComponent(ref, StateEvaluator.getComponentType());
/* 27 */     assert stateEvaluatorComponent != null;
/* 28 */     stateEvaluatorComponent.setActive(this.enable);
/*    */     
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\statemachine\ActionToggleStateEvaluator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */