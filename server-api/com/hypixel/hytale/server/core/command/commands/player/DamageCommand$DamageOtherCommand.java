/*     */ package com.hypixel.hytale.server.core.command.commands.player;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
/*     */ import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
/*     */ import com.hypixel.hytale.server.core.permissions.HytalePermissions;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DamageOtherCommand
/*     */   extends CommandBase
/*     */ {
/*  77 */   private static final Message MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD = Message.translation("server.commands.errors.playerNotInWorld");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  83 */   private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */   
/*     */   @Nonnull
/*  86 */   private final OptionalArg<Double> amountArg = withOptionalArg("amount", "server.commands.damage.arg.amount.desc", (ArgumentType)ArgTypes.DOUBLE);
/*     */   
/*     */   @Nonnull
/*  89 */   private final FlagArg silentArg = withFlagArg("silent", "server.commands.damage.arg.silent.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DamageOtherCommand() {
/*  95 */     super("server.commands.damage.other.desc");
/*  96 */     requirePermission(HytalePermissions.fromCommand("damage.other"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/* 101 */     PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 102 */     Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */     
/* 104 */     if (ref == null || !ref.isValid()) {
/* 105 */       context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */       
/*     */       return;
/*     */     } 
/* 109 */     Store<EntityStore> store = ref.getStore();
/* 110 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */     
/* 112 */     world.execute(() -> {
/*     */           Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */           if (playerComponent == null) {
/*     */             context.sendMessage(MESSAGE_COMMANDS_ERRORS_PLAYER_NOT_IN_WORLD);
/*     */             return;
/*     */           } 
/*     */           PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/*     */           assert playerRefComponent != null;
/*     */           double amount = this.amountArg.provided(context) ? ((Double)this.amountArg.get(context)).doubleValue() : 1.0D;
/*     */           boolean silent = ((Boolean)this.silentArg.get(context)).booleanValue();
/*     */           Damage.CommandSource damageSource = new Damage.CommandSource(context.sender(), "damage");
/*     */           Damage damage = new Damage((Damage.Source)damageSource, DamageCause.COMMAND, (float)amount);
/*     */           DamageSystems.executeDamage(ref, (ComponentAccessor)store, damage);
/*     */           if (silent)
/*     */             return; 
/*     */           String damageFmt = String.format("%.1f", new Object[] { Double.valueOf(amount) });
/*     */           context.sendMessage(Message.translation("server.commands.damage.dealt").param("damage", damageFmt).param("victim", playerRefComponent.getUsername()));
/*     */           if (world.getGameplayConfig().getCombatConfig().isPlayerIncomingDamageDisabled())
/*     */             context.sendMessage(Message.translation("server.commands.damage.disabled").color("#ffc800")); 
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\DamageCommand$DamageOtherCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */