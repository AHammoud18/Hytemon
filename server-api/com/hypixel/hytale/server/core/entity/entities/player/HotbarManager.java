/*     */ package com.hypixel.hytale.server.core.entity.entities.player;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
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
/*     */ public class HotbarManager
/*     */ {
/*     */   public static final int HOTBARS_MAX = 10;
/*     */   @Nonnull
/*     */   public static final BuilderCodec<HotbarManager> CODEC;
/*     */   
/*     */   static {
/*  47 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(HotbarManager.class, HotbarManager::new).append(new KeyedCodec("SavedHotbars", (Codec)new ArrayCodec((Codec)ItemContainer.CODEC, x$0 -> new ItemContainer[x$0])), (player, savedHotbars) -> player.savedHotbars = savedHotbars, player -> player.savedHotbars).documentation("An array of item containers that represent the saved hotbars.").add()).append(new KeyedCodec("CurrentHotbar", (Codec)Codec.INTEGER), (player, currentHotbar) -> player.currentHotbar = currentHotbar.intValue(), player -> Integer.valueOf(player.currentHotbar)).documentation("The current hotbar that the player has active.").add()).build();
/*     */   }
/*  49 */   private static final Message MESSAGE_GENERAL_HOTBAR_INVALID_SLOT = Message.translation("server.general.hotbar.invalidSlot");
/*  50 */   private static final Message MESSAGE_GENERAL_HOTBAR_INVALID_GAME_MODE = Message.translation("server.general.hotbar.invalidGameMode");
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  55 */   private ItemContainer[] savedHotbars = new ItemContainer[10];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private int currentHotbar = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean currentlyLoadingHotbar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveHotbar(@Nonnull Ref<EntityStore> playerRef, short hotbarIndex, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  78 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, PlayerRef.getComponentType());
/*  79 */     assert playerRefComponent != null;
/*     */     
/*  81 */     if (hotbarIndex < 0 || hotbarIndex > 9) {
/*  82 */       playerRefComponent.sendMessage(MESSAGE_GENERAL_HOTBAR_INVALID_SLOT);
/*     */       
/*     */       return;
/*     */     } 
/*  86 */     Player playerComponent = (Player)componentAccessor.getComponent(playerRef, Player.getComponentType());
/*  87 */     assert playerComponent != null;
/*     */     
/*  89 */     if (!playerComponent.getGameMode().equals(GameMode.Creative)) {
/*  90 */       playerRefComponent.sendMessage(MESSAGE_GENERAL_HOTBAR_INVALID_GAME_MODE);
/*     */       
/*     */       return;
/*     */     } 
/*  94 */     this.currentlyLoadingHotbar = true;
/*     */     
/*  96 */     this.savedHotbars[hotbarIndex] = playerComponent.getInventory().getHotbar().clone();
/*  97 */     this.currentHotbar = hotbarIndex;
/*     */     
/*  99 */     this.currentlyLoadingHotbar = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadHotbar(@Nonnull Ref<EntityStore> playerRef, short hotbarIndex, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 112 */     PlayerRef playerRefComponent = (PlayerRef)componentAccessor.getComponent(playerRef, PlayerRef.getComponentType());
/* 113 */     assert playerRefComponent != null;
/*     */     
/* 115 */     if (hotbarIndex < 0 || hotbarIndex > 9) {
/* 116 */       playerRefComponent.sendMessage(MESSAGE_GENERAL_HOTBAR_INVALID_SLOT);
/*     */       
/*     */       return;
/*     */     } 
/* 120 */     Player playerComponent = (Player)componentAccessor.getComponent(playerRef, Player.getComponentType());
/* 121 */     assert playerComponent != null;
/*     */     
/* 123 */     if (!playerComponent.getGameMode().equals(GameMode.Creative)) {
/* 124 */       playerRefComponent.sendMessage(MESSAGE_GENERAL_HOTBAR_INVALID_GAME_MODE);
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     this.currentlyLoadingHotbar = true;
/*     */     
/* 130 */     ItemContainer hotbar = playerComponent.getInventory().getHotbar();
/* 131 */     hotbar.removeAllItemStacks();
/*     */     
/* 133 */     if (this.savedHotbars[hotbarIndex] != null) {
/* 134 */       ItemContainer savedHotbar = this.savedHotbars[hotbarIndex].clone();
/* 135 */       Objects.requireNonNull(hotbar); savedHotbar.forEach(hotbar::setItemStackForSlot);
/*     */     } 
/*     */     
/* 138 */     this.currentHotbar = hotbarIndex;
/*     */     
/* 140 */     this.currentlyLoadingHotbar = false;
/* 141 */     playerRefComponent.sendMessage(Message.translation("server.general.hotbar.loaded").param("id", hotbarIndex + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHotbarIndex() {
/* 148 */     return this.currentHotbar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsCurrentlyLoadingHotbar() {
/* 155 */     return this.currentlyLoadingHotbar;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\HotbarManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */