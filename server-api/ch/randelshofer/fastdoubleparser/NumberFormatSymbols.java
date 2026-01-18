/*     */ package ch.randelshofer.fastdoubleparser;
/*     */ 
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ public final class NumberFormatSymbols
/*     */ {
/*     */   private final Set<Character> decimalSeparator;
/*     */   private final Set<Character> groupingSeparator;
/*     */   private final Set<String> exponentSeparator;
/*     */   private final Set<Character> minusSign;
/*     */   private final Set<Character> plusSign;
/*     */   private final Set<String> infinity;
/*     */   private final Set<String> nan;
/*     */   private final List<Character> digits;
/*     */   
/*     */   public NumberFormatSymbols(Set<Character> decimalSeparator, Set<Character> groupingSeparator, Set<String> exponentSeparator, Set<Character> minusSign, Set<Character> plusSign, Set<String> infinity, Set<String> nan, List<Character> digits) {
/*  47 */     if (((List)Objects.<List>requireNonNull(digits, "digits")).size() != 10)
/*  48 */       throw new IllegalArgumentException("digits list must have size 10"); 
/*  49 */     this.decimalSeparator = new LinkedHashSet<>(Objects.<Collection<? extends Character>>requireNonNull(decimalSeparator, "decimalSeparator"));
/*  50 */     this.groupingSeparator = new LinkedHashSet<>(Objects.<Collection<? extends Character>>requireNonNull(groupingSeparator, "groupingSeparator"));
/*  51 */     this.exponentSeparator = new LinkedHashSet<>(Objects.<Collection<? extends String>>requireNonNull(exponentSeparator, "exponentSeparator"));
/*  52 */     this.minusSign = new LinkedHashSet<>(Objects.<Collection<? extends Character>>requireNonNull(minusSign, "minusSign"));
/*  53 */     this.plusSign = new LinkedHashSet<>(Objects.<Collection<? extends Character>>requireNonNull(plusSign, "plusSign"));
/*  54 */     this.infinity = new LinkedHashSet<>(Objects.<Collection<? extends String>>requireNonNull(infinity, "infinity"));
/*  55 */     this.nan = new LinkedHashSet<>(Objects.<Collection<? extends String>>requireNonNull(nan, "nan"));
/*  56 */     this.digits = new ArrayList<>(digits);
/*     */   }
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
/*     */   public NumberFormatSymbols(String decimalSeparators, String groupingSeparators, Collection<String> exponentSeparators, String minusSigns, String plusSigns, Collection<String> infinity, Collection<String> nan, String digits) {
/*  74 */     this(toSet(decimalSeparators), 
/*  75 */         toSet(groupingSeparators), new LinkedHashSet<>(exponentSeparators), 
/*     */         
/*  77 */         toSet(minusSigns), 
/*  78 */         toSet(plusSigns), new LinkedHashSet<>(infinity), new LinkedHashSet<>(nan), 
/*     */ 
/*     */         
/*  81 */         toList(expandDigits(digits)));
/*     */   }
/*     */   
/*     */   private static String expandDigits(String digits) {
/*  85 */     if (digits.length() == 10) return digits; 
/*  86 */     if (digits.length() != 1)
/*  87 */       throw new IllegalArgumentException("digits must have length 1 or 10, digits=\"" + digits + "\""); 
/*  88 */     StringBuilder buf = new StringBuilder(10);
/*  89 */     char zeroChar = digits.charAt(0);
/*  90 */     for (int i = 0; i < 10; i++) {
/*  91 */       buf.append((char)(zeroChar + i));
/*     */     }
/*  93 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NumberFormatSymbols fromDecimalFormatSymbols(DecimalFormatSymbols symbols) {
/* 103 */     List<Character> digits = new ArrayList<>(10);
/* 104 */     char zeroDigit = symbols.getZeroDigit();
/* 105 */     for (int i = 0; i < 10; i++) {
/* 106 */       digits.add(Character.valueOf((char)(zeroDigit + i)));
/*     */     }
/* 108 */     return new NumberFormatSymbols(
/* 109 */         Collections.singleton(Character.valueOf(symbols.getDecimalSeparator())), 
/* 110 */         Collections.singleton(Character.valueOf(symbols.getGroupingSeparator())), 
/* 111 */         Collections.singleton(symbols.getExponentSeparator()), 
/* 112 */         Collections.singleton(Character.valueOf(symbols.getMinusSign())), 
/* 113 */         Collections.emptySet(), 
/* 114 */         Collections.singleton(symbols.getInfinity()), 
/* 115 */         Collections.singleton(symbols.getNaN()), digits);
/*     */   }
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
/*     */   public static NumberFormatSymbols fromDefault() {
/* 136 */     return new NumberFormatSymbols(
/* 137 */         Collections.singleton(Character.valueOf('.')), 
/* 138 */         Collections.emptySet(), new HashSet<>(
/* 139 */           Arrays.asList(new String[] { "e", "E"
/* 140 */             }, )), Collections.singleton(Character.valueOf('-')), 
/* 141 */         Collections.singleton(Character.valueOf('+')), 
/* 142 */         Collections.singleton("Infinity"), 
/* 143 */         Collections.singleton("NaN"), 
/* 144 */         Arrays.asList(new Character[] { Character.valueOf('0'), Character.valueOf('1'), Character.valueOf('2'), Character.valueOf('3'), Character.valueOf('4'), Character.valueOf('5'), Character.valueOf('6'), Character.valueOf('7'), Character.valueOf('8'), Character.valueOf('9') }));
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Character> toList(String chars) {
/* 149 */     List<Character> set = new ArrayList<>(10);
/* 150 */     for (char ch : chars.toCharArray()) {
/* 151 */       set.add(Character.valueOf(ch));
/*     */     }
/* 153 */     return set;
/*     */   }
/*     */   
/*     */   private static Set<Character> toSet(String chars) {
/* 157 */     Set<Character> set = new LinkedHashSet<>(chars.length() * 2);
/* 158 */     for (char ch : chars.toCharArray()) {
/* 159 */       set.add(Character.valueOf(ch));
/*     */     }
/* 161 */     return set;
/*     */   }
/*     */   
/*     */   public Set<Character> decimalSeparator() {
/* 165 */     return this.decimalSeparator;
/*     */   }
/*     */   
/*     */   public List<Character> digits() {
/* 169 */     return this.digits;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 174 */     if (obj == this) return true; 
/* 175 */     if (obj == null || obj.getClass() != getClass()) return false; 
/* 176 */     NumberFormatSymbols that = (NumberFormatSymbols)obj;
/* 177 */     return (Objects.equals(this.decimalSeparator, that.decimalSeparator) && 
/* 178 */       Objects.equals(this.groupingSeparator, that.groupingSeparator) && 
/* 179 */       Objects.equals(this.exponentSeparator, that.exponentSeparator) && 
/* 180 */       Objects.equals(this.minusSign, that.minusSign) && 
/* 181 */       Objects.equals(this.plusSign, that.plusSign) && 
/* 182 */       Objects.equals(this.infinity, that.infinity) && 
/* 183 */       Objects.equals(this.nan, that.nan) && 
/* 184 */       Objects.equals(this.digits, that.digits));
/*     */   }
/*     */   
/*     */   public Set<String> exponentSeparator() {
/* 188 */     return this.exponentSeparator;
/*     */   }
/*     */   
/*     */   public Set<Character> groupingSeparator() {
/* 192 */     return this.groupingSeparator;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     return Objects.hash(new Object[] { this.decimalSeparator, this.groupingSeparator, this.exponentSeparator, this.minusSign, this.plusSign, this.infinity, this.nan, this.digits });
/*     */   }
/*     */   
/*     */   public Set<String> infinity() {
/* 201 */     return this.infinity;
/*     */   }
/*     */   
/*     */   public Set<Character> minusSign() {
/* 205 */     return this.minusSign;
/*     */   }
/*     */   
/*     */   public Set<String> nan() {
/* 209 */     return this.nan;
/*     */   }
/*     */   
/*     */   public Set<Character> plusSign() {
/* 213 */     return this.plusSign;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 218 */     return "NumberFormatSymbols[decimalSeparator=" + this.decimalSeparator + ", groupingSeparator=" + this.groupingSeparator + ", exponentSeparator=" + this.exponentSeparator + ", minusSign=" + this.minusSign + ", plusSign=" + this.plusSign + ", infinity=" + this.infinity + ", nan=" + this.nan + ", digits=" + this.digits + ']';
/*     */   }
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
/*     */   public NumberFormatSymbols withDecimalSeparator(Set<Character> newValue) {
/* 236 */     return new NumberFormatSymbols(newValue, this.groupingSeparator, this.exponentSeparator, this.minusSign, this.plusSign, this.infinity, this.nan, this.digits);
/*     */   }
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
/*     */   public NumberFormatSymbols withDigits(List<Character> newValue) {
/* 253 */     return new NumberFormatSymbols(this.decimalSeparator, this.groupingSeparator, this.exponentSeparator, this.minusSign, this.plusSign, this.infinity, this.nan, newValue);
/*     */   }
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
/*     */   public NumberFormatSymbols withExponentSeparator(Set<String> newValue) {
/* 270 */     return new NumberFormatSymbols(this.decimalSeparator, this.groupingSeparator, newValue, this.minusSign, this.plusSign, this.infinity, this.nan, this.digits);
/*     */   }
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
/*     */   public NumberFormatSymbols withGroupingSeparator(Set<Character> newValue) {
/* 287 */     return new NumberFormatSymbols(this.decimalSeparator, newValue, this.exponentSeparator, this.minusSign, this.plusSign, this.infinity, this.nan, this.digits);
/*     */   }
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
/*     */   public NumberFormatSymbols withInfinity(Set<String> newValue) {
/* 304 */     return new NumberFormatSymbols(this.decimalSeparator, this.groupingSeparator, this.exponentSeparator, this.minusSign, this.plusSign, newValue, this.nan, this.digits);
/*     */   }
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
/*     */   public NumberFormatSymbols withMinusSign(Set<Character> newValue) {
/* 321 */     return new NumberFormatSymbols(this.decimalSeparator, this.groupingSeparator, this.exponentSeparator, newValue, this.plusSign, this.infinity, this.nan, this.digits);
/*     */   }
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
/*     */   public NumberFormatSymbols withNaN(Set<String> newValue) {
/* 338 */     return new NumberFormatSymbols(this.decimalSeparator, this.groupingSeparator, this.exponentSeparator, this.minusSign, this.plusSign, this.infinity, newValue, this.digits);
/*     */   }
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
/*     */   public NumberFormatSymbols withPlusSign(Set<Character> newValue) {
/* 355 */     return new NumberFormatSymbols(this.decimalSeparator, this.groupingSeparator, this.exponentSeparator, this.minusSign, newValue, this.infinity, this.nan, this.digits);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\ch\randelshofer\fastdoubleparser\NumberFormatSymbols.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */