/*    */ package com.hypixel.hytale.server.core.event.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
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
/*    */ public class InteractivelyPickupItemEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   @Nonnull
/*    */   private ItemStack itemStack;
/*    */   
/*    */   public InteractivelyPickupItemEvent(@Nonnull ItemStack itemStack) {
/* 27 */     this.itemStack = itemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ItemStack getItemStack() {
/* 35 */     return this.itemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItemStack(@Nonnull ItemStack itemStack) {
/* 44 */     this.itemStack = itemStack;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\InteractivelyPickupItemEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */