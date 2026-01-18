/*    */ package com.hypixel.hytale.server.core.command.commands.player.stats;
/*    */ 
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.command.commands.world.entity.stats.EntityStatsSetCommand;
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
/*    */ public class PlayerStatsSetCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 25 */   private final RequiredArg<String> entityStatNameArg = withRequiredArg("statName", "server.commands.player.stats.set.statName.desc", (ArgumentType)ArgTypes.STRING);
/*    */   @Nonnull
/* 27 */   private final RequiredArg<Integer> statValueArg = withRequiredArg("statValue", "server.commands.player.stats.set.statValue.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PlayerStatsSetCommand() {
/* 33 */     super("set", "server.commands.player.stats.set.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 38 */     int newStatValue = ((Integer)this.statValueArg.get(context)).intValue();
/* 39 */     String entityStat = (String)this.entityStatNameArg.get(context);
/* 40 */     EntityStatsSetCommand.setEntityStat(context, Collections.singletonList(playerRef.getReference()), newStatValue, entityStat, store);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\stats\PlayerStatsSetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */