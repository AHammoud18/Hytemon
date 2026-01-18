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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ItemBase
/*      */ {
/*      */   public static final int NULLABLE_BIT_FIELD_SIZE = 4;
/*      */   public static final int FIXED_BLOCK_SIZE = 147;
/*      */   public static final int VARIABLE_FIELD_COUNT = 26;
/*      */   public static final int VARIABLE_BLOCK_START = 251;
/*      */   public static final int MAX_SIZE = 1677721600;
/*      */   @Nullable
/*      */   public String id;
/*      */   @Nullable
/*      */   public String model;
/*      */   public float scale;
/*      */   @Nullable
/*      */   public String texture;
/*      */   @Nullable
/*      */   public String animation;
/*      */   @Nullable
/*      */   public String playerAnimationsId;
/*      */   public boolean usePlayerAnimations;
/*      */   public int maxStack;
/*      */   public int reticleIndex;
/*      */   @Nullable
/*      */   public String icon;
/*      */   @Nullable
/*      */   public AssetIconProperties iconProperties;
/*      */   @Nullable
/*      */   public ItemTranslationProperties translationProperties;
/*      */   public int itemLevel;
/*      */   public int qualityIndex;
/*      */   @Nullable
/*      */   public ItemResourceType[] resourceTypes;
/*      */   public boolean consumable;
/*      */   public boolean variant;
/*      */   public int blockId;
/*      */   @Nullable
/*      */   public ItemTool tool;
/*      */   @Nullable
/*      */   public ItemWeapon weapon;
/*      */   
/*      */   public ItemBase(@Nullable String id, @Nullable String model, float scale, @Nullable String texture, @Nullable String animation, @Nullable String playerAnimationsId, boolean usePlayerAnimations, int maxStack, int reticleIndex, @Nullable String icon, @Nullable AssetIconProperties iconProperties, @Nullable ItemTranslationProperties translationProperties, int itemLevel, int qualityIndex, @Nullable ItemResourceType[] resourceTypes, boolean consumable, boolean variant, int blockId, @Nullable ItemTool tool, @Nullable ItemWeapon weapon, @Nullable ItemArmor armor, @Nullable ItemGlider gliderConfig, @Nullable ItemUtility utility, @Nullable BlockSelectorToolData blockSelectorTool, @Nullable ItemBuilderToolData builderToolData, @Nullable ItemEntityConfig itemEntity, @Nullable String set, @Nullable String[] categories, @Nullable ModelParticle[] particles, @Nullable ModelParticle[] firstPersonParticles, @Nullable ModelTrail[] trails, @Nullable ColorLight light, double durability, int soundEventIndex, int itemSoundSetIndex, @Nullable Map<InteractionType, Integer> interactions, @Nullable Map<String, Integer> interactionVars, @Nullable InteractionConfiguration interactionConfig, @Nullable String droppedItemAnimation, @Nullable int[] tagIndexes, @Nullable Map<Integer, ItemAppearanceCondition[]> itemAppearanceConditions, @Nullable int[] displayEntityStatsHUD, @Nullable ItemPullbackConfiguration pullbackConfig, boolean clipsGeometry, boolean renderDeployablePreview) {
/*   70 */     this.id = id;
/*   71 */     this.model = model;
/*   72 */     this.scale = scale;
/*   73 */     this.texture = texture;
/*   74 */     this.animation = animation;
/*   75 */     this.playerAnimationsId = playerAnimationsId;
/*   76 */     this.usePlayerAnimations = usePlayerAnimations;
/*   77 */     this.maxStack = maxStack;
/*   78 */     this.reticleIndex = reticleIndex;
/*   79 */     this.icon = icon;
/*   80 */     this.iconProperties = iconProperties;
/*   81 */     this.translationProperties = translationProperties;
/*   82 */     this.itemLevel = itemLevel;
/*   83 */     this.qualityIndex = qualityIndex;
/*   84 */     this.resourceTypes = resourceTypes;
/*   85 */     this.consumable = consumable;
/*   86 */     this.variant = variant;
/*   87 */     this.blockId = blockId;
/*   88 */     this.tool = tool;
/*   89 */     this.weapon = weapon;
/*   90 */     this.armor = armor;
/*   91 */     this.gliderConfig = gliderConfig;
/*   92 */     this.utility = utility;
/*   93 */     this.blockSelectorTool = blockSelectorTool;
/*   94 */     this.builderToolData = builderToolData;
/*   95 */     this.itemEntity = itemEntity;
/*   96 */     this.set = set;
/*   97 */     this.categories = categories;
/*   98 */     this.particles = particles;
/*   99 */     this.firstPersonParticles = firstPersonParticles;
/*  100 */     this.trails = trails;
/*  101 */     this.light = light;
/*  102 */     this.durability = durability;
/*  103 */     this.soundEventIndex = soundEventIndex;
/*  104 */     this.itemSoundSetIndex = itemSoundSetIndex;
/*  105 */     this.interactions = interactions;
/*  106 */     this.interactionVars = interactionVars;
/*  107 */     this.interactionConfig = interactionConfig;
/*  108 */     this.droppedItemAnimation = droppedItemAnimation;
/*  109 */     this.tagIndexes = tagIndexes;
/*  110 */     this.itemAppearanceConditions = itemAppearanceConditions;
/*  111 */     this.displayEntityStatsHUD = displayEntityStatsHUD;
/*  112 */     this.pullbackConfig = pullbackConfig;
/*  113 */     this.clipsGeometry = clipsGeometry;
/*  114 */     this.renderDeployablePreview = renderDeployablePreview; } @Nullable public ItemArmor armor; @Nullable public ItemGlider gliderConfig; @Nullable public ItemUtility utility; @Nullable public BlockSelectorToolData blockSelectorTool; @Nullable public ItemBuilderToolData builderToolData; @Nullable public ItemEntityConfig itemEntity; @Nullable public String set; @Nullable public String[] categories; @Nullable public ModelParticle[] particles; @Nullable public ModelParticle[] firstPersonParticles; @Nullable public ModelTrail[] trails; @Nullable public ColorLight light; public double durability; public int soundEventIndex; public int itemSoundSetIndex; @Nullable public Map<InteractionType, Integer> interactions; @Nullable public Map<String, Integer> interactionVars; @Nullable public InteractionConfiguration interactionConfig; @Nullable public String droppedItemAnimation; @Nullable
/*      */   public int[] tagIndexes; @Nullable
/*      */   public Map<Integer, ItemAppearanceCondition[]> itemAppearanceConditions; @Nullable
/*      */   public int[] displayEntityStatsHUD; @Nullable
/*  118 */   public ItemPullbackConfiguration pullbackConfig; public boolean clipsGeometry; public boolean renderDeployablePreview; public ItemBase() {} public ItemBase(@Nonnull ItemBase other) { this.id = other.id;
/*  119 */     this.model = other.model;
/*  120 */     this.scale = other.scale;
/*  121 */     this.texture = other.texture;
/*  122 */     this.animation = other.animation;
/*  123 */     this.playerAnimationsId = other.playerAnimationsId;
/*  124 */     this.usePlayerAnimations = other.usePlayerAnimations;
/*  125 */     this.maxStack = other.maxStack;
/*  126 */     this.reticleIndex = other.reticleIndex;
/*  127 */     this.icon = other.icon;
/*  128 */     this.iconProperties = other.iconProperties;
/*  129 */     this.translationProperties = other.translationProperties;
/*  130 */     this.itemLevel = other.itemLevel;
/*  131 */     this.qualityIndex = other.qualityIndex;
/*  132 */     this.resourceTypes = other.resourceTypes;
/*  133 */     this.consumable = other.consumable;
/*  134 */     this.variant = other.variant;
/*  135 */     this.blockId = other.blockId;
/*  136 */     this.tool = other.tool;
/*  137 */     this.weapon = other.weapon;
/*  138 */     this.armor = other.armor;
/*  139 */     this.gliderConfig = other.gliderConfig;
/*  140 */     this.utility = other.utility;
/*  141 */     this.blockSelectorTool = other.blockSelectorTool;
/*  142 */     this.builderToolData = other.builderToolData;
/*  143 */     this.itemEntity = other.itemEntity;
/*  144 */     this.set = other.set;
/*  145 */     this.categories = other.categories;
/*  146 */     this.particles = other.particles;
/*  147 */     this.firstPersonParticles = other.firstPersonParticles;
/*  148 */     this.trails = other.trails;
/*  149 */     this.light = other.light;
/*  150 */     this.durability = other.durability;
/*  151 */     this.soundEventIndex = other.soundEventIndex;
/*  152 */     this.itemSoundSetIndex = other.itemSoundSetIndex;
/*  153 */     this.interactions = other.interactions;
/*  154 */     this.interactionVars = other.interactionVars;
/*  155 */     this.interactionConfig = other.interactionConfig;
/*  156 */     this.droppedItemAnimation = other.droppedItemAnimation;
/*  157 */     this.tagIndexes = other.tagIndexes;
/*  158 */     this.itemAppearanceConditions = other.itemAppearanceConditions;
/*  159 */     this.displayEntityStatsHUD = other.displayEntityStatsHUD;
/*  160 */     this.pullbackConfig = other.pullbackConfig;
/*  161 */     this.clipsGeometry = other.clipsGeometry;
/*  162 */     this.renderDeployablePreview = other.renderDeployablePreview; }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static ItemBase deserialize(@Nonnull ByteBuf buf, int offset) {
/*  167 */     ItemBase obj = new ItemBase();
/*  168 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 4);
/*  169 */     obj.scale = buf.getFloatLE(offset + 4);
/*  170 */     obj.usePlayerAnimations = (buf.getByte(offset + 8) != 0);
/*  171 */     obj.maxStack = buf.getIntLE(offset + 9);
/*  172 */     obj.reticleIndex = buf.getIntLE(offset + 13);
/*  173 */     if ((nullBits[0] & 0x40) != 0) obj.iconProperties = AssetIconProperties.deserialize(buf, offset + 17); 
/*  174 */     obj.itemLevel = buf.getIntLE(offset + 42);
/*  175 */     obj.qualityIndex = buf.getIntLE(offset + 46);
/*  176 */     obj.consumable = (buf.getByte(offset + 50) != 0);
/*  177 */     obj.variant = (buf.getByte(offset + 51) != 0);
/*  178 */     obj.blockId = buf.getIntLE(offset + 52);
/*  179 */     if ((nullBits[1] & 0x10) != 0) obj.gliderConfig = ItemGlider.deserialize(buf, offset + 56); 
/*  180 */     if ((nullBits[1] & 0x40) != 0) obj.blockSelectorTool = BlockSelectorToolData.deserialize(buf, offset + 72); 
/*  181 */     if ((nullBits[2] & 0x40) != 0) obj.light = ColorLight.deserialize(buf, offset + 76); 
/*  182 */     obj.durability = buf.getDoubleLE(offset + 80);
/*  183 */     obj.soundEventIndex = buf.getIntLE(offset + 88);
/*  184 */     obj.itemSoundSetIndex = buf.getIntLE(offset + 92);
/*  185 */     if ((nullBits[3] & 0x40) != 0) obj.pullbackConfig = ItemPullbackConfiguration.deserialize(buf, offset + 96); 
/*  186 */     obj.clipsGeometry = (buf.getByte(offset + 145) != 0);
/*  187 */     obj.renderDeployablePreview = (buf.getByte(offset + 146) != 0);
/*      */     
/*  189 */     if ((nullBits[0] & 0x1) != 0) {
/*  190 */       int varPos0 = offset + 251 + buf.getIntLE(offset + 147);
/*  191 */       int idLen = VarInt.peek(buf, varPos0);
/*  192 */       if (idLen < 0) throw ProtocolException.negativeLength("Id", idLen); 
/*  193 */       if (idLen > 4096000) throw ProtocolException.stringTooLong("Id", idLen, 4096000); 
/*  194 */       obj.id = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*      */     } 
/*  196 */     if ((nullBits[0] & 0x2) != 0) {
/*  197 */       int varPos1 = offset + 251 + buf.getIntLE(offset + 151);
/*  198 */       int modelLen = VarInt.peek(buf, varPos1);
/*  199 */       if (modelLen < 0) throw ProtocolException.negativeLength("Model", modelLen); 
/*  200 */       if (modelLen > 4096000) throw ProtocolException.stringTooLong("Model", modelLen, 4096000); 
/*  201 */       obj.model = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*      */     } 
/*  203 */     if ((nullBits[0] & 0x4) != 0) {
/*  204 */       int varPos2 = offset + 251 + buf.getIntLE(offset + 155);
/*  205 */       int textureLen = VarInt.peek(buf, varPos2);
/*  206 */       if (textureLen < 0) throw ProtocolException.negativeLength("Texture", textureLen); 
/*  207 */       if (textureLen > 4096000) throw ProtocolException.stringTooLong("Texture", textureLen, 4096000); 
/*  208 */       obj.texture = PacketIO.readVarString(buf, varPos2, PacketIO.UTF8);
/*      */     } 
/*  210 */     if ((nullBits[0] & 0x8) != 0) {
/*  211 */       int varPos3 = offset + 251 + buf.getIntLE(offset + 159);
/*  212 */       int animationLen = VarInt.peek(buf, varPos3);
/*  213 */       if (animationLen < 0) throw ProtocolException.negativeLength("Animation", animationLen); 
/*  214 */       if (animationLen > 4096000) throw ProtocolException.stringTooLong("Animation", animationLen, 4096000); 
/*  215 */       obj.animation = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*      */     } 
/*  217 */     if ((nullBits[0] & 0x10) != 0) {
/*  218 */       int varPos4 = offset + 251 + buf.getIntLE(offset + 163);
/*  219 */       int playerAnimationsIdLen = VarInt.peek(buf, varPos4);
/*  220 */       if (playerAnimationsIdLen < 0) throw ProtocolException.negativeLength("PlayerAnimationsId", playerAnimationsIdLen); 
/*  221 */       if (playerAnimationsIdLen > 4096000) throw ProtocolException.stringTooLong("PlayerAnimationsId", playerAnimationsIdLen, 4096000); 
/*  222 */       obj.playerAnimationsId = PacketIO.readVarString(buf, varPos4, PacketIO.UTF8);
/*      */     } 
/*  224 */     if ((nullBits[0] & 0x20) != 0) {
/*  225 */       int varPos5 = offset + 251 + buf.getIntLE(offset + 167);
/*  226 */       int iconLen = VarInt.peek(buf, varPos5);
/*  227 */       if (iconLen < 0) throw ProtocolException.negativeLength("Icon", iconLen); 
/*  228 */       if (iconLen > 4096000) throw ProtocolException.stringTooLong("Icon", iconLen, 4096000); 
/*  229 */       obj.icon = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*      */     } 
/*  231 */     if ((nullBits[0] & 0x80) != 0) {
/*  232 */       int varPos6 = offset + 251 + buf.getIntLE(offset + 171);
/*  233 */       obj.translationProperties = ItemTranslationProperties.deserialize(buf, varPos6);
/*      */     } 
/*  235 */     if ((nullBits[1] & 0x1) != 0) {
/*  236 */       int varPos7 = offset + 251 + buf.getIntLE(offset + 175);
/*  237 */       int resourceTypesCount = VarInt.peek(buf, varPos7);
/*  238 */       if (resourceTypesCount < 0) throw ProtocolException.negativeLength("ResourceTypes", resourceTypesCount); 
/*  239 */       if (resourceTypesCount > 4096000) throw ProtocolException.arrayTooLong("ResourceTypes", resourceTypesCount, 4096000); 
/*  240 */       int varIntLen = VarInt.length(buf, varPos7);
/*  241 */       if ((varPos7 + varIntLen) + resourceTypesCount * 5L > buf.readableBytes())
/*  242 */         throw ProtocolException.bufferTooSmall("ResourceTypes", varPos7 + varIntLen + resourceTypesCount * 5, buf.readableBytes()); 
/*  243 */       obj.resourceTypes = new ItemResourceType[resourceTypesCount];
/*  244 */       int elemPos = varPos7 + varIntLen;
/*  245 */       for (int i = 0; i < resourceTypesCount; i++) {
/*  246 */         obj.resourceTypes[i] = ItemResourceType.deserialize(buf, elemPos);
/*  247 */         elemPos += ItemResourceType.computeBytesConsumed(buf, elemPos);
/*      */       } 
/*      */     } 
/*  250 */     if ((nullBits[1] & 0x2) != 0) {
/*  251 */       int varPos8 = offset + 251 + buf.getIntLE(offset + 179);
/*  252 */       obj.tool = ItemTool.deserialize(buf, varPos8);
/*      */     } 
/*  254 */     if ((nullBits[1] & 0x4) != 0) {
/*  255 */       int varPos9 = offset + 251 + buf.getIntLE(offset + 183);
/*  256 */       obj.weapon = ItemWeapon.deserialize(buf, varPos9);
/*      */     } 
/*  258 */     if ((nullBits[1] & 0x8) != 0) {
/*  259 */       int varPos10 = offset + 251 + buf.getIntLE(offset + 187);
/*  260 */       obj.armor = ItemArmor.deserialize(buf, varPos10);
/*      */     } 
/*  262 */     if ((nullBits[1] & 0x20) != 0) {
/*  263 */       int varPos11 = offset + 251 + buf.getIntLE(offset + 191);
/*  264 */       obj.utility = ItemUtility.deserialize(buf, varPos11);
/*      */     } 
/*  266 */     if ((nullBits[1] & 0x80) != 0) {
/*  267 */       int varPos12 = offset + 251 + buf.getIntLE(offset + 195);
/*  268 */       obj.builderToolData = ItemBuilderToolData.deserialize(buf, varPos12);
/*      */     } 
/*  270 */     if ((nullBits[2] & 0x1) != 0) {
/*  271 */       int varPos13 = offset + 251 + buf.getIntLE(offset + 199);
/*  272 */       obj.itemEntity = ItemEntityConfig.deserialize(buf, varPos13);
/*      */     } 
/*  274 */     if ((nullBits[2] & 0x2) != 0) {
/*  275 */       int varPos14 = offset + 251 + buf.getIntLE(offset + 203);
/*  276 */       int setLen = VarInt.peek(buf, varPos14);
/*  277 */       if (setLen < 0) throw ProtocolException.negativeLength("Set", setLen); 
/*  278 */       if (setLen > 4096000) throw ProtocolException.stringTooLong("Set", setLen, 4096000); 
/*  279 */       obj.set = PacketIO.readVarString(buf, varPos14, PacketIO.UTF8);
/*      */     } 
/*  281 */     if ((nullBits[2] & 0x4) != 0) {
/*  282 */       int varPos15 = offset + 251 + buf.getIntLE(offset + 207);
/*  283 */       int categoriesCount = VarInt.peek(buf, varPos15);
/*  284 */       if (categoriesCount < 0) throw ProtocolException.negativeLength("Categories", categoriesCount); 
/*  285 */       if (categoriesCount > 4096000) throw ProtocolException.arrayTooLong("Categories", categoriesCount, 4096000); 
/*  286 */       int varIntLen = VarInt.length(buf, varPos15);
/*  287 */       if ((varPos15 + varIntLen) + categoriesCount * 1L > buf.readableBytes())
/*  288 */         throw ProtocolException.bufferTooSmall("Categories", varPos15 + varIntLen + categoriesCount * 1, buf.readableBytes()); 
/*  289 */       obj.categories = new String[categoriesCount];
/*  290 */       int elemPos = varPos15 + varIntLen;
/*  291 */       for (int i = 0; i < categoriesCount; i++) {
/*  292 */         int strLen = VarInt.peek(buf, elemPos);
/*  293 */         if (strLen < 0) throw ProtocolException.negativeLength("categories[" + i + "]", strLen); 
/*  294 */         if (strLen > 4096000) throw ProtocolException.stringTooLong("categories[" + i + "]", strLen, 4096000); 
/*  295 */         int strVarLen = VarInt.length(buf, elemPos);
/*  296 */         obj.categories[i] = PacketIO.readVarString(buf, elemPos);
/*  297 */         elemPos += strVarLen + strLen;
/*      */       } 
/*      */     } 
/*  300 */     if ((nullBits[2] & 0x8) != 0) {
/*  301 */       int varPos16 = offset + 251 + buf.getIntLE(offset + 211);
/*  302 */       int particlesCount = VarInt.peek(buf, varPos16);
/*  303 */       if (particlesCount < 0) throw ProtocolException.negativeLength("Particles", particlesCount); 
/*  304 */       if (particlesCount > 4096000) throw ProtocolException.arrayTooLong("Particles", particlesCount, 4096000); 
/*  305 */       int varIntLen = VarInt.length(buf, varPos16);
/*  306 */       if ((varPos16 + varIntLen) + particlesCount * 34L > buf.readableBytes())
/*  307 */         throw ProtocolException.bufferTooSmall("Particles", varPos16 + varIntLen + particlesCount * 34, buf.readableBytes()); 
/*  308 */       obj.particles = new ModelParticle[particlesCount];
/*  309 */       int elemPos = varPos16 + varIntLen;
/*  310 */       for (int i = 0; i < particlesCount; i++) {
/*  311 */         obj.particles[i] = ModelParticle.deserialize(buf, elemPos);
/*  312 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*      */       } 
/*      */     } 
/*  315 */     if ((nullBits[2] & 0x10) != 0) {
/*  316 */       int varPos17 = offset + 251 + buf.getIntLE(offset + 215);
/*  317 */       int firstPersonParticlesCount = VarInt.peek(buf, varPos17);
/*  318 */       if (firstPersonParticlesCount < 0) throw ProtocolException.negativeLength("FirstPersonParticles", firstPersonParticlesCount); 
/*  319 */       if (firstPersonParticlesCount > 4096000) throw ProtocolException.arrayTooLong("FirstPersonParticles", firstPersonParticlesCount, 4096000); 
/*  320 */       int varIntLen = VarInt.length(buf, varPos17);
/*  321 */       if ((varPos17 + varIntLen) + firstPersonParticlesCount * 34L > buf.readableBytes())
/*  322 */         throw ProtocolException.bufferTooSmall("FirstPersonParticles", varPos17 + varIntLen + firstPersonParticlesCount * 34, buf.readableBytes()); 
/*  323 */       obj.firstPersonParticles = new ModelParticle[firstPersonParticlesCount];
/*  324 */       int elemPos = varPos17 + varIntLen;
/*  325 */       for (int i = 0; i < firstPersonParticlesCount; i++) {
/*  326 */         obj.firstPersonParticles[i] = ModelParticle.deserialize(buf, elemPos);
/*  327 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*      */       } 
/*      */     } 
/*  330 */     if ((nullBits[2] & 0x20) != 0) {
/*  331 */       int varPos18 = offset + 251 + buf.getIntLE(offset + 219);
/*  332 */       int trailsCount = VarInt.peek(buf, varPos18);
/*  333 */       if (trailsCount < 0) throw ProtocolException.negativeLength("Trails", trailsCount); 
/*  334 */       if (trailsCount > 4096000) throw ProtocolException.arrayTooLong("Trails", trailsCount, 4096000); 
/*  335 */       int varIntLen = VarInt.length(buf, varPos18);
/*  336 */       if ((varPos18 + varIntLen) + trailsCount * 27L > buf.readableBytes())
/*  337 */         throw ProtocolException.bufferTooSmall("Trails", varPos18 + varIntLen + trailsCount * 27, buf.readableBytes()); 
/*  338 */       obj.trails = new ModelTrail[trailsCount];
/*  339 */       int elemPos = varPos18 + varIntLen;
/*  340 */       for (int i = 0; i < trailsCount; i++) {
/*  341 */         obj.trails[i] = ModelTrail.deserialize(buf, elemPos);
/*  342 */         elemPos += ModelTrail.computeBytesConsumed(buf, elemPos);
/*      */       } 
/*      */     } 
/*  345 */     if ((nullBits[2] & 0x80) != 0) {
/*  346 */       int varPos19 = offset + 251 + buf.getIntLE(offset + 223);
/*  347 */       int interactionsCount = VarInt.peek(buf, varPos19);
/*  348 */       if (interactionsCount < 0) throw ProtocolException.negativeLength("Interactions", interactionsCount); 
/*  349 */       if (interactionsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", interactionsCount, 4096000); 
/*  350 */       int varIntLen = VarInt.length(buf, varPos19);
/*  351 */       obj.interactions = new HashMap<>(interactionsCount);
/*  352 */       int dictPos = varPos19 + varIntLen;
/*  353 */       for (int i = 0; i < interactionsCount; i++) {
/*  354 */         InteractionType key = InteractionType.fromValue(buf.getByte(dictPos)); dictPos++;
/*  355 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/*  356 */         if (obj.interactions.put(key, Integer.valueOf(val)) != null)
/*  357 */           throw ProtocolException.duplicateKey("interactions", key); 
/*      */       } 
/*      */     } 
/*  360 */     if ((nullBits[3] & 0x1) != 0) {
/*  361 */       int varPos20 = offset + 251 + buf.getIntLE(offset + 227);
/*  362 */       int interactionVarsCount = VarInt.peek(buf, varPos20);
/*  363 */       if (interactionVarsCount < 0) throw ProtocolException.negativeLength("InteractionVars", interactionVarsCount); 
/*  364 */       if (interactionVarsCount > 4096000) throw ProtocolException.dictionaryTooLarge("InteractionVars", interactionVarsCount, 4096000); 
/*  365 */       int varIntLen = VarInt.length(buf, varPos20);
/*  366 */       obj.interactionVars = new HashMap<>(interactionVarsCount);
/*  367 */       int dictPos = varPos20 + varIntLen;
/*  368 */       for (int i = 0; i < interactionVarsCount; i++) {
/*  369 */         int keyLen = VarInt.peek(buf, dictPos);
/*  370 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  371 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  372 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  373 */         String key = PacketIO.readVarString(buf, dictPos);
/*  374 */         dictPos += keyVarLen + keyLen;
/*  375 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/*  376 */         if (obj.interactionVars.put(key, Integer.valueOf(val)) != null)
/*  377 */           throw ProtocolException.duplicateKey("interactionVars", key); 
/*      */       } 
/*      */     } 
/*  380 */     if ((nullBits[3] & 0x2) != 0) {
/*  381 */       int varPos21 = offset + 251 + buf.getIntLE(offset + 231);
/*  382 */       obj.interactionConfig = InteractionConfiguration.deserialize(buf, varPos21);
/*      */     } 
/*  384 */     if ((nullBits[3] & 0x4) != 0) {
/*  385 */       int varPos22 = offset + 251 + buf.getIntLE(offset + 235);
/*  386 */       int droppedItemAnimationLen = VarInt.peek(buf, varPos22);
/*  387 */       if (droppedItemAnimationLen < 0) throw ProtocolException.negativeLength("DroppedItemAnimation", droppedItemAnimationLen); 
/*  388 */       if (droppedItemAnimationLen > 4096000) throw ProtocolException.stringTooLong("DroppedItemAnimation", droppedItemAnimationLen, 4096000); 
/*  389 */       obj.droppedItemAnimation = PacketIO.readVarString(buf, varPos22, PacketIO.UTF8);
/*      */     } 
/*  391 */     if ((nullBits[3] & 0x8) != 0) {
/*  392 */       int varPos23 = offset + 251 + buf.getIntLE(offset + 239);
/*  393 */       int tagIndexesCount = VarInt.peek(buf, varPos23);
/*  394 */       if (tagIndexesCount < 0) throw ProtocolException.negativeLength("TagIndexes", tagIndexesCount); 
/*  395 */       if (tagIndexesCount > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", tagIndexesCount, 4096000); 
/*  396 */       int varIntLen = VarInt.length(buf, varPos23);
/*  397 */       if ((varPos23 + varIntLen) + tagIndexesCount * 4L > buf.readableBytes())
/*  398 */         throw ProtocolException.bufferTooSmall("TagIndexes", varPos23 + varIntLen + tagIndexesCount * 4, buf.readableBytes()); 
/*  399 */       obj.tagIndexes = new int[tagIndexesCount];
/*  400 */       for (int i = 0; i < tagIndexesCount; i++) {
/*  401 */         obj.tagIndexes[i] = buf.getIntLE(varPos23 + varIntLen + i * 4);
/*      */       }
/*      */     } 
/*  404 */     if ((nullBits[3] & 0x10) != 0) {
/*  405 */       int varPos24 = offset + 251 + buf.getIntLE(offset + 243);
/*  406 */       int itemAppearanceConditionsCount = VarInt.peek(buf, varPos24);
/*  407 */       if (itemAppearanceConditionsCount < 0) throw ProtocolException.negativeLength("ItemAppearanceConditions", itemAppearanceConditionsCount); 
/*  408 */       if (itemAppearanceConditionsCount > 4096000) throw ProtocolException.dictionaryTooLarge("ItemAppearanceConditions", itemAppearanceConditionsCount, 4096000); 
/*  409 */       int varIntLen = VarInt.length(buf, varPos24);
/*  410 */       obj.itemAppearanceConditions = (Map)new HashMap<>(itemAppearanceConditionsCount);
/*  411 */       int dictPos = varPos24 + varIntLen;
/*  412 */       for (int i = 0; i < itemAppearanceConditionsCount; i++) {
/*  413 */         int key = buf.getIntLE(dictPos); dictPos += 4;
/*  414 */         int valLen = VarInt.peek(buf, dictPos);
/*  415 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  416 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/*  417 */         int valVarLen = VarInt.length(buf, dictPos);
/*  418 */         if ((dictPos + valVarLen) + valLen * 18L > buf.readableBytes())
/*  419 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 18, buf.readableBytes()); 
/*  420 */         dictPos += valVarLen;
/*  421 */         ItemAppearanceCondition[] val = new ItemAppearanceCondition[valLen];
/*  422 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/*  423 */           val[valIdx] = ItemAppearanceCondition.deserialize(buf, dictPos);
/*  424 */           dictPos += ItemAppearanceCondition.computeBytesConsumed(buf, dictPos);
/*      */         } 
/*  426 */         if (obj.itemAppearanceConditions.put(Integer.valueOf(key), val) != null)
/*  427 */           throw ProtocolException.duplicateKey("itemAppearanceConditions", Integer.valueOf(key)); 
/*      */       } 
/*      */     } 
/*  430 */     if ((nullBits[3] & 0x20) != 0) {
/*  431 */       int varPos25 = offset + 251 + buf.getIntLE(offset + 247);
/*  432 */       int displayEntityStatsHUDCount = VarInt.peek(buf, varPos25);
/*  433 */       if (displayEntityStatsHUDCount < 0) throw ProtocolException.negativeLength("DisplayEntityStatsHUD", displayEntityStatsHUDCount); 
/*  434 */       if (displayEntityStatsHUDCount > 4096000) throw ProtocolException.arrayTooLong("DisplayEntityStatsHUD", displayEntityStatsHUDCount, 4096000); 
/*  435 */       int varIntLen = VarInt.length(buf, varPos25);
/*  436 */       if ((varPos25 + varIntLen) + displayEntityStatsHUDCount * 4L > buf.readableBytes())
/*  437 */         throw ProtocolException.bufferTooSmall("DisplayEntityStatsHUD", varPos25 + varIntLen + displayEntityStatsHUDCount * 4, buf.readableBytes()); 
/*  438 */       obj.displayEntityStatsHUD = new int[displayEntityStatsHUDCount];
/*  439 */       for (int i = 0; i < displayEntityStatsHUDCount; i++) {
/*  440 */         obj.displayEntityStatsHUD[i] = buf.getIntLE(varPos25 + varIntLen + i * 4);
/*      */       }
/*      */     } 
/*      */     
/*  444 */     return obj;
/*      */   }
/*      */   
/*      */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  448 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 4);
/*  449 */     int maxEnd = 251;
/*  450 */     if ((nullBits[0] & 0x1) != 0) {
/*  451 */       int fieldOffset0 = buf.getIntLE(offset + 147);
/*  452 */       int pos0 = offset + 251 + fieldOffset0;
/*  453 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  454 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*      */     } 
/*  456 */     if ((nullBits[0] & 0x2) != 0) {
/*  457 */       int fieldOffset1 = buf.getIntLE(offset + 151);
/*  458 */       int pos1 = offset + 251 + fieldOffset1;
/*  459 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  460 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*      */     } 
/*  462 */     if ((nullBits[0] & 0x4) != 0) {
/*  463 */       int fieldOffset2 = buf.getIntLE(offset + 155);
/*  464 */       int pos2 = offset + 251 + fieldOffset2;
/*  465 */       int sl = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + sl;
/*  466 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*      */     } 
/*  468 */     if ((nullBits[0] & 0x8) != 0) {
/*  469 */       int fieldOffset3 = buf.getIntLE(offset + 159);
/*  470 */       int pos3 = offset + 251 + fieldOffset3;
/*  471 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/*  472 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*      */     } 
/*  474 */     if ((nullBits[0] & 0x10) != 0) {
/*  475 */       int fieldOffset4 = buf.getIntLE(offset + 163);
/*  476 */       int pos4 = offset + 251 + fieldOffset4;
/*  477 */       int sl = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4) + sl;
/*  478 */       if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*      */     } 
/*  480 */     if ((nullBits[0] & 0x20) != 0) {
/*  481 */       int fieldOffset5 = buf.getIntLE(offset + 167);
/*  482 */       int pos5 = offset + 251 + fieldOffset5;
/*  483 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/*  484 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*      */     } 
/*  486 */     if ((nullBits[0] & 0x80) != 0) {
/*  487 */       int fieldOffset6 = buf.getIntLE(offset + 171);
/*  488 */       int pos6 = offset + 251 + fieldOffset6;
/*  489 */       pos6 += ItemTranslationProperties.computeBytesConsumed(buf, pos6);
/*  490 */       if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*      */     } 
/*  492 */     if ((nullBits[1] & 0x1) != 0) {
/*  493 */       int fieldOffset7 = buf.getIntLE(offset + 175);
/*  494 */       int pos7 = offset + 251 + fieldOffset7;
/*  495 */       int arrLen = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7);
/*  496 */       for (int i = 0; i < arrLen; ) { pos7 += ItemResourceType.computeBytesConsumed(buf, pos7); i++; }
/*  497 */        if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*      */     } 
/*  499 */     if ((nullBits[1] & 0x2) != 0) {
/*  500 */       int fieldOffset8 = buf.getIntLE(offset + 179);
/*  501 */       int pos8 = offset + 251 + fieldOffset8;
/*  502 */       pos8 += ItemTool.computeBytesConsumed(buf, pos8);
/*  503 */       if (pos8 - offset > maxEnd) maxEnd = pos8 - offset; 
/*      */     } 
/*  505 */     if ((nullBits[1] & 0x4) != 0) {
/*  506 */       int fieldOffset9 = buf.getIntLE(offset + 183);
/*  507 */       int pos9 = offset + 251 + fieldOffset9;
/*  508 */       pos9 += ItemWeapon.computeBytesConsumed(buf, pos9);
/*  509 */       if (pos9 - offset > maxEnd) maxEnd = pos9 - offset; 
/*      */     } 
/*  511 */     if ((nullBits[1] & 0x8) != 0) {
/*  512 */       int fieldOffset10 = buf.getIntLE(offset + 187);
/*  513 */       int pos10 = offset + 251 + fieldOffset10;
/*  514 */       pos10 += ItemArmor.computeBytesConsumed(buf, pos10);
/*  515 */       if (pos10 - offset > maxEnd) maxEnd = pos10 - offset; 
/*      */     } 
/*  517 */     if ((nullBits[1] & 0x20) != 0) {
/*  518 */       int fieldOffset11 = buf.getIntLE(offset + 191);
/*  519 */       int pos11 = offset + 251 + fieldOffset11;
/*  520 */       pos11 += ItemUtility.computeBytesConsumed(buf, pos11);
/*  521 */       if (pos11 - offset > maxEnd) maxEnd = pos11 - offset; 
/*      */     } 
/*  523 */     if ((nullBits[1] & 0x80) != 0) {
/*  524 */       int fieldOffset12 = buf.getIntLE(offset + 195);
/*  525 */       int pos12 = offset + 251 + fieldOffset12;
/*  526 */       pos12 += ItemBuilderToolData.computeBytesConsumed(buf, pos12);
/*  527 */       if (pos12 - offset > maxEnd) maxEnd = pos12 - offset; 
/*      */     } 
/*  529 */     if ((nullBits[2] & 0x1) != 0) {
/*  530 */       int fieldOffset13 = buf.getIntLE(offset + 199);
/*  531 */       int pos13 = offset + 251 + fieldOffset13;
/*  532 */       pos13 += ItemEntityConfig.computeBytesConsumed(buf, pos13);
/*  533 */       if (pos13 - offset > maxEnd) maxEnd = pos13 - offset; 
/*      */     } 
/*  535 */     if ((nullBits[2] & 0x2) != 0) {
/*  536 */       int fieldOffset14 = buf.getIntLE(offset + 203);
/*  537 */       int pos14 = offset + 251 + fieldOffset14;
/*  538 */       int sl = VarInt.peek(buf, pos14); pos14 += VarInt.length(buf, pos14) + sl;
/*  539 */       if (pos14 - offset > maxEnd) maxEnd = pos14 - offset; 
/*      */     } 
/*  541 */     if ((nullBits[2] & 0x4) != 0) {
/*  542 */       int fieldOffset15 = buf.getIntLE(offset + 207);
/*  543 */       int pos15 = offset + 251 + fieldOffset15;
/*  544 */       int arrLen = VarInt.peek(buf, pos15); pos15 += VarInt.length(buf, pos15);
/*  545 */       for (int i = 0; i < arrLen; ) { int sl = VarInt.peek(buf, pos15); pos15 += VarInt.length(buf, pos15) + sl; i++; }
/*  546 */        if (pos15 - offset > maxEnd) maxEnd = pos15 - offset; 
/*      */     } 
/*  548 */     if ((nullBits[2] & 0x8) != 0) {
/*  549 */       int fieldOffset16 = buf.getIntLE(offset + 211);
/*  550 */       int pos16 = offset + 251 + fieldOffset16;
/*  551 */       int arrLen = VarInt.peek(buf, pos16); pos16 += VarInt.length(buf, pos16);
/*  552 */       for (int i = 0; i < arrLen; ) { pos16 += ModelParticle.computeBytesConsumed(buf, pos16); i++; }
/*  553 */        if (pos16 - offset > maxEnd) maxEnd = pos16 - offset; 
/*      */     } 
/*  555 */     if ((nullBits[2] & 0x10) != 0) {
/*  556 */       int fieldOffset17 = buf.getIntLE(offset + 215);
/*  557 */       int pos17 = offset + 251 + fieldOffset17;
/*  558 */       int arrLen = VarInt.peek(buf, pos17); pos17 += VarInt.length(buf, pos17);
/*  559 */       for (int i = 0; i < arrLen; ) { pos17 += ModelParticle.computeBytesConsumed(buf, pos17); i++; }
/*  560 */        if (pos17 - offset > maxEnd) maxEnd = pos17 - offset; 
/*      */     } 
/*  562 */     if ((nullBits[2] & 0x20) != 0) {
/*  563 */       int fieldOffset18 = buf.getIntLE(offset + 219);
/*  564 */       int pos18 = offset + 251 + fieldOffset18;
/*  565 */       int arrLen = VarInt.peek(buf, pos18); pos18 += VarInt.length(buf, pos18);
/*  566 */       for (int i = 0; i < arrLen; ) { pos18 += ModelTrail.computeBytesConsumed(buf, pos18); i++; }
/*  567 */        if (pos18 - offset > maxEnd) maxEnd = pos18 - offset; 
/*      */     } 
/*  569 */     if ((nullBits[2] & 0x80) != 0) {
/*  570 */       int fieldOffset19 = buf.getIntLE(offset + 223);
/*  571 */       int pos19 = offset + 251 + fieldOffset19;
/*  572 */       int dictLen = VarInt.peek(buf, pos19); pos19 += VarInt.length(buf, pos19);
/*  573 */       for (int i = 0; i < dictLen; ) { pos19++; pos19 += 4; i++; }
/*  574 */        if (pos19 - offset > maxEnd) maxEnd = pos19 - offset; 
/*      */     } 
/*  576 */     if ((nullBits[3] & 0x1) != 0) {
/*  577 */       int fieldOffset20 = buf.getIntLE(offset + 227);
/*  578 */       int pos20 = offset + 251 + fieldOffset20;
/*  579 */       int dictLen = VarInt.peek(buf, pos20); pos20 += VarInt.length(buf, pos20);
/*  580 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos20); pos20 += VarInt.length(buf, pos20) + sl; pos20 += 4; i++; }
/*  581 */        if (pos20 - offset > maxEnd) maxEnd = pos20 - offset; 
/*      */     } 
/*  583 */     if ((nullBits[3] & 0x2) != 0) {
/*  584 */       int fieldOffset21 = buf.getIntLE(offset + 231);
/*  585 */       int pos21 = offset + 251 + fieldOffset21;
/*  586 */       pos21 += InteractionConfiguration.computeBytesConsumed(buf, pos21);
/*  587 */       if (pos21 - offset > maxEnd) maxEnd = pos21 - offset; 
/*      */     } 
/*  589 */     if ((nullBits[3] & 0x4) != 0) {
/*  590 */       int fieldOffset22 = buf.getIntLE(offset + 235);
/*  591 */       int pos22 = offset + 251 + fieldOffset22;
/*  592 */       int sl = VarInt.peek(buf, pos22); pos22 += VarInt.length(buf, pos22) + sl;
/*  593 */       if (pos22 - offset > maxEnd) maxEnd = pos22 - offset; 
/*      */     } 
/*  595 */     if ((nullBits[3] & 0x8) != 0) {
/*  596 */       int fieldOffset23 = buf.getIntLE(offset + 239);
/*  597 */       int pos23 = offset + 251 + fieldOffset23;
/*  598 */       int arrLen = VarInt.peek(buf, pos23); pos23 += VarInt.length(buf, pos23) + arrLen * 4;
/*  599 */       if (pos23 - offset > maxEnd) maxEnd = pos23 - offset; 
/*      */     } 
/*  601 */     if ((nullBits[3] & 0x10) != 0) {
/*  602 */       int fieldOffset24 = buf.getIntLE(offset + 243);
/*  603 */       int pos24 = offset + 251 + fieldOffset24;
/*  604 */       int dictLen = VarInt.peek(buf, pos24); pos24 += VarInt.length(buf, pos24);
/*  605 */       for (int i = 0; i < dictLen; ) { int al; int j; for (pos24 += 4, al = VarInt.peek(buf, pos24), pos24 += VarInt.length(buf, pos24), j = 0; j < al; ) { pos24 += ItemAppearanceCondition.computeBytesConsumed(buf, pos24); j++; }  i++; }
/*  606 */        if (pos24 - offset > maxEnd) maxEnd = pos24 - offset; 
/*      */     } 
/*  608 */     if ((nullBits[3] & 0x20) != 0) {
/*  609 */       int fieldOffset25 = buf.getIntLE(offset + 247);
/*  610 */       int pos25 = offset + 251 + fieldOffset25;
/*  611 */       int arrLen = VarInt.peek(buf, pos25); pos25 += VarInt.length(buf, pos25) + arrLen * 4;
/*  612 */       if (pos25 - offset > maxEnd) maxEnd = pos25 - offset; 
/*      */     } 
/*  614 */     return maxEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   public void serialize(@Nonnull ByteBuf buf) {
/*  619 */     int startPos = buf.writerIndex();
/*  620 */     byte[] nullBits = new byte[4];
/*  621 */     if (this.id != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/*  622 */     if (this.model != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/*  623 */     if (this.texture != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/*  624 */     if (this.animation != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/*  625 */     if (this.playerAnimationsId != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/*  626 */     if (this.icon != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/*  627 */     if (this.iconProperties != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/*  628 */     if (this.translationProperties != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/*  629 */     if (this.resourceTypes != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/*  630 */     if (this.tool != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/*  631 */     if (this.weapon != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/*  632 */     if (this.armor != null) nullBits[1] = (byte)(nullBits[1] | 0x8); 
/*  633 */     if (this.gliderConfig != null) nullBits[1] = (byte)(nullBits[1] | 0x10); 
/*  634 */     if (this.utility != null) nullBits[1] = (byte)(nullBits[1] | 0x20); 
/*  635 */     if (this.blockSelectorTool != null) nullBits[1] = (byte)(nullBits[1] | 0x40); 
/*  636 */     if (this.builderToolData != null) nullBits[1] = (byte)(nullBits[1] | 0x80); 
/*  637 */     if (this.itemEntity != null) nullBits[2] = (byte)(nullBits[2] | 0x1); 
/*  638 */     if (this.set != null) nullBits[2] = (byte)(nullBits[2] | 0x2); 
/*  639 */     if (this.categories != null) nullBits[2] = (byte)(nullBits[2] | 0x4); 
/*  640 */     if (this.particles != null) nullBits[2] = (byte)(nullBits[2] | 0x8); 
/*  641 */     if (this.firstPersonParticles != null) nullBits[2] = (byte)(nullBits[2] | 0x10); 
/*  642 */     if (this.trails != null) nullBits[2] = (byte)(nullBits[2] | 0x20); 
/*  643 */     if (this.light != null) nullBits[2] = (byte)(nullBits[2] | 0x40); 
/*  644 */     if (this.interactions != null) nullBits[2] = (byte)(nullBits[2] | 0x80); 
/*  645 */     if (this.interactionVars != null) nullBits[3] = (byte)(nullBits[3] | 0x1); 
/*  646 */     if (this.interactionConfig != null) nullBits[3] = (byte)(nullBits[3] | 0x2); 
/*  647 */     if (this.droppedItemAnimation != null) nullBits[3] = (byte)(nullBits[3] | 0x4); 
/*  648 */     if (this.tagIndexes != null) nullBits[3] = (byte)(nullBits[3] | 0x8); 
/*  649 */     if (this.itemAppearanceConditions != null) nullBits[3] = (byte)(nullBits[3] | 0x10); 
/*  650 */     if (this.displayEntityStatsHUD != null) nullBits[3] = (byte)(nullBits[3] | 0x20); 
/*  651 */     if (this.pullbackConfig != null) nullBits[3] = (byte)(nullBits[3] | 0x40); 
/*  652 */     buf.writeBytes(nullBits);
/*      */     
/*  654 */     buf.writeFloatLE(this.scale);
/*  655 */     buf.writeByte(this.usePlayerAnimations ? 1 : 0);
/*  656 */     buf.writeIntLE(this.maxStack);
/*  657 */     buf.writeIntLE(this.reticleIndex);
/*  658 */     if (this.iconProperties != null) { this.iconProperties.serialize(buf); } else { buf.writeZero(25); }
/*  659 */      buf.writeIntLE(this.itemLevel);
/*  660 */     buf.writeIntLE(this.qualityIndex);
/*  661 */     buf.writeByte(this.consumable ? 1 : 0);
/*  662 */     buf.writeByte(this.variant ? 1 : 0);
/*  663 */     buf.writeIntLE(this.blockId);
/*  664 */     if (this.gliderConfig != null) { this.gliderConfig.serialize(buf); } else { buf.writeZero(16); }
/*  665 */      if (this.blockSelectorTool != null) { this.blockSelectorTool.serialize(buf); } else { buf.writeZero(4); }
/*  666 */      if (this.light != null) { this.light.serialize(buf); } else { buf.writeZero(4); }
/*  667 */      buf.writeDoubleLE(this.durability);
/*  668 */     buf.writeIntLE(this.soundEventIndex);
/*  669 */     buf.writeIntLE(this.itemSoundSetIndex);
/*  670 */     if (this.pullbackConfig != null) { this.pullbackConfig.serialize(buf); } else { buf.writeZero(49); }
/*  671 */      buf.writeByte(this.clipsGeometry ? 1 : 0);
/*  672 */     buf.writeByte(this.renderDeployablePreview ? 1 : 0);
/*      */     
/*  674 */     int idOffsetSlot = buf.writerIndex();
/*  675 */     buf.writeIntLE(0);
/*  676 */     int modelOffsetSlot = buf.writerIndex();
/*  677 */     buf.writeIntLE(0);
/*  678 */     int textureOffsetSlot = buf.writerIndex();
/*  679 */     buf.writeIntLE(0);
/*  680 */     int animationOffsetSlot = buf.writerIndex();
/*  681 */     buf.writeIntLE(0);
/*  682 */     int playerAnimationsIdOffsetSlot = buf.writerIndex();
/*  683 */     buf.writeIntLE(0);
/*  684 */     int iconOffsetSlot = buf.writerIndex();
/*  685 */     buf.writeIntLE(0);
/*  686 */     int translationPropertiesOffsetSlot = buf.writerIndex();
/*  687 */     buf.writeIntLE(0);
/*  688 */     int resourceTypesOffsetSlot = buf.writerIndex();
/*  689 */     buf.writeIntLE(0);
/*  690 */     int toolOffsetSlot = buf.writerIndex();
/*  691 */     buf.writeIntLE(0);
/*  692 */     int weaponOffsetSlot = buf.writerIndex();
/*  693 */     buf.writeIntLE(0);
/*  694 */     int armorOffsetSlot = buf.writerIndex();
/*  695 */     buf.writeIntLE(0);
/*  696 */     int utilityOffsetSlot = buf.writerIndex();
/*  697 */     buf.writeIntLE(0);
/*  698 */     int builderToolDataOffsetSlot = buf.writerIndex();
/*  699 */     buf.writeIntLE(0);
/*  700 */     int itemEntityOffsetSlot = buf.writerIndex();
/*  701 */     buf.writeIntLE(0);
/*  702 */     int setOffsetSlot = buf.writerIndex();
/*  703 */     buf.writeIntLE(0);
/*  704 */     int categoriesOffsetSlot = buf.writerIndex();
/*  705 */     buf.writeIntLE(0);
/*  706 */     int particlesOffsetSlot = buf.writerIndex();
/*  707 */     buf.writeIntLE(0);
/*  708 */     int firstPersonParticlesOffsetSlot = buf.writerIndex();
/*  709 */     buf.writeIntLE(0);
/*  710 */     int trailsOffsetSlot = buf.writerIndex();
/*  711 */     buf.writeIntLE(0);
/*  712 */     int interactionsOffsetSlot = buf.writerIndex();
/*  713 */     buf.writeIntLE(0);
/*  714 */     int interactionVarsOffsetSlot = buf.writerIndex();
/*  715 */     buf.writeIntLE(0);
/*  716 */     int interactionConfigOffsetSlot = buf.writerIndex();
/*  717 */     buf.writeIntLE(0);
/*  718 */     int droppedItemAnimationOffsetSlot = buf.writerIndex();
/*  719 */     buf.writeIntLE(0);
/*  720 */     int tagIndexesOffsetSlot = buf.writerIndex();
/*  721 */     buf.writeIntLE(0);
/*  722 */     int itemAppearanceConditionsOffsetSlot = buf.writerIndex();
/*  723 */     buf.writeIntLE(0);
/*  724 */     int displayEntityStatsHUDOffsetSlot = buf.writerIndex();
/*  725 */     buf.writeIntLE(0);
/*      */     
/*  727 */     int varBlockStart = buf.writerIndex();
/*  728 */     if (this.id != null) {
/*  729 */       buf.setIntLE(idOffsetSlot, buf.writerIndex() - varBlockStart);
/*  730 */       PacketIO.writeVarString(buf, this.id, 4096000);
/*      */     } else {
/*  732 */       buf.setIntLE(idOffsetSlot, -1);
/*      */     } 
/*  734 */     if (this.model != null) {
/*  735 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/*  736 */       PacketIO.writeVarString(buf, this.model, 4096000);
/*      */     } else {
/*  738 */       buf.setIntLE(modelOffsetSlot, -1);
/*      */     } 
/*  740 */     if (this.texture != null) {
/*  741 */       buf.setIntLE(textureOffsetSlot, buf.writerIndex() - varBlockStart);
/*  742 */       PacketIO.writeVarString(buf, this.texture, 4096000);
/*      */     } else {
/*  744 */       buf.setIntLE(textureOffsetSlot, -1);
/*      */     } 
/*  746 */     if (this.animation != null) {
/*  747 */       buf.setIntLE(animationOffsetSlot, buf.writerIndex() - varBlockStart);
/*  748 */       PacketIO.writeVarString(buf, this.animation, 4096000);
/*      */     } else {
/*  750 */       buf.setIntLE(animationOffsetSlot, -1);
/*      */     } 
/*  752 */     if (this.playerAnimationsId != null) {
/*  753 */       buf.setIntLE(playerAnimationsIdOffsetSlot, buf.writerIndex() - varBlockStart);
/*  754 */       PacketIO.writeVarString(buf, this.playerAnimationsId, 4096000);
/*      */     } else {
/*  756 */       buf.setIntLE(playerAnimationsIdOffsetSlot, -1);
/*      */     } 
/*  758 */     if (this.icon != null) {
/*  759 */       buf.setIntLE(iconOffsetSlot, buf.writerIndex() - varBlockStart);
/*  760 */       PacketIO.writeVarString(buf, this.icon, 4096000);
/*      */     } else {
/*  762 */       buf.setIntLE(iconOffsetSlot, -1);
/*      */     } 
/*  764 */     if (this.translationProperties != null) {
/*  765 */       buf.setIntLE(translationPropertiesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  766 */       this.translationProperties.serialize(buf);
/*      */     } else {
/*  768 */       buf.setIntLE(translationPropertiesOffsetSlot, -1);
/*      */     } 
/*  770 */     if (this.resourceTypes != null) {
/*  771 */       buf.setIntLE(resourceTypesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  772 */       if (this.resourceTypes.length > 4096000) throw ProtocolException.arrayTooLong("ResourceTypes", this.resourceTypes.length, 4096000);  VarInt.write(buf, this.resourceTypes.length); for (ItemResourceType item : this.resourceTypes) item.serialize(buf); 
/*      */     } else {
/*  774 */       buf.setIntLE(resourceTypesOffsetSlot, -1);
/*      */     } 
/*  776 */     if (this.tool != null) {
/*  777 */       buf.setIntLE(toolOffsetSlot, buf.writerIndex() - varBlockStart);
/*  778 */       this.tool.serialize(buf);
/*      */     } else {
/*  780 */       buf.setIntLE(toolOffsetSlot, -1);
/*      */     } 
/*  782 */     if (this.weapon != null) {
/*  783 */       buf.setIntLE(weaponOffsetSlot, buf.writerIndex() - varBlockStart);
/*  784 */       this.weapon.serialize(buf);
/*      */     } else {
/*  786 */       buf.setIntLE(weaponOffsetSlot, -1);
/*      */     } 
/*  788 */     if (this.armor != null) {
/*  789 */       buf.setIntLE(armorOffsetSlot, buf.writerIndex() - varBlockStart);
/*  790 */       this.armor.serialize(buf);
/*      */     } else {
/*  792 */       buf.setIntLE(armorOffsetSlot, -1);
/*      */     } 
/*  794 */     if (this.utility != null) {
/*  795 */       buf.setIntLE(utilityOffsetSlot, buf.writerIndex() - varBlockStart);
/*  796 */       this.utility.serialize(buf);
/*      */     } else {
/*  798 */       buf.setIntLE(utilityOffsetSlot, -1);
/*      */     } 
/*  800 */     if (this.builderToolData != null) {
/*  801 */       buf.setIntLE(builderToolDataOffsetSlot, buf.writerIndex() - varBlockStart);
/*  802 */       this.builderToolData.serialize(buf);
/*      */     } else {
/*  804 */       buf.setIntLE(builderToolDataOffsetSlot, -1);
/*      */     } 
/*  806 */     if (this.itemEntity != null) {
/*  807 */       buf.setIntLE(itemEntityOffsetSlot, buf.writerIndex() - varBlockStart);
/*  808 */       this.itemEntity.serialize(buf);
/*      */     } else {
/*  810 */       buf.setIntLE(itemEntityOffsetSlot, -1);
/*      */     } 
/*  812 */     if (this.set != null) {
/*  813 */       buf.setIntLE(setOffsetSlot, buf.writerIndex() - varBlockStart);
/*  814 */       PacketIO.writeVarString(buf, this.set, 4096000);
/*      */     } else {
/*  816 */       buf.setIntLE(setOffsetSlot, -1);
/*      */     } 
/*  818 */     if (this.categories != null) {
/*  819 */       buf.setIntLE(categoriesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  820 */       if (this.categories.length > 4096000) throw ProtocolException.arrayTooLong("Categories", this.categories.length, 4096000);  VarInt.write(buf, this.categories.length); for (String item : this.categories) PacketIO.writeVarString(buf, item, 4096000); 
/*      */     } else {
/*  822 */       buf.setIntLE(categoriesOffsetSlot, -1);
/*      */     } 
/*  824 */     if (this.particles != null) {
/*  825 */       buf.setIntLE(particlesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  826 */       if (this.particles.length > 4096000) throw ProtocolException.arrayTooLong("Particles", this.particles.length, 4096000);  VarInt.write(buf, this.particles.length); for (ModelParticle item : this.particles) item.serialize(buf); 
/*      */     } else {
/*  828 */       buf.setIntLE(particlesOffsetSlot, -1);
/*      */     } 
/*  830 */     if (this.firstPersonParticles != null) {
/*  831 */       buf.setIntLE(firstPersonParticlesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  832 */       if (this.firstPersonParticles.length > 4096000) throw ProtocolException.arrayTooLong("FirstPersonParticles", this.firstPersonParticles.length, 4096000);  VarInt.write(buf, this.firstPersonParticles.length); for (ModelParticle item : this.firstPersonParticles) item.serialize(buf); 
/*      */     } else {
/*  834 */       buf.setIntLE(firstPersonParticlesOffsetSlot, -1);
/*      */     } 
/*  836 */     if (this.trails != null) {
/*  837 */       buf.setIntLE(trailsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  838 */       if (this.trails.length > 4096000) throw ProtocolException.arrayTooLong("Trails", this.trails.length, 4096000);  VarInt.write(buf, this.trails.length); for (ModelTrail item : this.trails) item.serialize(buf); 
/*      */     } else {
/*  840 */       buf.setIntLE(trailsOffsetSlot, -1);
/*      */     } 
/*  842 */     if (this.interactions != null)
/*  843 */     { buf.setIntLE(interactionsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  844 */       if (this.interactions.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", this.interactions.size(), 4096000);  VarInt.write(buf, this.interactions.size()); for (Map.Entry<InteractionType, Integer> e : this.interactions.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*      */        }
/*  846 */     else { buf.setIntLE(interactionsOffsetSlot, -1); }
/*      */     
/*  848 */     if (this.interactionVars != null)
/*  849 */     { buf.setIntLE(interactionVarsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  850 */       if (this.interactionVars.size() > 4096000) throw ProtocolException.dictionaryTooLarge("InteractionVars", this.interactionVars.size(), 4096000);  VarInt.write(buf, this.interactionVars.size()); for (Map.Entry<String, Integer> e : this.interactionVars.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*      */        }
/*  852 */     else { buf.setIntLE(interactionVarsOffsetSlot, -1); }
/*      */     
/*  854 */     if (this.interactionConfig != null) {
/*  855 */       buf.setIntLE(interactionConfigOffsetSlot, buf.writerIndex() - varBlockStart);
/*  856 */       this.interactionConfig.serialize(buf);
/*      */     } else {
/*  858 */       buf.setIntLE(interactionConfigOffsetSlot, -1);
/*      */     } 
/*  860 */     if (this.droppedItemAnimation != null) {
/*  861 */       buf.setIntLE(droppedItemAnimationOffsetSlot, buf.writerIndex() - varBlockStart);
/*  862 */       PacketIO.writeVarString(buf, this.droppedItemAnimation, 4096000);
/*      */     } else {
/*  864 */       buf.setIntLE(droppedItemAnimationOffsetSlot, -1);
/*      */     } 
/*  866 */     if (this.tagIndexes != null) {
/*  867 */       buf.setIntLE(tagIndexesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  868 */       if (this.tagIndexes.length > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", this.tagIndexes.length, 4096000);  VarInt.write(buf, this.tagIndexes.length); for (int item : this.tagIndexes) buf.writeIntLE(item); 
/*      */     } else {
/*  870 */       buf.setIntLE(tagIndexesOffsetSlot, -1);
/*      */     } 
/*  872 */     if (this.itemAppearanceConditions != null)
/*  873 */     { buf.setIntLE(itemAppearanceConditionsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  874 */       if (this.itemAppearanceConditions.size() > 4096000) throw ProtocolException.dictionaryTooLarge("ItemAppearanceConditions", this.itemAppearanceConditions.size(), 4096000);  VarInt.write(buf, this.itemAppearanceConditions.size()); for (Map.Entry<Integer, ItemAppearanceCondition[]> e : this.itemAppearanceConditions.entrySet()) { buf.writeIntLE(((Integer)e.getKey()).intValue()); VarInt.write(buf, ((ItemAppearanceCondition[])e.getValue()).length); for (ItemAppearanceCondition arrItem : (ItemAppearanceCondition[])e.getValue()) arrItem.serialize(buf);  }
/*      */        }
/*  876 */     else { buf.setIntLE(itemAppearanceConditionsOffsetSlot, -1); }
/*      */     
/*  878 */     if (this.displayEntityStatsHUD != null) {
/*  879 */       buf.setIntLE(displayEntityStatsHUDOffsetSlot, buf.writerIndex() - varBlockStart);
/*  880 */       if (this.displayEntityStatsHUD.length > 4096000) throw ProtocolException.arrayTooLong("DisplayEntityStatsHUD", this.displayEntityStatsHUD.length, 4096000);  VarInt.write(buf, this.displayEntityStatsHUD.length); for (int item : this.displayEntityStatsHUD) buf.writeIntLE(item); 
/*      */     } else {
/*  882 */       buf.setIntLE(displayEntityStatsHUDOffsetSlot, -1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeSize() {
/*  888 */     int size = 251;
/*  889 */     if (this.id != null) size += PacketIO.stringSize(this.id); 
/*  890 */     if (this.model != null) size += PacketIO.stringSize(this.model); 
/*  891 */     if (this.texture != null) size += PacketIO.stringSize(this.texture); 
/*  892 */     if (this.animation != null) size += PacketIO.stringSize(this.animation); 
/*  893 */     if (this.playerAnimationsId != null) size += PacketIO.stringSize(this.playerAnimationsId); 
/*  894 */     if (this.icon != null) size += PacketIO.stringSize(this.icon); 
/*  895 */     if (this.translationProperties != null) size += this.translationProperties.computeSize(); 
/*  896 */     if (this.resourceTypes != null) {
/*  897 */       int resourceTypesSize = 0;
/*  898 */       for (ItemResourceType elem : this.resourceTypes) resourceTypesSize += elem.computeSize(); 
/*  899 */       size += VarInt.size(this.resourceTypes.length) + resourceTypesSize;
/*      */     } 
/*  901 */     if (this.tool != null) size += this.tool.computeSize(); 
/*  902 */     if (this.weapon != null) size += this.weapon.computeSize(); 
/*  903 */     if (this.armor != null) size += this.armor.computeSize(); 
/*  904 */     if (this.utility != null) size += this.utility.computeSize(); 
/*  905 */     if (this.builderToolData != null) size += this.builderToolData.computeSize(); 
/*  906 */     if (this.itemEntity != null) size += this.itemEntity.computeSize(); 
/*  907 */     if (this.set != null) size += PacketIO.stringSize(this.set); 
/*  908 */     if (this.categories != null) {
/*  909 */       int categoriesSize = 0;
/*  910 */       for (String elem : this.categories) categoriesSize += PacketIO.stringSize(elem); 
/*  911 */       size += VarInt.size(this.categories.length) + categoriesSize;
/*      */     } 
/*  913 */     if (this.particles != null) {
/*  914 */       int particlesSize = 0;
/*  915 */       for (ModelParticle elem : this.particles) particlesSize += elem.computeSize(); 
/*  916 */       size += VarInt.size(this.particles.length) + particlesSize;
/*      */     } 
/*  918 */     if (this.firstPersonParticles != null) {
/*  919 */       int firstPersonParticlesSize = 0;
/*  920 */       for (ModelParticle elem : this.firstPersonParticles) firstPersonParticlesSize += elem.computeSize(); 
/*  921 */       size += VarInt.size(this.firstPersonParticles.length) + firstPersonParticlesSize;
/*      */     } 
/*  923 */     if (this.trails != null) {
/*  924 */       int trailsSize = 0;
/*  925 */       for (ModelTrail elem : this.trails) trailsSize += elem.computeSize(); 
/*  926 */       size += VarInt.size(this.trails.length) + trailsSize;
/*      */     } 
/*  928 */     if (this.interactions != null) size += VarInt.size(this.interactions.size()) + this.interactions.size() * 5; 
/*  929 */     if (this.interactionVars != null) {
/*  930 */       int interactionVarsSize = 0;
/*  931 */       for (Map.Entry<String, Integer> kvp : this.interactionVars.entrySet()) interactionVarsSize += PacketIO.stringSize(kvp.getKey()) + 4; 
/*  932 */       size += VarInt.size(this.interactionVars.size()) + interactionVarsSize;
/*      */     } 
/*  934 */     if (this.interactionConfig != null) size += this.interactionConfig.computeSize(); 
/*  935 */     if (this.droppedItemAnimation != null) size += PacketIO.stringSize(this.droppedItemAnimation); 
/*  936 */     if (this.tagIndexes != null) size += VarInt.size(this.tagIndexes.length) + this.tagIndexes.length * 4; 
/*  937 */     if (this.itemAppearanceConditions != null) {
/*  938 */       int itemAppearanceConditionsSize = 0;
/*  939 */       for (Map.Entry<Integer, ItemAppearanceCondition[]> kvp : this.itemAppearanceConditions.entrySet()) itemAppearanceConditionsSize += 4 + VarInt.size(((ItemAppearanceCondition[])kvp.getValue()).length) + Arrays.<ItemAppearanceCondition>stream(kvp.getValue()).mapToInt(inner -> inner.computeSize()).sum(); 
/*  940 */       size += VarInt.size(this.itemAppearanceConditions.size()) + itemAppearanceConditionsSize;
/*      */     } 
/*  942 */     if (this.displayEntityStatsHUD != null) size += VarInt.size(this.displayEntityStatsHUD.length) + this.displayEntityStatsHUD.length * 4;
/*      */     
/*  944 */     return size;
/*      */   }
/*      */   
/*      */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  948 */     if (buffer.readableBytes() - offset < 251) {
/*  949 */       return ValidationResult.error("Buffer too small: expected at least 251 bytes");
/*      */     }
/*      */     
/*  952 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 4);
/*      */     
/*  954 */     if ((nullBits[0] & 0x1) != 0) {
/*  955 */       int idOffset = buffer.getIntLE(offset + 147);
/*  956 */       if (idOffset < 0) {
/*  957 */         return ValidationResult.error("Invalid offset for Id");
/*      */       }
/*  959 */       int pos = offset + 251 + idOffset;
/*  960 */       if (pos >= buffer.writerIndex()) {
/*  961 */         return ValidationResult.error("Offset out of bounds for Id");
/*      */       }
/*  963 */       int idLen = VarInt.peek(buffer, pos);
/*  964 */       if (idLen < 0) {
/*  965 */         return ValidationResult.error("Invalid string length for Id");
/*      */       }
/*  967 */       if (idLen > 4096000) {
/*  968 */         return ValidationResult.error("Id exceeds max length 4096000");
/*      */       }
/*  970 */       pos += VarInt.length(buffer, pos);
/*  971 */       pos += idLen;
/*  972 */       if (pos > buffer.writerIndex()) {
/*  973 */         return ValidationResult.error("Buffer overflow reading Id");
/*      */       }
/*      */     } 
/*      */     
/*  977 */     if ((nullBits[0] & 0x2) != 0) {
/*  978 */       int modelOffset = buffer.getIntLE(offset + 151);
/*  979 */       if (modelOffset < 0) {
/*  980 */         return ValidationResult.error("Invalid offset for Model");
/*      */       }
/*  982 */       int pos = offset + 251 + modelOffset;
/*  983 */       if (pos >= buffer.writerIndex()) {
/*  984 */         return ValidationResult.error("Offset out of bounds for Model");
/*      */       }
/*  986 */       int modelLen = VarInt.peek(buffer, pos);
/*  987 */       if (modelLen < 0) {
/*  988 */         return ValidationResult.error("Invalid string length for Model");
/*      */       }
/*  990 */       if (modelLen > 4096000) {
/*  991 */         return ValidationResult.error("Model exceeds max length 4096000");
/*      */       }
/*  993 */       pos += VarInt.length(buffer, pos);
/*  994 */       pos += modelLen;
/*  995 */       if (pos > buffer.writerIndex()) {
/*  996 */         return ValidationResult.error("Buffer overflow reading Model");
/*      */       }
/*      */     } 
/*      */     
/* 1000 */     if ((nullBits[0] & 0x4) != 0) {
/* 1001 */       int textureOffset = buffer.getIntLE(offset + 155);
/* 1002 */       if (textureOffset < 0) {
/* 1003 */         return ValidationResult.error("Invalid offset for Texture");
/*      */       }
/* 1005 */       int pos = offset + 251 + textureOffset;
/* 1006 */       if (pos >= buffer.writerIndex()) {
/* 1007 */         return ValidationResult.error("Offset out of bounds for Texture");
/*      */       }
/* 1009 */       int textureLen = VarInt.peek(buffer, pos);
/* 1010 */       if (textureLen < 0) {
/* 1011 */         return ValidationResult.error("Invalid string length for Texture");
/*      */       }
/* 1013 */       if (textureLen > 4096000) {
/* 1014 */         return ValidationResult.error("Texture exceeds max length 4096000");
/*      */       }
/* 1016 */       pos += VarInt.length(buffer, pos);
/* 1017 */       pos += textureLen;
/* 1018 */       if (pos > buffer.writerIndex()) {
/* 1019 */         return ValidationResult.error("Buffer overflow reading Texture");
/*      */       }
/*      */     } 
/*      */     
/* 1023 */     if ((nullBits[0] & 0x8) != 0) {
/* 1024 */       int animationOffset = buffer.getIntLE(offset + 159);
/* 1025 */       if (animationOffset < 0) {
/* 1026 */         return ValidationResult.error("Invalid offset for Animation");
/*      */       }
/* 1028 */       int pos = offset + 251 + animationOffset;
/* 1029 */       if (pos >= buffer.writerIndex()) {
/* 1030 */         return ValidationResult.error("Offset out of bounds for Animation");
/*      */       }
/* 1032 */       int animationLen = VarInt.peek(buffer, pos);
/* 1033 */       if (animationLen < 0) {
/* 1034 */         return ValidationResult.error("Invalid string length for Animation");
/*      */       }
/* 1036 */       if (animationLen > 4096000) {
/* 1037 */         return ValidationResult.error("Animation exceeds max length 4096000");
/*      */       }
/* 1039 */       pos += VarInt.length(buffer, pos);
/* 1040 */       pos += animationLen;
/* 1041 */       if (pos > buffer.writerIndex()) {
/* 1042 */         return ValidationResult.error("Buffer overflow reading Animation");
/*      */       }
/*      */     } 
/*      */     
/* 1046 */     if ((nullBits[0] & 0x10) != 0) {
/* 1047 */       int playerAnimationsIdOffset = buffer.getIntLE(offset + 163);
/* 1048 */       if (playerAnimationsIdOffset < 0) {
/* 1049 */         return ValidationResult.error("Invalid offset for PlayerAnimationsId");
/*      */       }
/* 1051 */       int pos = offset + 251 + playerAnimationsIdOffset;
/* 1052 */       if (pos >= buffer.writerIndex()) {
/* 1053 */         return ValidationResult.error("Offset out of bounds for PlayerAnimationsId");
/*      */       }
/* 1055 */       int playerAnimationsIdLen = VarInt.peek(buffer, pos);
/* 1056 */       if (playerAnimationsIdLen < 0) {
/* 1057 */         return ValidationResult.error("Invalid string length for PlayerAnimationsId");
/*      */       }
/* 1059 */       if (playerAnimationsIdLen > 4096000) {
/* 1060 */         return ValidationResult.error("PlayerAnimationsId exceeds max length 4096000");
/*      */       }
/* 1062 */       pos += VarInt.length(buffer, pos);
/* 1063 */       pos += playerAnimationsIdLen;
/* 1064 */       if (pos > buffer.writerIndex()) {
/* 1065 */         return ValidationResult.error("Buffer overflow reading PlayerAnimationsId");
/*      */       }
/*      */     } 
/*      */     
/* 1069 */     if ((nullBits[0] & 0x20) != 0) {
/* 1070 */       int iconOffset = buffer.getIntLE(offset + 167);
/* 1071 */       if (iconOffset < 0) {
/* 1072 */         return ValidationResult.error("Invalid offset for Icon");
/*      */       }
/* 1074 */       int pos = offset + 251 + iconOffset;
/* 1075 */       if (pos >= buffer.writerIndex()) {
/* 1076 */         return ValidationResult.error("Offset out of bounds for Icon");
/*      */       }
/* 1078 */       int iconLen = VarInt.peek(buffer, pos);
/* 1079 */       if (iconLen < 0) {
/* 1080 */         return ValidationResult.error("Invalid string length for Icon");
/*      */       }
/* 1082 */       if (iconLen > 4096000) {
/* 1083 */         return ValidationResult.error("Icon exceeds max length 4096000");
/*      */       }
/* 1085 */       pos += VarInt.length(buffer, pos);
/* 1086 */       pos += iconLen;
/* 1087 */       if (pos > buffer.writerIndex()) {
/* 1088 */         return ValidationResult.error("Buffer overflow reading Icon");
/*      */       }
/*      */     } 
/*      */     
/* 1092 */     if ((nullBits[0] & 0x80) != 0) {
/* 1093 */       int translationPropertiesOffset = buffer.getIntLE(offset + 171);
/* 1094 */       if (translationPropertiesOffset < 0) {
/* 1095 */         return ValidationResult.error("Invalid offset for TranslationProperties");
/*      */       }
/* 1097 */       int pos = offset + 251 + translationPropertiesOffset;
/* 1098 */       if (pos >= buffer.writerIndex()) {
/* 1099 */         return ValidationResult.error("Offset out of bounds for TranslationProperties");
/*      */       }
/* 1101 */       ValidationResult translationPropertiesResult = ItemTranslationProperties.validateStructure(buffer, pos);
/* 1102 */       if (!translationPropertiesResult.isValid()) {
/* 1103 */         return ValidationResult.error("Invalid TranslationProperties: " + translationPropertiesResult.error());
/*      */       }
/* 1105 */       pos += ItemTranslationProperties.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1108 */     if ((nullBits[1] & 0x1) != 0) {
/* 1109 */       int resourceTypesOffset = buffer.getIntLE(offset + 175);
/* 1110 */       if (resourceTypesOffset < 0) {
/* 1111 */         return ValidationResult.error("Invalid offset for ResourceTypes");
/*      */       }
/* 1113 */       int pos = offset + 251 + resourceTypesOffset;
/* 1114 */       if (pos >= buffer.writerIndex()) {
/* 1115 */         return ValidationResult.error("Offset out of bounds for ResourceTypes");
/*      */       }
/* 1117 */       int resourceTypesCount = VarInt.peek(buffer, pos);
/* 1118 */       if (resourceTypesCount < 0) {
/* 1119 */         return ValidationResult.error("Invalid array count for ResourceTypes");
/*      */       }
/* 1121 */       if (resourceTypesCount > 4096000) {
/* 1122 */         return ValidationResult.error("ResourceTypes exceeds max length 4096000");
/*      */       }
/* 1124 */       pos += VarInt.length(buffer, pos);
/* 1125 */       for (int i = 0; i < resourceTypesCount; i++) {
/* 1126 */         ValidationResult structResult = ItemResourceType.validateStructure(buffer, pos);
/* 1127 */         if (!structResult.isValid()) {
/* 1128 */           return ValidationResult.error("Invalid ItemResourceType in ResourceTypes[" + i + "]: " + structResult.error());
/*      */         }
/* 1130 */         pos += ItemResourceType.computeBytesConsumed(buffer, pos);
/*      */       } 
/*      */     } 
/*      */     
/* 1134 */     if ((nullBits[1] & 0x2) != 0) {
/* 1135 */       int toolOffset = buffer.getIntLE(offset + 179);
/* 1136 */       if (toolOffset < 0) {
/* 1137 */         return ValidationResult.error("Invalid offset for Tool");
/*      */       }
/* 1139 */       int pos = offset + 251 + toolOffset;
/* 1140 */       if (pos >= buffer.writerIndex()) {
/* 1141 */         return ValidationResult.error("Offset out of bounds for Tool");
/*      */       }
/* 1143 */       ValidationResult toolResult = ItemTool.validateStructure(buffer, pos);
/* 1144 */       if (!toolResult.isValid()) {
/* 1145 */         return ValidationResult.error("Invalid Tool: " + toolResult.error());
/*      */       }
/* 1147 */       pos += ItemTool.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1150 */     if ((nullBits[1] & 0x4) != 0) {
/* 1151 */       int weaponOffset = buffer.getIntLE(offset + 183);
/* 1152 */       if (weaponOffset < 0) {
/* 1153 */         return ValidationResult.error("Invalid offset for Weapon");
/*      */       }
/* 1155 */       int pos = offset + 251 + weaponOffset;
/* 1156 */       if (pos >= buffer.writerIndex()) {
/* 1157 */         return ValidationResult.error("Offset out of bounds for Weapon");
/*      */       }
/* 1159 */       ValidationResult weaponResult = ItemWeapon.validateStructure(buffer, pos);
/* 1160 */       if (!weaponResult.isValid()) {
/* 1161 */         return ValidationResult.error("Invalid Weapon: " + weaponResult.error());
/*      */       }
/* 1163 */       pos += ItemWeapon.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1166 */     if ((nullBits[1] & 0x8) != 0) {
/* 1167 */       int armorOffset = buffer.getIntLE(offset + 187);
/* 1168 */       if (armorOffset < 0) {
/* 1169 */         return ValidationResult.error("Invalid offset for Armor");
/*      */       }
/* 1171 */       int pos = offset + 251 + armorOffset;
/* 1172 */       if (pos >= buffer.writerIndex()) {
/* 1173 */         return ValidationResult.error("Offset out of bounds for Armor");
/*      */       }
/* 1175 */       ValidationResult armorResult = ItemArmor.validateStructure(buffer, pos);
/* 1176 */       if (!armorResult.isValid()) {
/* 1177 */         return ValidationResult.error("Invalid Armor: " + armorResult.error());
/*      */       }
/* 1179 */       pos += ItemArmor.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1182 */     if ((nullBits[1] & 0x20) != 0) {
/* 1183 */       int utilityOffset = buffer.getIntLE(offset + 191);
/* 1184 */       if (utilityOffset < 0) {
/* 1185 */         return ValidationResult.error("Invalid offset for Utility");
/*      */       }
/* 1187 */       int pos = offset + 251 + utilityOffset;
/* 1188 */       if (pos >= buffer.writerIndex()) {
/* 1189 */         return ValidationResult.error("Offset out of bounds for Utility");
/*      */       }
/* 1191 */       ValidationResult utilityResult = ItemUtility.validateStructure(buffer, pos);
/* 1192 */       if (!utilityResult.isValid()) {
/* 1193 */         return ValidationResult.error("Invalid Utility: " + utilityResult.error());
/*      */       }
/* 1195 */       pos += ItemUtility.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1198 */     if ((nullBits[1] & 0x80) != 0) {
/* 1199 */       int builderToolDataOffset = buffer.getIntLE(offset + 195);
/* 1200 */       if (builderToolDataOffset < 0) {
/* 1201 */         return ValidationResult.error("Invalid offset for BuilderToolData");
/*      */       }
/* 1203 */       int pos = offset + 251 + builderToolDataOffset;
/* 1204 */       if (pos >= buffer.writerIndex()) {
/* 1205 */         return ValidationResult.error("Offset out of bounds for BuilderToolData");
/*      */       }
/* 1207 */       ValidationResult builderToolDataResult = ItemBuilderToolData.validateStructure(buffer, pos);
/* 1208 */       if (!builderToolDataResult.isValid()) {
/* 1209 */         return ValidationResult.error("Invalid BuilderToolData: " + builderToolDataResult.error());
/*      */       }
/* 1211 */       pos += ItemBuilderToolData.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1214 */     if ((nullBits[2] & 0x1) != 0) {
/* 1215 */       int itemEntityOffset = buffer.getIntLE(offset + 199);
/* 1216 */       if (itemEntityOffset < 0) {
/* 1217 */         return ValidationResult.error("Invalid offset for ItemEntity");
/*      */       }
/* 1219 */       int pos = offset + 251 + itemEntityOffset;
/* 1220 */       if (pos >= buffer.writerIndex()) {
/* 1221 */         return ValidationResult.error("Offset out of bounds for ItemEntity");
/*      */       }
/* 1223 */       ValidationResult itemEntityResult = ItemEntityConfig.validateStructure(buffer, pos);
/* 1224 */       if (!itemEntityResult.isValid()) {
/* 1225 */         return ValidationResult.error("Invalid ItemEntity: " + itemEntityResult.error());
/*      */       }
/* 1227 */       pos += ItemEntityConfig.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1230 */     if ((nullBits[2] & 0x2) != 0) {
/* 1231 */       int setOffset = buffer.getIntLE(offset + 203);
/* 1232 */       if (setOffset < 0) {
/* 1233 */         return ValidationResult.error("Invalid offset for Set");
/*      */       }
/* 1235 */       int pos = offset + 251 + setOffset;
/* 1236 */       if (pos >= buffer.writerIndex()) {
/* 1237 */         return ValidationResult.error("Offset out of bounds for Set");
/*      */       }
/* 1239 */       int setLen = VarInt.peek(buffer, pos);
/* 1240 */       if (setLen < 0) {
/* 1241 */         return ValidationResult.error("Invalid string length for Set");
/*      */       }
/* 1243 */       if (setLen > 4096000) {
/* 1244 */         return ValidationResult.error("Set exceeds max length 4096000");
/*      */       }
/* 1246 */       pos += VarInt.length(buffer, pos);
/* 1247 */       pos += setLen;
/* 1248 */       if (pos > buffer.writerIndex()) {
/* 1249 */         return ValidationResult.error("Buffer overflow reading Set");
/*      */       }
/*      */     } 
/*      */     
/* 1253 */     if ((nullBits[2] & 0x4) != 0) {
/* 1254 */       int categoriesOffset = buffer.getIntLE(offset + 207);
/* 1255 */       if (categoriesOffset < 0) {
/* 1256 */         return ValidationResult.error("Invalid offset for Categories");
/*      */       }
/* 1258 */       int pos = offset + 251 + categoriesOffset;
/* 1259 */       if (pos >= buffer.writerIndex()) {
/* 1260 */         return ValidationResult.error("Offset out of bounds for Categories");
/*      */       }
/* 1262 */       int categoriesCount = VarInt.peek(buffer, pos);
/* 1263 */       if (categoriesCount < 0) {
/* 1264 */         return ValidationResult.error("Invalid array count for Categories");
/*      */       }
/* 1266 */       if (categoriesCount > 4096000) {
/* 1267 */         return ValidationResult.error("Categories exceeds max length 4096000");
/*      */       }
/* 1269 */       pos += VarInt.length(buffer, pos);
/* 1270 */       for (int i = 0; i < categoriesCount; i++) {
/* 1271 */         int strLen = VarInt.peek(buffer, pos);
/* 1272 */         if (strLen < 0) {
/* 1273 */           return ValidationResult.error("Invalid string length in Categories");
/*      */         }
/* 1275 */         pos += VarInt.length(buffer, pos);
/* 1276 */         pos += strLen;
/* 1277 */         if (pos > buffer.writerIndex()) {
/* 1278 */           return ValidationResult.error("Buffer overflow reading string in Categories");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1283 */     if ((nullBits[2] & 0x8) != 0) {
/* 1284 */       int particlesOffset = buffer.getIntLE(offset + 211);
/* 1285 */       if (particlesOffset < 0) {
/* 1286 */         return ValidationResult.error("Invalid offset for Particles");
/*      */       }
/* 1288 */       int pos = offset + 251 + particlesOffset;
/* 1289 */       if (pos >= buffer.writerIndex()) {
/* 1290 */         return ValidationResult.error("Offset out of bounds for Particles");
/*      */       }
/* 1292 */       int particlesCount = VarInt.peek(buffer, pos);
/* 1293 */       if (particlesCount < 0) {
/* 1294 */         return ValidationResult.error("Invalid array count for Particles");
/*      */       }
/* 1296 */       if (particlesCount > 4096000) {
/* 1297 */         return ValidationResult.error("Particles exceeds max length 4096000");
/*      */       }
/* 1299 */       pos += VarInt.length(buffer, pos);
/* 1300 */       for (int i = 0; i < particlesCount; i++) {
/* 1301 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 1302 */         if (!structResult.isValid()) {
/* 1303 */           return ValidationResult.error("Invalid ModelParticle in Particles[" + i + "]: " + structResult.error());
/*      */         }
/* 1305 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*      */       } 
/*      */     } 
/*      */     
/* 1309 */     if ((nullBits[2] & 0x10) != 0) {
/* 1310 */       int firstPersonParticlesOffset = buffer.getIntLE(offset + 215);
/* 1311 */       if (firstPersonParticlesOffset < 0) {
/* 1312 */         return ValidationResult.error("Invalid offset for FirstPersonParticles");
/*      */       }
/* 1314 */       int pos = offset + 251 + firstPersonParticlesOffset;
/* 1315 */       if (pos >= buffer.writerIndex()) {
/* 1316 */         return ValidationResult.error("Offset out of bounds for FirstPersonParticles");
/*      */       }
/* 1318 */       int firstPersonParticlesCount = VarInt.peek(buffer, pos);
/* 1319 */       if (firstPersonParticlesCount < 0) {
/* 1320 */         return ValidationResult.error("Invalid array count for FirstPersonParticles");
/*      */       }
/* 1322 */       if (firstPersonParticlesCount > 4096000) {
/* 1323 */         return ValidationResult.error("FirstPersonParticles exceeds max length 4096000");
/*      */       }
/* 1325 */       pos += VarInt.length(buffer, pos);
/* 1326 */       for (int i = 0; i < firstPersonParticlesCount; i++) {
/* 1327 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 1328 */         if (!structResult.isValid()) {
/* 1329 */           return ValidationResult.error("Invalid ModelParticle in FirstPersonParticles[" + i + "]: " + structResult.error());
/*      */         }
/* 1331 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*      */       } 
/*      */     } 
/*      */     
/* 1335 */     if ((nullBits[2] & 0x20) != 0) {
/* 1336 */       int trailsOffset = buffer.getIntLE(offset + 219);
/* 1337 */       if (trailsOffset < 0) {
/* 1338 */         return ValidationResult.error("Invalid offset for Trails");
/*      */       }
/* 1340 */       int pos = offset + 251 + trailsOffset;
/* 1341 */       if (pos >= buffer.writerIndex()) {
/* 1342 */         return ValidationResult.error("Offset out of bounds for Trails");
/*      */       }
/* 1344 */       int trailsCount = VarInt.peek(buffer, pos);
/* 1345 */       if (trailsCount < 0) {
/* 1346 */         return ValidationResult.error("Invalid array count for Trails");
/*      */       }
/* 1348 */       if (trailsCount > 4096000) {
/* 1349 */         return ValidationResult.error("Trails exceeds max length 4096000");
/*      */       }
/* 1351 */       pos += VarInt.length(buffer, pos);
/* 1352 */       for (int i = 0; i < trailsCount; i++) {
/* 1353 */         ValidationResult structResult = ModelTrail.validateStructure(buffer, pos);
/* 1354 */         if (!structResult.isValid()) {
/* 1355 */           return ValidationResult.error("Invalid ModelTrail in Trails[" + i + "]: " + structResult.error());
/*      */         }
/* 1357 */         pos += ModelTrail.computeBytesConsumed(buffer, pos);
/*      */       } 
/*      */     } 
/*      */     
/* 1361 */     if ((nullBits[2] & 0x80) != 0) {
/* 1362 */       int interactionsOffset = buffer.getIntLE(offset + 223);
/* 1363 */       if (interactionsOffset < 0) {
/* 1364 */         return ValidationResult.error("Invalid offset for Interactions");
/*      */       }
/* 1366 */       int pos = offset + 251 + interactionsOffset;
/* 1367 */       if (pos >= buffer.writerIndex()) {
/* 1368 */         return ValidationResult.error("Offset out of bounds for Interactions");
/*      */       }
/* 1370 */       int interactionsCount = VarInt.peek(buffer, pos);
/* 1371 */       if (interactionsCount < 0) {
/* 1372 */         return ValidationResult.error("Invalid dictionary count for Interactions");
/*      */       }
/* 1374 */       if (interactionsCount > 4096000) {
/* 1375 */         return ValidationResult.error("Interactions exceeds max length 4096000");
/*      */       }
/* 1377 */       pos += VarInt.length(buffer, pos);
/* 1378 */       for (int i = 0; i < interactionsCount; i++) {
/* 1379 */         pos++;
/*      */         
/* 1381 */         pos += 4;
/* 1382 */         if (pos > buffer.writerIndex()) {
/* 1383 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1388 */     if ((nullBits[3] & 0x1) != 0) {
/* 1389 */       int interactionVarsOffset = buffer.getIntLE(offset + 227);
/* 1390 */       if (interactionVarsOffset < 0) {
/* 1391 */         return ValidationResult.error("Invalid offset for InteractionVars");
/*      */       }
/* 1393 */       int pos = offset + 251 + interactionVarsOffset;
/* 1394 */       if (pos >= buffer.writerIndex()) {
/* 1395 */         return ValidationResult.error("Offset out of bounds for InteractionVars");
/*      */       }
/* 1397 */       int interactionVarsCount = VarInt.peek(buffer, pos);
/* 1398 */       if (interactionVarsCount < 0) {
/* 1399 */         return ValidationResult.error("Invalid dictionary count for InteractionVars");
/*      */       }
/* 1401 */       if (interactionVarsCount > 4096000) {
/* 1402 */         return ValidationResult.error("InteractionVars exceeds max length 4096000");
/*      */       }
/* 1404 */       pos += VarInt.length(buffer, pos);
/* 1405 */       for (int i = 0; i < interactionVarsCount; i++) {
/* 1406 */         int keyLen = VarInt.peek(buffer, pos);
/* 1407 */         if (keyLen < 0) {
/* 1408 */           return ValidationResult.error("Invalid string length for key");
/*      */         }
/* 1410 */         if (keyLen > 4096000) {
/* 1411 */           return ValidationResult.error("key exceeds max length 4096000");
/*      */         }
/* 1413 */         pos += VarInt.length(buffer, pos);
/* 1414 */         pos += keyLen;
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
/* 1425 */     if ((nullBits[3] & 0x2) != 0) {
/* 1426 */       int interactionConfigOffset = buffer.getIntLE(offset + 231);
/* 1427 */       if (interactionConfigOffset < 0) {
/* 1428 */         return ValidationResult.error("Invalid offset for InteractionConfig");
/*      */       }
/* 1430 */       int pos = offset + 251 + interactionConfigOffset;
/* 1431 */       if (pos >= buffer.writerIndex()) {
/* 1432 */         return ValidationResult.error("Offset out of bounds for InteractionConfig");
/*      */       }
/* 1434 */       ValidationResult interactionConfigResult = InteractionConfiguration.validateStructure(buffer, pos);
/* 1435 */       if (!interactionConfigResult.isValid()) {
/* 1436 */         return ValidationResult.error("Invalid InteractionConfig: " + interactionConfigResult.error());
/*      */       }
/* 1438 */       pos += InteractionConfiguration.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1441 */     if ((nullBits[3] & 0x4) != 0) {
/* 1442 */       int droppedItemAnimationOffset = buffer.getIntLE(offset + 235);
/* 1443 */       if (droppedItemAnimationOffset < 0) {
/* 1444 */         return ValidationResult.error("Invalid offset for DroppedItemAnimation");
/*      */       }
/* 1446 */       int pos = offset + 251 + droppedItemAnimationOffset;
/* 1447 */       if (pos >= buffer.writerIndex()) {
/* 1448 */         return ValidationResult.error("Offset out of bounds for DroppedItemAnimation");
/*      */       }
/* 1450 */       int droppedItemAnimationLen = VarInt.peek(buffer, pos);
/* 1451 */       if (droppedItemAnimationLen < 0) {
/* 1452 */         return ValidationResult.error("Invalid string length for DroppedItemAnimation");
/*      */       }
/* 1454 */       if (droppedItemAnimationLen > 4096000) {
/* 1455 */         return ValidationResult.error("DroppedItemAnimation exceeds max length 4096000");
/*      */       }
/* 1457 */       pos += VarInt.length(buffer, pos);
/* 1458 */       pos += droppedItemAnimationLen;
/* 1459 */       if (pos > buffer.writerIndex()) {
/* 1460 */         return ValidationResult.error("Buffer overflow reading DroppedItemAnimation");
/*      */       }
/*      */     } 
/*      */     
/* 1464 */     if ((nullBits[3] & 0x8) != 0) {
/* 1465 */       int tagIndexesOffset = buffer.getIntLE(offset + 239);
/* 1466 */       if (tagIndexesOffset < 0) {
/* 1467 */         return ValidationResult.error("Invalid offset for TagIndexes");
/*      */       }
/* 1469 */       int pos = offset + 251 + tagIndexesOffset;
/* 1470 */       if (pos >= buffer.writerIndex()) {
/* 1471 */         return ValidationResult.error("Offset out of bounds for TagIndexes");
/*      */       }
/* 1473 */       int tagIndexesCount = VarInt.peek(buffer, pos);
/* 1474 */       if (tagIndexesCount < 0) {
/* 1475 */         return ValidationResult.error("Invalid array count for TagIndexes");
/*      */       }
/* 1477 */       if (tagIndexesCount > 4096000) {
/* 1478 */         return ValidationResult.error("TagIndexes exceeds max length 4096000");
/*      */       }
/* 1480 */       pos += VarInt.length(buffer, pos);
/* 1481 */       pos += tagIndexesCount * 4;
/* 1482 */       if (pos > buffer.writerIndex()) {
/* 1483 */         return ValidationResult.error("Buffer overflow reading TagIndexes");
/*      */       }
/*      */     } 
/*      */     
/* 1487 */     if ((nullBits[3] & 0x10) != 0) {
/* 1488 */       int itemAppearanceConditionsOffset = buffer.getIntLE(offset + 243);
/* 1489 */       if (itemAppearanceConditionsOffset < 0) {
/* 1490 */         return ValidationResult.error("Invalid offset for ItemAppearanceConditions");
/*      */       }
/* 1492 */       int pos = offset + 251 + itemAppearanceConditionsOffset;
/* 1493 */       if (pos >= buffer.writerIndex()) {
/* 1494 */         return ValidationResult.error("Offset out of bounds for ItemAppearanceConditions");
/*      */       }
/* 1496 */       int itemAppearanceConditionsCount = VarInt.peek(buffer, pos);
/* 1497 */       if (itemAppearanceConditionsCount < 0) {
/* 1498 */         return ValidationResult.error("Invalid dictionary count for ItemAppearanceConditions");
/*      */       }
/* 1500 */       if (itemAppearanceConditionsCount > 4096000) {
/* 1501 */         return ValidationResult.error("ItemAppearanceConditions exceeds max length 4096000");
/*      */       }
/* 1503 */       pos += VarInt.length(buffer, pos);
/* 1504 */       for (int i = 0; i < itemAppearanceConditionsCount; i++) {
/* 1505 */         pos += 4;
/* 1506 */         if (pos > buffer.writerIndex()) {
/* 1507 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1509 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 1510 */         if (valueArrCount < 0) {
/* 1511 */           return ValidationResult.error("Invalid array count for value");
/*      */         }
/* 1513 */         pos += VarInt.length(buffer, pos);
/* 1514 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 1515 */           pos += ItemAppearanceCondition.computeBytesConsumed(buffer, pos);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1521 */     if ((nullBits[3] & 0x20) != 0) {
/* 1522 */       int displayEntityStatsHUDOffset = buffer.getIntLE(offset + 247);
/* 1523 */       if (displayEntityStatsHUDOffset < 0) {
/* 1524 */         return ValidationResult.error("Invalid offset for DisplayEntityStatsHUD");
/*      */       }
/* 1526 */       int pos = offset + 251 + displayEntityStatsHUDOffset;
/* 1527 */       if (pos >= buffer.writerIndex()) {
/* 1528 */         return ValidationResult.error("Offset out of bounds for DisplayEntityStatsHUD");
/*      */       }
/* 1530 */       int displayEntityStatsHUDCount = VarInt.peek(buffer, pos);
/* 1531 */       if (displayEntityStatsHUDCount < 0) {
/* 1532 */         return ValidationResult.error("Invalid array count for DisplayEntityStatsHUD");
/*      */       }
/* 1534 */       if (displayEntityStatsHUDCount > 4096000) {
/* 1535 */         return ValidationResult.error("DisplayEntityStatsHUD exceeds max length 4096000");
/*      */       }
/* 1537 */       pos += VarInt.length(buffer, pos);
/* 1538 */       pos += displayEntityStatsHUDCount * 4;
/* 1539 */       if (pos > buffer.writerIndex()) {
/* 1540 */         return ValidationResult.error("Buffer overflow reading DisplayEntityStatsHUD");
/*      */       }
/*      */     } 
/* 1543 */     return ValidationResult.OK;
/*      */   }
/*      */   
/*      */   public ItemBase clone() {
/* 1547 */     ItemBase copy = new ItemBase();
/* 1548 */     copy.id = this.id;
/* 1549 */     copy.model = this.model;
/* 1550 */     copy.scale = this.scale;
/* 1551 */     copy.texture = this.texture;
/* 1552 */     copy.animation = this.animation;
/* 1553 */     copy.playerAnimationsId = this.playerAnimationsId;
/* 1554 */     copy.usePlayerAnimations = this.usePlayerAnimations;
/* 1555 */     copy.maxStack = this.maxStack;
/* 1556 */     copy.reticleIndex = this.reticleIndex;
/* 1557 */     copy.icon = this.icon;
/* 1558 */     copy.iconProperties = (this.iconProperties != null) ? this.iconProperties.clone() : null;
/* 1559 */     copy.translationProperties = (this.translationProperties != null) ? this.translationProperties.clone() : null;
/* 1560 */     copy.itemLevel = this.itemLevel;
/* 1561 */     copy.qualityIndex = this.qualityIndex;
/* 1562 */     copy.resourceTypes = (this.resourceTypes != null) ? (ItemResourceType[])Arrays.<ItemResourceType>stream(this.resourceTypes).map(e -> e.clone()).toArray(x$0 -> new ItemResourceType[x$0]) : null;
/* 1563 */     copy.consumable = this.consumable;
/* 1564 */     copy.variant = this.variant;
/* 1565 */     copy.blockId = this.blockId;
/* 1566 */     copy.tool = (this.tool != null) ? this.tool.clone() : null;
/* 1567 */     copy.weapon = (this.weapon != null) ? this.weapon.clone() : null;
/* 1568 */     copy.armor = (this.armor != null) ? this.armor.clone() : null;
/* 1569 */     copy.gliderConfig = (this.gliderConfig != null) ? this.gliderConfig.clone() : null;
/* 1570 */     copy.utility = (this.utility != null) ? this.utility.clone() : null;
/* 1571 */     copy.blockSelectorTool = (this.blockSelectorTool != null) ? this.blockSelectorTool.clone() : null;
/* 1572 */     copy.builderToolData = (this.builderToolData != null) ? this.builderToolData.clone() : null;
/* 1573 */     copy.itemEntity = (this.itemEntity != null) ? this.itemEntity.clone() : null;
/* 1574 */     copy.set = this.set;
/* 1575 */     copy.categories = (this.categories != null) ? Arrays.<String>copyOf(this.categories, this.categories.length) : null;
/* 1576 */     copy.particles = (this.particles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.particles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 1577 */     copy.firstPersonParticles = (this.firstPersonParticles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.firstPersonParticles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 1578 */     copy.trails = (this.trails != null) ? (ModelTrail[])Arrays.<ModelTrail>stream(this.trails).map(e -> e.clone()).toArray(x$0 -> new ModelTrail[x$0]) : null;
/* 1579 */     copy.light = (this.light != null) ? this.light.clone() : null;
/* 1580 */     copy.durability = this.durability;
/* 1581 */     copy.soundEventIndex = this.soundEventIndex;
/* 1582 */     copy.itemSoundSetIndex = this.itemSoundSetIndex;
/* 1583 */     copy.interactions = (this.interactions != null) ? new HashMap<>(this.interactions) : null;
/* 1584 */     copy.interactionVars = (this.interactionVars != null) ? new HashMap<>(this.interactionVars) : null;
/* 1585 */     copy.interactionConfig = (this.interactionConfig != null) ? this.interactionConfig.clone() : null;
/* 1586 */     copy.droppedItemAnimation = this.droppedItemAnimation;
/* 1587 */     copy.tagIndexes = (this.tagIndexes != null) ? Arrays.copyOf(this.tagIndexes, this.tagIndexes.length) : null;
/* 1588 */     if (this.itemAppearanceConditions != null) {
/* 1589 */       Map<Integer, ItemAppearanceCondition[]> m = (Map)new HashMap<>();
/* 1590 */       for (Map.Entry<Integer, ItemAppearanceCondition[]> e : this.itemAppearanceConditions.entrySet()) m.put(e.getKey(), (ItemAppearanceCondition[])Arrays.<ItemAppearanceCondition>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new ItemAppearanceCondition[x$0])); 
/* 1591 */       copy.itemAppearanceConditions = m;
/*      */     } 
/* 1593 */     copy.displayEntityStatsHUD = (this.displayEntityStatsHUD != null) ? Arrays.copyOf(this.displayEntityStatsHUD, this.displayEntityStatsHUD.length) : null;
/* 1594 */     copy.pullbackConfig = (this.pullbackConfig != null) ? this.pullbackConfig.clone() : null;
/* 1595 */     copy.clipsGeometry = this.clipsGeometry;
/* 1596 */     copy.renderDeployablePreview = this.renderDeployablePreview;
/* 1597 */     return copy;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*      */     ItemBase other;
/* 1603 */     if (this == obj) return true; 
/* 1604 */     if (obj instanceof ItemBase) { other = (ItemBase)obj; } else { return false; }
/* 1605 */      return (Objects.equals(this.id, other.id) && Objects.equals(this.model, other.model) && this.scale == other.scale && Objects.equals(this.texture, other.texture) && Objects.equals(this.animation, other.animation) && Objects.equals(this.playerAnimationsId, other.playerAnimationsId) && this.usePlayerAnimations == other.usePlayerAnimations && this.maxStack == other.maxStack && this.reticleIndex == other.reticleIndex && Objects.equals(this.icon, other.icon) && Objects.equals(this.iconProperties, other.iconProperties) && Objects.equals(this.translationProperties, other.translationProperties) && this.itemLevel == other.itemLevel && this.qualityIndex == other.qualityIndex && Arrays.equals((Object[])this.resourceTypes, (Object[])other.resourceTypes) && this.consumable == other.consumable && this.variant == other.variant && this.blockId == other.blockId && Objects.equals(this.tool, other.tool) && Objects.equals(this.weapon, other.weapon) && Objects.equals(this.armor, other.armor) && Objects.equals(this.gliderConfig, other.gliderConfig) && Objects.equals(this.utility, other.utility) && Objects.equals(this.blockSelectorTool, other.blockSelectorTool) && Objects.equals(this.builderToolData, other.builderToolData) && Objects.equals(this.itemEntity, other.itemEntity) && Objects.equals(this.set, other.set) && Arrays.equals((Object[])this.categories, (Object[])other.categories) && Arrays.equals((Object[])this.particles, (Object[])other.particles) && Arrays.equals((Object[])this.firstPersonParticles, (Object[])other.firstPersonParticles) && Arrays.equals((Object[])this.trails, (Object[])other.trails) && Objects.equals(this.light, other.light) && this.durability == other.durability && this.soundEventIndex == other.soundEventIndex && this.itemSoundSetIndex == other.itemSoundSetIndex && Objects.equals(this.interactions, other.interactions) && Objects.equals(this.interactionVars, other.interactionVars) && Objects.equals(this.interactionConfig, other.interactionConfig) && Objects.equals(this.droppedItemAnimation, other.droppedItemAnimation) && Arrays.equals(this.tagIndexes, other.tagIndexes) && Objects.equals(this.itemAppearanceConditions, other.itemAppearanceConditions) && Arrays.equals(this.displayEntityStatsHUD, other.displayEntityStatsHUD) && Objects.equals(this.pullbackConfig, other.pullbackConfig) && this.clipsGeometry == other.clipsGeometry && this.renderDeployablePreview == other.renderDeployablePreview);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1610 */     int result = 1;
/* 1611 */     result = 31 * result + Objects.hashCode(this.id);
/* 1612 */     result = 31 * result + Objects.hashCode(this.model);
/* 1613 */     result = 31 * result + Float.hashCode(this.scale);
/* 1614 */     result = 31 * result + Objects.hashCode(this.texture);
/* 1615 */     result = 31 * result + Objects.hashCode(this.animation);
/* 1616 */     result = 31 * result + Objects.hashCode(this.playerAnimationsId);
/* 1617 */     result = 31 * result + Boolean.hashCode(this.usePlayerAnimations);
/* 1618 */     result = 31 * result + Integer.hashCode(this.maxStack);
/* 1619 */     result = 31 * result + Integer.hashCode(this.reticleIndex);
/* 1620 */     result = 31 * result + Objects.hashCode(this.icon);
/* 1621 */     result = 31 * result + Objects.hashCode(this.iconProperties);
/* 1622 */     result = 31 * result + Objects.hashCode(this.translationProperties);
/* 1623 */     result = 31 * result + Integer.hashCode(this.itemLevel);
/* 1624 */     result = 31 * result + Integer.hashCode(this.qualityIndex);
/* 1625 */     result = 31 * result + Arrays.hashCode((Object[])this.resourceTypes);
/* 1626 */     result = 31 * result + Boolean.hashCode(this.consumable);
/* 1627 */     result = 31 * result + Boolean.hashCode(this.variant);
/* 1628 */     result = 31 * result + Integer.hashCode(this.blockId);
/* 1629 */     result = 31 * result + Objects.hashCode(this.tool);
/* 1630 */     result = 31 * result + Objects.hashCode(this.weapon);
/* 1631 */     result = 31 * result + Objects.hashCode(this.armor);
/* 1632 */     result = 31 * result + Objects.hashCode(this.gliderConfig);
/* 1633 */     result = 31 * result + Objects.hashCode(this.utility);
/* 1634 */     result = 31 * result + Objects.hashCode(this.blockSelectorTool);
/* 1635 */     result = 31 * result + Objects.hashCode(this.builderToolData);
/* 1636 */     result = 31 * result + Objects.hashCode(this.itemEntity);
/* 1637 */     result = 31 * result + Objects.hashCode(this.set);
/* 1638 */     result = 31 * result + Arrays.hashCode((Object[])this.categories);
/* 1639 */     result = 31 * result + Arrays.hashCode((Object[])this.particles);
/* 1640 */     result = 31 * result + Arrays.hashCode((Object[])this.firstPersonParticles);
/* 1641 */     result = 31 * result + Arrays.hashCode((Object[])this.trails);
/* 1642 */     result = 31 * result + Objects.hashCode(this.light);
/* 1643 */     result = 31 * result + Double.hashCode(this.durability);
/* 1644 */     result = 31 * result + Integer.hashCode(this.soundEventIndex);
/* 1645 */     result = 31 * result + Integer.hashCode(this.itemSoundSetIndex);
/* 1646 */     result = 31 * result + Objects.hashCode(this.interactions);
/* 1647 */     result = 31 * result + Objects.hashCode(this.interactionVars);
/* 1648 */     result = 31 * result + Objects.hashCode(this.interactionConfig);
/* 1649 */     result = 31 * result + Objects.hashCode(this.droppedItemAnimation);
/* 1650 */     result = 31 * result + Arrays.hashCode(this.tagIndexes);
/* 1651 */     result = 31 * result + Objects.hashCode(this.itemAppearanceConditions);
/* 1652 */     result = 31 * result + Arrays.hashCode(this.displayEntityStatsHUD);
/* 1653 */     result = 31 * result + Objects.hashCode(this.pullbackConfig);
/* 1654 */     result = 31 * result + Boolean.hashCode(this.clipsGeometry);
/* 1655 */     result = 31 * result + Boolean.hashCode(this.renderDeployablePreview);
/* 1656 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\ItemBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */