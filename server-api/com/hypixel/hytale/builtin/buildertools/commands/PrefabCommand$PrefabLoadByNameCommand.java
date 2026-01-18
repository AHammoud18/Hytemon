/*     */ package com.hypixel.hytale.builtin.buildertools.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.utils.RecursivePrefabLoader;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.DefaultArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabStore;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
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
/*     */ class PrefabLoadByNameCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/* 123 */   private final RequiredArg<String> nameArg = withRequiredArg("name", "server.commands.prefab.load.name.desc", (ArgumentType)ArgTypes.STRING);
/*     */   
/*     */   @Nonnull
/* 126 */   private final DefaultArg<String> storeTypeArg = withDefaultArg("storeType", "server.commands.prefab.load.storeType.desc", (ArgumentType)ArgTypes.STRING, "asset", "server.commands.prefab.load.storeType.desc");
/*     */   
/*     */   @Nonnull
/* 129 */   private final DefaultArg<String> storeNameArg = withDefaultArg("storeName", "server.commands.prefab.load.storeName.desc", (ArgumentType)ArgTypes.STRING, null, "");
/*     */   
/*     */   @Nonnull
/* 132 */   private final FlagArg childrenFlag = withFlagArg("children", "server.commands.prefab.load.children.desc");
/*     */   
/*     */   public PrefabLoadByNameCommand() {
/* 135 */     super("server.commands.prefab.load.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*     */     BiFunction<String, Random, BlockSelection> loader;
/*     */     Path foundPath, storePath;
/* 144 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 145 */     assert playerComponent != null;
/*     */     
/* 147 */     String storeType = (String)this.storeTypeArg.get(context);
/* 148 */     String storeName = (String)this.storeNameArg.get(context);
/* 149 */     String name = (String)this.nameArg.get(context);
/*     */     
/* 151 */     if (!name.endsWith(".prefab.json")) {
/* 152 */       name = name + ".prefab.json";
/*     */     }
/*     */     
/* 155 */     Path prefabStorePath = null;
/* 156 */     Path resolvedPrefabPath = null;
/* 157 */     String finalName = name;
/*     */     
/* 159 */     switch (storeType) {
/*     */       case "server":
/* 161 */         prefabStorePath = PrefabStore.get().getServerPrefabsPath();
/* 162 */         Objects.requireNonNull(PrefabStore.get());
/*     */ 
/*     */       
/*     */       case "asset":
/* 166 */         foundPath = PrefabStore.get().findAssetPrefabPath(finalName);
/* 167 */         if (foundPath != null) {
/* 168 */           resolvedPrefabPath = foundPath;
/* 169 */           prefabStorePath = foundPath.getParent();
/*     */         } 
/*     */ 
/*     */         
/* 173 */         prefabStorePath = PrefabStore.get().getAssetPrefabsPath();
/* 174 */         Objects.requireNonNull(PrefabStore.get());
/*     */       
/*     */       case "worldgen":
/* 177 */         storePath = PrefabStore.get().getWorldGenPrefabsPath(storeName);
/* 178 */         prefabStorePath = PrefabStore.get().getWorldGenPrefabsPath(storeName);
/*     */ 
/*     */       
/*     */       default:
/* 182 */         context.sendMessage(Message.translation("server.commands.prefab.invalidStoreType")
/* 183 */             .param("storeType", storeType)); break;
/* 184 */     }  Function<String, BlockSelection> prefabGetter = null;
/*     */ 
/*     */ 
/*     */     
/* 188 */     if (prefabGetter == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 193 */     Path finalPrefabStorePath = prefabStorePath;
/* 194 */     if (((Boolean)this.childrenFlag.get(context)).booleanValue()) {
/* 195 */       RecursivePrefabLoader.BlockSelectionLoader blockSelectionLoader = new RecursivePrefabLoader.BlockSelectionLoader(finalPrefabStorePath, prefabGetter);
/*     */     } else {
/* 197 */       loader = ((prefabFile, rand) -> (BlockSelection)prefabGetter.apply(prefabFile));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 202 */     boolean prefabExists = ((resolvedPrefabPath != null && Files.isRegularFile(resolvedPrefabPath, new java.nio.file.LinkOption[0])) || Files.isRegularFile(prefabStorePath.resolve(name), new java.nio.file.LinkOption[0]));
/*     */     
/* 204 */     if (prefabExists) {
/* 205 */       BuilderToolsPlugin.addToQueue(playerComponent, playerRef, (r, s, componentAccessor) -> s.load(finalName, loader.apply(finalName, s.getRandom()), componentAccessor));
/*     */     } else {
/*     */       
/* 208 */       context.sendMessage(Message.translation("server.builderTools.prefab.prefabNotFound")
/* 209 */           .param("name", name));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\commands\PrefabCommand$PrefabLoadByNameCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */