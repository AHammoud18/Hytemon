/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.SeedGenerator;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*    */ import com.hypixel.hytale.math.util.FastRandom;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RandomDirectionality
/*    */   extends Directionality
/*    */ {
/*    */   @Nonnull
/*    */   private final List<PrefabRotation> rotations;
/*    */   @Nonnull
/*    */   private final Pattern pattern;
/*    */   @Nonnull
/*    */   private final SeedGenerator seedGenerator;
/*    */   
/*    */   public RandomDirectionality(@Nonnull Pattern pattern, int seed) {
/* 28 */     this.pattern = pattern;
/* 29 */     this.seedGenerator = new SeedGenerator(seed);
/* 30 */     this.rotations = Collections.unmodifiableList(
/* 31 */         List.of(PrefabRotation.ROTATION_0, PrefabRotation.ROTATION_90, PrefabRotation.ROTATION_180, PrefabRotation.ROTATION_270));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern getGeneralPattern() {
/* 37 */     return this.pattern;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i getReadRangeWith(@Nonnull Scanner scanner) {
/* 43 */     return scanner.readSpaceWith(this.pattern).getRange();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<PrefabRotation> getPossibleRotations() {
/* 49 */     return this.rotations;
/*    */   }
/*    */ 
/*    */   
/*    */   public PrefabRotation getRotationAt(@Nonnull Pattern.Context context) {
/* 54 */     FastRandom random = new FastRandom(this.seedGenerator.seedAt(context.position.x, context.position.y, context.position.z));
/* 55 */     return this.rotations.get(random.nextInt(this.rotations.size()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\directionality\RandomDirectionality.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */