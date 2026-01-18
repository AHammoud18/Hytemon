/*    */ package com.hypixel.hytale.protocol.packets.asseteditor;
/*    */ 
/*    */ import com.hypixel.hytale.protocol.Vector3f;
/*    */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AssetEditorPreviewCameraSettings
/*    */ {
/*    */   public static final int NULLABLE_BIT_FIELD_SIZE = 1;
/*    */   public static final int FIXED_BLOCK_SIZE = 29;
/*    */   public static final int VARIABLE_FIELD_COUNT = 0;
/*    */   public static final int VARIABLE_BLOCK_START = 29;
/*    */   public static final int MAX_SIZE = 29;
/*    */   public float modelScale;
/*    */   @Nullable
/*    */   public Vector3f cameraPosition;
/*    */   @Nullable
/*    */   public Vector3f cameraOrientation;
/*    */   
/*    */   public AssetEditorPreviewCameraSettings() {}
/*    */   
/*    */   public AssetEditorPreviewCameraSettings(float modelScale, @Nullable Vector3f cameraPosition, @Nullable Vector3f cameraOrientation) {
/* 28 */     this.modelScale = modelScale;
/* 29 */     this.cameraPosition = cameraPosition;
/* 30 */     this.cameraOrientation = cameraOrientation;
/*    */   }
/*    */   
/*    */   public AssetEditorPreviewCameraSettings(@Nonnull AssetEditorPreviewCameraSettings other) {
/* 34 */     this.modelScale = other.modelScale;
/* 35 */     this.cameraPosition = other.cameraPosition;
/* 36 */     this.cameraOrientation = other.cameraOrientation;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static AssetEditorPreviewCameraSettings deserialize(@Nonnull ByteBuf buf, int offset) {
/* 41 */     AssetEditorPreviewCameraSettings obj = new AssetEditorPreviewCameraSettings();
/* 42 */     byte nullBits = buf.getByte(offset);
/* 43 */     obj.modelScale = buf.getFloatLE(offset + 1);
/* 44 */     if ((nullBits & 0x1) != 0) obj.cameraPosition = Vector3f.deserialize(buf, offset + 5); 
/* 45 */     if ((nullBits & 0x2) != 0) obj.cameraOrientation = Vector3f.deserialize(buf, offset + 17);
/*    */ 
/*    */     
/* 48 */     return obj;
/*    */   }
/*    */   
/*    */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/* 52 */     return 29;
/*    */   }
/*    */   
/*    */   public void serialize(@Nonnull ByteBuf buf) {
/* 56 */     byte nullBits = 0;
/* 57 */     if (this.cameraPosition != null) nullBits = (byte)(nullBits | 0x1); 
/* 58 */     if (this.cameraOrientation != null) nullBits = (byte)(nullBits | 0x2); 
/* 59 */     buf.writeByte(nullBits);
/*    */     
/* 61 */     buf.writeFloatLE(this.modelScale);
/* 62 */     if (this.cameraPosition != null) { this.cameraPosition.serialize(buf); } else { buf.writeZero(12); }
/* 63 */      if (this.cameraOrientation != null) { this.cameraOrientation.serialize(buf); } else { buf.writeZero(12); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public int computeSize() {
/* 69 */     return 29;
/*    */   }
/*    */   
/*    */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/* 73 */     if (buffer.readableBytes() - offset < 29) {
/* 74 */       return ValidationResult.error("Buffer too small: expected at least 29 bytes");
/*    */     }
/*    */ 
/*    */     
/* 78 */     return ValidationResult.OK;
/*    */   }
/*    */   
/*    */   public AssetEditorPreviewCameraSettings clone() {
/* 82 */     AssetEditorPreviewCameraSettings copy = new AssetEditorPreviewCameraSettings();
/* 83 */     copy.modelScale = this.modelScale;
/* 84 */     copy.cameraPosition = (this.cameraPosition != null) ? this.cameraPosition.clone() : null;
/* 85 */     copy.cameraOrientation = (this.cameraOrientation != null) ? this.cameraOrientation.clone() : null;
/* 86 */     return copy;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     AssetEditorPreviewCameraSettings other;
/* 92 */     if (this == obj) return true; 
/* 93 */     if (obj instanceof AssetEditorPreviewCameraSettings) { other = (AssetEditorPreviewCameraSettings)obj; } else { return false; }
/* 94 */      return (this.modelScale == other.modelScale && Objects.equals(this.cameraPosition, other.cameraPosition) && Objects.equals(this.cameraOrientation, other.cameraOrientation));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 99 */     return Objects.hash(new Object[] { Float.valueOf(this.modelScale), this.cameraPosition, this.cameraOrientation });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\packets\asseteditor\AssetEditorPreviewCameraSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */