/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class AssetEditorFileEntry
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 2;
/*     */   public static final int MAX_SIZE = 16384007;
/*     */   @Nullable
/*     */   public String path;
/*     */   public boolean isDirectory;
/*     */   
/*     */   public AssetEditorFileEntry() {}
/*     */   
/*     */   public AssetEditorFileEntry(@Nullable String path, boolean isDirectory) {
/*  27 */     this.path = path;
/*  28 */     this.isDirectory = isDirectory;
/*     */   }
/*     */   
/*     */   public AssetEditorFileEntry(@Nonnull AssetEditorFileEntry other) {
/*  32 */     this.path = other.path;
/*  33 */     this.isDirectory = other.isDirectory;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorFileEntry deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     AssetEditorFileEntry obj = new AssetEditorFileEntry();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.isDirectory = (buf.getByte(offset + 1) != 0);
/*     */     
/*  42 */     int pos = offset + 2;
/*  43 */     if ((nullBits & 0x1) != 0) { int pathLen = VarInt.peek(buf, pos);
/*  44 */       if (pathLen < 0) throw ProtocolException.negativeLength("Path", pathLen); 
/*  45 */       if (pathLen > 4096000) throw ProtocolException.stringTooLong("Path", pathLen, 4096000); 
/*  46 */       int pathVarLen = VarInt.length(buf, pos);
/*  47 */       obj.path = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  48 */       pos += pathVarLen + pathLen; }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 2;
/*  56 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  57 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeByte(this.isDirectory ? 1 : 0);
/*     */     
/*  68 */     if (this.path != null) PacketIO.writeVarString(buf, this.path, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 2;
/*  74 */     if (this.path != null) size += PacketIO.stringSize(this.path);
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 2) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 2 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 2;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       int pathLen = VarInt.peek(buffer, pos);
/*  90 */       if (pathLen < 0) {
/*  91 */         return ValidationResult.error("Invalid string length for Path");
/*     */       }
/*  93 */       if (pathLen > 4096000) {
/*  94 */         return ValidationResult.error("Path exceeds max length 4096000");
/*     */       }
/*  96 */       pos += VarInt.length(buffer, pos);
/*  97 */       pos += pathLen;
/*  98 */       if (pos > buffer.writerIndex()) {
/*  99 */         return ValidationResult.error("Buffer overflow reading Path");
/*     */       }
/*     */     } 
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorFileEntry clone() {
/* 106 */     AssetEditorFileEntry copy = new AssetEditorFileEntry();
/* 107 */     copy.path = this.path;
/* 108 */     copy.isDirectory = this.isDirectory;
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorFileEntry other;
/* 115 */     if (this == obj) return true; 
/* 116 */     if (obj instanceof AssetEditorFileEntry) { other = (AssetEditorFileEntry)obj; } else { return false; }
/* 117 */      return (Objects.equals(this.path, other.path) && this.isDirectory == other.isDirectory);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 122 */     return Objects.hash(new Object[] { this.path, Boolean.valueOf(this.isDirectory) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorFileEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */