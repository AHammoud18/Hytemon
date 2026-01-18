/*   */ package com.hypixel.hytale.builtin.adventure.objectives.historydata;
/*   */ 
/*   */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*   */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*   */ 
/*   */ public abstract class ObjectiveRewardHistoryData {
/* 7 */   public static final CodecMapCodec<ObjectiveRewardHistoryData> CODEC = new CodecMapCodec("Type");
/* 8 */   public static final BuilderCodec<ObjectiveRewardHistoryData> BASE_CODEC = BuilderCodec.abstractBuilder(ObjectiveRewardHistoryData.class)
/* 9 */     .build();
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\historydata\ObjectiveRewardHistoryData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */