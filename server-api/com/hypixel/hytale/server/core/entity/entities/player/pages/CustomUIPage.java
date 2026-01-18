/*     */ package com.hypixel.hytale.server.core.entity.entities.player.pages;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomPage;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*     */ import com.hypixel.hytale.server.core.entity.entities.Player;
/*     */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*     */ import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CustomUIPage
/*     */ {
/*     */   @Nonnull
/*     */   protected final PlayerRef playerRef;
/*     */   @Nonnull
/*     */   protected CustomPageLifetime lifetime;
/*     */   
/*     */   public CustomUIPage(@Nonnull PlayerRef playerRef, @Nonnull CustomPageLifetime lifetime) {
/*  40 */     this.playerRef = playerRef;
/*  41 */     this.lifetime = lifetime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLifetime(@Nonnull CustomPageLifetime lifetime) {
/*  50 */     this.lifetime = lifetime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CustomPageLifetime getLifetime() {
/*  58 */     return this.lifetime;
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
/*     */   public void handleDataEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, String rawData) {
/*  70 */     throw new UnsupportedOperationException("CustomUIPage doesn't support events! " + String.valueOf(this) + ": " + rawData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void build(@Nonnull Ref<EntityStore> paramRef, @Nonnull UICommandBuilder paramUICommandBuilder, @Nonnull UIEventBuilder paramUIEventBuilder, @Nonnull Store<EntityStore> paramStore);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rebuild() {
/*  84 */     Ref<EntityStore> ref = this.playerRef.getReference();
/*  85 */     if (ref == null)
/*     */       return; 
/*  87 */     Store<EntityStore> store = ref.getStore();
/*  88 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */     
/*  90 */     UICommandBuilder commandBuilder = new UICommandBuilder();
/*  91 */     UIEventBuilder eventBuilder = new UIEventBuilder();
/*  92 */     build(ref, commandBuilder, eventBuilder, ref.getStore());
/*     */     
/*  94 */     playerComponent.getPageManager().updateCustomPage(new CustomPage(
/*  95 */           getClass().getName(), false, true, this.lifetime, commandBuilder
/*  96 */           .getCommands(), eventBuilder
/*  97 */           .getEvents()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendUpdate() {
/* 105 */     sendUpdate(null, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendUpdate(@Nullable UICommandBuilder commandBuilder) {
/* 114 */     sendUpdate(commandBuilder, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendUpdate(@Nullable UICommandBuilder commandBuilder, boolean clear) {
/* 124 */     Ref<EntityStore> ref = this.playerRef.getReference();
/* 125 */     if (ref == null)
/*     */       return; 
/* 127 */     Store<EntityStore> store = ref.getStore();
/* 128 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */     
/* 130 */     playerComponent.getPageManager().updateCustomPage(new CustomPage(
/* 131 */           getClass().getName(), false, clear, this.lifetime, 
/* 132 */           (commandBuilder != null) ? commandBuilder.getCommands() : UICommandBuilder.EMPTY_COMMAND_ARRAY, UIEventBuilder.EMPTY_EVENT_BINDING_ARRAY));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void close() {
/* 138 */     Ref<EntityStore> ref = this.playerRef.getReference();
/* 139 */     if (ref == null)
/*     */       return; 
/* 141 */     Store<EntityStore> store = ref.getStore();
/* 142 */     Player playerComponent = (Player)store.getComponent(ref, Player.getComponentType());
/*     */     
/* 144 */     playerComponent.getPageManager().setPage(ref, store, Page.None);
/*     */   }
/*     */   
/*     */   public void onDismiss(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\CustomUIPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */