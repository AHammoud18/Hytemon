/*    */ package com.hypixel.hytale.builtin.adventure.objectives.historydata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public final class ObjectiveLineHistoryData extends CommonObjectiveHistoryData {
/*    */   public static final BuilderCodec<ObjectiveLineHistoryData> CODEC;
/*    */   private ObjectiveHistoryData[] objectiveHistoryDataArray;
/*    */   private String[] nextObjectiveLineIds;
/*    */   
/*    */   static {
/* 22 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ObjectiveLineHistoryData.class, ObjectiveLineHistoryData::new, BASE_CODEC).append(new KeyedCodec("Objectives", (Codec)new ArrayCodec((Codec)ObjectiveHistoryData.CODEC, x$0 -> new ObjectiveHistoryData[x$0])), (objectiveLineDetails, objectiveDetails) -> objectiveLineDetails.objectiveHistoryDataArray = objectiveDetails, objectiveLineDetails -> objectiveLineDetails.objectiveHistoryDataArray).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectiveLineHistoryData(String id, String category, String[] nextObjectiveLineIds) {
/* 28 */     super(id, category);
/* 29 */     this.nextObjectiveLineIds = nextObjectiveLineIds;
/*    */   }
/*    */ 
/*    */   
/*    */   private ObjectiveLineHistoryData() {}
/*    */   
/*    */   public ObjectiveHistoryData[] getObjectiveHistoryDataArray() {
/* 36 */     return this.objectiveHistoryDataArray;
/*    */   }
/*    */   
/*    */   public String[] getNextObjectiveLineIds() {
/* 40 */     return this.nextObjectiveLineIds;
/*    */   }
/*    */   
/*    */   public void addObjectiveHistoryData(@Nonnull ObjectiveHistoryData objectiveHistoryData) {
/* 44 */     this.objectiveHistoryDataArray = (ObjectiveHistoryData[])ArrayUtil.append((Object[])this.objectiveHistoryDataArray, objectiveHistoryData);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Map<UUID, ObjectiveLineHistoryData> cloneForPlayers(@Nonnull Set<UUID> playerUUIDs) {
/* 49 */     Object2ObjectOpenHashMap<UUID, ObjectiveLineHistoryData> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*    */     
/* 51 */     for (ObjectiveHistoryData objectiveHistoryData : this.objectiveHistoryDataArray) {
/* 52 */       for (UUID playerUUID : playerUUIDs) {
/* 53 */         ((ObjectiveLineHistoryData)object2ObjectOpenHashMap.computeIfAbsent(playerUUID, k -> new ObjectiveLineHistoryData())).addObjectiveHistoryData(objectiveHistoryData.cloneForPlayer(playerUUID));
/*    */       }
/*    */     } 
/*    */     
/* 57 */     return (Map<UUID, ObjectiveLineHistoryData>)object2ObjectOpenHashMap;
/*    */   }
/*    */   
/*    */   public void completed(UUID playerUUID, @Nonnull ObjectiveLineHistoryData objectiveLineHistoryData) {
/* 61 */     completed();
/*    */     
/* 63 */     for (ObjectiveHistoryData latestObjectiveHistoryData : objectiveLineHistoryData.objectiveHistoryDataArray) {
/* 64 */       boolean updated = false; ObjectiveHistoryData[] arrayOfObjectiveHistoryData; int i; byte b;
/* 65 */       for (arrayOfObjectiveHistoryData = this.objectiveHistoryDataArray, i = arrayOfObjectiveHistoryData.length, b = 0; b < i; ) { ObjectiveHistoryData savedObjectiveHistoryData = arrayOfObjectiveHistoryData[b];
/* 66 */         if (!savedObjectiveHistoryData.id.equals(latestObjectiveHistoryData.id)) { b++; continue; }
/* 67 */          savedObjectiveHistoryData.completed(playerUUID, latestObjectiveHistoryData);
/* 68 */         updated = true; }
/*    */ 
/*    */ 
/*    */       
/* 72 */       if (!updated)
/*    */       {
/* 74 */         addObjectiveHistoryData(latestObjectiveHistoryData);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 82 */     return "ObjectiveLineHistoryData{objectiveHistoryDataArray=" + Arrays.toString((Object[])this.objectiveHistoryDataArray) + ", nextObjectiveLineIds=" + 
/* 83 */       Arrays.toString((Object[])this.nextObjectiveLineIds) + "} " + super
/* 84 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\historydata\ObjectiveLineHistoryData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */