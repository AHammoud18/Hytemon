/*     */ package com.hypixel.hytale.protocol.packets.player;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.PickupLocation;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncPlayerPreferences
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 116;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 8;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 8;
/*     */   public static final int MAX_SIZE = 8;
/*     */   public boolean showEntityMarkers;
/*     */   
/*     */   public int getId() {
/*  25 */     return 116;
/*     */   }
/*     */   
/*     */   @Nonnull
/*  29 */   public PickupLocation armorItemsPreferredPickupLocation = PickupLocation.Hotbar; @Nonnull
/*  30 */   public PickupLocation weaponAndToolItemsPreferredPickupLocation = PickupLocation.Hotbar; @Nonnull
/*  31 */   public PickupLocation usableItemsItemsPreferredPickupLocation = PickupLocation.Hotbar; @Nonnull
/*  32 */   public PickupLocation solidBlockItemsPreferredPickupLocation = PickupLocation.Hotbar; @Nonnull
/*  33 */   public PickupLocation miscItemsPreferredPickupLocation = PickupLocation.Hotbar;
/*     */   
/*     */   public boolean allowNPCDetection;
/*     */   
/*     */   public boolean respondToHit;
/*     */ 
/*     */   
/*     */   public SyncPlayerPreferences(boolean showEntityMarkers, @Nonnull PickupLocation armorItemsPreferredPickupLocation, @Nonnull PickupLocation weaponAndToolItemsPreferredPickupLocation, @Nonnull PickupLocation usableItemsItemsPreferredPickupLocation, @Nonnull PickupLocation solidBlockItemsPreferredPickupLocation, @Nonnull PickupLocation miscItemsPreferredPickupLocation, boolean allowNPCDetection, boolean respondToHit) {
/*  41 */     this.showEntityMarkers = showEntityMarkers;
/*  42 */     this.armorItemsPreferredPickupLocation = armorItemsPreferredPickupLocation;
/*  43 */     this.weaponAndToolItemsPreferredPickupLocation = weaponAndToolItemsPreferredPickupLocation;
/*  44 */     this.usableItemsItemsPreferredPickupLocation = usableItemsItemsPreferredPickupLocation;
/*  45 */     this.solidBlockItemsPreferredPickupLocation = solidBlockItemsPreferredPickupLocation;
/*  46 */     this.miscItemsPreferredPickupLocation = miscItemsPreferredPickupLocation;
/*  47 */     this.allowNPCDetection = allowNPCDetection;
/*  48 */     this.respondToHit = respondToHit;
/*     */   }
/*     */   
/*     */   public SyncPlayerPreferences(@Nonnull SyncPlayerPreferences other) {
/*  52 */     this.showEntityMarkers = other.showEntityMarkers;
/*  53 */     this.armorItemsPreferredPickupLocation = other.armorItemsPreferredPickupLocation;
/*  54 */     this.weaponAndToolItemsPreferredPickupLocation = other.weaponAndToolItemsPreferredPickupLocation;
/*  55 */     this.usableItemsItemsPreferredPickupLocation = other.usableItemsItemsPreferredPickupLocation;
/*  56 */     this.solidBlockItemsPreferredPickupLocation = other.solidBlockItemsPreferredPickupLocation;
/*  57 */     this.miscItemsPreferredPickupLocation = other.miscItemsPreferredPickupLocation;
/*  58 */     this.allowNPCDetection = other.allowNPCDetection;
/*  59 */     this.respondToHit = other.respondToHit;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SyncPlayerPreferences deserialize(@Nonnull ByteBuf buf, int offset) {
/*  64 */     SyncPlayerPreferences obj = new SyncPlayerPreferences();
/*     */     
/*  66 */     obj.showEntityMarkers = (buf.getByte(offset + 0) != 0);
/*  67 */     obj.armorItemsPreferredPickupLocation = PickupLocation.fromValue(buf.getByte(offset + 1));
/*  68 */     obj.weaponAndToolItemsPreferredPickupLocation = PickupLocation.fromValue(buf.getByte(offset + 2));
/*  69 */     obj.usableItemsItemsPreferredPickupLocation = PickupLocation.fromValue(buf.getByte(offset + 3));
/*  70 */     obj.solidBlockItemsPreferredPickupLocation = PickupLocation.fromValue(buf.getByte(offset + 4));
/*  71 */     obj.miscItemsPreferredPickupLocation = PickupLocation.fromValue(buf.getByte(offset + 5));
/*  72 */     obj.allowNPCDetection = (buf.getByte(offset + 6) != 0);
/*  73 */     obj.respondToHit = (buf.getByte(offset + 7) != 0);
/*     */ 
/*     */     
/*  76 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  80 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  86 */     buf.writeByte(this.showEntityMarkers ? 1 : 0);
/*  87 */     buf.writeByte(this.armorItemsPreferredPickupLocation.getValue());
/*  88 */     buf.writeByte(this.weaponAndToolItemsPreferredPickupLocation.getValue());
/*  89 */     buf.writeByte(this.usableItemsItemsPreferredPickupLocation.getValue());
/*  90 */     buf.writeByte(this.solidBlockItemsPreferredPickupLocation.getValue());
/*  91 */     buf.writeByte(this.miscItemsPreferredPickupLocation.getValue());
/*  92 */     buf.writeByte(this.allowNPCDetection ? 1 : 0);
/*  93 */     buf.writeByte(this.respondToHit ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  99 */     return 8;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 103 */     if (buffer.readableBytes() - offset < 8) {
/* 104 */       return ValidationResult.error("Buffer too small: expected at least 8 bytes");
/*     */     }
/*     */ 
/*     */     
/* 108 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SyncPlayerPreferences clone() {
/* 112 */     SyncPlayerPreferences copy = new SyncPlayerPreferences();
/* 113 */     copy.showEntityMarkers = this.showEntityMarkers;
/* 114 */     copy.armorItemsPreferredPickupLocation = this.armorItemsPreferredPickupLocation;
/* 115 */     copy.weaponAndToolItemsPreferredPickupLocation = this.weaponAndToolItemsPreferredPickupLocation;
/* 116 */     copy.usableItemsItemsPreferredPickupLocation = this.usableItemsItemsPreferredPickupLocation;
/* 117 */     copy.solidBlockItemsPreferredPickupLocation = this.solidBlockItemsPreferredPickupLocation;
/* 118 */     copy.miscItemsPreferredPickupLocation = this.miscItemsPreferredPickupLocation;
/* 119 */     copy.allowNPCDetection = this.allowNPCDetection;
/* 120 */     copy.respondToHit = this.respondToHit;
/* 121 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SyncPlayerPreferences other;
/* 127 */     if (this == obj) return true; 
/* 128 */     if (obj instanceof SyncPlayerPreferences) { other = (SyncPlayerPreferences)obj; } else { return false; }
/* 129 */      return (this.showEntityMarkers == other.showEntityMarkers && Objects.equals(this.armorItemsPreferredPickupLocation, other.armorItemsPreferredPickupLocation) && Objects.equals(this.weaponAndToolItemsPreferredPickupLocation, other.weaponAndToolItemsPreferredPickupLocation) && Objects.equals(this.usableItemsItemsPreferredPickupLocation, other.usableItemsItemsPreferredPickupLocation) && Objects.equals(this.solidBlockItemsPreferredPickupLocation, other.solidBlockItemsPreferredPickupLocation) && Objects.equals(this.miscItemsPreferredPickupLocation, other.miscItemsPreferredPickupLocation) && this.allowNPCDetection == other.allowNPCDetection && this.respondToHit == other.respondToHit);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 134 */     return Objects.hash(new Object[] { Boolean.valueOf(this.showEntityMarkers), this.armorItemsPreferredPickupLocation, this.weaponAndToolItemsPreferredPickupLocation, this.usableItemsItemsPreferredPickupLocation, this.solidBlockItemsPreferredPickupLocation, this.miscItemsPreferredPickupLocation, Boolean.valueOf(this.allowNPCDetection), Boolean.valueOf(this.respondToHit) });
/*     */   }
/*     */   
/*     */   public SyncPlayerPreferences() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\player\SyncPlayerPreferences.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */