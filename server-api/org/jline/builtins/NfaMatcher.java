/*     */ package org.jline.builtins;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Deque;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NfaMatcher<T>
/*     */ {
/*     */   private final String regexp;
/*     */   private final BiFunction<T, String, Boolean> matcher;
/*     */   private volatile State start;
/*     */   
/*     */   public NfaMatcher(String regexp, BiFunction<T, String, Boolean> matcher) {
/*  46 */     this.regexp = regexp;
/*  47 */     this.matcher = matcher;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void compile() {
/*  58 */     if (this.start == null) {
/*  59 */       this.start = toNfa(toPostFix(this.regexp));
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
/*     */   public boolean match(List<T> args) {
/*  74 */     Set<State> clist = new HashSet<>();
/*  75 */     compile();
/*  76 */     addState(clist, this.start);
/*  77 */     for (T arg : args) {
/*  78 */       Set<State> nlist = new HashSet<>();
/*  79 */       clist.stream()
/*  80 */         .filter(s -> (!Objects.equals("++MATCH++", s.c) && !Objects.equals("++SPLIT++", s.c)))
/*  81 */         .filter(s -> ((Boolean)this.matcher.apply((T)arg, s.c)).booleanValue())
/*  82 */         .forEach(s -> addState(nlist, s.out));
/*  83 */       clist = nlist;
/*     */     } 
/*  85 */     return clist.stream().anyMatch(s -> Objects.equals("++MATCH++", s.c));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> matchPartial(List<T> args) {
/*  95 */     Set<State> clist = new HashSet<>();
/*  96 */     compile();
/*  97 */     addState(clist, this.start);
/*  98 */     for (T arg : args) {
/*  99 */       Set<State> nlist = new HashSet<>();
/* 100 */       clist.stream()
/* 101 */         .filter(s -> (!Objects.equals("++MATCH++", s.c) && !Objects.equals("++SPLIT++", s.c)))
/* 102 */         .filter(s -> ((Boolean)this.matcher.apply((T)arg, s.c)).booleanValue())
/* 103 */         .forEach(s -> addState(nlist, s.out));
/* 104 */       clist = nlist;
/*     */     } 
/* 106 */     return (Set<String>)clist.stream()
/* 107 */       .filter(s -> (!Objects.equals("++MATCH++", s.c) && !Objects.equals("++SPLIT++", s.c)))
/* 108 */       .map(s -> s.c)
/* 109 */       .collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addState(Set<State> l, State s) {
/* 119 */     if (s != null && l.add(s) && 
/* 120 */       Objects.equals("++SPLIT++", s.c)) {
/* 121 */       addState(l, s.out);
/* 122 */       addState(l, s.out1);
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
/*     */   static State toNfa(List<String> postfix) {
/* 134 */     Deque<Frag> stack = new ArrayDeque<>();
/*     */ 
/*     */     
/* 137 */     for (String p : postfix) {
/* 138 */       Frag e1, e2, frag1; switch (p) {
/*     */         case ".":
/* 140 */           e2 = stack.pollLast();
/* 141 */           e1 = stack.pollLast();
/* 142 */           e1.patch(e2.start);
/* 143 */           stack.offerLast(new Frag(e1.start, e2.out));
/*     */           continue;
/*     */         case "|":
/* 146 */           e2 = stack.pollLast();
/* 147 */           e1 = stack.pollLast();
/* 148 */           s = new State("++SPLIT++", e1.start, e2.start);
/* 149 */           stack.offerLast(new Frag(s, e1.out, e2.out));
/*     */           continue;
/*     */         case "?":
/* 152 */           frag1 = stack.pollLast();
/* 153 */           s = new State("++SPLIT++", frag1.start, null);
/* 154 */           Objects.requireNonNull(s); stack.offerLast(new Frag(s, frag1.out, s::setOut1));
/*     */           continue;
/*     */         case "*":
/* 157 */           frag1 = stack.pollLast();
/* 158 */           s = new State("++SPLIT++", frag1.start, null);
/* 159 */           frag1.patch(s);
/* 160 */           Objects.requireNonNull(s); stack.offerLast(new Frag(s, s::setOut1));
/*     */           continue;
/*     */         case "+":
/* 163 */           frag1 = stack.pollLast();
/* 164 */           s = new State("++SPLIT++", frag1.start, null);
/* 165 */           frag1.patch(s);
/* 166 */           Objects.requireNonNull(s); stack.offerLast(new Frag(frag1.start, s::setOut1));
/*     */           continue;
/*     */       } 
/* 169 */       State s = new State(p, null, null);
/* 170 */       Objects.requireNonNull(s); stack.offerLast(new Frag(s, s::setOut));
/*     */     } 
/*     */ 
/*     */     
/* 174 */     Frag e = stack.pollLast();
/* 175 */     if (!stack.isEmpty()) {
/* 176 */       throw new IllegalStateException("Wrong postfix expression, " + stack.size() + " elements remaining");
/*     */     }
/* 178 */     e.patch(new State("++MATCH++", null, null));
/* 179 */     return e.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static List<String> toPostFix(String regexp) {
/* 189 */     List<String> postfix = new ArrayList<>();
/* 190 */     int s = -1;
/* 191 */     int natom = 0;
/* 192 */     int nalt = 0;
/* 193 */     Deque<Integer> natoms = new ArrayDeque<>();
/* 194 */     Deque<Integer> nalts = new ArrayDeque<>();
/* 195 */     for (int i = 0; i < regexp.length(); i++) {
/* 196 */       char c = regexp.charAt(i);
/*     */       
/* 198 */       if (Character.isJavaIdentifierPart(c)) {
/* 199 */         if (s < 0) {
/* 200 */           s = i;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 205 */         if (s >= 0) {
/* 206 */           if (natom > 1) {
/* 207 */             natom--;
/* 208 */             postfix.add(".");
/*     */           } 
/* 210 */           postfix.add(regexp.substring(s, i));
/* 211 */           natom++;
/* 212 */           s = -1;
/*     */         } 
/*     */         
/* 215 */         if (!Character.isWhitespace(c))
/*     */         {
/*     */ 
/*     */           
/* 219 */           switch (c) {
/*     */             case '(':
/* 221 */               if (natom > 1) {
/* 222 */                 natom--;
/* 223 */                 postfix.add(".");
/*     */               } 
/* 225 */               nalts.offerLast(Integer.valueOf(nalt));
/* 226 */               natoms.offerLast(Integer.valueOf(natom));
/* 227 */               nalt = 0;
/* 228 */               natom = 0;
/*     */               break;
/*     */             case '|':
/* 231 */               if (natom == 0) {
/* 232 */                 throw new IllegalStateException("unexpected '" + c + "' at pos " + i);
/*     */               }
/* 234 */               while (--natom > 0) {
/* 235 */                 postfix.add(".");
/*     */               }
/* 237 */               nalt++;
/*     */               break;
/*     */             case ')':
/* 240 */               if (nalts.isEmpty() || natom == 0) {
/* 241 */                 throw new IllegalStateException("unexpected '" + c + "' at pos " + i);
/*     */               }
/* 243 */               while (--natom > 0) {
/* 244 */                 postfix.add(".");
/*     */               }
/* 246 */               for (; nalt > 0; nalt--) {
/* 247 */                 postfix.add("|");
/*     */               }
/* 249 */               nalt = ((Integer)nalts.pollLast()).intValue();
/* 250 */               natom = ((Integer)natoms.pollLast()).intValue();
/* 251 */               natom++;
/*     */               break;
/*     */             case '*':
/*     */             case '+':
/*     */             case '?':
/* 256 */               if (natom == 0) {
/* 257 */                 throw new IllegalStateException("unexpected '" + c + "' at pos " + i);
/*     */               }
/* 259 */               postfix.add(String.valueOf(c));
/*     */               break;
/*     */             default:
/* 262 */               throw new IllegalStateException("unexpected '" + c + "' at pos " + i);
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 266 */     if (s >= 0) {
/* 267 */       if (natom > 1) {
/* 268 */         natom--;
/* 269 */         postfix.add(".");
/*     */       } 
/* 271 */       postfix.add(regexp.substring(s));
/* 272 */       natom++;
/*     */     } 
/*     */     
/* 275 */     while (--natom > 0) {
/* 276 */       postfix.add(".");
/*     */     }
/*     */     
/* 279 */     for (; nalt > 0; nalt--) {
/* 280 */       postfix.add("|");
/*     */     }
/* 282 */     return postfix;
/*     */   }
/*     */ 
/*     */   
/*     */   static class State
/*     */   {
/*     */     static final String Match = "++MATCH++";
/*     */     
/*     */     static final String Split = "++SPLIT++";
/*     */     
/*     */     final String c;
/*     */     
/*     */     State out;
/*     */     State out1;
/*     */     
/*     */     public State(String c, State out, State out1) {
/* 298 */       this.c = c;
/* 299 */       this.out = out;
/* 300 */       this.out1 = out1;
/*     */     }
/*     */     
/*     */     public void setOut(State out) {
/* 304 */       this.out = out;
/*     */     }
/*     */     
/*     */     public void setOut1(State out1) {
/* 308 */       this.out1 = out1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Frag
/*     */   {
/*     */     final NfaMatcher.State start;
/*     */     
/* 317 */     final List<Consumer<NfaMatcher.State>> out = new ArrayList<>();
/*     */     
/*     */     public Frag(NfaMatcher.State start, Collection<Consumer<NfaMatcher.State>> l) {
/* 320 */       this.start = start;
/* 321 */       this.out.addAll(l);
/*     */     }
/*     */     
/*     */     public Frag(NfaMatcher.State start, Collection<Consumer<NfaMatcher.State>> l1, Collection<Consumer<NfaMatcher.State>> l2) {
/* 325 */       this.start = start;
/* 326 */       this.out.addAll(l1);
/* 327 */       this.out.addAll(l2);
/*     */     }
/*     */     
/*     */     public Frag(NfaMatcher.State start, Consumer<NfaMatcher.State> c) {
/* 331 */       this.start = start;
/* 332 */       this.out.add(c);
/*     */     }
/*     */     
/*     */     public Frag(NfaMatcher.State start, Collection<Consumer<NfaMatcher.State>> l, Consumer<NfaMatcher.State> c) {
/* 336 */       this.start = start;
/* 337 */       this.out.addAll(l);
/* 338 */       this.out.add(c);
/*     */     }
/*     */     
/*     */     public void patch(NfaMatcher.State s) {
/* 342 */       this.out.forEach(c -> c.accept(s));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jline\builtins\NfaMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */