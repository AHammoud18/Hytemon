/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptionSet
/*     */ {
/*     */   private final List<OptionSpec<?>> detectedSpecs;
/*     */   private final Map<String, AbstractOptionSpec<?>> detectedOptions;
/*     */   private final Map<AbstractOptionSpec<?>, List<String>> optionsToArguments;
/*     */   private final Map<String, AbstractOptionSpec<?>> recognizedSpecs;
/*     */   private final Map<String, List<?>> defaultValues;
/*     */   
/*     */   OptionSet(Map<String, AbstractOptionSpec<?>> recognizedSpecs) {
/*  53 */     this.detectedSpecs = new ArrayList<>();
/*  54 */     this.detectedOptions = new HashMap<>();
/*  55 */     this.optionsToArguments = new IdentityHashMap<>();
/*  56 */     this.defaultValues = defaultValues(recognizedSpecs);
/*  57 */     this.recognizedSpecs = recognizedSpecs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasOptions() {
/*  66 */     return (this.detectedOptions.size() != 1 || !((AbstractOptionSpec)this.detectedOptions.values().iterator().next()).representsNonOptions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean has(String option) {
/*  77 */     return this.detectedOptions.containsKey(option);
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
/*     */   public boolean has(OptionSpec<?> option) {
/*  94 */     return this.optionsToArguments.containsKey(option);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasArgument(String option) {
/* 105 */     AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
/* 106 */     return (spec != null && hasArgument(spec));
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
/*     */   public boolean hasArgument(OptionSpec<?> option) {
/* 124 */     Objects.requireNonNull(option);
/*     */     
/* 126 */     List<String> values = this.optionsToArguments.get(option);
/* 127 */     return (values != null && !values.isEmpty());
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
/*     */   public Object valueOf(String option) {
/* 145 */     Objects.requireNonNull(option);
/*     */     
/* 147 */     AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
/* 148 */     if (spec == null) {
/* 149 */       List<?> defaults = defaultValuesFor(option);
/* 150 */       return defaults.isEmpty() ? null : defaults.get(0);
/*     */     } 
/*     */     
/* 153 */     return valueOf(spec);
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
/*     */   public <V> V valueOf(OptionSpec<V> option) {
/* 170 */     Objects.requireNonNull(option);
/*     */     
/* 172 */     List<V> values = valuesOf(option);
/* 173 */     switch (values.size()) {
/*     */       case 0:
/* 175 */         return null;
/*     */       case 1:
/* 177 */         return values.get(0);
/*     */     } 
/* 179 */     throw new MultipleArgumentsForOptionException(option);
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
/*     */   public List<?> valuesOf(String option) {
/* 193 */     Objects.requireNonNull(option);
/*     */     
/* 195 */     AbstractOptionSpec<?> spec = this.detectedOptions.get(option);
/* 196 */     return (spec == null) ? defaultValuesFor(option) : valuesOf(spec);
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
/*     */   public <V> List<V> valuesOf(OptionSpec<V> option) {
/* 214 */     Objects.requireNonNull(option);
/*     */     
/* 216 */     List<String> values = this.optionsToArguments.get(option);
/* 217 */     if (values == null || values.isEmpty()) {
/* 218 */       return defaultValueFor(option);
/*     */     }
/* 220 */     AbstractOptionSpec<V> spec = (AbstractOptionSpec<V>)option;
/* 221 */     List<V> convertedValues = new ArrayList<>();
/* 222 */     for (String each : values) {
/* 223 */       convertedValues.add(spec.convert(each));
/*     */     }
/* 225 */     return Collections.unmodifiableList(convertedValues);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<OptionSpec<?>> specs() {
/* 235 */     List<OptionSpec<?>> specs = this.detectedSpecs;
/* 236 */     specs.removeAll(Collections.singletonList(this.detectedOptions.get("[arguments]")));
/*     */     
/* 238 */     return Collections.unmodifiableList(specs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<OptionSpec<?>, List<?>> asMap() {
/* 247 */     Map<OptionSpec<?>, List<?>> map = new HashMap<>();
/*     */     
/* 249 */     for (AbstractOptionSpec<?> spec : this.recognizedSpecs.values()) {
/* 250 */       if (!spec.representsNonOptions()) {
/* 251 */         map.put(spec, valuesOf(spec));
/*     */       }
/*     */     } 
/* 254 */     return Collections.unmodifiableMap(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<?> nonOptionArguments() {
/* 261 */     AbstractOptionSpec<?> spec = this.detectedOptions.get("[arguments]");
/* 262 */     return valuesOf(spec);
/*     */   }
/*     */   
/*     */   void add(AbstractOptionSpec<?> spec) {
/* 266 */     addWithArgument(spec, null);
/*     */   }
/*     */   
/*     */   void addWithArgument(AbstractOptionSpec<?> spec, String argument) {
/* 270 */     this.detectedSpecs.add(spec);
/*     */     
/* 272 */     for (String each : spec.options()) {
/* 273 */       this.detectedOptions.put(each, spec);
/*     */     }
/* 275 */     List<String> optionArguments = this.optionsToArguments.get(spec);
/*     */     
/* 277 */     if (optionArguments == null) {
/* 278 */       optionArguments = new ArrayList<>();
/* 279 */       this.optionsToArguments.put(spec, optionArguments);
/*     */     } 
/*     */     
/* 282 */     if (argument != null) {
/* 283 */       optionArguments.add(argument);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean equals(Object that) {
/* 288 */     if (this == that) {
/* 289 */       return true;
/*     */     }
/* 291 */     if (that == null || !getClass().equals(that.getClass())) {
/* 292 */       return false;
/*     */     }
/* 294 */     OptionSet other = (OptionSet)that;
/* 295 */     Map<AbstractOptionSpec<?>, List<String>> thisOptionsToArguments = new HashMap<>(this.optionsToArguments);
/* 296 */     Map<AbstractOptionSpec<?>, List<String>> otherOptionsToArguments = new HashMap<>(other.optionsToArguments);
/* 297 */     return (this.detectedOptions.equals(other.detectedOptions) && thisOptionsToArguments
/* 298 */       .equals(otherOptionsToArguments));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 303 */     Map<AbstractOptionSpec<?>, List<String>> thisOptionsToArguments = new HashMap<>(this.optionsToArguments);
/* 304 */     return this.detectedOptions.hashCode() ^ thisOptionsToArguments.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   private <V> List<V> defaultValuesFor(String option) {
/* 309 */     if (this.defaultValues.containsKey(option)) {
/* 310 */       return Collections.unmodifiableList((List<? extends V>)this.defaultValues.get(option));
/*     */     }
/* 312 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   private <V> List<V> defaultValueFor(OptionSpec<V> option) {
/* 316 */     return defaultValuesFor(option.options().iterator().next());
/*     */   }
/*     */   
/*     */   private static Map<String, List<?>> defaultValues(Map<String, AbstractOptionSpec<?>> recognizedSpecs) {
/* 320 */     Map<String, List<?>> defaults = new HashMap<>();
/* 321 */     for (Map.Entry<String, AbstractOptionSpec<?>> each : recognizedSpecs.entrySet())
/* 322 */       defaults.put(each.getKey(), ((AbstractOptionSpec)each.getValue()).defaultValues()); 
/* 323 */     return defaults;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */