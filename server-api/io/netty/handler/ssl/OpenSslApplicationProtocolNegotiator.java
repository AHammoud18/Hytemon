package io.netty.handler.ssl;

@Deprecated
public interface OpenSslApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {
  ApplicationProtocolConfig.Protocol protocol();
  
  ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior();
  
  ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\ssl\OpenSslApplicationProtocolNegotiator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */