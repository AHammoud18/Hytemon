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
/*     */ public class ConnectedBlockRuleSet
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 2;
/*     */   public static final int VARIABLE_FIELD_COUNT = 2;
/*     */   public static final int VARIABLE_BLOCK_START = 10;
/*     */   public static final int MAX_SIZE = 65536114;
/*     */   @Nonnull
/*  20 */   public ConnectedBlockRuleSetType type = ConnectedBlockRuleSetType.Stair;
/*     */   
/*     */   @Nullable
/*     */   public StairConnectedBlockRuleSet stair;
/*     */   @Nullable
/*     */   public RoofConnectedBlockRuleSet roof;
/*     */   
/*     */   public ConnectedBlockRuleSet(@Nonnull ConnectedBlockRuleSetType type, @Nullable StairConnectedBlockRuleSet stair, @Nullable RoofConnectedBlockRuleSet roof) {
/*  28 */     this.type = type;
/*  29 */     this.stair = stair;
/*  30 */     this.roof = roof;
/*     */   }
/*     */   
/*     */   public ConnectedBlockRuleSet(@Nonnull ConnectedBlockRuleSet other) {
/*  34 */     this.type = other.type;
/*  35 */     this.stair = other.stair;
/*  36 */     this.roof = other.roof;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ConnectedBlockRuleSet deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ConnectedBlockRuleSet obj = new ConnectedBlockRuleSet();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.type = ConnectedBlockRuleSetType.fromValue(buf.getByte(offset + 1));
/*     */     
/*  45 */     if ((nullBits & 0x1) != 0) {
/*  46 */       int varPos0 = offset + 10 + buf.getIntLE(offset + 2);
/*  47 */       obj.stair = StairConnectedBlockRuleSet.deserialize(buf, varPos0);
/*     */     } 
/*  49 */     if ((nullBits & 0x2) != 0) {
/*  50 */       int varPos1 = offset + 10 + buf.getIntLE(offset + 6);
/*  51 */       obj.roof = RoofConnectedBlockRuleSet.deserialize(buf, varPos1);
/*     */     } 
/*     */     
/*  54 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  58 */     byte nullBits = buf.getByte(offset);
/*  59 */     int maxEnd = 10;
/*  60 */     if ((nullBits & 0x1) != 0) {
/*  61 */       int fieldOffset0 = buf.getIntLE(offset + 2);
/*  62 */       int pos0 = offset + 10 + fieldOffset0;
/*  63 */       pos0 += StairConnectedBlockRuleSet.computeBytesConsumed(buf, pos0);
/*  64 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*     */     } 
/*  66 */     if ((nullBits & 0x2) != 0) {
/*  67 */       int fieldOffset1 = buf.getIntLE(offset + 6);
/*  68 */       int pos1 = offset + 10 + fieldOffset1;
/*  69 */       pos1 += RoofConnectedBlockRuleSet.computeBytesConsumed(buf, pos1);
/*  70 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*     */     } 
/*  72 */     return maxEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  77 */     int startPos = buf.writerIndex();
/*  78 */     byte nullBits = 0;
/*  79 */     if (this.stair != null) nullBits = (byte)(nullBits | 0x1); 
/*  80 */     if (this.roof != null) nullBits = (byte)(nullBits | 0x2); 
/*  81 */     buf.writeByte(nullBits);
/*     */     
/*  83 */     buf.writeByte(this.type.getValue());
/*     */     
/*  85 */     int stairOffsetSlot = buf.writerIndex();
/*  86 */     buf.writeIntLE(0);
/*  87 */     int roofOffsetSlot = buf.writerIndex();
/*  88 */     buf.writeIntLE(0);
/*     */     
/*  90 */     int varBlockStart = buf.writerIndex();
/*  91 */     if (this.stair != null) {
/*  92 */       buf.setIntLE(stairOffsetSlot, buf.writerIndex() - varBlockStart);
/*  93 */       this.stair.serialize(buf);
/*     */     } else {
/*  95 */       buf.setIntLE(stairOffsetSlot, -1);
/*     */     } 
/*  97 */     if (this.roof != null) {
/*  98 */       buf.setIntLE(roofOffsetSlot, buf.writerIndex() - varBlockStart);
/*  99 */       this.roof.serialize(buf);
/*     */     } else {
/* 101 */       buf.setIntLE(roofOffsetSlot, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/* 107 */     int size = 10;
/* 108 */     if (this.stair != null) size += this.stair.computeSize(); 
/* 109 */     if (this.roof != null) size += this.roof.computeSize();
/*     */     
/* 111 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 115 */     if (buffer.readableBytes() - offset < 10) {
/* 116 */       return ValidationResult.error("Buffer too small: expected at least 10 bytes");
/*     */     }
/*     */     
/* 119 */     byte nullBits = buffer.getByte(offset);
/*     */ 
/*     */     
/* 122 */     if ((nullBits & 0x1) != 0) {
/* 123 */       int stairOffset = buffer.getIntLE(offset + 2);
/* 124 */       if (stairOffset < 0) {
/* 125 */         return ValidationResult.error("Invalid offset for Stair");
/*     */       }
/* 127 */       int pos = offset + 10 + stairOffset;
/* 128 */       if (pos >= buffer.writerIndex()) {
/* 129 */         return ValidationResult.error("Offset out of bounds for Stair");
/*     */       }
/* 131 */       ValidationResult stairResult = StairConnectedBlockRuleSet.validateStructure(buffer, pos);
/* 132 */       if (!stairResult.isValid()) {
/* 133 */         return ValidationResult.error("Invalid Stair: " + stairResult.error());
/*     */       }
/* 135 */       pos += StairConnectedBlockRuleSet.computeBytesConsumed(buffer, pos);
/*     */     } 
/*     */     
/* 138 */     if ((nullBits & 0x2) != 0) {
/* 139 */       int roofOffset = buffer.getIntLE(offset + 6);
/* 140 */       if (roofOffset < 0) {
/* 141 */         return ValidationResult.error("Invalid offset for Roof");
/*     */       }
/* 143 */       int pos = offset + 10 + roofOffset;
/* 144 */       if (pos >= buffer.writerIndex()) {
/* 145 */         return ValidationResult.error("Offset out of bounds for Roof");
/*     */       }
/* 147 */       ValidationResult roofResult = RoofConnectedBlockRuleSet.validateStructure(buffer, pos);
/* 148 */       if (!roofResult.isValid()) {
/* 149 */         return ValidationResult.error("Invalid Roof: " + roofResult.error());
/*     */       }
/* 151 */       pos += RoofConnectedBlockRuleSet.computeBytesConsumed(buffer, pos);
/*     */     } 
/* 153 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ConnectedBlockRuleSet clone() {
/* 157 */     ConnectedBlockRuleSet copy = new ConnectedBlockRuleSet();
/* 158 */     copy.type = this.type;
/* 159 */     copy.stair = (this.stair != null) ? this.stair.clone() : null;
/* 160 */     copy.roof = (this.roof != null) ? this.roof.clone() : null;
/* 161 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ConnectedBlockRuleSet other;
/* 167 */     if (this == obj) return true; 
/* 168 */     if (obj instanceof ConnectedBlockRuleSet) { other = (ConnectedBlockRuleSet)obj; } else { return false; }
/* 169 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.stair, other.stair) && Objects.equals(this.roof, other.roof));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 174 */     return Objects.hash(new Object[] { this.type, this.stair, this.roof });
/*     */   }
/*     */   
/*     */   public ConnectedBlockRuleSet() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ConnectedBlockRuleSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */