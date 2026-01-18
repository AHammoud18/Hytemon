/*    */ package com.hypixel.hytale.builtin.adventure.reputation.choices;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.ReputationPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationGroup;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationRank;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceRequirement;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ public class ReputationRequirement
/*    */   extends ChoiceRequirement
/*    */ {
/*    */   @Nonnull
/*    */   public static final BuilderCodec<ReputationRequirement> CODEC;
/*    */   protected String reputationGroupId;
/*    */   protected String minRequiredRankId;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ReputationRequirement.class, ReputationRequirement::new, ChoiceRequirement.BASE_CODEC).append(new KeyedCodec("ReputationGroupId", (Codec)Codec.STRING), (reputationRequirement, s) -> reputationRequirement.reputationGroupId = s, reputationRequirement -> reputationRequirement.reputationGroupId).addValidator(ReputationGroup.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("MinRequiredRankId", (Codec)Codec.STRING), (reputationRequirement, s) -> reputationRequirement.minRequiredRankId = s, reputationRequirement -> reputationRequirement.minRequiredRankId).addValidator(ReputationRank.VALIDATOR_CACHE.getValidator()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ReputationRequirement(String reputationGroupId, String minRequiredRankId) {
/* 40 */     this.reputationGroupId = reputationGroupId;
/* 41 */     this.minRequiredRankId = minRequiredRankId;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ReputationRequirement() {}
/*    */ 
/*    */   
/*    */   public boolean canFulfillRequirement(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef) {
/* 49 */     ReputationPlugin reputationModule = ReputationPlugin.get();
/*    */     
/* 51 */     int playerReputationValue = reputationModule.getReputationValue(store, ref, this.reputationGroupId);
/* 52 */     if (playerReputationValue == Integer.MIN_VALUE) return false;
/*    */     
/* 54 */     ReputationRank minReputationRank = (ReputationRank)ReputationRank.getAssetMap().getAsset(this.minRequiredRankId);
/* 55 */     if (minReputationRank == null) return false;
/*    */     
/* 57 */     return (playerReputationValue >= minReputationRank.getMinValue());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 63 */     return "ReputationRequirement{reputationGroupId='" + this.reputationGroupId + "', minRequiredRankId='" + this.minRequiredRankId + "'} " + super
/*    */ 
/*    */       
/* 66 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\choices\ReputationRequirement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */