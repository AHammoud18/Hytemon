/*    */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskSet
/*    */ {
/*    */   public static final BuilderCodec<TaskSet> CODEC;
/*    */   public static final String TASKSET_DESCRIPTION_KEY = "server.objectives.{0}.taskSet.{1}";
/*    */   protected String descriptionId;
/*    */   protected ObjectiveTaskAsset[] tasks;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TaskSet.class, TaskSet::new).append(new KeyedCodec("DescriptionId", (Codec)Codec.STRING), (taskSet, s) -> taskSet.descriptionId = s, taskSet -> taskSet.descriptionId).add()).append(new KeyedCodec("Tasks", (Codec)new ArrayCodec((Codec)ObjectiveTaskAsset.CODEC, x$0 -> new ObjectiveTaskAsset[x$0])), (taskSet, objectiveTaskAssets) -> taskSet.tasks = objectiveTaskAssets, taskSet -> taskSet.tasks).addValidator(Validators.nonEmptyArray()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TaskSet(String descriptionId, ObjectiveTaskAsset[] tasks) {
/* 33 */     this.descriptionId = descriptionId;
/* 34 */     this.tasks = tasks;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TaskSet() {}
/*    */   
/*    */   public String getDescriptionId() {
/* 41 */     return this.descriptionId;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public String getDescriptionKey(String objectiveId, int taskSetIndex) {
/* 46 */     if (this.descriptionId != null) return this.descriptionId; 
/* 47 */     return MessageFormat.format("server.objectives.{0}.taskSet.{1}", new Object[] { objectiveId, Integer.valueOf(taskSetIndex) });
/*    */   }
/*    */   
/*    */   public ObjectiveTaskAsset[] getTasks() {
/* 51 */     return this.tasks;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 57 */     return "TaskSet{descriptionId='" + this.descriptionId + "', tasks=" + 
/*    */       
/* 59 */       Arrays.toString((Object[])this.tasks) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\TaskSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */