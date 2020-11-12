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
        Category category = new Category("History");
        categoryRepository.save(category);
        List<Answer> answerList = new ArrayList<>();
        saveQuestion(new Answer("1941", false), new Answer("1938", false),
                new Answer("1940", false), new Answer("1939", true),
                answerList, "В каком году началасть Вторая мировая война?", null, false, Difficulty.EASY, category);

        saveQuestion(new Answer("1 сентября 1939", false), new Answer("23 июня 1941", false),
                new Answer("22 июня 1941", true), new Answer("1 сентября 1941", false),
                answerList, "Дата начала Великой Отечественной Войны?", null, false, Difficulty.EASY, category);

        saveQuestion(new Answer("1918", true), new Answer("1945", false),
                new Answer("1917", false), new Answer("1944", false),
                answerList, "В каком году закончилась Первая мировая война?", null, false, Difficulty.EASY, category);

        Category literatureCategory = new Category("Literature");
        categoryRepository.save(literatureCategory);
        saveQuestion(new Answer("Александр Пушкин", false), new Answer("Николай Гоголь", false),
                new Answer("Лев Толстой", true), new Answer("Иван Тургенев", false),
                answerList, "Автор романа Война и мир?", null, false, Difficulty.EASY, literatureCategory);

        saveQuestion(new Answer("Уильям Шекспир", true), new Answer("Александр Пушкин", false),
                new Answer("Иван Тургенев", false), new Answer("Лев Толстой", false),
                answerList, "Автор трагедии Ромео и Джульетта?", null, false, Difficulty.MEDIUM, literatureCategory);

        saveQuestion(new Answer("Александр Пушкин", false), new Answer("Максим Горький", false),
                new Answer("Иван Тургенев", true), new Answer("Лев Толстой", false),
                answerList, "Автор рассказа Муму?", null, false, Difficulty.MEDIUM, literatureCategory);

        saveQuestion(new Answer("Абудаби", false), new Answer("Валенсия", false),
                new Answer("Ванкувер", false), new Answer("Стратфорд-на-Эйвоне", true),
                answerList, "В каком городе родился Уильям Шекспир?", null, false, Difficulty.HARD, literatureCategory);

        Category moneyCategory = new Category("Money");
        saveQuestion(new Answer("Доллар", false), new Answer("Евро", false),
                new Answer("Фунт стерлингов", true), new Answer("Британский Шиллинг", false),
                answerList, "Валюта Великобритании?", null, false, Difficulty.MEDIUM, moneyCategory);

        saveQuestion(new Answer("Евро", false), new Answer("Швейцарский франк", true),
                new Answer("Доллар", false), new Answer("Фунт стерлингов", false),
                answerList, "Валюта Швейцарии?", null, false, Difficulty.MEDIUM, moneyCategory);

        Category secondCategory = new Category("Geography");
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
        saveQuestion(new Answer("Нил", true), new Answer("Амазонка", false),
                new Answer("Янцзы", false), new Answer("Хуанхэ", false),
                answerList, "Самая длинная река в мире?", null, false, Difficulty.HARD, secondCategory);
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
