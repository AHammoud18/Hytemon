/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ModelTransform;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BuilderToolSetEntityTransform
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 402;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 54;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 54;
/*     */   public static final int MAX_SIZE = 54;
/*     */   public int entityId;
/*     */   @Nullable
/*     */   public ModelTransform modelTransform;
/*     */   
/*     */   public int getId() {
/*  25 */     return 402;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolSetEntityTransform() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolSetEntityTransform(int entityId, @Nullable ModelTransform modelTransform) {
/*  35 */     this.entityId = entityId;
/*  36 */     this.modelTransform = modelTransform;
/*     */   }
/*     */   
/*     */   public BuilderToolSetEntityTransform(@Nonnull BuilderToolSetEntityTransform other) {
/*  40 */     this.entityId = other.entityId;
/*  41 */     this.modelTransform = other.modelTransform;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolSetEntityTransform deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     BuilderToolSetEntityTransform obj = new BuilderToolSetEntityTransform();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.entityId = buf.getIntLE(offset + 1);
/*  49 */     if ((nullBits & 0x1) != 0) obj.modelTransform = ModelTransform.deserialize(buf, offset + 5);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 54;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  61 */     byte nullBits = 0;
/*  62 */     if (this.modelTransform != null) nullBits = (byte)(nullBits | 0x1); 
/*  63 */     buf.writeByte(nullBits);
/*     */     
/*  65 */     buf.writeIntLE(this.entityId);
/*  66 */     if (this.modelTransform != null) { this.modelTransform.serialize(buf); } else { buf.writeZero(49); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  72 */     return 54;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  76 */     if (buffer.readableBytes() - offset < 54) {
/*  77 */       return ValidationResult.error("Buffer too small: expected at least 54 bytes");
/*     */     }
/*     */ 
/*     */     
/*  81 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolSetEntityTransform clone() {
/*  85 */     BuilderToolSetEntityTransform copy = new BuilderToolSetEntityTransform();
/*  86 */     copy.entityId = this.entityId;
/*  87 */     copy.modelTransform = (this.modelTransform != null) ? this.modelTransform.clone() : null;
/*  88 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolSetEntityTransform other;
/*  94 */     if (this == obj) return true; 
/*  95 */     if (obj instanceof BuilderToolSetEntityTransform) { other = (BuilderToolSetEntityTransform)obj; } else { return false; }
/*  96 */      return (this.entityId == other.entityId && Objects.equals(this.modelTransform, other.modelTransform));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), this.modelTransform });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolSetEntityTransform.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */