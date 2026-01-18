/*    */ package com.hypixel.hytale.server.core.command.commands.player.stats;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.commands.world.entity.stats.EntityStatsAddCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Collections;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerStatsAddCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 25 */   private final RequiredArg<String> entityStatNameArg = withRequiredArg("statName", "server.commands.player.stats.add.statName.desc", (ArgumentType)ArgTypes.STRING);
/*    */   @Nonnull
/* 27 */   private final RequiredArg<Integer> statAmountArg = withRequiredArg("statAmount", "server.commands.player.stats.add.statAmount.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerStatsAddCommand() {
/* 33 */     super("add", "server.commands.player.stats.add.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 38 */     int statAmount = ((Integer)this.statAmountArg.get(context)).intValue();
/* 39 */     String entityStat = (String)this.entityStatNameArg.get(context);
/* 40 */     EntityStatsAddCommand.addEntityStat(context, Collections.singletonList(ref), statAmount, entityStat, store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\stats\PlayerStatsAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */