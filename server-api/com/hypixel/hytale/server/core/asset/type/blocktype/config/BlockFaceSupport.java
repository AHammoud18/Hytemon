/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockFaceSupport;
/*     */ import com.hypixel.hytale.protocol.Vector3i;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockFaceSupport
/*     */   implements NetworkSerializable<BlockFaceSupport>
/*     */ {
/*     */   public static final BuilderCodec<BlockFaceSupport> CODEC;
/*     */   
/*     */   static {
/*  34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(BlockFaceSupport.class, BlockFaceSupport::new).append(new KeyedCodec("FaceType", (Codec)Codec.STRING), (blockFaceSupport, s) -> blockFaceSupport.faceType = s, blockFaceSupport -> blockFaceSupport.faceType).add()).documentation("Can be any string. Compared with FaceType in \"Support\". A LOT of blocks use 'Full'.")).append(new KeyedCodec("Filler", (Codec)new ArrayCodec((Codec)Vector3i.CODEC, x$0 -> new Vector3i[x$0])), (blockFaceSupport, o) -> blockFaceSupport.filler = o, blockFaceSupport -> blockFaceSupport.filler).add()).build();
/*  35 */   } public static final BlockFaceSupport ALL = new BlockFaceSupport();
/*     */   
/*     */   public static final String FULL_SUPPORTING_FACE = "Full";
/*  38 */   protected String faceType = "Full";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Vector3i[] filler;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFaceSupport(String faceType, Vector3i[] filler) {
/*  49 */     this.faceType = faceType;
/*  50 */     this.filler = filler;
/*     */   }
/*     */   
/*     */   public String getFaceType() {
/*  54 */     return this.faceType;
/*     */   }
/*     */   
/*     */   public Vector3i[] getFiller() {
/*  58 */     return this.filler;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean providesSupportFromFiller(Vector3i filler) {
/*  63 */     return (this.filler == null || ArrayUtil.contains((Object[])this.filler, filler));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  69 */     return "BlockFaceSupport{faceType='" + this.faceType + "', filler=" + 
/*     */       
/*  71 */       Arrays.toString((Object[])this.filler) + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BlockFaceSupport rotate(@Nonnull BlockFaceSupport original, @Nonnull Rotation rotationYaw, @Nonnull Rotation rotationPitch, @Nonnull Rotation roll) {
/*  77 */     if (original == ALL) return ALL; 
/*  78 */     Vector3i[] rotatedFiller = (Vector3i[])ArrayUtil.copyAndMutate((Object[])original.filler, vector -> Rotation.rotate(vector, rotationYaw, rotationPitch, roll), x$0 -> new Vector3i[x$0]);
/*  79 */     return new BlockFaceSupport(original.faceType, rotatedFiller);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockFaceSupport toPacket() {
/*  88 */     BlockFaceSupport protocolBlockFaceSupport = new BlockFaceSupport();
/*     */     
/*  90 */     protocolBlockFaceSupport.faceType = this.faceType;
/*     */     
/*  92 */     if (this.filler != null) {
/*  93 */       Vector3i[] filler = new Vector3i[this.filler.length];
/*     */       
/*  95 */       for (int j = 0; j < this.filler.length; j++) {
/*  96 */         Vector3i fillerVector = this.filler[j];
/*  97 */         filler[j] = new Vector3i(fillerVector.x, fillerVector.y, fillerVector.z);
/*     */       } 
/*     */       
/* 100 */       protocolBlockFaceSupport.filler = filler;
/*     */     } 
/*     */     
/* 103 */     return protocolBlockFaceSupport;
/*     */   }
/*     */   
/*     */   public BlockFaceSupport() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\BlockFaceSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */