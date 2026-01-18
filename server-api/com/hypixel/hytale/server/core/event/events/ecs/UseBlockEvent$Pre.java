/*     */ package com.hypixel.hytale.server.core.event.events.ecs;
/*     */ 
/*     */ import com.hypixel.hytale.component.system.ICancellableEcsEvent;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
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
/*     */ public final class Pre
/*     */   extends UseBlockEvent
/*     */   implements ICancellableEcsEvent
/*     */ {
/*     */   private boolean cancelled = false;
/*     */   
/*     */   public Pre(@Nonnull InteractionType interactionType, @Nonnull InteractionContext context, @Nonnull Vector3i targetBlock, @Nonnull BlockType blockType) {
/* 115 */     super(interactionType, context, targetBlock, blockType);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 120 */     return this.cancelled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCancelled(boolean cancelled) {
/* 125 */     this.cancelled = cancelled;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\UseBlockEvent$Pre.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */