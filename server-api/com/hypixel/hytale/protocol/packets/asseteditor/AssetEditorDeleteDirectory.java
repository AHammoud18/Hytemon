/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorDeleteDirectory
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 308;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 32768024;
/*     */   public int token;
/*     */   @Nullable
/*     */   public AssetPath path;
/*     */   
/*     */   public int getId() {
/*  25 */     return 308;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorDeleteDirectory() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorDeleteDirectory(int token, @Nullable AssetPath path) {
/*  35 */     this.token = token;
/*  36 */     this.path = path;
/*     */   }
/*     */   
/*     */   public AssetEditorDeleteDirectory(@Nonnull AssetEditorDeleteDirectory other) {
/*  40 */     this.token = other.token;
/*  41 */     this.path = other.path;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorDeleteDirectory deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     AssetEditorDeleteDirectory obj = new AssetEditorDeleteDirectory();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.token = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { obj.path = AssetPath.deserialize(buf, pos);
/*  52 */       pos += AssetPath.computeBytesConsumed(buf, pos); }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 5;
/*  60 */     if ((nullBits & 0x1) != 0) pos += AssetPath.computeBytesConsumed(buf, pos); 
/*  61 */     return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  67 */     byte nullBits = 0;
/*  68 */     if (this.path != null) nullBits = (byte)(nullBits | 0x1); 
/*  69 */     buf.writeByte(nullBits);
/*     */     
/*  71 */     buf.writeIntLE(this.token);
/*     */     
/*  73 */     if (this.path != null) this.path.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  78 */     int size = 5;
/*  79 */     if (this.path != null) size += this.path.computeSize();
/*     */     
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 5) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/*  89 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  91 */     int pos = offset + 5;
/*     */     
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       ValidationResult pathResult = AssetPath.validateStructure(buffer, pos);
/*  95 */       if (!pathResult.isValid()) {
/*  96 */         return ValidationResult.error("Invalid Path: " + pathResult.error());
/*     */       }
/*  98 */       pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 100 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorDeleteDirectory clone() {
/* 104 */     AssetEditorDeleteDirectory copy = new AssetEditorDeleteDirectory();
/* 105 */     copy.token = this.token;
/* 106 */     copy.path = (this.path != null) ? this.path.clone() : null;
/* 107 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorDeleteDirectory other;
/* 113 */     if (this == obj) return true; 
/* 114 */     if (obj instanceof AssetEditorDeleteDirectory) { other = (AssetEditorDeleteDirectory)obj; } else { return false; }
/* 115 */      return (this.token == other.token && Objects.equals(this.path, other.path));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 120 */     return Objects.hash(new Object[] { Integer.valueOf(this.token), this.path });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorDeleteDirectory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */