/*     */ package com.hypixel.hytale.server.core.util;
/*     */ 
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.HideEventTitle;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.ShowEventTitle;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventTitleUtil
/*     */ {
/*     */   public static final String DEFAULT_ZONE = "Void";
/*     */   public static final float DEFAULT_DURATION = 4.0F;
/*     */   public static final float DEFAULT_FADE_DURATION = 1.5F;
/*     */   
/*     */   public static void showEventTitleToUniverse(@Nonnull Message primaryTitle, @Nonnull Message secondaryTitle, boolean isMajor, String icon, float duration, float fadeInDuration, float fadeOutDuration) {
/*  52 */     for (Iterator<World> iterator = Universe.get().getWorlds().values().iterator(); iterator.hasNext(); ) { World world = iterator.next();
/*  53 */       world.execute(() -> {
/*     */             Store<EntityStore> store = world.getEntityStore().getStore();
/*     */             showEventTitleToWorld(primaryTitle, secondaryTitle, isMajor, icon, duration, fadeInDuration, fadeOutDuration, store);
/*     */           }); }
/*     */   
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showEventTitleToWorld(@Nonnull Message primaryTitle, @Nonnull Message secondaryTitle, boolean isMajor, String icon, float duration, float fadeInDuration, float fadeOutDuration, @Nonnull Store<EntityStore> store) {
/*  80 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  81 */     for (PlayerRef playerRef : world.getPlayerRefs()) {
/*  82 */       showEventTitleToPlayer(playerRef, primaryTitle, secondaryTitle, isMajor, icon, duration, fadeInDuration, fadeOutDuration);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void hideEventTitleFromWorld(float fadeOutDuration, @Nonnull Store<EntityStore> store) {
/*  93 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/*  94 */     for (PlayerRef playerRef : world.getPlayerRefs()) {
/*  95 */       hideEventTitleFromPlayer(playerRef, fadeOutDuration);
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showEventTitleToPlayer(@Nonnull PlayerRef playerRefComponent, @Nonnull Message primaryTitle, @Nonnull Message secondaryTitle, boolean isMajor, @Nullable String icon, float duration, float fadeInDuration, float fadeOutDuration) {
/* 119 */     playerRefComponent.getPacketHandler()
/* 120 */       .writeNoCache((Packet)new ShowEventTitle(fadeInDuration, fadeOutDuration, duration, icon, isMajor, primaryTitle.getFormattedMessage(), secondaryTitle.getFormattedMessage()));
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
/*     */ 
/*     */   
/*     */   public static void showEventTitleToPlayer(@Nonnull PlayerRef playerRefComponent, @Nonnull Message primaryTitle, @Nonnull Message secondaryTitle, boolean isMajor) {
/* 135 */     showEventTitleToPlayer(playerRefComponent, primaryTitle, secondaryTitle, isMajor, null, 4.0F, 1.5F, 1.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void hideEventTitleFromPlayer(@Nonnull PlayerRef playerRefComponent, float fadeOutDuration) {
/* 146 */     playerRefComponent.getPacketHandler().writeNoCache((Packet)new HideEventTitle(fadeOutDuration));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\EventTitleUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */