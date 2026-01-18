/*     */ package com.hypixel.hytale.server.core.plugin.commands;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.HytaleServerConfig;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginBase;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginManager;
/*     */ import com.hypixel.hytale.server.core.plugin.PluginState;
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
/*     */ class PluginLoadCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/* 103 */   private final RequiredArg<PluginIdentifier> pluginNameArg = withRequiredArg("pluginName", "server.commands.plugin.load.pluginName.desc", (ArgumentType)PluginCommand.PLUGIN_IDENTIFIER_ARG_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 109 */   private final FlagArg bootFlag = withFlagArg("boot", "server.commands.plugin.load.boot.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginLoadCommand() {
/* 115 */     super("load", "server.commands.plugin.load.desc");
/* 116 */     addAliases(new String[] { "l" });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/* 121 */     PluginManager module = PluginManager.get();
/* 122 */     PluginIdentifier identifier = (PluginIdentifier)this.pluginNameArg.get(context);
/* 123 */     PluginBase plugin = module.getPlugin(identifier);
/*     */     
/* 125 */     if (identifier != null) {
/* 126 */       boolean onlyBootList = ((Boolean)this.bootFlag.get(context)).booleanValue();
/*     */       
/* 128 */       HytaleServerConfig serverConfig = HytaleServer.get().getConfig();
/* 129 */       HytaleServerConfig.ModConfig.setBoot(serverConfig, identifier, true);
/* 130 */       if (serverConfig.consumeHasChanged()) {
/* 131 */         HytaleServerConfig.save(serverConfig).join();
/*     */       }
/*     */       
/* 134 */       context.sendMessage(Message.translation("server.commands.plugin.bootListEnabled")
/* 135 */           .param("id", identifier.toString()));
/*     */       
/* 137 */       if (onlyBootList)
/*     */         return; 
/*     */     } 
/* 140 */     if (plugin == null || plugin.getState() == PluginState.DISABLED) {
/* 141 */       context.sendMessage(Message.translation("server.commands.plugin.pluginLoading")
/* 142 */           .param("id", identifier.toString()));
/* 143 */       if (module.load(identifier)) {
/* 144 */         context.sendMessage(Message.translation("server.commands.plugin.pluginLoaded")
/* 145 */             .param("id", identifier.toString()));
/*     */       } else {
/* 147 */         context.sendMessage(Message.translation("server.commands.plugin.failedToLoadPlugin")
/* 148 */             .param("id", identifier.toString()));
/*     */       } 
/*     */     } else {
/* 151 */       assert identifier != null;
/*     */       
/* 153 */       switch (PluginCommand.null.$SwitchMap$com$hypixel$hytale$server$core$plugin$PluginState[plugin.getState().ordinal()]) {
/*     */         case 1:
/* 155 */           context.sendMessage(Message.translation("server.commands.plugin.failedToLoadInvalidState")
/* 156 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 2:
/* 159 */           context.sendMessage(Message.translation("server.commands.plugin.failedToLoadSetup")
/* 160 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 3:
/* 163 */           context.sendMessage(Message.translation("server.commands.plugin.failedToLoadStarted")
/* 164 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 4:
/* 167 */           context.sendMessage(Message.translation("server.commands.plugin.failedToLoadAlreadyEnabled")
/* 168 */               .param("id", identifier.toString()));
/*     */           return;
/*     */       } 
/* 171 */       context.sendMessage(Message.translation("server.commands.plugin.failedPluginState")
/* 172 */           .param("state", plugin.getState().toString()));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\commands\PluginCommand$PluginLoadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */