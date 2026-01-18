/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NCountedPixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NPixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NSimplePixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NPixelBufferView;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NBiomeDistanceStage
/*     */   implements NStage {
/*     */   private static final double ORIGIN_REACH = 1.0D;
/*  24 */   private static final double BUFFER_DIAGONAL_VOXEL_GRID = Math.sqrt((NPixelBuffer.SIZE.x * NPixelBuffer.SIZE.x + NPixelBuffer.SIZE.z * NPixelBuffer.SIZE.z));
/*     */   
/*     */   public static final double DEFAULT_DISTANCE_TO_BIOME_EDGE = 1.7976931348623157E308D;
/*     */   
/*  28 */   public static final Class<NCountedPixelBuffer> biomeBufferClass = NCountedPixelBuffer.class;
/*  29 */   public static final Class<BiomeType> biomeTypeClass = BiomeType.class;
/*     */   
/*  31 */   public static final Class<NSimplePixelBuffer> biomeDistanceBufferClass = NSimplePixelBuffer.class;
/*  32 */   public static final Class<BiomeDistanceEntries> biomeDistanceClass = BiomeDistanceEntries.class;
/*     */   
/*     */   private final NParametrizedBufferType biomeInputBufferType;
/*     */   
/*     */   private final NParametrizedBufferType biomeDistanceOutputBufferType;
/*     */   
/*     */   private final String stageName;
/*     */   
/*     */   private final double maxDistance_voxelGrid;
/*     */   
/*     */   private final int maxDistance_bufferGrid;
/*     */   
/*     */   private final Bounds3i inputBounds_bufferGrid;
/*     */ 
/*     */   
/*     */   public NBiomeDistanceStage(@Nonnull String stageName, @Nonnull NParametrizedBufferType biomeInputBufferType, @Nonnull NParametrizedBufferType biomeDistanceOutputBufferType, double maxDistance_voxelGrid) {
/*  48 */     assert maxDistance_voxelGrid >= 0.0D;
/*     */     
/*  50 */     this.stageName = stageName;
/*  51 */     this.biomeInputBufferType = biomeInputBufferType;
/*  52 */     this.biomeDistanceOutputBufferType = biomeDistanceOutputBufferType;
/*  53 */     this.maxDistance_voxelGrid = maxDistance_voxelGrid;
/*  54 */     this.maxDistance_bufferGrid = GridUtils.toBufferDistanceInclusive_fromVoxelDistance((int)Math.ceil(maxDistance_voxelGrid));
/*     */     
/*  56 */     Bounds3i inputBounds_voxelGrid = GridUtils.createBounds_fromRadius_originVoxelInclusive((int)Math.ceil(maxDistance_voxelGrid));
/*  57 */     Bounds3i bufferColumnBounds_voxelGrid = GridUtils.createColumnBounds_voxelGrid(new Vector3i(), 0, 1);
/*  58 */     inputBounds_voxelGrid.stack(bufferColumnBounds_voxelGrid);
/*  59 */     this.inputBounds_bufferGrid = GridUtils.createBufferBoundsInclusive_fromVoxelBounds(inputBounds_voxelGrid);
/*  60 */     GridUtils.setBoundsYToWorldHeight_bufferGrid(this.inputBounds_bufferGrid);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(@Nonnull NStage.Context context) {
/*  65 */     NBufferBundle.Access.View biomeAccess = context.bufferAccess.get(this.biomeInputBufferType);
/*  66 */     NPixelBufferView<BiomeType> biomeSpace = new NPixelBufferView(biomeAccess, biomeTypeClass);
/*     */     
/*  68 */     NBufferBundle.Access.View biomeDistanceAccess = context.bufferAccess.get(this.biomeDistanceOutputBufferType);
/*  69 */     NPixelBufferView<BiomeDistanceEntries> biomeDistanceSpace = new NPixelBufferView(biomeDistanceAccess, biomeDistanceClass);
/*     */     
/*  71 */     Vector3i position_voxelGrid = new Vector3i();
/*     */     
/*  73 */     for (position_voxelGrid.x = biomeDistanceSpace.minX(); position_voxelGrid.x < biomeDistanceSpace.maxX(); position_voxelGrid.x++) {
/*  74 */       for (position_voxelGrid.z = biomeDistanceSpace.minZ(); position_voxelGrid.z < biomeDistanceSpace.maxZ(); position_voxelGrid.z++) {
/*     */         
/*  76 */         BiomeDistanceEntries distanceEntries = createDistanceTracker(biomeAccess, biomeSpace, position_voxelGrid);
/*  77 */         biomeDistanceSpace.set(distanceEntries, position_voxelGrid);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private BiomeDistanceEntries createDistanceTracker(@Nonnull NBufferBundle.Access.View biomeAccess, @Nonnull NPixelBufferView<BiomeType> biomeSpace, @Nonnull Vector3i targetPosition_voxelGrid) {
/*  88 */     BiomeDistanceCounter counter = new BiomeDistanceCounter();
/*  89 */     Vector3i position_bufferGrid = new Vector3i();
/*     */     
/*  91 */     Bounds3i scanBounds_voxelGrid = GridUtils.createBounds_fromRadius_originVoxelInclusive((int)Math.ceil(this.maxDistance_voxelGrid));
/*  92 */     scanBounds_voxelGrid.offset(targetPosition_voxelGrid);
/*  93 */     Bounds3i scanBounds_bufferGrid = GridUtils.createBufferBoundsInclusive_fromVoxelBounds(scanBounds_voxelGrid);
/*     */     
/*  95 */     for (position_bufferGrid.x = scanBounds_bufferGrid.min.x; position_bufferGrid.x < scanBounds_bufferGrid.max.x; position_bufferGrid.x++) {
/*  96 */       for (position_bufferGrid.z = scanBounds_bufferGrid.min.z; position_bufferGrid.z < scanBounds_bufferGrid.max.z; position_bufferGrid.z++) {
/*     */         
/*  98 */         double distanceToBuffer_voxelGrid = distanceToBuffer_voxelGrid(targetPosition_voxelGrid, position_bufferGrid);
/*  99 */         distanceToBuffer_voxelGrid = Math.max(distanceToBuffer_voxelGrid - 1.0D, 0.0D);
/*     */ 
/*     */         
/* 102 */         if (distanceToBuffer_voxelGrid <= this.maxDistance_voxelGrid) {
/*     */ 
/*     */           
/* 105 */           NCountedPixelBuffer<BiomeType> biomeBuffer = (NCountedPixelBuffer<BiomeType>)biomeAccess.getBuffer(position_bufferGrid).buffer();
/* 106 */           List<BiomeType> uniqueBiomeTypes = biomeBuffer.getUniqueEntries();
/* 107 */           assert !uniqueBiomeTypes.isEmpty();
/*     */ 
/*     */           
/* 110 */           if (!allBiomesAreCountedAndFarther(counter, uniqueBiomeTypes, distanceToBuffer_voxelGrid))
/*     */           {
/*     */ 
/*     */             
/* 114 */             if (uniqueBiomeTypes.size() == 1) {
/* 115 */               if (distanceToBuffer_voxelGrid <= this.maxDistance_voxelGrid)
/*     */               {
/*     */                 
/* 118 */                 counter.accountFor(uniqueBiomeTypes.getFirst(), distanceToBuffer_voxelGrid);
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 123 */               Bounds3i bufferBounds_voxelGrid = GridUtils.createColumnBounds_voxelGrid(position_bufferGrid, 0, 1);
/* 124 */               Vector3i columnPosition_voxelGrid = new Vector3i();
/* 125 */               for (columnPosition_voxelGrid.x = bufferBounds_voxelGrid.min.x; columnPosition_voxelGrid.x < bufferBounds_voxelGrid.max.x; columnPosition_voxelGrid.x++) {
/* 126 */                 for (columnPosition_voxelGrid.z = bufferBounds_voxelGrid.min.z; columnPosition_voxelGrid.z < bufferBounds_voxelGrid.max.z; columnPosition_voxelGrid.z++) {
/*     */                   
/* 128 */                   double distanceToColumn_voxelGrid = Calculator.distance(columnPosition_voxelGrid.x, columnPosition_voxelGrid.z, targetPosition_voxelGrid.x, targetPosition_voxelGrid.z);
/* 129 */                   distanceToColumn_voxelGrid = Math.max(distanceToColumn_voxelGrid - 1.0D, 0.0D);
/*     */                   
/* 131 */                   if (distanceToColumn_voxelGrid <= this.maxDistance_voxelGrid)
/*     */                   
/*     */                   { 
/* 134 */                     BiomeType biomeType = (BiomeType)biomeSpace.getContent(columnPosition_voxelGrid);
/* 135 */                     assert biomeType != null;
/*     */                     
/* 137 */                     counter.accountFor(biomeType, distanceToColumn_voxelGrid); } 
/*     */                 } 
/*     */               } 
/*     */             }  } 
/*     */         } 
/*     */       } 
/* 143 */     }  return new BiomeDistanceEntries(counter.entries);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<NBufferType, Bounds3i> getInputTypesAndBounds_bufferGrid() {
/* 149 */     return (Map)Map.of(this.biomeInputBufferType, this.inputBounds_bufferGrid);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<NBufferType> getOutputTypes() {
/* 155 */     return (List)List.of(this.biomeDistanceOutputBufferType);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/* 161 */     return this.stageName;
/*     */   }
/*     */   
/*     */   public static double distanceToBuffer_voxelGrid(@Nonnull Vector3i position_voxelGrid, @Nonnull Vector3i position_bufferGrid) {
/* 165 */     assert position_voxelGrid.y == 0.0D;
/* 166 */     assert position_bufferGrid.y == 0.0D;
/*     */ 
/*     */ 
/*     */     
/* 170 */     Vector3i bufferAtPosition_bufferGrid = position_voxelGrid.clone();
/* 171 */     GridUtils.toBufferGrid_fromVoxelGrid(bufferAtPosition_bufferGrid);
/* 172 */     if (bufferAtPosition_bufferGrid.x == position_bufferGrid.x && bufferAtPosition_bufferGrid.z == position_bufferGrid.z) {
/* 173 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/* 177 */     int cornerShift = NCountedPixelBuffer.SIZE_VOXEL_GRID.x - 1;
/*     */     
/* 179 */     Vector3i corner00 = position_bufferGrid.clone();
/* 180 */     GridUtils.toVoxelGrid_fromBufferGrid(corner00);
/*     */     
/* 182 */     Vector3i corner01 = new Vector3i(corner00);
/* 183 */     corner01.z += cornerShift;
/*     */ 
/*     */     
/* 186 */     if (position_voxelGrid.x >= corner00.x && position_voxelGrid.x <= corner00.x + cornerShift) {
/* 187 */       return Math.min(Math.abs(position_voxelGrid.z - corner00.z), Math.abs(position_voxelGrid.z - corner01.z));
/*     */     }
/* 189 */     Vector3i corner10 = new Vector3i(corner00);
/* 190 */     corner10.x += cornerShift;
/*     */ 
/*     */     
/* 193 */     if (position_voxelGrid.z >= corner00.z && position_voxelGrid.z <= corner00.z + cornerShift) {
/* 194 */       return Math.min(Math.abs(position_voxelGrid.x - corner00.x), Math.abs(position_voxelGrid.x - corner10.x));
/*     */     }
/*     */     
/* 197 */     if (position_voxelGrid.x < corner00.x && position_voxelGrid.z < corner00.z) {
/* 198 */       return position_voxelGrid.distanceTo(corner00);
/*     */     }
/*     */     
/* 201 */     if (position_voxelGrid.x < corner01.x && position_voxelGrid.z > corner01.z) {
/* 202 */       return position_voxelGrid.distanceTo(corner01);
/*     */     }
/*     */     
/* 205 */     if (position_voxelGrid.x > corner10.x && position_voxelGrid.z < corner10.z) {
/* 206 */       return position_voxelGrid.distanceTo(corner10);
/*     */     }
/* 208 */     Vector3i corner11 = new Vector3i(corner10.x, 0, corner01.z);
/*     */ 
/*     */     
/* 211 */     return position_voxelGrid.distanceTo(corner11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean allBiomesAreCountedAndFarther(@Nonnull BiomeDistanceCounter counter, @Nonnull List<BiomeType> uniqueBiomes, double distanceToBuffer_voxelGrid) {
/* 219 */     for (BiomeType biomeType : uniqueBiomes) {
/* 220 */       if (counter.isCloserThanCounted(biomeType, distanceToBuffer_voxelGrid)) {
/* 221 */         return false;
/*     */       }
/*     */     } 
/* 224 */     return true;
/*     */   }
/*     */   
/*     */   public static class BiomeDistanceEntry {
/*     */     public BiomeType biomeType;
/*     */     public double distance_voxelGrid;
/*     */   }
/*     */   
/*     */   public static class BiomeDistanceEntries {
/*     */     public final List<NBiomeDistanceStage.BiomeDistanceEntry> entries;
/*     */     
/*     */     public BiomeDistanceEntries(@Nonnull List<NBiomeDistanceStage.BiomeDistanceEntry> entries) {
/* 236 */       this.entries = entries;
/*     */     }
/*     */     
/*     */     public double distanceToClosestOtherBiome(@Nonnull BiomeType thisBiome) {
/* 240 */       double smallestDistance = Double.MAX_VALUE;
/* 241 */       for (NBiomeDistanceStage.BiomeDistanceEntry entry : this.entries) {
/* 242 */         if (entry.biomeType == thisBiome)
/* 243 */           continue;  smallestDistance = Math.min(smallestDistance, entry.distance_voxelGrid);
/*     */       } 
/* 245 */       return smallestDistance;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BiomeDistanceCounter {
/*     */     @Nonnull
/*     */     final List<NBiomeDistanceStage.BiomeDistanceEntry> entries;
/*     */     @Nullable
/*     */     NBiomeDistanceStage.BiomeDistanceEntry cachedEntry;
/*     */     
/*     */     BiomeDistanceCounter() {
/* 256 */       this.entries = new ArrayList<>(3);
/* 257 */       this.cachedEntry = null;
/*     */     }
/*     */     
/*     */     boolean isCloserThanCounted(@Nonnull BiomeType biomeType, double distance_voxelGrid) {
/* 261 */       for (NBiomeDistanceStage.BiomeDistanceEntry entry : this.entries) {
/* 262 */         if (entry.biomeType == biomeType) {
/* 263 */           return (distance_voxelGrid < entry.distance_voxelGrid);
/*     */         }
/*     */       } 
/* 266 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     void accountFor(@Nonnull BiomeType biomeType, double distance_voxelGrid) {
/* 271 */       if (this.cachedEntry != null && this.cachedEntry.biomeType == biomeType) {
/* 272 */         if (this.cachedEntry.distance_voxelGrid <= distance_voxelGrid) {
/*     */           return;
/*     */         }
/* 275 */         this.cachedEntry.distance_voxelGrid = distance_voxelGrid;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 280 */       for (NBiomeDistanceStage.BiomeDistanceEntry biomeDistanceEntry : this.entries) {
/* 281 */         if (biomeDistanceEntry.biomeType == biomeType) {
/* 282 */           this.cachedEntry = biomeDistanceEntry;
/* 283 */           if (biomeDistanceEntry.distance_voxelGrid <= distance_voxelGrid) {
/*     */             return;
/*     */           }
/* 286 */           biomeDistanceEntry.distance_voxelGrid = distance_voxelGrid;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 292 */       NBiomeDistanceStage.BiomeDistanceEntry entry = new NBiomeDistanceStage.BiomeDistanceEntry();
/* 293 */       entry.biomeType = biomeType;
/* 294 */       entry.distance_voxelGrid = distance_voxelGrid;
/* 295 */       this.entries.add(entry);
/* 296 */       this.cachedEntry = entry;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NBiomeDistanceStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */