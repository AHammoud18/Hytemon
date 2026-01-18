/*    */ package com.hypixel.hytale.builtin.adventure.objectivereputation.assets;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ObjectiveCompletionAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationGroup;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReputationCompletionAsset
/*    */   extends ObjectiveCompletionAsset
/*    */ {
/*    */   public static final BuilderCodec<ReputationCompletionAsset> CODEC;
/*    */   protected String reputationGroupId;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ReputationCompletionAsset.class, ReputationCompletionAsset::new, ObjectiveCompletionAsset.BASE_CODEC).append(new KeyedCodec("ReputationGroupId", (Codec)Codec.STRING), (reputationCompletionAsset, s) -> reputationCompletionAsset.reputationGroupId = s, reputationCompletionAsset -> reputationCompletionAsset.reputationGroupId).addValidator(Validators.nonNull()).addValidator(ReputationGroup.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Amount", (Codec)Codec.INTEGER), (reputationCompletionAsset, integer) -> reputationCompletionAsset.amount = integer.intValue(), reputationCompletionAsset -> Integer.valueOf(reputationCompletionAsset.amount)).addValidator(Validators.notEqual(Integer.valueOf(0))).add()).build();
/*    */   }
/*    */   
/* 27 */   protected int amount = 1;
/*    */   
/*    */   public ReputationCompletionAsset(String reputationGroupId, int amount) {
/* 30 */     this.reputationGroupId = reputationGroupId;
/* 31 */     this.amount = amount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getReputationGroupId() {
/* 38 */     return this.reputationGroupId;
/*    */   }
/*    */   
/*    */   public int getAmount() {
/* 42 */     return this.amount;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 48 */     return "ReputationCompletionAsset{reputationGroupId='" + this.reputationGroupId + "', amount=" + this.amount + "} " + super
/*    */ 
/*    */       
/* 51 */       .toString();
/*    */   }
/*    */   
/*    */   protected ReputationCompletionAsset() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectivereputation\assets\ReputationCompletionAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */