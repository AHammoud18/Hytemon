/*     */ package org.jline.console.impl;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import org.jline.builtins.Styles;
/*     */ import org.jline.builtins.SyntaxHighlighter;
/*     */ import org.jline.console.SystemRegistry;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.ParsedLine;
/*     */ import org.jline.reader.Parser;
/*     */ import org.jline.reader.impl.DefaultHighlighter;
/*     */ import org.jline.utils.AttributedCharSequence;
/*     */ import org.jline.utils.AttributedString;
/*     */ import org.jline.utils.AttributedStringBuilder;
/*     */ import org.jline.utils.Log;
/*     */ import org.jline.utils.OSUtils;
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
/*     */ public class SystemHighlighter
/*     */   extends DefaultHighlighter
/*     */ {
/*  48 */   private StyleResolver resolver = Styles.lsStyle();
/*     */   private static final String REGEX_COMMENT_LINE = "\\s*#.*";
/*     */   private static final String READER_COLORS = "READER_COLORS";
/*     */   protected final SyntaxHighlighter commandHighlighter;
/*     */   protected final SyntaxHighlighter argsHighlighter;
/*     */   protected final SyntaxHighlighter langHighlighter;
/*     */   protected final SystemRegistry systemRegistry;
/*  55 */   protected final Map<String, FileHighlightCommand> fileHighlight = new HashMap<>();
/*  56 */   protected final Map<String, SyntaxHighlighter> specificHighlighter = new HashMap<>();
/*     */   protected int commandIndex;
/*  58 */   private final List<Supplier<Boolean>> externalHighlightersRefresh = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SystemHighlighter(SyntaxHighlighter commandHighlighter, SyntaxHighlighter argsHighlighter, SyntaxHighlighter langHighlighter) {
/*  64 */     this.commandHighlighter = commandHighlighter;
/*  65 */     this.argsHighlighter = argsHighlighter;
/*  66 */     this.langHighlighter = langHighlighter;
/*  67 */     this.systemRegistry = SystemRegistry.get();
/*     */   }
/*     */   
/*     */   public void setSpecificHighlighter(String command, SyntaxHighlighter highlighter) {
/*  71 */     this.specificHighlighter.put(command, highlighter);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(LineReader lineReader) {
/*  76 */     Path currentTheme = null;
/*  77 */     if (this.commandHighlighter != null) {
/*  78 */       this.commandHighlighter.refresh();
/*  79 */       currentTheme = compareThemes(this.commandHighlighter, currentTheme);
/*     */     } 
/*  81 */     if (this.argsHighlighter != null) {
/*  82 */       this.argsHighlighter.refresh();
/*  83 */       currentTheme = compareThemes(this.argsHighlighter, currentTheme);
/*     */     } 
/*  85 */     if (this.langHighlighter != null) {
/*  86 */       this.langHighlighter.refresh();
/*  87 */       currentTheme = compareThemes(this.langHighlighter, currentTheme);
/*     */     } 
/*  89 */     for (SyntaxHighlighter sh : this.specificHighlighter.values()) {
/*  90 */       sh.refresh();
/*  91 */       currentTheme = compareThemes(sh, currentTheme);
/*     */     } 
/*  93 */     if (currentTheme != null) {
/*  94 */       try { BufferedReader reader = Files.newBufferedReader(currentTheme);
/*     */         
/*  96 */         try { Map<String, String> tokens = new HashMap<>(); String line;
/*  97 */           while ((line = reader.readLine()) != null) {
/*  98 */             String[] parts = line.trim().split("\\s+", 2);
/*  99 */             if (parts[0].matches("[A-Z_]+") && parts.length == 2) {
/* 100 */               tokens.put(parts[0], parts[1]);
/*     */             }
/*     */           } 
/* 103 */           SystemRegistry registry = SystemRegistry.get();
/* 104 */           registry.setConsoleOption("NANORC_THEME", tokens);
/* 105 */           Map<String, String> readerColors = (Map<String, String>)registry.consoleOption("READER_COLORS", new HashMap<>());
/* 106 */           Styles.StyleCompiler styleCompiler = new Styles.StyleCompiler(readerColors);
/* 107 */           for (String key : readerColors.keySet()) {
/* 108 */             lineReader.setVariable(key, styleCompiler.getStyle(key));
/*     */           }
/* 110 */           for (Supplier<Boolean> refresh : this.externalHighlightersRefresh) {
/* 111 */             refresh.get();
/*     */           }
/* 113 */           this.resolver = Styles.lsStyle();
/* 114 */           if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null) try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 115 */       { Log.warn(new Object[] { e.getMessage() }); }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   public void addExternalHighlighterRefresh(Supplier<Boolean> refresh) {
/* 121 */     this.externalHighlightersRefresh.add(refresh);
/*     */   }
/*     */   
/*     */   private Path compareThemes(SyntaxHighlighter highlighter, Path currentTheme) {
/*     */     Path out;
/* 126 */     if (currentTheme != null) {
/* 127 */       Path theme = highlighter.getCurrentTheme();
/*     */       try {
/* 129 */         if (theme != null && !Files.isSameFile(theme, currentTheme)) {
/* 130 */           Log.warn(new Object[] { "Multiple nanorc themes are in use!" });
/*     */         }
/* 132 */       } catch (Exception e) {
/* 133 */         Log.warn(new Object[] { e.getMessage() });
/*     */       } 
/* 135 */       out = currentTheme;
/*     */     } else {
/* 137 */       out = highlighter.getCurrentTheme();
/*     */     } 
/* 139 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributedString highlight(LineReader reader, String buffer) {
/* 144 */     return doDefaultHighlight(reader) ? super.highlight(reader, buffer) : systemHighlight(reader, buffer);
/*     */   }
/*     */   
/*     */   public void addFileHighlight(String... commands) {
/* 148 */     for (String c : commands) {
/* 149 */       this.fileHighlight.put(c, new FileHighlightCommand());
/*     */     }
/*     */   }
/*     */   
/*     */   public void addFileHighlight(String command, String subcommand, Collection<String> fileOptions) {
/* 154 */     this.fileHighlight.put(command, new FileHighlightCommand(subcommand, fileOptions));
/*     */   }
/*     */   
/*     */   private boolean doDefaultHighlight(LineReader reader) {
/* 158 */     String search = reader.getSearchTerm();
/* 159 */     return ((search != null && !search.isEmpty()) || reader
/* 160 */       .getRegionActive() != LineReader.RegionType.NONE || this.errorIndex > -1 || this.errorPattern != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AttributedString systemHighlight(LineReader reader, String buffer) {
/*     */     AttributedString out;
/* 167 */     Parser parser = reader.getParser();
/* 168 */     ParsedLine pl = parser.parse(buffer, 0, Parser.ParseContext.SPLIT_LINE);
/* 169 */     String command = !pl.words().isEmpty() ? parser.getCommand(pl.words().get(0)) : "";
/* 170 */     command = command.startsWith("!") ? "!" : command;
/* 171 */     this.commandIndex = buffer.indexOf(command) + command.length();
/* 172 */     if (buffer.trim().isEmpty()) {
/* 173 */       out = (new AttributedStringBuilder()).append(buffer).toAttributedString();
/* 174 */     } else if (this.specificHighlighter.containsKey(command)) {
/* 175 */       AttributedStringBuilder asb = new AttributedStringBuilder();
/* 176 */       if (this.commandHighlighter == null) {
/* 177 */         asb.append(((SyntaxHighlighter)this.specificHighlighter.get(command)).reset().highlight(buffer));
/*     */       } else {
/* 179 */         highlightCommand(buffer.substring(0, this.commandIndex), asb);
/* 180 */         asb.append(((SyntaxHighlighter)this.specificHighlighter.get(command)).reset().highlight(buffer.substring(this.commandIndex)));
/*     */       } 
/* 182 */       out = asb.toAttributedString();
/* 183 */     } else if (this.fileHighlight.containsKey(command)) {
/* 184 */       FileHighlightCommand fhc = this.fileHighlight.get(command);
/* 185 */       if (!fhc.hasFileOptions()) {
/* 186 */         out = doFileArgsHighlight(reader, buffer, pl.words(), fhc);
/*     */       } else {
/* 188 */         out = doFileOptsHighlight(reader, buffer, pl.words(), fhc);
/*     */       } 
/* 190 */     } else if (this.systemRegistry.isCommandOrScript(command) || this.systemRegistry
/* 191 */       .isCommandAlias(command) || command
/* 192 */       .isEmpty() || buffer
/* 193 */       .matches("\\s*#.*")) {
/* 194 */       out = doCommandHighlight(buffer);
/* 195 */     } else if (this.langHighlighter != null) {
/* 196 */       out = this.langHighlighter.reset().highlight(buffer);
/*     */     } else {
/* 198 */       out = (new AttributedStringBuilder()).append(buffer).toAttributedString();
/*     */     } 
/* 200 */     return out;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AttributedString doFileOptsHighlight(LineReader reader, String buffer, List<String> words, FileHighlightCommand fhc) {
/* 205 */     AttributedStringBuilder asb = new AttributedStringBuilder();
/* 206 */     if (this.commandIndex < 0) {
/* 207 */       highlightCommand(buffer, asb);
/*     */     } else {
/* 209 */       highlightCommand(buffer.substring(0, this.commandIndex), asb);
/* 210 */       if (!fhc.isSubcommand() || (words.size() > 2 && fhc.getSubcommand().equals(words.get(1)))) {
/* 211 */         boolean subCommand = fhc.isSubcommand();
/* 212 */         int idx = buffer.indexOf(words.get(0)) + ((String)words.get(0)).length();
/* 213 */         boolean fileOption = false;
/* 214 */         for (int i = 1; i < words.size(); i++) {
/* 215 */           int nextIdx = buffer.substring(idx).indexOf(words.get(i)) + idx;
/* 216 */           for (int j = idx; j < nextIdx; j++) {
/* 217 */             asb.append(buffer.charAt(j));
/*     */           }
/* 219 */           String word = words.get(i);
/* 220 */           if (subCommand) {
/* 221 */             subCommand = false;
/* 222 */             highlightArgs(word, asb);
/* 223 */           } else if (word.contains("=") && fhc
/* 224 */             .getFileOptions().contains(word.substring(0, word.indexOf("=")))) {
/* 225 */             highlightArgs(word.substring(0, word.indexOf("=") + 1), asb);
/* 226 */             highlightFileArg(reader, word.substring(word.indexOf("=") + 1), asb);
/* 227 */           } else if (fhc.getFileOptions().contains(word)) {
/* 228 */             highlightArgs(word, asb);
/* 229 */             fileOption = true;
/* 230 */           } else if (fileOption) {
/* 231 */             highlightFileArg(reader, word, asb);
/*     */           } else {
/* 233 */             highlightArgs(word, asb);
/* 234 */             fileOption = false;
/*     */           } 
/* 236 */           idx = nextIdx + word.length();
/*     */         } 
/*     */       } else {
/* 239 */         highlightArgs(buffer.substring(this.commandIndex), asb);
/*     */       } 
/*     */     } 
/* 242 */     return asb.toAttributedString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AttributedString doFileArgsHighlight(LineReader reader, String buffer, List<String> words, FileHighlightCommand fhc) {
/* 247 */     AttributedStringBuilder asb = new AttributedStringBuilder();
/* 248 */     if (this.commandIndex < 0) {
/* 249 */       highlightCommand(buffer, asb);
/*     */     } else {
/* 251 */       highlightCommand(buffer.substring(0, this.commandIndex), asb);
/* 252 */       if (!fhc.isSubcommand() || (words.size() > 2 && fhc.getSubcommand().equals(words.get(1)))) {
/* 253 */         boolean subCommand = fhc.isSubcommand();
/* 254 */         int idx = buffer.indexOf(words.get(0)) + ((String)words.get(0)).length();
/* 255 */         for (int i = 1; i < words.size(); i++) {
/* 256 */           int nextIdx = buffer.substring(idx).indexOf(words.get(i)) + idx;
/* 257 */           for (int j = idx; j < nextIdx; j++) {
/* 258 */             asb.append(buffer.charAt(j));
/*     */           }
/* 260 */           if (subCommand) {
/* 261 */             subCommand = false;
/* 262 */             highlightArgs(words.get(i), asb);
/*     */           } else {
/* 264 */             highlightFileArg(reader, words.get(i), asb);
/* 265 */             idx = nextIdx + ((String)words.get(i)).length();
/*     */           } 
/*     */         } 
/*     */       } else {
/* 269 */         highlightArgs(buffer.substring(this.commandIndex), asb);
/*     */       } 
/*     */     } 
/* 272 */     return asb.toAttributedString();
/*     */   }
/*     */   
/*     */   protected AttributedString doCommandHighlight(String buffer) {
/*     */     AttributedString out;
/* 277 */     if (this.commandHighlighter != null || this.argsHighlighter != null) {
/* 278 */       AttributedStringBuilder asb = new AttributedStringBuilder();
/* 279 */       if (this.commandIndex < 0 || buffer.matches("\\s*#.*")) {
/* 280 */         highlightCommand(buffer, asb);
/*     */       } else {
/* 282 */         highlightCommand(buffer.substring(0, this.commandIndex), asb);
/* 283 */         highlightArgs(buffer.substring(this.commandIndex), asb);
/*     */       } 
/* 285 */       out = asb.toAttributedString();
/*     */     } else {
/* 287 */       out = (new AttributedStringBuilder()).append(buffer).toAttributedString();
/*     */     } 
/* 289 */     return out;
/*     */   }
/*     */   
/*     */   private void highlightFileArg(LineReader reader, String arg, AttributedStringBuilder asb) {
/* 293 */     if (arg.startsWith("-")) {
/* 294 */       highlightArgs(arg, asb);
/*     */     }
/*     */     else {
/*     */       
/* 298 */       String separator = reader.isSet(LineReader.Option.USE_FORWARD_SLASH) ? "/" : Paths.get(System.getProperty("user.dir"), new String[0]).getFileSystem().getSeparator();
/* 299 */       StringBuilder sb = new StringBuilder();
/*     */       try {
/* 301 */         Path path = (new File(arg)).toPath();
/* 302 */         Iterator<Path> iterator = path.iterator();
/* 303 */         if (OSUtils.IS_WINDOWS && arg.matches("^[A-Za-z]:.*$")) {
/* 304 */           if (arg.length() == 2) {
/* 305 */             sb.append(arg);
/* 306 */             asb.append(arg);
/* 307 */           } else if (arg.charAt(2) == separator.charAt(0)) {
/* 308 */             sb.append(arg.substring(0, 3));
/* 309 */             asb.append(arg.substring(0, 3));
/*     */           } 
/*     */         }
/* 312 */         if (arg.startsWith(separator)) {
/* 313 */           sb.append(separator);
/* 314 */           asb.append(separator);
/*     */         } 
/* 316 */         while (iterator.hasNext()) {
/* 317 */           sb.append(iterator.next());
/* 318 */           highlightFile((new File(sb.toString())).toPath(), asb);
/* 319 */           if (iterator.hasNext()) {
/* 320 */             sb.append(separator);
/* 321 */             asb.append(separator);
/*     */           } 
/*     */         } 
/* 324 */         if (arg.length() > 2 && !arg.matches("^[A-Za-z]:" + separator) && arg.endsWith(separator)) {
/* 325 */           asb.append(separator);
/*     */         }
/* 327 */       } catch (Exception e) {
/* 328 */         asb.append(arg);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void highlightFile(Path path, AttributedStringBuilder asb) {
/* 334 */     AttributedStringBuilder sb = new AttributedStringBuilder();
/* 335 */     String name = path.getFileName().toString();
/* 336 */     int idx = name.lastIndexOf(".");
/* 337 */     String type = (idx != -1) ? (".*" + name.substring(idx)) : null;
/* 338 */     if (Files.isSymbolicLink(path)) {
/* 339 */       sb.styled(this.resolver.resolve(".ln"), name);
/* 340 */     } else if (Files.isDirectory(path, new java.nio.file.LinkOption[0])) {
/* 341 */       sb.styled(this.resolver.resolve(".di"), name);
/* 342 */     } else if (Files.isExecutable(path) && !OSUtils.IS_WINDOWS) {
/* 343 */       sb.styled(this.resolver.resolve(".ex"), name);
/* 344 */     } else if (type != null && this.resolver.resolve(type).getStyle() != 0L) {
/* 345 */       sb.styled(this.resolver.resolve(type), name);
/* 346 */     } else if (Files.isRegularFile(path, new java.nio.file.LinkOption[0])) {
/* 347 */       sb.styled(this.resolver.resolve(".fi"), name);
/*     */     } else {
/* 349 */       sb.append(name);
/*     */     } 
/* 351 */     asb.append((AttributedCharSequence)sb);
/*     */   }
/*     */   
/*     */   private void highlightArgs(String args, AttributedStringBuilder asb) {
/* 355 */     if (this.argsHighlighter != null) {
/* 356 */       asb.append(this.argsHighlighter.reset().highlight(args));
/*     */     } else {
/* 358 */       asb.append(args);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void highlightCommand(String command, AttributedStringBuilder asb) {
/* 363 */     if (this.commandHighlighter != null) {
/* 364 */       asb.append(this.commandHighlighter.reset().highlight(command));
/*     */     } else {
/* 366 */       asb.append(command);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static class FileHighlightCommand {
/*     */     private final String subcommand;
/* 372 */     private final List<String> fileOptions = new ArrayList<>();
/*     */     
/*     */     public FileHighlightCommand() {
/* 375 */       this(null, new ArrayList<>());
/*     */     }
/*     */     
/*     */     public FileHighlightCommand(String subcommand, Collection<String> fileOptions) {
/* 379 */       this.subcommand = subcommand;
/* 380 */       this.fileOptions.addAll(fileOptions);
/*     */     }
/*     */     
/*     */     public boolean isSubcommand() {
/* 384 */       return (this.subcommand != null);
/*     */     }
/*     */     
/*     */     public boolean hasFileOptions() {
/* 388 */       return !this.fileOptions.isEmpty();
/*     */     }
/*     */     
/*     */     public String getSubcommand() {
/* 392 */       return this.subcommand;
/*     */     }
/*     */     
/*     */     public List<String> getFileOptions() {
/* 396 */       return this.fileOptions;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\impl\SystemHighlighter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */