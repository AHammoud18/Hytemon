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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Forget
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   @Nonnull
/* 141 */   private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.forget.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Forget() {
/* 147 */     super("forget", "server.commands.recipe.forget.desc");
/* 148 */     addUsageVariant((AbstractCommand)new ForgetOther());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 153 */     Item item = (Item)this.itemArg.get(context);
/* 154 */     String itemId = item.getId();
/*     */     
/* 156 */     if (CraftingPlugin.forgetRecipe(ref, itemId, (ComponentAccessor)store)) {
/* 157 */       context.sendMessage(Message.translation("server.commands.recipe.forgotten")
/* 158 */           .param("id", itemId).color(Color.GREEN));
/*     */     } else {
/* 160 */       context.sendMessage(Message.translation("server.commands.recipe.alreadyNotKnown")
/* 161 */           .param("id", itemId).color(Color.RED));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ForgetOther
/*     */     extends CommandBase
/*     */   {
/*     */     @Nonnull
/* 173 */     private final RequiredArg<Item> itemArg = withRequiredArg("item", "server.commands.recipe.forget.item.desc", (ArgumentType)ArgTypes.ITEM_ASSET);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/* 179 */     private final RequiredArg<PlayerRef> playerArg = withRequiredArg("player", "server.commands.argtype.player.desc", (ArgumentType)ArgTypes.PLAYER_REF);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ForgetOther() {
/* 185 */       super("server.commands.recipe.forget.other.desc");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void executeSync(@Nonnull CommandContext context) {
/* 190 */       PlayerRef targetPlayerRef = (PlayerRef)this.playerArg.get(context);
/* 191 */       Ref<EntityStore> ref = targetPlayerRef.getReference();
/*     */       
/* 193 */       if (ref == null || !ref.isValid()) {
/* 194 */         context.sendMessage(Message.translation("server.commands.errors.playerNotInWorld"));
/*     */         
/*     */         return;
/*     */       } 
/* 198 */       Store<EntityStore> store = ref.getStore();
/* 199 */       World world = ((EntityStore)store.getExternalData()).getWorld();
/*     */       
/* 201 */       world.execute(() -> {
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
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\crafting\commands\RecipeCommand$Forget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */