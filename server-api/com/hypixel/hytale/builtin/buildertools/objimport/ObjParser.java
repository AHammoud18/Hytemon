/*     */ package com.hypixel.hytale.builtin.buildertools.objimport;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class ObjParser
/*     */ {
/*     */   @Nonnull
/*     */   public static ObjMesh parse(@Nonnull Path path) throws IOException, ObjParseException {
/*  29 */     List<float[]> vertices = (List)new ArrayList<>();
/*  30 */     List<float[]> uvCoordinates = (List)new ArrayList<>();
/*  31 */     List<int[]> faces = (List)new ArrayList<>();
/*  32 */     List<int[]> faceUvIndices = (List)new ArrayList<>();
/*  33 */     List<String> faceMaterials = new ArrayList<>();
/*  34 */     String mtlLib = null;
/*  35 */     String currentMaterial = null;
/*     */     
/*  37 */     BufferedReader reader = Files.newBufferedReader(path);
/*     */     
/*  39 */     try { int lineNum = 0; String line;
/*  40 */       while ((line = reader.readLine()) != null) {
/*  41 */         int faceCountBefore, facesAdded, i; lineNum++;
/*  42 */         line = line.trim();
/*  43 */         if (line.isEmpty() || line.startsWith("#"))
/*     */           continue; 
/*  45 */         String[] parts = line.split("\\s+");
/*  46 */         if (parts.length == 0)
/*     */           continue; 
/*  48 */         switch (parts[0]) { case "v":
/*  49 */             parseVertex(parts, vertices, lineNum);
/*  50 */           case "vt": parseUvCoordinate(parts, uvCoordinates, lineNum);
/*     */           case "f":
/*  52 */             faceCountBefore = faces.size();
/*  53 */             parseFace(parts, faces, faceUvIndices, uvCoordinates.size(), lineNum);
/*  54 */             facesAdded = faces.size() - faceCountBefore;
/*  55 */             for (i = 0; i < facesAdded; i++) {
/*  56 */               faceMaterials.add(currentMaterial);
/*     */             }
/*     */           
/*     */           case "mtllib":
/*  60 */             if (parts.length > 1) {
/*  61 */               mtlLib = parts[1].trim();
/*     */             }
/*     */           
/*     */           case "usemtl":
/*  65 */             if (parts.length > 1) {
/*  66 */               currentMaterial = parts[1].trim();
/*     */             } }
/*     */ 
/*     */       
/*     */       } 
/*  71 */       if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null)
/*     */         try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*  73 */      if (vertices.isEmpty()) {
/*  74 */       throw new ObjParseException("OBJ file contains no vertices");
/*     */     }
/*  76 */     if (faces.isEmpty()) {
/*  77 */       throw new ObjParseException("OBJ file contains no faces");
/*     */     }
/*     */     
/*  80 */     return new ObjMesh(vertices, uvCoordinates, faces, faceUvIndices, faceMaterials, mtlLib);
/*     */   }
/*     */   
/*     */   private static void parseVertex(String[] parts, List<float[]> vertices, int lineNum) throws ObjParseException {
/*  84 */     if (parts.length < 4) {
/*  85 */       throw new ObjParseException("Invalid vertex at line " + lineNum + ": expected at least 3 coordinates");
/*     */     }
/*     */     try {
/*  88 */       float x = Float.parseFloat(parts[1]);
/*  89 */       float y = Float.parseFloat(parts[2]);
/*  90 */       float z = Float.parseFloat(parts[3]);
/*  91 */       vertices.add(new float[] { x, y, z });
/*  92 */     } catch (NumberFormatException e) {
/*  93 */       throw new ObjParseException("Invalid vertex coordinates at line " + lineNum);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void parseUvCoordinate(String[] parts, List<float[]> uvCoordinates, int lineNum) throws ObjParseException {
/*  98 */     if (parts.length < 3) {
/*  99 */       throw new ObjParseException("Invalid UV coordinate at line " + lineNum + ": expected at least 2 values");
/*     */     }
/*     */     try {
/* 102 */       float u = Float.parseFloat(parts[1]);
/* 103 */       float v = Float.parseFloat(parts[2]);
/* 104 */       uvCoordinates.add(new float[] { u, v });
/* 105 */     } catch (NumberFormatException e) {
/* 106 */       throw new ObjParseException("Invalid UV coordinates at line " + lineNum);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void parseFace(String[] parts, List<int[]> faces, List<int[]> faceUvIndices, int uvCount, int lineNum) throws ObjParseException {
/* 112 */     if (parts.length < 4) {
/* 113 */       throw new ObjParseException("Invalid face at line " + lineNum + ": expected at least 3 vertices");
/*     */     }
/*     */ 
/*     */     
/* 117 */     int[] vertexIndices = new int[parts.length - 1];
/* 118 */     int[] uvIndices = new int[parts.length - 1];
/* 119 */     boolean hasUvs = false;
/*     */     int i;
/* 121 */     for (i = 1; i < parts.length; i++) {
/* 122 */       String vertexData = parts[i];
/* 123 */       String[] components = vertexData.split("/");
/*     */ 
/*     */       
/*     */       try {
/* 127 */         int vIndex = Integer.parseInt(components[0]);
/* 128 */         vertexIndices[i - 1] = (vIndex > 0) ? (vIndex - 1) : vIndex;
/* 129 */       } catch (NumberFormatException e) {
/* 130 */         throw new ObjParseException("Invalid face vertex index at line " + lineNum);
/*     */       } 
/*     */ 
/*     */       
/* 134 */       if (components.length >= 2 && !components[1].isEmpty()) {
/*     */         try {
/* 136 */           int uvIndex = Integer.parseInt(components[1]);
/* 137 */           uvIndices[i - 1] = (uvIndex > 0) ? (uvIndex - 1) : (uvIndex + uvCount);
/* 138 */           hasUvs = true;
/* 139 */         } catch (NumberFormatException e) {
/* 140 */           uvIndices[i - 1] = -1;
/*     */         } 
/*     */       } else {
/* 143 */         uvIndices[i - 1] = -1;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 148 */     if (vertexIndices.length == 3) {
/* 149 */       faces.add(vertexIndices);
/* 150 */       faceUvIndices.add(hasUvs ? uvIndices : null);
/* 151 */     } else if (vertexIndices.length == 4) {
/*     */       
/* 153 */       faces.add(new int[] { vertexIndices[0], vertexIndices[1], vertexIndices[2] });
/* 154 */       faces.add(new int[] { vertexIndices[0], vertexIndices[2], vertexIndices[3] });
/* 155 */       if (hasUvs) {
/* 156 */         faceUvIndices.add(new int[] { uvIndices[0], uvIndices[1], uvIndices[2] });
/* 157 */         faceUvIndices.add(new int[] { uvIndices[0], uvIndices[2], uvIndices[3] });
/*     */       } else {
/* 159 */         faceUvIndices.add((int[])null);
/* 160 */         faceUvIndices.add((int[])null);
/*     */       } 
/*     */     } else {
/*     */       
/* 164 */       for (i = 1; i < vertexIndices.length - 1; i++) {
/* 165 */         faces.add(new int[] { vertexIndices[0], vertexIndices[i], vertexIndices[i + 1] });
/* 166 */         if (hasUvs) {
/* 167 */           faceUvIndices.add(new int[] { uvIndices[0], uvIndices[i], uvIndices[i + 1] });
/*     */         } else {
/* 169 */           faceUvIndices.add((int[])null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static final class ObjMesh extends Record {
/*     */     private final List<float[]> vertices; private final List<float[]> uvCoordinates; private final List<int[]> faces; private final List<int[]> faceUvIndices; private final List<String> faceMaterials; @Nullable
/*     */     private final String mtlLib;
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #185	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #185	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;
/*     */     }
/*     */     
/* 185 */     public ObjMesh(List<float[]> vertices, List<float[]> uvCoordinates, List<int[]> faces, List<int[]> faceUvIndices, List<String> faceMaterials, @Nullable String mtlLib) { this.vertices = vertices; this.uvCoordinates = uvCoordinates; this.faces = faces; this.faceUvIndices = faceUvIndices; this.faceMaterials = faceMaterials; this.mtlLib = mtlLib; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #185	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/ObjParser$ObjMesh;
/* 185 */       //   0	8	1	o	Ljava/lang/Object; } public List<float[]> vertices() { return this.vertices; } public List<float[]> uvCoordinates() { return this.uvCoordinates; } public List<int[]> faces() { return this.faces; } public List<int[]> faceUvIndices() { return this.faceUvIndices; } public List<String> faceMaterials() { return this.faceMaterials; } @Nullable public String mtlLib() { return this.mtlLib; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float[] getBounds() {
/* 197 */       float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, minZ = Float.MAX_VALUE;
/* 198 */       float maxX = -3.4028235E38F, maxY = -3.4028235E38F, maxZ = -3.4028235E38F;
/*     */       
/* 200 */       for (float[] v : this.vertices) {
/* 201 */         minX = Math.min(minX, v[0]);
/* 202 */         minY = Math.min(minY, v[1]);
/* 203 */         minZ = Math.min(minZ, v[2]);
/* 204 */         maxX = Math.max(maxX, v[0]);
/* 205 */         maxY = Math.max(maxY, v[1]);
/* 206 */         maxZ = Math.max(maxZ, v[2]);
/*     */       } 
/*     */       
/* 209 */       return new float[] { minX, minY, minZ, maxX, maxY, maxZ };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getHeight() {
/* 216 */       float[] bounds = getBounds();
/* 217 */       return bounds[4] - bounds[1];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasMaterials() {
/* 224 */       return (this.mtlLib != null && !this.faceMaterials.isEmpty() && this.faceMaterials
/* 225 */         .stream().anyMatch(m -> (m != null)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasUvCoordinates() {
/* 232 */       return (!this.uvCoordinates.isEmpty() && this.faceUvIndices.stream().anyMatch(uv -> (uv != null)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void transformZUpToYUp() {
/* 240 */       for (float[] v : this.vertices) {
/* 241 */         float y = v[1];
/* 242 */         float z = v[2];
/* 243 */         v[1] = z;
/* 244 */         v[2] = -y;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void transformXUpToYUp() {
/* 252 */       for (float[] v : this.vertices) {
/* 253 */         float x = v[0];
/* 254 */         float y = v[1];
/* 255 */         v[0] = y;
/* 256 */         v[1] = x;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ObjParseException extends Exception {
/*     */     public ObjParseException(String message) {
/* 263 */       super(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\objimport\ObjParser.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */