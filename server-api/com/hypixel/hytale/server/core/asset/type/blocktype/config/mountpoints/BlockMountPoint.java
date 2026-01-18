/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.mountpoints;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.math.vector.Vector3f;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class BlockMountPoint
/*    */ {
/*    */   public static final BuilderCodec<BlockMountPoint> CODEC;
/*    */   
/*    */   static {
/* 31 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockMountPoint.class, BlockMountPoint::new).appendInherited(new KeyedCodec("Offset", (Codec)Vector3f.CODEC), (seat, i) -> seat.offset = i, seat -> seat.offset, (seat, p) -> seat.offset = p.offset).documentation("Relative offset from the block center (the point at .5,.5,.5 in world). Forward on a chair is 0,0,0.3").add()).appendInherited(new KeyedCodec("Yaw", (Codec)Codec.DOUBLE), (seat, o) -> seat.yawOffSetDegrees = o.floatValue(), seat -> Double.valueOf(seat.yawOffSetDegrees), (seat, p) -> seat.yawOffSetDegrees = p.yawOffSetDegrees).documentation("Offset for the model sitting on this seat in DEGREES").add()).build();
/*    */   }
/* 33 */   public static final BlockMountPoint[] EMPTY_ARRAY = new BlockMountPoint[0];
/*    */   
/*    */   private Vector3f offset;
/*    */   private float yawOffSetDegrees;
/*    */   
/*    */   public BlockMountPoint() {
/* 39 */     this(new Vector3f(), 0.0F);
/*    */   }
/*    */   
/*    */   public BlockMountPoint(Vector3f offset, float yawOffSetDegrees) {
/* 43 */     this.offset = offset;
/* 44 */     this.yawOffSetDegrees = yawOffSetDegrees;
/*    */   }
/*    */   
/*    */   public Vector3f getOffset() {
/* 48 */     return this.offset;
/*    */   }
/*    */   
/*    */   public float getYawOffSetDegrees() {
/* 52 */     return this.yawOffSetDegrees;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public BlockMountPoint rotate(@Nonnull Rotation yaw, @Nonnull Rotation pitch, @Nonnull Rotation roll) {
/* 57 */     Vector3f rotatedOffset = Rotation.rotate(this.offset, yaw, pitch, roll);
/* 58 */     return new BlockMountPoint(rotatedOffset, this.yawOffSetDegrees);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3f computeWorldSpacePosition(@Nonnull Vector3i blockLoc) {
/* 63 */     return blockLoc.toVector3f()
/* 64 */       .add(0.5F, 0.5F, 0.5F)
/* 65 */       .add(this.offset.x, this.offset.y, this.offset.z);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3f computeRotationEuler(@Nonnull int rotationIndex) {
/* 70 */     RotationTuple rotationTuple = RotationTuple.get(rotationIndex);
/*    */ 
/*    */ 
/*    */     
/* 74 */     Vector3f rotation = new Vector3f((float)rotationTuple.pitch().getRadians(), (float)rotationTuple.yaw().getRadians(), (float)rotationTuple.roll().getRadians());
/*    */     
/* 76 */     rotation.addYaw(3.1415927F);
/* 77 */     rotation.addYaw((float)Math.toRadians(this.yawOffSetDegrees));
/* 78 */     return rotation;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\mountpoints\BlockMountPoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */