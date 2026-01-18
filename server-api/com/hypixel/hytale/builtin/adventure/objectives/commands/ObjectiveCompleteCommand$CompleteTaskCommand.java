/*    */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.Objective;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.task.ObjectiveTask;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompleteTaskCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 53 */   private final RequiredArg<String> objectiveArg = withRequiredArg("objectiveId", "server.commands.objective.complete.task.arg.objectiveId.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 59 */   private final RequiredArg<Integer> taskIndexArg = withRequiredArg("taskIndex", "server.commands.objective.complete.task.arg.taskIndex.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CompleteTaskCommand() {
/* 65 */     super("task", "server.commands.objective.complete.task");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 76 */     String objectiveId = (String)this.objectiveArg.get(context);
/* 77 */     int taskIndex = ((Integer)this.taskIndexArg.get(context)).intValue();
/*    */     
/* 79 */     Objective objective = ObjectiveCompleteCommand.getObjectiveFromId(ref, objectiveId, (ComponentAccessor<EntityStore>)store);
/* 80 */     if (objective == null) {
/* 81 */       context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_OBJECTIVE_NOT_FOUND);
/*    */       
/*    */       return;
/*    */     } 
/* 85 */     ObjectiveTask[] tasks = objective.getCurrentTasks();
/* 86 */     if (taskIndex >= tasks.length) {
/* 87 */       context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_NO_TASK_FOR_INDEX);
/*    */       
/*    */       return;
/*    */     } 
/* 91 */     if (tasks[taskIndex].isComplete()) {
/* 92 */       context.sendMessage(ObjectiveCompleteCommand.MESSAGE_COMMANDS_OBJECTIVE_TASK_ALREADY_COMPLETED);
/*    */       
/*    */       return;
/*    */     } 
/* 96 */     tasks[taskIndex].complete(objective, (ComponentAccessor)store);
/* 97 */     objective.checkTaskSetCompletion(store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveCompleteCommand$CompleteTaskCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */