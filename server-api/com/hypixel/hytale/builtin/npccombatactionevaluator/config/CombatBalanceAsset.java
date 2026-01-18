/*    */ package com.hypixel.hytale.builtin.npccombatactionevaluator.config;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.npccombatactionevaluator.evaluator.CombatActionEvaluatorConfig;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.server.npc.config.balancing.BalanceAsset;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CombatBalanceAsset
/*    */   extends BalanceAsset
/*    */ {
/*    */   public static final BuilderCodec<CombatBalanceAsset> CODEC;
/*    */   
/*    */   static {
/* 37 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CombatBalanceAsset.class, CombatBalanceAsset::new, ABSTRACT_CODEC).documentation("A balance asset which also configures a combat action evaluator.")).appendInherited(new KeyedCodec("TargetMemoryDuration", (Codec)Codec.FLOAT), (balanceAsset, f) -> balanceAsset.targetMemoryDuration = f.floatValue(), balanceAsset -> Float.valueOf(balanceAsset.targetMemoryDuration), (balanceAsset, parent) -> balanceAsset.targetMemoryDuration = parent.targetMemoryDuration).documentation("How long the target should remain in the NPCs list of potential targets after last being spotted").addValidator(Validators.greaterThan(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("CombatActionEvaluator", (Codec)CombatActionEvaluatorConfig.CODEC), (balanceAsset, o) -> balanceAsset.evaluatorConfig = o, balanceAsset -> balanceAsset.evaluatorConfig, (balanceAsset, parent) -> balanceAsset.targetMemoryDuration = parent.targetMemoryDuration).addValidator(Validators.nonNull()).documentation("The combat action evaluator complete with combat action definitions and conditions.").add()).build();
/*    */   }
/* 39 */   protected float targetMemoryDuration = 15.0F;
/*    */   protected CombatActionEvaluatorConfig evaluatorConfig;
/*    */   
/*    */   public float getTargetMemoryDuration() {
/* 43 */     return this.targetMemoryDuration;
/*    */   }
/*    */   
/*    */   public CombatActionEvaluatorConfig getEvaluatorConfig() {
/* 47 */     return this.evaluatorConfig;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "CombatBalanceAsset{TargetMemoryDuration='" + this.targetMemoryDuration + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\npccombatactionevaluator\config\CombatBalanceAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */