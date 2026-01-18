/*    */ package com.hypixel.hytale.server.core.command.commands.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WhoAmICommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public static final String UUID_ALIAS = "uuid";
/*    */   
/*    */   public WhoAmICommand() {
/* 30 */     super("whoami", "server.commands.whoami.desc");
/* 31 */     setPermissionGroup(GameMode.Adventure);
/* 32 */     addAliases(new String[] { "uuid" });
/* 33 */     addUsageVariant((AbstractCommand)new WhoAmIOtherCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 38 */     sendPlayerInfo(context, playerRef);
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
/*    */   private static void sendPlayerInfo(@Nonnull CommandContext context, @Nonnull PlayerRef playerRef) {
/* 51 */     Message message = Message.translation("server.commands.whoami.header").param("uuid", playerRef.getUuid().toString()).param("username", playerRef.getUsername()).param("language", playerRef.getLanguage());
/* 52 */     context.sendMessage(message);
/*    */   }
/*    */ 
/*    */   
/*    */   private static class WhoAmIOtherCommand
/*    */     extends CommandBase
/*    */   {
/*    */     @Nonnull
/* 60 */     private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/* 66 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     WhoAmIOtherCommand() {
/* 72 */       super("server.commands.whoami.other.desc");
/*    */     }
/*    */ 
/*    */     
/*    */     protected void executeSync(@Nonnull CommandContext context) {
/* 77 */       PlayerRef playerRef = (PlayerRef)this.playerArg.get(context);
/* 78 */       Ref<EntityStore> ref = playerRef.getReference();
/*    */       
/* 80 */       if (ref == null || !ref.isValid()) {
/* 81 */         context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */         
/*    */         return;
/*    */       } 
/* 85 */       Store<EntityStore> store = ref.getStore();
/* 86 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */       
/* 88 */       world.execute(() -> {
/*    */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*    */             if (playerComponent == null) {
/*    */               context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */               return;
/*    */             } 
/*    */             WhoAmICommand.sendPlayerInfo(context, playerRef);
/*    */           });
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\WhoAmICommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */