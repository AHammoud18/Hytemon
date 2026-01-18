/*    */ package com.hypixel.hytale.server.core.command.commands.world.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class EntityResendCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 22 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */   @Nonnull
/* 24 */   private static final Message MESSAGE_COMMANDS_ENTITY_RESET_NO_ENTITY_VIEWER_COMPONENT = Message.translation("server.commands.entity.resend.noEntityViewerComponent");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 30 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.entity.resend.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityResendCommand() {
/* 36 */     super("resend", "server.commands.entity.resend.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 41 */     PlayerRef playerRef = (PlayerRef)this.playerArg.get(context);
/* 42 */     Ref<EntityStore> ref = playerRef.getReference();
/* 43 */     if (ref == null || !ref.isValid()) {
/* 44 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 48 */     if (!EntityTrackerSystems.despawnAll(ref, store))
/* 49 */       context.sendMessage(MESSAGE_COMMANDS_ENTITY_RESET_NO_ENTITY_VIEWER_COMPONENT); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\world\entity\EntityResendCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */