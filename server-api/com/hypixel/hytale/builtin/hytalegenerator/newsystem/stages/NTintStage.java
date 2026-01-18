/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NCountedPixelBuffer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NSimplePixelBuffer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NPixelBufferView;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.tintproviders.TintProvider;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NTintStage
/*    */   implements NStage {
/* 20 */   public static final Class<NCountedPixelBuffer> biomeBufferClass = NCountedPixelBuffer.class;
/* 21 */   public static final Class<BiomeType> biomeTypeClass = BiomeType.class;
/*    */   
/* 23 */   public static final Class<NSimplePixelBuffer> tintBufferClass = NSimplePixelBuffer.class;
/* 24 */   public static final Class<Integer> tintClass = Integer.class;
/*    */ 
/*    */   
/*    */   private final NParametrizedBufferType biomeInputBufferType;
/*    */ 
/*    */   
/*    */   private final NParametrizedBufferType tintOutputBufferType;
/*    */   
/*    */   private final Bounds3i inputBounds_bufferGrid;
/*    */   
/*    */   private final String stageName;
/*    */ 
/*    */   
/*    */   public NTintStage(@Nonnull String stageName, @Nonnull NParametrizedBufferType biomeInputBufferType, @Nonnull NParametrizedBufferType tintOutputBufferType) {
/* 38 */     assert biomeInputBufferType.isValidType(biomeBufferClass, biomeTypeClass);
/* 39 */     assert tintOutputBufferType.isValidType(tintBufferClass, tintClass);
/*    */     
/* 41 */     this.biomeInputBufferType = biomeInputBufferType;
/* 42 */     this.tintOutputBufferType = tintOutputBufferType;
/*    */     
/* 44 */     this.stageName = stageName;
/* 45 */     this.inputBounds_bufferGrid = GridUtils.createUnitBounds3i(Vector3i.ZERO);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(@Nonnull NStage.Context context) {
/* 50 */     NBufferBundle.Access.View biomeAccess = context.bufferAccess.get(this.biomeInputBufferType);
/* 51 */     NPixelBufferView<BiomeType> biomeSpace = new NPixelBufferView(biomeAccess, biomeTypeClass);
/*    */     
/* 53 */     NBufferBundle.Access.View tintAccess = context.bufferAccess.get(this.tintOutputBufferType);
/* 54 */     NPixelBufferView<Integer> tintSpace = new NPixelBufferView(tintAccess, tintClass);
/*    */     
/* 56 */     Bounds3i outputBounds_voxelGrid = tintSpace.getBounds();
/*    */     
/* 58 */     Vector3i position_voxelGrid = new Vector3i(outputBounds_voxelGrid.min);
/* 59 */     position_voxelGrid.setY(0);
/* 60 */     TintProvider.Context tintContext = new TintProvider.Context(position_voxelGrid, context.workerId);
/*    */     
/* 62 */     for (position_voxelGrid.x = outputBounds_voxelGrid.min.x; position_voxelGrid.x < outputBounds_voxelGrid.max.x; position_voxelGrid.x++) {
/* 63 */       for (position_voxelGrid.z = outputBounds_voxelGrid.min.z; position_voxelGrid.z < outputBounds_voxelGrid.max.z; position_voxelGrid.z++) {
/*    */         
/* 65 */         BiomeType biome = (BiomeType)biomeSpace.getContent(position_voxelGrid.x, 0, position_voxelGrid.z);
/* 66 */         assert biome != null;
/* 67 */         TintProvider tintProvider = biome.getTintProvider();
/* 68 */         TintProvider.Result tintResult = tintProvider.getValue(tintContext);
/*    */         
/* 70 */         if (!tintResult.hasValue) {
/* 71 */           tintSpace.set(Integer.valueOf(TintProvider.DEFAULT_TINT), position_voxelGrid);
/*    */         } else {
/*    */           
/* 74 */           tintSpace.set(Integer.valueOf(tintResult.tint), position_voxelGrid);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Map<NBufferType, Bounds3i> getInputTypesAndBounds_bufferGrid() {
/* 82 */     return (Map)Map.of(this.biomeInputBufferType, this.inputBounds_bufferGrid);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<NBufferType> getOutputTypes() {
/* 88 */     return (List)List.of(this.tintOutputBufferType);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getName() {
/* 94 */     return this.stageName;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NTintStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */