package com.example.quiz;

import com.example.quiz.model.Answer;
import com.example.quiz.model.Category;
import com.example.quiz.model.Question;
import com.example.quiz.model.enumeration.Difficulty;
import com.example.quiz.repository.CategoryRepository;
import com.example.quiz.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DBInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public DBInitializer(CategoryRepository categoryRepository, QuestionRepository questionRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        Category category = new Category("JAVA_PROGRAMMING");
        categoryRepository.save(category);
        Set<Answer> answerSet = new HashSet<>();
        saveQuestion(new Answer("14", false), new Answer("13", false),
                new Answer("12", false), new Answer("15", true),
                answerSet, "Сколько версий JDK существует?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("Thread", false), new Answer("Exception", false),
                new Answer("Object", true), new Answer("Subject", false),
                answerSet, "Какой класс является суперклассом для всех остальных классов?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("ArrayList", true), new Answer("TreeSet", false),
                new Answer("HashMap", false), new Answer("LinkedHashSet", false),
                answerSet, "Какой класс позволяет обращаться к элементам коллекции по индексу?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("TreeMap", false), new Answer("HashSet", false),
                new Answer("TreeSet", true), new Answer("ArrayList", false),
                answerSet, "Какой класс хранит элементы коллекции отсортированными?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("Интерфейсам", true), new Answer("Классам", false),
                new Answer("Объектам", false), new Answer("Методам", false),
                answerSet, "Благодаря чему поддерживается множественное наследование в Java?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("compare(Object o1)", false), new Answer("comparator(Object o1, Object o2)", false),
                new Answer("compareTo(Object o1, Object o2)", true), new Answer("Не нужно переопределять", false),
                answerSet, "Какой метод необходимо переопределить для использования интерфейса Comparator?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("6", false), new Answer("7", false),
                new Answer("9", false), new Answer("8", true),
                answerSet, "Сколько примитивных типов в java?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("MyClass extends String", false), new Answer("MyClass implements String", false),
                new Answer("Нельзя создать", true), new Answer("MyClass extends StringBuilder", false),
                answerSet, "Как создать наследника класса String?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("Лямбда-вырадения", false), new Answer("Модульность", true),
                new Answer("Optional", false), new Answer("try with resources", false),
                answerSet, "Что нового появилось в Java 9?", null, Difficulty.EASY, category);
        saveQuestion(new Answer("в Optional метод orElseThrow", true), new Answer("jShell", false),
                new Answer("Reactive streams", false), new Answer("binary literals", false),
                answerSet, "Что нового появилось в Java 10?", null, Difficulty.EASY, category);
    }

    public void saveQuestion(Answer firstAnswer, Answer secondAnswer, Answer thirdAnswer, Answer fourthAnswer,
                             Set<Answer> answerSet, String questionText, String imagePath, Difficulty difficulty,
                             Category category) {
        answerSet.clear();
        answerSet.add(firstAnswer);
        answerSet.add(secondAnswer);
        answerSet.add(thirdAnswer);
        answerSet.add(fourthAnswer);
        Question question = new Question(questionText, imagePath, difficulty, category, answerSet);
        questionRepository.save(question);
    }
}
