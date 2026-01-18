/*    */ package com.hypixel.hytale.server.npc.asset.builder.util;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.regex.Pattern;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class StringListHelpers {
/*    */   @Nonnull
/* 16 */   private static Pattern listSplitter = Pattern.compile("[,; \t]");
/*    */   @Nonnull
/* 18 */   private static Pattern listListSplitter = Pattern.compile("\\|");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static String stringListToString(@Nullable Collection<String> list) {
/* 25 */     if (list == null) return ""; 
/* 26 */     return list.stream().map(String::trim).collect(Collectors.joining(", "));
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static List<String> splitToStringList(String string, @Nullable Function<String, String> mapper) {
/* 31 */     if (mapper == null) mapper = Function.identity(); 
/* 32 */     return (List<String>)listSplitter.splitAsStream(string).filter(s -> !s.isEmpty()).<String>map(mapper).collect(Collectors.toList());
/*    */   }
/*    */   
/*    */   public static void splitToStringList(String string, @Nullable Function<String, String> mapper, @Nonnull Collection<String> result) {
/* 36 */     if (mapper == null) mapper = Function.identity(); 
/* 37 */     Objects.requireNonNull(result); listSplitter.splitAsStream(string).filter(s -> !s.isEmpty()).<String>map(mapper).forEachOrdered(result::add);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static String stringListListToString(@Nonnull Collection<Collection<String>> list) {
/* 42 */     return list.stream().map(StringListHelpers::stringListToString).collect(Collectors.joining("| "));
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static List<List<String>> splitToStringListList(@Nullable String string, Function<String, String> mapper) {
/* 47 */     if (string == null || string.isEmpty()) {
/* 48 */       return Collections.emptyList();
/*    */     }
/* 50 */     return (List<List<String>>)listListSplitter.splitAsStream(string).filter(s -> !s.isEmpty()).map(s -> splitToStringList(s, mapper)).filter(l -> (l != null && !l.isEmpty())).collect(Collectors.toList());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void splitToStringListList(String string, Function<String, String> mapper, @Nonnull Collection<Collection<String>> result, @Nonnull Supplier<Collection<String>> supplier) {
/* 58 */     Objects.requireNonNull(result); listListSplitter.splitAsStream(string).filter(s -> !s.isEmpty()).map(s -> { Collection<String> r = supplier.get(); splitToStringList(s, mapper, r); return r; }).filter(l -> (l != null && !l.isEmpty())).forEachOrdered(result::add);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Set<String> stringListToStringSet(@Nonnull List<String> list) {
/* 63 */     return new HashSet<>(list);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static Set<String> splitToStringSet(@Nullable String input) {
/* 68 */     if (input == null || input.isEmpty()) {
/* 69 */       return Collections.emptySet();
/*    */     }
/* 71 */     List<String> list = splitToStringList(input, null);
/* 72 */     return new HashSet<>(list);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static <T> Set<T> splitToStringSet(@Nullable String input, Function<String, T> transform) {
/* 77 */     if (input == null || input.isEmpty()) {
/* 78 */       return Collections.emptySet();
/*    */     }
/* 80 */     return (Set<T>)splitToStringList(input, null).stream().<T>map(transform).collect(Collectors.toSet());
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static List<Set<String>> stringListListToStringSetList(@Nonnull List<List<String>> group) {
/* 85 */     return (List<Set<String>>)group.stream().map(HashSet::new).collect(Collectors.toList());
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builde\\util\StringListHelpers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */