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
/*     */ public class BlockMount
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 30;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 30;
/*     */   public static final int MAX_SIZE = 30;
/*     */   @Nonnull
/*  20 */   public BlockMountType type = BlockMountType.Seat;
/*     */   
/*     */   @Nullable
/*     */   public Vector3f position;
/*     */   @Nullable
/*     */   public Vector3f orientation;
/*     */   public int blockTypeId;
/*     */   
/*     */   public BlockMount(@Nonnull BlockMountType type, @Nullable Vector3f position, @Nullable Vector3f orientation, int blockTypeId) {
/*  29 */     this.type = type;
/*  30 */     this.position = position;
/*  31 */     this.orientation = orientation;
/*  32 */     this.blockTypeId = blockTypeId;
/*     */   }
/*     */   
/*     */   public BlockMount(@Nonnull BlockMount other) {
/*  36 */     this.type = other.type;
/*  37 */     this.position = other.position;
/*  38 */     this.orientation = other.orientation;
/*  39 */     this.blockTypeId = other.blockTypeId;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BlockMount deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     BlockMount obj = new BlockMount();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.type = BlockMountType.fromValue(buf.getByte(offset + 1));
/*  47 */     if ((nullBits & 0x1) != 0) obj.position = Vector3f.deserialize(buf, offset + 2); 
/*  48 */     if ((nullBits & 0x2) != 0) obj.orientation = Vector3f.deserialize(buf, offset + 14); 
/*  49 */     obj.blockTypeId = buf.getIntLE(offset + 26);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 30;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  60 */     byte nullBits = 0;
/*  61 */     if (this.position != null) nullBits = (byte)(nullBits | 0x1); 
/*  62 */     if (this.orientation != null) nullBits = (byte)(nullBits | 0x2); 
/*  63 */     buf.writeByte(nullBits);
/*     */     
/*  65 */     buf.writeByte(this.type.getValue());
/*  66 */     if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(12); }
/*  67 */      if (this.orientation != null) { this.orientation.serialize(buf); } else { buf.writeZero(12); }
/*  68 */      buf.writeIntLE(this.blockTypeId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     return 30;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  78 */     if (buffer.readableBytes() - offset < 30) {
/*  79 */       return ValidationResult.error("Buffer too small: expected at least 30 bytes");
/*     */     }
/*     */ 
/*     */     
/*  83 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BlockMount clone() {
/*  87 */     BlockMount copy = new BlockMount();
/*  88 */     copy.type = this.type;
/*  89 */     copy.position = (this.position != null) ? this.position.clone() : null;
/*  90 */     copy.orientation = (this.orientation != null) ? this.orientation.clone() : null;
/*  91 */     copy.blockTypeId = this.blockTypeId;
/*  92 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BlockMount other;
/*  98 */     if (this == obj) return true; 
/*  99 */     if (obj instanceof BlockMount) { other = (BlockMount)obj; } else { return false; }
/* 100 */      return (Objects.equals(this.type, other.type) && Objects.equals(this.position, other.position) && Objects.equals(this.orientation, other.orientation) && this.blockTypeId == other.blockTypeId);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 105 */     return Objects.hash(new Object[] { this.type, this.position, this.orientation, Integer.valueOf(this.blockTypeId) });
/*     */   }
/*     */   
/*     */   public BlockMount() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockMount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */