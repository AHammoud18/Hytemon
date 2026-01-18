/*    */ package com.hypixel.hytale.builtin.hytalegenerator.chunkgenerator;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GeneratorProfile
/*    */ {
/*    */   @Nonnull
/*    */   private final String worldStructureName;
/*    */   @Nonnull
/*    */   private final Transform spawnPosition;
/*    */   private int seed;
/*    */   
/*    */   public GeneratorProfile(@Nonnull String worldStructureName, @Nonnull Transform spawnPosition, int seed) {
/* 23 */     this.worldStructureName = worldStructureName;
/* 24 */     this.spawnPosition = spawnPosition;
/* 25 */     this.seed = seed;
/*    */   }
/*    */   @Nonnull
/*    */   public String worldStructureName() {
/* 29 */     return this.worldStructureName;
/*    */   } @Nonnull
/*    */   public Transform spawnPosition() {
/* 32 */     return this.spawnPosition;
/*    */   } public int seed() {
/* 34 */     return this.seed;
/*    */   } public void setSeed(int seed) {
/* 36 */     this.seed = seed;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 40 */     if (obj == this) return true; 
/* 41 */     if (obj == null || obj.getClass() != getClass()) return false; 
/* 42 */     GeneratorProfile that = (GeneratorProfile)obj;
/* 43 */     return (Objects.equals(this.worldStructureName, that.worldStructureName) && 
/* 44 */       Objects.equals(this.spawnPosition, that.spawnPosition) && this.seed == that.seed);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     return Objects.hash(new Object[] { this.worldStructureName, this.spawnPosition, Integer.valueOf(this.seed) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     return "GeneratorProfile[worldStructureName=" + this.worldStructureName + ", spawnPosition=" + String.valueOf(this.spawnPosition) + ", seed=" + this.seed + "]";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\chunkgenerator\ChunkRequest$GeneratorProfile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */