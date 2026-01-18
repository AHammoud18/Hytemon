/*    */ package com.hypixel.hytale.server.core.event.events.ecs;
/*    */ 
/*    */ import com.hypixel.hytale.component.system.EcsEvent;
/*    */ import com.hypixel.hytale.component.system.ICancellableEcsEvent;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldMapTracker;
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
/*    */ public abstract class DiscoverZoneEvent
/*    */   extends EcsEvent
/*    */ {
/*    */   @Nonnull
/*    */   private final WorldMapTracker.ZoneDiscoveryInfo discoveryInfo;
/*    */   
/*    */   public DiscoverZoneEvent(@Nonnull WorldMapTracker.ZoneDiscoveryInfo discoveryInfo) {
/* 26 */     this.discoveryInfo = discoveryInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public WorldMapTracker.ZoneDiscoveryInfo getDiscoveryInfo() {
/* 34 */     return this.discoveryInfo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Display
/*    */     extends DiscoverZoneEvent
/*    */     implements ICancellableEcsEvent
/*    */   {
/*    */     private boolean cancelled = false;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public Display(@Nonnull WorldMapTracker.ZoneDiscoveryInfo discoveryInfo) {
/* 55 */       super(discoveryInfo);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isCancelled() {
/* 60 */       return this.cancelled;
/*    */     }
/*    */ 
/*    */     
/*    */     public void setCancelled(boolean cancelled) {
/* 65 */       this.cancelled = cancelled;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\event\events\ecs\DiscoverZoneEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */