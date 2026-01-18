/*     */ package joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class OptionSpecBuilder
/*     */   extends NoArgumentOptionSpec
/*     */ {
/*     */   private final OptionParser parser;
/*     */   
/*     */   OptionSpecBuilder(OptionParser parser, List<String> options, String description) {
/*  63 */     super(options, description);
/*     */     
/*  65 */     this.parser = parser;
/*  66 */     attachToParser();
/*     */   }
/*     */   
/*     */   private void attachToParser() {
/*  70 */     this.parser.recognize(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentAcceptingOptionSpec<String> withRequiredArg() {
/*  79 */     ArgumentAcceptingOptionSpec<String> newSpec = new RequiredArgumentOptionSpec<>(options(), description());
/*  80 */     this.parser.recognize(newSpec);
/*     */     
/*  82 */     return newSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArgumentAcceptingOptionSpec<String> withOptionalArg() {
/*  92 */     ArgumentAcceptingOptionSpec<String> newSpec = new OptionalArgumentOptionSpec<>(options(), description());
/*  93 */     this.parser.recognize(newSpec);
/*     */     
/*  95 */     return newSpec;
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
/*     */   public OptionSpecBuilder requiredIf(String dependent, String... otherDependents) {
/* 111 */     List<String> dependents = validatedDependents(dependent, otherDependents);
/* 112 */     for (String each : dependents) {
/* 113 */       this.parser.requiredIf(options(), each);
/*     */     }
/* 115 */     return this;
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
/*     */   public OptionSpecBuilder requiredIf(OptionSpec<?> dependent, OptionSpec<?>... otherDependents) {
/* 132 */     this.parser.requiredIf(options(), dependent);
/* 133 */     for (OptionSpec<?> each : otherDependents) {
/* 134 */       this.parser.requiredIf(options(), each);
/*     */     }
/* 136 */     return this;
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
/*     */   public OptionSpecBuilder requiredUnless(String dependent, String... otherDependents) {
/* 152 */     List<String> dependents = validatedDependents(dependent, otherDependents);
/* 153 */     for (String each : dependents) {
/* 154 */       this.parser.requiredUnless(options(), each);
/*     */     }
/* 156 */     return this;
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
/*     */   public OptionSpecBuilder requiredUnless(OptionSpec<?> dependent, OptionSpec<?>... otherDependents) {
/* 173 */     this.parser.requiredUnless(options(), dependent);
/* 174 */     for (OptionSpec<?> each : otherDependents) {
/* 175 */       this.parser.requiredUnless(options(), each);
/*     */     }
/* 177 */     return this;
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
/*     */   public OptionSpecBuilder availableIf(String dependent, String... otherDependents) {
/* 193 */     List<String> dependents = validatedDependents(dependent, otherDependents);
/* 194 */     for (String each : dependents) {
/* 195 */       this.parser.availableIf(options(), each);
/*     */     }
/* 197 */     return this;
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
/*     */   public OptionSpecBuilder availableIf(OptionSpec<?> dependent, OptionSpec<?>... otherDependents) {
/* 214 */     this.parser.availableIf(options(), dependent);
/*     */     
/* 216 */     for (OptionSpec<?> each : otherDependents) {
/* 217 */       this.parser.availableIf(options(), each);
/*     */     }
/* 219 */     return this;
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
/*     */   public OptionSpecBuilder availableUnless(String dependent, String... otherDependents) {
/* 235 */     List<String> dependents = validatedDependents(dependent, otherDependents);
/* 236 */     for (String each : dependents) {
/* 237 */       this.parser.availableUnless(options(), each);
/*     */     }
/* 239 */     return this;
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
/*     */   public OptionSpecBuilder availableUnless(OptionSpec<?> dependent, OptionSpec<?>... otherDependents) {
/* 256 */     this.parser.availableUnless(options(), dependent);
/* 257 */     for (OptionSpec<?> each : otherDependents) {
/* 258 */       this.parser.availableUnless(options(), each);
/*     */     }
/* 260 */     return this;
/*     */   }
/*     */   
/*     */   private List<String> validatedDependents(String dependent, String... otherDependents) {
/* 264 */     List<String> dependents = new ArrayList<>();
/* 265 */     dependents.add(dependent);
/* 266 */     Collections.addAll(dependents, otherDependents);
/*     */     
/* 268 */     for (String each : dependents) {
/* 269 */       if (!this.parser.isRecognized(each)) {
/* 270 */         throw new UnconfiguredOptionException(each);
/*     */       }
/*     */     } 
/* 273 */     return dependents;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionSpecBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */