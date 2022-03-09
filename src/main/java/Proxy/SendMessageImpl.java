package Proxy;

/**
 * @author jinman.zhu@hand-china.com 2022-03-04 16:57
 **/
public class SendMessageImpl implements SendMessage{
	@Override
	public String sendMessage(String s) {
		System.out.println("send message" + s);
		return s;
	}
}
