/*     */ package com.hypixel.hytale.codec.validation;
/*     */ import com.hypixel.hytale.codec.validation.validator.ArrayValidator;
/*     */ import com.hypixel.hytale.codec.validation.validator.DeprecatedValidator;
/*     */ import com.hypixel.hytale.codec.validation.validator.RangeValidator;
/*     */ import com.hypixel.hytale.codec.validation.validator.SequentialDoubleArrayValidator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class Validators {
/*     */   @Nonnull
/*     */   public static <T> DeprecatedValidator<T> deprecated() {
/*  13 */     return DeprecatedValidator.INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Validator<T> nonNull() {
/*  18 */     return (Validator<T>)NonNullValidator.INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> ArrayValidator<T> nonNullArrayElements() {
/*  23 */     return new ArrayValidator(nonNull());
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Validator<String> nonEmptyString() {
/*  28 */     return (Validator<String>)NonEmptyStringValidator.INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Validator<T[]> nonEmptyArray() {
/*  33 */     return (Validator<T[]>)NonEmptyArrayValidator.INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <K, V> Validator<Map<K, V>> nonEmptyMap() {
/*  38 */     return (Validator<Map<K, V>>)NonEmptyMapValidator.INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Validator<T[]> uniqueInArray() {
/*  43 */     return (Validator<T[]>)UniqueInArrayValidator.INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Validator<Map<T, ?>> requiredMapKeysValidator(T[] array) {
/*  48 */     return (Validator<Map<T, ?>>)new RequiredMapKeysValidator((Object[])array);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> greaterThan(T greaterThan) {
/*  53 */     return (Validator<T>)new RangeValidator((Comparable)greaterThan, null, false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> greaterThanOrEqual(T greaterThan) {
/*  58 */     return (Validator<T>)new RangeValidator((Comparable)greaterThan, null, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> lessThan(T lessThan) {
/*  63 */     return (Validator<T>)new RangeValidator(null, (Comparable)lessThan, false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> insideRange(T greaterthan, T lessThan) {
/*  68 */     return (Validator<T>)new RangeValidator((Comparable)greaterthan, (Comparable)lessThan, false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> min(T min) {
/*  73 */     return (Validator<T>)new RangeValidator((Comparable)min, null, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> max(T max) {
/*  78 */     return (Validator<T>)new RangeValidator(null, (Comparable)max, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> range(T min, T max) {
/*  83 */     return (Validator<T>)new RangeValidator((Comparable)min, (Comparable)max, true);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Validator<T[]> arraySizeRange(int min, int max) {
/*  88 */     return (Validator<T[]>)new ArraySizeRangeValidator(min, max);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Validator<T[]> arraySize(int size) {
/*  93 */     return (Validator<T[]>)new ArraySizeValidator(size);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Validator<int[]> intArraySize(int size) {
/*  98 */     return (Validator<int[]>)new IntArraySizeValidator(size);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Validator<double[]> doubleArraySize(int size) {
/* 103 */     return (Validator<double[]>)new DoubleArraySizeValidator(size);
/*     */   }
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> equal(@Nonnull T value) {
/* 107 */     return (Validator<T>)new EqualValidator((Comparable)value);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T extends Comparable<T>> Validator<T> notEqual(@Nonnull T value) {
/* 112 */     return (Validator<T>)new NotEqualValidator((Comparable)value);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Validator<double[]> nonEmptyDoubleArray() {
/* 117 */     return (Validator<double[]>)NonEmptyDoubleArrayValidator.INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Validator<float[]> nonEmptyFloatArray() {
/* 122 */     return (Validator<float[]>)NonEmptyFloatArrayValidator.INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Validator<double[]> monotonicSequentialDoubleArrayValidator() {
/* 127 */     return (Validator<double[]>)SequentialDoubleArrayValidator.NEQ_INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Validator<double[]> weaklyMonotonicSequentialDoubleArrayValidator() {
/* 132 */     return (Validator<double[]>)SequentialDoubleArrayValidator.ALLOW_EQ_INSTANCE;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Validator<T> or(Validator<T>... validators) {
/* 137 */     return (Validator<T>)new OrValidator((Validator[])validators);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Validator<List<T>> listItem(Validator<T> validator) {
/* 142 */     return (Validator<List<T>>)new ListValidator(validator);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\codec\validation\Validators.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */