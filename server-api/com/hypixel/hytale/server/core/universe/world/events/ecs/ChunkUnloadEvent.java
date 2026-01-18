/*    */ package com.hypixel.hytale.server.core.universe.world.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.CancellableEcsEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkUnloadEvent
/*    */   extends CancellableEcsEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final WorldChunk chunk;
/*    */   private boolean resetKeepAlive = true;
/*    */   
/*    */   public ChunkUnloadEvent(@Nonnull WorldChunk chunk) {
/* 33 */     this.chunk = chunk;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WorldChunk getChunk() {
/* 41 */     return this.chunk;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setResetKeepAlive(boolean willResetKeepAlive) {
/* 50 */     this.resetKeepAlive = willResetKeepAlive;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean willResetKeepAlive() {
/* 57 */     return this.resetKeepAlive;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\events\ecs\ChunkUnloadEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */