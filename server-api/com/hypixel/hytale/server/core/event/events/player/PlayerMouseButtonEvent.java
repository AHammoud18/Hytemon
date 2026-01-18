/*    */ package com.hypixel.hytale.server.core.event.events.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.event.ICancellable;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.protocol.MouseButtonEvent;
/*    */ import com.hypixel.hytale.protocol.Vector2f;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.entity.Entity;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ public class PlayerMouseButtonEvent
/*    */   extends PlayerEvent<Void>
/*    */   implements ICancellable
/*    */ {
/*    */   @Nonnull
/*    */   private final PlayerRef playerRef;
/*    */   private final long clientUseTime;
/*    */   private final Item itemInHand;
/*    */   private final Vector3i targetBlock;
/*    */   private final Entity targetEntity;
/*    */   private final Vector2f screenPoint;
/*    */   private final MouseButtonEvent mouseButton;
/*    */   private boolean cancelled;
/*    */   
/*    */   public PlayerMouseButtonEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Player player, @Nonnull PlayerRef playerRefComponent, long clientUseTime, @Nonnull Item itemInHand, @Nonnull Vector3i targetBlock, @Nonnull Entity targetEntity, @Nonnull Vector2f screenPoint, @Nonnull MouseButtonEvent mouseButton) {
/* 38 */     super(ref, player);
/* 39 */     this.playerRef = playerRefComponent;
/* 40 */     this.clientUseTime = clientUseTime;
/* 41 */     this.itemInHand = itemInHand;
/* 42 */     this.targetBlock = targetBlock;
/* 43 */     this.targetEntity = targetEntity;
/* 44 */     this.screenPoint = screenPoint;
/* 45 */     this.mouseButton = mouseButton;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public PlayerRef getPlayerRefComponent() {
/* 50 */     return this.playerRef;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 55 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 60 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public long getClientUseTime() {
/* 64 */     return this.clientUseTime;
/*    */   }
/*    */   
/*    */   public Item getItemInHand() {
/* 68 */     return this.itemInHand;
/*    */   }
/*    */   
/*    */   public Vector3i getTargetBlock() {
/* 72 */     return this.targetBlock;
/*    */   }
/*    */   
/*    */   public Entity getTargetEntity() {
/* 76 */     return this.targetEntity;
/*    */   }
/*    */   
/*    */   public Vector2f getScreenPoint() {
/* 80 */     return this.screenPoint;
/*    */   }
/*    */   
/*    */   public MouseButtonEvent getMouseButton() {
/* 84 */     return this.mouseButton;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 90 */     return "PlayerMouseButtonEvent{clientUseTime=" + this.clientUseTime + ", itemInHand=" + String.valueOf(this.itemInHand) + ", targetBlock=" + String.valueOf(this.targetBlock) + ", targetEntity=" + String.valueOf(this.targetEntity) + ", screenPoint=" + String.valueOf(this.screenPoint) + ", mouseButton=" + String.valueOf(this.mouseButton) + ", cancelled=" + this.cancelled + "} " + super
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 98 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\player\PlayerMouseButtonEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */