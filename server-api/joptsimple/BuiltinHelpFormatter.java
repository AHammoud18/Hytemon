/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import joptsimple.internal.Classes;
/*     */ import joptsimple.internal.Messages;
/*     */ import joptsimple.internal.Rows;
/*     */ import joptsimple.internal.Strings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BuiltinHelpFormatter
/*     */   implements HelpFormatter
/*     */ {
/*     */   private final Rows nonOptionRows;
/*     */   private final Rows optionRows;
/*     */   
/*     */   BuiltinHelpFormatter() {
/*  62 */     this(80, 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuiltinHelpFormatter(int desiredOverallWidth, int desiredColumnSeparatorWidth) {
/*  73 */     this.nonOptionRows = new Rows(desiredOverallWidth * 2, 0);
/*  74 */     this.optionRows = new Rows(desiredOverallWidth, desiredColumnSeparatorWidth);
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
/*     */   public String format(Map<String, ? extends OptionDescriptor> options) {
/*  88 */     this.optionRows.reset();
/*  89 */     this.nonOptionRows.reset();
/*     */     
/*  91 */     Comparator<OptionDescriptor> comparator = new Comparator<OptionDescriptor>()
/*     */       {
/*     */         public int compare(OptionDescriptor first, OptionDescriptor second) {
/*  94 */           return ((String)first.options().iterator().next()).compareTo(second.options().iterator().next());
/*     */         }
/*     */       };
/*     */     
/*  98 */     Set<OptionDescriptor> sorted = new TreeSet<>(comparator);
/*  99 */     sorted.addAll(options.values());
/*     */     
/* 101 */     addRows(sorted);
/*     */     
/* 103 */     return formattedHelpOutput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOptionRow(String single) {
/* 112 */     addOptionRow(single, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOptionRow(String left, String right) {
/* 122 */     this.optionRows.add(left, right);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addNonOptionRow(String single) {
/* 131 */     this.nonOptionRows.add(single, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fitRowsToWidth() {
/* 138 */     this.nonOptionRows.fitToWidth();
/* 139 */     this.optionRows.fitToWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String nonOptionOutput() {
/* 148 */     return this.nonOptionRows.render();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String optionOutput() {
/* 157 */     return this.optionRows.render();
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
/*     */   protected String formattedHelpOutput() {
/* 173 */     StringBuilder formatted = new StringBuilder();
/* 174 */     String nonOptionDisplay = nonOptionOutput();
/* 175 */     if (!Strings.isNullOrEmpty(nonOptionDisplay))
/* 176 */       formatted.append(nonOptionDisplay).append(Strings.LINE_SEPARATOR); 
/* 177 */     formatted.append(optionOutput());
/*     */     
/* 179 */     return formatted.toString();
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
/*     */   protected void addRows(Collection<? extends OptionDescriptor> options) {
/* 198 */     addNonOptionsDescription(options);
/*     */     
/* 200 */     if (options.isEmpty()) {
/* 201 */       addOptionRow(message("no.options.specified", new Object[0]));
/*     */     } else {
/* 203 */       addHeaders(options);
/* 204 */       addOptions(options);
/*     */     } 
/*     */     
/* 207 */     fitRowsToWidth();
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
/*     */   protected void addNonOptionsDescription(Collection<? extends OptionDescriptor> options) {
/* 227 */     OptionDescriptor nonOptions = findAndRemoveNonOptionsSpec(options);
/* 228 */     if (shouldShowNonOptionArgumentDisplay(nonOptions)) {
/* 229 */       addNonOptionRow(message("non.option.arguments.header", new Object[0]));
/* 230 */       addNonOptionRow(createNonOptionArgumentsDisplay(nonOptions));
/*     */     } 
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
/*     */   protected boolean shouldShowNonOptionArgumentDisplay(OptionDescriptor nonOptionDescriptor) {
/* 245 */     return (!Strings.isNullOrEmpty(nonOptionDescriptor.description()) || 
/* 246 */       !Strings.isNullOrEmpty(nonOptionDescriptor.argumentTypeIndicator()) || 
/* 247 */       !Strings.isNullOrEmpty(nonOptionDescriptor.argumentDescription()));
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
/*     */   protected String createNonOptionArgumentsDisplay(OptionDescriptor nonOptionDescriptor) {
/* 262 */     StringBuilder buffer = new StringBuilder();
/* 263 */     maybeAppendOptionInfo(buffer, nonOptionDescriptor);
/* 264 */     maybeAppendNonOptionsDescription(buffer, nonOptionDescriptor);
/*     */     
/* 266 */     return buffer.toString();
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
/*     */   protected void maybeAppendNonOptionsDescription(StringBuilder buffer, OptionDescriptor nonOptions) {
/* 280 */     buffer.append((buffer.length() > 0 && !Strings.isNullOrEmpty(nonOptions.description())) ? " -- " : "")
/* 281 */       .append(nonOptions.description());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OptionDescriptor findAndRemoveNonOptionsSpec(Collection<? extends OptionDescriptor> options) {
/* 291 */     for (Iterator<? extends OptionDescriptor> it = options.iterator(); it.hasNext(); ) {
/* 292 */       OptionDescriptor next = it.next();
/* 293 */       if (next.representsNonOptions()) {
/* 294 */         it.remove();
/* 295 */         return next;
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     throw new AssertionError("no non-options argument spec");
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
/*     */   protected void addHeaders(Collection<? extends OptionDescriptor> options) {
/* 312 */     if (hasRequiredOption(options)) {
/* 313 */       addOptionRow(message("option.header.with.required.indicator", new Object[0]), message("description.header", new Object[0]));
/* 314 */       addOptionRow(message("option.divider.with.required.indicator", new Object[0]), message("description.divider", new Object[0]));
/*     */     } else {
/* 316 */       addOptionRow(message("option.header", new Object[0]), message("description.header", new Object[0]));
/* 317 */       addOptionRow(message("option.divider", new Object[0]), message("description.divider", new Object[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean hasRequiredOption(Collection<? extends OptionDescriptor> options) {
/* 328 */     for (OptionDescriptor each : options) {
/* 329 */       if (each.isRequired()) {
/* 330 */         return true;
/*     */       }
/*     */     } 
/* 333 */     return false;
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
/*     */   protected void addOptions(Collection<? extends OptionDescriptor> options) {
/* 346 */     for (OptionDescriptor each : options) {
/* 347 */       if (!each.representsNonOptions()) {
/* 348 */         addOptionRow(createOptionDisplay(each), createDescriptionDisplay(each));
/*     */       }
/*     */     } 
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
/*     */   protected String createOptionDisplay(OptionDescriptor descriptor) {
/* 371 */     StringBuilder buffer = new StringBuilder(descriptor.isRequired() ? "* " : "");
/*     */     
/* 373 */     for (Iterator<String> i = descriptor.options().iterator(); i.hasNext(); ) {
/* 374 */       String option = i.next();
/* 375 */       buffer.append(optionLeader(option));
/* 376 */       buffer.append(option);
/*     */       
/* 378 */       if (i.hasNext()) {
/* 379 */         buffer.append(", ");
/*     */       }
/*     */     } 
/* 382 */     maybeAppendOptionInfo(buffer, descriptor);
/*     */     
/* 384 */     return buffer.toString();
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
/*     */   protected String optionLeader(String option) {
/* 397 */     return (option.length() > 1) ? "--" : ParserRules.HYPHEN;
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
/*     */   protected void maybeAppendOptionInfo(StringBuilder buffer, OptionDescriptor descriptor) {
/* 415 */     String indicator = extractTypeIndicator(descriptor);
/* 416 */     String description = descriptor.argumentDescription();
/* 417 */     if (descriptor.acceptsArguments() || 
/* 418 */       !Strings.isNullOrEmpty(description) || descriptor
/* 419 */       .representsNonOptions())
/*     */     {
/* 421 */       appendOptionHelp(buffer, indicator, description, descriptor.requiresArgument());
/*     */     }
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
/*     */   protected String extractTypeIndicator(OptionDescriptor descriptor) {
/* 437 */     String indicator = descriptor.argumentTypeIndicator();
/*     */     
/* 439 */     if (!Strings.isNullOrEmpty(indicator) && !String.class.getName().equals(indicator)) {
/* 440 */       return Classes.shortNameOf(indicator);
/*     */     }
/* 442 */     return "String";
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
/*     */   protected void appendOptionHelp(StringBuilder buffer, String typeIndicator, String description, boolean required) {
/* 459 */     if (required) {
/* 460 */       appendTypeIndicator(buffer, typeIndicator, description, '<', '>');
/*     */     } else {
/* 462 */       appendTypeIndicator(buffer, typeIndicator, description, '[', ']');
/*     */     } 
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
/*     */ 
/*     */   
/*     */   protected void appendTypeIndicator(StringBuilder buffer, String typeIndicator, String description, char start, char end) {
/* 486 */     buffer.append(' ').append(start);
/* 487 */     if (typeIndicator != null) {
/* 488 */       buffer.append(typeIndicator);
/*     */     }
/* 490 */     if (!Strings.isNullOrEmpty(description)) {
/* 491 */       if (typeIndicator != null) {
/* 492 */         buffer.append(": ");
/*     */       }
/* 494 */       buffer.append(description);
/*     */     } 
/*     */     
/* 497 */     buffer.append(end);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected String createDescriptionDisplay(OptionDescriptor descriptor) {
/* 521 */     List<?> defaultValues = descriptor.defaultValues();
/* 522 */     if (defaultValues.isEmpty()) {
/* 523 */       return descriptor.description();
/*     */     }
/* 525 */     String defaultValuesDisplay = createDefaultValuesDisplay(defaultValues);
/*     */ 
/*     */ 
/*     */     
/* 529 */     return (descriptor.description() + ' ' + Strings.surround(message("default.value.header", new Object[0]) + ' ' + defaultValuesDisplay, '(', ')')).trim();
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
/*     */   protected String createDefaultValuesDisplay(List<?> defaultValues) {
/* 542 */     return (defaultValues.size() == 1) ? defaultValues.get(0).toString() : defaultValues.toString();
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
/*     */   protected String message(String keySuffix, Object... args) {
/* 558 */     return Messages.message(
/* 559 */         Locale.getDefault(), "joptsimple.HelpFormatterMessages", BuiltinHelpFormatter.class, keySuffix, args);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\BuiltinHelpFormatter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */