/*     */ package com.hypixel.hytale.server.core.entity.entities.player.hud;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.CustomHud;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.HudComponent;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.ResetUserInterfaceState;
/*     */ import com.hypixel.hytale.protocol.packets.interface_.UpdateVisibleHudComponents;
/*     */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HudManager
/*     */ {
/*  21 */   private static final Set<HudComponent> DEFAULT_HUD_COMPONENTS = Set.of(new HudComponent[] { HudComponent.UtilitySlotSelector, HudComponent.BlockVariantSelector, HudComponent.StatusIcons, HudComponent.Hotbar, HudComponent.Chat, HudComponent.Notifications, HudComponent.KillFeed, HudComponent.InputBindings, HudComponent.Reticle, HudComponent.Compass, HudComponent.Speedometer, HudComponent.ObjectivePanel, HudComponent.PortalPanel, HudComponent.EventTitle, HudComponent.Stamina, HudComponent.AmmoIndicator, HudComponent.Health, HudComponent.Mana, HudComponent.Oxygen, HudComponent.BuilderToolsLegend, HudComponent.Sleep });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private final Set<HudComponent> visibleHudComponents = ConcurrentHashMap.newKeySet();
/*     */ 
/*     */   
/*  48 */   private final Set<HudComponent> unmodifiableVisibleHudComponents = Collections.unmodifiableSet(this.visibleHudComponents);
/*     */   
/*     */   @Nullable
/*     */   private CustomUIHud customHud;
/*     */   
/*     */   public HudManager() {
/*  54 */     this.visibleHudComponents.addAll(DEFAULT_HUD_COMPONENTS);
/*     */   }
/*     */   
/*     */   public HudManager(@Nonnull HudManager other) {
/*  58 */     this.customHud = other.customHud;
/*     */   }
/*     */   @Nullable
/*     */   public CustomUIHud getCustomHud() {
/*  62 */     return this.customHud;
/*     */   }
/*     */   @Nonnull
/*     */   public Set<HudComponent> getVisibleHudComponents() {
/*  66 */     return this.unmodifiableVisibleHudComponents;
/*     */   }
/*     */   
/*     */   public void setVisibleHudComponents(@Nonnull PlayerRef ref, HudComponent... hudComponents) {
/*  70 */     this.visibleHudComponents.clear();
/*  71 */     Collections.addAll(this.visibleHudComponents, hudComponents);
/*     */     
/*  73 */     sendVisibleHudComponents(ref.getPacketHandler());
/*     */   }
/*     */   
/*     */   public void setVisibleHudComponents(@Nonnull PlayerRef ref, @Nonnull Set<HudComponent> hudComponents) {
/*  77 */     this.visibleHudComponents.clear();
/*  78 */     this.visibleHudComponents.addAll(hudComponents);
/*     */     
/*  80 */     sendVisibleHudComponents(ref.getPacketHandler());
/*     */   }
/*     */   
/*     */   public void showHudComponents(@Nonnull PlayerRef ref, HudComponent... hudComponents) {
/*  84 */     Collections.addAll(this.visibleHudComponents, hudComponents);
/*     */     
/*  86 */     sendVisibleHudComponents(ref.getPacketHandler());
/*     */   }
/*     */   
/*     */   public void showHudComponents(@Nonnull PlayerRef ref, @Nonnull Set<HudComponent> hudComponents) {
/*  90 */     this.visibleHudComponents.addAll(hudComponents);
/*     */     
/*  92 */     sendVisibleHudComponents(ref.getPacketHandler());
/*     */   }
/*     */   
/*     */   public void hideHudComponents(@Nonnull PlayerRef ref, @Nonnull HudComponent... hudComponents) {
/*  96 */     for (HudComponent hudComponent : hudComponents) {
/*  97 */       this.visibleHudComponents.remove(hudComponent);
/*     */     }
/*     */     
/* 100 */     sendVisibleHudComponents(ref.getPacketHandler());
/*     */   }
/*     */   
/*     */   public void setCustomHud(@Nonnull PlayerRef ref, @Nullable CustomUIHud hud) {
/* 104 */     CustomUIHud oldHud = getCustomHud();
/* 105 */     if (oldHud == hud)
/*     */       return; 
/* 107 */     this.customHud = hud;
/*     */     
/* 109 */     if (hud == null) {
/* 110 */       ref.getPacketHandler().writeNoCache((Packet)new CustomHud(true, null));
/*     */     } else {
/* 112 */       hud.show();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resetHud(@Nonnull PlayerRef ref) {
/* 117 */     setVisibleHudComponents(ref, DEFAULT_HUD_COMPONENTS);
/* 118 */     setCustomHud(ref, null);
/*     */   }
/*     */   
/*     */   public void resetUserInterface(@Nonnull PlayerRef ref) {
/* 122 */     ref.getPacketHandler().writeNoCache((Packet)new ResetUserInterfaceState());
/*     */   }
/*     */   
/*     */   public void sendVisibleHudComponents(@Nonnull PacketHandler packetHandler) {
/* 126 */     packetHandler.writeNoCache((Packet)new UpdateVisibleHudComponents((HudComponent[])this.visibleHudComponents.toArray(x$0 -> new HudComponent[x$0])));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 132 */     return "HudManager{visibleHudComponents=" + String.valueOf(this.visibleHudComponents) + ", unmodifiableVisibleHudComponents=" + String.valueOf(this.unmodifiableVisibleHudComponents) + ", customHud=" + String.valueOf(this.customHud) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\entities\player\hud\HudManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */