/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class ItemToolSpec
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 16384014;
/*     */   @Nullable
/*     */   public String gatherType;
/*     */   public float power;
/*     */   public int quality;
/*     */   
/*     */   public ItemToolSpec() {}
/*     */   
/*     */   public ItemToolSpec(@Nullable String gatherType, float power, int quality) {
/*  28 */     this.gatherType = gatherType;
/*  29 */     this.power = power;
/*  30 */     this.quality = quality;
/*     */   }
/*     */   
/*     */   public ItemToolSpec(@Nonnull ItemToolSpec other) {
/*  34 */     this.gatherType = other.gatherType;
/*  35 */     this.power = other.power;
/*  36 */     this.quality = other.quality;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ItemToolSpec deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ItemToolSpec obj = new ItemToolSpec();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.power = buf.getFloatLE(offset + 1);
/*  44 */     obj.quality = buf.getIntLE(offset + 5);
/*     */     
/*  46 */     int pos = offset + 9;
/*  47 */     if ((nullBits & 0x1) != 0) { int gatherTypeLen = VarInt.peek(buf, pos);
/*  48 */       if (gatherTypeLen < 0) throw ProtocolException.negativeLength("GatherType", gatherTypeLen); 
/*  49 */       if (gatherTypeLen > 4096000) throw ProtocolException.stringTooLong("GatherType", gatherTypeLen, 4096000); 
/*  50 */       int gatherTypeVarLen = VarInt.length(buf, pos);
/*  51 */       obj.gatherType = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  52 */       pos += gatherTypeVarLen + gatherTypeLen; }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 9;
/*  60 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  61 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.gatherType != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     buf.writeFloatLE(this.power);
/*  71 */     buf.writeIntLE(this.quality);
/*     */     
/*  73 */     if (this.gatherType != null) PacketIO.writeVarString(buf, this.gatherType, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  78 */     int size = 9;
/*  79 */     if (this.gatherType != null) size += PacketIO.stringSize(this.gatherType);
/*     */     
/*  81 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  85 */     if (buffer.readableBytes() - offset < 9) {
/*  86 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  89 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  91 */     int pos = offset + 9;
/*     */     
/*  93 */     if ((nullBits & 0x1) != 0) {
/*  94 */       int gatherTypeLen = VarInt.peek(buffer, pos);
/*  95 */       if (gatherTypeLen < 0) {
/*  96 */         return ValidationResult.error("Invalid string length for GatherType");
/*     */       }
/*  98 */       if (gatherTypeLen > 4096000) {
/*  99 */         return ValidationResult.error("GatherType exceeds max length 4096000");
/*     */       }
/* 101 */       pos += VarInt.length(buffer, pos);
/* 102 */       pos += gatherTypeLen;
/* 103 */       if (pos > buffer.writerIndex()) {
/* 104 */         return ValidationResult.error("Buffer overflow reading GatherType");
/*     */       }
/*     */     } 
/* 107 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ItemToolSpec clone() {
/* 111 */     ItemToolSpec copy = new ItemToolSpec();
/* 112 */     copy.gatherType = this.gatherType;
/* 113 */     copy.power = this.power;
/* 114 */     copy.quality = this.quality;
/* 115 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ItemToolSpec other;
/* 121 */     if (this == obj) return true; 
/* 122 */     if (obj instanceof ItemToolSpec) { other = (ItemToolSpec)obj; } else { return false; }
/* 123 */      return (Objects.equals(this.gatherType, other.gatherType) && this.power == other.power && this.quality == other.quality);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return Objects.hash(new Object[] { this.gatherType, Float.valueOf(this.power), Integer.valueOf(this.quality) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemToolSpec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */