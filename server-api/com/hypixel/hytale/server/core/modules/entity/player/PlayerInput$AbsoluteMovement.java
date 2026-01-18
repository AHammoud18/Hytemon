/*     */ package com.hypixel.hytale.server.core.modules.entity.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
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
/*     */ public class AbsoluteMovement
/*     */   implements PlayerInput.InputUpdate
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   
/*     */   public AbsoluteMovement(double x, double y, double z) {
/* 124 */     this.x = x;
/* 125 */     this.y = y;
/* 126 */     this.z = z;
/*     */   }
/*     */   
/*     */   public double getX() {
/* 130 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(double x) {
/* 134 */     this.x = x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 138 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(double y) {
/* 142 */     this.y = y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 146 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setZ(double z) {
/* 150 */     this.z = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void apply(@Nonnull CommandBuffer<EntityStore> commandBuffer, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, int index) {
/* 155 */     Ref<EntityStore> playerRef = archetypeChunk.getReferenceTo(index);
/*     */     
/* 157 */     Player playerComponent = (Player)archetypeChunk.getComponent(index, Player.getComponentType());
/* 158 */     assert playerComponent != null;
/*     */     
/* 160 */     playerComponent.moveTo(playerRef, this.x, this.y, this.z, (ComponentAccessor)commandBuffer);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerInput$AbsoluteMovement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */