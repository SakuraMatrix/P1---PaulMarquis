
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.DisposableServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;


public class App { 
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static void main(String[] args) throws URISyntaxException {
             
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class); 
        appContext.getBean(DisposableServer.class).onDispose().block(); 
        appContext.close();
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