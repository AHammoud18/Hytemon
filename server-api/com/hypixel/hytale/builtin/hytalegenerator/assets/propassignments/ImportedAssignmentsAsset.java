/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.LoggerUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImportedAssignmentsAsset
/*    */   extends AssignmentsAsset
/*    */ {
/*    */   public static final BuilderCodec<ImportedAssignmentsAsset> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ImportedAssignmentsAsset.class, ImportedAssignmentsAsset::new, AssignmentsAsset.ABSTRACT_CODEC).append(new KeyedCodec("Name", (Codec)Codec.STRING, true), (asset, v) -> asset.name = v, asset -> asset.name).add()).build();
/*    */   }
/* 21 */   private String name = "";
/*    */ 
/*    */   
/*    */   public Assignments build(@Nonnull AssignmentsAsset.Argument argument) {
/* 25 */     if (skip()) {
/* 26 */       return Assignments.noPropDistribution(argument.runtime);
/*    */     }
/*    */     
/* 29 */     AssignmentsAsset asset = getExportedAsset(this.name);
/* 30 */     if (asset == null) {
/* 31 */       LoggerUtil.getLogger().warning("Couldn't find Assignments asset exported with name: '" + this.name + "'.");
/* 32 */       return Assignments.noPropDistribution(argument.runtime);
/*    */     } 
/* 34 */     return asset.build(argument);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propassignments\ImportedAssignmentsAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */