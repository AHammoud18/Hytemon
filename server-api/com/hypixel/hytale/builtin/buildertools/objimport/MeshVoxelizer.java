/*     */ package com.hypixel.hytale.builtin.buildertools.objimport;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.BlockColorIndex;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class MeshVoxelizer
/*     */ {
/*     */   @Nonnull
/*     */   public static VoxelResult voxelize(@Nonnull ObjParser.ObjMesh mesh, int targetHeight, boolean fillSolid) {
/*  28 */     return voxelize(mesh, targetHeight, fillSolid, null, null, null, 0, false);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static VoxelResult voxelize(@Nonnull ObjParser.ObjMesh mesh, int targetHeight, boolean fillSolid, @Nullable Map<String, Integer> materialToBlockId) {
/*  47 */     return voxelize(mesh, targetHeight, fillSolid, null, materialToBlockId, null, 0, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static VoxelResult voxelize(@Nonnull ObjParser.ObjMesh mesh, int targetHeight, boolean fillSolid, @Nullable Map<String, Integer> materialToBlockId, int defaultBlockId) {
/*  68 */     return voxelize(mesh, targetHeight, fillSolid, null, materialToBlockId, null, defaultBlockId, false);
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
/*     */   public static VoxelResult voxelize(@Nonnull ObjParser.ObjMesh mesh, int targetHeight, boolean fillSolid, @Nullable Map<String, BufferedImage> materialTextures, @Nullable Map<String, Integer> materialToBlockId, @Nullable BlockColorIndex colorIndex, int defaultBlockId) {
/*  93 */     return voxelize(mesh, targetHeight, fillSolid, materialTextures, materialToBlockId, colorIndex, defaultBlockId, false);
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
/*     */   public static VoxelResult voxelize(@Nonnull ObjParser.ObjMesh mesh, int targetHeight, boolean fillSolid, @Nullable Map<String, BufferedImage> materialTextures, @Nullable Map<String, Integer> materialToBlockId, @Nullable BlockColorIndex colorIndex, int defaultBlockId, boolean preserveOrigin) {
/*     */     int sizeX, sizeY, sizeZ;
/* 120 */     float[] bounds = mesh.getBounds();
/* 121 */     float meshHeight = bounds[4] - bounds[1];
/* 122 */     float meshWidth = bounds[3] - bounds[0];
/* 123 */     float meshDepth = bounds[5] - bounds[2];
/*     */     
/* 125 */     if (meshHeight <= 0.0F) {
/* 126 */       return new VoxelResult(new boolean[1][1][1], null, 1, 1, 1);
/*     */     }
/*     */ 
/*     */     
/* 130 */     float scale = targetHeight / meshHeight;
/*     */ 
/*     */     
/* 133 */     float[][] scaledVertices = new float[mesh.vertices().size()][3];
/*     */ 
/*     */     
/* 136 */     if (preserveOrigin) {
/*     */       
/* 138 */       float scaledMinX = bounds[0] * scale;
/* 139 */       float scaledMaxX = bounds[3] * scale;
/* 140 */       float scaledMinY = bounds[1] * scale;
/* 141 */       float scaledMaxY = bounds[4] * scale;
/* 142 */       float scaledMinZ = bounds[2] * scale;
/* 143 */       float scaledMaxZ = bounds[5] * scale;
/*     */ 
/*     */       
/* 146 */       float offsetX = (scaledMinX < 0.0F) ? (-scaledMinX + 1.0F) : 1.0F;
/* 147 */       float offsetY = (scaledMinY < 0.0F) ? (-scaledMinY + 1.0F) : 1.0F;
/* 148 */       float offsetZ = (scaledMinZ < 0.0F) ? (-scaledMinZ + 1.0F) : 1.0F;
/*     */       
/* 150 */       for (int i = 0; i < mesh.vertices().size(); i++) {
/* 151 */         float[] v = mesh.vertices().get(i);
/* 152 */         scaledVertices[i][0] = v[0] * scale + offsetX;
/* 153 */         scaledVertices[i][1] = v[1] * scale + offsetY;
/* 154 */         scaledVertices[i][2] = v[2] * scale + offsetZ;
/*     */       } 
/*     */ 
/*     */       
/* 158 */       sizeX = Math.max(1, (int)Math.ceil((scaledMaxX + offsetX))) + 2;
/* 159 */       sizeY = Math.max(1, (int)Math.ceil((scaledMaxY + offsetY))) + 2;
/* 160 */       sizeZ = Math.max(1, (int)Math.ceil((scaledMaxZ + offsetZ))) + 2;
/*     */     }
/*     */     else {
/*     */       
/* 164 */       sizeX = Math.max(1, (int)Math.ceil((meshWidth * scale))) + 2;
/* 165 */       sizeY = Math.max(1, targetHeight) + 2;
/* 166 */       sizeZ = Math.max(1, (int)Math.ceil((meshDepth * scale))) + 2;
/*     */       
/* 168 */       for (int i = 0; i < mesh.vertices().size(); i++) {
/* 169 */         float[] v = mesh.vertices().get(i);
/*     */         
/* 171 */         scaledVertices[i][0] = (v[0] - bounds[0]) * scale + 1.0F;
/* 172 */         scaledVertices[i][1] = (v[1] - bounds[1]) * scale + 1.0F;
/* 173 */         scaledVertices[i][2] = (v[2] - bounds[2]) * scale + 1.0F;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 178 */     boolean[][][] shell = new boolean[sizeX][sizeY][sizeZ];
/* 179 */     boolean hasTextures = (materialTextures != null && !materialTextures.isEmpty() && colorIndex != null);
/*     */     
/* 181 */     int[][][] blockIds = (hasTextures || materialToBlockId != null || defaultBlockId != 0) ? new int[sizeX][sizeY][sizeZ] : null;
/*     */     
/* 183 */     rasterizeSurface(shell, blockIds, scaledVertices, mesh, materialTextures, materialToBlockId, colorIndex, defaultBlockId, sizeX, sizeY, sizeZ);
/*     */ 
/*     */     
/* 186 */     if (fillSolid) {
/*     */       
/* 188 */       boolean[][][] solid = floodFillSolid(shell, sizeX, sizeY, sizeZ);
/*     */       
/* 190 */       if (blockIds != null) {
/* 191 */         fillInteriorBlockIds(solid, shell, blockIds, defaultBlockId, sizeX, sizeY, sizeZ);
/*     */       }
/* 193 */       return cropToSolidBounds(solid, blockIds, sizeX, sizeY, sizeZ);
/*     */     } 
/*     */     
/* 196 */     return cropToSolidBounds(shell, blockIds, sizeX, sizeY, sizeZ);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int resolveIndex(int index, int count) {
/* 201 */     return (index < 0) ? (count + index) : index;
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
/*     */ 
/*     */   
/*     */   private static void rasterizeSurface(boolean[][][] voxels, @Nullable int[][][] blockIds, float[][] vertices, ObjParser.ObjMesh mesh, @Nullable Map<String, BufferedImage> materialTextures, @Nullable Map<String, Integer> materialToBlockId, @Nullable BlockColorIndex colorIndex, int defaultBlockId, int sizeX, int sizeY, int sizeZ) {
/* 218 */     List<int[]> faces = mesh.faces();
/* 219 */     List<int[]> faceUvIndices = mesh.faceUvIndices();
/* 220 */     List<float[]> uvCoordinates = mesh.uvCoordinates();
/* 221 */     List<String> faceMaterials = mesh.faceMaterials();
/* 222 */     boolean hasTextures = (materialTextures != null && !materialTextures.isEmpty() && colorIndex != null);
/*     */     
/* 224 */     for (int faceIdx = 0; faceIdx < faces.size(); faceIdx++) {
/* 225 */       int[] face = faces.get(faceIdx);
/* 226 */       int i0 = resolveIndex(face[0], vertices.length);
/* 227 */       int i1 = resolveIndex(face[1], vertices.length);
/* 228 */       int i2 = resolveIndex(face[2], vertices.length);
/*     */       
/* 230 */       float[] v0 = vertices[i0];
/* 231 */       float[] v1 = vertices[i1];
/* 232 */       float[] v2 = vertices[i2];
/*     */ 
/*     */       
/* 235 */       String material = (faceIdx < faceMaterials.size()) ? faceMaterials.get(faceIdx) : null;
/* 236 */       BufferedImage texture = null;
/* 237 */       int faceBlockId = defaultBlockId;
/*     */       
/* 239 */       if (material != null) {
/*     */         
/* 241 */         if (hasTextures) {
/* 242 */           texture = materialTextures.get(material);
/*     */         }
/*     */         
/* 245 */         if (texture == null && materialToBlockId != null) {
/* 246 */           faceBlockId = ((Integer)materialToBlockId.getOrDefault(material, Integer.valueOf(defaultBlockId))).intValue();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 251 */       float[] uv0 = null, uv1 = null, uv2 = null;
/* 252 */       if (texture != null && faceIdx < faceUvIndices.size()) {
/* 253 */         int[] uvIndices = faceUvIndices.get(faceIdx);
/* 254 */         if (uvIndices != null && uvIndices.length >= 3) {
/* 255 */           int uvCount = uvCoordinates.size();
/* 256 */           int ui0 = resolveIndex(uvIndices[0], uvCount);
/* 257 */           int ui1 = resolveIndex(uvIndices[1], uvCount);
/* 258 */           int ui2 = resolveIndex(uvIndices[2], uvCount);
/*     */           
/* 260 */           if (ui0 >= 0 && ui0 < uvCount) uv0 = uvCoordinates.get(ui0); 
/* 261 */           if (ui1 >= 0 && ui1 < uvCount) uv1 = uvCoordinates.get(ui1); 
/* 262 */           if (ui2 >= 0 && ui2 < uvCount) uv2 = uvCoordinates.get(ui2);
/*     */         
/*     */         } 
/*     */       } 
/*     */       
/* 267 */       rasterizeLine(voxels, blockIds, v0, v1, uv0, uv1, texture, colorIndex, faceBlockId, sizeX, sizeY, sizeZ);
/* 268 */       rasterizeLine(voxels, blockIds, v1, v2, uv1, uv2, texture, colorIndex, faceBlockId, sizeX, sizeY, sizeZ);
/* 269 */       rasterizeLine(voxels, blockIds, v2, v0, uv2, uv0, texture, colorIndex, faceBlockId, sizeX, sizeY, sizeZ);
/*     */ 
/*     */       
/* 272 */       rasterizeTriangle(voxels, blockIds, v0, v1, v2, uv0, uv1, uv2, texture, colorIndex, faceBlockId, sizeX, sizeY, sizeZ);
/*     */     } 
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
/*     */   
/*     */   private static void rasterizeLine(boolean[][][] voxels, @Nullable int[][][] blockIds, float[] a, float[] b, @Nullable float[] uvA, @Nullable float[] uvB, @Nullable BufferedImage texture, @Nullable BlockColorIndex colorIndex, int fallbackBlockId, int sizeX, int sizeY, int sizeZ) {
/* 289 */     float dx = b[0] - a[0];
/* 290 */     float dy = b[1] - a[1];
/* 291 */     float dz = b[2] - a[2];
/* 292 */     float len = (float)Math.sqrt((dx * dx + dy * dy + dz * dz));
/*     */     
/* 294 */     if (len < 0.001F) {
/* 295 */       int blockId = sampleBlockId(uvA, texture, colorIndex, fallbackBlockId);
/* 296 */       setVoxel(voxels, blockIds, (int)a[0], (int)a[1], (int)a[2], blockId, sizeX, sizeY, sizeZ);
/*     */       
/*     */       return;
/*     */     } 
/* 300 */     int steps = (int)Math.ceil((len * 2.0F)) + 1;
/* 301 */     for (int i = 0; i <= steps; i++) {
/* 302 */       float t = i / steps;
/* 303 */       float x = a[0] + dx * t;
/* 304 */       float y = a[1] + dy * t;
/* 305 */       float z = a[2] + dz * t;
/*     */ 
/*     */       
/* 308 */       float[] uv = interpolateUv(uvA, uvB, t);
/* 309 */       int blockId = sampleBlockId(uv, texture, colorIndex, fallbackBlockId);
/*     */       
/* 311 */       setVoxel(voxels, blockIds, (int)x, (int)y, (int)z, blockId, sizeX, sizeY, sizeZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static float[] interpolateUv(@Nullable float[] uvA, @Nullable float[] uvB, float t) {
/* 320 */     if (uvA == null || uvB == null) return uvA; 
/* 321 */     return new float[] { uvA[0] + (uvB[0] - uvA[0]) * t, uvA[1] + (uvB[1] - uvA[1]) * t };
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
/*     */   private static int sampleBlockId(@Nullable float[] uv, @Nullable BufferedImage texture, @Nullable BlockColorIndex colorIndex, int fallbackBlockId) {
/* 336 */     if (uv == null || texture == null || colorIndex == null) {
/* 337 */       return fallbackBlockId;
/*     */     }
/*     */ 
/*     */     
/* 341 */     int alpha = TextureSampler.sampleAlphaAt(texture, uv[0], uv[1]);
/* 342 */     if (alpha < 128) {
/* 343 */       return 0;
/*     */     }
/*     */     
/* 346 */     int[] rgb = TextureSampler.sampleAt(texture, uv[0], uv[1]);
/* 347 */     int blockId = colorIndex.findClosestBlock(rgb[0], rgb[1], rgb[2]);
/* 348 */     return (blockId > 0) ? blockId : fallbackBlockId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setVoxel(boolean[][][] voxels, @Nullable int[][][] blockIds, int x, int y, int z, int blockId, int sizeX, int sizeY, int sizeZ) {
/* 358 */     if (x >= 0 && x < sizeX && y >= 0 && y < sizeY && z >= 0 && z < sizeZ) {
/* 359 */       voxels[x][y][z] = true;
/* 360 */       if (blockIds != null && blockId != 0 && blockIds[x][y][z] == 0) {
/* 361 */         blockIds[x][y][z] = blockId;
/*     */       }
/*     */     } 
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
/*     */   
/*     */   private static void rasterizeTriangle(boolean[][][] voxels, @Nullable int[][][] blockIds, float[] v0, float[] v1, float[] v2, @Nullable float[] uv0, @Nullable float[] uv1, @Nullable float[] uv2, @Nullable BufferedImage texture, @Nullable BlockColorIndex colorIndex, int fallbackBlockId, int sizeX, int sizeY, int sizeZ) {
/* 379 */     float minX = Math.min(v0[0], Math.min(v1[0], v2[0]));
/* 380 */     float maxX = Math.max(v0[0], Math.max(v1[0], v2[0]));
/* 381 */     float minY = Math.min(v0[1], Math.min(v1[1], v2[1]));
/* 382 */     float maxY = Math.max(v0[1], Math.max(v1[1], v2[1]));
/* 383 */     float minZ = Math.min(v0[2], Math.min(v1[2], v2[2]));
/* 384 */     float maxZ = Math.max(v0[2], Math.max(v1[2], v2[2]));
/*     */     
/* 386 */     int startX = Math.max(0, (int)Math.floor(minX) - 1);
/* 387 */     int endX = Math.min(sizeX - 1, (int)Math.ceil(maxX) + 1);
/* 388 */     int startY = Math.max(0, (int)Math.floor(minY) - 1);
/* 389 */     int endY = Math.min(sizeY - 1, (int)Math.ceil(maxY) + 1);
/* 390 */     int startZ = Math.max(0, (int)Math.floor(minZ) - 1);
/* 391 */     int endZ = Math.min(sizeZ - 1, (int)Math.ceil(maxZ) + 1);
/*     */     
/* 393 */     boolean hasUvSampling = (uv0 != null && uv1 != null && uv2 != null && texture != null && colorIndex != null);
/*     */     
/* 395 */     for (int x = startX; x <= endX; x++) {
/* 396 */       for (int y = startY; y <= endY; y++) {
/* 397 */         for (int z = startZ; z <= endZ; z++) {
/* 398 */           float px = x + 0.5F;
/* 399 */           float py = y + 0.5F;
/* 400 */           float pz = z + 0.5F;
/*     */           
/* 402 */           if (pointNearTriangle(px, py, pz, v0, v1, v2, 0.87F)) {
/* 403 */             int blockId = fallbackBlockId;
/*     */             
/* 405 */             if (hasUvSampling) {
/*     */               
/* 407 */               float[] bary = barycentric(px, py, pz, v0, v1, v2);
/* 408 */               if (bary != null) {
/*     */                 
/* 410 */                 float u = bary[0] * uv0[0] + bary[1] * uv1[0] + bary[2] * uv2[0];
/* 411 */                 float v = bary[0] * uv0[1] + bary[1] * uv1[1] + bary[2] * uv2[1];
/*     */ 
/*     */                 
/* 414 */                 int alpha = TextureSampler.sampleAlphaAt(texture, u, v);
/* 415 */                 if (alpha < 128) {
/*     */                   continue;
/*     */                 }
/*     */                 
/* 419 */                 int[] rgb = TextureSampler.sampleAt(texture, u, v);
/* 420 */                 int sampledId = colorIndex.findClosestBlock(rgb[0], rgb[1], rgb[2]);
/* 421 */                 if (sampledId > 0) {
/* 422 */                   blockId = sampledId;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 427 */             voxels[x][y][z] = true;
/* 428 */             if (blockIds != null && blockId != 0 && blockIds[x][y][z] == 0) {
/* 429 */               blockIds[x][y][z] = blockId;
/*     */             }
/*     */           } 
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static float[] barycentric(float px, float py, float pz, float[] v0, float[] v1, float[] v2) {
/* 445 */     float u0, u1, u2, v0c, v1c, v2c, pu, pv, e1[] = { v1[0] - v0[0], v1[1] - v0[1], v1[2] - v0[2] };
/* 446 */     float[] e2 = { v2[0] - v0[0], v2[1] - v0[1], v2[2] - v0[2] };
/*     */ 
/*     */     
/* 449 */     float nx = e1[1] * e2[2] - e1[2] * e2[1];
/* 450 */     float ny = e1[2] * e2[0] - e1[0] * e2[2];
/* 451 */     float nz = e1[0] * e2[1] - e1[1] * e2[0];
/*     */ 
/*     */     
/* 454 */     float ax = Math.abs(nx), ay = Math.abs(ny), az = Math.abs(nz);
/*     */ 
/*     */     
/* 457 */     if (ax >= ay && ax >= az) {
/*     */       
/* 459 */       u0 = v0[1]; v0c = v0[2];
/* 460 */       u1 = v1[1]; v1c = v1[2];
/* 461 */       u2 = v2[1]; v2c = v2[2];
/* 462 */       pu = py; pv = pz;
/* 463 */     } else if (ay >= ax && ay >= az) {
/*     */       
/* 465 */       u0 = v0[0]; v0c = v0[2];
/* 466 */       u1 = v1[0]; v1c = v1[2];
/* 467 */       u2 = v2[0]; v2c = v2[2];
/* 468 */       pu = px; pv = pz;
/*     */     } else {
/*     */       
/* 471 */       u0 = v0[0]; v0c = v0[1];
/* 472 */       u1 = v1[0]; v1c = v1[1];
/* 473 */       u2 = v2[0]; v2c = v2[1];
/* 474 */       pu = px; pv = py;
/*     */     } 
/*     */ 
/*     */     
/* 478 */     float denom = (v1c - v2c) * (u0 - u2) + (u2 - u1) * (v0c - v2c);
/* 479 */     if (Math.abs(denom) < 1.0E-10F) return null;
/*     */     
/* 481 */     float w0 = ((v1c - v2c) * (pu - u2) + (u2 - u1) * (pv - v2c)) / denom;
/* 482 */     float w1 = ((v2c - v0c) * (pu - u2) + (u0 - u2) * (pv - v2c)) / denom;
/* 483 */     float w2 = 1.0F - w0 - w1;
/*     */     
/* 485 */     return new float[] { w0, w1, w2 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean pointNearTriangle(float px, float py, float pz, float[] v0, float[] v1, float[] v2, float threshold) {
/* 493 */     float e1x = v1[0] - v0[0], e1y = v1[1] - v0[1], e1z = v1[2] - v0[2];
/* 494 */     float e2x = v2[0] - v0[0], e2y = v2[1] - v0[1], e2z = v2[2] - v0[2];
/*     */     
/* 496 */     float nx = e1y * e2z - e1z * e2y;
/* 497 */     float ny = e1z * e2x - e1x * e2z;
/* 498 */     float nz = e1x * e2y - e1y * e2x;
/* 499 */     float lenSq = nx * nx + ny * ny + nz * nz;
/* 500 */     if (lenSq < 1.0E-12F) return false; 
/* 501 */     float len = (float)Math.sqrt(lenSq);
/*     */     
/* 503 */     float dpx = px - v0[0], dpy = py - v0[1], dpz = pz - v0[2];
/* 504 */     float dotNP = nx * dpx + ny * dpy + nz * dpz;
/* 505 */     float dist = Math.abs(dotNP) / len;
/* 506 */     if (dist > threshold) return false;
/*     */     
/* 508 */     float t = dotNP / lenSq;
/* 509 */     float projX = px - t * nx;
/* 510 */     float projY = py - t * ny;
/* 511 */     float projZ = pz - t * nz;
/*     */     
/* 513 */     return pointInTriangleWithTolerance(projX, projY, projZ, v0, v1, v2, 0.1F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean pointInTriangleWithTolerance(float px, float py, float pz, float[] v0, float[] v1, float[] v2, float tolerance) {
/* 519 */     float vax = v1[0] - v0[0], vay = v1[1] - v0[1], vaz = v1[2] - v0[2];
/* 520 */     float vbx = v2[0] - v0[0], vby = v2[1] - v0[1], vbz = v2[2] - v0[2];
/* 521 */     float vpx = px - v0[0], vpy = py - v0[1], vpz = pz - v0[2];
/*     */     
/* 523 */     float d00 = vax * vax + vay * vay + vaz * vaz;
/* 524 */     float d01 = vax * vbx + vay * vby + vaz * vbz;
/* 525 */     float d11 = vbx * vbx + vby * vby + vbz * vbz;
/* 526 */     float d20 = vpx * vax + vpy * vay + vpz * vaz;
/* 527 */     float d21 = vpx * vbx + vpy * vby + vpz * vbz;
/*     */     
/* 529 */     float denom = d00 * d11 - d01 * d01;
/* 530 */     if (Math.abs(denom) < 1.0E-12F) return false;
/*     */     
/* 532 */     float u = (d11 * d20 - d01 * d21) / denom;
/* 533 */     float v = (d00 * d21 - d01 * d20) / denom;
/*     */     
/* 535 */     return (u >= -tolerance && v >= -tolerance && u + v <= 1.0F + tolerance);
/*     */   }
/*     */   public static final class VoxelResult extends Record { private final boolean[][][] voxels; @Nullable
/*     */     private final int[][][] blockIds; private final int sizeX; private final int sizeY;
/*     */     private final int sizeZ;
/*     */     
/* 541 */     public VoxelResult(boolean[][][] voxels, @Nullable int[][][] blockIds, int sizeX, int sizeY, int sizeZ) { this.voxels = voxels; this.blockIds = blockIds; this.sizeX = sizeX; this.sizeY = sizeY; this.sizeZ = sizeZ; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MeshVoxelizer$VoxelResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #541	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 541 */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MeshVoxelizer$VoxelResult; } public boolean[][][] voxels() { return this.voxels; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MeshVoxelizer$VoxelResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #541	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MeshVoxelizer$VoxelResult; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MeshVoxelizer$VoxelResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #541	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MeshVoxelizer$VoxelResult;
/* 541 */       //   0	8	1	o	Ljava/lang/Object; } @Nullable public int[][][] blockIds() { return this.blockIds; } public int sizeX() { return this.sizeX; } public int sizeY() { return this.sizeY; } public int sizeZ() { return this.sizeZ; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int countSolid() {
/* 547 */       int count = 0;
/* 548 */       for (int x = 0; x < this.sizeX; x++) {
/* 549 */         for (int y = 0; y < this.sizeY; y++) {
/* 550 */           for (int z = 0; z < this.sizeZ; z++) {
/* 551 */             if (this.voxels[x][y][z]) count++; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 555 */       return count;
/*     */     }
/*     */     
/*     */     public int getBlockId(int x, int y, int z) {
/* 559 */       if (this.blockIds == null) return 0; 
/* 560 */       if (x < 0 || x >= this.sizeX || y < 0 || y >= this.sizeY || z < 0 || z >= this.sizeZ) return 0; 
/* 561 */       return this.blockIds[x][y][z];
/*     */     } }
/*     */ 
/*     */   
/*     */   private static boolean[][][] floodFillSolid(boolean[][][] shell, int sizeX, int sizeY, int sizeZ) {
/* 566 */     int dx = sizeX + 2;
/* 567 */     int dy = sizeY + 2;
/* 568 */     int dz = sizeZ + 2;
/* 569 */     int plane = dx * dy;
/* 570 */     int total = plane * dz;
/*     */     
/* 572 */     boolean[] visited = new boolean[total];
/* 573 */     int[] queue = new int[total];
/* 574 */     int qh = 0;
/* 575 */     int qt = 0;
/*     */     
/* 577 */     visited[0] = true;
/* 578 */     queue[qt++] = 0;
/*     */     
/* 580 */     while (qh < qt) {
/* 581 */       int idx = queue[qh++];
/* 582 */       int i = idx % dx;
/* 583 */       int y = idx / dx % dy;
/* 584 */       int z = idx / plane;
/*     */       
/* 586 */       if (i + 1 < dx && tryEnqueue(shell, sizeX, sizeY, sizeZ, visited, queue, i + 1, y, z, dx, plane, qt)) qt++; 
/* 587 */       if (i - 1 >= 0 && tryEnqueue(shell, sizeX, sizeY, sizeZ, visited, queue, i - 1, y, z, dx, plane, qt)) qt++; 
/* 588 */       if (y + 1 < dy && tryEnqueue(shell, sizeX, sizeY, sizeZ, visited, queue, i, y + 1, z, dx, plane, qt)) qt++; 
/* 589 */       if (y - 1 >= 0 && tryEnqueue(shell, sizeX, sizeY, sizeZ, visited, queue, i, y - 1, z, dx, plane, qt)) qt++; 
/* 590 */       if (z + 1 < dz && tryEnqueue(shell, sizeX, sizeY, sizeZ, visited, queue, i, y, z + 1, dx, plane, qt)) qt++; 
/* 591 */       if (z - 1 >= 0 && tryEnqueue(shell, sizeX, sizeY, sizeZ, visited, queue, i, y, z - 1, dx, plane, qt)) qt++;
/*     */     
/*     */     } 
/* 594 */     boolean[][][] solid = new boolean[sizeX][sizeY][sizeZ];
/* 595 */     for (int x = 0; x < sizeX; x++) {
/* 596 */       for (int y = 0; y < sizeY; y++) {
/* 597 */         for (int z = 0; z < sizeZ; z++) {
/* 598 */           int ex = x + 1;
/* 599 */           int ey = y + 1;
/* 600 */           int ez = z + 1;
/* 601 */           int eIdx = ex + ey * dx + ez * plane;
/* 602 */           solid[x][y][z] = !visited[eIdx];
/*     */         } 
/*     */       } 
/*     */     } 
/* 606 */     return solid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean tryEnqueue(boolean[][][] shell, int sizeX, int sizeY, int sizeZ, boolean[] visited, int[] queue, int ex, int ey, int ez, int dx, int plane, int writeIndex) {
/* 614 */     int idx = ex + ey * dx + ez * plane;
/* 615 */     if (visited[idx]) return false;
/*     */     
/* 617 */     int x = ex - 1;
/* 618 */     int y = ey - 1;
/* 619 */     int z = ez - 1;
/*     */     
/* 621 */     if (x >= 0 && y >= 0 && z >= 0 && x < sizeX && y < sizeY && z < sizeZ && 
/* 622 */       shell[x][y][z]) return false;
/*     */ 
/*     */     
/* 625 */     visited[idx] = true;
/* 626 */     queue[writeIndex] = idx;
/* 627 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static VoxelResult cropToSolidBounds(boolean[][][] voxels, @Nullable int[][][] blockIds, int sizeX, int sizeY, int sizeZ) {
/* 635 */     int minX = sizeX, minY = sizeY, minZ = sizeZ;
/* 636 */     int maxX = -1, maxY = -1, maxZ = -1;
/*     */     
/* 638 */     for (int x = 0; x < sizeX; x++) {
/* 639 */       for (int y = 0; y < sizeY; y++) {
/* 640 */         for (int z = 0; z < sizeZ; z++) {
/* 641 */           if (voxels[x][y][z]) {
/* 642 */             if (x < minX) minX = x; 
/* 643 */             if (y < minY) minY = y; 
/* 644 */             if (z < minZ) minZ = z; 
/* 645 */             if (x > maxX) maxX = x; 
/* 646 */             if (y > maxY) maxY = y; 
/* 647 */             if (z > maxZ) maxZ = z; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 652 */     if (maxX < minX || maxY < minY || maxZ < minZ) {
/* 653 */       return new VoxelResult(new boolean[1][1][1], null, 1, 1, 1);
/*     */     }
/*     */     
/* 656 */     int outX = maxX - minX + 1;
/* 657 */     int outY = maxY - minY + 1;
/* 658 */     int outZ = maxZ - minZ + 1;
/* 659 */     boolean[][][] out = new boolean[outX][outY][outZ];
/* 660 */     int[][][] outBlockIds = (blockIds != null) ? new int[outX][outY][outZ] : null;
/*     */     
/* 662 */     for (int i = 0; i < outX; i++) {
/* 663 */       for (int y = 0; y < outY; y++) {
/* 664 */         System.arraycopy(voxels[minX + i][minY + y], minZ, out[i][y], 0, outZ);
/* 665 */         if (outBlockIds != null && blockIds != null) {
/* 666 */           System.arraycopy(blockIds[minX + i][minY + y], minZ, outBlockIds[i][y], 0, outZ);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 671 */     return new VoxelResult(out, outBlockIds, outX, outY, outZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fillInteriorBlockIds(boolean[][][] solid, boolean[][][] shell, int[][][] blockIds, int defaultBlockId, int sizeX, int sizeY, int sizeZ) {
/* 681 */     for (int x = 0; x < sizeX; x++) {
/* 682 */       for (int y = 0; y < sizeY; y++) {
/* 683 */         for (int z = 0; z < sizeZ; z++) {
/* 684 */           if (solid[x][y][z] && !shell[x][y][z] && blockIds[x][y][z] == 0) {
/* 685 */             int bestId = findNearestSurfaceBlockId(blockIds, shell, x, y, z, sizeX, sizeY, sizeZ);
/* 686 */             blockIds[x][y][z] = (bestId != 0) ? bestId : defaultBlockId;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findNearestSurfaceBlockId(int[][][] blockIds, boolean[][][] shell, int cx, int cy, int cz, int sizeX, int sizeY, int sizeZ) {
/* 699 */     for (int radius = 1; radius <= 5; radius++) {
/* 700 */       for (int dx = -radius; dx <= radius; dx++) {
/* 701 */         for (int dy = -radius; dy <= radius; dy++) {
/* 702 */           for (int dz = -radius; dz <= radius; dz++) {
/* 703 */             int nx = cx + dx;
/* 704 */             int ny = cy + dy;
/* 705 */             int nz = cz + dz;
/* 706 */             if (nx >= 0 && nx < sizeX && ny >= 0 && ny < sizeY && nz >= 0 && nz < sizeZ && 
/* 707 */               shell[nx][ny][nz] && blockIds[nx][ny][nz] != 0) {
/* 708 */               return blockIds[nx][ny][nz];
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 715 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\objimport\MeshVoxelizer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */