/*     */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders.cached;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CachedPositionProvider
/*     */   extends PositionProvider
/*     */ {
/*     */   @Nonnull
/*     */   private final PositionProvider positionProvider;
/*     */   private final int sectionSize;
/*     */   private WorkerIndexer.Data<CacheThreadMemory> threadData;
/*     */   
/*     */   public CachedPositionProvider(@Nonnull PositionProvider positionProvider, int sectionSize, int cacheSize, boolean useInternalThreadData, int threadCount) {
/*  24 */     if (sectionSize <= 0 || cacheSize < 0 || threadCount <= 0) {
/*  25 */       throw new IllegalArgumentException();
/*     */     }
/*  27 */     this.positionProvider = positionProvider;
/*  28 */     this.sectionSize = sectionSize;
/*  29 */     this.threadData = new WorkerIndexer.Data(threadCount, () -> new CacheThreadMemory(cacheSize));
/*     */   }
/*     */ 
/*     */   
/*     */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/*  34 */     get(context);
/*     */   }
/*     */   
/*     */   public void get(@Nonnull PositionProvider.Context context) {
/*  38 */     CacheThreadMemory cachedData = (CacheThreadMemory)this.threadData.get(context.workerId);
/*     */ 
/*     */     
/*  41 */     Vector3i minSection = sectionAddress(context.minInclusive);
/*  42 */     Vector3i maxSection = sectionAddress(context.maxExclusive);
/*     */ 
/*     */     
/*  45 */     Vector3i sectionAddress = minSection.clone();
/*  46 */     for (sectionAddress.x = minSection.x; sectionAddress.x <= maxSection.x; sectionAddress.x++) {
/*  47 */       for (sectionAddress.z = minSection.z; sectionAddress.z <= maxSection.z; sectionAddress.z++) {
/*  48 */         for (sectionAddress.y = minSection.y; sectionAddress.y <= maxSection.y; sectionAddress.y++) {
/*     */           
/*  50 */           long key = HashUtil.hash(sectionAddress.x, sectionAddress.y, sectionAddress.z);
/*  51 */           Vector3d[] section = cachedData.sections.get(Long.valueOf(key));
/*     */           
/*  53 */           if (section == null) {
/*     */             
/*  55 */             Vector3d sectionMin = sectionMin(sectionAddress);
/*  56 */             Vector3d sectionMax = sectionMin.clone().add(this.sectionSize, this.sectionSize, this.sectionSize);
/*  57 */             ArrayList<Vector3d> generatedPositions = new ArrayList<>();
/*  58 */             Objects.requireNonNull(generatedPositions); PositionProvider.Context childContext = new PositionProvider.Context(sectionMin, sectionMax, generatedPositions::add, null, context.workerId);
/*     */             
/*  60 */             this.positionProvider.positionsIn(childContext);
/*     */             
/*  62 */             section = new Vector3d[generatedPositions.size()];
/*  63 */             generatedPositions.toArray(section);
/*  64 */             cachedData.sections.put(Long.valueOf(key), section);
/*     */ 
/*     */             
/*  67 */             cachedData.expirationList.addFirst(Long.valueOf(key));
/*  68 */             if (cachedData.expirationList.size() > cachedData.size) {
/*  69 */               long removedKey = ((Long)cachedData.expirationList.removeLast()).longValue();
/*  70 */               cachedData.sections.remove(Long.valueOf(removedKey));
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/*  75 */           for (Vector3d position : section) {
/*     */             
/*  77 */             if (VectorUtil.isInside(position, context.minInclusive, context.maxExclusive)) {
/*  78 */               context.consumer.accept(position.clone());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Vector3i sectionAddress(@Nonnull Vector3d pointer) {
/*  88 */     Vector3i address = pointer.toVector3i();
/*  89 */     address.x = sectionFloor(address.x) / this.sectionSize;
/*  90 */     address.y = sectionFloor(address.y) / this.sectionSize;
/*  91 */     address.z = sectionFloor(address.z) / this.sectionSize;
/*  92 */     return address;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private Vector3d sectionMin(@Nonnull Vector3i sectionAddress) {
/*  97 */     Vector3d min = sectionAddress.toVector3d();
/*  98 */     min.x *= this.sectionSize;
/*  99 */     min.y *= this.sectionSize;
/* 100 */     min.z *= this.sectionSize;
/* 101 */     return min;
/*     */   }
/*     */   
/*     */   private int toSectionAddress(double position) {
/* 105 */     int positionAddress = (int)position;
/* 106 */     positionAddress = sectionFloor(positionAddress);
/* 107 */     positionAddress /= this.sectionSize;
/* 108 */     return positionAddress;
/*     */   }
/*     */   
/*     */   public int sectionFloor(int voxelAddress) {
/* 112 */     if (voxelAddress < 0) {
/* 113 */       return voxelAddress - voxelAddress % this.sectionSize - this.sectionSize;
/*     */     }
/* 115 */     return voxelAddress - voxelAddress % this.sectionSize;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\cached\CachedPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */