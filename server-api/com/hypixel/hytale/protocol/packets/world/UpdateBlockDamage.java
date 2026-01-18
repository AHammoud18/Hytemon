/*     */ package com.hypixel.hytale.protocol.packets.world;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class UpdateBlockDamage implements Packet {
/*     */   public static final int PACKET_ID = 144;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 21;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 21;
/*     */   public static final int MAX_SIZE = 21;
/*     */   @Nullable
/*     */   public BlockPosition blockPosition;
/*     */   public float damage;
/*     */   public float delta;
/*     */   
/*     */   public int getId() {
/*  25 */     return 144;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateBlockDamage() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdateBlockDamage(@Nullable BlockPosition blockPosition, float damage, float delta) {
/*  36 */     this.blockPosition = blockPosition;
/*  37 */     this.damage = damage;
/*  38 */     this.delta = delta;
/*     */   }
/*     */   
/*     */   public UpdateBlockDamage(@Nonnull UpdateBlockDamage other) {
/*  42 */     this.blockPosition = other.blockPosition;
/*  43 */     this.damage = other.damage;
/*  44 */     this.delta = other.delta;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static UpdateBlockDamage deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     UpdateBlockDamage obj = new UpdateBlockDamage();
/*  50 */     byte nullBits = buf.getByte(offset);
/*  51 */     if ((nullBits & 0x1) != 0) obj.blockPosition = BlockPosition.deserialize(buf, offset + 1); 
/*  52 */     obj.damage = buf.getFloatLE(offset + 13);
/*  53 */     obj.delta = buf.getFloatLE(offset + 17);
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 21;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  65 */     byte nullBits = 0;
/*  66 */     if (this.blockPosition != null) nullBits = (byte)(nullBits | 0x1); 
/*  67 */     buf.writeByte(nullBits);
/*     */     
/*  69 */     if (this.blockPosition != null) { this.blockPosition.serialize(buf); } else { buf.writeZero(12); }
/*  70 */      buf.writeFloatLE(this.damage);
/*  71 */     buf.writeFloatLE(this.delta);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  77 */     return 21;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  81 */     if (buffer.readableBytes() - offset < 21) {
/*  82 */       return ValidationResult.error("Buffer too small: expected at least 21 bytes");
/*     */     }
/*     */ 
/*     */     
/*  86 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public UpdateBlockDamage clone() {
/*  90 */     UpdateBlockDamage copy = new UpdateBlockDamage();
/*  91 */     copy.blockPosition = (this.blockPosition != null) ? this.blockPosition.clone() : null;
/*  92 */     copy.damage = this.damage;
/*  93 */     copy.delta = this.delta;
/*  94 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     UpdateBlockDamage other;
/* 100 */     if (this == obj) return true; 
/* 101 */     if (obj instanceof UpdateBlockDamage) { other = (UpdateBlockDamage)obj; } else { return false; }
/* 102 */      return (Objects.equals(this.blockPosition, other.blockPosition) && this.damage == other.damage && this.delta == other.delta);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 107 */     return Objects.hash(new Object[] { this.blockPosition, Float.valueOf(this.damage), Float.valueOf(this.delta) });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\world\UpdateBlockDamage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */