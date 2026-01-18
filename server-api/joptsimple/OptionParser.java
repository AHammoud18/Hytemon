/*     */ package joptsimple;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import joptsimple.internal.AbbreviationMap;
/*     */ import joptsimple.internal.OptionNameMap;
/*     */ import joptsimple.internal.SimpleOptionNameMap;
/*     */ import joptsimple.util.KeyValuePair;
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
/*     */ 
/*     */ public class OptionParser
/*     */   implements OptionDeclarer
/*     */ {
/*     */   private final OptionNameMap<AbstractOptionSpec<?>> recognizedOptions;
/*     */   private final ArrayList<AbstractOptionSpec<?>> trainingOrder;
/*     */   private final Map<List<String>, Set<OptionSpec<?>>> requiredIf;
/*     */   private final Map<List<String>, Set<OptionSpec<?>>> requiredUnless;
/*     */   private final Map<List<String>, Set<OptionSpec<?>>> availableIf;
/*     */   private final Map<List<String>, Set<OptionSpec<?>>> availableUnless;
/*     */   private OptionParserState state;
/*     */   private boolean posixlyCorrect;
/*     */   private boolean allowsUnrecognizedOptions;
/* 203 */   private HelpFormatter helpFormatter = new BuiltinHelpFormatter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionParser() {
/* 210 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionParser(boolean allowAbbreviations) {
/* 221 */     this.trainingOrder = new ArrayList<>();
/* 222 */     this.requiredIf = new HashMap<>();
/* 223 */     this.requiredUnless = new HashMap<>();
/* 224 */     this.availableIf = new HashMap<>();
/* 225 */     this.availableUnless = new HashMap<>();
/* 226 */     this.state = OptionParserState.moreOptions(false);
/*     */     
/* 228 */     this.recognizedOptions = allowAbbreviations ? (OptionNameMap<AbstractOptionSpec<?>>)new AbbreviationMap() : (OptionNameMap<AbstractOptionSpec<?>>)new SimpleOptionNameMap();
/*     */ 
/*     */ 
/*     */     
/* 232 */     recognize(new NonOptionArgumentSpec());
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
/*     */   public OptionParser(String optionSpecification) {
/* 246 */     this();
/*     */     
/* 248 */     (new OptionSpecTokenizer(optionSpecification)).configure(this);
/*     */   }
/*     */   
/*     */   public OptionSpecBuilder accepts(String option) {
/* 252 */     return acceptsAll(Collections.singletonList(option));
/*     */   }
/*     */   
/*     */   public OptionSpecBuilder accepts(String option, String description) {
/* 256 */     return acceptsAll(Collections.singletonList(option), description);
/*     */   }
/*     */   
/*     */   public OptionSpecBuilder acceptsAll(List<String> options) {
/* 260 */     return acceptsAll(options, "");
/*     */   }
/*     */   
/*     */   public OptionSpecBuilder acceptsAll(List<String> options, String description) {
/* 264 */     if (options.isEmpty()) {
/* 265 */       throw new IllegalArgumentException("need at least one option");
/*     */     }
/* 267 */     ParserRules.ensureLegalOptions(options);
/*     */     
/* 269 */     return new OptionSpecBuilder(this, options, description);
/*     */   }
/*     */   
/*     */   public NonOptionArgumentSpec<String> nonOptions() {
/* 273 */     NonOptionArgumentSpec<String> spec = new NonOptionArgumentSpec<>();
/*     */     
/* 275 */     recognize(spec);
/*     */     
/* 277 */     return spec;
/*     */   }
/*     */   
/*     */   public NonOptionArgumentSpec<String> nonOptions(String description) {
/* 281 */     NonOptionArgumentSpec<String> spec = new NonOptionArgumentSpec<>(description);
/*     */     
/* 283 */     recognize(spec);
/*     */     
/* 285 */     return spec;
/*     */   }
/*     */   
/*     */   public void posixlyCorrect(boolean setting) {
/* 289 */     this.posixlyCorrect = setting;
/* 290 */     this.state = OptionParserState.moreOptions(setting);
/*     */   }
/*     */   
/*     */   boolean posixlyCorrect() {
/* 294 */     return this.posixlyCorrect;
/*     */   }
/*     */   
/*     */   public void allowsUnrecognizedOptions() {
/* 298 */     this.allowsUnrecognizedOptions = true;
/*     */   }
/*     */   
/*     */   boolean doesAllowsUnrecognizedOptions() {
/* 302 */     return this.allowsUnrecognizedOptions;
/*     */   }
/*     */   
/*     */   public void recognizeAlternativeLongOptions(boolean recognize) {
/* 306 */     if (recognize) {
/* 307 */       recognize(new AlternativeLongOptionSpec());
/*     */     } else {
/* 309 */       this.recognizedOptions.remove(String.valueOf("W"));
/*     */     } 
/*     */   }
/*     */   void recognize(AbstractOptionSpec<?> spec) {
/* 313 */     this.recognizedOptions.putAll(spec.options(), spec);
/* 314 */     this.trainingOrder.add(spec);
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
/*     */   public void printHelpOn(OutputStream sink) throws IOException {
/* 328 */     printHelpOn(new OutputStreamWriter(sink));
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
/*     */   public void printHelpOn(Writer sink) throws IOException {
/* 342 */     sink.write(this.helpFormatter.format((Map)_recognizedOptions()));
/* 343 */     sink.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void formatHelpWith(HelpFormatter formatter) {
/* 353 */     if (formatter == null) {
/* 354 */       throw new NullPointerException();
/*     */     }
/* 356 */     this.helpFormatter = formatter;
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
/*     */   public Map<String, OptionSpec<?>> recognizedOptions() {
/* 370 */     return new LinkedHashMap<>((Map)_recognizedOptions());
/*     */   }
/*     */   
/*     */   private Map<String, AbstractOptionSpec<?>> _recognizedOptions() {
/* 374 */     Map<String, AbstractOptionSpec<?>> options = new LinkedHashMap<>();
/* 375 */     for (AbstractOptionSpec<?> spec : this.trainingOrder) {
/* 376 */       for (String option : spec.options())
/* 377 */         options.put(option, spec); 
/*     */     } 
/* 379 */     return options;
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
/*     */   public OptionSet parse(String... arguments) {
/* 391 */     ArgumentList argumentList = new ArgumentList(arguments);
/* 392 */     OptionSet detected = new OptionSet(this.recognizedOptions.toJavaUtilMap());
/* 393 */     detected.add((AbstractOptionSpec)this.recognizedOptions.get("[arguments]"));
/*     */     
/* 395 */     while (argumentList.hasMore()) {
/* 396 */       this.state.handleArgument(this, argumentList, detected);
/*     */     }
/* 398 */     reset();
/*     */     
/* 400 */     ensureRequiredOptions(detected);
/* 401 */     ensureAllowedOptions(detected);
/*     */     
/* 403 */     return detected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mutuallyExclusive(OptionSpecBuilder... specs) {
/* 413 */     for (int i = 0; i < specs.length; i++) {
/* 414 */       for (int j = 0; j < specs.length; j++) {
/* 415 */         if (i != j)
/* 416 */           specs[i].availableUnless(specs[j], (OptionSpec<?>[])new OptionSpec[0]); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void ensureRequiredOptions(OptionSet options) {
/* 422 */     List<AbstractOptionSpec<?>> missingRequiredOptions = missingRequiredOptions(options);
/* 423 */     boolean helpOptionPresent = isHelpOptionPresent(options);
/*     */     
/* 425 */     if (!missingRequiredOptions.isEmpty() && !helpOptionPresent)
/* 426 */       throw new MissingRequiredOptionsException(missingRequiredOptions); 
/*     */   }
/*     */   
/*     */   private void ensureAllowedOptions(OptionSet options) {
/* 430 */     List<AbstractOptionSpec<?>> forbiddenOptions = unavailableOptions(options);
/* 431 */     boolean helpOptionPresent = isHelpOptionPresent(options);
/*     */     
/* 433 */     if (!forbiddenOptions.isEmpty() && !helpOptionPresent)
/* 434 */       throw new UnavailableOptionException(forbiddenOptions); 
/*     */   }
/*     */   
/*     */   private List<AbstractOptionSpec<?>> missingRequiredOptions(OptionSet options) {
/* 438 */     List<AbstractOptionSpec<?>> missingRequiredOptions = new ArrayList<>();
/*     */     
/* 440 */     for (AbstractOptionSpec<?> each : (Iterable<AbstractOptionSpec<?>>)this.recognizedOptions.toJavaUtilMap().values()) {
/* 441 */       if (each.isRequired() && !options.has(each)) {
/* 442 */         missingRequiredOptions.add(each);
/*     */       }
/*     */     } 
/* 445 */     for (Map.Entry<List<String>, Set<OptionSpec<?>>> each : this.requiredIf.entrySet()) {
/* 446 */       AbstractOptionSpec<?> required = specFor(((List<String>)each.getKey()).iterator().next());
/*     */       
/* 448 */       if (optionsHasAnyOf(options, each.getValue()) && !options.has(required)) {
/* 449 */         missingRequiredOptions.add(required);
/*     */       }
/*     */     } 
/* 452 */     for (Map.Entry<List<String>, Set<OptionSpec<?>>> each : this.requiredUnless.entrySet()) {
/* 453 */       AbstractOptionSpec<?> required = specFor(((List<String>)each.getKey()).iterator().next());
/*     */       
/* 455 */       if (!optionsHasAnyOf(options, each.getValue()) && !options.has(required)) {
/* 456 */         missingRequiredOptions.add(required);
/*     */       }
/*     */     } 
/* 459 */     return missingRequiredOptions;
/*     */   }
/*     */   
/*     */   private List<AbstractOptionSpec<?>> unavailableOptions(OptionSet options) {
/* 463 */     List<AbstractOptionSpec<?>> unavailableOptions = new ArrayList<>();
/*     */     
/* 465 */     for (Map.Entry<List<String>, Set<OptionSpec<?>>> eachEntry : this.availableIf.entrySet()) {
/* 466 */       AbstractOptionSpec<?> forbidden = specFor(((List<String>)eachEntry.getKey()).iterator().next());
/*     */       
/* 468 */       if (!optionsHasAnyOf(options, eachEntry.getValue()) && options.has(forbidden)) {
/* 469 */         unavailableOptions.add(forbidden);
/*     */       }
/*     */     } 
/*     */     
/* 473 */     for (Map.Entry<List<String>, Set<OptionSpec<?>>> eachEntry : this.availableUnless.entrySet()) {
/* 474 */       AbstractOptionSpec<?> forbidden = specFor(((List<String>)eachEntry.getKey()).iterator().next());
/*     */       
/* 476 */       if (optionsHasAnyOf(options, eachEntry.getValue()) && options.has(forbidden)) {
/* 477 */         unavailableOptions.add(forbidden);
/*     */       }
/*     */     } 
/*     */     
/* 481 */     return unavailableOptions;
/*     */   }
/*     */   
/*     */   private boolean optionsHasAnyOf(OptionSet options, Collection<OptionSpec<?>> specs) {
/* 485 */     for (OptionSpec<?> each : specs) {
/* 486 */       if (options.has(each)) {
/* 487 */         return true;
/*     */       }
/*     */     } 
/* 490 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isHelpOptionPresent(OptionSet options) {
/* 494 */     boolean helpOptionPresent = false;
/*     */     
/* 496 */     for (AbstractOptionSpec<?> each : (Iterable<AbstractOptionSpec<?>>)this.recognizedOptions.toJavaUtilMap().values()) {
/* 497 */       if (each.isForHelp() && options.has(each)) {
/* 498 */         helpOptionPresent = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 503 */     return helpOptionPresent;
/*     */   }
/*     */   
/*     */   void handleLongOptionToken(String candidate, ArgumentList arguments, OptionSet detected) {
/* 507 */     KeyValuePair optionAndArgument = parseLongOptionWithArgument(candidate);
/*     */     
/* 509 */     if (!isRecognized(optionAndArgument.key)) {
/* 510 */       throw OptionException.unrecognizedOption(optionAndArgument.key);
/*     */     }
/* 512 */     AbstractOptionSpec<?> optionSpec = specFor(optionAndArgument.key);
/* 513 */     optionSpec.handleOption(this, arguments, detected, optionAndArgument.value);
/*     */   }
/*     */   
/*     */   void handleShortOptionToken(String candidate, ArgumentList arguments, OptionSet detected) {
/* 517 */     KeyValuePair optionAndArgument = parseShortOptionWithArgument(candidate);
/*     */     
/* 519 */     if (isRecognized(optionAndArgument.key)) {
/* 520 */       specFor(optionAndArgument.key).handleOption(this, arguments, detected, optionAndArgument.value);
/*     */     } else {
/*     */       
/* 523 */       handleShortOptionCluster(candidate, arguments, detected);
/*     */     } 
/*     */   }
/*     */   private void handleShortOptionCluster(String candidate, ArgumentList arguments, OptionSet detected) {
/* 527 */     char[] options = extractShortOptionsFrom(candidate);
/* 528 */     validateOptionCharacters(options);
/*     */     
/* 530 */     for (int i = 0; i < options.length; i++) {
/* 531 */       AbstractOptionSpec<?> optionSpec = specFor(options[i]);
/*     */       
/* 533 */       if (optionSpec.acceptsArguments() && options.length > i + 1) {
/* 534 */         String detectedArgument = String.valueOf(options, i + 1, options.length - 1 - i);
/* 535 */         optionSpec.handleOption(this, arguments, detected, detectedArgument);
/*     */         
/*     */         break;
/*     */       } 
/* 539 */       optionSpec.handleOption(this, arguments, detected, (String)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   void handleNonOptionArgument(String candidate, ArgumentList arguments, OptionSet detectedOptions) {
/* 544 */     specFor("[arguments]").handleOption(this, arguments, detectedOptions, candidate);
/*     */   }
/*     */   
/*     */   void noMoreOptions() {
/* 548 */     this.state = OptionParserState.noMoreOptions();
/*     */   }
/*     */   
/*     */   boolean looksLikeAnOption(String argument) {
/* 552 */     return (ParserRules.isShortOptionToken(argument) || ParserRules.isLongOptionToken(argument));
/*     */   }
/*     */   
/*     */   boolean isRecognized(String option) {
/* 556 */     return this.recognizedOptions.contains(option);
/*     */   }
/*     */   
/*     */   void requiredIf(List<String> precedentSynonyms, String required) {
/* 560 */     requiredIf(precedentSynonyms, specFor(required));
/*     */   }
/*     */   
/*     */   void requiredIf(List<String> precedentSynonyms, OptionSpec<?> required) {
/* 564 */     putDependentOption(precedentSynonyms, required, this.requiredIf);
/*     */   }
/*     */   
/*     */   void requiredUnless(List<String> precedentSynonyms, String required) {
/* 568 */     requiredUnless(precedentSynonyms, specFor(required));
/*     */   }
/*     */   
/*     */   void requiredUnless(List<String> precedentSynonyms, OptionSpec<?> required) {
/* 572 */     putDependentOption(precedentSynonyms, required, this.requiredUnless);
/*     */   }
/*     */   
/*     */   void availableIf(List<String> precedentSynonyms, String available) {
/* 576 */     availableIf(precedentSynonyms, specFor(available));
/*     */   }
/*     */   
/*     */   void availableIf(List<String> precedentSynonyms, OptionSpec<?> available) {
/* 580 */     putDependentOption(precedentSynonyms, available, this.availableIf);
/*     */   }
/*     */   
/*     */   void availableUnless(List<String> precedentSynonyms, String available) {
/* 584 */     availableUnless(precedentSynonyms, specFor(available));
/*     */   }
/*     */   
/*     */   void availableUnless(List<String> precedentSynonyms, OptionSpec<?> available) {
/* 588 */     putDependentOption(precedentSynonyms, available, this.availableUnless);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void putDependentOption(List<String> precedentSynonyms, OptionSpec<?> required, Map<List<String>, Set<OptionSpec<?>>> target) {
/* 594 */     for (String each : precedentSynonyms) {
/* 595 */       AbstractOptionSpec<?> spec = specFor(each);
/* 596 */       if (spec == null) {
/* 597 */         throw new UnconfiguredOptionException(precedentSynonyms);
/*     */       }
/*     */     } 
/* 600 */     Set<OptionSpec<?>> associated = target.get(precedentSynonyms);
/* 601 */     if (associated == null) {
/* 602 */       associated = new HashSet<>();
/* 603 */       target.put(precedentSynonyms, associated);
/*     */     } 
/*     */     
/* 606 */     associated.add(required);
/*     */   }
/*     */   
/*     */   private AbstractOptionSpec<?> specFor(char option) {
/* 610 */     return specFor(String.valueOf(option));
/*     */   }
/*     */   
/*     */   private AbstractOptionSpec<?> specFor(String option) {
/* 614 */     return (AbstractOptionSpec)this.recognizedOptions.get(option);
/*     */   }
/*     */   
/*     */   private void reset() {
/* 618 */     this.state = OptionParserState.moreOptions(this.posixlyCorrect);
/*     */   }
/*     */   
/*     */   private static char[] extractShortOptionsFrom(String argument) {
/* 622 */     char[] options = new char[argument.length() - 1];
/* 623 */     argument.getChars(1, argument.length(), options, 0);
/*     */     
/* 625 */     return options;
/*     */   }
/*     */   
/*     */   private void validateOptionCharacters(char[] options) {
/* 629 */     for (char each : options) {
/* 630 */       String option = String.valueOf(each);
/*     */       
/* 632 */       if (!isRecognized(option)) {
/* 633 */         throw OptionException.unrecognizedOption(option);
/*     */       }
/* 635 */       if (specFor(option).acceptsArguments())
/*     */         return; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static KeyValuePair parseLongOptionWithArgument(String argument) {
/* 641 */     return KeyValuePair.valueOf(argument.substring(2));
/*     */   }
/*     */   
/*     */   private static KeyValuePair parseShortOptionWithArgument(String argument) {
/* 645 */     return KeyValuePair.valueOf(argument.substring(1));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */