/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class AssetEditorRebuildCaches
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 348;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 5;
/*     */   public boolean blockTextures;
/*     */   public boolean models;
/*     */   public boolean modelTextures;
/*     */   public boolean mapGeometry;
/*     */   public boolean itemIcons;
/*     */   
/*     */   public int getId() {
/*  25 */     return 348;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorRebuildCaches() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorRebuildCaches(boolean blockTextures, boolean models, boolean modelTextures, boolean mapGeometry, boolean itemIcons) {
/*  38 */     this.blockTextures = blockTextures;
/*  39 */     this.models = models;
/*  40 */     this.modelTextures = modelTextures;
/*  41 */     this.mapGeometry = mapGeometry;
/*  42 */     this.itemIcons = itemIcons;
/*     */   }
/*     */   
/*     */   public AssetEditorRebuildCaches(@Nonnull AssetEditorRebuildCaches other) {
/*  46 */     this.blockTextures = other.blockTextures;
/*  47 */     this.models = other.models;
/*  48 */     this.modelTextures = other.modelTextures;
/*  49 */     this.mapGeometry = other.mapGeometry;
/*  50 */     this.itemIcons = other.itemIcons;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorRebuildCaches deserialize(@Nonnull ByteBuf buf, int offset) {
/*  55 */     AssetEditorRebuildCaches obj = new AssetEditorRebuildCaches();
/*     */     
/*  57 */     obj.blockTextures = (buf.getByte(offset + 0) != 0);
/*  58 */     obj.models = (buf.getByte(offset + 1) != 0);
/*  59 */     obj.modelTextures = (buf.getByte(offset + 2) != 0);
/*  60 */     obj.mapGeometry = (buf.getByte(offset + 3) != 0);
/*  61 */     obj.itemIcons = (buf.getByte(offset + 4) != 0);
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
/*  74 */     buf.writeByte(this.blockTextures ? 1 : 0);
/*  75 */     buf.writeByte(this.models ? 1 : 0);
/*  76 */     buf.writeByte(this.modelTextures ? 1 : 0);
/*  77 */     buf.writeByte(this.mapGeometry ? 1 : 0);
/*  78 */     buf.writeByte(this.itemIcons ? 1 : 0);
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
/*     */   public AssetEditorRebuildCaches clone() {
/*  97 */     AssetEditorRebuildCaches copy = new AssetEditorRebuildCaches();
/*  98 */     copy.blockTextures = this.blockTextures;
/*  99 */     copy.models = this.models;
/* 100 */     copy.modelTextures = this.modelTextures;
/* 101 */     copy.mapGeometry = this.mapGeometry;
/* 102 */     copy.itemIcons = this.itemIcons;
/* 103 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorRebuildCaches other;
/* 109 */     if (this == obj) return true; 
/* 110 */     if (obj instanceof AssetEditorRebuildCaches) { other = (AssetEditorRebuildCaches)obj; } else { return false; }
/* 111 */      return (this.blockTextures == other.blockTextures && this.models == other.models && this.modelTextures == other.modelTextures && this.mapGeometry == other.mapGeometry && this.itemIcons == other.itemIcons);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return Objects.hash(new Object[] { Boolean.valueOf(this.blockTextures), Boolean.valueOf(this.models), Boolean.valueOf(this.modelTextures), Boolean.valueOf(this.mapGeometry), Boolean.valueOf(this.itemIcons) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorRebuildCaches.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */