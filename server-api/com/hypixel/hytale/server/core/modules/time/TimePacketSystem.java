/*    */ package com.hypixel.hytale.server.core.modules.time;
/*    */ 
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.DelayedSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimePacketSystem
/*    */   extends DelayedSystem<EntityStore>
/*    */ {
/*    */   private static final float BROADCAST_INTERVAL = 1.0F;
/*    */   @Nonnull
/*    */   private final ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType;
/*    */   
/*    */   public TimePacketSystem(@Nonnull ResourceType<EntityStore, WorldTimeResource> worldTimeResourceType) {
/* 34 */     super(1.0F);
/* 35 */     this.worldTimeResourceType = worldTimeResourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void delayedTick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 40 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 41 */     if (world.getWorldConfig().isGameTimePaused())
/*    */       return; 
/* 43 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(this.worldTimeResourceType);
/* 44 */     worldTimeResource.broadcastTimePacket(store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\time\TimePacketSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */