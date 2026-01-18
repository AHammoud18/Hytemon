/*    */ package com.hypixel.hytale.builtin.hytalegenerator.patterns;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AndPattern
/*    */   extends Pattern {
/*    */   @Nonnull
/*    */   private final Pattern[] patterns;
/*    */   private final SpaceSize readSpaceSize;
/*    */   
/*    */   public AndPattern(@Nonnull List<Pattern> patterns) {
/* 14 */     if (patterns.isEmpty()) {
/* 15 */       this.patterns = new Pattern[0];
/* 16 */       this.readSpaceSize = SpaceSize.empty();
/*    */       
/*    */       return;
/*    */     } 
/* 20 */     this.patterns = new Pattern[patterns.size()];
/* 21 */     SpaceSize spaceAcc = ((Pattern)patterns.getFirst()).readSpace();
/*    */     
/* 23 */     for (int i = 0; i < patterns.size(); i++) {
/* 24 */       Pattern pattern = patterns.get(i);
/* 25 */       this.patterns[i] = pattern;
/* 26 */       spaceAcc = SpaceSize.merge(spaceAcc, pattern.readSpace());
/*    */     } 
/* 28 */     this.readSpaceSize = spaceAcc;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(@Nonnull Pattern.Context context) {
/* 33 */     for (Pattern pattern : this.patterns) {
/* 34 */       if (!pattern.matches(context)) return false; 
/*    */     } 
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize readSpace() {
/* 42 */     return this.readSpaceSize.clone();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\patterns\AndPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */