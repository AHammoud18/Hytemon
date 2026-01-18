/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.ItemTool;
/*     */ import com.hypixel.hytale.protocol.ItemToolSpec;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockset.config.BlockSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemTool
/*     */   implements NetworkSerializable<ItemTool>
/*     */ {
/*     */   public static final BuilderCodec<ItemTool> CODEC;
/*     */   protected ItemToolSpec[] specs;
/*     */   protected float speed;
/*     */   protected DurabilityLossBlockTypes[] durabilityLossBlockTypes;
/*     */   
/*     */   static {
/*  58 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemTool.class, ItemTool::new).addField(new KeyedCodec("Specs", (Codec)new ArrayCodec((Codec)ItemToolSpec.CODEC, x$0 -> new ItemToolSpec[x$0])), (itemTool, s) -> itemTool.specs = s, itemTool -> itemTool.specs)).addField(new KeyedCodec("Speed", (Codec)Codec.DOUBLE), (itemTool, d) -> itemTool.speed = d.floatValue(), itemTool -> Double.valueOf(itemTool.speed))).addField(new KeyedCodec("DurabilityLossBlockTypes", (Codec)new ArrayCodec((Codec)DurabilityLossBlockTypes.CODEC, x$0 -> new DurabilityLossBlockTypes[x$0])), (item, s) -> item.durabilityLossBlockTypes = s, item -> item.durabilityLossBlockTypes)).appendInherited(new KeyedCodec("HitSoundLayer", (Codec)Codec.STRING), (item, s) -> item.hitSoundLayerId = s, item -> item.hitSoundLayerId, (item, parent) -> item.hitSoundLayerId = parent.hitSoundLayerId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).documentation("Sound to play in addition to the block breaking sound when hitting a block this tool is designed to break.").add()).appendInherited(new KeyedCodec("IncorrectMaterialSoundLayer", (Codec)Codec.STRING), (item, s) -> item.incorrectMaterialSoundLayerId = s, item -> item.incorrectMaterialSoundLayerId, (item, parent) -> item.incorrectMaterialSoundLayerId = parent.incorrectMaterialSoundLayerId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).documentation("Sound to play in addition to the block breaking sound when hitting a block this tool cannot break.").add()).afterDecode(ItemTool::processConfig)).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  64 */   protected String hitSoundLayerId = null;
/*     */   
/*  66 */   protected transient int hitSoundLayerIndex = 0;
/*     */   @Nullable
/*  68 */   protected String incorrectMaterialSoundLayerId = null;
/*     */   
/*  70 */   protected transient int incorrectMaterialSoundLayerIndex = 0;
/*     */   
/*     */   public ItemTool(ItemToolSpec[] specs, float speed, DurabilityLossBlockTypes[] durabilityLossBlockTypes) {
/*  73 */     this.specs = specs;
/*  74 */     this.speed = speed;
/*  75 */     this.durabilityLossBlockTypes = durabilityLossBlockTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processConfig() {
/*  82 */     if (this.hitSoundLayerId != null) {
/*  83 */       this.hitSoundLayerIndex = SoundEvent.getAssetMap().getIndex(this.hitSoundLayerId);
/*     */     }
/*  85 */     if (this.incorrectMaterialSoundLayerId != null) {
/*  86 */       this.incorrectMaterialSoundLayerIndex = SoundEvent.getAssetMap().getIndex(this.incorrectMaterialSoundLayerId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemTool toPacket() {
/*  93 */     ItemTool packet = new ItemTool();
/*  94 */     if (this.specs != null && this.specs.length > 0) {
/*  95 */       packet.specs = (ItemToolSpec[])ArrayUtil.copyAndMutate((Object[])this.specs, ItemToolSpec::toPacket, x$0 -> new ItemToolSpec[x$0]);
/*     */     }
/*  97 */     packet.speed = this.speed;
/*  98 */     return packet;
/*     */   }
/*     */   
/*     */   public ItemToolSpec[] getSpecs() {
/* 102 */     return this.specs;
/*     */   }
/*     */   
/*     */   public float getSpeed() {
/* 106 */     return this.speed;
/*     */   }
/*     */   
/*     */   public DurabilityLossBlockTypes[] getDurabilityLossBlockTypes() {
/* 110 */     return this.durabilityLossBlockTypes;
/*     */   }
/*     */   
/*     */   public int getHitSoundLayerIndex() {
/* 114 */     return this.hitSoundLayerIndex;
/*     */   }
/*     */   
/*     */   public int getIncorrectMaterialSoundLayerIndex() {
/* 118 */     return this.incorrectMaterialSoundLayerIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 125 */     return "ItemTool{specs=" + Arrays.toString((Object[])this.specs) + ", speed=" + this.speed + ", durabilityLossBlockTypes=" + 
/*     */       
/* 127 */       Arrays.toString((Object[])this.durabilityLossBlockTypes) + ", hitSoundLayerId='" + this.hitSoundLayerId + "', hitSoundLayerIndex=" + this.hitSoundLayerIndex + ", incorrectMaterialSoundLayerId='" + this.incorrectMaterialSoundLayerId + "', incorrectMaterialSoundLayerIndex=" + this.incorrectMaterialSoundLayerIndex + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ItemTool() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DurabilityLossBlockTypes
/*     */   {
/*     */     public static final BuilderCodec<DurabilityLossBlockTypes> CODEC;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String[] blockTypes;
/*     */ 
/*     */ 
/*     */     
/*     */     protected String[] blockSets;
/*     */ 
/*     */ 
/*     */     
/*     */     protected double durabilityLossOnHit;
/*     */ 
/*     */     
/*     */     protected int[] blockTypeIndexes;
/*     */ 
/*     */     
/*     */     protected int[] blockSetIndexes;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 163 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(DurabilityLossBlockTypes.class, DurabilityLossBlockTypes::new).addField(new KeyedCodec("BlockTypes", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (item, s) -> item.blockTypes = s, item -> item.blockTypes)).addField(new KeyedCodec("BlockSets", (Codec)Codec.STRING_ARRAY), (item, s) -> item.blockSets = s, item -> item.blockSets)).addField(new KeyedCodec("DurabilityLossOnHit", (Codec)Codec.DOUBLE), (item, s) -> item.durabilityLossOnHit = s.doubleValue(), item -> Double.valueOf(item.durabilityLossOnHit))).afterDecode(item -> { if (item.blockSets != null) { item.blockSetIndexes = new int[item.blockSets.length]; for (int i = 0; i < item.blockSets.length; i++) { String blockSet = item.blockSets[i]; int index = BlockSet.getAssetMap().getIndex(blockSet); if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + blockSet);  item.blockSetIndexes[i] = index; }  }  })).build();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected DurabilityLossBlockTypes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DurabilityLossBlockTypes(String[] blockTypes, String[] blockSets, double durabilityLossOnHit) {
/* 176 */       this.blockTypes = blockTypes;
/* 177 */       this.blockSets = blockSets;
/* 178 */       this.durabilityLossOnHit = durabilityLossOnHit;
/*     */     }
/*     */     
/*     */     public String[] getBlockTypes() {
/* 182 */       return this.blockTypes;
/*     */     }
/*     */     
/*     */     public String[] getBlockSets() {
/* 186 */       return this.blockSets;
/*     */     }
/*     */     
/*     */     public double getDurabilityLossOnHit() {
/* 190 */       return this.durabilityLossOnHit;
/*     */     }
/*     */     
/*     */     public int[] getBlockTypeIndexes() {
/* 194 */       if (this.blockTypes != null && this.blockTypeIndexes == null) {
/* 195 */         int[] blockTypeIndexes = new int[this.blockTypes.length];
/* 196 */         for (int i = 0; i < this.blockTypes.length; i++) {
/* 197 */           String key = this.blockTypes[i];
/* 198 */           int index = BlockType.getAssetMap().getIndex(key);
/* 199 */           if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/* 200 */           blockTypeIndexes[i] = index;
/*     */         } 
/* 202 */         this.blockTypeIndexes = blockTypeIndexes;
/*     */       } 
/*     */       
/* 205 */       return this.blockTypeIndexes;
/*     */     }
/*     */     
/*     */     public int[] getBlockSetIndexes() {
/* 209 */       return this.blockSetIndexes;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 216 */       return "DurabilityLossBlockTypes{blockTypes=" + Arrays.toString((Object[])this.blockTypes) + ", blockSets=" + 
/* 217 */         Arrays.toString((Object[])this.blockSets) + ", durabilityLossOnHit=" + this.durabilityLossOnHit + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemTool.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */