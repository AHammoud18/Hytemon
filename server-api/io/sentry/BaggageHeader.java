/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Experimental
/*    */ public final class BaggageHeader
/*    */ {
/*    */   @NotNull
/*    */   public static final String BAGGAGE_HEADER = "baggage";
/*    */   @NotNull
/*    */   private final String value;
/*    */   
/*    */   @Nullable
/*    */   public static BaggageHeader fromBaggageAndOutgoingHeader(@NotNull Baggage baggage, @Nullable List<String> outgoingBaggageHeaders) {
/* 18 */     Baggage thirdPartyBaggage = Baggage.fromHeader(outgoingBaggageHeaders, true, baggage.logger);
/* 19 */     String headerValue = baggage.toHeaderString(thirdPartyBaggage.getThirdPartyHeader());
/*    */     
/* 21 */     if (headerValue.isEmpty()) {
/* 22 */       return null;
/*    */     }
/* 24 */     return new BaggageHeader(headerValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public BaggageHeader(@NotNull String value) {
/* 29 */     this.value = value;
/*    */   }
/*    */   @NotNull
/*    */   public String getName() {
/* 33 */     return "baggage";
/*    */   }
/*    */   @NotNull
/*    */   public String getValue() {
/* 37 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\BaggageHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */