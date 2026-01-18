/*    */ package com.hypixel.hytale.builtin.hytalegenerator.props.prefab;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.patterns.Pattern;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.scanners.Scanner;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class PrefabMoldingConfiguration
/*    */ {
/*    */   public final Scanner moldingScanner;
/*    */   public final Pattern moldingPattern;
/*    */   public final MoldingDirection moldingDirection;
/*    */   public final boolean moldChildren;
/*    */   
/*    */   public PrefabMoldingConfiguration(Scanner moldingScanner, Pattern moldingPattern, MoldingDirection moldingDirection, boolean moldChildren) {
/* 15 */     this.moldingScanner = moldingScanner;
/* 16 */     this.moldingPattern = moldingPattern;
/* 17 */     this.moldingDirection = moldingDirection;
/* 18 */     this.moldChildren = moldChildren;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static PrefabMoldingConfiguration none() {
/* 23 */     return new PrefabMoldingConfiguration(null, null, MoldingDirection.NONE, false);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\props\prefab\PrefabMoldingConfiguration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */