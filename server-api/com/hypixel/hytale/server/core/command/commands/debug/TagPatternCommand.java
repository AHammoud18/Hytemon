/*    */ package com.hypixel.hytale.server.core.command.commands.debug;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*    */ import com.hypixel.hytale.server.core.asset.type.tagpattern.config.TagPattern;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.AssetArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TagPatternCommand
/*    */   extends CommandBase
/*    */ {
/*    */   @Nonnull
/* 24 */   private static final SingleArgumentType<TagPattern> TAG_PATTERN_ARG_TYPE = (SingleArgumentType<TagPattern>)new AssetArgumentType("server.commands.parsing.argtype.asset.tagpattern.name", TagPattern.class, "server.commands.parsing.argtype.asset.tagpattern.usage");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final RequiredArg<TagPattern> tagPatternArg = withRequiredArg("tagPattern", "server.commands.tagpattern.tagPattern.desc", (ArgumentType)TAG_PATTERN_ARG_TYPE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final RequiredArg<BlockType> blockTypeArg = withRequiredArg("blockType", "server.commands.tagpattern.blockType.desc", (ArgumentType)ArgTypes.BLOCK_TYPE_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TagPatternCommand() {
/* 46 */     super("tagpattern", "server.commands.tagpattern.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void executeSync(@Nonnull CommandContext context) {
/* 51 */     TagPattern tagPattern = (TagPattern)this.tagPatternArg.get(context);
/* 52 */     BlockType blockType = (BlockType)this.blockTypeArg.get(context);
/* 53 */     boolean result = tagPattern.test(blockType.getData().getTags());
/*    */     
/* 55 */     context.sendMessage((result ? Message.translation("server.commands.tagpattern.matches") : Message.translation("server.commands.tagpattern.noMatches"))
/* 56 */         .param("blocktype", blockType.getId())
/* 57 */         .param("pattern", tagPattern.getId()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\commands\debug\TagPatternCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */