/*     */ package com.hypixel.hytale.server.core.event.events.ecs;
/*     */ 
/*     */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class DamageBlockEvent
/*     */   extends CancellableEcsEvent
/*     */ {
/*     */   @Nullable
/*     */   private final ItemStack itemInHand;
/*     */   @Nonnull
/*     */   private Vector3i targetBlock;
/*     */   @Nonnull
/*     */   private final BlockType blockType;
/*     */   private final float currentDamage;
/*     */   private float damage;
/*     */   
/*     */   public DamageBlockEvent(@Nullable ItemStack itemInHand, @Nonnull Vector3i targetBlock, @Nonnull BlockType blockType, float currentDamage, float damage) {
/*  56 */     this.itemInHand = itemInHand;
/*  57 */     this.targetBlock = targetBlock;
/*  58 */     this.blockType = blockType;
/*  59 */     this.currentDamage = currentDamage;
/*  60 */     this.damage = damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ItemStack getItemInHand() {
/*  68 */     return this.itemInHand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getTargetBlock() {
/*  76 */     return this.targetBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetBlock(@Nonnull Vector3i targetBlock) {
/*  85 */     this.targetBlock = targetBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockType getBlockType() {
/*  93 */     return this.blockType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCurrentDamage() {
/* 100 */     return this.currentDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDamage() {
/* 107 */     return this.damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDamage(float damage) {
/* 116 */     this.damage = damage;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\DamageBlockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */