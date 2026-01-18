/*     */ package com.hypixel.hytale.server.core.entity.entities.player.pages;
/*     */ 
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomPage;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomPageEvent;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomPageEventType;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.Page;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.SetPage;
/*     */ import com.hypixel.hytale.protocol.packets.window.OpenWindow;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.Window;
/*     */ import com.hypixel.hytale.server.core.entity.entities.player.windows.WindowManager;
/*     */ import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
/*     */ import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class PageManager
/*     */ {
/*     */   @Nullable
/*     */   private WindowManager windowManager;
/*     */   private PlayerRef playerRef;
/*     */   @Nullable
/*     */   private CustomUIPage customPage;
/*     */   @Nonnull
/*  45 */   private final AtomicInteger customPageRequiredAcknowledgments = new AtomicInteger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(@Nonnull PlayerRef playerRef, @Nonnull WindowManager windowManager) {
/*  55 */     this.windowManager = windowManager;
/*  56 */     this.playerRef = playerRef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCustomPageAcknowledgements() {
/*  63 */     this.customPageRequiredAcknowledgments.set(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CustomUIPage getCustomPage() {
/*  71 */     return this.customPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPage(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Page page) {
/*  82 */     setPage(ref, store, page, false);
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
/*     */   public void setPage(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Page page, boolean canCloseThroughInteraction) {
/*  94 */     if (this.customPage != null) {
/*  95 */       this.customPage.onDismiss(ref, store);
/*  96 */       this.customPage = null;
/*  97 */       this.customPageRequiredAcknowledgments.incrementAndGet();
/*     */     } 
/*     */     
/* 100 */     this.playerRef.getPacketHandler().writeNoCache((Packet)new SetPage(page, canCloseThroughInteraction));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openCustomPage(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull CustomUIPage page) {
/* 111 */     UICommandBuilder commandBuilder = new UICommandBuilder();
/* 112 */     UIEventBuilder eventBuilder = new UIEventBuilder();
/*     */     
/* 114 */     if (this.customPage != null) {
/* 115 */       this.customPage.onDismiss(ref, ref.getStore());
/*     */     }
/*     */     
/* 118 */     page.build(ref, commandBuilder, eventBuilder, store);
/* 119 */     updateCustomPage(new CustomPage(page.getClass().getName(), true, true, page.getLifetime(), commandBuilder.getCommands(), eventBuilder.getEvents()));
/* 120 */     this.customPage = page;
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
/*     */   public boolean setPageWithWindows(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull Page page, boolean canCloseThroughInteraction, @Nonnull Window... windows) {
/* 134 */     if (this.windowManager == null) return false;
/*     */     
/* 136 */     List<OpenWindow> windowPackets = this.windowManager.openWindows(windows);
/* 137 */     if (windowPackets == null) return false;
/*     */     
/* 139 */     setPage(ref, store, page, canCloseThroughInteraction);
/* 140 */     for (OpenWindow packet : windowPackets) {
/* 141 */       this.playerRef.getPacketHandler().write((Packet)packet);
/*     */     }
/*     */     
/* 144 */     return true;
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
/*     */   public boolean openCustomPageWithWindows(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull CustomUIPage page, @Nonnull Window... windows) {
/* 157 */     if (this.windowManager == null) return false;
/*     */     
/* 159 */     List<OpenWindow> windowPackets = this.windowManager.openWindows(windows);
/* 160 */     if (windowPackets == null) return false;
/*     */     
/* 162 */     openCustomPage(ref, store, page);
/* 163 */     for (OpenWindow packet : windowPackets) {
/* 164 */       this.playerRef.getPacketHandler().write((Packet)packet);
/*     */     }
/*     */     
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCustomPage(@Nonnull CustomPage page) {
/* 176 */     this.customPageRequiredAcknowledgments.incrementAndGet();
/* 177 */     this.playerRef.getPacketHandler().write((Packet)page);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleEvent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store, @Nonnull CustomPageEvent event) {
/* 188 */     switch (event.type) {
/*     */       case Dismiss:
/* 190 */         if (this.customPage == null)
/* 191 */           return;  this.customPage.onDismiss(ref, store);
/* 192 */         this.customPage = null;
/*     */         break;
/*     */       
/*     */       case Data:
/* 196 */         if (this.customPageRequiredAcknowledgments.get() != 0 || this.customPage == null)
/* 197 */           return;  this.customPage.handleDataEvent(ref, store, event.data);
/*     */         break;
/*     */       
/*     */       case Acknowledge:
/* 201 */         if (this.customPageRequiredAcknowledgments.decrementAndGet() < 0) {
/* 202 */           this.customPageRequiredAcknowledgments.incrementAndGet();
/* 203 */           throw new IllegalArgumentException("Client sent unexpected acknowledgement");
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\pages\PageManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */