package com.artion.androiddemos.acts;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NettyDemo extends BaseActivity {
	private Button btn1, btn2, btn3;
	
	public static final String HOST = "/websocket.yunzhijia.com/xuntong/websocket";
    public static int PORT = 80;
    private NioEventLoopGroup group;
    
    private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		
		setContentView(R.layout.act_button);
		
		initLayout();
		initListener();
	}
	
	// 连接到Socket服务端
    private void connected() {
        new Thread() {
            @Override
            public void run() {
                 group = new NioEventLoopGroup();
                try {
                    // Client服务启动器 3.x的ClientBootstrap
                    // 改为Bootstrap，且构造函数变化很大，这里用无参构造。
                    Bootstrap bootstrap = new Bootstrap();
                    // 指定EventLoopGroup
                    bootstrap.group(group);
                    // 指定channel类型
                    bootstrap.channel(NioSocketChannel.class);
                    bootstrap.option(ChannelOption.SO_KEEPALIVE, true); 
                    // 指定Handler
                    bootstrap.handler(new MyClientInitializer(context));
                    // 连接到目标IP的8000端口的服务端
                    Channel channel = bootstrap.connect(new InetSocketAddress(HOST,
                                            PORT)).sync().channel();
                    channel.writeAndFlush("我是客户端，我是客户端！");
                    channel.read();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                	e.printStackTrace();
				}
            }
        }.start();
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(group!=null){
            group.shutdownGracefully();
        }
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		btn1.setText("连接");
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				connected();
			}
		});
	}

	
	public class MyClientInitializer extends ChannelInitializer {
		
		public MyClientInitializer(Context context) {
			
		}

		@Override
		protected void initChannel(Channel ch) throws Exception {
			ch.pipeline().addLast(new TimeClientHandler());
		}
		
	}
	
	public class TimeClientHandler  extends ChannelInboundHandlerAdapter {

		@Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {
	        try {
	            System.out.println(msg.toString());
	            ctx.close();
	        } finally {
	        }
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();
	        ctx.close();
	    }

	}

}
