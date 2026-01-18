/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OffsetPattern
/*    */   extends Pattern {
/*    */   @Nonnull
/*    */   private final Pattern pattern;
/*    */   @Nonnull
/*    */   private final Vector3i offset;
/*    */   @Nonnull
/*    */   private final SpaceSize readSpaceSize;
/*    */   
/*    */   public OffsetPattern(@Nonnull Pattern pattern, @Nonnull Vector3i offset) {
/* 17 */     this.pattern = pattern;
/* 18 */     this.offset = offset;
/* 19 */     this.readSpaceSize = pattern.readSpace().moveBy(offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Pattern.Context context) {
/* 24 */     Pattern.Context childContext = new Pattern.Context(context);
/* 25 */     childContext.position = context.position.clone().add(this.offset);
/* 26 */     return this.pattern.matches(childContext);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize readSpace() {
/* 32 */     return this.readSpaceSize.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\OffsetPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */