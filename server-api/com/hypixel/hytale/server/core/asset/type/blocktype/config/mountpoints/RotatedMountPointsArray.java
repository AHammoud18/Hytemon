/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.mountpoints;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.function.FunctionCodec;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class RotatedMountPointsArray {
/*    */   static {
/* 13 */     CHILD = new ArrayCodec((Codec)BlockMountPoint.CODEC, x$0 -> new BlockMountPoint[x$0]);
/*    */   }
/* 15 */   public static final Codec<RotatedMountPointsArray> CODEC = (Codec<RotatedMountPointsArray>)new FunctionCodec((Codec)CHILD, RotatedMountPointsArray::new, RotatedMountPointsArray::getRaw);
/*    */   
/*    */   private static final ArrayCodec<BlockMountPoint> CHILD;
/*    */   private BlockMountPoint[] raw;
/*    */   private transient BlockMountPoint[][] rotated;
/*    */   
/*    */   public RotatedMountPointsArray() {}
/*    */   
/*    */   public RotatedMountPointsArray(BlockMountPoint[] raw) {
/* 24 */     this.raw = raw;
/*    */   }
/*    */   
/*    */   public int size() {
/* 28 */     return (this.raw == null) ? 0 : this.raw.length;
/*    */   }
/*    */   
/*    */   public BlockMountPoint[] getRaw() {
/* 32 */     return this.raw;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BlockMountPoint[] getRotated(int rotationIndex) {
/* 37 */     if (this.raw == null || rotationIndex == 0) return this.raw;
/*    */ 
/*    */ 
/*    */     
/* 41 */     if (this.rotated == null) {
/* 42 */       this.rotated = new BlockMountPoint[RotationTuple.VALUES.length][];
/*    */     }
/*    */     
/* 45 */     BlockMountPoint[] value = this.rotated[rotationIndex];
/* 46 */     if (value == null) {
/* 47 */       RotationTuple rotation = RotationTuple.get(rotationIndex);
/* 48 */       ObjectArrayList<BlockMountPoint> objectArrayList = new ObjectArrayList();
/* 49 */       for (BlockMountPoint mountPoint : this.raw) {
/* 50 */         BlockMountPoint rotated = mountPoint.rotate(rotation.yaw(), rotation.pitch(), rotation.roll());
/* 51 */         objectArrayList.add(rotated);
/*    */       } 
/* 53 */       value = (BlockMountPoint[])objectArrayList.toArray(x$0 -> new BlockMountPoint[x$0]);
/* 54 */       this.rotated[rotationIndex] = value;
/*    */     } 
/*    */     
/* 57 */     return value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\mountpoints\RotatedMountPointsArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */