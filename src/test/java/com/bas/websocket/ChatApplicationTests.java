package com.bas.websocket;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.completions.CompletionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

@SpringBootTest
class ChatApplicationTests {

	@Test
	void contextLoads() {
		OpenAiService service = new OpenAiService("sk-rLEtBvwqM7Do85Icpo8yT3BlbkFJB5LkoGZ9gbWcgJiUwjCj1");
		CompletionRequest completionRequest = CompletionRequest.builder()
				.prompt("Somebody once told me the world is gonna roll me")
				.model("babbage-002")
						.echo(true)
						.build();
		service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
	}

	@Test
	public void test01() {

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 23457));
		OpenAiClient openAiClient = OpenAiClient.builder()
				.apiKey("sk-rLEtBvwqM7Do85Icpo8yT3BlbkFJB5LkoGZ9gbWcgJiUwjCj1")
				.connectTimeout(5000)
				.writeTimeout(5000)
				.readTimeout(5000)
				.proxy(proxy)
				//.interceptor(Collections.singletonList(httpLoggingInterceptor))
				.apiHost("https://api.openai.com/")
				.build();
		openAiClient.model("gpt-3.5-turbo");
		CompletionResponse completions = openAiClient.completions("你是openAi吗");
		System.out.println(completions.getChoices().length);
//		Arrays.stream(completions.getChoices()).forEach(System.out::println);
	}

}
