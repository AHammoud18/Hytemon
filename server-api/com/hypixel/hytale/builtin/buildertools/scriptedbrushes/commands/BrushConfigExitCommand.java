/*    */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.builtin.buildertools.tooloperations.ToolOperation;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
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
/*    */ public class BrushConfigExitCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public BrushConfigExitCommand() {
/* 23 */     super("exit", "Exit a running debug state or stop an incomplete execution");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 28 */     PrototypePlayerBuilderToolSettings prototypeSettings = ToolOperation.getOrCreatePrototypeSettings(playerRef.getUuid());
/* 29 */     prototypeSettings.getBrushConfigCommandExecutor().exitExecution(ref, (ComponentAccessor)store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\commands\BrushConfigExitCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */