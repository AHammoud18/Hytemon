/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsUserData;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
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
/*    */ public class SelectionHistoryCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 29 */   private final RequiredArg<Boolean> enabledArg = withRequiredArg("enabled", "server.commands.selectionHistory.enabled.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SelectionHistoryCommand() {
/* 35 */     super("selectionHistory", "server.commands.selectionHistory.desc");
/* 36 */     setPermissionGroup(GameMode.Creative);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 41 */     boolean enabled = ((Boolean)this.enabledArg.get(context)).booleanValue();
/*    */     
/* 43 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 44 */     assert playerComponent != null;
/*    */     
/* 46 */     BuilderToolsPlugin.BuilderState builderState = BuilderToolsPlugin.getState(playerComponent, playerRef);
/* 47 */     BuilderToolsUserData userData = builderState.getUserData();
/* 48 */     userData.setRecordSelectionHistory(enabled);
/*    */     
/* 50 */     context.sendMessage(Message.translation("server.commands.selectionHistory.set")
/* 51 */         .param("enabled", enabled));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\SelectionHistoryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */