/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import io.sentry.util.SentryRandom;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SamplingContext
/*    */ {
/*    */   @NotNull
/*    */   private final TransactionContext transactionContext;
/*    */   @Nullable
/*    */   private final CustomSamplingContext customSamplingContext;
/*    */   @NotNull
/*    */   private final Double sampleRand;
/*    */   @NotNull
/*    */   private final Map<String, Object> attributes;
/*    */   
/*    */   @Deprecated
/*    */   public SamplingContext(@NotNull TransactionContext transactionContext, @Nullable CustomSamplingContext customSamplingContext) {
/* 29 */     this(transactionContext, customSamplingContext, Double.valueOf(SentryRandom.current().nextDouble()), null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Internal
/*    */   public SamplingContext(@NotNull TransactionContext transactionContext, @Nullable CustomSamplingContext customSamplingContext, @NotNull Double sampleRand, @Nullable Map<String, Object> attributes) {
/* 38 */     this
/* 39 */       .transactionContext = (TransactionContext)Objects.requireNonNull(transactionContext, "transactionContexts is required");
/* 40 */     this.customSamplingContext = customSamplingContext;
/* 41 */     this.sampleRand = sampleRand;
/* 42 */     this.attributes = (attributes == null) ? Collections.<String, Object>emptyMap() : attributes;
/*    */   }
/*    */   @Nullable
/*    */   public CustomSamplingContext getCustomSamplingContext() {
/* 46 */     return this.customSamplingContext;
/*    */   }
/*    */   @NotNull
/*    */   public TransactionContext getTransactionContext() {
/* 50 */     return this.transactionContext;
/*    */   }
/*    */   @NotNull
/*    */   public Double getSampleRand() {
/* 54 */     return this.sampleRand;
/*    */   }
/*    */   @Nullable
/*    */   public Object getAttribute(@Nullable String key) {
/* 58 */     if (key == null) {
/* 59 */       return null;
/*    */     }
/* 61 */     return this.attributes.get(key);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SamplingContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */