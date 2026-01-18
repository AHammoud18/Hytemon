/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.commands;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.BrushConfigCommandExecutor;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.BrushOperationSetting;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.GlobalBrushOperation;
/*    */ import com.hypixel.hytale.builtin.buildertools.scriptedbrushes.operations.system.SequenceBrushOperation;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.UUIDComponent;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BrushConfigListCommand extends AbstractPlayerCommand {
/*    */   public BrushConfigListCommand() {
/* 22 */     super("list", "List the brush config operations that are currently set");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 27 */     UUIDComponent uuidComponent = (UUIDComponent)store.getComponent(ref, UUIDComponent.getComponentType());
/* 28 */     assert uuidComponent != null;
/*    */     
/* 30 */     BrushConfigCommandExecutor brushConfigCommandExecutor = ToolOperation.getOrCreatePrototypeSettings(uuidComponent.getUuid()).getBrushConfigCommandExecutor();
/*    */ 
/*    */     
/* 33 */     Message header = Message.translation("server.commands.brushConfig.list.globalOperation.header");
/* 34 */     ObjectArrayList<Message> objectArrayList2 = new ObjectArrayList();
/* 35 */     for (GlobalBrushOperation operation : brushConfigCommandExecutor.getGlobalOperations().values()) {
/* 36 */       objectArrayList2.add(Message.translation("server.commands.brushConfig.list.globalOperation")
/* 37 */           .param("name", operation.getName()));
/* 38 */       for (Map.Entry<String, BrushOperationSetting<?>> entry : (Iterable<Map.Entry<String, BrushOperationSetting<?>>>)operation.getRegisteredOperationSettings().entrySet()) {
/* 39 */         objectArrayList2.add(Message.translation("server.commands.brushConfig.list.setting")
/* 40 */             .param("name", entry.getKey())
/* 41 */             .param("value", ((BrushOperationSetting)entry.getValue()).getValueString()));
/*    */       }
/*    */     } 
/* 44 */     playerRef.sendMessage(MessageFormat.list(header, (Collection)objectArrayList2));
/*    */ 
/*    */ 
/*    */     
/* 48 */     header = Message.translation("server.commands.brushConfig.list.sequentialOperation.header");
/* 49 */     ObjectArrayList<Message> objectArrayList1 = new ObjectArrayList();
/* 50 */     for (int i = 0; i < brushConfigCommandExecutor.getSequentialOperations().size(); i++) {
/* 51 */       SequenceBrushOperation operation = brushConfigCommandExecutor.getSequentialOperations().get(i);
/* 52 */       objectArrayList1.add(Message.translation("server.commands.brushConfig.list.sequentialOperation")
/* 53 */           .param("index", i)
/* 54 */           .param("name", operation.getName()));
/* 55 */       for (Map.Entry<String, BrushOperationSetting<?>> entry : (Iterable<Map.Entry<String, BrushOperationSetting<?>>>)operation.getRegisteredOperationSettings().entrySet()) {
/* 56 */         objectArrayList1.add(Message.translation("server.commands.brushConfig.list.setting")
/* 57 */             .param("name", entry.getKey())
/* 58 */             .param("value", ((BrushOperationSetting)entry.getValue()).getValueString()));
/*    */       }
/*    */     } 
/* 61 */     playerRef.sendMessage(MessageFormat.list(header, (Collection)objectArrayList1));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\commands\BrushConfigListCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */