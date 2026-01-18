/*    */ package com.hypixel.hytale.server.core.asset.type.model.config.camera;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.CameraAxis;
/*    */ import com.hypixel.hytale.protocol.CameraNode;
/*    */ import com.hypixel.hytale.protocol.Rangef;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CameraAxis
/*    */   implements NetworkSerializable<CameraAxis>
/*    */ {
/*    */   public static final BuilderCodec<CameraAxis> CODEC;
/*    */   
/*    */   static {
/* 28 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CameraAxis.class, CameraAxis::new).append(new KeyedCodec("AngleRange", (Codec)ProtocolCodecs.RANGEF), (cameraAxis, s) -> cameraAxis.angleRange = s, cameraAxis -> cameraAxis.angleRange).add()).append(new KeyedCodec("TargetNodes", (Codec)new ArrayCodec((Codec)new EnumCodec(CameraNode.class), x$0 -> new CameraNode[x$0])), (cameraAxis, s) -> cameraAxis.targetNodes = s, cameraAxis -> cameraAxis.targetNodes).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/* 30 */   public static final CameraAxis STATIC_HEAD = new CameraAxis(new Rangef(0.0F, 0.0F), new CameraNode[] { CameraNode.Head });
/*    */   
/*    */   protected Rangef angleRange;
/*    */   
/*    */   protected CameraNode[] targetNodes;
/*    */   
/*    */   protected CameraAxis() {}
/*    */   
/*    */   public CameraAxis(Rangef angleRange, CameraNode[] targetNodes) {
/* 39 */     this.angleRange = angleRange;
/* 40 */     this.targetNodes = targetNodes;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public CameraAxis toPacket() {
/* 46 */     CameraAxis packet = new CameraAxis();
/* 47 */     packet.angleRange = this.angleRange;
/* 48 */     packet.targetNodes = this.targetNodes;
/* 49 */     return packet;
/*    */   }
/*    */   
/*    */   public Rangef getAngleRange() {
/* 53 */     return this.angleRange;
/*    */   }
/*    */   
/*    */   public CameraNode[] getTargetNodes() {
/* 57 */     return this.targetNodes;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 63 */     return "CameraAxis{angleRange=" + String.valueOf(this.angleRange) + ", targetNodes=" + 
/*    */       
/* 65 */       Arrays.toString((Object[])this.targetNodes) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\camera\CameraAxis.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */