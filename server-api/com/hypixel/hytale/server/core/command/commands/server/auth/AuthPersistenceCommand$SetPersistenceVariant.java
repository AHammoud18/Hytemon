/*    */ package com.hypixel.hytale.server.core.command.commands.server.auth;
/*    */ 
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.server.core.HytaleServer;
/*    */ import com.hypixel.hytale.server.core.auth.AuthCredentialStoreProvider;
/*    */ import com.hypixel.hytale.server.core.auth.ServerAuthManager;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SetPersistenceVariant
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 63 */   private final RequiredArg<String> typeArg = withRequiredArg("type", "server.commands.auth.persistence.type.desc", (ArgumentType)ArgTypes.STRING);
/*    */   
/*    */   SetPersistenceVariant() {
/* 66 */     super("server.commands.auth.persistence.variant.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 71 */     ServerAuthManager authManager = ServerAuthManager.getInstance();
/*    */     
/* 73 */     if (authManager.isSingleplayer()) {
/* 74 */       context.sendMessage(AuthPersistenceCommand.MESSAGE_SINGLEPLAYER);
/*    */       
/*    */       return;
/*    */     } 
/* 78 */     String typeName = (String)this.typeArg.get(context);
/*    */ 
/*    */     
/* 81 */     BuilderCodec<? extends AuthCredentialStoreProvider> codec = (BuilderCodec<? extends AuthCredentialStoreProvider>)AuthCredentialStoreProvider.CODEC.getCodecFor(typeName);
/* 82 */     if (codec == null) {
/* 83 */       context.sendMessage(AuthPersistenceCommand.MESSAGE_UNKNOWN_TYPE.param("type", typeName));
/*    */       
/*    */       return;
/*    */     } 
/* 87 */     AuthCredentialStoreProvider newProvider = (AuthCredentialStoreProvider)codec.getDefaultValue();
/*    */ 
/*    */     
/* 90 */     HytaleServer.get().getConfig().setAuthCredentialStoreProvider(newProvider);
/* 91 */     authManager.swapCredentialStoreProvider(newProvider);
/*    */     
/* 93 */     context.sendMessage(AuthPersistenceCommand.MESSAGE_CHANGED.param("type", typeName));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\server\auth\AuthPersistenceCommand$SetPersistenceVariant.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */