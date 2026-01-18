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
/*     */ public class BlockMatcher
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 3;
/*     */   public static final int VARIABLE_FIELD_COUNT = 1;
/*     */   public static final int VARIABLE_BLOCK_START = 3;
/*     */   public static final int MAX_SIZE = 32768026;
/*     */   @Nullable
/*     */   public BlockIdMatcher block;
/*     */   @Nonnull
/*  21 */   public BlockFace face = BlockFace.None;
/*     */ 
/*     */   
/*     */   public boolean staticFace;
/*     */ 
/*     */   
/*     */   public BlockMatcher(@Nullable BlockIdMatcher block, @Nonnull BlockFace face, boolean staticFace) {
/*  28 */     this.block = block;
/*  29 */     this.face = face;
/*  30 */     this.staticFace = staticFace;
/*     */   }
/*     */   
/*     */   public BlockMatcher(@Nonnull BlockMatcher other) {
/*  34 */     this.block = other.block;
/*  35 */     this.face = other.face;
/*  36 */     this.staticFace = other.staticFace;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockMatcher deserialize(@Nonnull ByteBuf buf, int offset) {
/*  41 */     BlockMatcher obj = new BlockMatcher();
/*  42 */     byte nullBits = buf.getByte(offset);
/*  43 */     obj.face = BlockFace.fromValue(buf.getByte(offset + 1));
/*  44 */     obj.staticFace = (buf.getByte(offset + 2) != 0);
/*     */     
/*  46 */     int pos = offset + 3;
/*  47 */     if ((nullBits & 0x1) != 0) { obj.block = BlockIdMatcher.deserialize(buf, pos);
/*  48 */       pos += BlockIdMatcher.computeBytesConsumed(buf, pos); }
/*     */     
/*  50 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  54 */     byte nullBits = buf.getByte(offset);
/*  55 */     int pos = offset + 3;
/*  56 */     if ((nullBits & 0x1) != 0) pos += BlockIdMatcher.computeBytesConsumed(buf, pos); 
/*  57 */     return pos - offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  62 */     byte nullBits = 0;
/*  63 */     if (this.block != null) nullBits = (byte)(nullBits | 0x1); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeByte(this.face.getValue());
/*  67 */     buf.writeByte(this.staticFace ? 1 : 0);
/*     */     
/*  69 */     if (this.block != null) this.block.serialize(buf);
/*     */   
/*     */   }
/*     */   
/*     */   public int computeSize() {
/*  74 */     int size = 3;
/*  75 */     if (this.block != null) size += this.block.computeSize();
/*     */     
/*  77 */     return size;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 3) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 3 bytes");
/*     */     }
/*     */     
/*  85 */     byte nullBits = buffer.getByte(offset);
/*     */     
/*  87 */     int pos = offset + 3;
/*     */     
/*  89 */     if ((nullBits & 0x1) != 0) {
/*  90 */       ValidationResult blockResult = BlockIdMatcher.validateStructure(buffer, pos);
/*  91 */       if (!blockResult.isValid()) {
/*  92 */         return ValidationResult.error("Invalid Block: " + blockResult.error());
/*     */       }
/*  94 */       pos += BlockIdMatcher.computeBytesConsumed(buffer, pos);
/*     */     } 
/*  96 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockMatcher clone() {
/* 100 */     BlockMatcher copy = new BlockMatcher();
/* 101 */     copy.block = (this.block != null) ? this.block.clone() : null;
/* 102 */     copy.face = this.face;
/* 103 */     copy.staticFace = this.staticFace;
/* 104 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockMatcher other;
/* 110 */     if (this == obj) return true; 
/* 111 */     if (obj instanceof BlockMatcher) { other = (BlockMatcher)obj; } else { return false; }
/* 112 */      return (Objects.equals(this.block, other.block) && Objects.equals(this.face, other.face) && this.staticFace == other.staticFace);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     return Objects.hash(new Object[] { this.block, this.face, Boolean.valueOf(this.staticFace) });
/*     */   }
/*     */   
/*     */   public BlockMatcher() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockMatcher.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */