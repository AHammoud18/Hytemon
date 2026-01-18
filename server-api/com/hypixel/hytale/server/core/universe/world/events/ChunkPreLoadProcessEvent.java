/*     */ package com.hypixel.hytale.server.core.universe.world.events;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.FormatUtil;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.event.IProcessedEvent;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.logging.Level;
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
/*     */ public class ChunkPreLoadProcessEvent
/*     */   extends ChunkEvent
/*     */   implements IProcessedEvent
/*     */ {
/*     */   private final boolean newlyGenerated;
/*     */   private long lastDispatchNanos;
/*     */   private boolean didLog;
/*     */   @Nonnull
/*     */   private final Holder<ChunkStore> holder;
/*     */   
/*     */   public ChunkPreLoadProcessEvent(@Nonnull Holder<ChunkStore> holder, @Nonnull WorldChunk chunk, boolean newlyGenerated, long lastDispatchNanos) {
/*  50 */     super(chunk);
/*     */     
/*  52 */     this.newlyGenerated = newlyGenerated;
/*  53 */     this.lastDispatchNanos = lastDispatchNanos;
/*  54 */     this.holder = holder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNewlyGenerated() {
/*  61 */     return this.newlyGenerated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Holder<ChunkStore> getHolder() {
/*  68 */     return this.holder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void processEvent(@Nonnull String hookName) {
/*  73 */     long end = System.nanoTime();
/*  74 */     long diff = end - this.lastDispatchNanos;
/*     */     
/*  76 */     this.lastDispatchNanos = end;
/*  77 */     if (diff > getChunk().getWorld().getTickStepNanos()) {
/*     */       
/*  79 */       World world = getChunk().getWorld();
/*  80 */       if (world.consumeGCHasRun()) {
/*  81 */         world.getLogger().at(Level.SEVERE).log(String.format("Took too long to run pre-load process hook for chunk: %s > TICK_STEP, Has GC Run: true, %%s, Hook: %%s", new Object[] { FormatUtil.nanosToString(diff) }), getChunk(), hookName);
/*     */       } else {
/*  83 */         world.getLogger().at(Level.SEVERE).log(String.format("Took too long to run pre-load process hook for chunk: %s > TICK_STEP, %%s, Hook: %%s", new Object[] { FormatUtil.nanosToString(diff) }), getChunk(), hookName);
/*     */       } 
/*  85 */       this.didLog = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean didLog() {
/*  93 */     return this.didLog;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  99 */     return "ChunkPreLoadProcessEvent{newlyGenerated=" + this.newlyGenerated + ", lastDispatchNanos=" + this.lastDispatchNanos + ", didLog=" + this.didLog + "} " + super
/*     */ 
/*     */ 
/*     */       
/* 103 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\events\ChunkPreLoadProcessEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */