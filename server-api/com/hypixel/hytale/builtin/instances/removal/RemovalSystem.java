/*    */ package com.hypixel.hytale.builtin.instances.removal;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.config.InstanceWorldConfig;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.system.tick.RunWhenPausedSystem;
/*    */ import com.hypixel.hytale.component.system.tick.TickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.Universe;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class RemovalSystem
/*    */   extends TickingSystem<ChunkStore>
/*    */   implements RunWhenPausedSystem<ChunkStore> {
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<ChunkStore> store) {
/* 17 */     InstanceDataResource data = (InstanceDataResource)store.getResource(InstanceDataResource.getResourceType());
/*    */     
/* 19 */     if (!data.isRemoving() && shouldRemoveWorld(store)) {
/* 20 */       data.setRemoving(true);
/*    */ 
/*    */       
/* 23 */       CompletableFuture.runAsync(() -> Universe.get().removeWorld(((ChunkStore)store.getExternalData()).getWorld().getName()));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean shouldRemoveWorld(@Nonnull Store<ChunkStore> store) {
/* 35 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 36 */     InstanceWorldConfig config = InstanceWorldConfig.get(world.getWorldConfig());
/* 37 */     if (config == null) return false;
/*    */     
/* 39 */     RemovalCondition[] removalConditions = config.getRemovalConditions();
/*    */     
/* 41 */     if (removalConditions.length == 0) return false;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 47 */     boolean shouldRemove = true;
/* 48 */     for (RemovalCondition cond : removalConditions) {
/* 49 */       shouldRemove &= cond.shouldRemoveWorld(store);
/*    */     }
/* 51 */     return shouldRemove;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\removal\RemovalSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */