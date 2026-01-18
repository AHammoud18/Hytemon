/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.environmentproviders.EnvironmentProvider;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NCountedPixelBuffer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NPixelBufferView;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NVoxelBufferView;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NEnvironmentStage
/*    */   implements NStage {
/* 21 */   public static final Class<NCountedPixelBuffer> biomeBufferClass = NCountedPixelBuffer.class;
/* 22 */   public static final Class<BiomeType> biomeTypeClass = BiomeType.class;
/*    */   
/* 24 */   public static final Class<NVoxelBuffer> environmentBufferClass = NVoxelBuffer.class;
/* 25 */   public static final Class<Integer> environmentClass = Integer.class;
/*    */ 
/*    */   
/*    */   private final NParametrizedBufferType biomeInputBufferType;
/*    */ 
/*    */   
/*    */   private final NParametrizedBufferType environmentOutputBufferType;
/*    */   
/*    */   private final Bounds3i inputBounds_bufferGrid;
/*    */   
/*    */   private final String stageName;
/*    */ 
/*    */   
/*    */   public NEnvironmentStage(@Nonnull String stageName, @Nonnull NParametrizedBufferType biomeInputBufferType, @Nonnull NParametrizedBufferType environmentOutputBufferType) {
/* 39 */     assert biomeInputBufferType.isValidType(biomeBufferClass, biomeTypeClass);
/* 40 */     assert environmentOutputBufferType.isValidType(environmentBufferClass, environmentClass);
/*    */     
/* 42 */     this.biomeInputBufferType = biomeInputBufferType;
/* 43 */     this.environmentOutputBufferType = environmentOutputBufferType;
/*    */     
/* 45 */     this.stageName = stageName;
/* 46 */     this.inputBounds_bufferGrid = GridUtils.createUnitBounds3i(Vector3i.ZERO);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(@Nonnull NStage.Context context) {
/* 51 */     NBufferBundle.Access.View biomeAccess = context.bufferAccess.get(this.biomeInputBufferType);
/* 52 */     NPixelBufferView<BiomeType> biomeSpace = new NPixelBufferView(biomeAccess, biomeTypeClass);
/*    */     
/* 54 */     NBufferBundle.Access.View environmentAccess = context.bufferAccess.get(this.environmentOutputBufferType);
/* 55 */     NVoxelBufferView<Integer> environmentSpace = new NVoxelBufferView(environmentAccess, environmentClass);
/*    */     
/* 57 */     Bounds3i outputBounds_voxelGrid = environmentSpace.getBounds();
/* 58 */     Vector3i position_voxelGrid = new Vector3i(outputBounds_voxelGrid.min);
/* 59 */     EnvironmentProvider.Context tintContext = new EnvironmentProvider.Context(position_voxelGrid, context.workerId);
/*    */     
/* 61 */     for (position_voxelGrid.x = outputBounds_voxelGrid.min.x; position_voxelGrid.x < outputBounds_voxelGrid.max.x; position_voxelGrid.x++) {
/* 62 */       for (position_voxelGrid.z = outputBounds_voxelGrid.min.z; position_voxelGrid.z < outputBounds_voxelGrid.max.z; position_voxelGrid.z++) {
/* 63 */         BiomeType biome = (BiomeType)biomeSpace.getContent(position_voxelGrid.x, 0, position_voxelGrid.z);
/* 64 */         assert biome != null;
/* 65 */         EnvironmentProvider environmentProvider = biome.getEnvironmentProvider();
/*    */         
/* 67 */         for (position_voxelGrid.y = outputBounds_voxelGrid.min.y; position_voxelGrid.y < outputBounds_voxelGrid.max.y; position_voxelGrid.y++) {
/* 68 */           int environment = environmentProvider.getValue(tintContext);
/* 69 */           environmentSpace.set(Integer.valueOf(environment), position_voxelGrid);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Map<NBufferType, Bounds3i> getInputTypesAndBounds_bufferGrid() {
/* 78 */     return (Map)Map.of(this.biomeInputBufferType, this.inputBounds_bufferGrid);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<NBufferType> getOutputTypes() {
/* 84 */     return (List)List.of(this.environmentOutputBufferType);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getName() {
/* 90 */     return this.stageName;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NEnvironmentStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */