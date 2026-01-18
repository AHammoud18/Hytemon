/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.directionality;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public abstract class Directionality
/*    */ {
/*    */   @Nullable
/*    */   public abstract PrefabRotation getRotationAt(@Nonnull Pattern.Context paramContext);
/*    */   
/*    */   public abstract Pattern getGeneralPattern();
/*    */   
/*    */   public abstract Vector3i getReadRangeWith(@Nonnull Scanner paramScanner);
/*    */   
/*    */   public abstract List<PrefabRotation> getPossibleRotations();
/*    */   
/*    */   @Nonnull
/*    */   public static Directionality noDirectionality() {
/* 25 */     return new Directionality()
/*    */       {
/*    */         public PrefabRotation getRotationAt(@Nonnull Pattern.Context context) {
/* 28 */           return null;
/*    */         }
/*    */ 
/*    */         
/*    */         @Nonnull
/*    */         public Pattern getGeneralPattern() {
/* 34 */           return Pattern.noPattern();
/*    */         }
/*    */ 
/*    */         
/*    */         @Nonnull
/*    */         public Vector3i getReadRangeWith(@Nonnull Scanner scanner) {
/* 40 */           return new Vector3i();
/*    */         }
/*    */ 
/*    */         
/*    */         @Nonnull
/*    */         public List<PrefabRotation> getPossibleRotations() {
/* 46 */           return Collections.emptyList();
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\directionality\Directionality.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */