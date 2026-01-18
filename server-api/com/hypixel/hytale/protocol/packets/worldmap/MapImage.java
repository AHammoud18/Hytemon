/*     */ package com.hypixel.hytale.protocol.packets.worldmap;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapImage
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 16384014;
/*     */   public int width;
/*     */   public int height;
/*     */   @Nullable
/*     */   public int[] data;
/*     */   
/*     */   public MapImage() {}
/*     */   
/*     */   public MapImage(int width, int height, @Nullable int[] data) {
/*  28 */     this.width = width;
/*  29 */     this.height = height;
/*  30 */     this.data = data;
/*     */   }
/*     */   
/*     */   public MapImage(@Nonnull MapImage other) {
/*  34 */     this.width = other.width;
/*  35 */     this.height = other.height;
/*  36 */     this.data = other.data;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static MapImage deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     MapImage obj = new MapImage();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.width = buf.getIntLE(offset + 1);
/*  44 */     obj.height = buf.getIntLE(offset + 5);
/*     */     
/*  46 */     int pos = offset + 9;
/*  47 */     if ((nullBits & 0x1) != 0) { int dataCount = VarInt.peek(buf, pos);
/*  48 */       if (dataCount < 0) throw ProtocolException.negativeLength("Data", dataCount); 
/*  49 */       if (dataCount > 4096000) throw ProtocolException.arrayTooLong("Data", dataCount, 4096000); 
/*  50 */       int dataVarLen = VarInt.size(dataCount);
/*  51 */       if ((pos + dataVarLen) + dataCount * 4L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("Data", pos + dataVarLen + dataCount * 4, buf.readableBytes()); 
/*  53 */       pos += dataVarLen;
/*  54 */       obj.data = new int[dataCount];
/*  55 */       for (int i = 0; i < dataCount; i++) {
/*  56 */         obj.data[i] = buf.getIntLE(pos + i * 4);
/*     */       }
/*  58 */       pos += dataCount * 4; }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 9;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 4; }
/*  67 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  72 */     byte nullBits = 0;
/*  73 */     if (this.data != null) nullBits = (byte)(nullBits | 0x1); 
/*  74 */     buf.writeByte(nullBits);
/*     */     
/*  76 */     buf.writeIntLE(this.width);
/*  77 */     buf.writeIntLE(this.height);
/*     */     
/*  79 */     if (this.data != null) { if (this.data.length > 4096000) throw ProtocolException.arrayTooLong("Data", this.data.length, 4096000);  VarInt.write(buf, this.data.length); for (int item : this.data) buf.writeIntLE(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 9;
/*  85 */     if (this.data != null) size += VarInt.size(this.data.length) + this.data.length * 4;
/*     */     
/*  87 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  91 */     if (buffer.readableBytes() - offset < 9) {
/*  92 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  95 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  97 */     int pos = offset + 9;
/*     */     
/*  99 */     if ((nullBits & 0x1) != 0) {
/* 100 */       int dataCount = VarInt.peek(buffer, pos);
/* 101 */       if (dataCount < 0) {
/* 102 */         return ValidationResult.error("Invalid array count for Data");
/*     */       }
/* 104 */       if (dataCount > 4096000) {
/* 105 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 107 */       pos += VarInt.length(buffer, pos);
/* 108 */       pos += dataCount * 4;
/* 109 */       if (pos > buffer.writerIndex()) {
/* 110 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/* 113 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public MapImage clone() {
/* 117 */     MapImage copy = new MapImage();
/* 118 */     copy.width = this.width;
/* 119 */     copy.height = this.height;
/* 120 */     copy.data = (this.data != null) ? Arrays.copyOf(this.data, this.data.length) : null;
/* 121 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     MapImage other;
/* 127 */     if (this == obj) return true; 
/* 128 */     if (obj instanceof MapImage) { other = (MapImage)obj; } else { return false; }
/* 129 */      return (this.width == other.width && this.height == other.height && Arrays.equals(this.data, other.data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 134 */     int result = 1;
/* 135 */     result = 31 * result + Integer.hashCode(this.width);
/* 136 */     result = 31 * result + Integer.hashCode(this.height);
/* 137 */     result = 31 * result + Arrays.hashCode(this.data);
/* 138 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\worldmap\MapImage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */