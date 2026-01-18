/*    */ package com.hypixel.hytale.builtin.fluid;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.dependency.Dependency;
/*    */ import com.hypixel.hytale.component.dependency.RootDependency;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnsureFluidSection
/*    */   extends HolderSystem<ChunkStore>
/*    */ {
/*    */   @Nonnull
/* 67 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)ChunkSection.getComponentType(), (Query)Query.not((Query)FluidSection.getComponentType()) });
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {
/* 71 */     holder.addComponent(FluidSection.getComponentType(), (Component)new FluidSection());
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
/* 82 */     return QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 88 */     return RootDependency.firstSet();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\fluid\FluidSystems$EnsureFluidSection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */