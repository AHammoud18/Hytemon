/*     */ package org.jline.console.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jline.builtins.Completers;
/*     */ import org.jline.builtins.Options;
/*     */ import org.jline.console.ArgDesc;
/*     */ import org.jline.console.CmdDesc;
/*     */ import org.jline.console.CommandRegistry;
/*     */ import org.jline.reader.Completer;
/*     */ import org.jline.reader.impl.completer.ArgumentCompleter;
/*     */ import org.jline.reader.impl.completer.NullCompleter;
/*     */ import org.jline.utils.AttributedString;
/*     */ import org.jline.utils.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JlineCommandRegistry
/*     */   extends AbstractCommandRegistry
/*     */ {
/*     */   public List<String> commandInfo(String command) {
/*     */     try {
/*  40 */       Object[] args = { "--help" };
/*  41 */       if (command.equals("help")) {
/*  42 */         args = new Object[0];
/*     */       }
/*  44 */       invoke(new CommandRegistry.CommandSession(), command, args);
/*  45 */     } catch (org.jline.builtins.Options.HelpException e) {
/*  46 */       return compileCommandInfo(e.getMessage());
/*  47 */     } catch (Exception e) {
/*  48 */       Log.info(new Object[] { "Error while getting command info", e });
/*  49 */       if (Log.isDebugEnabled()) {
/*  50 */         e.printStackTrace();
/*     */       }
/*  52 */       return new ArrayList<>();
/*     */     } 
/*  54 */     throw new IllegalArgumentException("JlineCommandRegistry.commandInfo() method must be overridden in class " + 
/*  55 */         getClass().getCanonicalName());
/*     */   }
/*     */   
/*     */   public CmdDesc commandDescription(List<String> args) {
/*  59 */     String command = (args != null && !args.isEmpty()) ? args.get(0) : "";
/*     */     try {
/*  61 */       invoke(new CommandRegistry.CommandSession(), command, new Object[] { "--help" });
/*  62 */     } catch (org.jline.builtins.Options.HelpException e) {
/*  63 */       return compileCommandDescription(e.getMessage());
/*  64 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  67 */     throw new IllegalArgumentException("JlineCommandRegistry.commandDescription() method must be overridden in class " + 
/*     */         
/*  69 */         getClass().getCanonicalName());
/*     */   }
/*     */   
/*     */   public List<Completers.OptDesc> commandOptions(String command) {
/*     */     try {
/*  74 */       invoke(new CommandRegistry.CommandSession(), command, new Object[] { "--help" });
/*  75 */     } catch (org.jline.builtins.Options.HelpException e) {
/*  76 */       return compileCommandOptions(e.getMessage());
/*  77 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public List<Completer> defaultCompleter(String command) {
/*  84 */     List<Completer> completers = new ArrayList<>();
/*  85 */     completers.add(new ArgumentCompleter(new Completer[] { (Completer)NullCompleter.INSTANCE, (Completer)new Completers.OptionCompleter((Completer)NullCompleter.INSTANCE, this::commandOptions, 1) }));
/*     */     
/*  87 */     return completers;
/*     */   }
/*     */   
/*     */   public Options parseOptions(String[] usage, Object[] args) throws Options.HelpException {
/*  91 */     Options opt = Options.compile(usage).parse(args);
/*  92 */     if (opt.isSet("help")) {
/*  93 */       throw new Options.HelpException(opt.usage());
/*     */     }
/*  95 */     return opt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AttributedString highlightComment(String comment) {
/* 102 */     return Options.HelpException.highlightComment(comment, Options.HelpException.defaultStyle());
/*     */   }
/*     */   
/*     */   private static String[] helpLines(String helpMessage, boolean body) {
/* 106 */     return (new HelpLines(helpMessage, body)).lines();
/*     */   }
/*     */   
/*     */   private static class HelpLines {
/*     */     private final String helpMessage;
/*     */     private final boolean body;
/*     */     private boolean subcommands;
/*     */     
/*     */     public HelpLines(String helpMessage, boolean body) {
/* 115 */       this.helpMessage = helpMessage;
/* 116 */       this.body = body;
/*     */     }
/*     */     
/*     */     public String[] lines() {
/* 120 */       String out = "";
/* 121 */       Matcher tm = Pattern.compile("(^|\\n)(Usage|Summary)(:)").matcher(this.helpMessage);
/* 122 */       if (tm.find()) {
/* 123 */         this.subcommands = tm.group(2).matches("Summary");
/* 124 */         if (this.body) {
/* 125 */           out = this.helpMessage.substring(tm.end(3));
/*     */         } else {
/* 127 */           out = this.helpMessage.substring(0, tm.start(1));
/*     */         } 
/* 129 */       } else if (!this.body) {
/* 130 */         out = this.helpMessage;
/*     */       } 
/* 132 */       return out.split("\\r?\\n");
/*     */     }
/*     */     
/*     */     public boolean subcommands() {
/* 136 */       return this.subcommands;
/*     */     }
/*     */   }
/*     */   
/*     */   public static CmdDesc compileCommandDescription(String helpMessage) {
/* 141 */     List<AttributedString> main = new ArrayList<>();
/* 142 */     Map<String, List<AttributedString>> options = new HashMap<>();
/* 143 */     String prevOpt = null;
/* 144 */     boolean mainDone = false;
/* 145 */     HelpLines hl = new HelpLines(helpMessage, true);
/* 146 */     for (String s : hl.lines()) {
/* 147 */       if (s.matches("^\\s+-.*$")) {
/* 148 */         mainDone = true;
/* 149 */         int ind = s.lastIndexOf("  ");
/* 150 */         if (ind > 0) {
/* 151 */           String o = s.substring(0, ind);
/* 152 */           String d = s.substring(ind);
/* 153 */           if (!o.trim().isEmpty()) {
/* 154 */             prevOpt = o.trim();
/* 155 */             options.put(prevOpt, new ArrayList<>(Collections.singletonList(highlightComment(d.trim()))));
/*     */           } 
/*     */         } 
/* 158 */       } else if (s.matches("^[\\s]{20}.*$") && prevOpt != null && options.containsKey(prevOpt)) {
/* 159 */         int ind = s.lastIndexOf("  ");
/* 160 */         if (ind > 0) {
/* 161 */           ((List<AttributedString>)options.get(prevOpt)).add(highlightComment(s.substring(ind).trim()));
/*     */         }
/*     */       } else {
/* 164 */         prevOpt = null;
/*     */       } 
/* 166 */       if (!mainDone) {
/* 167 */         main.add(Options.HelpException.highlightSyntax(s.trim(), Options.HelpException.defaultStyle(), hl.subcommands()));
/*     */       }
/*     */     } 
/* 170 */     return new CmdDesc(main, ArgDesc.doArgNames(Collections.singletonList("")), options);
/*     */   }
/*     */   
/*     */   public static List<Completers.OptDesc> compileCommandOptions(String helpMessage) {
/* 174 */     List<Completers.OptDesc> out = new ArrayList<>();
/* 175 */     for (String s : helpLines(helpMessage, true)) {
/* 176 */       if (s.matches("^\\s+-.*$")) {
/* 177 */         int ind = s.lastIndexOf("  ");
/* 178 */         if (ind > 0) {
/* 179 */           String[] op = s.substring(0, ind).trim().split("\\s+");
/* 180 */           String d = s.substring(ind).trim();
/* 181 */           String so = null;
/* 182 */           String lo = null;
/* 183 */           if (op.length == 1) {
/* 184 */             if (op[0].startsWith("--")) {
/* 185 */               lo = op[0];
/*     */             } else {
/* 187 */               so = op[0];
/*     */             } 
/*     */           } else {
/* 190 */             so = op[0];
/* 191 */             lo = op[1];
/*     */           } 
/* 193 */           boolean hasValue = false;
/* 194 */           if (lo != null && lo.contains("=")) {
/* 195 */             hasValue = true;
/* 196 */             lo = lo.split("=")[0];
/*     */           } 
/* 198 */           out.add(new Completers.OptDesc(so, lo, d, hasValue ? (Completer)Completers.AnyCompleter.INSTANCE : null));
/*     */         } 
/*     */       } 
/*     */     } 
/* 202 */     return out;
/*     */   }
/*     */   
/*     */   public static List<String> compileCommandInfo(String helpMessage) {
/* 206 */     List<String> out = new ArrayList<>();
/* 207 */     boolean first = true;
/* 208 */     for (String s : helpLines(helpMessage, false)) {
/* 209 */       if (first && s.contains(" - ")) {
/* 210 */         out.add(s.substring(s.indexOf(" - ") + 3).trim());
/*     */       } else {
/* 212 */         out.add(s.trim());
/*     */       } 
/* 214 */       first = false;
/*     */     } 
/* 216 */     return out;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\impl\JlineCommandRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */