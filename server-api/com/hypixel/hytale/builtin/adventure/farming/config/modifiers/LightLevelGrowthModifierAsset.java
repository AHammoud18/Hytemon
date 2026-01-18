/*     */ package com.hypixel.hytale.builtin.adventure.farming.config.modifiers;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.Range;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.GrowthModifierAsset;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkLightData;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class LightLevelGrowthModifierAsset
/*     */   extends GrowthModifierAsset
/*     */ {
/*     */   public static final BuilderCodec<LightLevelGrowthModifierAsset> CODEC;
/*     */   protected ArtificialLight artificialLight;
/*     */   protected Rangef sunlight;
/*     */   protected boolean requireBoth;
/*     */   
/*     */   static {
/*  36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(LightLevelGrowthModifierAsset.class, LightLevelGrowthModifierAsset::new, ABSTRACT_CODEC).addField(new KeyedCodec("ArtificialLight", (Codec)ArtificialLight.CODEC), (lightLevel, artificialLight) -> lightLevel.artificialLight = artificialLight, lightLevel -> lightLevel.artificialLight)).addField(new KeyedCodec("Sunlight", (Codec)ProtocolCodecs.RANGEF), (lightLevel, sunLight) -> lightLevel.sunlight = sunLight, lightLevel -> lightLevel.sunlight)).addField(new KeyedCodec("RequireBoth", (Codec)Codec.BOOLEAN), (lightLevel, requireBoth) -> lightLevel.requireBoth = requireBoth.booleanValue(), lightLevel -> Boolean.valueOf(lightLevel.requireBoth))).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArtificialLight getArtificialLight() {
/*  43 */     return this.artificialLight;
/*     */   }
/*     */   
/*     */   public Rangef getSunlight() {
/*  47 */     return this.sunlight;
/*     */   }
/*     */   
/*     */   public boolean isRequireBoth() {
/*  51 */     return this.requireBoth;
/*     */   }
/*     */   
/*     */   protected boolean checkArtificialLight(byte red, byte green, byte blue) {
/*  55 */     ArtificialLight artificialLight = this.artificialLight;
/*  56 */     Range redRange = artificialLight.getRed();
/*  57 */     Range greenRange = artificialLight.getGreen();
/*  58 */     Range blueRange = artificialLight.getBlue();
/*     */     
/*  60 */     return (isInRange(redRange, red) && isInRange(greenRange, green) && isInRange(blueRange, blue));
/*     */   }
/*     */   
/*     */   protected boolean checkSunLight(WorldTimeResource worldTimeResource, byte sky) {
/*  64 */     Rangef range = this.sunlight;
/*     */     
/*  66 */     double sunlightFactor = worldTimeResource.getSunlightFactor();
/*  67 */     double daylight = sunlightFactor * sky;
/*  68 */     return (range.min <= daylight && daylight <= range.max);
/*     */   }
/*     */   
/*     */   protected static boolean isInRange(@Nonnull Range range, int value) {
/*  72 */     return (range.min <= value && value <= range.max);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCurrentGrowthMultiplier(CommandBuffer<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z, boolean initialTick) {
/*  77 */     BlockSection blockSection = (BlockSection)commandBuffer.getComponent(sectionRef, BlockSection.getComponentType());
/*  78 */     short lightRaw = blockSection.getGlobalLight().getLightRaw(x, y, z);
/*     */     
/*  80 */     byte redLight = ChunkLightData.getLightValue(lightRaw, 0);
/*  81 */     byte greenLight = ChunkLightData.getLightValue(lightRaw, 1);
/*  82 */     byte blueLight = ChunkLightData.getLightValue(lightRaw, 2);
/*  83 */     byte skyLight = ChunkLightData.getLightValue(lightRaw, 3);
/*     */     
/*  85 */     WorldTimeResource worldTimeResource = (WorldTimeResource)((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore().getResource(WorldTimeResource.getResourceType());
/*     */     
/*  87 */     boolean active = false;
/*  88 */     boolean onlySunlight = false;
/*     */ 
/*     */     
/*  91 */     if (this.requireBoth) {
/*     */       
/*  93 */       active = (checkArtificialLight(redLight, greenLight, blueLight) && checkSunLight(worldTimeResource, skyLight));
/*     */ 
/*     */     
/*     */     }
/*  97 */     else if (checkArtificialLight(redLight, greenLight, blueLight)) {
/*  98 */       active = true;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 103 */     else if (checkSunLight(worldTimeResource, skyLight)) {
/* 104 */       active = true;
/* 105 */       onlySunlight = true;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     if (active) {
/* 110 */       if (onlySunlight && initialTick) {
/* 111 */         return super.getCurrentGrowthMultiplier(commandBuffer, sectionRef, blockRef, x, y, z, initialTick) * 0.6000000238418579D;
/*     */       }
/* 113 */       return super.getCurrentGrowthMultiplier(commandBuffer, sectionRef, blockRef, x, y, z, initialTick);
/*     */     } 
/* 115 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 121 */     return "LightLevelGrowthModifierAsset{artificialLight=" + String.valueOf(this.artificialLight) + ", sunLight=" + String.valueOf(this.sunlight) + ", requireBoth=" + this.requireBoth + "} " + super
/*     */ 
/*     */ 
/*     */       
/* 125 */       .toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ArtificialLight
/*     */   {
/*     */     public static final BuilderCodec<ArtificialLight> CODEC;
/*     */ 
/*     */     
/*     */     protected Range red;
/*     */ 
/*     */     
/*     */     protected Range green;
/*     */ 
/*     */     
/*     */     protected Range blue;
/*     */ 
/*     */     
/*     */     static {
/* 145 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ArtificialLight.class, ArtificialLight::new).addField(new KeyedCodec("Red", (Codec)ProtocolCodecs.RANGE), (light, red) -> light.red = red, light -> light.red)).addField(new KeyedCodec("Green", (Codec)ProtocolCodecs.RANGE), (light, green) -> light.green = green, light -> light.green)).addField(new KeyedCodec("Blue", (Codec)ProtocolCodecs.RANGE), (light, blue) -> light.blue = blue, light -> light.blue)).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Range getRed() {
/* 151 */       return this.red;
/*     */     }
/*     */     
/*     */     public Range getGreen() {
/* 155 */       return this.green;
/*     */     }
/*     */     
/*     */     public Range getBlue() {
/* 159 */       return this.blue;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 165 */       return "ArtificialLightLevel{red=" + String.valueOf(this.red) + ", green=" + String.valueOf(this.green) + ", blue=" + String.valueOf(this.blue) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\config\modifiers\LightLevelGrowthModifierAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */