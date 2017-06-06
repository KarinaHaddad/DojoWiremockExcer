import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.BasicCredentials;
import com.github.tomakehurst.wiremock.matching.MultiValuePattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.squareup.okhttp.*;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class WireMockTest {

    @Test
    public void cenario01MetodoExemplo() throws IOException {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(
                "http://localhost:8080/cenario01/get");

        getRequest.addHeader("accept", "application/json");

        HttpResponse response = httpClient.execute(getRequest);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    @Test
    public void cenario02BuscarSaldo() throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        Request request = new Request.Builder()
                .url("http://localhost:8080/buscarSaldo?nome=" + "Aroldo")
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        //JSON from String to Object
        ClientUser clientU = mapper.readValue(response.body().byteStream(), ClientUser.class);

        Assert.assertEquals(clientU.getSaldo(), 200);
    }

    @Test
    public void cenario03SinalizarInadiplencia() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/application-text");

        Request request = new Request.Builder()
                .url("http://localhost:8080/sinalizarInadiplencia?conta=" + "1234")
                .method("POST", RequestBody.create(mediaType, ""))
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        //JSON from String to Object
        ClientUser clientU = mapper.readValue(response.body().byteStream(), ClientUser.class);

        Assert.assertEquals(clientU.getInadiplencia(), true);
        Assert.assertEquals(clientU.getNome(), "Aroldo");
    }

    @Test
    public void cenario04BuscarContato() throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/application-text");

        Request request = new Request.Builder()
                .url("http://localhost:8080/buscarContato?cpf="+"98757609398")
                .method("POST", RequestBody.create(mediaType, ""))
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        //JSON from String to Object
        ClientUser clientU = mapper.readValue(response.body().byteStream(), ClientUser.class);

        Assert.assertEquals(clientU.getTelefone(), "34538273");

        //Verificação do Header
        Map<String, List<String>> map = response.headers().toMultimap();

        List<String> contentLength = map.get("Content-Length");
        Assert.assertNotNull(contentLength);
        Assert.assertEquals(contentLength.get(0), "5538");

        List<String> accessControl = map.get("Access-Control-Allow-Credentials");
        Assert.assertNotNull(accessControl);
        Assert.assertEquals(accessControl.get(0), "true");

        List<String> cacheControl = map.get("Cache-Control");
        Assert.assertNotNull(cacheControl);
        Assert.assertEquals(cacheControl.get(0), "max-age=0");

        List<String> contentType = map.get("Content-Type");
        Assert.assertNotNull(contentType);
        Assert.assertEquals(contentType.get(0), "application/json; charset=UTF-8");

        List<String> date = map.get("Date");
        Assert.assertNotNull(date);
        Assert.assertEquals(date.get(0), "Sun, 11 Oct 2015 14:33:06 GMT");

        List<String> server = map.get("Server");
        Assert.assertNotNull(server);
        Assert.assertEquals(server.get(0), "spray-can/1.3.3");

        List<String> surrogateControl = map.get("Surrogate-Control");
        Assert.assertNotNull(surrogateControl);
        Assert.assertEquals(surrogateControl.get(0), "max-age=1");

        List<String> marsheryResponder = map.get("X-Mashery-Responder");
        Assert.assertNotNull(marsheryResponder);
        Assert.assertEquals(marsheryResponder.get(0), "prod-j-worker-eu-west-1c-55.mashery.com");

    }

    @Test
    public void cenario05ConsultarLimiteCartaoCredito() throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/application-text");

        //User name: jeff@example.com
        //Password: jeffteenjefftyjeff
        Request request = new Request.Builder()
                .url("http://localhost:8080/consultarLimiteCartaoCredito?cpf="+"98757609333")
                .method("POST", RequestBody.create(mediaType, ""))
                .addHeader("authorization", "Basic amVmZkBleGFtcGxlLmNvbTpqZWZmdGVlbmplZmZ0eWplZmY=")
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        //JSON from String to Object
        ClientUser clientU = mapper.readValue(response.body().byteStream(), ClientUser.class);

        Assert.assertEquals(clientU.getLimiteCC(), 3000);
    }

    @Test
    public void cenario06ComprovantePagamentoIPVA() throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/application-text");

        Request request = new Request.Builder()
                .url("http://localhost:8080/codigo_barras/39990016332400000000005020037726970620000000001")
                .build();

        Response response = client.newCall(request).execute();

        ObjectMapper mapper = new ObjectMapper();
        //JSON from String to Object
        IPVA ipva = mapper.readValue(response.body().byteStream(), IPVA.class);

        Assert.assertEquals(ipva.getCodigo_barras(), "39990016332400000000005020037726970620000000001");

    }

    @Test
    public void cenario07ComprovantePagamentoCartaoCredito() throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/application-text");

        //User name: jeff@example.com
        //Password: jeffteenjefftyjeff
        Request request = new Request.Builder()
                .url("http://localhost:8080/comprovantePagamento?cpf="+"98757609333"+"&tipo="+"CartaoCredito")
                .addHeader("authorization", "Basic amVmZkBleGFtcGxlLmNvbTpqZWZmdGVlbmplZmZ0eWplZmY=")
                .build();

        Response response = client.newCall(request).execute();


        Assert.assertEquals(response.code(), 206);

    }

    @Test
    public void cenario08ComprovantePagamentoTelefone() throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/application-text");

        Request request = new Request.Builder()
                .url("http://localhost:8080/comprovantePagamento?cpf="+"98757609333"+"&tipo="+"Telefone")
                .build();

        Response response = client.newCall(request).execute();


        Assert.assertEquals(response.code(), 406);

    }

}