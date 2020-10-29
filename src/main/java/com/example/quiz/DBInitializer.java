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

import java.util.ArrayList;
import java.util.List;

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
        List<Answer> answerList = new ArrayList<>();
        saveQuestion(new Answer("14", false), new Answer("13", false),
                new Answer("12", false), new Answer("15", true),
                answerList, "Сколько версий JDK существует?", null, false, Difficulty.EASY, category);
        saveQuestion(new Answer("Thread", false), new Answer("Exception", false),
                new Answer("Object", true), new Answer("Subject", false),
                answerList, "Какой класс является суперклассом для всех остальных классов?", null, false, Difficulty.EASY, category);
        saveQuestion(new Answer("ArrayList", true), new Answer("TreeSet", false),
                new Answer("HashMap", false), new Answer("LinkedHashSet", false),
                answerList, "Какой класс позволяет обращаться к элементам коллекции по индексу?", null, false, Difficulty.MEDIUM, category);
        saveQuestion(new Answer("TreeMap", false), new Answer("HashSet", false),
                new Answer("TreeSet", true), new Answer("ArrayList", false),
                answerList, "Какой класс хранит элементы коллекции отсортированными?", null, false, Difficulty.MEDIUM, category);
        saveQuestion(new Answer("Интерфейсам", true), new Answer("Классам", false),
                new Answer("Объектам", false), new Answer("Методам", false),
                answerList, "Благодаря чему поддерживается множественное наследование в Java?", null, false, Difficulty.EASY, category);
        saveQuestion(new Answer("compare(Object o1)", false), new Answer("comparator(Object o1, Object o2)", false),
                new Answer("compareTo(Object o1, Object o2)", true), new Answer("Не нужно переопределять", false),
                answerList, "Какой метод необходимо переопределить для использования интерфейса Comparator?", null, false, Difficulty.HARD, category);
        saveQuestion(new Answer("6", false), new Answer("7", false),
                new Answer("9", false), new Answer("8", true),
                answerList, "Сколько примитивных типов в java?", null, false, Difficulty.EASY, category);
        saveQuestion(new Answer("MyClass extends String", false), new Answer("MyClass implements String", false),
                new Answer("Нельзя создать", true), new Answer("MyClass extends StringBuilder", false),
                answerList, "Как создать наследника класса String?", null, false, Difficulty.EASY, category);
        saveQuestion(new Answer("Лямбда-вырадения", false), new Answer("Модульность", true),
                new Answer("Optional", false), new Answer("try with resources", false),
                answerList, "Что нового появилось в Java 9?", null, false, Difficulty.NIGHTMARE, category);
        saveQuestion(new Answer("в Optional метод orElseThrow", true), new Answer("jShell", false),
                new Answer("Reactive streams", false), new Answer("binary literals", false),
                answerList, "Что нового появилось в Java 10?", null, false, Difficulty.HARD, category);

        Category secondCategory = new Category("COUNTRIES");
        categoryRepository.save(secondCategory);
        saveQuestion(new Answer("Кирибати", false), new Answer("Палау", false), new Answer("Науру", true),
                new Answer("Маршалловы острова", false), answerList, "На флаге какой страны Океании изображена линия, символизирующая экватор?",
                null, false, Difficulty.NIGHTMARE, secondCategory);
        saveQuestion(new Answer("Кирибати", true), new Answer("Палау", false), new Answer("Науру", false),
                new Answer("Маршалловы острова", false), answerList, "Флаг какой страны изображен на картинке?",
                "https://res.cloudinary.com/dsnsf4ukx/image/upload/v1603614834/Kiribati_uk6wy8.png", false, Difficulty.NIGHTMARE, secondCategory);
        saveQuestion(new Answer("Италия", false), new Answer("Франция", true), new Answer("Испания", false),
                new Answer("Россия", false), answerList, "Флаг какой страны изображен на картинке?", "https://res.cloudinary.com/dsnsf4ukx/image/upload/v1603615118/France_oyifgk.jpg",
                false, Difficulty.MEDIUM, secondCategory);
        saveQuestion(new Answer("Италия", false), new Answer("Франция", false), new Answer("Босния и Герцеговина", true),
                new Answer("Болгария", false), answerList, "Флаг какой страны изображен на картинке?", "https://res.cloudinary.com/dsnsf4ukx/image/upload/v1603615377/BosniyaAndHercegovina_ubuzls.png",
                false, Difficulty.HARD, secondCategory);
        saveQuestion(new Answer("Северного Ледовитого", false), new Answer("Атлантического", false), new Answer("Индийского", false),
                new Answer("Южного", true), answerList, "Воды какого океана не омывают Евразию?", null, false,
                Difficulty.EASY, secondCategory);
    }

    public void saveQuestion(Answer firstAnswer, Answer secondAnswer, Answer thirdAnswer, Answer fourthAnswer,
                             List<Answer> answerList, String questionText, String imagePath, Boolean isTemporal,
                             Difficulty difficulty, Category category) {
        answerList.clear();
        answerList.add(firstAnswer);
        answerList.add(secondAnswer);
        answerList.add(thirdAnswer);
        answerList.add(fourthAnswer);
        Question question = new Question(questionText, imagePath, isTemporal, difficulty, category, answerList);
        questionRepository.save(question);
    }
}
