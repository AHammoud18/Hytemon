/*    */ package com.hypixel.hytale.builtin.adventure.reputation.command;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.ReputationPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationGroup;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationRank;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReputationRankCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 29 */   private static final SingleArgumentType<ReputationGroup> REPUTATION_GROUP_ARG_TYPE = (SingleArgumentType<ReputationGroup>)new AssetArgumentType("server.commands.parsing.argtype.asset.reputationgroup.name", ReputationGroup.class, "server.commands.parsing.argtype.asset.reputationgroup.usage");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 39 */   private final RequiredArg<ReputationGroup> reputationGroupIdArg = withRequiredArg("reputationGroupId", "server.commands.reputation.check.rank.reputationGroupId.desc", (ArgumentType)REPUTATION_GROUP_ARG_TYPE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReputationRankCommand() {
/* 45 */     super("rank", "server.commands.reputation.check.rank.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 50 */     ReputationGroup reputationGroup = (ReputationGroup)this.reputationGroupIdArg.get(context);
/*    */     
/* 52 */     ReputationRank rank = ReputationPlugin.get().getReputationRank(store, ref, reputationGroup.getId());
/* 53 */     if (rank != null) {
/* 54 */       context.sendMessage(Message.translation("server.modules.reputation.valueForGroup")
/* 55 */           .param("id", reputationGroup.getId())
/* 56 */           .param("value", rank.toString()));
/*    */     } else {
/* 58 */       context.sendMessage(Message.translation("server.modules.reputation.noRankFoundForValue")
/* 59 */           .param("value", ReputationPlugin.get().getReputationValue(store, ref, reputationGroup.getId())));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\command\ReputationRankCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */