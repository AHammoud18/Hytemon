/*     */ package org.fusesource.jansi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnsiRenderer
/*     */ {
/*     */   public static final String BEGIN_TOKEN = "@|";
/*     */   public static final String END_TOKEN = "|@";
/*     */   public static final String CODE_TEXT_SEPARATOR = " ";
/*     */   public static final String CODE_LIST_SEPARATOR = ",";
/*     */   private static final int BEGIN_TOKEN_LEN = 2;
/*     */   private static final int END_TOKEN_LEN = 2;
/*     */   
/*     */   public static String render(String input) throws IllegalArgumentException {
/*     */     try {
/*  61 */       return render(input, new StringBuilder()).toString();
/*  62 */     } catch (IOException e) {
/*     */       
/*  64 */       throw new IllegalArgumentException(e);
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
/*     */   public static Appendable render(String input, Appendable target) throws IOException {
/*  81 */     int i = 0;
/*     */ 
/*     */     
/*     */     while (true) {
/*  85 */       int j = input.indexOf("@|", i);
/*  86 */       if (j == -1) {
/*  87 */         if (i == 0) {
/*  88 */           target.append(input);
/*  89 */           return target;
/*     */         } 
/*  91 */         target.append(input.substring(i));
/*  92 */         return target;
/*     */       } 
/*  94 */       target.append(input.substring(i, j));
/*  95 */       int k = input.indexOf("|@", j);
/*     */       
/*  97 */       if (k == -1) {
/*  98 */         target.append(input);
/*  99 */         return target;
/*     */       } 
/* 101 */       j += 2;
/*     */ 
/*     */       
/* 104 */       if (k < j) {
/* 105 */         throw new IllegalArgumentException("Invalid input string found.");
/*     */       }
/* 107 */       String spec = input.substring(j, k);
/*     */       
/* 109 */       String[] items = spec.split(" ", 2);
/* 110 */       if (items.length == 1) {
/* 111 */         target.append(input);
/* 112 */         return target;
/*     */       } 
/* 114 */       String replacement = render(items[1], items[0].split(","));
/*     */       
/* 116 */       target.append(replacement);
/*     */       
/* 118 */       i = k + 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String render(String text, String... codes) {
/* 123 */     return render(Ansi.ansi(), codes).a(text).reset().toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String renderCodes(String... codes) {
/* 132 */     return render(Ansi.ansi(), codes).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String renderCodes(String codes) {
/* 141 */     return renderCodes(codes.split("\\s"));
/*     */   }
/*     */   
/*     */   private static Ansi render(Ansi ansi, String... names) {
/* 145 */     for (String name : names) {
/* 146 */       Code code = Code.valueOf(name.toUpperCase(Locale.ENGLISH));
/* 147 */       if (code.isColor()) {
/* 148 */         if (code.isBackground()) {
/* 149 */           ansi.bg(code.getColor());
/*     */         } else {
/* 151 */           ansi.fg(code.getColor());
/*     */         } 
/* 153 */       } else if (code.isAttribute()) {
/* 154 */         ansi.a(code.getAttribute());
/*     */       } 
/*     */     } 
/* 157 */     return ansi;
/*     */   }
/*     */   
/*     */   public static boolean test(String text) {
/* 161 */     return (text != null && text.contains("@|"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Code
/*     */   {
/* 171 */     BLACK((String)Ansi.Color.BLACK),
/* 172 */     RED((String)Ansi.Color.RED),
/* 173 */     GREEN((String)Ansi.Color.GREEN),
/* 174 */     YELLOW((String)Ansi.Color.YELLOW),
/* 175 */     BLUE((String)Ansi.Color.BLUE),
/* 176 */     MAGENTA((String)Ansi.Color.MAGENTA),
/* 177 */     CYAN((String)Ansi.Color.CYAN),
/* 178 */     WHITE((String)Ansi.Color.WHITE),
/* 179 */     DEFAULT((String)Ansi.Color.DEFAULT),
/*     */ 
/*     */     
/* 182 */     FG_BLACK((String)Ansi.Color.BLACK, false),
/* 183 */     FG_RED((String)Ansi.Color.RED, false),
/* 184 */     FG_GREEN((String)Ansi.Color.GREEN, false),
/* 185 */     FG_YELLOW((String)Ansi.Color.YELLOW, false),
/* 186 */     FG_BLUE((String)Ansi.Color.BLUE, false),
/* 187 */     FG_MAGENTA((String)Ansi.Color.MAGENTA, false),
/* 188 */     FG_CYAN((String)Ansi.Color.CYAN, false),
/* 189 */     FG_WHITE((String)Ansi.Color.WHITE, false),
/* 190 */     FG_DEFAULT((String)Ansi.Color.DEFAULT, false),
/*     */ 
/*     */     
/* 193 */     BG_BLACK((String)Ansi.Color.BLACK, true),
/* 194 */     BG_RED((String)Ansi.Color.RED, true),
/* 195 */     BG_GREEN((String)Ansi.Color.GREEN, true),
/* 196 */     BG_YELLOW((String)Ansi.Color.YELLOW, true),
/* 197 */     BG_BLUE((String)Ansi.Color.BLUE, true),
/* 198 */     BG_MAGENTA((String)Ansi.Color.MAGENTA, true),
/* 199 */     BG_CYAN((String)Ansi.Color.CYAN, true),
/* 200 */     BG_WHITE((String)Ansi.Color.WHITE, true),
/* 201 */     BG_DEFAULT((String)Ansi.Color.DEFAULT, true),
/*     */ 
/*     */     
/* 204 */     RESET((String)Ansi.Attribute.RESET),
/* 205 */     INTENSITY_BOLD((String)Ansi.Attribute.INTENSITY_BOLD),
/* 206 */     INTENSITY_FAINT((String)Ansi.Attribute.INTENSITY_FAINT),
/* 207 */     ITALIC((String)Ansi.Attribute.ITALIC),
/* 208 */     UNDERLINE((String)Ansi.Attribute.UNDERLINE),
/* 209 */     BLINK_SLOW((String)Ansi.Attribute.BLINK_SLOW),
/* 210 */     BLINK_FAST((String)Ansi.Attribute.BLINK_FAST),
/* 211 */     BLINK_OFF((String)Ansi.Attribute.BLINK_OFF),
/* 212 */     NEGATIVE_ON((String)Ansi.Attribute.NEGATIVE_ON),
/* 213 */     NEGATIVE_OFF((String)Ansi.Attribute.NEGATIVE_OFF),
/* 214 */     CONCEAL_ON((String)Ansi.Attribute.CONCEAL_ON),
/* 215 */     CONCEAL_OFF((String)Ansi.Attribute.CONCEAL_OFF),
/* 216 */     UNDERLINE_DOUBLE((String)Ansi.Attribute.UNDERLINE_DOUBLE),
/* 217 */     UNDERLINE_OFF((String)Ansi.Attribute.UNDERLINE_OFF),
/*     */ 
/*     */     
/* 220 */     BOLD((String)Ansi.Attribute.INTENSITY_BOLD),
/* 221 */     FAINT((String)Ansi.Attribute.INTENSITY_FAINT);
/*     */     
/*     */     private final Enum<?> n;
/*     */     
/*     */     private final boolean background;
/*     */     
/*     */     Code(Enum<?> n, boolean background) {
/* 228 */       this.n = n;
/* 229 */       this.background = background;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isColor() {
/* 237 */       return this.n instanceof Ansi.Color;
/*     */     }
/*     */     
/*     */     public Ansi.Color getColor() {
/* 241 */       return (Ansi.Color)this.n;
/*     */     }
/*     */     
/*     */     public boolean isAttribute() {
/* 245 */       return this.n instanceof Ansi.Attribute;
/*     */     }
/*     */     
/*     */     public Ansi.Attribute getAttribute() {
/* 249 */       return (Ansi.Attribute)this.n;
/*     */     }
/*     */     
/*     */     public boolean isBackground() {
/* 253 */       return this.background;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\fusesource\jansi\AnsiRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */