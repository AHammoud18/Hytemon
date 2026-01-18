/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.commands;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfig;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.BrushOperationSetting;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BrushConfigDebugStepCommand extends AbstractPlayerCommand {
/* 25 */   private final DefaultArg<Integer> numStepsArg = (DefaultArg<Integer>)withDefaultArg("steps", "The number of operations to step through", (ArgumentType)ArgTypes.INTEGER, 
/* 26 */       Integer.valueOf(1), "A single step")
/* 27 */     .addValidator(Validators.range(Integer.valueOf(1), Integer.valueOf(100)));
/*    */   
/*    */   public BrushConfigDebugStepCommand() {
/* 30 */     super("step", "Advance one or more steps into your order of operations brush config debug");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 35 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 36 */     assert uuidComponent != null;
/*    */     
/* 38 */     int numSteps = ((Integer)this.numStepsArg.get(context)).intValue();
/*    */     
/* 40 */     PrototypePlayerBuilderToolSettings prototypeSettings = ToolOperation.getOrCreatePrototypeSettings(uuidComponent.getUuid());
/* 41 */     BrushConfig brushConfig = prototypeSettings.getBrushConfig();
/* 42 */     BrushConfigCommandExecutor brushConfigCommandExecutor = prototypeSettings.getBrushConfigCommandExecutor();
/*    */     
/* 44 */     if (!brushConfig.isCurrentlyExecuting()) {
/* 45 */       playerRef.sendMessage(Message.translation("server.commands.brushConfig.debug.notStarted"));
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     int indexAtStart = brushConfigCommandExecutor.getCurrentOperationIndex();
/* 50 */     int indexAtEnd = 0;
/*    */     
/* 52 */     BrushConfig.BCExecutionStatus status = null;
/* 53 */     for (int i = 0; i < numSteps; i++) {
/* 54 */       indexAtEnd = brushConfigCommandExecutor.getCurrentOperationIndex();
/* 55 */       status = brushConfigCommandExecutor.step(ref, true, (ComponentAccessor)store);
/* 56 */       if (!status.equals(BrushConfig.BCExecutionStatus.Continue))
/*    */         break; 
/*    */     } 
/* 59 */     if (status == BrushConfig.BCExecutionStatus.Complete) {
/* 60 */       playerRef.sendMessage(Message.translation("server.commands.brushConfig.debug.finished"));
/*    */     }
/*    */     
/* 63 */     Message header = Message.translation("server.commands.brushConfig.debug.executed");
/* 64 */     ObjectArrayList<Message> objectArrayList = new ObjectArrayList();
/* 65 */     for (int j = indexAtStart; j <= indexAtEnd; j++) {
/* 66 */       SequenceBrushOperation brushOperation = brushConfigCommandExecutor.getSequentialOperations().get(j);
/* 67 */       objectArrayList.add(Message.translation("server.commands.brushConfig.list.sequentialOperation")
/* 68 */           .param("index", j)
/* 69 */           .param("name", brushOperation.getName()));
/* 70 */       for (Map.Entry<String, BrushOperationSetting<?>> entry : (Iterable<Map.Entry<String, BrushOperationSetting<?>>>)brushOperation.getRegisteredOperationSettings().entrySet()) {
/* 71 */         objectArrayList.add(Message.translation("server.commands.brushConfig.list.setting")
/* 72 */             .param("name", entry.getKey())
/* 73 */             .param("value", ((BrushOperationSetting)entry.getValue()).getValueString()));
/*    */       }
/*    */     } 
/* 76 */     playerRef.sendMessage(MessageFormat.list(header, (Collection)objectArrayList));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\commands\BrushConfigDebugStepCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */