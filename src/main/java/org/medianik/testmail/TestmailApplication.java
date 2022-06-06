package org.medianik.testmail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.medianik.testmail.model.Book;
import org.medianik.testmail.model.BookItem;
import org.medianik.testmail.model.Market;
import org.medianik.testmail.repository.BookItemRepository;
import org.medianik.testmail.repository.MarketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class TestmailApplication{
    public static void main(String[] args){
        if(args.length >= 1){
            args[0] = createArgForLog4j(args[0]);
        }
        SpringApplication.run(TestmailApplication.class, args);
    }


    private static String createArgForLog4j(String arg){
        return "--logging.file.name=" + arg;
    }
    @Component
    private static class Initializer implements CommandLineRunner{
        private final MarketRepository marketRepository;

        private Initializer(MarketRepository marketRepository){
            this.marketRepository = marketRepository;
        }

        @Override
        public void run(String... args) throws Exception{
            if(args.length >= 2){
                var market = marketRepository.findById(1L).orElseGet(Market::new);

                if(!market.getBooks().isEmpty())
                    return;

                var contents = new ObjectMapper().readTree(new File(args[1]));
                var bookItems = inputToBookItems(contents.get("books"));
                market.getBooks().addAll(bookItems);

                marketRepository.save(market);
            }
        }

        @NotNull
        private List<BookItem> inputToBookItems(JsonNode contents){
            return toStream(contents.iterator(), contents.size())
                .map(node ->
                    new BookItem(
                        new Book(
                            node.get("author").asText(),
                            node.get("author").asText(),
                            node.get("price").decimalValue()
                        ), node.get("amount").asLong()
                    )
                ).toList();
        }

        static <T> Stream<T> toStream(Iterator<T> iterator, int size){
            return StreamSupport
                .stream(
                    Spliterators.spliterator(
                        iterator,
                        size,
                        Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL
                    ),
                    false
                );
        }
    }
}
