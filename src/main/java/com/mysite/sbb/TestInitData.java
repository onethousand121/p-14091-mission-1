package com.mysite.sbb;


import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;

//@Profile("test")
@Configuration
@RequiredArgsConstructor

public class TestInitData {
    @Autowired
    @Lazy
    private TestInitData self;
    private final QuestionRepository questionRepository;

    @Bean
    ApplicationRunner testInitDataApplicationRunner() {
        return args -> {
            self.work1();
        };
    }

    @Transactional
    void work1() {
        if (questionRepository.count() > 0) return;
        Question q1 = new Question();
        q1.setSubject("1번 질문 제목");
        q1.setContent("1번 질문 내용");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("2번 질문 제목");
        q2.setContent("2번 질문 내용");
        q2.setCreateDate(LocalDateTime.now());
        q2.addAnswer("자동 생성 답변 2");
        questionRepository.save(q2);
    }
}
