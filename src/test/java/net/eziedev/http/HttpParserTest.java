package net.eziedev.http;

import net.eziedev.httpserver.exceptions.HttpParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateValidGETTestCase());
        } catch (HttpParsingException e) {
            Assertions.fail(e);
        }
        Assertions.assertEquals(request.getMethod(), HttpMethod.GET);
    }

    @Test
    void parseHttpRequestBadMethod() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(generateInvalidGETTestCaseBadMethod());
            Assertions.fail();
        } catch (HttpParsingException e) {
            Assertions.assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_500_NOT_IMPLEMENTED);
        }
    }

    private InputStream generateValidGETTestCase() {
        String rawData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "sec-ch-ua: \"Chromium\";v=\"124\", \"Google Chrome\";v=\"124\", \"Not-A.Brand\";v=\"99\"\r\n" +
                "sec-ch-ua-mobile: ?0\r\n" +
                "sec-ch-ua-platform: \"macOS\"\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\r\n" +
                "Accept-Language: en-US,en;q=0.9\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }

    private InputStream generateInvalidGETTestCaseBadMethod() {
        String rawData = "GETTER / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "\r\n";

        return new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));
    }
}