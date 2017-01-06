package com.stt.NetWorkDemo.Netty.HeartBeat;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public final class MarshallingCodeCFactory {

	/**
	 * 编码器
	 */
	public static MarshallingEncoder buildMarshallingEncoder() {
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration config = new MarshallingConfiguration();
		config.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, config);
		// 构建Netty的MarshallingEncoder对象，MarshallingEncoder用于实现序列化接口的POJO对象序列化为二进制数组
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}

	/**
	 * 解码器
	 */
	public static MarshallingDecoder buildMarshallingDecoder() {
		// 通过Marshalling工具类获取实例对象
		// 参数serial表示的是java序列化工厂对象
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		// 创建MarshallingConfiguration对象，配置版本号
		final MarshallingConfiguration config = new MarshallingConfiguration();
		config.setVersion(5);
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, config);
		// 构建Netty的MashallingDecoder对象
		// 参数是provider和 单个消息序列化后的最大长度
		MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024 * 1024 * 1);
		return decoder;
	}

}
