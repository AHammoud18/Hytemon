/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class AssetEditorSelectAsset
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 336;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 32768020;
/*     */   @Nullable
/*     */   public AssetPath path;
/*     */   
/*     */   public int getId() {
/*  25 */     return 336;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorSelectAsset() {}
/*     */ 
/*     */   
/*     */   public AssetEditorSelectAsset(@Nullable AssetPath path) {
/*  34 */     this.path = path;
/*     */   }
/*     */   
/*     */   public AssetEditorSelectAsset(@Nonnull AssetEditorSelectAsset other) {
/*  38 */     this.path = other.path;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorSelectAsset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorSelectAsset obj = new AssetEditorSelectAsset();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { obj.path = AssetPath.deserialize(buf, pos);
/*  48 */       pos += AssetPath.computeBytesConsumed(buf, pos); }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 1;
/*  56 */     if ((nullBits & 0x1) != 0) pos += AssetPath.computeBytesConsumed(buf, pos); 
/*  57 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  63 */     byte nullBits = 0;
/*  64 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/*  65 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  68 */     if (this.path != null) this.path.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 1;
/*  74 */     if (this.path != null) size += this.path.computeSize();
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 1) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 1;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/*  90 */       if (!pathResult.isValid()) {
/*  91 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/*  93 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/*  95 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorSelectAsset clone() {
/*  99 */     AssetEditorSelectAsset copy = new AssetEditorSelectAsset();
/* 100 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 101 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorSelectAsset other;
/* 107 */     if (this == obj) return true; 
/* 108 */     if (obj instanceof AssetEditorSelectAsset) { other = (AssetEditorSelectAsset)obj; } else { return false; }
/* 109 */      return Objects.equals(this.path, other.path);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     return Objects.hash(new Object[] { this.path });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorSelectAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */