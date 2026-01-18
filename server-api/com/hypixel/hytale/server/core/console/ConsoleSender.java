/*    */ package com.hypixel.hytale.server.core.console;
/*    */ 
/*    */ import com.hypixel.hytale.logger.backend.HytaleLoggerBackend;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandSender;
/*    */ import com.hypixel.hytale.server.core.util.MessageUtil;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nonnull;
/*    */ import org.jline.terminal.Terminal;
/*    */ import org.jline.utils.AttributedString;
/*    */ 
/*    */ public class ConsoleSender
/*    */   implements CommandSender {
/* 14 */   public static final ConsoleSender INSTANCE = new ConsoleSender();
/*    */   
/* 16 */   private final UUID uuid = new UUID(0L, 0L);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendMessage(@Nonnull Message message) {
/* 23 */     Terminal terminal = ConsoleModule.get().getTerminal();
/* 24 */     AttributedString attributedString = MessageUtil.toAnsiString(message);
/* 25 */     HytaleLoggerBackend.rawLog(attributedString.toAnsi(terminal));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getDisplayName() {
/* 31 */     return "Console";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public UUID getUuid() {
/* 37 */     return this.uuid;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(@Nonnull String id) {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission(@Nonnull String id, boolean def) {
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\console\ConsoleSender.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */