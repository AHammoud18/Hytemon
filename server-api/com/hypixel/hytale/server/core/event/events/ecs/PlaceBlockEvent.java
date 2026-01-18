/*    */ package com.hypixel.hytale.server.core.event.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class PlaceBlockEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   @Nullable
/*    */   private final ItemStack itemInHand;
/*    */   @Nonnull
/*    */   private Vector3i targetBlock;
/*    */   @Nonnull
/*    */   private RotationTuple rotation;
/*    */   
/*    */   public PlaceBlockEvent(@Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull RotationTuple rotation) {
/* 47 */     this.itemInHand = itemInHand;
/* 48 */     this.targetBlock = targetBlock;
/* 49 */     this.rotation = rotation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ItemStack getItemInHand() {
/* 57 */     return this.itemInHand;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i getTargetBlock() {
/* 65 */     return this.targetBlock;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTargetBlock(@Nonnull Vector3i targetBlock) {
/* 74 */     Objects.requireNonNull(targetBlock, "Block can't be null");
/* 75 */     this.targetBlock = targetBlock;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public RotationTuple getRotation() {
/* 83 */     return this.rotation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotation(@Nonnull RotationTuple rotation) {
/* 92 */     this.rotation = rotation;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\PlaceBlockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */