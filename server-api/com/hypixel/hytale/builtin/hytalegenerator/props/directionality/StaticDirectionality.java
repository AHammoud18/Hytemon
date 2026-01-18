/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class StaticDirectionality
/*    */   extends Directionality
/*    */ {
/*    */   @Nonnull
/*    */   private final List<PrefabRotation> possibleRotations;
/*    */   @Nonnull
/*    */   private final PrefabRotation rotation;
/*    */   @Nonnull
/*    */   private final Pattern pattern;
/*    */   
/*    */   public StaticDirectionality(@Nonnull PrefabRotation rotation, @Nonnull Pattern pattern) {
/* 22 */     this.rotation = rotation;
/* 23 */     this.pattern = pattern;
/* 24 */     this.possibleRotations = Collections.unmodifiableList(List.of(rotation));
/*    */   }
/*    */ 
/*    */   
/*    */   public PrefabRotation getRotationAt(@Nonnull Pattern.Context context) {
/* 29 */     return this.rotation;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Pattern getGeneralPattern() {
/* 35 */     return this.pattern;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Vector3i getReadRangeWith(@Nonnull Scanner scanner) {
/* 41 */     return scanner.readSpaceWith(this.pattern).getRange();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public List<PrefabRotation> getPossibleRotations() {
/* 47 */     return this.possibleRotations;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\directionality\StaticDirectionality.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */