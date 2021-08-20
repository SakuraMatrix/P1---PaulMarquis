import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Primary;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.scheduling.annotation.EnableAsync;

import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import service.StockService;

@Configuration  
@EnableAsync 
@EnableMBeanExport
@ComponentScan 
public class AppConfig {  
    @Autowired  
    StockService service;
    @Bean 
    public CqlSession session(){ 
        return CqlSession.builder().build();
    }
    
    @Bean 
    public DisposableServer server() throws URISyntaxException{  
        Path testHTML = Paths.get(App.class.getResource("/resources/error.html").toURI()); 

        return HttpServer.create()
        .port(8080)
        .route(routes ->
            routes.get("/stocks", (request, response) ->
                    response.status(200).send(service.getAll().map(App::toByteBuf)
                            .log("http-server")))
                .get("/stocks/{param}", (request, response) ->
                        response.status(200).send(service.get(request.param("param")).map(App::toByteBuf)
                            .log("http-server"))) 
                .get("/stocks/delete/{param}", (request, response) ->
                        response.send(service.deleteStock(request.param("param")).map(App::toByteBuf)
                            .log("http-server")))
                .get("/test", (request, response) ->
                        response.status(404).addHeader("Message", "Test HTML Page")
                            .sendFile(testHTML)) 
                .get("/stocks/add/{param}", (request, response) ->
                        {
                            try {
                                return response.send(service.addStock(request.param("param")).map(App::toByteBuf)
                                    .log("http-server"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return response;
                        })
                ) 
                
        .bindNow(); 
        
    } 

    @Bean
    public InstrumentationLoadTimeWeaver loadTimeWeaver()  throws Throwable {
    InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
    return loadTimeWeaver;
    } 

    @Primary
    @Bean
    public CacheManager jdkCacheManager() {
        return new ConcurrentMapCacheManager("cache");
    }

    
}
