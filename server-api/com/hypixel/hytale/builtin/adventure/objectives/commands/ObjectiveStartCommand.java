/*     */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.config.ObjectiveLineAsset;
/*     */ import com.hypixel.hytale.common.util.StringUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.HashSet;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectiveStartCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public ObjectiveStartCommand() {
/*  34 */     super("start", "server.commands.objective.start");
/*  35 */     addSubCommand((AbstractCommand)new StartObjectiveCommand());
/*  36 */     addSubCommand((AbstractCommand)new StartObjectiveLineCommand());
/*     */   }
/*     */ 
/*     */   
/*     */   public static class StartObjectiveCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  44 */     private static final Message MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_NOT_FOUND = Message.translation("server.commands.objective.objectiveNotFound");
/*     */     @Nonnull
/*  46 */     private static final Message MESSAGE_GENERAL_FAILED_DID_YOU_MEAN = Message.translation("server.general.failed.didYouMean");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  52 */     private final RequiredArg<String> objectiveArg = withRequiredArg("objectiveId", "server.commands.objective.start.objective.arg.objectiveId.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StartObjectiveCommand() {
/*  58 */       super("objective", "server.commands.objective.start.objective");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  69 */       String objectiveId = (String)this.objectiveArg.get(context);
/*     */       
/*  71 */       ObjectiveAsset asset = (ObjectiveAsset)ObjectiveAsset.getAssetMap().getAsset(objectiveId);
/*  72 */       if (asset == null) {
/*  73 */         context.sendMessage(MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_NOT_FOUND.param("id", objectiveId));
/*  74 */         context.sendMessage(MESSAGE_GENERAL_FAILED_DID_YOU_MEAN
/*  75 */             .param("choices", StringUtil.sortByFuzzyDistance(objectiveId, ObjectiveAsset.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*     */         
/*     */         return;
/*     */       } 
/*  79 */       HashSet<UUID> playerSet = new HashSet<>();
/*  80 */       playerSet.add(playerRef.getUuid());
/*     */       
/*  82 */       Objective objective = ObjectivePlugin.get().startObjective(objectiveId, playerSet, world.getWorldConfig().getUuid(), null, store);
/*  83 */       if (objective == null)
/*     */         return; 
/*  85 */       objective.checkTaskSetCompletion(store);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class StartObjectiveLineCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  94 */     private static final Message MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_LINE_NOT_FOUND = Message.translation("server.commands.objective.objectiveLineNotFound");
/*     */     @Nonnull
/*  96 */     private static final Message MESSAGE_GENERAL_FAILED_DID_YOU_MEAN = Message.translation("server.general.failed.didYouMean");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 102 */     private final RequiredArg<String> objectiveLineArg = withRequiredArg("objectiveLineId", "server.commands.objective.start.objectiveLine.arg.objectiveLineId.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StartObjectiveLineCommand() {
/* 108 */       super("line", "server.commands.objective.start.objectiveLine");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 119 */       String objectiveLineId = (String)this.objectiveLineArg.get(context);
/*     */ 
/*     */       
/* 122 */       if (ObjectiveLineAsset.getAssetMap().getAsset(objectiveLineId) == null) {
/* 123 */         context.sendMessage(MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_LINE_NOT_FOUND.param("id", objectiveLineId));
/* 124 */         context.sendMessage(MESSAGE_GENERAL_FAILED_DID_YOU_MEAN
/* 125 */             .param("choices", StringUtil.sortByFuzzyDistance(objectiveLineId, ObjectiveLineAsset.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*     */         
/*     */         return;
/*     */       } 
/* 129 */       HashSet<UUID> playerSet = new HashSet<>();
/* 130 */       playerSet.add(playerRef.getUuid());
/*     */       
/* 132 */       ObjectivePlugin.get().startObjectiveLine(store, objectiveLineId, playerSet, world.getWorldConfig().getUuid(), null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveStartCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */