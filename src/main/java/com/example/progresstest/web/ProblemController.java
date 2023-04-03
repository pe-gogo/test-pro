package com.example.progresstest.web;

import com.example.progresstest.service.PhilosopherProblem;
import com.example.progresstest.service.ReadWriterProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemController {

    @Autowired
    PhilosopherProblem philosopherProblem;

    @Autowired
    ReadWriterProblem readWriterProblem;
    @GetMapping("/simulate")
    public Object simulateProblem(@RequestParam String problemType) {
        Object result;
        switch (problemType) {
            case "philosopher":
                result = philosopherProblem. simulatePhilosopherProblem();
                break;
            case "reader-writer":
                result = readWriterProblem.simulateReaderWriterProblem();
                break;
            case "producer-consumer":
                result = simulateProducerConsumerProblem();
                break;
            default:
                result = "未知问题类型";
        }
        return result;
    }

    private String simulatePhilosopherProblem() {
        // 实现哲学家就餐问题模拟
        return "哲学家就餐问题模拟结果";
    }

    private String simulateReaderWriterProblem() {
        // 实现读者-写者问题模拟
        return "读者-写者问题模拟结果";
    }

    private String simulateProducerConsumerProblem() {
        // 实现生产者-消费者问题模拟
        return "生产者-消费者问题模拟结果";
    }
}
