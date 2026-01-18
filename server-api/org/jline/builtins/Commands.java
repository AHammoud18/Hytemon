/*      */ package org.jline.builtins;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.nio.file.FileSystems;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.PathMatcher;
/*      */ import java.nio.file.Paths;
/*      */ import java.time.LocalDateTime;
/*      */ import java.time.LocalTime;
/*      */ import java.time.ZoneId;
/*      */ import java.time.format.DateTimeFormatter;
/*      */ import java.time.temporal.ChronoUnit;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.regex.Pattern;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import org.jline.keymap.KeyMap;
/*      */ import org.jline.reader.Binding;
/*      */ import org.jline.reader.Highlighter;
/*      */ import org.jline.reader.History;
/*      */ import org.jline.reader.LineReader;
/*      */ import org.jline.reader.Macro;
/*      */ import org.jline.reader.Reference;
/*      */ import org.jline.reader.Widget;
/*      */ import org.jline.terminal.Terminal;
/*      */ import org.jline.utils.AttributedStringBuilder;
/*      */ import org.jline.utils.AttributedStyle;
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
/*      */ public class Commands
/*      */ {
/*      */   public static void tmux(Terminal terminal, PrintStream out, PrintStream err, Supplier<Object> getter, Consumer<Object> setter, Consumer<Terminal> runner, String[] argv) throws Exception {
/*   98 */     String[] usage = { "tmux -  terminal multiplexer", "Usage: tmux [command]", "  -? --help                    Show help" };
/*      */ 
/*      */     
/*  101 */     Options opt = Options.compile(usage).parse((Object[])argv);
/*  102 */     if (opt.isSet("help")) {
/*  103 */       throw new Options.HelpException(opt.usage());
/*      */     }
/*      */     
/*  106 */     if (argv.length == 0) {
/*  107 */       Object instance = getter.get();
/*  108 */       if (instance != null) {
/*  109 */         err.println("tmux: can't run tmux inside itself");
/*      */       } else {
/*  111 */         Tmux tmux = new Tmux(terminal, err, runner);
/*  112 */         setter.accept(tmux);
/*      */         try {
/*  114 */           tmux.run();
/*      */         } finally {
/*  116 */           setter.accept(null);
/*      */         } 
/*      */       } 
/*      */     } else {
/*  120 */       Object instance = getter.get();
/*  121 */       if (instance != null) {
/*  122 */         ((Tmux)instance).execute(out, err, Arrays.asList(argv));
/*      */       } else {
/*  124 */         err.println("tmux: no instance running");
/*      */       } 
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
/*      */   public static void nano(Terminal terminal, PrintStream out, PrintStream err, Path currentDir, String[] argv) throws Exception {
/*  143 */     nano(terminal, out, err, currentDir, argv, null);
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
/*      */   public static void nano(Terminal terminal, PrintStream out, PrintStream err, Path currentDir, String[] argv, ConfigurationPath configPath) throws Exception {
/*  165 */     Options opt = Options.compile(Nano.usage()).parse((Object[])argv);
/*  166 */     if (opt.isSet("help")) {
/*  167 */       throw new Options.HelpException(opt.usage());
/*      */     }
/*  169 */     Nano edit = new Nano(terminal, currentDir, opt, configPath);
/*  170 */     edit.open(opt.args());
/*  171 */     edit.run();
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
/*      */   public static void less(Terminal terminal, InputStream in, PrintStream out, PrintStream err, Path currentDir, Object[] argv) throws Exception {
/*  190 */     less(terminal, in, out, err, currentDir, argv, null);
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
/*      */ 
/*      */   
/*      */   public static void less(Terminal terminal, InputStream in, PrintStream out, PrintStream err, Path currentDir, Object[] argv, ConfigurationPath configPath) throws Exception {
/*  214 */     Options opt = Options.compile(Less.usage()).parse(argv);
/*  215 */     if (opt.isSet("help")) {
/*  216 */       throw new Options.HelpException(opt.usage());
/*      */     }
/*  218 */     Less less = new Less(terminal, currentDir, opt, configPath);
/*  219 */     List<Source> sources = new ArrayList<>();
/*  220 */     if (opt.argObjects().isEmpty()) {
/*  221 */       opt.argObjects().add("-");
/*      */     }
/*      */     
/*  224 */     for (Object o : opt.argObjects()) {
/*  225 */       if (o instanceof String) {
/*  226 */         String arg = (String)o;
/*  227 */         arg = arg.startsWith("~") ? arg.replace("~", System.getProperty("user.home")) : arg;
/*  228 */         if ("-".equals(arg)) {
/*  229 */           sources.add(new Source.StdInSource(in)); continue;
/*  230 */         }  if (arg.contains("*") || arg.contains("?")) {
/*  231 */           for (Path p : findFiles(currentDir, arg))
/*  232 */             sources.add(new Source.URLSource(p.toUri().toURL(), p.toString())); 
/*      */           continue;
/*      */         } 
/*  235 */         sources.add(new Source.URLSource(currentDir.resolve(arg).toUri().toURL(), arg)); continue;
/*      */       } 
/*  237 */       if (o instanceof Source) {
/*  238 */         sources.add((Source)o); continue;
/*      */       } 
/*  240 */       ByteArrayInputStream bais = null;
/*  241 */       if (o instanceof String[]) {
/*      */         
/*  243 */         bais = new ByteArrayInputStream(String.join("\n", (CharSequence[])o).getBytes());
/*  244 */       } else if (o instanceof ByteArrayInputStream) {
/*  245 */         bais = (ByteArrayInputStream)o;
/*  246 */       } else if (o instanceof byte[]) {
/*  247 */         bais = new ByteArrayInputStream((byte[])o);
/*      */       } 
/*  249 */       if (bais != null) {
/*  250 */         sources.add(new Source.InputStreamSource(bais, true, "Less"));
/*      */       }
/*      */     } 
/*      */     
/*  254 */     less.run(sources);
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
/*      */   protected static List<Path> findFiles(Path root, String files) throws IOException {
/*  266 */     files = files.startsWith("~") ? files.replace("~", System.getProperty("user.home")) : files;
/*  267 */     String regex = files;
/*  268 */     Path searchRoot = Paths.get("/", new String[0]);
/*  269 */     if ((new File(files)).isAbsolute()) {
/*  270 */       regex = regex.replaceAll("\\\\", "/").replaceAll("//", "/");
/*  271 */       if (regex.contains("/")) {
/*  272 */         String sr = regex.substring(0, regex.lastIndexOf("/") + 1);
/*  273 */         while (sr.contains("*") || sr.contains("?")) {
/*  274 */           sr = sr.substring(0, sr.lastIndexOf("/"));
/*      */         }
/*  276 */         searchRoot = Paths.get(sr + "/", new String[0]);
/*      */       } 
/*      */     } else {
/*  279 */       regex = ((root.toString().length() == 0) ? "" : (root + "/")) + files;
/*  280 */       regex = regex.replaceAll("\\\\", "/").replaceAll("//", "/");
/*  281 */       searchRoot = root;
/*      */     } 
/*  283 */     PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + regex);
/*  284 */     Stream<Path> pathStream = Files.walk(searchRoot, new java.nio.file.FileVisitOption[0]); try {
/*  285 */       Objects.requireNonNull(pathMatcher); List<Path> list = (List)pathStream.filter(pathMatcher::matches).collect(Collectors.toList());
/*  286 */       if (pathStream != null) pathStream.close();
/*      */       
/*      */       return list;
/*      */     } catch (Throwable throwable) {
/*      */       if (pathStream != null)
/*      */         try {
/*      */           pathStream.close();
/*      */         } catch (Throwable throwable1) {
/*      */           throwable.addSuppressed(throwable1);
/*      */         }  
/*      */       throw throwable;
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void history(LineReader reader, PrintStream out, PrintStream err, Path currentDir, String[] argv) throws Exception {
/*  301 */     String[] usage = { "history -  list history of commands", "Usage: history [-dnrfEie] [-m match] [first] [last]", "       history -ARWI [filename]", "       history -s [old=new] [command]", "       history --clear", "       history --save", "  -? --help                       Displays command help", "     --clear                      Clear history", "     --save                       Save history", "  -m match                        If option -m is present the first argument is taken as a pattern", "                                  and only the history events matching the pattern will be shown", "  -d                              Print timestamps for each event", "  -f                              Print full time date stamps in the US format", "  -E                              Print full time date stamps in the European format", "  -i                              Print full time date stamps in ISO8601 format", "  -n                              Suppresses command numbers", "  -r                              Reverses the order of the commands", "  -A                              Appends the history out to the given file", "  -R                              Reads the history from the given file", "  -W                              Writes the history out to the given file", "  -I                              If added to -R, only the events that are not contained within the internal list are added", "                                  If added to -W or -A, only the events that are new since the last incremental operation", "                                  to the file are added", "  [first] [last]                  These optional arguments may be specified as a number or as a string. A negative number", "                                  is used as an offset to the current history event number. A string specifies the most", "                                  recent event beginning with the given string.", "  -e                              Uses the nano editor to edit the commands before executing", "  -s                              Re-executes the command without invoking an editor" };
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
/*  331 */     Options opt = Options.compile(usage).parse((Object[])argv);
/*      */     
/*  333 */     if (opt.isSet("help")) {
/*  334 */       throw new Options.HelpException(opt.usage());
/*      */     }
/*  336 */     History history = reader.getHistory();
/*  337 */     boolean done = true;
/*  338 */     boolean increment = opt.isSet("I");
/*  339 */     if (opt.isSet("clear")) {
/*  340 */       history.purge();
/*  341 */     } else if (opt.isSet("save")) {
/*  342 */       history.save();
/*  343 */     } else if (opt.isSet("A")) {
/*  344 */       Path file = !opt.args().isEmpty() ? currentDir.resolve(opt.args().get(0)) : null;
/*  345 */       history.append(file, increment);
/*  346 */     } else if (opt.isSet("R")) {
/*  347 */       Path file = !opt.args().isEmpty() ? currentDir.resolve(opt.args().get(0)) : null;
/*  348 */       history.read(file, increment);
/*  349 */     } else if (opt.isSet("W")) {
/*  350 */       Path file = !opt.args().isEmpty() ? currentDir.resolve(opt.args().get(0)) : null;
/*  351 */       history.write(file, increment);
/*      */     } else {
/*  353 */       done = false;
/*      */     } 
/*  355 */     if (done) {
/*      */       return;
/*      */     }
/*  358 */     ReExecute execute = new ReExecute(history, opt);
/*  359 */     int argId = execute.getArgId();
/*      */     
/*  361 */     Pattern pattern = null;
/*  362 */     if (opt.isSet("m") && opt.args().size() > argId) {
/*  363 */       StringBuilder sb = new StringBuilder();
/*  364 */       char prev = '0';
/*  365 */       for (char c : ((String)opt.args().get(argId++)).toCharArray()) {
/*  366 */         if (c == '*' && prev != '\\' && prev != '.') {
/*  367 */           sb.append('.');
/*      */         }
/*  369 */         sb.append(c);
/*  370 */         prev = c;
/*      */       } 
/*  372 */       pattern = Pattern.compile(sb.toString(), 32);
/*      */     } 
/*  374 */     boolean reverse = (opt.isSet("r") || (opt.isSet("s") && opt.args().size() <= argId));
/*      */ 
/*      */     
/*  377 */     int firstId = (opt.args().size() > argId) ? retrieveHistoryId(history, opt.args().get(argId++)) : -17;
/*      */ 
/*      */     
/*  380 */     int lastId = (opt.args().size() > argId) ? retrieveHistoryId(history, opt.args().get(argId++)) : -1;
/*  381 */     firstId = historyId(firstId, history.first(), history.last());
/*  382 */     lastId = historyId(lastId, history.first(), history.last());
/*  383 */     if (firstId > lastId) {
/*  384 */       int tmpId = firstId;
/*  385 */       firstId = lastId;
/*  386 */       lastId = tmpId;
/*  387 */       reverse = !reverse;
/*      */     } 
/*  389 */     int tot = lastId - firstId + 1;
/*  390 */     int listed = 0;
/*  391 */     Highlighter highlighter = reader.getHighlighter();
/*  392 */     Iterator<History.Entry> iter = null;
/*  393 */     if (reverse) {
/*  394 */       iter = history.reverseIterator(lastId);
/*      */     } else {
/*  396 */       iter = history.iterator(firstId);
/*      */     } 
/*      */     
/*  399 */     while (iter.hasNext() && listed < tot) {
/*  400 */       History.Entry entry = iter.next();
/*  401 */       listed++;
/*  402 */       if (pattern != null && !pattern.matcher(entry.line()).matches()) {
/*      */         continue;
/*      */       }
/*  405 */       if (execute.isExecute()) {
/*  406 */         if (execute.isEdit()) {
/*  407 */           execute.addCommandInFile(entry.line()); continue;
/*      */         } 
/*  409 */         execute.addCommandInBuffer(reader, entry.line());
/*      */         
/*      */         break;
/*      */       } 
/*  413 */       AttributedStringBuilder sb = new AttributedStringBuilder();
/*  414 */       if (!opt.isSet("n")) {
/*  415 */         sb.append("  ");
/*  416 */         sb.styled(AttributedStyle::bold, String.format("%3d", new Object[] { Integer.valueOf(entry.index()) }));
/*      */       } 
/*  418 */       if (opt.isSet("d") || opt.isSet("f") || opt.isSet("E") || opt.isSet("i")) {
/*  419 */         sb.append("  ");
/*  420 */         if (opt.isSet("d")) {
/*      */           
/*  422 */           LocalTime lt = LocalTime.from(entry.time().atZone(ZoneId.systemDefault())).truncatedTo(ChronoUnit.SECONDS);
/*  423 */           DateTimeFormatter.ISO_LOCAL_TIME.formatTo(lt, (Appendable)sb);
/*      */         } else {
/*  425 */           LocalDateTime lt = LocalDateTime.from(entry
/*  426 */               .time().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.MINUTES));
/*  427 */           String format = "yyyy-MM-dd hh:mm";
/*  428 */           if (opt.isSet("f")) {
/*  429 */             format = "MM/dd/yy hh:mm";
/*  430 */           } else if (opt.isSet("E")) {
/*  431 */             format = "dd.MM.yyyy hh:mm";
/*      */           } 
/*  433 */           DateTimeFormatter.ofPattern(format).formatTo(lt, (Appendable)sb);
/*      */         } 
/*      */       } 
/*  436 */       sb.append("  ");
/*  437 */       sb.append(highlighter.highlight(reader, entry.line()));
/*  438 */       out.println(sb.toAnsi(reader.getTerminal()));
/*      */     } 
/*      */     
/*  441 */     execute.editCommandsAndClose(reader);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class ReExecute
/*      */   {
/*      */     private final boolean execute;
/*      */     
/*      */     private final boolean edit;
/*      */     private String oldParam;
/*      */     private String newParam;
/*      */     private FileWriter cmdWriter;
/*      */     private File cmdFile;
/*  454 */     private int argId = 0;
/*      */     
/*      */     public ReExecute(History history, Options opt) throws IOException {
/*  457 */       this.execute = (opt.isSet("e") || opt.isSet("s"));
/*  458 */       this.edit = opt.isSet("e");
/*  459 */       if (this.execute) {
/*  460 */         Iterator<History.Entry> iter = history.reverseIterator(history.last());
/*  461 */         if (iter.hasNext()) {
/*  462 */           iter.next();
/*  463 */           iter.remove();
/*      */         } 
/*  465 */         if (this.edit) {
/*  466 */           this.cmdFile = File.createTempFile("jline-history-", null);
/*  467 */           this.cmdWriter = new FileWriter(this.cmdFile);
/*  468 */         } else if (!opt.args().isEmpty()) {
/*  469 */           String[] s = ((String)opt.args().get(this.argId)).split("=");
/*  470 */           if (s.length == 2) {
/*  471 */             this.argId++;
/*  472 */             this.oldParam = s[0];
/*  473 */             this.newParam = s[1];
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int getArgId() {
/*  480 */       return this.argId;
/*      */     }
/*      */     
/*      */     public boolean isEdit() {
/*  484 */       return this.edit;
/*      */     }
/*      */     
/*      */     public boolean isExecute() {
/*  488 */       return this.execute;
/*      */     }
/*      */     
/*      */     public void addCommandInFile(String command) throws IOException {
/*  492 */       this.cmdWriter.write(command + "\n");
/*      */     }
/*      */     
/*      */     public void addCommandInBuffer(LineReader reader, String command) {
/*  496 */       reader.addCommandsInBuffer(Arrays.asList(new String[] { replaceParam(command) }));
/*      */     }
/*      */     
/*      */     private String replaceParam(String command) {
/*  500 */       String out = command;
/*  501 */       if (this.oldParam != null && this.newParam != null) {
/*  502 */         out = command.replaceAll(this.oldParam, this.newParam);
/*      */       }
/*  504 */       return out;
/*      */     }
/*      */     
/*      */     public void editCommandsAndClose(LineReader reader) throws Exception {
/*  508 */       if (this.edit) {
/*  509 */         this.cmdWriter.close();
/*      */         try {
/*  511 */           reader.editAndAddInBuffer(this.cmdFile);
/*      */         } finally {
/*  513 */           this.cmdFile.delete();
/*      */         } 
/*      */       } 
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
/*      */   private static int historyId(int id, int minId, int maxId) {
/*  528 */     int out = id;
/*  529 */     if (id < 0) {
/*  530 */       out = maxId + id + 1;
/*      */     }
/*  532 */     if (out < minId) {
/*  533 */       out = minId;
/*  534 */     } else if (out > maxId) {
/*  535 */       out = maxId;
/*      */     } 
/*  537 */     return out;
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
/*      */   private static int retrieveHistoryId(History history, String s) throws IllegalArgumentException {
/*      */     try {
/*  551 */       return Integer.parseInt(s);
/*  552 */     } catch (NumberFormatException ex) {
/*  553 */       Iterator<History.Entry> iter = history.iterator();
/*  554 */       while (iter.hasNext()) {
/*  555 */         History.Entry entry = iter.next();
/*  556 */         if (entry.line().startsWith(s)) {
/*  557 */           return entry.index();
/*      */         }
/*      */       } 
/*  560 */       throw new IllegalArgumentException("history: event not found: " + s);
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
/*      */   public static void complete(LineReader reader, PrintStream out, PrintStream err, Map<String, List<Completers.CompletionData>> completions, String[] argv) throws Options.HelpException {
/*  581 */     String[] usage = { "complete -  edit command specific tab-completions", "Usage: complete", "  -? --help                       Displays command help", "  -c --command=COMMAND            Command to add completion to", "  -d --description=DESCRIPTION    Description of this completions", "  -e --erase                      Erase the completions", "  -s --short-option=SHORT_OPTION  Posix-style option to complete", "  -l --long-option=LONG_OPTION    GNU-style option to complete", "  -a --argument=ARGUMENTS         A list of possible arguments", "  -n --condition=CONDITION        The completion should only be used if the", "                                  specified command has a zero exit status" };
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
/*  595 */     Options opt = Options.compile(usage).parse((Object[])argv);
/*      */     
/*  597 */     if (opt.isSet("help")) {
/*  598 */       throw new Options.HelpException(opt.usage());
/*      */     }
/*      */     
/*  601 */     String command = opt.get("command");
/*      */     
/*  603 */     if (opt.isSet("erase")) {
/*  604 */       completions.remove(command);
/*      */       
/*      */       return;
/*      */     } 
/*  608 */     List<Completers.CompletionData> cmdCompletions = completions.computeIfAbsent(command, s -> new ArrayList());
/*  609 */     List<String> options = null;
/*  610 */     if (opt.isSet("short-option")) {
/*  611 */       for (String op : opt.getList("short-option")) {
/*  612 */         if (options == null) {
/*  613 */           options = new ArrayList<>();
/*      */         }
/*  615 */         options.add("-" + op);
/*      */       } 
/*      */     }
/*  618 */     if (opt.isSet("long-option")) {
/*  619 */       for (String op : opt.getList("long-option")) {
/*  620 */         if (options == null) {
/*  621 */           options = new ArrayList<>();
/*      */         }
/*  623 */         options.add("--" + op);
/*      */       } 
/*      */     }
/*  626 */     String description = opt.isSet("description") ? opt.get("description") : null;
/*  627 */     String argument = opt.isSet("argument") ? opt.get("argument") : null;
/*  628 */     String condition = opt.isSet("condition") ? opt.get("condition") : null;
/*  629 */     cmdCompletions.add(new Completers.CompletionData(options, description, argument, condition));
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
/*      */   public static void widget(LineReader reader, PrintStream out, PrintStream err, Function<String, Widget> widgetCreator, String[] argv) throws Exception {
/*  645 */     String[] usage = { "widget -  manipulate widgets", "Usage: widget -N new-widget [function-name]", "       widget -D widget ...", "       widget -A old-widget new-widget", "       widget -U string ...", "       widget -l [options]", "  -? --help                       Displays command help", "  -A                              Create alias to widget", "  -N                              Create new widget", "  -D                              Delete widgets", "  -U                              Push characters to the stack", "  -l                              List user-defined widgets", "  -a                              With -l, list all widgets" };
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
/*  660 */     Options opt = Options.compile(usage).parse((Object[])argv);
/*  661 */     if (opt.isSet("help")) {
/*  662 */       throw new Options.HelpException(opt.usage());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  669 */     int actions = (opt.isSet("N") ? 1 : 0) + (opt.isSet("D") ? 1 : 0) + (opt.isSet("U") ? 1 : 0) + (opt.isSet("l") ? 1 : 0) + (opt.isSet("A") ? 1 : 0);
/*  670 */     if (actions > 1) {
/*  671 */       err.println("widget: incompatible operation selection options");
/*      */       return;
/*      */     } 
/*  674 */     if (opt.isSet("l")) {
/*  675 */       TreeSet<String> ws = new TreeSet<>(reader.getWidgets().keySet());
/*  676 */       if (opt.isSet("a")) {
/*  677 */         Set<String> temp = new HashSet<>(ws);
/*  678 */         for (String s : temp) {
/*  679 */           ws.add(((Widget)reader.getWidgets().get(s)).toString());
/*      */         }
/*      */       } 
/*  682 */       for (String s : ws) {
/*  683 */         if (opt.isSet("a")) {
/*  684 */           out.println(s); continue;
/*  685 */         }  if (!((Widget)reader.getWidgets().get(s)).toString().startsWith(".")) {
/*  686 */           out.println(s + " (" + reader.getWidgets().get(s) + ")");
/*      */         }
/*      */       } 
/*  689 */     } else if (opt.isSet("N")) {
/*  690 */       if (opt.args().size() < 1) {
/*  691 */         err.println("widget: not enough arguments for -N");
/*      */         return;
/*      */       } 
/*  694 */       if (opt.args().size() > 2) {
/*  695 */         err.println("widget: too many arguments for -N");
/*      */         return;
/*      */       } 
/*  698 */       String name = opt.args().get(0);
/*  699 */       String func = (opt.args().size() == 2) ? opt.args().get(1) : name;
/*  700 */       reader.getWidgets().put(name, widgetCreator.apply(func));
/*  701 */     } else if (opt.isSet("D")) {
/*  702 */       for (String name : opt.args()) {
/*  703 */         reader.getWidgets().remove(name);
/*      */       }
/*  705 */     } else if (opt.isSet("A")) {
/*  706 */       if (opt.args().size() < 2) {
/*  707 */         err.println("widget: not enough arguments for -A");
/*      */         return;
/*      */       } 
/*  710 */       if (opt.args().size() > 2) {
/*  711 */         err.println("widget: too many arguments for -A");
/*      */         return;
/*      */       } 
/*  714 */       Widget org = null;
/*  715 */       if (((String)opt.args().get(0)).startsWith(".")) {
/*  716 */         org = (Widget)reader.getBuiltinWidgets().get(((String)opt.args().get(0)).substring(1));
/*      */       } else {
/*  718 */         org = (Widget)reader.getWidgets().get(opt.args().get(0));
/*      */       } 
/*  720 */       if (org == null) {
/*  721 */         err.println("widget: no such widget `" + (String)opt.args().get(0) + "'");
/*      */         return;
/*      */       } 
/*  724 */       reader.getWidgets().put(opt.args().get(1), org);
/*  725 */     } else if (opt.isSet("U")) {
/*  726 */       for (String arg : opt.args()) {
/*  727 */         reader.runMacro(KeyMap.translate(arg));
/*      */       }
/*  729 */     } else if (opt.args().size() == 1) {
/*  730 */       reader.callWidget(opt.args().get(0));
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
/*      */   public static void keymap(LineReader reader, PrintStream out, PrintStream err, String[] argv) throws Options.HelpException {
/*  744 */     String[] usage = { "keymap -  manipulate keymaps", "Usage: keymap [options] -l [-L] [keymap ...]", "       keymap [options] -d", "       keymap [options] -D keymap ...", "       keymap [options] -A old-keymap new-keymap", "       keymap [options] -N new-keymap [old-keymap]", "       keymap [options] -M", "       keymap [options] -r in-string ...", "       keymap [options] -s in-string out-string ...", "       keymap [options] in-string command ...", "       keymap [options] [in-string]", "  -? --help                       Displays command help", "  -A                              Create alias to keymap", "  -D                              Delete named keymaps", "  -L                              Output in form of keymap commands", "  -M (default=main)               Specify keymap to select", "  -N                              Create new keymap", "  -R                              Interpret in-strings as ranges", "  -a                              Select vicmd keymap", "  -d                              Delete existing keymaps and reset to default state", "  -e                              Select emacs keymap and bind it to main", "  -l                              List existing keymap names", "  -p                              List bindings which have given key sequence as a a prefix", "  -r                              Unbind specified in-strings ", "  -s                              Bind each in-string to each out-string ", "  -v                              Select viins keymap and bind it to main" };
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
/*  772 */     Options opt = Options.compile(usage).parse((Object[])argv);
/*  773 */     if (opt.isSet("help")) {
/*  774 */       throw new Options.HelpException(opt.usage());
/*      */     }
/*      */     
/*  777 */     Map<String, KeyMap<Binding>> keyMaps = reader.getKeyMaps();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  785 */     int actions = (opt.isSet("N") ? 1 : 0) + (opt.isSet("d") ? 1 : 0) + (opt.isSet("D") ? 1 : 0) + (opt.isSet("l") ? 1 : 0) + (opt.isSet("r") ? 1 : 0) + (opt.isSet("s") ? 1 : 0) + (opt.isSet("A") ? 1 : 0);
/*  786 */     if (actions > 1) {
/*  787 */       err.println("keymap: incompatible operation selection options");
/*      */       return;
/*      */     } 
/*  790 */     if (opt.isSet("l")) {
/*  791 */       boolean commands = opt.isSet("L");
/*      */       
/*  793 */       if (!opt.args().isEmpty()) {
/*  794 */         for (String arg : opt.args()) {
/*  795 */           KeyMap<Binding> map = keyMaps.get(arg);
/*  796 */           if (map == null) {
/*  797 */             err.println("keymap: no such keymap: `" + arg + "'"); continue;
/*      */           } 
/*  799 */           out.println(arg);
/*      */         } 
/*      */       } else {
/*      */         
/*  803 */         Objects.requireNonNull(out); keyMaps.keySet().forEach(out::println);
/*      */       } 
/*  805 */     } else if (opt.isSet("N")) {
/*  806 */       if (opt.isSet("e") || opt.isSet("v") || opt.isSet("a") || opt.isSet("M")) {
/*  807 */         err.println("keymap: keymap can not be selected with -N");
/*      */         return;
/*      */       } 
/*  810 */       if (opt.args().size() < 1) {
/*  811 */         err.println("keymap: not enough arguments for -N");
/*      */         return;
/*      */       } 
/*  814 */       if (opt.args().size() > 2) {
/*  815 */         err.println("keymap: too many arguments for -N");
/*      */         return;
/*      */       } 
/*  818 */       KeyMap<Binding> org = null;
/*  819 */       if (opt.args().size() == 2) {
/*  820 */         org = keyMaps.get(opt.args().get(1));
/*  821 */         if (org == null) {
/*  822 */           err.println("keymap: no such keymap `" + (String)opt.args().get(1) + "'");
/*      */           return;
/*      */         } 
/*      */       } 
/*  826 */       KeyMap<Binding> map = new KeyMap();
/*  827 */       if (org != null) {
/*  828 */         for (Map.Entry<String, Binding> bound : (Iterable<Map.Entry<String, Binding>>)org.getBoundKeys().entrySet()) {
/*  829 */           map.bind(bound.getValue(), bound.getKey());
/*      */         }
/*      */       }
/*  832 */       keyMaps.put(opt.args().get(0), map);
/*  833 */     } else if (opt.isSet("A")) {
/*  834 */       if (opt.isSet("e") || opt.isSet("v") || opt.isSet("a") || opt.isSet("M")) {
/*  835 */         err.println("keymap: keymap can not be selected with -N");
/*      */         return;
/*      */       } 
/*  838 */       if (opt.args().size() < 2) {
/*  839 */         err.println("keymap: not enough arguments for -A");
/*      */         return;
/*      */       } 
/*  842 */       if (opt.args().size() > 2) {
/*  843 */         err.println("keymap: too many arguments for -A");
/*      */         return;
/*      */       } 
/*  846 */       KeyMap<Binding> org = keyMaps.get(opt.args().get(0));
/*  847 */       if (org == null) {
/*  848 */         err.println("keymap: no such keymap `" + (String)opt.args().get(0) + "'");
/*      */         return;
/*      */       } 
/*  851 */       keyMaps.put(opt.args().get(1), org);
/*  852 */     } else if (opt.isSet("d")) {
/*  853 */       if (opt.isSet("e") || opt.isSet("v") || opt.isSet("a") || opt.isSet("M")) {
/*  854 */         err.println("keymap: keymap can not be selected with -N");
/*      */         return;
/*      */       } 
/*  857 */       if (!opt.args().isEmpty()) {
/*  858 */         err.println("keymap: too many arguments for -d");
/*      */         return;
/*      */       } 
/*  861 */       keyMaps.clear();
/*  862 */       keyMaps.putAll(reader.defaultKeyMaps());
/*  863 */     } else if (opt.isSet("D")) {
/*  864 */       if (opt.isSet("e") || opt.isSet("v") || opt.isSet("a") || opt.isSet("M")) {
/*  865 */         err.println("keymap: keymap can not be selected with -N");
/*      */         return;
/*      */       } 
/*  868 */       if (opt.args().size() < 1) {
/*  869 */         err.println("keymap: not enough arguments for -A");
/*      */         return;
/*      */       } 
/*  872 */       for (String name : opt.args()) {
/*  873 */         if (keyMaps.remove(name) == null) {
/*  874 */           err.println("keymap: no such keymap `" + name + "'");
/*      */           return;
/*      */         } 
/*      */       } 
/*  878 */     } else if (opt.isSet("r")) {
/*      */       
/*  880 */       String keyMapName = "main";
/*      */ 
/*      */ 
/*      */       
/*  884 */       int sel = (opt.isSet("a") ? 1 : 0) + (opt.isSet("e") ? 1 : 0) + (opt.isSet("v") ? 1 : 0) + (opt.isSet("M") ? 1 : 0);
/*  885 */       if (sel > 1) {
/*  886 */         err.println("keymap: incompatible keymap selection options"); return;
/*      */       } 
/*  888 */       if (opt.isSet("a")) {
/*  889 */         keyMapName = "vicmd";
/*  890 */       } else if (opt.isSet("e")) {
/*  891 */         keyMapName = "emacs";
/*  892 */       } else if (opt.isSet("v")) {
/*  893 */         keyMapName = "viins";
/*  894 */       } else if (opt.isSet("M")) {
/*  895 */         if (opt.args().isEmpty()) {
/*  896 */           err.println("keymap: argument expected: -M");
/*      */           return;
/*      */         } 
/*  899 */         keyMapName = opt.args().remove(0);
/*      */       } 
/*  901 */       KeyMap<Binding> map = keyMaps.get(keyMapName);
/*  902 */       if (map == null) {
/*  903 */         err.println("keymap: no such keymap `" + keyMapName + "'");
/*      */         
/*      */         return;
/*      */       } 
/*  907 */       boolean range = opt.isSet("R");
/*  908 */       boolean prefix = opt.isSet("p");
/*  909 */       Set<String> toRemove = new HashSet<>();
/*  910 */       Map<String, Binding> bound = map.getBoundKeys();
/*  911 */       for (String arg : opt.args()) {
/*  912 */         if (range) {
/*  913 */           Collection<String> r = KeyMap.range(opt.args().get(0));
/*  914 */           if (r == null) {
/*  915 */             err.println("keymap: malformed key range `" + (String)opt.args().get(0) + "'");
/*      */             return;
/*      */           } 
/*  918 */           toRemove.addAll(r); continue;
/*      */         } 
/*  920 */         String seq = KeyMap.translate(arg);
/*  921 */         for (String k : bound.keySet()) {
/*  922 */           if ((prefix && k.startsWith(seq) && k.length() > seq.length()) || (!prefix && k.equals(seq))) {
/*  923 */             toRemove.add(k);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  928 */       for (String seq : toRemove) {
/*  929 */         map.unbind(seq);
/*      */       }
/*  931 */       if (opt.isSet("e") || opt.isSet("v")) {
/*  932 */         keyMaps.put("main", map);
/*      */       }
/*  934 */     } else if (opt.isSet("s") || opt.args().size() > 1) {
/*      */       
/*  936 */       String keyMapName = "main";
/*      */ 
/*      */ 
/*      */       
/*  940 */       int sel = (opt.isSet("a") ? 1 : 0) + (opt.isSet("e") ? 1 : 0) + (opt.isSet("v") ? 1 : 0) + (opt.isSet("M") ? 1 : 0);
/*  941 */       if (sel > 1) {
/*  942 */         err.println("keymap: incompatible keymap selection options"); return;
/*      */       } 
/*  944 */       if (opt.isSet("a")) {
/*  945 */         keyMapName = "vicmd";
/*  946 */       } else if (opt.isSet("e")) {
/*  947 */         keyMapName = "emacs";
/*  948 */       } else if (opt.isSet("v")) {
/*  949 */         keyMapName = "viins";
/*  950 */       } else if (opt.isSet("M")) {
/*  951 */         if (opt.args().isEmpty()) {
/*  952 */           err.println("keymap: argument expected: -M");
/*      */           return;
/*      */         } 
/*  955 */         keyMapName = opt.args().remove(0);
/*      */       } 
/*  957 */       KeyMap<Binding> map = keyMaps.get(keyMapName);
/*  958 */       if (map == null) {
/*  959 */         err.println("keymap: no such keymap `" + keyMapName + "'");
/*      */         
/*      */         return;
/*      */       } 
/*  963 */       boolean range = opt.isSet("R");
/*  964 */       if (opt.args().size() % 2 == 1) {
/*  965 */         err.println("keymap: even number of arguments required");
/*      */         return;
/*      */       } 
/*  968 */       for (int i = 0; i < opt.args().size(); i += 2) {
/*      */ 
/*      */         
/*  971 */         Binding bout = opt.isSet("s") ? (Binding)new Macro(KeyMap.translate(opt.args().get(i + 1))) : (Binding)new Reference(opt.args().get(i + 1));
/*  972 */         if (range) {
/*  973 */           Collection<String> r = KeyMap.range(opt.args().get(i));
/*  974 */           if (r == null) {
/*  975 */             err.println("keymap: malformed key range `" + (String)opt.args().get(i) + "'");
/*      */             return;
/*      */           } 
/*  978 */           map.bind(bout, r);
/*      */         } else {
/*  980 */           String in = KeyMap.translate(opt.args().get(i));
/*  981 */           map.bind(bout, in);
/*      */         } 
/*      */       } 
/*  984 */       if (opt.isSet("e") || opt.isSet("v")) {
/*  985 */         keyMaps.put("main", map);
/*      */       }
/*      */     } else {
/*      */       
/*  989 */       String keyMapName = "main";
/*      */ 
/*      */ 
/*      */       
/*  993 */       int sel = (opt.isSet("a") ? 1 : 0) + (opt.isSet("e") ? 1 : 0) + (opt.isSet("v") ? 1 : 0) + (opt.isSet("M") ? 1 : 0);
/*  994 */       if (sel > 1) {
/*  995 */         err.println("keymap: incompatible keymap selection options"); return;
/*      */       } 
/*  997 */       if (opt.isSet("a")) {
/*  998 */         keyMapName = "vicmd";
/*  999 */       } else if (opt.isSet("e")) {
/* 1000 */         keyMapName = "emacs";
/* 1001 */       } else if (opt.isSet("v")) {
/* 1002 */         keyMapName = "viins";
/* 1003 */       } else if (opt.isSet("M")) {
/* 1004 */         if (opt.args().isEmpty()) {
/* 1005 */           err.println("keymap: argument expected: -M");
/*      */           return;
/*      */         } 
/* 1008 */         keyMapName = opt.args().remove(0);
/*      */       } 
/* 1010 */       KeyMap<Binding> map = keyMaps.get(keyMapName);
/* 1011 */       if (map == null) {
/* 1012 */         err.println("keymap: no such keymap `" + keyMapName + "'");
/*      */         
/*      */         return;
/*      */       } 
/* 1016 */       boolean prefix = opt.isSet("p");
/* 1017 */       boolean commands = opt.isSet("L");
/* 1018 */       if (prefix && opt.args().isEmpty()) {
/* 1019 */         err.println("keymap: option -p requires a prefix string");
/*      */         return;
/*      */       } 
/* 1022 */       if (!opt.args().isEmpty() || (!opt.isSet("e") && !opt.isSet("v"))) {
/* 1023 */         Map<String, Binding> bound = map.getBoundKeys();
/* 1024 */         String seq = !opt.args().isEmpty() ? KeyMap.translate(opt.args().get(0)) : null;
/* 1025 */         Map.Entry<String, Binding> begin = null;
/* 1026 */         String last = null;
/* 1027 */         Iterator<Map.Entry<String, Binding>> iterator = bound.entrySet().iterator();
/* 1028 */         while (iterator.hasNext()) {
/* 1029 */           Map.Entry<String, Binding> entry = iterator.next();
/* 1030 */           String key = entry.getKey();
/* 1031 */           if (seq == null || (prefix && key
/* 1032 */             .startsWith(seq) && !key.equals(seq)) || (!prefix && key
/* 1033 */             .equals(seq))) {
/* 1034 */             if (begin != null || !iterator.hasNext()) {
/*      */               
/* 1036 */               String n = ((last.length() > 1) ? last.substring(0, last.length() - 1) : "") + (char)(last.charAt(last.length() - 1) + 1);
/* 1037 */               if (key.equals(n) && ((Binding)entry.getValue()).equals(begin.getValue())) {
/* 1038 */                 last = key;
/*      */                 continue;
/*      */               } 
/* 1041 */               StringBuilder sb = new StringBuilder();
/* 1042 */               if (commands) {
/* 1043 */                 sb.append("keymap -M ");
/* 1044 */                 sb.append(keyMapName);
/* 1045 */                 sb.append(" ");
/*      */               } 
/* 1047 */               if (((String)begin.getKey()).equals(last)) {
/* 1048 */                 sb.append(KeyMap.display(last));
/* 1049 */                 sb.append(" ");
/* 1050 */                 displayValue(sb, begin.getValue());
/* 1051 */                 out.println(sb);
/*      */               } else {
/* 1053 */                 if (commands) {
/* 1054 */                   sb.append("-R ");
/*      */                 }
/* 1056 */                 sb.append(KeyMap.display(begin.getKey()));
/* 1057 */                 sb.append("-");
/* 1058 */                 sb.append(KeyMap.display(last));
/* 1059 */                 sb.append(" ");
/* 1060 */                 displayValue(sb, begin.getValue());
/* 1061 */                 out.println(sb);
/*      */               } 
/* 1063 */               begin = entry;
/* 1064 */               last = key;
/*      */               continue;
/*      */             } 
/* 1067 */             begin = entry;
/* 1068 */             last = key;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1073 */       if (opt.isSet("e") || opt.isSet("v")) {
/* 1074 */         keyMaps.put("main", map);
/*      */       }
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
/*      */   public static void setopt(LineReader reader, PrintStream out, PrintStream err, String[] argv) throws Options.HelpException {
/* 1089 */     String[] usage = { "setopt -  set options", "Usage: setopt [-m] option ...", "       setopt", "  -? --help                       Displays command help", "  -m                              Use pattern matching" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     Options opt = Options.compile(usage).parse((Object[])argv);
/* 1097 */     if (opt.isSet("help")) {
/* 1098 */       throw new Options.HelpException(opt.usage());
/*      */     }
/* 1100 */     if (opt.args().isEmpty()) {
/* 1101 */       for (LineReader.Option option : LineReader.Option.values()) {
/* 1102 */         if (reader.isSet(option) != option.isDef()) {
/* 1103 */           out.println((option.isDef() ? "no-" : "") + option
/* 1104 */               .toString().toLowerCase().replace('_', '-'));
/*      */         }
/*      */       } 
/*      */     } else {
/* 1108 */       boolean match = opt.isSet("m");
/* 1109 */       doSetOpts(reader, out, err, opt.args(), match, true);
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
/*      */   public static void unsetopt(LineReader reader, PrintStream out, PrintStream err, String[] argv) throws Options.HelpException {
/* 1124 */     String[] usage = { "unsetopt -  unset options", "Usage: unsetopt [-m] option ...", "       unsetopt", "  -? --help                       Displays command help", "  -m                              Use pattern matching" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1131 */     Options opt = Options.compile(usage).parse((Object[])argv);
/* 1132 */     if (opt.isSet("help")) {
/* 1133 */       throw new Options.HelpException(opt.usage());
/*      */     }
/* 1135 */     if (opt.args().isEmpty()) {
/* 1136 */       for (LineReader.Option option : LineReader.Option.values()) {
/* 1137 */         if (reader.isSet(option) == option.isDef()) {
/* 1138 */           out.println((option.isDef() ? "no-" : "") + option
/* 1139 */               .toString().toLowerCase().replace('_', '-'));
/*      */         }
/*      */       } 
/*      */     } else {
/* 1143 */       boolean match = opt.isSet("m");
/* 1144 */       doSetOpts(reader, out, err, opt.args(), match, false);
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
/*      */   private static void doSetOpts(LineReader reader, PrintStream out, PrintStream err, List<String> options, boolean match, boolean set) {
/* 1160 */     for (String name : options) {
/* 1161 */       String tname = name.toLowerCase().replaceAll("[-_]", "");
/* 1162 */       if (match) {
/* 1163 */         tname = tname.replaceAll("\\*", "[a-z]*");
/* 1164 */         tname = tname.replaceAll("\\?", "[a-z]");
/*      */       } 
/* 1166 */       boolean found = false;
/* 1167 */       for (LineReader.Option option : LineReader.Option.values()) {
/* 1168 */         String optName = option.name().toLowerCase().replaceAll("[-_]", "");
/* 1169 */         if (match ? optName.matches(tname) : optName.equals(tname)) {
/* 1170 */           if (set) {
/* 1171 */             reader.setOpt(option);
/*      */           } else {
/* 1173 */             reader.unsetOpt(option);
/*      */           } 
/* 1175 */           found = true;
/* 1176 */           if (!match) {
/*      */             break;
/*      */           }
/* 1179 */         } else if (match ? ("no" + optName).matches(tname) : ("no" + optName).equals(tname)) {
/* 1180 */           if (set) {
/* 1181 */             reader.unsetOpt(option);
/*      */           } else {
/* 1183 */             reader.setOpt(option);
/*      */           } 
/* 1185 */           if (!match) {
/* 1186 */             found = true;
/*      */           }
/*      */           break;
/*      */         } 
/*      */       } 
/* 1191 */       if (!found) {
/* 1192 */         err.println("No matching option: " + name);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void displayValue(StringBuilder sb, Object value) {
/* 1204 */     if (value == null) {
/* 1205 */       sb.append("undefined-key");
/* 1206 */     } else if (value instanceof Macro) {
/* 1207 */       sb.append(KeyMap.display(((Macro)value).getSequence()));
/* 1208 */     } else if (value instanceof Reference) {
/* 1209 */       sb.append(((Reference)value).name());
/*      */     } else {
/* 1211 */       sb.append(value);
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
/*      */   public static void setvar(LineReader lineReader, PrintStream out, PrintStream err, String[] argv) throws Options.HelpException {
/* 1226 */     String[] usage = { "setvar -  set lineReader variable value", "Usage: setvar [variable] [value]", "  -? --help                    Show help" };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1231 */     Options opt = Options.compile(usage).parse((Object[])argv);
/* 1232 */     if (opt.isSet("help")) {
/* 1233 */       throw new Options.HelpException(opt.usage());
/*      */     }
/* 1235 */     if (opt.args().isEmpty()) {
/* 1236 */       for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)lineReader.getVariables().entrySet()) {
/* 1237 */         out.println((String)entry.getKey() + ": " + entry.getValue());
/*      */       }
/* 1239 */     } else if (opt.args().size() == 1) {
/* 1240 */       out.println(lineReader.getVariable(opt.args().get(0)));
/*      */     } else {
/* 1242 */       lineReader.setVariable(opt.args().get(0), opt.args().get(1));
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
/*      */   public static void colors(Terminal terminal, PrintStream out, String[] argv) throws Options.HelpException, IOException {
/* 1256 */     String[] usage = { "colors -  view 256-color table and ANSI-styles", "Usage: colors [OPTIONS]", "  -? --help                     Displays command help", "  -a --ansistyles               List ANSI-styles", "  -c --columns=COLUMNS          Number of columns in name/rgb table", "                                COLUMNS = 1, display columns: color, style, ansi and HSL", "  -f --find=NAME                Find color names which contains NAME ", "  -l --lock=STYLE               Lock fore- or background color", "  -n --name                     Color name table (default number table)", "  -r --rgb                      Use and display rgb value", "  -s --small                    View 16-color table (default 256-color)", "  -v --view=COLOR               View 24bit color table of COLOR ", "                                COLOR = <colorName>, <color24bit> or hue<angle>" };
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
/* 1271 */     Options opt = Options.compile(usage).parse((Object[])argv);
/* 1272 */     if (opt.isSet("help")) {
/* 1273 */       throw new Options.HelpException(opt.usage());
/*      */     }
/* 1275 */     Colors colors = new Colors(terminal, out);
/* 1276 */     if (opt.isSet("ansistyles")) {
/* 1277 */       colors.printStyles();
/*      */     } else {
/* 1279 */       String style = null;
/* 1280 */       if (opt.isSet("lock")) {
/* 1281 */         style = opt.get("lock");
/* 1282 */         if (style.length() - style.replace(":", "").length() > 1) {
/* 1283 */           style = null;
/*      */         }
/*      */       } 
/* 1286 */       if (!opt.isSet("view")) {
/* 1287 */         boolean rgb = opt.isSet("rgb");
/* 1288 */         int columns = (terminal.getWidth() > (rgb ? 71 : 122)) ? 6 : 5;
/* 1289 */         String findName = null;
/* 1290 */         boolean nameTable = opt.isSet("name");
/* 1291 */         boolean table16 = opt.isSet("small");
/* 1292 */         if (opt.isSet("find")) {
/* 1293 */           findName = opt.get("find").toLowerCase();
/* 1294 */           nameTable = true;
/* 1295 */           table16 = false;
/* 1296 */           columns = 4;
/*      */         } 
/* 1298 */         if (table16) {
/* 1299 */           columns += 2;
/*      */         }
/* 1301 */         if (opt.isSet("columns")) {
/* 1302 */           columns = opt.getNumber("columns");
/*      */         }
/* 1304 */         colors.printColors(nameTable, rgb, table16, columns, findName, style);
/*      */       } else {
/* 1306 */         colors.printColor(opt.get("view").toLowerCase(), style);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static class Colors
/*      */   {
/*      */     private static final String COLORS_24BIT = "[0-9a-fA-F]{6}";
/*      */     
/* 1316 */     private static final List<String> COLORS_16 = Arrays.asList(new String[] { "black", "red", "green", "yellow", "blue", "magenta", "cyan", "white", "!black", "!red", "!green", "!yellow", "!blue", "!magenta", "!cyan", "!white" });
/*      */ 
/*      */     
/*      */     boolean name;
/*      */ 
/*      */     
/*      */     boolean rgb;
/*      */ 
/*      */     
/*      */     private final Terminal terminal;
/*      */ 
/*      */     
/*      */     private final PrintStream out;
/*      */     
/*      */     private boolean fixedBg;
/*      */     
/*      */     private String fixedStyle;
/*      */     
/*      */     int r;
/*      */     
/*      */     int g;
/*      */     
/*      */     int b;
/*      */ 
/*      */     
/*      */     public Colors(Terminal terminal, PrintStream out) {
/* 1342 */       this.terminal = terminal;
/* 1343 */       this.out = out;
/*      */     }
/*      */     
/*      */     private String getAnsiStyle(String style) {
/* 1347 */       return style;
/*      */     }
/*      */     
/*      */     public void printStyles() {
/* 1351 */       AttributedStringBuilder asb = new AttributedStringBuilder();
/* 1352 */       asb.tabs(13);
/* 1353 */       for (String s : Styles.ANSI_STYLES) {
/* 1354 */         AttributedStyle as = (new StyleResolver(this::getAnsiStyle)).resolve("." + s);
/* 1355 */         asb.style(as);
/* 1356 */         asb.append(s);
/* 1357 */         asb.style(AttributedStyle.DEFAULT);
/* 1358 */         asb.append("\t");
/* 1359 */         asb.append(getAnsiStyle(s));
/* 1360 */         asb.append("\t");
/* 1361 */         asb.append(as.toAnsi());
/* 1362 */         asb.append("\n");
/*      */       } 
/* 1364 */       asb.toAttributedString().println(this.terminal);
/*      */     }
/*      */     
/*      */     private String getStyle(String color) {
/*      */       String out;
/* 1369 */       char fg = ' ';
/* 1370 */       if (this.name) {
/* 1371 */         out = (this.fixedBg ? "fg:" : "bg:") + "~" + color.substring(1);
/* 1372 */         fg = color.charAt(0);
/* 1373 */       } else if (this.rgb) {
/* 1374 */         out = (this.fixedBg ? "fg-rgb:" : "bg-rgb:") + "#" + color.substring(1);
/* 1375 */         fg = color.charAt(0);
/* 1376 */       } else if (color.substring(1).matches("\\d+")) {
/* 1377 */         out = (this.fixedBg ? "38;5;" : "48;5;") + color.substring(1);
/* 1378 */         fg = color.charAt(0);
/*      */       } else {
/* 1380 */         out = (this.fixedBg ? "fg:" : "bg:") + color;
/*      */       } 
/* 1382 */       if (this.fixedStyle == null) {
/* 1383 */         if (color.startsWith("!") || color.equals("white") || fg == 'b') {
/* 1384 */           out = out + ",fg:black";
/*      */         } else {
/* 1386 */           out = out + ",fg:!white";
/*      */         } 
/*      */       } else {
/* 1389 */         out = out + "," + this.fixedStyle;
/*      */       } 
/* 1391 */       return out;
/*      */     }
/*      */     
/*      */     private String foreground(int idx) {
/* 1395 */       String fg = "w";
/* 1396 */       if ((idx > 6 && idx < 16) || (idx > 33 && idx < 52) || (idx > 69 && idx < 88) || (idx > 105 && idx < 124) || (idx > 141 && idx < 160) || (idx > 177 && idx < 196) || (idx > 213 && idx < 232) || idx > 243)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1404 */         fg = "b";
/*      */       }
/* 1406 */       return fg;
/*      */     }
/*      */     
/*      */     private String addPadding(int width, String field) {
/* 1410 */       int s = width - field.length();
/* 1411 */       int left = s / 2;
/* 1412 */       StringBuilder lp = new StringBuilder();
/* 1413 */       StringBuilder rp = new StringBuilder(); int i;
/* 1414 */       for (i = 0; i < left; i++) {
/* 1415 */         lp.append(" ");
/*      */       }
/* 1417 */       for (i = 0; i < s - left; i++) {
/* 1418 */         rp.append(" ");
/*      */       }
/* 1420 */       return lp + field + rp;
/*      */     }
/*      */     
/*      */     private String addLeftPadding(int width, String field) {
/* 1424 */       int s = width - field.length();
/* 1425 */       StringBuilder lp = new StringBuilder();
/* 1426 */       for (int i = 0; i < s; i++) {
/* 1427 */         lp.append(" ");
/*      */       }
/* 1429 */       return lp + field;
/*      */     }
/*      */     
/*      */     private void setFixedStyle(String style) {
/* 1433 */       this.fixedStyle = style;
/* 1434 */       if (style != null && (style
/* 1435 */         .contains("b:") || style
/* 1436 */         .contains("b-") || style
/* 1437 */         .contains("bg:") || style
/* 1438 */         .contains("bg-") || style
/* 1439 */         .contains("background"))) {
/* 1440 */         this.fixedBg = true;
/*      */       }
/*      */     }
/*      */     
/*      */     private List<String> retrieveColorNames() throws IOException {
/*      */       List<String> out;
/* 1446 */       InputStream is = (new Source.ResourceSource("/org/jline/utils/colors.txt", null)).read(); 
/* 1447 */       try { BufferedReader br = new BufferedReader(new InputStreamReader(is));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1452 */         try { out = (List<String>)br.lines().map(String::trim).filter(s -> !s.startsWith("#")).filter(s -> !s.isEmpty()).collect(Collectors.toList());
/* 1453 */           br.close(); } catch (Throwable throwable) { try { br.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  if (is != null) is.close();  } catch (Throwable throwable) { if (is != null)
/* 1454 */           try { is.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return out;
/*      */     }
/*      */ 
/*      */     
/*      */     public void printColors(boolean name, boolean rgb, boolean small, int columns, String findName, String style) throws IOException {
/* 1459 */       this.name = (!rgb && name);
/* 1460 */       this.rgb = rgb;
/* 1461 */       setFixedStyle(style);
/* 1462 */       AttributedStringBuilder asb = new AttributedStringBuilder();
/* 1463 */       int width = this.terminal.getWidth();
/* 1464 */       String tableName = small ? " 16-color " : "256-color ";
/* 1465 */       if (!name && !rgb) {
/* 1466 */         this.out.print(tableName);
/* 1467 */         this.out.print("table, fg:<name> ");
/* 1468 */         if (!small) {
/* 1469 */           this.out.print("/ 38;5;<n>");
/*      */         }
/* 1471 */         this.out.println();
/* 1472 */         this.out.print("                 bg:<name> ");
/* 1473 */         if (!small) {
/* 1474 */           this.out.print("/ 48;5;<n>");
/*      */         }
/* 1476 */         this.out.println("\n");
/* 1477 */         boolean narrow = (width < 180);
/* 1478 */         for (String c : COLORS_16) {
/* 1479 */           AttributedStyle ss = (new StyleResolver(this::getStyle)).resolve('.' + c, null);
/* 1480 */           asb.style(ss);
/* 1481 */           asb.append(addPadding(11, c));
/* 1482 */           asb.style(AttributedStyle.DEFAULT);
/* 1483 */           if (c.equals("white")) {
/* 1484 */             if (narrow || small) {
/* 1485 */               asb.append('\n'); continue;
/*      */             } 
/* 1487 */             asb.append("    "); continue;
/*      */           } 
/* 1489 */           if (c.equals("!white")) {
/* 1490 */             asb.append('\n');
/*      */           }
/*      */         } 
/* 1493 */         asb.append('\n');
/* 1494 */         if (!small) {
/* 1495 */           for (int i = 16; i < 256; i++) {
/* 1496 */             String fg = foreground(i);
/* 1497 */             String code = Integer.toString(i);
/* 1498 */             AttributedStyle ss = (new StyleResolver(this::getStyle)).resolve("." + fg + code, null);
/* 1499 */             asb.style(ss);
/* 1500 */             String str = " ";
/* 1501 */             if (i < 100) {
/* 1502 */               str = "  ";
/* 1503 */             } else if (i > 231) {
/* 1504 */               str = (i % 2 == 0) ? "    " : "   ";
/*      */             } 
/* 1506 */             asb.append(str).append(code).append(' ');
/* 1507 */             if (i == 51 || i == 87 || i == 123 || i == 159 || i == 195 || i == 231 || (narrow && (i == 33 || i == 69 || i == 105 || i == 141 || i == 177 || i == 213 || i == 243))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1516 */               asb.style(AttributedStyle.DEFAULT);
/* 1517 */               asb.append('\n');
/* 1518 */               if (i == 231) {
/* 1519 */                 asb.append('\n');
/*      */               }
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } else {
/* 1525 */         this.out.print(tableName);
/* 1526 */         if (name) {
/* 1527 */           asb.tabs(Arrays.asList(new Integer[] { Integer.valueOf(25), Integer.valueOf(60), Integer.valueOf(75) }));
/* 1528 */           this.out.println("table, fg:~<name> OR 38;5;<n>");
/* 1529 */           this.out.println("                 bg:~<name> OR 48;5;<n>");
/*      */         } else {
/* 1531 */           asb.tabs(Arrays.asList(new Integer[] { Integer.valueOf(15), Integer.valueOf(45), Integer.valueOf(70) }));
/* 1532 */           this.out.println("table, fg-rgb:<color24bit> OR 38;5;<n>");
/* 1533 */           this.out.println("                 bg-rgb:<color24bit> OR 48;5;<n>");
/*      */         } 
/* 1535 */         this.out.println();
/* 1536 */         int col = 0;
/* 1537 */         int idx = 0;
/* 1538 */         int colWidth = rgb ? 12 : 21;
/* 1539 */         int lb = 1;
/* 1540 */         if (findName != null && (findName.startsWith("#") || findName.startsWith("x"))) {
/* 1541 */           findName = findName.substring(1);
/*      */         }
/* 1543 */         for (String line : retrieveColorNames()) {
/* 1544 */           if (!rgb)
/*      */           {
/* 1546 */             if (findName != null) {
/* 1547 */               if (!line.toLowerCase().contains(findName)) {
/* 1548 */                 idx++;
/*      */                 continue;
/*      */               } 
/* 1551 */             } else if (small) {
/* 1552 */               colWidth = 15;
/* 1553 */               lb = 1;
/* 1554 */             } else if (columns > 4) {
/* 1555 */               if (idx > 15 && idx < 232) {
/* 1556 */                 colWidth = (columns != 6 || col == 1 || col == 2 || col == 3) ? 21 : 20;
/* 1557 */                 lb = 1;
/*      */               } else {
/* 1559 */                 colWidth = (columns != 6 || idx % 2 == 0 || col == 7) ? 15 : 16;
/* 1560 */                 lb = -1;
/*      */               } 
/*      */             }  } 
/* 1563 */           String fg = foreground(idx);
/* 1564 */           if (rgb) {
/* 1565 */             line = Integer.toHexString(org.jline.utils.Colors.DEFAULT_COLORS_256[idx]);
/* 1566 */             for (int p = line.length(); p < 6; p++) {
/* 1567 */               line = "0" + line;
/*      */             }
/* 1569 */             if (findName != null && 
/* 1570 */               !line.toLowerCase().matches(findName)) {
/* 1571 */               idx++;
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/* 1576 */           AttributedStyle ss = (new StyleResolver(this::getStyle)).resolve("." + fg + line, null);
/* 1577 */           if (rgb) {
/* 1578 */             line = "#" + line;
/*      */           }
/* 1580 */           asb.style(ss);
/* 1581 */           String idxstr = Integer.toString(idx);
/* 1582 */           if (rgb) {
/* 1583 */             if (idx < 10) {
/* 1584 */               idxstr = "  " + idxstr;
/* 1585 */             } else if (idx < 100) {
/* 1586 */               idxstr = " " + idxstr;
/*      */             } 
/*      */           }
/* 1589 */           asb.append(idxstr).append(addPadding(colWidth - idxstr.length(), line));
/* 1590 */           if (columns == 1) {
/* 1591 */             asb.style(AttributedStyle.DEFAULT);
/* 1592 */             asb.append("\t").append(getStyle(fg + line.substring(rgb ? 1 : 0)));
/* 1593 */             asb.append("\t").append(ss.toAnsi());
/* 1594 */             int[] rgb1 = rgb(org.jline.utils.Colors.DEFAULT_COLORS_256[idx]);
/* 1595 */             int[] hsl = rgb2hsl(rgb1[0], rgb1[1], rgb1[2]);
/* 1596 */             asb.append("\t")
/* 1597 */               .append(addLeftPadding(6, hsl[0] + ", "))
/* 1598 */               .append(addLeftPadding(4, hsl[1] + "%"))
/* 1599 */               .append(", ")
/* 1600 */               .append(addLeftPadding(4, hsl[2] + "%"));
/*      */           } 
/* 1602 */           col++;
/* 1603 */           idx++;
/* 1604 */           if ((col + 1) * colWidth > width || col + lb > columns) {
/* 1605 */             col = 0;
/* 1606 */             asb.style(AttributedStyle.DEFAULT);
/* 1607 */             asb.append('\n');
/*      */           } 
/* 1609 */           if (findName == null) {
/* 1610 */             if (idx == 16) {
/* 1611 */               if (small)
/*      */                 break; 
/* 1613 */               if (col != 0) {
/* 1614 */                 col = 0;
/* 1615 */                 asb.style(AttributedStyle.DEFAULT);
/* 1616 */                 asb.append('\n');
/*      */               }  continue;
/* 1618 */             }  if (idx == 232 && col != 0) {
/* 1619 */               col = 0;
/* 1620 */               asb.style(AttributedStyle.DEFAULT);
/* 1621 */               asb.append('\n');
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1626 */       asb.toAttributedString().println(this.terminal);
/*      */     }
/*      */     
/*      */     private int[] rgb(long color) {
/* 1630 */       int[] rgb = { 0, 0, 0 };
/* 1631 */       rgb[0] = (int)(color >> 16L & 0xFFL);
/* 1632 */       rgb[1] = (int)(color >> 8L & 0xFFL);
/* 1633 */       rgb[2] = (int)(color & 0xFFL);
/* 1634 */       return rgb;
/*      */     }
/*      */     
/*      */     private int[] hue2rgb(int degree) {
/* 1638 */       int[] rgb = { 0, 0, 0 };
/* 1639 */       double hue = degree / 60.0D;
/* 1640 */       double a = Math.tan(degree / 360.0D * 2.0D * Math.PI) / Math.sqrt(3.0D);
/* 1641 */       if (hue >= 0.0D && hue < 1.0D) {
/* 1642 */         rgb[0] = 255;
/* 1643 */         rgb[1] = (int)(2.0D * a * 255.0D / (1.0D + a));
/* 1644 */       } else if (hue >= 1.0D && hue < 2.0D) {
/* 1645 */         rgb[0] = (int)(255.0D * (1.0D + a) / 2.0D * a);
/* 1646 */         rgb[1] = 255;
/* 1647 */       } else if (hue >= 2.0D && hue < 3.0D) {
/* 1648 */         rgb[1] = 255;
/* 1649 */         rgb[2] = (int)(255.0D * (1.0D + a) / (1.0D - a));
/* 1650 */       } else if (hue >= 3.0D && hue < 4.0D) {
/* 1651 */         rgb[1] = (int)(255.0D * (1.0D - a) / (1.0D + a));
/* 1652 */         rgb[2] = 255;
/* 1653 */       } else if (hue >= 4.0D && hue <= 5.0D) {
/* 1654 */         rgb[0] = (int)(255.0D * (a - 1.0D) / 2.0D * a);
/* 1655 */         rgb[2] = 255;
/* 1656 */       } else if (hue > 5.0D && hue <= 6.0D) {
/* 1657 */         rgb[0] = 255;
/* 1658 */         rgb[2] = (int)(510.0D * a / (a - 1.0D));
/*      */       } 
/* 1660 */       return rgb;
/*      */     }
/*      */     
/*      */     private int[] rgb2hsl(int r, int g, int b) {
/* 1664 */       int[] hsl = { 0, 0, 0 };
/* 1665 */       if (r != 0 || g != 0 || b != 0) {
/* 1666 */         hsl[0] = (int)Math.round(57.29577951308232D * Math.atan2(Math.sqrt(3.0D) * (g - b), (2 * r - g - b)));
/* 1667 */         while (hsl[0] < 0) {
/* 1668 */           hsl[0] = hsl[0] + 360;
/*      */         }
/*      */       } 
/* 1671 */       double mx = Math.max(Math.max(r, g), b) / 255.0D;
/* 1672 */       double mn = Math.min(Math.min(r, g), b) / 255.0D;
/* 1673 */       double l = (mx + mn) / 2.0D;
/* 1674 */       hsl[1] = (l == 0.0D || l == 1.0D) ? 0 : (int)Math.round(100.0D * (mx - mn) / (1.0D - Math.abs(2.0D * l - 1.0D)));
/* 1675 */       hsl[2] = (int)Math.round(100.0D * l);
/* 1676 */       return hsl;
/*      */     }
/*      */     
/*      */     String getStyleRGB(String s) {
/* 1680 */       if (this.fixedStyle == null) {
/* 1681 */         double ry = Math.pow(this.r / 255.0D, 2.2D);
/* 1682 */         double by = Math.pow(this.b / 255.0D, 2.2D);
/* 1683 */         double gy = Math.pow(this.g / 255.0D, 2.2D);
/* 1684 */         double y = 0.2126D * ry + 0.7151D * gy + 0.0721D * by;
/* 1685 */         String fg = "black";
/* 1686 */         if (1.05D / (y + 0.05D) > (y + 0.05D) / 0.05D) {
/* 1687 */           fg = "white";
/*      */         }
/* 1689 */         return "bg-rgb:" + String.format("#%02x%02x%02x", new Object[] { Integer.valueOf(this.r), Integer.valueOf(this.g), Integer.valueOf(this.b) }) + ",fg:" + fg;
/*      */       } 
/* 1691 */       return (this.fixedBg ? "fg-rgb:" : "bg-rgb:") + String.format("#%02x%02x%02x", new Object[] { Integer.valueOf(this.r), Integer.valueOf(this.g), Integer.valueOf(this.b) }) + "," + this.fixedStyle;
/*      */     }
/*      */ 
/*      */     
/*      */     public void printColor(String name, String style) throws IOException {
/* 1696 */       setFixedStyle(style);
/*      */       
/* 1698 */       double zoom = 1.0D;
/* 1699 */       int[] rgb = { 0, 0, 0 };
/* 1700 */       if (name.matches("[0-9a-fA-F]{6}")) {
/* 1701 */         rgb = rgb(Long.parseLong(name, 16));
/* 1702 */         zoom = 2.0D;
/* 1703 */       } else if ((name.startsWith("#") || name.startsWith("x")) && name
/* 1704 */         .substring(1).matches("[0-9a-fA-F]{6}")) {
/* 1705 */         rgb = rgb(Long.parseLong(name.substring(1), 16));
/* 1706 */         zoom = 2.0D;
/* 1707 */       } else if (COLORS_16.contains(name)) {
/* 1708 */         for (int i = 0; i < 16; i++) {
/* 1709 */           if (((String)COLORS_16.get(i)).equals(name)) {
/* 1710 */             rgb = rgb(org.jline.utils.Colors.DEFAULT_COLORS_256[i]);
/*      */             break;
/*      */           } 
/*      */         } 
/* 1714 */       } else if (name.matches("hue[1-3]?[0-9]{1,2}")) {
/* 1715 */         int i = Integer.parseInt(name.substring(3));
/* 1716 */         if (i > 360) {
/* 1717 */           throw new IllegalArgumentException("Color not found: " + name);
/*      */         }
/* 1719 */         rgb = hue2rgb(i);
/* 1720 */       } else if (name.matches("[a-z0-9]+")) {
/* 1721 */         List<String> colors = retrieveColorNames();
/* 1722 */         if (colors.contains(name)) {
/* 1723 */           for (int i = 0; i < 256; i++) {
/* 1724 */             if (((String)colors.get(i)).equals(name)) {
/* 1725 */               rgb = rgb(org.jline.utils.Colors.DEFAULT_COLORS_256[i]);
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } else {
/* 1730 */           boolean found = false; int i;
/* 1731 */           for (i = 0; i < 256; i++) {
/* 1732 */             if (((String)colors.get(i)).startsWith(name)) {
/* 1733 */               rgb = rgb(org.jline.utils.Colors.DEFAULT_COLORS_256[i]);
/* 1734 */               found = true;
/*      */               break;
/*      */             } 
/*      */           } 
/* 1738 */           if (!found) {
/* 1739 */             for (i = 0; i < 256; i++) {
/* 1740 */               if (((String)colors.get(i)).contains(name)) {
/* 1741 */                 rgb = rgb(org.jline.utils.Colors.DEFAULT_COLORS_256[i]);
/* 1742 */                 found = true;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/* 1747 */           if (!found) {
/* 1748 */             throw new IllegalArgumentException("Color not found: " + name);
/*      */           }
/*      */         } 
/*      */       } else {
/* 1752 */         throw new IllegalArgumentException("Color not found: " + name);
/*      */       } 
/* 1754 */       double step = 32.0D;
/* 1755 */       int barSize = 14;
/* 1756 */       int width = this.terminal.getWidth();
/* 1757 */       if (width > 287) {
/* 1758 */         step = 8.0D;
/* 1759 */         barSize = 58;
/* 1760 */       } else if (width > 143) {
/* 1761 */         step = 16.0D;
/* 1762 */         barSize = 29;
/* 1763 */       } else if (width > 98) {
/* 1764 */         step = 24.0D;
/* 1765 */         barSize = 18;
/*      */       } 
/* 1767 */       this.r = rgb[0];
/* 1768 */       this.g = rgb[1];
/* 1769 */       this.b = rgb[2];
/* 1770 */       int[] hsl = rgb2hsl(this.r, this.g, this.b);
/* 1771 */       int hueAngle = hsl[0];
/* 1772 */       this.out.println("HSL: " + hsl[0] + "deg, " + hsl[1] + "%, " + hsl[2] + "%");
/* 1773 */       if (hsl[2] > 85 || hsl[2] < 15 || hsl[1] < 15) {
/* 1774 */         zoom = 1.0D;
/*      */       }
/* 1776 */       double div = zoom * 256.0D / step;
/* 1777 */       int ndiv = (int)(div / zoom);
/* 1778 */       double xrs = (255 - this.r) / div;
/* 1779 */       double xgs = (255 - this.g) / div;
/* 1780 */       double xbs = (255 - this.b) / div;
/* 1781 */       double[] yrs = new double[ndiv], ygs = new double[ndiv], ybs = new double[ndiv];
/* 1782 */       double[] ro = new double[ndiv], go = new double[ndiv], bo = new double[ndiv];
/* 1783 */       AttributedStringBuilder asb = new AttributedStringBuilder();
/* 1784 */       for (int y = 0; y < ndiv; y++) {
/* 1785 */         for (int x = 0; x < ndiv; x++) {
/* 1786 */           if (y == 0) {
/* 1787 */             yrs[x] = (rgb[0] + x * xrs) / div;
/* 1788 */             ygs[x] = (rgb[1] + x * xgs) / div;
/* 1789 */             ybs[x] = (rgb[2] + x * xbs) / div;
/* 1790 */             ro[x] = rgb[0] + x * xrs;
/* 1791 */             go[x] = rgb[1] + x * xgs;
/* 1792 */             bo[x] = rgb[2] + x * xbs;
/* 1793 */             this.r = (int)ro[x];
/* 1794 */             this.g = (int)go[x];
/* 1795 */             this.b = (int)bo[x];
/*      */           } else {
/* 1797 */             this.r = (int)(ro[x] - y * yrs[x]);
/* 1798 */             this.g = (int)(go[x] - y * ygs[x]);
/* 1799 */             this.b = (int)(bo[x] - y * ybs[x]);
/*      */           } 
/* 1801 */           String col = String.format("%02x%02x%02x", new Object[] { Integer.valueOf(this.r), Integer.valueOf(this.g), Integer.valueOf(this.b) });
/* 1802 */           AttributedStyle s = (new StyleResolver(this::getStyleRGB)).resolve(".rgb" + col);
/* 1803 */           asb.style(s);
/* 1804 */           asb.append(" ").append("#").append(col).append(" ");
/*      */         } 
/* 1806 */         asb.style(AttributedStyle.DEFAULT).append("\n");
/*      */       } 
/* 1808 */       asb.toAttributedString().println(this.terminal);
/* 1809 */       if (hueAngle != -1) {
/* 1810 */         int dAngle = 5;
/* 1811 */         int zero = (int)(hueAngle - dAngle / 2.0D * (barSize - 1));
/* 1812 */         zero -= zero % 5;
/* 1813 */         AttributedStringBuilder asb2 = new AttributedStringBuilder();
/* 1814 */         for (int i = 0; i < barSize; i++) {
/* 1815 */           int angle = zero + dAngle * i;
/* 1816 */           while (angle < 0) {
/* 1817 */             angle += 360;
/*      */           }
/* 1819 */           while (angle > 360) {
/* 1820 */             angle -= 360;
/*      */           }
/* 1822 */           rgb = hue2rgb(angle);
/* 1823 */           this.r = rgb[0];
/* 1824 */           this.g = rgb[1];
/* 1825 */           this.b = rgb[2];
/* 1826 */           AttributedStyle s = (new StyleResolver(this::getStyleRGB)).resolve(".hue" + angle);
/* 1827 */           asb2.style(s);
/* 1828 */           asb2.append(" ").append(addPadding(3, "" + angle)).append(" ");
/*      */         } 
/* 1830 */         asb2.style(AttributedStyle.DEFAULT).append("\n");
/* 1831 */         asb2.toAttributedString().println(this.terminal);
/*      */       } 
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
/*      */   public static void highlighter(LineReader lineReader, Terminal terminal, PrintStream out, PrintStream err, String[] argv, ConfigurationPath configPath) throws Options.HelpException {
/* 1855 */     String[] usage = { "highlighter -  manage nanorc theme system", "Usage: highlighter [OPTIONS]", "  -? --help                       Displays command help", "  -c --columns=COLUMNS            Number of columns in theme view", "  -l --list                       List available nanorc themes", "  -r --refresh                    Refresh highlighter config", "  -s --switch=THEME               Switch nanorc theme", "  -v --view=THEME                 View nanorc theme" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1865 */     Options opt = Options.compile(usage).parse((Object[])argv);
/* 1866 */     if (opt.isSet("help")) {
/* 1867 */       throw new Options.HelpException(opt.usage());
/*      */     }
/*      */     try {
/* 1870 */       if (opt.isSet("refresh")) {
/* 1871 */         lineReader.getHighlighter().refresh(lineReader);
/* 1872 */       } else if (opt.isSet("switch")) {
/* 1873 */         Path userConfig = configPath.getUserConfig("jnanorc");
/* 1874 */         if (userConfig != null) {
/* 1875 */           SyntaxHighlighter sh = SyntaxHighlighter.build(userConfig, null);
/* 1876 */           Path currentTheme = sh.getCurrentTheme();
/* 1877 */           String newTheme = replaceFileName(currentTheme, opt.get("switch"));
/* 1878 */           File themeFile = new File(newTheme);
/* 1879 */           if (themeFile.exists()) {
/* 1880 */             switchTheme(err, userConfig, newTheme);
/* 1881 */             Path lessConfig = configPath.getUserConfig("jlessrc");
/* 1882 */             if (lessConfig != null) {
/* 1883 */               switchTheme(err, lessConfig, newTheme);
/*      */             }
/* 1885 */             lineReader.getHighlighter().refresh(lineReader);
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1889 */         Path config = configPath.getConfig("jnanorc");
/*      */         
/* 1891 */         Path currentTheme = (config != null) ? SyntaxHighlighter.build(config, null).getCurrentTheme() : null;
/* 1892 */         if (currentTheme != null)
/* 1893 */           if (opt.isSet("list"))
/* 1894 */           { String parameter = replaceFileName(currentTheme, "*.nanorctheme");
/* 1895 */             out.println(currentTheme.getParent() + ":");
/* 1896 */             PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + parameter);
/* 1897 */             Stream<Path> pathStream = Files.walk(Paths.get((new File(parameter)).getParent(), new String[0]), new java.nio.file.FileVisitOption[0]); 
/* 1898 */             try { Objects.requireNonNull(pathMatcher); pathStream.filter(pathMatcher::matches).forEach(p -> out.println(p.getFileName()));
/* 1899 */               if (pathStream != null) pathStream.close();  } catch (Throwable throwable) { if (pathStream != null)
/*      */                 try { pathStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*      */              }
/* 1902 */           else { Path themeFile; if (opt.isSet("view")) {
/* 1903 */               themeFile = Paths.get(replaceFileName(currentTheme, opt.get("view")), new String[0]);
/*      */             } else {
/* 1905 */               themeFile = currentTheme;
/*      */             } 
/* 1907 */             out.println(themeFile.toAbsolutePath());
/* 1908 */             BufferedReader reader = Files.newBufferedReader(themeFile);
/*      */             
/* 1910 */             try { List<List<String>> tokens = new ArrayList<>();
/* 1911 */               int maxKeyLen = 0;
/* 1912 */               int maxValueLen = 0; String line;
/* 1913 */               while ((line = reader.readLine()) != null) {
/* 1914 */                 line = line.trim();
/* 1915 */                 if (!line.isEmpty() && !line.startsWith("#")) {
/* 1916 */                   List<String> parts = Arrays.asList(line.split("\\s+", 2));
/* 1917 */                   if (((String)parts.get(0)).matches("[A-Z_]+")) {
/* 1918 */                     if (((String)parts.get(0)).length() > maxKeyLen) {
/* 1919 */                       maxKeyLen = ((String)parts.get(0)).length();
/*      */                     }
/* 1921 */                     if (((String)parts.get(1)).length() > maxValueLen) {
/* 1922 */                       maxValueLen = ((String)parts.get(1)).length();
/*      */                     }
/* 1924 */                     tokens.add(parts);
/*      */                   } 
/*      */                 } 
/*      */               } 
/* 1928 */               AttributedStringBuilder asb = new AttributedStringBuilder();
/* 1929 */               maxKeyLen += 2;
/* 1930 */               maxValueLen++;
/* 1931 */               int cols = opt.isSet("columns") ? opt.getNumber("columns") : 2;
/* 1932 */               List<Integer> tabstops = new ArrayList<>();
/* 1933 */               for (int c = 0; c < cols; c++) {
/* 1934 */                 tabstops.add(Integer.valueOf((c + 1) * maxKeyLen + c * maxValueLen));
/* 1935 */                 tabstops.add(Integer.valueOf((c + 1) * maxKeyLen + (c + 1) * maxValueLen));
/*      */               } 
/* 1937 */               asb.tabs(tabstops);
/* 1938 */               int ind = 0;
/* 1939 */               for (List<String> token : tokens) {
/* 1940 */                 asb.style(AttributedStyle.DEFAULT).append(" ");
/* 1941 */                 asb.style(compileStyle("token" + ind++, token.get(1)));
/* 1942 */                 asb.append(token.get(0)).append("\t");
/* 1943 */                 asb.append(token.get(1));
/* 1944 */                 asb.style(AttributedStyle.DEFAULT).append("\t");
/* 1945 */                 if (ind % cols == 0) {
/* 1946 */                   asb.style(AttributedStyle.DEFAULT).append("\n");
/*      */                 }
/*      */               } 
/* 1949 */               asb.toAttributedString().println(terminal);
/* 1950 */               if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null)
/*      */                 try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  }
/*      */            
/*      */       } 
/* 1954 */     } catch (Exception e) {
/* 1955 */       err.println(e.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void switchTheme(PrintStream err, Path config, String theme) {
/*      */     
/* 1967 */     try { Stream<String> stream = Files.lines(config, StandardCharsets.UTF_8);
/*      */ 
/*      */       
/* 1970 */       try { List<String> list = (List<String>)stream.map(line -> line.matches("\\s*theme\\s+.*") ? ("theme " + theme) : line).collect(Collectors.toList());
/* 1971 */         Files.write(config, (Iterable)list, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]);
/* 1972 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 1973 */     { err.println(e.getMessage()); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String replaceFileName(Path path, String name) {
/* 1985 */     int nameLength = path.getFileName().toString().length();
/* 1986 */     int pathLength = path.toString().length();
/* 1987 */     return (path.toString().substring(0, pathLength - nameLength) + name).replace("\\", "\\\\");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static AttributedStyle compileStyle(String reference, String colorDef) {
/* 1998 */     Map<String, String> spec = new HashMap<>();
/* 1999 */     spec.put(reference, colorDef);
/* 2000 */     Styles.StyleCompiler sh = new Styles.StyleCompiler(spec, true);
/* 2001 */     Objects.requireNonNull(sh); return (new StyleResolver(sh::getStyle)).resolve("." + reference);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\Commands.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */