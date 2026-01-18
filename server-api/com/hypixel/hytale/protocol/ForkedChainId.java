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
/*     */ public class ForkedChainId
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 1033;
/*     */   public int entryIndex;
/*     */   public int subIndex;
/*     */   @Nullable
/*     */   public ForkedChainId forkedId;
/*     */   
/*     */   public ForkedChainId() {}
/*     */   
/*     */   public ForkedChainId(int entryIndex, int subIndex, @Nullable ForkedChainId forkedId) {
/*  28 */     this.entryIndex = entryIndex;
/*  29 */     this.subIndex = subIndex;
/*  30 */     this.forkedId = forkedId;
/*     */   }
/*     */   
/*     */   public ForkedChainId(@Nonnull ForkedChainId other) {
/*  34 */     this.entryIndex = other.entryIndex;
/*  35 */     this.subIndex = other.subIndex;
/*  36 */     this.forkedId = other.forkedId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static ForkedChainId deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     ForkedChainId obj = new ForkedChainId();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.entryIndex = buf.getIntLE(offset + 1);
/*  44 */     obj.subIndex = buf.getIntLE(offset + 5);
/*     */     
/*  46 */     int pos = offset + 9;
/*  47 */     if ((nullBits & 0x1) != 0) { obj.forkedId = deserialize(buf, pos);
/*  48 */       pos += computeBytesConsumed(buf, pos); }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 9;
/*  56 */     if ((nullBits & 0x1) != 0) pos += computeBytesConsumed(buf, pos); 
/*  57 */     return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.forkedId != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeIntLE(this.entryIndex);
/*  67 */     buf.writeIntLE(this.subIndex);
/*     */     
/*  69 */     if (this.forkedId != null) this.forkedId.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  74 */     int size = 9;
/*  75 */     if (this.forkedId != null) size += this.forkedId.computeSize();
/*     */     
/*  77 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 9) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */     
/*  85 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  87 */     int pos = offset + 9;
/*     */     
/*  89 */     if ((nullBits & 0x1) != 0) {
/*  90 */       ValidationResult forkedIdResult = validateStructure(buffer, pos);
/*  91 */       if (!forkedIdResult.isValid()) {
/*  92 */         return ValidationResult.error("Invalid ForkedId: " + forkedIdResult.error());
/*     */       }
/*  94 */       pos += computeBytesConsumed(buffer, pos);
/*     */     } 
/*  96 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public ForkedChainId clone() {
/* 100 */     ForkedChainId copy = new ForkedChainId();
/* 101 */     copy.entryIndex = this.entryIndex;
/* 102 */     copy.subIndex = this.subIndex;
/* 103 */     copy.forkedId = (this.forkedId != null) ? this.forkedId.clone() : null;
/* 104 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     ForkedChainId other;
/* 110 */     if (this == obj) return true; 
/* 111 */     if (obj instanceof ForkedChainId) { other = (ForkedChainId)obj; } else { return false; }
/* 112 */      return (this.entryIndex == other.entryIndex && this.subIndex == other.subIndex && Objects.equals(this.forkedId, other.forkedId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return Objects.hash(new Object[] { Integer.valueOf(this.entryIndex), Integer.valueOf(this.subIndex), this.forkedId });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ForkedChainId.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */