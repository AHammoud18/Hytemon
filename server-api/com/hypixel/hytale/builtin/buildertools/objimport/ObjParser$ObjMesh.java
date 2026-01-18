/*     */ package com.hypixel.hytale.builtin.buildertools.objimport;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public final class ObjMesh
/*     */   extends Record
/*     */ {
/*     */   private final List<float[]> vertices;
/*     */   private final List<float[]> uvCoordinates;
/*     */   private final List<int[]> faces;
/*     */   private final List<int[]> faceUvIndices;
/*     */   private final List<String> faceMaterials;
/*     */   @Nullable
/*     */   private final String mtlLib;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #185	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #185	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #185	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public ObjMesh(List<float[]> vertices, List<float[]> uvCoordinates, List<int[]> faces, List<int[]> faceUvIndices, List<String> faceMaterials, @Nullable String mtlLib) {
/* 185 */     this.vertices = vertices; this.uvCoordinates = uvCoordinates; this.faces = faces; this.faceUvIndices = faceUvIndices; this.faceMaterials = faceMaterials; this.mtlLib = mtlLib; } public List<float[]> vertices() { return this.vertices; } public List<float[]> uvCoordinates() { return this.uvCoordinates; } public List<int[]> faces() { return this.faces; } public List<int[]> faceUvIndices() { return this.faceUvIndices; } public List<String> faceMaterials() { return this.faceMaterials; } @Nullable public String mtlLib() { return this.mtlLib; }
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
/*     */   public float[] getBounds() {
/* 197 */     float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, minZ = Float.MAX_VALUE;
/* 198 */     float maxX = -3.4028235E38F, maxY = -3.4028235E38F, maxZ = -3.4028235E38F;
/*     */     
/* 200 */     for (float[] v : this.vertices) {
/* 201 */       minX = Math.min(minX, v[0]);
/* 202 */       minY = Math.min(minY, v[1]);
/* 203 */       minZ = Math.min(minZ, v[2]);
/* 204 */       maxX = Math.max(maxX, v[0]);
/* 205 */       maxY = Math.max(maxY, v[1]);
/* 206 */       maxZ = Math.max(maxZ, v[2]);
/*     */     } 
/*     */     
/* 209 */     return new float[] { minX, minY, minZ, maxX, maxY, maxZ };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/* 216 */     float[] bounds = getBounds();
/* 217 */     return bounds[4] - bounds[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMaterials() {
/* 224 */     return (this.mtlLib != null && !this.faceMaterials.isEmpty() && this.faceMaterials
/* 225 */       .stream().anyMatch(m -> (m != null)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasUvCoordinates() {
/* 232 */     return (!this.uvCoordinates.isEmpty() && this.faceUvIndices.stream().anyMatch(uv -> (uv != null)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformZUpToYUp() {
/* 240 */     for (float[] v : this.vertices) {
/* 241 */       float y = v[1];
/* 242 */       float z = v[2];
/* 243 */       v[1] = z;
/* 244 */       v[2] = -y;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformXUpToYUp() {
/* 252 */     for (float[] v : this.vertices) {
/* 253 */       float x = v[0];
/* 254 */       float y = v[1];
/* 255 */       v[0] = y;
/* 256 */       v[1] = x;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\objimport\ObjParser$ObjMesh.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */