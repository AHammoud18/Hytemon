/*     */ package com.hypixel.hytale.protocol;
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
/*     */ public class AbilityEffects
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 4096006;
/*     */   @Nullable
/*     */   public InteractionType[] disabled;
/*     */   
/*     */   public AbilityEffects() {}
/*     */   
/*     */   public AbilityEffects(@Nullable InteractionType[] disabled) {
/*  26 */     this.disabled = disabled;
/*     */   }
/*     */   
/*     */   public AbilityEffects(@Nonnull AbilityEffects other) {
/*  30 */     this.disabled = other.disabled;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static AbilityEffects deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     AbilityEffects obj = new AbilityEffects();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int disabledCount = VarInt.peek(buf, pos);
/*  40 */       if (disabledCount < 0) throw ProtocolException.negativeLength("Disabled", disabledCount); 
/*  41 */       if (disabledCount > 4096000) throw ProtocolException.arrayTooLong("Disabled", disabledCount, 4096000); 
/*  42 */       int disabledVarLen = VarInt.size(disabledCount);
/*  43 */       if ((pos + disabledVarLen) + disabledCount * 1L > buf.readableBytes())
/*  44 */         throw ProtocolException.bufferTooSmall("Disabled", pos + disabledVarLen + disabledCount * 1, buf.readableBytes()); 
/*  45 */       pos += disabledVarLen;
/*  46 */       obj.disabled = new InteractionType[disabledCount];
/*  47 */       for (int i = 0; i < disabledCount; i++) {
/*  48 */         obj.disabled[i] = InteractionType.fromValue(buf.getByte(pos)); pos++;
/*     */       }  }
/*     */     
/*  51 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  55 */     byte nullBits = buf.getByte(offset);
/*  56 */     int pos = offset + 1;
/*  57 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 1; }
/*  58 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  63 */     byte nullBits = 0;
/*  64 */     if (this.disabled != null) nullBits = (byte)(nullBits | 0x1); 
/*  65 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  68 */     if (this.disabled != null) { if (this.disabled.length > 4096000) throw ProtocolException.arrayTooLong("Disabled", this.disabled.length, 4096000);  VarInt.write(buf, this.disabled.length); for (InteractionType item : this.disabled) buf.writeByte(item.getValue());  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  73 */     int size = 1;
/*  74 */     if (this.disabled != null) size += VarInt.size(this.disabled.length) + this.disabled.length * 1;
/*     */     
/*  76 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  80 */     if (buffer.readableBytes() - offset < 1) {
/*  81 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  84 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  86 */     int pos = offset + 1;
/*     */     
/*  88 */     if ((nullBits & 0x1) != 0) {
/*  89 */       int disabledCount = VarInt.peek(buffer, pos);
/*  90 */       if (disabledCount < 0) {
/*  91 */         return ValidationResult.error("Invalid array count for Disabled");
/*     */       }
/*  93 */       if (disabledCount > 4096000) {
/*  94 */         return ValidationResult.error("Disabled exceeds max length 4096000");
/*     */       }
/*  96 */       pos += VarInt.length(buffer, pos);
/*  97 */       pos += disabledCount * 1;
/*  98 */       if (pos > buffer.writerIndex()) {
/*  99 */         return ValidationResult.error("Buffer overflow reading Disabled");
/*     */       }
/*     */     } 
/* 102 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public AbilityEffects clone() {
/* 106 */     AbilityEffects copy = new AbilityEffects();
/* 107 */     copy.disabled = (this.disabled != null) ? Arrays.<InteractionType>copyOf(this.disabled, this.disabled.length) : null;
/* 108 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     AbilityEffects other;
/* 114 */     if (this == obj) return true; 
/* 115 */     if (obj instanceof AbilityEffects) { other = (AbilityEffects)obj; } else { return false; }
/* 116 */      return Arrays.equals((Object[])this.disabled, (Object[])other.disabled);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 121 */     int result = 1;
/* 122 */     result = 31 * result + Arrays.hashCode((Object[])this.disabled);
/* 123 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\AbilityEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */