/*    */ package com.hypixel.hytale.server.spawning.util;
/*    */ 
/*    */ import com.hypixel.hytale.component.Resource;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FloodFillEntryPoolProviderSimple
/*    */   implements Resource<EntityStore>
/*    */ {
/*    */   public static ResourceType<EntityStore, FloodFillEntryPoolProviderSimple> getResourceType() {
/* 15 */     return SpawningPlugin.get().getFloodFillEntryPoolProviderSimpleResourceType();
/*    */   }
/*    */   @Nonnull
/* 18 */   private final FloodFillEntryPoolSimple pool = new FloodFillEntryPoolSimple();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public FloodFillEntryPoolSimple getPool() {
/* 23 */     return this.pool;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Resource<EntityStore> clone() {
/* 30 */     return new FloodFillEntryPoolProviderSimple();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawnin\\util\FloodFillEntryPoolProviderSimple.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */