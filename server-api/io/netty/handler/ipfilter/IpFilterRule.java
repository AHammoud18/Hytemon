package io.netty.handler.ipfilter;

import java.net.InetSocketAddress;

public interface IpFilterRule {
  boolean matches(InetSocketAddress paramInetSocketAddress);
  
  IpFilterRuleType ruleType();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ipfilter\IpFilterRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */