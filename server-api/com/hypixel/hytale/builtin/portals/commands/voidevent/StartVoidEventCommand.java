/*    */ package com.hypixel.hytale.builtin.portals.commands.voidevent;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.portals.integrations.PortalGameplayConfig;
/*    */ import com.hypixel.hytale.builtin.portals.integrations.PortalRemovalCondition;
/*    */ import com.hypixel.hytale.builtin.portals.resources.PortalWorld;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.vector.Transform;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.gameplay.GameplayConfig;
/*    */ import com.hypixel.hytale.server.core.asset.type.portalworld.PortalType;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class StartVoidEventCommand
/*    */   extends AbstractWorldCommand {
/* 20 */   private final FlagArg overrideWorld = withFlagArg("override", "server.commands.voidevent.start.overrideArg");
/*    */   
/*    */   private static final String HARDCODED_GAMEPLAY_CONFIG = "Portal";
/*    */   private static final String HARDCODED_PORTAL_TYPE = "Hederas_Lair";
/*    */   
/*    */   public StartVoidEventCommand() {
/* 26 */     super("start", "server.commands.voidevent.start.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 31 */     PortalWorld portalWorld = (PortalWorld)store.getResource(PortalWorld.getResourceType());
/* 32 */     if (portalWorld.exists() && portalWorld.isVoidEventActive()) {
/* 33 */       context.sendMessage(Message.translation("server.commands.voidevent.start.alreadyRunning"));
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     if (!portalWorld.exists()) {
/* 38 */       if (!((Boolean)this.overrideWorld.get(context)).booleanValue()) {
/* 39 */         context.sendMessage(Message.translation("server.commands.portals.notInPortal"));
/*    */         
/*    */         return;
/*    */       } 
/* 43 */       GameplayConfig gameplayConfig = (GameplayConfig)GameplayConfig.getAssetMap().getAsset("Portal");
/* 44 */       PortalGameplayConfig portalGameplayConfig = (gameplayConfig == null) ? null : (PortalGameplayConfig)gameplayConfig.getPluginConfig().get(PortalGameplayConfig.class);
/* 45 */       if (portalGameplayConfig == null) {
/* 46 */         context.sendMessage(Message.translation("server.commands.voidevent.start.botchedConfig").param("config", "Portal"));
/*    */         
/*    */         return;
/*    */       } 
/* 50 */       world.getWorldConfig().setGameplayConfig("Portal");
/*    */       
/* 52 */       portalWorld.init(
/* 53 */           (PortalType)PortalType.getAssetMap().getAsset("Hederas_Lair"), 10000, new PortalRemovalCondition(10000.0D), portalGameplayConfig);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 58 */       portalWorld.setSpawnPoint(new Transform(0.0D, 100.0D, 0.0D));
/*    */       
/* 60 */       context.sendMessage(Message.translation("server.commands.voidevent.start.overrode"));
/*    */     } 
/*    */     
/* 63 */     portalWorld.setRemainingSeconds(world, 1.0D);
/*    */     
/* 65 */     context.sendMessage(Message.translation("server.commands.voidevent.start.success"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\portals\commands\voidevent\StartVoidEventCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */