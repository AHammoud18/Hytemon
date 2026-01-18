/*    */ package com.hypixel.hytale.server.core.event.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
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
/*    */ public class BreakBlockEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   @Nullable
/*    */   private final ItemStack itemInHand;
/*    */   @Nonnull
/*    */   private Vector3i targetBlock;
/*    */   @Nonnull
/*    */   private final BlockType blockType;
/*    */   
/*    */   public BreakBlockEvent(@Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull BlockType blockType) {
/* 44 */     this.itemInHand = itemInHand;
/* 45 */     this.targetBlock = targetBlock;
/* 46 */     this.blockType = blockType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ItemStack getItemInHand() {
/* 54 */     return this.itemInHand;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i getTargetBlock() {
/* 62 */     return this.targetBlock;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public BlockType getBlockType() {
/* 70 */     return this.blockType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTargetBlock(@Nonnull Vector3i targetBlock) {
/* 77 */     this.targetBlock = targetBlock;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\BreakBlockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */