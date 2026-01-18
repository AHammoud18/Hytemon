/*    */ package com.hypixel.hytale.server.core.asset.type.model.config.camera;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.CameraSettings;
/*    */ import com.hypixel.hytale.protocol.Vector3f;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CameraSettings
/*    */   implements NetworkSerializable<CameraSettings>
/*    */ {
/*    */   public static final BuilderCodec<CameraSettings> CODEC;
/*    */   @Nullable
/*    */   protected Vector3f positionOffset;
/*    */   protected CameraAxis yaw;
/*    */   protected CameraAxis pitch;
/*    */   
/*    */   static {
/* 29 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CameraSettings.class, CameraSettings::new).addField(new KeyedCodec("PositionOffset", (Codec)ProtocolCodecs.VECTOR3F), (cameraSettings, s) -> cameraSettings.positionOffset = s, cameraSettings -> cameraSettings.positionOffset)).addField(new KeyedCodec("Yaw", (Codec)CameraAxis.CODEC), (cameraSettings, s) -> cameraSettings.yaw = s, cameraSettings -> cameraSettings.yaw)).addField(new KeyedCodec("Pitch", (Codec)CameraAxis.CODEC), (cameraSettings, s) -> cameraSettings.pitch = s, cameraSettings -> cameraSettings.pitch)).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected CameraSettings() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public CameraSettings(Vector3f positionOffset, CameraAxis yaw, CameraAxis pitch) {
/* 40 */     this.positionOffset = positionOffset;
/* 41 */     this.yaw = yaw;
/* 42 */     this.pitch = pitch;
/*    */   }
/*    */   
/*    */   public CameraSettings(CameraSettings other) {
/* 46 */     this.positionOffset = (other.positionOffset != null) ? new Vector3f(other.positionOffset) : null;
/* 47 */     this.yaw = other.yaw;
/* 48 */     this.pitch = other.pitch;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CameraSettings toPacket() {
/* 54 */     CameraSettings packet = new CameraSettings();
/* 55 */     packet.positionOffset = this.positionOffset;
/* 56 */     if (this.yaw != null) packet.yaw = this.yaw.toPacket(); 
/* 57 */     if (this.pitch != null) packet.pitch = this.pitch.toPacket(); 
/* 58 */     return packet;
/*    */   }
/*    */   
/*    */   public Vector3f getPositionOffset() {
/* 62 */     return this.positionOffset;
/*    */   }
/*    */   
/*    */   public CameraAxis getYaw() {
/* 66 */     return this.yaw;
/*    */   }
/*    */   
/*    */   public CameraAxis getPitch() {
/* 70 */     return this.pitch;
/*    */   }
/*    */   
/*    */   public CameraSettings scale(float scale) {
/* 74 */     if (this.positionOffset != null) {
/* 75 */       this.positionOffset.x *= scale;
/* 76 */       this.positionOffset.y *= scale;
/* 77 */       this.positionOffset.z *= scale;
/*    */     } 
/* 79 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 85 */     return "CameraSettings{positionOffset=" + String.valueOf(this.positionOffset) + ", yaw=" + String.valueOf(this.yaw) + ", pitch=" + String.valueOf(this.pitch) + "}";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CameraSettings clone() {
/* 94 */     return new CameraSettings(this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\camera\CameraSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */