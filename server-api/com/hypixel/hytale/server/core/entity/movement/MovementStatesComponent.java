/*    */ package com.hypixel.hytale.server.core.entity.movement;
/*    */ 
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.protocol.MovementStates;
/*    */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MovementStatesComponent
/*    */   implements Component<EntityStore> {
/*    */   public static ComponentType<EntityStore, MovementStatesComponent> getComponentType() {
/* 13 */     return EntityModule.get().getMovementStatesComponentType();
/*    */   }
/*    */   
/* 16 */   private MovementStates movementStates = new MovementStates();
/* 17 */   private MovementStates sentMovementStates = new MovementStates();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MovementStatesComponent(@Nonnull MovementStatesComponent other) {
/* 24 */     this.movementStates = new MovementStates(other.movementStates);
/* 25 */     this.sentMovementStates = new MovementStates(other.sentMovementStates);
/*    */   }
/*    */   
/*    */   public MovementStates getMovementStates() {
/* 29 */     return this.movementStates;
/*    */   }
/*    */   
/*    */   public void setMovementStates(MovementStates movementStates) {
/* 33 */     this.movementStates = movementStates;
/*    */   }
/*    */   
/*    */   public MovementStates getSentMovementStates() {
/* 37 */     return this.sentMovementStates;
/*    */   }
/*    */   
/*    */   public void setSentMovementStates(MovementStates sentMovementStates) {
/* 41 */     this.sentMovementStates = sentMovementStates;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 47 */     return new MovementStatesComponent(this);
/*    */   }
/*    */   
/*    */   public MovementStatesComponent() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\movement\MovementStatesComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */