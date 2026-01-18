/*     */ package com.hypixel.hytale.builtin.buildertools.objimport;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MtlMaterial
/*     */   extends Record
/*     */ {
/*     */   @Nonnull
/*     */   private final String name;
/*     */   @Nullable
/*     */   private final float[] diffuseColor;
/*     */   @Nullable
/*     */   private final String diffuseTexturePath;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #99	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #99	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #99	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lcom/hypixel/hytale/builtin/buildertools/objimport/MtlParser$MtlMaterial;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public MtlMaterial(@Nonnull String name, @Nullable float[] diffuseColor, @Nullable String diffuseTexturePath) {
/*  99 */     this.name = name; this.diffuseColor = diffuseColor; this.diffuseTexturePath = diffuseTexturePath; } @Nonnull public String name() { return this.name; } @Nullable public float[] diffuseColor() { return this.diffuseColor; } @Nullable public String diffuseTexturePath() { return this.diffuseTexturePath; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public int[] getDiffuseColorRGB() {
/* 110 */     if (this.diffuseColor == null) return null; 
/* 111 */     return new int[] {
/* 112 */         Math.max(0, Math.min(255, Math.round(this.diffuseColor[0] * 255.0F))), 
/* 113 */         Math.max(0, Math.min(255, Math.round(this.diffuseColor[1] * 255.0F))), 
/* 114 */         Math.max(0, Math.min(255, Math.round(this.diffuseColor[2] * 255.0F)))
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\objimport\MtlParser$MtlMaterial.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */