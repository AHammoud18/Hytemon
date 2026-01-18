/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.MovementStates;
/*     */ import com.hypixel.hytale.protocol.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.entity.movement.MovementStatesComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.EntityModule;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.Velocity;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class PlayerInput implements Component<EntityStore> {
/*     */   public static ComponentType<EntityStore, PlayerInput> getComponentType() {
/*  25 */     return EntityModule.get().getPlayerInputComponentType();
/*     */   }
/*     */   @Nonnull
/*  28 */   private final List<InputUpdate> inputUpdateQueue = (List<InputUpdate>)new ObjectArrayList();
/*     */ 
/*     */   
/*     */   private int mountId;
/*     */ 
/*     */   
/*     */   public void queue(InputUpdate inputUpdate) {
/*  35 */     this.inputUpdateQueue.add(inputUpdate);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public List<InputUpdate> getMovementUpdateQueue() {
/*  40 */     return this.inputUpdateQueue;
/*     */   }
/*     */   
/*     */   public int getMountId() {
/*  44 */     return this.mountId;
/*     */   }
/*     */   
/*     */   public void setMountId(int mountId) {
/*  48 */     this.mountId = mountId;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Component<EntityStore> clone() {
/*  54 */     PlayerInput playerInput = new PlayerInput();
/*  55 */     playerInput.inputUpdateQueue.addAll(this.inputUpdateQueue);
/*  56 */     return playerInput;
/*     */   }
/*     */   public static interface InputUpdate {
/*     */     void apply(CommandBuffer<EntityStore> param1CommandBuffer, ArchetypeChunk<EntityStore> param1ArchetypeChunk, int param1Int); }
/*     */   
/*     */   public static final class SetMovementStates extends Record implements InputUpdate { private final MovementStates movementStates;
/*     */     
/*  63 */     public SetMovementStates(MovementStates movementStates) { this.movementStates = movementStates; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #63	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  63 */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates; } public MovementStates movementStates() { return this.movementStates; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #63	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates; } public final boolean equals(Object o) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #63	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetMovementStates;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */     } public void apply(CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/*  67 */       MovementStatesComponent movementStatesComponent = (MovementStatesComponent)archetypeChunk.getComponent(index, MovementStatesComponent.getComponentType());
/*  68 */       if (movementStatesComponent == null)
/*  69 */         return;  movementStatesComponent.setMovementStates(this.movementStates);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class SetClientVelocity implements InputUpdate {
/*     */     private final Vector3d velocity;
/*     */     
/*     */     public SetClientVelocity(Vector3d velocity) {
/*  77 */       this.velocity = new Vector3d(velocity.x, velocity.y, velocity.z);
/*     */     }
/*     */     
/*     */     public Vector3d getVelocity() {
/*  81 */       return this.velocity;
/*     */     }
/*     */ 
/*     */     
/*     */     public void apply(CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/*  86 */       Velocity velocityComponent = (Velocity)archetypeChunk.getComponent(index, Velocity.getComponentType());
/*  87 */       if (velocityComponent == null)
/*  88 */         return;  velocityComponent.setClient(this.velocity);
/*     */     } }
/*     */   public static final class SetRiderMovementStates extends Record implements InputUpdate { private final MovementStates movementStates;
/*     */     
/*  92 */     public SetRiderMovementStates(MovementStates movementStates) { this.movementStates = movementStates; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetRiderMovementStates;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #92	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetRiderMovementStates; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetRiderMovementStates;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #92	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetRiderMovementStates; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetRiderMovementStates;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #92	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetRiderMovementStates;
/*  92 */       //   0	8	1	o	Ljava/lang/Object; } public MovementStates movementStates() { return this.movementStates; }
/*     */ 
/*     */     
/*     */     public void apply(CommandBuffer<EntityStore> commandBuffer, ArchetypeChunk<EntityStore> archetypeChunk, int index) {} }
/*     */ 
/*     */   
/*     */   public static final class SetHead extends Record implements InputUpdate { private final Direction direction;
/*     */     
/* 100 */     public SetHead(Direction direction) { this.direction = direction; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetHead;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #100	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetHead; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetHead;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #100	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetHead; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetHead;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #100	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetHead;
/* 100 */       //   0	8	1	o	Ljava/lang/Object; } public Direction direction() { return this.direction; }
/*     */ 
/*     */     
/*     */     public void apply(CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 104 */       HeadRotation headRotationComponent = (HeadRotation)archetypeChunk.getComponent(index, HeadRotation.getComponentType());
/* 105 */       if (headRotationComponent == null)
/* 106 */         return;  headRotationComponent.getRotation().assign(this.direction.pitch, this.direction.yaw, this.direction.roll);
/*     */     } }
/*     */   public static final class SetBody extends Record implements InputUpdate { private final Direction direction;
/*     */     
/* 110 */     public SetBody(Direction direction) { this.direction = direction; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetBody;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetBody; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetBody;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetBody; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetBody;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/server/core/modules/entity/player/PlayerInput$SetBody;
/* 110 */       //   0	8	1	o	Ljava/lang/Object; } public Direction direction() { return this.direction; }
/*     */ 
/*     */     
/*     */     public void apply(CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 114 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 115 */       if (transformComponent == null)
/* 116 */         return;  transformComponent.getRotation().assign(this.direction.pitch, this.direction.yaw, this.direction.roll);
/*     */     } }
/*     */   
/*     */   public static class AbsoluteMovement implements InputUpdate { private double x;
/*     */     private double y;
/*     */     private double z;
/*     */     
/*     */     public AbsoluteMovement(double x, double y, double z) {
/* 124 */       this.x = x;
/* 125 */       this.y = y;
/* 126 */       this.z = z;
/*     */     }
/*     */     
/*     */     public double getX() {
/* 130 */       return this.x;
/*     */     }
/*     */     
/*     */     public void setX(double x) {
/* 134 */       this.x = x;
/*     */     }
/*     */     
/*     */     public double getY() {
/* 138 */       return this.y;
/*     */     }
/*     */     
/*     */     public void setY(double y) {
/* 142 */       this.y = y;
/*     */     }
/*     */     
/*     */     public double getZ() {
/* 146 */       return this.z;
/*     */     }
/*     */     
/*     */     public void setZ(double z) {
/* 150 */       this.z = z;
/*     */     }
/*     */ 
/*     */     
/*     */     public void apply(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 155 */       Ref<EntityStore> playerRef = archetypeChunk.getReferenceTo(index);
/*     */       
/* 157 */       Player playerComponent = (Player)archetypeChunk.getComponent(index, Player.getComponentType());
/* 158 */       assert playerComponent != null;
/*     */       
/* 160 */       playerComponent.moveTo(playerRef, this.x, this.y, this.z, (ComponentAccessor)commandBuffer);
/*     */     } }
/*     */   
/*     */   public static class RelativeMovement implements InputUpdate { private double x;
/*     */     private double y;
/*     */     private double z;
/*     */     
/*     */     public RelativeMovement(double x, double y, double z) {
/* 168 */       this.x = x;
/* 169 */       this.y = y;
/* 170 */       this.z = z;
/*     */     }
/*     */     
/*     */     public double getX() {
/* 174 */       return this.x;
/*     */     }
/*     */     
/*     */     public void setX(double x) {
/* 178 */       this.x = x;
/*     */     }
/*     */     
/*     */     public double getY() {
/* 182 */       return this.y;
/*     */     }
/*     */     
/*     */     public void setY(double y) {
/* 186 */       this.y = y;
/*     */     }
/*     */     
/*     */     public double getZ() {
/* 190 */       return this.z;
/*     */     }
/*     */     
/*     */     public void setZ(double z) {
/* 194 */       this.z = z;
/*     */     }
/*     */ 
/*     */     
/*     */     public void apply(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 199 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */       
/* 201 */       Player playerComponent = (Player)archetypeChunk.getComponent(index, Player.getComponentType());
/* 202 */       assert playerComponent != null;
/*     */       
/* 204 */       TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 205 */       assert transformComponent != null;
/*     */       
/* 207 */       Vector3d position = transformComponent.getPosition();
/*     */       
/* 209 */       playerComponent.moveTo(ref, position.x + this.x, position.y + this.y, position.z + this.z, (ComponentAccessor)commandBuffer);
/*     */     } }
/*     */   
/*     */   public static class WishMovement implements InputUpdate { private double x;
/*     */     private double y;
/*     */     private double z;
/*     */     
/*     */     public WishMovement(double x, double y, double z) {
/* 217 */       this.x = x;
/* 218 */       this.y = y;
/* 219 */       this.z = z;
/*     */     }
/*     */     
/*     */     public double getX() {
/* 223 */       return this.x;
/*     */     }
/*     */     
/*     */     public void setX(double x) {
/* 227 */       this.x = x;
/*     */     }
/*     */     
/*     */     public double getY() {
/* 231 */       return this.y;
/*     */     }
/*     */     
/*     */     public void setY(double y) {
/* 235 */       this.y = y;
/*     */     }
/*     */     
/*     */     public double getZ() {
/* 239 */       return this.z;
/*     */     }
/*     */     
/*     */     public void setZ(double z) {
/* 243 */       this.z = z;
/*     */     }
/*     */     
/*     */     public void apply(CommandBuffer<EntityStore> commandBuffer, ArchetypeChunk<EntityStore> archetypeChunk, int index) {} }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerInput.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */