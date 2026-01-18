/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RelativeMovement
/*     */   implements PlayerInput.InputUpdate
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   
/*     */   public RelativeMovement(double x, double y, double z) {
/* 168 */     this.x = x;
/* 169 */     this.y = y;
/* 170 */     this.z = z;
/*     */   }
/*     */   
/*     */   public double getX() {
/* 174 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/* 178 */     this.x = x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 182 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/* 186 */     this.y = y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 190 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setZ(double z) {
/* 194 */     this.z = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void apply(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 199 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */     
/* 201 */     Player playerComponent = (Player)archetypeChunk.getComponent(index, Player.getComponentType());
/* 202 */     assert playerComponent != null;
/*     */     
/* 204 */     TransformComponent transformComponent = (TransformComponent)archetypeChunk.getComponent(index, TransformComponent.getComponentType());
/* 205 */     assert transformComponent != null;
/*     */     
/* 207 */     Vector3d position = transformComponent.getPosition();
/*     */     
/* 209 */     playerComponent.moveTo(ref, position.x + this.x, position.y + this.y, position.z + this.z, (ComponentAccessor)commandBuffer);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerInput$RelativeMovement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */