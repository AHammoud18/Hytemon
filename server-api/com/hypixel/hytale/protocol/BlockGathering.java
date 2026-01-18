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
/*     */ public class BlockGathering
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 3;
/*     */   public static final int VARIABLE_BLOCK_START = 13;
/*     */   public static final int MAX_SIZE = 114688092;
/*     */   @Nullable
/*     */   public BlockBreaking breaking;
/*     */   @Nullable
/*     */   public Harvesting harvest;
/*     */   @Nullable
/*     */   public SoftBlock soft;
/*     */   
/*     */   public BlockGathering() {}
/*     */   
/*     */   public BlockGathering(@Nullable BlockBreaking breaking, @Nullable Harvesting harvest, @Nullable SoftBlock soft) {
/*  28 */     this.breaking = breaking;
/*  29 */     this.harvest = harvest;
/*  30 */     this.soft = soft;
/*     */   }
/*     */   
/*     */   public BlockGathering(@Nonnull BlockGathering other) {
/*  34 */     this.breaking = other.breaking;
/*  35 */     this.harvest = other.harvest;
/*  36 */     this.soft = other.soft;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockGathering deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     BlockGathering obj = new BlockGathering();
/*  42 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  44 */     if ((nullBits & 0x1) != 0) {
/*  45 */       int varPos0 = offset + 13 + buf.getIntLE(offset + 1);
/*  46 */       obj.breaking = BlockBreaking.deserialize(buf, varPos0);
/*     */     } 
/*  48 */     if ((nullBits & 0x2) != 0) {
/*  49 */       int varPos1 = offset + 13 + buf.getIntLE(offset + 5);
/*  50 */       obj.harvest = Harvesting.deserialize(buf, varPos1);
/*     */     } 
/*  52 */     if ((nullBits & 0x4) != 0) {
/*  53 */       int varPos2 = offset + 13 + buf.getIntLE(offset + 9);
/*  54 */       obj.soft = SoftBlock.deserialize(buf, varPos2);
/*     */     } 
/*     */     
/*  57 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  61 */     byte nullBits = buf.getByte(offset);
/*  62 */     int maxEnd = 13;
/*  63 */     if ((nullBits & 0x1) != 0) {
/*  64 */       int fieldOffset0 = buf.getIntLE(offset + 1);
/*  65 */       int pos0 = offset + 13 + fieldOffset0;
/*  66 */       pos0 += BlockBreaking.computeBytesConsumed(buf, pos0);
/*  67 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  69 */     if ((nullBits & 0x2) != 0) {
/*  70 */       int fieldOffset1 = buf.getIntLE(offset + 5);
/*  71 */       int pos1 = offset + 13 + fieldOffset1;
/*  72 */       pos1 += Harvesting.computeBytesConsumed(buf, pos1);
/*  73 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  75 */     if ((nullBits & 0x4) != 0) {
/*  76 */       int fieldOffset2 = buf.getIntLE(offset + 9);
/*  77 */       int pos2 = offset + 13 + fieldOffset2;
/*  78 */       pos2 += SoftBlock.computeBytesConsumed(buf, pos2);
/*  79 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*     */     } 
/*  81 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  86 */     int startPos = buf.writerIndex();
/*  87 */     byte nullBits = 0;
/*  88 */     if (this.breaking != null) nullBits = (byte)(nullBits | 0x1); 
/*  89 */     if (this.harvest != null) nullBits = (byte)(nullBits | 0x2); 
/*  90 */     if (this.soft != null) nullBits = (byte)(nullBits | 0x4); 
/*  91 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  94 */     int breakingOffsetSlot = buf.writerIndex();
/*  95 */     buf.writeIntLE(0);
/*  96 */     int harvestOffsetSlot = buf.writerIndex();
/*  97 */     buf.writeIntLE(0);
/*  98 */     int softOffsetSlot = buf.writerIndex();
/*  99 */     buf.writeIntLE(0);
/*     */     
/* 101 */     int varBlockStart = buf.writerIndex();
/* 102 */     if (this.breaking != null) {
/* 103 */       buf.setIntLE(breakingOffsetSlot, buf.writerIndex() - varBlockStart);
/* 104 */       this.breaking.serialize(buf);
/*     */     } else {
/* 106 */       buf.setIntLE(breakingOffsetSlot, -1);
/*     */     } 
/* 108 */     if (this.harvest != null) {
/* 109 */       buf.setIntLE(harvestOffsetSlot, buf.writerIndex() - varBlockStart);
/* 110 */       this.harvest.serialize(buf);
/*     */     } else {
/* 112 */       buf.setIntLE(harvestOffsetSlot, -1);
/*     */     } 
/* 114 */     if (this.soft != null) {
/* 115 */       buf.setIntLE(softOffsetSlot, buf.writerIndex() - varBlockStart);
/* 116 */       this.soft.serialize(buf);
/*     */     } else {
/* 118 */       buf.setIntLE(softOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 124 */     int size = 13;
/* 125 */     if (this.breaking != null) size += this.breaking.computeSize(); 
/* 126 */     if (this.harvest != null) size += this.harvest.computeSize(); 
/* 127 */     if (this.soft != null) size += this.soft.computeSize();
/*     */     
/* 129 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 133 */     if (buffer.readableBytes() - offset < 13) {
/* 134 */       return ValidationResult.error("Buffer too small: expected at least 13 bytes");
/*     */     }
/*     */     
/* 137 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 140 */     if ((nullBits & 0x1) != 0) {
/* 141 */       int breakingOffset = buffer.getIntLE(offset + 1);
/* 142 */       if (breakingOffset < 0) {
/* 143 */         return ValidationResult.error("Invalid offset for Breaking");
/*     */       }
/* 145 */       int pos = offset + 13 + breakingOffset;
/* 146 */       if (pos >= buffer.writerIndex()) {
/* 147 */         return ValidationResult.error("Offset out of bounds for Breaking");
/*     */       }
/* 149 */       ValidationResult breakingResult = BlockBreaking.validateStructure(buffer, pos);
/* 150 */       if (!breakingResult.isValid()) {
/* 151 */         return ValidationResult.error("Invalid Breaking: " + breakingResult.error());
/*     */       }
/* 153 */       pos += BlockBreaking.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 156 */     if ((nullBits & 0x2) != 0) {
/* 157 */       int harvestOffset = buffer.getIntLE(offset + 5);
/* 158 */       if (harvestOffset < 0) {
/* 159 */         return ValidationResult.error("Invalid offset for Harvest");
/*     */       }
/* 161 */       int pos = offset + 13 + harvestOffset;
/* 162 */       if (pos >= buffer.writerIndex()) {
/* 163 */         return ValidationResult.error("Offset out of bounds for Harvest");
/*     */       }
/* 165 */       ValidationResult harvestResult = Harvesting.validateStructure(buffer, pos);
/* 166 */       if (!harvestResult.isValid()) {
/* 167 */         return ValidationResult.error("Invalid Harvest: " + harvestResult.error());
/*     */       }
/* 169 */       pos += Harvesting.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 172 */     if ((nullBits & 0x4) != 0) {
/* 173 */       int softOffset = buffer.getIntLE(offset + 9);
/* 174 */       if (softOffset < 0) {
/* 175 */         return ValidationResult.error("Invalid offset for Soft");
/*     */       }
/* 177 */       int pos = offset + 13 + softOffset;
/* 178 */       if (pos >= buffer.writerIndex()) {
/* 179 */         return ValidationResult.error("Offset out of bounds for Soft");
/*     */       }
/* 181 */       ValidationResult softResult = SoftBlock.validateStructure(buffer, pos);
/* 182 */       if (!softResult.isValid()) {
/* 183 */         return ValidationResult.error("Invalid Soft: " + softResult.error());
/*     */       }
/* 185 */       pos += SoftBlock.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 187 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockGathering clone() {
/* 191 */     BlockGathering copy = new BlockGathering();
/* 192 */     copy.breaking = (this.breaking != null) ? this.breaking.clone() : null;
/* 193 */     copy.harvest = (this.harvest != null) ? this.harvest.clone() : null;
/* 194 */     copy.soft = (this.soft != null) ? this.soft.clone() : null;
/* 195 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockGathering other;
/* 201 */     if (this == obj) return true; 
/* 202 */     if (obj instanceof BlockGathering) { other = (BlockGathering)obj; } else { return false; }
/* 203 */      return (Objects.equals(this.breaking, other.breaking) && Objects.equals(this.harvest, other.harvest) && Objects.equals(this.soft, other.soft));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 208 */     return Objects.hash(new Object[] { this.breaking, this.harvest, this.soft });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockGathering.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */