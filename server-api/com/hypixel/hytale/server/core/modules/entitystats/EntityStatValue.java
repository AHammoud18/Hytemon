/*     */ package com.hypixel.hytale.server.core.modules.entitystats;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
/*     */ import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class EntityStatValue
/*     */ {
/*  21 */   public static final EntityStatValue[] EMPTY_ARRAY = new EntityStatValue[0];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<EntityStatValue> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  38 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EntityStatValue.class, EntityStatValue::new).addField(new KeyedCodec("Id", (Codec)Codec.STRING), (regenerating, value) -> regenerating.id = value, regenerating -> regenerating.id)).addField(new KeyedCodec("Value", (Codec)Codec.FLOAT), (regenerating, value) -> regenerating.value = value.floatValue(), regenerating -> Float.valueOf(regenerating.value))).addField(new KeyedCodec("Modifiers", (Codec)new MapCodec((Codec)Modifier.CODEC, java.util.HashMap::new, false)), (regenerating, value) -> regenerating.modifiers = value, regenerating -> (regenerating.modifiers == null || regenerating.modifiers.isEmpty()) ? null : regenerating.modifiers)).build();
/*     */   }
/*     */   
/*  41 */   private int index = Integer.MIN_VALUE;
/*     */   
/*     */   private float value;
/*     */   
/*     */   private float min;
/*     */   
/*     */   private float max;
/*     */   
/*     */   private boolean ignoreInvulnerability;
/*     */   
/*     */   @Nullable
/*     */   private RegeneratingValue[] regeneratingValues;
/*     */   @Nullable
/*     */   private Map<String, Modifier> modifiers;
/*     */   
/*     */   public EntityStatValue(int index, @Nonnull EntityStatType asset) {
/*  57 */     this.id = asset.getId();
/*  58 */     this.index = index;
/*  59 */     this.value = asset.getInitialValue();
/*     */     
/*  61 */     synchronizeAsset(index, asset);
/*     */   }
/*     */   
/*     */   public String getId() {
/*  65 */     return this.id;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  69 */     return this.index;
/*     */   }
/*     */   
/*     */   public float get() {
/*  73 */     return this.value;
/*     */   }
/*     */   
/*     */   public float asPercentage() {
/*  77 */     return (this.value - this.min) / (this.max - this.min);
/*     */   }
/*     */   
/*     */   public float getMin() {
/*  81 */     return this.min;
/*     */   }
/*     */   
/*     */   public float getMax() {
/*  85 */     return this.max;
/*     */   }
/*     */   
/*     */   protected float set(float newValue) {
/*  89 */     return this.value = MathUtil.clamp(newValue, this.min, this.max);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public RegeneratingValue[] getRegeneratingValues() {
/*  94 */     return this.regeneratingValues;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Modifier getModifier(String key) {
/*  99 */     if (this.modifiers == null) return null; 
/* 100 */     return this.modifiers.get(key);
/*     */   }
/*     */   
/*     */   public boolean getIgnoreInvulnerability() {
/* 104 */     return this.ignoreInvulnerability;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Map<String, Modifier> getModifiers() {
/* 109 */     return this.modifiers;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Modifier putModifier(String key, Modifier modifier) {
/* 114 */     if (this.modifiers == null) this.modifiers = (Map<String, Modifier>)new Object2ObjectOpenHashMap();
/*     */     
/* 116 */     Modifier oldModifier = this.modifiers.put(key, modifier);
/*     */     
/* 118 */     computeModifiers((EntityStatType)EntityStatType.getAssetMap().getAsset(this.index));
/* 119 */     return oldModifier;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Modifier removeModifier(String key) {
/* 124 */     if (this.modifiers == null) return null;
/*     */     
/* 126 */     Modifier modifier = this.modifiers.remove(key);
/*     */     
/* 128 */     if (modifier != null) computeModifiers((EntityStatType)EntityStatType.getAssetMap().getAsset(this.index)); 
/* 129 */     return modifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean synchronizeAsset(int index, @Nonnull EntityStatType asset) {
/* 139 */     this.id = asset.getId();
/* 140 */     this.index = index;
/*     */     
/* 142 */     initializeRegenerating(asset);
/*     */     
/* 144 */     boolean minMaxChanged = (this.min != asset.getMin() || this.max != asset.getMax());
/*     */     
/* 146 */     this.ignoreInvulnerability = asset.getIgnoreInvulnerability();
/*     */     
/* 148 */     float oldValue = this.value;
/* 149 */     computeModifiers(asset);
/*     */     
/* 151 */     return (minMaxChanged || this.value != oldValue);
/*     */   }
/*     */   
/*     */   private void initializeRegenerating(@Nonnull EntityStatType entityStatType) {
/* 155 */     EntityStatType.Regenerating[] regeneratingTypes = entityStatType.getRegenerating();
/* 156 */     if (regeneratingTypes == null)
/*     */       return; 
/* 158 */     this.regeneratingValues = new RegeneratingValue[regeneratingTypes.length];
/* 159 */     for (int i = 0; i < regeneratingTypes.length; i++) {
/* 160 */       this.regeneratingValues[i] = new RegeneratingValue(regeneratingTypes[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void computeModifiers(@Nonnull EntityStatType asset) {
/* 165 */     this.min = asset.getMin();
/* 166 */     this.max = asset.getMax();
/*     */     
/* 168 */     if (this.modifiers != null) {
/*     */ 
/*     */ 
/*     */       
/* 172 */       for (Modifier.ModifierTarget target : Modifier.ModifierTarget.VALUES) {
/*     */         
/* 174 */         boolean hasAdditive = false;
/* 175 */         float additive = 0.0F;
/* 176 */         boolean hasMultiplicative = false;
/* 177 */         float multiplicative = 0.0F;
/* 178 */         for (Modifier modifier : this.modifiers.values()) {
/* 179 */           if (modifier instanceof StaticModifier) { StaticModifier staticModifier = (StaticModifier)modifier;
/* 180 */             if (staticModifier.getTarget() != target)
/*     */               continue; 
/* 182 */             switch (staticModifier.getCalculationType()) {
/*     */               case MIN:
/* 184 */                 hasAdditive = true;
/* 185 */                 additive += staticModifier.getAmount();
/*     */               
/*     */               case MAX:
/* 188 */                 hasMultiplicative = true;
/* 189 */                 multiplicative += staticModifier.getAmount();
/*     */             } 
/*     */             
/*     */              }
/*     */         
/*     */         } 
/* 195 */         switch (target) {
/*     */           case MIN:
/* 197 */             if (hasAdditive) this.min = StaticModifier.CalculationType.ADDITIVE.compute(this.min, additive); 
/* 198 */             if (hasMultiplicative) this.min = StaticModifier.CalculationType.MULTIPLICATIVE.compute(this.min, multiplicative); 
/*     */             break;
/*     */           case MAX:
/* 201 */             if (hasAdditive) this.max = StaticModifier.CalculationType.ADDITIVE.compute(this.max, additive); 
/* 202 */             if (hasMultiplicative) this.max = StaticModifier.CalculationType.MULTIPLICATIVE.compute(this.max, multiplicative);
/*     */             
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/* 208 */       for (Modifier modifier : this.modifiers.values()) {
/* 209 */         if (!(modifier instanceof StaticModifier)) {
/* 210 */           applyModifier(modifier);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 216 */     this.value = MathUtil.clamp(this.value, this.min, this.max);
/*     */   }
/*     */   
/*     */   private void applyModifier(@Nonnull Modifier modifier) {
/* 220 */     switch (modifier.getTarget()) { case MIN:
/* 221 */         this.min = modifier.apply(this.min); break;
/* 222 */       case MAX: this.max = modifier.apply(this.max);
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 229 */     return "EntityStatValue{id='" + this.id + "', index=" + this.index + ", value=" + this.value + ", min=" + this.min + ", max=" + this.max + ", regeneratingValues=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 235 */       Arrays.toString((Object[])this.regeneratingValues) + ", modifiers=" + String.valueOf(this.modifiers) + "}";
/*     */   }
/*     */   
/*     */   protected EntityStatValue() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\EntityStatValue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */