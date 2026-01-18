/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
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
/*    */ public class UndoCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public UndoCommand() {
/* 29 */     super("undo", "server.commands.undo.desc");
/* 30 */     setPermissionGroup(GameMode.Creative);
/* 31 */     requirePermission("hytale.editor.history");
/* 32 */     addAliases(new String[] { "u" });
/* 33 */     addUsageVariant((AbstractCommand)new UndoWithCountCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 42 */     executeUndo(store, ref, 1);
/*    */   }
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
/*    */   private static void executeUndo(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, int count) {
/* 55 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 56 */     assert playerComponent != null;
/*    */     
/* 58 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 59 */     assert playerRefComponent != null;
/*    */     
/* 61 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRefComponent, (r, s, c) -> s.undo(r, count, c));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class UndoWithCountCommand
/*    */     extends AbstractPlayerCommand
/*    */   {
/*    */     @Nonnull
/* 73 */     private final RequiredArg<Integer> countArg = withRequiredArg("count", "server.commands.undo.count.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public UndoWithCountCommand() {
/* 79 */       super("server.commands.undo.desc");
/* 80 */       setPermissionGroup(GameMode.Creative);
/* 81 */       requirePermission("hytale.editor.history");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 90 */       UndoCommand.executeUndo(store, ref, ((Integer)this.countArg.get(context)).intValue());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\UndoCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */