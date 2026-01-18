/*    */ package com.hypixel.hytale.builtin.adventure.objectives.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.ObjectivePlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.components.ObjectiveHistoryComponent;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveHistoryData;
/*    */ import com.hypixel.hytale.builtin.adventure.objectives.historydata.ObjectiveLineHistoryData;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObjectiveHistoryCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public ObjectiveHistoryCommand() {
/* 26 */     super("history", "server.commands.objective.history");
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
/* 37 */     StringBuilder sb = new StringBuilder("Completed objectives\n");
/*    */     
/* 39 */     ObjectiveHistoryComponent objectiveHistoryComponent = (ObjectiveHistoryComponent)store.getComponent(ref, ObjectivePlugin.get().getObjectiveHistoryComponentType());
/* 40 */     assert objectiveHistoryComponent != null;
/*    */     
/* 42 */     Map<String, ObjectiveHistoryData> objectiveDataMap = objectiveHistoryComponent.getObjectiveHistoryMap();
/* 43 */     for (ObjectiveHistoryData objectiveHistory : objectiveDataMap.values()) {
/* 44 */       sb.append(objectiveHistory).append("\n");
/*    */     }
/*    */     
/* 47 */     sb.append("\nCompleted objective lines\n");
/*    */     
/* 49 */     Map<String, ObjectiveLineHistoryData> objectiveLineDataMap = objectiveHistoryComponent.getObjectiveLineHistoryMap();
/* 50 */     for (ObjectiveLineHistoryData objectiveLineHistory : objectiveLineDataMap.values()) {
/* 51 */       sb.append(objectiveLineHistory).append("\n");
/*    */     }
/*    */ 
/*    */     
/* 55 */     context.sendMessage(Message.raw(sb.toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\commands\ObjectiveHistoryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */