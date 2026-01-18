/*     */ package com.hypixel.hytale.protocol.packets.camera;
/*     */ 
/*     */ import com.hypixel.hytale.protocol.ClientCameraView;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.ServerCameraSettings;
/*     */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SetServerCamera
/*     */   implements Packet
/*     */ {
/*     */   public static final int PACKET_ID = 280;
/*     */   public static final boolean IS_COMPRESSED = false;
/*     */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*     */   public static final int FIXED_BLOCK_SIZE = 157;
/*     */   public static final int VARIABLE_FIELD_COUNT = 0;
/*     */   public static final int VARIABLE_BLOCK_START = 157;
/*     */   public static final int MAX_SIZE = 157;
/*     */   
/*     */   public int getId() {
/*  26 */     return 280;
/*     */   }
/*     */   @Nonnull
/*  29 */   public ClientCameraView clientCameraView = ClientCameraView.FirstPerson;
/*     */   
/*     */   public boolean isLocked;
/*     */   
/*     */   @Nullable
/*     */   public ServerCameraSettings cameraSettings;
/*     */   
/*     */   public SetServerCamera(@Nonnull ClientCameraView clientCameraView, boolean isLocked, @Nullable ServerCameraSettings cameraSettings) {
/*  37 */     this.clientCameraView = clientCameraView;
/*  38 */     this.isLocked = isLocked;
/*  39 */     this.cameraSettings = cameraSettings;
/*     */   }
/*     */   
/*     */   public SetServerCamera(@Nonnull SetServerCamera other) {
/*  43 */     this.clientCameraView = other.clientCameraView;
/*  44 */     this.isLocked = other.isLocked;
/*  45 */     this.cameraSettings = other.cameraSettings;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SetServerCamera deserialize(@Nonnull ByteBuf buf, int offset) {
/*  50 */     SetServerCamera obj = new SetServerCamera();
/*  51 */     byte nullBits = buf.getByte(offset);
/*  52 */     obj.clientCameraView = ClientCameraView.fromValue(buf.getByte(offset + 1));
/*  53 */     obj.isLocked = (buf.getByte(offset + 2) != 0);
/*  54 */     if ((nullBits & 0x1) != 0) obj.cameraSettings = ServerCameraSettings.deserialize(buf, offset + 3);
/*     */ 
/*     */     
/*  57 */     return obj;
/*     */   }
/*     */   
/*     */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  61 */     return 157;
/*     */   }
/*     */ 
/*     */   
/*     */   public void serialize(@Nonnull ByteBuf buf) {
/*  66 */     byte nullBits = 0;
/*  67 */     if (this.cameraSettings != null) nullBits = (byte)(nullBits | 0x1); 
/*  68 */     buf.writeByte(nullBits);
/*     */     
/*  70 */     buf.writeByte(this.clientCameraView.getValue());
/*  71 */     buf.writeByte(this.isLocked ? 1 : 0);
/*  72 */     if (this.cameraSettings != null) { this.cameraSettings.serialize(buf); } else { buf.writeZero(154); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public int computeSize() {
/*  78 */     return 157;
/*     */   }
/*     */   
/*     */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  82 */     if (buffer.readableBytes() - offset < 157) {
/*  83 */       return ValidationResult.error("Buffer too small: expected at least 157 bytes");
/*     */     }
/*     */ 
/*     */     
/*  87 */     return ValidationResult.OK;
/*     */   }
/*     */   
/*     */   public SetServerCamera clone() {
/*  91 */     SetServerCamera copy = new SetServerCamera();
/*  92 */     copy.clientCameraView = this.clientCameraView;
/*  93 */     copy.isLocked = this.isLocked;
/*  94 */     copy.cameraSettings = (this.cameraSettings != null) ? this.cameraSettings.clone() : null;
/*  95 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     SetServerCamera other;
/* 101 */     if (this == obj) return true; 
/* 102 */     if (obj instanceof SetServerCamera) { other = (SetServerCamera)obj; } else { return false; }
/* 103 */      return (Objects.equals(this.clientCameraView, other.clientCameraView) && this.isLocked == other.isLocked && Objects.equals(this.cameraSettings, other.cameraSettings));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 108 */     return Objects.hash(new Object[] { this.clientCameraView, Boolean.valueOf(this.isLocked), this.cameraSettings });
/*     */   }
/*     */   
/*     */   public SetServerCamera() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\camera\SetServerCamera.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */