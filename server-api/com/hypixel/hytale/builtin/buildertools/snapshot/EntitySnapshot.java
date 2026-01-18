/*    */ package com.hypixel.hytale.builtin.buildertools.snapshot;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface EntitySnapshot<T extends SelectionSnapshot<?>>
/*    */   extends SelectionSnapshot<T>
/*    */ {
/*    */   @Nullable
/*    */   T restoreEntity(@Nonnull Player paramPlayer, @Nonnull World paramWorld, @Nonnull ComponentAccessor<EntityStore> paramComponentAccessor);
/*    */   
/*    */   default T restore(Ref<EntityStore> ref, Player player, @Nonnull World world, ComponentAccessor<EntityStore> componentAccessor) {
/* 27 */     Store<EntityStore> store = world.getEntityStore().getStore();
/* 28 */     if (!world.isInThread()) {
/* 29 */       return (T)CompletableFuture.<SelectionSnapshot>supplyAsync(() -> restoreEntity(player, world, (ComponentAccessor<EntityStore>)store), (Executor)world).join();
/*    */     }
/* 31 */     return restoreEntity(player, world, (ComponentAccessor<EntityStore>)store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\snapshot\EntitySnapshot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */