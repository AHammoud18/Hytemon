/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.protocol.MovementStates;
/*    */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SetMovementStates
/*    */   extends Record
/*    */   implements PlayerInput.InputUpdate
/*    */ {
/*    */   private final MovementStates movementStates;
/*    */   
/*    */   public SetMovementStates(MovementStates movementStates) {
/* 63 */     this.movementStates = movementStates; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 63 */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates; } public MovementStates movementStates() { return this.movementStates; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates; } public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #63	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   } public void apply(CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 67 */     MovementStatesComponent movementStatesComponent = (MovementStatesComponent)archetypeChunk.getComponent(index, MovementStatesComponent.getComponentType());
/* 68 */     if (movementStatesComponent == null)
/* 69 */       return;  movementStatesComponent.setMovementStates(this.movementStates);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerInput$SetMovementStates.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */