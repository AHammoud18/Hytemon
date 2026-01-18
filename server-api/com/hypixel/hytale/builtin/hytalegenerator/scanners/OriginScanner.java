/*    */ package com.hypixel.hytale.builtin.hytalegenerator.scanners;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.SpaceSize;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OriginScanner
/*    */   extends Scanner {
/* 12 */   private static final OriginScanner instance = new OriginScanner();
/* 13 */   private static final SpaceSize SCAN_SPACE_SIZE = new SpaceSize(new Vector3i(0, 0, 0), new Vector3i(1, 0, 1));
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<Vector3i> scan(@Nonnull Scanner.Context context) {
/* 20 */     Pattern.Context patternContext = new Pattern.Context(context.position, context.materialSpace, context.workerId);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 26 */     if (context.pattern.matches(patternContext)) {
/* 27 */       return Collections.singletonList(context.position.clone());
/*    */     }
/*    */     
/* 30 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public SpaceSize scanSpace() {
/* 37 */     return SCAN_SPACE_SIZE.clone();
/*    */   }
/*    */   
/*    */   public static OriginScanner getInstance() {
/* 41 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\scanners\OriginScanner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */