/*     */ package org.jline.builtins;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jline.utils.AttributedCharSequence;
/*     */ import org.jline.utils.AttributedString;
/*     */ import org.jline.utils.AttributedStringBuilder;
/*     */ import org.jline.utils.StyleResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Options
/*     */ {
/*  70 */   public static final String NL = System.getProperty("line.separator", "\n");
/*     */ 
/*     */   
/*     */   private static final String regex = "(?x)\\s*(?:-([^-]))?(?:,?\\s*-(\\w))?(?:,?\\s*--(\\w[\\w-]*)(=\\w+)?)?(?:,?\\s*--(\\w[\\w-]*))?.*?(?:\\(default=(.*)\\))?\\s*";
/*     */ 
/*     */   
/*     */   private static final int GROUP_SHORT_OPT_1 = 1;
/*     */ 
/*     */   
/*     */   private static final int GROUP_SHORT_OPT_2 = 2;
/*     */   
/*     */   private static final int GROUP_LONG_OPT_1 = 3;
/*     */   
/*     */   private static final int GROUP_ARG_1 = 4;
/*     */   
/*     */   private static final int GROUP_LONG_OPT_2 = 5;
/*     */   
/*     */   private static final int GROUP_DEFAULT = 6;
/*     */   
/*  89 */   private static final Pattern parser = Pattern.compile("(?x)\\s*(?:-([^-]))?(?:,?\\s*-(\\w))?(?:,?\\s*--(\\w[\\w-]*)(=\\w+)?)?(?:,?\\s*--(\\w[\\w-]*))?.*?(?:\\(default=(.*)\\))?\\s*");
/*  90 */   private static final Pattern uname = Pattern.compile("^Usage:\\s+(\\w+)");
/*     */   
/*     */   private final Map<String, Boolean> unmodifiableOptSet;
/*     */   private final Map<String, Object> unmodifiableOptArg;
/*  94 */   private final Map<String, Boolean> optSet = new HashMap<>();
/*  95 */   private final Map<String, Object> optArg = new HashMap<>();
/*     */   
/*  97 */   private final Map<String, String> optName = new HashMap<>();
/*  98 */   private final Map<String, String> optAlias = new HashMap<>();
/*  99 */   private final List<Object> xargs = new ArrayList();
/* 100 */   private List<String> args = null;
/*     */   
/*     */   private static final String UNKNOWN = "unknown";
/* 103 */   private String usageName = "unknown";
/* 104 */   private int usageIndex = 0;
/*     */   
/*     */   private final String[] spec;
/*     */   private final String[] gspec;
/*     */   private final String defOpts;
/*     */   private final String[] defArgs;
/* 110 */   private String error = null;
/*     */   
/*     */   private boolean optionsFirst = false;
/*     */   private boolean stopOnBadOption = false;
/*     */   
/*     */   public static Options compile(String[] optSpec) {
/* 116 */     return new Options(optSpec, null, null, System::getenv);
/*     */   }
/*     */   
/*     */   public static Options compile(String[] optSpec, Function<String, String> env) {
/* 120 */     return new Options(optSpec, null, null, env);
/*     */   }
/*     */   
/*     */   public static Options compile(String optSpec) {
/* 124 */     return compile(optSpec.split("\\n"), System::getenv);
/*     */   }
/*     */   
/*     */   public static Options compile(String optSpec, Function<String, String> env) {
/* 128 */     return compile(optSpec.split("\\n"), env);
/*     */   }
/*     */   
/*     */   public static Options compile(String[] optSpec, Options gopt) {
/* 132 */     return new Options(optSpec, null, gopt, System::getenv);
/*     */   }
/*     */   
/*     */   public static Options compile(String[] optSpec, String[] gspec) {
/* 136 */     return new Options(optSpec, gspec, null, System::getenv);
/*     */   }
/*     */   
/*     */   public Options setStopOnBadOption(boolean stopOnBadOption) {
/* 140 */     this.stopOnBadOption = stopOnBadOption;
/* 141 */     return this;
/*     */   }
/*     */   
/*     */   public Options setOptionsFirst(boolean optionsFirst) {
/* 145 */     this.optionsFirst = optionsFirst;
/* 146 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isSet(String name) {
/* 150 */     Boolean isSet = this.optSet.get(name);
/* 151 */     if (isSet == null) {
/* 152 */       throw new IllegalArgumentException("option not defined in spec: " + name);
/*     */     }
/* 154 */     return isSet.booleanValue();
/*     */   }
/*     */   
/*     */   public Object getObject(String name) {
/* 158 */     if (!this.optArg.containsKey(name)) throw new IllegalArgumentException("option not defined with argument: " + name);
/*     */     
/* 160 */     List<Object> list = getObjectList(name);
/*     */     
/* 162 */     return list.isEmpty() ? "" : list.get(list.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Object> getObjectList(String name) {
/*     */     List<Object> list;
/* 168 */     Object arg = this.optArg.get(name);
/*     */     
/* 170 */     if (arg == null) {
/* 171 */       throw new IllegalArgumentException("option not defined with argument: " + name);
/*     */     }
/*     */     
/* 174 */     if (arg instanceof String) {
/* 175 */       list = new ArrayList();
/* 176 */       if (!"".equals(arg)) list.add(arg); 
/*     */     } else {
/* 178 */       list = (List<Object>)arg;
/*     */     } 
/*     */     
/* 181 */     return list;
/*     */   }
/*     */   
/*     */   public List<String> getList(String name) {
/* 185 */     ArrayList<String> list = new ArrayList<>();
/* 186 */     for (Object o : getObjectList(name)) {
/*     */       try {
/* 188 */         list.add((String)o);
/* 189 */       } catch (ClassCastException e) {
/* 190 */         throw new IllegalArgumentException("option not String: " + name);
/*     */       } 
/*     */     } 
/* 193 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addArg(String name, Object value) {
/*     */     List<Object> list;
/* 199 */     Object arg = this.optArg.get(name);
/*     */     
/* 201 */     if (arg instanceof String) {
/* 202 */       list = new ArrayList();
/* 203 */       this.optArg.put(name, list);
/*     */     } else {
/* 205 */       list = (List<Object>)arg;
/*     */     } 
/*     */     
/* 208 */     list.add(value);
/*     */   }
/*     */   
/*     */   public String get(String name) {
/*     */     try {
/* 213 */       return (String)getObject(name);
/* 214 */     } catch (ClassCastException e) {
/* 215 */       throw new IllegalArgumentException("option not String: " + name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNumber(String name) {
/* 220 */     String number = get(name);
/*     */     try {
/* 222 */       if (number != null) return Integer.parseInt(number); 
/* 223 */       return 0;
/* 224 */     } catch (NumberFormatException e) {
/* 225 */       throw new IllegalArgumentException("option '" + name + "' not Number: " + number);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Object> argObjects() {
/* 230 */     return this.xargs;
/*     */   }
/*     */   
/*     */   public List<String> args() {
/* 234 */     if (this.args == null) {
/* 235 */       this.args = new ArrayList<>();
/* 236 */       for (Object arg : this.xargs) {
/* 237 */         this.args.add((arg == null) ? "null" : arg.toString());
/*     */       }
/*     */     } 
/* 240 */     return this.args;
/*     */   }
/*     */ 
/*     */   
/*     */   public void usage(PrintStream err) {
/* 245 */     err.print(usage());
/*     */   }
/*     */   
/*     */   public String usage() {
/* 249 */     StringBuilder buf = new StringBuilder();
/* 250 */     int index = 0;
/*     */     
/* 252 */     if (this.error != null) {
/* 253 */       buf.append(this.error);
/* 254 */       buf.append(NL);
/* 255 */       index = this.usageIndex;
/*     */     } 
/*     */     
/* 258 */     for (int i = index; i < this.spec.length; i++) {
/* 259 */       buf.append(this.spec[i]);
/* 260 */       buf.append(NL);
/*     */     } 
/*     */     
/* 263 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IllegalArgumentException usageError(String s) {
/* 273 */     this.error = this.usageName + ": " + s;
/* 274 */     return new IllegalArgumentException(this.error);
/*     */   }
/*     */ 
/*     */   
/*     */   private Options(String[] spec, String[] gspec, Options opt, Function<String, String> env) {
/* 279 */     this.gspec = gspec;
/*     */     
/* 281 */     if (gspec == null && opt == null) {
/* 282 */       this.spec = spec;
/*     */     } else {
/* 284 */       ArrayList<String> list = new ArrayList<>();
/* 285 */       list.addAll(Arrays.asList(spec));
/* 286 */       list.addAll(Arrays.asList((gspec != null) ? gspec : opt.gspec));
/* 287 */       this.spec = list.<String>toArray(new String[list.size()]);
/*     */     } 
/*     */     
/* 290 */     Map<String, Boolean> myOptSet = new HashMap<>();
/* 291 */     Map<String, Object> myOptArg = new HashMap<>();
/*     */     
/* 293 */     parseSpec(myOptSet, myOptArg);
/*     */     
/* 295 */     if (opt != null) {
/* 296 */       for (Map.Entry<String, Boolean> e : opt.optSet.entrySet()) {
/* 297 */         if (((Boolean)e.getValue()).booleanValue()) myOptSet.put(e.getKey(), Boolean.valueOf(true));
/*     */       
/*     */       } 
/* 300 */       for (Map.Entry<String, Object> e : opt.optArg.entrySet()) {
/* 301 */         if (!e.getValue().equals("")) myOptArg.put(e.getKey(), e.getValue());
/*     */       
/*     */       } 
/* 304 */       opt.reset();
/*     */     } 
/*     */     
/* 307 */     this.unmodifiableOptSet = Collections.unmodifiableMap(myOptSet);
/* 308 */     this.unmodifiableOptArg = Collections.unmodifiableMap(myOptArg);
/*     */     
/* 310 */     this.defOpts = (env != null) ? env.apply(this.usageName.toUpperCase() + "_OPTS") : null;
/* 311 */     this.defArgs = (this.defOpts != null) ? this.defOpts.split("\\s+") : new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseSpec(Map<String, Boolean> myOptSet, Map<String, Object> myOptArg) {
/* 318 */     int index = 0;
/* 319 */     for (String line : this.spec) {
/* 320 */       Matcher m = parser.matcher(line);
/*     */       
/* 322 */       if (m.matches()) {
/* 323 */         String opt = m.group(3);
/* 324 */         String name = (opt != null) ? opt : m.group(1);
/*     */         
/* 326 */         if (name != null && 
/* 327 */           myOptSet.putIfAbsent(name, Boolean.valueOf(false)) != null) {
/* 328 */           throw new IllegalArgumentException("duplicate option in spec: --" + name);
/*     */         }
/*     */         
/* 331 */         String dflt = (m.group(6) != null) ? m.group(6) : "";
/* 332 */         if (m.group(4) != null) myOptArg.put(opt, dflt);
/*     */         
/* 334 */         String opt2 = m.group(5);
/* 335 */         if (opt2 != null) {
/* 336 */           this.optAlias.put(opt2, opt);
/* 337 */           myOptSet.put(opt2, Boolean.valueOf(false));
/* 338 */           if (m.group(4) != null) myOptArg.put(opt2, "");
/*     */         
/*     */         } 
/* 341 */         for (int i = 0; i < 2; i++) {
/* 342 */           String sopt = m.group((i == 0) ? 1 : 2);
/* 343 */           if (sopt != null && 
/* 344 */             this.optName.putIfAbsent(sopt, name) != null) {
/* 345 */             throw new IllegalArgumentException("duplicate option in spec: -" + sopt);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 350 */       if (Objects.equals(this.usageName, "unknown")) {
/* 351 */         Matcher u = uname.matcher(line);
/* 352 */         if (u.find()) {
/* 353 */           this.usageName = u.group(1);
/* 354 */           this.usageIndex = index;
/*     */         } 
/*     */       } 
/*     */       
/* 358 */       index++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void reset() {
/* 363 */     this.optSet.clear();
/* 364 */     this.optSet.putAll(this.unmodifiableOptSet);
/* 365 */     this.optArg.clear();
/* 366 */     this.optArg.putAll(this.unmodifiableOptArg);
/* 367 */     this.xargs.clear();
/* 368 */     this.args = null;
/* 369 */     this.error = null;
/*     */   }
/*     */   
/*     */   public Options parse(Object[] argv) {
/* 373 */     return parse(argv, false);
/*     */   }
/*     */   
/*     */   public Options parse(List<?> argv) {
/* 377 */     return parse(argv, false);
/*     */   }
/*     */   
/*     */   public Options parse(Object[] argv, boolean skipArg0) {
/* 381 */     if (null == argv) throw new IllegalArgumentException("argv is null");
/*     */     
/* 383 */     return parse(Arrays.asList(argv), skipArg0);
/*     */   }
/*     */   
/*     */   public Options parse(List<?> argv, boolean skipArg0) {
/* 387 */     reset();
/* 388 */     List<Object> args = new ArrayList();
/* 389 */     args.addAll(Arrays.asList((Object[])this.defArgs));
/*     */     
/* 391 */     for (Object arg : argv) {
/* 392 */       if (skipArg0) {
/* 393 */         skipArg0 = false;
/* 394 */         this.usageName = arg.toString(); continue;
/*     */       } 
/* 396 */       args.add(arg);
/*     */     } 
/*     */ 
/*     */     
/* 400 */     String needArg = null;
/* 401 */     String needOpt = null;
/* 402 */     boolean endOpt = false;
/*     */     
/* 404 */     for (Object oarg : args) {
/* 405 */       String arg = (oarg == null) ? "null" : oarg.toString();
/*     */       
/* 407 */       if (endOpt) {
/* 408 */         this.xargs.add(oarg); continue;
/* 409 */       }  if (needArg != null) {
/* 410 */         addArg(needArg, oarg);
/* 411 */         needArg = null;
/* 412 */         needOpt = null; continue;
/* 413 */       }  if (!arg.startsWith("-") || (arg
/* 414 */         .length() > 1 && Character.isDigit(arg.charAt(1))) || "-"
/* 415 */         .equals(oarg)) {
/* 416 */         if (this.optionsFirst) endOpt = true; 
/* 417 */         this.xargs.add(oarg); continue;
/*     */       } 
/* 419 */       if (arg.equals("--")) { endOpt = true; continue; }
/* 420 */        if (arg.startsWith("--")) {
/* 421 */         int eq = arg.indexOf("=");
/* 422 */         String value = (eq == -1) ? null : arg.substring(eq + 1);
/* 423 */         String name = arg.substring(2, (eq == -1) ? arg.length() : eq);
/* 424 */         List<String> names = new ArrayList<>();
/*     */         
/* 426 */         if (this.optSet.containsKey(name)) {
/* 427 */           names.add(name);
/*     */         } else {
/* 429 */           for (String k : this.optSet.keySet()) {
/* 430 */             if (k.startsWith(name)) names.add(k);
/*     */           
/*     */           } 
/*     */         } 
/* 434 */         switch (names.size()) {
/*     */           case 1:
/* 436 */             name = names.get(0);
/* 437 */             this.optSet.put(name, Boolean.valueOf(true));
/* 438 */             if (this.optArg.containsKey(name)) {
/* 439 */               if (value != null) { addArg(name, value); continue; }
/* 440 */                needArg = name; continue;
/* 441 */             }  if (value != null) {
/* 442 */               throw usageError("option '--" + name + "' doesn't allow an argument");
/*     */             }
/*     */             continue;
/*     */           
/*     */           case 0:
/* 447 */             if (this.stopOnBadOption) {
/* 448 */               endOpt = true;
/* 449 */               this.xargs.add(oarg); continue;
/*     */             } 
/* 451 */             throw usageError("invalid option '--" + name + "'");
/*     */         } 
/*     */         
/* 454 */         throw usageError("option '--" + name + "' is ambiguous: " + names);
/*     */       } 
/*     */       
/* 457 */       for (int i = 1; i < arg.length(); i++) {
/* 458 */         String c = String.valueOf(arg.charAt(i));
/* 459 */         if (this.optName.containsKey(c))
/* 460 */         { String name = this.optName.get(c);
/* 461 */           this.optSet.put(name, Boolean.valueOf(true));
/* 462 */           if (this.optArg.containsKey(name)) {
/* 463 */             int k = i + 1;
/* 464 */             if (k < arg.length()) {
/* 465 */               addArg(name, arg.substring(k)); break;
/*     */             } 
/* 467 */             needOpt = c;
/* 468 */             needArg = name;
/*     */ 
/*     */             
/*     */             break;
/*     */           }  }
/* 473 */         else if (this.stopOnBadOption)
/* 474 */         { this.xargs.add("-" + c);
/* 475 */           endOpt = true; }
/* 476 */         else { throw usageError("invalid option '" + c + "'"); }
/*     */       
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 483 */     if (needArg != null) {
/* 484 */       String name = (needOpt != null) ? needOpt : ("--" + needArg);
/* 485 */       throw usageError("option '" + name + "' requires an argument");
/*     */     } 
/*     */ 
/*     */     
/* 489 */     for (Map.Entry<String, String> alias : this.optAlias.entrySet()) {
/* 490 */       if (((Boolean)this.optSet.get(alias.getKey())).booleanValue()) {
/* 491 */         this.optSet.put(alias.getValue(), Boolean.valueOf(true));
/* 492 */         if (this.optArg.containsKey(alias.getKey())) this.optArg.put(alias.getValue(), this.optArg.get(alias.getKey())); 
/*     */       } 
/* 494 */       this.optSet.remove(alias.getKey());
/* 495 */       this.optArg.remove(alias.getKey());
/*     */     } 
/*     */     
/* 498 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 503 */     return "isSet" + this.optSet + "\nArg" + this.optArg + "\nargs" + this.xargs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class HelpException
/*     */     extends Exception
/*     */   {
/*     */     public HelpException(String message) {
/* 515 */       super(message);
/*     */     }
/*     */     
/*     */     public static StyleResolver defaultStyle() {
/* 519 */       return Styles.helpStyle();
/*     */     }
/*     */     
/*     */     public static AttributedString highlight(String msg, StyleResolver resolver) {
/* 523 */       Matcher tm = Pattern.compile("(^|\\n)(Usage|Summary)(:)").matcher(msg);
/* 524 */       if (tm.find()) {
/* 525 */         boolean subcommand = tm.group(2).equals("Summary");
/* 526 */         AttributedStringBuilder asb = new AttributedStringBuilder(msg.length());
/*     */ 
/*     */ 
/*     */         
/* 530 */         AttributedStringBuilder acommand = (new AttributedStringBuilder()).append(msg.substring(0, tm.start(2))).styleMatches(
/* 531 */             Pattern.compile("(?:^\\s*)([a-z]+[a-zA-Z0-9-]*)\\b"), 
/* 532 */             Collections.singletonList(resolver.resolve(".co")));
/* 533 */         asb.append((AttributedCharSequence)acommand);
/*     */         
/* 535 */         asb.styled(resolver.resolve(".ti"), tm.group(2)).append(":");
/*     */         
/* 537 */         for (String line : msg.substring(tm.end(3)).split("\n")) {
/* 538 */           String syntax, comment; int ind = line.lastIndexOf("  ");
/*     */           
/* 540 */           if (ind > 20) {
/* 541 */             syntax = line.substring(0, ind);
/* 542 */             comment = line.substring(ind + 1);
/*     */           } else {
/* 544 */             syntax = line;
/* 545 */             comment = "";
/*     */           } 
/* 547 */           asb.append((AttributedCharSequence)_highlightSyntax(syntax, resolver, subcommand));
/* 548 */           asb.append((AttributedCharSequence)_highlightComment(comment, resolver));
/* 549 */           asb.append("\n");
/*     */         } 
/* 551 */         return asb.toAttributedString();
/*     */       } 
/* 553 */       return AttributedString.fromAnsi(msg);
/*     */     }
/*     */ 
/*     */     
/*     */     public static AttributedString highlightSyntax(String syntax, StyleResolver resolver, boolean subcommands) {
/* 558 */       return _highlightSyntax(syntax, resolver, subcommands).toAttributedString();
/*     */     }
/*     */     
/*     */     public static AttributedString highlightSyntax(String syntax, StyleResolver resolver) {
/* 562 */       return _highlightSyntax(syntax, resolver, false).toAttributedString();
/*     */     }
/*     */     
/*     */     public static AttributedString highlightComment(String comment, StyleResolver resolver) {
/* 566 */       return _highlightComment(comment, resolver).toAttributedString();
/*     */     }
/*     */ 
/*     */     
/*     */     private static AttributedStringBuilder _highlightSyntax(String syntax, StyleResolver resolver, boolean subcommand) {
/* 571 */       StringBuilder indent = new StringBuilder();
/* 572 */       for (char c : syntax.toCharArray()) {
/* 573 */         if (c != ' ') {
/*     */           break;
/*     */         }
/* 576 */         indent.append(c);
/*     */       } 
/* 578 */       AttributedStringBuilder asyntax = (new AttributedStringBuilder()).append(syntax.substring(indent.length()));
/*     */       
/* 580 */       asyntax.styleMatches(
/* 581 */           Pattern.compile("(?:^)([a-z]+[a-zA-Z0-9-]*)\\b"), 
/* 582 */           Collections.singletonList(resolver.resolve(".co")));
/* 583 */       if (!subcommand) {
/*     */         
/* 585 */         asyntax.styleMatches(
/* 586 */             Pattern.compile("(?:<|\\[|\\s|=)([A-Za-z]+[A-Za-z_-]*)\\b"), 
/* 587 */             Collections.singletonList(resolver.resolve(".ar")));
/*     */         
/* 589 */         asyntax.styleMatches(
/* 590 */             Pattern.compile("(?:^|\\s|\\[)(-\\$|-\\?|[-]{1,2}[A-Za-z-]+\\b)"), 
/* 591 */             Collections.singletonList(resolver.resolve(".op")));
/*     */       } 
/* 593 */       return (new AttributedStringBuilder()).append(indent).append((AttributedCharSequence)asyntax);
/*     */     }
/*     */     
/*     */     private static AttributedStringBuilder _highlightComment(String comment, StyleResolver resolver) {
/* 597 */       AttributedStringBuilder acomment = (new AttributedStringBuilder()).append(comment);
/*     */       
/* 599 */       acomment.styleMatches(
/* 600 */           Pattern.compile("(?:\\s|\\[)(-\\$|-\\?|[-]{1,2}[A-Za-z-]+\\b)"), 
/* 601 */           Collections.singletonList(resolver.resolve(".op")));
/*     */       
/* 603 */       acomment.styleMatches(
/* 604 */           Pattern.compile("(?:\\s)([a-z]+[-]+[a-z]+|[A-Z_]{2,})(?:\\s)"), 
/* 605 */           Collections.singletonList(resolver.resolve(".ar")));
/* 606 */       return acomment;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\Options.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */