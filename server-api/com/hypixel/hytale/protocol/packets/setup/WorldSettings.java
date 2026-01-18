/*     */ package com.hypixel.hytale.protocol.packets.setup;
/*     */ import com.hypixel.hytale.protocol.Asset;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class WorldSettings implements Packet {
/*     */   public static final int PACKET_ID = 20;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 5;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 5;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   public int worldHeight;
/*     */   @Nullable
/*     */   public Asset[] requiredAssets;
/*     */   
/*     */   public int getId() {
/*  25 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings(int worldHeight, @Nullable Asset[] requiredAssets) {
/*  35 */     this.worldHeight = worldHeight;
/*  36 */     this.requiredAssets = requiredAssets;
/*     */   }
/*     */   
/*     */   public WorldSettings(@Nonnull WorldSettings other) {
/*  40 */     this.worldHeight = other.worldHeight;
/*  41 */     this.requiredAssets = other.requiredAssets;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static WorldSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     WorldSettings obj = new WorldSettings();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.worldHeight = buf.getIntLE(offset + 1);
/*     */     
/*  50 */     int pos = offset + 5;
/*  51 */     if ((nullBits & 0x1) != 0) { int requiredAssetsCount = VarInt.peek(buf, pos);
/*  52 */       if (requiredAssetsCount < 0) throw ProtocolException.negativeLength("RequiredAssets", requiredAssetsCount); 
/*  53 */       if (requiredAssetsCount > 4096000) throw ProtocolException.arrayTooLong("RequiredAssets", requiredAssetsCount, 4096000); 
/*  54 */       int requiredAssetsVarLen = VarInt.size(requiredAssetsCount);
/*  55 */       if ((pos + requiredAssetsVarLen) + requiredAssetsCount * 64L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("RequiredAssets", pos + requiredAssetsVarLen + requiredAssetsCount * 64, buf.readableBytes()); 
/*  57 */       pos += requiredAssetsVarLen;
/*  58 */       obj.requiredAssets = new Asset[requiredAssetsCount];
/*  59 */       for (int i = 0; i < requiredAssetsCount; i++) {
/*  60 */         obj.requiredAssets[i] = Asset.deserialize(buf, pos);
/*  61 */         pos += Asset.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 5;
/*  70 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  71 */       for (int i = 0; i < arrLen; ) { pos += Asset.computeBytesConsumed(buf, pos); i++; }  }
/*  72 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.requiredAssets != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     buf.writeByte(nullBits);
/*     */     
/*  82 */     buf.writeIntLE(this.worldHeight);
/*     */     
/*  84 */     if (this.requiredAssets != null) { if (this.requiredAssets.length > 4096000) throw ProtocolException.arrayTooLong("RequiredAssets", this.requiredAssets.length, 4096000);  VarInt.write(buf, this.requiredAssets.length); for (Asset item : this.requiredAssets) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  89 */     int size = 5;
/*  90 */     if (this.requiredAssets != null) {
/*  91 */       int requiredAssetsSize = 0;
/*  92 */       for (Asset elem : this.requiredAssets) requiredAssetsSize += elem.computeSize(); 
/*  93 */       size += VarInt.size(this.requiredAssets.length) + requiredAssetsSize;
/*     */     } 
/*     */     
/*  96 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 100 */     if (buffer.readableBytes() - offset < 5) {
/* 101 */       return ValidationResult.error("Buffer too small: expected at least 5 bytes");
/*     */     }
/*     */     
/* 104 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 106 */     int pos = offset + 5;
/*     */     
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int requiredAssetsCount = VarInt.peek(buffer, pos);
/* 110 */       if (requiredAssetsCount < 0) {
/* 111 */         return ValidationResult.error("Invalid array count for RequiredAssets");
/*     */       }
/* 113 */       if (requiredAssetsCount > 4096000) {
/* 114 */         return ValidationResult.error("RequiredAssets exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       for (int i = 0; i < requiredAssetsCount; i++) {
/* 118 */         ValidationResult structResult = Asset.validateStructure(buffer, pos);
/* 119 */         if (!structResult.isValid()) {
/* 120 */           return ValidationResult.error("Invalid Asset in RequiredAssets[" + i + "]: " + structResult.error());
/*     */         }
/* 122 */         pos += Asset.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 125 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public WorldSettings clone() {
/* 129 */     WorldSettings copy = new WorldSettings();
/* 130 */     copy.worldHeight = this.worldHeight;
/* 131 */     copy.requiredAssets = (this.requiredAssets != null) ? (Asset[])Arrays.<Asset>stream(this.requiredAssets).map(e -> e.clone()).toArray(x$0 -> new Asset[x$0]) : null;
/* 132 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     WorldSettings other;
/* 138 */     if (this == obj) return true; 
/* 139 */     if (obj instanceof WorldSettings) { other = (WorldSettings)obj; } else { return false; }
/* 140 */      return (this.worldHeight == other.worldHeight && Arrays.equals((Object[])this.requiredAssets, (Object[])other.requiredAssets));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 145 */     int result = 1;
/* 146 */     result = 31 * result + Integer.hashCode(this.worldHeight);
/* 147 */     result = 31 * result + Arrays.hashCode((Object[])this.requiredAssets);
/* 148 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\setup\WorldSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */