/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class AssetEditorCapabilities
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 304;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 5;
/*     */   public boolean canDiscardAssets;
/*     */   public boolean canEditAssets;
/*     */   public boolean canCreateAssetPacks;
/*     */   public boolean canEditAssetPacks;
/*     */   public boolean canDeleteAssetPacks;
/*     */   
/*     */   public int getId() {
/*  25 */     return 304;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorCapabilities() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorCapabilities(boolean canDiscardAssets, boolean canEditAssets, boolean canCreateAssetPacks, boolean canEditAssetPacks, boolean canDeleteAssetPacks) {
/*  38 */     this.canDiscardAssets = canDiscardAssets;
/*  39 */     this.canEditAssets = canEditAssets;
/*  40 */     this.canCreateAssetPacks = canCreateAssetPacks;
/*  41 */     this.canEditAssetPacks = canEditAssetPacks;
/*  42 */     this.canDeleteAssetPacks = canDeleteAssetPacks;
/*     */   }
/*     */   
/*     */   public AssetEditorCapabilities(@Nonnull AssetEditorCapabilities other) {
/*  46 */     this.canDiscardAssets = other.canDiscardAssets;
/*  47 */     this.canEditAssets = other.canEditAssets;
/*  48 */     this.canCreateAssetPacks = other.canCreateAssetPacks;
/*  49 */     this.canEditAssetPacks = other.canEditAssetPacks;
/*  50 */     this.canDeleteAssetPacks = other.canDeleteAssetPacks;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorCapabilities deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     AssetEditorCapabilities obj = new AssetEditorCapabilities();
/*     */     
/*  57 */     obj.canDiscardAssets = (buf.getByte(offset + 0) != 0);
/*  58 */     obj.canEditAssets = (buf.getByte(offset + 1) != 0);
/*  59 */     obj.canCreateAssetPacks = (buf.getByte(offset + 2) != 0);
/*  60 */     obj.canEditAssetPacks = (buf.getByte(offset + 3) != 0);
/*  61 */     obj.canDeleteAssetPacks = (buf.getByte(offset + 4) != 0);
/*     */ 
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     buf.writeByte(this.canDiscardAssets ? 1 : 0);
/*  75 */     buf.writeByte(this.canEditAssets ? 1 : 0);
/*  76 */     buf.writeByte(this.canCreateAssetPacks ? 1 : 0);
/*  77 */     buf.writeByte(this.canEditAssetPacks ? 1 : 0);
/*  78 */     buf.writeByte(this.canDeleteAssetPacks ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  84 */     return 5;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  88 */     if (buffer.readableBytes() - offset < 5) {
/*  89 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */ 
/*     */     
/*  93 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorCapabilities clone() {
/*  97 */     AssetEditorCapabilities copy = new AssetEditorCapabilities();
/*  98 */     copy.canDiscardAssets = this.canDiscardAssets;
/*  99 */     copy.canEditAssets = this.canEditAssets;
/* 100 */     copy.canCreateAssetPacks = this.canCreateAssetPacks;
/* 101 */     copy.canEditAssetPacks = this.canEditAssetPacks;
/* 102 */     copy.canDeleteAssetPacks = this.canDeleteAssetPacks;
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorCapabilities other;
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj instanceof AssetEditorCapabilities) { other = (AssetEditorCapabilities)obj; } else { return false; }
/* 111 */      return (this.canDiscardAssets == other.canDiscardAssets && this.canEditAssets == other.canEditAssets && this.canCreateAssetPacks == other.canCreateAssetPacks && this.canEditAssetPacks == other.canEditAssetPacks && this.canDeleteAssetPacks == other.canDeleteAssetPacks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return Objects.hash(new Object[] { Boolean.valueOf(this.canDiscardAssets), Boolean.valueOf(this.canEditAssets), Boolean.valueOf(this.canCreateAssetPacks), Boolean.valueOf(this.canEditAssetPacks), Boolean.valueOf(this.canDeleteAssetPacks) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorCapabilities.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */