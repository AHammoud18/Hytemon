/*    */ package com.hypixel.hytale.server.core.universe.world.chunk.systems;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.RootDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.modules.migrations.ChunkColumnMigrationSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.Set;
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
/*    */ public class OnNewChunk
/*    */   extends ChunkColumnMigrationSystem
/*    */ {
/* 39 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] {
/* 40 */         (Query)WorldChunk.getComponentType(), 
/* 41 */         (Query)Query.not((Query)ChunkColumn.getComponentType())
/*    */       });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 48 */     Holder[] sectionHolders = new Holder[10];
/* 49 */     for (int i = 0; i < sectionHolders.length; i++) {
/* 50 */       sectionHolders[i] = ChunkStore.REGISTRY.newHolder();
/*    */     }
/* 52 */     holder.addComponent(ChunkColumn.getComponentType(), (Component)new ChunkColumn(sectionHolders));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<ChunkStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {}
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<ChunkStore> getQuery() {
/* 63 */     return QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 69 */     return RootDependency.firstSet();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\chunk\systems\ChunkSystems$OnNewChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */