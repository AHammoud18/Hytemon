/*     */ package com.hypixel.hytale.builtin.buildertools.commands;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.PathUtil;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.modules.singleplayer.SingleplayerModule;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
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
/*     */ class PrefabDeleteCommand
/*     */   extends CommandBase
/*     */ {
/*     */   @Nonnull
/* 223 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.prefab.delete.name.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrefabDeleteCommand() {
/* 229 */     super("delete", "server.commands.prefab.delete.desc", true);
/* 230 */     requirePermission("hytale.editor.prefab.manage");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void executeSync(@Nonnull CommandContext context) {
/* 235 */     String name = (String)this.nameArg.get(context);
/* 236 */     if (!name.endsWith(".prefab.json")) {
/* 237 */       name = name + ".prefab.json";
/*     */     }
/* 239 */     PrefabStore module = PrefabStore.get();
/* 240 */     Path serverPrefabsPath = module.getServerPrefabsPath();
/* 241 */     Path resolve = serverPrefabsPath.resolve(name);
/*     */     try {
/* 243 */       Ref<EntityStore> ref = context.senderAsPlayerRef();
/* 244 */       boolean isOwner = false;
/* 245 */       if (ref != null && ref.isValid()) {
/* 246 */         Store<EntityStore> store = ref.getStore();
/* 247 */         PlayerRef playerRefComponent = (PlayerRef)store.getComponent(ref, PlayerRef.getComponentType());
/* 248 */         if (playerRefComponent != null) {
/* 249 */           isOwner = SingleplayerModule.isOwner(playerRefComponent);
/*     */         }
/*     */       } 
/* 252 */       if (!PathUtil.isChildOf(serverPrefabsPath, resolve) && !isOwner) {
/* 253 */         context.sendMessage(Message.translation("server.builderTools.attemptedToSaveOutsidePrefabsDir"));
/*     */         return;
/*     */       } 
/* 256 */       Path relativize = PathUtil.relativize(serverPrefabsPath, resolve);
/* 257 */       if (Files.isRegularFile(resolve, new java.nio.file.LinkOption[0])) {
/* 258 */         Files.delete(resolve);
/* 259 */         context.sendMessage(Message.translation("server.builderTools.prefab.deleted")
/* 260 */             .param("name", relativize.toString()));
/*     */       } else {
/* 262 */         context.sendMessage(Message.translation("server.builderTools.prefab.prefabNotFound")
/* 263 */             .param("name", relativize.toString()));
/*     */       } 
/* 265 */     } catch (IOException e) {
/* 266 */       context.sendMessage(Message.translation("server.builderTools.prefab.errorOccured")
/* 267 */           .param("reason", e.getMessage()));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\PrefabCommand$PrefabDeleteCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */