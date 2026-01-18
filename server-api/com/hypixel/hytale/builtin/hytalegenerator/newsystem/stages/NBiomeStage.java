/*    */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.stages;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.biome.BiomeType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiCarta;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NCountedPixelBuffer;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NBufferType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.type.NParametrizedBufferType;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.views.NPixelBufferView;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NBiomeStage
/*    */   implements NStage {
/* 17 */   public static final Class<NCountedPixelBuffer> bufferClass = NCountedPixelBuffer.class;
/* 18 */   public static final Class<BiomeType> biomeTypeClass = BiomeType.class;
/*    */ 
/*    */   
/*    */   private final NParametrizedBufferType biomeOutputBufferType;
/*    */ 
/*    */   
/*    */   private final String stageName;
/*    */   
/*    */   private BiCarta<BiomeType> biomeCarta;
/*    */ 
/*    */   
/*    */   public NBiomeStage(@Nonnull String stageName, @Nonnull NParametrizedBufferType biomeOutputBufferType, @Nonnull BiCarta<BiomeType> biomeCarta) {
/* 30 */     this.stageName = stageName;
/* 31 */     this.biomeOutputBufferType = biomeOutputBufferType;
/* 32 */     this.biomeCarta = biomeCarta;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(@Nonnull NStage.Context context) {
/* 37 */     NBufferBundle.Access.View biomeAccess = context.bufferAccess.get(this.biomeOutputBufferType);
/* 38 */     NPixelBufferView<BiomeType> biomeSpace = new NPixelBufferView(biomeAccess, biomeTypeClass);
/*    */     
/* 40 */     for (int x = biomeSpace.minX(); x < biomeSpace.maxX(); x++) {
/* 41 */       for (int z = biomeSpace.minZ(); z < biomeSpace.maxZ(); z++) {
/* 42 */         BiomeType biome = (BiomeType)this.biomeCarta.apply(x, z, context.workerId);
/* 43 */         biomeSpace.set(biome, x, 0, z);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Map<NBufferType, Bounds3i> getInputTypesAndBounds_bufferGrid() {
/* 51 */     return Map.of();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<NBufferType> getOutputTypes() {
/* 57 */     return (List)List.of(this.biomeOutputBufferType);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getName() {
/* 63 */     return this.stageName;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\stages\NBiomeStage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */