/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.event.ICancellable;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ @Deprecated
/*    */ public class PlayerInteractEvent
/*    */   extends PlayerEvent<String>
/*    */   implements ICancellable {
/*    */   private final InteractionType actionType;
/*    */   private final long clientUseTime;
/*    */   private final ItemStack itemInHand;
/*    */   private final Vector3i targetBlock;
/*    */   private final Ref<EntityStore> targetRef;
/*    */   private final Entity targetEntity;
/*    */   private boolean cancelled;
/*    */   
/*    */   public PlayerInteractEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, long clientUseTime, InteractionType actionType, ItemStack itemInHand, Vector3i targetBlock, Ref<EntityStore> targetRef, Entity targetEntity) {
/* 26 */     super(ref, player);
/* 27 */     this.actionType = actionType;
/* 28 */     this.clientUseTime = clientUseTime;
/* 29 */     this.itemInHand = itemInHand;
/* 30 */     this.targetBlock = targetBlock;
/* 31 */     this.targetRef = targetRef;
/* 32 */     this.targetEntity = targetEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 37 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 42 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public InteractionType getActionType() {
/* 46 */     return this.actionType;
/*    */   }
/*    */   
/*    */   public long getClientUseTime() {
/* 50 */     return this.clientUseTime;
/*    */   }
/*    */   
/*    */   public ItemStack getItemInHand() {
/* 54 */     return this.itemInHand;
/*    */   }
/*    */   
/*    */   public Vector3i getTargetBlock() {
/* 58 */     return this.targetBlock;
/*    */   }
/*    */   
/*    */   public Entity getTargetEntity() {
/* 62 */     return this.targetEntity;
/*    */   }
/*    */   
/*    */   public Ref<EntityStore> getTargetRef() {
/* 66 */     return this.targetRef;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "PlayerInteractEvent{actionType=" + String.valueOf(this.actionType) + ", clientUseTime=" + this.clientUseTime + ", itemInHand=" + String.valueOf(this.itemInHand) + ", targetBlock=" + String.valueOf(this.targetBlock) + ", targetEntity=" + String.valueOf(this.targetEntity) + ", cancelled=" + this.cancelled + "} " + super
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 79 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerInteractEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */