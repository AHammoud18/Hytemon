/*     */ package com.hypixel.hytale.builtin.adventure.objectives.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.completion.ObjectiveCompletionAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.ObjectiveTaskAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.task.TaskSet;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectiveAsset
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ObjectiveAsset>>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ObjectiveAsset> CODEC;
/*     */   
/*     */   static {
/*  82 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ObjectiveAsset.class, ObjectiveAsset::new, (Codec)Codec.STRING, (objective, s) -> objective.id = s, objective -> objective.id, (objective, data) -> objective.extraData = data, objective -> objective.extraData).appendInherited(new KeyedCodec("Category", (Codec)Codec.STRING), (objectiveAsset, s) -> objectiveAsset.category = s, objectiveAsset -> objectiveAsset.category, (objectiveAsset, parent) -> objectiveAsset.category = parent.category).add()).appendInherited(new KeyedCodec("TaskSets", (Codec)new ArrayCodec((Codec)TaskSet.CODEC, x$0 -> new TaskSet[x$0])), (objective, tasks) -> objective.taskSets = tasks, objective -> objective.taskSets, (objective, parent) -> objective.taskSets = parent.taskSets).addValidator(Validators.nonEmptyArray()).add()).appendInherited(new KeyedCodec("Completions", (Codec)new ArrayCodec((Codec)ObjectiveCompletionAsset.CODEC, x$0 -> new ObjectiveCompletionAsset[x$0])), (objective, rewards) -> objective.completionHandlers = rewards, objective -> objective.completionHandlers, (objective, parent) -> objective.completionHandlers = parent.completionHandlers).add()).appendInherited(new KeyedCodec("TitleId", (Codec)Codec.STRING), (objectiveAsset, s) -> objectiveAsset.objectiveTitleKey = s, objectiveAsset -> objectiveAsset.objectiveTitleKey, (objectiveAsset, parent) -> objectiveAsset.objectiveTitleKey = parent.objectiveTitleKey).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("server.objectives.{assetId}.title", true))).add()).appendInherited(new KeyedCodec("DescriptionId", (Codec)Codec.STRING), (objectiveAsset, s) -> objectiveAsset.objectiveDescriptionKey = s, objectiveAsset -> objectiveAsset.objectiveDescriptionKey, (objectiveAsset, parent) -> objectiveAsset.objectiveDescriptionKey = parent.objectiveDescriptionKey).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.LocalizationKeyField("server.objectives.{assetId}.desc"))).add()).appendInherited(new KeyedCodec("RemoveOnItemDrop", (Codec)Codec.BOOLEAN), (objectiveAsset, aBoolean) -> objectiveAsset.removeOnItemDrop = aBoolean.booleanValue(), objectiveAsset -> Boolean.valueOf(objectiveAsset.removeOnItemDrop), (objectiveAsset, parent) -> objectiveAsset.removeOnItemDrop = parent.removeOnItemDrop).add()).afterDecode(objectiveAsset -> { if (objectiveAsset.objectiveTitleKey == null) objectiveAsset.objectiveTitleKey = MessageFormat.format("server.objectives.{0}.title", new Object[] { objectiveAsset.id });  if (objectiveAsset.objectiveDescriptionKey == null) objectiveAsset.objectiveDescriptionKey = MessageFormat.format("server.objectives.{0}.desc", new Object[] { objectiveAsset.id });  })).build();
/*     */   }
/*  84 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ObjectiveAsset::getAssetStore)); private static AssetStore<String, ObjectiveAsset, DefaultAssetMap<String, ObjectiveAsset>> ASSET_STORE; protected AssetExtraInfo.Data extraData;
/*     */   protected String id;
/*     */   protected String category;
/*     */   
/*     */   public static AssetStore<String, ObjectiveAsset, DefaultAssetMap<String, ObjectiveAsset>> getAssetStore() {
/*  89 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ObjectiveAsset.class); 
/*  90 */     return ASSET_STORE;
/*     */   }
/*     */   protected TaskSet[] taskSets; protected ObjectiveCompletionAsset[] completionHandlers; protected String objectiveTitleKey; protected String objectiveDescriptionKey; protected boolean removeOnItemDrop;
/*     */   public static DefaultAssetMap<String, ObjectiveAsset> getAssetMap() {
/*  94 */     return (DefaultAssetMap<String, ObjectiveAsset>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectiveAsset(String id, String category, TaskSet[] taskSets, ObjectiveCompletionAsset[] completionHandlers, String objectiveTitleKey, String objectiveDescriptionKey, boolean removeOnItemDrop) {
/* 108 */     this.id = id;
/* 109 */     this.category = category;
/* 110 */     this.taskSets = taskSets;
/* 111 */     this.completionHandlers = completionHandlers;
/* 112 */     this.objectiveTitleKey = objectiveTitleKey;
/* 113 */     this.objectiveDescriptionKey = objectiveDescriptionKey;
/* 114 */     this.removeOnItemDrop = removeOnItemDrop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ObjectiveAsset() {}
/*     */ 
/*     */   
/*     */   public String getId() {
/* 122 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 126 */     return this.category;
/*     */   }
/*     */   
/*     */   public String getTitleKey() {
/* 130 */     return this.objectiveTitleKey;
/*     */   }
/*     */   
/*     */   public String getDescriptionKey() {
/* 134 */     return this.objectiveDescriptionKey;
/*     */   }
/*     */   
/*     */   public TaskSet[] getTaskSets() {
/* 138 */     return this.taskSets;
/*     */   }
/*     */   
/*     */   public ObjectiveCompletionAsset[] getCompletionHandlers() {
/* 142 */     return this.completionHandlers;
/*     */   }
/*     */   
/*     */   public String getObjectiveTitleKey() {
/* 146 */     return this.objectiveTitleKey;
/*     */   }
/*     */   
/*     */   public String getObjectiveDescriptionKey() {
/* 150 */     return this.objectiveDescriptionKey;
/*     */   }
/*     */   
/*     */   public boolean isRemoveOnItemDrop() {
/* 154 */     return this.removeOnItemDrop;
/*     */   }
/*     */   
/*     */   public boolean isValidForPlayer() {
/* 158 */     for (TaskSet taskSet : this.taskSets) {
/* 159 */       for (ObjectiveTaskAsset task : taskSet.getTasks()) {
/* 160 */         if (!task.getTaskScope().isTaskPossibleForPlayer()) {
/* 161 */           ObjectivePlugin.get().getLogger().at(Level.WARNING).log("Task %s isn't valid for Player held objective", task.getClass().toString());
/* 162 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 166 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isValidForMarker() {
/* 170 */     for (TaskSet taskSet : this.taskSets) {
/* 171 */       for (ObjectiveTaskAsset task : taskSet.getTasks()) {
/* 172 */         if (!task.getTaskScope().isTaskPossibleForMarker()) {
/* 173 */           ObjectivePlugin.get().getLogger().at(Level.WARNING).log("Task %s isn't valid for Marker held objective", task.getClass().toString());
/* 174 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 184 */     return "ObjectiveAsset{id='" + this.id + "', category='" + this.category + "', taskSets=" + 
/*     */ 
/*     */       
/* 187 */       Arrays.toString((Object[])this.taskSets) + ", completionHandlers=" + 
/* 188 */       Arrays.toString((Object[])this.completionHandlers) + ", objectiveTitleKey='" + this.objectiveTitleKey + "', objectiveDescriptionKey='" + this.objectiveDescriptionKey + "', removeOnItemDrop=" + this.removeOnItemDrop + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\config\ObjectiveAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */