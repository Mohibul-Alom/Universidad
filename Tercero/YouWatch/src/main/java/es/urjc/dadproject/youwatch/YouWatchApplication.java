package es.urjc.dadproject.youwatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;

import javax.persistence.Entity;

@SpringBootApplication
@EntityScan
public class YouWatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouWatchApplication.class, args);
    }

}
