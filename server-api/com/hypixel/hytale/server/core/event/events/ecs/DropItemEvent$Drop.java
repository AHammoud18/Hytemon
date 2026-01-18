/*     */ package com.hypixel.hytale.server.core.event.events.ecs;
/*     */ 
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
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
/*     */ public final class Drop
/*     */   extends DropItemEvent
/*     */ {
/*     */   @Nonnull
/*     */   private ItemStack itemStack;
/*     */   private float throwSpeed;
/*     */   
/*     */   public Drop(@Nonnull ItemStack itemStack, float throwSpeed) {
/*  81 */     this.itemStack = itemStack;
/*  82 */     this.throwSpeed = throwSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowSpeed(float throwSpeed) {
/*  91 */     this.throwSpeed = throwSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getThrowSpeed() {
/*  98 */     return this.throwSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemStack(@Nonnull ItemStack itemStack) {
/* 107 */     this.itemStack = itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemStack getItemStack() {
/* 115 */     return this.itemStack;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\DropItemEvent$Drop.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */