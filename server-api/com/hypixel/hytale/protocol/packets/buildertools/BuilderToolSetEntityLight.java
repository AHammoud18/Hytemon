/*     */ package com.hypixel.hytale.protocol.packets.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ColorLight;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BuilderToolSetEntityLight
/*     */   implements Packet {
/*     */   public static final int PACKET_ID = 422;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 9;
/*     */   public int entityId;
/*     */   @Nullable
/*     */   public ColorLight light;
/*     */   
/*     */   public int getId() {
/*  25 */     return 422;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolSetEntityLight() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderToolSetEntityLight(int entityId, @Nullable ColorLight light) {
/*  35 */     this.entityId = entityId;
/*  36 */     this.light = light;
/*     */   }
/*     */   
/*     */   public BuilderToolSetEntityLight(@Nonnull BuilderToolSetEntityLight other) {
/*  40 */     this.entityId = other.entityId;
/*  41 */     this.light = other.light;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static BuilderToolSetEntityLight deserialize(@Nonnull ByteBuf buf, int offset) {
/*  46 */     BuilderToolSetEntityLight obj = new BuilderToolSetEntityLight();
/*  47 */     byte nullBits = buf.getByte(offset);
/*  48 */     obj.entityId = buf.getIntLE(offset + 1);
/*  49 */     if ((nullBits & 0x1) != 0) obj.light = ColorLight.deserialize(buf, offset + 5);
/*     */ 
/*     */     
/*  52 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  56 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  61 */     byte nullBits = 0;
/*  62 */     if (this.light != null) nullBits = (byte)(nullBits | 0x1); 
/*  63 */     buf.writeByte(nullBits);
/*     */     
/*  65 */     buf.writeIntLE(this.entityId);
/*  66 */     if (this.light != null) { this.light.serialize(buf); } else { buf.writeZero(4); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  72 */     return 9;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  76 */     if (buffer.readableBytes() - offset < 9) {
/*  77 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */ 
/*     */     
/*  81 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public BuilderToolSetEntityLight clone() {
/*  85 */     BuilderToolSetEntityLight copy = new BuilderToolSetEntityLight();
/*  86 */     copy.entityId = this.entityId;
/*  87 */     copy.light = (this.light != null) ? this.light.clone() : null;
/*  88 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     BuilderToolSetEntityLight other;
/*  94 */     if (this == obj) return true; 
/*  95 */     if (obj instanceof BuilderToolSetEntityLight) { other = (BuilderToolSetEntityLight)obj; } else { return false; }
/*  96 */      return (this.entityId == other.entityId && Objects.equals(this.light, other.light));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 101 */     return Objects.hash(new Object[] { Integer.valueOf(this.entityId), this.light });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\buildertools\BuilderToolSetEntityLight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */