/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class CuboidPattern
/*    */   extends Pattern
/*    */ {
/*    */   @Nonnull
/*    */   private final Pattern subPattern;
/*    */   @Nonnull
/*    */   private final Vector3i min;
/*    */   @Nonnull
/*    */   private final Vector3i max;
/*    */   @Nonnull
/*    */   private final SpaceSize readSpaceSize;
/*    */   
/*    */   public CuboidPattern(@Nonnull Pattern subPattern, @Nonnull Vector3i min, @Nonnull Vector3i max) {
/* 21 */     this.subPattern = subPattern;
/* 22 */     this.min = min;
/* 23 */     this.max = max;
/* 24 */     this.readSpaceSize = new SpaceSize(min, max.clone().add(1, 1, 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Pattern.Context context) {
/* 29 */     Vector3i scanMin = this.min.clone().add(context.position);
/* 30 */     Vector3i scanMax = this.max.clone().add(context.position);
/*    */     
/* 32 */     Vector3i childPosition = context.position.clone();
/* 33 */     Pattern.Context childContext = new Pattern.Context(context);
/* 34 */     childContext.position = childPosition;
/*    */     
/* 36 */     for (childPosition.x = scanMin.x; childPosition.x <= scanMax.x; childPosition.x++) {
/* 37 */       for (childPosition.z = scanMin.z; childPosition.z <= scanMax.z; childPosition.z++) {
/* 38 */         for (childPosition.y = scanMin.y; childPosition.y <= scanMax.y; childPosition.y++)
/*    */         
/* 40 */         { if (!context.materialSpace.isInsideSpace(childPosition))
/* 41 */             return false; 
/* 42 */           if (!this.subPattern.matches(childContext))
/* 43 */             return false;  } 
/*    */       } 
/* 45 */     }  return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize readSpace() {
/* 51 */     return this.readSpaceSize.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\CuboidPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */