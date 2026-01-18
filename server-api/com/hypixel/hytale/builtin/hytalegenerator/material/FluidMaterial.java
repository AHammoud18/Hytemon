/*    */ package com.hypixel.hytale.builtin.hytalegenerator.material;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class FluidMaterial
/*    */ {
/*    */   private final MaterialCache materialCache;
/*    */   public final int fluidId;
/*    */   public final byte fluidLevel;
/*    */   
/*    */   FluidMaterial(@Nonnull MaterialCache materialCache, int fluidId, byte fluidLevel) {
/* 13 */     this.materialCache = materialCache;
/* 14 */     this.fluidId = fluidId;
/* 15 */     this.fluidLevel = fluidLevel;
/*    */   }
/*    */   
/*    */   public MaterialCache getVoxelCache() {
/* 19 */     return this.materialCache;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     FluidMaterial that;
/* 24 */     if (o instanceof FluidMaterial) { that = (FluidMaterial)o; } else { return false; }
/*    */     
/* 26 */     return (this.fluidId == that.fluidId && this.fluidLevel == that.fluidLevel && this.materialCache.equals(that.materialCache));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 31 */     return contentHash(this.fluidId, this.fluidLevel);
/*    */   }
/*    */   
/*    */   public static int contentHash(int blockId, byte fluidLevel) {
/* 35 */     return Objects.hash(new Object[] { Integer.valueOf(blockId), Byte.valueOf(fluidLevel) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "FluidMaterial{materialCache=" + String.valueOf(this.materialCache) + ", fluidId=" + this.fluidId + ", fluidLevel=" + this.fluidLevel + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\material\FluidMaterial.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */