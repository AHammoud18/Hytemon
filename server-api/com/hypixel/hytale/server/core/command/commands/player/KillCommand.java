/*    */ package com.hypixel.hytale.server.core.command.commands.player;
/*    */ 
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
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*    */ import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
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
/*    */ public class KillCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   public KillCommand() {
/* 33 */     super("kill", "server.commands.kill.desc");
/* 34 */     requirePermission(HytalePermissions.fromCommand("kill.self"));
/* 35 */     addUsageVariant((AbstractCommand)new KillOtherCommand());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 44 */     Damage.CommandSource damageSource = new Damage.CommandSource(context.sender(), getName());
/*    */     
/* 46 */     DeathComponent.tryAddComponent(store, ref, new Damage((Damage.Source)damageSource, DamageCause.COMMAND, 2.1474836E9F));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     context.sendMessage(Message.translation("server.commands.kill.success.self"));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static class KillOtherCommand
/*    */     extends CommandBase
/*    */   {
/* 59 */     private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/* 65 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     KillOtherCommand() {
/* 71 */       super("server.commands.kill.other.desc");
/* 72 */       requirePermission(HytalePermissions.fromCommand("kill.other"));
/*    */     }
/*    */ 
/*    */     
/*    */     protected void executeSync(@Nonnull CommandContext context) {
/* 77 */       PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 78 */       Ref<EntityStore> ref = targetPlayerRef.getReference();
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
/*    */             PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*    */             assert playerRefComponent != null;
/*    */             Damage.CommandSource damageSource = new Damage.CommandSource(context.sender(), "kill");
/*    */             DeathComponent.tryAddComponent(store, ref, new Damage((Damage.Source)damageSource, DamageCause.COMMAND, 2.1474836E9F));
/*    */             context.sendMessage(Message.translation("server.commands.kill.success.other").param("username", playerRefComponent.getUsername()));
/*    */           });
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\KillCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */