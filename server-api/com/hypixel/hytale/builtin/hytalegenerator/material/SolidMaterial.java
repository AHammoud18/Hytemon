/*    */ package com.hypixel.hytale.builtin.hytalegenerator.material;
/*    */ 
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class SolidMaterial
/*    */ {
/*    */   private final MaterialCache materialCache;
/*    */   public final int blockId;
/*    */   public final int support;
/*    */   public final int rotation;
/*    */   public final int filler;
/*    */   @Nullable
/*    */   public final Holder<ChunkStore> holder;
/*    */   
/*    */   SolidMaterial(@Nonnull MaterialCache materialCache, int blockId, int support, int rotation, int filler, @Nullable Holder<ChunkStore> holder) {
/* 21 */     this.materialCache = materialCache;
/* 22 */     this.blockId = blockId;
/* 23 */     this.support = support;
/* 24 */     this.rotation = rotation;
/* 25 */     this.filler = filler;
/* 26 */     this.holder = holder;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/*    */     SolidMaterial that;
/* 31 */     if (o instanceof SolidMaterial) { that = (SolidMaterial)o; } else { return false; }
/*    */     
/* 33 */     return (this.blockId == that.blockId && this.support == that.support && this.rotation == that.rotation && this.filler == that.filler && Objects.equals(this.materialCache, that.materialCache) && Objects.equals(this.holder, that.holder));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 38 */     return contentHash(this.blockId, this.support, this.rotation, this.filler, this.holder);
/*    */   }
/*    */   
/*    */   public static int contentHash(int blockId, int support, int rotation, int filler, @Nullable Holder<ChunkStore> holder) {
/* 42 */     return Objects.hash(new Object[] { Integer.valueOf(blockId), Integer.valueOf(support), Integer.valueOf(rotation), Integer.valueOf(filler), holder });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return "SolidMaterial{materialCache=" + String.valueOf(this.materialCache) + ", blockId=" + this.blockId + ", support=" + this.support + ", rotation=" + this.rotation + ", filler=" + this.filler + ", holder=" + String.valueOf(this.holder) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\material\SolidMaterial.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */