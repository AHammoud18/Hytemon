/*    */ package com.hypixel.hytale.builtin.instances.removal;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldEmptyCondition
/*    */   implements RemovalCondition
/*    */ {
/* 23 */   public static final WorldEmptyCondition INSTANCE = new WorldEmptyCondition();
/* 24 */   public static final RemovalCondition[] REMOVE_WHEN_EMPTY = new RemovalCondition[] { INSTANCE };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<WorldEmptyCondition> CODEC;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WorldEmptyCondition.class, WorldEmptyCondition::new).documentation("A condition that triggers when the world is empty.\n\nIt will only trigger after at least one player has joined. As a safety measure it provides a timeout for waiting for a player to join in case the player disconnected before entering the world.")).append(new KeyedCodec("TimeoutSeconds", (Codec)Codec.DOUBLE), (o, i) -> o.timeoutSeconds = i.doubleValue(), o -> Double.valueOf(o.timeoutSeconds)).documentation("How long to wait (in seconds) for a player to join before closing the world.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldEmptyCondition(double timeoutSeconds) {
/* 45 */     this.timeoutSeconds = timeoutSeconds;
/*    */   }
/*    */ 
/*    */   
/* 49 */   private double timeoutSeconds = TimeUnit.MINUTES.toSeconds(5L);
/*    */ 
/*    */   
/*    */   public boolean shouldRemoveWorld(@Nonnull Store<ChunkStore> store) {
/* 53 */     InstanceDataResource data = (InstanceDataResource)store.getResource(InstanceDataResource.getResourceType());
/* 54 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 55 */     TimeResource timeResource = (TimeResource)world.getEntityStore().getStore().getResource(TimeResource.getResourceType());
/*    */     
/* 57 */     boolean hasPlayer = (world.getPlayerCount() > 0);
/* 58 */     boolean hadPlayer = data.hadPlayer();
/*    */     
/* 60 */     if (!hasPlayer && hadPlayer) {
/* 61 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 65 */     if (hasPlayer && !hadPlayer) {
/* 66 */       data.setHadPlayer(true);
/* 67 */       data.setWorldTimeoutTimer(null);
/*    */     } 
/*    */ 
/*    */     
/* 71 */     if (hadPlayer || hasPlayer) return false;
/*    */ 
/*    */     
/* 74 */     if (data.getWorldTimeoutTimer() == null) {
/* 75 */       data.setWorldTimeoutTimer(timeResource.getNow().plusNanos((long)(this.timeoutSeconds * 1.0E9D)));
/*    */     }
/*    */ 
/*    */     
/* 79 */     return timeResource.getNow().isAfter(data.getWorldTimeoutTimer());
/*    */   }
/*    */   
/*    */   public WorldEmptyCondition() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\removal\WorldEmptyCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */