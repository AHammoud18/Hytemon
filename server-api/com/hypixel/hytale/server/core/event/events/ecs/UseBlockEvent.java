/*     */ package com.hypixel.hytale.server.core.event.events.ecs;
/*     */ 
/*     */ import com.hypixel.hytale.component.system.EcsEvent;
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
/*     */ public abstract class UseBlockEvent
/*     */   extends EcsEvent
/*     */ {
/*     */   @Nonnull
/*     */   private final InteractionType interactionType;
/*     */   @Nonnull
/*     */   private final InteractionContext context;
/*     */   @Nonnull
/*     */   private final Vector3i targetBlock;
/*     */   @Nonnull
/*     */   private final BlockType blockType;
/*     */   
/*     */   public UseBlockEvent(@Nonnull InteractionType interactionType, @Nonnull InteractionContext context, @Nonnull Vector3i targetBlock, @Nonnull BlockType blockType) {
/*  53 */     this.interactionType = interactionType;
/*  54 */     this.context = context;
/*  55 */     this.targetBlock = targetBlock;
/*  56 */     this.blockType = blockType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionType getInteractionType() {
/*  64 */     return this.interactionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionContext getContext() {
/*  72 */     return this.context;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Vector3i getTargetBlock() {
/*  80 */     return this.targetBlock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockType getBlockType() {
/*  88 */     return this.blockType;
/*     */   }
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
/*     */   public static final class Pre
/*     */     extends UseBlockEvent
/*     */     implements ICancellableEcsEvent
/*     */   {
/*     */     private boolean cancelled = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pre(@Nonnull InteractionType interactionType, @Nonnull InteractionContext context, @Nonnull Vector3i targetBlock, @Nonnull BlockType blockType) {
/* 115 */       super(interactionType, context, targetBlock, blockType);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCancelled() {
/* 120 */       return this.cancelled;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCancelled(boolean cancelled) {
/* 125 */       this.cancelled = cancelled;
/*     */     }
/*     */   }
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
/*     */   public static final class Post
/*     */     extends UseBlockEvent
/*     */   {
/*     */     public Post(@Nonnull InteractionType interactionType, @Nonnull InteractionContext context, @Nonnull Vector3i targetBlock, @Nonnull BlockType blockType) {
/* 146 */       super(interactionType, context, targetBlock, blockType);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\UseBlockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */