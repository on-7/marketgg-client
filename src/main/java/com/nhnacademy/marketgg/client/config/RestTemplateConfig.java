package com.nhnacademy.marketgg.client.config;

import com.nhnacademy.marketgg.client.interceptor.JwtAddInterceptor;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.time.Duration;
import java.util.Optional;
import javax.net.ssl.SSLContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 관련 설정을 처리합니다.
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

    /**
     * RestTemplate 을 원하는 값으로 설정 후 반환합니다.
     *
     * @param builder - RestTemplate 의 설정을 변경할 수 있는 Builder 객체입니다.
     * @return 원하는 값으로 설정한 RestTemplate 객체를 반환합니다.
     * @author 박세완, 윤동열
     * @since 1.0.0
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofSeconds(10L))
                .setConnectTimeout(Duration.ofSeconds(5L))
                .interceptors(new JwtAddInterceptor())
                .build();
    }

    /**
     * {@link HttpClient} 인터페이스의 구현 클래스 빈을 생성합니다.
     * 단, 커넥션 풀을 사용하려면 호출하는 API 서버가 Keep-Alive 를 지원해야 합니다.
     *
     * @param keystoreType     - 인증서 종류
     * @param keystorePath     - 인증서 경로
     * @param keystorePassword - 인증서 비밀번호
     * @param protocol         - 인증 프로토콜
     * @return {@link org.apache.http.impl.client.DefaultHttpClient} 와 달리 커넥션 풀 지원이 가능한 CloseableHttpClient
     * @author 윤동열, 이제훈
     * @author 이제훈
     */
    @Bean
    CloseableHttpClient httpClient(final @Value("${gg.keystore.type}") String keystoreType,
                                   final @Value("${gg.keystore.path}") String keystorePath,
                                   final @Value("${gg.keystore.password}") String keystorePassword,
                                   final @Value("${gg.protocol}") String protocol) {

        SSLContext sslContext;

        try {

            KeyStore clientStore = KeyStore.getInstance(keystoreType);
            Resource resource = new ClassPathResource(keystorePath);
            clientStore.load(resource.getInputStream(), keystorePassword.toCharArray());

            sslContext = SSLContexts.custom()
                                    .setProtocol(protocol)
                                    .loadKeyMaterial(clientStore, keystorePassword.toCharArray())
                                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                                    .build();

        } catch (GeneralSecurityException gse) {
            log.error("", gse);
            throw new IllegalArgumentException(gse);
        } catch (IOException ie) {
            log.error("", ie);
            throw new UncheckedIOException(ie);
        }

        SSLConnectionSocketFactory socketFactory
                = new SSLConnectionSocketFactory(Optional.ofNullable(sslContext).orElseThrow());

        return HttpClients.custom()
                          .setSSLSocketFactory(socketFactory)
                          .setMaxConnTotal(100)
                          .setMaxConnPerRoute(5)
                          .build();
    }

    /**
     * {@link org.springframework.http.client.ClientHttpRequestFactory} 의 구현 클래스 빈을 생성합니다.
     *
     * @param httpClient - HTTP 통신에 필요한 인스턴스
     * @return {@link org.springframework.http.client.ClientHttpRequestFactory} 구현 클래스
     * @author 이제훈
     */
    @Bean
    HttpComponentsClientHttpRequestFactory requestFactory(final HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        requestFactory.setReadTimeout(5_000);
        requestFactory.setConnectTimeout(3_000);
        requestFactory.setHttpClient(httpClient);

        return requestFactory;
    }

    /**
     * 클라이언트 인증서 및 커넥션 풀에서 관리하는 RestTemplate 인스턴스를 생성합니다.
     *
     * @param requestFactory - SSL 클라이언트 인증 및 연결 관련 설정 정보가 담긴 요청 팩토리
     * @return SSL 클라이언트 인증을 통한 RestTemplate 빈
     * @author 이제훈
     */
    @Bean(name = "clientCertificateAuthenticationRestTemplate")
    public RestTemplate clientCertificationRestTemplate(final HttpComponentsClientHttpRequestFactory requestFactory) {
        return new RestTemplate(requestFactory);
    }

}
