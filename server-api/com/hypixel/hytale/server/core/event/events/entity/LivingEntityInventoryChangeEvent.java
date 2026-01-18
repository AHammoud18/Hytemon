/*    */ package com.hypixel.hytale.server.core.event.events.entity;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*    */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*    */ import com.hypixel.hytale.server.core.inventory.transaction.Transaction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class LivingEntityInventoryChangeEvent
/*    */   extends EntityEvent<LivingEntity, String> {
/*    */   private ItemContainer itemContainer;
/*    */   private Transaction transaction;
/*    */   
/*    */   public LivingEntityInventoryChangeEvent(LivingEntity entity, ItemContainer itemContainer, Transaction transaction) {
/* 14 */     super(entity);
/* 15 */     this.itemContainer = itemContainer;
/* 16 */     this.transaction = transaction;
/*    */   }
/*    */   
/*    */   public ItemContainer getItemContainer() {
/* 20 */     return this.itemContainer;
/*    */   }
/*    */   
/*    */   public Transaction getTransaction() {
/* 24 */     return this.transaction;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 30 */     return "LivingEntityInventoryChangeEvent{itemContainer=" + String.valueOf(this.itemContainer) + ", transaction=" + String.valueOf(this.transaction) + "} " + super
/*    */ 
/*    */       
/* 33 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\entity\LivingEntityInventoryChangeEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */