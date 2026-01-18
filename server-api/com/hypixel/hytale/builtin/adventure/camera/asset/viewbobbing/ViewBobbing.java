/*    */ package com.hypixel.hytale.builtin.adventure.camera.asset.viewbobbing;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.builtin.adventure.camera.asset.CameraShakeConfig;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.MovementType;
/*    */ import com.hypixel.hytale.protocol.ViewBobbing;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ViewBobbing
/*    */   implements NetworkSerializable<ViewBobbing>, JsonAssetWithMap<MovementType, AssetMap<MovementType, ViewBobbing>>
/*    */ {
/*    */   @Nonnull
/* 26 */   public static final Codec<MovementType> MOVEMENT_TYPE_CODEC = (Codec<MovementType>)new EnumCodec(MovementType.class);
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static final AssetBuilderCodec<MovementType, ViewBobbing> CODEC;
/*    */ 
/*    */   
/*    */   protected MovementType id;
/*    */ 
/*    */   
/*    */   protected AssetExtraInfo.Data data;
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected CameraShakeConfig firstPerson;
/*    */ 
/*    */   
/*    */   static {
/* 44 */     CODEC = ((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ViewBobbing.class, ViewBobbing::new, MOVEMENT_TYPE_CODEC, (o, v) -> o.id = v, ViewBobbing::getId, (o, data) -> o.data = data, o -> o.data).appendInherited(new KeyedCodec("FirstPerson", (Codec)CameraShakeConfig.CODEC), (o, v) -> o.firstPerson = v, o -> o.firstPerson, (o, p) -> o.firstPerson = p.firstPerson).documentation("The camera shake profile to be applied").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MovementType getId() {
/* 65 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ViewBobbing toPacket() {
/* 71 */     return new ViewBobbing(this.firstPerson.toPacket());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 77 */     return "ViewBobbing{id=" + String.valueOf(this.id) + ", data=" + String.valueOf(this.data) + ", firstPerson=" + String.valueOf(this.firstPerson) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\viewbobbing\ViewBobbing.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */