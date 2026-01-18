/*     */ package org.jline.console;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class CmdDesc
/*     */ {
/*     */   private List<AttributedString> mainDesc;
/*     */   private List<ArgDesc> argsDesc;
/*     */   private TreeMap<String, List<AttributedString>> optsDesc;
/*     */   private Pattern errorPattern;
/*  32 */   private int errorIndex = -1;
/*     */ 
/*     */   
/*     */   private boolean valid = true;
/*     */ 
/*     */   
/*     */   private boolean command = false;
/*     */   
/*     */   private boolean subcommand = false;
/*     */   
/*     */   private boolean highlighted = true;
/*     */ 
/*     */   
/*     */   public CmdDesc() {
/*  46 */     this.command = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CmdDesc(boolean valid) {
/*  55 */     this.valid = valid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CmdDesc(List<ArgDesc> argsDesc) {
/*  64 */     this(new ArrayList<>(), argsDesc, new HashMap<>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CmdDesc(List<ArgDesc> argsDesc, Map<String, List<AttributedString>> optsDesc) {
/*  74 */     this(new ArrayList<>(), argsDesc, optsDesc);
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
/*     */   public CmdDesc(List<AttributedString> mainDesc, List<ArgDesc> argsDesc, Map<String, List<AttributedString>> optsDesc) {
/*  87 */     this.argsDesc = new ArrayList<>(argsDesc);
/*  88 */     this.optsDesc = new TreeMap<>(optsDesc);
/*  89 */     if (mainDesc.isEmpty() && optsDesc.containsKey("main")) {
/*  90 */       this.mainDesc = new ArrayList<>(optsDesc.get("main"));
/*  91 */       this.optsDesc.remove("main");
/*     */     } else {
/*  93 */       this.mainDesc = new ArrayList<>(mainDesc);
/*     */     } 
/*  95 */     this.command = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/* 104 */     return this.valid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommand() {
/* 113 */     return this.command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubcommand(boolean subcommand) {
/* 122 */     this.subcommand = subcommand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSubcommand() {
/* 131 */     return this.subcommand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHighlighted(boolean highlighted) {
/* 140 */     this.highlighted = highlighted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHighlighted() {
/* 149 */     return this.highlighted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CmdDesc mainDesc(List<AttributedString> mainDesc) {
/* 159 */     this.mainDesc = new ArrayList<>(mainDesc);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMainDesc(List<AttributedString> mainDesc) {
/* 169 */     this.mainDesc = new ArrayList<>(mainDesc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AttributedString> getMainDesc() {
/* 178 */     return this.mainDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeMap<String, List<AttributedString>> getOptsDesc() {
/* 187 */     return this.optsDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorPattern(Pattern errorPattern) {
/* 196 */     this.errorPattern = errorPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pattern getErrorPattern() {
/* 205 */     return this.errorPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setErrorIndex(int errorIndex) {
/* 214 */     this.errorIndex = errorIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getErrorIndex() {
/* 223 */     return this.errorIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ArgDesc> getArgsDesc() {
/* 232 */     return this.argsDesc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean optionWithValue(String option) {
/* 242 */     for (String key : this.optsDesc.keySet()) {
/* 243 */       if (key.matches("(^|.*\\s)" + option + "($|=.*|\\s.*)")) {
/* 244 */         return key.contains("=");
/*     */       }
/*     */     } 
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedString optionDescription(String key) {
/* 257 */     return !((List)this.optsDesc.get(key)).isEmpty() ? ((List<AttributedString>)this.optsDesc.get(key)).get(0) : new AttributedString("");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\console\CmdDesc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */