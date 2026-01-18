/*    */ package com.hypixel.hytale.server.npc.corecomponents.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
/*    */ import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
/*    */ import com.hypixel.hytale.server.npc.role.Role;
/*    */ import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionCrouch
/*    */   extends ActionBase
/*    */ {
/*    */   private final boolean crouching;
/*    */   
/*    */   public ActionCrouch(@Nonnull BuilderActionBase builderActionBase, boolean crouching) {
/* 31 */     super(builderActionBase);
/* 32 */     this.crouching = crouching;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
/* 37 */     super.execute(ref, role, sensorInfo, dt, store);
/*    */     
/* 39 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)store.getComponent(ref, MovementStatesComponent.getComponentType());
/* 40 */     if (movementStatesComponent != null) {
/* 41 */       (movementStatesComponent.getMovementStates()).crouching = this.crouching;
/*    */     }
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\corecomponents\movement\ActionCrouch.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */