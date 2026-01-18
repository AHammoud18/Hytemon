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
/*     */ public class SelectedHitEntity
/*     */ {
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 53;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 53;
/*     */   public static final int MAX_SIZE = 53;
/*     */   public int networkId;
/*     */   @Nullable
/*     */   public Vector3f hitLocation;
/*     */   @Nullable
/*     */   public Position position;
/*     */   @Nullable
/*     */   public Direction bodyRotation;
/*     */   
/*     */   public SelectedHitEntity() {}
/*     */   
/*     */   public SelectedHitEntity(int networkId, @Nullable Vector3f hitLocation, @Nullable Position position, @Nullable Direction bodyRotation) {
/*  29 */     this.networkId = networkId;
/*  30 */     this.hitLocation = hitLocation;
/*  31 */     this.position = position;
/*  32 */     this.bodyRotation = bodyRotation;
/*     */   }
/*     */   
/*     */   public SelectedHitEntity(@Nonnull SelectedHitEntity other) {
/*  36 */     this.networkId = other.networkId;
/*  37 */     this.hitLocation = other.hitLocation;
/*  38 */     this.position = other.position;
/*  39 */     this.bodyRotation = other.bodyRotation;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SelectedHitEntity deserialize(@Nonnull ByteBuf buf, int offset) {
/*  44 */     SelectedHitEntity obj = new SelectedHitEntity();
/*  45 */     byte nullBits = buf.getByte(offset);
/*  46 */     obj.networkId = buf.getIntLE(offset + 1);
/*  47 */     if ((nullBits & 0x1) != 0) obj.hitLocation = Vector3f.deserialize(buf, offset + 5); 
/*  48 */     if ((nullBits & 0x2) != 0) obj.position = Position.deserialize(buf, offset + 17); 
/*  49 */     if ((nullBits & 0x4) != 0) obj.bodyRotation = Direction.deserialize(buf, offset + 41);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 53;
/*     */   }
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  60 */     byte nullBits = 0;
/*  61 */     if (this.hitLocation != null) nullBits = (byte)(nullBits | 0x1); 
/*  62 */     if (this.position != null) nullBits = (byte)(nullBits | 0x2); 
/*  63 */     if (this.bodyRotation != null) nullBits = (byte)(nullBits | 0x4); 
/*  64 */     buf.writeByte(nullBits);
/*     */     
/*  66 */     buf.writeIntLE(this.networkId);
/*  67 */     if (this.hitLocation != null) { this.hitLocation.serialize(buf); } else { buf.writeZero(12); }
/*  68 */      if (this.position != null) { this.position.serialize(buf); } else { buf.writeZero(24); }
/*  69 */      if (this.bodyRotation != null) { this.bodyRotation.serialize(buf); } else { buf.writeZero(12); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  75 */     return 53;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  79 */     if (buffer.readableBytes() - offset < 53) {
/*  80 */       return ValidationResult.error("Buffer too small: expected at least 53 bytes");
/*     */     }
/*     */ 
/*     */     
/*  84 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SelectedHitEntity clone() {
/*  88 */     SelectedHitEntity copy = new SelectedHitEntity();
/*  89 */     copy.networkId = this.networkId;
/*  90 */     copy.hitLocation = (this.hitLocation != null) ? this.hitLocation.clone() : null;
/*  91 */     copy.position = (this.position != null) ? this.position.clone() : null;
/*  92 */     copy.bodyRotation = (this.bodyRotation != null) ? this.bodyRotation.clone() : null;
/*  93 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SelectedHitEntity other;
/*  99 */     if (this == obj) return true; 
/* 100 */     if (obj instanceof SelectedHitEntity) { other = (SelectedHitEntity)obj; } else { return false; }
/* 101 */      return (this.networkId == other.networkId && Objects.equals(this.hitLocation, other.hitLocation) && Objects.equals(this.position, other.position) && Objects.equals(this.bodyRotation, other.bodyRotation));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     return Objects.hash(new Object[] { Integer.valueOf(this.networkId), this.hitLocation, this.position, this.bodyRotation });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\SelectedHitEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */