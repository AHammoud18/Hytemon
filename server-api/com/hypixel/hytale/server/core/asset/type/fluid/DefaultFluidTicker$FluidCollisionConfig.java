/*     */ package com.hypixel.hytale.server.core.asset.type.fluid;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FluidCollisionConfig
/*     */ {
/*     */   public static final BuilderCodec<FluidCollisionConfig> CODEC;
/*     */   private String blockToPlace;
/*     */   
/*     */   static {
/* 242 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FluidCollisionConfig.class, FluidCollisionConfig::new).appendInherited(new KeyedCodec("BlockToPlace", (Codec)Codec.STRING), (o, v) -> o.blockToPlace = v, o -> o.blockToPlace, (o, p) -> o.blockToPlace = p.blockToPlace).documentation("The block to place when a collision occurs").add()).appendInherited(new KeyedCodec("SoundEvent", (Codec)Codec.STRING), (o, v) -> o.soundEvent = v, o -> o.soundEvent, (o, p) -> o.soundEvent = p.soundEvent).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("PlaceFluid", (Codec)Codec.BOOLEAN), (o, v) -> o.placeFluid = v.booleanValue(), o -> Boolean.valueOf(o.placeFluid), (o, p) -> o.placeFluid = p.placeFluid).documentation("Whether to still place the fluid on collision").add()).build();
/*     */   }
/*     */   
/* 245 */   private int blockToPlaceIndex = Integer.MIN_VALUE;
/*     */   public boolean placeFluid = false;
/*     */   private String soundEvent;
/* 248 */   private int soundEventIndex = Integer.MIN_VALUE;
/*     */   
/*     */   public int getBlockToPlaceIndex() {
/* 251 */     if (this.blockToPlaceIndex == Integer.MIN_VALUE && this.blockToPlace != null) {
/* 252 */       this.blockToPlaceIndex = BlockType.getBlockIdOrUnknown(this.blockToPlace, "Unknown block type %s", new Object[] { this.blockToPlace });
/*     */     }
/* 254 */     return this.blockToPlaceIndex;
/*     */   }
/*     */   
/*     */   public int getSoundEventIndex() {
/* 258 */     if (this.soundEventIndex == Integer.MIN_VALUE && this.soundEvent != null) {
/* 259 */       this.soundEventIndex = SoundEvent.getAssetMap().getIndex(this.soundEvent);
/*     */     }
/* 261 */     return this.soundEventIndex;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\fluid\DefaultFluidTicker$FluidCollisionConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */