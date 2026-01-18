/*      */ package org.jline.builtins;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Array;
/*      */ import java.nio.file.DirectoryStream;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.Paths;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import org.jline.reader.Candidate;
/*      */ import org.jline.reader.LineReader;
/*      */ import org.jline.reader.ParsedLine;
/*      */ import org.jline.reader.impl.completer.NullCompleter;
/*      */ import org.jline.reader.impl.completer.StringsCompleter;
/*      */ import org.jline.terminal.Terminal;
/*      */ import org.jline.utils.AttributedString;
/*      */ import org.jline.utils.AttributedStringBuilder;
/*      */ import org.jline.utils.OSUtils;
/*      */ import org.jline.utils.StyleResolver;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Completers
/*      */ {
/*      */   public static interface CompletionEnvironment
/*      */   {
/*      */     Map<String, List<Completers.CompletionData>> getCompletions();
/*      */     
/*      */     Set<String> getCommands();
/*      */     
/*      */     String resolveCommand(String param1String);
/*      */     
/*      */     String commandName(String param1String);
/*      */     
/*      */     Object evaluate(LineReader param1LineReader, ParsedLine param1ParsedLine, String param1String) throws Exception;
/*      */   }
/*      */   
/*      */   public static class CompletionData
/*      */   {
/*      */     public final List<String> options;
/*      */     public final String description;
/*      */     public final String argument;
/*      */     public final String condition;
/*      */     
/*      */     public CompletionData(List<String> options, String description, String argument, String condition) {
/*  138 */       this.options = options;
/*  139 */       this.description = description;
/*  140 */       this.argument = argument;
/*  141 */       this.condition = condition;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Completer
/*      */     implements org.jline.reader.Completer
/*      */   {
/*      */     private final Completers.CompletionEnvironment environment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Completer(Completers.CompletionEnvironment environment) {
/*  162 */       this.environment = environment;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
/*  177 */       if (line.wordIndex() == 0) {
/*  178 */         completeCommand(candidates);
/*      */       } else {
/*  180 */         tryCompleteArguments(reader, line, candidates);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void tryCompleteArguments(LineReader reader, ParsedLine line, List<Candidate> candidates) {
/*  196 */       String command = line.words().get(0);
/*  197 */       String resolved = this.environment.resolveCommand(command);
/*  198 */       Map<String, List<Completers.CompletionData>> comp = this.environment.getCompletions();
/*  199 */       if (comp != null) {
/*  200 */         List<Completers.CompletionData> cmd = comp.get(resolved);
/*  201 */         if (cmd != null) {
/*  202 */           completeCommandArguments(reader, line, candidates, cmd);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void completeCommandArguments(LineReader reader, ParsedLine line, List<Candidate> candidates, List<Completers.CompletionData> completions) {
/*  221 */       for (Completers.CompletionData completion : completions) {
/*  222 */         boolean isOption = line.word().startsWith("-");
/*      */ 
/*      */ 
/*      */         
/*  226 */         String prevOption = (line.wordIndex() >= 2 && ((String)line.words().get(line.wordIndex() - 1)).startsWith("-")) ? line.words().get(line.wordIndex() - 1) : null;
/*  227 */         String key = UUID.randomUUID().toString();
/*  228 */         boolean conditionValue = true;
/*  229 */         if (completion.condition != null) {
/*  230 */           Object res = Boolean.FALSE;
/*      */           try {
/*  232 */             res = this.environment.evaluate(reader, line, completion.condition);
/*  233 */           } catch (Throwable throwable) {}
/*      */ 
/*      */           
/*  236 */           conditionValue = isTrue(res);
/*      */         } 
/*  238 */         if (conditionValue && isOption && completion.options != null) {
/*  239 */           for (String opt : completion.options)
/*  240 */             candidates.add(new Candidate(opt, opt, "options", completion.description, null, key, true));  continue;
/*      */         } 
/*  242 */         if (!isOption && prevOption != null && completion.argument != null && completion.options != null && completion.options
/*      */ 
/*      */           
/*  245 */           .contains(prevOption)) {
/*  246 */           Object res = null;
/*      */           try {
/*  248 */             res = this.environment.evaluate(reader, line, completion.argument);
/*  249 */           } catch (Throwable throwable) {}
/*      */ 
/*      */           
/*  252 */           if (res instanceof Candidate) {
/*  253 */             candidates.add((Candidate)res); continue;
/*  254 */           }  if (res instanceof String) {
/*  255 */             candidates.add(new Candidate((String)res, (String)res, null, null, null, null, true)); continue;
/*  256 */           }  if (res instanceof Collection) {
/*  257 */             for (Object s : res) {
/*  258 */               if (s instanceof Candidate) {
/*  259 */                 candidates.add((Candidate)s); continue;
/*  260 */               }  if (s instanceof String)
/*  261 */                 candidates.add(new Candidate((String)s, (String)s, null, null, null, null, true)); 
/*      */             }  continue;
/*      */           } 
/*  264 */           if (res != null && res.getClass().isArray())
/*  265 */             for (int i = 0, l = Array.getLength(res); i < l; i++) {
/*  266 */               Object s = Array.get(res, i);
/*  267 */               if (s instanceof Candidate) {
/*  268 */                 candidates.add((Candidate)s);
/*  269 */               } else if (s instanceof String) {
/*  270 */                 candidates.add(new Candidate((String)s, (String)s, null, null, null, null, true));
/*      */               } 
/*      */             }   continue;
/*      */         } 
/*  274 */         if (!isOption && completion.argument != null) {
/*  275 */           Object res = null;
/*      */           try {
/*  277 */             res = this.environment.evaluate(reader, line, completion.argument);
/*  278 */           } catch (Throwable throwable) {}
/*      */ 
/*      */           
/*  281 */           if (res instanceof Candidate) {
/*  282 */             candidates.add((Candidate)res); continue;
/*  283 */           }  if (res instanceof String) {
/*  284 */             candidates.add(new Candidate((String)res, (String)res, null, completion.description, null, null, true)); continue;
/*      */           } 
/*  286 */           if (res instanceof Collection) {
/*  287 */             for (Object s : res) {
/*  288 */               if (s instanceof Candidate) {
/*  289 */                 candidates.add((Candidate)s); continue;
/*  290 */               }  if (s instanceof String) {
/*  291 */                 candidates.add(new Candidate((String)s, (String)s, null, completion.description, null, null, true));
/*      */               }
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void completeCommand(List<Candidate> candidates) {
/*  310 */       Set<String> commands = this.environment.getCommands();
/*  311 */       for (String command : commands) {
/*  312 */         String name = this.environment.commandName(command);
/*  313 */         boolean resolved = command.equals(this.environment.resolveCommand(name));
/*  314 */         if (!name.startsWith("_")) {
/*  315 */           String desc = null;
/*  316 */           Map<String, List<Completers.CompletionData>> comp = this.environment.getCompletions();
/*  317 */           if (comp != null) {
/*  318 */             List<Completers.CompletionData> completions = comp.get(command);
/*  319 */             if (completions != null) {
/*  320 */               for (Completers.CompletionData completion : completions) {
/*  321 */                 if (completion.description != null && completion.options == null && completion.argument == null && completion.condition == null)
/*      */                 {
/*      */ 
/*      */                   
/*  325 */                   desc = completion.description;
/*      */                 }
/*      */               } 
/*      */             }
/*      */           } 
/*  330 */           String key = UUID.randomUUID().toString();
/*  331 */           if (desc != null) {
/*  332 */             candidates.add(new Candidate(command, command, null, desc, null, key, true));
/*  333 */             if (resolved)
/*  334 */               candidates.add(new Candidate(name, name, null, desc, null, key, true)); 
/*      */             continue;
/*      */           } 
/*  337 */           candidates.add(new Candidate(command, command, null, null, null, key, true));
/*  338 */           if (resolved) {
/*  339 */             candidates.add(new Candidate(name, name, null, null, null, key, true));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean isTrue(Object result) {
/*  356 */       if (result == null) return false; 
/*  357 */       if (result instanceof Boolean) return ((Boolean)result).booleanValue(); 
/*  358 */       if (result instanceof Number && 0 == ((Number)result).intValue()) {
/*  359 */         return false;
/*      */       }
/*  361 */       return (!"".equals(result) && !"0".equals(result));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class DirectoriesCompleter
/*      */     extends FileNameCompleter
/*      */   {
/*      */     private final Supplier<Path> currentDir;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DirectoriesCompleter(File currentDir) {
/*  383 */       this(currentDir.toPath());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DirectoriesCompleter(Path currentDir) {
/*  392 */       this.currentDir = (() -> currentDir);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DirectoriesCompleter(Supplier<Path> currentDir) {
/*  401 */       this.currentDir = currentDir;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Path getUserDir() {
/*  411 */       return this.currentDir.get();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean accept(Path path) {
/*  425 */       return (Files.isDirectory(path, new java.nio.file.LinkOption[0]) && super.accept(path));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FilesCompleter
/*      */     extends FileNameCompleter
/*      */   {
/*      */     private final Supplier<Path> currentDir;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final String namePattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FilesCompleter(File currentDir) {
/*  449 */       this(currentDir.toPath(), (String)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FilesCompleter(File currentDir, String namePattern) {
/*  459 */       this(currentDir.toPath(), namePattern);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FilesCompleter(Path currentDir) {
/*  468 */       this(currentDir, (String)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FilesCompleter(Path currentDir, String namePattern) {
/*  478 */       this.currentDir = (() -> currentDir);
/*  479 */       this.namePattern = compilePattern(namePattern);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FilesCompleter(Supplier<Path> currentDir) {
/*  488 */       this(currentDir, (String)null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FilesCompleter(Supplier<Path> currentDir, String namePattern) {
/*  498 */       this.currentDir = currentDir;
/*  499 */       this.namePattern = compilePattern(namePattern);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String compilePattern(String pattern) {
/*  513 */       if (pattern == null) {
/*  514 */         return null;
/*      */       }
/*  516 */       StringBuilder sb = new StringBuilder();
/*  517 */       for (int i = 0; i < pattern.length(); i++) {
/*  518 */         char ch = pattern.charAt(i);
/*  519 */         if (ch == '\\') {
/*  520 */           ch = pattern.charAt(++i);
/*  521 */           sb.append(ch);
/*  522 */         } else if (ch == '.') {
/*  523 */           sb.append('\\').append('.');
/*  524 */         } else if (ch == '*') {
/*  525 */           sb.append('.').append('*');
/*      */         } else {
/*  527 */           sb.append(ch);
/*      */         } 
/*      */       } 
/*  530 */       return sb.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected Path getUserDir() {
/*  540 */       return this.currentDir.get();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean accept(Path path) {
/*  554 */       if (this.namePattern == null || Files.isDirectory(path, new java.nio.file.LinkOption[0])) {
/*  555 */         return super.accept(path);
/*      */       }
/*  557 */       return (path.getFileName().toString().matches(this.namePattern) && super.accept(path));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FileNameCompleter
/*      */     implements org.jline.reader.Completer
/*      */   {
/*      */     public void complete(LineReader reader, ParsedLine commandLine, List<Candidate> candidates) {
/*  583 */       assert commandLine != null;
/*  584 */       assert candidates != null;
/*      */       
/*  586 */       String buffer = commandLine.word().substring(0, commandLine.wordCursor());
/*      */ 
/*      */ 
/*      */       
/*  590 */       String sep = getSeparator(reader.isSet(LineReader.Option.USE_FORWARD_SLASH));
/*  591 */       int lastSep = buffer.lastIndexOf(sep); try {
/*      */         Path current; String curBuf;
/*  593 */         if (lastSep >= 0) {
/*  594 */           curBuf = buffer.substring(0, lastSep + 1);
/*  595 */           if (curBuf.startsWith("~")) {
/*  596 */             if (curBuf.startsWith("~" + sep)) {
/*  597 */               current = getUserHome().resolve(curBuf.substring(2));
/*      */             } else {
/*  599 */               current = getUserHome().getParent().resolve(curBuf.substring(1));
/*      */             } 
/*      */           } else {
/*  602 */             current = getUserDir().resolve(curBuf);
/*      */           } 
/*      */         } else {
/*  605 */           curBuf = "";
/*  606 */           current = getUserDir();
/*      */         } 
/*  608 */         StyleResolver resolver = Styles.lsStyle(); 
/*  609 */         try { DirectoryStream<Path> directory = Files.newDirectoryStream(current, this::accept); 
/*  610 */           try { directory.forEach(p -> {
/*      */                   String value = curBuf + p.getFileName().toString();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   if (Files.isDirectory(p, new java.nio.file.LinkOption[0])) {
/*      */                     candidates.add(new Candidate(value + (reader.isSet(LineReader.Option.AUTO_PARAM_SLASH) ? sep : ""), getDisplay(reader.getTerminal(), p, resolver, sep), null, null, reader.isSet(LineReader.Option.AUTO_REMOVE_SLASH) ? sep : null, null, false));
/*      */                   } else {
/*      */                     candidates.add(new Candidate(value, getDisplay(reader.getTerminal(), p, resolver, sep), null, null, null, null, true));
/*      */                   } 
/*      */                 });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  632 */             if (directory != null) directory.close();  } catch (Throwable throwable) { if (directory != null) try { directory.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
/*      */       
/*      */       }
/*  635 */       catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean accept(Path path) {
/*      */       try {
/*  642 */         return !Files.isHidden(path);
/*  643 */       } catch (IOException e) {
/*  644 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*      */     protected Path getUserDir() {
/*  649 */       return Paths.get(System.getProperty("user.dir"), new String[0]);
/*      */     }
/*      */     
/*      */     protected Path getUserHome() {
/*  653 */       return Paths.get(System.getProperty("user.home"), new String[0]);
/*      */     }
/*      */     
/*      */     protected String getSeparator(boolean useForwardSlash) {
/*  657 */       return useForwardSlash ? "/" : getUserDir().getFileSystem().getSeparator();
/*      */     }
/*      */     
/*      */     protected String getDisplay(Terminal terminal, Path p, StyleResolver resolver, String separator) {
/*  661 */       AttributedStringBuilder sb = new AttributedStringBuilder();
/*  662 */       String name = p.getFileName().toString();
/*  663 */       int idx = name.lastIndexOf(".");
/*  664 */       String type = (idx != -1) ? (".*" + name.substring(idx)) : null;
/*  665 */       if (Files.isSymbolicLink(p)) {
/*  666 */         sb.styled(resolver.resolve(".ln"), name).append("@");
/*  667 */       } else if (Files.isDirectory(p, new java.nio.file.LinkOption[0])) {
/*  668 */         sb.styled(resolver.resolve(".di"), name).append(separator);
/*  669 */       } else if (Files.isExecutable(p) && !OSUtils.IS_WINDOWS) {
/*  670 */         sb.styled(resolver.resolve(".ex"), name).append("*");
/*  671 */       } else if (type != null && resolver.resolve(type).getStyle() != 0L) {
/*  672 */         sb.styled(resolver.resolve(type), name);
/*  673 */       } else if (Files.isRegularFile(p, new java.nio.file.LinkOption[0])) {
/*  674 */         sb.styled(resolver.resolve(".fi"), name);
/*      */       } else {
/*  676 */         sb.append(name);
/*      */       } 
/*  678 */       return sb.toAnsi(terminal);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TreeCompleter
/*      */     implements org.jline.reader.Completer
/*      */   {
/*  693 */     final Map<String, org.jline.reader.Completer> completers = new HashMap<>();
/*      */ 
/*      */ 
/*      */     
/*      */     final Completers.RegexCompleter completer;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeCompleter(Node... nodes) {
/*  703 */       this(Arrays.asList(nodes));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TreeCompleter(List<Node> nodes) {
/*  713 */       StringBuilder sb = new StringBuilder();
/*  714 */       addRoots(sb, nodes);
/*  715 */       Objects.requireNonNull(this.completers); this.completer = new Completers.RegexCompleter(sb.toString(), this.completers::get);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Node node(Object... objs) {
/*  734 */       org.jline.reader.Completer comp = null;
/*  735 */       List<Candidate> cands = new ArrayList<>();
/*  736 */       List<Node> nodes = new ArrayList<>();
/*  737 */       for (Object obj : objs) {
/*  738 */         if (obj instanceof String) {
/*  739 */           cands.add(new Candidate((String)obj));
/*  740 */         } else if (obj instanceof Candidate) {
/*  741 */           cands.add((Candidate)obj);
/*  742 */         } else if (obj instanceof Node) {
/*  743 */           nodes.add((Node)obj);
/*  744 */         } else if (obj instanceof org.jline.reader.Completer) {
/*  745 */           comp = (org.jline.reader.Completer)obj;
/*      */         } else {
/*  747 */           throw new IllegalArgumentException();
/*      */         } 
/*      */       } 
/*  750 */       if (comp != null) {
/*  751 */         if (!cands.isEmpty()) {
/*  752 */           throw new IllegalArgumentException();
/*      */         }
/*  754 */         return new Node(comp, nodes);
/*  755 */       }  if (!cands.isEmpty()) {
/*  756 */         return new Node((r, l, c) -> c.addAll(cands), nodes);
/*      */       }
/*  758 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void addRoots(StringBuilder sb, List<Node> nodes) {
/*  773 */       if (!nodes.isEmpty()) {
/*  774 */         sb.append(" ( ");
/*  775 */         boolean first = true;
/*  776 */         for (Node n : nodes) {
/*  777 */           if (first) {
/*  778 */             first = false;
/*      */           } else {
/*  780 */             sb.append(" | ");
/*      */           } 
/*  782 */           String name = "c" + this.completers.size();
/*  783 */           this.completers.put(name, n.completer);
/*  784 */           sb.append(name);
/*  785 */           addRoots(sb, n.nodes);
/*      */         } 
/*  787 */         sb.append(" ) ");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
/*  803 */       this.completer.complete(reader, line, candidates);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class Node
/*      */     {
/*      */       final org.jline.reader.Completer completer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       final List<Node> nodes;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Node(org.jline.reader.Completer completer, List<Node> nodes) {
/*  826 */         this.completer = completer;
/*  827 */         this.nodes = nodes;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class RegexCompleter
/*      */     implements org.jline.reader.Completer
/*      */   {
/*      */     private final NfaMatcher<String> matcher;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Function<String, org.jline.reader.Completer> completers;
/*      */ 
/*      */ 
/*      */     
/*  847 */     private final ThreadLocal<LineReader> reader = new ThreadLocal<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public RegexCompleter(String syntax, Function<String, org.jline.reader.Completer> completers) {
/*  856 */       this.matcher = new NfaMatcher<>(syntax, this::doMatch);
/*  857 */       this.completers = completers;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
/*  873 */       List<String> words = line.words().subList(0, line.wordIndex());
/*  874 */       this.reader.set(reader);
/*  875 */       Set<String> next = this.matcher.matchPartial(words);
/*  876 */       for (String n : next) {
/*  877 */         ((org.jline.reader.Completer)this.completers.apply(n)).complete(reader, new ArgumentLine(line.word(), line.wordCursor()), candidates);
/*      */       }
/*  879 */       this.reader.set(null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean doMatch(String arg, String name) {
/*  893 */       List<Candidate> candidates = new ArrayList<>();
/*  894 */       LineReader r = this.reader.get();
/*  895 */       boolean caseInsensitive = (r != null && r.isSet(LineReader.Option.CASE_INSENSITIVE));
/*  896 */       ((org.jline.reader.Completer)this.completers.apply(name)).complete(r, new ArgumentLine(arg, arg.length()), candidates);
/*  897 */       return candidates.stream()
/*  898 */         .anyMatch(c -> caseInsensitive ? c.value().equalsIgnoreCase(arg) : c.value().equals(arg));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static class ArgumentLine
/*      */       implements ParsedLine
/*      */     {
/*      */       private final String word;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       private final int cursor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public ArgumentLine(String word, int cursor) {
/*  923 */         this.word = word;
/*  924 */         this.cursor = cursor;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String word() {
/*  934 */         return this.word;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int wordCursor() {
/*  944 */         return this.cursor;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int wordIndex() {
/*  954 */         return 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public List<String> words() {
/*  964 */         return Collections.singletonList(this.word);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String line() {
/*  974 */         return this.word;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public int cursor() {
/*  984 */         return this.cursor;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class OptDesc
/*      */   {
/*      */     private String shortOption;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String longOption;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private String description;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private org.jline.reader.Completer valueCompleter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected static List<OptDesc> compile(Map<String, List<String>> optionValues, Collection<String> options) {
/* 1017 */       List<OptDesc> out = new ArrayList<>();
/* 1018 */       for (Map.Entry<String, List<String>> entry : optionValues.entrySet()) {
/* 1019 */         if (((String)entry.getKey()).startsWith("--")) {
/* 1020 */           out.add(new OptDesc(null, entry.getKey(), (org.jline.reader.Completer)new StringsCompleter(entry.getValue()))); continue;
/* 1021 */         }  if (((String)entry.getKey()).matches("-[a-zA-Z]")) {
/* 1022 */           out.add(new OptDesc(entry.getKey(), null, (org.jline.reader.Completer)new StringsCompleter(entry.getValue())));
/*      */         }
/*      */       } 
/* 1025 */       for (String o : options) {
/* 1026 */         if (o.startsWith("--")) {
/* 1027 */           out.add(new OptDesc(null, o)); continue;
/* 1028 */         }  if (o.matches("-[a-zA-Z]")) {
/* 1029 */           out.add(new OptDesc(o, null));
/*      */         }
/*      */       } 
/* 1032 */       return out;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptDesc(String shortOption, String longOption, String description, org.jline.reader.Completer valueCompleter) {
/* 1045 */       this.shortOption = shortOption;
/* 1046 */       this.longOption = longOption;
/* 1047 */       this.description = description;
/* 1048 */       this.valueCompleter = valueCompleter;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptDesc(String shortOption, String longOption, org.jline.reader.Completer valueCompleter) {
/* 1059 */       this(shortOption, longOption, null, valueCompleter);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptDesc(String shortOption, String longOption, String description) {
/* 1069 */       this(shortOption, longOption, description, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptDesc(String shortOption, String longOption) {
/* 1078 */       this(shortOption, longOption, null, null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected OptDesc() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setValueCompleter(org.jline.reader.Completer valueCompleter) {
/* 1092 */       this.valueCompleter = valueCompleter;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String longOption() {
/* 1101 */       return this.longOption;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String shortOption() {
/* 1110 */       return this.shortOption;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String description() {
/* 1119 */       return this.description;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean hasValue() {
/* 1128 */       return (this.valueCompleter != null && this.valueCompleter != NullCompleter.INSTANCE);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected org.jline.reader.Completer valueCompleter() {
/* 1137 */       return this.valueCompleter;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void completeOption(LineReader reader, ParsedLine commandLine, List<Candidate> candidates, boolean longOpt) {
/* 1153 */       if (!longOpt) {
/* 1154 */         if (this.shortOption != null) {
/* 1155 */           candidates.add(new Candidate(this.shortOption, this.shortOption, null, this.description, null, null, false));
/*      */         }
/* 1157 */       } else if (this.longOption != null) {
/* 1158 */         if (hasValue()) {
/* 1159 */           candidates.add(new Candidate(this.longOption + "=", this.longOption, null, this.description, null, null, false));
/*      */         } else {
/* 1161 */           candidates.add(new Candidate(this.longOption, this.longOption, null, this.description, null, null, true));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean completeValue(LineReader reader, ParsedLine commandLine, List<Candidate> candidates, String curBuf, String partialValue) {
/* 1185 */       boolean out = false;
/* 1186 */       List<Candidate> temp = new ArrayList<>();
/* 1187 */       ParsedLine pl = reader.getParser().parse(partialValue, partialValue.length());
/* 1188 */       this.valueCompleter.complete(reader, pl, temp);
/* 1189 */       for (Candidate c : temp) {
/* 1190 */         String v = c.value();
/* 1191 */         if (v.startsWith(partialValue)) {
/* 1192 */           out = true;
/* 1193 */           String val = c.value();
/* 1194 */           if (this.valueCompleter instanceof Completers.FileNameCompleter) {
/* 1195 */             Completers.FileNameCompleter cc = (Completers.FileNameCompleter)this.valueCompleter;
/* 1196 */             String sep = cc.getSeparator(reader.isSet(LineReader.Option.USE_FORWARD_SLASH));
/* 1197 */             val = cc.getDisplay(reader.getTerminal(), Paths.get(c.value(), new String[0]), Styles.lsStyle(), sep);
/*      */           } 
/* 1199 */           candidates.add(new Candidate(curBuf + v, val, null, null, null, null, c.complete()));
/*      */         } 
/*      */       } 
/* 1202 */       return out;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean match(String option) {
/* 1212 */       return ((this.shortOption != null && this.shortOption.equals(option)) || (this.longOption != null && this.longOption
/* 1213 */         .equals(option)));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected boolean startsWith(String option) {
/* 1223 */       return ((this.shortOption != null && this.shortOption.startsWith(option)) || (this.longOption != null && this.longOption
/* 1224 */         .startsWith(option)));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class OptionCompleter
/*      */     implements org.jline.reader.Completer
/*      */   {
/*      */     private Function<String, Collection<Completers.OptDesc>> commandOptions;
/*      */ 
/*      */ 
/*      */     
/*      */     private Collection<Completers.OptDesc> options;
/*      */ 
/*      */ 
/*      */     
/* 1242 */     private List<org.jline.reader.Completer> argsCompleters = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int startPos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptionCompleter(org.jline.reader.Completer completer, Function<String, Collection<Completers.OptDesc>> commandOptions, int startPos) {
/* 1256 */       this.startPos = startPos;
/* 1257 */       this.commandOptions = commandOptions;
/* 1258 */       this.argsCompleters.add(completer);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptionCompleter(List<org.jline.reader.Completer> completers, Function<String, Collection<Completers.OptDesc>> commandOptions, int startPos) {
/* 1271 */       this.startPos = startPos;
/* 1272 */       this.commandOptions = commandOptions;
/* 1273 */       this.argsCompleters = new ArrayList<>(completers);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptionCompleter(List<org.jline.reader.Completer> completers, Map<String, List<String>> optionValues, Collection<String> options, int startPos) {
/* 1288 */       this(optionValues, options, startPos);
/* 1289 */       this.argsCompleters = new ArrayList<>(completers);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptionCompleter(org.jline.reader.Completer completer, Map<String, List<String>> optionValues, Collection<String> options, int startPos) {
/* 1304 */       this(optionValues, options, startPos);
/* 1305 */       this.argsCompleters.add(completer);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptionCompleter(Map<String, List<String>> optionValues, Collection<String> options, int startPos) {
/* 1315 */       this(Completers.OptDesc.compile(optionValues, options), startPos);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptionCompleter(org.jline.reader.Completer completer, Collection<Completers.OptDesc> options, int startPos) {
/* 1325 */       this(options, startPos);
/* 1326 */       this.argsCompleters.add(completer);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptionCompleter(List<org.jline.reader.Completer> completers, Collection<Completers.OptDesc> options, int startPos) {
/* 1336 */       this(options, startPos);
/* 1337 */       this.argsCompleters = new ArrayList<>(completers);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public OptionCompleter(Collection<Completers.OptDesc> options, int startPos) {
/* 1346 */       this.options = options;
/* 1347 */       this.startPos = startPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setStartPos(int startPos) {
/* 1356 */       this.startPos = startPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void complete(LineReader reader, ParsedLine commandLine, List<Candidate> candidates) {
/* 1375 */       assert commandLine != null;
/* 1376 */       assert candidates != null;
/* 1377 */       List<String> words = commandLine.words();
/* 1378 */       String buffer = commandLine.word().substring(0, commandLine.wordCursor());
/* 1379 */       if (this.startPos >= words.size()) {
/* 1380 */         candidates.add(new Candidate(buffer, buffer, null, null, null, null, true));
/*      */         return;
/*      */       } 
/* 1383 */       String command = reader.getParser().getCommand(words.get(this.startPos - 1));
/* 1384 */       if (buffer.startsWith("-")) {
/* 1385 */         boolean addbuff = true;
/* 1386 */         boolean valueCandidates = false;
/* 1387 */         boolean longOption = buffer.startsWith("--");
/* 1388 */         int eq = buffer.matches("-[a-zA-Z][a-zA-Z0-9]+") ? 2 : buffer.indexOf('=');
/* 1389 */         if (eq < 0) {
/* 1390 */           List<String> usedOptions = new ArrayList<>();
/* 1391 */           for (int i = this.startPos; i < words.size(); i++) {
/* 1392 */             if (((String)words.get(i)).startsWith("-")) {
/* 1393 */               String w = words.get(i);
/* 1394 */               int ind = w.indexOf('=');
/* 1395 */               if (ind < 0) {
/* 1396 */                 usedOptions.add(w);
/*      */               } else {
/* 1398 */                 usedOptions.add(w.substring(0, ind));
/*      */               } 
/*      */             } 
/*      */           } 
/* 1402 */           for (Completers.OptDesc o : (this.commandOptions == null) ? this.options : this.commandOptions.apply(command)) {
/* 1403 */             if (usedOptions.contains(o.shortOption()) || usedOptions.contains(o.longOption())) {
/*      */               continue;
/*      */             }
/* 1406 */             if (o.startsWith(buffer)) {
/* 1407 */               addbuff = false;
/*      */             }
/* 1409 */             o.completeOption(reader, commandLine, candidates, longOption);
/*      */           } 
/*      */         } else {
/* 1412 */           addbuff = false;
/* 1413 */           int nb = buffer.contains("=") ? 1 : 0;
/* 1414 */           String value = buffer.substring(eq + nb);
/* 1415 */           String curBuf = buffer.substring(0, eq + nb);
/* 1416 */           String opt = buffer.substring(0, eq);
/* 1417 */           Completers.OptDesc option = findOptDesc(command, opt);
/* 1418 */           if (option.hasValue()) {
/* 1419 */             valueCandidates = option.completeValue(reader, commandLine, candidates, curBuf, value);
/*      */           }
/*      */         } 
/* 1422 */         if ((buffer.contains("=") && !buffer.endsWith("=") && !valueCandidates) || addbuff) {
/* 1423 */           candidates.add(new Candidate(buffer, buffer, null, null, null, null, true));
/*      */         }
/* 1425 */       } else if (words.size() > 1 && shortOptionValueCompleter(command, words.get(words.size() - 2)) != null) {
/* 1426 */         shortOptionValueCompleter(command, words.get(words.size() - 2))
/* 1427 */           .complete(reader, commandLine, candidates);
/* 1428 */       } else if (words.size() > 1 && longOptionValueCompleter(command, words.get(words.size() - 2)) != null) {
/* 1429 */         longOptionValueCompleter(command, words.get(words.size() - 2))
/* 1430 */           .complete(reader, commandLine, candidates);
/* 1431 */       } else if (!this.argsCompleters.isEmpty()) {
/* 1432 */         int args = -1;
/* 1433 */         for (int i = this.startPos; i < words.size(); i++) {
/* 1434 */           if (!((String)words.get(i)).startsWith("-") && 
/* 1435 */             i > 0 && 
/* 1436 */             shortOptionValueCompleter(command, words.get(i - 1)) == null && 
/* 1437 */             longOptionValueCompleter(command, words.get(i - 1)) == null) {
/* 1438 */             args++;
/*      */           }
/*      */         } 
/*      */         
/* 1442 */         if (args == -1) {
/* 1443 */           candidates.add(new Candidate(buffer, buffer, null, null, null, null, true));
/* 1444 */         } else if (args < this.argsCompleters.size()) {
/* 1445 */           ((org.jline.reader.Completer)this.argsCompleters.get(args)).complete(reader, commandLine, candidates);
/*      */         } else {
/* 1447 */           ((org.jline.reader.Completer)this.argsCompleters.get(this.argsCompleters.size() - 1)).complete(reader, commandLine, candidates);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private org.jline.reader.Completer longOptionValueCompleter(String command, String opt) {
/* 1460 */       if (!opt.matches("--[a-zA-Z]+")) {
/* 1461 */         return null;
/*      */       }
/* 1463 */       Collection<Completers.OptDesc> optDescs = (this.commandOptions == null) ? this.options : this.commandOptions.apply(command);
/* 1464 */       Completers.OptDesc option = findOptDesc(optDescs, opt);
/* 1465 */       return option.hasValue() ? option.valueCompleter() : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private org.jline.reader.Completer shortOptionValueCompleter(String command, String opt) {
/* 1479 */       if (!opt.matches("-[a-zA-Z]+")) {
/* 1480 */         return null;
/*      */       }
/* 1482 */       org.jline.reader.Completer out = null;
/* 1483 */       Collection<Completers.OptDesc> optDescs = (this.commandOptions == null) ? this.options : this.commandOptions.apply(command);
/* 1484 */       if (opt.length() == 2) {
/* 1485 */         out = findOptDesc(optDescs, opt).valueCompleter();
/* 1486 */       } else if (opt.length() > 2) {
/* 1487 */         for (int i = 1; i < opt.length(); i++) {
/* 1488 */           Completers.OptDesc o = findOptDesc(optDescs, "-" + opt.charAt(i));
/* 1489 */           if (o.shortOption() == null)
/* 1490 */             return null; 
/* 1491 */           if (out == null) {
/* 1492 */             out = o.valueCompleter();
/*      */           }
/*      */         } 
/*      */       } 
/* 1496 */       return out;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Completers.OptDesc findOptDesc(String command, String opt) {
/* 1507 */       return findOptDesc((this.commandOptions == null) ? this.options : this.commandOptions.apply(command), opt);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Completers.OptDesc findOptDesc(Collection<Completers.OptDesc> optDescs, String opt) {
/* 1518 */       for (Completers.OptDesc o : optDescs) {
/* 1519 */         if (o.match(opt)) {
/* 1520 */           return o;
/*      */         }
/*      */       } 
/* 1523 */       return new Completers.OptDesc();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class AnyCompleter
/*      */     implements org.jline.reader.Completer
/*      */   {
/* 1536 */     public static final AnyCompleter INSTANCE = new AnyCompleter();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void complete(LineReader reader, ParsedLine commandLine, List<Candidate> candidates) {
/* 1547 */       assert commandLine != null;
/* 1548 */       assert candidates != null;
/* 1549 */       String buffer = commandLine.word().substring(0, commandLine.wordCursor());
/* 1550 */       candidates.add(new Candidate(AttributedString.stripAnsi(buffer), buffer, null, null, null, null, true));
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\Completers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */