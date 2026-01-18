/*      */ package com.hypixel.hytale.protocol;
/*      */ import com.hypixel.hytale.protocol.io.PacketIO;
/*      */ import com.hypixel.hytale.protocol.io.ProtocolException;
/*      */ import com.hypixel.hytale.protocol.io.ValidationResult;
/*      */ import com.hypixel.hytale.protocol.io.VarInt;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ public class BlockType {
/*      */   public static final int NULLABLE_BIT_FIELD_SIZE = 4;
/*      */   public static final int FIXED_BLOCK_SIZE = 163;
/*      */   public static final int VARIABLE_FIELD_COUNT = 24;
/*      */   public static final int VARIABLE_BLOCK_START = 259;
/*      */   public static final int MAX_SIZE = 1677721600;
/*      */   @Nullable
/*      */   public String item;
/*      */   @Nullable
/*      */   public String name;
/*      */   public boolean unknown;
/*      */   @Nonnull
/*   23 */   public DrawType drawType = DrawType.Empty; @Nonnull
/*   24 */   public BlockMaterial material = BlockMaterial.Empty; @Nonnull
/*   25 */   public Opacity opacity = Opacity.Solid; @Nullable
/*      */   public ShaderType[] shaderEffect; public int hitbox; public int interactionHitbox; @Nullable
/*      */   public String model; @Nullable
/*      */   public ModelTexture[] modelTexture;
/*      */   public float modelScale;
/*      */   @Nullable
/*      */   public String modelAnimation;
/*      */   public boolean looping;
/*      */   public int maxSupportDistance;
/*      */   @Nonnull
/*   35 */   public BlockSupportsRequiredForType blockSupportsRequiredFor = BlockSupportsRequiredForType.Any; @Nullable
/*      */   public Map<BlockNeighbor, RequiredBlockFaceSupport[]> support; @Nullable
/*      */   public Map<BlockNeighbor, BlockFaceSupport[]> supporting; public boolean requiresAlphaBlending; @Nullable
/*      */   public BlockTextures[] cubeTextures; @Nullable
/*      */   public String cubeSideMaskTexture;
/*      */   @Nonnull
/*   41 */   public ShadingMode cubeShadingMode = ShadingMode.Standard; @Nonnull
/*   42 */   public RandomRotation randomRotation = RandomRotation.None; @Nonnull
/*   43 */   public VariantRotation variantRotation = VariantRotation.None; @Nonnull
/*   44 */   public Rotation rotationYawPlacementOffset = Rotation.None;
/*      */   
/*      */   public int blockSoundSetIndex;
/*      */   
/*      */   public int ambientSoundEventIndex;
/*      */   
/*      */   @Nullable
/*      */   public ModelParticle[] particles;
/*      */   
/*      */   @Nullable
/*      */   public String blockParticleSetId;
/*      */   
/*      */   @Nullable
/*      */   public String blockBreakingDecalId;
/*      */   
/*      */   @Nullable
/*      */   public Color particleColor;
/*      */   @Nullable
/*      */   public ColorLight light;
/*      */   @Nullable
/*      */   public Tint tint;
/*      */   @Nullable
/*      */   public Tint biomeTint;
/*      */   public int group;
/*      */   @Nullable
/*      */   public String transitionTexture;
/*      */   @Nullable
/*      */   public int[] transitionToGroups;
/*      */   @Nullable
/*      */   public BlockMovementSettings movementSettings;
/*      */   
/*      */   public BlockType(@Nullable String item, @Nullable String name, boolean unknown, @Nonnull DrawType drawType, @Nonnull BlockMaterial material, @Nonnull Opacity opacity, @Nullable ShaderType[] shaderEffect, int hitbox, int interactionHitbox, @Nullable String model, @Nullable ModelTexture[] modelTexture, float modelScale, @Nullable String modelAnimation, boolean looping, int maxSupportDistance, @Nonnull BlockSupportsRequiredForType blockSupportsRequiredFor, @Nullable Map<BlockNeighbor, RequiredBlockFaceSupport[]> support, @Nullable Map<BlockNeighbor, BlockFaceSupport[]> supporting, boolean requiresAlphaBlending, @Nullable BlockTextures[] cubeTextures, @Nullable String cubeSideMaskTexture, @Nonnull ShadingMode cubeShadingMode, @Nonnull RandomRotation randomRotation, @Nonnull VariantRotation variantRotation, @Nonnull Rotation rotationYawPlacementOffset, int blockSoundSetIndex, int ambientSoundEventIndex, @Nullable ModelParticle[] particles, @Nullable String blockParticleSetId, @Nullable String blockBreakingDecalId, @Nullable Color particleColor, @Nullable ColorLight light, @Nullable Tint tint, @Nullable Tint biomeTint, int group, @Nullable String transitionTexture, @Nullable int[] transitionToGroups, @Nullable BlockMovementSettings movementSettings, @Nullable BlockFlags flags, @Nullable String interactionHint, @Nullable BlockGathering gathering, @Nullable BlockPlacementSettings placementSettings, @Nullable ModelDisplay display, @Nullable RailConfig rail, boolean ignoreSupportWhenPlaced, @Nullable Map<InteractionType, Integer> interactions, @Nullable Map<String, Integer> states, int transitionToTag, @Nullable int[] tagIndexes, @Nullable Bench bench, @Nullable ConnectedBlockRuleSet connectedBlockRuleSet) {
/*   76 */     this.item = item;
/*   77 */     this.name = name;
/*   78 */     this.unknown = unknown;
/*   79 */     this.drawType = drawType;
/*   80 */     this.material = material;
/*   81 */     this.opacity = opacity;
/*   82 */     this.shaderEffect = shaderEffect;
/*   83 */     this.hitbox = hitbox;
/*   84 */     this.interactionHitbox = interactionHitbox;
/*   85 */     this.model = model;
/*   86 */     this.modelTexture = modelTexture;
/*   87 */     this.modelScale = modelScale;
/*   88 */     this.modelAnimation = modelAnimation;
/*   89 */     this.looping = looping;
/*   90 */     this.maxSupportDistance = maxSupportDistance;
/*   91 */     this.blockSupportsRequiredFor = blockSupportsRequiredFor;
/*   92 */     this.support = support;
/*   93 */     this.supporting = supporting;
/*   94 */     this.requiresAlphaBlending = requiresAlphaBlending;
/*   95 */     this.cubeTextures = cubeTextures;
/*   96 */     this.cubeSideMaskTexture = cubeSideMaskTexture;
/*   97 */     this.cubeShadingMode = cubeShadingMode;
/*   98 */     this.randomRotation = randomRotation;
/*   99 */     this.variantRotation = variantRotation;
/*  100 */     this.rotationYawPlacementOffset = rotationYawPlacementOffset;
/*  101 */     this.blockSoundSetIndex = blockSoundSetIndex;
/*  102 */     this.ambientSoundEventIndex = ambientSoundEventIndex;
/*  103 */     this.particles = particles;
/*  104 */     this.blockParticleSetId = blockParticleSetId;
/*  105 */     this.blockBreakingDecalId = blockBreakingDecalId;
/*  106 */     this.particleColor = particleColor;
/*  107 */     this.light = light;
/*  108 */     this.tint = tint;
/*  109 */     this.biomeTint = biomeTint;
/*  110 */     this.group = group;
/*  111 */     this.transitionTexture = transitionTexture;
/*  112 */     this.transitionToGroups = transitionToGroups;
/*  113 */     this.movementSettings = movementSettings;
/*  114 */     this.flags = flags;
/*  115 */     this.interactionHint = interactionHint;
/*  116 */     this.gathering = gathering;
/*  117 */     this.placementSettings = placementSettings;
/*  118 */     this.display = display;
/*  119 */     this.rail = rail;
/*  120 */     this.ignoreSupportWhenPlaced = ignoreSupportWhenPlaced;
/*  121 */     this.interactions = interactions;
/*  122 */     this.states = states;
/*  123 */     this.transitionToTag = transitionToTag;
/*  124 */     this.tagIndexes = tagIndexes;
/*  125 */     this.bench = bench;
/*  126 */     this.connectedBlockRuleSet = connectedBlockRuleSet; } @Nullable public BlockFlags flags; @Nullable public String interactionHint; @Nullable public BlockGathering gathering; @Nullable public BlockPlacementSettings placementSettings; @Nullable public ModelDisplay display; @Nullable public RailConfig rail; public boolean ignoreSupportWhenPlaced; @Nullable public Map<InteractionType, Integer> interactions; @Nullable
/*      */   public Map<String, Integer> states; public int transitionToTag; @Nullable
/*      */   public int[] tagIndexes; @Nullable
/*      */   public Bench bench; @Nullable
/*  130 */   public ConnectedBlockRuleSet connectedBlockRuleSet; public BlockType(@Nonnull BlockType other) { this.item = other.item;
/*  131 */     this.name = other.name;
/*  132 */     this.unknown = other.unknown;
/*  133 */     this.drawType = other.drawType;
/*  134 */     this.material = other.material;
/*  135 */     this.opacity = other.opacity;
/*  136 */     this.shaderEffect = other.shaderEffect;
/*  137 */     this.hitbox = other.hitbox;
/*  138 */     this.interactionHitbox = other.interactionHitbox;
/*  139 */     this.model = other.model;
/*  140 */     this.modelTexture = other.modelTexture;
/*  141 */     this.modelScale = other.modelScale;
/*  142 */     this.modelAnimation = other.modelAnimation;
/*  143 */     this.looping = other.looping;
/*  144 */     this.maxSupportDistance = other.maxSupportDistance;
/*  145 */     this.blockSupportsRequiredFor = other.blockSupportsRequiredFor;
/*  146 */     this.support = other.support;
/*  147 */     this.supporting = other.supporting;
/*  148 */     this.requiresAlphaBlending = other.requiresAlphaBlending;
/*  149 */     this.cubeTextures = other.cubeTextures;
/*  150 */     this.cubeSideMaskTexture = other.cubeSideMaskTexture;
/*  151 */     this.cubeShadingMode = other.cubeShadingMode;
/*  152 */     this.randomRotation = other.randomRotation;
/*  153 */     this.variantRotation = other.variantRotation;
/*  154 */     this.rotationYawPlacementOffset = other.rotationYawPlacementOffset;
/*  155 */     this.blockSoundSetIndex = other.blockSoundSetIndex;
/*  156 */     this.ambientSoundEventIndex = other.ambientSoundEventIndex;
/*  157 */     this.particles = other.particles;
/*  158 */     this.blockParticleSetId = other.blockParticleSetId;
/*  159 */     this.blockBreakingDecalId = other.blockBreakingDecalId;
/*  160 */     this.particleColor = other.particleColor;
/*  161 */     this.light = other.light;
/*  162 */     this.tint = other.tint;
/*  163 */     this.biomeTint = other.biomeTint;
/*  164 */     this.group = other.group;
/*  165 */     this.transitionTexture = other.transitionTexture;
/*  166 */     this.transitionToGroups = other.transitionToGroups;
/*  167 */     this.movementSettings = other.movementSettings;
/*  168 */     this.flags = other.flags;
/*  169 */     this.interactionHint = other.interactionHint;
/*  170 */     this.gathering = other.gathering;
/*  171 */     this.placementSettings = other.placementSettings;
/*  172 */     this.display = other.display;
/*  173 */     this.rail = other.rail;
/*  174 */     this.ignoreSupportWhenPlaced = other.ignoreSupportWhenPlaced;
/*  175 */     this.interactions = other.interactions;
/*  176 */     this.states = other.states;
/*  177 */     this.transitionToTag = other.transitionToTag;
/*  178 */     this.tagIndexes = other.tagIndexes;
/*  179 */     this.bench = other.bench;
/*  180 */     this.connectedBlockRuleSet = other.connectedBlockRuleSet; }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public static BlockType deserialize(@Nonnull ByteBuf buf, int offset) {
/*  185 */     BlockType obj = new BlockType();
/*  186 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 4);
/*  187 */     obj.unknown = (buf.getByte(offset + 4) != 0);
/*  188 */     obj.drawType = DrawType.fromValue(buf.getByte(offset + 5));
/*  189 */     obj.material = BlockMaterial.fromValue(buf.getByte(offset + 6));
/*  190 */     obj.opacity = Opacity.fromValue(buf.getByte(offset + 7));
/*  191 */     obj.hitbox = buf.getIntLE(offset + 8);
/*  192 */     obj.interactionHitbox = buf.getIntLE(offset + 12);
/*  193 */     obj.modelScale = buf.getFloatLE(offset + 16);
/*  194 */     obj.looping = (buf.getByte(offset + 20) != 0);
/*  195 */     obj.maxSupportDistance = buf.getIntLE(offset + 21);
/*  196 */     obj.blockSupportsRequiredFor = BlockSupportsRequiredForType.fromValue(buf.getByte(offset + 25));
/*  197 */     obj.requiresAlphaBlending = (buf.getByte(offset + 26) != 0);
/*  198 */     obj.cubeShadingMode = ShadingMode.fromValue(buf.getByte(offset + 27));
/*  199 */     obj.randomRotation = RandomRotation.fromValue(buf.getByte(offset + 28));
/*  200 */     obj.variantRotation = VariantRotation.fromValue(buf.getByte(offset + 29));
/*  201 */     obj.rotationYawPlacementOffset = Rotation.fromValue(buf.getByte(offset + 30));
/*  202 */     obj.blockSoundSetIndex = buf.getIntLE(offset + 31);
/*  203 */     obj.ambientSoundEventIndex = buf.getIntLE(offset + 35);
/*  204 */     if ((nullBits[1] & 0x20) != 0) obj.particleColor = Color.deserialize(buf, offset + 39); 
/*  205 */     if ((nullBits[1] & 0x40) != 0) obj.light = ColorLight.deserialize(buf, offset + 42); 
/*  206 */     if ((nullBits[1] & 0x80) != 0) obj.tint = Tint.deserialize(buf, offset + 46); 
/*  207 */     if ((nullBits[2] & 0x1) != 0) obj.biomeTint = Tint.deserialize(buf, offset + 70); 
/*  208 */     obj.group = buf.getIntLE(offset + 94);
/*  209 */     if ((nullBits[2] & 0x8) != 0) obj.movementSettings = BlockMovementSettings.deserialize(buf, offset + 98); 
/*  210 */     if ((nullBits[2] & 0x10) != 0) obj.flags = BlockFlags.deserialize(buf, offset + 140); 
/*  211 */     if ((nullBits[2] & 0x80) != 0) obj.placementSettings = BlockPlacementSettings.deserialize(buf, offset + 142); 
/*  212 */     obj.ignoreSupportWhenPlaced = (buf.getByte(offset + 158) != 0);
/*  213 */     obj.transitionToTag = buf.getIntLE(offset + 159);
/*      */     
/*  215 */     if ((nullBits[0] & 0x1) != 0) {
/*  216 */       int varPos0 = offset + 259 + buf.getIntLE(offset + 163);
/*  217 */       int itemLen = VarInt.peek(buf, varPos0);
/*  218 */       if (itemLen < 0) throw ProtocolException.negativeLength("Item", itemLen); 
/*  219 */       if (itemLen > 4096000) throw ProtocolException.stringTooLong("Item", itemLen, 4096000); 
/*  220 */       obj.item = PacketIO.readVarString(buf, varPos0, PacketIO.UTF8);
/*      */     } 
/*  222 */     if ((nullBits[0] & 0x2) != 0) {
/*  223 */       int varPos1 = offset + 259 + buf.getIntLE(offset + 167);
/*  224 */       int nameLen = VarInt.peek(buf, varPos1);
/*  225 */       if (nameLen < 0) throw ProtocolException.negativeLength("Name", nameLen); 
/*  226 */       if (nameLen > 4096000) throw ProtocolException.stringTooLong("Name", nameLen, 4096000); 
/*  227 */       obj.name = PacketIO.readVarString(buf, varPos1, PacketIO.UTF8);
/*      */     } 
/*  229 */     if ((nullBits[0] & 0x4) != 0) {
/*  230 */       int varPos2 = offset + 259 + buf.getIntLE(offset + 171);
/*  231 */       int shaderEffectCount = VarInt.peek(buf, varPos2);
/*  232 */       if (shaderEffectCount < 0) throw ProtocolException.negativeLength("ShaderEffect", shaderEffectCount); 
/*  233 */       if (shaderEffectCount > 4096000) throw ProtocolException.arrayTooLong("ShaderEffect", shaderEffectCount, 4096000); 
/*  234 */       int varIntLen = VarInt.length(buf, varPos2);
/*  235 */       if ((varPos2 + varIntLen) + shaderEffectCount * 1L > buf.readableBytes())
/*  236 */         throw ProtocolException.bufferTooSmall("ShaderEffect", varPos2 + varIntLen + shaderEffectCount * 1, buf.readableBytes()); 
/*  237 */       obj.shaderEffect = new ShaderType[shaderEffectCount];
/*  238 */       int elemPos = varPos2 + varIntLen;
/*  239 */       for (int i = 0; i < shaderEffectCount; i++) {
/*  240 */         obj.shaderEffect[i] = ShaderType.fromValue(buf.getByte(elemPos)); elemPos++;
/*      */       } 
/*      */     } 
/*  243 */     if ((nullBits[0] & 0x8) != 0) {
/*  244 */       int varPos3 = offset + 259 + buf.getIntLE(offset + 175);
/*  245 */       int modelLen = VarInt.peek(buf, varPos3);
/*  246 */       if (modelLen < 0) throw ProtocolException.negativeLength("Model", modelLen); 
/*  247 */       if (modelLen > 4096000) throw ProtocolException.stringTooLong("Model", modelLen, 4096000); 
/*  248 */       obj.model = PacketIO.readVarString(buf, varPos3, PacketIO.UTF8);
/*      */     } 
/*  250 */     if ((nullBits[0] & 0x10) != 0) {
/*  251 */       int varPos4 = offset + 259 + buf.getIntLE(offset + 179);
/*  252 */       int modelTextureCount = VarInt.peek(buf, varPos4);
/*  253 */       if (modelTextureCount < 0) throw ProtocolException.negativeLength("ModelTexture", modelTextureCount); 
/*  254 */       if (modelTextureCount > 4096000) throw ProtocolException.arrayTooLong("ModelTexture", modelTextureCount, 4096000); 
/*  255 */       int varIntLen = VarInt.length(buf, varPos4);
/*  256 */       if ((varPos4 + varIntLen) + modelTextureCount * 5L > buf.readableBytes())
/*  257 */         throw ProtocolException.bufferTooSmall("ModelTexture", varPos4 + varIntLen + modelTextureCount * 5, buf.readableBytes()); 
/*  258 */       obj.modelTexture = new ModelTexture[modelTextureCount];
/*  259 */       int elemPos = varPos4 + varIntLen;
/*  260 */       for (int i = 0; i < modelTextureCount; i++) {
/*  261 */         obj.modelTexture[i] = ModelTexture.deserialize(buf, elemPos);
/*  262 */         elemPos += ModelTexture.computeBytesConsumed(buf, elemPos);
/*      */       } 
/*      */     } 
/*  265 */     if ((nullBits[0] & 0x20) != 0) {
/*  266 */       int varPos5 = offset + 259 + buf.getIntLE(offset + 183);
/*  267 */       int modelAnimationLen = VarInt.peek(buf, varPos5);
/*  268 */       if (modelAnimationLen < 0) throw ProtocolException.negativeLength("ModelAnimation", modelAnimationLen); 
/*  269 */       if (modelAnimationLen > 4096000) throw ProtocolException.stringTooLong("ModelAnimation", modelAnimationLen, 4096000); 
/*  270 */       obj.modelAnimation = PacketIO.readVarString(buf, varPos5, PacketIO.UTF8);
/*      */     } 
/*  272 */     if ((nullBits[0] & 0x40) != 0) {
/*  273 */       int varPos6 = offset + 259 + buf.getIntLE(offset + 187);
/*  274 */       int supportCount = VarInt.peek(buf, varPos6);
/*  275 */       if (supportCount < 0) throw ProtocolException.negativeLength("Support", supportCount); 
/*  276 */       if (supportCount > 4096000) throw ProtocolException.dictionaryTooLarge("Support", supportCount, 4096000); 
/*  277 */       int varIntLen = VarInt.length(buf, varPos6);
/*  278 */       obj.support = (Map)new HashMap<>(supportCount);
/*  279 */       int dictPos = varPos6 + varIntLen;
/*  280 */       for (int i = 0; i < supportCount; i++) {
/*  281 */         BlockNeighbor key = BlockNeighbor.fromValue(buf.getByte(dictPos)); dictPos++;
/*  282 */         int valLen = VarInt.peek(buf, dictPos);
/*  283 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  284 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/*  285 */         int valVarLen = VarInt.length(buf, dictPos);
/*  286 */         if ((dictPos + valVarLen) + valLen * 17L > buf.readableBytes())
/*  287 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 17, buf.readableBytes()); 
/*  288 */         dictPos += valVarLen;
/*  289 */         RequiredBlockFaceSupport[] val = new RequiredBlockFaceSupport[valLen];
/*  290 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/*  291 */           val[valIdx] = RequiredBlockFaceSupport.deserialize(buf, dictPos);
/*  292 */           dictPos += RequiredBlockFaceSupport.computeBytesConsumed(buf, dictPos);
/*      */         } 
/*  294 */         if (obj.support.put(key, val) != null)
/*  295 */           throw ProtocolException.duplicateKey("support", key); 
/*      */       } 
/*      */     } 
/*  298 */     if ((nullBits[0] & 0x80) != 0) {
/*  299 */       int varPos7 = offset + 259 + buf.getIntLE(offset + 191);
/*  300 */       int supportingCount = VarInt.peek(buf, varPos7);
/*  301 */       if (supportingCount < 0) throw ProtocolException.negativeLength("Supporting", supportingCount); 
/*  302 */       if (supportingCount > 4096000) throw ProtocolException.dictionaryTooLarge("Supporting", supportingCount, 4096000); 
/*  303 */       int varIntLen = VarInt.length(buf, varPos7);
/*  304 */       obj.supporting = (Map)new HashMap<>(supportingCount);
/*  305 */       int dictPos = varPos7 + varIntLen;
/*  306 */       for (int i = 0; i < supportingCount; i++) {
/*  307 */         BlockNeighbor key = BlockNeighbor.fromValue(buf.getByte(dictPos)); dictPos++;
/*  308 */         int valLen = VarInt.peek(buf, dictPos);
/*  309 */         if (valLen < 0) throw ProtocolException.negativeLength("val", valLen); 
/*  310 */         if (valLen > 64) throw ProtocolException.arrayTooLong("val", valLen, 64); 
/*  311 */         int valVarLen = VarInt.length(buf, dictPos);
/*  312 */         if ((dictPos + valVarLen) + valLen * 1L > buf.readableBytes())
/*  313 */           throw ProtocolException.bufferTooSmall("val", dictPos + valVarLen + valLen * 1, buf.readableBytes()); 
/*  314 */         dictPos += valVarLen;
/*  315 */         BlockFaceSupport[] val = new BlockFaceSupport[valLen];
/*  316 */         for (int valIdx = 0; valIdx < valLen; valIdx++) {
/*  317 */           val[valIdx] = BlockFaceSupport.deserialize(buf, dictPos);
/*  318 */           dictPos += BlockFaceSupport.computeBytesConsumed(buf, dictPos);
/*      */         } 
/*  320 */         if (obj.supporting.put(key, val) != null)
/*  321 */           throw ProtocolException.duplicateKey("supporting", key); 
/*      */       } 
/*      */     } 
/*  324 */     if ((nullBits[1] & 0x1) != 0) {
/*  325 */       int varPos8 = offset + 259 + buf.getIntLE(offset + 195);
/*  326 */       int cubeTexturesCount = VarInt.peek(buf, varPos8);
/*  327 */       if (cubeTexturesCount < 0) throw ProtocolException.negativeLength("CubeTextures", cubeTexturesCount); 
/*  328 */       if (cubeTexturesCount > 4096000) throw ProtocolException.arrayTooLong("CubeTextures", cubeTexturesCount, 4096000); 
/*  329 */       int varIntLen = VarInt.length(buf, varPos8);
/*  330 */       if ((varPos8 + varIntLen) + cubeTexturesCount * 5L > buf.readableBytes())
/*  331 */         throw ProtocolException.bufferTooSmall("CubeTextures", varPos8 + varIntLen + cubeTexturesCount * 5, buf.readableBytes()); 
/*  332 */       obj.cubeTextures = new BlockTextures[cubeTexturesCount];
/*  333 */       int elemPos = varPos8 + varIntLen;
/*  334 */       for (int i = 0; i < cubeTexturesCount; i++) {
/*  335 */         obj.cubeTextures[i] = BlockTextures.deserialize(buf, elemPos);
/*  336 */         elemPos += BlockTextures.computeBytesConsumed(buf, elemPos);
/*      */       } 
/*      */     } 
/*  339 */     if ((nullBits[1] & 0x2) != 0) {
/*  340 */       int varPos9 = offset + 259 + buf.getIntLE(offset + 199);
/*  341 */       int cubeSideMaskTextureLen = VarInt.peek(buf, varPos9);
/*  342 */       if (cubeSideMaskTextureLen < 0) throw ProtocolException.negativeLength("CubeSideMaskTexture", cubeSideMaskTextureLen); 
/*  343 */       if (cubeSideMaskTextureLen > 4096000) throw ProtocolException.stringTooLong("CubeSideMaskTexture", cubeSideMaskTextureLen, 4096000); 
/*  344 */       obj.cubeSideMaskTexture = PacketIO.readVarString(buf, varPos9, PacketIO.UTF8);
/*      */     } 
/*  346 */     if ((nullBits[1] & 0x4) != 0) {
/*  347 */       int varPos10 = offset + 259 + buf.getIntLE(offset + 203);
/*  348 */       int particlesCount = VarInt.peek(buf, varPos10);
/*  349 */       if (particlesCount < 0) throw ProtocolException.negativeLength("Particles", particlesCount); 
/*  350 */       if (particlesCount > 4096000) throw ProtocolException.arrayTooLong("Particles", particlesCount, 4096000); 
/*  351 */       int varIntLen = VarInt.length(buf, varPos10);
/*  352 */       if ((varPos10 + varIntLen) + particlesCount * 34L > buf.readableBytes())
/*  353 */         throw ProtocolException.bufferTooSmall("Particles", varPos10 + varIntLen + particlesCount * 34, buf.readableBytes()); 
/*  354 */       obj.particles = new ModelParticle[particlesCount];
/*  355 */       int elemPos = varPos10 + varIntLen;
/*  356 */       for (int i = 0; i < particlesCount; i++) {
/*  357 */         obj.particles[i] = ModelParticle.deserialize(buf, elemPos);
/*  358 */         elemPos += ModelParticle.computeBytesConsumed(buf, elemPos);
/*      */       } 
/*      */     } 
/*  361 */     if ((nullBits[1] & 0x8) != 0) {
/*  362 */       int varPos11 = offset + 259 + buf.getIntLE(offset + 207);
/*  363 */       int blockParticleSetIdLen = VarInt.peek(buf, varPos11);
/*  364 */       if (blockParticleSetIdLen < 0) throw ProtocolException.negativeLength("BlockParticleSetId", blockParticleSetIdLen); 
/*  365 */       if (blockParticleSetIdLen > 4096000) throw ProtocolException.stringTooLong("BlockParticleSetId", blockParticleSetIdLen, 4096000); 
/*  366 */       obj.blockParticleSetId = PacketIO.readVarString(buf, varPos11, PacketIO.UTF8);
/*      */     } 
/*  368 */     if ((nullBits[1] & 0x10) != 0) {
/*  369 */       int varPos12 = offset + 259 + buf.getIntLE(offset + 211);
/*  370 */       int blockBreakingDecalIdLen = VarInt.peek(buf, varPos12);
/*  371 */       if (blockBreakingDecalIdLen < 0) throw ProtocolException.negativeLength("BlockBreakingDecalId", blockBreakingDecalIdLen); 
/*  372 */       if (blockBreakingDecalIdLen > 4096000) throw ProtocolException.stringTooLong("BlockBreakingDecalId", blockBreakingDecalIdLen, 4096000); 
/*  373 */       obj.blockBreakingDecalId = PacketIO.readVarString(buf, varPos12, PacketIO.UTF8);
/*      */     } 
/*  375 */     if ((nullBits[2] & 0x2) != 0) {
/*  376 */       int varPos13 = offset + 259 + buf.getIntLE(offset + 215);
/*  377 */       int transitionTextureLen = VarInt.peek(buf, varPos13);
/*  378 */       if (transitionTextureLen < 0) throw ProtocolException.negativeLength("TransitionTexture", transitionTextureLen); 
/*  379 */       if (transitionTextureLen > 4096000) throw ProtocolException.stringTooLong("TransitionTexture", transitionTextureLen, 4096000); 
/*  380 */       obj.transitionTexture = PacketIO.readVarString(buf, varPos13, PacketIO.UTF8);
/*      */     } 
/*  382 */     if ((nullBits[2] & 0x4) != 0) {
/*  383 */       int varPos14 = offset + 259 + buf.getIntLE(offset + 219);
/*  384 */       int transitionToGroupsCount = VarInt.peek(buf, varPos14);
/*  385 */       if (transitionToGroupsCount < 0) throw ProtocolException.negativeLength("TransitionToGroups", transitionToGroupsCount); 
/*  386 */       if (transitionToGroupsCount > 4096000) throw ProtocolException.arrayTooLong("TransitionToGroups", transitionToGroupsCount, 4096000); 
/*  387 */       int varIntLen = VarInt.length(buf, varPos14);
/*  388 */       if ((varPos14 + varIntLen) + transitionToGroupsCount * 4L > buf.readableBytes())
/*  389 */         throw ProtocolException.bufferTooSmall("TransitionToGroups", varPos14 + varIntLen + transitionToGroupsCount * 4, buf.readableBytes()); 
/*  390 */       obj.transitionToGroups = new int[transitionToGroupsCount];
/*  391 */       for (int i = 0; i < transitionToGroupsCount; i++) {
/*  392 */         obj.transitionToGroups[i] = buf.getIntLE(varPos14 + varIntLen + i * 4);
/*      */       }
/*      */     } 
/*  395 */     if ((nullBits[2] & 0x20) != 0) {
/*  396 */       int varPos15 = offset + 259 + buf.getIntLE(offset + 223);
/*  397 */       int interactionHintLen = VarInt.peek(buf, varPos15);
/*  398 */       if (interactionHintLen < 0) throw ProtocolException.negativeLength("InteractionHint", interactionHintLen); 
/*  399 */       if (interactionHintLen > 4096000) throw ProtocolException.stringTooLong("InteractionHint", interactionHintLen, 4096000); 
/*  400 */       obj.interactionHint = PacketIO.readVarString(buf, varPos15, PacketIO.UTF8);
/*      */     } 
/*  402 */     if ((nullBits[2] & 0x40) != 0) {
/*  403 */       int varPos16 = offset + 259 + buf.getIntLE(offset + 227);
/*  404 */       obj.gathering = BlockGathering.deserialize(buf, varPos16);
/*      */     } 
/*  406 */     if ((nullBits[3] & 0x1) != 0) {
/*  407 */       int varPos17 = offset + 259 + buf.getIntLE(offset + 231);
/*  408 */       obj.display = ModelDisplay.deserialize(buf, varPos17);
/*      */     } 
/*  410 */     if ((nullBits[3] & 0x2) != 0) {
/*  411 */       int varPos18 = offset + 259 + buf.getIntLE(offset + 235);
/*  412 */       obj.rail = RailConfig.deserialize(buf, varPos18);
/*      */     } 
/*  414 */     if ((nullBits[3] & 0x4) != 0) {
/*  415 */       int varPos19 = offset + 259 + buf.getIntLE(offset + 239);
/*  416 */       int interactionsCount = VarInt.peek(buf, varPos19);
/*  417 */       if (interactionsCount < 0) throw ProtocolException.negativeLength("Interactions", interactionsCount); 
/*  418 */       if (interactionsCount > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", interactionsCount, 4096000); 
/*  419 */       int varIntLen = VarInt.length(buf, varPos19);
/*  420 */       obj.interactions = new HashMap<>(interactionsCount);
/*  421 */       int dictPos = varPos19 + varIntLen;
/*  422 */       for (int i = 0; i < interactionsCount; i++) {
/*  423 */         InteractionType key = InteractionType.fromValue(buf.getByte(dictPos)); dictPos++;
/*  424 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/*  425 */         if (obj.interactions.put(key, Integer.valueOf(val)) != null)
/*  426 */           throw ProtocolException.duplicateKey("interactions", key); 
/*      */       } 
/*      */     } 
/*  429 */     if ((nullBits[3] & 0x8) != 0) {
/*  430 */       int varPos20 = offset + 259 + buf.getIntLE(offset + 243);
/*  431 */       int statesCount = VarInt.peek(buf, varPos20);
/*  432 */       if (statesCount < 0) throw ProtocolException.negativeLength("States", statesCount); 
/*  433 */       if (statesCount > 4096000) throw ProtocolException.dictionaryTooLarge("States", statesCount, 4096000); 
/*  434 */       int varIntLen = VarInt.length(buf, varPos20);
/*  435 */       obj.states = new HashMap<>(statesCount);
/*  436 */       int dictPos = varPos20 + varIntLen;
/*  437 */       for (int i = 0; i < statesCount; i++) {
/*  438 */         int keyLen = VarInt.peek(buf, dictPos);
/*  439 */         if (keyLen < 0) throw ProtocolException.negativeLength("key", keyLen); 
/*  440 */         if (keyLen > 4096000) throw ProtocolException.stringTooLong("key", keyLen, 4096000); 
/*  441 */         int keyVarLen = VarInt.length(buf, dictPos);
/*  442 */         String key = PacketIO.readVarString(buf, dictPos);
/*  443 */         dictPos += keyVarLen + keyLen;
/*  444 */         int val = buf.getIntLE(dictPos); dictPos += 4;
/*  445 */         if (obj.states.put(key, Integer.valueOf(val)) != null)
/*  446 */           throw ProtocolException.duplicateKey("states", key); 
/*      */       } 
/*      */     } 
/*  449 */     if ((nullBits[3] & 0x10) != 0) {
/*  450 */       int varPos21 = offset + 259 + buf.getIntLE(offset + 247);
/*  451 */       int tagIndexesCount = VarInt.peek(buf, varPos21);
/*  452 */       if (tagIndexesCount < 0) throw ProtocolException.negativeLength("TagIndexes", tagIndexesCount); 
/*  453 */       if (tagIndexesCount > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", tagIndexesCount, 4096000); 
/*  454 */       int varIntLen = VarInt.length(buf, varPos21);
/*  455 */       if ((varPos21 + varIntLen) + tagIndexesCount * 4L > buf.readableBytes())
/*  456 */         throw ProtocolException.bufferTooSmall("TagIndexes", varPos21 + varIntLen + tagIndexesCount * 4, buf.readableBytes()); 
/*  457 */       obj.tagIndexes = new int[tagIndexesCount];
/*  458 */       for (int i = 0; i < tagIndexesCount; i++) {
/*  459 */         obj.tagIndexes[i] = buf.getIntLE(varPos21 + varIntLen + i * 4);
/*      */       }
/*      */     } 
/*  462 */     if ((nullBits[3] & 0x20) != 0) {
/*  463 */       int varPos22 = offset + 259 + buf.getIntLE(offset + 251);
/*  464 */       obj.bench = Bench.deserialize(buf, varPos22);
/*      */     } 
/*  466 */     if ((nullBits[3] & 0x40) != 0) {
/*  467 */       int varPos23 = offset + 259 + buf.getIntLE(offset + 255);
/*  468 */       obj.connectedBlockRuleSet = ConnectedBlockRuleSet.deserialize(buf, varPos23);
/*      */     } 
/*      */     
/*  471 */     return obj;
/*      */   }
/*      */   
/*      */   public static int computeBytesConsumed(@Nonnull ByteBuf buf, int offset) {
/*  475 */     byte[] nullBits = PacketIO.readBytes(buf, offset, 4);
/*  476 */     int maxEnd = 259;
/*  477 */     if ((nullBits[0] & 0x1) != 0) {
/*  478 */       int fieldOffset0 = buf.getIntLE(offset + 163);
/*  479 */       int pos0 = offset + 259 + fieldOffset0;
/*  480 */       int sl = VarInt.peek(buf, pos0); pos0 += VarInt.length(buf, pos0) + sl;
/*  481 */       if (pos0 - offset > maxEnd) maxEnd = pos0 - offset; 
/*      */     } 
/*  483 */     if ((nullBits[0] & 0x2) != 0) {
/*  484 */       int fieldOffset1 = buf.getIntLE(offset + 167);
/*  485 */       int pos1 = offset + 259 + fieldOffset1;
/*  486 */       int sl = VarInt.peek(buf, pos1); pos1 += VarInt.length(buf, pos1) + sl;
/*  487 */       if (pos1 - offset > maxEnd) maxEnd = pos1 - offset; 
/*      */     } 
/*  489 */     if ((nullBits[0] & 0x4) != 0) {
/*  490 */       int fieldOffset2 = buf.getIntLE(offset + 171);
/*  491 */       int pos2 = offset + 259 + fieldOffset2;
/*  492 */       int arrLen = VarInt.peek(buf, pos2); pos2 += VarInt.length(buf, pos2) + arrLen * 1;
/*  493 */       if (pos2 - offset > maxEnd) maxEnd = pos2 - offset; 
/*      */     } 
/*  495 */     if ((nullBits[0] & 0x8) != 0) {
/*  496 */       int fieldOffset3 = buf.getIntLE(offset + 175);
/*  497 */       int pos3 = offset + 259 + fieldOffset3;
/*  498 */       int sl = VarInt.peek(buf, pos3); pos3 += VarInt.length(buf, pos3) + sl;
/*  499 */       if (pos3 - offset > maxEnd) maxEnd = pos3 - offset; 
/*      */     } 
/*  501 */     if ((nullBits[0] & 0x10) != 0) {
/*  502 */       int fieldOffset4 = buf.getIntLE(offset + 179);
/*  503 */       int pos4 = offset + 259 + fieldOffset4;
/*  504 */       int arrLen = VarInt.peek(buf, pos4); pos4 += VarInt.length(buf, pos4);
/*  505 */       for (int i = 0; i < arrLen; ) { pos4 += ModelTexture.computeBytesConsumed(buf, pos4); i++; }
/*  506 */        if (pos4 - offset > maxEnd) maxEnd = pos4 - offset; 
/*      */     } 
/*  508 */     if ((nullBits[0] & 0x20) != 0) {
/*  509 */       int fieldOffset5 = buf.getIntLE(offset + 183);
/*  510 */       int pos5 = offset + 259 + fieldOffset5;
/*  511 */       int sl = VarInt.peek(buf, pos5); pos5 += VarInt.length(buf, pos5) + sl;
/*  512 */       if (pos5 - offset > maxEnd) maxEnd = pos5 - offset; 
/*      */     } 
/*  514 */     if ((nullBits[0] & 0x40) != 0) {
/*  515 */       int fieldOffset6 = buf.getIntLE(offset + 187);
/*  516 */       int pos6 = offset + 259 + fieldOffset6;
/*  517 */       int dictLen = VarInt.peek(buf, pos6); pos6 += VarInt.length(buf, pos6);
/*  518 */       for (int i = 0; i < dictLen; ) { for (int al = VarInt.peek(buf, ++pos6), j = 0; j < al; ) { pos6 += RequiredBlockFaceSupport.computeBytesConsumed(buf, pos6); j++; }  i++; }
/*  519 */        if (pos6 - offset > maxEnd) maxEnd = pos6 - offset; 
/*      */     } 
/*  521 */     if ((nullBits[0] & 0x80) != 0) {
/*  522 */       int fieldOffset7 = buf.getIntLE(offset + 191);
/*  523 */       int pos7 = offset + 259 + fieldOffset7;
/*  524 */       int dictLen = VarInt.peek(buf, pos7); pos7 += VarInt.length(buf, pos7);
/*  525 */       for (int i = 0; i < dictLen; ) { for (int al = VarInt.peek(buf, ++pos7), j = 0; j < al; ) { pos7 += BlockFaceSupport.computeBytesConsumed(buf, pos7); j++; }  i++; }
/*  526 */        if (pos7 - offset > maxEnd) maxEnd = pos7 - offset; 
/*      */     } 
/*  528 */     if ((nullBits[1] & 0x1) != 0) {
/*  529 */       int fieldOffset8 = buf.getIntLE(offset + 195);
/*  530 */       int pos8 = offset + 259 + fieldOffset8;
/*  531 */       int arrLen = VarInt.peek(buf, pos8); pos8 += VarInt.length(buf, pos8);
/*  532 */       for (int i = 0; i < arrLen; ) { pos8 += BlockTextures.computeBytesConsumed(buf, pos8); i++; }
/*  533 */        if (pos8 - offset > maxEnd) maxEnd = pos8 - offset; 
/*      */     } 
/*  535 */     if ((nullBits[1] & 0x2) != 0) {
/*  536 */       int fieldOffset9 = buf.getIntLE(offset + 199);
/*  537 */       int pos9 = offset + 259 + fieldOffset9;
/*  538 */       int sl = VarInt.peek(buf, pos9); pos9 += VarInt.length(buf, pos9) + sl;
/*  539 */       if (pos9 - offset > maxEnd) maxEnd = pos9 - offset; 
/*      */     } 
/*  541 */     if ((nullBits[1] & 0x4) != 0) {
/*  542 */       int fieldOffset10 = buf.getIntLE(offset + 203);
/*  543 */       int pos10 = offset + 259 + fieldOffset10;
/*  544 */       int arrLen = VarInt.peek(buf, pos10); pos10 += VarInt.length(buf, pos10);
/*  545 */       for (int i = 0; i < arrLen; ) { pos10 += ModelParticle.computeBytesConsumed(buf, pos10); i++; }
/*  546 */        if (pos10 - offset > maxEnd) maxEnd = pos10 - offset; 
/*      */     } 
/*  548 */     if ((nullBits[1] & 0x8) != 0) {
/*  549 */       int fieldOffset11 = buf.getIntLE(offset + 207);
/*  550 */       int pos11 = offset + 259 + fieldOffset11;
/*  551 */       int sl = VarInt.peek(buf, pos11); pos11 += VarInt.length(buf, pos11) + sl;
/*  552 */       if (pos11 - offset > maxEnd) maxEnd = pos11 - offset; 
/*      */     } 
/*  554 */     if ((nullBits[1] & 0x10) != 0) {
/*  555 */       int fieldOffset12 = buf.getIntLE(offset + 211);
/*  556 */       int pos12 = offset + 259 + fieldOffset12;
/*  557 */       int sl = VarInt.peek(buf, pos12); pos12 += VarInt.length(buf, pos12) + sl;
/*  558 */       if (pos12 - offset > maxEnd) maxEnd = pos12 - offset; 
/*      */     } 
/*  560 */     if ((nullBits[2] & 0x2) != 0) {
/*  561 */       int fieldOffset13 = buf.getIntLE(offset + 215);
/*  562 */       int pos13 = offset + 259 + fieldOffset13;
/*  563 */       int sl = VarInt.peek(buf, pos13); pos13 += VarInt.length(buf, pos13) + sl;
/*  564 */       if (pos13 - offset > maxEnd) maxEnd = pos13 - offset; 
/*      */     } 
/*  566 */     if ((nullBits[2] & 0x4) != 0) {
/*  567 */       int fieldOffset14 = buf.getIntLE(offset + 219);
/*  568 */       int pos14 = offset + 259 + fieldOffset14;
/*  569 */       int arrLen = VarInt.peek(buf, pos14); pos14 += VarInt.length(buf, pos14) + arrLen * 4;
/*  570 */       if (pos14 - offset > maxEnd) maxEnd = pos14 - offset; 
/*      */     } 
/*  572 */     if ((nullBits[2] & 0x20) != 0) {
/*  573 */       int fieldOffset15 = buf.getIntLE(offset + 223);
/*  574 */       int pos15 = offset + 259 + fieldOffset15;
/*  575 */       int sl = VarInt.peek(buf, pos15); pos15 += VarInt.length(buf, pos15) + sl;
/*  576 */       if (pos15 - offset > maxEnd) maxEnd = pos15 - offset; 
/*      */     } 
/*  578 */     if ((nullBits[2] & 0x40) != 0) {
/*  579 */       int fieldOffset16 = buf.getIntLE(offset + 227);
/*  580 */       int pos16 = offset + 259 + fieldOffset16;
/*  581 */       pos16 += BlockGathering.computeBytesConsumed(buf, pos16);
/*  582 */       if (pos16 - offset > maxEnd) maxEnd = pos16 - offset; 
/*      */     } 
/*  584 */     if ((nullBits[3] & 0x1) != 0) {
/*  585 */       int fieldOffset17 = buf.getIntLE(offset + 231);
/*  586 */       int pos17 = offset + 259 + fieldOffset17;
/*  587 */       pos17 += ModelDisplay.computeBytesConsumed(buf, pos17);
/*  588 */       if (pos17 - offset > maxEnd) maxEnd = pos17 - offset; 
/*      */     } 
/*  590 */     if ((nullBits[3] & 0x2) != 0) {
/*  591 */       int fieldOffset18 = buf.getIntLE(offset + 235);
/*  592 */       int pos18 = offset + 259 + fieldOffset18;
/*  593 */       pos18 += RailConfig.computeBytesConsumed(buf, pos18);
/*  594 */       if (pos18 - offset > maxEnd) maxEnd = pos18 - offset; 
/*      */     } 
/*  596 */     if ((nullBits[3] & 0x4) != 0) {
/*  597 */       int fieldOffset19 = buf.getIntLE(offset + 239);
/*  598 */       int pos19 = offset + 259 + fieldOffset19;
/*  599 */       int dictLen = VarInt.peek(buf, pos19); pos19 += VarInt.length(buf, pos19);
/*  600 */       for (int i = 0; i < dictLen; ) { pos19++; pos19 += 4; i++; }
/*  601 */        if (pos19 - offset > maxEnd) maxEnd = pos19 - offset; 
/*      */     } 
/*  603 */     if ((nullBits[3] & 0x8) != 0) {
/*  604 */       int fieldOffset20 = buf.getIntLE(offset + 243);
/*  605 */       int pos20 = offset + 259 + fieldOffset20;
/*  606 */       int dictLen = VarInt.peek(buf, pos20); pos20 += VarInt.length(buf, pos20);
/*  607 */       for (int i = 0; i < dictLen; ) { int sl = VarInt.peek(buf, pos20); pos20 += VarInt.length(buf, pos20) + sl; pos20 += 4; i++; }
/*  608 */        if (pos20 - offset > maxEnd) maxEnd = pos20 - offset; 
/*      */     } 
/*  610 */     if ((nullBits[3] & 0x10) != 0) {
/*  611 */       int fieldOffset21 = buf.getIntLE(offset + 247);
/*  612 */       int pos21 = offset + 259 + fieldOffset21;
/*  613 */       int arrLen = VarInt.peek(buf, pos21); pos21 += VarInt.length(buf, pos21) + arrLen * 4;
/*  614 */       if (pos21 - offset > maxEnd) maxEnd = pos21 - offset; 
/*      */     } 
/*  616 */     if ((nullBits[3] & 0x20) != 0) {
/*  617 */       int fieldOffset22 = buf.getIntLE(offset + 251);
/*  618 */       int pos22 = offset + 259 + fieldOffset22;
/*  619 */       pos22 += Bench.computeBytesConsumed(buf, pos22);
/*  620 */       if (pos22 - offset > maxEnd) maxEnd = pos22 - offset; 
/*      */     } 
/*  622 */     if ((nullBits[3] & 0x40) != 0) {
/*  623 */       int fieldOffset23 = buf.getIntLE(offset + 255);
/*  624 */       int pos23 = offset + 259 + fieldOffset23;
/*  625 */       pos23 += ConnectedBlockRuleSet.computeBytesConsumed(buf, pos23);
/*  626 */       if (pos23 - offset > maxEnd) maxEnd = pos23 - offset; 
/*      */     } 
/*  628 */     return maxEnd;
/*      */   }
/*      */ 
/*      */   
/*      */   public void serialize(@Nonnull ByteBuf buf) {
/*  633 */     int startPos = buf.writerIndex();
/*  634 */     byte[] nullBits = new byte[4];
/*  635 */     if (this.item != null) nullBits[0] = (byte)(nullBits[0] | 0x1); 
/*  636 */     if (this.name != null) nullBits[0] = (byte)(nullBits[0] | 0x2); 
/*  637 */     if (this.shaderEffect != null) nullBits[0] = (byte)(nullBits[0] | 0x4); 
/*  638 */     if (this.model != null) nullBits[0] = (byte)(nullBits[0] | 0x8); 
/*  639 */     if (this.modelTexture != null) nullBits[0] = (byte)(nullBits[0] | 0x10); 
/*  640 */     if (this.modelAnimation != null) nullBits[0] = (byte)(nullBits[0] | 0x20); 
/*  641 */     if (this.support != null) nullBits[0] = (byte)(nullBits[0] | 0x40); 
/*  642 */     if (this.supporting != null) nullBits[0] = (byte)(nullBits[0] | 0x80); 
/*  643 */     if (this.cubeTextures != null) nullBits[1] = (byte)(nullBits[1] | 0x1); 
/*  644 */     if (this.cubeSideMaskTexture != null) nullBits[1] = (byte)(nullBits[1] | 0x2); 
/*  645 */     if (this.particles != null) nullBits[1] = (byte)(nullBits[1] | 0x4); 
/*  646 */     if (this.blockParticleSetId != null) nullBits[1] = (byte)(nullBits[1] | 0x8); 
/*  647 */     if (this.blockBreakingDecalId != null) nullBits[1] = (byte)(nullBits[1] | 0x10); 
/*  648 */     if (this.particleColor != null) nullBits[1] = (byte)(nullBits[1] | 0x20); 
/*  649 */     if (this.light != null) nullBits[1] = (byte)(nullBits[1] | 0x40); 
/*  650 */     if (this.tint != null) nullBits[1] = (byte)(nullBits[1] | 0x80); 
/*  651 */     if (this.biomeTint != null) nullBits[2] = (byte)(nullBits[2] | 0x1); 
/*  652 */     if (this.transitionTexture != null) nullBits[2] = (byte)(nullBits[2] | 0x2); 
/*  653 */     if (this.transitionToGroups != null) nullBits[2] = (byte)(nullBits[2] | 0x4); 
/*  654 */     if (this.movementSettings != null) nullBits[2] = (byte)(nullBits[2] | 0x8); 
/*  655 */     if (this.flags != null) nullBits[2] = (byte)(nullBits[2] | 0x10); 
/*  656 */     if (this.interactionHint != null) nullBits[2] = (byte)(nullBits[2] | 0x20); 
/*  657 */     if (this.gathering != null) nullBits[2] = (byte)(nullBits[2] | 0x40); 
/*  658 */     if (this.placementSettings != null) nullBits[2] = (byte)(nullBits[2] | 0x80); 
/*  659 */     if (this.display != null) nullBits[3] = (byte)(nullBits[3] | 0x1); 
/*  660 */     if (this.rail != null) nullBits[3] = (byte)(nullBits[3] | 0x2); 
/*  661 */     if (this.interactions != null) nullBits[3] = (byte)(nullBits[3] | 0x4); 
/*  662 */     if (this.states != null) nullBits[3] = (byte)(nullBits[3] | 0x8); 
/*  663 */     if (this.tagIndexes != null) nullBits[3] = (byte)(nullBits[3] | 0x10); 
/*  664 */     if (this.bench != null) nullBits[3] = (byte)(nullBits[3] | 0x20); 
/*  665 */     if (this.connectedBlockRuleSet != null) nullBits[3] = (byte)(nullBits[3] | 0x40); 
/*  666 */     buf.writeBytes(nullBits);
/*      */     
/*  668 */     buf.writeByte(this.unknown ? 1 : 0);
/*  669 */     buf.writeByte(this.drawType.getValue());
/*  670 */     buf.writeByte(this.material.getValue());
/*  671 */     buf.writeByte(this.opacity.getValue());
/*  672 */     buf.writeIntLE(this.hitbox);
/*  673 */     buf.writeIntLE(this.interactionHitbox);
/*  674 */     buf.writeFloatLE(this.modelScale);
/*  675 */     buf.writeByte(this.looping ? 1 : 0);
/*  676 */     buf.writeIntLE(this.maxSupportDistance);
/*  677 */     buf.writeByte(this.blockSupportsRequiredFor.getValue());
/*  678 */     buf.writeByte(this.requiresAlphaBlending ? 1 : 0);
/*  679 */     buf.writeByte(this.cubeShadingMode.getValue());
/*  680 */     buf.writeByte(this.randomRotation.getValue());
/*  681 */     buf.writeByte(this.variantRotation.getValue());
/*  682 */     buf.writeByte(this.rotationYawPlacementOffset.getValue());
/*  683 */     buf.writeIntLE(this.blockSoundSetIndex);
/*  684 */     buf.writeIntLE(this.ambientSoundEventIndex);
/*  685 */     if (this.particleColor != null) { this.particleColor.serialize(buf); } else { buf.writeZero(3); }
/*  686 */      if (this.light != null) { this.light.serialize(buf); } else { buf.writeZero(4); }
/*  687 */      if (this.tint != null) { this.tint.serialize(buf); } else { buf.writeZero(24); }
/*  688 */      if (this.biomeTint != null) { this.biomeTint.serialize(buf); } else { buf.writeZero(24); }
/*  689 */      buf.writeIntLE(this.group);
/*  690 */     if (this.movementSettings != null) { this.movementSettings.serialize(buf); } else { buf.writeZero(42); }
/*  691 */      if (this.flags != null) { this.flags.serialize(buf); } else { buf.writeZero(2); }
/*  692 */      if (this.placementSettings != null) { this.placementSettings.serialize(buf); } else { buf.writeZero(16); }
/*  693 */      buf.writeByte(this.ignoreSupportWhenPlaced ? 1 : 0);
/*  694 */     buf.writeIntLE(this.transitionToTag);
/*      */     
/*  696 */     int itemOffsetSlot = buf.writerIndex();
/*  697 */     buf.writeIntLE(0);
/*  698 */     int nameOffsetSlot = buf.writerIndex();
/*  699 */     buf.writeIntLE(0);
/*  700 */     int shaderEffectOffsetSlot = buf.writerIndex();
/*  701 */     buf.writeIntLE(0);
/*  702 */     int modelOffsetSlot = buf.writerIndex();
/*  703 */     buf.writeIntLE(0);
/*  704 */     int modelTextureOffsetSlot = buf.writerIndex();
/*  705 */     buf.writeIntLE(0);
/*  706 */     int modelAnimationOffsetSlot = buf.writerIndex();
/*  707 */     buf.writeIntLE(0);
/*  708 */     int supportOffsetSlot = buf.writerIndex();
/*  709 */     buf.writeIntLE(0);
/*  710 */     int supportingOffsetSlot = buf.writerIndex();
/*  711 */     buf.writeIntLE(0);
/*  712 */     int cubeTexturesOffsetSlot = buf.writerIndex();
/*  713 */     buf.writeIntLE(0);
/*  714 */     int cubeSideMaskTextureOffsetSlot = buf.writerIndex();
/*  715 */     buf.writeIntLE(0);
/*  716 */     int particlesOffsetSlot = buf.writerIndex();
/*  717 */     buf.writeIntLE(0);
/*  718 */     int blockParticleSetIdOffsetSlot = buf.writerIndex();
/*  719 */     buf.writeIntLE(0);
/*  720 */     int blockBreakingDecalIdOffsetSlot = buf.writerIndex();
/*  721 */     buf.writeIntLE(0);
/*  722 */     int transitionTextureOffsetSlot = buf.writerIndex();
/*  723 */     buf.writeIntLE(0);
/*  724 */     int transitionToGroupsOffsetSlot = buf.writerIndex();
/*  725 */     buf.writeIntLE(0);
/*  726 */     int interactionHintOffsetSlot = buf.writerIndex();
/*  727 */     buf.writeIntLE(0);
/*  728 */     int gatheringOffsetSlot = buf.writerIndex();
/*  729 */     buf.writeIntLE(0);
/*  730 */     int displayOffsetSlot = buf.writerIndex();
/*  731 */     buf.writeIntLE(0);
/*  732 */     int railOffsetSlot = buf.writerIndex();
/*  733 */     buf.writeIntLE(0);
/*  734 */     int interactionsOffsetSlot = buf.writerIndex();
/*  735 */     buf.writeIntLE(0);
/*  736 */     int statesOffsetSlot = buf.writerIndex();
/*  737 */     buf.writeIntLE(0);
/*  738 */     int tagIndexesOffsetSlot = buf.writerIndex();
/*  739 */     buf.writeIntLE(0);
/*  740 */     int benchOffsetSlot = buf.writerIndex();
/*  741 */     buf.writeIntLE(0);
/*  742 */     int connectedBlockRuleSetOffsetSlot = buf.writerIndex();
/*  743 */     buf.writeIntLE(0);
/*      */     
/*  745 */     int varBlockStart = buf.writerIndex();
/*  746 */     if (this.item != null) {
/*  747 */       buf.setIntLE(itemOffsetSlot, buf.writerIndex() - varBlockStart);
/*  748 */       PacketIO.writeVarString(buf, this.item, 4096000);
/*      */     } else {
/*  750 */       buf.setIntLE(itemOffsetSlot, -1);
/*      */     } 
/*  752 */     if (this.name != null) {
/*  753 */       buf.setIntLE(nameOffsetSlot, buf.writerIndex() - varBlockStart);
/*  754 */       PacketIO.writeVarString(buf, this.name, 4096000);
/*      */     } else {
/*  756 */       buf.setIntLE(nameOffsetSlot, -1);
/*      */     } 
/*  758 */     if (this.shaderEffect != null) {
/*  759 */       buf.setIntLE(shaderEffectOffsetSlot, buf.writerIndex() - varBlockStart);
/*  760 */       if (this.shaderEffect.length > 4096000) throw ProtocolException.arrayTooLong("ShaderEffect", this.shaderEffect.length, 4096000);  VarInt.write(buf, this.shaderEffect.length); for (ShaderType item : this.shaderEffect) buf.writeByte(item.getValue()); 
/*      */     } else {
/*  762 */       buf.setIntLE(shaderEffectOffsetSlot, -1);
/*      */     } 
/*  764 */     if (this.model != null) {
/*  765 */       buf.setIntLE(modelOffsetSlot, buf.writerIndex() - varBlockStart);
/*  766 */       PacketIO.writeVarString(buf, this.model, 4096000);
/*      */     } else {
/*  768 */       buf.setIntLE(modelOffsetSlot, -1);
/*      */     } 
/*  770 */     if (this.modelTexture != null) {
/*  771 */       buf.setIntLE(modelTextureOffsetSlot, buf.writerIndex() - varBlockStart);
/*  772 */       if (this.modelTexture.length > 4096000) throw ProtocolException.arrayTooLong("ModelTexture", this.modelTexture.length, 4096000);  VarInt.write(buf, this.modelTexture.length); for (ModelTexture item : this.modelTexture) item.serialize(buf); 
/*      */     } else {
/*  774 */       buf.setIntLE(modelTextureOffsetSlot, -1);
/*      */     } 
/*  776 */     if (this.modelAnimation != null) {
/*  777 */       buf.setIntLE(modelAnimationOffsetSlot, buf.writerIndex() - varBlockStart);
/*  778 */       PacketIO.writeVarString(buf, this.modelAnimation, 4096000);
/*      */     } else {
/*  780 */       buf.setIntLE(modelAnimationOffsetSlot, -1);
/*      */     } 
/*  782 */     if (this.support != null)
/*  783 */     { buf.setIntLE(supportOffsetSlot, buf.writerIndex() - varBlockStart);
/*  784 */       if (this.support.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Support", this.support.size(), 4096000);  VarInt.write(buf, this.support.size()); for (Map.Entry<BlockNeighbor, RequiredBlockFaceSupport[]> e : this.support.entrySet()) { buf.writeByte(((BlockNeighbor)e.getKey()).getValue()); VarInt.write(buf, ((RequiredBlockFaceSupport[])e.getValue()).length); for (RequiredBlockFaceSupport arrItem : (RequiredBlockFaceSupport[])e.getValue()) arrItem.serialize(buf);  }
/*      */        }
/*  786 */     else { buf.setIntLE(supportOffsetSlot, -1); }
/*      */     
/*  788 */     if (this.supporting != null)
/*  789 */     { buf.setIntLE(supportingOffsetSlot, buf.writerIndex() - varBlockStart);
/*  790 */       if (this.supporting.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Supporting", this.supporting.size(), 4096000);  VarInt.write(buf, this.supporting.size()); for (Map.Entry<BlockNeighbor, BlockFaceSupport[]> e : this.supporting.entrySet()) { buf.writeByte(((BlockNeighbor)e.getKey()).getValue()); VarInt.write(buf, ((BlockFaceSupport[])e.getValue()).length); for (BlockFaceSupport arrItem : (BlockFaceSupport[])e.getValue()) arrItem.serialize(buf);  }
/*      */        }
/*  792 */     else { buf.setIntLE(supportingOffsetSlot, -1); }
/*      */     
/*  794 */     if (this.cubeTextures != null) {
/*  795 */       buf.setIntLE(cubeTexturesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  796 */       if (this.cubeTextures.length > 4096000) throw ProtocolException.arrayTooLong("CubeTextures", this.cubeTextures.length, 4096000);  VarInt.write(buf, this.cubeTextures.length); for (BlockTextures item : this.cubeTextures) item.serialize(buf); 
/*      */     } else {
/*  798 */       buf.setIntLE(cubeTexturesOffsetSlot, -1);
/*      */     } 
/*  800 */     if (this.cubeSideMaskTexture != null) {
/*  801 */       buf.setIntLE(cubeSideMaskTextureOffsetSlot, buf.writerIndex() - varBlockStart);
/*  802 */       PacketIO.writeVarString(buf, this.cubeSideMaskTexture, 4096000);
/*      */     } else {
/*  804 */       buf.setIntLE(cubeSideMaskTextureOffsetSlot, -1);
/*      */     } 
/*  806 */     if (this.particles != null) {
/*  807 */       buf.setIntLE(particlesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  808 */       if (this.particles.length > 4096000) throw ProtocolException.arrayTooLong("Particles", this.particles.length, 4096000);  VarInt.write(buf, this.particles.length); for (ModelParticle item : this.particles) item.serialize(buf); 
/*      */     } else {
/*  810 */       buf.setIntLE(particlesOffsetSlot, -1);
/*      */     } 
/*  812 */     if (this.blockParticleSetId != null) {
/*  813 */       buf.setIntLE(blockParticleSetIdOffsetSlot, buf.writerIndex() - varBlockStart);
/*  814 */       PacketIO.writeVarString(buf, this.blockParticleSetId, 4096000);
/*      */     } else {
/*  816 */       buf.setIntLE(blockParticleSetIdOffsetSlot, -1);
/*      */     } 
/*  818 */     if (this.blockBreakingDecalId != null) {
/*  819 */       buf.setIntLE(blockBreakingDecalIdOffsetSlot, buf.writerIndex() - varBlockStart);
/*  820 */       PacketIO.writeVarString(buf, this.blockBreakingDecalId, 4096000);
/*      */     } else {
/*  822 */       buf.setIntLE(blockBreakingDecalIdOffsetSlot, -1);
/*      */     } 
/*  824 */     if (this.transitionTexture != null) {
/*  825 */       buf.setIntLE(transitionTextureOffsetSlot, buf.writerIndex() - varBlockStart);
/*  826 */       PacketIO.writeVarString(buf, this.transitionTexture, 4096000);
/*      */     } else {
/*  828 */       buf.setIntLE(transitionTextureOffsetSlot, -1);
/*      */     } 
/*  830 */     if (this.transitionToGroups != null) {
/*  831 */       buf.setIntLE(transitionToGroupsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  832 */       if (this.transitionToGroups.length > 4096000) throw ProtocolException.arrayTooLong("TransitionToGroups", this.transitionToGroups.length, 4096000);  VarInt.write(buf, this.transitionToGroups.length); for (int item : this.transitionToGroups) buf.writeIntLE(item); 
/*      */     } else {
/*  834 */       buf.setIntLE(transitionToGroupsOffsetSlot, -1);
/*      */     } 
/*  836 */     if (this.interactionHint != null) {
/*  837 */       buf.setIntLE(interactionHintOffsetSlot, buf.writerIndex() - varBlockStart);
/*  838 */       PacketIO.writeVarString(buf, this.interactionHint, 4096000);
/*      */     } else {
/*  840 */       buf.setIntLE(interactionHintOffsetSlot, -1);
/*      */     } 
/*  842 */     if (this.gathering != null) {
/*  843 */       buf.setIntLE(gatheringOffsetSlot, buf.writerIndex() - varBlockStart);
/*  844 */       this.gathering.serialize(buf);
/*      */     } else {
/*  846 */       buf.setIntLE(gatheringOffsetSlot, -1);
/*      */     } 
/*  848 */     if (this.display != null) {
/*  849 */       buf.setIntLE(displayOffsetSlot, buf.writerIndex() - varBlockStart);
/*  850 */       this.display.serialize(buf);
/*      */     } else {
/*  852 */       buf.setIntLE(displayOffsetSlot, -1);
/*      */     } 
/*  854 */     if (this.rail != null) {
/*  855 */       buf.setIntLE(railOffsetSlot, buf.writerIndex() - varBlockStart);
/*  856 */       this.rail.serialize(buf);
/*      */     } else {
/*  858 */       buf.setIntLE(railOffsetSlot, -1);
/*      */     } 
/*  860 */     if (this.interactions != null)
/*  861 */     { buf.setIntLE(interactionsOffsetSlot, buf.writerIndex() - varBlockStart);
/*  862 */       if (this.interactions.size() > 4096000) throw ProtocolException.dictionaryTooLarge("Interactions", this.interactions.size(), 4096000);  VarInt.write(buf, this.interactions.size()); for (Map.Entry<InteractionType, Integer> e : this.interactions.entrySet()) { buf.writeByte(((InteractionType)e.getKey()).getValue()); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*      */        }
/*  864 */     else { buf.setIntLE(interactionsOffsetSlot, -1); }
/*      */     
/*  866 */     if (this.states != null)
/*  867 */     { buf.setIntLE(statesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  868 */       if (this.states.size() > 4096000) throw ProtocolException.dictionaryTooLarge("States", this.states.size(), 4096000);  VarInt.write(buf, this.states.size()); for (Map.Entry<String, Integer> e : this.states.entrySet()) { PacketIO.writeVarString(buf, e.getKey(), 4096000); buf.writeIntLE(((Integer)e.getValue()).intValue()); }
/*      */        }
/*  870 */     else { buf.setIntLE(statesOffsetSlot, -1); }
/*      */     
/*  872 */     if (this.tagIndexes != null) {
/*  873 */       buf.setIntLE(tagIndexesOffsetSlot, buf.writerIndex() - varBlockStart);
/*  874 */       if (this.tagIndexes.length > 4096000) throw ProtocolException.arrayTooLong("TagIndexes", this.tagIndexes.length, 4096000);  VarInt.write(buf, this.tagIndexes.length); for (int item : this.tagIndexes) buf.writeIntLE(item); 
/*      */     } else {
/*  876 */       buf.setIntLE(tagIndexesOffsetSlot, -1);
/*      */     } 
/*  878 */     if (this.bench != null) {
/*  879 */       buf.setIntLE(benchOffsetSlot, buf.writerIndex() - varBlockStart);
/*  880 */       this.bench.serialize(buf);
/*      */     } else {
/*  882 */       buf.setIntLE(benchOffsetSlot, -1);
/*      */     } 
/*  884 */     if (this.connectedBlockRuleSet != null) {
/*  885 */       buf.setIntLE(connectedBlockRuleSetOffsetSlot, buf.writerIndex() - varBlockStart);
/*  886 */       this.connectedBlockRuleSet.serialize(buf);
/*      */     } else {
/*  888 */       buf.setIntLE(connectedBlockRuleSetOffsetSlot, -1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeSize() {
/*  894 */     int size = 259;
/*  895 */     if (this.item != null) size += PacketIO.stringSize(this.item); 
/*  896 */     if (this.name != null) size += PacketIO.stringSize(this.name); 
/*  897 */     if (this.shaderEffect != null) size += VarInt.size(this.shaderEffect.length) + this.shaderEffect.length * 1; 
/*  898 */     if (this.model != null) size += PacketIO.stringSize(this.model); 
/*  899 */     if (this.modelTexture != null) {
/*  900 */       int modelTextureSize = 0;
/*  901 */       for (ModelTexture elem : this.modelTexture) modelTextureSize += elem.computeSize(); 
/*  902 */       size += VarInt.size(this.modelTexture.length) + modelTextureSize;
/*      */     } 
/*  904 */     if (this.modelAnimation != null) size += PacketIO.stringSize(this.modelAnimation); 
/*  905 */     if (this.support != null) {
/*  906 */       int supportSize = 0;
/*  907 */       for (Map.Entry<BlockNeighbor, RequiredBlockFaceSupport[]> kvp : this.support.entrySet()) supportSize += 1 + VarInt.size(((RequiredBlockFaceSupport[])kvp.getValue()).length) + Arrays.<RequiredBlockFaceSupport>stream(kvp.getValue()).mapToInt(inner -> inner.computeSize()).sum(); 
/*  908 */       size += VarInt.size(this.support.size()) + supportSize;
/*      */     } 
/*  910 */     if (this.supporting != null) {
/*  911 */       int supportingSize = 0;
/*  912 */       for (Map.Entry<BlockNeighbor, BlockFaceSupport[]> kvp : this.supporting.entrySet()) supportingSize += 1 + VarInt.size(((BlockFaceSupport[])kvp.getValue()).length) + Arrays.<BlockFaceSupport>stream(kvp.getValue()).mapToInt(inner -> inner.computeSize()).sum(); 
/*  913 */       size += VarInt.size(this.supporting.size()) + supportingSize;
/*      */     } 
/*  915 */     if (this.cubeTextures != null) {
/*  916 */       int cubeTexturesSize = 0;
/*  917 */       for (BlockTextures elem : this.cubeTextures) cubeTexturesSize += elem.computeSize(); 
/*  918 */       size += VarInt.size(this.cubeTextures.length) + cubeTexturesSize;
/*      */     } 
/*  920 */     if (this.cubeSideMaskTexture != null) size += PacketIO.stringSize(this.cubeSideMaskTexture); 
/*  921 */     if (this.particles != null) {
/*  922 */       int particlesSize = 0;
/*  923 */       for (ModelParticle elem : this.particles) particlesSize += elem.computeSize(); 
/*  924 */       size += VarInt.size(this.particles.length) + particlesSize;
/*      */     } 
/*  926 */     if (this.blockParticleSetId != null) size += PacketIO.stringSize(this.blockParticleSetId); 
/*  927 */     if (this.blockBreakingDecalId != null) size += PacketIO.stringSize(this.blockBreakingDecalId); 
/*  928 */     if (this.transitionTexture != null) size += PacketIO.stringSize(this.transitionTexture); 
/*  929 */     if (this.transitionToGroups != null) size += VarInt.size(this.transitionToGroups.length) + this.transitionToGroups.length * 4; 
/*  930 */     if (this.interactionHint != null) size += PacketIO.stringSize(this.interactionHint); 
/*  931 */     if (this.gathering != null) size += this.gathering.computeSize(); 
/*  932 */     if (this.display != null) size += this.display.computeSize(); 
/*  933 */     if (this.rail != null) size += this.rail.computeSize(); 
/*  934 */     if (this.interactions != null) size += VarInt.size(this.interactions.size()) + this.interactions.size() * 5; 
/*  935 */     if (this.states != null) {
/*  936 */       int statesSize = 0;
/*  937 */       for (Map.Entry<String, Integer> kvp : this.states.entrySet()) statesSize += PacketIO.stringSize(kvp.getKey()) + 4; 
/*  938 */       size += VarInt.size(this.states.size()) + statesSize;
/*      */     } 
/*  940 */     if (this.tagIndexes != null) size += VarInt.size(this.tagIndexes.length) + this.tagIndexes.length * 4; 
/*  941 */     if (this.bench != null) size += this.bench.computeSize(); 
/*  942 */     if (this.connectedBlockRuleSet != null) size += this.connectedBlockRuleSet.computeSize();
/*      */     
/*  944 */     return size;
/*      */   }
/*      */   
/*      */   public static ValidationResult validateStructure(@Nonnull ByteBuf buffer, int offset) {
/*  948 */     if (buffer.readableBytes() - offset < 259) {
/*  949 */       return ValidationResult.error("Buffer too small: expected at least 259 bytes");
/*      */     }
/*      */     
/*  952 */     byte[] nullBits = PacketIO.readBytes(buffer, offset, 4);
/*      */     
/*  954 */     if ((nullBits[0] & 0x1) != 0) {
/*  955 */       int itemOffset = buffer.getIntLE(offset + 163);
/*  956 */       if (itemOffset < 0) {
/*  957 */         return ValidationResult.error("Invalid offset for Item");
/*      */       }
/*  959 */       int pos = offset + 259 + itemOffset;
/*  960 */       if (pos >= buffer.writerIndex()) {
/*  961 */         return ValidationResult.error("Offset out of bounds for Item");
/*      */       }
/*  963 */       int itemLen = VarInt.peek(buffer, pos);
/*  964 */       if (itemLen < 0) {
/*  965 */         return ValidationResult.error("Invalid string length for Item");
/*      */       }
/*  967 */       if (itemLen > 4096000) {
/*  968 */         return ValidationResult.error("Item exceeds max length 4096000");
/*      */       }
/*  970 */       pos += VarInt.length(buffer, pos);
/*  971 */       pos += itemLen;
/*  972 */       if (pos > buffer.writerIndex()) {
/*  973 */         return ValidationResult.error("Buffer overflow reading Item");
/*      */       }
/*      */     } 
/*      */     
/*  977 */     if ((nullBits[0] & 0x2) != 0) {
/*  978 */       int nameOffset = buffer.getIntLE(offset + 167);
/*  979 */       if (nameOffset < 0) {
/*  980 */         return ValidationResult.error("Invalid offset for Name");
/*      */       }
/*  982 */       int pos = offset + 259 + nameOffset;
/*  983 */       if (pos >= buffer.writerIndex()) {
/*  984 */         return ValidationResult.error("Offset out of bounds for Name");
/*      */       }
/*  986 */       int nameLen = VarInt.peek(buffer, pos);
/*  987 */       if (nameLen < 0) {
/*  988 */         return ValidationResult.error("Invalid string length for Name");
/*      */       }
/*  990 */       if (nameLen > 4096000) {
/*  991 */         return ValidationResult.error("Name exceeds max length 4096000");
/*      */       }
/*  993 */       pos += VarInt.length(buffer, pos);
/*  994 */       pos += nameLen;
/*  995 */       if (pos > buffer.writerIndex()) {
/*  996 */         return ValidationResult.error("Buffer overflow reading Name");
/*      */       }
/*      */     } 
/*      */     
/* 1000 */     if ((nullBits[0] & 0x4) != 0) {
/* 1001 */       int shaderEffectOffset = buffer.getIntLE(offset + 171);
/* 1002 */       if (shaderEffectOffset < 0) {
/* 1003 */         return ValidationResult.error("Invalid offset for ShaderEffect");
/*      */       }
/* 1005 */       int pos = offset + 259 + shaderEffectOffset;
/* 1006 */       if (pos >= buffer.writerIndex()) {
/* 1007 */         return ValidationResult.error("Offset out of bounds for ShaderEffect");
/*      */       }
/* 1009 */       int shaderEffectCount = VarInt.peek(buffer, pos);
/* 1010 */       if (shaderEffectCount < 0) {
/* 1011 */         return ValidationResult.error("Invalid array count for ShaderEffect");
/*      */       }
/* 1013 */       if (shaderEffectCount > 4096000) {
/* 1014 */         return ValidationResult.error("ShaderEffect exceeds max length 4096000");
/*      */       }
/* 1016 */       pos += VarInt.length(buffer, pos);
/* 1017 */       pos += shaderEffectCount * 1;
/* 1018 */       if (pos > buffer.writerIndex()) {
/* 1019 */         return ValidationResult.error("Buffer overflow reading ShaderEffect");
/*      */       }
/*      */     } 
/*      */     
/* 1023 */     if ((nullBits[0] & 0x8) != 0) {
/* 1024 */       int modelOffset = buffer.getIntLE(offset + 175);
/* 1025 */       if (modelOffset < 0) {
/* 1026 */         return ValidationResult.error("Invalid offset for Model");
/*      */       }
/* 1028 */       int pos = offset + 259 + modelOffset;
/* 1029 */       if (pos >= buffer.writerIndex()) {
/* 1030 */         return ValidationResult.error("Offset out of bounds for Model");
/*      */       }
/* 1032 */       int modelLen = VarInt.peek(buffer, pos);
/* 1033 */       if (modelLen < 0) {
/* 1034 */         return ValidationResult.error("Invalid string length for Model");
/*      */       }
/* 1036 */       if (modelLen > 4096000) {
/* 1037 */         return ValidationResult.error("Model exceeds max length 4096000");
/*      */       }
/* 1039 */       pos += VarInt.length(buffer, pos);
/* 1040 */       pos += modelLen;
/* 1041 */       if (pos > buffer.writerIndex()) {
/* 1042 */         return ValidationResult.error("Buffer overflow reading Model");
/*      */       }
/*      */     } 
/*      */     
/* 1046 */     if ((nullBits[0] & 0x10) != 0) {
/* 1047 */       int modelTextureOffset = buffer.getIntLE(offset + 179);
/* 1048 */       if (modelTextureOffset < 0) {
/* 1049 */         return ValidationResult.error("Invalid offset for ModelTexture");
/*      */       }
/* 1051 */       int pos = offset + 259 + modelTextureOffset;
/* 1052 */       if (pos >= buffer.writerIndex()) {
/* 1053 */         return ValidationResult.error("Offset out of bounds for ModelTexture");
/*      */       }
/* 1055 */       int modelTextureCount = VarInt.peek(buffer, pos);
/* 1056 */       if (modelTextureCount < 0) {
/* 1057 */         return ValidationResult.error("Invalid array count for ModelTexture");
/*      */       }
/* 1059 */       if (modelTextureCount > 4096000) {
/* 1060 */         return ValidationResult.error("ModelTexture exceeds max length 4096000");
/*      */       }
/* 1062 */       pos += VarInt.length(buffer, pos);
/* 1063 */       for (int i = 0; i < modelTextureCount; i++) {
/* 1064 */         ValidationResult structResult = ModelTexture.validateStructure(buffer, pos);
/* 1065 */         if (!structResult.isValid()) {
/* 1066 */           return ValidationResult.error("Invalid ModelTexture in ModelTexture[" + i + "]: " + structResult.error());
/*      */         }
/* 1068 */         pos += ModelTexture.computeBytesConsumed(buffer, pos);
/*      */       } 
/*      */     } 
/*      */     
/* 1072 */     if ((nullBits[0] & 0x20) != 0) {
/* 1073 */       int modelAnimationOffset = buffer.getIntLE(offset + 183);
/* 1074 */       if (modelAnimationOffset < 0) {
/* 1075 */         return ValidationResult.error("Invalid offset for ModelAnimation");
/*      */       }
/* 1077 */       int pos = offset + 259 + modelAnimationOffset;
/* 1078 */       if (pos >= buffer.writerIndex()) {
/* 1079 */         return ValidationResult.error("Offset out of bounds for ModelAnimation");
/*      */       }
/* 1081 */       int modelAnimationLen = VarInt.peek(buffer, pos);
/* 1082 */       if (modelAnimationLen < 0) {
/* 1083 */         return ValidationResult.error("Invalid string length for ModelAnimation");
/*      */       }
/* 1085 */       if (modelAnimationLen > 4096000) {
/* 1086 */         return ValidationResult.error("ModelAnimation exceeds max length 4096000");
/*      */       }
/* 1088 */       pos += VarInt.length(buffer, pos);
/* 1089 */       pos += modelAnimationLen;
/* 1090 */       if (pos > buffer.writerIndex()) {
/* 1091 */         return ValidationResult.error("Buffer overflow reading ModelAnimation");
/*      */       }
/*      */     } 
/*      */     
/* 1095 */     if ((nullBits[0] & 0x40) != 0) {
/* 1096 */       int supportOffset = buffer.getIntLE(offset + 187);
/* 1097 */       if (supportOffset < 0) {
/* 1098 */         return ValidationResult.error("Invalid offset for Support");
/*      */       }
/* 1100 */       int pos = offset + 259 + supportOffset;
/* 1101 */       if (pos >= buffer.writerIndex()) {
/* 1102 */         return ValidationResult.error("Offset out of bounds for Support");
/*      */       }
/* 1104 */       int supportCount = VarInt.peek(buffer, pos);
/* 1105 */       if (supportCount < 0) {
/* 1106 */         return ValidationResult.error("Invalid dictionary count for Support");
/*      */       }
/* 1108 */       if (supportCount > 4096000) {
/* 1109 */         return ValidationResult.error("Support exceeds max length 4096000");
/*      */       }
/* 1111 */       pos += VarInt.length(buffer, pos);
/* 1112 */       for (int i = 0; i < supportCount; i++) {
/* 1113 */         pos++;
/*      */         
/* 1115 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 1116 */         if (valueArrCount < 0) {
/* 1117 */           return ValidationResult.error("Invalid array count for value");
/*      */         }
/* 1119 */         pos += VarInt.length(buffer, pos);
/* 1120 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 1121 */           pos += RequiredBlockFaceSupport.computeBytesConsumed(buffer, pos);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1127 */     if ((nullBits[0] & 0x80) != 0) {
/* 1128 */       int supportingOffset = buffer.getIntLE(offset + 191);
/* 1129 */       if (supportingOffset < 0) {
/* 1130 */         return ValidationResult.error("Invalid offset for Supporting");
/*      */       }
/* 1132 */       int pos = offset + 259 + supportingOffset;
/* 1133 */       if (pos >= buffer.writerIndex()) {
/* 1134 */         return ValidationResult.error("Offset out of bounds for Supporting");
/*      */       }
/* 1136 */       int supportingCount = VarInt.peek(buffer, pos);
/* 1137 */       if (supportingCount < 0) {
/* 1138 */         return ValidationResult.error("Invalid dictionary count for Supporting");
/*      */       }
/* 1140 */       if (supportingCount > 4096000) {
/* 1141 */         return ValidationResult.error("Supporting exceeds max length 4096000");
/*      */       }
/* 1143 */       pos += VarInt.length(buffer, pos);
/* 1144 */       for (int i = 0; i < supportingCount; i++) {
/* 1145 */         pos++;
/*      */         
/* 1147 */         int valueArrCount = VarInt.peek(buffer, pos);
/* 1148 */         if (valueArrCount < 0) {
/* 1149 */           return ValidationResult.error("Invalid array count for value");
/*      */         }
/* 1151 */         pos += VarInt.length(buffer, pos);
/* 1152 */         for (int valueArrIdx = 0; valueArrIdx < valueArrCount; valueArrIdx++) {
/* 1153 */           pos += BlockFaceSupport.computeBytesConsumed(buffer, pos);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1159 */     if ((nullBits[1] & 0x1) != 0) {
/* 1160 */       int cubeTexturesOffset = buffer.getIntLE(offset + 195);
/* 1161 */       if (cubeTexturesOffset < 0) {
/* 1162 */         return ValidationResult.error("Invalid offset for CubeTextures");
/*      */       }
/* 1164 */       int pos = offset + 259 + cubeTexturesOffset;
/* 1165 */       if (pos >= buffer.writerIndex()) {
/* 1166 */         return ValidationResult.error("Offset out of bounds for CubeTextures");
/*      */       }
/* 1168 */       int cubeTexturesCount = VarInt.peek(buffer, pos);
/* 1169 */       if (cubeTexturesCount < 0) {
/* 1170 */         return ValidationResult.error("Invalid array count for CubeTextures");
/*      */       }
/* 1172 */       if (cubeTexturesCount > 4096000) {
/* 1173 */         return ValidationResult.error("CubeTextures exceeds max length 4096000");
/*      */       }
/* 1175 */       pos += VarInt.length(buffer, pos);
/* 1176 */       for (int i = 0; i < cubeTexturesCount; i++) {
/* 1177 */         ValidationResult structResult = BlockTextures.validateStructure(buffer, pos);
/* 1178 */         if (!structResult.isValid()) {
/* 1179 */           return ValidationResult.error("Invalid BlockTextures in CubeTextures[" + i + "]: " + structResult.error());
/*      */         }
/* 1181 */         pos += BlockTextures.computeBytesConsumed(buffer, pos);
/*      */       } 
/*      */     } 
/*      */     
/* 1185 */     if ((nullBits[1] & 0x2) != 0) {
/* 1186 */       int cubeSideMaskTextureOffset = buffer.getIntLE(offset + 199);
/* 1187 */       if (cubeSideMaskTextureOffset < 0) {
/* 1188 */         return ValidationResult.error("Invalid offset for CubeSideMaskTexture");
/*      */       }
/* 1190 */       int pos = offset + 259 + cubeSideMaskTextureOffset;
/* 1191 */       if (pos >= buffer.writerIndex()) {
/* 1192 */         return ValidationResult.error("Offset out of bounds for CubeSideMaskTexture");
/*      */       }
/* 1194 */       int cubeSideMaskTextureLen = VarInt.peek(buffer, pos);
/* 1195 */       if (cubeSideMaskTextureLen < 0) {
/* 1196 */         return ValidationResult.error("Invalid string length for CubeSideMaskTexture");
/*      */       }
/* 1198 */       if (cubeSideMaskTextureLen > 4096000) {
/* 1199 */         return ValidationResult.error("CubeSideMaskTexture exceeds max length 4096000");
/*      */       }
/* 1201 */       pos += VarInt.length(buffer, pos);
/* 1202 */       pos += cubeSideMaskTextureLen;
/* 1203 */       if (pos > buffer.writerIndex()) {
/* 1204 */         return ValidationResult.error("Buffer overflow reading CubeSideMaskTexture");
/*      */       }
/*      */     } 
/*      */     
/* 1208 */     if ((nullBits[1] & 0x4) != 0) {
/* 1209 */       int particlesOffset = buffer.getIntLE(offset + 203);
/* 1210 */       if (particlesOffset < 0) {
/* 1211 */         return ValidationResult.error("Invalid offset for Particles");
/*      */       }
/* 1213 */       int pos = offset + 259 + particlesOffset;
/* 1214 */       if (pos >= buffer.writerIndex()) {
/* 1215 */         return ValidationResult.error("Offset out of bounds for Particles");
/*      */       }
/* 1217 */       int particlesCount = VarInt.peek(buffer, pos);
/* 1218 */       if (particlesCount < 0) {
/* 1219 */         return ValidationResult.error("Invalid array count for Particles");
/*      */       }
/* 1221 */       if (particlesCount > 4096000) {
/* 1222 */         return ValidationResult.error("Particles exceeds max length 4096000");
/*      */       }
/* 1224 */       pos += VarInt.length(buffer, pos);
/* 1225 */       for (int i = 0; i < particlesCount; i++) {
/* 1226 */         ValidationResult structResult = ModelParticle.validateStructure(buffer, pos);
/* 1227 */         if (!structResult.isValid()) {
/* 1228 */           return ValidationResult.error("Invalid ModelParticle in Particles[" + i + "]: " + structResult.error());
/*      */         }
/* 1230 */         pos += ModelParticle.computeBytesConsumed(buffer, pos);
/*      */       } 
/*      */     } 
/*      */     
/* 1234 */     if ((nullBits[1] & 0x8) != 0) {
/* 1235 */       int blockParticleSetIdOffset = buffer.getIntLE(offset + 207);
/* 1236 */       if (blockParticleSetIdOffset < 0) {
/* 1237 */         return ValidationResult.error("Invalid offset for BlockParticleSetId");
/*      */       }
/* 1239 */       int pos = offset + 259 + blockParticleSetIdOffset;
/* 1240 */       if (pos >= buffer.writerIndex()) {
/* 1241 */         return ValidationResult.error("Offset out of bounds for BlockParticleSetId");
/*      */       }
/* 1243 */       int blockParticleSetIdLen = VarInt.peek(buffer, pos);
/* 1244 */       if (blockParticleSetIdLen < 0) {
/* 1245 */         return ValidationResult.error("Invalid string length for BlockParticleSetId");
/*      */       }
/* 1247 */       if (blockParticleSetIdLen > 4096000) {
/* 1248 */         return ValidationResult.error("BlockParticleSetId exceeds max length 4096000");
/*      */       }
/* 1250 */       pos += VarInt.length(buffer, pos);
/* 1251 */       pos += blockParticleSetIdLen;
/* 1252 */       if (pos > buffer.writerIndex()) {
/* 1253 */         return ValidationResult.error("Buffer overflow reading BlockParticleSetId");
/*      */       }
/*      */     } 
/*      */     
/* 1257 */     if ((nullBits[1] & 0x10) != 0) {
/* 1258 */       int blockBreakingDecalIdOffset = buffer.getIntLE(offset + 211);
/* 1259 */       if (blockBreakingDecalIdOffset < 0) {
/* 1260 */         return ValidationResult.error("Invalid offset for BlockBreakingDecalId");
/*      */       }
/* 1262 */       int pos = offset + 259 + blockBreakingDecalIdOffset;
/* 1263 */       if (pos >= buffer.writerIndex()) {
/* 1264 */         return ValidationResult.error("Offset out of bounds for BlockBreakingDecalId");
/*      */       }
/* 1266 */       int blockBreakingDecalIdLen = VarInt.peek(buffer, pos);
/* 1267 */       if (blockBreakingDecalIdLen < 0) {
/* 1268 */         return ValidationResult.error("Invalid string length for BlockBreakingDecalId");
/*      */       }
/* 1270 */       if (blockBreakingDecalIdLen > 4096000) {
/* 1271 */         return ValidationResult.error("BlockBreakingDecalId exceeds max length 4096000");
/*      */       }
/* 1273 */       pos += VarInt.length(buffer, pos);
/* 1274 */       pos += blockBreakingDecalIdLen;
/* 1275 */       if (pos > buffer.writerIndex()) {
/* 1276 */         return ValidationResult.error("Buffer overflow reading BlockBreakingDecalId");
/*      */       }
/*      */     } 
/*      */     
/* 1280 */     if ((nullBits[2] & 0x2) != 0) {
/* 1281 */       int transitionTextureOffset = buffer.getIntLE(offset + 215);
/* 1282 */       if (transitionTextureOffset < 0) {
/* 1283 */         return ValidationResult.error("Invalid offset for TransitionTexture");
/*      */       }
/* 1285 */       int pos = offset + 259 + transitionTextureOffset;
/* 1286 */       if (pos >= buffer.writerIndex()) {
/* 1287 */         return ValidationResult.error("Offset out of bounds for TransitionTexture");
/*      */       }
/* 1289 */       int transitionTextureLen = VarInt.peek(buffer, pos);
/* 1290 */       if (transitionTextureLen < 0) {
/* 1291 */         return ValidationResult.error("Invalid string length for TransitionTexture");
/*      */       }
/* 1293 */       if (transitionTextureLen > 4096000) {
/* 1294 */         return ValidationResult.error("TransitionTexture exceeds max length 4096000");
/*      */       }
/* 1296 */       pos += VarInt.length(buffer, pos);
/* 1297 */       pos += transitionTextureLen;
/* 1298 */       if (pos > buffer.writerIndex()) {
/* 1299 */         return ValidationResult.error("Buffer overflow reading TransitionTexture");
/*      */       }
/*      */     } 
/*      */     
/* 1303 */     if ((nullBits[2] & 0x4) != 0) {
/* 1304 */       int transitionToGroupsOffset = buffer.getIntLE(offset + 219);
/* 1305 */       if (transitionToGroupsOffset < 0) {
/* 1306 */         return ValidationResult.error("Invalid offset for TransitionToGroups");
/*      */       }
/* 1308 */       int pos = offset + 259 + transitionToGroupsOffset;
/* 1309 */       if (pos >= buffer.writerIndex()) {
/* 1310 */         return ValidationResult.error("Offset out of bounds for TransitionToGroups");
/*      */       }
/* 1312 */       int transitionToGroupsCount = VarInt.peek(buffer, pos);
/* 1313 */       if (transitionToGroupsCount < 0) {
/* 1314 */         return ValidationResult.error("Invalid array count for TransitionToGroups");
/*      */       }
/* 1316 */       if (transitionToGroupsCount > 4096000) {
/* 1317 */         return ValidationResult.error("TransitionToGroups exceeds max length 4096000");
/*      */       }
/* 1319 */       pos += VarInt.length(buffer, pos);
/* 1320 */       pos += transitionToGroupsCount * 4;
/* 1321 */       if (pos > buffer.writerIndex()) {
/* 1322 */         return ValidationResult.error("Buffer overflow reading TransitionToGroups");
/*      */       }
/*      */     } 
/*      */     
/* 1326 */     if ((nullBits[2] & 0x20) != 0) {
/* 1327 */       int interactionHintOffset = buffer.getIntLE(offset + 223);
/* 1328 */       if (interactionHintOffset < 0) {
/* 1329 */         return ValidationResult.error("Invalid offset for InteractionHint");
/*      */       }
/* 1331 */       int pos = offset + 259 + interactionHintOffset;
/* 1332 */       if (pos >= buffer.writerIndex()) {
/* 1333 */         return ValidationResult.error("Offset out of bounds for InteractionHint");
/*      */       }
/* 1335 */       int interactionHintLen = VarInt.peek(buffer, pos);
/* 1336 */       if (interactionHintLen < 0) {
/* 1337 */         return ValidationResult.error("Invalid string length for InteractionHint");
/*      */       }
/* 1339 */       if (interactionHintLen > 4096000) {
/* 1340 */         return ValidationResult.error("InteractionHint exceeds max length 4096000");
/*      */       }
/* 1342 */       pos += VarInt.length(buffer, pos);
/* 1343 */       pos += interactionHintLen;
/* 1344 */       if (pos > buffer.writerIndex()) {
/* 1345 */         return ValidationResult.error("Buffer overflow reading InteractionHint");
/*      */       }
/*      */     } 
/*      */     
/* 1349 */     if ((nullBits[2] & 0x40) != 0) {
/* 1350 */       int gatheringOffset = buffer.getIntLE(offset + 227);
/* 1351 */       if (gatheringOffset < 0) {
/* 1352 */         return ValidationResult.error("Invalid offset for Gathering");
/*      */       }
/* 1354 */       int pos = offset + 259 + gatheringOffset;
/* 1355 */       if (pos >= buffer.writerIndex()) {
/* 1356 */         return ValidationResult.error("Offset out of bounds for Gathering");
/*      */       }
/* 1358 */       ValidationResult gatheringResult = BlockGathering.validateStructure(buffer, pos);
/* 1359 */       if (!gatheringResult.isValid()) {
/* 1360 */         return ValidationResult.error("Invalid Gathering: " + gatheringResult.error());
/*      */       }
/* 1362 */       pos += BlockGathering.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1365 */     if ((nullBits[3] & 0x1) != 0) {
/* 1366 */       int displayOffset = buffer.getIntLE(offset + 231);
/* 1367 */       if (displayOffset < 0) {
/* 1368 */         return ValidationResult.error("Invalid offset for Display");
/*      */       }
/* 1370 */       int pos = offset + 259 + displayOffset;
/* 1371 */       if (pos >= buffer.writerIndex()) {
/* 1372 */         return ValidationResult.error("Offset out of bounds for Display");
/*      */       }
/* 1374 */       ValidationResult displayResult = ModelDisplay.validateStructure(buffer, pos);
/* 1375 */       if (!displayResult.isValid()) {
/* 1376 */         return ValidationResult.error("Invalid Display: " + displayResult.error());
/*      */       }
/* 1378 */       pos += ModelDisplay.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1381 */     if ((nullBits[3] & 0x2) != 0) {
/* 1382 */       int railOffset = buffer.getIntLE(offset + 235);
/* 1383 */       if (railOffset < 0) {
/* 1384 */         return ValidationResult.error("Invalid offset for Rail");
/*      */       }
/* 1386 */       int pos = offset + 259 + railOffset;
/* 1387 */       if (pos >= buffer.writerIndex()) {
/* 1388 */         return ValidationResult.error("Offset out of bounds for Rail");
/*      */       }
/* 1390 */       ValidationResult railResult = RailConfig.validateStructure(buffer, pos);
/* 1391 */       if (!railResult.isValid()) {
/* 1392 */         return ValidationResult.error("Invalid Rail: " + railResult.error());
/*      */       }
/* 1394 */       pos += RailConfig.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1397 */     if ((nullBits[3] & 0x4) != 0) {
/* 1398 */       int interactionsOffset = buffer.getIntLE(offset + 239);
/* 1399 */       if (interactionsOffset < 0) {
/* 1400 */         return ValidationResult.error("Invalid offset for Interactions");
/*      */       }
/* 1402 */       int pos = offset + 259 + interactionsOffset;
/* 1403 */       if (pos >= buffer.writerIndex()) {
/* 1404 */         return ValidationResult.error("Offset out of bounds for Interactions");
/*      */       }
/* 1406 */       int interactionsCount = VarInt.peek(buffer, pos);
/* 1407 */       if (interactionsCount < 0) {
/* 1408 */         return ValidationResult.error("Invalid dictionary count for Interactions");
/*      */       }
/* 1410 */       if (interactionsCount > 4096000) {
/* 1411 */         return ValidationResult.error("Interactions exceeds max length 4096000");
/*      */       }
/* 1413 */       pos += VarInt.length(buffer, pos);
/* 1414 */       for (int i = 0; i < interactionsCount; i++) {
/* 1415 */         pos++;
/*      */         
/* 1417 */         pos += 4;
/* 1418 */         if (pos > buffer.writerIndex()) {
/* 1419 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1424 */     if ((nullBits[3] & 0x8) != 0) {
/* 1425 */       int statesOffset = buffer.getIntLE(offset + 243);
/* 1426 */       if (statesOffset < 0) {
/* 1427 */         return ValidationResult.error("Invalid offset for States");
/*      */       }
/* 1429 */       int pos = offset + 259 + statesOffset;
/* 1430 */       if (pos >= buffer.writerIndex()) {
/* 1431 */         return ValidationResult.error("Offset out of bounds for States");
/*      */       }
/* 1433 */       int statesCount = VarInt.peek(buffer, pos);
/* 1434 */       if (statesCount < 0) {
/* 1435 */         return ValidationResult.error("Invalid dictionary count for States");
/*      */       }
/* 1437 */       if (statesCount > 4096000) {
/* 1438 */         return ValidationResult.error("States exceeds max length 4096000");
/*      */       }
/* 1440 */       pos += VarInt.length(buffer, pos);
/* 1441 */       for (int i = 0; i < statesCount; i++) {
/* 1442 */         int keyLen = VarInt.peek(buffer, pos);
/* 1443 */         if (keyLen < 0) {
/* 1444 */           return ValidationResult.error("Invalid string length for key");
/*      */         }
/* 1446 */         if (keyLen > 4096000) {
/* 1447 */           return ValidationResult.error("key exceeds max length 4096000");
/*      */         }
/* 1449 */         pos += VarInt.length(buffer, pos);
/* 1450 */         pos += keyLen;
/* 1451 */         if (pos > buffer.writerIndex()) {
/* 1452 */           return ValidationResult.error("Buffer overflow reading key");
/*      */         }
/* 1454 */         pos += 4;
/* 1455 */         if (pos > buffer.writerIndex()) {
/* 1456 */           return ValidationResult.error("Buffer overflow reading value");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1461 */     if ((nullBits[3] & 0x10) != 0) {
/* 1462 */       int tagIndexesOffset = buffer.getIntLE(offset + 247);
/* 1463 */       if (tagIndexesOffset < 0) {
/* 1464 */         return ValidationResult.error("Invalid offset for TagIndexes");
/*      */       }
/* 1466 */       int pos = offset + 259 + tagIndexesOffset;
/* 1467 */       if (pos >= buffer.writerIndex()) {
/* 1468 */         return ValidationResult.error("Offset out of bounds for TagIndexes");
/*      */       }
/* 1470 */       int tagIndexesCount = VarInt.peek(buffer, pos);
/* 1471 */       if (tagIndexesCount < 0) {
/* 1472 */         return ValidationResult.error("Invalid array count for TagIndexes");
/*      */       }
/* 1474 */       if (tagIndexesCount > 4096000) {
/* 1475 */         return ValidationResult.error("TagIndexes exceeds max length 4096000");
/*      */       }
/* 1477 */       pos += VarInt.length(buffer, pos);
/* 1478 */       pos += tagIndexesCount * 4;
/* 1479 */       if (pos > buffer.writerIndex()) {
/* 1480 */         return ValidationResult.error("Buffer overflow reading TagIndexes");
/*      */       }
/*      */     } 
/*      */     
/* 1484 */     if ((nullBits[3] & 0x20) != 0) {
/* 1485 */       int benchOffset = buffer.getIntLE(offset + 251);
/* 1486 */       if (benchOffset < 0) {
/* 1487 */         return ValidationResult.error("Invalid offset for Bench");
/*      */       }
/* 1489 */       int pos = offset + 259 + benchOffset;
/* 1490 */       if (pos >= buffer.writerIndex()) {
/* 1491 */         return ValidationResult.error("Offset out of bounds for Bench");
/*      */       }
/* 1493 */       ValidationResult benchResult = Bench.validateStructure(buffer, pos);
/* 1494 */       if (!benchResult.isValid()) {
/* 1495 */         return ValidationResult.error("Invalid Bench: " + benchResult.error());
/*      */       }
/* 1497 */       pos += Bench.computeBytesConsumed(buffer, pos);
/*      */     } 
/*      */     
/* 1500 */     if ((nullBits[3] & 0x40) != 0) {
/* 1501 */       int connectedBlockRuleSetOffset = buffer.getIntLE(offset + 255);
/* 1502 */       if (connectedBlockRuleSetOffset < 0) {
/* 1503 */         return ValidationResult.error("Invalid offset for ConnectedBlockRuleSet");
/*      */       }
/* 1505 */       int pos = offset + 259 + connectedBlockRuleSetOffset;
/* 1506 */       if (pos >= buffer.writerIndex()) {
/* 1507 */         return ValidationResult.error("Offset out of bounds for ConnectedBlockRuleSet");
/*      */       }
/* 1509 */       ValidationResult connectedBlockRuleSetResult = ConnectedBlockRuleSet.validateStructure(buffer, pos);
/* 1510 */       if (!connectedBlockRuleSetResult.isValid()) {
/* 1511 */         return ValidationResult.error("Invalid ConnectedBlockRuleSet: " + connectedBlockRuleSetResult.error());
/*      */       }
/* 1513 */       pos += ConnectedBlockRuleSet.computeBytesConsumed(buffer, pos);
/*      */     } 
/* 1515 */     return ValidationResult.OK;
/*      */   }
/*      */   
/*      */   public BlockType clone() {
/* 1519 */     BlockType copy = new BlockType();
/* 1520 */     copy.item = this.item;
/* 1521 */     copy.name = this.name;
/* 1522 */     copy.unknown = this.unknown;
/* 1523 */     copy.drawType = this.drawType;
/* 1524 */     copy.material = this.material;
/* 1525 */     copy.opacity = this.opacity;
/* 1526 */     copy.shaderEffect = (this.shaderEffect != null) ? Arrays.<ShaderType>copyOf(this.shaderEffect, this.shaderEffect.length) : null;
/* 1527 */     copy.hitbox = this.hitbox;
/* 1528 */     copy.interactionHitbox = this.interactionHitbox;
/* 1529 */     copy.model = this.model;
/* 1530 */     copy.modelTexture = (this.modelTexture != null) ? (ModelTexture[])Arrays.<ModelTexture>stream(this.modelTexture).map(e -> e.clone()).toArray(x$0 -> new ModelTexture[x$0]) : null;
/* 1531 */     copy.modelScale = this.modelScale;
/* 1532 */     copy.modelAnimation = this.modelAnimation;
/* 1533 */     copy.looping = this.looping;
/* 1534 */     copy.maxSupportDistance = this.maxSupportDistance;
/* 1535 */     copy.blockSupportsRequiredFor = this.blockSupportsRequiredFor;
/* 1536 */     if (this.support != null) {
/* 1537 */       Map<BlockNeighbor, RequiredBlockFaceSupport[]> m = (Map)new HashMap<>();
/* 1538 */       for (Map.Entry<BlockNeighbor, RequiredBlockFaceSupport[]> e : this.support.entrySet()) m.put(e.getKey(), (RequiredBlockFaceSupport[])Arrays.<RequiredBlockFaceSupport>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new RequiredBlockFaceSupport[x$0])); 
/* 1539 */       copy.support = m;
/*      */     } 
/* 1541 */     if (this.supporting != null) {
/* 1542 */       Map<BlockNeighbor, BlockFaceSupport[]> m = (Map)new HashMap<>();
/* 1543 */       for (Map.Entry<BlockNeighbor, BlockFaceSupport[]> e : this.supporting.entrySet()) m.put(e.getKey(), (BlockFaceSupport[])Arrays.<BlockFaceSupport>stream(e.getValue()).map(x -> x.clone()).toArray(x$0 -> new BlockFaceSupport[x$0])); 
/* 1544 */       copy.supporting = m;
/*      */     } 
/* 1546 */     copy.requiresAlphaBlending = this.requiresAlphaBlending;
/* 1547 */     copy.cubeTextures = (this.cubeTextures != null) ? (BlockTextures[])Arrays.<BlockTextures>stream(this.cubeTextures).map(e -> e.clone()).toArray(x$0 -> new BlockTextures[x$0]) : null;
/* 1548 */     copy.cubeSideMaskTexture = this.cubeSideMaskTexture;
/* 1549 */     copy.cubeShadingMode = this.cubeShadingMode;
/* 1550 */     copy.randomRotation = this.randomRotation;
/* 1551 */     copy.variantRotation = this.variantRotation;
/* 1552 */     copy.rotationYawPlacementOffset = this.rotationYawPlacementOffset;
/* 1553 */     copy.blockSoundSetIndex = this.blockSoundSetIndex;
/* 1554 */     copy.ambientSoundEventIndex = this.ambientSoundEventIndex;
/* 1555 */     copy.particles = (this.particles != null) ? (ModelParticle[])Arrays.<ModelParticle>stream(this.particles).map(e -> e.clone()).toArray(x$0 -> new ModelParticle[x$0]) : null;
/* 1556 */     copy.blockParticleSetId = this.blockParticleSetId;
/* 1557 */     copy.blockBreakingDecalId = this.blockBreakingDecalId;
/* 1558 */     copy.particleColor = (this.particleColor != null) ? this.particleColor.clone() : null;
/* 1559 */     copy.light = (this.light != null) ? this.light.clone() : null;
/* 1560 */     copy.tint = (this.tint != null) ? this.tint.clone() : null;
/* 1561 */     copy.biomeTint = (this.biomeTint != null) ? this.biomeTint.clone() : null;
/* 1562 */     copy.group = this.group;
/* 1563 */     copy.transitionTexture = this.transitionTexture;
/* 1564 */     copy.transitionToGroups = (this.transitionToGroups != null) ? Arrays.copyOf(this.transitionToGroups, this.transitionToGroups.length) : null;
/* 1565 */     copy.movementSettings = (this.movementSettings != null) ? this.movementSettings.clone() : null;
/* 1566 */     copy.flags = (this.flags != null) ? this.flags.clone() : null;
/* 1567 */     copy.interactionHint = this.interactionHint;
/* 1568 */     copy.gathering = (this.gathering != null) ? this.gathering.clone() : null;
/* 1569 */     copy.placementSettings = (this.placementSettings != null) ? this.placementSettings.clone() : null;
/* 1570 */     copy.display = (this.display != null) ? this.display.clone() : null;
/* 1571 */     copy.rail = (this.rail != null) ? this.rail.clone() : null;
/* 1572 */     copy.ignoreSupportWhenPlaced = this.ignoreSupportWhenPlaced;
/* 1573 */     copy.interactions = (this.interactions != null) ? new HashMap<>(this.interactions) : null;
/* 1574 */     copy.states = (this.states != null) ? new HashMap<>(this.states) : null;
/* 1575 */     copy.transitionToTag = this.transitionToTag;
/* 1576 */     copy.tagIndexes = (this.tagIndexes != null) ? Arrays.copyOf(this.tagIndexes, this.tagIndexes.length) : null;
/* 1577 */     copy.bench = (this.bench != null) ? this.bench.clone() : null;
/* 1578 */     copy.connectedBlockRuleSet = (this.connectedBlockRuleSet != null) ? this.connectedBlockRuleSet.clone() : null;
/* 1579 */     return copy;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*      */     BlockType other;
/* 1585 */     if (this == obj) return true; 
/* 1586 */     if (obj instanceof BlockType) { other = (BlockType)obj; } else { return false; }
/* 1587 */      return (Objects.equals(this.item, other.item) && Objects.equals(this.name, other.name) && this.unknown == other.unknown && Objects.equals(this.drawType, other.drawType) && Objects.equals(this.material, other.material) && Objects.equals(this.opacity, other.opacity) && Arrays.equals((Object[])this.shaderEffect, (Object[])other.shaderEffect) && this.hitbox == other.hitbox && this.interactionHitbox == other.interactionHitbox && Objects.equals(this.model, other.model) && Arrays.equals((Object[])this.modelTexture, (Object[])other.modelTexture) && this.modelScale == other.modelScale && Objects.equals(this.modelAnimation, other.modelAnimation) && this.looping == other.looping && this.maxSupportDistance == other.maxSupportDistance && Objects.equals(this.blockSupportsRequiredFor, other.blockSupportsRequiredFor) && Objects.equals(this.support, other.support) && Objects.equals(this.supporting, other.supporting) && this.requiresAlphaBlending == other.requiresAlphaBlending && Arrays.equals((Object[])this.cubeTextures, (Object[])other.cubeTextures) && Objects.equals(this.cubeSideMaskTexture, other.cubeSideMaskTexture) && Objects.equals(this.cubeShadingMode, other.cubeShadingMode) && Objects.equals(this.randomRotation, other.randomRotation) && Objects.equals(this.variantRotation, other.variantRotation) && Objects.equals(this.rotationYawPlacementOffset, other.rotationYawPlacementOffset) && this.blockSoundSetIndex == other.blockSoundSetIndex && this.ambientSoundEventIndex == other.ambientSoundEventIndex && Arrays.equals((Object[])this.particles, (Object[])other.particles) && Objects.equals(this.blockParticleSetId, other.blockParticleSetId) && Objects.equals(this.blockBreakingDecalId, other.blockBreakingDecalId) && Objects.equals(this.particleColor, other.particleColor) && Objects.equals(this.light, other.light) && Objects.equals(this.tint, other.tint) && Objects.equals(this.biomeTint, other.biomeTint) && this.group == other.group && Objects.equals(this.transitionTexture, other.transitionTexture) && Arrays.equals(this.transitionToGroups, other.transitionToGroups) && Objects.equals(this.movementSettings, other.movementSettings) && Objects.equals(this.flags, other.flags) && Objects.equals(this.interactionHint, other.interactionHint) && Objects.equals(this.gathering, other.gathering) && Objects.equals(this.placementSettings, other.placementSettings) && Objects.equals(this.display, other.display) && Objects.equals(this.rail, other.rail) && this.ignoreSupportWhenPlaced == other.ignoreSupportWhenPlaced && Objects.equals(this.interactions, other.interactions) && Objects.equals(this.states, other.states) && this.transitionToTag == other.transitionToTag && Arrays.equals(this.tagIndexes, other.tagIndexes) && Objects.equals(this.bench, other.bench) && Objects.equals(this.connectedBlockRuleSet, other.connectedBlockRuleSet));
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1592 */     int result = 1;
/* 1593 */     result = 31 * result + Objects.hashCode(this.item);
/* 1594 */     result = 31 * result + Objects.hashCode(this.name);
/* 1595 */     result = 31 * result + Boolean.hashCode(this.unknown);
/* 1596 */     result = 31 * result + Objects.hashCode(this.drawType);
/* 1597 */     result = 31 * result + Objects.hashCode(this.material);
/* 1598 */     result = 31 * result + Objects.hashCode(this.opacity);
/* 1599 */     result = 31 * result + Arrays.hashCode((Object[])this.shaderEffect);
/* 1600 */     result = 31 * result + Integer.hashCode(this.hitbox);
/* 1601 */     result = 31 * result + Integer.hashCode(this.interactionHitbox);
/* 1602 */     result = 31 * result + Objects.hashCode(this.model);
/* 1603 */     result = 31 * result + Arrays.hashCode((Object[])this.modelTexture);
/* 1604 */     result = 31 * result + Float.hashCode(this.modelScale);
/* 1605 */     result = 31 * result + Objects.hashCode(this.modelAnimation);
/* 1606 */     result = 31 * result + Boolean.hashCode(this.looping);
/* 1607 */     result = 31 * result + Integer.hashCode(this.maxSupportDistance);
/* 1608 */     result = 31 * result + Objects.hashCode(this.blockSupportsRequiredFor);
/* 1609 */     result = 31 * result + Objects.hashCode(this.support);
/* 1610 */     result = 31 * result + Objects.hashCode(this.supporting);
/* 1611 */     result = 31 * result + Boolean.hashCode(this.requiresAlphaBlending);
/* 1612 */     result = 31 * result + Arrays.hashCode((Object[])this.cubeTextures);
/* 1613 */     result = 31 * result + Objects.hashCode(this.cubeSideMaskTexture);
/* 1614 */     result = 31 * result + Objects.hashCode(this.cubeShadingMode);
/* 1615 */     result = 31 * result + Objects.hashCode(this.randomRotation);
/* 1616 */     result = 31 * result + Objects.hashCode(this.variantRotation);
/* 1617 */     result = 31 * result + Objects.hashCode(this.rotationYawPlacementOffset);
/* 1618 */     result = 31 * result + Integer.hashCode(this.blockSoundSetIndex);
/* 1619 */     result = 31 * result + Integer.hashCode(this.ambientSoundEventIndex);
/* 1620 */     result = 31 * result + Arrays.hashCode((Object[])this.particles);
/* 1621 */     result = 31 * result + Objects.hashCode(this.blockParticleSetId);
/* 1622 */     result = 31 * result + Objects.hashCode(this.blockBreakingDecalId);
/* 1623 */     result = 31 * result + Objects.hashCode(this.particleColor);
/* 1624 */     result = 31 * result + Objects.hashCode(this.light);
/* 1625 */     result = 31 * result + Objects.hashCode(this.tint);
/* 1626 */     result = 31 * result + Objects.hashCode(this.biomeTint);
/* 1627 */     result = 31 * result + Integer.hashCode(this.group);
/* 1628 */     result = 31 * result + Objects.hashCode(this.transitionTexture);
/* 1629 */     result = 31 * result + Arrays.hashCode(this.transitionToGroups);
/* 1630 */     result = 31 * result + Objects.hashCode(this.movementSettings);
/* 1631 */     result = 31 * result + Objects.hashCode(this.flags);
/* 1632 */     result = 31 * result + Objects.hashCode(this.interactionHint);
/* 1633 */     result = 31 * result + Objects.hashCode(this.gathering);
/* 1634 */     result = 31 * result + Objects.hashCode(this.placementSettings);
/* 1635 */     result = 31 * result + Objects.hashCode(this.display);
/* 1636 */     result = 31 * result + Objects.hashCode(this.rail);
/* 1637 */     result = 31 * result + Boolean.hashCode(this.ignoreSupportWhenPlaced);
/* 1638 */     result = 31 * result + Objects.hashCode(this.interactions);
/* 1639 */     result = 31 * result + Objects.hashCode(this.states);
/* 1640 */     result = 31 * result + Integer.hashCode(this.transitionToTag);
/* 1641 */     result = 31 * result + Arrays.hashCode(this.tagIndexes);
/* 1642 */     result = 31 * result + Objects.hashCode(this.bench);
/* 1643 */     result = 31 * result + Objects.hashCode(this.connectedBlockRuleSet);
/* 1644 */     return result;
/*      */   }
/*      */   
/*      */   public BlockType() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\protocol\BlockType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */