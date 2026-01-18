/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorEnableAssetPack implements Packet {
/*     */   public static final int PACKET_ID = 318;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 16384007;
/*     */   @Nullable
/*     */   public String id;
/*     */   public boolean enabled;
/*     */   
/*     */   public int getId() {
/*  25 */     return 318;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorEnableAssetPack() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorEnableAssetPack(@Nullable String id, boolean enabled) {
/*  35 */     this.id = id;
/*  36 */     this.enabled = enabled;
/*     */   }
/*     */   
/*     */   public AssetEditorEnableAssetPack(@Nonnull AssetEditorEnableAssetPack other) {
/*  40 */     this.id = other.id;
/*  41 */     this.enabled = other.enabled;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorEnableAssetPack deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorEnableAssetPack obj = new AssetEditorEnableAssetPack();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.enabled = (buf.getByte(offset + 1) != 0);
/*     */     
/*  50 */     int pos = offset + 2;
/*  51 */     if ((nullBits & 0x1) != 0) { int idLen = VarInt.peek(buf, pos);
/*  52 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  53 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  54 */       int idVarLen = VarInt.length(buf, pos);
/*  55 */       obj.id = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  56 */       pos += idVarLen + idLen; }
/*     */     
/*  58 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  62 */     byte nullBits = buf.getByte(offset);
/*  63 */     int pos = offset + 2;
/*  64 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  65 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  71 */     byte nullBits = 0;
/*  72 */     if (this.id != null) nullBits = (byte)(nullBits | 0x1); 
/*  73 */     buf.writeByte(nullBits);
/*     */     
/*  75 */     buf.writeByte(this.enabled ? 1 : 0);
/*     */     
/*  77 */     if (this.id != null) PacketIO.writeVarString(buf, this.id, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  82 */     int size = 2;
/*  83 */     if (this.id != null) size += PacketIO.stringSize(this.id);
/*     */     
/*  85 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  89 */     if (buffer.readableBytes() - offset < 2) {
/*  90 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/*  93 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  95 */     int pos = offset + 2;
/*     */     
/*  97 */     if ((nullBits & 0x1) != 0) {
/*  98 */       int idLen = VarInt.peek(buffer, pos);
/*  99 */       if (idLen < 0) {
/* 100 */         return ValidationResult.error("Invalid string length for Id");
/*     */       }
/* 102 */       if (idLen > 4096000) {
/* 103 */         return ValidationResult.error("Id exceeds max length 4096000");
/*     */       }
/* 105 */       pos += VarInt.length(buffer, pos);
/* 106 */       pos += idLen;
/* 107 */       if (pos > buffer.writerIndex()) {
/* 108 */         return ValidationResult.error("Buffer overflow reading Id");
/*     */       }
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorEnableAssetPack clone() {
/* 115 */     AssetEditorEnableAssetPack copy = new AssetEditorEnableAssetPack();
/* 116 */     copy.id = this.id;
/* 117 */     copy.enabled = this.enabled;
/* 118 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorEnableAssetPack other;
/* 124 */     if (this == obj) return true; 
/* 125 */     if (obj instanceof AssetEditorEnableAssetPack) { other = (AssetEditorEnableAssetPack)obj; } else { return false; }
/* 126 */      return (Objects.equals(this.id, other.id) && this.enabled == other.enabled);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 131 */     return Objects.hash(new Object[] { this.id, Boolean.valueOf(this.enabled) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorEnableAssetPack.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */