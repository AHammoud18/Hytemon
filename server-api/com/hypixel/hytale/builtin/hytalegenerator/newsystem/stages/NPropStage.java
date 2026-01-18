/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.PropField;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3d;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.Material;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.material.MaterialCache;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NCountedPixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NEntityBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NSimplePixelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.EntityContainer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NEntityBufferView;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NPixelBufferView;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NVoxelBufferView;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.positionproviders.PositionProvider;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.props.ScanResult;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NPropStage implements NStage {
/*     */   public static final double DEFAULT_BACKGROUND_DENSITY = 0.0D;
/*  38 */   public static final Class<NCountedPixelBuffer> biomeBufferClass = NCountedPixelBuffer.class;
/*  39 */   public static final Class<BiomeType> biomeTypeClass = BiomeType.class;
/*     */   
/*  41 */   public static final Class<NSimplePixelBuffer> biomeDistanceBufferClass = NSimplePixelBuffer.class;
/*  42 */   public static final Class<NBiomeDistanceStage.BiomeDistanceEntries> biomeDistanceClass = NBiomeDistanceStage.BiomeDistanceEntries.class;
/*     */   
/*  44 */   public static final Class<NVoxelBuffer> materialBufferClass = NVoxelBuffer.class;
/*  45 */   public static final Class<Material> materialClass = Material.class;
/*     */   
/*  47 */   public static final Class<NEntityBuffer> entityBufferClass = NEntityBuffer.class;
/*     */ 
/*     */   
/*     */   private final NParametrizedBufferType biomeInputBufferType;
/*     */ 
/*     */   
/*     */   private final NParametrizedBufferType biomeDistanceInputBufferType;
/*     */ 
/*     */   
/*     */   private final NParametrizedBufferType materialInputBufferType;
/*     */   
/*     */   private final NBufferType entityInputBufferType;
/*     */   
/*     */   private final NParametrizedBufferType materialOutputBufferType;
/*     */   
/*     */   private final NBufferType entityOutputBufferType;
/*     */   
/*     */   private final Bounds3i inputBounds_bufferGrid;
/*     */   
/*     */   private final Bounds3i inputBounds_voxelGrid;
/*     */   
/*     */   private final String stageName;
/*     */   
/*     */   private final MaterialCache materialCache;
/*     */   
/*     */   private final int runtimeIndex;
/*     */ 
/*     */   
/*     */   public NPropStage(@Nonnull String stageName, @Nonnull NParametrizedBufferType biomeInputBufferType, @Nonnull NParametrizedBufferType biomeDistanceInputBufferType, @Nonnull NParametrizedBufferType materialInputBufferType, @Nullable NBufferType entityInputBufferType, @Nonnull NParametrizedBufferType materialOutputBufferType, @Nonnull NBufferType entityOutputBufferType, @Nonnull MaterialCache materialCache, @Nonnull List<BiomeType> expectedBiomes, int runtimeIndex) {
/*  76 */     assert biomeInputBufferType.isValidType(biomeBufferClass, biomeTypeClass);
/*  77 */     assert biomeDistanceInputBufferType.isValidType(biomeDistanceBufferClass, biomeDistanceClass);
/*  78 */     assert materialInputBufferType.isValidType(materialBufferClass, materialClass);
/*  79 */     assert entityInputBufferType == null || entityInputBufferType.isValidType(entityBufferClass);
/*  80 */     assert materialOutputBufferType.isValidType(materialBufferClass, materialClass);
/*  81 */     assert entityOutputBufferType.isValidType(entityBufferClass);
/*     */     
/*  83 */     this.biomeInputBufferType = biomeInputBufferType;
/*  84 */     this.biomeDistanceInputBufferType = biomeDistanceInputBufferType;
/*  85 */     this.materialInputBufferType = materialInputBufferType;
/*  86 */     this.entityInputBufferType = entityInputBufferType;
/*  87 */     this.materialOutputBufferType = materialOutputBufferType;
/*  88 */     this.entityOutputBufferType = entityOutputBufferType;
/*     */     
/*  90 */     this.stageName = stageName;
/*  91 */     this.materialCache = materialCache;
/*  92 */     this.runtimeIndex = runtimeIndex;
/*     */     
/*  94 */     this.inputBounds_voxelGrid = new Bounds3i();
/*  95 */     Vector3i range = new Vector3i();
/*  96 */     for (BiomeType biome : expectedBiomes) {
/*  97 */       for (PropField propField : biome.getPropFields()) {
/*  98 */         if (propField.getRuntime() != this.runtimeIndex) {
/*     */           continue;
/*     */         }
/*     */         
/* 102 */         for (Prop prop : propField.getPropDistribution().getAllPossibleProps()) {
/* 103 */           Vector3i readRange_voxelGrid = prop.getContextDependency().getReadRange();
/* 104 */           Vector3i writeRange_voxelGrid = prop.getContextDependency().getWriteRange();
/*     */           
/* 106 */           readRange_voxelGrid.x += writeRange_voxelGrid.x;
/* 107 */           readRange_voxelGrid.y += writeRange_voxelGrid.y;
/* 108 */           readRange_voxelGrid.z += writeRange_voxelGrid.z;
/*     */           
/* 110 */           this.inputBounds_voxelGrid.encompass(range.clone().add(Vector3i.ALL_ONES));
/* 111 */           range.scale(-1);
/* 112 */           this.inputBounds_voxelGrid.encompass(range);
/*     */         } 
/*     */       } 
/*     */     } 
/* 116 */     this.inputBounds_voxelGrid.min.y = 0;
/* 117 */     this.inputBounds_voxelGrid.max.y = 320;
/* 118 */     this.inputBounds_bufferGrid = GridUtils.createBufferBoundsInclusive_fromVoxelBounds(this.inputBounds_voxelGrid);
/* 119 */     GridUtils.setBoundsYToWorldHeight_bufferGrid(this.inputBounds_bufferGrid);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(@Nonnull NStage.Context context) {
/* 124 */     NBufferBundle.Access.View biomeAccess = context.bufferAccess.get(this.biomeInputBufferType);
/* 125 */     NPixelBufferView<BiomeType> biomeInputSpace = new NPixelBufferView(biomeAccess, biomeTypeClass);
/*     */     
/* 127 */     NBufferBundle.Access.View biomeDistanceAccess = context.bufferAccess.get(this.biomeDistanceInputBufferType);
/* 128 */     NPixelBufferView<NBiomeDistanceStage.BiomeDistanceEntries> biomeDistanceSpace = new NPixelBufferView(biomeDistanceAccess, biomeDistanceClass);
/*     */     
/* 130 */     NBufferBundle.Access.View materialInputAccess = context.bufferAccess.get(this.materialInputBufferType);
/* 131 */     NVoxelBufferView<Material> materialInputSpace = new NVoxelBufferView(materialInputAccess, materialClass);
/*     */     
/* 133 */     NBufferBundle.Access.View materialOutputAccess = context.bufferAccess.get(this.materialOutputBufferType);
/* 134 */     NVoxelBufferView<Material> materialOutputSpace = new NVoxelBufferView(materialOutputAccess, materialClass);
/*     */     
/* 136 */     NBufferBundle.Access.View entityOutputAccess = context.bufferAccess.get(this.entityOutputBufferType);
/* 137 */     NEntityBufferView entityOutputSpace = new NEntityBufferView(entityOutputAccess);
/*     */     
/* 139 */     Bounds3i localOutputBounds_voxelGrid = materialOutputSpace.getBounds();
/* 140 */     Bounds3i localInputBounds_voxelGrid = this.inputBounds_voxelGrid.clone();
/*     */     
/* 142 */     Bounds3i absoluteOutputBounds_voxelGrid = localOutputBounds_voxelGrid.clone();
/* 143 */     absoluteOutputBounds_voxelGrid.offset(localOutputBounds_voxelGrid.min.clone().scale(-1));
/*     */     
/* 145 */     localInputBounds_voxelGrid.stack(absoluteOutputBounds_voxelGrid);
/* 146 */     localInputBounds_voxelGrid.offset(localOutputBounds_voxelGrid.min);
/* 147 */     localInputBounds_voxelGrid.min.y = 0;
/* 148 */     localInputBounds_voxelGrid.max.y = 320;
/*     */ 
/*     */     
/* 151 */     Bounds3d localInputBoundsDouble_voxelGrid = localInputBounds_voxelGrid.toBounds3d();
/*     */     
/* 153 */     materialOutputSpace.copyFrom(materialInputSpace);
/*     */     
/* 155 */     if (this.entityInputBufferType != null) {
/* 156 */       NBufferBundle.Access.View entityInputAccess = context.bufferAccess.get(this.entityInputBufferType);
/* 157 */       NEntityBufferView entityInputSpace = new NEntityBufferView(entityInputAccess);
/* 158 */       entityOutputSpace.copyFrom(entityInputSpace);
/*     */     } 
/*     */ 
/*     */     
/* 162 */     HashSet<BiomeType> biomesInBuffer = new HashSet<>();
/* 163 */     for (int x = localInputBounds_voxelGrid.min.x; x < localInputBounds_voxelGrid.max.x; x++) {
/* 164 */       for (int z = localInputBounds_voxelGrid.min.z; z < localInputBounds_voxelGrid.max.z; z++) {
/* 165 */         biomesInBuffer.add((BiomeType)biomeInputSpace.getContent(x, 0, z));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 170 */     Map<PropField, BiomeType> propFieldBiomeMap = new HashMap<>();
/* 171 */     for (BiomeType biome : biomesInBuffer) {
/* 172 */       for (PropField propField : biome.getPropFields()) {
/* 173 */         if (propField.getRuntime() != this.runtimeIndex) {
/*     */           continue;
/*     */         }
/* 176 */         propFieldBiomeMap.put(propField, biome);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 181 */     for (Iterator<Map.Entry<PropField, BiomeType>> iterator = propFieldBiomeMap.entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<PropField, BiomeType> entry = iterator.next();
/* 182 */       PropField propField = entry.getKey();
/* 183 */       BiomeType biome = entry.getValue();
/*     */       
/* 185 */       PositionProvider positionProvider = propField.getPositionProvider();
/* 186 */       Consumer<Vector3d> positionsConsumer = position -> {
/*     */           if (!localInputBoundsDouble_voxelGrid.contains(position)) {
/*     */             return;
/*     */           }
/*     */           
/*     */           Vector3i positionInt_voxelGrid = position.toVector3i();
/*     */           
/*     */           BiomeType biomeAtPosition = (BiomeType)biomeInputSpace.getContent(positionInt_voxelGrid.x, 0, positionInt_voxelGrid.z);
/*     */           
/*     */           if (biomeAtPosition != biome) {
/*     */             return;
/*     */           }
/*     */           
/*     */           Vector3i position2d_voxelGrid = positionInt_voxelGrid.clone();
/*     */           
/*     */           position2d_voxelGrid.setY(0);
/*     */           
/*     */           double distanceToBiomeEdge = ((NBiomeDistanceStage.BiomeDistanceEntries)biomeDistanceSpace.getContent(position2d_voxelGrid)).distanceToClosestOtherBiome(biomeAtPosition);
/*     */           
/*     */           Prop prop = propField.getPropDistribution().propAt(position, context.workerId, distanceToBiomeEdge);
/*     */           
/*     */           Bounds3i propWriteBounds = prop.getWriteBounds().clone();
/*     */           
/*     */           propWriteBounds.offset(positionInt_voxelGrid);
/*     */           
/*     */           if (!propWriteBounds.intersects(localOutputBounds_voxelGrid)) {
/*     */             return;
/*     */           }
/*     */           
/*     */           ScanResult scanResult = prop.scan(positionInt_voxelGrid, (VoxelSpace)materialInputSpace, context.workerId);
/*     */           
/*     */           Prop.Context propContext = new Prop.Context(scanResult, (VoxelSpace)materialOutputSpace, (EntityContainer)entityOutputSpace, context.workerId, distanceToBiomeEdge);
/*     */           
/*     */           prop.place(propContext);
/*     */         };
/* 221 */       PositionProvider.Context positionsContext = new PositionProvider.Context(localInputBoundsDouble_voxelGrid.min, localInputBoundsDouble_voxelGrid.max, positionsConsumer, null, context.workerId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 228 */       positionProvider.positionsIn(positionsContext); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Map<NBufferType, Bounds3i> getInputTypesAndBounds_bufferGrid() {
/* 235 */     Map<NBufferType, Bounds3i> map = new HashMap<>();
/* 236 */     map.put(this.biomeInputBufferType, this.inputBounds_bufferGrid);
/* 237 */     map.put(this.biomeDistanceInputBufferType, this.inputBounds_bufferGrid);
/* 238 */     map.put(this.materialInputBufferType, this.inputBounds_bufferGrid);
/* 239 */     if (this.entityInputBufferType != null) {
/* 240 */       map.put(this.entityInputBufferType, this.inputBounds_bufferGrid);
/*     */     }
/* 242 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<NBufferType> getOutputTypes() {
/* 248 */     return (List)List.of(this.materialOutputBufferType, this.entityOutputBufferType);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/* 254 */     return this.stageName;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NPropStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */