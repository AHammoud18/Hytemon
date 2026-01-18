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
/*     */ public class Bench
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 1;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 1;
/*     */   public static final int MAX_SIZE = 1677721600;
/*     */   @Nullable
/*     */   public BenchTierLevel[] benchTierLevels;
/*     */   
/*     */   public Bench() {}
/*     */   
/*     */   public Bench(@Nullable BenchTierLevel[] benchTierLevels) {
/*  26 */     this.benchTierLevels = benchTierLevels;
/*     */   }
/*     */   
/*     */   public Bench(@Nonnull Bench other) {
/*  30 */     this.benchTierLevels = other.benchTierLevels;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Bench deserialize(@Nonnull ByteBuf buf, int offset) {
/*  35 */     Bench obj = new Bench();
/*  36 */     byte nullBits = buf.getByte(offset);
/*     */     
/*  38 */     int pos = offset + 1;
/*  39 */     if ((nullBits & 0x1) != 0) { int benchTierLevelsCount = VarInt.peek(buf, pos);
/*  40 */       if (benchTierLevelsCount < 0) throw ProtocolException.negativeLength("BenchTierLevels", benchTierLevelsCount); 
/*  41 */       if (benchTierLevelsCount > 4096000) throw ProtocolException.arrayTooLong("BenchTierLevels", benchTierLevelsCount, 4096000); 
/*  42 */       int benchTierLevelsVarLen = VarInt.size(benchTierLevelsCount);
/*  43 */       if ((pos + benchTierLevelsVarLen) + benchTierLevelsCount * 17L > buf.readableBytes())
/*  44 */         throw ProtocolException.bufferTooSmall("BenchTierLevels", pos + benchTierLevelsVarLen + benchTierLevelsCount * 17, buf.readableBytes()); 
/*  45 */       pos += benchTierLevelsVarLen;
/*  46 */       obj.benchTierLevels = new BenchTierLevel[benchTierLevelsCount];
/*  47 */       for (int i = 0; i < benchTierLevelsCount; i++) {
/*  48 */         obj.benchTierLevels[i] = BenchTierLevel.deserialize(buf, pos);
/*  49 */         pos += BenchTierLevel.computeBytesConsumed(buf, pos);
/*     */       }  }
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     byte nullBits = buf.getByte(offset);
/*  57 */     int pos = offset + 1;
/*  58 */     if ((nullBits & 0x1) != 0) { int arrLen = VarInt.peek(buf, pos); pos += VarInt.length(buf, pos);
/*  59 */       for (int i = 0; i < arrLen; ) { pos += BenchTierLevel.computeBytesConsumed(buf, pos); i++; }  }
/*  60 */      return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  65 */     byte nullBits = 0;
/*  66 */     if (this.benchTierLevels != null) nullBits = (byte)(nullBits | 0x1); 
/*  67 */     buf.writeByte(nullBits);
/*     */ 
/*     */     
/*  70 */     if (this.benchTierLevels != null) { if (this.benchTierLevels.length > 4096000) throw ProtocolException.arrayTooLong("BenchTierLevels", this.benchTierLevels.length, 4096000);  VarInt.write(buf, this.benchTierLevels.length); for (BenchTierLevel item : this.benchTierLevels) item.serialize(buf);  }
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  75 */     int size = 1;
/*  76 */     if (this.benchTierLevels != null) {
/*  77 */       int benchTierLevelsSize = 0;
/*  78 */       for (BenchTierLevel elem : this.benchTierLevels) benchTierLevelsSize += elem.computeSize(); 
/*  79 */       size += VarInt.size(this.benchTierLevels.length) + benchTierLevelsSize;
/*     */     } 
/*     */     
/*  82 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 1) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 1 bytes");
/*     */     }
/*     */     
/*  90 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  92 */     int pos = offset + 1;
/*     */     
/*  94 */     if ((nullBits & 0x1) != 0) {
/*  95 */       int benchTierLevelsCount = VarInt.peek(buffer, pos);
/*  96 */       if (benchTierLevelsCount < 0) {
/*  97 */         return ValidationResult.error("Invalid array count for BenchTierLevels");
/*     */       }
/*  99 */       if (benchTierLevelsCount > 4096000) {
/* 100 */         return ValidationResult.error("BenchTierLevels exceeds max length 4096000");
/*     */       }
/* 102 */       pos += VarInt.length(buffer, pos);
/* 103 */       for (int i = 0; i < benchTierLevelsCount; i++) {
/* 104 */         ValidationResult structResult = BenchTierLevel.validateStructure(buffer, pos);
/* 105 */         if (!structResult.isValid()) {
/* 106 */           return ValidationResult.error("Invalid BenchTierLevel in BenchTierLevels[" + i + "]: " + structResult.error());
/*     */         }
/* 108 */         pos += BenchTierLevel.computeBytesConsumed(buffer, pos);
/*     */       } 
/*     */     } 
/* 111 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public Bench clone() {
/* 115 */     Bench copy = new Bench();
/* 116 */     copy.benchTierLevels = (this.benchTierLevels != null) ? (BenchTierLevel[])Arrays.<BenchTierLevel>stream(this.benchTierLevels).map(e -> e.clone()).toArray(x$0 -> new BenchTierLevel[x$0]) : null;
/* 117 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     Bench other;
/* 123 */     if (this == obj) return true; 
/* 124 */     if (obj instanceof Bench) { other = (Bench)obj; } else { return false; }
/* 125 */      return Arrays.equals((Object[])this.benchTierLevels, (Object[])other.benchTierLevels);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 130 */     int result = 1;
/* 131 */     result = 31 * result + Arrays.hashCode((Object[])this.benchTierLevels);
/* 132 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Bench.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */