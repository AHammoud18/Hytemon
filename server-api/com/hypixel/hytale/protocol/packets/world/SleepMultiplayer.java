/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.PacketIO;
/*     */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import com.hypixel.hytale.protocol.io.VarInt;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Arrays;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SleepMultiplayer
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 65536014;
/*     */   public int sleepersCount;
/*     */   public int awakeCount;
/*     */   @Nullable
/*     */   public UUID[] awakeSample;
/*     */   
/*     */   public SleepMultiplayer() {}
/*     */   
/*     */   public SleepMultiplayer(int sleepersCount, int awakeCount, @Nullable UUID[] awakeSample) {
/*  28 */     this.sleepersCount = sleepersCount;
/*  29 */     this.awakeCount = awakeCount;
/*  30 */     this.awakeSample = awakeSample;
/*     */   }
/*     */   
/*     */   public SleepMultiplayer(@Nonnull SleepMultiplayer other) {
/*  34 */     this.sleepersCount = other.sleepersCount;
/*  35 */     this.awakeCount = other.awakeCount;
/*  36 */     this.awakeSample = other.awakeSample;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SleepMultiplayer deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     SleepMultiplayer obj = new SleepMultiplayer();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.sleepersCount = buf.getIntLE(offset + 1);
/*  44 */     obj.awakeCount = buf.getIntLE(offset + 5);
/*     */     
/*  46 */     int pos = offset + 9;
/*  47 */     if ((nullBits & 0x1) != 0) { int awakeSampleCount = VarInt.peek(buf, pos);
/*  48 */       if (awakeSampleCount < 0) throw ProtocolException.negativeLength("AwakeSample", awakeSampleCount); 
/*  49 */       if (awakeSampleCount > 4096000) throw ProtocolException.arrayTooLong("AwakeSample", awakeSampleCount, 4096000); 
/*  50 */       int awakeSampleVarLen = VarInt.size(awakeSampleCount);
/*  51 */       if ((pos + awakeSampleVarLen) + awakeSampleCount * 16L > buf.readableBytes())
/*  52 */         throw ProtocolException.bufferTooSmall("AwakeSample", pos + awakeSampleVarLen + awakeSampleCount * 16, buf.readableBytes()); 
/*  53 */       pos += awakeSampleVarLen;
/*  54 */       obj.awakeSample = new UUID[awakeSampleCount];
/*  55 */       for (int i = 0; i < awakeSampleCount; i++) {
/*  56 */         obj.awakeSample[i] = PacketIO.readUUID(buf, pos + i * 16);
/*     */       }
/*  58 */       pos += awakeSampleCount * 16; }
/*     */     
/*  60 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  64 */     byte nullBits = buf.getByte(offset);
/*  65 */     int pos = offset + 9;
/*  66 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 16; }
/*  67 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  72 */     byte nullBits = 0;
/*  73 */     if (this.awakeSample != null) nullBits = (byte)(nullBits | 0x1); 
/*  74 */     buf.writeByte(nullBits);
/*     */     
/*  76 */     buf.writeIntLE(this.sleepersCount);
/*  77 */     buf.writeIntLE(this.awakeCount);
/*     */     
/*  79 */     if (this.awakeSample != null) { if (this.awakeSample.length > 4096000) throw ProtocolException.arrayTooLong("AwakeSample", this.awakeSample.length, 4096000);  VarInt.write(buf, this.awakeSample.length); for (UUID item : this.awakeSample) PacketIO.writeUUID(buf, item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  84 */     int size = 9;
/*  85 */     if (this.awakeSample != null) size += VarInt.size(this.awakeSample.length) + this.awakeSample.length * 16;
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
/* 100 */       int awakeSampleCount = VarInt.peek(buffer, pos);
/* 101 */       if (awakeSampleCount < 0) {
/* 102 */         return ValidationResult.error("Invalid array count for AwakeSample");
/*     */       }
/* 104 */       if (awakeSampleCount > 4096000) {
/* 105 */         return ValidationResult.error("AwakeSample exceeds max length 4096000");
/*     */       }
/* 107 */       pos += VarInt.length(buffer, pos);
/* 108 */       pos += awakeSampleCount * 16;
/* 109 */       if (pos > buffer.writerIndex()) {
/* 110 */         return ValidationResult.error("Buffer overflow reading AwakeSample");
/*     */       }
/*     */     } 
/* 113 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SleepMultiplayer clone() {
/* 117 */     SleepMultiplayer copy = new SleepMultiplayer();
/* 118 */     copy.sleepersCount = this.sleepersCount;
/* 119 */     copy.awakeCount = this.awakeCount;
/* 120 */     copy.awakeSample = (this.awakeSample != null) ? Arrays.<UUID>copyOf(this.awakeSample, this.awakeSample.length) : null;
/* 121 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SleepMultiplayer other;
/* 127 */     if (this == obj) return true; 
/* 128 */     if (obj instanceof SleepMultiplayer) { other = (SleepMultiplayer)obj; } else { return false; }
/* 129 */      return (this.sleepersCount == other.sleepersCount && this.awakeCount == other.awakeCount && Arrays.equals((Object[])this.awakeSample, (Object[])other.awakeSample));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 134 */     int result = 1;
/* 135 */     result = 31 * result + Integer.hashCode(this.sleepersCount);
/* 136 */     result = 31 * result + Integer.hashCode(this.awakeCount);
/* 137 */     result = 31 * result + Arrays.hashCode((Object[])this.awakeSample);
/* 138 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\SleepMultiplayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */