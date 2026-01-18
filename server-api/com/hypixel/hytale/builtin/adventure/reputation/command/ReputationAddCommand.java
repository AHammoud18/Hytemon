/*    */ package com.hypixel.hytale.builtin.adventure.reputation.command;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.ReputationPlugin;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationGroup;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractTargetPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.entity.entities.Player;
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
/*    */ 
/*    */ public class ReputationAddCommand
/*    */   extends AbstractTargetPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 32 */   private static final SingleArgumentType<ReputationGroup> REPUTATION_GROUP_ARG_TYPE = (SingleArgumentType<ReputationGroup>)new AssetArgumentType("server.commands.parsing.argtype.asset.reputationgroup.name", ReputationGroup.class, "server.commands.parsing.argtype.asset.reputationgroup.usage");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 42 */   private final RequiredArg<ReputationGroup> reputationGroupIdArg = withRequiredArg("reputationGroupId", "server.commands.reputation.add.reputationGroupId.desc", (ArgumentType)REPUTATION_GROUP_ARG_TYPE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 48 */   private final RequiredArg<Integer> valueArg = withRequiredArg("value", "server.commands.reputation.add.value.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReputationAddCommand() {
/* 54 */     super("add", "server.commands.reputation.add.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nullable Ref<EntityStore> sourceRef, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 59 */     ReputationGroup reputationGroup = (ReputationGroup)this.reputationGroupIdArg.get(context);
/* 60 */     int value = ((Integer)this.valueArg.get(context)).intValue();
/*    */     
/* 62 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 63 */     assert playerComponent != null;
/*    */     
/* 65 */     int newReputationAmount = ReputationPlugin.get().changeReputation(playerComponent, reputationGroup.getId(), value, (ComponentAccessor)store);
/*    */     
/* 67 */     context.sendMessage(Message.translation("server.modules.reputation.success")
/* 68 */         .param("id", reputationGroup.getId())
/* 69 */         .param("value", newReputationAmount));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\reputation\command\ReputationAddCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */