/*     */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AssetEditorExportAssets
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 342;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public AssetPath[] paths;
/*     */   
/*     */   public int getId() {
/*  25 */     return 342;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AssetEditorExportAssets() {}
/*     */ 
/*     */   
/*     */   public AssetEditorExportAssets(@Nullable AssetPath[] paths) {
/*  34 */     this.paths = paths;
/*     */   }
/*     */   
/*     */   public AssetEditorExportAssets(@Nonnull AssetEditorExportAssets other) {
/*  38 */     this.paths = other.paths;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AssetEditorExportAssets deserialize(@Nonnull ByteBuf buf, int offset) {
/*  43 */     AssetEditorExportAssets obj = new AssetEditorExportAssets();
/*  44 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  46 */     int pos = offset + 1;
/*  47 */     if ((nullBits & 0x1) != 0) { int pathsCount = VarInt.peek(buf, pos);
/*  48 */       if (pathsCount < 0) throw ProtocolException.negativeLength("Paths", pathsCount); 
/*  49 */       if (pathsCount > 4096000) throw ProtocolException.arrayTooLong("Paths", pathsCount, 4096000); 
/*  50 */       int pathsVarLen = VarInt.size(pathsCount);
/*  51 */       if ((pos + pathsVarLen) + pathsCount * 1L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Paths", pos + pathsVarLen + pathsCount * 1, buf.readableBytes()); 
/*  53 */       pos += pathsVarLen;
/*  54 */       obj.paths = new AssetPath[pathsCount];
/*  55 */       for (int i = 0; i < pathsCount; i++) {
/*  56 */         obj.paths[i] = AssetPath.deserialize(buf, pos);
/*  57 */         pos += AssetPath.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 1;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  67 */       for (int i = 0; i < arrLen; ) { pos += AssetPath.computeBytesConsumed(buf, pos); i++; }  }
/*  68 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  74 */     byte nullBits = 0;
/*  75 */     if (this.paths != null) nullBits = (byte)(nullBits | 0x1); 
/*  76 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  79 */     if (this.paths != null) { if (this.paths.length > 4096000) throw ProtocolException.arrayTooLong("Paths", this.paths.length, 4096000);  VarInt.write(buf, this.paths.length); for (AssetPath item : this.paths) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 1;
/*  85 */     if (this.paths != null) {
/*  86 */       int pathsSize = 0;
/*  87 */       for (AssetPath elem : this.paths) pathsSize += elem.computeSize(); 
/*  88 */       size += VarInt.size(this.paths.length) + pathsSize;
/*     */     } 
/*     */     
/*  91 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  95 */     if (buffer.readableBytes() - offset < 1) {
/*  96 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  99 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 101 */     int pos = offset + 1;
/*     */     
/* 103 */     if ((nullBits & 0x1) != 0) {
/* 104 */       int pathsCount = VarInt.peek(buffer, pos);
/* 105 */       if (pathsCount < 0) {
/* 106 */         return ValidationResult.error("Invalid array count for Paths");
/*     */       }
/* 108 */       if (pathsCount > 4096000) {
/* 109 */         return ValidationResult.error("Paths exceeds max length 4096000");
/*     */       }
/* 111 */       pos += VarInt.length(buffer, pos);
/* 112 */       for (int i = 0; i < pathsCount; i++) {
/* 113 */         ValidationResult structResult = AssetPath.validateStructure(buffer, pos);
/* 114 */         if (!structResult.isValid()) {
/* 115 */           return ValidationResult.error("Invalid AssetPath in Paths[" + i + "]: " + structResult.error());
/*     */         }
/* 117 */         pos += AssetPath.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 120 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AssetEditorExportAssets clone() {
/* 124 */     AssetEditorExportAssets copy = new AssetEditorExportAssets();
/* 125 */     copy.paths = (this.paths != null) ? (AssetPath[])Arrays.<AssetPath>stream(this.paths).map(e -> e.clone()).toArray(x$0 -> new AssetPath[x$0]) : null;
/* 126 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AssetEditorExportAssets other;
/* 132 */     if (this == obj) return true; 
/* 133 */     if (obj instanceof AssetEditorExportAssets) { other = (AssetEditorExportAssets)obj; } else { return false; }
/* 134 */      return Arrays.equals((Object[])this.paths, (Object[])other.paths);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     int result = 1;
/* 140 */     result = 31 * result + Arrays.hashCode((Object[])this.paths);
/* 141 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorExportAssets.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */