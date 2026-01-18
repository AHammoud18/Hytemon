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
/*     */ public class BenchUpgradeRequirement
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public MaterialQuantity[] material;
/*     */   public double timeSeconds;
/*     */   
/*     */   public BenchUpgradeRequirement() {}
/*     */   
/*     */   public BenchUpgradeRequirement(@Nullable MaterialQuantity[] material, double timeSeconds) {
/*  27 */     this.material = material;
/*  28 */     this.timeSeconds = timeSeconds;
/*     */   }
/*     */   
/*     */   public BenchUpgradeRequirement(@Nonnull BenchUpgradeRequirement other) {
/*  32 */     this.material = other.material;
/*  33 */     this.timeSeconds = other.timeSeconds;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BenchUpgradeRequirement deserialize(@Nonnull ByteBuf buf, int offset) {
/*  38 */     BenchUpgradeRequirement obj = new BenchUpgradeRequirement();
/*  39 */     byte nullBits = buf.getByte(offset);
/*  40 */     obj.timeSeconds = buf.getDoubleLE(offset + 1);
/*     */     
/*  42 */     int pos = offset + 9;
/*  43 */     if ((nullBits & 0x1) != 0) { int materialCount = VarInt.peek(buf, pos);
/*  44 */       if (materialCount < 0) throw ProtocolException.negativeLength("Material", materialCount); 
/*  45 */       if (materialCount > 4096000) throw ProtocolException.arrayTooLong("Material", materialCount, 4096000); 
/*  46 */       int materialVarLen = VarInt.size(materialCount);
/*  47 */       if ((pos + materialVarLen) + materialCount * 9L > buf.readableBytes())
/*  48 */         throw ProtocolException.bufferTooSmall("Material", pos + materialVarLen + materialCount * 9, buf.readableBytes()); 
/*  49 */       pos += materialVarLen;
/*  50 */       obj.material = new MaterialQuantity[materialCount];
/*  51 */       for (int i = 0; i < materialCount; i++) {
/*  52 */         obj.material[i] = MaterialQuantity.deserialize(buf, pos);
/*  53 */         pos += MaterialQuantity.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     byte nullBits = buf.getByte(offset);
/*  61 */     int pos = offset + 9;
/*  62 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  63 */       for (int i = 0; i < arrLen; ) { pos += MaterialQuantity.computeBytesConsumed(buf, pos); i++; }  }
/*  64 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  69 */     byte nullBits = 0;
/*  70 */     if (this.material != null) nullBits = (byte)(nullBits | 0x1); 
/*  71 */     buf.writeByte(nullBits);
/*     */     
/*  73 */     buf.writeDoubleLE(this.timeSeconds);
/*     */     
/*  75 */     if (this.material != null) { if (this.material.length > 4096000) throw ProtocolException.arrayTooLong("Material", this.material.length, 4096000);  VarInt.write(buf, this.material.length); for (MaterialQuantity item : this.material) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  80 */     int size = 9;
/*  81 */     if (this.material != null) {
/*  82 */       int materialSize = 0;
/*  83 */       for (MaterialQuantity elem : this.material) materialSize += elem.computeSize(); 
/*  84 */       size += VarInt.size(this.material.length) + materialSize;
/*     */     } 
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
/* 100 */       int materialCount = VarInt.peek(buffer, pos);
/* 101 */       if (materialCount < 0) {
/* 102 */         return ValidationResult.error("Invalid array count for Material");
/*     */       }
/* 104 */       if (materialCount > 4096000) {
/* 105 */         return ValidationResult.error("Material exceeds max length 4096000");
/*     */       }
/* 107 */       pos += VarInt.length(buffer, pos);
/* 108 */       for (int i = 0; i < materialCount; i++) {
/* 109 */         ValidationResult structResult = MaterialQuantity.validateStructure(buffer, pos);
/* 110 */         if (!structResult.isValid()) {
/* 111 */           return ValidationResult.error("Invalid MaterialQuantity in Material[" + i + "]: " + structResult.error());
/*     */         }
/* 113 */         pos += MaterialQuantity.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 116 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BenchUpgradeRequirement clone() {
/* 120 */     BenchUpgradeRequirement copy = new BenchUpgradeRequirement();
/* 121 */     copy.material = (this.material != null) ? (MaterialQuantity[])Arrays.<MaterialQuantity>stream(this.material).map(e -> e.clone()).toArray(x$0 -> new MaterialQuantity[x$0]) : null;
/* 122 */     copy.timeSeconds = this.timeSeconds;
/* 123 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BenchUpgradeRequirement other;
/* 129 */     if (this == obj) return true; 
/* 130 */     if (obj instanceof BenchUpgradeRequirement) { other = (BenchUpgradeRequirement)obj; } else { return false; }
/* 131 */      return (Arrays.equals((Object[])this.material, (Object[])other.material) && this.timeSeconds == other.timeSeconds);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 136 */     int result = 1;
/* 137 */     result = 31 * result + Arrays.hashCode((Object[])this.material);
/* 138 */     result = 31 * result + Double.hashCode(this.timeSeconds);
/* 139 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BenchUpgradeRequirement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */