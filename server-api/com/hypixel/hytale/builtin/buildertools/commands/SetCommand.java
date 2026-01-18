/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.builtin.buildertools.PrototypePlayerBuilderToolSettings;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SetCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 29 */   private final RequiredArg<BlockPattern> patternArg = withRequiredArg("pattern", "server.commands.set.args.blocktype.desc", ArgTypes.BLOCK_PATTERN);
/*    */   
/*    */   public SetCommand() {
/* 32 */     super("setBlocks", "server.commands.set.desc");
/* 33 */     setPermissionGroup(GameMode.Creative);
/* 34 */     requirePermission("hytale.editor.selection.modify");
/* 35 */     addAliases(new String[] { "set" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 40 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 41 */     assert playerComponent != null;
/*    */     
/* 43 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 45 */     BlockPattern pattern = (BlockPattern)this.patternArg.get(context);
/*    */     
/* 47 */     if (pattern == null || pattern.isEmpty()) {
/* 48 */       context.sendMessage(Message.translation("server.builderTools.invalidBlockType")
/* 49 */           .param("name", "")
/* 50 */           .param("key", ""));
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.set(pattern, componentAccessor));
/*    */ 
/*    */     
/* 57 */     context.sendMessage(Message.translation("server.builderTools.set.selectionSet")
/* 58 */         .param("key", pattern.toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\SetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */