/*    */ package com.hypixel.hytale.builtin.adventure.objectives.historydata;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class CommonObjectiveHistoryData
/*    */ {
/* 12 */   public static final CodecMapCodec<CommonObjectiveHistoryData> CODEC = new CodecMapCodec("Type");
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<CommonObjectiveHistoryData> BASE_CODEC;
/*    */ 
/*    */ 
/*    */   
/*    */   protected String id;
/*    */ 
/*    */ 
/*    */   
/*    */   protected int timesCompleted;
/*    */ 
/*    */   
/*    */   protected Instant lastCompletionTimestamp;
/*    */ 
/*    */   
/*    */   protected String category;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 35 */     BASE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(CommonObjectiveHistoryData.class).append(new KeyedCodec("Id", (Codec)Codec.STRING), (commonObjectiveHistoryData, s) -> commonObjectiveHistoryData.id = s, commonObjectiveHistoryData -> commonObjectiveHistoryData.id).add()).append(new KeyedCodec("TimesCompleted", (Codec)Codec.INTEGER), (commonObjectiveHistoryData, integer) -> commonObjectiveHistoryData.timesCompleted = integer.intValue(), commonObjectiveHistoryData -> Integer.valueOf(commonObjectiveHistoryData.timesCompleted)).add()).append(new KeyedCodec("LastCompletionTimestamp", (Codec)Codec.LONG), (o, i) -> o.lastCompletionTimestamp = Instant.ofEpochMilli(i.longValue()), o -> (o.lastCompletionTimestamp == null) ? null : Long.valueOf(o.lastCompletionTimestamp.toEpochMilli())).add()).append(new KeyedCodec("Category", (Codec)Codec.STRING), (commonObjectiveHistoryData, s) -> commonObjectiveHistoryData.category = s, commonObjectiveHistoryData -> commonObjectiveHistoryData.category).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommonObjectiveHistoryData(String id, String category) {
/* 43 */     this.id = id;
/* 44 */     this.timesCompleted = 1;
/* 45 */     this.lastCompletionTimestamp = Instant.now();
/* 46 */     this.category = category;
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommonObjectiveHistoryData() {}
/*    */   
/*    */   public String getId() {
/* 53 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getTimesCompleted() {
/* 57 */     return this.timesCompleted;
/*    */   }
/*    */   
/*    */   public Instant getLastCompletionTimestamp() {
/* 61 */     return this.lastCompletionTimestamp;
/*    */   }
/*    */   
/*    */   public String getCategory() {
/* 65 */     return this.category;
/*    */   }
/*    */   
/*    */   protected void completed() {
/* 69 */     this.timesCompleted++;
/* 70 */     this.lastCompletionTimestamp = Instant.now();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 76 */     return "CommonObjectiveHistoryData{id='" + this.id + "', timesCompleted=" + this.timesCompleted + ", lastCompletionTimestamp=" + String.valueOf(this.lastCompletionTimestamp) + ", category='" + this.category + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\historydata\CommonObjectiveHistoryData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */