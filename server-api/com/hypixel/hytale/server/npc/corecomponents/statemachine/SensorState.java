/*    */ package com.hypixel.hytale.server.npc.corecomponents.statemachine;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.statemachine.builders.BuilderSensorState;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.role.support.StateSupport;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
/*    */ import com.hypixel.hytale.server.npc.util.ComponentInfo;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SensorState
/*    */   extends SensorBase
/*    */ {
/*    */   protected final int state;
/*    */   protected final boolean defaultSubState;
/*    */   
/*    */   public SensorState(@Nonnull BuilderSensorState builder, @Nonnull BuilderSupport support) {
/* 23 */     super((BuilderSensorBase)builder);
/* 24 */     this.state = builder.getState();
/* 25 */     this.defaultSubState = builder.isDefaultSubState();
/* 26 */     this.subState = builder.getSubStateIndex();
/* 27 */     this.componentLocal = builder.isComponentLocal();
/* 28 */     this.componentIndex = support.getComponentIndex();
/*    */   }
/*    */   protected final int subState; protected final boolean componentLocal; protected final int componentIndex;
/*    */   
/*    */   public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
/* 33 */     StateSupport stateSupport = role.getStateSupport();
/* 34 */     if (this.componentLocal) return (super.matches(ref, role, dt, store) && stateSupport.isComponentInState(this.componentIndex, this.state));
/*    */     
/* 36 */     return (super.matches(ref, role, dt, store) && stateSupport.inState(this.state) && (this.defaultSubState || stateSupport.inSubState(this.subState)));
/*    */   }
/*    */ 
/*    */   
/*    */   public InfoProvider getSensorInfo() {
/* 41 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getInfo(@Nonnull Role role, @Nonnull ComponentInfo holder) {
/* 46 */     if (this.componentLocal) {
/* 47 */       holder.addField("Component local state: " + this.state);
/*    */     } else {
/* 49 */       holder.addField("State: " + role.getStateSupport().getStateName(this.state, this.subState));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\statemachine\SensorState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */