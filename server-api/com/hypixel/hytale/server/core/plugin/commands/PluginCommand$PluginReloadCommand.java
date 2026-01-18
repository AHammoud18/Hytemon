/*     */ package com.hypixel.hytale.server.core.plugin.commands;
/*     */ 
/*     */ import com.hypixel.hytale.common.plugin.PluginIdentifier;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PluginReloadCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/* 275 */   private final RequiredArg<PluginIdentifier> pluginNameArg = withRequiredArg("pluginName", "server.commands.plugin.reload.pluginName.desc", (ArgumentType)PluginCommand.PLUGIN_IDENTIFIER_ARG_TYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PluginReloadCommand() {
/* 281 */     super("reload", "server.commands.plugin.reload.desc");
/* 282 */     addAliases(new String[] { "r" });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/* 287 */     PluginManager module = PluginManager.get();
/* 288 */     PluginIdentifier identifier = (PluginIdentifier)this.pluginNameArg.get(context);
/* 289 */     PluginBase plugin = module.getPlugin(identifier);
/*     */     
/* 291 */     if (plugin != null) {
/* 292 */       switch (PluginCommand.null.$SwitchMap$com$hypixel$hytale$server$core$plugin$PluginState[plugin.getState().ordinal()]) {
/*     */         case 1:
/* 294 */           context.sendMessage(Message.translation("server.commands.plugin.failedToReloadState")
/* 295 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 2:
/* 298 */           context.sendMessage(Message.translation("server.commands.plugin.failedToReloadSetup")
/* 299 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 3:
/* 302 */           context.sendMessage(Message.translation("server.commands.plugin.failedToReloadStarted")
/* 303 */               .param("id", identifier.toString()));
/*     */           return;
/*     */         case 4:
/* 306 */           if (module.reload(identifier)) {
/* 307 */             context.sendMessage(Message.translation("server.commands.plugin.pluginReloaded")
/* 308 */                 .param("id", identifier.toString()));
/*     */           } else {
/* 310 */             context.sendMessage(Message.translation("server.commands.plugin.failedToReload")
/* 311 */                 .param("id", identifier.toString()));
/*     */           } 
/*     */           return;
/*     */         case 5:
/* 315 */           context.sendMessage(Message.translation("server.commands.plugin.failedToReloadDisabled")
/* 316 */               .param("id", identifier.toString()));
/*     */           return;
/*     */       } 
/* 319 */       context.sendMessage(Message.translation("server.commands.plugin.failedPluginState")
/* 320 */           .param("state", plugin.getState().toString()));
/*     */     }
/*     */     else {
/*     */       
/* 324 */       context.sendMessage(Message.translation("server.commands.plugin.notLoaded")
/* 325 */           .param("id", identifier.toString()));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\plugin\commands\PluginCommand$PluginReloadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */