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
/*     */ public class EntityStatOnHit
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 13;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 16384018;
/*     */   public int entityStatIndex;
/*     */   public float amount;
/*     */   @Nullable
/*     */   public float[] multipliersPerEntitiesHit;
/*     */   public float multiplierPerExtraEntityHit;
/*     */   
/*     */   public EntityStatOnHit() {}
/*     */   
/*     */   public EntityStatOnHit(int entityStatIndex, float amount, @Nullable float[] multipliersPerEntitiesHit, float multiplierPerExtraEntityHit) {
/*  29 */     this.entityStatIndex = entityStatIndex;
/*  30 */     this.amount = amount;
/*  31 */     this.multipliersPerEntitiesHit = multipliersPerEntitiesHit;
/*  32 */     this.multiplierPerExtraEntityHit = multiplierPerExtraEntityHit;
/*     */   }
/*     */   
/*     */   public EntityStatOnHit(@Nonnull EntityStatOnHit other) {
/*  36 */     this.entityStatIndex = other.entityStatIndex;
/*  37 */     this.amount = other.amount;
/*  38 */     this.multipliersPerEntitiesHit = other.multipliersPerEntitiesHit;
/*  39 */     this.multiplierPerExtraEntityHit = other.multiplierPerExtraEntityHit;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static EntityStatOnHit deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     EntityStatOnHit obj = new EntityStatOnHit();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.entityStatIndex = buf.getIntLE(offset + 1);
/*  47 */     obj.amount = buf.getFloatLE(offset + 5);
/*  48 */     obj.multiplierPerExtraEntityHit = buf.getFloatLE(offset + 9);
/*     */     
/*  50 */     int pos = offset + 13;
/*  51 */     if ((nullBits & 0x1) != 0) { int multipliersPerEntitiesHitCount = VarInt.peek(buf, pos);
/*  52 */       if (multipliersPerEntitiesHitCount < 0) throw ProtocolException.negativeLength("MultipliersPerEntitiesHit", multipliersPerEntitiesHitCount); 
/*  53 */       if (multipliersPerEntitiesHitCount > 4096000) throw ProtocolException.arrayTooLong("MultipliersPerEntitiesHit", multipliersPerEntitiesHitCount, 4096000); 
/*  54 */       int multipliersPerEntitiesHitVarLen = VarInt.size(multipliersPerEntitiesHitCount);
/*  55 */       if ((pos + multipliersPerEntitiesHitVarLen) + multipliersPerEntitiesHitCount * 4L > buf.readableBytes())
/*  56 */         throw ProtocolException.bufferTooSmall("MultipliersPerEntitiesHit", pos + multipliersPerEntitiesHitVarLen + multipliersPerEntitiesHitCount * 4, buf.readableBytes()); 
/*  57 */       pos += multipliersPerEntitiesHitVarLen;
/*  58 */       obj.multipliersPerEntitiesHit = new float[multipliersPerEntitiesHitCount];
/*  59 */       for (int i = 0; i < multipliersPerEntitiesHitCount; i++) {
/*  60 */         obj.multipliersPerEntitiesHit[i] = buf.getFloatLE(pos + i * 4);
/*     */       }
/*  62 */       pos += multipliersPerEntitiesHitCount * 4; }
/*     */     
/*  64 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  68 */     byte nullBits = buf.getByte(offset);
/*  69 */     int pos = offset + 13;
/*  70 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos) + arrLen * 4; }
/*  71 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  76 */     byte nullBits = 0;
/*  77 */     if (this.multipliersPerEntitiesHit != null) nullBits = (byte)(nullBits | 0x1); 
/*  78 */     buf.writeByte(nullBits);
/*     */     
/*  80 */     buf.writeIntLE(this.entityStatIndex);
/*  81 */     buf.writeFloatLE(this.amount);
/*  82 */     buf.writeFloatLE(this.multiplierPerExtraEntityHit);
/*     */     
/*  84 */     if (this.multipliersPerEntitiesHit != null) { if (this.multipliersPerEntitiesHit.length > 4096000) throw ProtocolException.arrayTooLong("MultipliersPerEntitiesHit", this.multipliersPerEntitiesHit.length, 4096000);  VarInt.write(buf, this.multipliersPerEntitiesHit.length); for (float item : this.multipliersPerEntitiesHit) buf.writeFloatLE(item);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  89 */     int size = 13;
/*  90 */     if (this.multipliersPerEntitiesHit != null) size += VarInt.size(this.multipliersPerEntitiesHit.length) + this.multipliersPerEntitiesHit.length * 4;
/*     */     
/*  92 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  96 */     if (buffer.readableBytes() - offset < 13) {
/*  97 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 100 */     byte nullBits = buffer.getByte(offset);
/*     */     
/* 102 */     int pos = offset + 13;
/*     */     
/* 104 */     if ((nullBits & 0x1) != 0) {
/* 105 */       int multipliersPerEntitiesHitCount = VarInt.peek(buffer, pos);
/* 106 */       if (multipliersPerEntitiesHitCount < 0) {
/* 107 */         return ValidationResult.error("Invalid array count for MultipliersPerEntitiesHit");
/*     */       }
/* 109 */       if (multipliersPerEntitiesHitCount > 4096000) {
/* 110 */         return ValidationResult.error("MultipliersPerEntitiesHit exceeds max length 4096000");
/*     */       }
/* 112 */       pos += VarInt.length(buffer, pos);
/* 113 */       pos += multipliersPerEntitiesHitCount * 4;
/* 114 */       if (pos > buffer.writerIndex()) {
/* 115 */         return ValidationResult.error("Buffer overflow reading MultipliersPerEntitiesHit");
/*     */       }
/*     */     } 
/* 118 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public EntityStatOnHit clone() {
/* 122 */     EntityStatOnHit copy = new EntityStatOnHit();
/* 123 */     copy.entityStatIndex = this.entityStatIndex;
/* 124 */     copy.amount = this.amount;
/* 125 */     copy.multipliersPerEntitiesHit = (this.multipliersPerEntitiesHit != null) ? Arrays.copyOf(this.multipliersPerEntitiesHit, this.multipliersPerEntitiesHit.length) : null;
/* 126 */     copy.multiplierPerExtraEntityHit = this.multiplierPerExtraEntityHit;
/* 127 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     EntityStatOnHit other;
/* 133 */     if (this == obj) return true; 
/* 134 */     if (obj instanceof EntityStatOnHit) { other = (EntityStatOnHit)obj; } else { return false; }
/* 135 */      return (this.entityStatIndex == other.entityStatIndex && this.amount == other.amount && Arrays.equals(this.multipliersPerEntitiesHit, other.multipliersPerEntitiesHit) && this.multiplierPerExtraEntityHit == other.multiplierPerExtraEntityHit);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 140 */     int result = 1;
/* 141 */     result = 31 * result + Integer.hashCode(this.entityStatIndex);
/* 142 */     result = 31 * result + Float.hashCode(this.amount);
/* 143 */     result = 31 * result + Arrays.hashCode(this.multipliersPerEntitiesHit);
/* 144 */     result = 31 * result + Float.hashCode(this.multiplierPerExtraEntityHit);
/* 145 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\EntityStatOnHit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */