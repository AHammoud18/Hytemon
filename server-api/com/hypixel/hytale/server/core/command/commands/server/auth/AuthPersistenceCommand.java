/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.auth.AuthCredentialStoreProvider;
/*    */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*    */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import java.awt.Color;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuthPersistenceCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 27 */   private static final Message MESSAGE_SINGLEPLAYER = Message.translation("server.commands.auth.persistence.singleplayer").color(Color.RED);
/*    */   @Nonnull
/* 29 */   private static final Message MESSAGE_CURRENT = Message.translation("server.commands.auth.persistence.current").color(Color.YELLOW);
/*    */   @Nonnull
/* 31 */   private static final Message MESSAGE_AVAILABLE = Message.translation("server.commands.auth.persistence.available").color(Color.GRAY);
/*    */   @Nonnull
/* 33 */   private static final Message MESSAGE_CHANGED = Message.translation("server.commands.auth.persistence.changed").color(Color.GREEN);
/*    */   @Nonnull
/* 35 */   private static final Message MESSAGE_UNKNOWN_TYPE = Message.translation("server.commands.auth.persistence.unknownType").color(Color.RED);
/*    */   
/*    */   public AuthPersistenceCommand() {
/* 38 */     super("persistence", "server.commands.auth.persistence.desc");
/* 39 */     addUsageVariant((AbstractCommand)new SetPersistenceVariant());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 44 */     if (ServerAuthManager.getInstance().isSingleplayer()) {
/* 45 */       context.sendMessage(MESSAGE_SINGLEPLAYER);
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     AuthCredentialStoreProvider provider = HytaleServer.get().getConfig().getAuthCredentialStoreProvider();
/* 50 */     String typeName = (String)AuthCredentialStoreProvider.CODEC.getIdFor(provider.getClass());
/* 51 */     context.sendMessage(MESSAGE_CURRENT.param("type", typeName));
/*    */ 
/*    */     
/* 54 */     String availableTypes = String.join(", ", AuthCredentialStoreProvider.CODEC.getRegisteredIds());
/* 55 */     context.sendMessage(MESSAGE_AVAILABLE.param("types", availableTypes));
/*    */   }
/*    */ 
/*    */   
/*    */   private static class SetPersistenceVariant
/*    */     extends CommandBase
/*    */   {
/*    */     @Nonnull
/* 63 */     private final RequiredArg<String> typeArg = withRequiredArg("type", "server.commands.auth.persistence.type.desc", (ArgumentType)ArgTypes.STRING);
/*    */     
/*    */     SetPersistenceVariant() {
/* 66 */       super("server.commands.auth.persistence.variant.desc");
/*    */     }
/*    */ 
/*    */     
/*    */     protected void executeSync(@Nonnull CommandContext context) {
/* 71 */       ServerAuthManager authManager = ServerAuthManager.getInstance();
/*    */       
/* 73 */       if (authManager.isSingleplayer()) {
/* 74 */         context.sendMessage(AuthPersistenceCommand.MESSAGE_SINGLEPLAYER);
/*    */         
/*    */         return;
/*    */       } 
/* 78 */       String typeName = (String)this.typeArg.get(context);
/*    */ 
/*    */       
/* 81 */       BuilderCodec<? extends AuthCredentialStoreProvider> codec = (BuilderCodec<? extends AuthCredentialStoreProvider>)AuthCredentialStoreProvider.CODEC.getCodecFor(typeName);
/* 82 */       if (codec == null) {
/* 83 */         context.sendMessage(AuthPersistenceCommand.MESSAGE_UNKNOWN_TYPE.param("type", typeName));
/*    */         
/*    */         return;
/*    */       } 
/* 87 */       AuthCredentialStoreProvider newProvider = (AuthCredentialStoreProvider)codec.getDefaultValue();
/*    */ 
/*    */       
/* 90 */       HytaleServer.get().getConfig().setAuthCredentialStoreProvider(newProvider);
/* 91 */       authManager.swapCredentialStoreProvider(newProvider);
/*    */       
/* 93 */       context.sendMessage(AuthPersistenceCommand.MESSAGE_CHANGED.param("type", typeName));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthPersistenceCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */