/*     */ package org.jline.widget;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jline.keymap.KeyMap;
/*     */ import org.jline.reader.Binding;
/*     */ import org.jline.reader.Buffer;
/*     */ import org.jline.reader.LineReader;
/*     */ import org.jline.reader.Reference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutopairWidgets
/*     */   extends Widgets
/*     */ {
/*  42 */   private final Map<String, Binding> defaultBindings = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*  46 */   private final Map<String, String> pairs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final Map<String, String> LBOUNDS = new HashMap<>(); static {
/*  57 */     LBOUNDS.put("all", "[.:/\\!]");
/*  58 */     LBOUNDS.put("quotes", "[\\]})a-zA-Z0-9]");
/*  59 */     LBOUNDS.put("spaces", "[^{(\\[]");
/*  60 */     LBOUNDS.put("braces", "");
/*  61 */     LBOUNDS.put("`", "`");
/*  62 */     LBOUNDS.put("\"", "\"");
/*  63 */     LBOUNDS.put("'", "'");
/*  64 */   } private static final Map<String, String> RBOUNDS = new HashMap<>(); private boolean enabled; static {
/*  65 */     RBOUNDS.put("all", "[\\[{(<,.:?/%$!a-zA-Z0-9]");
/*  66 */     RBOUNDS.put("quotes", "[a-zA-Z0-9]");
/*  67 */     RBOUNDS.put("spaces", "[^\\]})]");
/*  68 */     RBOUNDS.put("braces", "");
/*  69 */     RBOUNDS.put("`", "");
/*  70 */     RBOUNDS.put("\"", "");
/*  71 */     RBOUNDS.put("'", "");
/*     */   }
/*     */   
/*     */   public AutopairWidgets(LineReader reader) {
/*  75 */     this(reader, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public AutopairWidgets(LineReader reader, boolean addCurlyBrackets) {
/*  80 */     super(reader); this.pairs.put("`", "`"); this.pairs.put("'", "'"); this.pairs.put("\"", "\""); this.pairs.put("[", "]"); this.pairs.put("(", ")"); this.pairs.put(" ", " ");
/*  81 */     if (existsWidget("_autopair-insert")) {
/*  82 */       throw new IllegalStateException("AutopairWidgets already created!");
/*     */     }
/*  84 */     if (addCurlyBrackets) {
/*  85 */       this.pairs.put("{", "}");
/*     */     }
/*  87 */     addWidget("_autopair-insert", this::autopairInsert);
/*  88 */     addWidget("_autopair-close", this::autopairClose);
/*  89 */     addWidget("_autopair-backward-delete-char", this::autopairDelete);
/*  90 */     addWidget("autopair-toggle", this::toggleKeyBindings);
/*     */     
/*  92 */     KeyMap<Binding> map = getKeyMap();
/*  93 */     for (Map.Entry<String, String> p : this.pairs.entrySet()) {
/*  94 */       this.defaultBindings.put(p.getKey(), (Binding)map.getBound(p.getKey()));
/*  95 */       if (!((String)p.getKey()).equals(p.getValue())) {
/*  96 */         this.defaultBindings.put(p.getValue(), (Binding)map.getBound(p.getValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void enable() {
/* 102 */     if (!this.enabled) {
/* 103 */       toggle();
/*     */     }
/*     */   }
/*     */   
/*     */   public void disable() {
/* 108 */     if (this.enabled) {
/* 109 */       toggle();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean toggle() {
/* 114 */     boolean before = this.enabled;
/* 115 */     toggleKeyBindings();
/* 116 */     return !before;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean autopairInsert() {
/* 123 */     if (this.pairs.containsKey(lastBinding())) {
/* 124 */       if (canSkip(lastBinding())) {
/* 125 */         callWidget("forward-char");
/* 126 */       } else if (canPair(lastBinding())) {
/* 127 */         callWidget("self-insert");
/* 128 */         putString(this.pairs.get(lastBinding()));
/* 129 */         callWidget("backward-char");
/*     */       } else {
/* 131 */         callWidget("self-insert");
/*     */       } 
/*     */     } else {
/* 134 */       callWidget("self-insert");
/*     */     } 
/* 136 */     return true;
/*     */   }
/*     */   
/*     */   public boolean autopairClose() {
/* 140 */     if (this.pairs.containsValue(lastBinding()) && currChar().equals(lastBinding())) {
/* 141 */       callWidget("forward-char");
/*     */     } else {
/* 143 */       callWidget("self-insert");
/*     */     } 
/* 145 */     return true;
/*     */   }
/*     */   
/*     */   public boolean autopairDelete() {
/* 149 */     if (this.pairs.containsKey(prevChar()) && ((String)this.pairs.get(prevChar())).equals(currChar()) && canDelete(prevChar())) {
/* 150 */       callWidget("delete-char");
/*     */     }
/* 152 */     callWidget("backward-delete-char");
/* 153 */     return true;
/*     */   }
/*     */   
/*     */   public boolean toggleKeyBindings() {
/* 157 */     if (this.enabled) {
/* 158 */       defaultBindings();
/*     */     } else {
/* 160 */       customBindings();
/*     */     } 
/* 162 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void customBindings() {
/* 169 */     boolean ttActive = tailtipEnabled();
/* 170 */     if (ttActive) {
/* 171 */       callWidget("tailtip-toggle");
/*     */     }
/* 173 */     KeyMap<Binding> map = getKeyMap();
/* 174 */     for (Map.Entry<String, String> p : this.pairs.entrySet()) {
/* 175 */       map.bind(new Reference("_autopair-insert"), p.getKey());
/* 176 */       if (!((String)p.getKey()).equals(p.getValue())) {
/* 177 */         map.bind(new Reference("_autopair-close"), p.getValue());
/*     */       }
/*     */     } 
/* 180 */     aliasWidget("_autopair-backward-delete-char", "backward-delete-char");
/* 181 */     if (ttActive) {
/* 182 */       callWidget("tailtip-toggle");
/*     */     }
/* 184 */     this.enabled = true;
/*     */   }
/*     */   
/*     */   private void defaultBindings() {
/* 188 */     KeyMap<Binding> map = getKeyMap();
/* 189 */     for (Map.Entry<String, String> p : this.pairs.entrySet()) {
/* 190 */       map.bind(this.defaultBindings.get(p.getKey()), p.getKey());
/* 191 */       if (!((String)p.getKey()).equals(p.getValue())) {
/* 192 */         map.bind(this.defaultBindings.get(p.getValue()), p.getValue());
/*     */       }
/*     */     } 
/* 195 */     aliasWidget(".backward-delete-char", "backward-delete-char");
/* 196 */     if (tailtipEnabled()) {
/* 197 */       callWidget("tailtip-toggle");
/* 198 */       callWidget("tailtip-toggle");
/*     */     } 
/* 200 */     this.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tailtipEnabled() {
/* 206 */     return getWidget("accept-line").equals("_tailtip-accept-line");
/*     */   }
/*     */   
/*     */   private boolean canPair(String d) {
/* 210 */     if (balanced(d) && !nexToBoundary(d)) {
/* 211 */       return (!d.equals(" ") || (!prevChar().equals(" ") && !currChar().equals(" ")));
/*     */     }
/* 213 */     return false;
/*     */   }
/*     */   
/*     */   private boolean canSkip(String d) {
/* 217 */     return (((String)this.pairs.get(d)).equals(d) && d.charAt(0) != ' ' && currChar().equals(d) && balanced(d));
/*     */   }
/*     */   
/*     */   private boolean canDelete(String d) {
/* 221 */     return balanced(d);
/*     */   }
/*     */   
/*     */   private boolean balanced(String d) {
/* 225 */     boolean out = false;
/* 226 */     Buffer buf = buffer();
/* 227 */     String lbuf = buf.upToCursor();
/* 228 */     String rbuf = buf.substring(lbuf.length());
/* 229 */     String regx1 = ((String)this.pairs.get(d)).equals(d) ? d : ("\\" + d);
/* 230 */     String regx2 = ((String)this.pairs.get(d)).equals(d) ? this.pairs.get(d) : ("\\" + (String)this.pairs.get(d));
/* 231 */     int llen = lbuf.length() - lbuf.replaceAll(regx1, "").length();
/* 232 */     int rlen = rbuf.length() - rbuf.replaceAll(regx2, "").length();
/* 233 */     if (llen == 0 && rlen == 0) {
/* 234 */       out = true;
/* 235 */     } else if (d.charAt(0) == ' ') {
/* 236 */       out = true;
/* 237 */     } else if (((String)this.pairs.get(d)).equals(d)) {
/* 238 */       if (llen == rlen || (llen + rlen) % 2 == 0) {
/* 239 */         out = true;
/*     */       }
/*     */     } else {
/* 242 */       int l2len = lbuf.length() - lbuf.replaceAll(regx2, "").length();
/* 243 */       int r2len = rbuf.length() - rbuf.replaceAll(regx1, "").length();
/* 244 */       int ltotal = llen - l2len;
/* 245 */       int rtotal = rlen - r2len;
/* 246 */       if (ltotal < 0) {
/* 247 */         ltotal = 0;
/*     */       }
/* 249 */       if (ltotal >= rtotal) {
/* 250 */         out = true;
/*     */       }
/*     */     } 
/* 253 */     return out;
/*     */   }
/*     */   
/*     */   private boolean boundary(String lb, String rb) {
/* 257 */     return ((lb.length() > 0 && prevChar().matches(lb)) || (rb
/* 258 */       .length() > 0 && currChar().matches(rb)));
/*     */   }
/*     */   
/*     */   private boolean nexToBoundary(String d) {
/* 262 */     List<String> bk = new ArrayList<>();
/* 263 */     bk.add("all");
/* 264 */     if (d.matches("['\"`]")) {
/* 265 */       bk.add("quotes");
/* 266 */     } else if (d.matches("[{\\[(<]")) {
/* 267 */       bk.add("braces");
/* 268 */     } else if (d.charAt(0) == ' ') {
/* 269 */       bk.add("spaces");
/*     */     } 
/* 271 */     if (LBOUNDS.containsKey(d) && RBOUNDS.containsKey(d)) {
/* 272 */       bk.add(d);
/*     */     }
/* 274 */     for (String k : bk) {
/* 275 */       if (boundary(LBOUNDS.get(k), RBOUNDS.get(k))) {
/* 276 */         return true;
/*     */       }
/*     */     } 
/* 279 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\widget\AutopairWidgets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */