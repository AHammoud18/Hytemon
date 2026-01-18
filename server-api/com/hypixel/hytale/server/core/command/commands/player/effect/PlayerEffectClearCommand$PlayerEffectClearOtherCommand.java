/*    */ package com.hypixel.hytale.server.core.command.commands.player.effect;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*    */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlayerEffectClearOtherCommand
/*    */   extends CommandBase
/*    */ {
/* 56 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/* 57 */   private static final Message MESSAGE_EFFECTS_CLEARED_OTHER = Message.translation("server.commands.player.effect.clear.success.other");
/*    */   
/*    */   @Nonnull
/* 60 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */   
/*    */   PlayerEffectClearOtherCommand() {
/* 63 */     super("server.commands.player.effect.clear.other.desc");
/* 64 */     requirePermission(HytalePermissions.fromCommand("player.effect.clear.other"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 69 */     PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 70 */     Ref<EntityStore> ref = targetPlayerRef.getReference();
/*    */     
/* 72 */     if (ref == null || !ref.isValid()) {
/* 73 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */       
/*    */       return;
/*    */     } 
/* 77 */     Store<EntityStore> store = ref.getStore();
/* 78 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*    */     
/* 80 */     world.execute(() -> {
/*    */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*    */           if (playerComponent == null) {
/*    */             context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*    */             return;
/*    */           } 
/*    */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*    */           assert playerRefComponent != null;
/*    */           EffectControllerComponent effectControllerComponent = (EffectControllerComponent)store.getComponent(ref, EffectControllerComponent.getComponentType());
/*    */           assert effectControllerComponent != null;
/*    */           effectControllerComponent.clearEffects(ref, (ComponentAccessor)store);
/*    */           context.sendMessage(MESSAGE_EFFECTS_CLEARED_OTHER.param("username", playerRefComponent.getUsername()));
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\effect\PlayerEffectClearCommand$PlayerEffectClearOtherCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */