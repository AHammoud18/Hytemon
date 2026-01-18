/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.AssetIconProperties;
/*    */ import com.hypixel.hytale.protocol.Vector2f;
/*    */ import com.hypixel.hytale.protocol.Vector3f;
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
/*    */ public class AssetIconProperties
/*    */   implements NetworkSerializable<AssetIconProperties>
/*    */ {
/*    */   public static final BuilderCodec<AssetIconProperties> CODEC;
/*    */   private float scale;
/*    */   @Nullable
/*    */   private Vector2f translation;
/*    */   @Nullable
/*    */   private Vector3f rotation;
/*    */   
/*    */   static {
/* 32 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AssetIconProperties.class, AssetIconProperties::new).addField(new KeyedCodec("Scale", (Codec)Codec.DOUBLE), (props, scale) -> props.scale = scale.floatValue(), props -> Double.valueOf(props.scale))).addField(new KeyedCodec("Translation", (Codec)Vector2d.AS_ARRAY_CODEC), (props, translation) -> props.translation = (translation == null) ? null : new Vector2f((float)translation.getX(), (float)translation.getY()), props -> (props.translation == null) ? null : new Vector2d(props.translation.x, props.translation.y))).addField(new KeyedCodec("Rotation", (Codec)Vector3d.AS_ARRAY_CODEC), (props, rot) -> props.rotation = (rot == null) ? null : new Vector3f((float)rot.getX(), (float)rot.getY(), (float)rot.getZ()), props -> (props.rotation == null) ? null : new Vector3d(props.rotation.x, props.rotation.y, props.rotation.z))).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   AssetIconProperties() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AssetIconProperties(float scale, Vector2f translation, Vector3f rotation) {
/* 44 */     this.scale = scale;
/* 45 */     this.translation = translation;
/* 46 */     this.rotation = rotation;
/*    */   }
/*    */   
/*    */   public float getScale() {
/* 50 */     return this.scale;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Vector2f getTranslation() {
/* 55 */     return this.translation;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Vector3f getRotation() {
/* 60 */     return this.rotation;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AssetIconProperties toPacket() {
/* 66 */     AssetIconProperties packet = new AssetIconProperties();
/* 67 */     packet.scale = this.scale;
/* 68 */     packet.translation = this.translation;
/* 69 */     packet.rotation = this.rotation;
/* 70 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 76 */     return "AssetIconProperties{scale=" + this.scale + ", translation=" + String.valueOf(this.translation) + ", rotation=" + String.valueOf(this.rotation) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\AssetIconProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */