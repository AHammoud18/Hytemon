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
/*    */ public class FillCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private final RequiredArg<BlockPattern> patternArg = withRequiredArg("pattern", "server.commands.fill.args.replacement.desc", ArgTypes.BLOCK_PATTERN);
/*    */   
/*    */   public FillCommand() {
/* 31 */     super("fillBlocks", "server.commands.fill.desc");
/* 32 */     setPermissionGroup(GameMode.Creative);
/* 33 */     addAliases(new String[] { "fill" });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 38 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 39 */     assert playerComponent != null;
/*    */     
/* 41 */     if (!PrototypePlayerBuilderToolSettings.isOkayToDoCommandsOnSelection(ref, playerComponent, (ComponentAccessor)store))
/*    */       return; 
/* 43 */     BlockPattern pattern = (BlockPattern)this.patternArg.get(context);
/*    */     
/* 45 */     if (pattern == null || pattern.isEmpty()) {
/* 46 */       context.sendMessage(Message.translation("server.builderTools.invalidBlockType")
/* 47 */           .param("name", "")
/* 48 */           .param("key", ""));
/*    */       
/*    */       return;
/*    */     } 
/* 52 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.fill(pattern, componentAccessor));
/*    */ 
/*    */     
/* 55 */     context.sendMessage(Message.translation("server.commands.fill.success")
/* 56 */         .param("key", pattern.toString()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\FillCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */