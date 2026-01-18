/*    */ package com.hypixel.hytale.builtin.adventure.objectivereputation;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.builtin.adventure.objectivereputation.assets.ReputationCompletionAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectivereputation.historydata.ReputationObjectiveRewardHistoryData;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveRewardHistoryData;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationGroup;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ObjectiveReputationPlugin
/*    */   extends JavaPlugin {
/*    */   public static ObjectiveReputationPlugin get() {
/* 19 */     return instance;
/*    */   }
/*    */   protected static ObjectiveReputationPlugin instance;
/*    */   public ObjectiveReputationPlugin(@Nonnull JavaPluginInit init) {
/* 23 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 28 */     instance = this;
/*    */     
/* 30 */     ObjectiveRewardHistoryData.CODEC.register("Reputation", ReputationObjectiveRewardHistoryData.class, (Codec)ReputationObjectiveRewardHistoryData.CODEC);
/*    */     
/* 32 */     ObjectivePlugin.get().registerCompletion("Reputation", ReputationCompletionAsset.class, (Codec)ReputationCompletionAsset.CODEC, ReputationCompletion::new);
/* 33 */     AssetRegistry.getAssetStore(ObjectiveAsset.class).injectLoadsAfter(ReputationGroup.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectivereputation\ObjectiveReputationPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */