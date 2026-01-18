/*     */ package com.hypixel.hytale.builtin.blockphysics;
/*     */ 
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.blocktype.component.BlockPhysics;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.AbstractCachedAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CachedAccessor
/*     */   extends AbstractCachedAccessor
/*     */ {
/* 109 */   private static final ThreadLocal<CachedAccessor> THREAD_LOCAL = ThreadLocal.withInitial(CachedAccessor::new);
/*     */   
/*     */   private static final int PHYSICS_COMPONENT = 0;
/*     */   private static final int FLUID_COMPONENT = 1;
/*     */   private static final int BLOCK_COMPONENT = 2;
/*     */   protected BlockSection selfBlockSection;
/*     */   protected BlockPhysics selfPhysics;
/*     */   protected FluidSection selfFluidSection;
/*     */   
/*     */   protected CachedAccessor() {
/* 119 */     super(3);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CachedAccessor of(ComponentAccessor<ChunkStore> commandBuffer, BlockSection blockSection, BlockPhysics section, FluidSection fluidSection, int cx, int cy, int cz, int radius) {
/* 124 */     CachedAccessor accessor = THREAD_LOCAL.get();
/* 125 */     accessor.init(commandBuffer, cx, cy, cz, radius);
/* 126 */     accessor.insertSectionComponent(0, (Component)section, cx, cy, cz);
/* 127 */     accessor.insertSectionComponent(1, (Component)fluidSection, cx, cy, cz);
/* 128 */     accessor.insertSectionComponent(2, (Component)blockSection, cx, cy, cz);
/* 129 */     accessor.selfBlockSection = blockSection;
/* 130 */     accessor.selfPhysics = section;
/* 131 */     accessor.selfFluidSection = fluidSection;
/* 132 */     return accessor;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockPhysics getBlockPhysics(int cx, int cy, int cz) {
/* 137 */     return (BlockPhysics)getComponentSection(cx, cy, cz, 0, BlockPhysics.getComponentType());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public FluidSection getFluidSection(int cx, int cy, int cz) {
/* 142 */     return (FluidSection)getComponentSection(cx, cy, cz, 1, FluidSection.getComponentType());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockSection getBlockSection(int cx, int cy, int cz) {
/* 147 */     return (BlockSection)getComponentSection(cx, cy, cz, 2, BlockSection.getComponentType());
/*     */   }
/*     */   
/*     */   public void performBlockUpdate(int x, int y, int z, int maxSupportDistance) {
/* 151 */     for (int ix = -1; ix < 2; ix++) {
/* 152 */       int wx = x + ix;
/* 153 */       for (int iz = -1; iz < 2; iz++) {
/* 154 */         int wz = z + iz;
/*     */         
/* 156 */         for (int iy = -1; iy < 2; iy++) {
/* 157 */           int wy = y + iy;
/*     */           
/* 159 */           BlockPhysics physics = getBlockPhysics(
/* 160 */               ChunkUtil.chunkCoordinate(wx), 
/* 161 */               ChunkUtil.chunkCoordinate(wy), 
/* 162 */               ChunkUtil.chunkCoordinate(wz));
/*     */           
/* 164 */           int support = (physics != null) ? physics.get(wx, wy, wz) : 0;
/* 165 */           if (support <= maxSupportDistance) {
/* 166 */             BlockSection blockChunk = getBlockSection(
/* 167 */                 ChunkUtil.chunkCoordinate(wx), 
/* 168 */                 ChunkUtil.chunkCoordinate(wy), 
/* 169 */                 ChunkUtil.chunkCoordinate(wz));
/* 170 */             if (blockChunk != null)
/*     */             {
/*     */               
/* 173 */               blockChunk.setTicking(wx, wy, wz, true); } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void performBlockUpdate(int x, int y, int z) {
/* 181 */     for (int ix = -1; ix < 2; ix++) {
/* 182 */       int wx = x + ix;
/* 183 */       for (int iz = -1; iz < 2; iz++) {
/* 184 */         int wz = z + iz;
/*     */         
/* 186 */         for (int iy = -1; iy < 2; iy++) {
/* 187 */           int wy = y + iy;
/* 188 */           BlockSection blockChunk = getBlockSection(
/* 189 */               ChunkUtil.chunkCoordinate(wx), 
/* 190 */               ChunkUtil.chunkCoordinate(wy), 
/* 191 */               ChunkUtil.chunkCoordinate(wz));
/* 192 */           if (blockChunk != null)
/*     */           {
/*     */             
/* 195 */             blockChunk.setTicking(wx, wy, wz, true);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockphysics\BlockPhysicsSystems$CachedAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */