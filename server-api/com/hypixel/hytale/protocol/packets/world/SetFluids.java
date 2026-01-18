/*     */ package com.hypixel.hytale.protocol.packets.world;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SetFluids
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 136;
/*     */   public static final boolean IS_COMPRESSED = true;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   
/*     */   public int getId() {
/*  25 */     return 136;
/*     */   }
/*     */   public static final int VARIABLE_BLOCK_START = 13; public static final int MAX_SIZE = 4096018;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   @Nullable
/*     */   public byte[] data;
/*     */   
/*     */   public SetFluids() {}
/*     */   
/*     */   public SetFluids(int x, int y, int z, @Nullable byte[] data) {
/*  37 */     this.x = x;
/*  38 */     this.y = y;
/*  39 */     this.z = z;
/*  40 */     this.data = data;
/*     */   }
/*     */   
/*     */   public SetFluids(@Nonnull SetFluids other) {
/*  44 */     this.x = other.x;
/*  45 */     this.y = other.y;
/*  46 */     this.z = other.z;
/*  47 */     this.data = other.data;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SetFluids deserialize(@Nonnull ByteBuf buf, int offset) {
/*  52 */     SetFluids obj = new SetFluids();
/*  53 */     byte nullBits = buf.getByte(offset);
/*  54 */     obj.x = buf.getIntLE(offset + 1);
/*  55 */     obj.y = buf.getIntLE(offset + 5);
/*  56 */     obj.z = buf.getIntLE(offset + 9);
/*     */     
/*  58 */     int pos = offset + 13;
/*  59 */     if ((nullBits & 0x1) != 0) { int dataCount = VarInt.peek(buf, pos);
/*  60 */       if (dataCount < 0) throw ProtocolException.negativeLength("Data", dataCount); 
/*  61 */       if (dataCount > 4096000) throw ProtocolException.arrayTooLong("Data", dataCount, 4096000); 
/*  62 */       int dataVarLen = VarInt.size(dataCount);
/*  63 */       if ((pos + dataVarLen) + dataCount * 1L > buf.readableBytes())
/*  64 */         throw ProtocolException.bufferTooSmall("Data", pos + dataVarLen + dataCount * 1, buf.readableBytes()); 
/*  65 */       pos += dataVarLen;
/*  66 */       obj.data = new byte[dataCount];
/*  67 */       for (int i = 0; i < dataCount; i++) {
/*  68 */         obj.data[i] = buf.getByte(pos + i * 1);
/*     */       }
/*  70 */       pos += dataCount * 1; }
/*     */     
/*  72 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  76 */     byte nullBits = buf.getByte(offset);
/*  77 */     int pos = offset + 13;
/*  78 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  79 */      return pos - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  85 */     byte nullBits = 0;
/*  86 */     if (this.data != null) nullBits = (byte)(nullBits | 0x1); 
/*  87 */     buf.writeByte(nullBits);
/*     */     
/*  89 */     buf.writeIntLE(this.x);
/*  90 */     buf.writeIntLE(this.y);
/*  91 */     buf.writeIntLE(this.z);
/*     */     
/*  93 */     if (this.data != null) { if (this.data.length > 4096000) throw ProtocolException.arrayTooLong("Data", this.data.length, 4096000);  VarInt.write(buf, this.data.length); for (byte item : this.data) buf.writeByte(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  98 */     int size = 13;
/*  99 */     if (this.data != null) size += VarInt.size(this.data.length) + this.data.length * 1;
/*     */     
/* 101 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 105 */     if (buffer.readableBytes() - offset < 13) {
/* 106 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 109 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 111 */     int pos = offset + 13;
/*     */     
/* 113 */     if ((nullBits & 0x1) != 0) {
/* 114 */       int dataCount = VarInt.peek(buffer, pos);
/* 115 */       if (dataCount < 0) {
/* 116 */         return ValidationResult.error("Invalid array count for Data");
/*     */       }
/* 118 */       if (dataCount > 4096000) {
/* 119 */         return ValidationResult.error("Data exceeds max length 4096000");
/*     */       }
/* 121 */       pos += VarInt.length(buffer, pos);
/* 122 */       pos += dataCount * 1;
/* 123 */       if (pos > buffer.writerIndex()) {
/* 124 */         return ValidationResult.error("Buffer overflow reading Data");
/*     */       }
/*     */     } 
/* 127 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SetFluids clone() {
/* 131 */     SetFluids copy = new SetFluids();
/* 132 */     copy.x = this.x;
/* 133 */     copy.y = this.y;
/* 134 */     copy.z = this.z;
/* 135 */     copy.data = (this.data != null) ? Arrays.copyOf(this.data, this.data.length) : null;
/* 136 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SetFluids other;
/* 142 */     if (this == obj) return true; 
/* 143 */     if (obj instanceof SetFluids) { other = (SetFluids)obj; } else { return false; }
/* 144 */      return (this.x == other.x && this.y == other.y && this.z == other.z && Arrays.equals(this.data, other.data));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 149 */     int result = 1;
/* 150 */     result = 31 * result + Integer.hashCode(this.x);
/* 151 */     result = 31 * result + Integer.hashCode(this.y);
/* 152 */     result = 31 * result + Integer.hashCode(this.z);
/* 153 */     result = 31 * result + Arrays.hashCode(this.data);
/* 154 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SetFluids.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */