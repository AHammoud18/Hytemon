/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.material.SolidMaterial;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NVoxelBufferView;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.procedurallib.logic.SimplexNoise;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NTestTerrainStage
/*    */   implements NStage {
/* 19 */   private static final Class<NVoxelBuffer> bufferClass = NVoxelBuffer.class;
/* 20 */   private static final Class<SolidMaterial> solidMaterialClass = SolidMaterial.class;
/*    */   
/*    */   private final NParametrizedBufferType outputBufferType;
/*    */   private final SolidMaterial ground;
/*    */   private final SolidMaterial empty;
/*    */   
/*    */   public NTestTerrainStage(@Nonnull NBufferType outputBufferType, @Nonnull SolidMaterial groundMaterial, @Nonnull SolidMaterial emptyMaterial) {
/* 27 */     assert outputBufferType instanceof NParametrizedBufferType;
/*    */     
/* 29 */     this.outputBufferType = (NParametrizedBufferType)outputBufferType;
/* 30 */     assert this.outputBufferType.isValidType(bufferClass, solidMaterialClass);
/*    */     
/* 32 */     this.ground = groundMaterial;
/* 33 */     this.empty = emptyMaterial;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(@Nonnull NStage.Context context) {
/* 38 */     NBufferBundle.Access.View access = context.bufferAccess.get(this.outputBufferType);
/* 39 */     NVoxelBufferView<SolidMaterial> materialBuffer = new NVoxelBufferView(access, solidMaterialClass);
/*    */     
/* 41 */     SimplexNoise noise = SimplexNoise.INSTANCE;
/*    */     
/* 43 */     Vector3i position = new Vector3i();
/* 44 */     for (position.x = materialBuffer.minX(); position.x < materialBuffer.maxX(); position.x++) {
/* 45 */       for (position.z = materialBuffer.minZ(); position.z < materialBuffer.maxZ(); position.z++) {
/* 46 */         for (position.y = materialBuffer.minY(); position.y < materialBuffer.maxY(); position.y++) {
/*    */           
/* 48 */           Vector3d noisePosition = position.toVector3d();
/* 49 */           noisePosition.scale(0.05D);
/*    */           
/* 51 */           double noiseValue = noise.get(1, 0, noisePosition.x, noisePosition.y, noisePosition.z);
/*    */           
/* 53 */           if (position.y < 130 || (noiseValue > 0.0D && position.y < 150)) {
/* 54 */             materialBuffer.set(this.ground, position);
/*    */           } else {
/* 56 */             materialBuffer.set(this.empty, position);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Map<NBufferType, Bounds3i> getInputTypesAndBounds_bufferGrid() {
/* 67 */     return Map.of();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<NBufferType> getOutputTypes() {
/* 73 */     return (List)List.of(this.outputBufferType);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getName() {
/* 79 */     return "TestTerrainStage";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NTestTerrainStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */