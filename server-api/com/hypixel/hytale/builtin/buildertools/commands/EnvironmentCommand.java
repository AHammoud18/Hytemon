/*    */ package com.hypixel.hytale.builtin.buildertools.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*    */ import com.hypixel.hytale.common.util.StringUtil;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.GameMode;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandUtil;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
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
/*    */ 
/*    */ 
/*    */ public class EnvironmentCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 33 */   private final RequiredArg<String> environmentArg = withRequiredArg("environment", "server.commands.environment.environment.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnvironmentCommand() {
/* 39 */     super("environment", "server.commands.environment.desc");
/* 40 */     setPermissionGroup(GameMode.Creative);
/* 41 */     addAliases(new String[] { "setenv", "setenvironment" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 50 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 51 */     assert playerComponent != null;
/*    */     
/* 53 */     String envName = (String)this.environmentArg.get(context);
/* 54 */     Environment environment = (Environment)Environment.getAssetMap().getAsset(envName);
/*    */     
/* 56 */     if (environment == null) {
/* 57 */       context.sendMessage(Message.translation("server.builderTools.environment.envNotFound")
/* 58 */           .param("name", envName));
/* 59 */       context.sendMessage(Message.translation("server.general.failed.didYouMean")
/* 60 */           .param("choices", StringUtil.sortByFuzzyDistance(envName, Environment.getAssetMap().getAssetMap().keySet(), CommandUtil.RECOMMEND_COUNT).toString()));
/*    */       
/*    */       return;
/*    */     } 
/* 64 */     String key = environment.getId();
/* 65 */     int index = Environment.getAssetMap().getIndex(key);
/* 66 */     if (index == Integer.MIN_VALUE) {
/* 67 */       throw new IllegalArgumentException("Unknown key! " + key);
/*    */     }
/*    */     
/* 70 */     BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.environment(r, index, componentAccessor));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\EnvironmentCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */