/*     */ package com.hypixel.hytale.server.core.universe.world.connectedblocks.builtin;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.simple.IntegerCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.ConnectedBlockRuleSet;
/*     */ import com.hypixel.hytale.protocol.ConnectedBlockRuleSetType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFace;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockFaceSupport;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.connectedblocks.ConnectedBlockRuleSet;
/*     */ import com.hypixel.hytale.server.core.universe.world.connectedblocks.ConnectedBlocksUtil;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIntPair;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
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
/*     */ public class RoofConnectedBlockRuleSet
/*     */   extends ConnectedBlockRuleSet
/*     */   implements StairLikeConnectedBlockRuleSet
/*     */ {
/*     */   public static final BuilderCodec<RoofConnectedBlockRuleSet> CODEC;
/*     */   private StairConnectedBlockRuleSet regular;
/*     */   private StairConnectedBlockRuleSet hollow;
/*     */   private ConnectedBlockOutput topper;
/*     */   private String materialName;
/*     */   
/*     */   static {
/*  58 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RoofConnectedBlockRuleSet.class, RoofConnectedBlockRuleSet::new).append(new KeyedCodec("Regular", (Codec)StairConnectedBlockRuleSet.CODEC), (ruleSet, output) -> ruleSet.regular = output, ruleSet -> ruleSet.regular).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Hollow", (Codec)StairConnectedBlockRuleSet.CODEC), (ruleSet, output) -> ruleSet.hollow = output, ruleSet -> ruleSet.hollow).add()).append(new KeyedCodec("Topper", (Codec)ConnectedBlockOutput.CODEC), (ruleSet, output) -> ruleSet.topper = output, ruleSet -> ruleSet.topper).add()).append(new KeyedCodec("Width", (Codec)new IntegerCodec()), (ruleSet, output) -> ruleSet.width = output.intValue(), ruleSet -> Integer.valueOf(ruleSet.width)).add()).append(new KeyedCodec("MaterialName", (Codec)Codec.STRING), (ruleSet, materialName) -> ruleSet.materialName = materialName, ruleSet -> ruleSet.materialName).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private int width = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static StairConnectedBlockRuleSet.StairType getConnectedBlockStairType(World world, Vector3i coordinate, StairLikeConnectedBlockRuleSet currentRuleSet, int blockId, int rotation, int width) {
/*  72 */     RotationTuple currentRotation = RotationTuple.get(rotation);
/*  73 */     Rotation currentYaw = currentRotation.yaw();
/*  74 */     Rotation currentPitch = currentRotation.pitch();
/*     */     
/*  76 */     boolean upsideDown = (currentPitch != Rotation.None);
/*     */     
/*  78 */     if (upsideDown) {
/*  79 */       currentYaw = currentYaw.flip();
/*     */     }
/*     */     
/*  82 */     Vector3i mutablePos = new Vector3i();
/*     */     
/*  84 */     StairConnectedBlockRuleSet.StairType resultingStair = StairConnectedBlockRuleSet.StairType.STRAIGHT;
/*  85 */     StairConnectedBlockRuleSet.StairConnection frontConnection = StairConnectedBlockRuleSet.getInvertedCornerConnection(world, currentRuleSet, coordinate, mutablePos, currentYaw, upsideDown);
/*     */     
/*  87 */     if (frontConnection != null) {
/*  88 */       boolean valid = isWidthFulfilled(world, coordinate, mutablePos, frontConnection, currentYaw, blockId, width);
/*     */       
/*  90 */       if (valid) {
/*  91 */         resultingStair = frontConnection.getStairType(true);
/*     */       }
/*     */     } 
/*     */     
/*  95 */     StairConnectedBlockRuleSet.StairConnection backConnection = StairConnectedBlockRuleSet.getCornerConnection(world, currentRuleSet, coordinate, mutablePos, rotation, currentYaw, upsideDown, width);
/*     */     
/*  97 */     if (backConnection != null) {
/*  98 */       boolean valid = isWidthFulfilled(world, coordinate, mutablePos, backConnection, currentYaw, blockId, width);
/*     */       
/* 100 */       if (valid) {
/* 101 */         resultingStair = backConnection.getStairType(false);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 106 */     if (resultingStair == StairConnectedBlockRuleSet.StairType.STRAIGHT) {
/* 107 */       Vector3i aboveCoordinate = (new Vector3i(coordinate)).add(0, 1, 0);
/* 108 */       StairConnectedBlockRuleSet.StairConnection resultingConnection = getValleyConnection(world, aboveCoordinate, currentRuleSet, currentRotation, mutablePos, false, width);
/*     */       
/* 110 */       if (resultingConnection != null) {
/* 111 */         resultingStair = resultingConnection.getStairType(true);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (resultingStair == StairConnectedBlockRuleSet.StairType.STRAIGHT) {
/* 117 */       Vector3i belowCoordinate = (new Vector3i(coordinate)).add(0, -1, 0);
/* 118 */       StairConnectedBlockRuleSet.StairConnection resultingConnection = getValleyConnection(world, belowCoordinate, currentRuleSet, currentRotation, mutablePos, true, width);
/*     */       
/* 120 */       if (resultingConnection != null) {
/* 121 */         resultingStair = resultingConnection.getStairType(false);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 126 */     if (upsideDown) {
/* 127 */       switch (resultingStair) { case CORNER_LEFT: case CORNER_RIGHT: case INVERTED_CORNER_LEFT: case INVERTED_CORNER_RIGHT: default: break; }  resultingStair = 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 132 */         resultingStair;
/*     */     } 
/*     */ 
/*     */     
/* 136 */     return resultingStair;
/*     */   }
/*     */   
/*     */   private static boolean isWidthFulfilled(World world, Vector3i coordinate, Vector3i mutablePos, StairConnectedBlockRuleSet.StairConnection backConnection, Rotation currentYaw, int blockId, int width) {
/* 140 */     boolean valid = true;
/*     */     
/* 142 */     for (int i = 0; i < width - 1; i++) {
/* 143 */       mutablePos.assign((backConnection == StairConnectedBlockRuleSet.StairConnection.CORNER_LEFT) ? Vector3i.WEST : Vector3i.EAST).scale(i + 1);
/* 144 */       currentYaw.rotateY(mutablePos, mutablePos);
/* 145 */       int requiredFiller = FillerBlockUtil.pack(mutablePos.x, mutablePos.y, mutablePos.z);
/* 146 */       mutablePos.add(coordinate.x, coordinate.y, coordinate.z);
/*     */       
/* 148 */       WorldChunk chunk = world.getChunkIfLoaded(ChunkUtil.indexChunkFromBlock(mutablePos.x, mutablePos.z));
/* 149 */       if (chunk != null) {
/*     */         
/* 151 */         int otherFiller = chunk.getFiller(mutablePos.x, mutablePos.y, mutablePos.z);
/* 152 */         int otherBlockId = chunk.getBlock(mutablePos);
/*     */         
/* 154 */         if ((otherFiller != 0 || otherBlockId != blockId) && (otherFiller != requiredFiller || otherBlockId != blockId)) {
/* 155 */           valid = false;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 160 */     return valid;
/*     */   }
/*     */   
/*     */   private static StairConnectedBlockRuleSet.StairConnection getValleyConnection(World world, Vector3i coordinate, StairLikeConnectedBlockRuleSet currentRuleSet, RotationTuple rotation, Vector3i mutablePos, boolean reverse, int width) {
/* 164 */     Rotation yaw = rotation.yaw();
/*     */     
/* 166 */     mutablePos.assign(reverse ? Vector3i.SOUTH : Vector3i.NORTH).scale(width);
/* 167 */     yaw.rotateY(mutablePos, mutablePos);
/* 168 */     mutablePos.add(coordinate.x, coordinate.y, coordinate.z);
/*     */     
/* 170 */     ObjectIntPair<StairConnectedBlockRuleSet.StairType> backStair = StairConnectedBlockRuleSet.getStairData(world, mutablePos, currentRuleSet.getMaterialName());
/*     */     
/* 172 */     if (backStair == null) {
/* 173 */       return null;
/*     */     }
/*     */     
/* 176 */     boolean backConnection = reverse ? isTopperConnectionCompatible(rotation, backStair, Rotation.None) : isValleyConnectionCompatible(rotation, backStair, Rotation.None, false);
/* 177 */     if (!backConnection) {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     mutablePos.assign(reverse ? Vector3i.EAST : Vector3i.WEST).scale(width);
/* 182 */     yaw.rotateY(mutablePos, mutablePos);
/* 183 */     mutablePos.add(coordinate.x, coordinate.y, coordinate.z);
/* 184 */     ObjectIntPair<StairConnectedBlockRuleSet.StairType> leftStair = StairConnectedBlockRuleSet.getStairData(world, mutablePos, currentRuleSet.getMaterialName());
/*     */     
/* 186 */     mutablePos.assign(reverse ? Vector3i.WEST : Vector3i.EAST).scale(width);
/* 187 */     yaw.rotateY(mutablePos, mutablePos);
/* 188 */     mutablePos.add(coordinate.x, coordinate.y, coordinate.z);
/* 189 */     ObjectIntPair<StairConnectedBlockRuleSet.StairType> rightStair = StairConnectedBlockRuleSet.getStairData(world, mutablePos, currentRuleSet.getMaterialName());
/*     */     
/* 191 */     boolean leftConnection = reverse ? isTopperConnectionCompatible(rotation, leftStair, Rotation.Ninety) : isValleyConnectionCompatible(rotation, leftStair, Rotation.Ninety, false);
/* 192 */     boolean rightConnection = reverse ? isTopperConnectionCompatible(rotation, rightStair, Rotation.TwoSeventy) : isValleyConnectionCompatible(rotation, rightStair, Rotation.TwoSeventy, false);
/*     */     
/* 194 */     if (leftConnection == rightConnection) {
/* 195 */       return null;
/*     */     }
/*     */     
/* 198 */     return leftConnection ? StairConnectedBlockRuleSet.StairConnection.CORNER_LEFT : StairConnectedBlockRuleSet.StairConnection.CORNER_RIGHT;
/*     */   }
/*     */   
/*     */   private static boolean isTopperConnectionCompatible(RotationTuple rotation, ObjectIntPair<StairConnectedBlockRuleSet.StairType> otherStair, Rotation yawOffset) {
/* 202 */     return isValleyConnectionCompatible(rotation, otherStair, yawOffset, true);
/*     */   }
/*     */   
/*     */   private static boolean canBeTopper(World world, Vector3i coordinate, StairLikeConnectedBlockRuleSet currentRuleSet, RotationTuple rotation, Vector3i mutablePos) {
/* 206 */     Rotation yaw = rotation.yaw();
/*     */     
/* 208 */     Vector3i[] directions = { Vector3i.NORTH, Vector3i.SOUTH, Vector3i.EAST, Vector3i.WEST };
/* 209 */     Rotation[] yawOffsets = { Rotation.OneEighty, Rotation.None, Rotation.Ninety, Rotation.TwoSeventy };
/*     */     
/* 211 */     for (int i = 0; i < directions.length; i++) {
/* 212 */       mutablePos.assign(directions[i]);
/* 213 */       yaw.rotateY(mutablePos, mutablePos);
/* 214 */       mutablePos.add(coordinate.x, coordinate.y, coordinate.z);
/*     */       
/* 216 */       ObjectIntPair<StairConnectedBlockRuleSet.StairType> stair = StairConnectedBlockRuleSet.getStairData(world, mutablePos, currentRuleSet.getMaterialName());
/*     */       
/* 218 */       if (stair == null || !isTopperConnectionCompatible(rotation, stair, yawOffsets[i])) {
/* 219 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 223 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isValleyConnectionCompatible(RotationTuple rotation, ObjectIntPair<StairConnectedBlockRuleSet.StairType> otherStair, Rotation yawOffset, boolean inverted) {
/* 227 */     Rotation targetYaw = rotation.yaw().add(yawOffset);
/*     */     
/* 229 */     if (otherStair == null) {
/* 230 */       return false;
/*     */     }
/*     */     
/* 233 */     RotationTuple stairRotation = RotationTuple.get(otherStair.rightInt());
/* 234 */     StairConnectedBlockRuleSet.StairType otherStairType = (StairConnectedBlockRuleSet.StairType)otherStair.first();
/*     */     
/* 236 */     if (stairRotation.pitch() != rotation.pitch()) {
/* 237 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 241 */     if (inverted && otherStairType.isCorner())
/* 242 */       return false; 
/* 243 */     if (!inverted && otherStairType.isInvertedCorner()) {
/* 244 */       return false;
/*     */     }
/*     */     
/* 247 */     return (stairRotation.yaw() == targetYaw || (otherStairType == StairConnectedBlockRuleSet.StairConnection.CORNER_RIGHT
/* 248 */       .getStairType(inverted) && stairRotation.yaw() == targetYaw.add(Rotation.Ninety)) || (otherStairType == StairConnectedBlockRuleSet.StairConnection.CORNER_LEFT
/* 249 */       .getStairType(inverted) && stairRotation.yaw() == targetYaw.add(Rotation.TwoSeventy)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onlyUpdateOnPlacement() {
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<ConnectedBlocksUtil.ConnectedBlockResult> getConnectedBlockType(World world, Vector3i coordinate, BlockType blockType, int rotation, Vector3i placementNormal, boolean isPlacement) {
/* 260 */     WorldChunk chunk = world.getChunkIfLoaded(ChunkUtil.indexChunkFromBlock(coordinate.x, coordinate.z));
/* 261 */     if (chunk == null) return Optional.empty();
/*     */     
/* 263 */     int belowBlockId = chunk.getBlock(coordinate.x, coordinate.y - 1, coordinate.z);
/* 264 */     BlockType belowBlockType = (BlockType)BlockType.getAssetMap().getAsset(belowBlockId);
/* 265 */     int belowBlockRotation = chunk.getRotationIndex(coordinate.x, coordinate.y - 1, coordinate.z);
/*     */     
/* 267 */     boolean hollow = true;
/*     */     
/* 269 */     if (belowBlockType != null) {
/* 270 */       Map<BlockFace, BlockFaceSupport[]> supporting = belowBlockType.getSupporting(belowBlockRotation);
/*     */       
/* 272 */       if (supporting != null) {
/* 273 */         BlockFaceSupport[] support = supporting.get(BlockFace.UP);
/* 274 */         hollow = (support == null);
/*     */       } 
/*     */     } 
/*     */     
/* 278 */     int blockId = BlockType.getAssetMap().getIndex(blockType.getId());
/* 279 */     StairConnectedBlockRuleSet.StairType stairType = getConnectedBlockStairType(world, coordinate, this, blockId, rotation, this.width);
/*     */ 
/*     */     
/* 282 */     if (this.topper != null && stairType == StairConnectedBlockRuleSet.StairType.STRAIGHT) {
/* 283 */       Vector3i belowCoordinate = (new Vector3i(coordinate)).add(0, -1, 0);
/* 284 */       RotationTuple currentRotation = RotationTuple.get(rotation);
/* 285 */       currentRotation = RotationTuple.of(Rotation.None, currentRotation.pitch(), currentRotation.roll());
/*     */       
/* 287 */       Vector3i mutablePos = new Vector3i();
/* 288 */       boolean topper = canBeTopper(world, belowCoordinate, this, currentRotation, mutablePos);
/*     */       
/* 290 */       if (topper) {
/* 291 */         BlockType topperBlockType = (BlockType)BlockType.getAssetMap().getAsset(this.topper.blockTypeKey);
/*     */         
/* 293 */         if (topperBlockType != null) {
/* 294 */           return Optional.of(new ConnectedBlocksUtil.ConnectedBlockResult(topperBlockType.getId(), rotation));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     if (this.hollow != null && hollow) {
/* 300 */       BlockType hollowBlockType = this.hollow.getStairBlockType(stairType);
/*     */       
/* 302 */       if (hollowBlockType != null) {
/* 303 */         return Optional.of(new ConnectedBlocksUtil.ConnectedBlockResult(hollowBlockType.getId(), rotation));
/*     */       }
/*     */     } 
/*     */     
/* 307 */     BlockType regularBlockType = this.regular.getStairBlockType(stairType);
/*     */     
/* 309 */     if (regularBlockType != null) {
/* 310 */       ConnectedBlocksUtil.ConnectedBlockResult result = new ConnectedBlocksUtil.ConnectedBlockResult(regularBlockType.getId(), rotation);
/*     */ 
/*     */       
/* 313 */       if (this.regular != null && this.width > 0) {
/* 314 */         StairConnectedBlockRuleSet.StairType existingStairType = this.regular.getStairType(BlockType.getAssetMap().getIndex(blockType.getId()));
/*     */         
/* 316 */         if (existingStairType != null && existingStairType != StairConnectedBlockRuleSet.StairType.STRAIGHT) {
/*     */ 
/*     */           
/* 319 */           int previousWidth = existingStairType.isLeft() ? -(this.width - 1) : (existingStairType.isRight() ? (this.width - 1) : 0);
/*     */ 
/*     */           
/* 322 */           int newWidth = stairType.isLeft() ? -(this.width - 1) : (stairType.isRight() ? (this.width - 1) : 0);
/*     */           
/* 324 */           if (newWidth != previousWidth) {
/* 325 */             Vector3i mutablePos = new Vector3i();
/* 326 */             Rotation currentYaw = RotationTuple.get(rotation).yaw();
/*     */             
/* 328 */             mutablePos.assign(Vector3i.EAST).scale(previousWidth);
/* 329 */             currentYaw.rotateY(mutablePos, mutablePos);
/*     */             
/* 331 */             result.addAdditionalBlock(mutablePos, regularBlockType.getId(), rotation);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 336 */       return Optional.of(result);
/*     */     } 
/*     */     
/* 339 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCachedBlockTypes(BlockType baseBlockType, BlockTypeAssetMap<String, BlockType> assetMap) {
/* 344 */     if (this.regular != null) {
/* 345 */       this.regular.updateCachedBlockTypes(baseBlockType, assetMap);
/*     */     }
/*     */     
/* 348 */     if (this.hollow != null) {
/* 349 */       this.hollow.updateCachedBlockTypes(baseBlockType, assetMap);
/*     */     }
/*     */     
/* 352 */     if (this.topper != null) {
/* 353 */       this.topper.resolve(baseBlockType, assetMap);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public StairConnectedBlockRuleSet.StairType getStairType(int blockId) {
/* 359 */     StairConnectedBlockRuleSet.StairType regularStairType = this.regular.getStairType(blockId);
/* 360 */     if (regularStairType != null) {
/* 361 */       return regularStairType;
/*     */     }
/*     */     
/* 364 */     if (this.hollow != null) {
/* 365 */       return this.hollow.getStairType(blockId);
/*     */     }
/*     */     
/* 368 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getMaterialName() {
/* 374 */     return this.materialName;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ConnectedBlockRuleSet toPacket(BlockTypeAssetMap<String, BlockType> assetMap) {
/* 380 */     ConnectedBlockRuleSet packet = new ConnectedBlockRuleSet();
/* 381 */     packet.type = ConnectedBlockRuleSetType.Roof;
/*     */     
/* 383 */     com.hypixel.hytale.protocol.RoofConnectedBlockRuleSet roofPacket = new com.hypixel.hytale.protocol.RoofConnectedBlockRuleSet();
/*     */     
/* 385 */     if (this.regular != null) {
/* 386 */       roofPacket.regular = this.regular.toProtocol(assetMap);
/*     */     }
/*     */     
/* 389 */     if (this.hollow != null) {
/* 390 */       roofPacket.hollow = this.hollow.toProtocol(assetMap);
/*     */     }
/*     */     
/* 393 */     if (this.topper != null) {
/* 394 */       roofPacket.topperBlockId = assetMap.getIndex(this.topper.blockTypeKey);
/*     */     } else {
/* 396 */       roofPacket.topperBlockId = -1;
/*     */     } 
/*     */     
/* 399 */     roofPacket.width = this.width;
/* 400 */     roofPacket.materialName = this.materialName;
/*     */     
/* 402 */     packet.roof = roofPacket;
/* 403 */     return packet;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\connectedblocks\builtin\RoofConnectedBlockRuleSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */