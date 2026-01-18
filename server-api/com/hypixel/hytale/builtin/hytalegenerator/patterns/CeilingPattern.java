/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CeilingPattern
/*    */   extends Pattern {
/*    */   @Nonnull
/*    */   private final Pattern ceilingPattern;
/*    */   @Nonnull
/*    */   private final Pattern airPattern;
/*    */   @Nonnull
/*    */   private final SpaceSize readSpaceSize;
/*    */   
/*    */   public CeilingPattern(@Nonnull Pattern ceilingPattern, @Nonnull Pattern airPattern) {
/* 17 */     this.ceilingPattern = ceilingPattern;
/* 18 */     this.airPattern = airPattern;
/*    */ 
/*    */     
/* 21 */     SpaceSize ceilingSpace = ceilingPattern.readSpace();
/* 22 */     ceilingSpace.moveBy(new Vector3i(0, 1, 0));
/* 23 */     this.readSpaceSize = SpaceSize.merge(ceilingSpace, airPattern.readSpace());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Pattern.Context context) {
/* 28 */     Vector3i ceilingPosition = new Vector3i(context.position.x, context.position.y + 1, context.position.z);
/*    */     
/* 30 */     if (!context.materialSpace.isInsideSpace(context.position) || 
/* 31 */       !context.materialSpace.isInsideSpace(ceilingPosition)) {
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     Pattern.Context ceilingContext = new Pattern.Context(context);
/* 36 */     ceilingContext.position = ceilingPosition;
/*    */     
/* 38 */     return (this.airPattern.matches(context) && this.ceilingPattern
/* 39 */       .matches(ceilingContext));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize readSpace() {
/* 45 */     return this.readSpaceSize.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\CeilingPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */