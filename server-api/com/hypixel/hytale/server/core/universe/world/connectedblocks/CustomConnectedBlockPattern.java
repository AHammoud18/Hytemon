/*     */ package com.hypixel.hytale.server.core.universe.world.connectedblocks;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.codecs.simple.BooleanCodec;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.block.BlockUtil;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFlipType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.buildertool.config.BlockTypeListAsset;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockPattern;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import it.unimi.dsi.fastutil.Pair;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomConnectedBlockPattern
/*     */   extends CustomTemplateConnectedBlockPattern
/*     */ {
/*     */   public static final BuilderCodec<CustomConnectedBlockPattern> CODEC;
/*     */   
/*     */   static {
/*  83 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CustomConnectedBlockPattern.class, CustomConnectedBlockPattern::new).append(new KeyedCodec("TransformRulesToOrientation", (Codec)Codec.BOOLEAN, false), (o, transformRulesToPlacedOrientation) -> o.transformRulesToOrientation = transformRulesToPlacedOrientation.booleanValue(), o -> Boolean.valueOf(o.transformRulesToOrientation)).documentation("If the rules should be transformed to the current orientation of the block.").add()).append(new KeyedCodec("YawToApplyAddReplacedBlockType", Rotation.CODEC, false), (o, yawToApplyAddReplacedBlockType) -> o.yawToApplyAddReplacedBlockType = yawToApplyAddReplacedBlockType, o -> o.yawToApplyAddReplacedBlockType).documentation("Apply an additional Yaw to the resulting BlockType represented by this shape. This allows your replacement to be offset from your original placement").add()).append(new KeyedCodec("RequireFaceTagsMatchingRoll", (Codec)Codec.BOOLEAN, false), (o, requireFaceTagsMatchingRoll) -> o.requireFaceTagsMatchingRoll = requireFaceTagsMatchingRoll.booleanValue(), o -> Boolean.valueOf(o.requireFaceTagsMatchingRoll)).documentation("Adds Roll comparison to face tag matching in patterns below").add()).append(new KeyedCodec("AllowedPatternTransformations", (Codec)PatternRotationDefinition.CODEC, false), (o, patternRotations) -> o.patternRotationDefinition = patternRotations, o -> o.patternRotationDefinition).documentation("Will create additional generated patterns that are variants of this pattern, but rotated/mirrored/flipped to achieve different results. A common example of this is the Fence, which should its resulting shape based on the rotation of its pattern (fence corner rotates depending on which two sides have the corner fence shape)").add()).append(new KeyedCodec("RulesToMatch", (Codec)new ArrayCodec((Codec)ConnectedBlockPatternRule.CODEC, x$0 -> new ConnectedBlockPatternRule[x$0]), true), (o, matchingPatterns) -> o.rulesToMatch = matchingPatterns, o -> o.rulesToMatch).documentation("All rules must match in order for the pattern to match").add()).append(new KeyedCodec("OnlyOnPlacement", (Codec)new BooleanCodec(), false), (o, onlyOnPlacement) -> o.onlyOnPlacement = onlyOnPlacement.booleanValue(), o -> Boolean.valueOf(o.onlyOnPlacement)).documentation("If true, this pattern will only be checked when the block is first placed.").add()).append(new KeyedCodec("OnlyOnUpdate", (Codec)new BooleanCodec(), false), (o, onlyOnUpdate) -> o.onlyOnUpdate = onlyOnUpdate.booleanValue(), o -> Boolean.valueOf(o.onlyOnUpdate)).documentation("If true, this pattern will only be checked when the block is updated by neighboring block changes.").add()).build();
/*     */   } @Nonnull
/*  85 */   private static final Random random = new Random();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean transformRulesToOrientation = true;
/*     */ 
/*     */ 
/*     */   
/*  93 */   private PatternRotationDefinition patternRotationDefinition = PatternRotationDefinition.DEFAULT;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ConnectedBlockPatternRule[] rulesToMatch;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Rotation yawToApplyAddReplacedBlockType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean requireFaceTagsMatchingRoll;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean onlyOnUpdate;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean onlyOnPlacement;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean checkPatternRuleAgainstBlockType(@Nonnull CustomTemplateConnectedBlockRuleSet placedRuleset, @Nonnull CustomConnectedBlockTemplateAsset template, @Nonnull String block, @Nonnull ConnectedBlockPatternRule rule, @Nonnull String blockToTest, RotationTuple rotationToCheckUnrotated, int fillerToCheckUnrotated) {
/* 122 */     if (!rule.getFaceTags().getDirections().isEmpty()) {
/* 123 */       CustomTemplateConnectedBlockRuleSet checkingConnectedBlockRuleSet; BlockType checkingBlockType = (BlockType)BlockType.getAssetMap().getAsset(blockToTest);
/*     */       
/* 125 */       ConnectedBlockRuleSet checkingRuleSet = checkingBlockType.getConnectedBlockRuleSet();
/* 126 */       if (checkingRuleSet instanceof CustomTemplateConnectedBlockRuleSet) { checkingConnectedBlockRuleSet = (CustomTemplateConnectedBlockRuleSet)checkingRuleSet; } else { return !rule.isInclude(); }
/*     */       
/* 128 */       int index = BlockType.getAssetMap().getIndex(blockToTest);
/* 129 */       Set<String> shapeNames = checkingConnectedBlockRuleSet.getShapesForBlockType(index);
/*     */       
/* 131 */       CustomConnectedBlockTemplateAsset checkingTemplateAsset = checkingConnectedBlockRuleSet.getShapeTemplateAsset();
/* 132 */       if (checkingTemplateAsset == null) return !rule.isInclude();
/*     */ 
/*     */       
/* 135 */       for (String shapeName : shapeNames) {
/*     */         
/* 137 */         if (!template.connectsToOtherMaterials && 
/* 138 */           !placedRuleset.getShapeNameToBlockPatternMap().equals(checkingConnectedBlockRuleSet.getShapeNameToBlockPatternMap())) {
/*     */           continue;
/*     */         }
/*     */         
/* 142 */         ConnectedBlockShape blockToCheckConnectedBlockShape = checkingTemplateAsset.connectedBlockShapes.get(shapeName);
/*     */ 
/*     */         
/* 145 */         Map<Vector3i, HashSet<String>> ruleFaceTags = rule.getFaceTags().getBlockFaceTags();
/*     */         
/* 147 */         for (Map.Entry<Vector3i, HashSet<String>> ruleFaceTag : ruleFaceTags.entrySet()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 158 */           Vector3i adjustedDirectionOfPattern = Rotation.rotate(((Vector3i)ruleFaceTag.getKey()).clone(), Rotation.None
/* 159 */               .subtract(rotationToCheckUnrotated.yaw()), Rotation.None);
/*     */           
/* 161 */           for (String faceTag : ruleFaceTag.getValue()) {
/*     */             
/* 163 */             boolean containsFaceTag = (blockToCheckConnectedBlockShape.getFaceTags() != null && blockToCheckConnectedBlockShape.getFaceTags().contains(adjustedDirectionOfPattern, faceTag));
/* 164 */             if (containsFaceTag) return rule.isInclude();
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 170 */     if (!rule.getShapeBlockTypeKeys().isEmpty()) {
/* 171 */       CustomTemplateConnectedBlockRuleSet checkingConnectedBlockRuleSet; BlockType checkingBlockType = (BlockType)BlockType.getAssetMap().getAsset(blockToTest);
/*     */       
/* 173 */       ConnectedBlockRuleSet checkingRuleSet = checkingBlockType.getConnectedBlockRuleSet();
/* 174 */       if (checkingRuleSet instanceof CustomTemplateConnectedBlockRuleSet) { checkingConnectedBlockRuleSet = (CustomTemplateConnectedBlockRuleSet)checkingRuleSet; } else { return !rule.isInclude(); }
/*     */       
/* 176 */       int index = BlockType.getAssetMap().getIndex(blockToTest);
/* 177 */       Set<String> shapeNames = checkingConnectedBlockRuleSet.getShapesForBlockType(index);
/*     */       
/* 179 */       for (String shapeName : shapeNames) {
/*     */         
/* 181 */         if (!template.connectsToOtherMaterials && 
/* 182 */           !placedRuleset.getShapeNameToBlockPatternMap().equals(checkingConnectedBlockRuleSet.getShapeNameToBlockPatternMap())) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 189 */         if (rule.getShapeBlockTypeKeys().contains(new BlockPattern.BlockEntry(shapeName, rotationToCheckUnrotated.index(), fillerToCheckUnrotated))) {
/* 190 */           return rule.isInclude();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 195 */     if (!rule.getBlockTypes().isEmpty() && rule.getBlockTypes().contains(blockToTest)) {
/* 196 */       return rule.isInclude();
/*     */     }
/*     */     
/* 199 */     if (rule.getBlockTypeListAssets() != null) {
/* 200 */       for (BlockTypeListAsset blockTypeListAsset : rule.getBlockTypeListAssets()) {
/* 201 */         if (blockTypeListAsset.getBlockTypeKeys().contains(blockToTest)) return rule.isInclude();
/*     */       
/*     */       } 
/*     */     }
/* 205 */     return !rule.isInclude();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Optional<ConnectedBlocksUtil.ConnectedBlockResult> getConnectedBlockTypeKey(String shapeName, @Nonnull World world, @Nonnull Vector3i coordinate, @Nonnull CustomTemplateConnectedBlockRuleSet connectedBlockRuleset, @Nonnull BlockType blockType, int rotation, @Nonnull Vector3i placementNormal, boolean isPlacement) {
/* 221 */     if ((isPlacement && this.onlyOnUpdate) || (!isPlacement && this.onlyOnPlacement)) {
/* 222 */       return Optional.empty();
/*     */     }
/*     */     
/* 225 */     CustomConnectedBlockTemplateAsset shapeTemplate = connectedBlockRuleset.getShapeTemplateAsset();
/* 226 */     if (shapeTemplate == null) return Optional.empty();
/*     */     
/* 228 */     Vector3i coordinateToTest = new Vector3i();
/* 229 */     Rotation3D totalRotation = new Rotation3D(Rotation.None, Rotation.None, Rotation.None);
/* 230 */     Rotation3D tempRotation = new Rotation3D(Rotation.None, Rotation.None, Rotation.None);
/*     */ 
/*     */ 
/*     */     
/* 234 */     List<Pair<Rotation, PatternRotationDefinition.MirrorAxis>> rotations = this.patternRotationDefinition.getRotations();
/*     */     
/* 236 */     for (int i = 0; i < rotations.size(); ) {
/* 237 */       Pair<Rotation, PatternRotationDefinition.MirrorAxis> patternTransform = rotations.get(i);
/*     */ 
/*     */       
/* 240 */       totalRotation.assign((Rotation)patternTransform.first(), Rotation.None, Rotation.None);
/*     */ 
/*     */       
/* 243 */       if (this.transformRulesToOrientation) {
/* 244 */         RotationTuple rotationTuple = RotationTuple.get(rotation);
/* 245 */         tempRotation.assign(rotationTuple
/* 246 */             .yaw(), rotationTuple
/* 247 */             .pitch(), rotationTuple
/* 248 */             .roll());
/*     */         
/* 250 */         totalRotation.add(tempRotation);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 255 */       for (ConnectedBlockPatternRule ruleToMatch : this.rulesToMatch) {
/*     */         
/* 257 */         coordinateToTest.assign(ruleToMatch.getRelativePosition());
/*     */ 
/*     */         
/* 260 */         switch ((PatternRotationDefinition.MirrorAxis)patternTransform.second()) { case X:
/* 261 */             coordinateToTest.setX(-coordinateToTest.getX()); break;
/* 262 */           case Z: coordinateToTest.setZ(-coordinateToTest.getZ());
/*     */             break; }
/*     */         
/* 265 */         if (ruleToMatch.getPlacementNormals() != null) {
/* 266 */           ConnectedBlockPatternRule.AdjacentSide[] arrayOfAdjacentSide = ruleToMatch.getPlacementNormals(); int j = arrayOfAdjacentSide.length; byte b = 0; while (true) { if (b < j) { ConnectedBlockPatternRule.AdjacentSide normal = arrayOfAdjacentSide[b];
/* 267 */               if (normal.relativePosition.equals(placementNormal))
/* 268 */                 break;  b++; continue; }  return Optional.empty(); }
/*     */         
/*     */         } else {
/*     */           
/* 272 */           coordinateToTest = Rotation.rotate(coordinateToTest, totalRotation.rotationYaw, totalRotation.rotationPitch, totalRotation.rotationRoll);
/* 273 */           coordinateToTest.add(coordinate);
/*     */ 
/*     */           
/* 276 */           WorldChunk chunkIfLoaded = world.getChunkIfLoaded(ChunkUtil.indexChunkFromBlock(coordinateToTest.x, coordinateToTest.z));
/* 277 */           if (chunkIfLoaded == null) return Optional.empty();
/*     */ 
/*     */           
/* 280 */           String blockToCheckUnrotated = chunkIfLoaded.getBlockType(coordinateToTest).getId();
/* 281 */           RotationTuple rotationToCheckUnrotated = chunkIfLoaded.getRotation(coordinateToTest.x, coordinateToTest.y, coordinateToTest.z);
/* 282 */           tempRotation.assign(rotationToCheckUnrotated);
/* 283 */           tempRotation.subtract(totalRotation);
/*     */           
/* 285 */           int fillerToCheckUnrotated = chunkIfLoaded.getFiller(coordinateToTest.x, coordinateToTest.y, coordinateToTest.z);
/* 286 */           fillerToCheckUnrotated = tempRotation.rotationPitch.subtract(rotationToCheckUnrotated.pitch()).rotateX(fillerToCheckUnrotated);
/* 287 */           fillerToCheckUnrotated = tempRotation.rotationYaw.subtract(rotationToCheckUnrotated.yaw()).rotateY(fillerToCheckUnrotated);
/* 288 */           fillerToCheckUnrotated = tempRotation.rotationRoll.subtract(rotationToCheckUnrotated.roll()).rotateY(fillerToCheckUnrotated);
/*     */           
/* 290 */           rotationToCheckUnrotated = RotationTuple.of(tempRotation.rotationYaw, tempRotation.rotationPitch, tempRotation.rotationRoll);
/*     */ 
/*     */           
/* 293 */           BlockType blockTypeToCheckUnrotated = (BlockType)BlockType.getAssetMap().getAsset(blockToCheckUnrotated);
/* 294 */           if (patternTransform.second() != PatternRotationDefinition.MirrorAxis.NONE) {
/* 295 */             Rotation newYawMirrored = blockTypeToCheckUnrotated.getFlipType().flipYaw(rotationToCheckUnrotated.yaw(), 
/* 296 */                 (patternTransform.second() == PatternRotationDefinition.MirrorAxis.X) ? Axis.X : Axis.Z);
/* 297 */             fillerToCheckUnrotated = newYawMirrored.subtract(rotationToCheckUnrotated.yaw()).rotateY(fillerToCheckUnrotated);
/* 298 */             rotationToCheckUnrotated = RotationTuple.of(newYawMirrored, rotationToCheckUnrotated
/* 299 */                 .pitch(), rotationToCheckUnrotated.roll());
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 306 */           boolean patternMatches = checkPatternRuleAgainstBlockType(connectedBlockRuleset, shapeTemplate, blockType
/*     */               
/* 308 */               .getId(), ruleToMatch, blockToCheckUnrotated, rotationToCheckUnrotated, fillerToCheckUnrotated);
/*     */ 
/*     */ 
/*     */           
/* 312 */           if (!patternMatches) {
/*     */             i++;
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 318 */       BlockPattern resultBlockPattern = connectedBlockRuleset.getShapeNameToBlockPatternMap().get(shapeName);
/* 319 */       if (resultBlockPattern == null) return Optional.empty();
/*     */ 
/*     */       
/* 322 */       random.setSeed(BlockUtil.pack(coordinate));
/* 323 */       BlockPattern.BlockEntry resultBlockTypeKey = resultBlockPattern.nextBlockTypeKey(random);
/* 324 */       if (resultBlockTypeKey == null) return Optional.empty();
/*     */       
/* 326 */       BlockType baseBlockTypeForFlip = (BlockType)BlockType.getAssetMap().getAsset(resultBlockTypeKey.blockTypeKey());
/* 327 */       if (baseBlockTypeForFlip == null) return Optional.empty(); 
/* 328 */       BlockFlipType flipType = baseBlockTypeForFlip.getFlipType();
/*     */       
/* 330 */       RotationTuple resultRotation = RotationTuple.get(resultBlockTypeKey.rotation());
/*     */ 
/*     */ 
/*     */       
/* 334 */       resultRotation = RotationTuple.of(resultRotation
/* 335 */           .yaw().add(this.yawToApplyAddReplacedBlockType), resultRotation
/* 336 */           .pitch(), resultRotation.roll());
/*     */ 
/*     */       
/* 339 */       if (patternTransform.second() != PatternRotationDefinition.MirrorAxis.NONE) {
/* 340 */         Rotation newYawMirrored = flipType.flipYaw(resultRotation.yaw(), (patternTransform.second() == PatternRotationDefinition.MirrorAxis.X) ? Axis.X : Axis.Z);
/* 341 */         resultRotation = RotationTuple.of(newYawMirrored, resultRotation
/* 342 */             .pitch(), resultRotation.roll());
/*     */       } 
/*     */ 
/*     */       
/* 346 */       resultRotation = RotationTuple.of(resultRotation
/* 347 */           .yaw().add(totalRotation.rotationYaw), resultRotation
/* 348 */           .pitch().add(totalRotation.rotationPitch), resultRotation
/* 349 */           .roll().add(totalRotation.rotationRoll));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 354 */       if (resultRotation.pitch().equals(Rotation.OneEighty) && flipType.equals(BlockFlipType.ORTHOGONAL)) {
/* 355 */         resultRotation = RotationTuple.of(resultRotation
/* 356 */             .yaw().subtract(Rotation.Ninety), resultRotation
/* 357 */             .pitch(), resultRotation.roll());
/*     */       }
/*     */       
/* 360 */       return Optional.of(new ConnectedBlocksUtil.ConnectedBlockResult(resultBlockTypeKey.blockTypeKey(), resultRotation.index()));
/*     */     } 
/*     */     
/* 363 */     return Optional.empty();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\CustomConnectedBlockPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */