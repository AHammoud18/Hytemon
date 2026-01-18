/*    */ package com.hypixel.hytale.builtin.instances.command;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.instances.InstancesPlugin;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class InstanceExitOtherCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 52 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */   @Nonnull
/* 54 */   private static final Message MESSAGE_COMMANDS_INSTANCES_EXIT_FAIL = Message.translation("server.commands.instances.exit.fail");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 60 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   InstanceExitOtherCommand() {
/* 66 */     super("server.commands.instances.exit.other.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 71 */     PlayerRef playerRef = (PlayerRef)this.playerArg.get(context);
/* 72 */     Ref<EntityStore> ref = playerRef.getReference();
/*    */     
/* 74 */     if (ref == null || !ref.isValid()) {
/* 75 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 79 */     Store<EntityStore> store = ref.getStore();
/* 80 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 82 */     world.execute(() -> {
/*    */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*    */           
/*    */           if (playerComponent == null) {
/*    */             context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */             
/*    */             return;
/*    */           } 
/*    */           try {
/*    */             InstancesPlugin.exitInstance(ref, (ComponentAccessor)store);
/*    */             context.sendMessage(Message.translation("server.commands.instances.exit.success.other").param("username", playerRef.getUsername()));
/* 93 */           } catch (IllegalArgumentException e) {
/*    */             context.sendMessage(MESSAGE_COMMANDS_INSTANCES_EXIT_FAIL);
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\command\InstanceExitCommand$InstanceExitOtherCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */