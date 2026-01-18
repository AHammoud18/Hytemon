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
/*     */ public class EntityEffectUpdate
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 12;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 12;
/*     */   public static final int MAX_SIZE = 16384017;
/*     */   @Nonnull
/*  20 */   public EffectOp type = EffectOp.Add;
/*     */   
/*     */   public int id;
/*     */   
/*     */   public float remainingTime;
/*     */   public boolean infinite;
/*     */   public boolean debuff;
/*     */   @Nullable
/*     */   public String statusEffectIcon;
/*     */   
/*     */   public EntityEffectUpdate(@Nonnull EffectOp type, int id, float remainingTime, boolean infinite, boolean debuff, @Nullable String statusEffectIcon) {
/*  31 */     this.type = type;
/*  32 */     this.id = id;
/*  33 */     this.remainingTime = remainingTime;
/*  34 */     this.infinite = infinite;
/*  35 */     this.debuff = debuff;
/*  36 */     this.statusEffectIcon = statusEffectIcon;
/*     */   }
/*     */   
/*     */   public EntityEffectUpdate(@Nonnull EntityEffectUpdate other) {
/*  40 */     this.type = other.type;
/*  41 */     this.id = other.id;
/*  42 */     this.remainingTime = other.remainingTime;
/*  43 */     this.infinite = other.infinite;
/*  44 */     this.debuff = other.debuff;
/*  45 */     this.statusEffectIcon = other.statusEffectIcon;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityEffectUpdate deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     EntityEffectUpdate obj = new EntityEffectUpdate();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.type = EffectOp.fromValue(buf.getByte(offset + 1));
/*  53 */     obj.id = buf.getIntLE(offset + 2);
/*  54 */     obj.remainingTime = buf.getFloatLE(offset + 6);
/*  55 */     obj.infinite = (buf.getByte(offset + 10) != 0);
/*  56 */     obj.debuff = (buf.getByte(offset + 11) != 0);
/*     */     
/*  58 */     int pos = offset + 12;
/*  59 */     if ((nullBits & 0x1) != 0) { int statusEffectIconLen = VarInt.peek(buf, pos);
/*  60 */       if (statusEffectIconLen < 0) throw ProtocolException.negativeLength("StatusEffectIcon", statusEffectIconLen); 
/*  61 */       if (statusEffectIconLen > 4096000) throw ProtocolException.stringTooLong("StatusEffectIcon", statusEffectIconLen, 4096000); 
/*  62 */       int statusEffectIconVarLen = VarInt.length(buf, pos);
/*  63 */       obj.statusEffectIcon = PacketIO.readVarString(buf, pos, PacketIO.UTF8);
/*  64 */       pos += statusEffectIconVarLen + statusEffectIconLen; }
/*     */     
/*  66 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  70 */     byte nullBits = buf.getByte(offset);
/*  71 */     int pos = offset + 12;
/*  72 */     if ((nullBits & 0x1) != 0) { int sl = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + sl; }
/*  73 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.statusEffectIcon != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     buf.writeByte(nullBits);
/*     */     
/*  82 */     buf.writeByte(this.type.getValue());
/*  83 */     buf.writeIntLE(this.id);
/*  84 */     buf.writeFloatLE(this.remainingTime);
/*  85 */     buf.writeByte(this.infinite ? 1 : 0);
/*  86 */     buf.writeByte(this.debuff ? 1 : 0);
/*     */     
/*  88 */     if (this.statusEffectIcon != null) PacketIO.writeVarString(buf, this.statusEffectIcon, 4096000);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  93 */     int size = 12;
/*  94 */     if (this.statusEffectIcon != null) size += PacketIO.stringSize(this.statusEffectIcon);
/*     */     
/*  96 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 100 */     if (buffer.readableBytes() - offset < 12) {
/* 101 */       return ValidationResult.error("Buffer too small: expected at least 12 bytes");
/*     */     }
/*     */     
/* 104 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 106 */     int pos = offset + 12;
/*     */     
/* 108 */     if ((nullBits & 0x1) != 0) {
/* 109 */       int statusEffectIconLen = VarInt.peek(buffer, pos);
/* 110 */       if (statusEffectIconLen < 0) {
/* 111 */         return ValidationResult.error("Invalid string length for StatusEffectIcon");
/*     */       }
/* 113 */       if (statusEffectIconLen > 4096000) {
/* 114 */         return ValidationResult.error("StatusEffectIcon exceeds max length 4096000");
/*     */       }
/* 116 */       pos += VarInt.length(buffer, pos);
/* 117 */       pos += statusEffectIconLen;
/* 118 */       if (pos > buffer.writerIndex()) {
/* 119 */         return ValidationResult.error("Buffer overflow reading StatusEffectIcon");
/*     */       }
/*     */     } 
/* 122 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityEffectUpdate clone() {
/* 126 */     EntityEffectUpdate copy = new EntityEffectUpdate();
/* 127 */     copy.type = this.type;
/* 128 */     copy.id = this.id;
/* 129 */     copy.remainingTime = this.remainingTime;
/* 130 */     copy.infinite = this.infinite;
/* 131 */     copy.debuff = this.debuff;
/* 132 */     copy.statusEffectIcon = this.statusEffectIcon;
/* 133 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityEffectUpdate other;
/* 139 */     if (this == obj) return true; 
/* 140 */     if (obj instanceof EntityEffectUpdate) { other = (EntityEffectUpdate)obj; } else { return false; }
/* 141 */      return (Objects.equals(this.type, other.type) && this.id == other.id && this.remainingTime == other.remainingTime && this.infinite == other.infinite && this.debuff == other.debuff && Objects.equals(this.statusEffectIcon, other.statusEffectIcon));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 146 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.id), Float.valueOf(this.remainingTime), Boolean.valueOf(this.infinite), Boolean.valueOf(this.debuff), this.statusEffectIcon });
/*     */   }
/*     */   
/*     */   public EntityEffectUpdate() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityEffectUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */