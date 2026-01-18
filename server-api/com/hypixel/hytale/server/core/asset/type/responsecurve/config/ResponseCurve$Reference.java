/*     */ package com.hypixel.hytale.server.core.asset.type.responsecurve.config;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Reference
/*     */ {
/*     */   private int index;
/*     */   private WeakReference<ResponseCurve> reference;
/*     */   
/*     */   public Reference(int index, @Nonnull ResponseCurve responseCurve) {
/*  90 */     this.index = index;
/*  91 */     this.reference = responseCurve.getReference();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ResponseCurve get() {
/*  96 */     ResponseCurve responseCurve = this.reference.get();
/*  97 */     if (responseCurve == null) {
/*  98 */       responseCurve = (ResponseCurve)ResponseCurve.getAssetMap().getAsset(this.index);
/*  99 */       this.reference = responseCurve.getReference();
/*     */     } 
/* 101 */     return responseCurve;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\responsecurve\config\ResponseCurve$Reference.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */