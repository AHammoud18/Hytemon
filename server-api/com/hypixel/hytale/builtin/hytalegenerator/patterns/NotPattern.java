/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NotPattern
/*    */   extends Pattern {
/*    */   @Nonnull
/*    */   private final Pattern pattern;
/*    */   private final SpaceSize readSpaceSize;
/*    */   
/*    */   public NotPattern(@Nonnull Pattern pattern) {
/* 13 */     this.pattern = pattern;
/* 14 */     this.readSpaceSize = pattern.readSpace();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Pattern.Context context) {
/* 19 */     return !this.pattern.matches(context);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize readSpace() {
/* 25 */     return this.readSpaceSize.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\NotPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */