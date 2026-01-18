/*     */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*     */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class CompleteTaskSetCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/* 109 */   private final RequiredArg<String> objectiveArg = withRequiredArg("objectiveId", "server.commands.objective.complete.taskSet.arg.objectiveId.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompleteTaskSetCommand() {
/* 115 */     super("taskSet", "server.commands.objective.complete.taskSet");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 126 */     String objectiveId = (String)this.objectiveArg.get(context);
/*     */     
/* 128 */     Objective objective = ObjectiveCompleteCommand.getObjectiveFromId(ref, objectiveId, (ComponentAccessor<EntityStore>)store);
/* 129 */     if (objective == null) {
/* 130 */       context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_NOT_FOUND);
/*     */       
/*     */       return;
/*     */     } 
/* 134 */     ObjectiveTask[] tasks = objective.getCurrentTasks();
/* 135 */     if (tasks == null || tasks.length == 0) {
/* 136 */       context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_NO_TASK_FOR_INDEX);
/*     */       
/*     */       return;
/*     */     } 
/* 140 */     for (ObjectiveTask task : tasks) {
/* 141 */       task.complete(objective, (ComponentAccessor)store);
/*     */     }
/*     */     
/* 144 */     objective.checkTaskSetCompletion(store);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveCompleteCommand$CompleteTaskSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */