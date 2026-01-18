/*     */ package com.hypixel.hytale.builtin.crafting.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.crafting.CraftingPlugin;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.command.system.AbstractCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*     */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.awt.Color;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipeCommand
/*     */   extends AbstractCommandCollection
/*     */ {
/*     */   public RecipeCommand() {
/*  33 */     super("recipe", "server.commands.recipe.desc");
/*  34 */     addSubCommand((AbstractCommand)new Learn());
/*  35 */     addSubCommand((AbstractCommand)new Forget());
/*  36 */     addSubCommand((AbstractCommand)new List());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Learn
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/*  47 */     private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.learn.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Learn() {
/*  53 */       super("learn", "server.commands.recipe.learn.desc");
/*  54 */       addUsageVariant((AbstractCommand)new LearnOther());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  59 */       Item item = (Item)this.itemArg.get(context);
/*  60 */       String itemId = item.getId();
/*  61 */       Message itemMessage = Message.translation(item.getTranslationKey());
/*     */       
/*  63 */       if (CraftingPlugin.learnRecipe(ref, itemId, (ComponentAccessor)store)) {
/*  64 */         context.sendMessage(Message.translation("server.modules.learnrecipe.success")
/*  65 */             .param("name", itemMessage).color(Color.GREEN));
/*     */       } else {
/*  67 */         context.sendMessage(Message.translation("server.modules.learnrecipe.alreadyKnown")
/*  68 */             .param("name", itemMessage).color(Color.RED));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static class LearnOther
/*     */       extends CommandBase
/*     */     {
/*     */       @Nonnull
/*  80 */       private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.learn.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @Nonnull
/*  86 */       private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       LearnOther() {
/*  92 */         super("server.commands.recipe.learn.other.desc");
/*     */       }
/*     */       
/*     */       protected void executeSync(@Nonnull CommandContext context)
/*     */       {
/*  97 */         PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/*  98 */         Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */         
/* 100 */         if (ref == null || !ref.isValid()) {
/* 101 */           context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */           
/*     */           return;
/*     */         } 
/* 105 */         Store<EntityStore> store = ref.getStore();
/* 106 */         World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */         
/* 108 */         world.execute(() -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  Item item = (Item)this.itemArg.get(context); String itemId = item.getId(); Message itemMessage = Message.translation(item.getTranslationKey()); if (CraftingPlugin.learnRecipe(ref, itemId, (ComponentAccessor)store)) { context.sendMessage(Message.translation("server.commands.recipe.learn.success.other").param("username", targetPlayerRef.getUsername()).param("name", itemMessage).color(Color.GREEN)); } else { context.sendMessage(Message.translation("server.commands.recipe.learn.alreadyKnown.other").param("username", targetPlayerRef.getUsername()).param("name", itemMessage).color(Color.RED)); }  }); } } } private static class LearnOther extends CommandBase { protected void executeSync(@Nonnull CommandContext context) { PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context); Ref<EntityStore> ref = targetPlayerRef.getReference(); if (ref == null || !ref.isValid()) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  Store<EntityStore> store = ref.getStore(); World world = ((EntityStore)store.getExternalData()).getWorld(); world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */               return;
/*     */             } 
/*     */             Item item = (Item)this.itemArg.get(context);
/*     */             String itemId = item.getId();
/*     */             Message itemMessage = Message.translation(item.getTranslationKey());
/*     */             if (CraftingPlugin.learnRecipe(ref, itemId, (ComponentAccessor)store)) {
/*     */               context.sendMessage(Message.translation("server.commands.recipe.learn.success.other").param("username", targetPlayerRef.getUsername()).param("name", itemMessage).color(Color.GREEN));
/*     */             } else {
/*     */               context.sendMessage(Message.translation("server.commands.recipe.learn.alreadyKnown.other").param("username", targetPlayerRef.getUsername()).param("name", itemMessage).color(Color.RED));
/*     */             } 
/*     */           }); }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.learn.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */     
/*     */     @Nonnull
/*     */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */     
/*     */     LearnOther() {
/*     */       super("server.commands.recipe.learn.other.desc");
/*     */     } }
/*     */ 
/*     */   
/*     */   static class Forget
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     @Nonnull
/* 141 */     private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.forget.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Forget() {
/* 147 */       super("forget", "server.commands.recipe.forget.desc");
/* 148 */       addUsageVariant((AbstractCommand)new ForgetOther());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 153 */       Item item = (Item)this.itemArg.get(context);
/* 154 */       String itemId = item.getId();
/*     */       
/* 156 */       if (CraftingPlugin.forgetRecipe(ref, itemId, (ComponentAccessor)store)) {
/* 157 */         context.sendMessage(Message.translation("server.commands.recipe.forgotten")
/* 158 */             .param("id", itemId).color(Color.GREEN));
/*     */       } else {
/* 160 */         context.sendMessage(Message.translation("server.commands.recipe.alreadyNotKnown")
/* 161 */             .param("id", itemId).color(Color.RED));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static class ForgetOther
/*     */       extends CommandBase
/*     */     {
/*     */       @Nonnull
/* 173 */       private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.forget.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       @Nonnull
/* 179 */       private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       ForgetOther() {
/* 185 */         super("server.commands.recipe.forget.other.desc");
/*     */       }
/*     */       
/*     */       protected void executeSync(@Nonnull CommandContext context)
/*     */       {
/* 190 */         PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 191 */         Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */         
/* 193 */         if (ref == null || !ref.isValid()) {
/* 194 */           context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */           
/*     */           return;
/*     */         } 
/* 198 */         Store<EntityStore> store = ref.getStore();
/* 199 */         World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */         
/* 201 */         world.execute(() -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  Item item = (Item)this.itemArg.get(context); String itemId = item.getId(); if (CraftingPlugin.forgetRecipe(ref, itemId, (ComponentAccessor)store)) { context.sendMessage(Message.translation("server.commands.recipe.forgotten.other").param("username", targetPlayerRef.getUsername()).param("id", itemId).color(Color.GREEN)); } else { context.sendMessage(Message.translation("server.commands.recipe.alreadyNotKnown.other").param("username", targetPlayerRef.getUsername()).param("id", itemId).color(Color.RED)); }  }); } } } private static class ForgetOther extends CommandBase { protected void executeSync(@Nonnull CommandContext context) { PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context); Ref<EntityStore> ref = targetPlayerRef.getReference(); if (ref == null || !ref.isValid()) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  Store<EntityStore> store = ref.getStore(); World world = ((EntityStore)store.getExternalData()).getWorld(); world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */               return;
/*     */             } 
/*     */             Item item = (Item)this.itemArg.get(context);
/*     */             String itemId = item.getId();
/*     */             if (CraftingPlugin.forgetRecipe(ref, itemId, (ComponentAccessor)store)) {
/*     */               context.sendMessage(Message.translation("server.commands.recipe.forgotten.other").param("username", targetPlayerRef.getUsername()).param("id", itemId).color(Color.GREEN));
/*     */             } else {
/*     */               context.sendMessage(Message.translation("server.commands.recipe.alreadyNotKnown.other").param("username", targetPlayerRef.getUsername()).param("id", itemId).color(Color.RED));
/*     */             } 
/*     */           }); }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.forget.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */     
/*     */     @Nonnull
/*     */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */     
/*     */     ForgetOther() {
/*     */       super("server.commands.recipe.forget.other.desc");
/*     */     } }
/*     */ 
/*     */   
/*     */   static class List
/*     */     extends AbstractPlayerCommand
/*     */   {
/*     */     List() {
/* 233 */       super("list", "server.commands.recipe.list.desc");
/* 234 */       addUsageVariant((AbstractCommand)new ListOther());
/*     */     }
/*     */ 
/*     */     
/*     */     protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 239 */       Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/* 240 */       assert playerComponent != null;
/*     */       
/* 242 */       Set<String> knownRecipes = playerComponent.getPlayerConfigData().getKnownRecipes();
/* 243 */       context.sendMessage(Message.translation("server.commands.recipe.knownRecipes")
/* 244 */           .param("list", knownRecipes.toString()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static class ListOther
/*     */       extends CommandBase
/*     */     {
/*     */       @Nonnull
/* 255 */       private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       ListOther() {
/* 261 */         super("server.commands.recipe.list.other.desc");
/*     */       }
/*     */       
/*     */       protected void executeSync(@Nonnull CommandContext context)
/*     */       {
/* 266 */         PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 267 */         Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */         
/* 269 */         if (ref == null || !ref.isValid()) {
/* 270 */           context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */           
/*     */           return;
/*     */         } 
/* 274 */         Store<EntityStore> store = ref.getStore();
/* 275 */         World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */         
/* 277 */         world.execute(() -> { Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType()); if (playerComponent == null) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  Set<String> knownRecipes = playerComponent.getPlayerConfigData().getKnownRecipes(); context.sendMessage(Message.translation("server.commands.recipe.knownRecipes.other").param("username", targetPlayerRef.getUsername()).param("list", knownRecipes.toString())); }); } } } private static class ListOther extends CommandBase { @Nonnull private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF); protected void executeSync(@Nonnull CommandContext context) { PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context); Ref<EntityStore> ref = targetPlayerRef.getReference(); if (ref == null || !ref.isValid()) { context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld")); return; }  Store<EntityStore> store = ref.getStore(); World world = ((EntityStore)store.getExternalData()).getWorld(); world.execute(() -> {
/*     */             Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */             if (playerComponent == null) {
/*     */               context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */               return;
/*     */             } 
/*     */             Set<String> knownRecipes = playerComponent.getPlayerConfigData().getKnownRecipes();
/*     */             context.sendMessage(Message.translation("server.commands.recipe.knownRecipes.other").param("username", targetPlayerRef.getUsername()).param("list", knownRecipes.toString()));
/*     */           }); }
/*     */ 
/*     */     
/*     */     ListOther() {
/*     */       super("server.commands.recipe.list.other.desc");
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\commands\RecipeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */