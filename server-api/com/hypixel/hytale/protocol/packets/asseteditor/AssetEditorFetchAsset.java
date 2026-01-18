/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorFetchAsset
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 310;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 6;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 6;
/*     */   public static final int MAX_SIZE = 32768025;
/*     */   public int token;
/*     */   @Nullable
/*     */   public AssetPath path;
/*     */   public boolean isFromOpenedTab;
/*     */   
/*     */   public int getId() {
/*  25 */     return 310;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorFetchAsset() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorFetchAsset(int token, @Nullable AssetPath path, boolean isFromOpenedTab) {
/*  36 */     this.token = token;
/*  37 */     this.path = path;
/*  38 */     this.isFromOpenedTab = isFromOpenedTab;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchAsset(@Nonnull AssetEditorFetchAsset other) {
/*  42 */     this.token = other.token;
/*  43 */     this.path = other.path;
/*  44 */     this.isFromOpenedTab = other.isFromOpenedTab;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorFetchAsset deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     AssetEditorFetchAsset obj = new AssetEditorFetchAsset();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     obj.token = buf.getIntLE(offset + 1);
/*  52 */     obj.isFromOpenedTab = (buf.getByte(offset + 5) != 0);
/*     */     
/*  54 */     int pos = offset + 6;
/*  55 */     if ((nullBits & 0x1) != 0) { obj.path = AssetPath.deserialize(buf, pos);
/*  56 */       pos += AssetPath.computeBytesConsumed(buf, pos); }
/*     */     
/*  58 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  62 */     byte nullBits = buf.getByte(offset);
/*  63 */     int pos = offset + 6;
/*  64 */     if ((nullBits & 0x1) != 0) pos += AssetPath.computeBytesConsumed(buf, pos); 
/*  65 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  71 */     byte nullBits = 0;
/*  72 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/*  73 */     buf.writeByte(nullBits);
/*     */     
/*  75 */     buf.writeIntLE(this.token);
/*  76 */     buf.writeByte(this.isFromOpenedTab ? 1 : 0);
/*     */     
/*  78 */     if (this.path != null) this.path.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  83 */     int size = 6;
/*  84 */     if (this.path != null) size += this.path.computeSize();
/*     */     
/*  86 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  90 */     if (buffer.readableBytes() - offset < 6) {
/*  91 */       return ValidationResult.error("Buffer too small: expected at least 6 bytes");
/*     */     }
/*     */     
/*  94 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  96 */     int pos = offset + 6;
/*     */     
/*  98 */     if ((nullBits & 0x1) != 0) {
/*  99 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/* 100 */       if (!pathResult.isValid()) {
/* 101 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/* 103 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 105 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorFetchAsset clone() {
/* 109 */     AssetEditorFetchAsset copy = new AssetEditorFetchAsset();
/* 110 */     copy.token = this.token;
/* 111 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 112 */     copy.isFromOpenedTab = this.isFromOpenedTab;
/* 113 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorFetchAsset other;
/* 119 */     if (this == obj) return true; 
/* 120 */     if (obj instanceof AssetEditorFetchAsset) { other = (AssetEditorFetchAsset)obj; } else { return false; }
/* 121 */      return (this.token == other.token && Objects.equals(this.path, other.path) && this.isFromOpenedTab == other.isFromOpenedTab);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 126 */     return Objects.hash(new Object[] { Integer.valueOf(this.token), this.path, Boolean.valueOf(this.isFromOpenedTab) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorFetchAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */