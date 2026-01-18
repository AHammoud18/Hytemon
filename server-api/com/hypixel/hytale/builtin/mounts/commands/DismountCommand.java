/*    */ package com.hypixel.hytale.builtin.mounts.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.mounts.MountedComponent;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class DismountCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 25 */   private static final Message MESSAGE_COMMANDS_DISMOUNT_DISMOUNT_ATTEMPTED = Message.translation("server.commands.dismount.dismountAttempted");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DismountCommand() {
/* 31 */     super("dismount", "server.commands.dismount.desc");
/* 32 */     addUsageVariant((AbstractCommand)new DismountOtherCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 37 */     store.tryRemoveComponent(ref, MountedComponent.getComponentType());
/* 38 */     context.sendMessage(MESSAGE_COMMANDS_DISMOUNT_DISMOUNT_ATTEMPTED);
/*    */   }
/*    */ 
/*    */   
/*    */   private static class DismountOtherCommand
/*    */     extends CommandBase
/*    */   {
/*    */     @Nonnull
/* 46 */     private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/* 52 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     DismountOtherCommand() {
/* 58 */       super("server.commands.dismount.other.desc");
/*    */     }
/*    */ 
/*    */     
/*    */     protected void executeSync(@Nonnull CommandContext context) {
/* 63 */       PlayerRef playerRef = (PlayerRef)this.playerArg.get(context);
/* 64 */       Ref<EntityStore> ref = playerRef.getReference();
/*    */       
/* 66 */       if (ref == null || !ref.isValid()) {
/* 67 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */         
/*    */         return;
/*    */       } 
/* 71 */       Store<EntityStore> store = ref.getStore();
/* 72 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */       
/* 74 */       world.execute(() -> {
/*    */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*    */             if (playerComponent == null) {
/*    */               context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */               return;
/*    */             } 
/*    */             store.tryRemoveComponent(ref, MountedComponent.getComponentType());
/*    */             context.sendMessage(Message.translation("server.commands.dismount.dismountOther").param("username", playerRef.getUsername()));
/*    */           });
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\mounts\commands\DismountCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */