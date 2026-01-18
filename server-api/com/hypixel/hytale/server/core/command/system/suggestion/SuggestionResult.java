/*     */ package com.hypixel.hytale.server.core.command.system.suggestion;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.StringCompareUtil;
/*     */ import it.unimi.dsi.fastutil.ints.IntObjectPair;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Function;
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
/*     */ public class SuggestionResult
/*     */ {
/*     */   private static final int FUZZY_SUGGESTION_MAX_RESULTS = 5;
/*     */   @Nonnull
/*  25 */   private static final Comparator<IntObjectPair<String>> INTEGER_STRING_PAIR_COMPARATOR = Comparator.comparingInt(IntObjectPair::leftInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private final List<String> suggestions = (List<String>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SuggestionResult suggest(@Nonnull String suggestion) {
/*  40 */     this.suggestions.add(suggestion);
/*  41 */     return this;
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
/*     */   @Nonnull
/*     */   public <DataType> SuggestionResult suggest(@Nonnull Function<DataType, String> toStringFunction, @Nonnull DataType suggestion) {
/*  54 */     return suggest(toStringFunction.apply(suggestion));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SuggestionResult suggest(@Nonnull Object objectToString) {
/*  65 */     return suggest(objectToString.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public List<String> getSuggestions() {
/*  73 */     return this.suggestions;
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
/*     */   @Nonnull
/*     */   public <DataType> SuggestionResult fuzzySuggest(@Nonnull String input, @Nonnull Collection<DataType> items, @Nonnull Function<DataType, String> toStringFunction) {
/*  89 */     ObjectArrayList<IntObjectPair> objectArrayList = new ObjectArrayList(5);
/*  90 */     int lowestStoredFuzzyValue = Integer.MIN_VALUE;
/*  91 */     for (DataType item : items) {
/*  92 */       String toString = toStringFunction.apply(item);
/*  93 */       int fuzzyValue = StringCompareUtil.getFuzzyDistance(toString, input, Locale.ENGLISH);
/*     */       
/*  95 */       if (objectArrayList.size() == 5) {
/*  96 */         if (fuzzyValue < lowestStoredFuzzyValue) {
/*     */           continue;
/*     */         }
/*  99 */         objectArrayList.set(0, IntObjectPair.of(fuzzyValue, toString));
/*     */       } else {
/* 101 */         objectArrayList.add(IntObjectPair.of(fuzzyValue, toString));
/*     */       } 
/*     */       
/* 104 */       objectArrayList.sort((Comparator)INTEGER_STRING_PAIR_COMPARATOR);
/* 105 */       lowestStoredFuzzyValue = ((IntObjectPair)objectArrayList.getFirst()).leftInt();
/*     */     } 
/*     */     
/* 108 */     for (IntObjectPair<String> integerStringPair : objectArrayList) {
/* 109 */       suggest((String)integerStringPair.right());
/*     */     }
/*     */     
/* 112 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\system\suggestion\SuggestionResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */