/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.event.ICancellable;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.MouseMotionEvent;
/*    */ import com.hypixel.hytale.protocol.Vector2f;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PlayerMouseMotionEvent
/*    */   extends PlayerEvent<Void>
/*    */   implements ICancellable {
/*    */   private final long clientUseTime;
/*    */   private final Item itemInHand;
/*    */   private final Vector3i targetBlock;
/*    */   private final Entity targetEntity;
/*    */   private final Vector2f screenPoint;
/*    */   private final MouseMotionEvent mouseMotion;
/*    */   private boolean cancelled;
/*    */   
/*    */   public PlayerMouseMotionEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, long clientUseTime, Item itemInHand, Vector3i targetBlock, Entity targetEntity, Vector2f screenPoint, MouseMotionEvent mouseMotion) {
/* 26 */     super(ref, player);
/* 27 */     this.clientUseTime = clientUseTime;
/* 28 */     this.itemInHand = itemInHand;
/* 29 */     this.targetBlock = targetBlock;
/* 30 */     this.targetEntity = targetEntity;
/* 31 */     this.screenPoint = screenPoint;
/* 32 */     this.mouseMotion = mouseMotion;
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
/*    */   public long getClientUseTime() {
/* 46 */     return this.clientUseTime;
/*    */   }
/*    */   
/*    */   public Item getItemInHand() {
/* 50 */     return this.itemInHand;
/*    */   }
/*    */   
/*    */   public Vector3i getTargetBlock() {
/* 54 */     return this.targetBlock;
/*    */   }
/*    */   
/*    */   public Entity getTargetEntity() {
/* 58 */     return this.targetEntity;
/*    */   }
/*    */   
/*    */   public Vector2f getScreenPoint() {
/* 62 */     return this.screenPoint;
/*    */   }
/*    */   
/*    */   public MouseMotionEvent getMouseMotion() {
/* 66 */     return this.mouseMotion;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 72 */     return "PlayerMouseMotionEvent{clientUseTime=" + this.clientUseTime + ", itemInHand=" + String.valueOf(this.itemInHand) + ", targetBlock=" + String.valueOf(this.targetBlock) + ", targetEntity=" + String.valueOf(this.targetEntity) + ", screenPoint=" + String.valueOf(this.screenPoint) + ", mouseMotion=" + String.valueOf(this.mouseMotion) + ", cancelled=" + this.cancelled + "} " + super
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 80 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerMouseMotionEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */