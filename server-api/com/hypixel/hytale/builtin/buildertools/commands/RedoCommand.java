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
/*    */ public class RedoCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public RedoCommand() {
/* 29 */     super("redo", "server.commands.redo.desc");
/* 30 */     setPermissionGroup(GameMode.Creative);
/* 31 */     requirePermission("hytale.editor.history");
/* 32 */     addAliases(new String[] { "r" });
/* 33 */     addUsageVariant((AbstractCommand)new RedoWithCountCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 42 */     executeRedo(store, ref, 1);
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
/*    */   private static void executeRedo(@Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, int count) {
/* 55 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 56 */     assert playerComponent != null;
/*    */     
/* 58 */     PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 59 */     assert playerRefComponent != null;
/*    */     
/* 61 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRefComponent, (r, s, componentAccessor) -> s.redo(r, count, componentAccessor));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class RedoWithCountCommand
/*    */     extends AbstractPlayerCommand
/*    */   {
/*    */     @Nonnull
/* 74 */     private final RequiredArg<Integer> countArg = withRequiredArg("count", "server.commands.redo.count.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public RedoWithCountCommand() {
/* 80 */       super("server.commands.redo.desc");
/* 81 */       setPermissionGroup(GameMode.Creative);
/* 82 */       requirePermission("hytale.editor.history");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 91 */       RedoCommand.executeRedo(store, ref, ((Integer)this.countArg.get(context)).intValue());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\RedoCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */