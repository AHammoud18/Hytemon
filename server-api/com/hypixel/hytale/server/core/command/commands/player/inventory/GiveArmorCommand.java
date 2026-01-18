/*     */ package com.hypixel.hytale.server.core.command.commands.player.inventory;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.NameMatching;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractAsyncCommand;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.ItemStack;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.awt.Color;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class GiveArmorCommand extends AbstractAsyncCommand {
/*     */   private static final String PREFIX = "Armor_";
/*     */   @Nonnull
/*  38 */   private static final Message MESSAGE_COMMANDS_GIVEARMOR_SUCCESS = Message.translation("server.commands.givearmor.success");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  45 */   private final OptionalArg<String> playerArg = withOptionalArg("player", "server.commands.givearmor.player.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  51 */   private final RequiredArg<String> searchStringArg = withRequiredArg("search", "server.commands.givearmor.search.desc", (ArgumentType)ArgTypes.STRING);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  57 */   private final FlagArg setFlag = withFlagArg("set", "server.commands.givearmor.set.desc");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GiveArmorCommand() {
/*  63 */     super("armor", "server.commands.givearmor.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected CompletableFuture<Void> executeAsync(@Nonnull CommandContext context) {
/*     */     Collection<Ref<EntityStore>> targets;
/*  71 */     if (this.playerArg.provided(context)) {
/*  72 */       String playerInput = (String)this.playerArg.get(context);
/*  73 */       if ("*".equals(playerInput)) {
/*  74 */         ObjectArrayList<Ref> objectArrayList = new ObjectArrayList();
/*  75 */         for (PlayerRef player : Universe.get().getPlayers()) {
/*  76 */           objectArrayList.add(player.getReference());
/*     */         }
/*     */       } else {
/*  79 */         PlayerRef player = Universe.get().getPlayer(playerInput, NameMatching.DEFAULT);
/*  80 */         if (player == null) {
/*  81 */           context.sendMessage(Message.translation("server.commands.errors.noSuchPlayer")
/*  82 */               .param("username", playerInput));
/*  83 */           return CompletableFuture.completedFuture(null);
/*     */         } 
/*  85 */         targets = Collections.singletonList(player.getReference());
/*     */       } 
/*     */     } else {
/*  88 */       if (!context.isPlayer()) {
/*  89 */         context.sendMessage(Message.translation("server.commands.errors.playerOrArg").param("option", "player"));
/*  90 */         return CompletableFuture.completedFuture(null);
/*     */       } 
/*  92 */       targets = Collections.singletonList(context.senderAsPlayerRef());
/*     */     } 
/*     */     
/*  95 */     if (targets.isEmpty()) {
/*  96 */       context.sendMessage(Message.translation("server.commands.errors.noSuchPlayer")
/*  97 */           .param("username", "*"));
/*  98 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     String searchString = (String)this.searchStringArg.get(context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     List<ItemStack> armor = (List<ItemStack>)Item.getAssetMap().getAssetMap().keySet().stream().filter(blockTypeKey -> (blockTypeKey.startsWith("Armor_") && blockTypeKey.indexOf(searchString, "Armor_".length()) == "Armor_".length())).map(ItemStack::new).collect(Collectors.toList());
/*     */     
/* 110 */     if (armor.isEmpty()) {
/* 111 */       context.sendMessage(Message.translation("server.commands.givearmor.typeNotFound")
/* 112 */           .param("type", searchString).color(Color.RED));
/* 113 */       return CompletableFuture.completedFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 117 */     Object2ObjectOpenHashMap<World, List<Ref<EntityStore>>> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 118 */     for (Ref<EntityStore> targetRef : targets) {
/* 119 */       if (targetRef == null || !targetRef.isValid()) {
/*     */         continue;
/*     */       }
/* 122 */       Store<EntityStore> store = targetRef.getStore();
/* 123 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/* 124 */       ((List<Ref<EntityStore>>)object2ObjectOpenHashMap.computeIfAbsent(world, k -> new ObjectArrayList())).add(targetRef);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     ObjectArrayList<CompletableFuture<Void>> futures = new ObjectArrayList();
/* 129 */     boolean shouldClear = this.setFlag.provided(context);
/*     */     
/* 131 */     for (Map.Entry<World, List<Ref<EntityStore>>> entry : object2ObjectOpenHashMap.entrySet()) {
/* 132 */       World world = entry.getKey();
/* 133 */       List<Ref<EntityStore>> worldPlayers = entry.getValue();
/*     */       
/* 135 */       CompletableFuture<Void> future = runAsync(context, () -> { for (Ref<EntityStore> playerRef : (Iterable<Ref<EntityStore>>)worldPlayers) { if (playerRef == null || !playerRef.isValid()) continue;  Store<EntityStore> store = playerRef.getStore(); Player targetPlayerComponent = (Player)store.getComponent(playerRef, Player.getComponentType()); if (targetPlayerComponent == null) continue;  ItemContainer armorInventory = targetPlayerComponent.getInventory().getArmor(); if (shouldClear) armorInventory.clear();  armorInventory.addItemStacks(armor); }  }(Executor)world);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       futures.add(future);
/*     */     } 
/*     */ 
/*     */     
/* 159 */     return CompletableFuture.allOf((CompletableFuture<?>[])futures.toArray((Object[])new CompletableFuture[0]))
/* 160 */       .thenRun(() -> context.sendMessage(MESSAGE_COMMANDS_GIVEARMOR_SUCCESS));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\player\inventory\GiveArmorCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */