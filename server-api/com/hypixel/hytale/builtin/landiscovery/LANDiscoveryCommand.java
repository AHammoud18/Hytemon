/*    */ package com.hypixel.hytale.builtin.landiscovery;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import com.hypixel.hytale.server.core.util.message.MessageFormat;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LANDiscoveryCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 18 */   private static final Message MESSAGE_IO_LAN_DISCOVERY_DISABLED = Message.translation("server.io.landiscovery.disabled");
/*    */   @Nonnull
/* 20 */   private static final Message MESSAGE_IO_LAN_DISCOVERY_ENABLED = Message.translation("server.io.landiscovery.enabled");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 26 */   private final OptionalArg<Boolean> enabledArg = withOptionalArg("enabled", "server.commands.landiscovery.enabled.desc", (ArgumentType)ArgTypes.BOOLEAN);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LANDiscoveryCommand() {
/* 32 */     super("landiscovery", "server.commands.landiscovery.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 37 */     if (this.enabledArg.provided(context)) {
/* 38 */       boolean enabled = ((Boolean)this.enabledArg.get(context)).booleanValue();
/* 39 */       LANDiscoveryPlugin lANDiscoveryPlugin = LANDiscoveryPlugin.get();
/*    */       
/* 41 */       if (!enabled && lANDiscoveryPlugin.getLanDiscoveryThread() != null) {
/* 42 */         lANDiscoveryPlugin.setLANDiscoveryEnabled(false);
/* 43 */         context.sendMessage(MESSAGE_IO_LAN_DISCOVERY_DISABLED);
/* 44 */       } else if (enabled && lANDiscoveryPlugin.getLanDiscoveryThread() == null) {
/* 45 */         lANDiscoveryPlugin.setLANDiscoveryEnabled(true);
/* 46 */         context.sendMessage(MESSAGE_IO_LAN_DISCOVERY_ENABLED);
/*    */       } else {
/* 48 */         context.sendMessage(Message.translation("server.io.landiscovery.alreadyToggled")
/* 49 */             .param("status", MessageFormat.enabled(enabled)));
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 55 */     LANDiscoveryPlugin plugin = LANDiscoveryPlugin.get();
/* 56 */     if (plugin.getLanDiscoveryThread() == null) {
/* 57 */       plugin.setLANDiscoveryEnabled(true);
/* 58 */       context.sendMessage(MESSAGE_IO_LAN_DISCOVERY_ENABLED);
/*    */     } else {
/* 60 */       plugin.setLANDiscoveryEnabled(false);
/* 61 */       context.sendMessage(MESSAGE_IO_LAN_DISCOVERY_DISABLED);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\landiscovery\LANDiscoveryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */