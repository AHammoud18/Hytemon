/*      */ package com.hypixel.hytale.protocol;
/*      */ 
/*      */ import com.hypixel.hytale.protocol.io.PacketIO;
/*      */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*      */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*      */ import com.hypixel.hytale.protocol.io.VarInt;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import javax.annotation.Nonnull;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Weather
/*      */ {
/*      */   public static final int NULLABLE_BIT_FIELD_SIZE = 4;
/*      */   public static final int FIXED_BLOCK_SIZE = 30;
/*      */   public static final int VARIABLE_FIELD_COUNT = 24;
/*      */   public static final int VARIABLE_BLOCK_START = 126;
/*      */   public static final int MAX_SIZE = 1677721600;
/*      */   @Nullable
/*      */   public String id;
/*      */   @Nullable
/*      */   public int[] tagIndexes;
/*      */   @Nullable
/*      */   public String stars;
/*      */   @Nullable
/*      */   public Map<Integer, String> moons;
/*      */   @Nullable
/*      */   public Cloud[] clouds;
/*      */   @Nullable
/*      */   public Map<Float, Float> sunlightDampingMultiplier;
/*      */   @Nullable
/*      */   public Map<Float, Color> sunlightColors;
/*      */   @Nullable
/*      */   public Map<Float, ColorAlpha> skyTopColors;
/*      */   @Nullable
/*      */   public Map<Float, ColorAlpha> skyBottomColors;
/*      */   @Nullable
/*      */   public Map<Float, ColorAlpha> skySunsetColors;
/*      */   @Nullable
/*      */   public Map<Float, Color> sunColors;
/*      */   
/*      */   public Weather(@Nullable String id, @Nullable int[] tagIndexes, @Nullable String stars, @Nullable Map<Integer, String> moons, @Nullable Cloud[] clouds, @Nullable Map<Float, Float> sunlightDampingMultiplier, @Nullable Map<Float, Color> sunlightColors, @Nullable Map<Float, ColorAlpha> skyTopColors, @Nullable Map<Float, ColorAlpha> skyBottomColors, @Nullable Map<Float, ColorAlpha> skySunsetColors, @Nullable Map<Float, Color> sunColors, @Nullable Map<Float, Float> sunScales, @Nullable Map<Float, ColorAlpha> sunGlowColors, @Nullable Map<Float, ColorAlpha> moonColors, @Nullable Map<Float, Float> moonScales, @Nullable Map<Float, ColorAlpha> moonGlowColors, @Nullable Map<Float, Color> fogColors, @Nullable Map<Float, Float> fogHeightFalloffs, @Nullable Map<Float, Float> fogDensities, @Nullable String screenEffect, @Nullable Map<Float, ColorAlpha> screenEffectColors, @Nullable Map<Float, Color> colorFilters, @Nullable Map<Float, Color> waterTints, @Nullable WeatherParticle particle, @Nullable NearFar fog, @Nullable FogOptions fogOptions) {
/*   51 */     this.id = id;
/*   52 */     this.tagIndexes = tagIndexes;
/*   53 */     this.stars = stars;
/*   54 */     this.moons = moons;
/*   55 */     this.clouds = clouds;
/*   56 */     this.sunlightDampingMultiplier = sunlightDampingMultiplier;
/*   57 */     this.sunlightColors = sunlightColors;
/*   58 */     this.skyTopColors = skyTopColors;
/*   59 */     this.skyBottomColors = skyBottomColors;
/*   60 */     this.skySunsetColors = skySunsetColors;
/*   61 */     this.sunColors = sunColors;
/*   62 */     this.sunScales = sunScales;
/*   63 */     this.sunGlowColors = sunGlowColors;
/*   64 */     this.moonColors = moonColors;
/*   65 */     this.moonScales = moonScales;
/*   66 */     this.moonGlowColors = moonGlowColors;
/*   67 */     this.fogColors = fogColors;
/*   68 */     this.fogHeightFalloffs = fogHeightFalloffs;
/*   69 */     this.fogDensities = fogDensities;
/*   70 */     this.screenEffect = screenEffect;
/*   71 */     this.screenEffectColors = screenEffectColors;
/*   72 */     this.colorFilters = colorFilters;
/*   73 */     this.waterTints = waterTints;
/*   74 */     this.particle = particle;
/*   75 */     this.fog = fog;
/*   76 */     this.fogOptions = fogOptions; } @Nullable public Map<Float, Float> sunScales; @Nullable public Map<Float, ColorAlpha> sunGlowColors; @Nullable public Map<Float, ColorAlpha> moonColors; @Nullable public Map<Float, Float> moonScales; @Nullable public Map<Float, ColorAlpha> moonGlowColors; @Nullable public Map<Float, Color> fogColors; @Nullable public Map<Float, Float> fogHeightFalloffs; @Nullable public Map<Float, Float> fogDensities; @Nullable public String screenEffect; @Nullable public Map<Float, ColorAlpha> screenEffectColors; @Nullable public Map<Float, Color> colorFilters; @Nullable
/*      */   public Map<Float, Color> waterTints; @Nullable
/*      */   public WeatherParticle particle; @Nullable
/*      */   public NearFar fog; @Nullable
/*   80 */   public FogOptions fogOptions; public Weather() {} public Weather(@Nonnull Weather other) { this.id = other.id;
/*   81 */     this.tagIndexes = other.tagIndexes;
/*   82 */     this.stars = other.stars;
/*   83 */     this.moons = other.moons;
/*   84 */     this.clouds = other.clouds;
/*   85 */     this.sunlightDampingMultiplier = other.sunlightDampingMultiplier;
/*   86 */     this.sunlightColors = other.sunlightColors;
/*   87 */     this.skyTopColors = other.skyTopColors;
/*   88 */     this.skyBottomColors = other.skyBottomColors;
/*   89 */     this.skySunsetColors = other.skySunsetColors;
/*   90 */     this.sunColors = other.sunColors;
/*   91 */     this.sunScales = other.sunScales;
/*   92 */     this.sunGlowColors = other.sunGlowColors;
/*   93 */     this.moonColors = other.moonColors;
/*   94 */     this.moonScales = other.moonScales;
/*   95 */     this.moonGlowColors = other.moonGlowColors;
/*   96 */     this.fogColors = other.fogColors;
/*   97 */     this.fogHeightFalloffs = other.fogHeightFalloffs;
/*   98 */     this.fogDensities = other.fogDensities;
/*   99 */     this.screenEffect = other.screenEffect;
/*  100 */     this.screenEffectColors = other.screenEffectColors;
/*  101 */     this.colorFilters = other.colorFilters;
/*  102 */     this.waterTints = other.waterTints;
/*  103 */     this.particle = other.particle;
/*  104 */     this.fog = other.fog;
/*  105 */     this.fogOptions = other.fogOptions; }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static Weather deserialize(@Nonnull ByteBuf buf, int offset) {
/*  110 */     Weather obj = new Weather();
/*  111 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 4);
/*  112 */     if ((nullBits[3] & 0x1) != 0) obj.fog = NearFar.deserialize(buf, offset + 4); 
/*  113 */     if ((nullBits[3] & 0x2) != 0) obj.fogOptions = FogOptions.deserialize(buf, offset + 12);
/*      */     
/*  115 */     if ((nullBits[0] & 0x1) != 0) {
/*  116 */       int varPos0 = offset + 126 + buf.getIntLE(offset + 30);
/*  117 */       int idLen = VarInt.peek(buf, varPos0);
/*  118 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  119 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  120 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*      */     } 
/*  122 */     if ((nullBits[0] & 0x2) != 0) {
/*  123 */       int varPos1 = offset + 126 + buf.getIntLE(offset + 34);
/*  124 */       int tagIndexesCount = VarInt.peek(buf, varPos1);
/*  125 */       if (tagIndexesCount < 0) throw ProtocolException.negativeLength("TagIndexes", tagIndexesCount); 
/*  126 */       if (tagIndexesCount > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", tagIndexesCount, 4096000); 
/*  127 */       int varIntLen = VarInt.length(buf, varPos1);
/*  128 */       if ((varPos1 + varIntLen) + tagIndexesCount * 4L > buf.readableBytes())
/*  129 */         throw ProtocolException.bufferTooSmall("TagIndexes", varPos1 + varIntLen + tagIndexesCount * 4, buf.readableBytes()); 
/*  130 */       obj.tagIndexes = new int[tagIndexesCount];
/*  131 */       for (int i = 0; i < tagIndexesCount; i++) {
/*  132 */         obj.tagIndexes[i] = buf.getIntLE(varPos1 + varIntLen + i * 4);
/*      */       }
/*      */     } 
/*  135 */     if ((nullBits[0] & 0x4) != 0) {
/*  136 */       int varPos2 = offset + 126 + buf.getIntLE(offset + 38);
/*  137 */       int starsLen = VarInt.peek(buf, varPos2);
/*  138 */       if (starsLen < 0) throw ProtocolException.negativeLength("Stars", starsLen); 
/*  139 */       if (starsLen > 4096000) throw ProtocolException.stringTooLong("Stars", starsLen, 4096000); 
/*  140 */       obj.stars = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*      */     } 
/*  142 */     if ((nullBits[0] & 0x8) != 0) {
/*  143 */       int varPos3 = offset + 126 + buf.getIntLE(offset + 42);
/*  144 */       int moonsCount = VarInt.peek(buf, varPos3);
/*  145 */       if (moonsCount < 0) throw ProtocolException.negativeLength("Moons", moonsCount); 
/*  146 */       if (moonsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Moons", moonsCount, 4096000); 
/*  147 */       int varIntLen = VarInt.length(buf, varPos3);
/*  148 */       obj.moons = new HashMap<>(moonsCount);
/*  149 */       int dictPos = varPos3 + varIntLen;
/*  150 */       for (int i = 0; i < moonsCount; i++) {
/*  151 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/*  152 */         int valLen = VarInt.peek(buf, dictPos);
/*  153 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  154 */         if (valLen > 4096000) throw ProtocolException.stringTooLong("val", valLen, 4096000); 
/*  155 */         int valVarLen = VarInt.length(buf, dictPos);
/*  156 */         String val = PacketIO.readVarString(buf, dictPos);
/*  157 */         dictPos += valVarLen + valLen;
/*  158 */         if (obj.moons.put(Integer.valueOf(key), val) != null)
/*  159 */           throw ProtocolException.duplicateKey("moons", Integer.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  162 */     if ((nullBits[0] & 0x10) != 0) {
/*  163 */       int varPos4 = offset + 126 + buf.getIntLE(offset + 46);
/*  164 */       int cloudsCount = VarInt.peek(buf, varPos4);
/*  165 */       if (cloudsCount < 0) throw ProtocolException.negativeLength("Clouds", cloudsCount); 
/*  166 */       if (cloudsCount > 4096000) throw ProtocolException.arrayTooLong("Clouds", cloudsCount, 4096000); 
/*  167 */       int varIntLen = VarInt.length(buf, varPos4);
/*  168 */       if ((varPos4 + varIntLen) + cloudsCount * 1L > buf.readableBytes())
/*  169 */         throw ProtocolException.bufferTooSmall("Clouds", varPos4 + varIntLen + cloudsCount * 1, buf.readableBytes()); 
/*  170 */       obj.clouds = new Cloud[cloudsCount];
/*  171 */       int elemPos = varPos4 + varIntLen;
/*  172 */       for (int i = 0; i < cloudsCount; i++) {
/*  173 */         obj.clouds[i] = Cloud.deserialize(buf, elemPos);
/*  174 */         elemPos += Cloud.computeBytesConsumed(buf, elemPos);
/*      */       } 
/*      */     } 
/*  177 */     if ((nullBits[0] & 0x20) != 0) {
/*  178 */       int varPos5 = offset + 126 + buf.getIntLE(offset + 50);
/*  179 */       int sunlightDampingMultiplierCount = VarInt.peek(buf, varPos5);
/*  180 */       if (sunlightDampingMultiplierCount < 0) throw ProtocolException.negativeLength("SunlightDampingMultiplier", sunlightDampingMultiplierCount); 
/*  181 */       if (sunlightDampingMultiplierCount > 4096000) throw ProtocolException.dictionaryTooLarge("SunlightDampingMultiplier", sunlightDampingMultiplierCount, 4096000); 
/*  182 */       int varIntLen = VarInt.length(buf, varPos5);
/*  183 */       obj.sunlightDampingMultiplier = new HashMap<>(sunlightDampingMultiplierCount);
/*  184 */       int dictPos = varPos5 + varIntLen;
/*  185 */       for (int i = 0; i < sunlightDampingMultiplierCount; i++) {
/*  186 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  187 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/*  188 */         if (obj.sunlightDampingMultiplier.put(Float.valueOf(key), Float.valueOf(val)) != null)
/*  189 */           throw ProtocolException.duplicateKey("sunlightDampingMultiplier", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  192 */     if ((nullBits[0] & 0x40) != 0) {
/*  193 */       int varPos6 = offset + 126 + buf.getIntLE(offset + 54);
/*  194 */       int sunlightColorsCount = VarInt.peek(buf, varPos6);
/*  195 */       if (sunlightColorsCount < 0) throw ProtocolException.negativeLength("SunlightColors", sunlightColorsCount); 
/*  196 */       if (sunlightColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("SunlightColors", sunlightColorsCount, 4096000); 
/*  197 */       int varIntLen = VarInt.length(buf, varPos6);
/*  198 */       obj.sunlightColors = new HashMap<>(sunlightColorsCount);
/*  199 */       int dictPos = varPos6 + varIntLen;
/*  200 */       for (int i = 0; i < sunlightColorsCount; i++) {
/*  201 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  202 */         Color val = Color.deserialize(buf, dictPos);
/*  203 */         dictPos += Color.computeBytesConsumed(buf, dictPos);
/*  204 */         if (obj.sunlightColors.put(Float.valueOf(key), val) != null)
/*  205 */           throw ProtocolException.duplicateKey("sunlightColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  208 */     if ((nullBits[0] & 0x80) != 0) {
/*  209 */       int varPos7 = offset + 126 + buf.getIntLE(offset + 58);
/*  210 */       int skyTopColorsCount = VarInt.peek(buf, varPos7);
/*  211 */       if (skyTopColorsCount < 0) throw ProtocolException.negativeLength("SkyTopColors", skyTopColorsCount); 
/*  212 */       if (skyTopColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("SkyTopColors", skyTopColorsCount, 4096000); 
/*  213 */       int varIntLen = VarInt.length(buf, varPos7);
/*  214 */       obj.skyTopColors = new HashMap<>(skyTopColorsCount);
/*  215 */       int dictPos = varPos7 + varIntLen;
/*  216 */       for (int i = 0; i < skyTopColorsCount; i++) {
/*  217 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  218 */         ColorAlpha val = ColorAlpha.deserialize(buf, dictPos);
/*  219 */         dictPos += ColorAlpha.computeBytesConsumed(buf, dictPos);
/*  220 */         if (obj.skyTopColors.put(Float.valueOf(key), val) != null)
/*  221 */           throw ProtocolException.duplicateKey("skyTopColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  224 */     if ((nullBits[1] & 0x1) != 0) {
/*  225 */       int varPos8 = offset + 126 + buf.getIntLE(offset + 62);
/*  226 */       int skyBottomColorsCount = VarInt.peek(buf, varPos8);
/*  227 */       if (skyBottomColorsCount < 0) throw ProtocolException.negativeLength("SkyBottomColors", skyBottomColorsCount); 
/*  228 */       if (skyBottomColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("SkyBottomColors", skyBottomColorsCount, 4096000); 
/*  229 */       int varIntLen = VarInt.length(buf, varPos8);
/*  230 */       obj.skyBottomColors = new HashMap<>(skyBottomColorsCount);
/*  231 */       int dictPos = varPos8 + varIntLen;
/*  232 */       for (int i = 0; i < skyBottomColorsCount; i++) {
/*  233 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  234 */         ColorAlpha val = ColorAlpha.deserialize(buf, dictPos);
/*  235 */         dictPos += ColorAlpha.computeBytesConsumed(buf, dictPos);
/*  236 */         if (obj.skyBottomColors.put(Float.valueOf(key), val) != null)
/*  237 */           throw ProtocolException.duplicateKey("skyBottomColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  240 */     if ((nullBits[1] & 0x2) != 0) {
/*  241 */       int varPos9 = offset + 126 + buf.getIntLE(offset + 66);
/*  242 */       int skySunsetColorsCount = VarInt.peek(buf, varPos9);
/*  243 */       if (skySunsetColorsCount < 0) throw ProtocolException.negativeLength("SkySunsetColors", skySunsetColorsCount); 
/*  244 */       if (skySunsetColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("SkySunsetColors", skySunsetColorsCount, 4096000); 
/*  245 */       int varIntLen = VarInt.length(buf, varPos9);
/*  246 */       obj.skySunsetColors = new HashMap<>(skySunsetColorsCount);
/*  247 */       int dictPos = varPos9 + varIntLen;
/*  248 */       for (int i = 0; i < skySunsetColorsCount; i++) {
/*  249 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  250 */         ColorAlpha val = ColorAlpha.deserialize(buf, dictPos);
/*  251 */         dictPos += ColorAlpha.computeBytesConsumed(buf, dictPos);
/*  252 */         if (obj.skySunsetColors.put(Float.valueOf(key), val) != null)
/*  253 */           throw ProtocolException.duplicateKey("skySunsetColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  256 */     if ((nullBits[1] & 0x4) != 0) {
/*  257 */       int varPos10 = offset + 126 + buf.getIntLE(offset + 70);
/*  258 */       int sunColorsCount = VarInt.peek(buf, varPos10);
/*  259 */       if (sunColorsCount < 0) throw ProtocolException.negativeLength("SunColors", sunColorsCount); 
/*  260 */       if (sunColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("SunColors", sunColorsCount, 4096000); 
/*  261 */       int varIntLen = VarInt.length(buf, varPos10);
/*  262 */       obj.sunColors = new HashMap<>(sunColorsCount);
/*  263 */       int dictPos = varPos10 + varIntLen;
/*  264 */       for (int i = 0; i < sunColorsCount; i++) {
/*  265 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  266 */         Color val = Color.deserialize(buf, dictPos);
/*  267 */         dictPos += Color.computeBytesConsumed(buf, dictPos);
/*  268 */         if (obj.sunColors.put(Float.valueOf(key), val) != null)
/*  269 */           throw ProtocolException.duplicateKey("sunColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  272 */     if ((nullBits[1] & 0x8) != 0) {
/*  273 */       int varPos11 = offset + 126 + buf.getIntLE(offset + 74);
/*  274 */       int sunScalesCount = VarInt.peek(buf, varPos11);
/*  275 */       if (sunScalesCount < 0) throw ProtocolException.negativeLength("SunScales", sunScalesCount); 
/*  276 */       if (sunScalesCount > 4096000) throw ProtocolException.dictionaryTooLarge("SunScales", sunScalesCount, 4096000); 
/*  277 */       int varIntLen = VarInt.length(buf, varPos11);
/*  278 */       obj.sunScales = new HashMap<>(sunScalesCount);
/*  279 */       int dictPos = varPos11 + varIntLen;
/*  280 */       for (int i = 0; i < sunScalesCount; i++) {
/*  281 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  282 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/*  283 */         if (obj.sunScales.put(Float.valueOf(key), Float.valueOf(val)) != null)
/*  284 */           throw ProtocolException.duplicateKey("sunScales", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  287 */     if ((nullBits[1] & 0x10) != 0) {
/*  288 */       int varPos12 = offset + 126 + buf.getIntLE(offset + 78);
/*  289 */       int sunGlowColorsCount = VarInt.peek(buf, varPos12);
/*  290 */       if (sunGlowColorsCount < 0) throw ProtocolException.negativeLength("SunGlowColors", sunGlowColorsCount); 
/*  291 */       if (sunGlowColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("SunGlowColors", sunGlowColorsCount, 4096000); 
/*  292 */       int varIntLen = VarInt.length(buf, varPos12);
/*  293 */       obj.sunGlowColors = new HashMap<>(sunGlowColorsCount);
/*  294 */       int dictPos = varPos12 + varIntLen;
/*  295 */       for (int i = 0; i < sunGlowColorsCount; i++) {
/*  296 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  297 */         ColorAlpha val = ColorAlpha.deserialize(buf, dictPos);
/*  298 */         dictPos += ColorAlpha.computeBytesConsumed(buf, dictPos);
/*  299 */         if (obj.sunGlowColors.put(Float.valueOf(key), val) != null)
/*  300 */           throw ProtocolException.duplicateKey("sunGlowColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  303 */     if ((nullBits[1] & 0x20) != 0) {
/*  304 */       int varPos13 = offset + 126 + buf.getIntLE(offset + 82);
/*  305 */       int moonColorsCount = VarInt.peek(buf, varPos13);
/*  306 */       if (moonColorsCount < 0) throw ProtocolException.negativeLength("MoonColors", moonColorsCount); 
/*  307 */       if (moonColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("MoonColors", moonColorsCount, 4096000); 
/*  308 */       int varIntLen = VarInt.length(buf, varPos13);
/*  309 */       obj.moonColors = new HashMap<>(moonColorsCount);
/*  310 */       int dictPos = varPos13 + varIntLen;
/*  311 */       for (int i = 0; i < moonColorsCount; i++) {
/*  312 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  313 */         ColorAlpha val = ColorAlpha.deserialize(buf, dictPos);
/*  314 */         dictPos += ColorAlpha.computeBytesConsumed(buf, dictPos);
/*  315 */         if (obj.moonColors.put(Float.valueOf(key), val) != null)
/*  316 */           throw ProtocolException.duplicateKey("moonColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  319 */     if ((nullBits[1] & 0x40) != 0) {
/*  320 */       int varPos14 = offset + 126 + buf.getIntLE(offset + 86);
/*  321 */       int moonScalesCount = VarInt.peek(buf, varPos14);
/*  322 */       if (moonScalesCount < 0) throw ProtocolException.negativeLength("MoonScales", moonScalesCount); 
/*  323 */       if (moonScalesCount > 4096000) throw ProtocolException.dictionaryTooLarge("MoonScales", moonScalesCount, 4096000); 
/*  324 */       int varIntLen = VarInt.length(buf, varPos14);
/*  325 */       obj.moonScales = new HashMap<>(moonScalesCount);
/*  326 */       int dictPos = varPos14 + varIntLen;
/*  327 */       for (int i = 0; i < moonScalesCount; i++) {
/*  328 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  329 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/*  330 */         if (obj.moonScales.put(Float.valueOf(key), Float.valueOf(val)) != null)
/*  331 */           throw ProtocolException.duplicateKey("moonScales", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  334 */     if ((nullBits[1] & 0x80) != 0) {
/*  335 */       int varPos15 = offset + 126 + buf.getIntLE(offset + 90);
/*  336 */       int moonGlowColorsCount = VarInt.peek(buf, varPos15);
/*  337 */       if (moonGlowColorsCount < 0) throw ProtocolException.negativeLength("MoonGlowColors", moonGlowColorsCount); 
/*  338 */       if (moonGlowColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("MoonGlowColors", moonGlowColorsCount, 4096000); 
/*  339 */       int varIntLen = VarInt.length(buf, varPos15);
/*  340 */       obj.moonGlowColors = new HashMap<>(moonGlowColorsCount);
/*  341 */       int dictPos = varPos15 + varIntLen;
/*  342 */       for (int i = 0; i < moonGlowColorsCount; i++) {
/*  343 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  344 */         ColorAlpha val = ColorAlpha.deserialize(buf, dictPos);
/*  345 */         dictPos += ColorAlpha.computeBytesConsumed(buf, dictPos);
/*  346 */         if (obj.moonGlowColors.put(Float.valueOf(key), val) != null)
/*  347 */           throw ProtocolException.duplicateKey("moonGlowColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  350 */     if ((nullBits[2] & 0x1) != 0) {
/*  351 */       int varPos16 = offset + 126 + buf.getIntLE(offset + 94);
/*  352 */       int fogColorsCount = VarInt.peek(buf, varPos16);
/*  353 */       if (fogColorsCount < 0) throw ProtocolException.negativeLength("FogColors", fogColorsCount); 
/*  354 */       if (fogColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("FogColors", fogColorsCount, 4096000); 
/*  355 */       int varIntLen = VarInt.length(buf, varPos16);
/*  356 */       obj.fogColors = new HashMap<>(fogColorsCount);
/*  357 */       int dictPos = varPos16 + varIntLen;
/*  358 */       for (int i = 0; i < fogColorsCount; i++) {
/*  359 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  360 */         Color val = Color.deserialize(buf, dictPos);
/*  361 */         dictPos += Color.computeBytesConsumed(buf, dictPos);
/*  362 */         if (obj.fogColors.put(Float.valueOf(key), val) != null)
/*  363 */           throw ProtocolException.duplicateKey("fogColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  366 */     if ((nullBits[2] & 0x2) != 0) {
/*  367 */       int varPos17 = offset + 126 + buf.getIntLE(offset + 98);
/*  368 */       int fogHeightFalloffsCount = VarInt.peek(buf, varPos17);
/*  369 */       if (fogHeightFalloffsCount < 0) throw ProtocolException.negativeLength("FogHeightFalloffs", fogHeightFalloffsCount); 
/*  370 */       if (fogHeightFalloffsCount > 4096000) throw ProtocolException.dictionaryTooLarge("FogHeightFalloffs", fogHeightFalloffsCount, 4096000); 
/*  371 */       int varIntLen = VarInt.length(buf, varPos17);
/*  372 */       obj.fogHeightFalloffs = new HashMap<>(fogHeightFalloffsCount);
/*  373 */       int dictPos = varPos17 + varIntLen;
/*  374 */       for (int i = 0; i < fogHeightFalloffsCount; i++) {
/*  375 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  376 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/*  377 */         if (obj.fogHeightFalloffs.put(Float.valueOf(key), Float.valueOf(val)) != null)
/*  378 */           throw ProtocolException.duplicateKey("fogHeightFalloffs", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  381 */     if ((nullBits[2] & 0x4) != 0) {
/*  382 */       int varPos18 = offset + 126 + buf.getIntLE(offset + 102);
/*  383 */       int fogDensitiesCount = VarInt.peek(buf, varPos18);
/*  384 */       if (fogDensitiesCount < 0) throw ProtocolException.negativeLength("FogDensities", fogDensitiesCount); 
/*  385 */       if (fogDensitiesCount > 4096000) throw ProtocolException.dictionaryTooLarge("FogDensities", fogDensitiesCount, 4096000); 
/*  386 */       int varIntLen = VarInt.length(buf, varPos18);
/*  387 */       obj.fogDensities = new HashMap<>(fogDensitiesCount);
/*  388 */       int dictPos = varPos18 + varIntLen;
/*  389 */       for (int i = 0; i < fogDensitiesCount; i++) {
/*  390 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  391 */         float val = buf.getFloatLE(dictPos); dictPos += 4;
/*  392 */         if (obj.fogDensities.put(Float.valueOf(key), Float.valueOf(val)) != null)
/*  393 */           throw ProtocolException.duplicateKey("fogDensities", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  396 */     if ((nullBits[2] & 0x8) != 0) {
/*  397 */       int varPos19 = offset + 126 + buf.getIntLE(offset + 106);
/*  398 */       int screenEffectLen = VarInt.peek(buf, varPos19);
/*  399 */       if (screenEffectLen < 0) throw ProtocolException.negativeLength("ScreenEffect", screenEffectLen); 
/*  400 */       if (screenEffectLen > 4096000) throw ProtocolException.stringTooLong("ScreenEffect", screenEffectLen, 4096000); 
/*  401 */       obj.screenEffect = PacketIO.readVarString(buf, varPos19, PacketIO.UTF8);
/*      */     } 
/*  403 */     if ((nullBits[2] & 0x10) != 0) {
/*  404 */       int varPos20 = offset + 126 + buf.getIntLE(offset + 110);
/*  405 */       int screenEffectColorsCount = VarInt.peek(buf, varPos20);
/*  406 */       if (screenEffectColorsCount < 0) throw ProtocolException.negativeLength("ScreenEffectColors", screenEffectColorsCount); 
/*  407 */       if (screenEffectColorsCount > 4096000) throw ProtocolException.dictionaryTooLarge("ScreenEffectColors", screenEffectColorsCount, 4096000); 
/*  408 */       int varIntLen = VarInt.length(buf, varPos20);
/*  409 */       obj.screenEffectColors = new HashMap<>(screenEffectColorsCount);
/*  410 */       int dictPos = varPos20 + varIntLen;
/*  411 */       for (int i = 0; i < screenEffectColorsCount; i++) {
/*  412 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  413 */         ColorAlpha val = ColorAlpha.deserialize(buf, dictPos);
/*  414 */         dictPos += ColorAlpha.computeBytesConsumed(buf, dictPos);
/*  415 */         if (obj.screenEffectColors.put(Float.valueOf(key), val) != null)
/*  416 */           throw ProtocolException.duplicateKey("screenEffectColors", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  419 */     if ((nullBits[2] & 0x20) != 0) {
/*  420 */       int varPos21 = offset + 126 + buf.getIntLE(offset + 114);
/*  421 */       int colorFiltersCount = VarInt.peek(buf, varPos21);
/*  422 */       if (colorFiltersCount < 0) throw ProtocolException.negativeLength("ColorFilters", colorFiltersCount); 
/*  423 */       if (colorFiltersCount > 4096000) throw ProtocolException.dictionaryTooLarge("ColorFilters", colorFiltersCount, 4096000); 
/*  424 */       int varIntLen = VarInt.length(buf, varPos21);
/*  425 */       obj.colorFilters = new HashMap<>(colorFiltersCount);
/*  426 */       int dictPos = varPos21 + varIntLen;
/*  427 */       for (int i = 0; i < colorFiltersCount; i++) {
/*  428 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  429 */         Color val = Color.deserialize(buf, dictPos);
/*  430 */         dictPos += Color.computeBytesConsumed(buf, dictPos);
/*  431 */         if (obj.colorFilters.put(Float.valueOf(key), val) != null)
/*  432 */           throw ProtocolException.duplicateKey("colorFilters", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  435 */     if ((nullBits[2] & 0x40) != 0) {
/*  436 */       int varPos22 = offset + 126 + buf.getIntLE(offset + 118);
/*  437 */       int waterTintsCount = VarInt.peek(buf, varPos22);
/*  438 */       if (waterTintsCount < 0) throw ProtocolException.negativeLength("WaterTints", waterTintsCount); 
/*  439 */       if (waterTintsCount > 4096000) throw ProtocolException.dictionaryTooLarge("WaterTints", waterTintsCount, 4096000); 
/*  440 */       int varIntLen = VarInt.length(buf, varPos22);
/*  441 */       obj.waterTints = new HashMap<>(waterTintsCount);
/*  442 */       int dictPos = varPos22 + varIntLen;
/*  443 */       for (int i = 0; i < waterTintsCount; i++) {
/*  444 */         float key = buf.getFloatLE(dictPos); dictPos += 4;
/*  445 */         Color val = Color.deserialize(buf, dictPos);
/*  446 */         dictPos += Color.computeBytesConsumed(buf, dictPos);
/*  447 */         if (obj.waterTints.put(Float.valueOf(key), val) != null)
/*  448 */           throw ProtocolException.duplicateKey("waterTints", Float.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  451 */     if ((nullBits[2] & 0x80) != 0) {
/*  452 */       int varPos23 = offset + 126 + buf.getIntLE(offset + 122);
/*  453 */       obj.particle = WeatherParticle.deserialize(buf, varPos23);
/*      */     } 
/*      */     
/*  456 */     return obj;
/*      */   }
/*      */   
/*      */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  460 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 4);
/*  461 */     int maxEnd = 126;
/*  462 */     if ((nullBits[0] & 0x1) != 0) {
/*  463 */       int fieldOffset0 = buf.getIntLE(offset + 30);
/*  464 */       int pos0 = offset + 126 + fieldOffset0;
/*  465 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  466 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*      */     } 
/*  468 */     if ((nullBits[0] & 0x2) != 0) {
/*  469 */       int fieldOffset1 = buf.getIntLE(offset + 34);
/*  470 */       int pos1 = offset + 126 + fieldOffset1;
/*  471 */       int arrLen = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + arrLen * 4;
/*  472 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*      */     } 
/*  474 */     if ((nullBits[0] & 0x4) != 0) {
/*  475 */       int fieldOffset2 = buf.getIntLE(offset + 38);
/*  476 */       int pos2 = offset + 126 + fieldOffset2;
/*  477 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  478 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*      */     } 
/*  480 */     if ((nullBits[0] & 0x8) != 0) {
/*  481 */       int fieldOffset3 = buf.getIntLE(offset + 42);
/*  482 */       int pos3 = offset + 126 + fieldOffset3;
/*  483 */       int dictLen = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3);
/*  484 */       for (int i = 0; i < dictLen; ) { pos3 += 4; int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl; i++; }
/*  485 */        if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*      */     } 
/*  487 */     if ((nullBits[0] & 0x10) != 0) {
/*  488 */       int fieldOffset4 = buf.getIntLE(offset + 46);
/*  489 */       int pos4 = offset + 126 + fieldOffset4;
/*  490 */       int arrLen = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4);
/*  491 */       for (int i = 0; i < arrLen; ) { pos4 += Cloud.computeBytesConsumed(buf, pos4); i++; }
/*  492 */        if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*      */     } 
/*  494 */     if ((nullBits[0] & 0x20) != 0) {
/*  495 */       int fieldOffset5 = buf.getIntLE(offset + 50);
/*  496 */       int pos5 = offset + 126 + fieldOffset5;
/*  497 */       int dictLen = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5);
/*  498 */       for (int i = 0; i < dictLen; ) { pos5 += 4; pos5 += 4; i++; }
/*  499 */        if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*      */     } 
/*  501 */     if ((nullBits[0] & 0x40) != 0) {
/*  502 */       int fieldOffset6 = buf.getIntLE(offset + 54);
/*  503 */       int pos6 = offset + 126 + fieldOffset6;
/*  504 */       int dictLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/*  505 */       for (int i = 0; i < dictLen; ) { pos6 += 4; pos6 += Color.computeBytesConsumed(buf, pos6); i++; }
/*  506 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*      */     } 
/*  508 */     if ((nullBits[0] & 0x80) != 0) {
/*  509 */       int fieldOffset7 = buf.getIntLE(offset + 58);
/*  510 */       int pos7 = offset + 126 + fieldOffset7;
/*  511 */       int dictLen = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7);
/*  512 */       for (int i = 0; i < dictLen; ) { pos7 += 4; pos7 += ColorAlpha.computeBytesConsumed(buf, pos7); i++; }
/*  513 */        if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*      */     } 
/*  515 */     if ((nullBits[1] & 0x1) != 0) {
/*  516 */       int fieldOffset8 = buf.getIntLE(offset + 62);
/*  517 */       int pos8 = offset + 126 + fieldOffset8;
/*  518 */       int dictLen = VarInt.peek(buf, pos8); pos8 += VarInt.length(buf, pos8);
/*  519 */       for (int i = 0; i < dictLen; ) { pos8 += 4; pos8 += ColorAlpha.computeBytesConsumed(buf, pos8); i++; }
/*  520 */        if (pos8 - offset > maxEnd) maxEnd = pos8 - offset; 
/*      */     } 
/*  522 */     if ((nullBits[1] & 0x2) != 0) {
/*  523 */       int fieldOffset9 = buf.getIntLE(offset + 66);
/*  524 */       int pos9 = offset + 126 + fieldOffset9;
/*  525 */       int dictLen = VarInt.peek(buf, pos9); pos9 += VarInt.length(buf, pos9);
/*  526 */       for (int i = 0; i < dictLen; ) { pos9 += 4; pos9 += ColorAlpha.computeBytesConsumed(buf, pos9); i++; }
/*  527 */        if (pos9 - offset > maxEnd) maxEnd = pos9 - offset; 
/*      */     } 
/*  529 */     if ((nullBits[1] & 0x4) != 0) {
/*  530 */       int fieldOffset10 = buf.getIntLE(offset + 70);
/*  531 */       int pos10 = offset + 126 + fieldOffset10;
/*  532 */       int dictLen = VarInt.peek(buf, pos10); pos10 += VarInt.length(buf, pos10);
/*  533 */       for (int i = 0; i < dictLen; ) { pos10 += 4; pos10 += Color.computeBytesConsumed(buf, pos10); i++; }
/*  534 */        if (pos10 - offset > maxEnd) maxEnd = pos10 - offset; 
/*      */     } 
/*  536 */     if ((nullBits[1] & 0x8) != 0) {
/*  537 */       int fieldOffset11 = buf.getIntLE(offset + 74);
/*  538 */       int pos11 = offset + 126 + fieldOffset11;
/*  539 */       int dictLen = VarInt.peek(buf, pos11); pos11 += VarInt.length(buf, pos11);
/*  540 */       for (int i = 0; i < dictLen; ) { pos11 += 4; pos11 += 4; i++; }
/*  541 */        if (pos11 - offset > maxEnd) maxEnd = pos11 - offset; 
/*      */     } 
/*  543 */     if ((nullBits[1] & 0x10) != 0) {
/*  544 */       int fieldOffset12 = buf.getIntLE(offset + 78);
/*  545 */       int pos12 = offset + 126 + fieldOffset12;
/*  546 */       int dictLen = VarInt.peek(buf, pos12); pos12 += VarInt.length(buf, pos12);
/*  547 */       for (int i = 0; i < dictLen; ) { pos12 += 4; pos12 += ColorAlpha.computeBytesConsumed(buf, pos12); i++; }
/*  548 */        if (pos12 - offset > maxEnd) maxEnd = pos12 - offset; 
/*      */     } 
/*  550 */     if ((nullBits[1] & 0x20) != 0) {
/*  551 */       int fieldOffset13 = buf.getIntLE(offset + 82);
/*  552 */       int pos13 = offset + 126 + fieldOffset13;
/*  553 */       int dictLen = VarInt.peek(buf, pos13); pos13 += VarInt.length(buf, pos13);
/*  554 */       for (int i = 0; i < dictLen; ) { pos13 += 4; pos13 += ColorAlpha.computeBytesConsumed(buf, pos13); i++; }
/*  555 */        if (pos13 - offset > maxEnd) maxEnd = pos13 - offset; 
/*      */     } 
/*  557 */     if ((nullBits[1] & 0x40) != 0) {
/*  558 */       int fieldOffset14 = buf.getIntLE(offset + 86);
/*  559 */       int pos14 = offset + 126 + fieldOffset14;
/*  560 */       int dictLen = VarInt.peek(buf, pos14); pos14 += VarInt.length(buf, pos14);
/*  561 */       for (int i = 0; i < dictLen; ) { pos14 += 4; pos14 += 4; i++; }
/*  562 */        if (pos14 - offset > maxEnd) maxEnd = pos14 - offset; 
/*      */     } 
/*  564 */     if ((nullBits[1] & 0x80) != 0) {
/*  565 */       int fieldOffset15 = buf.getIntLE(offset + 90);
/*  566 */       int pos15 = offset + 126 + fieldOffset15;
/*  567 */       int dictLen = VarInt.peek(buf, pos15); pos15 += VarInt.length(buf, pos15);
/*  568 */       for (int i = 0; i < dictLen; ) { pos15 += 4; pos15 += ColorAlpha.computeBytesConsumed(buf, pos15); i++; }
/*  569 */        if (pos15 - offset > maxEnd) maxEnd = pos15 - offset; 
/*      */     } 
/*  571 */     if ((nullBits[2] & 0x1) != 0) {
/*  572 */       int fieldOffset16 = buf.getIntLE(offset + 94);
/*  573 */       int pos16 = offset + 126 + fieldOffset16;
/*  574 */       int dictLen = VarInt.peek(buf, pos16); pos16 += VarInt.length(buf, pos16);
/*  575 */       for (int i = 0; i < dictLen; ) { pos16 += 4; pos16 += Color.computeBytesConsumed(buf, pos16); i++; }
/*  576 */        if (pos16 - offset > maxEnd) maxEnd = pos16 - offset; 
/*      */     } 
/*  578 */     if ((nullBits[2] & 0x2) != 0) {
/*  579 */       int fieldOffset17 = buf.getIntLE(offset + 98);
/*  580 */       int pos17 = offset + 126 + fieldOffset17;
/*  581 */       int dictLen = VarInt.peek(buf, pos17); pos17 += VarInt.length(buf, pos17);
/*  582 */       for (int i = 0; i < dictLen; ) { pos17 += 4; pos17 += 4; i++; }
/*  583 */        if (pos17 - offset > maxEnd) maxEnd = pos17 - offset; 
/*      */     } 
/*  585 */     if ((nullBits[2] & 0x4) != 0) {
/*  586 */       int fieldOffset18 = buf.getIntLE(offset + 102);
/*  587 */       int pos18 = offset + 126 + fieldOffset18;
/*  588 */       int dictLen = VarInt.peek(buf, pos18); pos18 += VarInt.length(buf, pos18);
/*  589 */       for (int i = 0; i < dictLen; ) { pos18 += 4; pos18 += 4; i++; }
/*  590 */        if (pos18 - offset > maxEnd) maxEnd = pos18 - offset; 
/*      */     } 
/*  592 */     if ((nullBits[2] & 0x8) != 0) {
/*  593 */       int fieldOffset19 = buf.getIntLE(offset + 106);
/*  594 */       int pos19 = offset + 126 + fieldOffset19;
/*  595 */       int sl = VarInt.peek(buf, pos19); pos19 += VarInt.length(buf, pos19) + sl;
/*  596 */       if (pos19 - offset > maxEnd) maxEnd = pos19 - offset; 
/*      */     } 
/*  598 */     if ((nullBits[2] & 0x10) != 0) {
/*  599 */       int fieldOffset20 = buf.getIntLE(offset + 110);
/*  600 */       int pos20 = offset + 126 + fieldOffset20;
/*  601 */       int dictLen = VarInt.peek(buf, pos20); pos20 += VarInt.length(buf, pos20);
/*  602 */       for (int i = 0; i < dictLen; ) { pos20 += 4; pos20 += ColorAlpha.computeBytesConsumed(buf, pos20); i++; }
/*  603 */        if (pos20 - offset > maxEnd) maxEnd = pos20 - offset; 
/*      */     } 
/*  605 */     if ((nullBits[2] & 0x20) != 0) {
/*  606 */       int fieldOffset21 = buf.getIntLE(offset + 114);
/*  607 */       int pos21 = offset + 126 + fieldOffset21;
/*  608 */       int dictLen = VarInt.peek(buf, pos21); pos21 += VarInt.length(buf, pos21);
/*  609 */       for (int i = 0; i < dictLen; ) { pos21 += 4; pos21 += Color.computeBytesConsumed(buf, pos21); i++; }
/*  610 */        if (pos21 - offset > maxEnd) maxEnd = pos21 - offset; 
/*      */     } 
/*  612 */     if ((nullBits[2] & 0x40) != 0) {
/*  613 */       int fieldOffset22 = buf.getIntLE(offset + 118);
/*  614 */       int pos22 = offset + 126 + fieldOffset22;
/*  615 */       int dictLen = VarInt.peek(buf, pos22); pos22 += VarInt.length(buf, pos22);
/*  616 */       for (int i = 0; i < dictLen; ) { pos22 += 4; pos22 += Color.computeBytesConsumed(buf, pos22); i++; }
/*  617 */        if (pos22 - offset > maxEnd) maxEnd = pos22 - offset; 
/*      */     } 
/*  619 */     if ((nullBits[2] & 0x80) != 0) {
/*  620 */       int fieldOffset23 = buf.getIntLE(offset + 122);
/*  621 */       int pos23 = offset + 126 + fieldOffset23;
/*  622 */       pos23 += WeatherParticle.computeBytesConsumed(buf, pos23);
/*  623 */       if (pos23 - offset > maxEnd) maxEnd = pos23 - offset; 
/*      */     } 
/*  625 */     return maxEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   public void serialize(@Nonnull ByteBuf buf) {
/*  630 */     int startPos = buf.writerIndex();
/*  631 */     byte[] nullBits = new byte[4];
/*  632 */     if (this.id != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/*  633 */     if (this.tagIndexes != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/*  634 */     if (this.stars != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/*  635 */     if (this.moons != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/*  636 */     if (this.clouds != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/*  637 */     if (this.sunlightDampingMultiplier != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/*  638 */     if (this.sunlightColors != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/*  639 */     if (this.skyTopColors != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/*  640 */     if (this.skyBottomColors != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/*  641 */     if (this.skySunsetColors != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/*  642 */     if (this.sunColors != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/*  643 */     if (this.sunScales != null) nullBits[1] = (byte)(nullBits[1] | 0x8); 
/*  644 */     if (this.sunGlowColors != null) nullBits[1] = (byte)(nullBits[1] | 0x10); 
/*  645 */     if (this.moonColors != null) nullBits[1] = (byte)(nullBits[1] | 0x20); 
/*  646 */     if (this.moonScales != null) nullBits[1] = (byte)(nullBits[1] | 0x40); 
/*  647 */     if (this.moonGlowColors != null) nullBits[1] = (byte)(nullBits[1] | 0x80); 
/*  648 */     if (this.fogColors != null) nullBits[2] = (byte)(nullBits[2] | 0x1); 
/*  649 */     if (this.fogHeightFalloffs != null) nullBits[2] = (byte)(nullBits[2] | 0x2); 
/*  650 */     if (this.fogDensities != null) nullBits[2] = (byte)(nullBits[2] | 0x4); 
/*  651 */     if (this.screenEffect != null) nullBits[2] = (byte)(nullBits[2] | 0x8); 
/*  652 */     if (this.screenEffectColors != null) nullBits[2] = (byte)(nullBits[2] | 0x10); 
/*  653 */     if (this.colorFilters != null) nullBits[2] = (byte)(nullBits[2] | 0x20); 
/*  654 */     if (this.waterTints != null) nullBits[2] = (byte)(nullBits[2] | 0x40); 
/*  655 */     if (this.particle != null) nullBits[2] = (byte)(nullBits[2] | 0x80); 
/*  656 */     if (this.fog != null) nullBits[3] = (byte)(nullBits[3] | 0x1); 
/*  657 */     if (this.fogOptions != null) nullBits[3] = (byte)(nullBits[3] | 0x2); 
/*  658 */     buf.writeBytes(nullBits);
/*      */     
/*  660 */     if (this.fog != null) { this.fog.serialize(buf); } else { buf.writeZero(8); }
/*  661 */      if (this.fogOptions != null) { this.fogOptions.serialize(buf); } else { buf.writeZero(18); }
/*      */     
/*  663 */     int idOffsetSlot = buf.writerIndex();
/*  664 */     buf.writeIntLE(0);
/*  665 */     int tagIndexesOffsetSlot = buf.writerIndex();
/*  666 */     buf.writeIntLE(0);
/*  667 */     int starsOffsetSlot = buf.writerIndex();
/*  668 */     buf.writeIntLE(0);
/*  669 */     int moonsOffsetSlot = buf.writerIndex();
/*  670 */     buf.writeIntLE(0);
/*  671 */     int cloudsOffsetSlot = buf.writerIndex();
/*  672 */     buf.writeIntLE(0);
/*  673 */     int sunlightDampingMultiplierOffsetSlot = buf.writerIndex();
/*  674 */     buf.writeIntLE(0);
/*  675 */     int sunlightColorsOffsetSlot = buf.writerIndex();
/*  676 */     buf.writeIntLE(0);
/*  677 */     int skyTopColorsOffsetSlot = buf.writerIndex();
/*  678 */     buf.writeIntLE(0);
/*  679 */     int skyBottomColorsOffsetSlot = buf.writerIndex();
/*  680 */     buf.writeIntLE(0);
/*  681 */     int skySunsetColorsOffsetSlot = buf.writerIndex();
/*  682 */     buf.writeIntLE(0);
/*  683 */     int sunColorsOffsetSlot = buf.writerIndex();
/*  684 */     buf.writeIntLE(0);
/*  685 */     int sunScalesOffsetSlot = buf.writerIndex();
/*  686 */     buf.writeIntLE(0);
/*  687 */     int sunGlowColorsOffsetSlot = buf.writerIndex();
/*  688 */     buf.writeIntLE(0);
/*  689 */     int moonColorsOffsetSlot = buf.writerIndex();
/*  690 */     buf.writeIntLE(0);
/*  691 */     int moonScalesOffsetSlot = buf.writerIndex();
/*  692 */     buf.writeIntLE(0);
/*  693 */     int moonGlowColorsOffsetSlot = buf.writerIndex();
/*  694 */     buf.writeIntLE(0);
/*  695 */     int fogColorsOffsetSlot = buf.writerIndex();
/*  696 */     buf.writeIntLE(0);
/*  697 */     int fogHeightFalloffsOffsetSlot = buf.writerIndex();
/*  698 */     buf.writeIntLE(0);
/*  699 */     int fogDensitiesOffsetSlot = buf.writerIndex();
/*  700 */     buf.writeIntLE(0);
/*  701 */     int screenEffectOffsetSlot = buf.writerIndex();
/*  702 */     buf.writeIntLE(0);
/*  703 */     int screenEffectColorsOffsetSlot = buf.writerIndex();
/*  704 */     buf.writeIntLE(0);
/*  705 */     int colorFiltersOffsetSlot = buf.writerIndex();
/*  706 */     buf.writeIntLE(0);
/*  707 */     int waterTintsOffsetSlot = buf.writerIndex();
/*  708 */     buf.writeIntLE(0);
/*  709 */     int particleOffsetSlot = buf.writerIndex();
/*  710 */     buf.writeIntLE(0);
/*      */     
/*  712 */     int varBlockStart = buf.writerIndex();
/*  713 */     if (this.id != null) {
/*  714 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/*  715 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*      */     } else {
/*  717 */       buf.setIntLE(idOffsetSlot, -1);
/*      */     } 
/*  719 */     if (this.tagIndexes != null) {
/*  720 */       buf.setIntLE(tagIndexesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  721 */       if (this.tagIndexes.length > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", this.tagIndexes.length, 4096000);  VarInt.write(buf, this.tagIndexes.length); for (int item : this.tagIndexes) buf.writeIntLE(item); 
/*      */     } else {
/*  723 */       buf.setIntLE(tagIndexesOffsetSlot, -1);
/*      */     } 
/*  725 */     if (this.stars != null) {
/*  726 */       buf.setIntLE(starsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  727 */       PacketIO.writeVarString(buf, this.stars, 4096000);
/*      */     } else {
/*  729 */       buf.setIntLE(starsOffsetSlot, -1);
/*      */     } 
/*  731 */     if (this.moons != null)
/*  732 */     { buf.setIntLE(moonsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  733 */       if (this.moons.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Moons", this.moons.size(), 4096000);  VarInt.write(buf, this.moons.size()); for (Map.Entry<Integer, String> e : this.moons.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); PacketIO.writeVarString(buf, e.getValue(), 4096000); }
/*      */        }
/*  735 */     else { buf.setIntLE(moonsOffsetSlot, -1); }
/*      */     
/*  737 */     if (this.clouds != null) {
/*  738 */       buf.setIntLE(cloudsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  739 */       if (this.clouds.length > 4096000) throw ProtocolException.arrayTooLong("Clouds", this.clouds.length, 4096000);  VarInt.write(buf, this.clouds.length); for (Cloud item : this.clouds) item.serialize(buf); 
/*      */     } else {
/*  741 */       buf.setIntLE(cloudsOffsetSlot, -1);
/*      */     } 
/*  743 */     if (this.sunlightDampingMultiplier != null)
/*  744 */     { buf.setIntLE(sunlightDampingMultiplierOffsetSlot, buf.writerIndex() - varBlockStart);
/*  745 */       if (this.sunlightDampingMultiplier.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SunlightDampingMultiplier", this.sunlightDampingMultiplier.size(), 4096000);  VarInt.write(buf, this.sunlightDampingMultiplier.size()); for (Map.Entry<Float, Float> e : this.sunlightDampingMultiplier.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*      */        }
/*  747 */     else { buf.setIntLE(sunlightDampingMultiplierOffsetSlot, -1); }
/*      */     
/*  749 */     if (this.sunlightColors != null)
/*  750 */     { buf.setIntLE(sunlightColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  751 */       if (this.sunlightColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SunlightColors", this.sunlightColors.size(), 4096000);  VarInt.write(buf, this.sunlightColors.size()); for (Map.Entry<Float, Color> e : this.sunlightColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((Color)e.getValue()).serialize(buf); }
/*      */        }
/*  753 */     else { buf.setIntLE(sunlightColorsOffsetSlot, -1); }
/*      */     
/*  755 */     if (this.skyTopColors != null)
/*  756 */     { buf.setIntLE(skyTopColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  757 */       if (this.skyTopColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SkyTopColors", this.skyTopColors.size(), 4096000);  VarInt.write(buf, this.skyTopColors.size()); for (Map.Entry<Float, ColorAlpha> e : this.skyTopColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((ColorAlpha)e.getValue()).serialize(buf); }
/*      */        }
/*  759 */     else { buf.setIntLE(skyTopColorsOffsetSlot, -1); }
/*      */     
/*  761 */     if (this.skyBottomColors != null)
/*  762 */     { buf.setIntLE(skyBottomColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  763 */       if (this.skyBottomColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SkyBottomColors", this.skyBottomColors.size(), 4096000);  VarInt.write(buf, this.skyBottomColors.size()); for (Map.Entry<Float, ColorAlpha> e : this.skyBottomColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((ColorAlpha)e.getValue()).serialize(buf); }
/*      */        }
/*  765 */     else { buf.setIntLE(skyBottomColorsOffsetSlot, -1); }
/*      */     
/*  767 */     if (this.skySunsetColors != null)
/*  768 */     { buf.setIntLE(skySunsetColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  769 */       if (this.skySunsetColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SkySunsetColors", this.skySunsetColors.size(), 4096000);  VarInt.write(buf, this.skySunsetColors.size()); for (Map.Entry<Float, ColorAlpha> e : this.skySunsetColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((ColorAlpha)e.getValue()).serialize(buf); }
/*      */        }
/*  771 */     else { buf.setIntLE(skySunsetColorsOffsetSlot, -1); }
/*      */     
/*  773 */     if (this.sunColors != null)
/*  774 */     { buf.setIntLE(sunColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  775 */       if (this.sunColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SunColors", this.sunColors.size(), 4096000);  VarInt.write(buf, this.sunColors.size()); for (Map.Entry<Float, Color> e : this.sunColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((Color)e.getValue()).serialize(buf); }
/*      */        }
/*  777 */     else { buf.setIntLE(sunColorsOffsetSlot, -1); }
/*      */     
/*  779 */     if (this.sunScales != null)
/*  780 */     { buf.setIntLE(sunScalesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  781 */       if (this.sunScales.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SunScales", this.sunScales.size(), 4096000);  VarInt.write(buf, this.sunScales.size()); for (Map.Entry<Float, Float> e : this.sunScales.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*      */        }
/*  783 */     else { buf.setIntLE(sunScalesOffsetSlot, -1); }
/*      */     
/*  785 */     if (this.sunGlowColors != null)
/*  786 */     { buf.setIntLE(sunGlowColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  787 */       if (this.sunGlowColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("SunGlowColors", this.sunGlowColors.size(), 4096000);  VarInt.write(buf, this.sunGlowColors.size()); for (Map.Entry<Float, ColorAlpha> e : this.sunGlowColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((ColorAlpha)e.getValue()).serialize(buf); }
/*      */        }
/*  789 */     else { buf.setIntLE(sunGlowColorsOffsetSlot, -1); }
/*      */     
/*  791 */     if (this.moonColors != null)
/*  792 */     { buf.setIntLE(moonColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  793 */       if (this.moonColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("MoonColors", this.moonColors.size(), 4096000);  VarInt.write(buf, this.moonColors.size()); for (Map.Entry<Float, ColorAlpha> e : this.moonColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((ColorAlpha)e.getValue()).serialize(buf); }
/*      */        }
/*  795 */     else { buf.setIntLE(moonColorsOffsetSlot, -1); }
/*      */     
/*  797 */     if (this.moonScales != null)
/*  798 */     { buf.setIntLE(moonScalesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  799 */       if (this.moonScales.size() > 4096000) throw ProtocolException.dictionaryTooLarge("MoonScales", this.moonScales.size(), 4096000);  VarInt.write(buf, this.moonScales.size()); for (Map.Entry<Float, Float> e : this.moonScales.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*      */        }
/*  801 */     else { buf.setIntLE(moonScalesOffsetSlot, -1); }
/*      */     
/*  803 */     if (this.moonGlowColors != null)
/*  804 */     { buf.setIntLE(moonGlowColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  805 */       if (this.moonGlowColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("MoonGlowColors", this.moonGlowColors.size(), 4096000);  VarInt.write(buf, this.moonGlowColors.size()); for (Map.Entry<Float, ColorAlpha> e : this.moonGlowColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((ColorAlpha)e.getValue()).serialize(buf); }
/*      */        }
/*  807 */     else { buf.setIntLE(moonGlowColorsOffsetSlot, -1); }
/*      */     
/*  809 */     if (this.fogColors != null)
/*  810 */     { buf.setIntLE(fogColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  811 */       if (this.fogColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("FogColors", this.fogColors.size(), 4096000);  VarInt.write(buf, this.fogColors.size()); for (Map.Entry<Float, Color> e : this.fogColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((Color)e.getValue()).serialize(buf); }
/*      */        }
/*  813 */     else { buf.setIntLE(fogColorsOffsetSlot, -1); }
/*      */     
/*  815 */     if (this.fogHeightFalloffs != null)
/*  816 */     { buf.setIntLE(fogHeightFalloffsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  817 */       if (this.fogHeightFalloffs.size() > 4096000) throw ProtocolException.dictionaryTooLarge("FogHeightFalloffs", this.fogHeightFalloffs.size(), 4096000);  VarInt.write(buf, this.fogHeightFalloffs.size()); for (Map.Entry<Float, Float> e : this.fogHeightFalloffs.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*      */        }
/*  819 */     else { buf.setIntLE(fogHeightFalloffsOffsetSlot, -1); }
/*      */     
/*  821 */     if (this.fogDensities != null)
/*  822 */     { buf.setIntLE(fogDensitiesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  823 */       if (this.fogDensities.size() > 4096000) throw ProtocolException.dictionaryTooLarge("FogDensities", this.fogDensities.size(), 4096000);  VarInt.write(buf, this.fogDensities.size()); for (Map.Entry<Float, Float> e : this.fogDensities.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); buf.writeFloatLE(((Float)e.getValue()).floatValue()); }
/*      */        }
/*  825 */     else { buf.setIntLE(fogDensitiesOffsetSlot, -1); }
/*      */     
/*  827 */     if (this.screenEffect != null) {
/*  828 */       buf.setIntLE(screenEffectOffsetSlot, buf.writerIndex() - varBlockStart);
/*  829 */       PacketIO.writeVarString(buf, this.screenEffect, 4096000);
/*      */     } else {
/*  831 */       buf.setIntLE(screenEffectOffsetSlot, -1);
/*      */     } 
/*  833 */     if (this.screenEffectColors != null)
/*  834 */     { buf.setIntLE(screenEffectColorsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  835 */       if (this.screenEffectColors.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ScreenEffectColors", this.screenEffectColors.size(), 4096000);  VarInt.write(buf, this.screenEffectColors.size()); for (Map.Entry<Float, ColorAlpha> e : this.screenEffectColors.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((ColorAlpha)e.getValue()).serialize(buf); }
/*      */        }
/*  837 */     else { buf.setIntLE(screenEffectColorsOffsetSlot, -1); }
/*      */     
/*  839 */     if (this.colorFilters != null)
/*  840 */     { buf.setIntLE(colorFiltersOffsetSlot, buf.writerIndex() - varBlockStart);
/*  841 */       if (this.colorFilters.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ColorFilters", this.colorFilters.size(), 4096000);  VarInt.write(buf, this.colorFilters.size()); for (Map.Entry<Float, Color> e : this.colorFilters.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((Color)e.getValue()).serialize(buf); }
/*      */        }
/*  843 */     else { buf.setIntLE(colorFiltersOffsetSlot, -1); }
/*      */     
/*  845 */     if (this.waterTints != null)
/*  846 */     { buf.setIntLE(waterTintsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  847 */       if (this.waterTints.size() > 4096000) throw ProtocolException.dictionaryTooLarge("WaterTints", this.waterTints.size(), 4096000);  VarInt.write(buf, this.waterTints.size()); for (Map.Entry<Float, Color> e : this.waterTints.entrySet()) { buf.writeFloatLE(((Float)e.getKey()).floatValue()); ((Color)e.getValue()).serialize(buf); }
/*      */        }
/*  849 */     else { buf.setIntLE(waterTintsOffsetSlot, -1); }
/*      */     
/*  851 */     if (this.particle != null) {
/*  852 */       buf.setIntLE(particleOffsetSlot, buf.writerIndex() - varBlockStart);
/*  853 */       this.particle.serialize(buf);
/*      */     } else {
/*  855 */       buf.setIntLE(particleOffsetSlot, -1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeSize() {
/*  861 */     int size = 126;
/*  862 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/*  863 */     if (this.tagIndexes != null) size += VarInt.size(this.tagIndexes.length) + this.tagIndexes.length * 4; 
/*  864 */     if (this.stars != null) size += PacketIO.stringSize(this.stars); 
/*  865 */     if (this.moons != null) {
/*  866 */       int moonsSize = 0;
/*  867 */       for (Map.Entry<Integer, String> kvp : this.moons.entrySet()) moonsSize += 4 + PacketIO.stringSize(kvp.getValue()); 
/*  868 */       size += VarInt.size(this.moons.size()) + moonsSize;
/*      */     } 
/*  870 */     if (this.clouds != null) {
/*  871 */       int cloudsSize = 0;
/*  872 */       for (Cloud elem : this.clouds) cloudsSize += elem.computeSize(); 
/*  873 */       size += VarInt.size(this.clouds.length) + cloudsSize;
/*      */     } 
/*  875 */     if (this.sunlightDampingMultiplier != null) size += VarInt.size(this.sunlightDampingMultiplier.size()) + this.sunlightDampingMultiplier.size() * 8; 
/*  876 */     if (this.sunlightColors != null) size += VarInt.size(this.sunlightColors.size()) + this.sunlightColors.size() * 7; 
/*  877 */     if (this.skyTopColors != null) size += VarInt.size(this.skyTopColors.size()) + this.skyTopColors.size() * 8; 
/*  878 */     if (this.skyBottomColors != null) size += VarInt.size(this.skyBottomColors.size()) + this.skyBottomColors.size() * 8; 
/*  879 */     if (this.skySunsetColors != null) size += VarInt.size(this.skySunsetColors.size()) + this.skySunsetColors.size() * 8; 
/*  880 */     if (this.sunColors != null) size += VarInt.size(this.sunColors.size()) + this.sunColors.size() * 7; 
/*  881 */     if (this.sunScales != null) size += VarInt.size(this.sunScales.size()) + this.sunScales.size() * 8; 
/*  882 */     if (this.sunGlowColors != null) size += VarInt.size(this.sunGlowColors.size()) + this.sunGlowColors.size() * 8; 
/*  883 */     if (this.moonColors != null) size += VarInt.size(this.moonColors.size()) + this.moonColors.size() * 8; 
/*  884 */     if (this.moonScales != null) size += VarInt.size(this.moonScales.size()) + this.moonScales.size() * 8; 
/*  885 */     if (this.moonGlowColors != null) size += VarInt.size(this.moonGlowColors.size()) + this.moonGlowColors.size() * 8; 
/*  886 */     if (this.fogColors != null) size += VarInt.size(this.fogColors.size()) + this.fogColors.size() * 7; 
/*  887 */     if (this.fogHeightFalloffs != null) size += VarInt.size(this.fogHeightFalloffs.size()) + this.fogHeightFalloffs.size() * 8; 
/*  888 */     if (this.fogDensities != null) size += VarInt.size(this.fogDensities.size()) + this.fogDensities.size() * 8; 
/*  889 */     if (this.screenEffect != null) size += PacketIO.stringSize(this.screenEffect); 
/*  890 */     if (this.screenEffectColors != null) size += VarInt.size(this.screenEffectColors.size()) + this.screenEffectColors.size() * 8; 
/*  891 */     if (this.colorFilters != null) size += VarInt.size(this.colorFilters.size()) + this.colorFilters.size() * 7; 
/*  892 */     if (this.waterTints != null) size += VarInt.size(this.waterTints.size()) + this.waterTints.size() * 7; 
/*  893 */     if (this.particle != null) size += this.particle.computeSize();
/*      */     
/*  895 */     return size;
/*      */   }
/*      */   
/*      */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  899 */     if (buffer.readableBytes() - offset < 126) {
/*  900 */       return ValidationResult.error("Buffer too small: expected at least 126 bytes");
/*      */     }
/*      */     
/*  903 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 4);
/*      */     
/*  905 */     if ((nullBits[0] & 0x1) != 0) {
/*  906 */       int idOffset = buffer.getIntLE(offset + 30);
/*  907 */       if (idOffset < 0) {
/*  908 */         return ValidationResult.error("Invalid offset for Id");
/*      */       }
/*  910 */       int pos = offset + 126 + idOffset;
/*  911 */       if (pos >= buffer.writerIndex()) {
/*  912 */         return ValidationResult.error("Offset out of bounds for Id");
/*      */       }
/*  914 */       int idLen = VarInt.peek(buffer, pos);
/*  915 */       if (idLen < 0) {
/*  916 */         return ValidationResult.error("Invalid string length for Id");
/*      */       }
/*  918 */       if (idLen > 4096000) {
/*  919 */         return ValidationResult.error("Id exceeds max length 4096000");
/*      */       }
/*  921 */       pos += VarInt.length(buffer, pos);
/*  922 */       pos += idLen;
/*  923 */       if (pos > buffer.writerIndex()) {
/*  924 */         return ValidationResult.error("Buffer overflow reading Id");
/*      */       }
/*      */     } 
/*      */     
/*  928 */     if ((nullBits[0] & 0x2) != 0) {
/*  929 */       int tagIndexesOffset = buffer.getIntLE(offset + 34);
/*  930 */       if (tagIndexesOffset < 0) {
/*  931 */         return ValidationResult.error("Invalid offset for TagIndexes");
/*      */       }
/*  933 */       int pos = offset + 126 + tagIndexesOffset;
/*  934 */       if (pos >= buffer.writerIndex()) {
/*  935 */         return ValidationResult.error("Offset out of bounds for TagIndexes");
/*      */       }
/*  937 */       int tagIndexesCount = VarInt.peek(buffer, pos);
/*  938 */       if (tagIndexesCount < 0) {
/*  939 */         return ValidationResult.error("Invalid array count for TagIndexes");
/*      */       }
/*  941 */       if (tagIndexesCount > 4096000) {
/*  942 */         return ValidationResult.error("TagIndexes exceeds max length 4096000");
/*      */       }
/*  944 */       pos += VarInt.length(buffer, pos);
/*  945 */       pos += tagIndexesCount * 4;
/*  946 */       if (pos > buffer.writerIndex()) {
/*  947 */         return ValidationResult.error("Buffer overflow reading TagIndexes");
/*      */       }
/*      */     } 
/*      */     
/*  951 */     if ((nullBits[0] & 0x4) != 0) {
/*  952 */       int starsOffset = buffer.getIntLE(offset + 38);
/*  953 */       if (starsOffset < 0) {
/*  954 */         return ValidationResult.error("Invalid offset for Stars");
/*      */       }
/*  956 */       int pos = offset + 126 + starsOffset;
/*  957 */       if (pos >= buffer.writerIndex()) {
/*  958 */         return ValidationResult.error("Offset out of bounds for Stars");
/*      */       }
/*  960 */       int starsLen = VarInt.peek(buffer, pos);
/*  961 */       if (starsLen < 0) {
/*  962 */         return ValidationResult.error("Invalid string length for Stars");
/*      */       }
/*  964 */       if (starsLen > 4096000) {
/*  965 */         return ValidationResult.error("Stars exceeds max length 4096000");
/*      */       }
/*  967 */       pos += VarInt.length(buffer, pos);
/*  968 */       pos += starsLen;
/*  969 */       if (pos > buffer.writerIndex()) {
/*  970 */         return ValidationResult.error("Buffer overflow reading Stars");
/*      */       }
/*      */     } 
/*      */     
/*  974 */     if ((nullBits[0] & 0x8) != 0) {
/*  975 */       int moonsOffset = buffer.getIntLE(offset + 42);
/*  976 */       if (moonsOffset < 0) {
/*  977 */         return ValidationResult.error("Invalid offset for Moons");
/*      */       }
/*  979 */       int pos = offset + 126 + moonsOffset;
/*  980 */       if (pos >= buffer.writerIndex()) {
/*  981 */         return ValidationResult.error("Offset out of bounds for Moons");
/*      */       }
/*  983 */       int moonsCount = VarInt.peek(buffer, pos);
/*  984 */       if (moonsCount < 0) {
/*  985 */         return ValidationResult.error("Invalid dictionary count for Moons");
/*      */       }
/*  987 */       if (moonsCount > 4096000) {
/*  988 */         return ValidationResult.error("Moons exceeds max length 4096000");
/*      */       }
/*  990 */       pos += VarInt.length(buffer, pos);
/*  991 */       for (int i = 0; i < moonsCount; i++) {
/*  992 */         pos += 4;
/*  993 */         if (pos > buffer.writerIndex()) {
/*  994 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/*  996 */         int valueLen = VarInt.peek(buffer, pos);
/*  997 */         if (valueLen < 0) {
/*  998 */           return ValidationResult.error("Invalid string length for value");
/*      */         }
/* 1000 */         if (valueLen > 4096000) {
/* 1001 */           return ValidationResult.error("value exceeds max length 4096000");
/*      */         }
/* 1003 */         pos += VarInt.length(buffer, pos);
/* 1004 */         pos += valueLen;
/* 1005 */         if (pos > buffer.writerIndex()) {
/* 1006 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1011 */     if ((nullBits[0] & 0x10) != 0) {
/* 1012 */       int cloudsOffset = buffer.getIntLE(offset + 46);
/* 1013 */       if (cloudsOffset < 0) {
/* 1014 */         return ValidationResult.error("Invalid offset for Clouds");
/*      */       }
/* 1016 */       int pos = offset + 126 + cloudsOffset;
/* 1017 */       if (pos >= buffer.writerIndex()) {
/* 1018 */         return ValidationResult.error("Offset out of bounds for Clouds");
/*      */       }
/* 1020 */       int cloudsCount = VarInt.peek(buffer, pos);
/* 1021 */       if (cloudsCount < 0) {
/* 1022 */         return ValidationResult.error("Invalid array count for Clouds");
/*      */       }
/* 1024 */       if (cloudsCount > 4096000) {
/* 1025 */         return ValidationResult.error("Clouds exceeds max length 4096000");
/*      */       }
/* 1027 */       pos += VarInt.length(buffer, pos);
/* 1028 */       for (int i = 0; i < cloudsCount; i++) {
/* 1029 */         ValidationResult structResult = Cloud.validateStructure(buffer, pos);
/* 1030 */         if (!structResult.isValid()) {
/* 1031 */           return ValidationResult.error("Invalid Cloud in Clouds[" + i + "]: " + structResult.error());
/*      */         }
/* 1033 */         pos += Cloud.computeBytesConsumed(buffer, pos);
/*      */       } 
/*      */     } 
/*      */     
/* 1037 */     if ((nullBits[0] & 0x20) != 0) {
/* 1038 */       int sunlightDampingMultiplierOffset = buffer.getIntLE(offset + 50);
/* 1039 */       if (sunlightDampingMultiplierOffset < 0) {
/* 1040 */         return ValidationResult.error("Invalid offset for SunlightDampingMultiplier");
/*      */       }
/* 1042 */       int pos = offset + 126 + sunlightDampingMultiplierOffset;
/* 1043 */       if (pos >= buffer.writerIndex()) {
/* 1044 */         return ValidationResult.error("Offset out of bounds for SunlightDampingMultiplier");
/*      */       }
/* 1046 */       int sunlightDampingMultiplierCount = VarInt.peek(buffer, pos);
/* 1047 */       if (sunlightDampingMultiplierCount < 0) {
/* 1048 */         return ValidationResult.error("Invalid dictionary count for SunlightDampingMultiplier");
/*      */       }
/* 1050 */       if (sunlightDampingMultiplierCount > 4096000) {
/* 1051 */         return ValidationResult.error("SunlightDampingMultiplier exceeds max length 4096000");
/*      */       }
/* 1053 */       pos += VarInt.length(buffer, pos);
/* 1054 */       for (int i = 0; i < sunlightDampingMultiplierCount; i++) {
/* 1055 */         pos += 4;
/* 1056 */         if (pos > buffer.writerIndex()) {
/* 1057 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1059 */         pos += 4;
/* 1060 */         if (pos > buffer.writerIndex()) {
/* 1061 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1066 */     if ((nullBits[0] & 0x40) != 0) {
/* 1067 */       int sunlightColorsOffset = buffer.getIntLE(offset + 54);
/* 1068 */       if (sunlightColorsOffset < 0) {
/* 1069 */         return ValidationResult.error("Invalid offset for SunlightColors");
/*      */       }
/* 1071 */       int pos = offset + 126 + sunlightColorsOffset;
/* 1072 */       if (pos >= buffer.writerIndex()) {
/* 1073 */         return ValidationResult.error("Offset out of bounds for SunlightColors");
/*      */       }
/* 1075 */       int sunlightColorsCount = VarInt.peek(buffer, pos);
/* 1076 */       if (sunlightColorsCount < 0) {
/* 1077 */         return ValidationResult.error("Invalid dictionary count for SunlightColors");
/*      */       }
/* 1079 */       if (sunlightColorsCount > 4096000) {
/* 1080 */         return ValidationResult.error("SunlightColors exceeds max length 4096000");
/*      */       }
/* 1082 */       pos += VarInt.length(buffer, pos);
/* 1083 */       for (int i = 0; i < sunlightColorsCount; i++) {
/* 1084 */         pos += 4;
/* 1085 */         if (pos > buffer.writerIndex()) {
/* 1086 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1088 */         pos += 3;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1093 */     if ((nullBits[0] & 0x80) != 0) {
/* 1094 */       int skyTopColorsOffset = buffer.getIntLE(offset + 58);
/* 1095 */       if (skyTopColorsOffset < 0) {
/* 1096 */         return ValidationResult.error("Invalid offset for SkyTopColors");
/*      */       }
/* 1098 */       int pos = offset + 126 + skyTopColorsOffset;
/* 1099 */       if (pos >= buffer.writerIndex()) {
/* 1100 */         return ValidationResult.error("Offset out of bounds for SkyTopColors");
/*      */       }
/* 1102 */       int skyTopColorsCount = VarInt.peek(buffer, pos);
/* 1103 */       if (skyTopColorsCount < 0) {
/* 1104 */         return ValidationResult.error("Invalid dictionary count for SkyTopColors");
/*      */       }
/* 1106 */       if (skyTopColorsCount > 4096000) {
/* 1107 */         return ValidationResult.error("SkyTopColors exceeds max length 4096000");
/*      */       }
/* 1109 */       pos += VarInt.length(buffer, pos);
/* 1110 */       for (int i = 0; i < skyTopColorsCount; i++) {
/* 1111 */         pos += 4;
/* 1112 */         if (pos > buffer.writerIndex()) {
/* 1113 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1115 */         pos += 4;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1120 */     if ((nullBits[1] & 0x1) != 0) {
/* 1121 */       int skyBottomColorsOffset = buffer.getIntLE(offset + 62);
/* 1122 */       if (skyBottomColorsOffset < 0) {
/* 1123 */         return ValidationResult.error("Invalid offset for SkyBottomColors");
/*      */       }
/* 1125 */       int pos = offset + 126 + skyBottomColorsOffset;
/* 1126 */       if (pos >= buffer.writerIndex()) {
/* 1127 */         return ValidationResult.error("Offset out of bounds for SkyBottomColors");
/*      */       }
/* 1129 */       int skyBottomColorsCount = VarInt.peek(buffer, pos);
/* 1130 */       if (skyBottomColorsCount < 0) {
/* 1131 */         return ValidationResult.error("Invalid dictionary count for SkyBottomColors");
/*      */       }
/* 1133 */       if (skyBottomColorsCount > 4096000) {
/* 1134 */         return ValidationResult.error("SkyBottomColors exceeds max length 4096000");
/*      */       }
/* 1136 */       pos += VarInt.length(buffer, pos);
/* 1137 */       for (int i = 0; i < skyBottomColorsCount; i++) {
/* 1138 */         pos += 4;
/* 1139 */         if (pos > buffer.writerIndex()) {
/* 1140 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1142 */         pos += 4;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1147 */     if ((nullBits[1] & 0x2) != 0) {
/* 1148 */       int skySunsetColorsOffset = buffer.getIntLE(offset + 66);
/* 1149 */       if (skySunsetColorsOffset < 0) {
/* 1150 */         return ValidationResult.error("Invalid offset for SkySunsetColors");
/*      */       }
/* 1152 */       int pos = offset + 126 + skySunsetColorsOffset;
/* 1153 */       if (pos >= buffer.writerIndex()) {
/* 1154 */         return ValidationResult.error("Offset out of bounds for SkySunsetColors");
/*      */       }
/* 1156 */       int skySunsetColorsCount = VarInt.peek(buffer, pos);
/* 1157 */       if (skySunsetColorsCount < 0) {
/* 1158 */         return ValidationResult.error("Invalid dictionary count for SkySunsetColors");
/*      */       }
/* 1160 */       if (skySunsetColorsCount > 4096000) {
/* 1161 */         return ValidationResult.error("SkySunsetColors exceeds max length 4096000");
/*      */       }
/* 1163 */       pos += VarInt.length(buffer, pos);
/* 1164 */       for (int i = 0; i < skySunsetColorsCount; i++) {
/* 1165 */         pos += 4;
/* 1166 */         if (pos > buffer.writerIndex()) {
/* 1167 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1169 */         pos += 4;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1174 */     if ((nullBits[1] & 0x4) != 0) {
/* 1175 */       int sunColorsOffset = buffer.getIntLE(offset + 70);
/* 1176 */       if (sunColorsOffset < 0) {
/* 1177 */         return ValidationResult.error("Invalid offset for SunColors");
/*      */       }
/* 1179 */       int pos = offset + 126 + sunColorsOffset;
/* 1180 */       if (pos >= buffer.writerIndex()) {
/* 1181 */         return ValidationResult.error("Offset out of bounds for SunColors");
/*      */       }
/* 1183 */       int sunColorsCount = VarInt.peek(buffer, pos);
/* 1184 */       if (sunColorsCount < 0) {
/* 1185 */         return ValidationResult.error("Invalid dictionary count for SunColors");
/*      */       }
/* 1187 */       if (sunColorsCount > 4096000) {
/* 1188 */         return ValidationResult.error("SunColors exceeds max length 4096000");
/*      */       }
/* 1190 */       pos += VarInt.length(buffer, pos);
/* 1191 */       for (int i = 0; i < sunColorsCount; i++) {
/* 1192 */         pos += 4;
/* 1193 */         if (pos > buffer.writerIndex()) {
/* 1194 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1196 */         pos += 3;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1201 */     if ((nullBits[1] & 0x8) != 0) {
/* 1202 */       int sunScalesOffset = buffer.getIntLE(offset + 74);
/* 1203 */       if (sunScalesOffset < 0) {
/* 1204 */         return ValidationResult.error("Invalid offset for SunScales");
/*      */       }
/* 1206 */       int pos = offset + 126 + sunScalesOffset;
/* 1207 */       if (pos >= buffer.writerIndex()) {
/* 1208 */         return ValidationResult.error("Offset out of bounds for SunScales");
/*      */       }
/* 1210 */       int sunScalesCount = VarInt.peek(buffer, pos);
/* 1211 */       if (sunScalesCount < 0) {
/* 1212 */         return ValidationResult.error("Invalid dictionary count for SunScales");
/*      */       }
/* 1214 */       if (sunScalesCount > 4096000) {
/* 1215 */         return ValidationResult.error("SunScales exceeds max length 4096000");
/*      */       }
/* 1217 */       pos += VarInt.length(buffer, pos);
/* 1218 */       for (int i = 0; i < sunScalesCount; i++) {
/* 1219 */         pos += 4;
/* 1220 */         if (pos > buffer.writerIndex()) {
/* 1221 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1223 */         pos += 4;
/* 1224 */         if (pos > buffer.writerIndex()) {
/* 1225 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1230 */     if ((nullBits[1] & 0x10) != 0) {
/* 1231 */       int sunGlowColorsOffset = buffer.getIntLE(offset + 78);
/* 1232 */       if (sunGlowColorsOffset < 0) {
/* 1233 */         return ValidationResult.error("Invalid offset for SunGlowColors");
/*      */       }
/* 1235 */       int pos = offset + 126 + sunGlowColorsOffset;
/* 1236 */       if (pos >= buffer.writerIndex()) {
/* 1237 */         return ValidationResult.error("Offset out of bounds for SunGlowColors");
/*      */       }
/* 1239 */       int sunGlowColorsCount = VarInt.peek(buffer, pos);
/* 1240 */       if (sunGlowColorsCount < 0) {
/* 1241 */         return ValidationResult.error("Invalid dictionary count for SunGlowColors");
/*      */       }
/* 1243 */       if (sunGlowColorsCount > 4096000) {
/* 1244 */         return ValidationResult.error("SunGlowColors exceeds max length 4096000");
/*      */       }
/* 1246 */       pos += VarInt.length(buffer, pos);
/* 1247 */       for (int i = 0; i < sunGlowColorsCount; i++) {
/* 1248 */         pos += 4;
/* 1249 */         if (pos > buffer.writerIndex()) {
/* 1250 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1252 */         pos += 4;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1257 */     if ((nullBits[1] & 0x20) != 0) {
/* 1258 */       int moonColorsOffset = buffer.getIntLE(offset + 82);
/* 1259 */       if (moonColorsOffset < 0) {
/* 1260 */         return ValidationResult.error("Invalid offset for MoonColors");
/*      */       }
/* 1262 */       int pos = offset + 126 + moonColorsOffset;
/* 1263 */       if (pos >= buffer.writerIndex()) {
/* 1264 */         return ValidationResult.error("Offset out of bounds for MoonColors");
/*      */       }
/* 1266 */       int moonColorsCount = VarInt.peek(buffer, pos);
/* 1267 */       if (moonColorsCount < 0) {
/* 1268 */         return ValidationResult.error("Invalid dictionary count for MoonColors");
/*      */       }
/* 1270 */       if (moonColorsCount > 4096000) {
/* 1271 */         return ValidationResult.error("MoonColors exceeds max length 4096000");
/*      */       }
/* 1273 */       pos += VarInt.length(buffer, pos);
/* 1274 */       for (int i = 0; i < moonColorsCount; i++) {
/* 1275 */         pos += 4;
/* 1276 */         if (pos > buffer.writerIndex()) {
/* 1277 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1279 */         pos += 4;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1284 */     if ((nullBits[1] & 0x40) != 0) {
/* 1285 */       int moonScalesOffset = buffer.getIntLE(offset + 86);
/* 1286 */       if (moonScalesOffset < 0) {
/* 1287 */         return ValidationResult.error("Invalid offset for MoonScales");
/*      */       }
/* 1289 */       int pos = offset + 126 + moonScalesOffset;
/* 1290 */       if (pos >= buffer.writerIndex()) {
/* 1291 */         return ValidationResult.error("Offset out of bounds for MoonScales");
/*      */       }
/* 1293 */       int moonScalesCount = VarInt.peek(buffer, pos);
/* 1294 */       if (moonScalesCount < 0) {
/* 1295 */         return ValidationResult.error("Invalid dictionary count for MoonScales");
/*      */       }
/* 1297 */       if (moonScalesCount > 4096000) {
/* 1298 */         return ValidationResult.error("MoonScales exceeds max length 4096000");
/*      */       }
/* 1300 */       pos += VarInt.length(buffer, pos);
/* 1301 */       for (int i = 0; i < moonScalesCount; i++) {
/* 1302 */         pos += 4;
/* 1303 */         if (pos > buffer.writerIndex()) {
/* 1304 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1306 */         pos += 4;
/* 1307 */         if (pos > buffer.writerIndex()) {
/* 1308 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1313 */     if ((nullBits[1] & 0x80) != 0) {
/* 1314 */       int moonGlowColorsOffset = buffer.getIntLE(offset + 90);
/* 1315 */       if (moonGlowColorsOffset < 0) {
/* 1316 */         return ValidationResult.error("Invalid offset for MoonGlowColors");
/*      */       }
/* 1318 */       int pos = offset + 126 + moonGlowColorsOffset;
/* 1319 */       if (pos >= buffer.writerIndex()) {
/* 1320 */         return ValidationResult.error("Offset out of bounds for MoonGlowColors");
/*      */       }
/* 1322 */       int moonGlowColorsCount = VarInt.peek(buffer, pos);
/* 1323 */       if (moonGlowColorsCount < 0) {
/* 1324 */         return ValidationResult.error("Invalid dictionary count for MoonGlowColors");
/*      */       }
/* 1326 */       if (moonGlowColorsCount > 4096000) {
/* 1327 */         return ValidationResult.error("MoonGlowColors exceeds max length 4096000");
/*      */       }
/* 1329 */       pos += VarInt.length(buffer, pos);
/* 1330 */       for (int i = 0; i < moonGlowColorsCount; i++) {
/* 1331 */         pos += 4;
/* 1332 */         if (pos > buffer.writerIndex()) {
/* 1333 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1335 */         pos += 4;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1340 */     if ((nullBits[2] & 0x1) != 0) {
/* 1341 */       int fogColorsOffset = buffer.getIntLE(offset + 94);
/* 1342 */       if (fogColorsOffset < 0) {
/* 1343 */         return ValidationResult.error("Invalid offset for FogColors");
/*      */       }
/* 1345 */       int pos = offset + 126 + fogColorsOffset;
/* 1346 */       if (pos >= buffer.writerIndex()) {
/* 1347 */         return ValidationResult.error("Offset out of bounds for FogColors");
/*      */       }
/* 1349 */       int fogColorsCount = VarInt.peek(buffer, pos);
/* 1350 */       if (fogColorsCount < 0) {
/* 1351 */         return ValidationResult.error("Invalid dictionary count for FogColors");
/*      */       }
/* 1353 */       if (fogColorsCount > 4096000) {
/* 1354 */         return ValidationResult.error("FogColors exceeds max length 4096000");
/*      */       }
/* 1356 */       pos += VarInt.length(buffer, pos);
/* 1357 */       for (int i = 0; i < fogColorsCount; i++) {
/* 1358 */         pos += 4;
/* 1359 */         if (pos > buffer.writerIndex()) {
/* 1360 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1362 */         pos += 3;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1367 */     if ((nullBits[2] & 0x2) != 0) {
/* 1368 */       int fogHeightFalloffsOffset = buffer.getIntLE(offset + 98);
/* 1369 */       if (fogHeightFalloffsOffset < 0) {
/* 1370 */         return ValidationResult.error("Invalid offset for FogHeightFalloffs");
/*      */       }
/* 1372 */       int pos = offset + 126 + fogHeightFalloffsOffset;
/* 1373 */       if (pos >= buffer.writerIndex()) {
/* 1374 */         return ValidationResult.error("Offset out of bounds for FogHeightFalloffs");
/*      */       }
/* 1376 */       int fogHeightFalloffsCount = VarInt.peek(buffer, pos);
/* 1377 */       if (fogHeightFalloffsCount < 0) {
/* 1378 */         return ValidationResult.error("Invalid dictionary count for FogHeightFalloffs");
/*      */       }
/* 1380 */       if (fogHeightFalloffsCount > 4096000) {
/* 1381 */         return ValidationResult.error("FogHeightFalloffs exceeds max length 4096000");
/*      */       }
/* 1383 */       pos += VarInt.length(buffer, pos);
/* 1384 */       for (int i = 0; i < fogHeightFalloffsCount; i++) {
/* 1385 */         pos += 4;
/* 1386 */         if (pos > buffer.writerIndex()) {
/* 1387 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1389 */         pos += 4;
/* 1390 */         if (pos > buffer.writerIndex()) {
/* 1391 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1396 */     if ((nullBits[2] & 0x4) != 0) {
/* 1397 */       int fogDensitiesOffset = buffer.getIntLE(offset + 102);
/* 1398 */       if (fogDensitiesOffset < 0) {
/* 1399 */         return ValidationResult.error("Invalid offset for FogDensities");
/*      */       }
/* 1401 */       int pos = offset + 126 + fogDensitiesOffset;
/* 1402 */       if (pos >= buffer.writerIndex()) {
/* 1403 */         return ValidationResult.error("Offset out of bounds for FogDensities");
/*      */       }
/* 1405 */       int fogDensitiesCount = VarInt.peek(buffer, pos);
/* 1406 */       if (fogDensitiesCount < 0) {
/* 1407 */         return ValidationResult.error("Invalid dictionary count for FogDensities");
/*      */       }
/* 1409 */       if (fogDensitiesCount > 4096000) {
/* 1410 */         return ValidationResult.error("FogDensities exceeds max length 4096000");
/*      */       }
/* 1412 */       pos += VarInt.length(buffer, pos);
/* 1413 */       for (int i = 0; i < fogDensitiesCount; i++) {
/* 1414 */         pos += 4;
/* 1415 */         if (pos > buffer.writerIndex()) {
/* 1416 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1418 */         pos += 4;
/* 1419 */         if (pos > buffer.writerIndex()) {
/* 1420 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1425 */     if ((nullBits[2] & 0x8) != 0) {
/* 1426 */       int screenEffectOffset = buffer.getIntLE(offset + 106);
/* 1427 */       if (screenEffectOffset < 0) {
/* 1428 */         return ValidationResult.error("Invalid offset for ScreenEffect");
/*      */       }
/* 1430 */       int pos = offset + 126 + screenEffectOffset;
/* 1431 */       if (pos >= buffer.writerIndex()) {
/* 1432 */         return ValidationResult.error("Offset out of bounds for ScreenEffect");
/*      */       }
/* 1434 */       int screenEffectLen = VarInt.peek(buffer, pos);
/* 1435 */       if (screenEffectLen < 0) {
/* 1436 */         return ValidationResult.error("Invalid string length for ScreenEffect");
/*      */       }
/* 1438 */       if (screenEffectLen > 4096000) {
/* 1439 */         return ValidationResult.error("ScreenEffect exceeds max length 4096000");
/*      */       }
/* 1441 */       pos += VarInt.length(buffer, pos);
/* 1442 */       pos += screenEffectLen;
/* 1443 */       if (pos > buffer.writerIndex()) {
/* 1444 */         return ValidationResult.error("Buffer overflow reading ScreenEffect");
/*      */       }
/*      */     } 
/*      */     
/* 1448 */     if ((nullBits[2] & 0x10) != 0) {
/* 1449 */       int screenEffectColorsOffset = buffer.getIntLE(offset + 110);
/* 1450 */       if (screenEffectColorsOffset < 0) {
/* 1451 */         return ValidationResult.error("Invalid offset for ScreenEffectColors");
/*      */       }
/* 1453 */       int pos = offset + 126 + screenEffectColorsOffset;
/* 1454 */       if (pos >= buffer.writerIndex()) {
/* 1455 */         return ValidationResult.error("Offset out of bounds for ScreenEffectColors");
/*      */       }
/* 1457 */       int screenEffectColorsCount = VarInt.peek(buffer, pos);
/* 1458 */       if (screenEffectColorsCount < 0) {
/* 1459 */         return ValidationResult.error("Invalid dictionary count for ScreenEffectColors");
/*      */       }
/* 1461 */       if (screenEffectColorsCount > 4096000) {
/* 1462 */         return ValidationResult.error("ScreenEffectColors exceeds max length 4096000");
/*      */       }
/* 1464 */       pos += VarInt.length(buffer, pos);
/* 1465 */       for (int i = 0; i < screenEffectColorsCount; i++) {
/* 1466 */         pos += 4;
/* 1467 */         if (pos > buffer.writerIndex()) {
/* 1468 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1470 */         pos += 4;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1475 */     if ((nullBits[2] & 0x20) != 0) {
/* 1476 */       int colorFiltersOffset = buffer.getIntLE(offset + 114);
/* 1477 */       if (colorFiltersOffset < 0) {
/* 1478 */         return ValidationResult.error("Invalid offset for ColorFilters");
/*      */       }
/* 1480 */       int pos = offset + 126 + colorFiltersOffset;
/* 1481 */       if (pos >= buffer.writerIndex()) {
/* 1482 */         return ValidationResult.error("Offset out of bounds for ColorFilters");
/*      */       }
/* 1484 */       int colorFiltersCount = VarInt.peek(buffer, pos);
/* 1485 */       if (colorFiltersCount < 0) {
/* 1486 */         return ValidationResult.error("Invalid dictionary count for ColorFilters");
/*      */       }
/* 1488 */       if (colorFiltersCount > 4096000) {
/* 1489 */         return ValidationResult.error("ColorFilters exceeds max length 4096000");
/*      */       }
/* 1491 */       pos += VarInt.length(buffer, pos);
/* 1492 */       for (int i = 0; i < colorFiltersCount; i++) {
/* 1493 */         pos += 4;
/* 1494 */         if (pos > buffer.writerIndex()) {
/* 1495 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1497 */         pos += 3;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1502 */     if ((nullBits[2] & 0x40) != 0) {
/* 1503 */       int waterTintsOffset = buffer.getIntLE(offset + 118);
/* 1504 */       if (waterTintsOffset < 0) {
/* 1505 */         return ValidationResult.error("Invalid offset for WaterTints");
/*      */       }
/* 1507 */       int pos = offset + 126 + waterTintsOffset;
/* 1508 */       if (pos >= buffer.writerIndex()) {
/* 1509 */         return ValidationResult.error("Offset out of bounds for WaterTints");
/*      */       }
/* 1511 */       int waterTintsCount = VarInt.peek(buffer, pos);
/* 1512 */       if (waterTintsCount < 0) {
/* 1513 */         return ValidationResult.error("Invalid dictionary count for WaterTints");
/*      */       }
/* 1515 */       if (waterTintsCount > 4096000) {
/* 1516 */         return ValidationResult.error("WaterTints exceeds max length 4096000");
/*      */       }
/* 1518 */       pos += VarInt.length(buffer, pos);
/* 1519 */       for (int i = 0; i < waterTintsCount; i++) {
/* 1520 */         pos += 4;
/* 1521 */         if (pos > buffer.writerIndex()) {
/* 1522 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1524 */         pos += 3;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1529 */     if ((nullBits[2] & 0x80) != 0) {
/* 1530 */       int particleOffset = buffer.getIntLE(offset + 122);
/* 1531 */       if (particleOffset < 0) {
/* 1532 */         return ValidationResult.error("Invalid offset for Particle");
/*      */       }
/* 1534 */       int pos = offset + 126 + particleOffset;
/* 1535 */       if (pos >= buffer.writerIndex()) {
/* 1536 */         return ValidationResult.error("Offset out of bounds for Particle");
/*      */       }
/* 1538 */       ValidationResult particleResult = WeatherParticle.validateStructure(buffer, pos);
/* 1539 */       if (!particleResult.isValid()) {
/* 1540 */         return ValidationResult.error("Invalid Particle: " + particleResult.error());
/*      */       }
/* 1542 */       pos += WeatherParticle.computeBytesConsumed(buffer, pos);
/*      */     } 
/* 1544 */     return ValidationResult.OK;
/*      */   }
/*      */   
/*      */   public Weather clone() {
/* 1548 */     Weather copy = new Weather();
/* 1549 */     copy.id = this.id;
/* 1550 */     copy.tagIndexes = (this.tagIndexes != null) ? Arrays.copyOf(this.tagIndexes, this.tagIndexes.length) : null;
/* 1551 */     copy.stars = this.stars;
/* 1552 */     copy.moons = (this.moons != null) ? new HashMap<>(this.moons) : null;
/* 1553 */     copy.clouds = (this.clouds != null) ? (Cloud[])Arrays.<Cloud>stream(this.clouds).map(e -> e.clone()).toArray(x$0 -> new Cloud[x$0]) : null;
/* 1554 */     copy.sunlightDampingMultiplier = (this.sunlightDampingMultiplier != null) ? new HashMap<>(this.sunlightDampingMultiplier) : null;
/* 1555 */     if (this.sunlightColors != null) {
/* 1556 */       Map<Float, Color> m = new HashMap<>();
/* 1557 */       for (Map.Entry<Float, Color> e : this.sunlightColors.entrySet()) m.put(e.getKey(), ((Color)e.getValue()).clone()); 
/* 1558 */       copy.sunlightColors = m;
/*      */     } 
/* 1560 */     if (this.skyTopColors != null) {
/* 1561 */       Map<Float, ColorAlpha> m = new HashMap<>();
/* 1562 */       for (Map.Entry<Float, ColorAlpha> e : this.skyTopColors.entrySet()) m.put(e.getKey(), ((ColorAlpha)e.getValue()).clone()); 
/* 1563 */       copy.skyTopColors = m;
/*      */     } 
/* 1565 */     if (this.skyBottomColors != null) {
/* 1566 */       Map<Float, ColorAlpha> m = new HashMap<>();
/* 1567 */       for (Map.Entry<Float, ColorAlpha> e : this.skyBottomColors.entrySet()) m.put(e.getKey(), ((ColorAlpha)e.getValue()).clone()); 
/* 1568 */       copy.skyBottomColors = m;
/*      */     } 
/* 1570 */     if (this.skySunsetColors != null) {
/* 1571 */       Map<Float, ColorAlpha> m = new HashMap<>();
/* 1572 */       for (Map.Entry<Float, ColorAlpha> e : this.skySunsetColors.entrySet()) m.put(e.getKey(), ((ColorAlpha)e.getValue()).clone()); 
/* 1573 */       copy.skySunsetColors = m;
/*      */     } 
/* 1575 */     if (this.sunColors != null) {
/* 1576 */       Map<Float, Color> m = new HashMap<>();
/* 1577 */       for (Map.Entry<Float, Color> e : this.sunColors.entrySet()) m.put(e.getKey(), ((Color)e.getValue()).clone()); 
/* 1578 */       copy.sunColors = m;
/*      */     } 
/* 1580 */     copy.sunScales = (this.sunScales != null) ? new HashMap<>(this.sunScales) : null;
/* 1581 */     if (this.sunGlowColors != null) {
/* 1582 */       Map<Float, ColorAlpha> m = new HashMap<>();
/* 1583 */       for (Map.Entry<Float, ColorAlpha> e : this.sunGlowColors.entrySet()) m.put(e.getKey(), ((ColorAlpha)e.getValue()).clone()); 
/* 1584 */       copy.sunGlowColors = m;
/*      */     } 
/* 1586 */     if (this.moonColors != null) {
/* 1587 */       Map<Float, ColorAlpha> m = new HashMap<>();
/* 1588 */       for (Map.Entry<Float, ColorAlpha> e : this.moonColors.entrySet()) m.put(e.getKey(), ((ColorAlpha)e.getValue()).clone()); 
/* 1589 */       copy.moonColors = m;
/*      */     } 
/* 1591 */     copy.moonScales = (this.moonScales != null) ? new HashMap<>(this.moonScales) : null;
/* 1592 */     if (this.moonGlowColors != null) {
/* 1593 */       Map<Float, ColorAlpha> m = new HashMap<>();
/* 1594 */       for (Map.Entry<Float, ColorAlpha> e : this.moonGlowColors.entrySet()) m.put(e.getKey(), ((ColorAlpha)e.getValue()).clone()); 
/* 1595 */       copy.moonGlowColors = m;
/*      */     } 
/* 1597 */     if (this.fogColors != null) {
/* 1598 */       Map<Float, Color> m = new HashMap<>();
/* 1599 */       for (Map.Entry<Float, Color> e : this.fogColors.entrySet()) m.put(e.getKey(), ((Color)e.getValue()).clone()); 
/* 1600 */       copy.fogColors = m;
/*      */     } 
/* 1602 */     copy.fogHeightFalloffs = (this.fogHeightFalloffs != null) ? new HashMap<>(this.fogHeightFalloffs) : null;
/* 1603 */     copy.fogDensities = (this.fogDensities != null) ? new HashMap<>(this.fogDensities) : null;
/* 1604 */     copy.screenEffect = this.screenEffect;
/* 1605 */     if (this.screenEffectColors != null) {
/* 1606 */       Map<Float, ColorAlpha> m = new HashMap<>();
/* 1607 */       for (Map.Entry<Float, ColorAlpha> e : this.screenEffectColors.entrySet()) m.put(e.getKey(), ((ColorAlpha)e.getValue()).clone()); 
/* 1608 */       copy.screenEffectColors = m;
/*      */     } 
/* 1610 */     if (this.colorFilters != null) {
/* 1611 */       Map<Float, Color> m = new HashMap<>();
/* 1612 */       for (Map.Entry<Float, Color> e : this.colorFilters.entrySet()) m.put(e.getKey(), ((Color)e.getValue()).clone()); 
/* 1613 */       copy.colorFilters = m;
/*      */     } 
/* 1615 */     if (this.waterTints != null) {
/* 1616 */       Map<Float, Color> m = new HashMap<>();
/* 1617 */       for (Map.Entry<Float, Color> e : this.waterTints.entrySet()) m.put(e.getKey(), ((Color)e.getValue()).clone()); 
/* 1618 */       copy.waterTints = m;
/*      */     } 
/* 1620 */     copy.particle = (this.particle != null) ? this.particle.clone() : null;
/* 1621 */     copy.fog = (this.fog != null) ? this.fog.clone() : null;
/* 1622 */     copy.fogOptions = (this.fogOptions != null) ? this.fogOptions.clone() : null;
/* 1623 */     return copy;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*      */     Weather other;
/* 1629 */     if (this == obj) return true; 
/* 1630 */     if (obj instanceof Weather) { other = (Weather)obj; } else { return false; }
/* 1631 */      return (Objects.equals(this.id, other.id) && Arrays.equals(this.tagIndexes, other.tagIndexes) && Objects.equals(this.stars, other.stars) && Objects.equals(this.moons, other.moons) && Arrays.equals((Object[])this.clouds, (Object[])other.clouds) && Objects.equals(this.sunlightDampingMultiplier, other.sunlightDampingMultiplier) && Objects.equals(this.sunlightColors, other.sunlightColors) && Objects.equals(this.skyTopColors, other.skyTopColors) && Objects.equals(this.skyBottomColors, other.skyBottomColors) && Objects.equals(this.skySunsetColors, other.skySunsetColors) && Objects.equals(this.sunColors, other.sunColors) && Objects.equals(this.sunScales, other.sunScales) && Objects.equals(this.sunGlowColors, other.sunGlowColors) && Objects.equals(this.moonColors, other.moonColors) && Objects.equals(this.moonScales, other.moonScales) && Objects.equals(this.moonGlowColors, other.moonGlowColors) && Objects.equals(this.fogColors, other.fogColors) && Objects.equals(this.fogHeightFalloffs, other.fogHeightFalloffs) && Objects.equals(this.fogDensities, other.fogDensities) && Objects.equals(this.screenEffect, other.screenEffect) && Objects.equals(this.screenEffectColors, other.screenEffectColors) && Objects.equals(this.colorFilters, other.colorFilters) && Objects.equals(this.waterTints, other.waterTints) && Objects.equals(this.particle, other.particle) && Objects.equals(this.fog, other.fog) && Objects.equals(this.fogOptions, other.fogOptions));
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1636 */     int result = 1;
/* 1637 */     result = 31 * result + Objects.hashCode(this.id);
/* 1638 */     result = 31 * result + Arrays.hashCode(this.tagIndexes);
/* 1639 */     result = 31 * result + Objects.hashCode(this.stars);
/* 1640 */     result = 31 * result + Objects.hashCode(this.moons);
/* 1641 */     result = 31 * result + Arrays.hashCode((Object[])this.clouds);
/* 1642 */     result = 31 * result + Objects.hashCode(this.sunlightDampingMultiplier);
/* 1643 */     result = 31 * result + Objects.hashCode(this.sunlightColors);
/* 1644 */     result = 31 * result + Objects.hashCode(this.skyTopColors);
/* 1645 */     result = 31 * result + Objects.hashCode(this.skyBottomColors);
/* 1646 */     result = 31 * result + Objects.hashCode(this.skySunsetColors);
/* 1647 */     result = 31 * result + Objects.hashCode(this.sunColors);
/* 1648 */     result = 31 * result + Objects.hashCode(this.sunScales);
/* 1649 */     result = 31 * result + Objects.hashCode(this.sunGlowColors);
/* 1650 */     result = 31 * result + Objects.hashCode(this.moonColors);
/* 1651 */     result = 31 * result + Objects.hashCode(this.moonScales);
/* 1652 */     result = 31 * result + Objects.hashCode(this.moonGlowColors);
/* 1653 */     result = 31 * result + Objects.hashCode(this.fogColors);
/* 1654 */     result = 31 * result + Objects.hashCode(this.fogHeightFalloffs);
/* 1655 */     result = 31 * result + Objects.hashCode(this.fogDensities);
/* 1656 */     result = 31 * result + Objects.hashCode(this.screenEffect);
/* 1657 */     result = 31 * result + Objects.hashCode(this.screenEffectColors);
/* 1658 */     result = 31 * result + Objects.hashCode(this.colorFilters);
/* 1659 */     result = 31 * result + Objects.hashCode(this.waterTints);
/* 1660 */     result = 31 * result + Objects.hashCode(this.particle);
/* 1661 */     result = 31 * result + Objects.hashCode(this.fog);
/* 1662 */     result = 31 * result + Objects.hashCode(this.fogOptions);
/* 1663 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\Weather.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */