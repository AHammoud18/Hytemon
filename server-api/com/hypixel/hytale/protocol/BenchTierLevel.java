/*     */ package com.hypixel.hytale.protocol;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BenchTierLevel
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 17;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 17;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public BenchUpgradeRequirement benchUpgradeRequirement;
/*     */   public double craftingTimeReductionModifier;
/*     */   public int extraInputSlot;
/*     */   public int extraOutputSlot;
/*     */   
/*     */   public BenchTierLevel() {}
/*     */   
/*     */   public BenchTierLevel(@Nullable BenchUpgradeRequirement benchUpgradeRequirement, double craftingTimeReductionModifier, int extraInputSlot, int extraOutputSlot) {
/*  29 */     this.benchUpgradeRequirement = benchUpgradeRequirement;
/*  30 */     this.craftingTimeReductionModifier = craftingTimeReductionModifier;
/*  31 */     this.extraInputSlot = extraInputSlot;
/*  32 */     this.extraOutputSlot = extraOutputSlot;
/*     */   }
/*     */   
/*     */   public BenchTierLevel(@Nonnull BenchTierLevel other) {
/*  36 */     this.benchUpgradeRequirement = other.benchUpgradeRequirement;
/*  37 */     this.craftingTimeReductionModifier = other.craftingTimeReductionModifier;
/*  38 */     this.extraInputSlot = other.extraInputSlot;
/*  39 */     this.extraOutputSlot = other.extraOutputSlot;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BenchTierLevel deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     BenchTierLevel obj = new BenchTierLevel();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.craftingTimeReductionModifier = buf.getDoubleLE(offset + 1);
/*  47 */     obj.extraInputSlot = buf.getIntLE(offset + 9);
/*  48 */     obj.extraOutputSlot = buf.getIntLE(offset + 13);
/*     */     
/*  50 */     int pos = offset + 17;
/*  51 */     if ((nullBits & 0x1) != 0) { obj.benchUpgradeRequirement = BenchUpgradeRequirement.deserialize(buf, pos);
/*  52 */       pos += BenchUpgradeRequirement.computeBytesConsumed(buf, pos); }
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int pos = offset + 17;
/*  60 */     if ((nullBits & 0x1) != 0) pos += BenchUpgradeRequirement.computeBytesConsumed(buf, pos); 
/*  61 */     return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.benchUpgradeRequirement != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     buf.writeDoubleLE(this.craftingTimeReductionModifier);
/*  71 */     buf.writeIntLE(this.extraInputSlot);
/*  72 */     buf.writeIntLE(this.extraOutputSlot);
/*     */     
/*  74 */     if (this.benchUpgradeRequirement != null) this.benchUpgradeRequirement.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  79 */     int size = 17;
/*  80 */     if (this.benchUpgradeRequirement != null) size += this.benchUpgradeRequirement.computeSize();
/*     */     
/*  82 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 17) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 17 bytes");
/*     */     }
/*     */     
/*  90 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  92 */     int pos = offset + 17;
/*     */     
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       ValidationResult benchUpgradeRequirementResult = BenchUpgradeRequirement.validateStructure(buffer, pos);
/*  96 */       if (!benchUpgradeRequirementResult.isValid()) {
/*  97 */         return ValidationResult.error("Invalid BenchUpgradeRequirement: " + benchUpgradeRequirementResult.error());
/*     */       }
/*  99 */       pos += BenchUpgradeRequirement.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 101 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BenchTierLevel clone() {
/* 105 */     BenchTierLevel copy = new BenchTierLevel();
/* 106 */     copy.benchUpgradeRequirement = (this.benchUpgradeRequirement != null) ? this.benchUpgradeRequirement.clone() : null;
/* 107 */     copy.craftingTimeReductionModifier = this.craftingTimeReductionModifier;
/* 108 */     copy.extraInputSlot = this.extraInputSlot;
/* 109 */     copy.extraOutputSlot = this.extraOutputSlot;
/* 110 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BenchTierLevel other;
/* 116 */     if (this == obj) return true; 
/* 117 */     if (obj instanceof BenchTierLevel) { other = (BenchTierLevel)obj; } else { return false; }
/* 118 */      return (Objects.equals(this.benchUpgradeRequirement, other.benchUpgradeRequirement) && this.craftingTimeReductionModifier == other.craftingTimeReductionModifier && this.extraInputSlot == other.extraInputSlot && this.extraOutputSlot == other.extraOutputSlot);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     return Objects.hash(new Object[] { this.benchUpgradeRequirement, Double.valueOf(this.craftingTimeReductionModifier), Integer.valueOf(this.extraInputSlot), Integer.valueOf(this.extraOutputSlot) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BenchTierLevel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */