/*    */ package com.hypixel.hytale.builtin.adventure.objectives.historydata;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public final class ObjectiveHistoryData extends CommonObjectiveHistoryData {
/*    */   public static final BuilderCodec<ObjectiveHistoryData> CODEC;
/*    */   
/*    */   static {
/* 19 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ObjectiveHistoryData.class, ObjectiveHistoryData::new, BASE_CODEC).append(new KeyedCodec("Rewards", (Codec)new ArrayCodec((Codec)ObjectiveRewardHistoryData.CODEC, x$0 -> new ObjectiveRewardHistoryData[x$0])), (objectiveDetails, objectiveRewardHistoryData) -> objectiveDetails.rewards = objectiveRewardHistoryData, objectiveDetails -> objectiveDetails.rewards).add()).build();
/*    */   } @Nonnull
/* 21 */   protected Map<UUID, List<ObjectiveRewardHistoryData>> rewardsPerPlayer = new ConcurrentHashMap<>();
/*    */   
/*    */   protected ObjectiveRewardHistoryData[] rewards;
/*    */   
/*    */   public ObjectiveHistoryData(String id, String category) {
/* 26 */     super(id, category);
/*    */   }
/*    */   
/*    */   public ObjectiveHistoryData(String id, String category, ObjectiveRewardHistoryData[] rewards) {
/* 30 */     super(id, category);
/* 31 */     this.rewards = rewards;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectiveRewardHistoryData[] getRewards() {
/* 38 */     return this.rewards;
/*    */   }
/*    */   
/*    */   public void addRewardForPlayerUUID(UUID playerUUID, ObjectiveRewardHistoryData objectiveRewardHistoryData) {
/* 42 */     ((List<ObjectiveRewardHistoryData>)this.rewardsPerPlayer.computeIfAbsent(playerUUID, k -> new ObjectArrayList())).add(objectiveRewardHistoryData);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public ObjectiveHistoryData cloneForPlayer(UUID playerUUID) {
/* 47 */     List<ObjectiveRewardHistoryData> playerRewards = this.rewardsPerPlayer.get(playerUUID);
/* 48 */     if (playerRewards == null) return new ObjectiveHistoryData(this.id, this.category); 
/* 49 */     return new ObjectiveHistoryData(this.id, this.category, (ObjectiveRewardHistoryData[])playerRewards.toArray(x$0 -> new ObjectiveRewardHistoryData[x$0]));
/*    */   }
/*    */   
/*    */   public void completed(UUID playerUUID, @Nonnull ObjectiveHistoryData objectiveHistoryData) {
/* 53 */     completed();
/*    */ 
/*    */     
/* 56 */     List<ObjectiveRewardHistoryData> lastRewards = objectiveHistoryData.rewardsPerPlayer.get(playerUUID);
/* 57 */     if (lastRewards == null)
/* 58 */       return;  this.rewards = (ObjectiveRewardHistoryData[])lastRewards.toArray(x$0 -> new ObjectiveRewardHistoryData[x$0]);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 64 */     return "ObjectiveHistoryData{rewardsPerPlayer=" + String.valueOf(this.rewardsPerPlayer) + ", rewards=" + 
/*    */       
/* 66 */       Arrays.toString((Object[])this.rewards) + "} " + super
/* 67 */       .toString();
/*    */   }
/*    */   
/*    */   protected ObjectiveHistoryData() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\historydata\ObjectiveHistoryData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */