/*     */ package com.hypixel.hytale.server.core.util;
/*     */ 
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.ItemWithAllMetadata;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.Notification;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.NotificationStyle;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.Universe;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
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
/*     */ public class NotificationUtil
/*     */ {
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nullable Message secondaryMessage, @Nullable String icon, @Nullable ItemWithAllMetadata item, @Nonnull NotificationStyle style) {
/*  35 */     for (Iterator<World> iterator = Universe.get().getWorlds().values().iterator(); iterator.hasNext(); ) { World world = iterator.next();
/*  36 */       world.execute(() -> {
/*     */             Store<EntityStore> store = world.getEntityStore().getStore();
/*     */             sendNotificationToWorld(message, secondaryMessage, icon, item, style, store);
/*     */           }); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull String message) {
/*  49 */     sendNotificationToUniverse(Message.raw(message), null, null, null, NotificationStyle.Default);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull String message, @Nonnull String secondaryMessage) {
/*  59 */     sendNotificationToUniverse(Message.raw(message), Message.raw(secondaryMessage), null, null, NotificationStyle.Default);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull String message, @Nonnull NotificationStyle style) {
/*  69 */     sendNotificationToUniverse(Message.raw(message), null, null, null, style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message) {
/*  78 */     sendNotificationToUniverse(message, null, null, null, NotificationStyle.Default);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nonnull NotificationStyle style) {
/*  88 */     sendNotificationToUniverse(message, null, null, null, style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nullable String icon, @Nonnull NotificationStyle style) {
/*  99 */     sendNotificationToUniverse(message, null, icon, null, style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nullable ItemWithAllMetadata item, @Nonnull NotificationStyle style) {
/* 110 */     sendNotificationToUniverse(message, null, null, item, style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nullable Message secondaryMessage, @Nullable String icon) {
/* 121 */     sendNotificationToUniverse(message, secondaryMessage, icon, null, NotificationStyle.Default);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nullable Message secondaryMessage, @Nullable ItemWithAllMetadata item) {
/* 132 */     sendNotificationToUniverse(message, secondaryMessage, null, item, NotificationStyle.Default);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nullable Message secondaryMessage, @Nonnull NotificationStyle style) {
/* 143 */     sendNotificationToUniverse(message, secondaryMessage, null, null, style);
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
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nullable Message secondaryMessage, @Nullable String icon, @Nonnull NotificationStyle style) {
/* 155 */     sendNotificationToUniverse(message, secondaryMessage, icon, null, style);
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
/*     */   public static void sendNotificationToUniverse(@Nonnull Message message, @Nullable Message secondaryMessage, @Nullable ItemWithAllMetadata item, @Nonnull NotificationStyle style) {
/* 167 */     sendNotificationToUniverse(message, secondaryMessage, null, item, style);
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
/*     */   public static void sendNotificationToWorld(@Nonnull Message message, @Nullable Message secondaryMessage, @Nullable String icon, @Nullable ItemWithAllMetadata item, @Nonnull NotificationStyle style, @Nonnull Store<EntityStore> store) {
/* 186 */     World world = ((EntityStore)store.getExternalData()).getWorld();
/* 187 */     for (PlayerRef playerRefComponent : world.getPlayerRefs()) {
/* 188 */       sendNotification(playerRefComponent.getPacketHandler(), message, secondaryMessage, icon, item, style);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, @Nonnull Message message, @Nullable Message secondaryMessage, @Nullable String icon, @Nullable ItemWithAllMetadata item, @Nonnull NotificationStyle style) {
/* 208 */     Objects.requireNonNull(message, "Notification message can't be null!");
/* 209 */     Objects.requireNonNull(style, "Notification style can't be null!");
/* 210 */     handler.writeNoCache((Packet)new Notification(message.getFormattedMessage(), (secondaryMessage != null) ? secondaryMessage.getFormattedMessage() : null, icon, item, style));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, @Nonnull String message) {
/* 221 */     sendNotification(handler, Message.raw(message), null, null, null, NotificationStyle.Default);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, @Nonnull Message message, @Nonnull String icon) {
/* 234 */     sendNotification(handler, message, null, icon, null, NotificationStyle.Default);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, @Nonnull Message message, @Nonnull String icon, @Nonnull NotificationStyle notificationStyle) {
/* 249 */     sendNotification(handler, message, null, icon, null, notificationStyle);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, @Nonnull String message, @Nonnull NotificationStyle style) {
/* 262 */     sendNotification(handler, Message.raw(message), null, null, null, style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, @Nonnull Message message) {
/* 273 */     sendNotification(handler, message, null, null, null, NotificationStyle.Default);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, Message message, NotificationStyle style) {
/* 285 */     sendNotification(handler, message, null, null, null, style);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, @Nonnull String message, @Nonnull String secondaryMessage) {
/* 298 */     sendNotification(handler, Message.raw(message), Message.raw(secondaryMessage), null, null, NotificationStyle.Default);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, Message message, Message secondaryMessage, String icon) {
/* 313 */     sendNotification(handler, message, secondaryMessage, icon, null, NotificationStyle.Default);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, Message message, Message secondaryMessage) {
/* 326 */     sendNotification(handler, message, secondaryMessage, null, null, NotificationStyle.Default);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, Message message, Message secondaryMessage, ItemWithAllMetadata item) {
/* 341 */     sendNotification(handler, message, secondaryMessage, null, item, NotificationStyle.Default);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, Message message, Message secondaryMessage, @Nonnull NotificationStyle style) {
/* 356 */     sendNotification(handler, message, secondaryMessage, null, null, style);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, Message message, Message secondaryMessage, String icon, @Nonnull NotificationStyle style) {
/* 372 */     sendNotification(handler, message, secondaryMessage, icon, null, style);
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
/*     */   public static void sendNotification(@Nonnull PacketHandler handler, Message message, Message secondaryMessage, ItemWithAllMetadata item, @Nonnull NotificationStyle style) {
/* 388 */     sendNotification(handler, message, secondaryMessage, null, item, style);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\NotificationUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */