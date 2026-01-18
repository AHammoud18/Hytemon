/*    */ package com.hypixel.hytale.builtin.hytalegenerator.assets.propassignments;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.NoPropAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.assets.props.PropAsset;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.Assignments;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.propdistributions.ConstantAssignments;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.props.Prop;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConstantAssignmentsAsset
/*    */   extends AssignmentsAsset
/*    */ {
/*    */   public static final BuilderCodec<ConstantAssignmentsAsset> CODEC;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ConstantAssignmentsAsset.class, ConstantAssignmentsAsset::new, AssignmentsAsset.ABSTRACT_CODEC).append(new KeyedCodec("Prop", (Codec)PropAsset.CODEC, true), (asset, v) -> asset.propAsset = v, asset -> asset.propAsset).add()).build();
/*    */   }
/* 22 */   private PropAsset propAsset = (PropAsset)new NoPropAsset();
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Assignments build(@Nonnull AssignmentsAsset.Argument argument) {
/* 27 */     if (skip()) {
/* 28 */       return Assignments.noPropDistribution(argument.runtime);
/*    */     }
/*    */     
/* 31 */     Prop prop = this.propAsset.build(new PropAsset.Argument(argument.parentSeed, argument.materialCache, argument.referenceBundle, argument.workerIndexer));
/* 32 */     return (Assignments)new ConstantAssignments(prop, argument.runtime);
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanUp() {
/* 37 */     this.propAsset.cleanUp();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\assets\propassignments\ConstantAssignmentsAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */