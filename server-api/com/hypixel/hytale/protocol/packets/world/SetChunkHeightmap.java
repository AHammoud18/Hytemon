/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SetChunkHeightmap implements Packet {
/*     */   public static final int PACKET_ID = 132;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 4096014;
/*     */   public int x;
/*     */   public int z;
/*     */   @Nullable
/*     */   public byte[] heightmap;
/*     */   
/*     */   public int getId() {
/*  25 */     return 132;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SetChunkHeightmap() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SetChunkHeightmap(int x, int z, @Nullable byte[] heightmap) {
/*  36 */     this.x = x;
/*  37 */     this.z = z;
/*  38 */     this.heightmap = heightmap;
/*     */   }
/*     */   
/*     */   public SetChunkHeightmap(@Nonnull SetChunkHeightmap other) {
/*  42 */     this.x = other.x;
/*  43 */     this.z = other.z;
/*  44 */     this.heightmap = other.heightmap;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SetChunkHeightmap deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     SetChunkHeightmap obj = new SetChunkHeightmap();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     obj.x = buf.getIntLE(offset + 1);
/*  52 */     obj.z = buf.getIntLE(offset + 5);
/*     */     
/*  54 */     int pos = offset + 9;
/*  55 */     if ((nullBits & 0x1) != 0) { int heightmapCount = VarInt.peek(buf, pos);
/*  56 */       if (heightmapCount < 0) throw ProtocolException.negativeLength("Heightmap", heightmapCount); 
/*  57 */       if (heightmapCount > 4096000) throw ProtocolException.arrayTooLong("Heightmap", heightmapCount, 4096000); 
/*  58 */       int heightmapVarLen = VarInt.size(heightmapCount);
/*  59 */       if ((pos + heightmapVarLen) + heightmapCount * 1L > buf.readableBytes())
/*  60 */         throw ProtocolException.bufferTooSmall("Heightmap", pos + heightmapVarLen + heightmapCount * 1, buf.readableBytes()); 
/*  61 */       pos += heightmapVarLen;
/*  62 */       obj.heightmap = new byte[heightmapCount];
/*  63 */       for (int i = 0; i < heightmapCount; i++) {
/*  64 */         obj.heightmap[i] = buf.getByte(pos + i * 1);
/*     */       }
/*  66 */       pos += heightmapCount * 1; }
/*     */     
/*  68 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  72 */     byte nullBits = buf.getByte(offset);
/*  73 */     int pos = offset + 9;
/*  74 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  75 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  81 */     byte nullBits = 0;
/*  82 */     if (this.heightmap != null) nullBits = (byte)(nullBits | 0x1); 
/*  83 */     buf.writeByte(nullBits);
/*     */     
/*  85 */     buf.writeIntLE(this.x);
/*  86 */     buf.writeIntLE(this.z);
/*     */     
/*  88 */     if (this.heightmap != null) { if (this.heightmap.length > 4096000) throw ProtocolException.arrayTooLong("Heightmap", this.heightmap.length, 4096000);  VarInt.write(buf, this.heightmap.length); for (byte item : this.heightmap) buf.writeByte(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  93 */     int size = 9;
/*  94 */     if (this.heightmap != null) size += VarInt.size(this.heightmap.length) + this.heightmap.length * 1;
/*     */     
/*  96 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 100 */     if (buffer.readableBytes() - offset < 9) {
/* 101 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/* 104 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 106 */     int pos = offset + 9;
/*     */     
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int heightmapCount = VarInt.peek(buffer, pos);
/* 110 */       if (heightmapCount < 0) {
/* 111 */         return ValidationResult.error("Invalid array count for Heightmap");
/*     */       }
/* 113 */       if (heightmapCount > 4096000) {
/* 114 */         return ValidationResult.error("Heightmap exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       pos += heightmapCount * 1;
/* 118 */       if (pos > buffer.writerIndex()) {
/* 119 */         return ValidationResult.error("Buffer overflow reading Heightmap");
/*     */       }
/*     */     } 
/* 122 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SetChunkHeightmap clone() {
/* 126 */     SetChunkHeightmap copy = new SetChunkHeightmap();
/* 127 */     copy.x = this.x;
/* 128 */     copy.z = this.z;
/* 129 */     copy.heightmap = (this.heightmap != null) ? Arrays.copyOf(this.heightmap, this.heightmap.length) : null;
/* 130 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SetChunkHeightmap other;
/* 136 */     if (this == obj) return true; 
/* 137 */     if (obj instanceof SetChunkHeightmap) { other = (SetChunkHeightmap)obj; } else { return false; }
/* 138 */      return (this.x == other.x && this.z == other.z && Arrays.equals(this.heightmap, other.heightmap));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 143 */     int result = 1;
/* 144 */     result = 31 * result + Integer.hashCode(this.x);
/* 145 */     result = 31 * result + Integer.hashCode(this.z);
/* 146 */     result = 31 * result + Arrays.hashCode(this.heightmap);
/* 147 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SetChunkHeightmap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */