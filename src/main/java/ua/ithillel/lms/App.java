package ua.ithillel.lms;

import java.util.LinkedHashMap;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.ithillel.lms.coffee.order.CoffeeOrderBoard;
import ua.ithillel.lms.coffee.order.Order;
import ua.ithillel.lms.coffee.order.exception.InvalidOrderNumberException;


/**
 * Hello world!
 */
@SpringBootApplication
@Slf4j
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void run() {
    Map<Integer, Order> orders = new LinkedHashMap<>();
    orders.put(4, new Order(1, "John"));
    orders.put(3, new Order(2, "Peter"));
    orders.put(2, new Order(3, "James"));
    CoffeeOrderBoard cob = new CoffeeOrderBoard(orders);
    cob.add(new Order(4, "Linda"));
    cob.add(new Order(5, "Jane"));
    Order latestOrder = cob.deliver();
    cob.add(new Order(2, "Addy"));
    log.info(latestOrder.getCustomerName() +
        ", please, come to Order board to take your coffee#" + latestOrder.getId());
    try {
      Order readyOrder = cob.deliver(3);
      log.info(readyOrder.getCustomerName() +
          ", please, come to Order board to take your coffee#" + readyOrder.getId());
    } catch (InvalidOrderNumberException e) {
      StringBuilder exceptionMessageBuilder = new StringBuilder("ERRORS: [\n");
      for (StackTraceElement ste : e.getStackTrace()) {
        exceptionMessageBuilder.append("{\n\"module\":\"");
        exceptionMessageBuilder.append(ste.getModuleName());
        exceptionMessageBuilder.append("\",\n\"file\":\"");
        exceptionMessageBuilder.append(ste.getFileName());
        exceptionMessageBuilder.append("\",\n\"class\":\"");
        exceptionMessageBuilder.append(ste.getClassName());
        exceptionMessageBuilder.append("\",\n\"method\":\"");
        exceptionMessageBuilder.append(ste.getMethodName());
        exceptionMessageBuilder.append("\",\n\"line\":");
        exceptionMessageBuilder.append(ste.getLineNumber());
        exceptionMessageBuilder.append("\n},\n");
      }
      exceptionMessageBuilder.append("]");
      log.error(exceptionMessageBuilder.toString());
    }
    log.info(cob.draw());
  }
}
