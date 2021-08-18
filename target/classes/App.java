
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.netty.http.server.HttpServer; 
import repository.StockRepository; 
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import service.StockService;

public class App { 
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static void main(String[] args) throws URISyntaxException {
        Path testHTML = Paths.get(App.class.getResource("/resources/error.html").toURI());
        System.out.println("Hello Would"); 
        CqlSession session = CqlSession.builder().build();
        StockRepository stockRepository = new StockRepository(session); 
        StockService stockService = new StockService(stockRepository); 
        stockService.addStock("TEST", 9000.01);

        HttpServer.create()
            .port(8080)
            .route(routes ->
                routes.get("/stocks", (request, response) ->
                        response.send(stockService.getAll().map(App::toByteBuf)
                                .log("http-server")))
                    .get("/stocks/{param}", (request, response) ->
                     response.send(stockService.get(request.param("param")).map(App::toByteBuf)
                                .log("http-server")))
                    .get("/test", (request, response) ->
                            response.status(404).addHeader("Message", "Test HTML Page")
                                    .sendFile(testHTML))
                    )
            .bindNow()
            .onDispose()
            .block();
    } 
    
    

    static ByteBuf toByteBuf(Object input) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            OBJECT_MAPPER.writeValue(out, input);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
    }
}