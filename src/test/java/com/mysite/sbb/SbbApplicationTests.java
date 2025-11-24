package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class SbbApplicationTests {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("findAll")
    void t1() {
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("1번 질문 제목", q.getSubject());
    }

    @Test
    @DisplayName("findById")
    void t2() {
        Question question = questionRepository.findById(1).get();
        assertThat(question.getSubject()).isEqualTo("1번 질문 제목");
    }

    @Test
    @DisplayName("findBySubject")
    void t3() {
        Question question = questionRepository.findBySubject("1번 질문 제목").get();
        assertThat(question.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findBySubjectAndContent")
    void t4() {
        Question question = questionRepository.findBySubjectAndContent("1번 질문 제목", "1번 질문 내용").get();
        assertThat(question.getId()).isEqualTo(1);

    }

    @Test
    @DisplayName("findBySubjectContaining")
    void t5() {
        List<Question> questionList = questionRepository.findBySubjectContaining("1번");
        Question question = questionList.get(0);
        assertThat(question.getSubject()).isEqualTo("1번 질문 제목");

    }

    @Test
    @DisplayName("setSubject")
    void t6() {
        Question question = questionRepository.findById(1).get();
        question.setSubject("1번 질문 제목 수정");

        Question modifiedQuestion = questionRepository.findById(1).get();
        assertThat(modifiedQuestion.getSubject()).isEqualTo("1번 질문 제목 수정");

    }

    @Test
    @DisplayName("delete")
    void t7() {
        assertThat(questionRepository.count()).isEqualTo(2);

        Question question = questionRepository.findById(1).get();
        questionRepository.delete(question);

        assertThat(questionRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("answer create")
    void t8() {
        Question question = questionRepository.findById(2).get();

        Answer answer = new Answer();
        answer.setContent("자동 생성 답변");
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);

        assertThat(answer.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("answer create onetomany")
    void t9() {
        Question question = questionRepository.findById(2).get();

        int beforeCount = question.getAnswers().size();

        Answer newAnswer = question.addAnswer("자동 생성 답변");

        assertThat(newAnswer.getId()).isEqualTo(0);

        int afterCount = question.getAnswers().size();

        questionRepository.flush(); // 영속성 컨텍스트 변경 내용 동기화

        assertThat(afterCount).isEqualTo(beforeCount + 1);
    }

    @Test
    @DisplayName("answser check")
    void t10() {
        Answer answer = answerRepository.findById(1).get();

        assertThat(answer.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("answer check onetomany")
    void t11() {
        Question question = questionRepository.findById(2).get();

        List<Answer> answers = question.getAnswers();
        assertThat(answers).hasSize(1);

        Answer answer = answers.get(0);
        assertThat(answer.getContent()).isEqualTo("자동 생성 답변 2");
    }

    @Test
    @DisplayName("findAnswer by question")
    void t12() {
        Question question = questionRepository.findById(2).get();

        Answer answer1 = question.getAnswers().get(0);

        assertThat(answer1.getId()).isEqualTo(1);
    }
}