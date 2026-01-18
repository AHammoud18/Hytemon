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
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.awt.Color;
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
/*     */ class Learn
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/*  47 */   private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.learn.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Learn() {
/*  53 */     super("learn", "server.commands.recipe.learn.desc");
/*  54 */     addUsageVariant((AbstractCommand)new LearnOther());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/*  59 */     Item item = (Item)this.itemArg.get(context);
/*  60 */     String itemId = item.getId();
/*  61 */     Message itemMessage = Message.translation(item.getTranslationKey());
/*     */     
/*  63 */     if (CraftingPlugin.learnRecipe(ref, itemId, (ComponentAccessor)store)) {
/*  64 */       context.sendMessage(Message.translation("server.modules.learnrecipe.success")
/*  65 */           .param("name", itemMessage).color(Color.GREEN));
/*     */     } else {
/*  67 */       context.sendMessage(Message.translation("server.modules.learnrecipe.alreadyKnown")
/*  68 */           .param("name", itemMessage).color(Color.RED));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LearnOther
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/*  80 */     private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.learn.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*  86 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LearnOther() {
/*  92 */       super("server.commands.recipe.learn.other.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/*  97 */       PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/*  98 */       Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */       
/* 100 */       if (ref == null || !ref.isValid()) {
/* 101 */         context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */         
/*     */         return;
/*     */       } 
/* 105 */       Store<EntityStore> store = ref.getStore();
/* 106 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/* 108 */       world.execute(() -> {
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
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\commands\RecipeCommand$Learn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */