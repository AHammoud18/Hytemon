/*     */ package com.hypixel.hytale.server.core.asset.type.blockhitbox;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.shape.Box;
/*     */ import com.hypixel.hytale.protocol.Hitbox;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializers;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class BlockBoundingBoxes
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, BlockBoundingBoxes>>, NetworkSerializable<Hitbox[]>
/*     */ {
/*  30 */   private static final int ROTATIONS = Rotation.VALUES.length * Rotation.VALUES.length * Rotation.VALUES.length;
/*     */   public static final int DEFAULT_ID = 0;
/*     */   public static final String DEFAULT = "Full";
/*  33 */   public static final BlockBoundingBoxes UNIT_BOX = new BlockBoundingBoxes("Full", 0.0D, 1.0D);
/*  34 */   public static final double UNIT_BOX_MAXIMUM_EXTENT = UNIT_BOX.variants[0].getBoundingBox().getMaximumExtent();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final AssetBuilderCodec<String, BlockBoundingBoxes> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  52 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockBoundingBoxes.class, BlockBoundingBoxes::new, (Codec)Codec.STRING, (blockBoundingBoxes, s) -> blockBoundingBoxes.id = s, blockBoundingBoxes -> blockBoundingBoxes.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Boxes", (Codec)new ArrayCodec(Box.CODEC, x$0 -> new Box[x$0]), true), (blockBoundingBoxes, boxes) -> blockBoundingBoxes.baseDetailBoxes = boxes, blockBoundingBoxes -> blockBoundingBoxes.baseDetailBoxes, (blockBoundingBoxes, parent) -> blockBoundingBoxes.baseDetailBoxes = parent.baseDetailBoxes).addValidator(Validators.nonEmptyArray()).add()).afterDecode(BlockBoundingBoxes::processConfig)).build();
/*     */   }
/*  54 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BlockBoundingBoxes::getAssetStore));
/*  55 */   public static final Hitbox[] EMPTY_HITBOXES = new Hitbox[0];
/*     */   private static AssetStore<String, BlockBoundingBoxes, IndexedLookupTableAssetMap<String, BlockBoundingBoxes>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   
/*     */   public static AssetStore<String, BlockBoundingBoxes, IndexedLookupTableAssetMap<String, BlockBoundingBoxes>> getAssetStore() {
/*  60 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BlockBoundingBoxes.class); 
/*  61 */     return ASSET_STORE;
/*     */   }
/*     */   protected String id; protected Box[] baseDetailBoxes;
/*     */   public static IndexedLookupTableAssetMap<String, BlockBoundingBoxes> getAssetMap() {
/*  65 */     return (IndexedLookupTableAssetMap<String, BlockBoundingBoxes>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  72 */   private final transient RotatedVariantBoxes[] variants = new RotatedVariantBoxes[ROTATIONS];
/*     */ 
/*     */   
/*     */   protected transient boolean protrudesUnitBox;
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockBoundingBoxes(String id, double min, double max) {
/*  80 */     this.id = id;
/*  81 */     this.baseDetailBoxes = new Box[] { (new Box()).setMinMax(min, max) };
/*  82 */     processConfig();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  87 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean protrudesUnitBox() {
/*  91 */     return this.protrudesUnitBox;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processConfig() {
/*  96 */     if (this.baseDetailBoxes == null)
/*     */       return; 
/*  98 */     this.protrudesUnitBox = false;
/*     */     
/* 100 */     for (RotationTuple tuple : RotationTuple.VALUES) {
/* 101 */       Rotation yaw = tuple.yaw();
/* 102 */       Rotation pitch = tuple.pitch();
/* 103 */       Rotation roll = tuple.roll();
/*     */       
/* 105 */       RotatedVariantBoxes variant = getRotated(this, yaw, pitch, roll);
/*     */       
/* 107 */       this.variants[RotationTuple.index(yaw, pitch, roll)] = variant;
/*     */       
/* 109 */       this.protrudesUnitBox |= (variant.boundingBox.min.x < 0.0D || variant.boundingBox.max.x > 1.0D || variant.boundingBox.min.y < 0.0D || variant.boundingBox.max.y > 1.0D || variant.boundingBox.min.z < 0.0D || variant.boundingBox.max.z > 1.0D) ? 1 : 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RotatedVariantBoxes get(@Nonnull Rotation yaw, @Nonnull Rotation pitch, @Nonnull Rotation roll) {
/* 116 */     return this.variants[RotationTuple.index(yaw, pitch, roll)];
/*     */   }
/*     */   
/*     */   public RotatedVariantBoxes get(int index) {
/* 120 */     return this.variants[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public Hitbox[] toPacket() {
/* 125 */     if (this.baseDetailBoxes != null) {
/* 126 */       Hitbox[] arr = new Hitbox[this.baseDetailBoxes.length];
/* 127 */       for (int i = 0; i < this.baseDetailBoxes.length; i++) {
/* 128 */         arr[i] = (Hitbox)NetworkSerializers.BOX.toPacket(this.baseDetailBoxes[i]);
/*     */       }
/* 130 */       return arr;
/*     */     } 
/* 132 */     return EMPTY_HITBOXES;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 139 */     return "BlockBoundingBoxes{data=" + String.valueOf(this.data) + ", id='" + this.id + "', baseDetailBoxes=" + 
/*     */ 
/*     */       
/* 142 */       Arrays.toString((Object[])this.baseDetailBoxes) + ", variants=" + 
/* 143 */       Arrays.toString((Object[])this.variants) + ", protrudesUnitBox=" + this.protrudesUnitBox + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static BlockBoundingBoxes getUnitBoxFor(String id) {
/* 150 */     return new BlockBoundingBoxes(id, 0.0D, 1.0D);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private static RotatedVariantBoxes getRotated(@Nonnull BlockBoundingBoxes boxes, @Nonnull Rotation rotationYaw, @Nonnull Rotation rotationPitch, @Nonnull Rotation rotationRoll) {
/* 155 */     Box[] detailBoxes = boxes.baseDetailBoxes;
/* 156 */     Box[] newDetailBoxes = new Box[detailBoxes.length];
/*     */     
/* 158 */     for (int i = 0; i < detailBoxes.length; i++) {
/* 159 */       newDetailBoxes[i] = rotate(new Box(detailBoxes[i]), rotationYaw, rotationPitch, rotationRoll);
/*     */     }
/*     */     
/* 162 */     return new RotatedVariantBoxes(newDetailBoxes);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   protected static Box rotate(@Nonnull Box box, @Nonnull Rotation rotationYaw, @Nonnull Rotation rotationPitch, @Nonnull Rotation rotationRoll) {
/* 167 */     switch (rotationRoll) {
/*     */       case Ninety:
/* 169 */         rotate90Z(box); break;
/* 170 */       case OneEighty: rotate180Z(box); break;
/* 171 */       case TwoSeventy: rotate270Z(box);
/*     */         break;
/*     */     } 
/* 174 */     switch (rotationPitch) {
/*     */       case Ninety:
/* 176 */         rotate90X(box); break;
/* 177 */       case OneEighty: rotate180X(box); break;
/* 178 */       case TwoSeventy: rotate270X(box);
/*     */         break;
/*     */     } 
/* 181 */     switch (rotationYaw) {
/*     */       case Ninety:
/* 183 */         rotate90Y(box); break;
/* 184 */       case OneEighty: rotate180Y(box); break;
/* 185 */       case TwoSeventy: rotate270Y(box);
/*     */         break;
/*     */     } 
/* 188 */     box.normalize();
/*     */     
/* 190 */     return box;
/*     */   }
/*     */   
/*     */   private static void rotate90X(@Nonnull Box box) {
/* 194 */     double t = box.min.z;
/* 195 */     box.min.z = box.min.y;
/* 196 */     box.min.y = 1.0D - t;
/*     */     
/* 198 */     t = box.max.z;
/* 199 */     box.max.z = box.max.y;
/* 200 */     box.max.y = 1.0D - t;
/*     */   }
/*     */   
/*     */   private static void rotate180X(@Nonnull Box box) {
/* 204 */     box.min.z = 1.0D - box.min.z;
/* 205 */     box.min.y = 1.0D - box.min.y;
/*     */     
/* 207 */     box.max.z = 1.0D - box.max.z;
/* 208 */     box.max.y = 1.0D - box.max.y;
/*     */   }
/*     */   
/*     */   private static void rotate270X(@Nonnull Box box) {
/* 212 */     double t = box.min.z;
/* 213 */     box.min.z = 1.0D - box.min.y;
/* 214 */     box.min.y = t;
/*     */     
/* 216 */     t = box.max.z;
/* 217 */     box.max.z = 1.0D - box.max.y;
/* 218 */     box.max.y = t;
/*     */   }
/*     */   
/*     */   private static void rotate90Y(@Nonnull Box box) {
/* 222 */     double t = box.min.x;
/* 223 */     box.min.x = box.min.z;
/* 224 */     box.min.z = 1.0D - t;
/*     */     
/* 226 */     t = box.max.x;
/* 227 */     box.max.x = box.max.z;
/* 228 */     box.max.z = 1.0D - t;
/*     */   }
/*     */   
/*     */   private static void rotate180Y(@Nonnull Box box) {
/* 232 */     box.min.x = 1.0D - box.min.x;
/* 233 */     box.min.z = 1.0D - box.min.z;
/*     */     
/* 235 */     box.max.x = 1.0D - box.max.x;
/* 236 */     box.max.z = 1.0D - box.max.z;
/*     */   }
/*     */   
/*     */   private static void rotate270Y(@Nonnull Box box) {
/* 240 */     double t = box.min.x;
/* 241 */     box.min.x = 1.0D - box.min.z;
/* 242 */     box.min.z = t;
/*     */     
/* 244 */     t = box.max.x;
/* 245 */     box.max.x = 1.0D - box.max.z;
/* 246 */     box.max.z = t;
/*     */   }
/*     */   
/*     */   private static void rotate90Z(@Nonnull Box box) {
/* 250 */     double t = box.min.x;
/* 251 */     box.min.x = box.min.y;
/* 252 */     box.min.y = 1.0D - t;
/*     */     
/* 254 */     t = box.max.x;
/* 255 */     box.max.x = box.max.y;
/* 256 */     box.max.y = 1.0D - t;
/*     */   }
/*     */   
/*     */   private static void rotate180Z(@Nonnull Box box) {
/* 260 */     box.min.x = 1.0D - box.min.x;
/* 261 */     box.min.y = 1.0D - box.min.y;
/*     */     
/* 263 */     box.max.x = 1.0D - box.max.x;
/* 264 */     box.max.y = 1.0D - box.max.y;
/*     */   }
/*     */   
/*     */   private static void rotate270Z(@Nonnull Box box) {
/* 268 */     double t = box.min.x;
/* 269 */     box.min.x = 1.0D - box.min.y;
/* 270 */     box.min.y = t;
/*     */     
/* 272 */     t = box.max.x;
/* 273 */     box.max.x = 1.0D - box.max.y;
/* 274 */     box.max.y = t;
/*     */   }
/*     */   public BlockBoundingBoxes() {}
/*     */   
/*     */   public static class RotatedVariantBoxes { protected Box boundingBox;
/*     */     protected Box[] detailBoxes;
/*     */     
/*     */     public RotatedVariantBoxes(@Nullable Box[] boxes) {
/* 282 */       if (boxes == null || boxes.length == 0) {
/* 283 */         boxes = new Box[] { (new Box()).setMinMax(0.0D, 1.0D) };
/*     */       }
/*     */       
/* 286 */       if (boxes.length == 1) {
/* 287 */         this.boundingBox = boxes[0];
/*     */       } else {
/* 289 */         Box box = this.boundingBox = new Box();
/* 290 */         for (int i = 0; i < boxes.length; ) { box.union(boxes[i]); i++; }
/*     */       
/* 292 */       }  this.detailBoxes = boxes;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Box getBoundingBox() {
/* 297 */       return this.boundingBox;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Box[] getDetailBoxes() {
/* 302 */       return this.detailBoxes;
/*     */     }
/*     */     
/*     */     public boolean hasDetailBoxes() {
/* 306 */       return (this.detailBoxes.length > 1);
/*     */     }
/*     */     
/*     */     public boolean containsPosition(double x, double y, double z) {
/* 310 */       if (hasDetailBoxes()) {
/* 311 */         for (Box detailBox : this.detailBoxes) {
/* 312 */           if (detailBox.containsPosition(x, y, z)) {
/* 313 */             return true;
/*     */           }
/*     */         } 
/* 316 */         return false;
/*     */       } 
/* 318 */       return this.boundingBox.containsPosition(x, y, z);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blockhitbox\BlockBoundingBoxes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */