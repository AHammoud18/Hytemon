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
/*     */ 
/*     */ 
/*     */ 
/*     */ class PluginUnloadCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/* 188 */   private final RequiredArg<PluginIdentifier> pluginNameArg = withRequiredArg("pluginName", "server.commands.plugin.unload.pluginName.desc", (ArgumentType)PluginCommand.PLUGIN_IDENTIFIER_ARG_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/* 194 */   private final FlagArg bootFlag = withFlagArg("boot", "server.commands.plugin.unload.boot.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginUnloadCommand() {
/* 200 */     super("unload", "server.commands.plugin.unload.desc");
/* 201 */     addAliases(new String[] { "u" });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/* 206 */     PluginManager module = PluginManager.get();
/* 207 */     PluginIdentifier identifier = (PluginIdentifier)this.pluginNameArg.get(context);
/* 208 */     PluginBase plugin = module.getPlugin(identifier);
/*     */     
/* 210 */     if (identifier != null) {
/* 211 */       boolean onlyBootList = ((Boolean)this.bootFlag.get(context)).booleanValue();
/*     */       
/* 213 */       HytaleServerConfig serverConfig = HytaleServer.get().getConfig();
/* 214 */       HytaleServerConfig.ModConfig.setBoot(serverConfig, identifier, false);
/* 215 */       if (serverConfig.consumeHasChanged()) {
/* 216 */         HytaleServerConfig.save(serverConfig).join();
/*     */       }
/*     */       
/* 219 */       context.sendMessage(Message.translation("server.commands.plugin.bootListDisabled")
/* 220 */           .param("id", identifier.toString()));
/*     */       
/* 222 */       if (onlyBootList)
/*     */         return; 
/*     */     } 
/* 225 */     if (plugin != null) {
/* 226 */       switch (PluginCommand.null.$SwitchMap$com$hypixel$hytale$server$core$plugin$PluginState[plugin.getState().ordinal()]) {
/*     */         case 1:
/* 228 */           context.sendMessage(Message.translation("server.commands.plugin.failedToUnloadState")
/* 229 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 2:
/* 232 */           context.sendMessage(Message.translation("server.commands.plugin.failedToUnloadSetup")
/* 233 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 3:
/* 236 */           context.sendMessage(Message.translation("server.commands.plugin.failedToUnloadStarted")
/* 237 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 4:
/* 240 */           context.sendMessage(Message.translation("server.commands.plugin.pluginUnloading")
/* 241 */               .param("id", identifier.toString()));
/* 242 */           if (module.unload(identifier)) {
/* 243 */             context.sendMessage(Message.translation("server.commands.plugin.pluginUnloaded")
/* 244 */                 .param("id", identifier.toString()));
/*     */           } else {
/* 246 */             context.sendMessage(Message.translation("server.commands.plugin.failedToUnload")
/* 247 */                 .param("id", identifier.toString()));
/*     */           } 
/*     */           return;
/*     */         case 5:
/* 251 */           context.sendMessage(Message.translation("server.commands.plugin.failedToUnloadDisabled")
/* 252 */               .param("id", identifier.toString()));
/*     */           return;
/*     */       } 
/* 255 */       context.sendMessage(Message.translation("server.commands.plugin.failedPluginState")
/* 256 */           .param("state", plugin.getState().toString()));
/*     */     }
/*     */     else {
/*     */       
/* 260 */       context.sendMessage(Message.translation("server.commands.plugin.notLoaded")
/* 261 */           .param("id", identifier.toString()));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\commands\PluginCommand$PluginUnloadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */