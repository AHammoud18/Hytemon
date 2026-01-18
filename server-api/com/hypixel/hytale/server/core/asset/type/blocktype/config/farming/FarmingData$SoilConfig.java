/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.farming;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
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
/*     */ public class SoilConfig
/*     */ {
/*     */   public static final BuilderCodec<SoilConfig> CODEC;
/*     */   protected String targetBlock;
/*     */   protected Rangef lifetime;
/*     */   
/*     */   static {
/* 124 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SoilConfig.class, SoilConfig::new).appendInherited(new KeyedCodec("TargetBlock", (Codec)Codec.STRING), (o, v) -> o.targetBlock = v, o -> o.targetBlock, (o, p) -> o.targetBlock = p.targetBlock).addValidatorLate(() -> BlockType.VALIDATOR_CACHE.getValidator().late()).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Lifetime", (Codec)ProtocolCodecs.RANGEF), (o, v) -> o.lifetime = v, o -> o.lifetime, (o, p) -> o.lifetime = p.lifetime).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTargetBlock() {
/* 130 */     return this.targetBlock;
/*     */   }
/*     */   
/*     */   public Rangef getLifetime() {
/* 134 */     return this.lifetime;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\farming\FarmingData$SoilConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */