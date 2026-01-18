/*     */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectiveDataStore;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ObjectiveCompleteCommand extends AbstractCommandCollection {
/*     */   @Nonnull
/*  29 */   private static final Message MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_NOT_FOUND = Message.translation("server.commands.objective.objectiveNotFound");
/*     */   @Nonnull
/*  31 */   private static final Message MESSAGE_COMMANDS_OBJECTIVE_NO_TASK_FOR_INDEX = Message.translation("server.commands.objective.noTaskForIndex");
/*     */   @Nonnull
/*  33 */   private static final Message MESSAGE_COMMANDS_OBJECTIVE_TASK_ALREADY_COMPLETED = Message.translation("server.commands.objective.taskAlreadyCompleted");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectiveCompleteCommand() {
/*  39 */     super("complete", "server.commands.objective.complete");
/*  40 */     addSubCommand((AbstractCommand)new CompleteTaskCommand());
/*  41 */     addSubCommand((AbstractCommand)new CompleteTaskSetCommand());
/*  42 */     addSubCommand((AbstractCommand)new CompleteObjectiveCommand());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CompleteTaskCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  53 */     private final RequiredArg<String> objectiveArg = withRequiredArg("objectiveId", "server.commands.objective.complete.task.arg.objectiveId.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  59 */     private final RequiredArg<Integer> taskIndexArg = withRequiredArg("taskIndex", "server.commands.objective.complete.task.arg.taskIndex.desc", (ArgumentType)ArgTypes.INTEGER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CompleteTaskCommand() {
/*  65 */       super("task", "server.commands.objective.complete.task");
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
/*  76 */       String objectiveId = (String)this.objectiveArg.get(context);
/*  77 */       int taskIndex = ((Integer)this.taskIndexArg.get(context)).intValue();
/*     */       
/*  79 */       Objective objective = ObjectiveCompleteCommand.getObjectiveFromId(ref, objectiveId, (ComponentAccessor<EntityStore>)store);
/*  80 */       if (objective == null) {
/*  81 */         context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_NOT_FOUND);
/*     */         
/*     */         return;
/*     */       } 
/*  85 */       ObjectiveTask[] tasks = objective.getCurrentTasks();
/*  86 */       if (taskIndex >= tasks.length) {
/*  87 */         context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_NO_TASK_FOR_INDEX);
/*     */         
/*     */         return;
/*     */       } 
/*  91 */       if (tasks[taskIndex].isComplete()) {
/*  92 */         context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_TASK_ALREADY_COMPLETED);
/*     */         
/*     */         return;
/*     */       } 
/*  96 */       tasks[taskIndex].complete(objective, (ComponentAccessor)store);
/*  97 */       objective.checkTaskSetCompletion(store);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CompleteTaskSetCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 109 */     private final RequiredArg<String> objectiveArg = withRequiredArg("objectiveId", "server.commands.objective.complete.taskSet.arg.objectiveId.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CompleteTaskSetCommand() {
/* 115 */       super("taskSet", "server.commands.objective.complete.taskSet");
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
/* 126 */       String objectiveId = (String)this.objectiveArg.get(context);
/*     */       
/* 128 */       Objective objective = ObjectiveCompleteCommand.getObjectiveFromId(ref, objectiveId, (ComponentAccessor<EntityStore>)store);
/* 129 */       if (objective == null) {
/* 130 */         context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_NOT_FOUND);
/*     */         
/*     */         return;
/*     */       } 
/* 134 */       ObjectiveTask[] tasks = objective.getCurrentTasks();
/* 135 */       if (tasks == null || tasks.length == 0) {
/* 136 */         context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_NO_TASK_FOR_INDEX);
/*     */         
/*     */         return;
/*     */       } 
/* 140 */       for (ObjectiveTask task : tasks) {
/* 141 */         task.complete(objective, (ComponentAccessor)store);
/*     */       }
/*     */       
/* 144 */       objective.checkTaskSetCompletion(store);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CompleteObjectiveCommand
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 156 */     private final RequiredArg<String> objectiveArg = withRequiredArg("objectiveId", "server.commands.objective.complete.objective.arg.objectiveId.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CompleteObjectiveCommand() {
/* 162 */       super("objective", "server.commands.objective.complete.objective");
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
/* 173 */       String objectiveId = (String)this.objectiveArg.get(context);
/*     */       
/* 175 */       Objective objective = ObjectiveCompleteCommand.getObjectiveFromId(ref, objectiveId, (ComponentAccessor<EntityStore>)store);
/* 176 */       if (objective == null) {
/* 177 */         context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_NOT_FOUND);
/*     */         
/*     */         return;
/*     */       } 
/* 181 */       ObjectiveTask[] tasks = objective.getCurrentTasks();
/* 182 */       if (tasks == null) {
/* 183 */         context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_NO_TASK_FOR_INDEX);
/*     */         
/*     */         return;
/*     */       } 
/* 187 */       for (ObjectiveTask task : tasks) {
/* 188 */         task.completeTransactionRecords();
/*     */       }
/*     */       
/* 191 */       objective.complete(store);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Objective getObjectiveFromId(@Nonnull Ref<EntityStore> participantRef, @Nonnull String objectiveId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 197 */     Player playerComponent = (Player)componentAccessor.getComponent(participantRef, Player.getComponentType());
/* 198 */     if (playerComponent == null) {
/* 199 */       return null;
/*     */     }
/*     */     
/* 202 */     Set<UUID> activeObjectiveUUIDs = playerComponent.getPlayerConfigData().getActiveObjectiveUUIDs();
/*     */     
/* 204 */     ObjectiveDataStore objectiveDataStore = ObjectivePlugin.get().getObjectiveDataStore();
/* 205 */     for (UUID objectiveUUID : activeObjectiveUUIDs) {
/* 206 */       Objective objective = objectiveDataStore.getObjective(objectiveUUID);
/* 207 */       if (objective == null)
/*     */         continue; 
/* 209 */       if (objective.getObjectiveId().equals(objectiveId)) {
/* 210 */         return objective;
/*     */       }
/*     */     } 
/*     */     
/* 214 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveCompleteCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */