/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OpenSslCertificateCompressionConfig
/*     */   implements Iterable<OpenSslCertificateCompressionConfig.AlgorithmConfig>
/*     */ {
/*     */   private final List<AlgorithmConfig> pairList;
/*     */   
/*     */   private OpenSslCertificateCompressionConfig(AlgorithmConfig... pairs) {
/*  34 */     this.pairList = Collections.unmodifiableList(Arrays.asList(pairs));
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<AlgorithmConfig> iterator() {
/*  39 */     return this.pairList.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder newBuilder() {
/*  48 */     return new Builder();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*  55 */     private final List<OpenSslCertificateCompressionConfig.AlgorithmConfig> algorithmList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder addAlgorithm(OpenSslCertificateCompressionAlgorithm algorithm, OpenSslCertificateCompressionConfig.AlgorithmMode mode) {
/*  72 */       this.algorithmList.add(new OpenSslCertificateCompressionConfig.AlgorithmConfig(algorithm, mode));
/*  73 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OpenSslCertificateCompressionConfig build() {
/*  83 */       return new OpenSslCertificateCompressionConfig(this.algorithmList.<OpenSslCertificateCompressionConfig.AlgorithmConfig>toArray(new OpenSslCertificateCompressionConfig.AlgorithmConfig[0]));
/*     */     }
/*     */     
/*     */     private Builder() {}
/*     */   }
/*     */   
/*     */   public static final class AlgorithmConfig
/*     */   {
/*     */     private final OpenSslCertificateCompressionAlgorithm algorithm;
/*     */     private final OpenSslCertificateCompressionConfig.AlgorithmMode mode;
/*     */     
/*     */     private AlgorithmConfig(OpenSslCertificateCompressionAlgorithm algorithm, OpenSslCertificateCompressionConfig.AlgorithmMode mode) {
/*  95 */       this.algorithm = (OpenSslCertificateCompressionAlgorithm)ObjectUtil.checkNotNull(algorithm, "algorithm");
/*  96 */       this.mode = (OpenSslCertificateCompressionConfig.AlgorithmMode)ObjectUtil.checkNotNull(mode, "mode");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OpenSslCertificateCompressionConfig.AlgorithmMode mode() {
/* 105 */       return this.mode;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OpenSslCertificateCompressionAlgorithm algorithm() {
/* 114 */       return this.algorithm;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum AlgorithmMode
/*     */   {
/* 125 */     Compress,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     Decompress,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     Both;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslCertificateCompressionConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */