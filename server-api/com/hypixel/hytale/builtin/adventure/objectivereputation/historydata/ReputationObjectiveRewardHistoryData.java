/*    */ package com.hypixel.hytale.builtin.adventure.objectivereputation.historydata;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveRewardHistoryData;
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
/*    */ public final class ReputationObjectiveRewardHistoryData
/*    */   extends ObjectiveRewardHistoryData
/*    */ {
/*    */   public static final BuilderCodec<ReputationObjectiveRewardHistoryData> CODEC;
/*    */   protected String reputationGroupId;
/*    */   protected int amount;
/*    */   
/*    */   static {
/* 24 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ReputationObjectiveRewardHistoryData.class, ReputationObjectiveRewardHistoryData::new, ObjectiveRewardHistoryData.BASE_CODEC).append(new KeyedCodec("ReputationGroupId", (Codec)Codec.STRING), (reputationObjectiveRewardDetails, s) -> reputationObjectiveRewardDetails.reputationGroupId = s, reputationObjectiveRewardDetails -> reputationObjectiveRewardDetails.reputationGroupId).addValidator(Validators.nonNull()).addValidator(ReputationGroup.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Amount", (Codec)Codec.INTEGER), (reputationObjectiveRewardHistoryData, integer) -> reputationObjectiveRewardHistoryData.amount = integer.intValue(), reputationObjectiveRewardHistoryData -> Integer.valueOf(reputationObjectiveRewardHistoryData.amount)).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ReputationObjectiveRewardHistoryData(String reputationGroupId, int amount) {
/* 30 */     this.reputationGroupId = reputationGroupId;
/* 31 */     this.amount = amount;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ReputationObjectiveRewardHistoryData() {}
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
/* 48 */     return "ReputationObjectiveRewardHistoryData{reputationGroupId='" + this.reputationGroupId + "', amount=" + this.amount + "} " + super
/*    */ 
/*    */       
/* 51 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectivereputation\historydata\ReputationObjectiveRewardHistoryData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */