/*     */ package com.hypixel.hytale.common.semver;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.function.FunctionCodec;
/*     */ import java.util.Objects;
/*     */ import java.util.StringJoiner;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SemverRange
/*     */   implements SemverSatisfies {
/*  11 */   public static final Codec<SemverRange> CODEC = (Codec<SemverRange>)new FunctionCodec((Codec)Codec.STRING, SemverRange::fromString, SemverRange::toString);
/*     */   
/*  13 */   public static final SemverRange WILDCARD = new SemverRange(new SemverSatisfies[0], true);
/*     */   private final SemverSatisfies[] comparators;
/*     */   private final boolean and;
/*     */   
/*     */   public SemverRange(SemverSatisfies[] comparators, boolean and) {
/*  18 */     this.comparators = comparators;
/*  19 */     this.and = and;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean satisfies(Semver semver) {
/*  24 */     if (this.and) {
/*  25 */       for (SemverSatisfies comparator : this.comparators) {
/*  26 */         if (!comparator.satisfies(semver)) {
/*  27 */           return false;
/*     */         }
/*     */       } 
/*  30 */       return true;
/*     */     } 
/*  32 */     for (SemverSatisfies comparator : this.comparators) {
/*  33 */       if (comparator.satisfies(semver)) {
/*  34 */         return true;
/*     */       }
/*     */     } 
/*  37 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  43 */     StringJoiner joiner = new StringJoiner(" || ");
/*  44 */     for (SemverSatisfies comparator : this.comparators) joiner.add(comparator.toString()); 
/*  45 */     return joiner.toString();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SemverRange fromString(String str) {
/*  50 */     return fromString(str, false);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static SemverRange fromString(String str, boolean strict) {
/*  55 */     Objects.requireNonNull(str, "String can't be null!");
/*  56 */     str = str.trim();
/*  57 */     if (str.isBlank() || "*".equals(str)) return WILDCARD;
/*     */ 
/*     */     
/*  60 */     String[] split = str.split("\\|\\|");
/*  61 */     SemverSatisfies[] comparators = new SemverSatisfies[split.length];
/*  62 */     for (int i = 0; i < split.length; i++) {
/*  63 */       String subRange = split[i].trim();
/*  64 */       if (subRange.contains(" - ")) {
/*  65 */         String[] range = subRange.split(" - ");
/*  66 */         if (range.length != 2) throw new IllegalArgumentException("Range has an invalid number of arguments!"); 
/*  67 */         comparators[i] = new SemverRange(new SemverSatisfies[] { new SemverComparator(SemverComparator.ComparisonType.GTE, 
/*  68 */                 Semver.fromString(range[0], strict)), new SemverComparator(SemverComparator.ComparisonType.LTE, 
/*  69 */                 Semver.fromString(range[1], strict)) }true);
/*     */       }
/*  71 */       else if (subRange.charAt(0) == '~') {
/*  72 */         Semver semver = Semver.fromString(subRange.substring(1), strict);
/*  73 */         if (semver.getMinor() > 0L) {
/*  74 */           comparators[i] = new SemverRange(new SemverSatisfies[] { new SemverComparator(SemverComparator.ComparisonType.GTE, semver), new SemverComparator(SemverComparator.ComparisonType.LT, new Semver(semver
/*     */                     
/*  76 */                     .getMajor(), semver.getMinor() + 1L, 0L, null, null)) }true);
/*     */         } else {
/*     */           
/*  79 */           comparators[i] = new SemverRange(new SemverSatisfies[] { new SemverComparator(SemverComparator.ComparisonType.GTE, semver), new SemverComparator(SemverComparator.ComparisonType.LT, new Semver(semver
/*     */                     
/*  81 */                     .getMajor() + 1L, 0L, 0L, null, null)) }true);
/*     */         }
/*     */       
/*  84 */       } else if (subRange.charAt(0) == '^') {
/*  85 */         Semver semver = Semver.fromString(subRange.substring(1), strict);
/*  86 */         if (semver.getMajor() > 0L) {
/*  87 */           comparators[i] = new SemverRange(new SemverSatisfies[] { new SemverComparator(SemverComparator.ComparisonType.GTE, semver), new SemverComparator(SemverComparator.ComparisonType.LT, new Semver(semver
/*     */                     
/*  89 */                     .getMajor() + 1L, 0L, 0L, null, null)) }true);
/*     */         }
/*  91 */         else if (semver.getMinor() > 0L) {
/*  92 */           comparators[i] = new SemverRange(new SemverSatisfies[] { new SemverComparator(SemverComparator.ComparisonType.GTE, semver), new SemverComparator(SemverComparator.ComparisonType.LT, new Semver(0L, semver
/*     */                     
/*  94 */                     .getMinor() + 1L, 0L, null, null)) }true);
/*     */         } else {
/*     */           
/*  97 */           comparators[i] = new SemverRange(new SemverSatisfies[] { new SemverComparator(SemverComparator.ComparisonType.GTE, semver), new SemverComparator(SemverComparator.ComparisonType.LT, new Semver(0L, 0L, semver
/*     */                     
/*  99 */                     .getPatch() + 1L, null, null)) }true);
/*     */         }
/*     */       
/* 102 */       } else if (SemverComparator.ComparisonType.hasAPrefix(subRange)) {
/* 103 */         comparators[i] = SemverComparator.fromString(subRange);
/* 104 */       } else if (subRange.contains(" ")) {
/* 105 */         String[] comparatorStrings = subRange.split(" ");
/*     */         
/* 107 */         SemverSatisfies[] comparatorsAnd = new SemverSatisfies[comparatorStrings.length];
/* 108 */         for (int y = 0; y < comparatorStrings.length; y++) {
/* 109 */           comparatorsAnd[i] = SemverComparator.fromString(comparatorStrings[i]);
/*     */         }
/* 111 */         comparators[i] = new SemverRange(comparatorsAnd, true);
/*     */       } else {
/* 113 */         Semver semver = Semver.fromString(subRange.replace("x", "0").replace("*", "0"), strict);
/* 114 */         if (semver.getPatch() == 0L && semver.getMinor() == 0L && semver.getMajor() == 0L) {
/* 115 */           comparators[i] = new SemverComparator(SemverComparator.ComparisonType.GTE, new Semver(0L, 0L, 0L));
/* 116 */         } else if (semver.getPatch() == 0L && semver.getMinor() == 0L) {
/* 117 */           comparators[i] = new SemverRange(new SemverSatisfies[] { new SemverComparator(SemverComparator.ComparisonType.GTE, semver), new SemverComparator(SemverComparator.ComparisonType.LT, new Semver(semver
/*     */                     
/* 119 */                     .getMajor() + 1L, 0L, 0L, null, null)) }true);
/*     */         }
/* 121 */         else if (semver.getPatch() == 0L) {
/* 122 */           comparators[i] = new SemverRange(new SemverSatisfies[] { new SemverComparator(SemverComparator.ComparisonType.GTE, semver), new SemverComparator(SemverComparator.ComparisonType.LT, new Semver(semver
/*     */                     
/* 124 */                     .getMajor(), semver.getMinor() + 1L, 0L, null, null)) }true);
/*     */         } else {
/*     */           
/* 127 */           throw new IllegalArgumentException("Invalid X-Range! " + subRange);
/*     */         } 
/*     */       } 
/*     */     } 
/* 131 */     return new SemverRange(comparators, false);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\semver\SemverRange.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */