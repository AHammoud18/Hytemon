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
/*     */ public class RaycastSelector
/*     */   extends Selector
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 23;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 23;
/*     */   public static final int MAX_SIZE = 23;
/*     */   @Nullable
/*     */   public Vector3f offset;
/*     */   public int distance;
/*  22 */   public int blockTagIndex = Integer.MIN_VALUE;
/*     */   
/*     */   public boolean ignoreFluids;
/*     */   
/*     */   public boolean ignoreEmptyCollisionMaterial;
/*     */ 
/*     */   
/*     */   public RaycastSelector(@Nullable Vector3f offset, int distance, int blockTagIndex, boolean ignoreFluids, boolean ignoreEmptyCollisionMaterial) {
/*  30 */     this.offset = offset;
/*  31 */     this.distance = distance;
/*  32 */     this.blockTagIndex = blockTagIndex;
/*  33 */     this.ignoreFluids = ignoreFluids;
/*  34 */     this.ignoreEmptyCollisionMaterial = ignoreEmptyCollisionMaterial;
/*     */   }
/*     */   
/*     */   public RaycastSelector(@Nonnull RaycastSelector other) {
/*  38 */     this.offset = other.offset;
/*  39 */     this.distance = other.distance;
/*  40 */     this.blockTagIndex = other.blockTagIndex;
/*  41 */     this.ignoreFluids = other.ignoreFluids;
/*  42 */     this.ignoreEmptyCollisionMaterial = other.ignoreEmptyCollisionMaterial;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static RaycastSelector deserialize(@Nonnull ByteBuf buf, int offset) {
/*  47 */     RaycastSelector obj = new RaycastSelector();
/*  48 */     byte nullBits = buf.getByte(offset);
/*  49 */     if ((nullBits & 0x1) != 0) obj.offset = Vector3f.deserialize(buf, offset + 1); 
/*  50 */     obj.distance = buf.getIntLE(offset + 13);
/*  51 */     obj.blockTagIndex = buf.getIntLE(offset + 17);
/*  52 */     obj.ignoreFluids = (buf.getByte(offset + 21) != 0);
/*  53 */     obj.ignoreEmptyCollisionMaterial = (buf.getByte(offset + 22) != 0);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 23;
/*     */   }
/*     */ 
/*     */   
/*     */   public int serialize(@Nonnull ByteBuf buf) {
/*  65 */     int startPos = buf.writerIndex();
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.offset != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     if (this.offset != null) { this.offset.serialize(buf); } else { buf.writeZero(12); }
/*  71 */      buf.writeIntLE(this.distance);
/*  72 */     buf.writeIntLE(this.blockTagIndex);
/*  73 */     buf.writeByte(this.ignoreFluids ? 1 : 0);
/*  74 */     buf.writeByte(this.ignoreEmptyCollisionMaterial ? 1 : 0);
/*     */     
/*  76 */     return buf.writerIndex() - startPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  82 */     return 23;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  86 */     if (buffer.readableBytes() - offset < 23) {
/*  87 */       return ValidationResult.error("Buffer too small: expected at least 23 bytes");
/*     */     }
/*     */ 
/*     */     
/*  91 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public RaycastSelector clone() {
/*  95 */     RaycastSelector copy = new RaycastSelector();
/*  96 */     copy.offset = (this.offset != null) ? this.offset.clone() : null;
/*  97 */     copy.distance = this.distance;
/*  98 */     copy.blockTagIndex = this.blockTagIndex;
/*  99 */     copy.ignoreFluids = this.ignoreFluids;
/* 100 */     copy.ignoreEmptyCollisionMaterial = this.ignoreEmptyCollisionMaterial;
/* 101 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     RaycastSelector other;
/* 107 */     if (this == obj) return true; 
/* 108 */     if (obj instanceof RaycastSelector) { other = (RaycastSelector)obj; } else { return false; }
/* 109 */      return (Objects.equals(this.offset, other.offset) && this.distance == other.distance && this.blockTagIndex == other.blockTagIndex && this.ignoreFluids == other.ignoreFluids && this.ignoreEmptyCollisionMaterial == other.ignoreEmptyCollisionMaterial);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 114 */     return Objects.hash(new Object[] { this.offset, Integer.valueOf(this.distance), Integer.valueOf(this.blockTagIndex), Boolean.valueOf(this.ignoreFluids), Boolean.valueOf(this.ignoreEmptyCollisionMaterial) });
/*     */   }
/*     */   
/*     */   public RaycastSelector() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\RaycastSelector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */