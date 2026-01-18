/*    */ package com.hypixel.hytale.server.core.universe.world.system;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.FormatUtil;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.Order;
/*    */ import com.hypixel.hytale.component.dependency.SystemGroupDependency;
/*    */ import com.hypixel.hytale.component.system.StoreSystem;
/*    */ import com.hypixel.hytale.logger.HytaleLogger;
/*    */ import com.hypixel.hytale.math.shape.Box2D;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.util.MathUtil;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class WorldPregenerateSystem
/*    */   extends StoreSystem<ChunkStore>
/*    */ {
/* 26 */   private static final Set<Dependency<ChunkStore>> DEPENDENCIES = (Set)Set.of(new SystemGroupDependency(Order.AFTER, ChunkStore.INIT_GROUP));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 33 */     return DEPENDENCIES;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onSystemAddedToStore(@Nonnull Store<ChunkStore> store) {
/* 38 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 39 */     Box2D region = world.getWorldConfig().getChunkConfig().getPregenerateRegion();
/* 40 */     if (region != null) {
/* 41 */       world.getLogger().at(Level.INFO).log("Ensuring region is generated: %s", region);
/* 42 */       long start = System.nanoTime();
/*    */       
/* 44 */       int lowX = MathUtil.floor(region.min.x);
/* 45 */       int lowZ = MathUtil.floor(region.min.y);
/* 46 */       int highX = MathUtil.floor(region.max.x);
/* 47 */       int highZ = MathUtil.floor(region.max.y);
/*    */       
/* 49 */       ObjectArrayList<CompletableFuture> objectArrayList = new ObjectArrayList();
/* 50 */       for (int x = lowX; x <= highX; x += 32) {
/* 51 */         for (int z = lowZ; z <= highZ; z += 32) {
/* 52 */           objectArrayList.add(world.getChunkStore().getChunkReferenceAsync(ChunkUtil.indexChunkFromBlock(x, z)));
/*    */         }
/*    */       } 
/*    */       
/* 56 */       int allFutures = objectArrayList.size();
/* 57 */       AtomicInteger done = new AtomicInteger();
/* 58 */       objectArrayList.forEach(f -> f.whenComplete(()));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onSystemRemovedFromStore(@Nonnull Store<ChunkStore> store) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\system\WorldPregenerateSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */