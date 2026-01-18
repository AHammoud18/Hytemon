/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class SentryAttribute
/*    */ {
/*    */   @NotNull
/*    */   private final String name;
/*    */   @Nullable
/*    */   private final SentryAttributeType type;
/*    */   @Nullable
/*    */   private final Object value;
/*    */   
/*    */   private SentryAttribute(@NotNull String name, @Nullable SentryAttributeType type, @Nullable Object value) {
/* 16 */     this.name = name;
/* 17 */     this.type = type;
/* 18 */     this.value = value;
/*    */   }
/*    */   @NotNull
/*    */   public String getName() {
/* 22 */     return this.name;
/*    */   }
/*    */   @Nullable
/*    */   public SentryAttributeType getType() {
/* 26 */     return this.type;
/*    */   }
/*    */   @Nullable
/*    */   public Object getValue() {
/* 30 */     return this.value;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public static SentryAttribute named(@NotNull String name, @Nullable Object value) {
/* 35 */     return new SentryAttribute(name, null, value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public static SentryAttribute booleanAttribute(@NotNull String name, @Nullable Boolean value) {
/* 40 */     return new SentryAttribute(name, SentryAttributeType.BOOLEAN, value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public static SentryAttribute integerAttribute(@NotNull String name, @Nullable Integer value) {
/* 45 */     return new SentryAttribute(name, SentryAttributeType.INTEGER, value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public static SentryAttribute doubleAttribute(@NotNull String name, @Nullable Double value) {
/* 50 */     return new SentryAttribute(name, SentryAttributeType.DOUBLE, value);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public static SentryAttribute stringAttribute(@NotNull String name, @Nullable String value) {
/* 55 */     return new SentryAttribute(name, SentryAttributeType.STRING, value);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SentryAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */