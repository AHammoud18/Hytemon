/*     */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.bench;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.inventory.MaterialQuantity;
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcessingBench
/*     */   extends Bench
/*     */ {
/*     */   public static final BuilderCodec<ProcessingBench> CODEC;
/*     */   protected ProcessingSlot[] input;
/*     */   protected ProcessingSlot[] fuel;
/*     */   protected boolean allowNoInputProcessing;
/*     */   protected ExtraOutput extraOutput;
/*     */   
/*     */   static {
/* 113 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ProcessingBench.class, ProcessingBench::new, Bench.BASE_CODEC).append(new KeyedCodec("Input", (Codec)new ArrayCodec((Codec)ProcessingSlot.CODEC, x$0 -> new ProcessingSlot[x$0])), (bench, s) -> bench.input = s, bench -> bench.input).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Fuel", (Codec)new ArrayCodec((Codec)ProcessingSlot.CODEC, x$0 -> new ProcessingSlot[x$0])), (bench, s) -> bench.fuel = s, bench -> bench.fuel).add()).append(new KeyedCodec("MaxFuel", (Codec)Codec.INTEGER), (bench, s) -> bench.maxFuel = s.intValue(), bench -> Integer.valueOf(bench.maxFuel)).add()).append(new KeyedCodec("FuelDropItemId", (Codec)Codec.STRING), (bench, s) -> bench.fuelDropItemId = s, bench -> bench.fuelDropItemId).add()).append(new KeyedCodec("OutputSlotsCount", (Codec)Codec.INTEGER), (bench, s) -> bench.outputSlotsCount = s.intValue(), bench -> Integer.valueOf(bench.outputSlotsCount)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).append(new KeyedCodec("ExtraOutput", (Codec)ExtraOutput.CODEC), (bench, s) -> bench.extraOutput = s, bench -> bench.extraOutput).add()).append(new KeyedCodec("AllowNoInputProcessing", (Codec)Codec.BOOLEAN), (bench, s) -> bench.allowNoInputProcessing = s.booleanValue(), bench -> Boolean.valueOf(bench.allowNoInputProcessing)).add()).append(new KeyedCodec("IconItem", (Codec)Codec.STRING), (bench, s) -> bench.iconItem = s, bench -> bench.iconItem).add()).append(new KeyedCodec("Icon", (Codec)Codec.STRING), (bench, s) -> bench.icon = s, bench -> bench.icon).add()).append(new KeyedCodec("IconName", (Codec)Codec.STRING), (bench, s) -> bench.iconName = s, bench -> bench.iconName).add()).append(new KeyedCodec("IconId", (Codec)Codec.STRING), (bench, s) -> bench.iconId = s, bench -> bench.iconId).add()).append(new KeyedCodec("EndSoundEventId", (Codec)Codec.STRING), (bench, s) -> bench.endSoundEventId = s, bench -> bench.endSoundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).addValidator((Validator)SoundEventValidators.ONESHOT).add()).afterDecode(p -> { if (p.icon != null) { if (p.iconId == null) { String name = p.icon.substring(0, p.icon.indexOf('.')); p.iconId = name.toLowerCase(Locale.ROOT); }  if (p.iconName == null) { String name = p.icon.substring(0, p.icon.indexOf('.')); p.iconName = name.toLowerCase(Locale.ROOT); }  }  if (p.endSoundEventId != null) p.endSoundEventIndex = SoundEvent.getAssetMap().getIndex(p.endSoundEventId);  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   protected int maxFuel = -1;
/*     */   protected String fuelDropItemId;
/* 121 */   protected int outputSlotsCount = 1;
/*     */   protected String iconItem;
/*     */   protected String icon;
/*     */   protected String iconName;
/*     */   protected String iconId;
/*     */   @Nullable
/* 127 */   protected String endSoundEventId = null;
/*     */   
/* 129 */   protected transient int endSoundEventIndex = 0;
/*     */   
/*     */   public String getIconItem() {
/* 132 */     return this.iconItem;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 136 */     return this.icon;
/*     */   }
/*     */   
/*     */   public String getIconName() {
/* 140 */     return this.iconName;
/*     */   }
/*     */   
/*     */   public String getIconId() {
/* 144 */     return this.iconId;
/*     */   }
/*     */   
/*     */   public ProcessingSlot[] getInput(int tierLevel) {
/* 148 */     if (this.tierLevels == null) return this.input; 
/* 149 */     if (tierLevel > this.tierLevels.length) return this.input;
/*     */     
/* 151 */     ProcessingSlot[] result = new ProcessingSlot[this.input.length + (this.tierLevels[tierLevel - 1]).extraInputSlot];
/*     */     
/* 153 */     Arrays.fill((Object[])result, this.input[0]);
/*     */     
/* 155 */     return result;
/*     */   }
/*     */   
/*     */   public ProcessingSlot[] getFuel() {
/* 159 */     return this.fuel;
/*     */   }
/*     */   
/*     */   public int getMaxFuel() {
/* 163 */     return this.maxFuel;
/*     */   }
/*     */   
/*     */   public String getFuelDropItemId() {
/* 167 */     return this.fuelDropItemId;
/*     */   }
/*     */   
/*     */   public int getOutputSlotsCount(int tierLevel) {
/* 171 */     if (this.tierLevels == null) return this.outputSlotsCount; 
/* 172 */     if (tierLevel > this.tierLevels.length) return this.outputSlotsCount; 
/* 173 */     return this.outputSlotsCount + (this.tierLevels[tierLevel - 1]).extraOutputSlot;
/*     */   }
/*     */   
/*     */   public ExtraOutput getExtraOutput() {
/* 177 */     return this.extraOutput;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getEndSoundEventId() {
/* 182 */     return this.endSoundEventId;
/*     */   }
/*     */   
/*     */   public int getEndSoundEventIndex() {
/* 186 */     return this.endSoundEventIndex;
/*     */   }
/*     */   
/*     */   public boolean shouldAllowNoInputProcessing() {
/* 190 */     return this.allowNoInputProcessing;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 195 */     if (o == null || getClass() != o.getClass()) return false; 
/* 196 */     if (!super.equals(o)) return false;
/*     */     
/* 198 */     ProcessingBench that = (ProcessingBench)o;
/* 199 */     return (this.allowNoInputProcessing == that.allowNoInputProcessing && this.maxFuel == that.maxFuel && this.outputSlotsCount == that.outputSlotsCount && this.endSoundEventIndex == that.endSoundEventIndex && Arrays.equals((Object[])this.input, (Object[])that.input) && Arrays.equals((Object[])this.fuel, (Object[])that.fuel) && Objects.equals(this.extraOutput, that.extraOutput) && Objects.equals(this.fuelDropItemId, that.fuelDropItemId) && Objects.equals(this.iconItem, that.iconItem) && Objects.equals(this.icon, that.icon) && Objects.equals(this.iconName, that.iconName) && Objects.equals(this.iconId, that.iconId) && Objects.equals(this.endSoundEventId, that.endSoundEventId));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 204 */     int result = super.hashCode();
/* 205 */     result = 31 * result + Arrays.hashCode((Object[])this.input);
/* 206 */     result = 31 * result + Arrays.hashCode((Object[])this.fuel);
/* 207 */     result = 31 * result + Boolean.hashCode(this.allowNoInputProcessing);
/* 208 */     result = 31 * result + Objects.hashCode(this.extraOutput);
/* 209 */     result = 31 * result + this.maxFuel;
/* 210 */     result = 31 * result + Objects.hashCode(this.fuelDropItemId);
/* 211 */     result = 31 * result + this.outputSlotsCount;
/* 212 */     result = 31 * result + Objects.hashCode(this.iconItem);
/* 213 */     result = 31 * result + Objects.hashCode(this.icon);
/* 214 */     result = 31 * result + Objects.hashCode(this.iconName);
/* 215 */     result = 31 * result + Objects.hashCode(this.iconId);
/* 216 */     result = 31 * result + Objects.hashCode(this.endSoundEventId);
/* 217 */     result = 31 * result + this.endSoundEventIndex;
/* 218 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ProcessingSlot
/*     */     extends Bench.BenchSlot
/*     */   {
/*     */     public static final BuilderCodec<ProcessingSlot> CODEC;
/*     */ 
/*     */     
/*     */     protected boolean filterValidIngredients;
/*     */     
/*     */     protected String resourceTypeId;
/*     */ 
/*     */     
/*     */     static {
/* 235 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ProcessingSlot.class, ProcessingSlot::new, Bench.BenchSlot.CODEC).append(new KeyedCodec("ResourceTypeId", (Codec)Codec.STRING), (benchSlot, s) -> benchSlot.resourceTypeId = s, benchSlot -> benchSlot.resourceTypeId).add()).append(new KeyedCodec("FilterValidIngredients", (Codec)Codec.BOOLEAN), (benchSlot, b) -> benchSlot.filterValidIngredients = b.booleanValue(), benchSlot -> Boolean.valueOf(benchSlot.filterValidIngredients)).add()).build();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String getResourceTypeId() {
/* 241 */       return this.resourceTypeId;
/*     */     }
/*     */     
/*     */     public boolean shouldFilterValidIngredients() {
/* 245 */       return this.filterValidIngredients;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ExtraOutput
/*     */   {
/*     */     public static final BuilderCodec<ExtraOutput> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private MaterialQuantity[] outputs;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int perFuelItemsConsumed;
/*     */ 
/*     */ 
/*     */     
/*     */     private MaterialQuantity[] ignoredFuelSources;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ExtraOutput() {
/* 275 */       this.perFuelItemsConsumed = 1;
/*     */     } static {
/*     */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ExtraOutput.class, ExtraOutput::new).append(new KeyedCodec("Outputs", (Codec)new ArrayCodec((Codec)MaterialQuantity.CODEC, x$0 -> new MaterialQuantity[x$0])), (o, i) -> o.outputs = i, o -> o.outputs).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("PerFuelItemsConsumed", (Codec)Codec.INTEGER), (o, i) -> o.perFuelItemsConsumed = i.intValue(), o -> Integer.valueOf(o.perFuelItemsConsumed)).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(1))).add()).append(new KeyedCodec("IgnoredFuelSources", (Codec)new ArrayCodec((Codec)MaterialQuantity.CODEC, x$0 -> new MaterialQuantity[x$0])), (o, i) -> o.ignoredFuelSources = i, o -> o.ignoredFuelSources).add()).build();
/*     */     } public MaterialQuantity[] getOutputs() {
/* 279 */       return this.outputs;
/*     */     }
/*     */     
/*     */     public int getPerFuelItemsConsumed() {
/* 283 */       return this.perFuelItemsConsumed;
/*     */     }
/*     */     
/*     */     public boolean isIgnoredFuelSource(Item id) {
/* 287 */       if (this.ignoredFuelSources == null) return false; 
/* 288 */       for (MaterialQuantity mat : this.ignoredFuelSources) {
/* 289 */         if (mat.getItemId() != null && mat.getItemId().equals(id.getBlockId())) return true;
/*     */       
/*     */       } 
/* 292 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(@Nullable Object o) {
/* 297 */       if (this == o) return true; 
/* 298 */       if (o == null || getClass() != o.getClass()) return false;
/*     */       
/* 300 */       ExtraOutput that = (ExtraOutput)o;
/*     */       
/* 302 */       if (this.perFuelItemsConsumed != that.perFuelItemsConsumed) return false;
/*     */       
/* 304 */       return Arrays.equals((Object[])this.outputs, (Object[])that.outputs);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 309 */       int result = Arrays.hashCode((Object[])this.outputs);
/* 310 */       result = 31 * result + this.perFuelItemsConsumed;
/* 311 */       return result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\bench\ProcessingBench.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */