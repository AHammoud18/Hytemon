/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config.task;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.taskcondition.TaskConditionAsset;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UseEntityObjectiveTaskAsset
/*     */   extends CountObjectiveTaskAsset
/*     */ {
/*     */   public static final BuilderCodec<UseEntityObjectiveTaskAsset> CODEC;
/*     */   protected String taskId;
/*     */   protected String animationIdToPlay;
/*     */   protected DialogOptions dialogOptions;
/*     */   
/*     */   static {
/*  31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(UseEntityObjectiveTaskAsset.class, UseEntityObjectiveTaskAsset::new, CountObjectiveTaskAsset.CODEC).append(new KeyedCodec("TaskId", (Codec)Codec.STRING), (useEntityObjectiveTaskAsset, s) -> useEntityObjectiveTaskAsset.taskId = s, useEntityObjectiveTaskAsset -> useEntityObjectiveTaskAsset.taskId).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("AnimationIdToPlay", (Codec)Codec.STRING), (useEntityObjectiveTaskAsset, s) -> useEntityObjectiveTaskAsset.animationIdToPlay = s, useEntityObjectiveTaskAsset -> useEntityObjectiveTaskAsset.animationIdToPlay).add()).append(new KeyedCodec("Dialog", (Codec)DialogOptions.CODEC), (useEntityObjectiveTask, dialogOptions) -> useEntityObjectiveTask.dialogOptions = dialogOptions, useEntityObjectiveTask -> useEntityObjectiveTask.dialogOptions).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UseEntityObjectiveTaskAsset(String descriptionId, TaskConditionAsset[] taskConditions, Vector3i[] mapMarkers, int count, String taskId, String animationIdToPlay, DialogOptions dialogOptions) {
/*  38 */     super(descriptionId, taskConditions, mapMarkers, count);
/*  39 */     this.taskId = taskId;
/*  40 */     this.animationIdToPlay = animationIdToPlay;
/*  41 */     this.dialogOptions = dialogOptions;
/*     */   }
/*     */ 
/*     */   
/*     */   protected UseEntityObjectiveTaskAsset() {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ObjectiveTaskAsset.TaskScope getTaskScope() {
/*  50 */     return ObjectiveTaskAsset.TaskScope.PLAYER_AND_MARKER;
/*     */   }
/*     */   
/*     */   public String getTaskId() {
/*  54 */     return this.taskId;
/*     */   }
/*     */   
/*     */   public String getAnimationIdToPlay() {
/*  58 */     return this.animationIdToPlay;
/*     */   }
/*     */   
/*     */   public DialogOptions getDialogOptions() {
/*  62 */     return this.dialogOptions;
/*     */   }
/*     */   
/*     */   protected boolean matchesAsset0(ObjectiveTaskAsset task) {
/*     */     UseEntityObjectiveTaskAsset asset;
/*  67 */     if (!super.matchesAsset0(task)) return false; 
/*  68 */     if (task instanceof UseEntityObjectiveTaskAsset) { asset = (UseEntityObjectiveTaskAsset)task; } else { return false; }
/*     */     
/*  70 */     if (!Objects.equals(asset.animationIdToPlay, this.animationIdToPlay)) return false; 
/*  71 */     if (!Objects.equals(asset.dialogOptions, this.dialogOptions)) return false; 
/*  72 */     return asset.taskId.equals(this.taskId);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  78 */     return "UseEntityObjectiveTaskAsset{taskId='" + this.taskId + "', animationIdToPlay='" + this.animationIdToPlay + "', dialogOptions=" + String.valueOf(this.dialogOptions) + "} " + super
/*     */ 
/*     */ 
/*     */       
/*  82 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DialogOptions
/*     */   {
/*     */     @Nonnull
/*     */     public static BuilderCodec<DialogOptions> CODEC;
/*     */     
/*     */     protected String entityNameKey;
/*     */     
/*     */     protected String dialogKey;
/*     */ 
/*     */     
/*     */     static {
/*  98 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DialogOptions.class, DialogOptions::new).append(new KeyedCodec("EntityNameKey", (Codec)Codec.STRING), (dialogOptions, s) -> dialogOptions.entityNameKey = s, dialogOptions -> dialogOptions.entityNameKey).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("DialogKey", (Codec)Codec.STRING), (dialogOptions, s) -> dialogOptions.dialogKey = s, dialogOptions -> dialogOptions.dialogKey).addValidator(Validators.nonNull()).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public DialogOptions(String entityNameKey, String dialogKey) {
/* 104 */       this.entityNameKey = entityNameKey;
/* 105 */       this.dialogKey = dialogKey;
/*     */     }
/*     */ 
/*     */     
/*     */     protected DialogOptions() {}
/*     */     
/*     */     public String getEntityNameKey() {
/* 112 */       return this.entityNameKey;
/*     */     }
/*     */     
/*     */     public String getDialogKey() {
/* 116 */       return this.dialogKey;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 121 */       if (this == o) return true; 
/* 122 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 124 */       DialogOptions that = (DialogOptions)o;
/*     */       
/* 126 */       if (!this.entityNameKey.equals(that.entityNameKey)) return false; 
/* 127 */       return this.dialogKey.equals(that.dialogKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 132 */       int result = this.entityNameKey.hashCode();
/* 133 */       result = 31 * result + this.dialogKey.hashCode();
/* 134 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 140 */       return "DialogOptions{entityNameKey='" + this.entityNameKey + "', dialogKey='" + this.dialogKey + "'}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\task\UseEntityObjectiveTaskAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */