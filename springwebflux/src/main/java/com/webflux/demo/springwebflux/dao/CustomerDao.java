package com.webflux.demo.springwebflux.dao;

import com.webflux.demo.springwebflux.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    private static void sleepExectuion(int i)   {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomer() {
        return IntStream.rangeClosed(1, 10)
                .peek(CustomerDao :: sleepExectuion)
                .peek(i-> System.out.println("processing count : "+i))
                .mapToObj(i -> new Customer(i, "customer" + i))
                .collect(Collectors.toList());

    }

    public Flux<Customer> getCustomerStream() {
        return Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i->System.out.println("processing count in stream " + i))
                .map(i-> new Customer(i, "customer" + i));

    }

    public Flux<Customer> getCustomerList() {
        return Flux.range(1, 10)
                .doOnNext(i->System.out.println("processing count in stream " + i))
                .map(i-> new Customer(i, "customer" + i));

    }

}
