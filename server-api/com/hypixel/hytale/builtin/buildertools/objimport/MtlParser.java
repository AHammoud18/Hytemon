/*     */ package com.hypixel.hytale.builtin.buildertools.objimport;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashMap;
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
/*     */ public final class MtlParser
/*     */ {
/*     */   @Nonnull
/*     */   public static Map<String, MtlMaterial> parse(@Nonnull Path path) throws IOException {
/*  29 */     Map<String, MtlMaterial> materials = new HashMap<>();
/*  30 */     String currentMaterial = null;
/*  31 */     float[] currentKd = null;
/*  32 */     String currentMapKd = null;
/*     */     
/*  34 */     BufferedReader reader = Files.newBufferedReader(path); 
/*     */     try { String line;
/*  36 */       while ((line = reader.readLine()) != null) {
/*  37 */         line = line.trim();
/*  38 */         if (line.isEmpty() || line.startsWith("#"))
/*     */           continue; 
/*  40 */         String[] parts = line.split("\\s+", 2);
/*  41 */         if (parts.length == 0)
/*     */           continue; 
/*  43 */         switch (parts[0]) {
/*     */           
/*     */           case "newmtl":
/*  46 */             if (currentMaterial != null) {
/*  47 */               materials.put(currentMaterial, new MtlMaterial(currentMaterial, currentKd, currentMapKd));
/*     */             }
/*  49 */             currentMaterial = (parts.length > 1) ? parts[1].trim() : "";
/*  50 */             currentKd = null;
/*  51 */             currentMapKd = null;
/*     */ 
/*     */           
/*     */           case "Kd":
/*  55 */             if (parts.length > 1) {
/*  56 */               currentKd = parseColor(parts[1]);
/*     */             }
/*     */ 
/*     */           
/*     */           case "map_Kd":
/*  61 */             if (parts.length > 1) {
/*  62 */               currentMapKd = parts[1].trim();
/*     */             }
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/*  69 */       if (currentMaterial != null) {
/*  70 */         materials.put(currentMaterial, new MtlMaterial(currentMaterial, currentKd, currentMapKd));
/*     */       }
/*  72 */       if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null)
/*     */         try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*  74 */      return materials;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static float[] parseColor(String colorStr) {
/*  79 */     String[] parts = colorStr.trim().split("\\s+");
/*  80 */     if (parts.length < 3) return null;
/*     */     
/*     */     try {
/*  83 */       float r = Float.parseFloat(parts[0]);
/*  84 */       float g = Float.parseFloat(parts[1]);
/*  85 */       float b = Float.parseFloat(parts[2]);
/*  86 */       return new float[] { r, g, b };
/*  87 */     } catch (NumberFormatException e) {
/*  88 */       return null;
/*     */     } 
/*     */   } public static final class MtlMaterial extends Record {
/*     */     @Nonnull
/*     */     private final String name; @Nullable
/*     */     private final float[] diffuseColor; @Nullable
/*     */     private final String diffuseTexturePath; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #99	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;
/*     */     } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #99	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;
/*     */     }
/*  99 */     public MtlMaterial(@Nonnull String name, @Nullable float[] diffuseColor, @Nullable String diffuseTexturePath) { this.name = name; this.diffuseColor = diffuseColor; this.diffuseTexturePath = diffuseTexturePath; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #99	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;
/*  99 */       //   0	8	1	o	Ljava/lang/Object; } @Nonnull public String name() { return this.name; } @Nullable public float[] diffuseColor() { return this.diffuseColor; } @Nullable public String diffuseTexturePath() { return this.diffuseTexturePath; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public int[] getDiffuseColorRGB() {
/* 110 */       if (this.diffuseColor == null) return null; 
/* 111 */       return new int[] {
/* 112 */           Math.max(0, Math.min(255, Math.round(this.diffuseColor[0] * 255.0F))), 
/* 113 */           Math.max(0, Math.min(255, Math.round(this.diffuseColor[1] * 255.0F))), 
/* 114 */           Math.max(0, Math.min(255, Math.round(this.diffuseColor[2] * 255.0F)))
/*     */         };
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\objimport\MtlParser.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */