/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockMask;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class GlobalMaskCommand
/*    */   extends AbstractPlayerCommand {
/*    */   public GlobalMaskCommand() {
/* 24 */     super("gmask", "server.commands.globalmask.desc");
/*    */     
/* 26 */     setPermissionGroup(GameMode.Creative);
/* 27 */     addUsageVariant((AbstractCommand)new GlobalMaskSetCommand());
/* 28 */     addSubCommand((AbstractCommand)new GlobalMaskClearCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 37 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 38 */     assert playerComponent != null;
/*    */     
/* 40 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> {
/*    */           BlockMask currentMask = s.getGlobalMask();
/*    */           if (currentMask == null) {
/*    */             context.sendMessage(Message.translation("server.builderTools.globalmask.current.none"));
/*    */           } else {
/*    */             context.sendMessage(Message.translation("server.builderTools.globalmask.current").param("mask", currentMask.informativeToString()));
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   private static class GlobalMaskSetCommand
/*    */     extends AbstractPlayerCommand
/*    */   {
/*    */     @Nonnull
/* 54 */     private final RequiredArg<BlockMask> maskArg = withRequiredArg("mask", "server.commands.globalmask.mask.desc", ArgTypes.BLOCK_MASK);
/*    */     
/*    */     public GlobalMaskSetCommand() {
/* 57 */       super("server.commands.globalmask.desc");
/* 58 */       setPermissionGroup(GameMode.Creative);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 67 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 68 */       assert playerComponent != null;
/*    */       
/* 70 */       BlockMask mask = (BlockMask)this.maskArg.get(context);
/*    */       try {
/* 72 */         BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.setGlobalMask(mask, componentAccessor));
/*    */       }
/* 74 */       catch (IllegalArgumentException e) {
/* 75 */         context.sendMessage(Message.translation("server.builderTools.globalmask.setFailed")
/* 76 */             .param("reason", e.getMessage()));
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   private static class GlobalMaskClearCommand
/*    */     extends AbstractPlayerCommand {
/*    */     public GlobalMaskClearCommand() {
/* 84 */       super("clear", "server.commands.globalmask.clear.desc");
/* 85 */       setPermissionGroup(GameMode.Creative);
/* 86 */       addAliases(new String[] { "disable", "c" });
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 95 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 96 */       assert playerComponent != null;
/*    */       
/* 98 */       BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.setGlobalMask(null, componentAccessor));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\GlobalMaskCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */