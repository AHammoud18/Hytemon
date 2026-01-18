/*     */ package org.jline.reader.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import org.jline.reader.Candidate;
/*     */ import org.jline.reader.CompletingParsedLine;
/*     */ import org.jline.reader.CompletionMatcher;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.utils.AttributedString;
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
/*     */ public class CompletionMatcherImpl
/*     */   implements CompletionMatcher
/*     */ {
/*     */   protected Predicate<String> exact;
/*     */   protected List<Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>>> matchers;
/*     */   private Map<String, List<Candidate>> matching;
/*     */   private boolean caseInsensitive;
/*     */   
/*     */   protected void reset(boolean caseInsensitive) {
/*  56 */     this.caseInsensitive = caseInsensitive;
/*  57 */     this.exact = (s -> false);
/*  58 */     this.matchers = new ArrayList<>();
/*  59 */     this.matching = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compile(Map<LineReader.Option, Boolean> options, boolean prefix, CompletingParsedLine line, boolean caseInsensitive, int errors, String originalGroupName) {
/*  70 */     reset(caseInsensitive);
/*  71 */     defaultMatchers(options, prefix, line, caseInsensitive, errors, originalGroupName);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Candidate> matches(List<Candidate> candidates) {
/*  76 */     this.matching = Collections.emptyMap();
/*  77 */     Map<String, List<Candidate>> sortedCandidates = sort(candidates);
/*  78 */     for (Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>> matcher : this.matchers) {
/*  79 */       this.matching = matcher.apply(sortedCandidates);
/*  80 */       if (!this.matching.isEmpty()) {
/*     */         break;
/*     */       }
/*     */     } 
/*  84 */     return !this.matching.isEmpty() ? 
/*     */ 
/*     */ 
/*     */       
/*  88 */       (List<Candidate>)this.matching.entrySet().stream().flatMap(e -> ((List)e.getValue()).stream()).distinct().collect(Collectors.toList()) : 
/*  89 */       new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public Candidate exactMatch() {
/*  94 */     if (this.matching == null) {
/*  95 */       throw new IllegalStateException();
/*     */     }
/*  97 */     return this.matching.values().stream()
/*  98 */       .flatMap(Collection::stream)
/*  99 */       .filter(Candidate::complete)
/* 100 */       .filter(c -> this.exact.test(c.value()))
/* 101 */       .findFirst()
/* 102 */       .orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCommonPrefix() {
/* 107 */     if (this.matching == null) {
/* 108 */       throw new IllegalStateException();
/*     */     }
/* 110 */     String commonPrefix = null;
/* 111 */     for (String key : this.matching.keySet()) {
/* 112 */       commonPrefix = (commonPrefix == null) ? key : getCommonStart(commonPrefix, key, this.caseInsensitive);
/*     */     }
/* 114 */     return commonPrefix;
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
/*     */   protected void defaultMatchers(Map<LineReader.Option, Boolean> options, boolean prefix, CompletingParsedLine line, boolean caseInsensitive, int errors, String originalGroupName) {
/* 129 */     String wd = line.word();
/* 130 */     String wdi = caseInsensitive ? wd.toLowerCase() : wd;
/* 131 */     String wp = wdi.substring(0, line.wordCursor());
/* 132 */     if (prefix) {
/* 133 */       this.matchers = new ArrayList<>(Arrays.asList((Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>>[])new Function[] {
/* 134 */               simpleMatcher(s -> (caseInsensitive ? s.toLowerCase() : s).startsWith(wp)), 
/* 135 */               simpleMatcher(s -> (caseInsensitive ? s.toLowerCase() : s).contains(wp)) }));
/* 136 */       if (LineReader.Option.COMPLETE_MATCHER_TYPO.isSet(options)) {
/* 137 */         this.matchers.add(typoMatcher(wp, errors, caseInsensitive, originalGroupName));
/*     */       }
/* 139 */       this.exact = (s -> caseInsensitive ? s.equalsIgnoreCase(wp) : s.equals(wp));
/* 140 */     } else if (!LineReader.Option.EMPTY_WORD_OPTIONS.isSet(options) && wd.length() == 0) {
/* 141 */       this.matchers = new ArrayList<>(Collections.singletonList(simpleMatcher(s -> !s.startsWith("-"))));
/* 142 */       this.exact = (s -> caseInsensitive ? s.equalsIgnoreCase(wd) : s.equals(wd));
/*     */     } else {
/* 144 */       if (LineReader.Option.COMPLETE_IN_WORD.isSet(options)) {
/* 145 */         String ws = wdi.substring(line.wordCursor());
/* 146 */         Pattern p1 = Pattern.compile(Pattern.quote(wp) + ".*" + Pattern.quote(ws) + ".*");
/* 147 */         Pattern p2 = Pattern.compile(".*" + Pattern.quote(wp) + ".*" + Pattern.quote(ws) + ".*");
/* 148 */         this.matchers = new ArrayList<>(Arrays.asList((Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>>[])new Function[] {
/* 149 */                 simpleMatcher(s -> p1.matcher(caseInsensitive ? s.toLowerCase() : s).matches()), 
/*     */                 
/* 151 */                 simpleMatcher(s -> p2.matcher(caseInsensitive ? s.toLowerCase() : s).matches())
/*     */               }));
/*     */       } else {
/* 154 */         this.matchers = new ArrayList<>(Arrays.asList((Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>>[])new Function[] {
/* 155 */                 simpleMatcher(s -> (caseInsensitive ? s.toLowerCase() : s).startsWith(wdi)), 
/* 156 */                 simpleMatcher(s -> (caseInsensitive ? s.toLowerCase() : s).contains(wdi)) }));
/*     */       } 
/* 158 */       if (LineReader.Option.COMPLETE_MATCHER_CAMELCASE.isSet(options)) {
/* 159 */         this.matchers.add(simpleMatcher(s -> camelMatch(wd, 0, s, 0)));
/*     */       }
/* 161 */       if (LineReader.Option.COMPLETE_MATCHER_TYPO.isSet(options)) {
/* 162 */         this.matchers.add(typoMatcher(wdi, errors, caseInsensitive, originalGroupName));
/*     */       }
/* 164 */       this.exact = (s -> caseInsensitive ? s.equalsIgnoreCase(wd) : s.equals(wd));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>> simpleMatcher(Predicate<String> predicate) {
/* 170 */     return m -> (Map)m.entrySet().stream().filter(()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>> typoMatcher(String word, int errors, boolean caseInsensitive, String originalGroupName) {
/* 177 */     return m -> {
/*     */         Map<String, List<Candidate>> map = (Map<String, List<Candidate>>)m.entrySet().stream().filter(()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
/*     */         if (map.size() > 1) {
/*     */           ((List<Candidate>)map.computeIfAbsent(word, ())).add(new Candidate(word, word, originalGroupName, null, null, null, false));
/*     */         }
/*     */         return map;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean camelMatch(String word, int i, String candidate, int j) {
/* 192 */     if (word.length() <= i)
/* 193 */       return true; 
/* 194 */     if (candidate.length() <= j) {
/* 195 */       return false;
/*     */     }
/* 197 */     char c = word.charAt(i);
/* 198 */     if (c == candidate.charAt(j)) {
/* 199 */       return camelMatch(word, i + 1, candidate, j + 1);
/*     */     }
/* 201 */     for (int j1 = j; j1 < candidate.length(); j1++) {
/* 202 */       if (Character.isUpperCase(candidate.charAt(j1)) && 
/* 203 */         Character.toUpperCase(c) == candidate.charAt(j1) && 
/* 204 */         camelMatch(word, i + 1, candidate, j1 + 1)) {
/* 205 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, List<Candidate>> sort(List<Candidate> candidates) {
/* 217 */     Map<String, List<Candidate>> sortedCandidates = new HashMap<>();
/* 218 */     for (Candidate candidate : candidates) {
/* 219 */       ((List<Candidate>)sortedCandidates
/* 220 */         .computeIfAbsent(
/* 221 */           AttributedString.fromAnsi(candidate.value()).toString(), s -> new ArrayList()))
/* 222 */         .add(candidate);
/*     */     }
/* 224 */     return sortedCandidates;
/*     */   }
/*     */   
/*     */   private String getCommonStart(String str1, String str2, boolean caseInsensitive) {
/* 228 */     int[] s1 = str1.codePoints().toArray();
/* 229 */     int[] s2 = str2.codePoints().toArray();
/* 230 */     int len = 0;
/* 231 */     while (len < Math.min(s1.length, s2.length)) {
/* 232 */       int ch1 = s1[len];
/* 233 */       int ch2 = s2[len];
/* 234 */       if (ch1 != ch2 && caseInsensitive) {
/* 235 */         ch1 = Character.toUpperCase(ch1);
/* 236 */         ch2 = Character.toUpperCase(ch2);
/* 237 */         if (ch1 != ch2) {
/* 238 */           ch1 = Character.toLowerCase(ch1);
/* 239 */           ch2 = Character.toLowerCase(ch2);
/*     */         } 
/*     */       } 
/* 242 */       if (ch1 != ch2) {
/*     */         break;
/*     */       }
/* 245 */       len++;
/*     */     } 
/* 247 */     return new String(s1, 0, len);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\reader\impl\CompletionMatcherImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */