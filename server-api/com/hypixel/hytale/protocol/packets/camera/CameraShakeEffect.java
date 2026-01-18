/*     */ package com.hypixel.hytale.protocol.packets.camera;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.AccumulationMode;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class CameraShakeEffect
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 281;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 0;
/*     */   public static final int FIXED_BLOCK_SIZE = 9;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 9;
/*     */   public static final int MAX_SIZE = 9;
/*     */   public int cameraShakeId;
/*     */   public float intensity;
/*     */   
/*     */   public int getId() {
/*  25 */     return 281;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*  30 */   public AccumulationMode mode = AccumulationMode.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CameraShakeEffect(int cameraShakeId, float intensity, @Nonnull AccumulationMode mode) {
/*  36 */     this.cameraShakeId = cameraShakeId;
/*  37 */     this.intensity = intensity;
/*  38 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public CameraShakeEffect(@Nonnull CameraShakeEffect other) {
/*  42 */     this.cameraShakeId = other.cameraShakeId;
/*  43 */     this.intensity = other.intensity;
/*  44 */     this.mode = other.mode;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static CameraShakeEffect deserialize(@Nonnull ByteBuf buf, int offset) {
/*  49 */     CameraShakeEffect obj = new CameraShakeEffect();
/*     */     
/*  51 */     obj.cameraShakeId = buf.getIntLE(offset + 0);
/*  52 */     obj.intensity = buf.getFloatLE(offset + 4);
/*  53 */     obj.mode = AccumulationMode.fromValue(buf.getByte(offset + 8));
/*     */ 
/*     */     
/*  56 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  60 */     return 9;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     buf.writeIntLE(this.cameraShakeId);
/*  67 */     buf.writeFloatLE(this.intensity);
/*  68 */     buf.writeByte(this.mode.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  74 */     return 9;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  78 */     if (buffer.readableBytes() - offset < 9) {
/*  79 */       return ValidationResult.error("Buffer too small: expected at least 9 bytes");
/*     */     }
/*     */ 
/*     */     
/*  83 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public CameraShakeEffect clone() {
/*  87 */     CameraShakeEffect copy = new CameraShakeEffect();
/*  88 */     copy.cameraShakeId = this.cameraShakeId;
/*  89 */     copy.intensity = this.intensity;
/*  90 */     copy.mode = this.mode;
/*  91 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     CameraShakeEffect other;
/*  97 */     if (this == obj) return true; 
/*  98 */     if (obj instanceof CameraShakeEffect) { other = (CameraShakeEffect)obj; } else { return false; }
/*  99 */      return (this.cameraShakeId == other.cameraShakeId && this.intensity == other.intensity && Objects.equals(this.mode, other.mode));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return Objects.hash(new Object[] { Integer.valueOf(this.cameraShakeId), Float.valueOf(this.intensity), this.mode });
/*     */   }
/*     */   
/*     */   public CameraShakeEffect() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\camera\CameraShakeEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */