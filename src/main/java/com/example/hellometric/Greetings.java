package com.example.hellometric;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/greeting")
public class Greetings {
    Random random;
    double mean = 3;
    double standardDeviation = 2;

    public Greetings() {
        random = new Random();
    }

//    @GetMapping
//    public double hello() throws InterruptedException {
//        double randomValue = mean + random.nextGaussian() * standardDeviation;
//        if(randomValue > 0) {
//            Thread.sleep(Math.round(randomValue) * 1000);
//        }
//        return randomValue;
//    }

    @GetMapping()
    public double helloRandom() throws InterruptedException {
        int randomValue = random.nextInt((5000 - 50) + 50);
        if (randomValue > 0) {
            Thread.sleep(Math.round(randomValue));
        }
        return randomValue;
    }
}
