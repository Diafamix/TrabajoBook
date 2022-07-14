package com.optimissa.BookShelfApi.Utils;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class ParseDateFactory {

    private Set<parseStrategy> strategies;

    @PostConstruct
    public void preLoad() {
        strategies = new HashSet<>();

        strategies.add(str -> new SimpleDateFormat("yyyy-MM-dd").parse(str));
        strategies.add(str -> new SimpleDateFormat("yyyy").parse(str));
    }

    public Date parse(String str) {
        Date date = null;
        for (parseStrategy strategy : strategies) {
            try {
                date = strategy.parse(str);
                break;
            } catch (Exception ignored) {
            }
        }

        return date;
    }

    public void register(parseStrategy strategy) {
        strategies.add(strategy);
    }

    public interface parseStrategy {
        Date parse(String str) throws Exception;
    }

}
